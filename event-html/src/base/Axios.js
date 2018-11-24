/**
 * Created by Richard on 17/4/7.
 */
/**
 * Toast，便于统一处理请求和返回
 */

import axios from 'axios'
import {Toast, Indicator} from 'mint-ui';


export default class Axios {

  constructor() {

  };

  static init() {
    // 添加一个请求拦截器
    axios.interceptors.request.use(function (config) {
      // Do something before request is sent

      if (config.showLoading == undefined || config.showLoading === true) {
        Indicator.open({spinnerType: 'triple-bounce'});
      }
      return config;
    }, function (error) {
      Indicator.close();
      // Do something with request error
      return Promise.reject(error);
    });

    // 添加一个响应拦截器
    axios.interceptors.response.use(function (response) {
      Indicator.close();
      if (response.status == 200 && response.data.code == "OK") {
        return response.data;
      } else {
        return Promise.reject(response.data);
      }
    }, function (error) {
      Indicator.close();
      if (error.response.status == 401) {
        window.location.href = process.env.LOGIN_URL;
      }
      return Promise.reject(error.response.data);
    });
  }
};
