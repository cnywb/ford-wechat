package com.ford.wechat.batch.item.processor.coupon;
/**
 * Created by Neel on 26/03/2017.
 */

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.entity.message.MessageSend;
import com.ford.wechat.service.auth.AuthToDmsService;
import com.ford.wechat.service.coupon.CouponDmsService;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.mdao.SmsService;
import com.ford.wechat.service.message.MessageSendService;
import io.dabing.common.util.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-03-26 15:14
 * </p>
 *
 * @author Neel
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class CouponClearProcessor implements ItemProcessor<Coupon, CouponDms> {


    public static final String AUTH_CONPON_MESSAGE_TEMPLATE = "[长安福特]您参与“豪送1500万，人人有礼”活动获得了一张%s元代金券已到账，请于%s前至任意长安福特授权经销商使用。回T退订";

    /**
     * 批次号
     */
    private String batchNo;


    @Autowired
    private CouponService couponService;


    @Autowired
    private CouponDmsService couponDmsService;

    @Autowired
    private MessageSendService messageSendService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private AuthToDmsService authToDmsService;


    @Override
    public CouponDms process(Coupon coupon) throws Exception {
        log.info("定时任务 开始代金券清洗");

        if (StringUtils.isEmpty(coupon.getDealerCode())) {
            log.info("项目编号: {}  代金券编号：{}  openId: {}  vin: {}  amount: {}", coupon.getProjectCode(), coupon.getCouponNo(), coupon.getOpenId(), coupon.getVin(), coupon.getAmount());
            String dealerCode = this.authToDmsService.getAuthToDms(coupon.getVin());
            dealerCode = StringUtils.isEmpty(dealerCode) ? "50110" : dealerCode;//重庆安福
            coupon.setDealerCode(dealerCode);
        }

        //设置为已处理
        coupon.setBatchNo(this.batchNo);
        coupon.setBatchStatus(Coupon.BATCH_STATUS_PROCESSED);
        couponService.update(coupon);

        CouponDms dms = new CouponDms();
        dms.setBatchNo(this.batchNo);
        dms.setSendSms(CouponDms.SEND_SMS_WAITING);
        dms.setAmount(coupon.getAmount().toString());
        dms.setCampaignCode(coupon.getProjectCode());
//        dms.setCancel("0");
        dms.setCopies(1);
        dms.setCustomerMobile(coupon.getMobile());
//        dms.setDmsResult();
//        dms.setLimitDeduct("0");
        dms.setLimitDeduct(coupon.getAmount().toString());//抵扣限额和代金券金额一致
        dms.setLowestAmount(coupon.getLowestAmount() == null ? "0" : coupon.getLowestAmount().toString());
        dms.setName(coupon.getProjectName());
        dms.setOrder(coupon);
        dms.setPeriodType(CouponDms.PERIOD_TYPE_COUPON);
        dms.setSendCount(0);
//        dms.setSendDmsStatus();
//        dms.setSendHistory();
        dms.setSeqNo(coupon.getCouponNo());
        dms.setTag(CouponDms.TAG_AUTH_COUPON);
        String dealerCode = StringUtils.isEmpty(coupon.getDealerCode()) || coupon.getDealerCode().length() <= 5 ? coupon.getDealerCode() : coupon.getDealerCode().substring(0, 5);

        dms.setTargetDealer(dealerCode);
        dms.setUseAmount("0");
        dms.setUseDealer(null);
        dms.setValidBeginDate(coupon.getStartTime());
        dms.setValidEndDate(coupon.getExpiredTime());
        dms.setVin(coupon.getVin());

        log.info("定时任务 代金券清洗结束");
        return dms;
    }

}
