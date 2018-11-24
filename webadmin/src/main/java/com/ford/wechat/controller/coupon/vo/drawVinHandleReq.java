package com.ford.wechat.controller.coupon.vo;

import lombok.Data;

/**
 * Created by zhaoliang on 2017/9/12.
 */
@Data
public class drawVinHandleReq {

    private String dateNo;
    private String openId;
    private String projectCode;
    private String mobile;
    private String vin;
    private String couponNo;

}
