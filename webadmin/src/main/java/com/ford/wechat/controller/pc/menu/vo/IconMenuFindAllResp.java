/*
 * Copyright (c)  2017
 * All rights reserved.
 * IconMenuFindAllResp.java 2017-05-15 下午5:42
 */

package com.ford.wechat.controller.pc.menu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述:查找所有图标返回对象
 *
 * @author yangkui create on 2017-05-15 下午5:42.
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class IconMenuFindAllResp {
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