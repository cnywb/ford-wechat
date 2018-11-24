package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

/**
 * Created by zhaoliang on 2017/9/25.
 */
@Data
public class CouponDmsHandleReq {

    private Long id;
    private String vin;
    private String targetDealer;
    private Integer sendCount;
    private String sendDmsStatus;
    private String cancel;

}
