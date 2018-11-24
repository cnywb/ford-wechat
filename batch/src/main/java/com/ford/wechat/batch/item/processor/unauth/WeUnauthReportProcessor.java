package com.ford.wechat.batch.item.processor.unauth;/**
 * Created by jovi on 29/05/2017.
 */

import com.ford.wechat.entity.dealer.ClubDealer;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.entity.member.WeUnauthReport;
import com.ford.wechat.entity.repair.FordRepairWeb;
import com.ford.wechat.entity.user.FordCar;
import com.ford.wechat.service.dealer.ClubDealerService;
import com.ford.wechat.service.dealer.WeChannelService;
import com.ford.wechat.service.repair.FordRepairWebService;
import com.ford.wechat.service.user.FordCarService;
import io.dabing.common.util.DateUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-29 20:22
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class WeUnauthReportProcessor implements ItemProcessor<WeWorkorderApply,WeUnauthReport> {

    @Autowired
    private FordRepairWebService fordRepairWebService;
    @Autowired
    private ClubDealerService clubDealerService;
    @Autowired
    private FordCarService fordCarService;
    @Autowired
    private WeChannelService channelService;

    @Override
    public WeUnauthReport process(WeWorkorderApply apply) throws Exception {

        WeUnauthReport report = new WeUnauthReport();

        report.setOpenId(apply.getOpenId());
        report.setVin(apply.getVin());
        report.setUnauthDate(DateUtils.format(apply.getCompletedDate(),DateUtils.FORMAT_DATE_TIME_DEFAULT));
        //authToDms
        if(!StringUtils.hasText(apply.getOldChannelCode())){
            return  mergeDealer(report);
        }
        WeChannel channel = channelService.getByCode(apply.getOldChannelCode());
        if(channel==null){
            return  mergeDealer(report);
        }
        //如果渠道类型为经销商
        if(WeChannel.DEALER == channel.getType()){
            report.setBigArea(channel.getBigArea());
            report.setDealerServiceCode(channel.getDealerServiceCode());
            report.setDealerName(channel.getName());
            return report;
        }
        return mergeDealer(report);
    }

    private WeUnauthReport mergeDealer(WeUnauthReport report){
        List<FordRepairWeb> fordRepairWebs = fordRepairWebService.findByVin(report.getVin());

        for(FordRepairWeb fordRepairWeb: fordRepairWebs){

            ClubDealer clubDealer = this.clubDealerService.findByServiceCode(fordRepairWeb.getVsstId());
            if(clubDealer==null){
                continue;
            }
            report.setDealerServiceCode(clubDealer.getSstCode());
            report.setDealerName(clubDealer.getSstName());
            return report;

        }

        FordCar fordCar = fordCarService.findByVin(report.getVin(), 5);
        if(fordCar!=null){
            ClubDealer clubDealer = this.clubDealerService.findByServiceCode(fordCar.getVdealerId());
            if(clubDealer!=null){
                report.setDealerServiceCode(clubDealer.getSstCode());
                report.setDealerName(clubDealer.getSstName());
                return report;
            }

        }
        FordCar fordCar6 = fordCarService.findByVin(report.getVin(), 6);
        if(fordCar6!=null){
            ClubDealer clubDealer = this.clubDealerService.findByDSaleCode(fordCar.getVdealerId());
            if(clubDealer!=null){
                report.setDealerServiceCode(clubDealer.getSstCode());
                report.setDealerName(clubDealer.getSstName());
                return report;
            }

        }

        return report;
    }
}
