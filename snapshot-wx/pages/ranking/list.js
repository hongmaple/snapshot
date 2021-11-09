var app=getApp();//获取app

var http_url = app.globalData.http_api + "&function=dr_my_list&param=list action=module module=news page=1 pagesize=10";

Page({

    /**
     * 页面的初始数据
     */
    data: {

        listData:[],
        hidden: true,
        page: 1,
        hasMore:"false",
        baseUrl: app.globalData.http_api
    },

    onLoad:function(options){
        wx.showLoading();
        var token = app.globalData.token;
        var self=this;
        wx.request({
            url: app.globalData.http_api +'/statistics/ranking',
            method: 'post',
            header: {
                // 'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Type': 'application/json',
                "Authorization": token
            },
            data: {
                pageNum: 1,
                pageSize: 50
            },
            success: function(res){
                wx.hideLoading();
                console.log(res.data);
                if (res.data.status == 200) {
                    self.setData({
                        listData:res.data.data.list,
                        page: res.data.pages
                    });
                } else {
                    console.log(res.data.message);
                    wx.showModal({
                      showCancel: false,
                      content: res.data.message
                    })
                }

            }

        })
    }

})