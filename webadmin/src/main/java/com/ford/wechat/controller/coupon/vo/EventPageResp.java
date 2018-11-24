package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

/**
 * Created by zhaoliang on 2017/8/29.
 */

@Data
public class EventPageResp {

    private Long eventId;

    /**活动代码*/
    private String projectCode;

    /**活动名称*/
    private String projectName;

    /**祝福语*/
    private String wishing;

    /**备注*/
    private String remark;

}
