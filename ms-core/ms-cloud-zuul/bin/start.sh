#! /bin/sh

# 设置启动的jar
SERVICE_NAME="project.restful-1.0.0.jar"

PRJ_BIN_DIR=$(dirname $(readlink -f "$0"))
SERVICE_HOME=$(dirname $PRJ_BIN_DIR)

LOGS_DIR=$SERVICE_HOME/logs
# 控制台日志
STDOUT_FILE=$SERVICE_HOME/logs/stdout.out

# 判断该服务名称是否已经启动加载了
PIDS=$(ps --no-heading -C java -f --width 1000 | grep $SERVICE_HOME |awk '{print $2}')
if [ -n "$PIDS" ]; then
    echo "ERROR: The $SERVICE_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

# java 常规启动
JAVA_OPTS="-Dproject.dir=$SERVICE_HOME"

# 默认启动内存配置
JAVA_MEM_OPTS="-server -Xmx1024m -Xms1024m -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"

# 整合JAVA启动参数
JAVA_EXEC="$JAVA_OPTS $JAVA_MEM_OPTS -jar $SERVICE_HOME/$SERVICE_NAME --spring.profiles.active=release"

#建立日志目录
mkdir -p $LOGS_DIR

# 正式启动区
# =====================================
echo -e "Starting the $SERVICE_NAME ...\c"
nohup java $JAVA_EXEC > $STDOUT_FILE 2>&1 &


# 启动后检测
# =====================================
COUNT=0
while [ $COUNT -lt 1 ]; do
    echo -e ".\c"
    sleep 1
       COUNT=$(ps --no-heading -C java -f --width 1000 | grep "$SERVICE_HOME" | awk '{print $2}' | wc -l)
    if [ $COUNT -gt 0 ]; then
        break
    fi
done
echo "OK!"

PIDS=$(ps --no-heading -C java -f --width 1000 | grep "$SERVICE_HOME" | awk '{print $2}')

echo "STDOUT: $STDOUT_FILE"

echo -e "Start success!"

echo "RUN PID: $PIDS"