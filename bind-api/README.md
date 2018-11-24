# 测试环境
### 初始部署
```
docker run -d \
-v /server/jar/:/jar \
-v /etc/localtime:/etc/localtime \
-p 8888:8888 \
--name bind-api \
index.tenxcloud.com/renyf/jdk:1.8 \
/usr/bin/java -jar -Duser.timezone=GMT+08 -Dfile.encoding="UTF-8" /jar/bind-api-1.0.jar
```
### 初始部署
```
docker run -d \
-v /server/jar/:/jar \
-v /etc/localtime:/etc/localtime \
-p 8888:8888 \
--name bind-api \
index.tenxcloud.com/renyf/jdk:1.8 \
/usr/bin/java -jar -Duser.timezone=GMT+08 /jar/bind-api-1.0.jar
```

### 检查容器
    docker ps -a
    docker logs bind-api
    docker logs bind-api -f --tail 100

### 检查容器
    docker restart bind-api
    
### 重启Nginx
    docker restart elastic_curie
    
### 查看Nginx日志
    docker logs elastic_curie -f --tail 100