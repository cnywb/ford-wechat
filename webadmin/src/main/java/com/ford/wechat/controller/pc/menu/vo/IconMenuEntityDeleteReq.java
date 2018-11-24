/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.controller.pc.menu.vo;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 描述：批量删除请求
 *
 * Created by yangkui on 2017-05-05 15:37:27.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class IconMenuEntityDeleteReq {
    private List<Long> ids;
}