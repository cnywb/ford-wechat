/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * AuthenticationInterceptor.java
 */

package com.ford.wechat.pc.interceptor;

import com.ford.wechat.pc.common.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 校验请求的路径当前会话是否有效
 * @author yangkui
 * @since 1.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public static final String SALT = "dabing";//默认盐
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String SESSION_TOKEN = "mobileApiSessionToken";
    public static final String M_KEY = "dbSmyApi";

    static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object o = request.getSession().getAttribute(SESSION_TOKEN);
        if (o == null) {
            response.setCharacterEncoding("UTF-8");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "未登陆");
            return false;
        }
        String openId = (String) o;
        //当前线程上下文存入用户标识
        UserUtil.set(openId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程，防止线程数据污染
        UserUtil.remove();
    }
}