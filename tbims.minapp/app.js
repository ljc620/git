//app.js
var User = require('./api/user');
import store from './store/index';

App({
  onLaunch: function () {
    this.checkAndApplyUpdate();
    // if (!store.getState().user.token || !store.getState().user.userInfo || !store.getState().user.userInf.username) {
    //   console.log('开始登录并获取用户信息')
    //   User.getUserInfo(this, true, this.userInfoReadyCallback, this.userInfoFailCallback);
    // } else {
    //   console.log('用户已登录，用户信息完整')
    // }
    
  },
  onShow: function () {

  },
  onError: function(error) {
    // console.log('app error', error);
  },
  onPageNotFound: function(e){
    wx.switchTab({
      url: '/pages/index/index'
    }) // 如果是 tabbar 页面，请使用 wx.switchTab

  },
  checkAndApplyUpdate: function() {
    const updateManager = wx.getUpdateManager();
    updateManager.onCheckForUpdate(function (res) {
      // 请求完新版本信息的回调
      console.log('是否有更新：', res.hasUpdate);
    });
    updateManager.onUpdateReady(function () {
      // updateManager.applyUpdate();
      wx.showModal({
        title: '更新提示',
        content: '新版本已准备就绪，请重启应用更新。',
        showCancel: false,
        confirmText: '重启',
        success: function (res) {
          if (res.confirm) {
            // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
            updateManager.applyUpdate();
          }
        }
      });
    });
    updateManager.onUpdateFailed(function () {
      // 新的版本下载失败
      wx.showModal({
        title: '更新提示',
        content: '新版本下载失败',
        showCancel: false
      });
    });
  },
  // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
  // 所以此处加入 callback 以防止这种情况
  userInfoReadyCallback: function(res) {
    wx.hideLoading();
  },
  userInfoFailCallback: function(err) {
    const app = this;
    wx.hideLoading();
    if (store.getState().user.boundState !== -1) {
      wx.showModal({
        title: '错误',
        content: '数据加载失败，请重新加载',
        showCancel: false,
        confirmText: '重新加载',
        success: function (res) {
          if (res.confirm) {
            wx.showLoading({
              title: '数据加载中',
              mask: true
            });
            User.getUserInfo(app, true, this.userInfoReadyCallback, this.userInfoFailCallback);
          }
        }
      });
    }
  },
  shareAppMessage: {
    title: '奇趣美术',
    path: '/pages/index/index',
    imageUrl: 'http://image.qiquart.com/share.jpg'
  }
})