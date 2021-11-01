var app=getApp();
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
    var member = wx.getStorageSync('member');
    //console.log(member);
    if (member == "") {
      // 未登录跳转登录界面
      wx.reLaunch({ url: "../login/login" });
    }
  },

    /**
     * 表单提交
     */
    formBindsubmit: function (e) {

        var self = this;
        var postParams = "is_ajax=1&"
            + "&data[name]=" + e.detail.value.name;
        wx.request({//提交
            url: app.globalData.member_api + "&s=member&c=account&m=index",
            data: postParams,
            method: 'post',
            header: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            success: function (res) {
                if (res.data.code == 1) {
                  // 储存会员信息
                  var member = wx.getStorageSync('member');
                    wx.removeStorageSync('member');
                    member.name = e.detail.value.name; // 改一个就变更一个字段
                    wx.setStorageSync('member', member);
                    // 修改成功
                    wx.showToast({
                        icon: 'success',
                        duration: 2000
                    });
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

})