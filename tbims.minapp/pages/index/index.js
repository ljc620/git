//index.js
import connector from '../../vendor/weappx/weappx-weapp.js';
import store from '../../store/index.js';

//获取应用实例
const app = getApp()

Page(
  connector.connectPage({
    user: state => state.user
  })({
    data: {
      imgUrls: [
        'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
        'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
        'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
      ]
    },
    onLoad: function () {
      // console.log('user', this.data.user);
      if (!this.data.user.userInfo) {
        // wx.showLoading({
        //   title: '数据加载中',
        //   mask: true
        // });
      }
    },
    onShow: function() {
      
    },
    onShareAppMessage: function (res) {

    },
    //事件处理函数


  })
);