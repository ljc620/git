import constants from './constants';
import utils from './utils';
import loginLib from './login';
import store from '../store/index.js'

// 全局Header，
var globalHeder = {
  'X-Requested-With': 'XMLHttpRequest'  // 标识ajax请求
};
var noop = function noop() { };


/***
 * @class
 * 表示请求过程中发生的异常
 */
var RequestError = (function () {
  function RequestError(type, message) {
    Error.call(this, message);
    this.type = type;
    this.message = message;
  }

  RequestError.prototype = new Error();
  RequestError.prototype.constructor = RequestError;

  return RequestError;
})();

function request(options) {
  if (typeof options !== 'object') {
    var message = '请求传参应为 object 类型，但实际传了 ' + (typeof options) + ' 类型';
    throw new RequestError(constants.ERR_INVALID_PARAMS, message);
  }

  if (options.url.substr(0, 4) !== 'http'){
    options.url = constants.BASE_URL + options.url;
  }

  var requireLogin = options.login;
  var success = options.success || noop;
  var fail = options.fail || noop;
  var complete = options.complete || noop;
  var originHeader = options.header || {};

  // 成功回调
  var callSuccess = function () {
    success.apply(null, arguments);
    complete.apply(null, arguments);
  };

  // 失败回调
  var callFail = function (error) {
    fail.call(null, error);
    complete.call(null, error);
  };

  // 是否已经进行过重试
  var hasRetried = false;

  if (requireLogin) {
    doRequestWithLogin();
  } else {
    doRequest();
  }

  // 登录后再请求
  function doRequestWithLogin() {
    loginLib.loginWithCode({ success: doRequest, fail: callFail });
  }

  // 实际进行请求的方法
  function doRequest() {
    var authHeader = {}
    var token = store.getState().user.token;
    if (token) {
      authHeader[constants.HEADER_TOKEN] = token;
    }

    wx.request(utils.extend({}, options, {
      header: utils.extend({}, globalHeder, originHeader, authHeader),

      success: function (response) {
        var data = response.data || {};

        // 保存会话Cookie（避免服务端每次都生成session）
        if (response && response.header && response.header['Set-Cookie']) {
          store.dispatcher.user.setToken(response.header['Set-Cookie']);    //保存Cookie到Storage
          console.log("保存Cookie：", response.header['Set-Cookie']);
        }

        var error, message;
        if ((data && data.errcode && data.errcode === 1004) || response.statusCode === 401) {
          // Session.clear(); // 采用Cookie的方式session不清楚，避免服务器重复创建session
          // store.dispatcher.user.clearToken();
          // 如果是登录态无效，并且还没重试过，会尝试登录后刷新凭据重新请求
          if (!hasRetried) {
            hasRetried = true;
            doRequestWithLogin();
            return;
          }

          message = '登录已过期';
          error = new RequestError(data.error, message);

          callFail(error);
          return;
        } else if (response.statusCode >= 400) {
          message = (response.data && response.data.message) ? response.data.message : response.data;
          error = new RequestError(null, message);
          callFail(error);
        } else {
          callSuccess.apply(null, arguments);
        }
      },

      fail: callFail,
      complete: noop,
    }));
  };

};

function requestForm(options) {
  if(options.header){
    options.header["Content-Type"] = "application/x-www-form-urlencoded";
  } else {
    options['header'] = { "Content-Type": "application/x-www-form-urlencoded"};
  }
  return request(options);
}

module.exports = {
  RequestError: RequestError,
  request: request,
  requestForm: requestForm
};