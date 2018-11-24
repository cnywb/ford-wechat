package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Data
public class EventDetailPageResp {

    private Long id;

    /**时间批次  yyyyMMdd*/
    private String dateNo ;

    /**活动代码*/
    private String projectCode;

    /**活动名称*/
    private String projectName;

    /**最大额度*/
    private Integer max = 0;

    /**最小额度*/
    private Integer min = 0;

    /**总金额*/
    private Integer totalAmount = 0;

    /**份数*/
    private Integer count = 0;

    /**门槛金额 界面暂时不需要配置*/
    private Integer lowestAmount = 0;

    /**每日开始时间 HH-mm-ss*/
    private Date startTime;

    /**每日结束时间 HH-mm-ss*/
    private Date endTime;

    /**代金券有效天数*/
    private int validTimes = 31;

    /**祝福语*/
    private String wishing;

    /**备注*/
    private String remark;
}
