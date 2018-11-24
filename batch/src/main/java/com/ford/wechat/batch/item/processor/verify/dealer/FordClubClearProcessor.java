package com.ford.wechat.batch.item.processor.verify.dealer;/**
 * Created by jovi on 26/03/2017.
 */

import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.entity.user.JobStatus;
import com.ford.wechat.service.auth.AuthToDmsService;
import com.ford.wechat.service.dealer.WeChannelService;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
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
public class FordClubClearProcessor implements ItemProcessor<FordClubMember,AuthToDms> {
    @Autowired
    private FordClubMemberService service;
    @Autowired
    private JoUserService userService;
    @Autowired
    private WeChannelService channelService;
    @Autowired
    private AuthToDmsService authToDmsService;

    private static Logger logger = LoggerFactory.getLogger(FordClubClearProcessor.class);
    /**
     * 日期批次
     */
    private String dateNo;
    /**
     * 批次号
     */
    private String batchNo;

    @Override
    public AuthToDms process(FordClubMember member) throws Exception {

        //更新为已进行批处理
        member.setJobStatus(JobStatus.ALREADY_TO_JOB);
        //用户编号 JO_USER查询不到
        if(member.getUserId()==null){
            return null;
        }
        //查询user
        JoUser user = userService.getById(member.getUserId());
        if(user!=null){
            member.setChannelCode(user.getDealerCode());
            member.setOpenId(user.getWechatUserName());
        }
        service.update(member);
        //如果没有vin码
        if(!StringUtils.hasText(member.getVvin())){
            return null;
        }

        AuthToDms authToDms = new AuthToDms();
        BeanUtils.copyProperties(member,authToDms);
        authToDms.setVerify(AuthToDms.VERIFY_AUTH);
        authToDms.setDateNo(dateNo);
        authToDms.setBatchNo(batchNo);
        authToDms.setMobile(member.getMobile());
        authToDms.setVin(member.getVvin());
        authToDms.setSendDmsStatus(AuthToDms.SEND_DMS_PENDING);
        authToDms.setDealerScan(AuthToDms.DEALER_SCAN_NOT_DEALER);
        authToDms.setFinalDate(member.getDcrtDate());

        //TODO
        authToDms.setFollow(AuthToDms.FOLLOW_FOLLOW);
        //authToDms
        if(!StringUtils.hasText(member.getChannelCode())){
            return  authToDmsService.getAuthToDms(authToDms);
        }
        WeChannel channel = channelService.getByCode(member.getChannelCode());
        if(channel==null){
            logger.info("channelCode not found. channelCode:{} VIN:{}",member.getChannelCode(),member.getVvin());
            return  authToDmsService.getAuthToDms(authToDms);
        }
        logger.info("channelCode not found. channelCode:{} channelType:{} VIN:{}",member.getChannelCode(),member.getChannelType(),member.getVvin());
        authToDms.setChannelCode(channel.getCode());
        authToDms.setChannelName(channel.getName());
        authToDms.setChannelType(channel.getType());
        //如果渠道类型为经销商
        if(channel.getType() == WeChannel.DEALER){
            logger.info("channelCode is dealer. channelCode:{} vin:{}",member.getChannelCode(),member.getVvin());
            authToDms.setDealerScan(AuthToDms.DEALER_SCAN_IS_DEALER);
            authToDms.setDealerServiceCode(channel.getDealerServiceCode());
            authToDms.setDealerName(channel.getName());
            return authToDms;
        }

        return  authToDmsService.getAuthToDms(authToDms);
    }



}
