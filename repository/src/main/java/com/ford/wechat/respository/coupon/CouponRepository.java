package com.ford.wechat.respository.coupon;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.entity.coupon.EventOperationRecord;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Coupon> pagingBy(GridPage page);

    int countBy(String dateNo, String projectCode, Integer status);

    Coupon findByLatest(String dateNo, String projectCode, Integer status);

    Coupon findBy(String dateNo, String projectCode, String couponNo, Integer status);

    Coupon findBy(String dateNo, String projectCode, String openId, String couponNo, Integer status);

    Coupon findBy(String dateNo, String projectCode, String openId, String vin, String couponNo, Integer status);

    Coupon findOneByRandom(String dateNo, String projectCode, Integer status);

    List<Coupon> findBy(String dateNo, String projectCode, Integer status);

    Coupon findByCustomer(String projectCode, String openId, Integer status);

    Coupon findByVin(String projectCode, String vin, Integer status);

    int countByAuthWithEvent(String dateNo, String projectCode, Integer status, Date eventStartDate, Date eventEndDate);

    int countByAuthBeforeEvent(String dateNo, String projectCode, Integer status, Date eventStartDate);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Coupon> pagingBy(String projectCode, String openId,String vin, Integer batchStatus,Integer status, String createStartDate, String createEndDate, GridPage page);

}
