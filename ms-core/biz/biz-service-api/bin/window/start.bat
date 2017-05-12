@echo off & setlocal enabledelayedexpansion

title biz-service-api

% 启动 %
echo Starting ...

set project.dir=${user.dir}/../../
java -Xms256m -Xmx256m -XX:MaxPermSize=64M -Dproject.dir=%project.dir% -jar ..\..\project-api-1.0.0.jar

:end
pause