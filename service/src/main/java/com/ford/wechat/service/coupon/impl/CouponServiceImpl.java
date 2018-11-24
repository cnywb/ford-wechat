package com.ford.wechat.service.coupon.impl;

import com.alibaba.fastjson.JSON;
import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.respository.coupon.CouponRepository;
import com.ford.wechat.service.coupon.CouponMemberService;
import com.ford.wechat.service.coupon.CouponService;
import com.ford.wechat.service.coupon.EventDetailService;
import com.ford.wechat.service.user.FordClubMemberService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.DateUtils;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Service
public class CouponServiceImpl extends AbstractService implements CouponService {

    public static final String KEY_EVENT_DATE_LATEST = "FORD:EVENT:%s:DATE:%s:LATEST";

    public static final String KEY_LOCK_CACHE_EVENT = "FORD:LOCK:CACHE:EVENT:%s:DATE:%s";
    public static final String KEY_LOCK_EVENT_COUPON = "FORD:LOCK:EVENT:%s:DATE:%s:COUPON:%s";

    public static final String KEY_CACHE_EVENT = "FORD:CACHE:EVENT:%s:DATE:%s";
    public static final String HASH_KEY_CACHE_EVENT_COUPON = "FORD:CACHE:EVENT:%s:DATE:%s:COUPON:%s";


    @Autowired
    private CouponRepository repository;

    @Autowired
    private EventDetailService eventDetailService;

    @Autowired
    private CouponMemberService couponMemberService;

    @Autowired
    private FordClubMemberService fordClubMemberService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<Coupon> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    @Override
    public Page<Coupon> pagingBy(String projectCode, String openId, String vin, Integer batchStatus, Integer status, String createStartDate, String createEndDate, GridPage page) {
        return repository.pagingBy(projectCode, openId, vin, batchStatus, status, createStartDate, createEndDate, page);
    }


    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void save(Coupon entity) {
        this.repository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void update(Coupon entity) {
        this.repository.update(entity);
    }


    @Override
    public int countBy(String dateNo, String projectCode, Integer status) {
        return this.repository.countBy(dateNo, projectCode, status);
    }

    @Override
    public int countByAuthWithEvent(String dateNo, String projectCode, Integer status, Date eventStartDate, Date eventEndDate) {
        return this.repository.countByAuthWithEvent(dateNo, projectCode, status, eventStartDate, eventEndDate);
    }

    @Override
    public int countByAuthBeforeEvent(String dateNo, String projectCode, Integer status, Date eventStartDate) {
        return this.repository.countByAuthBeforeEvent(dateNo, projectCode, status, eventStartDate);
    }


    /**
     * 剩余奖品数量
     * @param dateNo
     * @param projectCode
     * @return
     */
    @Override
    public int countSurplus(String dateNo, String projectCode) {
        String key = String.format(KEY_CACHE_EVENT, projectCode, dateNo);

        return redisTemplate.opsForHash().keys(key).size();
    }


    /**
     * 获取最近一次中奖详情
     * @param dateNo
     * @param projectCode
     * @return
     */
    @Override
    public Coupon getLatestCoupon(String dateNo, String projectCode) {
        String key = String.format(KEY_EVENT_DATE_LATEST, projectCode, dateNo);
        String value = this.redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(value)) return null;

        return JSON.parseObject(value, Coupon.class);
    }

    /**
     * 保存最近一次中奖情况
     * @param dateNo
     * @param projectCode
     * @param coupon
     */
    private void setLatestCoupon(String dateNo, String projectCode, Coupon coupon) {
        String key = String.format(KEY_EVENT_DATE_LATEST, projectCode, dateNo);

        this.redisTemplate.opsForValue().set(key, JSON.toJSONString(coupon), 24, TimeUnit.HOURS);
    }

    @Override
    public Coupon findByCustomer(String projectCode, String openId, Integer status) {
        return this.repository.findByCustomer(projectCode, openId, status);
    }
    @Override
    public Coupon findByVin(String projectCode, String vin, Integer status) {
        return this.repository.findByVin(projectCode, vin, status);
    }

    @Override
    public List<Coupon> findBy(String dateNo, String projectCode, Integer status) {
        return this.repository.findBy(dateNo, projectCode, status);
    }

    @Override
    public Coupon findBy(String dateNo, String projectCode, String openId, String couponNo, Integer status) {
        return this.repository.findBy(dateNo, projectCode, openId, couponNo, status);
    }

    /**
     * 抽奖
     * @param projectCode
     * @param openId
     * @param memberNo
     * @param mobile
     */
    @Override
//    @Transactional(rollbackFor = Exception.class, timeout = 60, propagation = Propagation.REQUIRED)
    public Coupon doDrawCoupon(String projectCode, String openId, String memberNo, String mobile) {
        log.info("进入抽奖 doDrawCoupon >  OpenId: {}  MemberNo: {}  Mobile: {}  ", openId, memberNo, mobile);

        Date date = new Date();
        String dateNo = DateUtils.format(date, DateUtils.FORMAT_DATE_YYYYMMDD);

        EventDetail detail = eventDetailService.getByDateNoAndProjectCode(dateNo, projectCode);

        if (detail == null) throw new BusinessException("100001", "不在活动期");

        if (date.getTime() < detail.getStartTime().getTime()) {
            throw new BusinessException("100002", "活动未开始");
        }
        if (date.getTime() > detail.getEndTime().getTime()) {
            throw new BusinessException("100003", "今天活动已结束");
        }


        this.doCacheCoupon(dateNo, projectCode);//校验缓存是否已缓存
        String key = String.format(KEY_CACHE_EVENT, projectCode, dateNo);

        Set<Object> keys = redisTemplate.opsForHash().keys(key);
        if (keys.isEmpty()) {
            throw new BusinessException("100004", "今日代金券已抽完");
        }

        Iterator<Object> iter = keys.iterator();
        while (iter.hasNext()) {
            Object couponKey = iter.next();
            String couponNo = (String)this.redisTemplate.opsForHash().get(key, couponKey);

            String lockKey = String.format(KEY_LOCK_EVENT_COUPON, projectCode, dateNo, couponNo);
            if (redisTemplate.opsForValue().setIfAbsent(lockKey, couponNo)) {
                redisTemplate.expire(lockKey, 5, TimeUnit.MINUTES);
                Coupon coupon = repository.findBy(dateNo, projectCode, couponNo, Coupon.STATUS_INIT);
                if (coupon == null) {
                    //该代金券已被人抽取，未同步到redis，更新redis
                    this.removeCacheCoupon(dateNo, projectCode, couponNo);
                    continue;
                }

                couponMemberService.saveCouponForMember(coupon, detail, date, openId, memberNo, mobile);

                //从缓存钟清除该奖券，防止被其他人抽取该奖券
                this.removeCacheCoupon(dateNo, projectCode, couponNo);

                return coupon;
            }

        }

        throw new BusinessException("100005", "未抽到，请重试");
    }

    /**
     * 绑定代金券和VIN
     * @param dateNo
     * @param projectCode
     * @param openId
     * @param couponNo
     * @param vin
     * @param mobile
     */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void saveCouponVin(String dateNo, String projectCode, String openId, String couponNo, String vin, String mobile, Date dcrtDate) {
        Coupon coupon = this.repository.findBy(null, projectCode, openId, couponNo, Coupon.STATUS_RECEIVING);

        if (coupon == null) {
            log.error("该代金券不存在或已被领取 openId:{} couponNo: {}", openId, couponNo);
            throw new BusinessException("100001", "该代金券不存在或已被领取");
        }
        coupon.setStatus(Coupon.STATUS_RECEIVED);
        coupon.setMobile(mobile);
        coupon.setAuthTime(dcrtDate);
        coupon.setVin(StringUtils.isEmpty(vin) ? vin : vin.toUpperCase());
        this.repository.update(coupon);

        //保存最新的中奖记录到redis
        this.setLatestCoupon(dateNo, projectCode, coupon);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void saveBackCouponVin(String dateNo, String projectCode, String openId, String couponNo, String vin, String mobile, Date dcrtDate) {

        Coupon coupon = this.repository.findBy(null, projectCode, openId, couponNo, Coupon.STATUS_RECEIVING);


        if (coupon == null) {
            log.error("该代金券不存在或已被领取 openId:{} couponNo: {}", openId, couponNo);
            throw new BusinessException("100001", "该代金券不存在或已被领取");
        }

        Coupon validCoupon = this.repository.findBy(coupon.getDateNo(), projectCode, openId, null, Coupon.STATUS_RECEIVED);

        if (validCoupon != null) {
            log.error("该车主今天重复领取代金券 openId:{} couponNo: {}", openId, validCoupon.getCouponNo());
            throw new BusinessException("100002", "重复领取，该车主已经领取过编号为["+validCoupon.getCouponNo()+"]的代金券");
        }

        coupon.setStatus(Coupon.STATUS_RECEIVED);
        coupon.setMobile(mobile);
        coupon.setAuthTime(dcrtDate);
        coupon.setVin(StringUtils.isEmpty(vin) ? vin : vin.toUpperCase());
        this.repository.update(coupon);
    }


    /**
     * 缓存初始奖券
     * @param dateNo
     * @param projectCode
     */
    @Override
    public void doCacheCoupon(String dateNo, String projectCode) {
        String lockKey = String.format(KEY_LOCK_CACHE_EVENT, projectCode, dateNo);

        if (!redisTemplate.opsForValue().setIfAbsent(lockKey, "true")) {//设置锁标识
            log.info("已被缓存，退出此次执行");
            return;
        }
        String key = String.format(KEY_CACHE_EVENT, projectCode, dateNo);
        try {
            redisTemplate.expire(lockKey, 5, TimeUnit.MINUTES);

            this.clearCouponCache(key);

            List<Coupon> list = this.repository.findBy(dateNo, projectCode, Coupon.STATUS_INIT);
            log.info("根据项目 {} 获取当天 {} 所有奖券 size: {}", projectCode, dateNo, list.size());
            for (Coupon coupon : list) {
//                log.info("奖券号：{}", coupon.getCouponNo());
                String couponKey = String.format(HASH_KEY_CACHE_EVENT_COUPON, projectCode, dateNo, coupon.getCouponNo());
                redisTemplate.opsForHash().putIfAbsent(key, couponKey, coupon.getCouponNo());
                redisTemplate.expire(key, 24, TimeUnit.HOURS);
            }
            if (!list.isEmpty()) {
                redisTemplate.expire(lockKey, 24, TimeUnit.HOURS);
                log.info("缓存成功，本次缓存 {} 条", list.size());
            }
            log.info("缓存代金券结束");
        } catch (Exception e) {
            this.clearCouponCache(key);
            redisTemplate.delete(lockKey);
        }
    }

    private void clearCouponCache(String key) {
        Set<Object> keys = redisTemplate.opsForHash().keys(key);
        if (keys.isEmpty()) return;
        redisTemplate.opsForHash().delete(key, keys.toArray());
    }

    /**
     *
     * @param dateNo
     * @param projectCode
     * @param couponNo
     */
    @Override
    public void removeCacheCoupon(String dateNo, String projectCode, String couponNo) {
        String key = String.format(KEY_CACHE_EVENT, projectCode, dateNo);
        String couponKey = String.format(HASH_KEY_CACHE_EVENT_COUPON, projectCode, dateNo, couponNo);

        redisTemplate.opsForHash().delete(key, couponKey);
    }

}
