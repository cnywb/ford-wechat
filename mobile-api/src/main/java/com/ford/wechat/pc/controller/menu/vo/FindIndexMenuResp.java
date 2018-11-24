/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * FindAllResp.java 2017-05-07 上午11:45
 */

package com.ford.wechat.pc.controller.menu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:菜单列表
 *
 *
 * @author yangkui create on 2017-05-07.
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class FindIndexMenuResp {
    /**
     * 图标名称，用于页面显示的中文名称
     */
    private String name;
    /**
     * 图标在首页上显示的位置，从大到小依次排序。
     * 这是系统默认排序，每个用户自己的排序根据另外一个关系获取
     */
    private int order;

    /**
     * 图标图片地址，可以是相对路径，也可以是绝对路径
     */
    private String imageUrl;
    /**
     * 跳转链接地址，可以是基于路由的地址，也可以是http协议的绝对路径地址
     */
    private String href;

    private Long id;

    private boolean redTip;

    private boolean requiredAuth;



}
