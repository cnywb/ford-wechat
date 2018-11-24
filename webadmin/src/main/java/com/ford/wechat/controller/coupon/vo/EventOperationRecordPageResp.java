package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Data
public class EventOperationRecordPageResp {

    /**
     * 主键
     */
    private Long id;

    /**
     * 时间批次
     */
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
     * 绑定OPENID
     */
    private String openId;

    /**
     * 操作类型  0 访问页面  1 点击抽奖
     */
    private Integer operationType;

    /**
     * 最后记录时间
     */
    private Date lastOperationTime;
}
