/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * PCRedisKeys.java
 */

package com.ford.wechat.entity.pc;

/**
 * 描述: 个人中心相关redis key管理类
 *
 * @author ziv
 * @since 1.0
 */
public class PCRedisKeys {

    /**
     * 公告点击记录redis key
     * {0}日期年月日 存储对象为 MessageReadRecordEntity
     * PC:MS:READ:RECORD:{0}
     */
    public static final String MSG_READ_RECORD = "PC:MS:READ:RECORD:{0}";
    /**
     * 菜单点击记录redis key
     * {0}日期年月日 存储对象为 MenuReadRecordEntity
     * PC:MENU:READ:RECORD:{0}
     */
    public static final String MENU_READ_RECORD = "PC:MENU:READ:RECORD:{0}";
}