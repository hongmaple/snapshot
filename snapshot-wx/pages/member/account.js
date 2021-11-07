var app=getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
      avatar: null,
      member: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var member = wx.getStorageSync('member');
    //console.log(member);
    if (member == "") {
      // 未登录跳转登录界面
      wx.reLaunch({ url: "../login/login" });
    }
    var ths = this;
    ths.setData({
        member: member,
        avatar: app.globalData.mobile_api+member.avatarImage
    })
  },

    /**
     * 表单提交
     */
    formBindsubmit: function (e) {
        var token = app.globalData.token;
        console.log(token);
        var self = this;
        var member = self.data.member;
        var postParams = {
            id: member.id,
            username: e.detail.value.username,
            phone: e.detail.value.phone,
            avatarImage: member.avatarImage,
            sex: e.detail.value.sex,
            age: e.detail.value.age
        };
        console.log(postParams);
        wx.request({//提交
            url: app.globalData.member_api + "/user",
            data: postParams,
            method: 'put',
            header: {
                // 'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Type': 'application/json',
                "Authorization": token
            },
            success: function (res) {
                if (res.data.status == 200) {
                    // 更新会员信息
                    wx.removeStorageSync('member');
                    self.queryUserInfo();
                    // 修改成功
                    wx.showToast({
                        icon: 'success',
                        duration: 2000
                    });
                    wx.reLaunch({ url: "../login/login" });
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
    //获取当前用户信息
    queryUserInfo: function() {
        var token = app.globalData.token;
        var ths = this;
        //获取当前用户信息
        wx.request({
          url: app.globalData.mobile_api+"/user/info",
          method: 'post',
          header: {
            // 'Content-Type': 'application/x-www-form-urlencoded',
            'Content-Type': 'application/json',
            "Authorization": token
          },
          success: function (res) {
            if (res.data.status == 200) {
              wx.clearStorageSync();
              wx.setStorageSync('member', res.data.data);
              console.log(res.data.data)
              ths.setData({
                member: res.data.data,
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
    //上传文件
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