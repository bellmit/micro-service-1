
insert into `sys_config` (`code`, `name`, `value`, `remark`, `exp1`, `exp2`) values('mail.smtp','发送邮箱的smtp','smtp.163.com',null,null,null);
insert into `sys_config` (`code`, `name`, `value`, `remark`, `exp1`, `exp2`) values('mail.from','发送邮件的邮箱','xxx@163.com',null,null,null);
insert into `sys_config` (`code`, `name`, `value`, `remark`, `exp1`, `exp2`) values('mail.username','发送邮件的用户名','xxxx',null,null,null);
insert into `sys_config` (`code`, `name`, `value`, `remark`, `exp1`, `exp2`) values('mail.password','发送邮件的密码','xxxxxx',null,null,null);

insert into `sys_config` (`code`, `name`, `value`, `remark`, `exp1`, `exp2`) values('prj.file.path','项目上传的目录','/home/monitor/file',null,null,null);
insert into `sys_config` (`code`, `name`, `value`, `remark`, `exp1`, `exp2`) values('prj.monitor.fail.email','项目检测失败接收的邮箱','yuejing@cjhxfund.com',null,null,null);


insert  into `sys_user`(`user_id`,`username`,`password`,`nickname`,`create_time`,`status`) values (1,'admin','43286a86708820e38c333cdd4c496355','admin','2016-10-19 10:53:38',10);





insert into `ms_secret` (`cli_id`, `name`, `remark`, `token`, `domain`, `is_use`, `create_time`) values('143756892','config','配置系统的密钥','4307a39dfc7ecb783d6682f44s3911e2','','1','2017-06-11 12:00:40');
insert into `ms_secret` (`cli_id`, `name`, `remark`, `token`, `domain`, `is_use`, `create_time`) values('145756897','biz-api','biz-api项目的密钥','6e09a39dfc7edb7w3d66f2f44s3913e2','','1','2017-06-11 12:05:45');

