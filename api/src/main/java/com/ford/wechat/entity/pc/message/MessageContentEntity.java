/*
 * Copyright (c)  2017
 * All rights reserved.
 * MessageContent.java 2017-05-08 上午10:25
 */

package com.ford.wechat.entity.pc.message;

import com.ford.wechat.entity.AuditEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:消息内容表，更加个性化的消息通过子类实现。这里只做统筹的字段
 *
 * @author yangkui create on 2017-05-08 上午10:25.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MS_MESSAGE_CONTENT")
public class MessageContentEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MS_MESSAGE_CONTENT_ID")
    @SequenceGenerator(name = "SEQ_MS_MESSAGE_CONTENT_ID", allocationSize = 1, sequenceName = "SEQ_MS_MESSAGE_CONTENT_ID")
    private Long id;

    /**
     * 用户的微信OPENID（如果是公告消息，可以为空）
     */
    @Column(name = "OPENID")
    private String openId;

    /**
     * 客户的VIN码（如果是公告消息，可以为空）
     */
    @Column(name = "VIN")
    private String vin;

    /**
     * 手机号（可选，针对短信发送时为必填）
     */
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * 公告内容标题
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * 消息种类，是公告消息还是个人消息
     */
    @Column(name = "MSG_CLASS")
    private String msgClass;


    /**
     * 消息类型，如：活动消息、推广消息。页面根据这个类型呈现不一样的样式
     */
    @Column(name = "MSG_TYPE")
    private String msgType;

    /**
     * 消息发送出去的推送渠道,多个渠道使用,分割，如：微信、短信平台、个人中心
     */
    @Column(name = "PUSH_CHANNEL")
    private String pushChannel = MessageEnum.PUSH_CHANNEL_GRZX;

    /**
     * 消息发布时间
     */
    @Column(name = "PUBLISH_TIME")
    private Date publishTime;

    /**
     * 消息失效时间
     */
    @Column(name = "INVALID_TIME")
    private Date invalidTime;

    /**
     * 发送状态，待发送、发送中、发送成功、发送失败、部分失败
     */
    @Column(name = "SEND_STATUS")
    private String sendStatus = MessageEnum.SEND_STATUS_WAIT;

    /**
     * 消息超链
     */
    @Column(name = "LINK")
    private String link;

    /**
     * 消息内容（由模板和参数值生成的最终内容）
     */
    @Column(name = "CONTENT")
    private String content;
    /**
     * 对应的模板消息ID。针对个人消息必须使用模板生成内容。
     */
    @ManyToOne
    @JoinColumn(name = "MESSAGE_TEMPLATE_ID")
    private MessageTemplateEntity messageTemplate;

    /**
     * 消息阅读总计，每个用户阅读一次都会增加一次。具体的增加方式由代码控制
     */
    @Column(name = "READ_COUNT")
    private Integer readCount = 0;
}