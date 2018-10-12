
var Session = require('../../api/session');

const userModel = {
  namespace: 'user',

  state: {
    userInfo: null, // { id: 0, username: '', userType: '', name: '', avatar: '', status: '', isOrgAdmin: true }
    roles: [],
    menuList: [], // [ { path: '', name: '', title: '', icon: '', permission: [], children: [] } ],
    token: Session.get(),
    wxUserInfo: null  // 微信用户信息
  },

  mutations: {
    setToken(state, token) {
      state.token = token;
      Session.set(token);
    },
    clearToken(state) {
      state.token = '';
      Session.clear();
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    setRoles(state, roles) {
      state.roles = roles;
    },
    setMenuList(state, menuList){
      state.menuList = menuList;
    },
    setWxUserInfo(state, wxUserInfo) {
      state.wxUserInfo = wxUserInfo;
    },
    reset(state) {
      state.userInfo = null;
      state.roles = [];
      state.menuList = [];
      state.token = Session.get();
    }
  },
}

module.exports = userModel