var app = getApp();

// var http_url = app.globalData.http_api + "&param=" + encodeURI("list action=content module=news id=");

var WxParse = require('../../wxParse/wxParse.js');

Page({
  data: {
    id: '',
    content: '',
    supports: 0,
    upsImg: "../../icons/ups.png",
    collectImg: "../../icons/collect.png",
    listData: [],
    hidden: true,
    currentPage: 1,
    pageSize: 10,
    pages: 0,
    hasMore: "false",
    baseUrl: app.globalData.http_api
  },
  onLoad: function (options) {
    console.log(options.id);
    wx.showLoading({
      title: '正在加载数据',
    });
    var self = this;
    self.setData({
      id: options.id
    })
      wx.request({
        url: app.globalData.http_api +'/work/'+options.id,
        method: 'get',
        header: {
            // 'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Type': 'application/json'
        },
        success: function(res){
            wx.hideLoading();
            console.log(res.data);
            if (res.data.status == 200) {
              self.setData({
                content: res.data.data
              })
            } else {
                console.log(res.data.message);
                wx.showModal({
                  showCancel: false,
                  content: res.data.message
                })
            }

        }

      });
      self.queryComment(self.data.currentPage,self.data.pageSize);
  },
  queryComment: function(currentPage,pageSize) {
    var self = this;
    wx.request({
      url: app.globalData.http_api +'/evaluation/list',
      method: 'post',
      header: {
          // 'Content-Type': 'application/x-www-form-urlencoded',
          'Content-Type': 'application/json'
      },
      data: {
          pageNum: currentPage,
          pageSize: pageSize,
          workId: self.data.id
      },
      success: function(res){
          wx.hideLoading();
          if (res.data.status == 200) {
              self.setData({
                listData: res.data.data.list,
                currentPage: currentPage,
                pageSize: pageSize,
                pages: res.data.data.pages
              })
          } else {
              console.log(res.data.message);
              wx.showModal({
                showCancel: false,
                content: res.data.message
              })
          }

      }

  });
  },
  getText: function (e) {
    this.setData({ text: e.detail.value });
  },

  save: function () {
    //发布评论
      
    var self = this;
    var text = self.data.text;//评论内容
    var token = app.globalData.token;
    if (token == "") {
      // 未登录跳转登录界面
         wx.showToast({
            title: '请登录后再操作',
            icon: 'success',
            duration: 10000
          });
          setTimeout(function () {
            wx.hideToast();
            wx.reLaunch({ url: "../login/login" });
          }, 700)
    }
    if(text=="") {
      wx.showToast({
        title: "评论不能为空",
        icon: 'success',
        duration: 10000
      });
    }
    wx.request({
      url: app.globalData.member_api+'/evaluation',
      data: {
        content: text,
        workId: self.data.id
      },
      header: {
        'content-type': 'application/json',
        "Authorization": token
      },
      dataType: 'json',
      method: 'POST',
      success: function (res) {
        console.log(res.data);
        if (res.data.status == 200) {
          wx.showToast({
            title: res.data.message,
            icon: 'success',
            duration: 10000
          });
          setTimeout(function () {
            wx.hideToast();
            self.queryComment(1,self.data.pageSize);
          }, 700)


        }
        else {
          wx.showToast({
            title: res.data.message,
            icon: 'loading',
            duration: 500
          });
        }

      }
    });





  },
  
onReachBottom: function () {

    this.setData({ hidden: false });
    var self = this;
    var currentPage = self.data.currentPage;
    if(self.data.pages<currentPage) {
      currentPage = currentPage+1;
    }

    self.queryComment(self.data.currentPage,self.data.pageSize);
  },

})