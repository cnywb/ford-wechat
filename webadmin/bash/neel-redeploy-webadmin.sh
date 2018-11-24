#!/usr/bin/env bash

#wget http://mappxq.oss-cn-shanghai.aliyuncs.com/deploy/uat/neel-redeploy-webadmin.sh -O neel-redeploy-webadmin.sh
#chmod +x neel-redeploy-webadmin.sh


DIR="/server/tomcat/tomcat-8-pc"
NAME="webadmin"
echo "=================START REDEPLOY ${NAME}================="
./bin/shutdown.sh

PID=$(ps -ef|grep "${DIR}" | grep -v grep | awk '{print $2}')

echo ">>>>>>>>>>>> KILL PID: ${PID}"
kill -9 ${PID}

echo ">>>>>>>>>>>> BAK ${NAME} TO war"
mv ${DIR}/webapps/${NAME}.war ${DIR}/war/${NAME}_$(date "+%Y%m%d_%H%M%S").war

echo ">>>>>>>>>>>> CLEAR ${NAME}"
#rm -rf ${DIR}/webapps/${NAME}.war
rm -rf ${DIR}/webapps/${NAME}
rm -rf ${DIR}/work/*


echo ">>>>>>>>>>>> START DOWNLOAD ${NAME}"
wget http://mappxq.oss-cn-shanghai.aliyuncs.com/deploy/${NAME}.war -O ${DIR}/webapps/${NAME}.war

echo ">>>>>>>>>>>> BEGIN START ${NAME} SERVER"
./bin/startup.sh
echo ">>>>>>>>>>>> ${NAME} SERVER RUNNING"

tail -fn100 ${DIR}/logs/catalina.out
