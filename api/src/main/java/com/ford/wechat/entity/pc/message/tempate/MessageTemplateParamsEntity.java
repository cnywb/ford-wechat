/*
 * Copyright (c)  2017
 * All rights reserved.
 * MessageTemplateParams.java 2017-05-09 下午4:22
 */

package com.ford.wechat.entity.pc.message.tempate;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:模板消息对应的参数列表
 *
 * @author yangkui create on 2017-05-09 下午4:22.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MS_MESSAGE_TEMPLATE_PARAMS")
public class MessageTemplateParamsEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MS_MSG_TEMPLATE_PARAMS_ID")
    @SequenceGenerator(name = "SEQ_MS_MSG_TEMPLATE_PARAMS_ID", allocationSize = 1, sequenceName = "SEQ_MS_MSG_TEMPLATE_PARAMS_ID")
    private Long id;


    /**
     * 参数key值，用于模板内容当中的占位符,如：name、amount
     */
    @Column(name = "PARAM_KEY")
    private String paramKey;

    /**
     * key描述，用于描述这个key的含义
     */
    @Column(name = "KEY_DESC")
    private String keyDesc;

    /**
     * 所属的模板
     */
    @Column(name = "TEMPLATE_ID")
    private Long templateId;

}