/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * AuthenticationInterceptor.java
 */

package com.ford.wechat.auth.interceptor;

import com.ford.wechat.auth.common.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 校验请求的路径当前会话是否有效
 * @author yangkui
 * @since 1.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    public static final String SALT = "dabing";//默认盐
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String SESSION_TOKEN = "sessionToken";
    public static final String M_KEY = "dbSmyApi";

    static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        Object openId = session.getAttribute(SessionUtils.USER_INFO);
        if (openId != null) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        response.sendError(HttpStatus.UNAUTHORIZED.value(), "未登陆");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}