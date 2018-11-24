package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo.HandleReq;

/**
 * Created by wanglijun on 16/11/2.
 */

public class UserInfoHandleReq extends HandleReq{
    /**
     * 微信唯一标识
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
