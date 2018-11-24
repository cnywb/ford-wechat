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
public class FindAllResp {
    private Long id;
    private String name;
    private Long typeId;
    private String typeName;
    private String imageUrl;
    private int order;
    /**
     * 所属分类的order
     */
    private int typeOrder;
    private boolean recommend;
    private String recommendEndTime;
    private boolean redTip;
    private String href;
    private boolean requiredAuth;

}
