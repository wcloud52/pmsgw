//app.js
var util = require('./utils/util2.js');
var api = require('./config/api.js');
var user = require('./utils/user.js');
App({
    onLaunch: function () {
        console.log('App Launch')
    }
  ,
  onShow: function (options) {
    user.checkLogin().then(res => {
      this.globalData.hasLogin = true;
    }).catch(() => {
      this.globalData.hasLogin = false;
    });
  },
  globalData: {
    hasLogin: false
  }
});

