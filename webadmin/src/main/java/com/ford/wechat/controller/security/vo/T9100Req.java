/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved. 
 * T9100.java 15/10/31 上午9:49
 */
package com.ford.wechat.controller.security.vo;

import io.dabing.common.grid.GridPage;

/**
 * 描述：查找角色
 *
 * @author yangkui on 15/10/31.
 * @since 1.0
 */
public class T9100Req {
    private GridPage page;

    public GridPage getPage() {
        return page;
    }

    public void setPage(GridPage page) {
        this.page = page;
    }
}