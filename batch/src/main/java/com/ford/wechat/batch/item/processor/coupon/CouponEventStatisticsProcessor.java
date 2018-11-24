package com.ford.wechat.batch.item.processor.coupon;
/**
 * Created by Neel on 26/03/2017.
 */

import com.ford.wechat.entity.coupon.*;
import com.ford.wechat.entity.message.MessageSend;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.coupon.EventOperationRecordService;
import com.ford.wechat.service.coupon.EventService;
import com.ford.wechat.service.coupon.EventStatisticsService;
import com.ford.wechat.service.message.MessageSendService;
import com.ford.wechat.service.user.FordClubMemberService;
import io.dabing.common.util.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
public class CouponEventStatisticsProcessor implements ItemProcessor<EventDetail, Void> {


    /**
     * 统计的数据时间
     */
    private String date;


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
    private EventOperationRecordService eventOperationRecordService;

    @Autowired
    private EventStatisticsService eventStatisticsService;

    @Autowired
    private MessageSendService messageSendService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private EventService eventService;


    @Override
    public Void process(EventDetail eventDetail) throws Exception {
        log.info("定时任务 认证激励统计报表开始 date: {}  batchNo: {}", this.date, this.batchNo);

        String projectCode = eventDetail.getProjectCode();

        Event event = this.eventService.findBy(projectCode);

        EventStatistics entity = new EventStatistics();
        entity.setBatchNo(this.batchNo);
        entity.setDateNo(this.date);
        entity.setProjectCode(event.getProjectCode());
        entity.setProjectName(event.getProjectName());

        entity.setWinnerCount(this.couponService.countBy(date, projectCode, Coupon.STATUS_RECEIVED));//抽中人数

        entity.setSmsSendCount(this.messageSendService.countBy(date, projectCode, null));//短信发送数
        entity.setSmsFailedCount(this.messageSendService.countBy(date, projectCode, MessageSend.SEND_RESULT_FAILED));//短信发送失败数量

        entity.setVisitCount(this.eventOperationRecordService.countBy(date, projectCode, EventOperationRecord.OPERATION_TYPE_VISIT));//每日访问人数
        entity.setDrawCount(this.eventOperationRecordService.countBy(date, projectCode, EventOperationRecord.OPERATION_TYPE_DRAW));//每次人抽奖人数

        entity.setAuthenCount(this.fordClubMemberService.countBy(date));//每日认证人数

        Date eventStartDate = DateUtils.parseDate(event.getStartTime(), DateUtils.FORMAT_DATE_YYYY_MM_DD);
        Date eventEndDate = DateUtils.parseDate(event.getEndTime(), DateUtils.FORMAT_DATE_YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(eventEndDate);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        eventEndDate = calendar.getTime();

        entity.setAuthenCountWithEvent(this.couponService.countByAuthWithEvent(date, projectCode, Coupon.STATUS_RECEIVED, eventStartDate, eventEndDate));//因活动认证人数
        entity.setOriginalWinnerCount(this.couponService.countByAuthBeforeEvent(date, projectCode, Coupon.STATUS_RECEIVED, eventStartDate));//老认证用户获券人数

        this.eventStatisticsService.save(entity);
        log.info("定时任务 认证激励统计报表结束");
        return null;
    }


}
