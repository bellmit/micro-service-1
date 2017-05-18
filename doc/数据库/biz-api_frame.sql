/*
SQLyog Ultimate v11.27 (32 bit)
MySQL - 5.0.22-community-nt : Database - frame
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`frame` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `frame`;

/*Table structure for table `rest_info` */

DROP TABLE IF EXISTS `rest_info`;

CREATE TABLE `rest_info` (
  `rp_id` int(11) NOT NULL auto_increment COMMENT '接口项目编号',
  `rs_id` int(11) NOT NULL COMMENT '服务地址编号',
  `url` varchar(255) NOT NULL COMMENT '接口地址',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`rp_id`),
  UNIQUE KEY `unique_rsId_url` (`rs_id`,`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目接口表';

/*Data for the table `rest_info` */

/*Table structure for table `rest_log` */

DROP TABLE IF EXISTS `rest_log`;

CREATE TABLE `rest_log` (
  `rl_id` varchar(32) NOT NULL COMMENT '编号',
  `rp_id` int(11) NOT NULL COMMENT '接口项目编号',
  `request_time` datetime NOT NULL COMMENT '请求时间',
  `ip` varchar(30) NOT NULL COMMENT '来源ip',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`rl_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口记录表';

/*Data for the table `rest_log` */

/*Table structure for table `rest_server` */

DROP TABLE IF EXISTS `rest_server`;

CREATE TABLE `rest_server` (
  `rs_id` int(11) NOT NULL auto_increment COMMENT '编号',
  `server_address` varchar(100) NOT NULL COMMENT '服务地址',
  `remark` varchar(150) default NULL COMMENT '备注',
  PRIMARY KEY  (`rs_id`),
  UNIQUE KEY `unique_server_address` (`server_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务地址表';

/*Data for the table `rest_server` */

/*Table structure for table `sys_dict` */

DROP TABLE IF EXISTS `sys_dict`;

CREATE TABLE `sys_dict` (
  `dict_id` varchar(32) NOT NULL COMMENT '编号',
  `type_code` varchar(32) NOT NULL COMMENT '类型编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `value` varchar(200) NOT NULL COMMENT '值',
  `remark` varchar(200) default NULL COMMENT '备注',
  `add_user_id` varchar(32) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`dict_id`),
  UNIQUE KEY `unique_type_code_name` (`type_code`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统字典值表';

/*Data for the table `sys_dict` */

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `code` varchar(32) NOT NULL COMMENT '编码',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `parent_code` varchar(32) default '0' COMMENT '上级编码',
  `remark` varchar(200) default NULL COMMENT '备注',
  `add_user_id` varchar(32) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`code`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统字典分类表';

/*Data for the table `sys_dict_type` */

/*Table structure for table `sys_res` */

DROP TABLE IF EXISTS `sys_res`;

CREATE TABLE `sys_res` (
  `res_id` varchar(32) NOT NULL COMMENT '资源编号',
  `type` int(11) NOT NULL COMMENT '类型[10菜单、20功能]',
  `parent_res_id` varchar(32) NOT NULL COMMENT '父级功能编码',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `show_name` varchar(50) NOT NULL COMMENT '显示名称',
  `url` varchar(200) default NULL COMMENT '跳转地址',
  `remark` varchar(200) default NULL COMMENT '备注',
  `orderby` int(11) NOT NULL COMMENT '排序号',
  `add_user_id` varchar(32) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统资源表';

/*Data for the table `sys_res` */

insert  into `sys_res`(`res_id`,`type`,`parent_res_id`,`name`,`show_name`,`url`,`remark`,`orderby`,`add_user_id`,`create_time`) values ('ADMIN',10,'0','管理后台的顶级菜单','管理后台的菜单',NULL,'管理后台的顶级菜单',0,'admin','2016-12-09 15:44:15'),('ADMIN_GRANT',10,'ADMIN','权限管理','权限管理',NULL,'权限管理的菜单',0,'admin','2016-12-09 15:44:15'),('ADMIN_GRANT_AUTH',10,'ADMIN_GRANT','授权管理','授权管理','/sysAuth/f-view/manager.shtml','授权的编辑',3,'admin','2016-12-09 15:44:15'),('ADMIN_GRANT_RES',10,'ADMIN_GRANT','菜单资源管理','菜单资源管理','/sysRes/f-view/manager.shtml','菜单资源的编辑',0,'admin','2016-12-09 15:44:15'),('ADMIN_GRANT_ROLE',10,'ADMIN_GRANT','角色管理','角色管理','/sysRole/f-view/manager.shtml','角色的编辑',1,'admin','2016-12-09 15:44:15'),('ADMIN_GRANT_USER',10,'ADMIN_GRANT','用户管理','用户管理','/userInfo/f-view/manager.shtml','用户的编辑',2,'admin','2016-12-09 15:44:15'),('ADMIN_OPERATION',10,'ADMIN','系统运维','系统运维',NULL,'系统运维的菜单',1,'admin','2016-12-09 15:44:15'),('ADMIN_OPERATION_DICT',10,'ADMIN_OPERATION','字典的配置','字典的配置','/sysDict/f-view/manager.shtml','字典的配置',0,'admin','2016-12-09 15:44:15'),('ADMIN_ORG',10,'ADMIN','组织管理','组织管理',NULL,'组织管理的菜单',2,'admin','2016-12-21 14:51:40'),('ADMIN_ORG_DEP',10,'ADMIN_ORG','部门管理','部门管理','/sysDict/f-view/manager.shtml','部门的配置',0,'admin','2016-12-21 14:51:40'),('ADMIN_ORG_EMP',10,'ADMIN_ORG','员工管理','员工管理','/sysDict/f-view/manager.shtml','员工的配置',1,'admin','2016-12-21 14:51:40');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` varchar(32) NOT NULL COMMENT '编号',
  `name` varchar(30) NOT NULL COMMENT '名称',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  `add_user_id` varchar(32) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`role_id`),
  UNIQUE KEY `unique_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`role_id`,`name`,`remark`,`add_user_id`,`create_time`) values ('admin','系统管理员','最高权限人员','admin','2016-12-08 11:19:07');

/*Table structure for table `sys_role_res` */

DROP TABLE IF EXISTS `sys_role_res`;

CREATE TABLE `sys_role_res` (
  `role_id` varchar(32) NOT NULL COMMENT '角色编号',
  `res_id` varchar(32) NOT NULL COMMENT '资源编号',
  `add_user_id` varchar(32) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`role_id`,`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色资源表';

/*Data for the table `sys_role_res` */

insert  into `sys_role_res`(`role_id`,`res_id`,`add_user_id`,`create_time`) values ('admin','ADMIN','admin','2016-12-08 13:38:30'),('admin','ADMIN_GRANT','admin','2016-12-08 13:38:30'),('admin','ADMIN_GRANT_AUTH','admin','2016-12-08 13:38:30'),('admin','ADMIN_GRANT_RES','admin','2016-12-08 13:38:30'),('admin','ADMIN_GRANT_ROLE','admin','2016-12-08 13:38:30'),('admin','ADMIN_GRANT_USER','admin','2016-12-08 13:38:30'),('admin','ADMIN_OPERATION','admin','2016-12-08 13:38:30'),('admin','ADMIN_OPERATION_DICT','admin','2016-12-08 13:38:30'),('admin','ADMIN_ORG','admin','2016-12-21 14:51:40'),('admin','ADMIN_ORG_DEP','admin','2016-12-21 14:51:41'),('admin','ADMIN_ORG_EMP','admin','2016-12-21 14:51:41');

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `user_id` varchar(32) NOT NULL COMMENT '编码',
  `user_name` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL default '1' COMMENT '密码',
  `add_user_id` varchar(32) NOT NULL default '1' COMMENT '添加人',
  `create_time` datetime NOT NULL default '2016-12-15 00:00:00' COMMENT '添加时间',
  `is_delete` int(11) NOT NULL default '0' COMMENT '是否删除',
  `status` int(11) NOT NULL default '10' COMMENT '状态[10正常、20冻结]',
  `name` varchar(30) NOT NULL COMMENT '名称',
  PRIMARY KEY  (`user_id`),
  UNIQUE KEY `unique_username` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `user_info` */

insert  into `user_info`(`user_id`,`user_name`,`password`,`add_user_id`,`create_time`,`is_delete`,`status`,`name`) values ('1','1','1','1','2016-12-01 10:01:44',0,20,'管理员'),('10','10','1514b2ddc48569bcb15ad5665200b74e','1','2016-12-15 00:00:00',0,10,'10啊'),('11','11','1','1','2016-12-15 00:00:00',1,10,''),('12','12','1514b2ddc48569bcb15ad5665200b74e','1','2016-12-15 00:00:00',0,10,'12'),('13','13','1','1','2016-12-15 00:00:00',0,10,''),('1b987ef3e7114183ac40799c53e08263','test2','1514b2ddc48569bcb15ad5665200b74e','admin','2016-12-21 13:49:36',0,10,'test2'),('2','2','1','1','2016-12-15 00:00:00',0,10,''),('3','3','1','1','2016-12-15 00:00:00',0,10,''),('4','4','1','1','2016-12-15 00:00:00',0,10,''),('5','5','1514b2ddc48569bcb15ad5665200b74e','1','2016-12-15 00:00:00',0,10,''),('6','6','1','1','2016-12-15 00:00:00',0,10,''),('6186c0d0c7ef420f8861fad6c6611582','123456','52189fe653c054867345e4527bbc664f','admin','2016-12-21 13:37:36',0,10,''),('7','7','1','1','2016-12-15 00:00:00',0,10,''),('8','8','1','1','2016-12-15 00:00:00',0,10,''),('9','9','1','1','2016-12-15 00:00:00',0,10,''),('959c11ded302408d9a58831b9f88db78','test','1514b2ddc48569bcb15ad5665200b74e','admin','2016-12-21 13:46:45',0,10,''),('admin','admin','1514b2ddc48569bcb15ad5665200b74e','admin','2016-12-08 11:19:07',0,10,'管理员');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `role_id` varchar(32) NOT NULL COMMENT '角色编号',
  `add_user_id` varchar(32) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY  (`role_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`,`add_user_id`,`create_time`) values ('admin','admin','admin','2016-12-08 11:19:07');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
