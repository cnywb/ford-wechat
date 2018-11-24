#!/usr/bin/env bash

LOG_DIR="hub"
NAME="hub"
echo "=================START REDEPLOY ${NAME}================="

PID=$(ps -ef|grep "${NAME}-1.0.jar" | grep -v grep | awk '{print $2}')

echo "=================START KILL PID: ${PID}================="
kill -9 ${PID}


echo "=================START REMOVE ${NAME}================="
mv /server/jar/${NAME}-1.0.jar /server/backups/${NAME}/${NAME}-1.0_$(date "+%Y%m%d_%H%M%S").jar
rm -rf /server/jar/${NAME}-1.0.jar


echo "=================START DOWNLOAD ${NAME}================="
wget http://mappxq.oss-cn-shanghai.aliyuncs.com/deploy/${NAME}-1.0.jar -O /server/jar/${NAME}-1.0.jar

echo "=================BEGIN START ${NAME} SERVER================="
/usr/bin/nohup java -jar /server/jar/${NAME}-1.0.jar -Xms512m -Xmx1024m &
echo "=================${NAME} SERVER RUNNING================="

tail -fn100 /server/logs/${LOG_DIR}/${NAME}-1.0.log
