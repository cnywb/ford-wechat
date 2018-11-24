package com.ford.wechat.event.controller.coupon.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neel on 2017/9/4.
 */
@Data
@NoArgsConstructor
public class CouponResp {

    private String couponNo;

    private int validTimes;

    private Integer amount;

    /**用户VIN*/
    private List<String> vinList = new ArrayList<>();

}
