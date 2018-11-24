/**
 * Created by yangkui on 17/4/7.
 */
/**
 * 封装http请求，便于统一处理请求和返回
 */
import  Vue from "vue";
import {Indicator} from 'mint-ui';
import {Toast} from 'mint-ui';
export  default  function (url, params, success, error) {
    Indicator.open('加载中...');
    Vue.http.interceptors.push(function (request, next) {
        // modify method
        request.method = 'POST';
        // modify headers
        request.headers.set('Content-Type', 'application/json');

        // continue to next interceptor
        next();
    });
    let requestURL = "";
    if (url.indexOf("/") == 0) {
        requestURL += url;
    } else {
        requestURL += "/" + url;
    }
    Vue.http.post(requestURL, params).then(response => {
        Indicator.close();
        let status = response.status;
        if (status === 200) {
            if (success) {
                success(response.body.body);
            }
        } else {
            if (error) {
                error(response.body);
            } else {
                Toast('系统异常:' + response.body);
            }
        }
    }, response => {
        Indicator.close();
        let status = response.status;
        if (status === 401) {
            console.error("当前用户未登录");
            Toast('用户身份无法识别!');
            //window.location.href = "/static/init.html"
        } else {
            if (error) {
                error(response.body);
            }
        }
    });

}