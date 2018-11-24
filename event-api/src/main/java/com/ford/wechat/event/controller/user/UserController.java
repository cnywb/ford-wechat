package com.ford.wechat.event.controller.user;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.Event;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.event.common.response.Response;
import com.ford.wechat.event.common.utils.SessionUtils;
import com.ford.wechat.event.controller.user.vo.LoginReq;
import com.ford.wechat.event.controller.user.vo.UserVo;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.coupon.EventDetailService;
import com.ford.wechat.service.coupon.EventService;
import com.ford.wechat.service.user.FordClubMemberService;
import com.ford.wechat.service.user.JoUserService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Neel on 2017/9/1.
 */
@Slf4j
@RestController
@RequestMapping(value="/e")
public class UserController {

    @Autowired
    private JoUserService joUserService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private EventDetailService eventDetailService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private EventService eventService;


    /**
     * 登录接口
     * @param eventId
     * @param req
     * @return
     */
    @RequestMapping(value = "/open/{id}/login", method = RequestMethod.POST)
    public Response<UserVo> login(@PathVariable(name = "id") Long eventId, @RequestBody LoginReq req) {
        log.info("/e/open/{}/login", eventId);

        if (StringUtils.isBlank(req.getOpenId())) {
            throw new BusinessException("OPENID 不存在");
        }
        UserVo body = new UserVo();
        JoUser user = joUserService.getJoUserByWechatId(req.getOpenId());
        if (user == null) {
            body.setBind(false);
            SessionUtils.set(req.getOpenId());
            SessionUtils.put(SessionUtils.USER_DETAIL, body);
            return new Response<>("500001", body);
//            throw new BusinessException("500001", "用户不存在");
        }


        List<FordClubMember> fordClubMembers = fordClubMemberService.findVinByOpenId(req.getOpenId());
        if (fordClubMembers.isEmpty()) {
            body.setBind(false);
            SessionUtils.set(req.getOpenId());
            SessionUtils.put(SessionUtils.USER_DETAIL, body);
            return new Response<>("500001", body);
//            throw new BusinessException("500002", "未认证");
        }

        List<String> vinList = new ArrayList<>();
        for (FordClubMember item : fordClubMembers) {
            vinList.add(StringUtils.isEmpty(item.getVvin()) ? item.getVvin() : item.getVvin().toUpperCase());
        }

        Event event = eventService.get(eventId);
        if (event == null) {
            SessionUtils.set(req.getOpenId());
            SessionUtils.put(SessionUtils.USER_DETAIL, body);
            return new Response<>("500003", body);
//            throw new BusinessException("500003", "活动不存在");
        }
        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);

        EventDetail detail = eventDetailService.getByDateNoAndProjectCode(dateNo, event.getProjectCode());

        if (detail == null) {
            SessionUtils.set(req.getOpenId());
            SessionUtils.put(SessionUtils.USER_DETAIL, body);
            return new Response<>("500004", body);
//            throw new BusinessException("500004", "不在活动期");
        }

        Coupon coupon = couponService.findByCustomer(event.getProjectCode(), req.getOpenId(), null);

        if (coupon != null) {
            log.info("校验openId是否已经抽奖 openId: {}", req.getOpenId());
            long days = DateUtils.getBetweenDayIgnoreTime(coupon.getReceivedTime(), date);
            if ((int)days <= detail.getValidTimes()) {
                log.info("该openId已抽奖 openId: {}", req.getOpenId());
                body.setInvolved(true);
                body.setLastReceivedTime(coupon.getReceivedTime());
                body.setNeedCouponBind(coupon.getStatus() == Coupon.STATUS_RECEIVING);
            }
        } else {
            for (String vin : vinList) {
                log.info("校验vin是否已经抽奖 Vin: {}", vin);
                coupon = couponService.findByVin(event.getProjectCode(), vin, Coupon.STATUS_RECEIVED);
                if (coupon == null) continue;
                long days = DateUtils.getBetweenDayIgnoreTime(coupon.getReceivedTime(), date);
                if ((int)days <= detail.getValidTimes()) {
                    log.info("该VIN已抽奖 Vin: {}", vin);
                    body.setInvolved(true);
                    body.setLastReceivedTime(coupon.getReceivedTime());
                    break;
                }
            }
        }

        body.setEventId(eventId);
        body.setProjectCode(event.getProjectCode());
        body.setVinList(vinList);
        body.setBind(true);
        body.setMemberNo(user.getMemberNo());
        body.setUserId(user.getId());
        body.setMobile(user.getMobilePhone());

        SessionUtils.set(req.getOpenId());
        SessionUtils.put(SessionUtils.USER_DETAIL, body);

        return new Response<>(body);
    }


}
