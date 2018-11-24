/**
 * Created by Neel on 17/4/7.
 */
/**
 * 封装sessionStorage
 * @author Neel
 */
export default class Session {

  static Key = {
    MOBILE: "KEY_MOBILE",
    AUTHED: "KEY_AUTHED",
    VIN: "KEY_VIN",
    PHOTO_URL: "KEY_PHOTO_URL",
    CHANNEL_URL: "KEY_CHANNEL_URL",
    CHANNEL_TYPE: "KEY_CHANNEL_TYPE",
    DEALER: "KEY_DEALER",
    BINDING: "KEY_BINDING",
    UNBINDING: "KEY_UNBINDING"

  };

  static set(key, value) {
    window.sessionStorage.setItem(key, value);
  }

  static remove(key) {
    window.sessionStorage.removeItem(key);
  }

  static get(key) {
    let value = window.sessionStorage.getItem(key);
    if (value == 'undefined') {
      return null;
    }
    if (value == 'none') {
      return null;
    }
    return value;
  }

  static getBool(key) {
    return (/^true$/i).test(window.sessionStorage.getItem(key));
  }

  static clear() {
    window.sessionStorage.clear();
  }

}
