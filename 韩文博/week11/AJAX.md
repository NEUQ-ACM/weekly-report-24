## AJAX
Asynchronous JavaScript and XML，异步的JS和SML，它最大的特点就是可以在不刷新页面的情况下向服务端发送HTTP请求，并得到HTTP响应。如获取二级菜单，用户注册查用户名等
XML是Extensible Markup Language，可拓展标记语言。它是被设计来传输和存储数据的。HTML与其类似，但是HTML都是预定义标签，拿来直接就用，而XML不是预定义标签，全是自定义标签。如今XML的地位已经被JSON取代了
HTTP是Hypertext Transport Protocol，超文本传输协议，其详细规定了浏览器和万维网服务器之间相互通信的规则。我们使用的绝大部分网页传输数据都是用HTTP协议。
#### AJAX的优缺点
优点：AJAX可以无需刷新页面就与服务端进行通信，而且可以允许程序根据用户事件来更新部分页面的内容
缺点：AJAX没有浏览历史，不能回退，而且存在跨域的问题（同源），AJAX默认不允许跨域的操作。SEO不友好，程序是根据AJAX从服务端返回的数据动态创建的显示数据，因此页面的源代码是没有这些数据的，对爬虫和搜索引擎等就不友好
#### HTTP简介
重点是格式和参数
请求报文：分为行、头、空行、体，在请求行中有请求类型（常见如GET、POST等），URL路径/查询字符串，HTTP协议版本。请求头有Host、Cookie、Content-type、User-Agent等，请求头的格式是名字冒号空格+值。如果是GET请求体为空，如果是POST请求体可以不为空
响应报文：行、头、空行、体，行有HTTP协议版本，相应状态码，相应字符串。响应头和请求头的格式相同，响应体是主要的返回内容，如返回需要渲染的内容等
#### express
express可以搭建一个web应用框架，我们可以利用它来传递HTTP请求。注意使用程序的时候要注意端口是否被占用，如果被占用可以在启动中的的服务crtl+c来关闭服务
```javascript
//引入express
const express = require('express')
//创建应用对象
const app = express()
//创建路由规则，request是对请求报文的封装，response是对响应报文的封装
app.get('/server', (request, response)=> {
  //如果url的路径是函数里面的参数，就会执行回调函数里面的代码，并由response做出相应
  response.send('Hello Express')
})
//监听端口，启动服务
app.listen(8000, ()=>{
  console.log("服务已经启动，8000端口监听中")
})
//随后在控制台以node执行文件来启动服务
```
`response.setHeader('名字', '值')`可以设置响应头，`response.setHeader('Access-Control-Allow-Origin', '*')`可以设置允许跨域
`response.send('内容')`可以设置响应体
```javascript
... = function() {
  //看到xhr要想到是ajax
  const xhr = new XMLHttpRequest()
  //初始化并设置请求方法和url
  xhr.open('GET', 'http://127.0.0.1:8000/server')
  xhr.send()
  //绑定事件 处理服务端返回的结果
  xhr.onreadystatechange = fucntion() {
    //总共有5中readystate，因此完整的传递会执行4次函数
    if(xhr.readystate===4) {
      if(xhr.status>=200 && xhr.status<300){
        
      }
    }
  }
}

```
readystate是xhr对象中的属性，有值0 未初始化（一开始就是0），1 open方法调用完毕，2 send方法调用完毕，3 服务端返回部分结果，4 服务端返回全部结果
xhr有status状态码，statusText状态字符串，getAllResponseHeaders()得到所有响应头，response响应体
如果想对传入参数，可以在url后面追加`?参数a=数值&参数b=数值`
```javascript
... = function() {
  const xhr = new XMLHttpRequest()
  //初始化并设置请求方法和url
  xhr.open('GET', 'http://127.0.0.1:8000/server')
  //设置请求头
  // xhr.setRequestHeader('头的名字','头的值')
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
  //POST的请求体在send方法中设置，其实可以传递任意格式的数据，前提是服务端可以处理
  xhr.send(''a:100&b:200)
  
  xhr.onreadystatechange = fucntion() {
    //总共有5中readystate，因此完整的传递会执行4次函数
    if(xhr.readystate===4) {
      if(xhr.status>=200 && xhr.status<300){
        
      }
    }
  }
}

```
```javascript
//服务端
const express = require('express')
const app = express()

app.all('/json-server', (request, response)=> {
  response.setHeader('Access-Control-Allow-Origin', '*')
  response.setHeader('Access-Control-Allow-Headers', '*')
  //响应一个数据
  const data = {
    name: 'username'
  }
  let str = JSON.stringfy(data)
  response.send('Hello Express')
})
//监听端口，启动服务
app.listen(8000, ()=>{
  console.log("服务已经启动，8000端口监听中")
})

//页面
  //xhr.responseType = 'json'这条语句会把response自动转换成json格式。在使用的时候可以直接用.属性访问json内部属性
    
  ... = function() {
  if(xhr.readystate===4) {
      if(xhr.status>=200 && xhr.status<300){
        //手动对数据转化
        let date = JSON.parse(xhr.response)
      }
    }
}
```
#### AJAX的IE缓存问题
IE会对AJAX的请求结果做缓存，那么下次再发送请求会先对缓存进行查询，这样对于时效性比较强的数据会影响结果，我们可以在获取数据的时候加上时间戳，如`xhr.open('GET', 'http://127.0.0.1:8000/ie?t='+Date.now())`，实际工作中我们有工具可以自动防止这些问题
#### AJAX请求超时和网络异常处理
```javascript
//服务端
app.get('/delay', (request, response)=> {
  //设置允许跨域
  response.setHeader('Access-Control-Allow-Origin', '*')
  setTimeout(()=>{
    response.send('延时响应')
  }, 1000)
})

//页面
<script>
  xhr.timeout = 2000
  //超时回调
  xhr.ontimeout = function() {
    alert("网络异常，请稍后重试")
  }
  //网络异常回调
  xhr.onerror = function() {
    alert("你的网络似乎出了一些问题")
  }
  
</script>
```
XMLHttpRequest对象有一个abort方法，可以手动取消请求
有时候我们会需要解决重复发送请求的问题（防抖），我们可以设置一个标识变量，如isSending表示是否正在发送请求。在发送一个请求后把isSending变为true表示正在发送，然后再onreadystatechange方法中，如果我们得到readyState是4（完全接收），就可以把isSending改回false，表示发送完毕。而在发送请求以前先检验一下isSending是否为true，如果是正在发送请求，再次进行请求就可以abort掉当前的请求
## jQuery中的AJAX
首先在head中引入jQuery的js文件，绑定事件。第一个参数是URL，第二个参数是传过去的数据，第三个参数是回调函数，第四个参数是响应体类型（写json比较常用）
```typescript
//GET
<script>
  $('button').eq(0).click(function()=>{
    $.get('http://127.0.0.1:8000/jquery-server', {a:100, b:200}, function(data){
      //响应体
    })
  })
</script>
//POST，POST传入的参数不再URL后面，在报文里面
<script>
  $('button').eq(1).click(function()=>{
    $.get('http://127.0.0.1:8000/jquery-server', {a:100, b:200}, function(data){
      
    })
  })
</script>
//jquery通用
<script>
  $('button').eq(2).click(function()=>{
    $.ajax({
      //url
      url: "http://127.0.0.1:8000/jquery-server"
      data: {a:100, b:200},
      type: 'GET',
      //响应体结果
      dataType: 'json',
      //执行成功的回调
      success: function(data){
        
      },
      //超时时间
      timeout: 2000,
      //失败的回调
      error: function(){
        
      },
      //头信息
      header: {
        c:300,
        d:400
      }
    })
  })
</script>
```
## Axios
可以在node.js里面发送http请求，支持Promise，有拦截器、数据转换、取消请求、JSON自动转换和安全保护等功能。但是归根结底还是要发送AJAX请求
```typescript
axios.defaults.baseURL = 'http://127.0.0.1:8000'
//get
function(){
  //配置baseURL这里发请求就不需要填URL前面一半了
  axios.get('/axios-server', {
    params: {
      id: 100,
      vip:7
    },
    headers: {
      name: 'username',
      age: 20
    }
    //GET没有请求体
  }).then(value=>{
    //jquery用回调函数，axios基于promise回调
  })
}
//post
function(){
  //配置baseURL这里发请求就不需要填URL前面一半了
  axios.get('/axios-server', {
    params: {
      id: 100,
      vip:7
    },
    headers: {
      name: 'username',
      age: 20
    },
    //请求体
    data: {
      username: 'admin',
      password: 'admin'
    }
    //请求体也可以作为第二个参数，然后其他参数的对象放在第三个参数
  })
}
//axios通用方法
function(){
  axios({
    //配置baseURL可以不加前面的地址
    url:'/axios-server',
    method: 'POST',
    params: {
      vip: 10,
      level: 30
    },
    headers: {
      a: 100,
      b: 200
    },
    data: {
      username: 'admin',
      password: 'admin'
    }
  }).then(response=>{
    
  })
}
```
## fetch()
`function(){ fetch() }`发送请求的时候直接调用fetch函数，接收两个参数，第一个是url（还可以接受一个Request对象），第二个是可选的配置项（比如method请求方法、headers、body等），返回一个promise对象，可以用.then继续操作
## 同源
同源策略是一种安全机制，说的是发起请求的网页和ajax请求的目标资源网站的url的协议、域名、端口号都是完全相同的，不使用同源策略就是跨域
#### JSONP
JSON with Padding是一个非官方的跨域解决方案，只支持get请求。有些标签天生具有跨域能力，如img、link、script。JSONP借助script进行跨域。
我们可以借助script标签的src发送一个http请求，然后我们在服务端返回一个js函数调用的代码，这样浏览器就可以直接解析js代码。利用JSON就可以添加更复杂的代码
#### CORS
Cross-Origin Resource Sharing跨域资源共享，是官方的跨域解决方案，它的特点是不需要在客户端做特殊操作，而是在服务器中处理，支持get和post。它新增了一组HTTP首部字段，允许服务器声明哪些源站通过浏览器有权限访问哪些资源。也就是说CORS通过设置一种响应头来告诉浏览器该对什么响应放行
`response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500")`，就是说这个http协议、域名127.0.0.1、端口5500才可以向服务发送请求
还有比如Access-Control-Expose-Headers可以指定暴露的头信息等
