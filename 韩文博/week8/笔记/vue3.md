## 创建vue3工程
#### vue-cli
目前vue-cli已处于维护模式，官方推荐基于Vite创建项目
#### vite
vite构建很快。webpack是全准备再启动，vite是先启动再准备，需要什么准备什么
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

