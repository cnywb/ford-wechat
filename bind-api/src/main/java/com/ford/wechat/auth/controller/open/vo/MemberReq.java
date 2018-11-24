package com.ford.wechat.auth.controller.open.vo;/**
 * Created by jovi on 05/05/2017.
 */

import lombok.Data;

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
public class MemberReq {
    /**
     * 微信唯一标识
     */
    private String openId;

    private String secret;


}
