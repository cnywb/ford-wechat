package com.ford.wechat.controller.auth.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/26.
 */
@Data
public class AuthToDmsPageResp {

    private Long id;
    /**
     * 批次号
     */
    private String batchNo;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 车主姓名
     */
    private String name;
    /**
     * 车主电话
     */
    private String mobile;
    /**
     * VIN码
     */
    private String vin;
    /**
     * 微信唯一标识
     */
    private String openId;
    /**
     * 认证时间
     */
    private Date dcrtDate;
    /**
     * 渠道类型
     */
    private Integer channelType;
    /**
     * 渠道代码
     */
    private String channelCode;
    /**
     * 渠道名称
     */
    private String channelName;
    /**
     * 经销商代码
     */
    private String dealerServiceCode;
    /**
     * 经销商名称
     */
    private String dealerName;
    /**
     * 日期批次
     */
    private String dateNo;
    /**
     * 大区
     */
    private String bigArea;

    /**
     * 小区
     */
    private String smallArea;

    /**
     * 认证状态
     */
    private Integer verify;

    /**
     * 关注状态
     */
    private Integer follow;

    /**
     * 有效性
     */
    private Integer valid;

    /**
     * 是否经销商扫码
     */
    private Integer dealerScan;

    /**
     * 备用字段
     */
    private String remark;

    /**
     * 发送dms时间
     */
    private Date sendDmsDate;

    /**
     * 发送dms状态
     */
    private Integer sendDmsStatus;

    /**
     * 解绑时间
     */
    private Date unauthDate;

    /**
     * 发送dms结果
     */
    private String sendDmsResult;
}
