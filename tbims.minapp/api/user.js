import Request from './request';
import store from '../store/index.js';

/**
 * 获取用户信息
 * @param {Object} [app]                必须。APP实例，在onLaunch阶段，此处无法通过getApp())获取到app实例
 * @param {Boolean} [requireLogin]      可选。默认值false， 表示在获取信息前是否先执行用户登录
 * @param {Function} [successCallback]  可选。成功回调函数
 * @param {Function} [failCallback]     可选。失败回调函数
 */
function getUserInfo(app, requireLogin = false, successCallback, failCallback) {
  Request.request({
    url: '/comm/info',
    login: requireLogin,
    success: function (response) {
      // console.log('用户信息获取成功', response);
      store.dispatcher.user.setUserInfo(response.data.userInfo);
      store.dispatcher.user.setRoles(response.data.roles); 
      // store.dispatcher.user.setMenuList(response.data.menuList);
      
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      if(successCallback) {
        successCallback(response);
      }
    },
    fail: function (err) {
      console.log('用户信息获取失败', err);
      if(failCallback) {
        failCallback(err);
      }
    }
  });
}

/**
 * 发送短信验证码
 */
function sendSmsCaptche(phoneNumber, successCallback, failCallback) {
  Request.requestForm({
    url: '/register/getSmsCode',
    method: 'POST',
    data: { phoneNumber: phoneNumber },
    success: successCallback,
    fail: failCallback
  });
}
/**
 * 新建用户并关联微信账号
 */
function createUserBound(data, successCallback, failCallback) {
  Request.requestForm({
    url: '/weixin/createAccount',
    method: 'POST',
    data: data,
    success: successCallback,
    fail: failCallback
  });
}


module.exports = { getUserInfo, sendSmsCaptche, createUserBound }