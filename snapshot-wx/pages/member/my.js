var app=getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
      avatar: null,
      member: null
  },
  onLoad: function() {
    var member = wx.getStorageSync('member')
    var ths = this;
    ths.setData({
      member: member,
      avatar: app.globalData.mobile_api+member.avatarImage
    });
  }
})