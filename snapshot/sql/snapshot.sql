/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : snapshot

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2021-11-15 17:32:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for evaluation
-- ----------------------------
DROP TABLE IF EXISTS `evaluation`;
CREATE TABLE `evaluation` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `content` varchar(150) CHARACTER SET utf8 DEFAULT NULL COMMENT '评价内容',
  `work_id` bigint(20) DEFAULT NULL COMMENT '评价的作品id',
  `status` varchar(255) DEFAULT 'TO_AUDIT' COMMENT '状态',
  `creator_id` bigint(20) NOT NULL COMMENT '评论人',
  `creator_type` int(11) DEFAULT '0' COMMENT '创建者类型(0:C端，1：B端）',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='评价/评论';

-- ----------------------------
-- Records of evaluation
-- ----------------------------
INSERT INTO `evaluation` VALUES ('72', '说的好啊', '9', 'PASS', '10', '0', '2021-11-12 16:07:55', '2021-11-12 16:07:55', '0');
INSERT INTO `evaluation` VALUES ('73', '我非常赞同你的观点', '9', 'NO_PASS', '10', '0', '2021-11-12 16:48:56', '2021-11-12 16:48:56', '0');

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `picture_url` varchar(255) DEFAULT NULL COMMENT '轮播图地址',
  `picture_index` int(20) DEFAULT '1' COMMENT '排序',
  `picture_status` int(255) DEFAULT '1' COMMENT '1: 有效，2：无效',
  `creator_id` bigint(20) NOT NULL COMMENT '发布图片的管理员ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` VALUES ('1', '/profile/upload/2021/11/10/7f6aefbc-d805-4581-846a-abba234634ef.jpg', '2', '1', '8', '2021-11-10 13:59:11', null, '0');
INSERT INTO `picture` VALUES ('2', '/profile/upload/2021/11/10/d64305ec-2c7c-44e8-8966-2a632ec8a1a5.jpg', '1', '1', '8', '2021-11-10 14:14:24', '2021-11-10 14:14:27', '0');
INSERT INTO `picture` VALUES ('3', '/profile/upload/2021/11/10/8ea8e92c-763c-4081-b118-03fd1371151c.jpg', '3', '1', '8', '2021-11-10 16:51:02', null, '0');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `avatar_image` varchar(500) DEFAULT '/profile/upload/2021/10/28/088d4925-7411-4447-90d8-3239eaee68f2.gif' COMMENT '头像',
  `creator_id` bigint(20) DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `age` int(11) DEFAULT '18' COMMENT '年龄',
  `sex` varchar(10) DEFAULT '男' COMMENT '性别',
  `point` int(11) DEFAULT '0' COMMENT '积分',
  `status` varchar(20) DEFAULT 'valid' COMMENT '状态 ''UNKNOWN'': 未知 ''valid''：有效，''invalid''：无效',
  `user_type` int(11) DEFAULT '0' COMMENT '0:C端，1：后台',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('8', 'maple', '$2a$10$ZlgXVSBPeriRXM3lkB5fVuz/Yj0IuaoiypvXaUM6A/eqxSUI1YjvK', '19976618156', 'admin', '/profile/upload/2021/10/28/088d4925-7411-4447-90d8-3239eaee68f2.gif', '0', '2021-10-28 10:27:57', '2021-10-28 10:27:59', '18', '男', '3', 'valid', '1', '0');
INSERT INTO `user` VALUES ('9', '张三', '$2a$10$ZlgXVSBPeriRXM3lkB5fVuz/Yj0IuaoiypvXaUM6A/eqxSUI1YjvK', '18579888989', 'consumer', '/profile/upload/2021/10/28/088d4925-7411-4447-90d8-3239eaee68f2.gif', '0', '2021-10-28 17:50:30', null, '18', '男', '6', 'valid', '0', '0');
INSERT INTO `user` VALUES ('10', 'sasadss324', '$2a$10$xDxYOWZuSBU4nC1mcRN.setKSvp33PwFZsUlyj1j8VGra5oVlmv.W', '18976618156', 'consumer', '/profile/upload/2021/11/03/57d7ea2f-df82-401e-8735-1ffd07d5df45.gif', '0', '2021-11-01 18:30:40', null, '30', '男', '9', 'valid', '0', '0');
INSERT INTO `user` VALUES ('11', '伊雪', '$2a$10$wrQu/DmIzNiFS5U9g70xTuIKpiDGRlkdKHwnQINfhZcLi/mbDwscy', '16628587176', 'consumer', '/profile/upload/2021/11/07/012db17a-7889-4995-8ec4-9cdc1d23be3d.jpg', '0', '2021-11-07 15:36:46', null, '23', '女', '20', 'valid', '0', '0');

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `url` varchar(500) DEFAULT NULL COMMENT '文件地址',
  `work_type` int(255) DEFAULT '1' COMMENT '作品类型 1：文明点赞，2：曝光台',
  `type` int(20) DEFAULT '1' COMMENT '文件类型 1：照片，2：视频',
  `status` varchar(20) DEFAULT 'TO_AUDIT' COMMENT '状态',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建者',
  `creator_type` int(11) DEFAULT '0' COMMENT '创建者类型 0：C，1：后台',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES ('5', 'it人的一天', '[\"/profile/upload/2021/10/28/72b3aa51-927f-40cd-a54d-0b9629bef632.mp4\"]', '1', '2', 'PASS', '8', '1', '2021-10-28 19:16:24', '2021-10-28 19:16:28', '0');
INSERT INTO `work` VALUES ('9', '今天是立冬记得吃饺子,叫阿三的撒旦撒旦撒旦撒大苏打撒大苏打撒的阿斯顿阿三的阿三的阿三打算的阿三的阿三的阿三打算的撒的阿三打算的萨阿丹撒说的是', '[\"/profile/upload/2021/11/07/e30193b8-b16b-4e38-a048-285a53d37787.jpg\",\"/profile/upload/2021/11/07/77ff76f5-c852-4d49-bd71-70a388128040.jpg\",\"/profile/upload/2021/11/07/96df0f08-87fd-4b70-bf3a-718690d5aef4.gif\"]', '1', '1', 'PASS', '11', '0', '2021-11-07 19:12:51', '2021-11-12 16:53:55', '0');
INSERT INTO `work` VALUES ('12', '今日立冬，雪初至，叶初枯', '[\"/profile/upload/2021/11/07/10f24187-bab2-4646-95f8-ef74e44d0149.mp4\"]', '1', '2', 'PASS', '10', '0', '2021-11-07 19:12:55', '2021-11-12 16:53:58', '0');
INSERT INTO `work` VALUES ('13', '好美的雪景啊', '[\"/profile/upload/2021/11/07/011d3649-f793-4fef-80b2-77becda6d4d4.jpg\",\"/profile/upload/2021/11/07/5ab9d2d9-f63a-44b5-8b59-c9da8c7f396a.webp\",\"/profile/upload/2021/11/07/f1438908-dbf1-4e5b-a4ea-5f256a6ddaa4.jpg\"]', '2', '1', 'PASS', '11', '0', '2021-11-07 19:13:33', '2021-11-12 16:54:00', '0');
INSERT INTO `work` VALUES ('14', '有人随地吐痰', '[\"/profile/upload/2021/11/07/011d3649-f793-4fef-80b2-77becda6d4d4.jpg\",\"/profile/upload/2021/11/07/5ab9d2d9-f63a-44b5-8b59-c9da8c7f396a.webp\",\"/profile/upload/2021/11/07/f1438908-dbf1-4e5b-a4ea-5f256a6ddaa4.jpg\"]', '2', '1', 'PASS', '10', '0', '2021-11-12 16:53:48', '2021-11-12 16:54:04', '0');
INSERT INTO `work` VALUES ('15', '有人随地吐痰', '[\"/profile/upload/2021/11/07/011d3649-f793-4fef-80b2-77becda6d4d4.jpg\",\"/profile/upload/2021/11/07/5ab9d2d9-f63a-44b5-8b59-c9da8c7f396a.webp\"]', '2', '1', 'PASS', '10', '0', '2021-11-12 16:53:51', '2021-11-12 16:54:07', '0');
INSERT INTO `work` VALUES ('16', '有人随地吐痰', '[\"/profile/upload/2021/11/07/011d3649-f793-4fef-80b2-77becda6d4d4.jpg\"]', '2', '1', 'PASS', '10', '0', '2021-11-12 16:53:53', '2021-11-12 16:54:10', '0');
