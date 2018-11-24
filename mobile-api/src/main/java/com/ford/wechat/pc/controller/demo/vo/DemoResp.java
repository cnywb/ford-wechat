/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ProductPageResp.java
 */
package com.ford.wechat.pc.controller.demo.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：Demo
 *
 * @author xiangli.ma
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class DemoResp {

    private Long id;

    private String code;

    private String name;

    private int rank;

    public DemoResp(Long id, String code, String name, int rank) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.rank = rank;
    }

}