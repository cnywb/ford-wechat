package com.ford.wechat.event.controller.coupon;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.event.common.response.Response;
import com.ford.wechat.event.common.utils.SessionUtils;
import com.ford.wechat.event.controller.coupon.vo.CouponResp;
import com.ford.wechat.event.controller.coupon.vo.DrawReq;
import com.ford.wechat.event.controller.coupon.vo.WinnerResp;
import com.ford.wechat.event.controller.user.vo.UserVo;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.coupon.EventDetailService;
import com.ford.wechat.service.user.FordClubMemberService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Neel on 2017/9/1.
 */
@Slf4j
@RestController
@RequestMapping(value="/e/coupon")
public class CouponController {

    public static final String KEY_COUPON_USER_STATUS = "FORD:COUPON:DRAW:USER:%s";

    @Autowired
    private CouponService couponService;

    @Autowired
    private EventDetailService eventDetailService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 查询代金券详情 - 查看代金券页面
     * @return
     */
    @RequestMapping(value = "/detail/latest", method = RequestMethod.GET)
    public Response<CouponResp> detail() {
        UserVo user = (UserVo) SessionUtils.get(SessionUtils.USER_DETAIL);
        String openId = SessionUtils.getOpenId();

        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);

        Coupon coupon = couponService.findByCustomer(user.getProjectCode(), openId, Coupon.STATUS_RECEIVED);
        if (coupon == null) {
            List<String> vinList = user.getVinList();
            for (String vin : vinList) {
                log.info("校验vin是否已经抽奖 Vin: {}", vin);
                coupon = couponService.findByVin(user.getProjectCode(), vin, Coupon.STATUS_RECEIVED);
                if (coupon == null) continue;
            }
            if (coupon == null) {
                throw new BusinessException("100001", "代金券不存在");
            }
        }

        EventDetail detail = eventDetailService.getByDateNoAndProjectCode(coupon.getDateNo(), user.getProjectCode());

        CouponResp body = new CouponResp();
        body.setAmount(coupon.getAmount());
        body.setCouponNo(coupon.getCouponNo());
        body.setValidTimes(detail != null ? detail.getValidTimes() : null);

        return new Response<>(body);
    }


    /**
     * 绑定VIN前获取代金券详情
     * @return
     */
    @RequestMapping(value = "/detail/latest/receiving", method = RequestMethod.GET)
    public Response<CouponResp> detailForReceiving() {
        UserVo user = (UserVo) SessionUtils.get(SessionUtils.USER_DETAIL);
        String openId = SessionUtils.getOpenId();

        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);


        List<FordClubMember> fordClubMembers = fordClubMemberService.findVinByOpenId(openId);
        if (fordClubMembers.isEmpty()) {
            throw new BusinessException("500002", "未认证");
        }

        List<String> vinList = new ArrayList<>();
        for (FordClubMember item : fordClubMembers) {
            vinList.add(StringUtils.isEmpty(item.getVvin()) ? item.getVvin() : item.getVvin().toUpperCase());
        }

        Coupon coupon = couponService.findByCustomer(user.getProjectCode(), openId, Coupon.STATUS_RECEIVING);

        if (coupon == null) {
            throw new BusinessException("100001", "代金券不存在");
        }

        EventDetail detail = eventDetailService.getByDateNoAndProjectCode(coupon.getDateNo(), user.getProjectCode());

        CouponResp body = new CouponResp();
        body.setVinList(vinList);
        body.setAmount(coupon.getAmount());
        body.setCouponNo(coupon.getCouponNo());
        body.setValidTimes(detail != null ? detail.getValidTimes() : null);

        return new Response<>(body);
    }


    /**
     * 获取中奖名单列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Response<List<WinnerResp>> list() {
        UserVo user = (UserVo)SessionUtils.get(SessionUtils.USER_DETAIL);
        String projectCode = user.getProjectCode();

        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);
        log.info("/e/coupon  projectCode: {}   dateNo: {}");


        List<Coupon> coupons = couponService.findBy(dateNo, projectCode, Coupon.STATUS_RECEIVED);

        List<WinnerResp> body = new ArrayList<>();
        for (Coupon item : coupons) {
            if (StringUtils.isEmpty(item.getMobile())) {
                continue;
            }
            String guardMobile = item.getMobile().replaceAll("(?<=\\d{3})\\d(?=\\d{3})", "*");
            WinnerResp t = new WinnerResp();
            t.setMobile(guardMobile);
            t.setAmount(item.getAmount());
            body.add(t);
        }

        return new Response<>(body);
    }

    /**
     * 抽奖接口
     * @return
     */
    @RequestMapping(value = "/draw", method = RequestMethod.POST)
    public Response<String> draw() {
        UserVo user = (UserVo)SessionUtils.get(SessionUtils.USER_DETAIL);
        String openId = SessionUtils.getOpenId();

        String key = String.format(KEY_COUPON_USER_STATUS, openId);

        if (!redisTemplate.opsForValue().setIfAbsent(key, "DRAWING")) {
            throw new BusinessException("500004", "请勿重复抽奖");
        }
        redisTemplate.expire(key, 10, TimeUnit.SECONDS);

        if (!user.isBind()) {
            throw new BusinessException("500002", "未认证");
        }
        if (user.isInvolved()) {
            throw new BusinessException("500003", "您暂时没有抽奖资格");
        }

        String projectCode = user.getProjectCode();

        Coupon coupon = this.couponService.doDrawCoupon(projectCode, openId, user.getMemberNo(), user.getMobile());

//        if (coupon == null) {
//            throw new BusinessException("100007", "未中奖");
//        }

        user.setInvolved(true);
        user.setNeedCouponBind(true);
        user.setLastReceivedTime(coupon.getReceivedTime());
        SessionUtils.put(SessionUtils.USER_DETAIL, user);
        redisTemplate.delete(key);
        return new Response<>(coupon.getCouponNo());
    }

    /**
     * 代金券绑定VIN
     * @param req
     * @return
     */
    @RequestMapping(value = "/draw/vin", method = RequestMethod.POST)
    public Response<Void> drawVin(DrawReq req) {
        UserVo user = (UserVo)SessionUtils.get(SessionUtils.USER_DETAIL);
        String openId = SessionUtils.getOpenId();
        String projectCode = user.getProjectCode();
        String mobile = user.getMobile();
        String vin = req.getVin();

        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);

        FordClubMember fordClubMember = this.fordClubMemberService.findBy(openId, vin);

        if (fordClubMember == null) {
            throw new BusinessException("100006", "未认证");
        }
        if (!StringUtils.isEmpty(fordClubMember.getMobile())) {
            mobile = fordClubMember.getMobile();
        }

        this.couponService.saveCouponVin(dateNo, projectCode, openId, req.getCouponNo(), vin, mobile, fordClubMember.getDcrtDate());

        user.setNeedCouponBind(false);
        SessionUtils.put(SessionUtils.USER_DETAIL, user);

        return new Response<>();
    }

}
