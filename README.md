#micro-service

目前提供了定时任务调度系统、服务监控、项目发布、查看和调用项目API接口信息等功能
支持根据模版生成功能模块所需要的增删改查的业务代码，简化开发

下一步计划：
密钥管理增加关联项目的字段
密钥管理可以设置拥有项目接口所有权限和增加授权接口访问的权限的信息

服务启动顺序
	1. 启动eureka
	2. 启动config
	3. 启动monitor
	4. 启动zuul
	5. 启动task
	6. 启动应用程序


hosts配置
127.0.0.1	msMonitor
127.0.0.1	msRc
127.0.0.1	msZuul
127.0.0.1	msConfig
127.0.0.1	msTask
127.0.0.1	msLog

待新增功能：
对应用的接口权限管理



带学习功能：
Spring Cloud Sleuth：日志收集工具包，封装了Dapper,Zipkin和HTrace操作。
http://blog.csdn.net/forezp/article/details/70162074

调用连跟踪，图表展示调用时间。 rpc用的什么框架  
日志管理是怎么排错，比如多个服务调用链  如何排查错误 通过ID标识 架构内部集成

http://blog.csdn.net/pzxwhc/article/details/49873623   api网关