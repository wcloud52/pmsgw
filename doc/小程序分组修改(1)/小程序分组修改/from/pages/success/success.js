//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    inputValue: '',
    listData: [{
      "userCode": "000001",
      "userName": "AAA   爱亚卡普-王志军",
      "toe": "2018-01-1665072",
      "a1": "√",
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
      "allMoney": "1800"
    }, {
      "userCode": "000001",
      "userName": "AAA   爱亚卡普-王志军",
      "toe": "2018-01-1665271",
        "a1": "√",
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
      "allMoney": "900"
    }, {
      "userCode": "000001",
      "userName": "AAA   爱亚卡普-王志军",
      "toe": "2018-01-1665296",
        "a1": "√",
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
      "allMoney": "2900"
    }],
    allTotal: 0
  },
  onLoad: function () {
    var that = this;
    that.getAllNum();
  },
  getAllNum: function () {
    var that = this;
    var listData = that.data.listData;
    var a1Arr = [], a2Arr = [], a3Arr = [], b1Arr = [], b2Arr = [], c1Arr = [], c2Arr = [], d1Arr = [], d2Arr = [], d3Arr = [], d4Arr = [];
    var a1Total = 0, a2Total = 0, a3Total = 0, b1Total = 0, b2Total = 0, c1Total = 0, c2Total = 0, d1Total = 0, d2Total = 0, d3Total = 0, d4Total = 0;
    var allTotal = 0;
    for (var i = 0; i < listData.length; i++) {
      allTotal += parseFloat(listData[i].allMoney);
      if (listData[i].a1 !== '-') {
        a1Arr.push(listData[i].a1);
      }
      if (listData[i].a2 !== '-') {
        a2Arr.push(listData[i].a2);
      }
      if (listData[i].a3 !== '-') {
        a3Arr.push(listData[i].a3);
      }
      if (listData[i].b1 !== '-') {
        b1Arr.push(listData[i].b1);
      }
      if (listData[i].b2 !== '-') {
        b2Arr.push(listData[i].b2);
      }
      if (listData[i].c1 !== '-') {
        c1Arr.push(listData[i].c1);
      }
      if (listData[i].c2 !== '-') {
        c2Arr.push(listData[i].c2);
      }
      if (listData[i].d1 !== '-') {
        d1Arr.push(listData[i].d1);
      }
      if (listData[i].d2 !== '-') {
        d2Arr.push(listData[i].d2);
      }
      if (listData[i].d3 !== '-') {
        d3Arr.push(listData[i].d3);
      }
      if (listData[i].d4 !== '-') {
        d4Arr.push(listData[i].d4);
      }
    }
    a1Total = a1Arr.length * 200;
    a2Total = a2Arr.length * 500;
    a3Total = a3Arr.length * 1000;
    b1Total = b1Arr.length * 500;
    b2Total = b2Arr.length * 1000;
    c1Total = c1Arr.length * 100;
    c2Total = c2Arr.length * 300;
    d1Total = d1Arr.length * 200;
    d2Total = d2Arr.length * 300;
    d3Total = d3Arr.length * 500;
    d4Total = d4Arr.length * 1000;
    allTotal += a1Total + a2Total + a3Total + b1Total + b2Total + c1Total + c2Total + d1Total + d2Total + d3Total + d4Total;
    that.setData({
      a1Total: a1Total,
      a2Total: a2Total,
      a3Total: a3Total,
      b1Total: b1Total,
      b2Total: b2Total,
      c1Total: c1Total,
      c2Total: c2Total,
      d1Total: d1Total,
      d2Total: d2Total,
      d3Total: d3Total,
      d4Total: d4Total,
      allTotal: allTotal
    })
  },
  goback: function () {
    wx.navigateBack({})
  },

})
