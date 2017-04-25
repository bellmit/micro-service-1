@echo off & setlocal enabledelayedexpansion

title ms-client

% 启动 %
echo Starting ...

java -Xms256m -Xmx256m -XX:MaxPermSize=64M -jar ..\..\ms-cloud-client-1.0.0.jar

:end
pause