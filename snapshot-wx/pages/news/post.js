var app = getApp();

var post_url = app.globalData.member_api + "/work";

// fid=19是缩略图的字段id号
var member = wx.getStorageSync('member');
var upload_url = app.globalData.mobile_api + "&param=upload&ext=jpg&uid=" + member.uid;

var thumb_id = 0;// 储存缩略图id

Page({
  data: {
    postData: {
      title: "",
      url: "",
      type: 1,
      workType: 1
    },
    type: 1,
    items: [
      {value: '1', name: '上传图片', checked: true},
      {value: '2', name: '上传视频', checked: false}
    ],
    workTypes: [
      {value: '1', name: '文明点赞', checked: true},
      {value: '2', name: '曝光台', checked: false}
    ],
    pics: [],
    count: [1, 2, 3],
    isShow:true,
    imageCount: 3,
    vidoCount: 1,
  },
  onShow: function() {
    if (app.globalData.token == "") {
      // 未登录跳转登录界面
      wx.reLaunch({ url: "../login/login" });
    }
  },
   formSubmit:function(e){
        
        var that = this;
        var upload_url = app.globalData.mobile_api + "/common/upload";
        var urls = [];
        var pics = that.data.pics;
        var i=0
        for(i=0;i<pics.length;i++) {
          wx.uploadFile({
            url: upload_url,
            filePath:pics[i],
            name:'file',
            formData: {
              is_ajax:1
            },
            header: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            success: function(res){
              var ret = JSON.parse(res.data);
                if (ret.status==200) {
                  urls.push(ret.fileName);
                  var sads = pics.length;
                  if(i==sads) {
                    that.setData({
                      ['postData.title']:e.detail.value.title,
                      ['postData.type']: that.data.type,
                      ['postData.url']: JSON.stringify(urls)
                    });
                    that.addWork();
                  }
                } else {
                    wx.showModal({
                        showCancel: false,
                        content: ret.message
                    })
                }

            }
         });
        }
    },
    addWork() {
      wx.request({
        url: post_url,
        data: this.data.postData,
        method: 'post',
        header: {
          'Content-Type': 'application/json',
          "Authorization": app.globalData.token
        },
        success: function (res) {
          console.log(res.data);
          if (res.data.status==200) {
            wx.showToast({
              title: res.data.message,
              icon: 'success'
            })
            wx.reLaunch({ url: "../home/index" });
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
    radioChange(e) {
      console.log('radio发生change事件，携带value值为：', e.detail.value)
  
      const items = this.data.items
      for (let i = 0, len = items.length; i < len; ++i) {
        items[i].checked = items[i].value === e.detail.value
      }
  
      this.setData({
        items: items,
        type: e.detail.value,
        pics: [],
        imageCount: 3,
        vidoCount: 1,
      })
    },
    radioWorkTypeChange: function(e) {
      console.log('radio发生change事件，携带value值为：', e.detail.value)
  
      const workTypes = this.data.workTypes
      for (let i = 0, len = workTypes.length; i < len; ++i) {
        workTypes[i].checked = workTypes[i].value === e.detail.value
      }
  
      this.setData({
        workTypes: workTypes,
        ['postData.workType']: e.detail.value
      })
    },
    chooseImage: function (e) {
      var ths = this;
      console.log(e.currentTarget.dataset.sign)
      if (e.currentTarget.dataset.sign){//这里的结果为trule
        return;
      }else{
        var pics =ths.data.pics;
        wx.chooseImage({
          count :ths.imageCount-pics.length, //最多可以选择的图片张数;默认9
          sizeType: [ 'original', 'compressed'], //original 原图，compressed压缩图;默认二者都有
          sourceType: ['album', 'camera'], //album从相册选图. camera使用相机，默认二者都有
          success: function (res) {
            console.log(res)
            var tempFilessize = res.tempFiles[0].size; //获取图片的大小 ，单位B
            console.log("上传的图片大小:"+tempFilessize);
            var imgSrc = res.tempFilePaths ;
            // for(var i=0;i<pics.length;i++){
            //   if(pics[i]==''){
            //     pics.splice(i, 1);
            //   }
            // }
            var pics = ths.data.pics;
             for(var i=0;i<imgSrc.length;i++){
                if(imgSrc[i]==''){
                  //pics.splice(i, 1);
                }else {
                  pics.push(imgSrc[i])
                }
            }
            ths.setData({
              pics: pics,
              imageCount: ths.imageCount-pics.length
            })
          }
        });
      }
    },
    chooseVido: function (e) {
      var ths = this;
      console.log(e.currentTarget.dataset.sign)
      if (e.currentTarget.dataset.sign){//这里的结果为trule
        return;
      }else{
        var pics =ths.data.pics;
        wx.chooseVideo({
          sourceType: ['album', 'camera'], //album从相册选图. camera使用相机，默认二者都有
          camera: 'back',//默认拉起的是前置或者后置摄像头，默认back
          compressed: true,//是否压缩所选择的视频文件
          success: function (res) {
            console.log(res)
            let duration = res.duration //选定视频的时间长度
            let size = parseFloat(res.size/1024/1024).toFixed(1) //选定视频的数据量大小
            console.log("上传的图片大小:"+size);
            // let height = res.height //返回选定视频的高度
            // let width = res.width //返回选中视频的宽度
            var tempFilePath = res.tempFilePath ;
        
            var pics = ths.data.pics;
            pics.push(tempFilePath);
            ths.setData({
              pics: pics,
              vidoCount: 0
            })
          }
        });
      }
    },
    previewImage: function (e) {
      var current = e.target.dataset.src
      wx.previewImage({
        current: current,
        urls: this.data.pics
      })
    }
})