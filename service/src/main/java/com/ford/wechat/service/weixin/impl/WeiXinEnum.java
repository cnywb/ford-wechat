/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * WeiXinEnum.java
 */

package com.ford.wechat.service.weixin.impl;

/**
 * 描述: 微信相关静态参数定义管理类
 *
 * @author ziv
 * @since 1.0
 */
public class WeiXinEnum {
    /**
     * 响应异常代码
     */
    public static final String ERR_CODE = "errcode";
    /**
     * 响应异常信息
     */
    public static final String ERR_MSG = "errmsg";


    /**
     * 接口调用凭证
     */
    public static final String ACCESS_TOKEN = "access_token";
    /**
     * 微信普通客户唯一标示
     */
    public static final String OPEN_ID = "openid";


    /**
     * 昵称
     */
    public static final String USER_NICK_NAME = "nickname";
    /**
     * 性别
     */
    public static final String USER_SEX = "sex";
    /**
     * 微信头像
     */
    public static final String USER_HEAD_IMG_URL = "headimgurl";

    /**
     * 获取用户信息正确返回的全集字段
     {
     "openid":"OPENID",
     "nickname":"NICKNAME",
     "sex":1,
     "province":"PROVINCE",
     "city":"CITY",
     "country":"COUNTRY",
     "headimgurl": "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     "privilege":[
     "PRIVILEGE1",
     "PRIVILEGE2"
     ],
     "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     }
     */
}