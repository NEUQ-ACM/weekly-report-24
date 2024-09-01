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
