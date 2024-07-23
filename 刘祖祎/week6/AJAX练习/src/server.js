//1. 引入express
const express = require('express');

//2. 创建应用对象
const app = express();

//3. 创建路由规划
//requset 是对请求报文的封装
//response 是对相应报文的封装
app.get('/server', (request, response)=>{
    //设置响应头  设置允许跨域
    response.setHeader('Access-Control-Allow-Origin','*');
    //设置响应体
    response.send('HELLO AJAX');
});

app.post('/server', (request, response)=>{
    //设置响应头  设置允许跨域
    response.setHeader('Access-Control-Allow-Origin','*');
    //设置响应体
    response.send('HELLO AJAX POST');
});

app.all('/json-server', (request, response)=>{
    //设置响应头  设置允许跨域
    response.setHeader('Access-Control-Allow-Origin','*');
    //响应头
    response.setHeader('Access-Control-Allow-Headers','*')
    //这里是允许所有自定义的请求头
    //响应一个数据
    const data = {
        name: 'youshanji'
    };
    //对对象进行字符串的转换
    let str = JSON.stringify(data);
    //设置响应体
    response.send(str);//send里只能是一个字符串类型，所以我们如果想传入上面的data对象的话
});

//针对IE缓存
app.get('/ie', (request, response)=>{
    //设置响应头  设置允许跨域
    response.setHeader('Access-Control-Allow-Origin','*');
    //设置响应体
    response.send('HELLO IE -3');
});

//延迟响应
app.get('/delay', (request, response)=>{
    //设置响应头  设置允许跨域
    response.setHeader('Access-Control-Allow-Origin','*');
    setTimeout(()=>{
        //设置响应体
        response.send('延迟响应');
    }, 3000)
});

//4. 监听端口启动服务
app.listen(8000, ()=>{
    console.log("服务已经启动，8000端口监听中");
})
