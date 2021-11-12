var app=getApp();//获取app

var http_url = app.globalData.http_api + "&function=dr_my_list&param=list action=module module=news page=1 pagesize=10";

Page({

    /**
     * 页面的初始数据
     */
    data: {

        listData:[],
        hidden: true,
        currentPage: 1,
        pageSize: 10,
        pages: 0,
        hasMore:"false",
        baseUrl: app.globalData.http_api,
        workType: 1,
        status: 'PASS'
    },

    onLoad:function(options){
        app.showModel();
        var self=this;
        self.setData({
            workType: options.workType,
            status: options.status
        });
        self.querWorkList(self.data.currentPage,self.data.pageSize,options.workType,options.status);
    },
    querWorkList: function(currentPage,pageSize,workType,status) {
        var self=this;
        var token = app.globalData.token;
        wx.request({
            url: app.globalData.http_api +'/work/my/list',
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
                status: status,
                workType: workType,
                pageNum: currentPage,
                pageSize: pageSize
            },
            success: function(res){
                wx.hideLoading();
                console.log(res.data);
                if (res.data.status == 200) {
                    var list = res.data.data.list;
                    if(currentPage==1) {
                        self.setData({
                            listData: list,
                            pages: res.data.pages,
                            currentPage: currentPage,
                            pageSize: pageSize
                        });
                    }else {
                        var originArticles = self.data.listData;
                        var newArticles = list.concat(originArticles);
                        self.setData({
                            listData: newArticles,
                            pages: res.data.pages,
                            currentPage: currentPage,
                            pageSize: pageSize
                        });
                    }
                   
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
    getCommentList:function(e){
        //评论跳转
        var id = e.currentTarget.dataset.id
        wx.navigateTo({
          url: '../workComment/comment?id='+id
       })
     },
     onReachBottom: function () {
        console.log("啊是擦上撒低级阿斯顿撒旦")
        this.setData({ hidden: false });
        var self = this;
        var currentPage = self.data.currentPage;
        if(self.data.pages<currentPage) {
          currentPage = currentPage+1;
          self.queryComment(self.data.currentPage,self.data.pageSize);
        }
        return;
      },
})