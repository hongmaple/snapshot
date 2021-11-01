var app = getApp();//获取appid

var member = wx.getStorageSync('member');
var http_url = app.globalData.mobile_api + "&param=function&name=dr_my_comment&p1=news&p2=" + member.uid + "&p3=5";

Page({

  /**
   * 页面的初始数据
   */
  data: {

    listData: [],
    hidden: true,
    page: 1,
    hasMore: "false"
  },

  onLoad: function (options) {

    var self = this;
    wx.request({
      url: http_url,
      method: 'GET',
      success: function (res) {
        if (res.data.code == 1) {
          self.setData({
            listData: res.data.result,
            page: 1
          });
        } else {
          console.log(res.data.msg);
          wx.showModal({
            showCancel: false,
            content: res.data.msg
          })
        }

      }

    })
  },
  onReachBottom: function () {

    this.setData({ hidden: false });
    var self = this;
    var pageid = self.data.page + 1;

    wx.request({
      url: http_url + pageid,
      method: 'GET',

      success: function (res) {

        if (res.data.code == 1) {
          if (res.data.result.length == 0) {
            self.setData({
              hasMore: "true",
              hidden: false
            });
            setTimeout(function () {
              self.setData({
                hasMore: "false",
                hidden: true
              });
            }, 900)
          } else {
            self.setData({
              listData: res.data.result,
              hidden: true,
              page: pageid
            });
          }
        } else {
          console.log(res.data.msg);
          wx.showModal({
            showCancel: false,
            content: res.data.msg
          })
        }

      }
    })
  }

})