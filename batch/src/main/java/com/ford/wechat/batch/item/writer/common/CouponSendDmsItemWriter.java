package com.ford.wechat.batch.item.writer.common;
/**
 * Created by Neel on 23/05/2017.
 */

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.service.coupon.CouponDmsService;
import com.google.common.collect.Lists;
import io.dabing.common.date.DateUtil;
import io.dabing.exception.BusinessException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-23 18:49
 * </p>
 *
 * @author Neel
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class CouponSendDmsItemWriter implements ItemWriter<CouponDms> {

    @Autowired
    private CouponDmsService couponDmsService;

    /**
     * 批次号
     */
    private String batchNo;

    @Override
    public void write(List<? extends CouponDms> list) throws Exception {

        if (list == null || list.isEmpty()) {
            log.info("本次无可发送回传DMS代金券数据JOB！");
            return;
        }
        log.info("本次发送回传DMS代金券数据JOB！数量：", list.size());
        List<Map<String, String>> mapContentList = Lists.newLinkedList();
        for (CouponDms couponDms : list) {
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("SEQ_NO", couponDms.getSeqNo());
            paramsMap.put("VIN", couponDms.getVin());
            paramsMap.put("NAME", couponDms.getName());
            paramsMap.put("CAMPAIGN_CODE", couponDms.getCampaignCode());
            paramsMap.put("VALID_BEGIN_DATE", couponDms.getValidBeginDate());
            paramsMap.put("VALID_END_DATE", couponDms.getValidEndDate());
            paramsMap.put("TARGET_DEALER", couponDms.getTargetDealer());//需要查询数据库获得得到经销商代码
            paramsMap.put("USE_DEALER", "");
            paramsMap.put("AMOUNT", couponDms.getAmount());
            paramsMap.put("USE_AMOUNT", "0.0");
            paramsMap.put("LOWEST_AMOUNT", couponDms.getLowestAmount());
            paramsMap.put("LIMIT_DEDUCT", couponDms.getLimitDeduct());
            paramsMap.put("IS_CANCEL", couponDms.getCancel());
            mapContentList.add(paramsMap);
        }
        String responseMessage = null;
        try {
            responseMessage = this.couponDmsService.sendDmsCoupon(mapContentList);
        } catch (BusinessException e) {
            log.error("调用DMS数据异常！{}", e.getMessage(), e);
            return;
        }
        Map dmsResp = JSON.parseObject(responseMessage, Map.class);
        if (!"0".equals(dmsResp.get("status_code"))) {
            log.info("DMS回传接口返回数据异常！FC不做任何处理！{}", JSON.toJSON(dmsResp));
            throw new BusinessException("DMS回传接口返回数据异常！FC不做任何处理！");
        }
        List<Map<String, String>> contentMapList = (List<Map<String, String>>) dmsResp.get("content");
        for (CouponDms couponDms : list) {
            couponDms.setSendDmsStatus(CouponDms.SEND_DMS_SENDED);
            couponDms.setDmsResult("发送成功");
            couponDms.setCancel("0");
            couponDms.setBatchNo(batchNo);
            Integer sendCount = couponDms.getSendCount();
            if (sendCount == null) {
                sendCount = 0;
            }
            couponDms.setSendCount(sendCount + 1);
            String sendHistory = couponDms.getSendHistory();
            if (sendHistory == null) {
                sendHistory = "";
            }
            if (contentMapList == null || contentMapList.size() == 0) {
                sendHistory += "【发送时间：" + DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATETIME_DEFAULT) + "，第" + (sendCount + 1) + "次发送，Cancel:0,发送结果：发送成功】";
            }
            for (Map<String, String> mapContent : contentMapList) {
                if (couponDms.getVin().equals(mapContent.get("VIN")) && couponDms.getSeqNo().equals(mapContent.get("SEQ_NO"))) {
                    couponDms.setSendDmsStatus(CouponDms.SEND_DMS_WAITING);
                    couponDms.setDmsResult(mapContent.get("ERROR_MSG"));
                    sendHistory += "【发送时间：" + DateUtil.formatDate(new Date(), DateUtil.FORMAT_DATETIME_DEFAULT) + "，第" + (sendCount + 1) + "次发送，Cancel:0，发送结果：" + mapContent.get("ERROR_MSG") + "】";
                    break;
                }
            }
            couponDms.setSendHistory(sendHistory);
        }
        this.couponDmsService.update((List<CouponDms>)list);
        log.info("本次发送回传DMS代金券数据JOB结束：", list.size());
        return;

    }

}
