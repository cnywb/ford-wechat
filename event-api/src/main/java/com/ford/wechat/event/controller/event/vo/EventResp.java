package com.ford.wechat.event.controller.event.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 2017/9/2.
 */
@Data
@NoArgsConstructor
public class EventResp {

    /**剩余份数*/
    private int surplus = 0;

    /**总份数*/
    private int total = 0;

    /**状态*/
    private int status;

    /**是否准备开始（开奖前5分钟）*/
    private boolean prepare = false;

    /**是否已经开始*/
    private boolean begun = false;

    /**是否已经结束*/
    private boolean ended = false;

    /**是否已中奖*/
    private boolean involved = false;

    /**是否需要绑定VIN*/
    private boolean needCouponBind = false;

    /**是否已认证*/
    private boolean bind = false;

    /**认证url*/
    private String bindUrl;

    /**下次可以参与抽奖的时间*/
    private String nextDate;

    /**开始时间*/
    private String startTime;

    /**结束时间*/
    private String endTime;

    /**最新中奖动态*/
    private String recentNews;

    /**活动代码*/
    private String eventCode;

    /**活动名称*/
    private String eventName;
}
