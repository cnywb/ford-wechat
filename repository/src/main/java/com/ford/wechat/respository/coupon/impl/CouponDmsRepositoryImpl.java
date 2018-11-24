package com.ford.wechat.respository.coupon.impl;

import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.respository.coupon.CouponDmsRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Repository
public class CouponDmsRepositoryImpl extends DefaultJpaRepository<CouponDms, Long> implements CouponDmsRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @param seqNo
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<CouponDms> pagingBy(GridPage page, String seqNo) {
        StringQuery query = StringQuery.newQuery()
                .query("from CouponDms a where a.deleted = :deleted ")
                .param("deleted", false)
                .query(" and a.tag = :tag")
                .param("tag", CouponDms.TAG_AUTH_COUPON)
                .predicateHasText(page.getKeyWord())
                .query(" and a.vin = :vin")
                .param("vin", page.getKeyWord())
                .predicateHasText(seqNo)
                .query(" and a.seqNo = :seqNo")
                .param("seqNo", seqNo).build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public void updateSendDmsStatus(String sendDmsStatusSource, String sendDmsStatusTarget) {
        StringQuery query = StringQuery.newQuery()
                .query("update CouponDms a set a.sendDmsStatus =:sendDmsStatusUpdate where a.deleted = :deleted and a.sendDmsStatus =:sendDmsStatus ")
                .query(" and a.tag = :tag")
                .param("tag", CouponDms.TAG_AUTH_COUPON)
                .param("deleted", false)
                .param("sendDmsStatusUpdate", sendDmsStatusTarget)
                .param("sendDmsStatus", sendDmsStatusSource).build();
        executeUpdate(query);
    }

    @Override
    public List<CouponDms> findBySendDmsStatus(String sendDmsStatus, Date sendDate) {
        StringQuery query = StringQuery.newQuery()
                .query("from CouponDms a where a.deleted = :deleted ")
                .param("deleted", false)
                .query(" and a.tag = :tag")
                .param("tag", CouponDms.TAG_AUTH_COUPON)
                .query(" and a.sendDmsStatus = :sendDmsStatus")
                .param("sendDmsStatus", sendDmsStatus)
                .query(" and to_char(a.firstInsert,'yyyy-MM-dd') = to_char(:sendDate,'yyyy-MM-dd')")
                .param("sendDate", sendDate).build();
        return find(query);
    }

    @Override
    public List<CouponDms> findBySendDmsStatus(String sendDmsStatus) {
        StringQuery query = StringQuery.newQuery()
                .query("from CouponDms a where a.deleted = :deleted ")
                .param("deleted", false)
                .query(" and a.tag = :tag")
                .param("tag", CouponDms.TAG_AUTH_COUPON)
                .query(" and a.sendDmsStatus = :sendDmsStatus")
                .param("sendDmsStatus", sendDmsStatus).build();
        return find(query);
    }

    @Override
    public List<CouponDms> findBySeqNo(String orderNo) {
        StringQuery query = StringQuery.newQuery()
                .query("from CouponDms a where a.deleted = :deleted ")
                .param("deleted", false)
                .query(" and a.tag = :tag")
                .param("tag", CouponDms.TAG_AUTH_COUPON)
                .query(" and a.seqNo = :orderNo")
                .param("orderNo", orderNo).build();
        return find(query);
    }

    @Override
    public List<CouponDms> findBySendDmsStatusAndDmsResultAndSendSmsStatusNot(String sendDmsStatus, String dmsResult, String sendSmsStatus) {
        StringQuery query = StringQuery.newQuery()
                .query("from CouponDms a where a.deleted = :deleted ")
                .param("deleted", false)
                .query(" and a.tag = :tag")
                .param("tag", CouponDms.TAG_AUTH_COUPON)
                .query(" and a.sendDmsStatus = :sendDmsStatus")
                .param("sendDmsStatus", sendDmsStatus)
                .query(" and a.dmsResult = :dmsResult")
                .param("dmsResult", dmsResult)
                .query(" and a.sendSms <> :sendSmsStatus")
                .param("sendSmsStatus", sendSmsStatus).build();
        return find(query);
    }

    @Override
    public Page<CouponDms> pagingBy(String sendDmsStatus,String targetDealer, String batchNo, String vin, String customerMobile, String sendSms, String createStartDate, String createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from CouponDms where 1 = 1 ")

                .predicateHasText (sendDmsStatus)
                .query (" and sendDmsStatus like :sendDmsStatus")
                .likeParam ("sendDmsStatus", sendDmsStatus)

                .predicateHasText (targetDealer)
                .query (" and targetDealer like :targetDealer")
                .likeParam ("targetDealer", targetDealer)

                .predicateHasText (batchNo)
                .query (" and batchNo like :batchNo")
                .likeParam ("batchNo", batchNo)

                .predicateHasText (vin)
                .query (" and vin like :vin")
                .likeParam ("vin", vin)

                .predicateHasText (customerMobile)
                .query (" and customerMobile like :customerMobile")
                .likeParam ("customerMobile", customerMobile)

                .predicateHasText (sendSms)
                .query (" and sendSms = :sendSms")
                .param ("sendSms", sendSms)

                .predicateHasText (createStartDate)
                .query(" and  validBeginDate  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateHasText (createEndDate)
                .query(" and  validBeginDate  <=  :createEndDate")
                .param ("createEndDate", createEndDate)

                .predicate (Boolean.TRUE)
                .query (" order by batchNo asc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
