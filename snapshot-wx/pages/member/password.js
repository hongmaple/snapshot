
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
    var self = this; 
    var postParams = {
      password: e.detail.value.password1,
      password1: e.detail.value.password2,
      password2: e.detail.value.password3,
    } 
    if(postParams.password1!==postParams.password2) {
      wx.hideLoading();
      wx.showModal({
        showCancel: false,
        content: '新密码不一致'
      })
      return;
    }
    wx.request({//提交
      url: app.globalData.member_api + "/user/updatePwdReq",
      data: postParams,
      method: 'put',
      header: {
        'Content-Type': 'application/json',
        "Authorization": app.globalData.token
      },
      success: function (res) {
        wx.hideLoading();
        if (res.data.status == 200) {
          // 修改成功，重新登录
          wx.setStorageSync('member', "");
            wx.showModal({
                showCancel: false,
                success: function(res) {
                    if (res.confirm) {
                        wx.navigateTo({ url: "../login/login" });
                    }
                },
                content: res.data.message
            })
        }
        else {
            wx.hideLoading();
            wx.showModal({
                showCancel: false,
                content: res.data.message
            })
        }
      }
    })

  },

  
})