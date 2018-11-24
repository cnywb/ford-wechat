package com.ford.wechat.controller.channel.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * All rights reserved. 2017-05-04 14:16
 * </p>
 *
 * @author zhaoliang
 * @version 1.0
 */
public class WeChannelGetReq extends PageReq {
    private String dealerCode;
    private String code;
    private String name;
    private Integer type;

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
