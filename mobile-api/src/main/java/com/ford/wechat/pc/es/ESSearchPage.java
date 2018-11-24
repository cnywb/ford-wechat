/*
 * Copyright (c)  2017
 * All rights reserved.
 * ESSearchPage.java 2017-05-18 下午7:31
 */

package com.ford.wechat.pc.es;

import io.dabing.common.grid.GridPage;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:ES分页搜索服务
 *
 * @author yangkui create on 2017-05-18 下午7:31.
 * @since 1.0
 */
@Data
@NoArgsConstructor
public class ESSearchPage extends GridPage {

    /**
     * 资讯分类
     */
    private String type;

}