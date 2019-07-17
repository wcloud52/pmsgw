//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    inputValue: '',
    saveBtnDisabled: false,
    allTotal: 0,
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
    }]
  },
  onLoad: function () {
    this.getAllNum();
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  getAllNum: function () {
    var that = this;
    var listData = that.data.listData;
    var a1Arr = [], a2Arr = [], a3Arr = [], b1Arr = [], b2Arr = [], c1Arr = [], c2Arr = [], d1Arr = [], d2Arr = [], d3Arr = [], d4Arr = [];
    var a1Total = 0, a2Total = 0, a3Total = 0, b1Total = 0, b2Total = 0, c1Total = 0, c2Total = 0, d1Total = 0, d2Total = 0, d3Total = 0, d4Total = 0;
    var allTotal = 0;
    for (var i = 0; i < listData.length; i++) {
      allTotal += parseFloat(listData[i].allMoney);
      if (listData[i].a1 == '√') {
        a1Arr.push(listData[i].a1);
      }
      if (listData[i].a2 == '√') {
        a2Arr.push(listData[i].a2);
      }
      if (listData[i].a3 == '√') {
        a3Arr.push(listData[i].a3);
      }
      if (listData[i].b1 == '√') {
        b1Arr.push(listData[i].b1);
      }
      if (listData[i].b2 == '√') {
        b2Arr.push(listData[i].b2);
      }
      if (listData[i].c1 == '√') {
        c1Arr.push(listData[i].c1);
      }
      if (listData[i].c2 == '√') {
        c2Arr.push(listData[i].c2);
      }
      if (listData[i].d1 == '√') {
        d1Arr.push(listData[i].d1);
      }
      if (listData[i].d2 == '√') {
        d2Arr.push(listData[i].d2);
      }
      if (listData[i].d3 == '√') {
        d3Arr.push(listData[i].d3);
      }
      if (listData[i].d4 == '√') {
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
  bindKeyInput: function (e) {
    this.setData({
      inputValue: e.detail.value
    })
  },
  // 搜索
  bindSearch: function () {
    var that = this;
  },
  clearInput: function () {
    this.setData({
      inputValue: ''
    })
  },
  // 保存
  saveForm: function () {
    var that = this;
    
    wx.showModal({
      title: '提示',
      content: '每只赛鸽只能有一次报名机会，请确认无误再提交',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定');
          // 接口请求开始
          // ----------
          // ------接口请求成功后执行

          wx.navigateTo({
            url: '../success/success'
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })

  },
  // 清除所有勾选
  clearAllCheck: function () {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定清除所有选中部分吗？',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定');
          var listData = that.data.listData;
          for(var i = 0; i < listData.length; i++) {
            listData[i].a1c = false;
            listData[i].a2c = false;
            listData[i].a3c = false;
            listData[i].b1c = false;
            listData[i].b2c = false;
            listData[i].c1c = false;
            listData[i].c2c = false;
            listData[i].d1c = false;
            listData[i].d2c = false;
            listData[i].d3c = false;
            listData[i].d4c = false;
            if (listData[i].a1 == "" && listData[i].a2 == "" && listData[i].a3 == "" && listData[i].b1 == "" && listData[i].b2 == "" && listData[i].c1 == "" && listData[i].c2 == "" && listData[i].d1 == "" && listData[i].d2 == "" && listData[i].d3 == "" && listData[i].d4 == "") {
              listData[i].allMoney = 0;
            }
          }
          that.setData({
            listData: listData,
            a1Total: 0,
            a2Total: 0,
            a3Total: 0,
            b1Total: 0,
            b2Total: 0,
            c1Total: 0,
            c2Total: 0,
            d1Total: 0,
            d2Total: 0,
            d3Total: 0,
            d4Total: 0,
          })
          that.getAllNum();
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  a1Change: function (e) {
    // var that = this;
    // this.totalCheck(that, e, "a1", "a1c", "a1Total");
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].a1c) {
          listData[i].a1c = true;
          listData[i].allMoney += 200; 
        } else {
          listData[i].a1c = false;
          listData[i].allMoney -= 200;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var a1cArr = [];
    var a1Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].a1 == "√") {
        a1cArr.push(listData[j].a1);
      }
      if (listData[j].a1c) {
        a1cArr.push(listData[j].a1c);
      }
      allTotal += parseFloat(listData[j].allMoney)
      
    }
    console.log("arr", a1cArr);
    a1Total = a1cArr.length * 200;
    that.setData({
      a1Total: a1Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  a2Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].a2c) {
          listData[i].a2c = true;
          listData[i].allMoney += 500;
        } else {
          listData[i].a2c = false;
          listData[i].allMoney -= 500;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var a2cArr = [];
    var a2Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].a2 == "√") {
        a2cArr.push(listData[j].a2);
      }
      if (listData[j].a2c) {
        a2cArr.push(listData[j].a2c);
      }
      allTotal += parseFloat(listData[j].allMoney)
    }
    console.log("arr", a2cArr);
    a2Total = a2cArr.length * 500;
    that.setData({
      a2Total: a2Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  a3Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].a3c) {
          listData[i].a3c = true;
          listData[i].allMoney += 1000;
        } else {
          listData[i].a3c = false;
          listData[i].allMoney -= 1000;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var a3cArr = [];
    var a3Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].a3 == "√") {
        a3cArr.push(listData[j].a3);
      }
      if (listData[j].a3c) {
        a3cArr.push(listData[j].a3c);
      }
allTotal += parseFloat(listData[j].allMoney)
    }
    console.log("arr", a3cArr);
    a3Total = a3cArr.length * 1000;
    that.setData({
      a3Total: a3Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  b1Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].b1c) {
          listData[i].b1c = true;
          listData[i].allMoney += 500;
        } else {
          listData[i].b1c = false;
          listData[i].allMoney -= 500;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var b1cArr = [];
    var b1Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].b1 == "√") {
        b1cArr.push(listData[j].b1);
      }
      if (listData[j].b1c) {
        b1cArr.push(listData[j].b1c);
      }
      allTotal += parseFloat(listData[j].allMoney)
    }
    console.log("arr", b1cArr);
    b1Total = b1cArr.length * 500;
    that.setData({
      b1Total: b1Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  b2Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].b2c) {
          listData[i].b2c = true;
          listData[i].allMoney += 1000;
        } else {
          listData[i].b2c = false;
          listData[i].allMoney -= 1000;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var b2cArr = [];
    var b2Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].b2 == "√") {
        b2cArr.push(listData[j].b2);
      }
      if (listData[j].b2c) {
        b2cArr.push(listData[j].b2c);
      }
      allTotal += parseFloat(listData[j].allMoney)

    }
    console.log("arr", b2cArr);
    b2Total = b2cArr.length * 1000;
    that.setData({
      b2Total: b2Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  c1Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].c1c) {
          listData[i].c1c = true;
          listData[i].allMoney += 100;
        } else {
          listData[i].c1c = false;
          listData[i].allMoney -= 100;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var c1cArr = [];
    var c1Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].c1 == "√") {
        c1cArr.push(listData[j].c1);
      }
      if (listData[j].c1c) {
        c1cArr.push(listData[j].c1c);
      }
      allTotal += parseFloat(listData[j].allMoney)

    }
    console.log("arr", c1cArr);
    c1Total = c1cArr.length * 100;
    that.setData({
      c1Total: c1Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  c2Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].c2c) {
          listData[i].c2c = true;
          listData[i].allMoney += 300;
        } else {
          listData[i].c2c = false;
          listData[i].allMoney -= 300;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var c2cArr = [];
    var c2Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].c2 == "√") {
        c2cArr.push(listData[j].c2);
      }
      if (listData[j].c2c) {
        c2cArr.push(listData[j].c2c);
      }
      allTotal += parseFloat(listData[j].allMoney);
    }
    console.log("arr", c2cArr);
    c2Total = c2cArr.length * 300;
    that.setData({
      c2Total: c2Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  d1Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].d1c) {
          listData[i].d1c = true;
          listData[i].allMoney += 200;
        } else {
          listData[i].d1c = false;
          listData[i].allMoney -= 200;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var d1cArr = [];
    var d1Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].d1 == "√") {
        d1cArr.push(listData[j].d1);
      }
      if (listData[j].d1c) {
        d1cArr.push(listData[j].d1c);
      }
      allTotal += parseFloat(listData[j].allMoney)

    }
    console.log("arr", d1cArr);
    d1Total = d1cArr.length * 200;
    that.setData({
      d1Total: d1Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  d2Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].d2c) {
          listData[i].d2c = true;
          listData[i].allMoney += 300;
        } else {
          listData[i].d2c = false;
          listData[i].allMoney -= 300;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var d2cArr = [];
    var d2Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].d2 == "√") {
        d2cArr.push(listData[j].d2);
      }
      if (listData[j].d2c) {
        d2cArr.push(listData[j].d2c);
      }
      allTotal += parseFloat(listData[j].allMoney);
    }
    console.log("arr", d2cArr);
    d2Total = d2cArr.length * 300;
    that.setData({
      d2Total: d2Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  d3Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].d3c) {
          listData[i].d3c = true;
          listData[i].allMoney += 500;
        } else {
          listData[i].d3c = false;
          listData[i].allMoney -= 500;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var d3cArr = [];
    var d3Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].d3 == "√") {
        d3cArr.push(listData[j].d3);
      }
      if (listData[j].d3c) {
        d3cArr.push(listData[j].d3c);
      }
      allTotal += parseFloat(listData[j].allMoney)
    }
    console.log("arr", d3cArr);
    d3Total = d3cArr.length * 500;
    that.setData({
      d3Total: d3Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  d4Change: function (e) {
    var that = this;
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].d4c) {
          listData[i].d4c = true;
          listData[i].allMoney += 1000;
        } else {
          listData[i].d4c = false;
          listData[i].allMoney -= 1000;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var d4cArr = [];
    var d4Total = 0;
    var allTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j].d4 == "√") {
        d4cArr.push(listData[j].d4);
      }
      if (listData[j].d4c) {
        d4cArr.push(listData[j].d4c);
      }
      allTotal += parseFloat(listData[j].allMoney)
    }
    console.log("arr", d4cArr);
    d4Total = d4cArr.length * 1000;
    that.setData({
      d4Total: d4Total
    })
    allTotal += that.data.a1Total + that.data.a2Total + that.data.a3Total + that.data.b1Total + that.data.b2Total + that.data.c1Total + that.data.c2Total + that.data.d1Total + that.data.d2Total + that.data.d3Total + that.data.d4Total;
    that.setData({
      allTotal: allTotal
    })
  },
  // this, a1, a1c, a1Total
  totalCheck: function (that, e, name, check, total) {
    console.log("data-index:", e);
    var index = e.currentTarget.dataset.index;
    var listData = that.data.listData;
    for (var i = 0; i < listData.length; i++) {
      if (i == index) {
        // listData[i].a1 = "√";
        if (!listData[i].check) {
          listData[i].check = true;
        } else {
          listData[i].check = false;
        }
      }
    }
    that.setData({
      listData: listData
    })
    var nameArr = [];
    var nameTotal = 0;
    for (var j = 0; j < listData.length; j++) {
      if (listData[j][name] == "√") {
        nameArr.push(listData[j][name]);
      }
      if (listData[j][check]) {
        nameArr.push(listData[j][check]);
      }

    }
    console.log("arr", nameArr);
    nameTotal = nameArr.length * 200;
    return nameTotal;
    // that.setData({
    //   total: nameTotal
    // })
  }


})
