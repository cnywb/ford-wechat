package com.ford.wechat.event.job;

import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.coupon.EventDetailService;
import io.dabing.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by Neel on 2017/9/4.
 */
@Slf4j
@Component
public class ScheduledTasks {


    @Autowired
    private CouponService couponService;

    @Autowired
    private EventDetailService eventDetailService;

    @Scheduled(cron="${job.cron.cache.coupon}")
    public void cacheConponJob(){
        log.info("开始执行 缓存代金券列表到Redis");
        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);

        List<EventDetail> details = this.eventDetailService.findBy(dateNo, null);

        for (EventDetail detail : details) {
            this.couponService.doCacheCoupon(dateNo, detail.getProjectCode());
        }

        log.info("执行完成 缓存代金券列表到Redis");
    }
}
