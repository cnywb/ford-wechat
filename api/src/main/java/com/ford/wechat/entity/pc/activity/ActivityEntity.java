/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * Activity.java
 */

package com.ford.wechat.entity.pc.activity;

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
@Table(name = "PC_ACTIVITY")
public class ActivityEntity extends AuditEntity {

    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PC_ACTIVITY_ID")
    @SequenceGenerator(name = "SEQ_PC_ACTIVITY_ID", allocationSize = 1, sequenceName = "SEQ_PC_ACTIVITY_ID")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "IMAGE_URL", nullable = false)
    private String imageUrl;
    @Column(name = "HREF", nullable = false)
    private String href;
    @Column(name = "SORT_NO", nullable = false)
    private Integer sortNo;
}