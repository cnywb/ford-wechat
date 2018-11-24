/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentEntityPageResp.java
 */
package com.ford.wechat.controller.pc.message.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MessageContentEntityPageResp 数据分页查询响应对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageContentEntityPageResp {

    private Long id;

    /**
     * 客户的VIN码（如果是公告消息，可以为空）
     */
    private String vin;

    /**
     * 手机号（可选，针对短信发送时为必填）
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
     * 对应的模板消息ID。针对个人消息必须使用模板生成内容。
     */
    private Long messageTemplateId;

    /**
     * 消息阅读总计，每个用户阅读一次都会增加一次。具体的增加方式由代码控制
     */
    private Integer readCount;
}
