/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntity.java 2017-05-16 19:31:48
 */
package com.ford.wechat.controller.pc.info.vo;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 描述：批量删除请求
 *
 * Created by ziv.hung on 2017-05-16 19:31:48.
 * @since 1.0
*/
@Data
@NoArgsConstructor
public class InfoEntityDeleteReq {
    private List<Long> ids;
}