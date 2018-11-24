package com.ford.wechat.respository.coupon.impl;

import com.ford.wechat.entity.coupon.Coupon;
import com.ford.wechat.respository.coupon.CouponRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Repository
public class CouponRepositoryImpl extends DefaultJpaRepository<Coupon, Long> implements CouponRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<Coupon> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and projectName like :projectName")
                .likeParam("projectName", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by projectName desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public int countBy(String dateNo, String projectCode, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("select count(*) as count from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .build();
        List<Object> list = findObject(query);
        if (list.isEmpty()) return 0;

        return Integer.parseInt(list.get(0).toString());
    }


    @Override
    public Coupon findByLatest(String dateNo, String projectCode, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        List<Coupon> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Coupon findBy(String dateNo, String projectCode, String couponNo, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(couponNo)
                .query(" and a.couponNo = :couponNo")
                .param("couponNo", couponNo)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        List<Coupon> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Coupon findBy(String dateNo, String projectCode, String openId, String couponNo, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(openId)
                .query(" and a.openId = :openId")
                .param("openId", openId)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(couponNo)
                .query(" and a.couponNo = :couponNo")
                .param("couponNo", couponNo)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        List<Coupon> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }



    @Override
    public Coupon findBy(String dateNo, String projectCode, String openId, String vin, String couponNo, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(openId)
                .query(" and a.openId = :openId")
                .param("openId", openId)
                .predicateNotNull(vin)
                .query(" and a.vin = :vin")
                .param("vin", vin)
                .predicclubateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(couponNo)
                .query(" and a.couponNo = :couponNo")
                .param("couponNo", couponNo)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        List<Coupon> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }



    @Override
    public Coupon findOneByRandom(String dateNo, String projectCode, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("select * from (SELECT * FROM WE_COUPON WHERE 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.DATE_NO = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.PROJECT_CODE = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" dbms_random.value) WHERE ROWNUM < 2")
                .build();
        List<Coupon> list = findBySql(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }



    @Override
    public List<Coupon> findBy(String dateNo, String projectCode, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        return find(query);
    }


    @Override
    public Coupon findByCustomer(String projectCode, String openId, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(openId)
                .query(" and a.openId = :openId")
                .param("openId", openId)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        List<Coupon> list = find(query);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public Coupon findByVin(String projectCode, String vin, Integer status) {
        StringQuery query = StringQuery.newQuery()
                .query("from Coupon a where 1 = 1 ")
                .predicateNotNull(vin)
                .query(" and a.vin = :vin")
                .param("vin", vin)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(Boolean.TRUE)
                .query(" order by receivedTime desc")
                .build();
        List<Coupon> list = find(query);
        if (list == null || list.isEmpty()) return null;
        return list.get(0);
    }


    @Override
    public int countByAuthWithEvent(String dateNo, String projectCode, Integer status, Date eventStartDate, Date eventEndDate) {
        StringQuery query = StringQuery.newQuery()
                .query("select count(*) as count from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(true)
                .query(" and a.authTime >= :eventStartDate")
                .param("eventStartDate", eventStartDate)
                .query(" and a.authTime < :eventEndDate")
                .param("eventEndDate", eventEndDate)
                .build();
        List<Object> list = findObject(query);
        if (list.isEmpty()) return 0;

        return Integer.parseInt(list.get(0).toString());
    }

    @Override
    public int countByAuthBeforeEvent(String dateNo, String projectCode, Integer status, Date eventStartDate) {
        StringQuery query = StringQuery.newQuery()
                .query("select count(*) as count from Coupon a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and a.dateNo = :dateNo")
                .param("dateNo", dateNo)
                .predicateNotNull(projectCode)
                .query(" and a.projectCode = :projectCode")
                .param("projectCode", projectCode)
                .predicateNotNull(status)
                .query(" and a.status = :status")
                .param("status", status)
                .predicate(true)
                .query(" and a.authTime < :eventStartDate")
                .param("eventStartDate", eventStartDate)
                .build();
        List<Object> list = findObject(query);
        if (list.isEmpty()) return 0;

        return Integer.parseInt(list.get(0).toString());
    }


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<Coupon> pagingBy(String projectCode, String openId, String vin, Integer batchStatus, Integer status, String createStartDate, String createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from Coupon where 1 = 1 ")

                .predicateHasText (projectCode)
                .query (" and projectCode like :projectCode")
                .likeParam ("projectCode", projectCode)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (vin)
                .query (" and vin like :vin")
                .likeParam ("vin", vin)

                .predicateNotNull (batchStatus)
                .query (" and batchStatus = :batchStatus")
                .param ("batchStatus", batchStatus)

                .predicateNotNull (status)
                .query (" and status = :status")
                .param ("status", status)

                .predicateHasText (createStartDate)
                .query(" and  dateNo  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateHasText (createEndDate)
                .query(" and  dateNo  <=  :createEndDate")
                .param ("createEndDate", createEndDate)

                .predicate (Boolean.TRUE)
                .query (" order by dateNo asc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
