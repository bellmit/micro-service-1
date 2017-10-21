### 微服务架构
```
目前提供了定时任务调度系统
服务监控
项目发布
查看和调用项目API接口信息
调用连跟踪等
源码生成功能（支持根据模版生成功能模块所需要的增删改查的业务代码，简化开发）
```

### 下一步计划：
密钥管理增加关联项目的字段
密钥管理可以设置拥有项目接口所有权限和增加授权接口访问的权限的信息

### 服务启动顺序
```
启动eureka
启动config
启动monitor
启动log
启动zuul
启动task
启动应用程序
```

### 配置域名映射关系 hosts配置
```
127.0.0.1	msMonitor
127.0.0.1	msRc
127.0.0.1	msZuul
127.0.0.1	msConfig
127.0.0.1	msTask
127.0.0.1	msLog
```

### 待新增功能：

对应用的接口权限管理

