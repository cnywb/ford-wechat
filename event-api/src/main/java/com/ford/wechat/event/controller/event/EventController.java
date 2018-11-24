package com.ford.wechat.event.controller.event;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.Event;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.event.common.response.Response;
import com.ford.wechat.event.common.utils.SessionUtils;
import com.ford.wechat.event.controller.event.vo.EventReq;
import com.ford.wechat.event.controller.event.vo.EventResp;
import com.ford.wechat.event.controller.user.vo.UserVo;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.coupon.EventDetailService;
import com.ford.wechat.service.coupon.EventService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Neel on 2017/9/2.
 */
@RestController
@RequestMapping(value="/e/")
public class EventController {

    @Value("${bind.url}")
    private String bindUrl;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventDetailService eventDetailService;

    @Autowired
    private CouponService couponService;


    /**
     * 获取抽奖详情接口 - 不间断刷新
     * @param id
     * @param req
     * @return
     */
    @RequestMapping(value = "/state/{id}", method = RequestMethod.GET)
    public Response<EventResp> state(@PathVariable Long id, EventReq req) {
        String openId = SessionUtils.getOpenId();

        UserVo user =  (UserVo)SessionUtils.get("USER_DETAIL");

        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);

        Event event = eventService.get(id);
        if (event == null) {
            throw new BusinessException("NO_EVENT", "没有活动");
        }

        String projectCode = event.getProjectCode();

        EventDetail detail = eventDetailService.getByDateNoAndProjectCode(dateNo, projectCode);
        if (detail == null) {
            throw new BusinessException("NO_EVENT_TODAY", "今天没有活动");
        }
        EventResp resp = new EventResp();

        long time = date.getTime();

        long startTime = detail.getStartTime().getTime();
        long endTime = detail.getEndTime().getTime();
        resp.setStatus(0);
        if (time > endTime) {//今天已经结束
            resp.setBegun(true);
            resp.setEnded(true);
            resp.setStatus(3);
        } else if (time > startTime) {//已经开始活动
            resp.setBegun(true);
            resp.setStatus(2);
        } else if (startTime - time < 5*50*1000) {//倒计时5分钟准备开始
            resp.setStatus(1);
            resp.setPrepare(true);
        }
        Coupon latestCoupon = this.couponService.getLatestCoupon(dateNo, projectCode);
        String recentNews = "";
        if (latestCoupon != null && latestCoupon.getMobile() != null) {
            String guardMobile = latestCoupon.getMobile().replaceAll("(?<=\\d{3})\\d(?=\\d{3})", "*");
            recentNews = latestCoupon == null ? null : String.format("%s领取了%d元", guardMobile, latestCoupon.getAmount());
        }

        resp.setRecentNews(recentNews);
        resp.setEventCode(projectCode);
        resp.setEventName(event.getProjectName());
        resp.setInvolved(user.isInvolved());
        resp.setNeedCouponBind(user.isNeedCouponBind());
        resp.setStartTime(DateUtils.format(detail.getStartTime(), DateUtils.FORMAT_DATE_TIME_DEFAULT));
        resp.setEndTime(DateUtils.format(detail.getEndTime(), DateUtils.FORMAT_DATE_TIME_DEFAULT));

        if (!user.isBind()) {//未认证
            /*String url = this.bindUrl.replaceAll(":openId", openId);
            resp.setBindUrl(url);*/
        } else if (user.isInvolved() && user.getLastReceivedTime() != null) {
            Date lastReceivedTime = user.getLastReceivedTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastReceivedTime);
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + detail.getValidTimes());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Date endDate = DateUtils.parseDate(event.getEndTime(), DateUtils.FORMAT_DATE_YYYY_MM_DD);

            if (calendar.getTimeInMillis() <= endDate.getTime()) {//还有下次抽奖活动
                resp.setNextDate(DateUtils.format(calendar.getTime(), "MM月dd日"));
            }
        }
        resp.setSurplus(couponService.countSurplus(dateNo, projectCode));
        resp.setTotal(detail.getCount());
        resp.setBind(user.isBind());

        return new Response<>(resp).time(DateUtils.format(date, DateUtils.FORMAT_DATE_TIME_DEFAULT));
    }

}
