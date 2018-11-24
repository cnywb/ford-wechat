/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainEntityRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.complain.impl;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.ford.wechat.repository.pc.complain.ComplainEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 描述：ComplainEntityRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class ComplainEntityRepositoryImpl extends DefaultJpaRepository<ComplainEntity, Long> implements ComplainEntityRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page      分页对象，里有关键字keyWord,供模糊匹配
     * @param startDate
     * @param endDate   @return 分页结果数据对象集合
     */
    public Page<ComplainEntity> pagingBy(GridPage page, Date startDate, Date endDate) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + ComplainEntity.class.getName() + " a where 1 = 1 ")
                .predicateNotNull(startDate)
                .query(" and a.createdDate >= :startDate")
                .param("startDate", startDate)
                .predicateNotNull(endDate)
                .query(" and a.createdDate < :endDate")
                .param("endDate", endDate)
                .predicate(true)
                .query(" order by a.createdDate desc")
                .build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List<ComplainEntity> findByOpenId(String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + ComplainEntity.class.getName() + " a where openId = :openId ")
                .param("openId", openId)
                .predicate(true)
                .query(" order by a.firstInsert desc")
                .build();
        return find(query);

    }

    @Override
    public List findForExcel(Date startDate, Date endDate) {
        StringQuery query = StringQuery.newQuery()
                .query("select a.customerName, a.customerMobile, a.customerVin, to_char(a.createdDate,'yyyy-mm-dd hh24:mi:ss'), a.complainObject, a.complainReason, a.involveDealer, a.incidentDesc, a.license, a.address, a.exerciseMileage from " + ComplainEntity.class.getName() + " a where 1 = 1 ")
                .predicateNotNull(startDate)
                .query(" and a.createdDate >= :startDate")
                .param("startDate", startDate)
                .predicateNotNull(endDate)
                .query(" and a.createdDate < :endDate")
                .param("endDate", endDate)
                .predicate(true)
                .query(" order by a.createdDate desc")
                .build();
        return find(query);
    }

    @Override
    public List<ComplainEntity> findBySendMail(String sendMail) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + ComplainEntity.class.getName() + " a where sendMail = :sendMail ")
                .param("sendMail", sendMail)
                .predicate(true)
                .query(" order by a.id desc")
                .build();
        return find(query);
    }

    @Override
    public void updateSendMailBySendMail(String sendMailTarget, String sendMailWhere) {
        StringQuery query = StringQuery.newQuery()
                .query("update " + ComplainEntity.class.getName() + " set sendMail = :sendMailTarget ,sendDate =:sendDate where sendMail = :sendMailWhere ")
                .param("sendMailTarget", sendMailTarget)
                .param("sendDate", new Date())
                .param("sendMailWhere", sendMailWhere)
                .build();
        executeUpdate(query);
    }
}
