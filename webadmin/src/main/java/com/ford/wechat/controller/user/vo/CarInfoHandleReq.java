package com.ford.wechat.controller.user.vo;

import com.ford.wechat.controller.vo.HandleReq;

/**
 * Created by wanglijun on 16/11/2.
 */
public class CarInfoHandleReq  extends HandleReq{

    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
