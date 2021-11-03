var app = getApp();

var post_url = app.globalData.member_api + "&s=member&mod=news&c=home&m=add";

// fid=19是缩略图的字段id号
var member = wx.getStorageSync('member');
var upload_url = app.globalData.mobile_api + "&param=upload&ext=jpg&uid=" + member.uid;

var thumb_id = 0;// 储存缩略图id

Page({
  data: {
    postData: {
      title: "",
      content: ""
    },
    type: 1,
    items: [
      {value: '1', name: '上传图片', checked: true},
      {value: '2', name: '上传视频', checked: false}
    ]
  },
  onShow: function() {
    if (app.globalData.token == "") {
      // 未登录跳转登录界面
      wx.reLaunch({ url: "../login/login" });
    }
  },
   formSubmit:function(e){
        console.log(e.detail.value)
        this.setData({postData:e.detail.value});

        var self = this;

        var postParams = "is_ajax=1"
          + "&data[title]=" + e.detail.value.title
          + "&data[content]=" + e.detail.value.content
          + "&data[thumb]=" + thumb_id
          + "&catid=18";// 暂时固定栏目18
        wx.request({
          url: post_url,
          data: postParams,
          method: 'post',
          header: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          success: function (res) {
            console.log(res.data);
            if (res.data.code) {
              wx.showToast({
                title: res.data.msg,
                icon: 'success'
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

    radioChange(e) {
      console.log('radio发生change事件，携带value值为：', e.detail.value)
  
      const items = this.data.items
      for (let i = 0, len = items.length; i < len; ++i) {
        items[i].checked = items[i].value === e.detail.value
      }
  
      this.setData({
        items,
        type: e.detail.value
      })
    },
    add: function () {

        
    },


    uploadFile:function(){
      var that = this;
      var upload_url = app.globalData.mobile_api + "/common/upload";
      wx.chooseImage({
          count: 1, // 最多可以选择的图片张数，默认9
          sizeType: ['compressed'], // original 原图，compressed 压缩图，默认二者都有
          sourceType: ['album', 'camera'], // album 从相册选图，camera 使用相机，默认二者都有
          success: function(res2){
              wx.uploadFile({
                  url: upload_url,
                  filePath:res2.tempFilePaths[0],
                  name:'file',
                  formData: {
                    is_ajax:1
                  },
                  header: {
                      'Content-Type': 'application/x-www-form-urlencoded',
                  },
                  success: function(res){
                    var ret = JSON.parse(res.data);
                    console.log(ret);
                      if (ret.status==200) {
                          wx.showModal({
                              showCancel: false,
                              content: "上传成功"
                          })
                          console.log(ret.fileName);
                          that.setData({
                              ['member.avatarImage']: ret.fileName,
                              avatar: ret.url
                          });
                      } else {
                          wx.showModal({
                              showCancel: false,
                              content: ret.message
                          })
                      }

                  }
              })
          },
          fail: function() {
              // fail
          },
          complete: function() {
              // complete
          }
      })
    },


})