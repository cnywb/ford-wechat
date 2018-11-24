package com.ford.wechat.event.controller.coupon.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Neel on 2017/9/4.
 */
@Data
@NoArgsConstructor
public class DrawReq {

    private String vin;

    private String couponNo;

}
