var app = getApp();
var http_url = app.globalData.http_api;


Page({

  /**
   * 页面的初始数据
   */
  data: {
    sdData: [],
    fabuData: [],
    banners: [
      '../../images/b2.jpg',
      '../../images/b1.jpg',
    ],
    icons: [
      {
        icon: '../../images/i2.jpg',
        name: '爆光台',
        url: '../baoguantai/list',
      },
      {
        icon: '../../images/i4.jpg',
        name: '文明点赞',
        url: '../cthumb/list',
      },
      {
        icon: '../../images/i3.jpg',
        name: '排行榜',
        url: '../ranking/list',
      }
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    app.showModel();
    var self = this;
    wx.request({
      url: http_url,
      method: 'GET',
      success: function (res) {
        wx.hideLoading();
        if (res.data.code == 1) {
          self.setData({
            fabuData: res.data.return,
          });
        }

      }

    });
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
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
  
  }
})