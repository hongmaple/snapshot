var app = getApp();
var http_url = app.globalData.http_api;


Page({

  /**
   * 页面的初始数据
   */
  data: {
    sdData: [],
    fabuData: [],
    banners: [],
    icons: [
      {
        icon: '../../images/i2.jpg',
        name: '曝光台',
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
    baseUrl: app.globalData.member_api
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.showLoading();
    var self = this;
    var fabuData = [
      {
        id: 1,
        thumb: 'https://img2.baidu.com/it/u=3164222016,4130425457&fm=253&fmt=auto&app=120&f=JPEG?w=750&h=500',
        title: '强寒潮来袭，是在“啪啪打脸”全球变暖吗？',
        hits: 5,
        comments: 100
      },
      {
        id: 2,
        thumb: 'https://img2.baidu.com/it/u=2269864924,3491850853&fm=26&fmt=auto',
        title: '“湘潭大学和湖南师大，到底谁更厉害？数据告诉你一切',
        hits: 100,
        comments: 100
      },
      {
        id: 3,
        thumb: 'https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F1109%2F9addd7b1j00r2a78b001ac000go0085m.jpg&thumbnail=650x2147483647&quality=80&type=jpg',
        title: '重庆商家推出火锅奶茶',
        hits: 100000,
        comments: 1000
      },
    ]
    self.setData({
      fabuData: fabuData,
    });
    
    var postParams = {
      "isAsc": null,
      "orderBy": null,
      "orderByColumn": null,
      "pageNum": 1,
      "pageSize": 1000
    };
  wx.request({//提交
      url: app.globalData.member_api + "/picture/query/1",
      data: postParams,
      method: 'post',
      header: {
          // 'Content-Type': 'application/x-www-form-urlencoded',
          'Content-Type': 'application/json'
      },
      success: function (res) {
          wx.hideLoading()
          if (res.data.status == 200) {
            console.log(res.data.data.list)
            self.setData({
              banners: res.data.data.list,
            });
          }
          else {
              wx.showModal({
                  showCancel: false,
                  content: res.data.message
              })
          }
      }
  })
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