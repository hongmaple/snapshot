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
      fabuData: [
        {
          id: 1,
          content: "“好冷啊！”“雪真大！”立冬前后，一场大范围强寒潮席卷我国中东部地区，东北地区遭遇大暴雪。雨雪和大风，让人们感到异常寒冷。"+
                   "11月8日18时，中央气象台又是“三警齐发”，继续发布暴雪橙色预警、寒潮蓝色预警和海上大风黄色预警！"+
                   "与此同时，2021年联合国气候变化大会正在英国格拉斯哥举行，会议的议题是：如何减少二氧化碳等温室气体排放、控制全球变暖。"+
                   "为啥要控制全球变暖？让冬季温暖一些难道不好吗？这样猛烈的强寒潮是不是在“啪啪打脸”全球变暖的判断……",
          title: "强寒潮来袭，是在“啪啪打脸”全球变暖吗？",
          inputtime: "2021-11-08 13:30",
          name: "流年",
          hits: 5
        },
        {
          id: 2,
          content: "湖南作为我国一个高等教育大省，虽然没有湖北那么强势，但是省内优秀高校数量也不少，包括中南大学、湖南大学两所 985高校和湖南师大这样的211高校。当然也有不少优秀的双非高校，比如曾经的全国重点大学湘潭大学，还有长沙理工、湖南科大等高校，因此湖南的高校选择还是比较多的。\n" +
          "\n" +
          "\n" +
          "而湖南的高校也有一对冤家，那就是湘潭大学和湖南师范大学，湘潭大学作为曾经的全国重点大学，大家觉得湘潭大学无论是实力还是名气都对得起一个211的称号，但是湖南省属211名额却给了湖南师大，因此很多人心里也是愤愤不平。那么这两所高校到底谁更值得一个211的名额呢？我们一起来看看吧。\n" +
          "\n" +
          "\n" +
          "首先看最近出炉的泰晤士世界大学排名，在这份榜单中，湘潭大学位居106位，而湖南师大则位居173位，差距还是比较明显的。虽说每个榜单的判断依据不一样，但是基本上也是八九不离十，因此在外界看来，湘潭大学的综合实力还是更胜一筹的。\n" +
          "\n" +
          "\n" +
          "看过了排名再来看看学科博士点数量，毕竟博士点也是对一个学校实力最直观的一个反映，也是各个学校优势学科所在。湘潭大学拥有3个国家重点学科，同时拥有15个博士后科研流动站，15个一级学科博士点，这个数量基本上跟一个中下游211有得一拼了。而且它的数学、工程学、化学、材料科学进入全球前1%的行列，这种表现在双非里面也是属于翘楚。\n" +
          "\n" +
          "\n" +
          "看完了湘潭大学再来看看湖南师大，湖南师大拥有20个博士后科研流动站，21个博士学位授权一级学科、1个博士专业学位授权点，并且拥有国家重点学科6个，从这里可以看出，国家对于211高校和非211高校的待遇区别还是挺大的，因此湖南师大在一些重点建设的学科方面还是要优于湘潭大学的。\n" +
          "\n" +
          "\n" +
          "最后再来看看学科评估情况，以第四轮学科评估为例，湘潭大学的法学、马克思主义理论和数学获得B+的成绩，而另外有六个学科进入B档，实力也不算弱了。而湖南师大同样获得B+的学科数量为3个，分别是教育学、外国语言文学和数学，但是另外进入B档的学科数量高达16个，不得不称赞湖南师大虽然顶尖学科数量很少，但是整体学科质量也是不差的，虽然相对于其他211来说还是存在一定的差距。\n" +
          "\n" +
          "\n" +
          "看了这些，我们会觉得两所高校其实各有所长，而湖南师大进入211已经有20多个年头，所以也是得到了国家的大力扶持，因此发展的劲头也是更足一点。但是湘潭大学也不差，如果当初是它进入 211的话，那么如今的湘大肯定会更上一层楼。但是这些都没有如果，如今的湘大几乎上可以跟湖南师大平起平坐，这也是许多网友所认同的，因此希望两校都能借着双一流的东风，为湖南高等教育做出更多的贡献。\n" +
          "\n湖南作为我国一个高等教育大省，虽然没有湖北那么强势，但是省内优秀高校数量也不少，包括中南大学、湖南大学两所 985高校和湖南师大这样的211高校。当然也有不少优秀的双非高校，比如曾经的全国重点大学湘潭大学，还有长沙理工、湖南科大等高校，因此湖南的高校选择还是比较多的。\n" +
          "\n" +
          "\n" +
          "而湖南的高校也有一对冤家，那就是湘潭大学和湖南师范大学，湘潭大学作为曾经的全国重点大学，大家觉得湘潭大学无论是实力还是名气都对得起一个211的称号，但是湖南省属211名额却给了湖南师大，因此很多人心里也是愤愤不平。那么这两所高校到底谁更值得一个211的名额呢？我们一起来看看吧。\n" +
          "\n" +
          "\n" +
          "首先看最近出炉的泰晤士世界大学排名，在这份榜单中，湘潭大学位居106位，而湖南师大则位居173位，差距还是比较明显的。虽说每个榜单的判断依据不一样，但是基本上也是八九不离十，因此在外界看来，湘潭大学的综合实力还是更胜一筹的。\n" +
          "\n" +
          "\n" +
          "看过了排名再来看看学科博士点数量，毕竟博士点也是对一个学校实力最直观的一个反映，也是各个学校优势学科所在。湘潭大学拥有3个国家重点学科，同时拥有15个博士后科研流动站，15个一级学科博士点，这个数量基本上跟一个中下游211有得一拼了。而且它的数学、工程学、化学、材料科学进入全球前1%的行列，这种表现在双非里面也是属于翘楚。\n" +
          "\n" +
          "\n" +
          "看完了湘潭大学再来看看湖南师大，湖南师大拥有20个博士后科研流动站，21个博士学位授权一级学科、1个博士专业学位授权点，并且拥有国家重点学科6个，从这里可以看出，国家对于211高校和非211高校的待遇区别还是挺大的，因此湖南师大在一些重点建设的学科方面还是要优于湘潭大学的。\n" +
          "\n" +
          "\n" +
          "最后再来看看学科评估情况，以第四轮学科评估为例，湘潭大学的法学、马克思主义理论和数学获得B+的成绩，而另外有六个学科进入B档，实力也不算弱了。而湖南师大同样获得B+的学科数量为3个，分别是教育学、外国语言文学和数学，但是另外进入B档的学科数量高达16个，不得不称赞湖南师大虽然顶尖学科数量很少，但是整体学科质量也是不差的，虽然相对于其他211来说还是存在一定的差距。\n" +
          "\n" +
          "\n" +
          "看了这些，我们会觉得两所高校其实各有所长，而湖南师大进入211已经有20多个年头，所以也是得到了国家的大力扶持，因此发展的劲头也是更足一点。但是湘潭大学也不差，如果当初是它进入 211的话，那么如今的湘大肯定会更上一层楼。但是这些都没有如果，如今的湘大几乎上可以跟湖南师大平起平坐，这也是许多网友所认同的，因此希望两校都能借着双一流的东风，为湖南高等教育做出更多的贡献。\n" +
          "\n",
          title: "“湘潭大学和湖南师大，到底谁更厉害？数据告诉你一切",
          inputtime: "2021-11-09 10:30",
          name: "奕雪",
          hits: 100
        },
        {
          id: 3,
          content: "红汤味和清汤味奶茶你喝过吗？\n" +
          "\n" +
          "近日，重庆观音桥步行街一奶茶店推出“火锅奶茶”，吸引不少市民前来尝鲜打卡，而这也引起了网友的围观。\n" +
          "\n" +
          "据悉，这款“火锅奶茶”有椒麻红汤味和番茄清汤味两种口味，配有红辣椒、青花椒、番茄等火锅配料。顾客还可以选择奶茶的温度、甜度等。\n" +
          "\n" +
          "\n" +
          "\n" +
          "对于这样的黑暗饮品，有网友直言，不知是创新还是黑暗料理，光听名字就觉得味道很酸爽。\n" +
          "\n" +
          "有尝试过的朋友吗，感觉如何？\n" +
          "\n",
          title: "重庆商家推出火锅奶茶",
          inputtime: "2021-11-09 14:30",
          name: "Chan",
          hits: 100000

        },
      ]
  },
  onLoad:function(options){

      app.showModel();
      var self=this;
      var fabuData = self.data.fabuData;
      console.log(options.id)
      for(var i=0;i<fabuData.length;i++) {

        if(options.id == fabuData[i].id) {
          // 格式化文章内容
          var article = fabuData[i].content;

          WxParse.wxParse('data', 'html', article, self);

          self.setData({
            content: fabuData[i],
            id: options.id
          })
          wx.hideToast();
        }
      }
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