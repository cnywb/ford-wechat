/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * UserInfoReq.java
 */

package com.ford.wechat.pc.controller.information.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述: 车主数据更新请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class UserInfoHandleReq {

    /**用户名*/
    private String userName;

    /**性别*/
    private String gender;

    /**用户生日*/
    private String birthday;

    /**驾照号码*/
    private String license;

    /**联系电话*/
    private String mobile;

    /**联系地址*/
    private String address;

    /**联系email*/
    private String email;
}