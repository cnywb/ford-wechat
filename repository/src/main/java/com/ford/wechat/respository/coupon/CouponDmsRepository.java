package com.ford.wechat.respository.coupon;

import com.ford.wechat.entity.coupon.CouponDms;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;


/**
 * Created by Neel on 2017/8/27.
 */
public interface CouponDmsRepository extends JpaRepository<CouponDms, Long> {

    Page<CouponDms> pagingBy(GridPage page, String seqNo);

    void updateSendDmsStatus(String sendDmsStatusSource, String sendDmsStatusTarget);

    List<CouponDms> findBySendDmsStatus(String sendDmsStatus, Date sendDate);

    List<CouponDms> findBySendDmsStatus(String sendDmsStatus);

    List<CouponDms> findBySeqNo(String orderNo);

    List<CouponDms> findBySendDmsStatusAndDmsResultAndSendSmsStatusNot(String sendDmsStatus, String dmsResult, String sendSmsStatus);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CouponDms> pagingBy(String sendDmsStatus,String targetDealer, String batchNo,String vin, String customerMobile,String sendSms, String createStartDate, String createEndDate, GridPage page);

}
