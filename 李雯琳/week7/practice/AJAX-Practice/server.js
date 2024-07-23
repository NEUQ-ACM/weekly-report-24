const express = require('express');
const { stringify } = require('querystring');

const app = express();

app.get('/server',(request,response)=>{
  // 设置响应头 允许跨域
  response.setHeader("Access-Control-Allow-Origin",'*');

  //设置响应体
  response.send("Hello AJAX");
});

app.post('/server',(request,response)=>{
  response.setHeader("Access-Control-Allow-Origin",'*');
  response.send("HAVE BEEN POSTED");
});

app.all('/json-server',(request,response)=>{
  response.setHeader("Access-Control-Allow-Origin","*");
  response.setHeader("Access-Control-Allow-Header","*");
  const data = {
    name: `yume`
  };
  const str = JSON.stringify(data);
  response.send(str);
});

app.listen(8000,()=>{
  console.log("服务已启动");
});