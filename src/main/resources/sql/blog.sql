/*
Navicat MySQL Data Transfer

Source Server         : YD
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2019-03-06 11:41:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `blog`
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文章标题',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文章信息',
  `content` longtext COMMENT '文章内容',
  `rel_time` timestamp NULL DEFAULT NULL COMMENT '发布时间',
  `reading_count` int(11) DEFAULT NULL COMMENT '阅读数',
  `topic_id` int(11) NOT NULL COMMENT '文章分类',
  PRIMARY KEY (`id`),
  KEY `topic_id_index` (`topic_id`),
  CONSTRAINT `blog_topic_fk` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;