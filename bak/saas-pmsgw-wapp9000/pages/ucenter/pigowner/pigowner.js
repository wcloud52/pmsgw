var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var app = getApp();

Page({
  data: {
    pigownerList: [],
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
    this.getPigownerList();
  },
  getPigownerList() {
    let that = this;
    util.request(api.PigownerList).then(function (res) {
     
      if (res.code === 0) {
        that.setData({
          pigownerList: res.data
        });
      }
    });
  },
  pigownerAddOrUpdate(event) {
   debugger;
    wx.navigateTo({
      url: '/pages/ucenter/pigownerAdd/pigownerAdd?id=' + event.currentTarget.dataset.pigownerId
    })
  },
  deletePigowner(event) {
    console.log(event.target)
    let that = this;
    wx.showModal({
      title: '',
      content: '确定要删除鸽子？',
      success: function (res) {
        if (res.confirm) {
          let pigownerId = event.target.dataset.pigownerId;
          util.request(api.PigownerDelete + '/' + pigownerId, {  }, 'POST').then(function (res) {
            if (res.code === 0) {
              that.getPigownerList();
            }
          });
          console.log('用户点击确定')
        }
      }
    })
    return false;

  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})