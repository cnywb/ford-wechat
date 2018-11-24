package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/29.
 */
@Data
public class EventDetailHandleReq {

    private Long eventId;

    private Long id;

    /**时间批次  yyyyMMdd*/
    private String dateNo ;

    /**活动代码*/
    private String projectCode;

    /**活动名称*/
    private String projectName;

    /**最大额度*/
    private Integer max;

    /**最小额度*/
    private Integer min;

    /**总金额*/
    private Integer totalAmount;

    /**份数*/
    private Integer count;

    /**门槛金额 界面暂时不需要配置*/
    private Integer lowestAmount;

    /**每日开始时间 HH-mm-ss*/
    private Date startTime;

    /**每日结束时间 HH-mm-ss*/
    private Date endTime;

    /**代金券有效天数*/
    private int validTimes;

    /**祝福语*/
    private String wishing;

    /**备注*/
    private String remark;
}
