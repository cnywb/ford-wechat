package com.ford.wechat.batch.item.writer.common;/**
 * Created by jovi on 23/05/2017.
 */

import com.alibaba.fastjson.JSON;
import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.entity.auth.AuthToDmsField;
import com.ford.wechat.service.auth.AuthToDmsService;
import com.google.common.collect.Lists;
import io.dabing.common.date.DateUtil;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.JSONUtil;
import io.dabing.redis.client.BinaryRedisClient;
import io.dabing.redis.util.RedisKeyUtils;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
public class AuthSendDmsItemWriter implements ItemWriter<AuthToDms> {
    @Autowired
    private AuthToDmsService service;
    @Autowired
    private BinaryRedisClient redisClient;

    private static Logger logger = LoggerFactory.getLogger(AuthSendDmsItemWriter.class);

    /**
     * 清洗批次key
     */
    private static final String SEND_AUTH_DMS_CLEAR_BATCHNO_VIN = "WE:SEND:AUTH:DMS:CLEAR:{0}:{1}";

    /**
     * 批次号
     */
    private String batchNo;

    @Override
    public void write(List<? extends AuthToDms> list) throws Exception {

        List<Map<String, String>> mapContentList = Lists.newLinkedList();
        for (AuthToDms authToDms : list) {
            //查看同批次是否存在重复VIN码
            String key= RedisKeyUtils.build (SEND_AUTH_DMS_CLEAR_BATCHNO_VIN,batchNo,authToDms.getVin());
            AuthToDms authToDmsRedis = redisClient.get(key,AuthToDms.class);
            //重复VIN码 设置不发送
            if(authToDmsRedis!=null){
                authToDms.setSendDmsStatus(AuthToDms.SEND_DMS_NOT);
                this.service.update(authToDms);
                continue;
            }
            redisClient.set(key,authToDms);
            //将
            Map<String, String> reqMap = new HashMap<String, String>();
            reqMap.put(AuthToDmsField.VERIFY_DATE, converNullString(DateUtil.formatDate(authToDms.getDcrtDate(), DateUtils.FORMAT_DATE_TIME_DEFAULT)));
            reqMap.put(AuthToDmsField.STATUS, "1");
            reqMap.put(AuthToDmsField.VERIFY_CHANNEL, converNullString(authToDms.getChannelCode()));
            reqMap.put(AuthToDmsField.VERIFY, converNullString(authToDms.getVerify().toString()));
            reqMap.put(AuthToDmsField.DEALER_SERVICE_CODE, converNullString(authToDms.getDealerServiceCode()));
            reqMap.put(AuthToDmsField.DATE_NO, converNullString(authToDms.getDateNo()));
            reqMap.put(AuthToDmsField.REMARK2, converNullString(authToDms.getRemark()));
            reqMap.put(AuthToDmsField.SEND_DATE, converNullString(DateUtil.formatDate(new Date(), DateUtils.FORMAT_DATE_TIME_DEFAULT)));
            reqMap.put(AuthToDmsField.DEALER_SCAN, converNullString(authToDms.getDealerScan().toString()));
            reqMap.put(AuthToDmsField.BATCH_NO, converNullString(authToDms.getBatchNo()));
            reqMap.put(AuthToDmsField.VERIFY_MOBILE, converNullString(authToDms.getMobile()));
            reqMap.put(AuthToDmsField.VIN, converNullString(authToDms.getVin().toUpperCase()));
            reqMap.put(AuthToDmsField.FOLLOW, converNullString(authToDms.getFollow().toString()));
            reqMap.put(AuthToDmsField.OPEN_ID, converNullString(authToDms.getOpenId()));
            reqMap.put(AuthToDmsField.UPDATE_DATE, converNullString(DateUtil.formatDate(authToDms.getUnauthDate(), DateUtils.FORMAT_DATE_TIME_DEFAULT)));
            mapContentList.add(reqMap);

        }
        String responseMessage = service.sendAuthToDms(mapContentList);
        Map dmsResp = JSON.parseObject(responseMessage, Map.class);
        if (!"0".equals(dmsResp.get("status_code"))) {//非零为接口失败
            logger.info("认证用户发送DMS【返回】数据异常！", JSON.toJSON(dmsResp));
            throw new BusinessException("认证用户发送DMS【返回】数据异常！AUTH不做任何处理！");
        }
        List<Map<String, String>> contentMapList = (List<Map<String, String>>) dmsResp.get("content");
        logger.info(JSONUtil.toJson(contentMapList));
        for (AuthToDms orderDms : list) {
            //建议：1，添加返回结果；
            if (contentMapList == null || contentMapList.size() == 0) {
                orderDms.setSendDmsDate(new Date());
                orderDms.setSendDmsStatus(AuthToDms.SEND_DMS_SUCCESS);
                orderDms.setSendDmsResult(AuthToDms.SEND_DMS_RESULT_SUCCESS);
            } else {
                for (Map<String, String> mapContent : contentMapList) {
                    if (orderDms.getVin().equals(mapContent.get("VIN"))) {

                        orderDms.setSendDmsDate(new Date());
                        orderDms.setSendDmsStatus(AuthToDms.SEND_DMS_FAIL);
                        orderDms.setSendDmsResult(mapContent.get("ERROR_MSG"));

                    } else {

                        orderDms.setSendDmsDate(new Date());
                        orderDms.setSendDmsStatus(AuthToDms.SEND_DMS_SUCCESS);
                        orderDms.setSendDmsResult(AuthToDms.SEND_DMS_RESULT_SUCCESS);

                    }
                }
            }
            service.update(orderDms);
        }

    }

    private String converNullString(String str){
        if(str==null){
            return "";
        }
        return str;
    }
}
