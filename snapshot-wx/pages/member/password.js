
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    member: wx.getStorageSync('member'),
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (wx.getStorageSync('member') == "") {
      // 未登录跳转登录界面
      wx.reLaunch({ url: "../login/login" });
    }
  },

  /**
   * 表单提交
   */
  formBindsubmit: function (e) {
      app.showModel();

    var self = this; 
    var postParams = "is_ajax=1&"
      + "&password=" + e.detail.value.password1
      + "&password1=" + e.detail.value.password2 
      + "&password2=" + e.detail.value.password3;
    wx.request({//提交
      url: app.globalData.member_api + "&s=member&c=account&m=password",
      data: postParams,
      method: 'post',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      success: function (res) {
        console.log(res.data);
        wx.hideLoading();
        if (res.data.code == 1) {
          // 修改成功，重新登录
          wx.setStorageSync('member', "");
            wx.showModal({
                showCancel: false,
                success: function(res) {
                    if (res.confirm) {
                        wx.navigateTo({ url: "../login/login" });
                    }
                },
                content: res.data.msg
            })
        }
        else {
            wx.showModal({
                showCancel: false,
                content: res.data.msg
            })
        }
      }
    })

  },

  
})