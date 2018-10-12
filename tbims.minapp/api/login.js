import constants from './constants';
import store from '../store/index.js'

const noop = function noop() { }
const defaultOptions = {
  method: 'GET',
  success: noop,
  fail: noop,
  loginUrl: constants.BASE_URL + '/doLoginWX',
}

/**
 * @method
 * 只通过 wx.login 的 code 进行登录
 * 已经登录过的用户，只需要用 code 换取 openid，从数据库中查询出来即可
 * 无需每次都使用 wx.getUserInfo 去获取用户信息
 * 后端 Wafer SDK 需配合 1.4.x 及以上版本
 * 
 * @param {Object}   opts           登录配置
 * @param {string}   opts.loginUrl  登录使用的 URL，服务器应该在这个 URL 上处理登录请求，建议配合服务端 SDK 使用
 * @param {string}   [opts.method]  可选。请求使用的 HTTP 方法，默认为 GET
 * @param {Function} [opts.success] 可选。登录成功后的回调函数，参数 userInfo 微信用户信息
 * @param {Function} [opts.fail]    可选。登录失败后的回调函数，参数 error 错误信息
 */
function loginWithCode(opts) {
  opts = Object.assign({}, defaultOptions, opts)

  if (!opts.loginUrl) {
    return opts.fail(new Error('登录错误：缺少登录地址，请通过 setLoginUrl() 方法设置登录地址'))
  }

  wx.login({
    success(loginResult) {
      // 构造请求头，包含 code、encryptedData 和 iv
      const header = {
        ['X-WX-Code']: loginResult.code,
        ['X-Requested-With']: 'XMLHttpRequest'
      }
      
      const token = store.getState().user.token;
      if (token) {
        header[constants.HEADER_TOKEN] = token;
      }

      // 请求服务器登录地址，获得会话信息
      wx.request({
        url: opts.loginUrl,
        header: header,
        method: opts.method,
        success(result) {
          const data = result.data;

          // 保存会话Cookie（不论登录是否成功都保存，避免服务端每次都生成session）
          if (result && result.header && result.header['Set-Cookie']) {
            store.dispatcher.user.setToken(result.header['Set-Cookie']);    //保存Cookie到Storage
            console.log("保存Cookie：", result.header['Set-Cookie']);
          }

          // if (!data || !data.token) {
          //   return opts.fail(new Error(`用户未登录过，请先使用 login() 登录`))
          // }

          if (data && data.errcode && data.errcode === 1001) {
            wx.redirectTo({
              url: '/pages/userCreate/userCreate',
            });
            console.log("用户微信未关联账户")
            return; // opts.fail(new Error(`用户微信未关联账户`));
          } else if (result.statusCode >= 500) {
            return opts.fail(new Error(`登录失败，服务器错误(${result.statusCode})`))
          } else if (!data || !data.token) {
            return opts.fail(new Error(`登录失败(${data.errcode})：${data.message}`))
          }

          console.log('登录成功', store.getState().user.token );
          opts.success(store.getState().user.token)
        },
        fail(err) {
          console.error('登录失败，可能是网络错误或者服务器发生异常', err)
          opts.fail(err)
        }
      });
    }
  })
}

function setLoginUrl(loginUrl) {
  defaultOptions.loginUrl = loginUrl;
}

module.exports = { setLoginUrl, loginWithCode }