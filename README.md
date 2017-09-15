#micro-service

目前提供了定时任务调度系统、服务监控、项目发布、查看和调用项目API接口信息等功能
支持根据模版生成功能模块所需要的增删改查的业务代码，简化开发

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

待新增功能
对应用的接口权限管理