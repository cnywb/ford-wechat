/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComboListReq.java
 */

package com.ford.wechat.pc.controller.combo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ComboListReq {
    private String packageType;
    private String model;
}