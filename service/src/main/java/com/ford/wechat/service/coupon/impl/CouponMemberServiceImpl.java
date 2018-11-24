package com.ford.wechat.service.coupon.impl;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.respository.coupon.CouponRepository;
import com.ford.wechat.service.coupon.CouponMemberService;
import io.dabing.common.util.DateUtils;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Neel on 2017/8/27.
 */
@Service
public class CouponMemberServiceImpl extends AbstractService implements CouponMemberService {


    @Autowired
    private CouponRepository repository;

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void saveCouponForMember(Coupon coupon, EventDetail detail, Date date, String openId, String memberNo, String mobile) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + detail.getValidTimes());

        String startTime = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYY_MM_DD);
        String expiredTime = DateUtils.format(calendar.getTime(), DateUtils.FORMAT_DATE_YYYY_MM_DD);

        log.info("中奖  OpenId: {}  MemberNo: {}  Mobile: {}  代金券编号: {}  金额: {}  代金券开始时间：{}  过期时间: {}", openId, memberNo, mobile, coupon.getCouponNo(), coupon.getAmount(), startTime, expiredTime);

        coupon.setStatus(Coupon.STATUS_RECEIVING);
        coupon.setMemberNo(memberNo);
        coupon.setOpenId(openId);
//      coupon.setMobile(mobile);
//      coupon.setVin(vin);
//      coupon.setDealerCode();
        coupon.setStartTime(startTime);
        coupon.setReceivedTime(date);
        coupon.setExpiredTime(expiredTime);
        repository.update(coupon);//更新数据库coupon
    }


}
