package com.ford.wechat.controller.config.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by wanglijun on 16/11/2.
 */
public class RedirectConfigPageReq extends PageReq{
    /**
     * 微信入口状态参数
     */
    private String state;
    /**
     * 授权跳转的url
     */
    private String url;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
