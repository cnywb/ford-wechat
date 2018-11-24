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
wget http://mappxq.oss-cn-shanghai.aliyuncs.com/deploy/${NAME}-1.0.jar -O /some/jar/${NAME}-1.0.jar

echo "=================BEGIN START ${NAME} SERVER================="
nohup java -jar /some/jar/${NAME}-1.0.jar -Xms128m -Xmx512m &
echo "=================${NAME} SERVER RUNNING================="

tail -fn100 /some/logs/${LOG_DIR}/${NAME}-1.0.log
