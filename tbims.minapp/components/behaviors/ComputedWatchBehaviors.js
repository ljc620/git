/**
 * https://github.com/wechat-miniprogram/computed
 * 在wechat-miniprogram/computed 基础上增加watch功能
 * 
 * watch支持computed属性和state属性的watch功能
 * 
 * 需api版本2.2.3及以上
 * 
 * 用 Component 构造器来构造页面即可在页面上使用这个扩展，但需要在json文件中添加 "usingComponents": {}，否则会报错
 * https://developers.weixin.qq.com/miniprogram/dev/framework/custom-component/component.html#%E4%BD%BF%E7%94%A8-component-%E6%9E%84%E9%80%A0%E5%99%A8%E6%9E%84%E9%80%A0%E9%A1%B5%E9%9D%A2
 * 
 * 
 * 示例：
 * Component(
 *   connector.connectComponent({
 *     user: state => state.user
 *   })({
 *     behaviors: [computedBehavior],
 *     data: {
 *       a: 10,
 *       b: 2
 *     },
 *     watch: {
 *   '   dashboard.orgData': function (newVal, oldVal){
 *         do some thing
 *       }
 *     },
 *     computed: {
 *       c() {
 *         return this.data.a + this.data.b
 *     },
 *     methods: {
 *       onLoad: function(options) {
 *       },
 *     }
 *   })
 * )
 */
// import isEqual from 'lodash.isequal';
import { isEqual } from '../../vendor/weappx/weappx-weapp.js'

module.exports = Behavior({
  lifetimes: {
    created() {
      this._originalSetData = this.setData
      this.setData = this._setData
    },
    attached() {
      this._initComputed()
    }
  },
  definitionFilter(defFields) {
    const computed = defFields.computed || {}
    const computedKeys = Object.keys(computed)
    const computedCache = {}
    let doingSetData = false

    const watch = defFields.watch || {}
    const watchKeys = Object.keys(watch)

    // 计算 computed
    const calcComputed = (scope) => {
      const needUpdate = {}
      const data = defFields.data = defFields.data || {}

      for (let i = 0, len = computedKeys.length; i < len; i++) {
        const key = computedKeys[i]
        const getter = computed[key]

        if (typeof getter === 'function') {
          const value = getter.call(scope)

          if (computedCache[key] !== value) {
            needUpdate[key] = computedCache[key] = value
          }
        }
      }

      return needUpdate
    }

    const getOldData = (scope, dataKeys) => {
      var oldData = {}
      for (let i = 0, len = dataKeys.length; i < len; i++) {
        const key = dataKeys[i]
        oldData[key] = scope.data[key] || undefined
      }
      return oldData
    }

    /**
     * getter 根据路径获取对应 key 的 value
     * 
     * @param {object} data data 对象
     * @param {string} path data 对象下的某一键值对的索引
     * @return {any} keyValue
     * 
     * e.g.
     * var data = {"a":{"b":{"c":{"d":123}}}}
     * getter(data, 'a.b[c]') // return {"d":123}
     */
    const getter = (data, path) => {
      const pathArr = getPathArr(path)
      return pathArr.reduce((res, currentPath) => {
        const currentValueType = Object.prototype.toString.call(res)
        return /String|Number|Boolean|Null|Undefined/.test(currentValueType)
          ? undefined
          : res[currentPath]
      }, data)
    }
    const getPathArr = (path) => {
      const REG_KEY = /\[((?:\S+?))\]|\./g
      return path.toString().split(REG_KEY).filter(item => !!item)
    }

    // 执行 watch
    const callWatch = (scope, newData, oldData) => {
      if (newData === {} && oldData === {}) {
        return
      }

      for (let i = 0, len = watchKeys.length; i < len; i++) {
        const key = watchKeys[i]
        const newVal = getter(newData, key)
        const oldVal = getter(oldData, key)

        if (!isEqual(newVal, oldVal)) {

          const getter = watch[key]

          if (typeof getter === 'function') {
            getter.call(scope, newVal, oldVal)
          }
        }
      }
    }

    defFields.methods = defFields.methods || {}
    defFields.methods._setData = function (data, callback) {
      const originalSetData = this._originalSetData

      if (doingSetData) {
        // eslint-disable-next-line no-console
        console.warn('can\'t call setData in computed getter function!')
        return
      }

      doingSetData = true

      // TODO 过滤掉 data 中的 computed 字段
      const dataKeys = Object.keys(data)
      for (let i = 0, len = dataKeys.length; i < len; i++) {
        const key = dataKeys[i]

        if (computed[key]) delete data[key]
      }

      // 获取data属性的旧值
      const oldData = getOldData(this, Object.keys(data))
      // 做 data 属性的 setData
      originalSetData.call(this, data, callback)

      // 计算 computed
      const needUpdate = calcComputed(this)

      // 获取computed属性的旧值
      const oldComputedData = getOldData(this, Object.keys(needUpdate))
      // 做 computed 属性的 setData
      originalSetData.call(this, needUpdate)

      doingSetData = false

      // call watch
      callWatch(this, data, oldData)
      callWatch(this, needUpdate, oldComputedData)
    }


    // 初始化 computed
    defFields.methods._initComputed = function() {
      doingSetData = true

      // 计算 computed
      const needUpdate = calcComputed(this)
      this._originalSetData.call(this, needUpdate)

      doingSetData = false
    }
  }
})
