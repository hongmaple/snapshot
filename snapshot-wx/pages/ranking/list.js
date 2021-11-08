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
        app.showModel();
        var token = app.globalData.token;
        var self=this;
        wx.request({
            url: app.globalData.http_api +'/work/queryWorkListByWorkType',
            method: 'post',
            header: {
                // 'Content-Type': 'application/x-www-form-urlencoded',
                'Content-Type': 'application/json',
                "Authorization": token
            },
            data: {
                title: null,
                creatorId: null,
                creatorType: null,
                status: null,
                workType: 1,
                pageNum: 1,
                pageSize: 10
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
    },
    onReachBottom:function(){

        app.showModel();
        this.setData({hidden:false});
        var self=this;
        var pageid = self.data.page + 1;

        wx.request({
          url: http_url + "&page=" + pageid,
            method: 'GET',
            success: function(res){

                wx.hideLoading();
                if (res.data.code == 1) {
                  if (res.data.return.length==0){
                        self.setData({
                            hasMore:"true",
                            hidden:false
                        });
                        setTimeout(function(){
                            self.setData({
                                hasMore:"false",
                                hidden:true
                            });
                        },900)
                    }else{
                        self.setData({
                          listData: res.data.return,
                            hidden:true,
                            page:pageid
                        });
                    }
                } else {
                    console.log(res.data.msg);
                    wx.showModal({
                      showCancel: false,
                      content: res.data.msg
                    })
                }

            }
        })
    }

})