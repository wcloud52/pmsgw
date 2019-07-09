// pages/group/group.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    memberNumber: '111',
    memberName: '张三',
    countTime: '10 ',// 倒计时
    countTimeFmt: '',
    selectArray: [{
      "id": "1",
      "text": "a"
    }, {
      "id": "2",
      "text": "b"
      }, {
        "id": "3",
        "text": "c"
      }, {
        "id": "4",
        "text": "d"
      }],
    list: [
      {
        id: 1,
        number: '1012',
        small: '',
        big: 'A'
      },
      {
        id: 2,
        number: '2012',
        small: '',
        big: 'A'
      },
      {
        id: 3,
        number: '3012',
        small: '',
        big: 'A'
      },
    ],
    

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that = this;
    that.count();
    that.loadListFmt();
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  // 倒计时
  count() {
    var that = this;
    var countTime = parseFloat(that.data.countTime);
    var timer = setInterval(function() {
      var h, m, s;
      if (countTime >= 0) {
        h = Math.floor(countTime / 60 / 60);
        m = Math.floor(countTime / 60 % 60);
        s = Math.floor(countTime % 60);
        h = h < 10 ? ("0" + h) : h;
        m = m < 10 ? ("0" + m) : m;
        s = s < 10 ? ("0" + s) : s;
        var countTimeFmt = h + "时" + m + "分" + s + "秒";
        that.setData({
          countTimeFmt: countTimeFmt,
          countTime: countTime
        })
        countTime--;
      } else {
        console.log('已截止');
        clearInterval(timer);
        // 这里写提交
        // ....
        return;
      }

    }, 1000)

  },
  //加载列表回调函数 在success中给列表加select属性
  loadListFmt() {
    var that = this;
    // 。。。加载列表success中内容
    var list = that.data.list;
    for (var i = 0; i < list.length; i++) {
      list[i].selectShow = false;
    }
    that.setData({
      list: list
    })
  },
  // 选择框的显示隐藏
  selectShow(e) {
    console.log(e.detail.selectShow);
    var that = this;
    var list = that.data.list;
    var id = e.currentTarget.dataset.id;
    for (var i = 0; i < list.length; i++) {
      if (id == list[i].id) {
        list[i].selectShow = true;
      } else {
        list[i].selectShow = false;
      }
    }
    that.setData({
      list: list,
    })
  },
  // 排序
  getGroup(e) {
    console.log(e)
    var that = this;
    var list = that.data.list;
    var id = e.currentTarget.dataset.id;
    var value = e.detail.nowText;
    console.log("id", id);
    console.log("值", value);
    for (var i = 0; i < list.length; i++) {
      if (id == list[i].id) {
        list[i].small = value;
        list[i].selectShow = false;
      }
    }
    var newlist = list.sort((a, b) => a.small.charCodeAt(0) - b.small.charCodeAt(0));
    that.setData({
      list: newlist,
    })
    console.log(newlist);
  },
  //提交
  saveForm() {
    var that = this;

    wx.showModal({
      title: '提示',
      content: '确认提交',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定');
          // 接口请求开始
          // ----------
          // ------接口请求成功后执行

          wx.navigateTo({
            url: 'success'
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  // 清除
  clearAllSelect() {
    var that = this;
    wx.showModal({
      title: '提示',
      content: '确定清除所有选中部分吗？',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定');
          var list = that.data.list;
          for (var i = 0; i < list.length; i++) {
            list[i].small = '';
          }
          that.setData({
            list: list
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  }
})