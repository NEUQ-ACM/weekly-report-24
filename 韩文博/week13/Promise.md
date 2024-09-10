## Promise
Promise是ES6引入的进行异步编程的新的解决方案，从语法上来说它就是个构造函数，可以进行对象的实例化，可以封装异步的任务并且可以获取其成功或是失败的结果值，然后对结果进行处理
异步操作包括fs文件操作（node.js），数据库操作，AJAX网络请求，定时器（setTimeout）等。在Promise出现以前使用的是回调函数来进行异步操作和进行结果处理
#### Promise的好处
支持链式调用，可以解决回调地狱（异步任务的回调函数嵌套），这样阅读十分困难，而且不便于错误处理
使用回调函数进行异步任务必须在启动异步任务之前进行指定，而Promise可以让在异步任务启动以后进行回调函数的绑定，甚至绑定多个回调函数
```javascript
btn.addEventListener('click', function(){
  //resolve 解决，reject 拒绝，它们都是函数类型，也就是说如果异步任务成功就执行resolve，反之就reject
  const p = new Promise((resolve, reject) =>{
    if(/*成功*/) {
      resolve()//执行后将promise对象状态设置为成功
    } else {
      reject()//执行后将promise对象状态设置为失败
    }
  })
  p.then(()=>{}/*resolve*/, ()=>{}/*reject*/)
})

  
```
最基本的写法无非就是创建一个promise对象，传入resolve和reject，回调函数里面放异步任务代码，然后成功的时候调resolve，失败的时候调reject，再通过then给它们绑定事件
另一种方法是写一个函数，而这个函数返回一个Promise对象，用的时候只需要调用这个函数的then方法并传入resolve和reject即可
#### util.promisify()
可以传入一个错误优先的函数，可以将普通回调函数风格的函数转变成promise风格的函数
#### promise的状态改变
promise实例对象具有一个状态属性，叫做PromiseState，它包括3种可能的值，pending未决定的（初始化后），resolved/fullfilled成功，rejected失败；它会进行两种变化，pending-resolved和pending-rejected，它不会从失败变成成功或是从成功变成失败，一个promise对象只会变化一次。Promise实例对象的值PromiseResult，它存储异步任务成功或者失败的结果，resolve和reject函数可以修改PromiseResult（赋值），都只会有一个结果数据
#### promise的基本流程
通过new Promise()创建一个promise对象，在其内部封装异步操作，如果成功则调用resolve函数，然后将promise状态改为resolved，之后在调用then方法时会调用第一个参数（resolve对应的函数）代码，并返回新的promise对象；如果异步任务失败则调用reject函数并将状态改为rejected，之后调用then方法中的第二个参数并返回新的promise对象
#### API
promise具有构造函数，可以通过new来实例化一个对象，实例化的时候需要接受一个函数类型的参数executor，可以使用箭头函数等，其需要两个参数resolve函数和reject函数，成功则调用resolve，失败则调用reject。executor会在promise内部立即同步调用，而其中的异步操作会在执行器中进行
promise具有then方法，它用来指定回调，传递两个参数onResolved函数和onRejected函数，成功执行前者，失败执行后者，并返回一个新的promise对象
catch方法可以指定失败的回调，不能指定成功回调，但是实现还是由then来实现
promise函数对象的resolve函数（不是单个的实例对象），它接收一个成功的数据或promise对象，然后返回一个成功/失败的promise对象。用来快速得到一个promise对象和封装一个特定的值，将其转化为promise对象。如果传入的参数为非promise类型对象，则返回结果为成功的promise对象；如果传入promise对象，则它的结果决定resolve的结果
promise函数对象的reject函数，传入reason参数，返回一个失败的promise对象（就算传入成功的promise对象也是失败），传入什么失败的结果就是什么
promise函数对象的all函数，一般传入包含n个的promise数组promises，返回一个新的promise，只有所有的promise都成功，则all返回成功的promise，并带有promise们的结果组成的数组；如果但凡有一个promise失败，则all返回一个失败的promise，结果值为那个失败的promise结果值
promise函数对象的race函数，传入promises，返回一个由第一个完成的promise的结果状态决定的promise
```javascript
<script>
  let p1 = Promise.resolve('OK')
  let p2 = Promise.resolve('Success')
  let p3 = Promise.resolve('123')

  const result1 = Promise.race([p1, p2, p3])
    //result1就是p1的值
  let p4 = new Promise((resolve, reject) => {
    setTimeout(()=>{
      resolve('OK')
    }, 1000)
  })
  let p5 = Promise.resolve('Success')
  let p6 = Promise.resolve('123')

  const result2 = Promise.race([p4, p5, p6])
    //result2是p2的值，因为p2完成时p1还在倒计时
    
</script>
```
#### promise的几个关键问题

- 如何改变promise的状态？可以用resolved和rejected，以及用thorw抛出错误
- 一个promise可以指定多个成功回调函数？当promise状态改变为对应的状态时都会调用对应状态的回调函数。如果promise没有执行resolved和rejecte/throw来改变状态，用then绑定的各种回调函数都不会执行
- 改变promise状态和指定回调函数谁先谁后？正常情况下是先指定回调函数后再改变状态，有时候也可以先改变状态再指定回调。当执行器函数内的代码是一个同步任务，直接调用resolve/rejecte时就是先改变promise的状态再执行回调；当其内部是异步任务的时候（也就是说要先等待一段时间），就会是先执行then绑定回调函数，再改变状态。如果是先改变的状态，在then的时候就会直接执行回调函数；如果是先执行的then绑定回调函数，则promise会等到状态改变的时候再去执行回调函数
- then返回的新promise结果状态由什么决定？简单来说，由then指定的回调函数执行的结果决定。如果resolve/reject中抛出了异常，则result为失败；如果返回的结果是非promise类型的对象（如字符串等），则result为成功；如果返回的结果是Promise对象，则根据这个Promise对象来决定成功还是失败
- promise如何串连多个操作任务？promise的then方法是返回一个新的promise，所以我们可以用then链式调用串联多个同步/异步任务
- promise异常穿透。当使用then链式调用时，可以在最后指定失败的回调
```javascript
let p = new Promise((resolve, reject) => {
  setTimeout(()=>{
    //如果这里是reject则不会执行then里面的代码，会直接穿透到底部的catch然后抛出error
    resolve('OK')
  }, 1000)
})

p.then(value=>{
  //这里进行throw就会直接穿透下面的代码直到catch，然后抛出error
  throw 'Error'
}).then(value=>{
  console.log(111)
}).then(value=>{
  console.log(222)
}).catch(reason=>{
  console.warn(reason)
})
```

- 中断promise链。只能返回一个pending状态的promise对象，因为不管返回什么非promise对象（比如false，或者null），都会被认为是执行完成，promise会返回fullfilled；而如果返回promise类型对象，如果是resolved则会执行后面的resolve函数，如果是rejected也是下面的resolve来处理。而如果是pending，进入后面的then，状态没有发生改变，所以不会执行resolve或者是reject，就可以中断promise链
## 自定义封装promise
```javascript
class Promise {
  constructor(executor) {
    this.PromiseState = 'pending'
    this.PromiseResult = null
    this.callbacks = []
    const self = this

    function resolve(data) {
      // 保证promiseState只会改变一次
      if (self.PromiseState !== 'pending') return
      // 修改对象的状态（promiseState）为resolved/fulfilled
      self.PromiseState = 'resolved'
      // 设置对象结果值（promiseResult）为data
      self.PromiseResult = data
      self.callbacks = data
      setTimeout(() => {
        self.callbacks.forEach(item => {
          item.onResolved(data)
        })
      })

    }
    function reject(data) {
      if (self.PromiseState !== 'pending') return
      // 修改对象的状态（promiseState）为rejected
      self.PromiseState = 'rejected'
      // 设置对象结果值（promiseResult）为data
      self.PromiseResult = data
      // setTimeout把then调整为异步任务，这样可以让promise实现回调函数的异步执行
      setTimeout(() => {
        self.callbacks.forEach(item => {
          item.onRejected(data)
        })
      })

    }
    try {
      // 同步调用执行器函数
      executor(resolve, reject)
    } catch (e) {
      // 修改promise为失败
      reject(e)
    }
  }
  // 封装then方法
  then(onResolved, onRejected) {

    const self = this
    // 判断回调函数
    if (typeof onRejected !== 'function') {
      onRejected = reason => {
        throw reason
      }
    }
    if (typeof onResolved !== 'function') {
      onResolved = value => value
    }
    return new Promise((resolve, reject) => {
      // 封装函数
      function callback(type) {
        try {
          let result = type(self.PromiseResult)
          if (result instanceof Promise) {
            result.then(v => {
              resolve(v)
            }, r => {
              reject(r)
            })
          } else {
            // 非Promise对象类型结果对象为成功
            resolve(result)
          }
        } catch (e) {
          reject(e)
        }
      }
      if (this.PromiseState === 'resolved') {
        setTimeout(() => {
          callback(onResolved)
        })

      }
      if (this.PromiseState === 'rejected') {
        setTimeout(() => {
          callback(onRejected)
        })

      }
      if (this.PromiseState === 'pending') {
        // 保存回调函数，异步任务会用到
        this.callback.push({
          onResolved: function () {
            callback(onResolved)
          },
          onRejected: function () {
            callback(onRejected)
          }
        })
      }
    })


  }
  // catch方法
  catch(onRejected) {
    return this.then(undefined, onRejected)
  }
  static resolve(value) {
    return new Promise((resolve, reject) => {
      if (value instanceof Promise) {
        value.then(v => {
          resolve(v)
        }, r => {
          reject(r)
        })
      } else {
        resolve(value)
      }
    })
  }

  // 添加reject方法，返回的结果永远都是失败
  static reject(reason) {
    return new Promise((resolve, reject) => {
      reject(reason)
    })
  }
  // all方法
  static all(promises) {
    return new Promise((resolve, reject) => {
      let count = 0
      let arr = []
      for (let i = 0; i < promises.length; i++) {
        promises[i].then(v => {
          // 成功，全部promise对象都成功才执行resolve函数
          count++
          //这里不能用push，因为promises里面的promise不一定安顺西执行完，那么这样push进去的顺序就会被改变
          arr[i] = v
          if (count === promises.length) {
            resolve(arr)
          }
        }, r => {
          reject(r)
        })
      }
    })
  }

  // race方法，最先更新状态的决定race的promise的状态
  static race(promises) {
    return new Promise((resolve, reject) => {
      for (let i = 0; i < promises.length; i++) {
        promises[i].then(v => {
          // 不需要判断到最后，最先完成的就直接resolve
          resolve(v)
        }, r => {
          reject(r)
        })

      }
    })
  }
}
```
## async和await
#### async
async返回一个primise对象，其结果由async函数执行的返回值决定。如果返回的是非Promise类型的数据，则结果为成功；如果返回的是一个成功的Promise，则结果为成功；如果返回的是失败的Promise或抛出异常，则结果为失败。和then一样
#### await
await右侧表达式一般为promise对象，但也可以是其他的值。如果是promise对象，则await返回的是promise成功的值；如果不是promise对象，则直接把此值作为await的返回值。
注意，await必须卸载async函数中，但是async函数中可以没有await。如果await的promise失败了就会抛出异常，需要通过try-catch捕获处理
