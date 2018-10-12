import User from '../../api/user';

//获取应用实例
const app = getApp();
var timer;

Page({

  /**
   * 页面的初始数据
   */
  data: {
    phoneNumber: '',
    smsCaptcha: '',
    isAgree: false,
    btnSendSmsText: '发送验证码',
    btnSendSmsDisabled: false,
    sendInterval: 120
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
    
  },
  onShow: function() {

  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    
  },
  bindAgreeChange: function (e) {
    this.setData({
      isAgree: !!e.detail.value.length
    });
  },
  /**
   * 输入控件值修改时触发（id值必须与data字段键名相同）
   */
  onInputChange: function(event) {
    this.setData({
      [event.target.id]: event.detail
    });
  },
  // 发送短信验证码
  sendSmsCaptche: function(e,that) {
    if(this.data.phoneNumber.length !=11 ) {
      this.showMessage('请正确输入手机号');
      return;
    }
    var that = this;
    User.sendSmsCaptche(this.data.phoneNumber,(response)=>{
      console.log('短信验证码发送成功', response);

      that.showMessage('短信验证码发送成功');
      that.setData({
        btnSendSmsText: '重新发送(' + that.data.sendInterval + 's)',
        btnSendSmsDisabled: true
      })
      timer = setInterval(function () {
        var lastInterval = that.data.sendInterval - 1;
        if (lastInterval <= 0) {
          clearTimeout(timer);
          that.setData({
            btnSendSmsText: '发送验证码',
            btnSendSmsDisabled: false,
            sendInterval: 120
          });
        } else {
          that.setData({
            btnSendSmsText: '重新发送(' + lastInterval + 's)',
            sendInterval: lastInterval
          });
        }
      }, 1000); //循环间隔 单位ms
    }, (err) => {
      console.log('短信验证码发送失败', err);
      that.showMessage(err.message);
    });
  },
  /**
   * 提交绑定
   */
  btnBoundSubmit: function() {
    if(this.data.phoneNumber.length != 11) {
      this.showMessage('请正确输入手机号');
      return;
    } else if (this.data.smsCaptcha.length != 6) {
      this.showMessage('请正确输入短信验证码');
      return;
    } else if (!this.data.isAgree) {
      this.showMessage('阅读并同意《服务条款和隐私声明》方可注册');
      return;
    }
    
    var that = this;
    wx.getUserInfo({
      lang: 'zh_CN',
      success: res => {
        wx.showLoading({
          title: '正在提交数据',
          mask: true
        });
        wx.login({
          success(loginResult) {
            var postData = {
              country: res.userInfo.country,
              province: res.userInfo.province,
              city: res.userInfo.city,
              gender: res.userInfo.gender,
              wxNickName: res.userInfo.nickName,
              wxCode: loginResult.code,
              phoneNumber: that.data.phoneNumber,
              smsCaptcha: that.data.smsCaptcha
            };
            User.createUserBound(postData, that.successBound, that.failBound);
          }
        })
      },
      fail: () => {
        wx.showModal({
          title: '错误',
          content: '读取微信公开信息错误。',
          showCancel: false
        });
      }
    })
    
  },
  successBound: function(response){
    console.log('账户创建成功', response);
    // that.showMessage('账户关联成功');
    wx.switchTab({
      url: '/pages/index/index'
    });
    User.getUserInfo(app, true, app.userInfoReadyCallback, app.userInfoFailCallback);
  },
  failBound: function(err){
    console.log('账户创建失败', err);
    this.showMessage(err.message);
  },
  
  // 显示提示消息
  showMessage: function (msg) {
    wx.showToast({
      title: msg,
      icon: 'none'
    });
  }
})