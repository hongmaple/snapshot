
var md5 = require("utils/md5.js");

var member = wx.getStorageSync('member');

App({
 
  showModel:function(){
     wx.showToast({
      title: '正在加载....',
      icon: 'loading',
      duration: 5000
    });
  },

  globalData: {
    data: {},
    // 通用查询接口
    http_api: "http://127.0.0.1:9501",
    // 移动端接口  + md5.hex_md5("NGE3NJAWYWQXOGRHYTG3MZBJZJDHZDDJZDC5MWQZZJNKYTHMMTJLZJRIMZRIZJI0"),
    mobile_api: "http://127.0.0.1:9501",
    // 登录认证接口
    member_api: "http://127.0.0.1:9501",
    token: ''
  }
})