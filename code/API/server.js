import express from 'express'
import data from './dummyData.js'


const app = express()
const port = 3001

app.listen(port, () => {
    console.log(`server is running on port ${port}`)
})

app.get('/users/:id', (req, res) => {
    const id = Number(req.params.id)
    const user = data.filter(data => data.id === id)

    res.send(user)
})