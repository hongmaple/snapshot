// pages/member/index.js

var app = getApp();

var avatar = '../../icons/user.png';
// if (member['avatarImage']) {
//   avatar = member['avatarImage'];
// }

Page({

  /**
   * 页面的初始数据
   */
  data: {
    token: null,
    avatar: avatar,
    user: {},
    columnList: [
      {
        "url": "../member/my",
        "iconPath": "../../icons/userinfo.png",
        "columnName": "账号信息"
      },

      {
        "url": "../member/account",
        "iconPath": "../../icons/updata-user.png",
        "columnName": "修改资料"
      },
      {
        "url": "../member/password",
        "iconPath": "../../icons/updata-pwd.png",
        "columnName": "修改密码"
      },
      // {
      //   "url": "../member/comment",
      //   "iconPath": "../../icons/star.png",
      //   "columnName": "文章评论"
      // },
      // {
      //   "url": "../member/favorite",
      //   "iconPath": "../../icons/star.png",
      //   "columnName": "文章收藏"
      // }
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var token = wx.getStorageSync('token');
    var ths = this;
    // if (token == "") {
    //   // 未登录跳转登录界面
    //   wx.reLaunch({ url: "../login/login" });
    // }
    
    ths.setData({
      token: token
    });
    app.globalData.token = token;
  },
  onShow: function () {
    var ths = this;
    //获取当前用户信息
    wx.request({
      url: app.globalData.mobile_api+"/user/info",
      method: 'post',
      header: {
        // 'Content-Type': 'application/x-www-form-urlencoded',
        'Content-Type': 'application/json',
        "Authorization": app.globalData.token
      },
      success: function (res) {
        if (res.data.status == 200) {
          wx.clearStorageSync();
          wx.setStorageSync('member', res.data.data);
          ths.setData({
            user: res.data.data,
            avatar: app.globalData.mobile_api+res.data.data.avatarImage
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
  downLogin: function () {
    wx.showModal({
      title: "退出",
      content: "是否退出？",
      success: function (res) {
        if (res.confirm) {
          wx.clearStorageSync('member');
          
          wx.reLaunch({ url: "../login/login" });
        }
      }
    })
  },
    userInfo:function(){
      if (wx.getStorageSync('member')) {
        wx.navigateTo({ url: "../member/my" });
      } else {
        wx.reLaunch({ url: "../login/login" });
      }
        

    },

})