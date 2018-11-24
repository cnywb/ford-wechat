package com.ford.wechat.controller.config.vo;

import com.ford.wechat.controller.vo.HandleReq;

/**
 * Created by wanglijun on 16/11/2.
 */
public class RedirectConfigHandleReq  extends HandleReq{
    /**
     * 主键
     */
    private Long id;
    /**微信入口状态参数*/
    private String state;

    /**授权跳转的URL，带有openid参数*/
    private String url;

    /**备注说明*/
    private String remark;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
