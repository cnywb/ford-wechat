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
public interface CouponService {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Coupon> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Coupon> pagingBy(String projectCode, String openId,String vin, Integer batchStatus,Integer status, String createStartDate, String createEndDate, GridPage page);


    void save(Coupon entity);

    void update(Coupon entity);

    int countBy(String dateNo, String projectCode, Integer status);

    int countByAuthWithEvent(String dateNo, String projectCode, Integer status, Date eventStartDate, Date eventEndDate);

    int countByAuthBeforeEvent(String dateNo, String projectCode, Integer status, Date eventStartDate);

    int countSurplus(String dateNo, String projectCode);

    Coupon getLatestCoupon(String dateNo, String projectCode);

    Coupon findByCustomer(String projectCode, String openId, Integer status);

    Coupon findByVin(String projectCode, String vin, Integer status);

    List<Coupon> findBy(String dateNo, String projectCode, Integer status);

    Coupon findBy(String dateNo, String projectCode, String openId, String couponNo, Integer status);

    Coupon doDrawCoupon(String projectCode, String openId, String memberNo, String mobile);

    void saveCouponVin(String dateNo, String projectCode, String openId, String couponNo, String vin, String mobile, Date dcrtDate);

    void saveBackCouponVin(String dateNo, String projectCode, String openId, String couponNo, String vin, String mobile, Date dcrtDate);

    void doCacheCoupon(String dateNo, String projectCode);

    void removeCacheCoupon(String dateNo, String projectCode, String couponNo);
}
