package com.ford.wechat.service.coupon;

import com.ford.wechat.entity.coupon.CouponDms;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Neel on 2017/8/27.
 */
public interface CouponDmsService {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page  分页对象，里有关键字keyWord,供模糊匹配
     * @param seqNo
     * @return 分页结果数据对象集合
     */
    Page<CouponDms> pagingBy(GridPage page, String seqNo);

    CouponDms get(Long id);

    void save(CouponDms object);

    void delete(Long id);

    void update(CouponDms object);

    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRES_NEW)
    void updateForBatch(CouponDms object);

    void update(List<CouponDms> orderDmsList);

    void updateSendDmsStatus(String sendDmsStatusSource, String sendDmsStatusTarget);

    List<CouponDms> findBySendDmsStatus(String sendDmsStatus, Date sendDate);

    List<CouponDms> findBySendDmsStatus(String sendDmsStatus);

    List<CouponDms> findBySendDmsStatusAndDmsResultAndSendSmsStatusNot(String sendDmsStatus, String dmsResult, String sendSmsStatus);

    CouponDms findBySeqNo(String orderNo);

    String sendDmsCoupon(List<Map<String, String>> contentList);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CouponDms> pagingBy(String sendDmsStatus,String targetDealer, String batchNo,String vin, String customerMobile,String sendSms, String createStartDate, String createEndDate, GridPage page);

}
