import connector from '../vendor/weappx/weappx-weapp.js';
import weappx from '../vendor/weappx/weappx.js';
import user from './models/user.js';



/**
 * 初始化store
 */
const app = weappx();

/**
 * 引入多个model时需添加到models数组中
 */
const models = [user];

/**
 * 刷新/重置所有state
 */
function resetStore() {
  
  app.dispatcher.user.reset();

}


app.models(models);
// 启动store
const storeInstance = app.start();
connector.setStore(storeInstance);


const getState = function() { 
  return connector.getStore().getState();
}

module.exports = {
  store: app,
  reset: resetStore,
  getState: getState,
  dispatcher: app.dispatcher
}
