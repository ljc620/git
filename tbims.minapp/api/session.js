var SESSION_KEY = 'tbims_minapp_session';

var Session = {
  get: function () {
    return wx.getStorageSync(SESSION_KEY) || null;
  },

  set: function (session) {
    wx.setStorageSync(SESSION_KEY, session);
  },

  clear: function () {
    wx.removeStorageSync(SESSION_KEY);
  },
};

module.exports = Session;