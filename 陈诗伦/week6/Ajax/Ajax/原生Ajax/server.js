const express = require('express');

const app = express();


app.get('/server',(request,response)=>{
  response.setHeader('Access-Control-Allow-Origin','*');
  response.send('HELLO EXPRESS');
})

app.listen(8000,()=>{
  console.log("服务器已经启动，8000端口监听中......");
})