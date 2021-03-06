/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentEntityHandleReq.java
 */
package com.ford.wechat.controller.pc.message.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageContentEntityHandleReq 数据新增/修改数据请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageContentEntityGetResp {

    private Long id;

    /**
     * 客户的VIN码（如果是公告消息，可以为空）
     */
    private String vin;

    /**
     * 标题
     */
    private String title;

    /**
     * 手机号（可选，针对短信发送时为必填）
     */
    private String mobile;

    /**
     * 消息种类，是公告消息还是个人消息
     */
    private String msgClass;

    /**
     * 消息类型，如：活动消息、推广消息。页面根据这个类型呈现不一样的样式
     */
    private String msgType;

    /**
     * 消息发送出去的推送渠道,多个渠道使用,分割，如：微信、短信平台、个人中心
     */
    private String pushChannel;

    /**
     * 消息发布时间
     */
    private String publishTime;

    /**
     * 消息失效时间
     */
    private String invalidTime;

    /**
     * 发送状态，待发送、发送中、发送成功、发送失败、部分失败
     */
    private String sendStatus;

    /**
     * 消息内容（由模板和参数值生成的最终内容）
     */
    private String content;

    /**
     * 消息模板名称
     */
    private String templateName;

    /**
     * 消息模板代码
     */
    private String templateCode;
}
