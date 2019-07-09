var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var check = require('../../../utils/check.js');

var app = getApp();
Page({
  data: {
    pigowner: {
      id: '0',
      name: '',
      description: '',
      ringnum: '',
      pigowner: ''
    },
    pigownerId: ''
  },

  bindinputName(event) {
    let pigowner = this.data.pigowner;
    pigowner.name = event.detail.value;
    this.setData({
      pigowner: pigowner
    });
  },
  bindinputDescription(event) {
    let pigowner = this.data.pigowner;
    pigowner.description = event.detail.value;
    this.setData({
      pigowner: pigowner
    });
  },
 
  getPigowneDetail() {
    let that = this;
    let id = that.data.pigownerId;
    util.request(api.PigownerDetail+'/'+id, {   }).then(function (res) {
      if (res.code === 0) {
        if (res.data) {
          that.setData({
            pigowner: res.data
          });
        }
      }
    });
    console.info(this.data.pigowner);
  },
 
 
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
    console.log(options+'->>>')
    if (options.id && options.id != 0) {
      this.setData({
        pigownerId: options.id
      });
      this.getPigowneDetail();
    }
  },
  onReady: function () {

  },
 
  
  cancelPigowner() {
    wx.navigateBack();
  },
  savePigowner() {
    debugger;
    console.log(this.data.pigowner)
    let pigowner = this.data.pigowner;

    if (pigowner.name == '') {
      util.showErrorToast('请输入足环号');

      return false;
    }

    let that = this;
    util.request(api.PigownerSave, {
      id: pigowner.id,
      name: pigowner.name,
      description: pigowner.description
    }, 'POST').then(function (res) {
      if (res.code === 0) {
        wx.navigateBack();
      }
    });

  },
  onShow: function () {
    // 页面显示

  },
  onHide: function () {
    // 页面隐藏

  },
  onUnload: function () {
    // 页面关闭

  }
})