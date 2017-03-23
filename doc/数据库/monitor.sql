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
   status               int not null comment '状态[10待发布、20发布中、30发布失败、40发布成功]',
   status_msg           varchar(200) comment '状态消息',
   release_time         datetime comment '发布时间',
   shell_script         text comment '客户端执行的Shell命令',
   primary key (prj_id, client_id)
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
   release_version      varchar(50) comment '发布的版本号',
   release_time         datetime comment '发布的版本时间',
   status               int not null comment '状态[10正常、20停用]',
   container            int not null comment '容器类型[10tomcat、50自定义服务、100其它]',
   shell_script         text not null comment 'shell脚本',
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








drop index unique_server_address on rest_server;

drop table if exists rest_server;

/*==============================================================*/
/* Table: rest_server                                           */
/*==============================================================*/
create table rest_server
(
   rs_id                int not null auto_increment comment '编号',
   server_address       varchar(100) not null comment '服务地址',
   remark               varchar(150) comment '备注',
   primary key (rs_id)
);

alter table rest_server comment '服务地址表';

/*==============================================================*/
/* Index: unique_server_address                                 */
/*==============================================================*/
create unique index unique_server_address on rest_server
(
   server_address
);

drop index unique_rsId_url on rest_info;

drop table if exists rest_info;

/*==============================================================*/
/* Table: rest_info                                             */
/*==============================================================*/
create table rest_info
(
   ri_id                int not null auto_increment comment '接口项目编号',
   rs_id                int not null comment '服务地址编号',
   url                  varchar(255) not null comment '接口地址',
   create_time          datetime not null comment '添加时间',
   primary key (ri_id)
);

alter table rest_info comment '项目接口表';

/*==============================================================*/
/* Index: unique_rsId_url                                       */
/*==============================================================*/
create unique index unique_rsId_url on rest_info
(
   rs_id,
   url
);
drop table if exists rest_log;

/*==============================================================*/
/* Table: rest_log                                              */
/*==============================================================*/
create table rest_log
(
   rl_id                varchar(32) not null comment '编号',
   ri_id                int not null comment '接口项目编号',
   request_time         datetime not null comment '请求时间',
   create_time          datetime not null comment '添加时间',
   dtl          		text default null comment '详细信息',
   primary key (rl_id)
);

alter table rest_log comment '接口记录表';

drop index unique_riId_statDate on rest_stat_day;

drop table if exists rest_stat_day;

/*==============================================================*/
/* Table: rest_stat_day                                         */
/*==============================================================*/
create table rest_stat_day
(
   rsd_id               varchar(32) not null comment '编号',
   rs_id                int not null comment '服务编号',
   ri_id                int not null comment '项目接口编号',
   stat_date            date not null comment '日期',
   create_time          datetime not null comment '新增时间',
   visit_num            int not null comment '访问次数',
   hour1                int not null comment '1点访问次数',
   hour2                int not null comment '2点访问次数',
   hour3                int not null comment '3点访问次数',
   hour4                int not null comment '4点访问次数',
   hour5                int not null comment '5点访问次数',
   hour6                int not null comment '6点访问次数',
   hour7                int not null comment '7点访问次数',
   hour8                int not null comment '8点访问次数',
   hour9                int not null comment '9点访问次数',
   hour10               int not null comment '10点访问次数',
   hour11               int not null comment '11点访问次数',
   hour12               int not null comment '12点访问次数',
   hour13               int not null comment '13点访问次数',
   hour14               int not null comment '14点访问次数',
   hour15               int not null comment '15点访问次数',
   hour16               int not null comment '16点访问次数',
   hour17               int not null comment '17点访问次数',
   hour18               int not null comment '18点访问次数',
   hour19               int not null comment '19点访问次数',
   hour20               int not null comment '20点访问次数',
   hour21               int not null comment '21点访问次数',
   hour22               int not null comment '22点访问次数',
   hour23               int not null comment '23点访问次数',
   hour0                int not null comment '0点访问次数',
   primary key (rsd_id)
);

alter table rest_stat_day comment '每日统计表';

/*==============================================================*/
/* Index: unique_riId_statDate                                  */
/*==============================================================*/
create unique index unique_riId_statDate on rest_stat_day
(
   ri_id,
   stat_date
);
