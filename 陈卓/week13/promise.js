class Promise{
  //构造函数
  constructor(executor) {
    this.PromiseState = 'pending'
    this.PromiseResult = null
    this.callbacks = []
    const self = this
    function resolve(data) {
      if (self.PromiseState !== 'pending') return;
      //修改对象的状态（PromiseState）
      self.PromiseState = 'resolved'
      //设置对象结果值（PromiseResult）
      self.PromiseResult = data
      setTimeout(() => {
        self.callbacks.forEach(item => {
          item.onResolved()
        })
      })
    }
    function reject(data) {
      if (self.PromiseState !== 'pending') return;
      //修改对象的状态（PromiseState）
      self.PromiseState = 'rejected'
      //设置对象结果值（PromiseResult）
      self.PromiseResult = data
      setTimeout(() => {
        self.callbacks.forEach(item => {
          item.onRejected()
        })
      })
    }
    try {
      //执行器函数  同步调用
      executor(resolve, reject)

    } catch (error) {
      //修改Promise对象状态为失败
      reject(error)
    } 
  }

  //添加then方法
then (onResolved, onRejected) {
    const self = this
    //then方法没有传递第二个参数，设置默认值
    if (typeof onRejected !== 'function') {
      onRejected = reason => {
        throw reason
      }
    }
    if (typeof onResolved !== 'function') {
      onResolved = value => value
    }
    return new Promise((resolve, reject) => {
      //封装函数
      function callback(type) {
        let result = type(self.PromiseResult)
        try {
          if (result instanceof Promise) {
            //如果是Promise类型的对象
            result.then(v => {
              resolve(v)
            }, j => {
              reject(j)
            })
          } else {
            //结果的对象状态变为成功
            resolve(result)
          }
        } catch (e) {
          reject(e)
        }
      }
      //调用回调
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

      //异步任务回调的执行
      if (this.PromiseState === 'pending') {
        //保存回调函数
        this.callbacks.push({
          onResolved: function () {
            callback(onResolved)
          },
          onRejected: function () {
            callback(onRejected)
          },
        })
      }
    })
  }

//添加catch方法  妙！！！！
catch (onRejected) {
    return this.then(undefined, onRejected)
  }

//添加resolve方法
static resolve  (value) {
    return new Promise((resolve, reject) => {
      if (value instanceof Promise) {
        value.then(v => {
          resolve(v)
        }, j => {
          reject(j)
        })
      } else {
        resolve(value)
      }
    })
  }

//添加reject方法
static reject (reason) {
    return new Promise((resolve, reject) => {
      reject(reason)
    })
  }

//添加all方法   面试常考
static all (promises) {
    //返回结果为Promise对象
    return new Promise((resolve, reject) => {
      let count = 0
      let arr = []
      //遍历
      for (let i = 0; i < promises.length; i++) {
        promises[i].then(v => {
          //此时对象的状态是成功的
          arr[i] = v
          count++
          if (count === promises.length) {
            resolve(arr)
          }
        }, j => {
          reject(j)
        })

      }
    })
  }

//添加race方法
static race (promises) {
    return new Promise((resolve, reject) => {
      for (let i = 0; i < promises.length; i++) {
        promises[i].then(v => {
          resolve(v)
        }, j => {
          reject(j)
        })
      }
    })
  }

}