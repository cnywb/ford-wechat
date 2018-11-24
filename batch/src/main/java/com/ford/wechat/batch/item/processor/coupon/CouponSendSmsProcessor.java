package com.ford.wechat.batch.item.processor.coupon;
/**
 * Created by Neel on 26/03/2017.
 */

import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.entity.message.MessageSend;
import com.ford.wechat.service.coupon.CouponDmsService;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.mdao.SmsService;
import com.ford.wechat.service.message.MessageSendService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
public class CouponSendSmsProcessor implements ItemProcessor<CouponDms, MessageSend> {


//    public static final String AUTH_CONPON_MESSAGE_TEMPLATE = "[长安福特]您参与“豪送1500万，人人有礼”活动获得了一张%s元代金券已到账，用于车架号%s的车辆，请于%s前至任意长安福特授权经销商使用。回T退订";
    public static final String AUTH_CONPON_MESSAGE_TEMPLATE = "您%s的爱车于“豪送1500万，人人有礼”获%s元代金券已生效，请于%s前至任意长安福特授权经销商使用。关注长安福特微客服，可在“个人中心”-“我的红包”查看。回T退订";


    /**
     * 日期批次
     */
    private String dateNo;
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
    private RedisTemplate<String, String> redisTemplate;


    public static final String KEY_BATCH_COUPON_SMS = "FORD:BATCH:COUPON:SMS:MOBILE:%s:COUPON_NO:%s";

    @Override
    public MessageSend process(CouponDms dms) throws Exception {
        log.info("定时任务 开始代金券短信通知");

        dms.setSendSms(CouponDms.SEND_SMS_WAITING);
        couponDmsService.updateForBatch(dms);

        String key = String.format(KEY_BATCH_COUPON_SMS, dms.getCustomerMobile(), dms.getSeqNo());

        if (!redisTemplate.opsForValue().setIfAbsent(key, dms.getCustomerMobile())){
            log.info("短信缓存redis失败");
            return null;
        } else {
            log.info("短信缓存redis成功，准备发送短信");
        }
        redisTemplate.expire(key, 12, TimeUnit.HOURS);

        MessageSend send = new MessageSend();

        String content = String.format(AUTH_CONPON_MESSAGE_TEMPLATE, dms.getVin(), dms.getAmount(), dms.getValidEndDate());
        String result = this.smsService.send(dms.getCustomerMobile(), content);
        log.info("手机号: {}  短信返回报文: {}", dms.getCustomerMobile(), result);
        if (result == null) {//发送成功
            log.info("短信发送成功 mobile: {}", dms.getCustomerMobile());
            send.setSendResult(MessageSend.SEND_RESULT_OK);
            dms.setSendSms(CouponDms.SEND_SMS_SENDED);
        } else {//短信发送失败
            log.info("短信发送失败 mobile: {}", dms.getCustomerMobile());
            send.setSendResult(MessageSend.SEND_RESULT_FAILED);
            send.setErrorMessage(result);
            dms.setSendSms(CouponDms.SEND_SMS_FAIL);
        }
        send.setSendCount(1);
        send.setVin(dms.getVin());
        send.setContent(content);
        send.setDateNo(dateNo);
//        send.setMemberNo();
        send.setMobile(dms.getCustomerMobile());
//        send.setOpenId();
        send.setProjectCode(dms.getCampaignCode());
        send.setProjectName(dms.getName());
        send.setSendChannel(MessageSend.CHANNEL_AUTH_COUPON);
        send.setSendTime(new Date());

        this.couponDmsService.update(dms);


        log.info("定时任务 代金券短信通知结束");
        return send;
    }
}
