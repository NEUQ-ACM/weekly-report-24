## 创建vue3工程
#### vue-cli
目前vue-cli已处于维护模式，官方推荐基于Vite创建项目
#### vite
vite构建很快。webpack是全准备再启动，vite是先启动再准备，需要什么准备什么。直接执行`npm create vue@latest`，并根据需要填写项目设置
项目命名的原则是：不能出现中文和特殊字符，最好用小写字母，数字和下划线。vite项目中，index.html是项目的入口文件，在项目的最外层。
## vue
一个vue文件里面要写三个标签<template>html结构</template>，<script>JS/TS（推荐）</script>，<style>样式</style>。
vue2是OptionsAPI（选项式、配置式），里面的数据、方法、计算属性等是分布在data、methods、computed中的，如果我们想要新增一些需求或者修改一些需求，就得分别修改它们，这是不利于我们维护和服用的
vue3是CompositionAPI，把数据、方法、属性等集合到函数中，让相关功能的代码更加有序地组织到一起。vue3可以写多个根标签，vue2不能
#### setup
setup是vue3的一个配置项，组件中用到的数据、方法、计算属性等都配置在setup中，其返回值可以是个对象，也可以是个渲染函数，函数的返回值可以渲染到页面上。注意，在setup中是不能用this的，this在setup中并没有维护，其值是undefined，vue3已经开始弱化this了。在setup中直接写变量的话，数据不是响应式的，也就是说如果后面改变了它并不会自动地反映到页面上
setup语法糖：为了代码简洁我们可以使用简化写法
```vue
<script>
  setup() {
    let a = 100
    return {a}
  }
</script>
<!-- 就相当于 -->
<script setup>
  let a = 100
</script>
```
## 响应式
#### ref
首先`import {ref} from 'vue'`，然后如果我们想把某些数据变为响应式，我们就要用ref把这个变量包起来`let name = ref('张三')`，这样这个变量就会变成一个RefImpl对象，我们需要对这个对象的.value进行操作，就可以让它成为动态的了。注意，模板里面用到的还是整个ref对象，所以模板里面不要传入.value。因此，对于这个元素来说，name本身不是响应式的，而name.value是响应式的
#### reactive
首先`import {reactive} from 'vue'`，ref里面写的是基本数据类型，而reactive可以写的是对象类型（数组也算对象类型）。用reactive把对象包起来就封装成了个Proxy对象（有它就算响应式的了）。只要用了reactive，不管数据嵌套了多深reactive都能把它变成响应式的
不过，reactive只能写对象类型，而ref不仅可以写基本数据类型，也可以写对象类型。我们上面用ref的时候需要用value访问，而ref如果包装一个对象类型，这个对象就会首先包装成一个Proxy然后再装入value里面。所以我们可以用ref来包装对象类型
reactive有一点局限性，当reactive重新分配一个新对象的时候，会失去响应式。比如我们已经有`reactive(a)`，此时我们再给a赋值一个对象b，那么它就会失去响应式；又比如说我们从后台接受过来一些对象，我们一般来讲是直接给对象赋值的，不会去对原本对象的属性进行修改。我们应该用Object.assign()去修改整个的reactive响应式对象。不过ref我们可以直接整个赋值，因为上面说到了，ref中的.value才是响应式的。然而，Object.assign()也只不过是把后面的参数每个数据相应地赋值给前面的参数，而并不是直接整个替换参数
#### 使用原则
如果需要一个基本类型的响应式数据，我们一定用ref；如果需要一个层级不深的响应式对象，用ref、reactive都可以；如果需要一个有较深层级的响应式对象，我们推荐使用reactive。
#### toRefs()和toRef()
当我们想要解构对象的时候，我们不能直接去解构reactive包装后的对象，因为我们直接解构出来的只是对象中的值而不是这个响应式的内容。所以我们可以用toRefs(object)来在解构的同时使其被ref包装，这样解构出来的也是个响应式的数据；toRef(object,'name')可以获取单个的ref包装
#### computed计算属性
首先想要让数据双向绑定要在标签里加`v-model="tagName"`（vue的知识）
我们先`import {computed} from 'vue'`，然后当我们想要在模板里面计算的时候，就可以把计算交给脚本去做。`let fullName = computed(()=>{return ...})`。computed只有当内部的数据发生变化的时候才会重新计算。计算属性会缓存，但是相同计算的方法是不会缓存的。计算属性是只读的，我们不能直接对计算属性进行修改
我们可以用`computed({get(){}, set(value){}})`来进行相应的computed操作。get()是回调函数，当需要读取属性的时候就会执行这个函数并返回相应的值，而set(value)则会监视属性，当属性值发生变化则会执行并更新相应的数据
#### watch
watch监视，可以监视ref、reactive定义的数据，函数返回一个值（getter函数，getter函数就是能返回一个值的函数），和一个包含上述内容的数组。当然也要首先就引入。在vue3里面，watch变成了一个函数，一般传入两个参数，要监视谁和满足条件后的回调函数。其中，回调函数可以接收newValue和oldValue两个数据（oldValue我们用得比较少，所以我们有时候可以省略掉）。watch函数是会返回一个停止函数的，我们可以在回调函数中设置条件来调用这个停止函数以停用这个监视（就像接收定时器的id那样，只不过在watch里面是调用停止函数）

- 监视ref定义的基本类型数据：直接写数据名，监视的就是ref数据value值的改变
- 监视ref定义的对象类型数据：直接写数据名，监视的是对象的地址值，如果我们要监视对象内部的数据，我们需要手动配置深度监视。此时我们需要向函数传入第三个配置参数{deep:true}，这样我们修改对象中的属性的时候也会触发这个watch函数。但是要注意的是，如果我们修改的对象里的属性，newValue和oldValue都是新值，因为它们还是原本的对象，地址是没变的（注意它监视的是对象的地址）；而如果我们修改整个ref定义的对象，它们就是它们原本的含义了
- 监视reactive定义的对象类型数据，默认开启深度监视
- 监视ref或reactive定义的对象类型数据中的某个属性：如果该属性不是对象类型，需要写成函数的形式；如果是对象类型，可以直接监视，也可以写成函数，但是我们建议写成函数。
```vue
watch(()=>{return person.name}, (newValue, oldValue)=>{
  console.log('perosn.name变化', newValue, oldValue)
})
  // 对于监视值来说，newValue和oldValue就不会发生一样的情况了，因为这个监视的不是地址
watch(person.car, (newValue, oldValue)=>{
  console.log('perosn.car变化', newValue, oldValue)
})
  //在这里，car是个对象，如果我们直接传入一个对象那就是监视它里面的数据，而且也不是深度监视的级别，那么对象数据发生改变的时候会触发函数，但是整个对象发生改变的时候就不会触发函数了
watch(()=> person.car, (newValue, oldValue)=>{
  console.log('perosn.car变化', newValue, oldValue)
},{deep:true})
  //所以我们是应该将对象也传入参数中的，而当我们想要同样监视内部的数据的时候，我们可以打开深度监视，这样不管是地址发生改变还是数据发生改变我们都能监视到
```

- 监视上述中的多个数据：如果我们想同时监视多个数据，我们可以把我们需要监视的数据写成一个数组传进第一个参数的位置。当然，如果我们想要监视对象的属性还是要写成函数传进去的。那么既然我们把多个数据作为数组传进去，数据发生改变的时候新旧值也是按照这个数组里面的顺序展示出来的
#### watchEffect
立即运行一个函数，同时响应式地追踪其依赖，并在依赖更改时重新执行该函数
 watch必须明确指出我们想要监视的东西，watchEffect会自己分析需要监视的变量`watchEffect(()=>{})`watchEffect会首先执行一次回调函数，我们直接在回调函数里面写条件，比如`if(temp.value>=60){...}`watchEffect就会自动监视temp.value
#### 标签的ref属性
不建议在项目里面用id，因为不同的组件可能会有同样的id，页面之间有先后渲染的顺序，后渲染的就会被冲突。我们可以引入ref，然后创建ref标记的变量`let title2 = ref()`，这样我们不用id，用ref属性就可以了`<h2 ref = "title2"></h2>`。当然，我们也可以给组件添加ref`<Person ref='person' />`来获得组件实例对象，只不过我们会无法直接访问到Person的内容，我们可以用defineExpose函数访问一些数据
#### props
父文件可以给子文件传输数据。首先引入defineProps，defineProps函数可以传入一个数组，格式是字符串，要和父文件发送的参数相匹配。它可以返回一个保存有传过来的参数的对象，我们也可以对参数进行类型限制，只要给defineProps函数之间加泛型就行`defineProps<{list:Persons}>()`
## 生命周期
组件的生命周期：创建，挂载，更新，销毁。在每个时刻都会调用特定的函数，比如创建的时候会调用created，挂载的时候要用mounted
钩子hook指的就是生命周期函数
#### vue2
创建分为两个时间点：创建前beforeCreate和创建完毕created
挂载分为两个时间点：挂载前beforeMount和挂载完毕mounted
更新分为两个时间点：更新前beforeUpdate和更新完毕Updated
销毁分为两个时间点：销毁前beforeDestroy和销毁完毕destroyed
#### vue3
在vue3中不再纠结创建前和创建完毕，而是使用setup一次直接创建
挂载要引入onBeforeMount，并传入回调函数参数，这个回调函数就是before的时候调用的函数；还有挂载完毕onMounted，当然也要引入
更新也是要引入，onBeforeUpdate和onUpdated
最后vue3中销毁不叫销毁了，叫卸载，引入并使用onBeforeUnmount和onUnmounted
而对于父页面和子页面嵌套的情况来说，一般是先解析子页面然后是父页面，因为子页面的解析标签在父页面的模板里，那么代码一般先按顺序执行所以就是先解析这个子页面标签，子页面的内容全部挂载完毕之后就是继续解析下面的代码内容。所以最后挂载的组件一般就是App.vue
#### hooks
hooks让一个功能的数据和方法贴在一起，发挥模块化的作用，与vue2中的mixin有点相似。hooks实际上就是某种js（ts）文件，但是其命名是use+内容
```typescript
//useDog.ts
import {ref, reactive} from 'vue'
import axios from 'axios'
//export default默认暴露后面要跟值
export default function() {
  let dogList = reactive([
    'address'
  ])

  async function getDog(){
    try {
      let result = await axios.get('address2')
      dogList.push(result.data.message)
    } catch(error) {
      alert(error)
    }
  }
  //向外部提供东西
  return {dogList, getDog}
}

// Person.vue
<script lang="ts" setup name="Person">
  import useDog from '@/hooks/useDog'

  const {dogList, getDog} = useDog()
  //这样以后就只需要维护useDog文件，其他文件中就可以用useDog了
</script>
```
## 路由
简单来讲，路由就是一种请求和逻辑的对应关系，就是一组key-value的对应关系，多个路由需要经过路由器的管理。对于vue中的路由，路径是至关重要的
#### SPA应用
single page web application单页网络应用，它只有一个html文件，但是可以实现“网页”的切换
#### 路由的简单应用
首先可以在建立项目的时候或者使用指令`npm i vue-router`，然后在src下建立router文件夹，然后在里面建立index.ts文件，用来创建一个路由器并暴露。在index.ts内，引入在vue-router内的createRouter和createWebHistory
```typescript
import { createRouter, createWebHistory } from 'vue-router'
//引入组件并在下面建立路由
import Home from '@/components/Home.vue'
import News from '@/components/News.vue'
import About from '@/components/About.vue'

const router = createRouter({
  //路由器的工作模式
  history: createWebHistory(),
  routes: [
    //路由规则
    {
      path: '/home',
      component: Home
    },
    {
      path: '/news',
      component: News
    },
    {
      path: '/about',
      component: About
    }
  ]
})

//暴露，分别暴露和完全暴露都是可行的，只要能让其他地方拿到组件就可以
export default router
```
在App.vue内，要在script中引入RouterView和RouterLink来实现路由链接的效果，RouterView可以让vue知道该在哪里展示组件，RouterLink用来链接组件
```typescript
<script setup lang="ts" name="App">
import { RouterView, RouterLink } from 'vue-router';
</script>

<template>
  <div class="app">
    <h2 class="title">Vue路由测试</h2>
    <!-- 导航区 -->
     <div class="navigate">
       //使用class=""会固定化，可以使用RouterLink自带的active-class属性
      <RouterLink to="/home" active-class="active">首页</RouterLink>
      <RouterLink to="/news" active-class="active">新闻</RouterLink>
      <RouterLink to="/about" active-class="active">关于</RouterLink>
     </div>
     <!-- 展示区，组件会在这里展示 -->
      <div class="main-content">
        <RouterView></RouterView>
      </div>
  </div>
</template>

```
注意：路由组件通常放在pages或者views文件夹，一般的组件放在components文件夹中。点击导航栏时，“消失”的路由组件默认是被卸载掉的，需要的时候我们再挂载。一般组件就是我们亲手写的（如<Header />），路由组件是靠路由的规则渲染出来的
#### 路由器的工作模式
history模式：vue2中是`mode:'history'`，vue3中是`history:createWebHistory()`，React中是`<BrowserRouter>`。history模式URL更加美观，不带有#，更接近传统的网站URL；但是后期项目上线需要服务端配合处理路径问题，否则就会404
hash模式：兼容性更好，不需要在服务器端处理路径；但是URL带有#不美观，而且在SEO优化方面相对较差（搜索搜不到，爬虫爬不到）
#### RouterLink中to的两种写法
第一种写法就是`<RouterLink to="/home"></RouterLink>`，另一种是对象写法`<RouterLink :to="{path:'/home'}"></RouterLink>`，对象写法不仅可以通过path跳转，还可以通过name跳转（在路由器中可以添加name属性，如`name:'homepage'`）
#### 嵌套路由
在页面较为简单的情况下会报错`No match found for location with path "/"`，说的是找不到“/”这个路径。子集路由不用加斜杠
```typescript
// index.ts
routes:[
  {
    name: 'xinwen',
    path: 'news'
    components: News,
    children: [
      {
        path: 'detail',
        component: Detail
        //这样就会给News组件添加Detail子组件
      }
    ]
  }
]

//News.vue
<template>
  <div class="news">
    <!-- 导航区 -->
    <ul>
      <li v-for="news in newsList"><RouterLink :to="{path:'/news/detail'}">{{ news.title }}</RouterLink></li>
    </ul>
    <!-- 展示区 -->
     <div class="news-content">
      <RouterView></RouterView>
     </div>
  </div>
</template>
```
#### 参数
query参数可以直接在路由后面加问号和参数，也可以在对象写法中添加query属性
```typescript
<RouterLink :to="{
          path:'/news/detail',
          query:{
            id:news.id,
            title:news.title,
            content:news.content
          }
        }"
```
param参数可以直接在路由后面加斜杠填参数，但是如果这样能够路由规则是找不到这个“路径”的，所以我们要提前在路由里面加好参数的占位，然后就在组件里引入useRoute并使用param参数就能拿了
```typescript
//index.ts
children:[
  {
    name:
  }
]
```
而在RouterLink中，如果我们使用了param命名路由，我们就不能用path，只能用name。而在传参的时候我们可以在参数后面加问号表示它可能为空
#### 路由规则的props配置
在路由规则中，component的作用是呈现一个<component />，如果我们给路由规则添加一个`props:true`，就相当于呈现一个<component id=?? name=?? content=??/>，就是说把路由收到的所有params参数一块传给路由组件，而在网页组件中直接defineProps接收参数就可以了
如果我们想要自己决定props传入的参数，我们可以使用props函数写法
```typescript
//路由规则index.ts
children:[
  {
    name:'xiang',
    path:'detail/:id/:title/:content',
    component:Detail

    props(route){
  return route.query
    }
  }
]

//Detail.vue
<script setup lang="ts" name="Detail">
  defineProps(['id', 'title', 'content'])
</script>
```
还有一种对象写法，可以给props填写一个对象，但是这样就是写死了，不是响应式的
#### replace
浏览器操作历史记录有push和replace，push就是把网页压入一个栈，然后通过指向网址的指针来访问网页，如果前一页就是把指针往前调等，默认是push。而replace就是直接替换掉整个浏览记录，如果给RouterLink添加replace就会让它变成replace模式，如果我们想让网页访问到某一个部分或者某一个组件就不能回退，我们可以让它成为replace模式
#### 编程式路由导航
前面用到的导航都是用RouterLink，而在网页中RouterLink的表现就是HTML中的a标签。然而，有时候我们需要触发一些事件就实行跳转，我们不能直接用在script中用RouterLink（template才能用），我们可以引入`useRouter`，然后`const router = useRouter()`来获取路由器，如果我们想实行跳转，我们可以router.push('/news')来跳转到路径为".../news"的页面
#### 重定向
在路由组件中，我们写的路由规则后面加一个`{path:'/',redirect:'/home'}`，就是说我们把默认路径重定向（redirect）给"/home"
## Pinia
集中式状态（数据）管理，我们要把那些可以共享的数据交给集中式状态管理，而组件自己的内部数据还是由组件自己保管。首先`npm i pinia`，`import {createPinia} from 'pinia'`，然后在App创建完之后创建pinia`const pinia = createPinia()`，之后安装pinia`app.use(pinia)`。这样pinia环境就搭建完毕了
建立store文件夹，在内部创建各种ts文件来存放对应组件的共享数据。引入defineStore，然后用hooks命名方法来定义store
```typescript
import {defineStore} from 'pinia'

export const useCountStore = defineStore('count', {
  //真正存储数据的地方
  state(){
    return {
      sun:6
    }
  }
})
```
在对应的文件中引入这个store文件的useCountStore，然后`const countStore = useCountStore()`，获取暴露出来的数据，在这里如果我们想要拿到state里的数据，可以用`countStore.sum`或者`countStore.$state.sum`
#### pinia中的数据修改
pinia中可以直接对数据进行修改，如`countStore.sum += 1`；当数据很多的时候我们可以用`countStore.$patch({sum:118, school:'schoolname'})`；还可以用actions，在store的ts文件中添加actions属性，在里面放置方法，用于响应组件中的动作，action维护了this，可以直接拿到当前的对象
如果我们想解构从store拿过来的数据，我们如果直接用toRefs()的话，会把整个对象中除了数据以外的东西都包装成ref，比如方法等。因此我们需要另一种东西，比如storeToRefs，它只关注store中的数据
#### getter
如果我们对store中的数据不满意，我们可以写getters并在里面加上方法来处理state里面的数据
#### $subscribe
在组件中引入store文件后，我们可以使用store的$subscribe方法，来表示store里面的数据发生了变化，mutate表示本次修改的信息，state就是真正的数据
```typescript
talkStore.$subscribe((mutate,state)=>{
  localStorage.setItem('talkList', JSON.stringify(state.talkList))
})
```
#### store的组合式写法
上面的store都是选项式写法，如果要用组合式就要在store里面写state和action，那么state就相当于数据，action就要写一个函数来表现成action应该要做的事情，最后return回去就可以了
## 组件通信
组件通信就是组件之间互相传递数据，组件之间的关系可能是父子，可能是兄弟，可能是祖孙，所以组件通信是必须牢牢掌握的
#### props
可以实现父传子也可以实现子传父，在父文件中给实例化的组件直接传参`<Child :car="car" />`，然后在子文件中`defineProps(['car'])`；子文件传给父文件要在父文件中定义get函数`function getValue(value:string) {}`，然后再把get函数传给子文件`<Child :sendValue="getValue" />`，子文件中再`defineProps(['sendToy'])`。子传父实际上还是父亲把回调函数传给子文件，然后子文件调用函数再把数据给父亲
最好不要用props一层一层给孙代甚至更深层次的文件
#### 自定义事件
在模板对脚本传参的时候，有一个$event占位符，在函数中可以用key:Event接收。自定义事件可以用来子传父，在父文件中定义自定义事件`<Child @customEvent="function01">`，在子文件中要定义这个触发`const emit = defineEmit(['customEvent'])`，这样，只要代码中触发了`emit('customEvent')`，就代表这个父文件要调用function01函数。当然函数也可以进行传参，如`function function01(value: number){}`，在子文件中触发的时候进行传参`"emit('customEvent', 123456)"`
#### mitt
可以实现任意组件通信。首先`npm i mitt`，是一种工具（tools/utils），引用，然后`const emitter = mitt()``export default emitter`，这个emitter可以绑定事件、触发事件，然后在其他的文件中`import emitter from '@/utils/emitter'`
`emitter.all`是拿到所有绑定的事件，`emitter.emit('event1')`触发某事件，`emitter.off('event1')`解绑某事件，`emitter.on('event1, ()=>{}')`绑定某事件，`emitter.all.clear()`可以清空所有绑定的事件
我们可以在文件1中`emitter.on('event', ()=>{})`，然后在文件2中`emitter.emit('event')`，其实还是一种自定义事件
#### v-model
实际开发很少亲自写组件并加model，但是UI组件库大量使用v-model进行通信，v-model实行双向绑定，用在html标签上。`<input type="text" v-model="username">`相当于`<input type="text" :value="username" @input="username = $event.target.value">`，就是动态value值配合input事件
如果v-model直接用在组件上`<MyInput v-model="username"/>`，就相当于双向绑定可以`<MyInput :modelValue="username" @update:modelValue="username = $event"/>`，modelValue是动态值，而update:modelValue是个事件名，我们就要在子文件中用`defineProps(['modelValue'])`接收这些参数，这样父传子就实现了。
然后，我们还需要用`const emit = defineEmits(['update:modelValue'])`来接收这个方法，随后在模板的标签里面加上这个事件`@input="emit('update:modelValue', $event.target.value)"`，实现子传父
如果我们想修改modelValue，可以`<MyInput v-model:myname="username"/>`，然后在MyInput组件中也修改对应的modelValue和update:modelValue。这让我们可以添加更多的v-model
#### $attrs
用于实现在某组件中的父组件向这个组件的子组件通信（祖辈->孙辈）。当我们通过动态值从父给子传递数据的时候`<Child :a="a" :b="b" :c="c" :d="d"/>`，如果我们在子文件中用props接收a、b，props就会有它们，而没接收的就会到attrs里面。那么我们就可以通过在子组件中`<GrandChild v-bind="$attrs"/>`来把父组件传到子组件中没用到的数据传给孙组件，孙组件中defineProps就能接收了
如果要实现孙传祖，我们还是可以写自定义事件，把函数从祖辈传到孙辈，然后孙辈中调用祖辈的函数就能实现孙传祖
#### $refs和$parent
如果我们想通过组件标签的ref标记拿到子文件ref的数据是不行的，因为有数据的保护：`<Child ref="child"/>`，子文件中就要把数据交给外部`defineExpose({toy, book})`。如果我们想拿到某文件的所有子文件ref，就可以用$refs`<button @click="getAllChild($refs)"></button>`，在这里就可以获取组件的子组件ref（是个object）
对于子组件来说，可以给子组件的函数参数加$parent，表示拿到父亲组件（当然父亲组件也要defineExpose交出来数据）
#### _provide-inject
专门实现祖孙数据传递。$attrs虽然也可以实现但是还是借助了子组件来实现数据传递。在祖辈组件中引入provide，然后直接`provide('car', car)`，可以向后代提供数据。在其子代文件中引入inject，然后`let car = inject('car', {brand:'carbrand', price:0})`，就可以在子代中使用。在接受的时候可以传入默认值，上面那个带有brand和price的对象就是一种默认值，这样可以在子代使用这个car的时候让它明白这个对象是带有brand和price属性的
要实现孙传祖当然还是从祖辈传函数进去，然后孙代调用函数
#### pinia
pinia也可以实现组件间通信
#### 插槽slot
默认插槽：当我们把本来是单标签的组件打成双标签的时候，就意味着双标签包着的东西就是要传到组件里的，然而如果只是传过去的话程序并不知道把内容放在那里，所以就可以用`<slot>默认内容</slot>`来进行占位，如果有内容就把slot替换，如果没有内容就是slot夹着的默认内容。可以用slotE
具名插槽：带名字的插槽，`<slot name="s1">默认内容</slot>`，它只能放在组件标签或者是template标签上，直接放到组件标签上我们不太能把内容分别插入到slot里面，我们有想要定位插入的内容就要用template包起来然后带上`v-slot:名字`。其实默认插槽也有default的名字
作用域插槽：有时候我们想要访问的数据和我们需要插入的内容不在一个组件里，所以我们可以通过给slot传参数`<slot :games="games"></slot>`并在slot的使用者的部分传入参数`<template v-slot="params"></template>`
#### 总结
父传子有：props，v-model，$refs，默认插槽和具名插槽
子传父有：props，自定义事件，v-model，$parent，作用域插槽
祖孙互传有：$attrs，provide-inject
兄弟之间、任意组件之间有：mitt，pinia
## 其他的api
#### shallowRef和shallowReactive
shallow浅层次的意思，它们只处理第一层的响应式数据，比如我们有`let sum = shallowRef(0)`，我们可以直接`sum.value += 1`，这只有一层响应式数据；如果我们有`let person = shallowRef({name:'张三', age:18})`，我们只能`person.value = {name:'李四', age:20}`，不能访问`person.value.name`或`person.value.age`。有时候我们只关注数据整体的改变，这时候就可以用这两个，效率高。shallowReactive就是只关注对象的顶层响应式属性，里面嵌套的对象就不管了，而且里面嵌套的对象的属性不是响应式的
#### readonly和shallowReadonly
readonly只读，里面只能传响应式对象`let sum1 = ref(0)``let sum2 = readonly(sum1)`，这样sum2就和sum1单向绑定了，sum1的改变sum2也会改变，而sum2是只读的，不可以改变的
shallowReadonly就是只对浅层次进行只读的绑定，如果有深层次的对象嵌套等，那些数据就可以被改变
#### toRaw和markRaw
toRaw可以获取响应式对象的原始对象，用了toRaw之后返回的对象就不再是响应式的了，比如如果我们要把数据传递给外部（vue外部或者是外部系统）的时候，我们就可以用这个确保其获得的是正常的数据
markRaw可以标记一个对象，让它永远不会被响应式组件包装，有些数据我们不想让它被响应式组件包装并被改变，所以我们可以用markRaw包装这些数据，这样就算有响应式组件包装它也是无效的
#### customRef
自定义Ref，并对其依赖项进行跟踪和更新触发进行逻辑控制，可以实现防抖，要注意get和set的情况，和对track和trigger的理解
```typescript
//首先引入
<script setup lang="ts" name="App">
  import {customRef} from 'vue'
  //track跟踪，trigger触发
  let initValue = 'initvalue'
  let msg = customRef((track, trigger)=>{
    //msg被读取的时候调用get函数
    get(){
    track()//告诉vue数据msg很重要，需要对msg进行跟踪，一旦msg变化就更新
      return initValue
    },
    //msg被修改的时候调用set函数
    set(value){
      initValue = value
      trigger()//通知vue数据msg变化了
    }
  })
</script>
```
#### teleport
teleport传送，可以让其触发的时候把内部包含的内容送到某个标签内，`<teleport to='body'></teleport>`，只是把结构给到对应的地方，脚本逻辑还是不变的
#### Suspense
等待异步组件的时候可以渲染一些额外的内容，让用户有更好的体验。异步比较典型的就是发送网络请求。如果我们的子组件里面包含异步任务，并且异步任务所提供的数据我们还要用，而我们还要在网速慢得时候想要展示一些东西（比如加载中等），就可以用到suspense
```typescript
//脚本中引入Suspense后
<template>
  <Suspense>
  //default是异步任务完成后出现
    <template v-slot:default>
      <Child/>
    </template>
  //fallback是异步任务没有完成就出现
    <template v-slot:fallback>
      <Child/>
    </template>
  </Suspense>
</template>
```
#### 全局API转移到应用对象

- app.component可以在app中挂载组件，然后子组件里面就可以直接用这个挂载了的组件
- app.comfig是全局配置项
- app.directive注册全局指令，`app.directive('directivename', ()=>{})`，挂载后在所有组件里面就可以用v-directivename
- app.mount挂载应用
- app.unmount卸载应用
- app.use安装插件
#### 其他
可以看看官网非兼容性改变，有一些vue3中改了的东西
