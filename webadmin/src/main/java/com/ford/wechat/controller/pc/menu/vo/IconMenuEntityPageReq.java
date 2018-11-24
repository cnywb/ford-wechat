/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.controller.pc.menu.vo;
import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述：分页查询请求
 *
 * Created by yangkui on 2017-05-05 15:37:27.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class IconMenuEntityPageReq {
    private GridPage page;
}