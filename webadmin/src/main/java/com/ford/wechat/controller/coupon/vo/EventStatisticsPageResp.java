package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Data
public class EventStatisticsPageResp {

    /**
     * 主键
     */
    private Long id;
    /**
     * 批次号
     */
    private String batchNo ;
    /**时间批次  yyyyMMdd*/
    private String dateNo ;
    /**
     * 活动代码
     */
    private String projectCode;
    /**
     * 活动名称
     */
    private String projectName;
    /**
     * 每日访问人数
     */
    private Integer visitCount;
    /**
     * 每次人抽奖人数
     */
    private Integer drawCount;
    /**
     * 短信发送数
     */
    private Integer smsSendCount;
    /**
     * 短信发送失败数量
     */
    private Integer smsFailedCount;
    /**
     * 每日认证人数
     */
    private Integer authenCount;
    /**
     * 因活动认证人数
     */
    private Integer authenCountWithEvent;
    /**
     * 抽中人数
     */
    private Integer winnerCount;
    /**
     * 老认证用户获券人数
     */
    private Integer originalWinnerCount;
}
