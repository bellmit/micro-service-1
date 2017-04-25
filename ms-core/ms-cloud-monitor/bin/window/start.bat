@echo off & setlocal enabledelayedexpansion

title ms-monitor

% 启动 %
echo Starting ...

java -Xms256m -Xmx256m -XX:MaxPermSize=64M -Dproject.dir=${user.dir}/../../ -jar ..\..\ms-cloud-monitor-1.0.0.war

:end
pause