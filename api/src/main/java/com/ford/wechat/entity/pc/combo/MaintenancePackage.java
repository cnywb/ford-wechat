/**
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrder.java
 */
package com.ford.wechat.entity.pc.combo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 描述:MaintenancePackage 原厂保养
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "MAINTENANCE_PACKAGE")
public class MaintenancePackage {
    /**
     * 物理主键
     */
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAIN_PACKAGE_ID")
    @SequenceGenerator(name = "SEQ_MAIN_PACKAGE_ID", allocationSize = 1, sequenceName = "SEQ_MAIN_PACKAGE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "PRICE")
    private String price;

    @Column(name = "PACKAGE_TYPE")
    private String packageType;

    @Column(name = "BUY_CHANCE")
    private String buyChance;
}
