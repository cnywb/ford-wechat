package com.ford.wechat.entity.batch;

/**
 * Created by jovi on 9/2/16.
 */
public enum BatchType {

    /*-----------------------------------------ODP------------------------------------------*/

    //认证激励每日代金券生成
    GENERATE_COUPON {public String getName(){return "认证激励每日代金券生成";}},

    COUPON_EVENT_STATISTICS {public String getName(){return "认证激励统计";}},

    COUPON_SEND_DMS {public String getName(){return "认证激励代金券下发DMS";}},

    //认证数据发送DMS
    AUTH_TO_DMS {public String getName(){return "认证数据发送DMS";}},
    //认证数据生成CSV
    AUTH_TO_CSV {public String getName(){return "认证数据生成CSV";}},
    //自动认证
    AUTO_AUTH {public String getName(){return "自动认证";}},
    //每月认证报告
    MONTH_VERIFY_REPORT {public String getName(){return "每月认证报告";}},
    //每日认证报告
    DAY_AUTH_REPORT {public String getName(){return "每日认证报告";}},
    //主机厂数据导入
    DMS_FACTORY_IMPORT {public String getName(){return "DMS_FACTORY_IMPORT";}},

    //浏览记录保存至DB
    BROWSE_MSG_TO_DB {public String getName(){return "公告预览记录保存至DB";}},

    //菜单点击记录保存至DB
    BROWSE_MENU_TO_DB {public String getName(){return "菜单点击记录保存至DB";}};


    public abstract String getName();
}
