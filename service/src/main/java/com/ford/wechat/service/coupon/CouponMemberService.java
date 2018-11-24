package com.ford.wechat.service.coupon;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.EventDetail;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface CouponMemberService {


    void saveCouponForMember(Coupon coupon, EventDetail detail, Date date, String openId, String memberNo, String mobile);

}
