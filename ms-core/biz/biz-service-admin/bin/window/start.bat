@echo off & setlocal enabledelayedexpansion

% 启动 %
echo Starting ...

set project.dir=${user.dir}/../../
java -Xms256m -Xmx256m -XX:MaxPermSize=64M -Dproject.dir=%project.dir% -jar ..\..\project-admin-1.0.0.war

:end
pause