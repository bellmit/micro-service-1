@echo off & setlocal enabledelayedexpansion

title ms-eureka

% 启动 %
echo Starting ...

java -Xms256m -Xmx256m -XX:MaxPermSize=64M -Dproject.dir=${user.dir}/../../ -jar ..\..\crm-cloud-eureka-1.0.0.jar

:end
pause