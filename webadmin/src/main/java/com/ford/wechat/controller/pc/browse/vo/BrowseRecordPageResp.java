/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MenuRecordPageResp.java
 */
package com.ford.wechat.controller.pc.browse.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：MenuRecordPageResp 数据分页查询响应对象
 *
 * @author ziv
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class BrowseRecordPageResp {

    /**
     * 菜单ID
     */
    private Object id;

    /**
     * 菜单名称
     */
    private Object name;

    /**
     * 菜单排序值
     */
    private Object sort;

    /**
     * 点击时间 年月日
     */
    private Object readTime;

    private Object times;
}
