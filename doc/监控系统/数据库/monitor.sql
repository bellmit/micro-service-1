drop table if exists cli_info;

drop table if exists prj_client;

drop table if exists prj_info;

drop table if exists prj_monitor;

drop table if exists prj_version;

drop table if exists sys_config;

drop table if exists sys_file;

drop index unique_username on sys_user;

drop table if exists sys_user;

/*==============================================================*/
/* Table: cli_info                                              */
/*==============================================================*/
create table cli_info
(
   client_id            varchar(32) not null comment '客户端编号',
   name                 varchar(50) not null comment '名称',
   remark               varchar(200) comment '描叙',
   ip                   varchar(30) not null comment 'ip地址',
   port                 int not null comment '端口',
   token                varchar(100) not null comment '密钥',
   create_time          datetime not null comment '添加时间',
   user_id              int not null comment '添加人',
   status               int not null comment '状态[10正常、20停用]',
   activity_status      int comment '活动状态[10正常、20心跳异常]',
   activity_time        datetime comment '上次活动时间',
   primary key (client_id)
);

alter table cli_info comment '客户端表';

/*==============================================================*/
/* Table: prj_client                                            */
/*==============================================================*/
create table prj_client
(
   prj_id               int not null comment '项目编号',
   client_id            varchar(32) not null comment '客户端编号',
   version              varchar(50) not null comment '版本编号',
   status               int not null comment '状态[10待发布、20发布中、30发布失败、40发布成功]',
   status_msg           varchar(200) comment '状态消息',
   release_time         datetime comment '发布时间',
   shell_script         text comment '客户端执行的Shell命令',
   log_path             varchar(150) comment '日志路径',
   primary key (prj_id, client_id, version)
);

alter table prj_client comment '项目客户端表';

/*==============================================================*/
/* Table: prj_info                                              */
/*==============================================================*/
create table prj_info
(
   prj_id               int not null auto_increment comment '编号',
   code                 varchar(50) not null comment '项目编码',
   name                 varchar(100) not null comment '名称',
   remark               varchar(200) comment '描叙',
   create_time          datetime not null comment '添加时间',
   user_id              int not null comment '添加人',
   status               int not null comment '状态[10正常、20停用]',
   container            int not null comment '容器类型[10tomcat、50自定义服务、100其它]',
   shell_script         text comment 'shell脚本',
   monitor_status       int not null default 0 comment '监控状态是否正常',
   monitor_msg          varchar(200) comment '监控消息',
   primary key (prj_id)
);

alter table prj_info comment '项目表';

/*==============================================================*/
/* Table: prj_monitor                                           */
/*==============================================================*/
create table prj_monitor
(
   prjm_id              int not null auto_increment comment '编号',
   prj_id               int not null comment '项目编号',
   type                 int not null comment '监控类型[10服务、20数据库、30缓存、40其它]',
   remark               varchar(100) not null comment '描叙',
   monitor_is           int not null comment '是否检测',
   monitor_succ_str     varchar(100) comment '检测成功标识',
   monitor_status       int comment '检测状态[10正常、20异常]',
   monitor_url          varchar(150) comment '检测地址',
   monitor_time         datetime comment '检测时间',
   monitor_fail_num     int comment '检测失败次数',
   monitor_fail_num_remind int comment '检测失败最大次数提醒[0代表不提醒]',
   monitor_fail_email   varchar(50) comment '检测失败接收邮箱',
   monitor_fail_send_time datetime comment '检测失败发送信息时间',
   monitor_fail_send_interval int comment '检测失败发送信息间隔[单位：分钟]',
   primary key (prjm_id)
);

alter table prj_monitor comment '项目监控表';

/*==============================================================*/
/* Table: prj_version                                           */
/*==============================================================*/
create table prj_version
(
   prj_id               varchar(50) not null comment '项目编号',
   version              varchar(50) not null comment '版本号',
   remark               varchar(300) comment '描叙',
   create_time          datetime not null comment '添加时间',
   user_id              int not null comment '添加人',
   is_release           int not null comment '是否发布',
   path_url             varchar(200) not null comment '版本所在的路径',
   primary key (prj_id, version)
);

alter table prj_version comment '项目版本表';

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config
(
   code                 varchar(50) not null comment '编码',
   name                 varchar(50) not null comment '名称',
   value                varchar(100) not null comment '值',
   remark               varchar(100) comment '描叙',
   exp1                 varchar(100) comment '扩展1',
   exp2                 varchar(100) comment '扩展2',
   primary key (code)
);

/*==============================================================*/
/* Table: sys_file                                              */
/*==============================================================*/
create table sys_file
(
   file_id              varchar(32) not null comment '编号',
   type                 int not null comment '类型[10项目]',
   org_name             varchar(80) not null comment '原名称',
   sys_name             varchar(80) not null comment '系统名称',
   url                  varchar(200) not null comment '显示路径',
   file_type            varchar(20) not null comment '文件类型',
   file_size            float not null comment '文件大小',
   status               int not null comment '状态[0待确定、1使用中、2未使用、3已作废]',
   create_time          datetime not null comment '添加时间',
   primary key (file_id)
);

alter table sys_file comment '系统文件表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              int not null auto_increment comment '编号',
   username             varchar(30) not null comment '用户名',
   password             varchar(80) not null comment '密码',
   nickname             varchar(30) not null comment '昵称',
   create_time          datetime not null comment '添加时间',
   status               int not null comment '状态[10正常、20冻结]',
   primary key (user_id)
);

/*==============================================================*/
/* Index: unique_username                                       */
/*==============================================================*/
create unique index unique_username on sys_user
(
   username
);






/*==============================================================*/
/* 2017-04-21 新增 版本：1.0.2                                  */
/*==============================================================*/

drop table if exists ms_config;

/*==============================================================*/
/* Table: ms_config                                             */
/*==============================================================*/
create table ms_config
(
   config_id            int not null auto_increment comment '编号',
   name                 varchar(100) not null comment '文件名称',
   remark               varchar(250) comment '备注',
   is_use               int not null comment '是否使用',
   create_time          datetime not null comment '创建日期',
   user_id              int not null comment '添加人',
   primary key (config_id)
);

alter table ms_config comment '配置文件表';

drop table if exists ms_config_value;

/*==============================================================*/
/* Table: ms_config_value                                       */
/*==============================================================*/
create table ms_config_value
(
   config_id            int not null comment '配置编号',
   code                 varchar(150) not null comment 'key的编码',
   value                varchar(250) comment 'value',
   remark               varchar(250) comment '备注',
   orderby	            int not null comment '排序',
   create_time          datetime not null comment '创建时间',
   user_id              int not null comment '添加人',
   primary key (config_id, code)
);

alter table ms_config_value comment '配置文件值表';





/*==============================================================*/
/* 2017-05-18 新增 版本：1.0.3                                  */
/*==============================================================*/


drop table if exists prj_api;

/*==============================================================*/
/* Table: prj_api                                               */
/*==============================================================*/
create table prj_api
(
   prj_id               int not null comment '项目编号',
   path                 varchar(255) not null comment '路径',
   name                 varchar(200) comment '名称',
   method               varchar(500) not null comment '方法详情',
   params               text comment '参数',
   response             text comment '结果',
   is_use               int not null comment '是否使用',
   create_time          datetime not null comment '新增时间',
   update_time          datetime not null comment '更新时间',
   primary key (prj_id, path)
);

alter table prj_api comment '项目的API表';


drop table if exists ms_secret;

/*==============================================================*/
/* Table: ms_secret                                             */
/*==============================================================*/
create table ms_secret
(
   cli_id               varchar(32) not null comment '客户端编号',
   name                 varchar(100) not null comment '名称',
   remark               varchar(100) comment '备注',
   token                varchar(100) not null comment '密钥',
   domain               varchar(200) comment '主路径',
   is_use               int not null comment '是否使用',
   create_time          datetime not null comment '创建时间',
   primary key (cli_id)
);

alter table ms_secret comment '应用密钥表';




/*==============================================================*/
/* 2017-06-12 新增 项目版本表，新增回滚版本                     */
/*==============================================================*/
alter table `monitor`.`prj_version`   
  add column `rb_version` varchar(50) NULL  COMMENT '回滚版本' after `path_url`;

  

 
/*==============================================================*/
/* 2017-06-21 新增 项目数据源表				                    */
/*==============================================================*/
drop table if exists prj_ds;

/*==============================================================*/
/* Table: prj_ds                                                */
/*==============================================================*/
create table prj_ds
(
   code                 varchar(32) not null comment '数据源编码',
   prj_id               int not null comment '项目编号',
   type                 varchar(30) not null comment '数据库类型[mysql/oracle]',
   driver_class         varchar(100) not null comment '驱动类',
   url                  varchar(100) not null comment 'jdbc的url',
   username             varchar(50) not null comment '用户名',
   password             varchar(50) not null comment '密码',
   initial_size         int not null comment '初始连接数',
   max_idle             int not null comment '最大连接数',
   min_idle             int not null comment '最小连接数',
   test_sql             varchar(200) not null comment '测试的sql语句',
   user_id              int not null comment '添加人',
   create_time          datetime not null comment '添加时间',
   primary key (code)
);

alter table prj_ds comment '项目数据源表';







/*==============================================================*/
/* 2017-07-04 新增 项目版本脚本表				                */
/*==============================================================*/
drop table if exists prj_version_script;

create table prj_version_script
(
   pvs_id               int not null auto_increment comment '编号',
   prj_id               int not null comment '项目编号',
   version              varchar(32) not null comment '版本号',
   remark               varchar(100) comment '备注',
   ds_code              varchar(32) not null comment '数据源编号',
   up_sql               text not null comment '升级脚本',
   callback_sql         text comment '回滚脚本',
   is_up                int not null comment '是否升级',
   create_time          datetime not null comment '添加时间',
   user_id              int not null comment '添加人',
   primary key (pvs_id)
);

alter table prj_version_script comment '项目版本脚本表';






/*==============================================================*/
/* 2017-07-27 新增 生成源码相关的表				                */
/*==============================================================*/
drop table if exists code_prj;

/*==============================================================*/
/* Table: code_prj                                              */
/*==============================================================*/
create table code_prj
(
   code                 varchar(50) not null comment '编码',
   prj_id               int not null comment '项目编号',
   name                 varchar(150) not null comment '名称',
   create_time          datetime not null comment '创建时间',
   user_id              int not null comment '创建人',
   primary key (code)
);

alter table code_prj comment '项目源码表';

drop table if exists code_template;

/*==============================================================*/
/* Table: code_template                                         */
/*==============================================================*/
create table code_template
(
   code                 varchar(50) not null comment '源码编号',
   type                 int not null comment '类型[10java、20jsp、30其它文件]',
   name                 varchar(100) not null comment '名称',
   remark               varchar(200) comment '描叙',
   package_name         varchar(30) not null comment '包名',
   content              text not null comment '模板内容',
   path                 varchar(200) not null comment '模板路劲',
   suffix               varchar(50) comment '文件后缀',
   primary key (code, name)
);

alter table code_template comment '项目模板表';


drop table if exists code_create;

/*==============================================================*/
/* Table: code_create                                           */
/*==============================================================*/
create table code_create
(
   id                   int not null auto_increment comment '编号',
   code                 varchar(50) not null comment '源码编号',
   package_path         varchar(200) not null comment '功能包路径',
   status               int not null comment '状态[10待生成、20生成中、30生成失败、40生成成功]',
   download             varchar(200) comment '下载地址',
   finish_time           datetime comment '生成完成时间',
   ds_code              varchar(100) not null comment '数据源编号',
   db_name              varchar(100) not null comment '数据库名',
   tables               varchar(200) not null comment '生成的表集合[多个,分隔]',
   create_time          datetime not null comment '创建时间',
   user_id              int not null comment '创建人',
   primary key (id)
);

alter table code_create comment '生成源码表';
