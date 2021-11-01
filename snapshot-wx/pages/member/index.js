// pages/member/index.js

var app = getApp();
var token = wx.getStorageSync('token');
console.log(token);

var avatar = '../../icons/user.png';
// if (member['avatarImage']) {
//   avatar = member['avatarImage'];
// }

Page({

  /**
   * 页面的初始数据
   */
  data: {
    token: token,
    avatar: avatar,
    columnList: [
      {
        "url": "../member/my",
        "iconPath": "../../icons/star.png",
        "columnName": "账号信息"
      },

      {
        "url": "../member/account",
        "iconPath": "../../icons/star.png",
        "columnName": "修改资料"
      },
      {
        "url": "../member/password",
        "iconPath": "../../icons/star.png",
        "columnName": "修改密码"
      },
      {
        "url": "../member/comment",
        "iconPath": "../../icons/star.png",
        "columnName": "文章评论"
      },
      {
        "url": "../member/favorite",
        "iconPath": "../../icons/star.png",
        "columnName": "文章收藏"
      }
    ],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(token);
    if (token === "") {
      // 未登录跳转登录界面
      wx.reLaunch({ url: "../login/login" });
    }
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