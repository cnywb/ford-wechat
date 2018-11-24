package com.ford.wechat.auth.controller.open.vo;/**
 * Created by jovi on 05/05/2017.
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-05 13:16
 * </p>
 *
 * @author Neel
 * @version 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MemberVinResp {

    /**
     * vin码
     */
    private String vin;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 客户名称
     */
    private String name;

    private Long userId;
    /**
     * 认证时间
     */
    private Date dcrtDate;

    /** Neel */

    /**
     * 会员号
     */
    private String memberNo;

    private String openId;

    private String address;
}
