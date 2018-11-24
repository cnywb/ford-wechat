package com.ford.wechat.batch.item.processor.coupon;
/**
 * Created by Neel on 26/03/2017.
 */

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.service.coupon.CouponService;
import io.dabing.common.util.CouponUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

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
 * All rights reserved. 2017-03-26 15:14
 * </p>
 *
 * @author Neel
 * @version 1.0
 */
@Slf4j
@Getter
@Setter
public class GenerateCouponProcessor implements ItemProcessor<EventDetail, Void> {

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


    @Override
    public Void process(EventDetail event) throws Exception {
        log.info("定时任务 开始生成代金券");
        List<Integer> splits = CouponUtils.split(event.getTotalAmount().intValue(), event.getCount().intValue(), event.getMin().intValue(), event.getMax().intValue(), 2.0d);

        int i = 0;
        for (Integer amount : splits) {
            i++;
            String couponNo = this.batchNo + String.format("%06d", i);
            Coupon coupon = new Coupon();
            coupon.setBatchNo(this.batchNo);
            coupon.setDateNo(this.dateNo);
            coupon.setAmount(amount);
            coupon.setBatchStatus(Coupon.BATCH_STATUS_INIT);
            coupon.setCouponNo(couponNo);
            coupon.setLowestAmount(event.getLowestAmount());
            coupon.setProjectCode(event.getProjectCode());
            coupon.setProjectName(event.getProjectName());
            coupon.setStatus(Coupon.STATUS_INIT);
            coupon.setWishing(event.getWishing());
            couponService.save(coupon);
        }

        log.info("定时任务 代金券生成结束");
        return null;
    }


}
