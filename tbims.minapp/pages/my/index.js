// pages/my/my.js
import connector from '../../vendor/weappx/weappx-weapp.js';
import store from '../../store/index.js';
var User = require('../../api/user');
var Constants = require('../../api/constants');
const computedBehavior = require('../../components/behaviors/ComputedWatchBehaviors.js')

Component(
  connector.connectComponent({
    user: state => state.user
  })({
    behaviors: [computedBehavior],
    /**
     * 页面的初始数据
     */
    data: {
      canRefreshData: true,
    },
    // watch: {
    //   'newBound': function (newVal, oldVal){
    //     wx.showModal({
    //       title: 'watch',
    //       content: 'watch test newBound',
    //     })
    //   },
    //   'user.roles': function (newVal, oldVal) {
    //     if (oldVal.length === 0 && newVal.length > 0 && newVal.includes('PRT')) {
    //       wx.showModal({
    //         title: 'watch',
    //         content: 'watch test user.roles',
    //       })
    //     }
    //   }
    // },
    computed: {
      avatarUrl() {
        if (this.data.user && this.data.user.wxUserInfo){
          return this.data.user.wxUserInfo.avatarUrl;
        } else if (this.data.user && this.data.user.userInfo && this.data.user.userInfo.avatar){
          return Constants.BASE_URL_IMG + '/avatar/' + this.data.user.userInfo.avatar;
        } else {
          return '/assets/image/default.avatar.png'; 
        }
      },
      newBound() {
        // console.log(this.data.user.roles)
        return !this.data.user.roles || !this.data.user.roles.includes('PRT');
      }
    },
    methods: {
      /**
       * 生命周期函数--监听页面加载
       */
      onLoad: function (options) {
        if(!this.data.user.wxUserInfo) {
          let that = this;
          wx.getUserInfo({
            lang: 'zh_CN',
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              store.dispatcher.user.setWxUserInfo(res.userInfo);
              console.log(res.userInfo);
            },
            fail: (err) => {
              console.log('获取信息失败', err);
            }
          });
        }
        // console.log(this.setData)
        // console.log('onLoad')
      },
      /**
       * 生命周期函数--监听页面初次渲染完成
       */
      onReady: function () {
        // console.log('onReady')
      },

      /**
       * 生命周期函数--监听页面显示
       */
      onShow: function () {
        // console.log('onShow')
      },

      /**
       * 生命周期函数--监听页面隐藏
       */
      onHide: function () {
        // console.log('onHide')
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
      
      btnRefresh: function () {
        if(this.data.canRefreshData) {
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
        // console.log(this.data);
      },
      refreshSuccess: function(res) {
        wx.hideLoading();
        wx.showModal({
          title: '成功',
          content: '数据刷新完成',
          showCancel: false,
          confirmText: '确定',
        });
        console.log('数据刷新完成');
      },
      refreshFail: function(err) {
        wx.hideLoading();
        wx.showModal({
          title: '错误',
          content: '数据刷新失败，请重试',
          showCancel: false,
          confirmText: '重新刷新',
          success: function (res) {
            if (res.confirm) {
              wx.showLoading({
                title: '正在刷新数据',
                mask: true
              });
              User.getUserInfo(getApp(), false, this.refreshSuccess, this.refreshFail);
            }
          }
        });
        console.log('数据刷新失败');
      },
    }
  })
)