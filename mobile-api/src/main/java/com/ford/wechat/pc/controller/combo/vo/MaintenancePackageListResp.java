/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageListResp.java
 */

package com.ford.wechat.pc.controller.combo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:TODO
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class MaintenancePackageListResp {

    private String name;
    private String type;
    private String model;
    private String price;
    private String packageType;
    private String buyChance;
}