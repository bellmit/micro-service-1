#micro-service

目前提供了定时任务调度系统、服务监控、项目发布、查看和调用项目API接口信息等功能

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