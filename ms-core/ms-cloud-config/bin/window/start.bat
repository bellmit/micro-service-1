@echo off & setlocal enabledelayedexpansion

title ms-config

% 启动 %
echo Starting ...

java -Xms256m -Xmx256m -XX:MaxPermSize=64M -Dproject.dir=${user.dir}/../../ -jar ..\..\crm-cloud-config-1.0.0.jar --server.port=3001

:end
pause