/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * AuthenticationInterceptor.java
 */

package com.ford.wechat.pc.interceptor;

import io.dabing.common.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Enumeration;

/**
 * 校验请求的路径当前会话是否有效
 *
 * @author yangkui
 * @since 1.0
 */
public class AuthenticationExternalInterceptor implements HandlerInterceptor {
    public static final String SALT = "dbford618pcggaoi";//默认盐
    public static final String SALT_MD5 = "cc2e29acde60dea29fef0fa428888ff4";//默认盐加密
    public static final String ACCESS_TOKEN = "accessToken";

    static Logger logger = LoggerFactory.getLogger(AuthenticationExternalInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration headers = request.getHeaders(ACCESS_TOKEN);
        String accessTokenReq = "";
        while (headers.hasMoreElements()) {
            accessTokenReq = (String) headers.nextElement();
        }
        boolean flag = true;
        if (StringUtils.isEmpty(accessTokenReq) || !checkPassAccessToken(accessTokenReq)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            response.getWriter().write("{\"status\":\"403\",\"message\":\"非法请求\"}");
            flag = false;
        }
        logger.info("合法性校验 preHandle，accessToken：{},flag:{}", accessTokenReq, flag);
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private Boolean checkPassAccessToken(String accessTokenReq) {
        String accessTokenToday = SALT_MD5 + DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATE_YYYYMMDD);
        String accessTokenTomorrow = SALT_MD5 + DateUtil.formatDate(DateUtil.getNextDay(new Date(), 1), DateUtil.FORMAT_DATE_YYYYMMDD);
        String accessTokenYesterday = SALT_MD5 + DateUtil.formatDate(DateUtil.getNextDay(new Date(), -1), DateUtil.FORMAT_DATE_YYYYMMDD);
        String apiAccessTokenToday = md5(accessTokenToday);
        String apiAccessTokenTomorrow = md5(accessTokenTomorrow);
        String apiAccessTokenYesterday = md5(accessTokenYesterday);
        if (apiAccessTokenToday.equals(accessTokenReq) || apiAccessTokenTomorrow.equals(accessTokenReq) || apiAccessTokenYesterday.equals(accessTokenReq)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static void main(String[] args) {
        String accessTokenToday = SALT_MD5 + DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATE_YYYYMMDD);

        String apiAccessTokenToday = md5(accessTokenToday);
        System.out.println(apiAccessTokenToday);
    }

    public static String md5(String str) {
        StringBuffer hexValue = new StringBuffer();
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int md5Bytes = 0; md5Bytes < charArray.length; ++md5Bytes) {
                byteArray[md5Bytes] = (byte) charArray[md5Bytes];
            }

            byte[] var10 = e.digest(byteArray);

            for (int i = 0; i < var10.length; ++i) {
                int val = var10[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5加密失败：", e.getMessage(), e);
        }
        return hexValue.toString();
    }

}