//index.js
//获取应用实例
var app = getApp();
var utils = require('../../utils/util3.js');
var Constant = require('../../utils/constant.js');

Page({
  data: {
    listData: [{
      "userCode": "000001",
      "userName": "AAA   爱亚卡普-王志军",
      "toe": "2018-01-1665072",
      "a1": "",
      "a2": "",
      "a3": "",
      "b1": "",
      "b2": "",
      "c1": "",
      "c2": "",
      "d1": "",
      "d2": "",
      "d3": "",
      "d4": "",
      "a1c": false,
      "a2c": false,
      "a3c": false,
      "b1c": false,
      "b2c": false,
      "c1c": false,
      "c2c": false,
      "d1c": false,
      "d2c": false,
      "d3c": false,
      "d4c": false,
      "allMoney": 0
    }, {
      "userCode": "000001",
      "userName": "AAA   爱亚卡普-王志军",
      "toe": "2018-01-1665271",
      "a1": "",
      "a2": "",
      "a3": "",
      "b1": "",
      "b2": "",
      "c1": "",
      "c2": "",
      "d1": "",
      "d2": "",
      "d3": "",
      "d4": "",
      "a1c": false,
      "a2c": false,
      "a3c": false,
      "b1c": false,
      "b2c": false,
      "c1c": false,
      "c2c": false,
      "d1c": false,
      "d2c": false,
      "d3c": false,
      "d4c": false,
      "allMoney": 0
    }, {
      "userCode": "000001",
      "userName": "AAA   爱亚卡普-王志军",
      "toe": "2018-01-1665296",
      "a1": "-",
      "a2": "√",
      "a3": "-",
      "b1": "-",
      "b2": "√",
      "c1": "-",
      "c2": "-",
      "d1": "-",
      "d2": "-",
      "d3": "-",
      "d4": "√",
      "a1c": false,
      "a2c": false,
      "a3c": false,
      "b1c": false,
      "b2c": false,
      "c1c": false,
      "c2c": false,
      "d1c": false,
      "d2c": false,
      "d3c": false,
      "d4c": false,
      "allMoney": 1500
    }],
    race:
    {
      list: []
    },
    slider:{
      banner:[],
      duration: 2000,
      indicatorDots: true,
      autoplay: true,
      interval: 3000
    },
    loading: false,
    step: 0,
    news:
    {
      list: [],
      plain: false,
     
    },
    currentTab: 0
  },
  //事件处理函数
  bindViewTap(e) {
    wx.navigateTo({
      url: '/pages/infoDetail/index?Id=' + e.target.dataset.id
    })
  },
  //点击切换
  clickTab: function (e) {
    var that = this;
    if (this.data.currentTab === e.target.dataset.current) {
      return false;
    } else {
      that.setData({
        currentTab: e.target.dataset.current,
      })
    }
  },
  loadMoreNews (e) {
    this.pageIndex++;
    var that = this
    that.setData({ loading: true });
    this.getInfoNews(that, "news.list", this.data.news.list,  that.pageIndex);
  },
  //获取比赛列表
  getRacelist: function (that) {
    wx.request({
      url: Constant.SERVER_ADDRESS + "/racelist",
      headers: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        if (res == null) {
          console.error(Constant.ERROR_DATA_IS_NULL);
          return;
        }
        that.setData({
          'race.list': res.data
        })
      }
    });
  },
  //获取轮播图
  getSlider: function (that) {
    wx.request({
      url: Constant.SERVER_ADDRESS + "/slider",
      headers: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        if (res == null) {
          console.error(Constant.ERROR_DATA_IS_NULL);
          return;
        }
         that.setData({
           'slider.banner':res.data
         })
      }
    });
  },
  //新闻
  getInfoNews: function (that, listKey, listVal, pageIndex) {  
    
    wx.request({
      url: Constant.SERVER_ADDRESS + "/info?pageIndex=" + pageIndex,
      headers: {
        'Content-Type': 'application/json'
      },
      success: function (res) {
        
        if (res == null || res.data == null || res.data.length<1) {
         
          that.setData({ loading: false });
         return;
        }
        else
        {
          
          that.setData({
            loading: false,
            [listKey]: listVal.concat(res.data)
          });
        }
      }
    });
   
  },
 
  onLoad () {
    
    this.pageIndex=1;
    this.getSlider(this);
    this.getInfoNews(this, "news.list", this.data.news.list, this.pageIndex);
    this.getRacelist(this);
  }
})
