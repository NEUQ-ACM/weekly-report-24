vue是一套前端框架，免除原生JS中的DOM操作，简化书写。基于MVVM（model-view-viewModel）的思想，实现数据的双向绑定，将编程的关注点放在数据上。是一种半成品软件，是一套可重用的、通用的、软件基础代码模型，基于框架进行开发可以更加快捷、更加高效
![image.png](https://cdn.nlark.com/yuque/0/2024/png/40611437/1723898713203-474333b5-0adb-49c7-8852-8b7b5b078b5a.png#averageHue=%23f7df96&clientId=ud9f07777-d74d-4&from=paste&height=230&id=uce7e35ce&originHeight=345&originWidth=745&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=58584&status=done&style=none&taskId=uef5c50f1-8f53-43fa-a54b-d700e342c02&title=&width=496.6666666666667)

- Model中包含很多业务数据以及数据的处理方法
- View是视图层，只负责数据的展示，可以理解为就是DOM元素
- ViewModel是用来沟通Model和View的桥梁，实现它们两个的数据绑定。在Model中变更的数据，View会自动更新视图层中的显示；而一旦视图层中的数据变化了，被Model监测到以后，也会自动地更新Model中的数据（双向数据绑定）
## 入门
首先新建HTML文件，然后在script的src绑定Vue.js文件（本地或在线URL）。在script区域创建Vue核心对象并定义数据模型
```javascript
<script>
  new Vue({
    el: "#app",//CSS选择器，选id
    data: {//数据模型（对象）
      message: "Hello Vue!"
    }
  })  
</script>

//视图
<div id="app">//这样上面的Vue就会接管这个div区域
  <input type="text" v-model="message">//v-model绑定message数据
  {{ message }}//这里就会栈是message
</div>
```
插值表达式：就是`{{ 表达式 }}`，里面可以是变量、三元运算符、函数调用、算术运算
#### vue常用指令

- v-bind：为HTML标签绑定属性值，如href，css样式等。`<a v-bind:href="url"></a>`，在Vue中就可以给data填url数据，这样这个a标签的href就是动态的。一般用的时候可以省略掉v-bind
- v-model：在表单元素上创建双向数据绑定。`<input type="text" v-model="url">`。这样在表单元素中的数据就会自动绑定到url上面。

通过v-bind或者v-model绑定的变量，必须在数据模型中声明

- v-on：为HTML标签绑定事件。`<input type="button" value="button01" v-on:click="onClick()">`，这样就会给这个button绑定一个onClick方法，不过在Vue中要在methods属性里面定义这个方法`new Vue({el:"#app", data: {...}, methods: {toClick:function(){} }})`。可以简写为`@click="toClick()"`
- v-if、v-else-if、v-else：条件性渲染某元素，控制的是到底渲染还是不渲染这个元素。`<span v-if="age<=35">年轻人</span>`，`<span v-else-if="age>35 && age<60">中年人</span>`，`<span v-else>老年人</span>`，这三个里面就会根据age来决定是否渲染
- v-show：根据条件展示某元素，切换的是display的值，一定是渲染了元素，但是不一定展示出来。`<span v-show="age<=35">年轻人</span>`，就算age≤35，这个span元素还是会渲染出来，只是被display了
- v-for：列表渲染，遍历容器的元素或者是对象的属性。比如我们想把Vue里面data的某个数组遍历，`<div v-for="addr in addrs">{{ addr }}</div>`，如果我们需要拿到当前遍历的索引，可以`<div v-for="(addr, index) in addrs">{{ index }}</div>`
#### Vue的生命周期
生命周期就是指一个对象从创建到销毁的整个过程

| **状态** | **阶段周期** |
| --- | --- |
| beforeCreate | 创建前 |
| created | 创建后 |
| beforeMount | 挂载前 |
| mounted | 挂载完成 |
| beforeUpdate | 更新前 |
| updated | 更新后 |
| beforeDestroy | 销毁前 |
| destroyed | 销毁后 |

![image.png](https://cdn.nlark.com/yuque/0/2024/png/40611437/1723901482545-84f2e727-a03d-45a4-962a-469a717ebce4.png#averageHue=%23fcf9f7&clientId=ud9f07777-d74d-4&from=paste&height=671&id=ubc665bc6&originHeight=1006&originWidth=1840&originalType=binary&ratio=1.5&rotation=0&showTitle=false&size=423948&status=done&style=none&taskId=ufb9d850c-2547-4aca-9349-643107d5297&title=&width=1226.6666666666667)
我们要用Vue，首先就要new一个Vue。随后开始触发这个Vue的生命周期，开始是beforeCreate，当对象创建完毕后就会触发created。渲染完页面后就开始挂载组件，触发beforeMount和mounted。一整个挂载完毕并启动服务后，就会不断检测View和Model有没有被修改，如果有就触发一次update（beforeUpdate和updated）。最后组件不再被用了，开始准备销毁，触发beforeDestory，最后完全销毁完毕就触发destoryed
我们可以在mounted里面挂载发送请求到服务端的方法，来获取服务端的数据等
## Ajax
Asynchronous JavaScript And XML，异步的JS和XML。可以实现数据交换，通过Ajax给服务器发送请求，并获取服务器相应的数据；还可以实现异步交互，可以在不重新加载整个页面的情况下，与服务器交换数据并更新部分网页，比如搜索引擎、校验用户名等功能
同步就是说这样的流程：在客户端进行一个请求-请求发送给服务器-服务器进行逻辑处理-服务端处理完毕，响应数据给客户端-客户端继续进行访问和请求。而在这个过程中，不管是请求的发送还是服务器处理请求，客户端都是要等待的，要等到服务端处理完毕并返回数据后才能继续执行其他操作
而异步就把每一条请求和处理都独立出来，客户端可以在某段时间发送多次请求，而服务端也可以一下接收多条请求并处理，而在这个过程中客户端仍旧可以继续操作或者发送请求，而服务端也在处理请求和等待请求的发送
#### 原生Ajax
首先创建XMLHttpRequest对象，用于和服务器交换数据`var xmlHttpRequest = new XMLHttpRequest()`，然后向服务器发送异步请求`xmlHttpRequest,open('GET', http://127.0.0.1:8000/emp/list)`，`xmlHttpRequest.send()`，最后获取到服务相应的数据`xmlHttprequest.onreadystatechange=function(){if(xmlHttpRequest.readyState==4&&xmlHttpRequest.status==200){console.log(xmlHttpRequest.responseText)}}`
#### Axios
Axios就是对原生Ajax的封装，简化其书写并让我们可以快速开发。Axios是基于promise的网络请求库，可以作用于node.js和浏览器中
首先引入Axios的js文件（本地或在线url），然后就可以使用Axios发送请求并获取相应的结果了
```javascript
axios({
  method: "post",
  url:"127.0.0.1:8000/emp/list"
}).then(result => {
  console.log(result.data)
})//then是成功回调函数
```
请求方式别名：axios的get、delete、post、put方法都可以简化，如`axios.get(url [, config])`，`axios.post(url [, data] [, config])`
