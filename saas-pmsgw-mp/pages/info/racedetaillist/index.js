//index.js
//获取应用实例
var app = getApp();
var utils = require('../../../utils/util3.js');
var Constant = require('../../../utils/constant.js');

Page({
  data: {
   
    race:
    {
      disabled:false,
      master:{},
      detail: []
    },
    inputValue: '',
    masterId:"",
    loading: false,
    moreLoading:false
  },

  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value
    })
  },
  // 搜索
  bindSearch: function () {
    this.pageIndex = 1;
    var that = this;
    that.setData({ loading: true,
      'race.detail':[]
     });
    this.getRacelist(that, this.data.masterId, that.pageIndex, that.data.inputValue);
  },
  clearInput: function () {
    this.setData({
      inputValue: ''
    })
  },

  loadMoreNews(e) {
    this.pageIndex++;
    var that = this
    that.setData({ 
      moreLoading: true,
      loading: false });
    this.getRacelist(that, this.data.masterId, that.pageIndex, that.data.inputValue);
  },
  //获取比赛列表
  getRacelist: function (that, masterId, pageIndex, pigowner) {
    wx.request({
      url: Constant.SERVER_ADDRESS + "/racedetaillist?masterId=" + masterId + "&pageIndex=" + pageIndex + "&pigowner=" + pigowner,
      headers: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        if (res == null) {
          console.error(Constant.ERROR_DATA_IS_NULL);
          that.setData({ 
            moreLoading: false,
            loading: true });
          return;
        }
       
        that.setData({
          moreLoading: false,
          loading: true,
          'race.master': res.data.master,
          'race.detail': that.data.race.detail.concat(res.data.detail)
        });

        var disabled = false;
        if (that.data.race.detail == null || that.data.race.detail.length < 50) {
          disabled = true;
        }
        that.setData({
          'race.disabled': disabled
        });
      }
    });
  },


  onLoad(options) {

    var that = this;
    if (options == null) {

      return;
    }
    this.pageIndex = 1;
    that.setData({ masterId: options.masterId });
    this.getRacelist(this, options.masterId, this.pageIndex,that.data.inputValue);
  }
})
