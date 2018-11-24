package com.ford.wechat.batch.item.processor.verify.dealer;/**
 * Created by jovi on 26/03/2017.
 */

import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.entity.user.JobStatus;
import com.ford.wechat.service.auth.AuthToDmsService;
import com.ford.wechat.service.dealer.WeChannelService;
import com.ford.wechat.service.members.WeWorkorderApplyService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
public class WeUnAuthClearProcessor implements ItemProcessor<WeWorkorderApply,AuthToDms> {
    @Autowired
    private WeWorkorderApplyService service;
    @Autowired
    private WeChannelService channelService;
    @Autowired
    private AuthToDmsService authToDmsService;
    /**
     * 日期批次
     */
    private String dateNo;
    /**
     * 批次号
     */
    private String batchNo;




    @Override
    public AuthToDms process(WeWorkorderApply apply) throws Exception {
        //更新为已进行批处理
        apply.setJobStatus(JobStatus.ALREADY_TO_JOB);
        service.update(apply);

        AuthToDms authToDms = new AuthToDms();
        authToDms.setBatchNo(batchNo);
        authToDms.setDateNo(dateNo);
        authToDms.setVerify(AuthToDms.VERIFY_UNAUTH);
        authToDms.setUserId(apply.getUserId());
        authToDms.setOpenId(apply.getOldOpenId());
        authToDms.setMobile(apply.getOldMobile());
        authToDms.setName(apply.getOldName());
        authToDms.setVin(apply.getVin());
        authToDms.setDcrtDate(authToDms.getDcrtDate());
        authToDms.setUnauthDate(apply.getCompletedDate());
        authToDms.setDealerScan(AuthToDms.DEALER_SCAN_NOT_DEALER);
        authToDms.setSendDmsStatus(AuthToDms.SEND_DMS_PENDING);
        authToDms.setDcrtDate(apply.getDcrtDate());
        authToDms.setFinalDate(apply.getCompletedDate());

        authToDms.setFollow(authToDmsService.subscribe(apply.getOldOpenId()));

        //authToDms
        if(!StringUtils.hasText(apply.getOldChannelCode())){
            return  authToDmsService.getAuthToDms(authToDms);
        }
        WeChannel channel = channelService.getByCode(apply.getOldChannelCode().trim());
        if(channel==null){
            return  authToDmsService.getAuthToDms(authToDms);
        }

        authToDms.setChannelCode(channel.getCode());
        authToDms.setChannelName(channel.getName());
        authToDms.setChannelType(channel.getType());
        //如果渠道类型为经销商
        if(WeChannel.DEALER == channel.getType()){
            authToDms.setDealerScan(AuthToDms.DEALER_SCAN_IS_DEALER);
            authToDms.setDealerServiceCode(channel.getCode());
            authToDms.setDealerName(channel.getName());
            authToDms.setBigArea(channel.getBigArea());
            return authToDms;
        }

        return  authToDmsService.getAuthToDms(authToDms);
    }


}
