import express from 'express'
import http from 'http'
import 'dotenv/config'
import pkg from 'pg'
import { createTerminus } from '@godaddy/terminus'
const { Pool } = pkg

// pool of clients to connect to DB
const pool = new Pool({
    connectionString: process.env.CONNECTION_STRING,
})

const app = express()

// User endpoint
app.get('/users/:id', async (req, res) => {
    await pool.connect()

    const { rows } = await pool.query('SELECT * FROM users')
    const id = Number(req.params.id)
    const user = rows.filter(user => Number(user.user_id) === id)

    res.send(user)
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
