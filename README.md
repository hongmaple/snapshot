# snapshot

## 介绍
该项目一款拍立得项目，可以发布类似于盆友圈的说说，实现了图片加文字，视频加文字形式发布，还有评论功能，分为两大类型，文明点赞和曝光台，有一个后端工程snapshot，使用iade进行开发，基于jdk1.8，有两个前端工程，snapshot-wx，snapshot-admin，此项目采用单体架构，本地文件存储，mysql数据库技术，ssm框架加jwt鉴权，rsa加密技术实现。

## 软件架构
### 后端架构

1. 技术栈

| 技术 | 说明 | 官网 |
| -------------------- | ------------------- | ---------------------------------------------- |
| spring | 类的管理工具 | http://c.biancheng.net/spring/ |
| springMvc | 显示层、控制层、数据层的操作 | https://www.jianshu.com/p/91a2d0a1e45a |
| mybatis | ORM框架 | http://www.mybatis.org/mybatis-3/zh/index.html |
| mybatisPlus | MyBatis的增强工具,在 MyBatis 的基础上只做增强不做改变,为简化开发、提高效率而生 | https://mp.baomidou.com/ |
| springBoot | 容器+MVC框架 | https://spring.io/projects/spring-boot |
| websocket | 单个TCP连接上进行全双工通信的协议 | http://www.ruanyifeng.com/blog/2017/05/websocket.html |
| pagehelper | MyBatis物理分页插件 | http://git.oschina.net/free/Mybatis_PageHelper |
| Swagger2 | 文档生成工具 | https://github.com/swagger-api/swagger-ui |
| lombok | 省略getter和setter方法 | https://www.jianshu.com/p/2543c71a8e45 |
| modelmapper | Java Object Mapping 工具 | https://github.com/modelmapper/modelmapper |
| fastjson| 将Java对象转换为JSON表示 | https://github.com/alibaba/fastjson |
| Hibernator-Validator | 验证框架 | https://blog.csdn.net/hy_coming/article/details/93978555 |

2. 数据库用的是mysql8.0，使用Navicat进行管理

3. 系统环境，windows ，jdk1.8
### 前端项目
#### snapshot-wx
介绍： 
使用基于微信平台的微信小程序技术进行开发，主要实现的功能有文明点赞与曝光台类型的说说发布，以及两大类型的说说的查看，点赞，评论，以及个人信息的维护。
##### snapshot-wx相关截图
![输入图片说明](imageQQ%E5%9B%BE%E7%89%8720211229164554.jpg)
![输入图片说明](imageQQ%E5%9B%BE%E7%89%8720211229164616.jpg)
##### 开发环境启动
请使用微信开发者工具导入项目进行开发

编辑 app.js 里的接口域名配置
#### snapshot-admin
#### 基于vue3.0实现后台管理模板

> 感谢[vue-admin](https://github.com/MrZHLF/vue-admin)

> 使用vue最新脚手架搭建环境，编写界面，使用axios请求接口，渲染界面,实现页面登录注册，数据的增删改查,数据部分存储到easy-mock数据中。

##### 项目结构
```
  |——— client  #vue项目入口文件
	|——— config  #秘钥配置文件
	|——— node_modules #一些常用安装的依赖
	|——— models #接口模型
	|——— router       #接口文档
	|——— package.json #项目配置文件
	|___ README.md    #项目的说明文档，markdown 格式
```
##### 相关技术
	1. vuejs2.0：一套构建用户界面的渐进式框架，易用、灵活、高效。
	2. vue-router：官方的路由组件，配合vue.js创建单页应用（SPA）非常简单。
	3. axios: 基于 Promise 的 HTTP 请求客户端，可同时在浏览器和 node.js 中使用。
	
##### 功能介绍
	- Element-ui
	- 请求拦截和响应拦截
	- 富文本编辑器
	- Markdown编辑器
	- Echarts
	- tab数据切换
	- token本地存储
	- 表单
	- 高德地图引入
	- 登录注册
	- 路由守卫
	- vuex存储
	- 数据分页和查询
	- Excel表格导出
	- 递归组件
	- 主题更换
	- 404
##### 部分界面展示
![输入图片说明](imageQQ%E5%9B%BE%E7%89%8720211229165205.jpg)

##### 项目编译和运行
	+ 进入snapshot-admin当前页面初始化
		cnpm install
	+ 进入client项目目录中
		cnpm install
  + 所有依赖安装成功后执行启动命令在当前目录启动,不要在client进行启动，已经配置好前后端联调
	  npm run dev 
	  如果显示一下内容说明安装成功
	  I  Your application is running here: http://localhost:8080
#### 安装教程

1.  安装mysql，导入snapshot/sql/snapshot.sql创建snapshot数据库
2.  打开iade导入snapshot项目工程，使用maven下载依赖
3.  配置项目：
在src/resources/application.yml中

配置数据库连接
```
  datasource:
    url: jdbc:mysql://localhost:3306/snapshot? 
    useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
```
配置文件上传

```
ruoyi:
  # 名称
  name: deflower
  # 版本
  version: 1.0.0
  # 版权年份
  copyrightYear: 2021
  # 文件路径 示例（ Windows配置D:/ruoyi/uploadPath，Linux配置 /home/ruoyi/uploadPath）
  profile: E:/GitWarehouse/deflower/uploadPath
```
配置rsa与jwt
```
# rsa配置
rsa:
  key:
    #公钥地址
    publicKey-file:  D:/project/snapshot/snapshot/auth_key/id_key_rsa.pub
    #私钥地址
    privateKey-file: D:/project/snapshot/snapshot/auth_key/id_key_rsa
    #密钥
    secret: (EMOK)_$^11244^%$_(IS)_@@++--(COOL:)_++++_:sds_(GUY)

token:
  #token头部
  header: Authorization
  #存储的key
  secret: with_pat_user
  #过期时间
  expiration: 10000000
```
到此就可以使用了。