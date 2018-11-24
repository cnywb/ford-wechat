/*
 * Copyright (c)  2017
 * All rights reserved.
 * BaseMessage.java 2017-05-06 下午12:07
 */

package com.ford.wechat.entity.pc.message;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:消息记录表，Content表当中的一条记录如果有多个渠道，则在这里有多条记录。相当于记录每个渠道的发送明细记录
 *
 * @author yangkui create on 2017-05-06 下午12:07.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MS_MESSAGE_RECORD")
public class MessageRecordEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MS_MESSAGE_RECORD_ID")
    @SequenceGenerator(name = "SEQ_MS_MESSAGE_RECORD_ID", allocationSize = 1, sequenceName = "SEQ_MS_MESSAGE_RECORD_ID")
    private Long id;

    /**
     * 用户的微信OPENID
     */
    @Column(name = "OPENID")
    private String openId;

    /**
     * 客户的VIN码
     */
    @Column(name = "VIN")
    private String vin;

    /**
     * 手机号
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
     * 消息超链
     */
    @Column(name = "LINK")
    private String link;

    /**
     * 消息发送内容
     */
    @ManyToOne
    @JoinColumn(name = "CONTENT_ID")
    private MessageContentEntity content;

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
     * 消息发送出去的推送渠道，如：微信、短信平台、个人中心
     * 根据Content当中的渠道生成一条记录
     */
    @Column(name = "PUSH_CHANNEL")
    private String pushChannel = MessageEnum.PUSH_CHANNEL_GRZX;

    /**
     * 发送状态（待发送、发送中、发送成功、发送失败、部分失败）
     */
    @Column(name = "SEND_STATUS")
    private String sendStatus = MessageEnum.SEND_STATUS_WAIT;

    /**
     * 发送结果（主要记录失败原因）
     */
    @Column(name = "SEND_RESULT")
    private String sendResult;


/*-----------------接口调用使用-----------------*/

    /**
     * 消息发送的来源，可以是接口调用，也可以使人工发送，用于记录一下。
     */
    @Column(name = "MSG_SOURCE")
    private String msgSource = MessageEnum.MSG_SOURCE_CUSTOMER;

    /**
     * 消息来源渠道（由谁调用的）
     */
    @Column(name = "SOURCE_CHANNEL")
    private String sourceChannel = MessageEnum.SOURCE_CHANNEL_GRZX;
}