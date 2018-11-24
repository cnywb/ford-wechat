/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainPageReq.java
 */

package com.ford.wechat.controller.pc.complain.vo;

import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述:TODO
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ComplainPageReq {

    private GridPage page;
    private Date startDate;
    private Date endDate;

}