/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * EmailConfig.java
 */

package com.ford.wechat.entity.pc.complain;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "PC_EMAIL_CONFIG")
public class EmailConfigEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PC_EMAIL_CONFIG")
    @SequenceGenerator(name = "SEQ_PC_EMAIL_CONFIG", allocationSize = 1, sequenceName = "SEQ_PC_EMAIL_CONFIG")
    private Long id;

    @Column(name = "CODE", nullable = false, unique = true)
    private String code;

    @Column(name = "SEND_EMAIL", nullable = false)
    private String sendEmail;

    @Column(name = "CC_EMAIL")
    private String ccEmail;

    @Column(name = "HISTORY_EMAIL", nullable = false)
    private String historyEmail;

    @Column(name = "HISTORY_CC_EMAIL")
    private String historyCCEmail;

    @Column(name = "HISTORY_DATE", nullable = false)
    private String historyDate;
}