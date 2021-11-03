/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : snapshot

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2021-11-02 11:27:30
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
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父评论id',
  `to_user_id` bigint(20) DEFAULT '0' COMMENT '被评论人Id',
  `to_user_type` int(11) DEFAULT '0' COMMENT '被评论人类型(0:C端，1：B端）',
  `creator_id` bigint(20) NOT NULL COMMENT '评论人',
  `creator_type` int(11) DEFAULT '0' COMMENT '创建者类型(0:C端，1：B端）',
  `created_time` datetime DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `is_deleted` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='评价/评论';

-- ----------------------------
-- Records of evaluation
-- ----------------------------

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `picture_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `creator_id` bigint(20) NOT NULL COMMENT '发布图片的管理员ID',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `picture_url` varchar(255) DEFAULT NULL COMMENT '轮播图地址',
  `is_deleted` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`picture_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picture
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('8', 'maple2', '$2a$10$ZlgXVSBPeriRXM3lkB5fVuz/Yj0IuaoiypvXaUM6A/eqxSUI1YjvK', '19976618156', 'admin', '/profile/upload/2021/10/28/088d4925-7411-4447-90d8-3239eaee68f2.gif', '0', '2021-10-28 10:27:57', '2021-10-28 10:27:59', '18', '男', '0', 'valid', '1', '0');
INSERT INTO `user` VALUES ('9', '张三', '$2a$10$ZlgXVSBPeriRXM3lkB5fVuz/Yj0IuaoiypvXaUM6A/eqxSUI1YjvK', '18579888989', 'consumer', '/profile/upload/2021/10/28/088d4925-7411-4447-90d8-3239eaee68f2.gif', '0', '2021-10-28 17:50:30', null, '18', '男', '0', 'valid', '0', '0');
INSERT INTO `user` VALUES ('10', '18156', '$2a$10$mLZbUkj1qXsdDBxmMa7ID./qJvCxOQog.KOW1Kz0EbVfyx.i4gX0a', '18976618156', 'consumer', '/profile/upload/2021/10/28/088d4925-7411-4447-90d8-3239eaee68f2.gif', '0', '2021-11-01 18:30:40', null, '18', '男', '0', 'valid', '0', '0');

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `pic` varchar(255) DEFAULT NULL COMMENT '封面',
  `url` varchar(255) DEFAULT NULL COMMENT '视频地址',
  `type` int(20) DEFAULT '1' COMMENT '类型 1：视频，2：照片',
  `status` varchar(11) DEFAULT 'TO_AUDIT' COMMENT '状态',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '创建者',
  `creator_type` int(11) DEFAULT '0' COMMENT '创建者类型 0：C，1：后台',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(2) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES ('5', 'it人的一天', '/profile/upload/2021/10/28/c7050d9c-c382-4849-898a-3f2a1a5f508a.jpg', '/profile/upload/2021/10/28/72b3aa51-927f-40cd-a54d-0b9629bef632.mp4', '1', 'TO_AUDIT', '8', '1', '2021-10-28 19:16:24', '2021-10-28 19:16:28', '0');
