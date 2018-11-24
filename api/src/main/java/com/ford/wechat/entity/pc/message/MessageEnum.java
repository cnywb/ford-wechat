/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageEnum.java
 */

package com.ford.wechat.entity.pc.message;

/**
 * 描述: 消息信息 枚举数据管理类
 *
 * @author ziv
 * @since 1.0
 */
public class MessageEnum {

    /*-----消息类型-----*/
    /**
     * 活动消息
     */
    public static final String MSG_TYPE_HDXX = "活动消息";
    /**
     * 推广消息
     */
    public static final String MSG_TYPE_TGXX = "推广消息";

    /*-----推送渠道--- 同db-im-data.json里的 useChannel 以及相同与消息模板中的 useChannel--*/

    /**
     * 微信平台
     */
    public static final String PUSH_CHANNEL_WXPT = "微信平台";
    /**
     * 短信平台
     */
    public static final String PUSH_CHANNEL_DXPT = "短信平台";
    /**
     * 个人中心
     */
    public static final String PUSH_CHANNEL_GRZX = "个人中心";


    /*-----发送状态-----*/

    /**
     * 待发送
     */
    public static final String SEND_STATUS_WAIT = "待发送";
    /**
     * 发送中
     */
    public static final String SEND_STATUS_ING = "发送中";
    /**
     * 发送成功
     */
    public static final String SEND_STATUS_SUCCESS = "发送成功";
    /**
     * 发送失败
     */
    public static final String SEND_STATUS_FAIL = "发送失败";
    /**
     * 部分失败
     */
    public static final String SEND_STATUS_HAVE_FAIL = "部分失败";

    /*-----模板类型（消息种类）  同消息内容里的msgClass-----*/

    /**
     * 个人消息
     */
    public static final String TEMPLATE_TYPE_GR = "个人消息";
    /**
     * 公告消息
     */
    public static final String TEMPLATE_TYPE_GG = "公告消息";


    /*-----模板类型（消息种类）  同消息内容里的msgClass-----*/

    /**
     * 个人消息
     */
    public static final String MSG_CLASS_GR = "个人消息";
    /**
     * 公告消息
     */
    public static final String MSG_CLASS_GG = "公告消息";



    /*-----消息发送的来源-----*/

    /**
     * 人工发送
     */
    public static final String MSG_SOURCE_CUSTOMER = "人工发送";
    /**
     * 接口调用
     */
    public static final String MSG_SOURCE_INTERFACE = "接口调用";




    /*-----消息来源渠道-----*/
    /**
     * 个人中心
     */
    public static final String SOURCE_CHANNEL_GRZX = "个人中心";

}