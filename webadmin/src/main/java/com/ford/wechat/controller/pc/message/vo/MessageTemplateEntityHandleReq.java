/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityHandleReq.java
 */
package com.ford.wechat.controller.pc.message.vo;


import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：MessageTemplateEntityHandleReq 数据新增/修改数据请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MessageTemplateEntityHandleReq {

    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板代码，微信模板采用微信系统的代码，自定义的模板添加C开头前缀，区分微信系统的模板代码
     */
    private String code;

    /**
     * 模板内容,内容参考微信的模板内容，关键位置采用占位符方式实现。
     */
    private String content;

    /**
     * 模板分类，通过数据字典实现
     */
    private String templateType;

    /**
     * 允许使用的渠道，如：微信，个人中心。多个渠道使用,分割。
     * 接收到发送消息的请求后需要校验传递过来的消息的发送渠道值是否在该模板允许使用的渠道。
     * 防止定义了一个自定义的模板，却要求发送给微信模板消息。
     */
    private String useChannel;

    /**
     * 消息模板参数对象
     */
    private List<MessageTemplateParamsEntityVo> params;
}
