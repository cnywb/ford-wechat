# bind-html

> 车主认证

## Build Setup

``` bash
# 进入bind-html项目
cd bind-html

# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

## 卸载组建
    npm uninstall [name] --save-dev
    
## 首页访问地址
###### 开发环境
    http://localhost/static/home.html?state=31
###### 测试环境
    http://dev.point.xiqing.info/bind/static/init.html?state=31
    http://dev.point.xiqing.info/bind/static/init.html?state=31&openId=:openId
    http://dev.point.xiqing.info/bind/static/uat.html?state=31&openId=:openId
###### 生产测试地址
    http://www.changanfordwechat.com/fordwechat/control?state=17262
###### 生产正式地址
    http://www.changanfordwechat.com/fordwechat/control?state=31&channelCode=:channelCode
    
    
## 认证问题连接（已认证用户）
###### 开发环境
    http://localhost/static/home.html?state=31&router=problem
###### 生产环境
    http://dev.point.xiqing.info/bind/static/init.html?state=31&router=problem

## 认证问题连接（非认证用户）
###### 开发环境
    http://localhost/static/home.html?state=31&router=service
###### 生产环境
    http://dev.point.xiqing.info/bind/static/init.html?state=31&router=service
    
## Source Map
    sentry-cli releases -o Pox -p bind-html new 1.0
    sentry-cli releases -o Pox -p bind-html files 1.0 upload-sourcemaps --url-prefix ~/bind ./dist
