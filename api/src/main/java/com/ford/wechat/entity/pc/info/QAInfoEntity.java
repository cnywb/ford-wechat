/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-11 下午9:37
 */

package com.ford.wechat.entity.pc.info;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:互动问答数据
 *
 * @author yangkui create on 2017-05-11.
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "PC_QA_INFO")
public class QAInfoEntity extends AuditEntity {
    /**
     * 物理主键
     */
    @javax.persistence.Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PC_QA_INFO_ID")
    @SequenceGenerator(name = "SEQ_PC_QA_INFO_ID", allocationSize = 1, sequenceName = "SEQ_PC_QA_INFO_ID")
    private Long id;

    /**
     * 问题描述
     */
    @Column(name = "QUESTION")
    private String question;
    /**
     * 问题答案
     */
    @Column(name = "ANSWER")
    private String answer;

    /**
     * 问题匹配标签，关键字
     */
    @Column(name = "TAGS")
    private String tags;

    /**
     * 是否已经索引过，数据是否已经在ES服务当中
     * 新创建和修改都需要重新建立索引
     */
    @Column(name = "INDEXED")
    private boolean indexed = false;

    /**
     * ES索引ID
     */
    @Column(name = "ES_ID")
    private String esId;

}