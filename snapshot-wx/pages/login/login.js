
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // userName: 18976618156,
    // userPwd: 123456,
    userName: null,
    userPwd: null,
  },



  getUserName: function (e) {
    this.setData({
      userName: e.detail.value
    });

  },
  getUserPwd: function (e) {
    this.setData({ userPwd: e.detail.value });
  },
  //登录
  login:function(){
      app.showModel();
    var self = this;
    wx.request({
      url: app.globalData.mobile_api+"/user/login",
      method: 'post',
      data: {
        username: this.data.userName,
        password: this.data.userPwd
      },
      header: {
        // 'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Type': 'application/json'
      },
      success: function (res) {
        if (res.data.status == 200) {
          // 登录成功储存会员信息
          wx.clearStorageSync();
          wx.setStorageSync('token', res.data.data);
          // 跳转到会员页面
          wx.showToast({
            title: "登录成功",
            icon: 'success',
            success: function () {
              wx.reLaunch({ url: "../member/index" });
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
      //注册页面
    register: function () {
    wx.navigateTo({
      url: '../register/register',
    })
  },
})