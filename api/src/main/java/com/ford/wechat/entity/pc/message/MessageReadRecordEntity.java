/*
 * Copyright (c)  2017
 * All rights reserved.
 * MessageReadRecordEntity.java 2017-05-10 下午7:52
 */

package com.ford.wechat.entity.pc.message;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * 描述:消息阅读记录，不需要集成无用的类
 * 用户阅读消息后，后台接口将阅读记录存入redis当中。后续通过批处理方式将数据转存入该表当中，用于之后的行为分析和统计
 *
 * @author yangkui create on 2017-05-10 下午7:52.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MS_MESSAGE_READ_RECORD")
public class MessageReadRecordEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MS_MSG_READ_RECORD_ID")
    @SequenceGenerator(name = "SEQ_MS_MSG_READ_RECORD_ID", allocationSize = 1, sequenceName = "SEQ_MS_MSG_READ_RECORD_ID")
    private Long id;
    /**
     * 消息ID
     */
    @Column(name = "MSG_ID")
    private Long msgId;

    /**
     * 消息标题
     */
    @Column(name = "MSG_TITLE")
    private String msgTitle;

    /**
     * 阅读时间
     */
    @Column(name = "READ_TIME")
    private Date readTime;

    /**
     * 记录来源：个人中心或其他渠道
     */
    @Column(name = "SOURCE_CHANNEL")
    private String sourceChannel = MessageEnum.SOURCE_CHANNEL_GRZX;
}