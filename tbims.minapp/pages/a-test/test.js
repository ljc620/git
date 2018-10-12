// pages/a-test/test.js
import connector from '../../vendor/weappx/weappx-weapp.js';
import store from '../../store/index.js';
var User = require('../../api/user');
const computedBehavior = require('../../components/behaviors/ComputedWatchBehaviors.js')

//获取应用实例
const app = getApp()

Component(
  connector.connectComponent({
    user: state => state.user
  })({
    behaviors: [computedBehavior],
    /**
     * 页面的初始数据
     */
    data: {
      userInfo: {},
      hasUserInfo: false,
      canIUse: wx.canIUse('button.open-type.getUserInfo'),

      canRefreshData: true,
      a: 10,
      b: 1,
    },
    watch: {
      'a': function (newVal, oldVal) {
        wx.showModal({
          title: '成功',
          content: 'watch完成,' + newVal + ' : ' + oldVal,
          showCancel: false,
          confirmText: '确定',
        })
      },
    },
    computed: {
      c() {
        return (this.data.user.userInfo ? this.data.user.userInfo.username : '') + '-' + this.data.a + this.data.b;
      }
    },
    methods: {
      /**
       * 生命周期函数--监听页面加载
       */
      onLoad: function (options) {

      },

      /**
       * 生命周期函数--监听页面初次渲染完成
       */
      onReady: function () {

      },

      /**
       * 生命周期函数--监听页面显示
       */
      onShow: function () {

      },

      /**
       * 生命周期函数--监听页面隐藏
       */
      onHide: function () {

      },

      /**
       * 生命周期函数--监听页面卸载
       */
      onUnload: function () {

      },

      /**
       * 页面相关事件处理函数--监听用户下拉动作
       */
      onPullDownRefresh: function () {

      },

      /**
       * 页面上拉触底事件的处理函数
       */
      onReachBottom: function () {

      },

      /**
       * 用户点击右上角分享
       */
      onShareAppMessage: function () {
        return getApp().shareAppMessage
      },
      //事件处理函数
      bindViewTap: function () {
        wx.navigateTo({
          url: '../logs/logs'
        })
      },
      getUserInfo: function (e) {
        console.log(e)
        this.setData({
          userInfo: e.detail.userInfo,
          hasUserInfo: true
        });
      },
      btnChange: function () {
        let b = this.data.a;
        this.setData({
          a: b + 1
        })
      },
      btnRefresh: function () {
        if (this.data.canRefreshData) {
          console.log('开始刷新数据');
          this.setData({
            canRefreshData: false
          });
          wx.showLoading({
            title: '正在刷新数据',
            mask: true
          });
          store.reset();
          // wx.switchTab({
          //   url: '/pages/index/index'
          // });
          User.getUserInfo(getApp(), false, this.refreshSuccess, this.refreshFail);
          var that = this;
          setTimeout(function () {
            that.setData({
              canRefreshData: true
            });
          }, 5000); //循环间隔 单位ms
        }
        console.log(this.data);
      },
      refreshSuccess: function (res) {
        wx.hideLoading();
        wx.showModal({
          title: '成功',
          content: '数据刷新完成',
          showCancel: false,
          confirmText: '确定',
        });
        console.log('数据刷新完成');
      },
      refreshFail: function (err) {
        wx.hideLoading();
        let that = this;
        wx.showModal({
          title: '错误',
          content: '数据刷新失败，请重试',
          // showCancel: false,
          confirmText: '重新刷新',
          success: function (res) {
            if (res.confirm) {
              wx.showLoading({
                title: '正在刷新数据',
                mask: true
              });
              // User.getUserInfo(getApp(), false, that.refreshSuccess, that.refreshFail);
            }
          }
        });
        console.log('数据刷新失败');
      }
    }
  })
)
