/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageRecordHandleReq.java
 */
package com.ford.wechat.controller.pc.message.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述：MessageRecordHandleReq 数据新增/修改数据请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageRecordGetResp {

    private Long id;

    /**
     * 用户的微信OPENID
     */
    private String openId;

    /**
     * 客户的VIN码
     */
    private String vin;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 标题
     */
    private String title;

    /**
     * 消息种类，是公告消息还是个人消息
     */
    private String msgClass;

    /**
     * 消息类型，如：活动消息、推广消息。页面根据这个类型呈现不一样的样式
     */
    private String msgType;

    /**
     * 消息发布时间
     */
    private String publishTime;

    /**
     * 消息失效时间
     */
    private String invalidTime;

    /**
     * 消息发送出去的推送渠道，如：微信、短信平台、个人中心
     * 根据Content当中的渠道生成一条记录
     */
    private String pushChannel;

    /**
     * 消息发送出去的推送渠道，如：微信、短信平台、个人中心
     * 根据Content当中的渠道生成一条记录
     */
    private String content;
}
