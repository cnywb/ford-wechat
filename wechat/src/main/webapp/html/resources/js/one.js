
/**
 * Created by wanglijun on 16/8/19.
 */
/** * 命名空间配置定义 */
var one=one||{
        version:'0.1',
        coreFileName:'one.js',
        namespace:'one'
    };

/** * 常量定义 */
one.constant={
    success:'success',
    error:'error',
    fail: 'fail',
    dataPrefix:'data-' //ui组件自定义属性前缀
};
/**
 * 组件缓存,以"模块-组件名-id"格式作为key存储.
 * 例如:ui-button-id
 */
one.assemblyCache={};

(function($){
    var exports={},_this=this;
    /**
     * 方法提供到util命名空间下
     */
    this.util=exports;
    //超时
    exports.timeout=12000;
    //生产环境域名
    exports.proHostName='point.xiqing.info';

    //测试环境域名
    exports.devHostName='p.xiqing.info';

    //URL地址设置
    exports.byws={
        authUrl:'http://www.changanfordwechat.com/fordwechat/control?state=1403',
        shareUrl:'https://www.changanfordclub.com/infocollection/potentialcustomer/byws/index.jspx?channel=1',
        verifyUrl:'http://www.changanfordwechat.com/fordwechat/control?state=311'
    };

    /**  判断是否为空 */
    exports.isEmpty=function(o){
        return o===null ||o=== undefined ? true : /^[\s\xa0]*$/.test(o);
    };
    /**判断是否不为空*/
    exports.isNotEmpty=function(o){
        return exports.isEmpty(o)?false:true;
    };

    /**获取应用上下文根属性*/
    exports.context=function(){
        //获取应用的上下文根路径
        var pathname=window.location.pathname;
        var indexNext= pathname.indexOf("/",1);
        return pathname.substr(0,indexNext);
    }();
    /**域名*/
    exports.hostname=function(){
        return window.location.hostname;
    }();
    //端口号
    exports.port=function(){
        return window.location.port;
    }();
    //跳转
    exports.href=function(href){
        window.location.href=href;
    }
    //协议
    exports.protocol=function(){
        return window.location.protocol;
    }();
    //重新
    exports.reload=function(){
        window.location.reload();
    };
    //显示对话框数据
    exports.dialog=function(title,content,time){
        time=time||2000;
        $.dialog({
            content :content,
            title :title,
            lock : true,
            time:time
        });
    };
    exports.info=function(content,time){
        exports.dialog('ok',content,time);
    }
    exports.alert=function(content,time){
        exports.dialog('alert',content,time);
    }
    //显示
    exports.requestURL=function(){
        var  url='/api.do';
        //生产
        if(exports.hostname==exports.proHostName) {
            url=exports.protocol + "//" + exports.hostname + ':' + exports.port + '/point/api.do';
        }else if(exports.hostname==exports.devHostName){
            url = exports.protocol + "//" + exports.hostname + ':' + exports.port + '/point/api.do';
        }else{
            url=exports.protocol + "//" + exports.hostname + ':' + exports.port + '/fordwechat/api.do';
        }
        return url;

    }();

    exports.loading=function(){
        return new loadings('');
    };

    exports.ajaxErrorTip=function(){
        exports.alert("亲,参数活动人有点多,请稍后再试");
    };

    exports.noop=function(){};

    //交易请求
    exports.trans=function(transCode,data,success,errors,args){
        var params={transCode:transCode,requestBody:data};
        var ajaxLoading=new loadings('');
        success=success|| exports.noop;
        errors=errors|| exports.noop;
        exports.post(exports.requestURL,JSON.stringify(params),function(){
            ajaxLoading.show();
        },function(data,status){
            if(data['status']==200){
                success.call(this,data['responseBody'],data['status']);
                return;
            }
            exports.ajaxErrorTip();
            return;
        },function(xhr,status){
            ajaxLoading.hide();
        },errors,args);
    }

    var _template='<div class="zepto-loading-wrap">' +
        '<div class="zepto-loading-block"> <div class="zepto-loading-content">' +
        '<div class="line-spin-fade-loader"><div>' +
        '</div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div></div></div></div>';

    function loadings(params){
        var main={
            loadingWrap:null,
            container:'body',
            init:function(){
                this.loadingWrap=$('.zepto-loading-wrap');
                if(this.loadingWrap.size()<1){
                    this.loadings=$(_template);
                    $(this.container).append(this.loadings);
                    this.loadingWrap.loading();

                }
            },
            show:function(){
                this.loadingWrap.show();
            },
            hide:function(){
                this.loadingWrap=$('.zepto-loading-wrap');
                if(this.loadingWrap.size()>=1) {
                    this.loadingWrap.hide();
                }
            }
        };
        main.init();
        return{
            show:function(){
                main.show();
                return this;
            },
            hide:function(){
                main.hide();
                return this;
            }
        };

    }


    //POST请求
    exports.post=function(url,data,before,success,complete,errors,args){
        success=success|| exports.noop;
        errors=errors||exports.noop;
        before=before|| exports.noop;
        complete=complete|| exports.noop;
        $.ajax({
            url:url,
            contentType:'application/json',
            type:'post',
            data:data,
            async:true,
            dateType:'json',
            timeout:exports.timeout,
            beforeSend:function(){
                before.call(this);
            },
            complete:function(xhr,status){
                complete.call(this,xhr,status);
            },
            success:function(data,status){
                success.call(this,data, status, args);
            },
            error:function(xhr,status,error){
                if(status){
                    exports.ajaxErrorTip();
                }
                errors.call(this,xhr,status,error,args);
            }
        });
    }


    /**
     * 判断是否为数组类型
     */
    exports.isArray = function (o) {
        return o !== null && typeof o === "object" && 'splice' in o && 'join' in o;
    };

    /**
     * 判断是否为object类型
     */
    exports.isObject = function (o) {
        return typeof o === "object";
    };

    /**
     * 判断是否为string类型
     */
    exports.isString = function (o) {
        return typeof o === "string";
    };

    /**
     * 判断是否为function类型
     */
    exports.isFunction = function (o) {
        return typeof o === "function";
    };

    /**
     * 判断是否为boolean类型
     */
    exports.isBoolean = function (o) {
        return typeof o === "boolean";
    };

    /**
     * 判断是否为number类型
     */
    exports.isNumber = function (o) {
        return typeof o === "number";
    };

   /** 获取URL单个的参数
    */
    exports.QueryUrlParam=function(name){
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    };
    /**
     * 获取URL所有的参数
     * */
    exports.QueryUrlParams=function(){
        var url =window.location.search;
        var params=[];
        if (url.indexOf("?") != -1) {
            var strParams = url.substr(1).split("&");
            for(var i = 0; i < strParams.length; i ++) {
                var paramName=strParams[i].split("=")[0];
                var paramValue=strParams[i].split("=")[1];
                var param={};
                param[paramName]=paramValue;
                params.push(param);
            }
        }
        return params;
    };



    /***
     *sessionStorage set
     * @param key
     * @param value
     */
    exports.setLocalStorageItem=function(key,value){
        window.localStorage.setItem(key,value);
    }
    /***
     * sessionStorage get
     * 获取session Key
     * @param key
     */
    exports.getLocalStorageItem=function(key){
        return window.localStorage.getItem(key);
    }

    /***
     *sessionStorage set
     * @param key
     * @param value
     */
    exports.setSessionItem=function(key,value){
        window.sessionStorage.setItem(key,value);
    }
    /***
     * sessionStorage get
     * 获取session Key
     * @param key
     */
    exports.getSessionItem=function(key){
        return window.sessionStorage.getItem(key);
    }

    /**
     * 判断是否为undefined
     */
    exports.isUndefined = function (o) {
        return typeof o === "undefined";
    };

    /**
     * 判断对象是否为空字符串，去除前后空格
     * @param o
     * @returns {boolean|*}
     */
    exports.isBlank = function (o) {
        o = o || "";
        return o === "" || o.trim() === "";
    };

    /**
     * 得到组件缓存对象
     */
    exports.getAssemblyCache = function (moduleId) {
        return _this.assemblyCache[moduleId];
    };

    /**
     * 添加组件缓存对象
     */
    exports.addAssemblyCache = function (moduleId,object) {
        return _this.assemblyCache[moduleId] = object;
    };

    /**是否微信浏览器*/
    exports.isWeiXin=function(){
        var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i)=='micromessenger') {
            return true;
        } else {
            return false;
        }
    };
    /***
     * 判断是否微信浏览器
     */
    exports.checkWeiXin=function(){
        if(!exports.isWeiXin()){
            exports.href(exports.byws.authUrl);
        }
    }

}).call(one,$);

(function ($) {

    var Loading = function (element) {
        this.element = $(element);
        this.hide();
    };

    Loading.prototype = {
        constructor: Loading,
        show: function () {
            var event = $.Event('loading:show');
            this.element.trigger(event);
            this.element.show();

        },
        hide: function () {
            var event = $.Event('loading:hide');
            this.element.trigger(event);
            this.element.hide();
        }
    };

    function Plugin() {
        return this.each(function () {
            var element = $(this);
            var data = element.data('zepto.loading');
            if (!data) {
                element.data('zepto.loading', new Loading(this));
            }
        });
    }

    $.fn.loading = Plugin;

})(window.Zepto);