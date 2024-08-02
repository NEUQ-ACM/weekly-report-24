const express = require('express');

const app = express();

app.get('/server', (request, response) => {
  response.setHeader('Access-Control-Allow-Origin', '*')
  response.send('Hello Ajax')
})


app.post('/server', (request, response) => {
  response.setHeader('Access-Control-Allow-Origin', '*')
  response.send(`<h1>Hello Ajax<h1/>`)
})
app.all('/json-server', (request, response) => {
  response.setHeader('Access-Control-Allow-Origin', '*')
  response.setHeader('Access-Control-Allow-Headers', '*')
  // response.send(`<h1>Hello Ajax<h1/>`)
  const data = {
    name: 'atguigu2'
  }
  let str = JSON.stringify(data)
  response.send(str)
})

app.get('/delay', (request, response) => {
  response.setHeader('Access-Control-Allow-Origin', '*')
  setTimeout(() => {
    response.send('Hello, Delay')
  }, 0);
})


app.all('/axios-server', (request, response) => {
  response.setHeader('Access-Control-Allow-Origin', '*')
  response.setHeader('Access-Control-Allow-Headers', '*')
  response.send('Hello, Axios')
})
// app.psot('/axios-server', (request, response) => {
//   response.setHeader('Access-Control-Allow-Origin', '*')
//   response.setHeader('Access-Control-Allow-Headers', '*')
//   response.send('Hello, Axios')
// })

app.listen(8000, () => {
  console.log('Server is running on port 8000');
})