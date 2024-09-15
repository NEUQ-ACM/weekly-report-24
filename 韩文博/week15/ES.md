ES全称EcmaScript，是脚本语言的规范，JS是ES的一种实现，所以ES的新特性可以应用到JS上

#### ECMA
European Computer Manufaturers Association，欧洲计算机制造商协会，评估、开发和认可电信和计算机标准。ES是通过ECMA-262标准的脚本程序设计语言。TC39（Technical Committee）负责推进ECMAScript发展，其会员公司包括苹果、谷歌、微软、因特尔等（浏览器厂商）

## ES6
#### let
用来声明可变变量，用let声明的变量不能重复，属于块级作用域。不存在变量提升，什么时候声明什么时候才能用。不影响作用域链

#### const
const用来声明常量，使用的时候必须赋初始值，一般常量整个要大写（比较通用），常量的值不会被修改，作用在块级作用域。对于常量数组和常量对象其中的元素的修改不算对整个常量的更改，所以不会报错，因为这个常量指向的数组或对象的地址没变；如果直接更改整个数据，那就会出错

#### 解构赋值
```javascript
// 数组解构
const arr = [123, 345, 456, 567]
let [num1, num2, num3, num4] = arr

// 对象解构
const obj = {
  name: '张三',
  age: 18,
  getName: function() {
    return this.name
  }
} 

let {name, age, getName} = obj
```

#### 模板字符串
用````声明字符串，内容中可以直接换行，可以进行变量拼接

#### 简化对象写法
ES6允许在打括号里面直接写入变量和函数作为对象的属性和方法，这样的书写更加简洁

```javascript
let name = '张三'
let change = function(){
  console.log('更改')
}

const school = {
  name,
  change
}

// 等价于const school = {
//   			name: name,
//   			change: change
// }
```

在简化对象写法中，如果要写函数可以不加`=function`，如`const school = { improve(){...} }`，而不是`improve = function(){...}`

#### 箭头函数
可以省略function。箭头函数有一个非常重要的特性，其this是静态的，始终指向函数声明时所在作用域下的this的值，就算想要用call函数改变this也不会变。但是箭头函数不能作为构造函数实例化对象，也不能用arguments变量。如果只有一个参数可以省略参数的小括号，如果代码体只有一条语句时可以省略代码体的大括号；省略大括号时如果只是return语句，那么return也要省略。`let pow = n => n*n`

我们可以在箭头函数外面保存要用到的this的值然后在函数中使用。主要就是简写，适合不与this有关的回调，如定时器、数组方法等；不适合事件回调，对象方法等（不是不能，有时候还是能用的）

#### 函数参数默认值
函数形参可以赋初始值，这样可以避免没传参数导致的错误；也可以与解构赋值结合来，比如没有传要解构的值，可以用默认值来获取解构后的值

#### rest
rest可以获取函数的实参，代替arguments。`function date(...args){...}`，这个args接收后是个数组，必须放在函数参数的最后，就是说剩下的参数等

#### 扩展运算符
`...`就是扩展运算符，能将数组转换为逗号分隔的参数序列，就是可以用来展开获取到的参数。rest放在形参的位置，扩展运算符在调用时放在括号里。

扩展运算符可以用来合并数组，如`const arr = [...arr1, ...arr2]`；也可以用来克隆数组`const arr1 = [1, 2, 3]; const arr2 = [...arr1]`，也可以将伪数组转换为真正的数组，比如我们通过DOM操作获取对象内容后，可以用`...`展开对象内容并交给数组

#### symbol
原始数据类型，表示独一无二的值，其值唯一，用来解决命名冲突的问题，它不能与其他数据进行运算（如加减乘除，字符串拼接，比较等），symbol定义的对象属性不能用for in进行遍历，但是可以用`Reflect.ownKeys`来获取所有键名。就算两个symbol的内容相同，它们也不能算相等

可以使用Symbol.for来创建Symbol数据，只不过这是个函数对象，可以根据传入的字符串生成唯一的标识值，这样如果我们再通过Symbol.for传入相同的字符串，这两个Symbol会是相同的，如`let s1 = Symbol.for('123')`和`let s2 = Symbol.for('123')`，s1和s2是相等的

USONB可以简记基本数据类型：

+ u：undefined
+ S：string symbol
+ O：object
+ n：null number
+ b：boolean

Symbol可以用来向对象安全地添加方法和属性

```javascript
let game = {...}
let methods = {
  up: Symbol(),
  down: Symbol()
}

game[methods.up] = function(){
  ...
}
game[methods.down] = function(){
  ...
}
// 这样不管game里面是否有up和down方法，都可以添加独一无二的Symbol方法，不会出错
```

由于Symbol是动态的，是个表达式，在对象内部直接添加的时候需要前后加中括号`[Symbol('say')]:...`

除了定义自己使用的Symbol以外，ES6还提供了11个内置的Symbol值，指向语言内部使用的方法，其实指的就是Symbol对象的属性，可以改变对象在特定情况下获得的结果

| Symbol.hasInstance        | 当其他对象使用instanceof运算符判断是否为该对象的实例时，会调用这个方法                                  |
| ------------------------- | ------------------------------------------------------------------------------------------------------- |
| Symbol.isConcatSpreadable | 对象的Symbol.isConcatSpreadable实行等于一个布尔值，表示该对象用于Array.prototype.concat()时是否可以展开 |
| Symbol.unscopables        | 该对象制定了使用with关键字时，哪些属性会被with环境排除                                                  |
| Symbol.match              | 当执行str.match(myObject)时，如果有该属性存在，会调用它，返回该方法的返回值                             |
| Symbol.replace            | 当该对象被str.replace(myObject)方法调用时，会返回该方法的返回值                                         |
| Symbol.search             | 当该对象被str.search(myObject)方法调用时，会返回该方法的返回值                                          |
| Symbol.species            | 创建衍生对象时，会使用该属性                                                                            |
| Symbol.split              | 当该对象被str.split(myObject)方法调用时，会返回该方法的返回值                                           |
| Symbol.iterator           | 对象进行for...of循环时，会调用Symbol.iterator方法，返回该对象的默认遍历器                               |
| Symbol.toPrimitive        | 该对象被转为原始类型的值时会调用这个方法，返回该对象对应的原始类型值                                    |
| Symbol.toStringTag        | 在该对象上面调用toString方法时返回该方法的返回值                                                        |
| Symbol.species            | 创建衍生对象时会使用该属性                                                                              |


这些都是Symbol里面的属性，它们又作为一个整体当作对象的属性来设置，可以控制对象在特定情况下的表现，说白了就是扩展对象功能

#### 迭代器
迭代器是一种接口，为各种不同的数据结构提供统一的访问机制。任何数据结构只要部署Iterator接口就可以完成遍历操作，Iterator就是对象里面的一个属性Symbol.iterator。ES6创造了一种新的遍历命令for...of循环，Iterator接口主要供for...of消费。原生具备Iterator接口的数据有：Array，Arguments，Set，Map，String，TypedArray，NodeList。for...in保存的是键名（如数组下标），for...of保存的是键值

Iterator会创建一个指针对象，指向当前数据结构的起始位置-第一次调用对象的next方法，指针自动指向数据结构的第一个成员-接下来不断调用next直到指针指向最后一个成员-在此期间每调用一次next方法都返回一个包含value和done属性的对象，done就是有没有遍历到最后

当我们想要自定义遍历数据的时候就要用到迭代器。forEach可以达到相同的效果，但是forEach是直接拿数据，不符合面向对象的思想；而Iterator可以设置在对象内部，属于的是对象本身，因此较为安全

```javascript
let index = 0
let _this = this

return {
  next: function(){
    if(index < _this.stus.length) {
      const result = { value: _this.stus[i], done: false }
      index++
      return result
    } else {
      return { value: undefined, done: true }
    }
  }
}
```

#### 生成器
生成器函数是ES6提供的一种异步编程解决方案，就是一个特殊的函数。原本异步编程都是纯回调函数一层套一层。生成器函数需要function和函数名之间有一个星号`function * gen(){}`，可以靠左可以靠右。其函数体由yield分割，可以理解为由从上往下的被yield分隔开的数组，然后要靠next来访问。next函数可以传入实参，其就是yield语句的返回结果

```javascript
function one(){
  setTimeout(()=>{
    console.log(111)
    iterator.next()
  }, 1000)
function two(){
  setTimeout(()=>{
    console.log(222)
    iterator.next()
  }, 2000)
function three(){
  setTimeout(()=>{
    console.log(333)
    iterator.next()
  }, 3000)
}

function * gen(){
  yield one()
  yield two()
  yield three()
}

let iterator = gen()
iterator.next()
```

#### Promise
是ES6引入的异步编程的新解决方案，从语法上来说Promise就是一个构造函数，用来封装异步操作并获取其成功或失败的结果。在Promise的回调函数内部写异步函数，在得到数据后可以调用回调函数的参数resolve和reject两个函数参数，它们分别会把Promise的状态设置为resolved和rejected，并执行它们所绑定的成功回调函数或失败回调函数。需要调用Promise对象的then方法来绑定resolve和reject相应的函数

then的返回对象也是一个Promise，其返回状态由回调函数的执行结果决定。如果回调函数中返回的结果是非Promise类型的属性，则返回值为成功对象的值；如果返回的是成功Promise对象，则返回值为resolve的值；如果返回的是失败Promise对象，则返回值为报错值；如果是抛出错误，则返回值为抛出的错误值

因为then返回的是Primise，我们可以对then方法再调用then来创造Promise链，在Promise链中写异步函数，这样就可以防止回调地狱

Promise对象由catch函数，传入reason原因函数参数，如果出错会直接跳过剩下的then直接到catch函数体

#### set
类似于数组，但是成员的值都是唯一的，支持iterator，可以使用扩展运算符和for...of等进行遍历。具有以下属性和方法

+ size：返回集合的元素个数
+ add：添加一个新元素，返回当前集合
+ delete：删除元素，返回boolean值
+ has：检测集合中是否包含某个元素，返回boolean值

#### map
类似于对象，是键值对的集合，但是键不再只是字符串，各种类型的值（包括对象）都可以当作键。也支持iterator，可以使用扩展运算符和for...of进行遍历。有以下属性和方法：

+ size：返回map的元素个数
+ set：增加一个新元素，返回当前map
+ get：返回键名对象的键值
+ has：检测map中是否包含某个元素，返回boolean值
+ clear：清空集合，返回undefined

#### class
ES6也提供了更传统语言的写法，引入了class类概念作为对象的模板。基板上ES6的class可以看作一个语法糖，它的绝大部分功能在ES5中都存在，而class写法只是让对象原型的写法更清晰，更面向对象

正常写法下实例对象是没有构造对象上的属性的，所以要在构造对象的protorype上加属性和方法才能让实例对象也能使用。直接在构造对象上写的函数就是它的静态成员，class语法糖下把属性或方法前面加static就表明它们是类的静态成员，不会被实例化

```javascript
function Phone(brand, price) {  
  this.brand = brand
  this.price = price
}

Phone.prototype.call = fucntion(){
  console.log("打电话")
}

function SmartPhone(brand, price, color, size){
  // call改变this值
  Phone.call(this, brand, price)
  this.color = color
  this.size = size
}
// 设置子集构造函数的原型
SmartPhone.prototype = new Phone
// 校正构造函数
SmartPhone.prototype.constructor = smartPhone
// 设置子类的方法
SmartPhone.prototype.photo = function(){
  console.log("拍照")
}
```

```javascript
class Phone {
  constructor(brand, price) {
    this.brand = brand
    this.price = price
  }

  call(){
    console.log("打电话")
  }
}

class SmartPhone extends Phone {
  constructor(brand, price, color, size) {
    // super就是父类的构造器方法
    super(brand, price)
    this.color = color
    this.size = size
  }

  photo(){
    console.log("拍照")
  }
}

// 这样只要正常实例化就能用
const xiaomi = new SmartPhone('xiaomi', '799', 'black', '4.7inch')
```

子类也可以对父类的方法进行重写，直接在子类中写重名的方法即可，但是子类是不能用调用父类的同名方法的，如用super调用。js中类不写构造函数是合法的

get函数可以在获取数据时调用，set函数会在设置数据时调用

```javascript
class Phone{
  get price(){
    console.log("价格被读取了")
    return '$123'
  }
// 可以在setter里面写判断，比如符合某种规定才能改变数据等
  set price(newVal) {
    console.log("价格被修改了")
  }
}
```

#### 数值扩展
+ Number.EPSILON是JS表示的最小精度，其值接近2.2204460492503130808472633361816E-16。如果两个数的差值小于EPSILON就说明这两个数相等，主要用在浮点数运算上，比如最经典的问题0.1+0.2>0.3的情况，它们在运算上因为精度会出现问题，所以我们用差值小于EPSILON来认定相等
+ 二进制0b开头（binary），八进制0o开头（octonary），十六进制0x开头（hexadecimal）
+ Number.isFinite检测一个数是否为有限数，无线数如100/0、Infinity等输出false
+ Number.isNaN检测数值是否为NaN
+ Number.parseInt和Number.parseFloat字符串转整数和浮点数，会省略数字后面的字符
+ Number.isInteger判断一个数是否为整数
+ Math.trunc将数字的小数部分抹掉（不是四舍五入）
+ Math.sign判断一个数是正数、负数还是0，分别返回1，-1，0

#### 对象方法扩展
+ Object.is判断两个值是否完全相等，可以判断两个NaN是否相同（三等不能判断两个NaN是否相同）
+ Object.assign合并对象，传入两个参数，后面的参数会覆盖前面的参数，前面的参数是模板。用来合并配置比较合适
+ Object.setPrototypeOf设置原型对象，Object.getPrototypeOf获取原型对象

#### 模块化
模块化是指将一个大程序文件拆分成许多小文件，然后将小文件组合起来。可以防止命名冲突，小文件之间互不干扰；可以复用部分代码，也具有高维护性。ES6以前有一些模块化规范，如CommonJS（NodeJS，Browserify），AMD（requireJS），CMD（seaJS）等

模块功能主要由两个命令构成：export和import。分别用来规定模块的对外接口和输入其他模块提供的功能。我们需要在想要暴露出来的数据或方法前面加export，如`export let data = '123'`；在使用有import的文件时，要给script加`type="module"`属性 ，在文件内或script标签内`import * as file1 from './js/file1.js'`，这样js就把文件中所有export出的数据存到变量file1里面了

分别给属性和方法添加export修饰叫分别暴露，如果在文件添加`export {school, work}`，就会统一暴露文件中的school和work属性。引入是相同的方式。可以在文件内添加`export default{}`默认暴露数据，内部可以写数据，一般写成对象形式，在使用的时候需要`file.default.function()`，要访问default

导入支持解构赋值，如文件内有school和teach属性，就可以`import {school, teach} from ...`。默认暴露的输出结果是个对象，在default里面存的是数据，所以我们需要进行别名设置，不能直接使用default，使用别名才可以

模块导入还有一种简便的形式，只能用在默认暴露上，直接`import file from "./js/file.js"`，拿到的就是默认暴露出来的对象

如果写成分开的js文件，就应该加`type="module"`属性和src属性

项目中一般不是直接script导入js文件，可以用Babel将ES6转化成ES5再打包形成单独文件，然后页面引入即可。npm安装babel-cli（babel命令行工具）、babel-preset-env（预设包，可将ES特性转换成ES5语法）、browserify（打包工具，实际项目一般使用webpack）

在ES6下使用npm安装的包，可以用import导入安装过的要使用的包

## ES7
引入了Array.prototype.includes方法来检测数组中是否包含某个元素并返回布尔类型值

指数运算符`**`，功能与Math.pow结果相同

## ES8
#### async和await
async和await结合可以简化异步代码，让它像同步代码一样。async函数的返回值为promise对象，其结果由async函数执行的返回值决定。await必须卸载async函数中，其右侧表达式一般为promise对象，若promise成功则返回promise成功的值，否则会抛出异常，需要try...catch处理。我们只需要在需要用到的异步函数内将数据绑定给Promise，然后再给await使用这个异步函数就可以大大简化异步处理的过程

#### 对象方法扩展
+ Object.values()方法返回一个给定对象的所有可枚举属性值的数组
+ Object.entries()方法返回一个给定对象自身可遍历属性[key, value]的数组，方便我们创建map
+ Object.getOwnPropertyDescriptors()方法返回对象所有自身属性的描述对象，如writable、configurable、enumerable等

## ES9
#### 对象展开
Rest参数与spread扩展运算符在ES6就已经引入，但是仅针对数组。ES9开始为对象提供像数组一样的rest参数和扩展运算符。可以用于对象的合并等

#### 正则扩展
+ 命名捕获分组

对分组匹配的结果进行命名，方便对数据进行处理

```javascript
let str = '<a href="http://www.bilibili.com">B站</a>'
// 正则匹配的时候在前面加上?<命名>
const reg = /<a href="?<url>.*">(?<name>.*)<\/a>/
```

+ 反向断言

可以根据前面的内容匹配后面的内容

```javascript
// 正向断言，从后面的内容匹配前面的内容
let str = "1235451一二三810四四四"
// /d表示数字，+表示匹配多次（多个）
const reg1 = /\d+(?=四)/
// 反向断言
const reg2 = /(?<=三)\d+/
```

+ dotAll模式

`.`是元字符，表示除换行符以外的任意单个字符

## ES10
+ Object.fromEntries()用来创建一个对象，接收二维数组或map；在ES8中的Object.entries可以转化为数组

#### 字符串扩展
+ trimStart：trim用来清除字符串两端的空白字符，trimStart可以指定清除字符串左端的空白字符
+ trimEnd：指定清除字符串右端的空白字符

#### 数组扩展
+ flat：将多维数组转化为低维数组，可以接受数字参数表示深度，如传入1则只展开一层，到2维；传入2展开到3维。默认参数1
+ flatMap：将map维度降低

#### Symbol扩展
+ Symbol.prototype.description：用来获取Symbol的字符串描述

## ES11
+ 可选链操作符：`?.`，当我们应对对象类型的参数时，其深度较深

```javascript
main({
  db: {
    host:'192.168.1.100'
    username: 'root'
  },
cache: {
  host: '192.168.101'
  username: 'admin'
  }
})

// 可以用可选链操作符判断某层是否有某属性，否则需要挨个判断
const dbHost = config?.db?.host
```

+ 动态import：静态import就是在前面直接引入文件，动态import我们可以在代码内部使用import函数，它返回一个Promise对象，其成功值就是文件export出的属性和方法
+ globalThis：不管怎么样它指向的都是全局对象（浏览器下是Window对象）

#### 私有属性
在面向对象是个非常重要的属性，对属性进行封装，不能被其他文件进行直接访问。公有属性直接写属性即可，私有属性要在前面加井号如`#age`、`#weight`等，在类内部访问私有属性的时候也要写全，如`this.#age = age`

#### Promise扩展
+ Promise.allSettled：接收一个Promise数组，返回一个Promise对象，其结果永远都是成功，成功的值是每个Promise的状态和结果

#### 字符串扩展
+ String.prototype.matchAll：用来得到正则批量匹配的结果，爬虫类很有效

#### 类型扩展
+ BigInt：大整形转换，不能用浮点数进行转换和与正常数值运算，可以用于大数值运算

