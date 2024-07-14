//1、引入express
const express = require('express');

//2、创建应用对象
const app = express();

//3、创建路由规则
// request 是对请求报文的封装
// response 是对响应报文的封装
app.get('/server', (request, response) => {
  //设置响应头
  response.setHeader('Access-Control-Allow-Origin', '*');
  //设置响应体
  response.send('HELLO AJAX GETdd');
});

app.all('/server', (request, response) => {
  //设置响应头
  response.setHeader('Access-Control-Allow-Origin', '*');
  response.setHeader('Access-Control-Allow-Headers', '*');
  //设置响应体
  response.send('HELLO AJAX POST');
});

//axios
app.all('/axios-server', (request, response) => {
  //设置响应头
  response.setHeader('Access-Control-Allow-Origin', '*');
  response.setHeader('Access-Control-Allow-Headers', '*');
  //设置响应体
  response.send('HELLO AJAX POST');
});


//ie缓存
app.get('/ie', (request, response) => {
  //设置响应头
  response.setHeader('Access-Control-Allow-Origin', '*');
  //设置响应体
  response.send('HELLO IE');
});

//延迟响应
app.get('/delay', (request, response) => {
  //设置响应头
  response.setHeader('Access-Control-Allow-Origin', '*');
  setTimeout(() => {
    //设置响应体
    response.send('延时响应');
  }, 3000);
});

app.all('/json-server', (request, response) => {
  //设置响应头
  response.setHeader('Access-Control-Allow-Origin', '*');
  response.setHeader('Access-Control-Allow-Headers', '*');
  // 响应一个数据
  const data = {
    name: 'cvbn'
  };
  //由于send只能发送字符串，所以对对象进行字符串转换
  let str = JSON.stringify(data);
  //设置响应体
  response.send(str);
});

//4、监听端口启动服务
app.listen(8000, () => {
  console.log("服务已经启动, 8000 端口监听中...");
})