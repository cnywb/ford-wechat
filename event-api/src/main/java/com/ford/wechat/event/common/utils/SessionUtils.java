package com.ford.wechat.event.common.utils;



import javax.servlet.http.HttpSession;

/**
 * Created by Richard on 16/8/23.
 */
public abstract class SessionUtils {

    public final static String USER_ID = "USER_ID";
    public final static String USER_DETAIL = "USER_DETAIL";

    private final static ThreadLocal<HttpSession> SESSION = new ThreadLocal<>();

    public final static ThreadLocal<HttpSession> getSession() {
        return SESSION;
    }

    public final static String getOpenId() {
        return SESSION.get() == null ? null : (String)SESSION.get().getAttribute(USER_ID);
    }

    public final static void set(String openId) {
        HttpSession session = SESSION.get();

        if(session != null) {
            session.setAttribute(USER_ID, openId);
        }
    }

    public final static void put(String key, Object object) {
        HttpSession session = SESSION.get();

        if(session != null) {
            session.setAttribute(key, object);
        }
    }
    public final static Object get(String key) {
        return SESSION.get() == null ? null : SESSION.get().getAttribute(key);
    }



}
