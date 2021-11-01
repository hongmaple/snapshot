var app = getApp();

var http_url = app.globalData.http_api + "&param=" + encodeURI("list action=content module=news id=");

var member_url = app.globalData.member_api +"&s=news&c=comment&m=add&id=";
var WxParse = require('../../wxParse/wxParse.js');

var comment_url = app.globalData.http_api + "&function=dr_my_list&param=" + encodeURI("list action=comment module=news cache=0 page=1 pagesize=10 cid=");

Page({
  data: {
    id: '',
    content: '',
    supports: 0,
    upsImg: "../../icons/ups.png",
    collectImg: "../../icons/collect.png",
    listData: [],
    hidden: true,
    page: 1,
    hasMore: "false"
  },
  onLoad: function (options) {

    app.showModel();
    var self = this;
    wx.request({
      url: http_url + options.id,
      header: {
        'content-type': 'application/json'
      },
      dataType: 'json',
      method: 'GET',
      success: function (res) {


        if (res.data.code == 1) {

          for (var i in res.data.return) {//不使用过滤
            var c = res.data.return[i];
          }

          // 格式化文章内容
          var article = c.content;

          WxParse.wxParse('data', 'html', article, self);

          self.setData({
            content: c,
            id: options.id
          })
          wx.hideToast();
        } else {
          wx.showModal({
            showCancel: false,
            content: res.data.msg
          })
        }



      }
    });
 
    wx.request({
      url: comment_url + options.id,
      header: {
        'content-type': 'application/json'
      },
      dataType: 'json',
      method: 'GET',
      success: function (res) {

        wx.hideToast();

        if (res.data.code == 1) {
          
          self.setData({
            listData: res.data.return,
            id: options.id,
            page: 1
          })
        } else {
          wx.showModal({
            showCancel: false,
            content: res.data.msg
          })
        }



      }
    })
  },



  getText: function (e) {
    this.setData({ text: e.detail.value });

  },

  save: function () {
    //发布评论
      
    var self = this;
    var text = self.data.text;//评论内容
    
    wx.request({
      url: member_url  + self.data.id,
      data: {
        content: text,
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      dataType: 'json',
      method: 'POST',

      success: function (res) {
        console.log(res.data);
        if (res.data.code == 1) {
          wx.showToast({
            title: res.data.msg,
            icon: 'success',
            duration: 10000
          });
          setTimeout(function () {
            wx.hideToast();
            wx.redirectTo({
              url: 'comment?id=' + self.data.id
            })
          }, 700)


        }
        else {
          wx.showToast({
            title: res.data.msg,
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
    var pageid = self.data.page + 1;

    wx.request({
      url: comment_url + self.data.id,
      method: 'GET',
      data: {
        page: pageid
      },

      success: function (res) {

        if (res.data.code == 1) {
          if (res.data.data.list.length == 0) {
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
              listData: res.data.return,
              hidden: true,
              page: pageid
            });
          }
        } else {
          wx.showModal({
            showCancel: false,
            content: res.data.msg
          })
        }

      }
    })
  },

})