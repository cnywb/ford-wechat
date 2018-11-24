/**
 * Created by Richard on 17/4/7.
 */
/**
 * Toast，便于统一处理请求和返回
 */

export default class Utils {

  constructor() {

  };

  static isWeixin() {
    return (/micromessenger/i).test(navigator.userAgent.toLowerCase());
  }
  static isProd() {
    return process.env.NODE_ENV == "production";
  }
  static isDev() {
    return process.env.NODE_ENV == "development";
  }
  static isUat() {
    return process.env.NODE_ENV == "uat";
  }

};
