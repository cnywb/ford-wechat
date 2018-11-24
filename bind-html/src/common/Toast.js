/**
 * Created by Richard on 17/4/7.
 */
/**
 * Toast，便于统一处理请求和返回
 */
import Vue from "vue";

export default class Toast {

  constructor() {

  };

  static success(text) {
    // Vue.toast('Can I have everybody`s attention?');
    Vue.toast(text, {
      parent: '#toast-container',
      className: 'et-info',
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000,
      mode: 'override',
      transition: 'fade'
    })
  }

  static warn(text) {
    Vue.toast(text, {
      parent: '#toast-container',
      className: 'et-warn',
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000,
      mode: 'override',
      transition: 'fade'
    })
  }
  static error(text) {
    Vue.toast(text, {
      parent: '#toast-container',
      className: 'et-alert',
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000,
      mode: 'override',
      transition: 'fade'
    })
  }
  static show(text) {
    Vue.toast(text, {
      parent: '#toast-container',
      horizontalPosition: 'center',
      verticalPosition: 'top',
      duration: 2000,
      mode: 'override',
      transition: 'fade'
    })
  }
};

// export default new Resource("http://localhost:8888");
