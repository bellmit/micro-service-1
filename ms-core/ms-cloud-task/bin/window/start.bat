@echo off & setlocal enabledelayedexpansion

title ms-task

% 启动 %
echo Starting ...

set project.dir=${user.dir}/../../
java -Xms256m -Xmx256m -XX:MaxPermSize=64M -Dproject.dir=%project.dir% -jar ..\..\ms-cloud-task-1.0.0.war

:end
pause