/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.controller.pc.menu.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述：分页查询请求返回
 *
 * Created by yangkui on 2017-05-05 15:37:27.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class IconMenuEntityPageResp {
    private Long id;
    private String name;
    private String typeName;
    private String typeCode;
    private String imageUrl;
    private int order;
    private boolean recommend;
    private String recommendEndTime;
    private boolean redTip;
    private String href;
    private boolean requiredAuth;

}