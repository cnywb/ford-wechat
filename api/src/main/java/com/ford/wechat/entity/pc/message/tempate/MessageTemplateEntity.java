/*
 * Copyright (c)  2017
 * All rights reserved.
 * MessageTemplate.java 2017-05-09 下午4:17
 */

package com.ford.wechat.entity.pc.message.tempate;

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.im.ImCodeList;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:消息模板，用来定义页面显示的模板和微信模板
 * 其他系统调用这个平台进行发送消息时，需要制定模板内容，如果是微信消息，则指定的是微信的模板
 * 对应微信模板需要在本地同步配置一遍，参数与微信的保持一致。
 *
 * @author yangkui create on 2017-05-09 下午4:17.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MS_MESSAGE_TEMPLATE")
public class MessageTemplateEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MS_MESSAGE_TEMPLATE_ID")
    @SequenceGenerator(name = "SEQ_MS_MESSAGE_TEMPLATE_ID", allocationSize = 1, sequenceName = "SEQ_MS_MESSAGE_TEMPLATE_ID")
    private Long id;

    /**
     * 模板名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 模板代码，微信模板采用微信系统的代码，自定义的模板添加C开头前缀，区分微信系统的模板代码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 模板内容,内容参考微信的模板内容，关键位置采用占位符方式实现。
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * 模板分类，通过数据字典实现
     */
    @Column(name = "TEMPLATE_TYPE")
    private String templateType;


    /**
     * 允许使用的渠道，如：微信，个人中心。多个渠道使用,分割。
     * 接收到发送消息的请求后需要校验传递过来的消息的发送渠道值是否在该模板允许使用的渠道。
     * 防止定义了一个自定义的模板，却要求发送给微信模板消息。
     */
    @Column(name = "USE_CHANNEL")
    private String useChannel;

}