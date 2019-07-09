//index.js
//获取应用实例
var app = getApp();
var myData = require('../../utils/data');
var util = require('../../utils/util');
var api = require('../../config/api.js');

Page({
  // 页面初始数据
  data: {
    userData:myData.userData(),
    addrDate:myData.userData().addrs,
    userInfo:{}
  },
  onLoad() {
    var that=this;
    
    wx.login({
      success: function (res) {
        debugger;
        var code = res.code;
        if (code) {
          wx.getUserInfo({
            success: function (re2s) {
              debugger;
              that.setData({
                userInfo: res2.userInfo
              })
              console.log(res2.userInfo + "/////////////////");
            }
          })
          that.getUserInfo(code, that.userInfo);
          console.log('获取用户登录凭证：' + code);
         
        } else {
          console.log('获取用户登录态失败：' + res.errMsg);
        }
      }
    });
  },
  onShow() {
    //this.getUserInfo();
    //console.log('->'+userInfo);
  },	
  // 地址编辑
  editAddr : function(e){
    console.log(e)
    wx.navigateTo({
      url:'../edit_addr/edit_addr?addrid='+e.currentTarget.dataset.addrid
    })
  },
  getUserInfo: function (code, userInfo) {
    wx.request({
      url: api.AuthLoginByWeixin,
      data: {
        code: code,
        userInfo: userInfo
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.errno == 0) {
         
        }
       
      }
    });
  }

})
