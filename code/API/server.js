import express from 'express'
import http from 'http'
import 'dotenv/config'
import pkg from 'pg'
import { createTerminus } from '@godaddy/terminus'
import bcrypt from 'bcrypt'
import jwt from 'jsonwebtoken'

const { Pool } = pkg

const saltRounds = 12;

// pool of clients to connect to DB
const pool = new Pool({
    connectionString: process.env.CONNECTION_STRING,
})

const app = express()
app.use(express.json())

// User endpoint
app.get('/users/:id', async (req, res) => {
    const { rows } = await pool.query('SELECT * FROM users')
    const id = Number(req.params.id)
    const user = rows.filter(user => Number(user.user_id) === id)

    res.send(user)
})

app.post('/login', async (req, res) => {
    console.log('request made')
    console.log(req.params.username, req.body.username)

    const username = req.body.username
    const password = req.body.password

    const { rows } = await pool.query(`SELECT * FROM users WHERE username = '${username}'`);
    if (rows.length === 0) {
        res.status(401).send({body: 'Invalid Credentials'})
        await pool.release()
        return
    }

    const dbPasswordHash = rows[0].password_hash
    const dbPass = bcrypt.compareSync(password, dbPasswordHash)

    if (dbPass) {
        const userId = rows[0].user_id
        const token = jwt.sign({user_id: rows[0].user_id}, process.env.JWT_SECRET, {
            expiresIn: '24h'
        })
        res.status(200).send({ token: token, user_id: userId, body: 'Login Succeeded' })
    } else {
        res.status(401).send({ body: 'Invalid Credentials' })
    }
})

app.post('/createAccount', async (req, res) => {
    const client = await pool.connect()

    const username = req.body.username
    const password = req.body.password

    const { rows } = await client.query(`SELECT * FROM users WHERE username = '${username}'`)
    if (rows.length !== 0) {
        res.status(400).send({body: 'Username already exists'})
        client.release()
        return
    }

    bcrypt.hash(password, saltRounds, async function (err, hash) {
        if (err) {
            res.status(400).send({body: 'Error occured during creation'})
            client.release()
            return
        }

        await client.query(`INSERT INTO users (username, password_hash) VALUES ('${username}', '${hash}')`)
        res.status(200).send({body: 'Account created'})
    })

    client.release()
})

// get all journals connected to user
app.get('/journals/:id', async (req, res) => {
    const userId = req.params.id

    const { rows } = await pool.query(`SELECT * FROM journalentries where user_id = ${userId}`)

    res.send(rows)
})


app.post('/createJournal', authenticateToken, async (req, res) => {
    const client = await pool.connect();

    const { entryText, keywords, mood, userId, year, month, day } = req.body;

    try {
        console.log('Year:', year, 'Month:', month, 'Day:', day);
        const entryDate = new Date(year, month, day); 
        console.log('Constructed Date:', entryDate);
                // Check if an entry for this date and user already exists
        const existingEntry = await client.query(`SELECT * FROM journalentries WHERE user_id = $1 AND entry_date = $2`, [userId, entryDate]);

        if (existingEntry.rows.length > 0) {
            // Entry exists, update it
            await client.query(`UPDATE journalentries SET entry_text = $1, keywords = $2, mood = $3 WHERE user_id = $4 AND entry_date = $5`, [entryText, keywords, mood, userId, entryDate]);
            console.log('Updated entry')
        } else {
            // No entry exists, insert a new one
            await client.query(`INSERT INTO journalentries (user_id, entry_text, keywords, mood, entry_date) VALUES ($1, $2, $3, $4, $5)`, [userId, entryText, keywords, mood, entryDate]);
            console.log('Inserted new entry')
        }
        res.status(200).send({body: 'Journal entry processed'});
    }
    catch(e) {
        console.log(e);
        res.status(400).send({body: 'Something went wrong!'});
    }

    client.release()
});


app.get('/getJournalEntry', async (req, res) => {
    const { userId, year, month, day } = req.query;
    const entryDate = new Date(year, month, day);

    try {
        const { rows } = await pool.query(`SELECT * FROM journalentries WHERE user_id = $1 AND entry_date = $2`, [userId, entryDate]);

        if (rows.length > 0) {
            res.status(200).send(rows[0]);
        } else {
            res.status(404).send({body: 'No entry found for this date'});
        }
    } catch(e) {
        console.log(e);
        res.status(400).send({body: 'Something went wrong!'});
    }
});


const server = http.createServer(app)

// kills db connection when server is killed
function onSignal () {
    console.log('server is starting cleanup')
    return Promise.all([
        pool.end()
    ])
}
  
async function onHealthCheck () {
    // should check if DB connection is live
    return Promise.resolve(true)
}


function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1]; // Bearer TOKEN

    if (token == null) return res.sendStatus(401); // if there isn't any token

    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
        if (err) return res.sendStatus(403);
        req.user = user;
        next(); // pass the execution off to whatever request the client intended
    });
}


// automatically calls onSignal when server is killed
createTerminus(server, {
    signal: 'SIGINT',
    healthChecks: { '/healthcheck': onHealthCheck },
    onSignal
})

server.listen(process.env.PORT)