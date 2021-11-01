var WxParse = require('../../wxParse/wxParse.js');

var app = getApp();

var http_url = app.globalData.http_api + "&param=list action=content module=news id=";

var member_url = app.globalData.member_api;
 

Page({
  data:{
      id:'',
      content:'',
      supports: 0,
      upsImg:"../../icons/ups.png",
      collectImg:"../../icons/collect.png",
  },
  onLoad:function(options){

      app.showModel();
      var self=this;
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
      })
  },
   getCommentList:function(){//评论跳转

      wx.navigateTo({
        url: '../news/comment?id='+this.data.content.id
     })
   },


   collect: function (){//收藏
     var self =this;
     wx.request({
       url: member_url + '&s=news&c=api&m=favorite&id=' + self.data.id,

       data: {
         is_ajax: 1,
       },
       header: {
         'content-type': 'application/x-www-form-urlencoded'
       },
       dataType: 'text',
       method: 'POST',
       success: function (sc) {
         if (sc == 1) {
           wx.showModal({
             showCancel: false,
             content: "没有登录，不能收藏"
           })
         } else if (sc == 2) {
           wx.showModal({
             showCancel: false,
             content: "文档不存在，无法收藏"
           })
         } else {
           wx.showToast({
             icon: 'success',
             title: "收藏成功",
             duration: 2000
           });
         }
       }
     });

   }


})