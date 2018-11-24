/*
 * Copyright (c)  2017
 * All rights reserved.
 * LoginResp.java 2017-05-17 下午7:31
 */

package com.ford.wechat.pc.controller.user.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述:登录返回数据
 *
 * @author yangkui create on 2017-05-17 下午7:31.
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class LoginResp {

    /**
     * 用户绑定的vin码
     */
    private String vin;
    /**
     * 是否认证
     */
    private boolean isAuth;

    /**
     * 编号
     */
    private String userNum;

    /**
     * 微信头像
     */
    private String headImg;

    private List<String> vinList;

    private String name;
    private String mobile;
}