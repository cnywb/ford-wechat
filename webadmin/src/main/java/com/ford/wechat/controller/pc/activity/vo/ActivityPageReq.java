/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityPageReq.java
 */
package com.ford.wechat.controller.pc.activity.vo;

import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述：ActivityPageReq 数据分页查询请求对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ActivityPageReq {

    private GridPage page;

    /**
     * 时， 日
     */
    private String type;

    /**
     * 日期格式化 pattern
     */
    public String getPattern(){
        if("时".equals(getType())){
            return "yyyy-mm-dd HH24";
        }else{
            return "yyyy-mm-dd";
        }
    }

    /**
     * 统计起始时间
     */
    private Date startDate;

    /**
     * 统计截止时间
     */
    private Date endDate;

}
