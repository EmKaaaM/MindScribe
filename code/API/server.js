import express from 'express'
import http from 'http'
import 'dotenv/config'
import pkg from 'pg'
import { createTerminus } from '@godaddy/terminus'
import bcrypt from 'bcrypt'
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
    await pool.connect()

    const { rows } = await pool.query('SELECT * FROM users')
    const id = Number(req.params.id)
    const user = rows.filter(user => Number(user.user_id) === id)

    res.send(user)
})

app.post('/login', async (req, res) => {
    await pool.connect()

    console.log('request made')
    console.log(req.params.username, req.body.username)

    const username = req.body.username
    const password = req.body.password

    const { rows } = await pool.query(`SELECT * FROM users WHERE username = '${username}'`);
    if (rows.length === 0) {
        res.status(401).send({body: 'Invalid Credentials'})
        return
    }

    const dbPasswordHash = rows[0].password_hash
    const dbPass = bcrypt.compareSync(password, dbPasswordHash)

    if (dbPass) {
        res.status(200).send({body: 'Login Succeeded'})
    }
    else {
        res.status(401).send({body: 'Invalid Credentials'})
    }
})

app.post('/createAccount', async (req, res) => {
    await pool.connect()

    const username = req.body.username
    const password = req.body.password

    const { rows } = await pool.query(`SELECT * FROM users WHERE username = '${username}'`)
    if (rows.length !== 0) {
        res.status(400).send({body: 'Username already exists'})
    }

    bcrypt.hash(password, saltRounds, async function (err, hash) {
        if (err) {
            res.status(400).send({body: 'Error occured during creation'})
            return
        }

        await pool.query(`INSERT INTO users (username, password_hash) VALUES ('${username}', '${hash}')`)
        res.status(200).send({body: 'Account created'})
    })
})

app.get('/journals/:id', async (req, res) => {
    await pool.connect()

    const userId = req.params.id

    const { rows } = await pool.query(`SELECT * FROM journalentries where user_id = ${userId}`)

    const keywordArray = rows.keywords.split(",")

    res.send({...rows, keywords: keywordArray})
})

app.post('createJournal', async (req, res) => {
    await pool.connect()

    const entryText = req.body.entryText
    const keywords = req.body.keywords
    const mood = req.body.mood

    // need to check if journal for current date already exists, and if so replace or reject request
    await pool.query(`INSERT INTO journal_entries (entry_text, keywords, mood) VALUES ('${entryText}', '${keywords}', ${mood})`)

    res.status(200).send({body: 'Journal Created'})
})

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

// automatically calls onSignal when server is killed
createTerminus(server, {
    signal: 'SIGINT',
    healthChecks: { '/healthcheck': onHealthCheck },
    onSignal
})

server.listen(process.env.PORT)