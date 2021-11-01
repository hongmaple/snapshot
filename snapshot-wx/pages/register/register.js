
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userName: "",
    userPwd: "",
  },



  getUserName: function (e) {
    this.setData({
      userName: e.detail.value
    });

  },
  getUserPwd: function (e) {
    this.setData({ userPwd: e.detail.value });
  },
  //注册
  register:function(){
      app.showModel();
    var self = this;
    wx.request({
      url: app.globalData.mobile_api+"/user/register",
      method: 'post',
      data: {
        phone: this.data.userName,
        password: this.data.userPwd
      },
      header: {
        // 'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Type': 'application/json'
      },
      success: function (res) {
        if (res.data.code == 200) {
          // 跳转到登录页面
          wx.showToast({
            title: "注册成功",
            icon: 'success',
            success: function () {
              wx.reLaunch({ url: "../login/login" });
            },
            duration: 2000
          })
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
    //登录页面
    login: function () {
      wx.navigateTo({
        url: '../login/login',
      })
    }
})