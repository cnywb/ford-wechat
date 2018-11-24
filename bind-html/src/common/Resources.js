/**
 * Created by Richard on 17/4/7.
 */
/**
 * 封装http请求，便于统一处理请求和返回
 */
import Vue from "vue";
import { Toast, Indicator } from 'mint-ui';

export default class Resources {

  constructor(baseUrl) {

  };

  static post(url, params) {
    return new Promise(function (resolve, reject) {
      Resources.handleIterceptors(true);
      Vue.http.post(url, params).then(response => {
        Resources.handleSuccessResponse(response, resolve, reject);
      }, response => {
        Resources.handleErrorResponse(response, reject);
      });
    });
  }

  static get(url, params) {
    return new Promise(function (resolve, reject) {

      Resources.handleIterceptors(true);
      Vue.http.get(url, params).then(response => {
        Resources.handleSuccessResponse(response, resolve, reject);
      }, response => {
        Resources.handleErrorResponse(response, reject);
      });
    });
  }
  static put(url, params) {
    return new Promise(function (resolve, reject) {

      Vue.http.put(url, params).then(response => {
        Resources.handleSuccessResponse(response, resolve, reject);
      }, response => {
        Resources.handleErrorResponse(response, reject);
      });
    });
  }

  static handleIterceptors(emulateJSON) {
    Vue.http.options.emulateJSON = emulateJSON;
    Vue.http.interceptors.push(function(request, next) {
      Indicator.open({
        spinnerType: 'triple-bounce'
      });
      next((response) => {
        Indicator.close();
        return response;
      });
    });
  }

  static handleSuccessResponse(response, resolve, reject) {
    let status = response.status;
    if (status === 200) {
      if (typeof(resolve) == "function") {
        resolve(response.body);
      }
    } else {
      if (typeof(reject) == "function") {
        reject(response.body);
      }
    }
  }


  static handleErrorResponse(response, reject) {
    let status = response.status;
    let processor = false;
    if (typeof(reject) == "function") {
      processor = reject(response.body);
      if (processor) return;
    }

    if (status === 401) {
      //TODO 未登录，重定向到初始化页面
      console.log(response.body);
      Resources.redirectInit();
    }
  }

  static redirectInit() {
    let authRedirectUrl = process.env.AUTH_REDIRECT;

    let channelCode = localStorage.getItem("channelCode");
    let state = localStorage.getItem("state");
    if (channelCode) {
      authRedirectUrl = authRedirectUrl + "&dealerCode=" + channelCode;
    }

    window.location.href = authRedirectUrl;
  }
};

// export default new Resources("http://localhost:8888");
