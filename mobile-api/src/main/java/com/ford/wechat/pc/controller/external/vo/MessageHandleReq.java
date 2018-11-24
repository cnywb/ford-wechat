/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageHandleReq.java
 */

package com.ford.wechat.pc.controller.external.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 描述: 外部公告发起请求对象
 *
 * @author Yvan
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageHandleReq implements Serializable {

    /**
     * 消息模板代码
     */
    private String msgTemplateCode;

    /**
     * 来源渠道
     */
    private String channel;

    /**
     * 微信消息：客户openId（个人消息必填）
     */
    private String openId;

    /**
     * 客户的VIN码（个人消息必填）
     */
    private String vin;

    /**
     * 公告内容标题
     */
    private String title;

    /**
     * 消息种类，是公告消息：2 还是个人消息 1
     */
    private String msgClass;

    /**
     * 消息发布时间
     */
    private Date publishTime;

    /**
     * 消息失效时间
     */
    private Date invalidTime;

    /**
     * 消息内容或概要
     */
    private String content;

    /**
     * 消息超链 或 微信消息：微信消息提醒点击跳转目标地址
     */
    private String link;

    /**
     * 消息模板参数，key要求跟模板中的key一致。
     */
    private Map<String, String> msgTemplateParamMap;
}