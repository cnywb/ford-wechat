#!/usr/bin/env bash

LOG_DIR="event"
NAME="event-api"
echo "=================START REDEPLOY ${NAME}================="

PID=$(ps -ef|grep "${NAME}-1.0.jar" | grep -v grep | awk '{print $2}')

echo "=================START KILL PID: ${PID}================="
kill -9 ${PID}

echo "=================START REMOVE ${NAME}================="
rm -rf ../${NAME}-1.0.jar


echo "=================START DOWNLOAD ${NAME}================="
wget http://mappxq.oss-cn-shanghai.aliyuncs.com/deploy/${NAME}-1.0.jar -O /server/jar/${NAME}-1.0.jar

echo "=================BEGIN START ${NAME} SERVER================="
/usr/bin/nohup java -jar /server/jar/${NAME}-1.0.jar -Xms2048m -Xmx2048m -XX:PermSize=512m -XX:MaxPermSize=512m &
echo "=================${NAME} SERVER RUNNING================="

tail -fn100 /server/logs/${LOG_DIR}/${NAME}-1.0.log
