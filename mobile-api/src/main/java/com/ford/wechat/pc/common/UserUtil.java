package com.ford.wechat.pc.common;

/**
 * Created by yangkui on 16/12/13.
 * 从当前线程当中获取用户OPENID
 */
public class UserUtil {
    private static ThreadLocal<String> openId = new ThreadLocal<>();

    public static void set(String personId) {
        openId.set(personId);
    }

    public static String get() {
        return openId.get();
    }

    public static void remove(){
        openId.remove();
    }
}
