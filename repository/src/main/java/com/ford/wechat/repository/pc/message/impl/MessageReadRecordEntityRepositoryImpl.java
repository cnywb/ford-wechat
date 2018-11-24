/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageReadRecordEntityRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.message.impl;

import com.ford.wechat.entity.pc.message.MessageReadRecordEntity;
import com.ford.wechat.repository.pc.message.MessageReadRecordEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 描述：MessageReadRecordEntityRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MessageReadRecordEntityRepositoryImpl extends DefaultJpaRepository<MessageReadRecordEntity, Long> implements MessageReadRecordEntityRepository {

    @Override
    public Page pagingBy(GridPage page, String datePattern, Date startDate, Date endDate) {
        StringQuery query = StringQuery.newQuery()
                .query("select a.MSG_ID,a.MSG_TITLE,TO_CHAR(a.READ_TIME,'"+datePattern+"'),count(1) from MS_MESSAGE_READ_RECORD a where 1 = 1 ")
                .predicateNotNull(startDate)
                .query(" and a.READ_TIME >= :startDate")
                .param("startDate", startDate)
                .predicateNotNull(endDate)
                .query(" and a.READ_TIME <= :endDate")
                .param("endDate", endDate)
                .predicate(true)
                .query(" group by a.MSG_ID,a.MSG_TITLE,TO_CHAR(a.READ_TIME,'"+datePattern+"') order by TO_CHAR(a.READ_TIME,'"+datePattern+"') DESC").build();

        StringQuery countQuery = StringQuery.newQuery()
                .query("select count(1) as aaaaaa from (select a.MSG_ID,a.MSG_TITLE,TO_CHAR(a.READ_TIME,'"+datePattern+"') from MS_MESSAGE_READ_RECORD a where 1 = 1 ")
                .predicateNotNull(startDate)
                .query(" and a.READ_TIME >= :startDate")
                .param("startDate", startDate)
                .predicateNotNull(endDate)
                .query(" and a.READ_TIME <= :endDate")
                .param("endDate", endDate)
                .predicate(true)
                .query(" group by a.MSG_ID,a.MSG_TITLE,TO_CHAR(a.READ_TIME,'"+datePattern+"'))").build();

        return findByMySql(query.getQuery(),countQuery.getQuery(), countQuery.getParams(), PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List findForExcel(String datePattern, Date startDate, Date endDate) {
        StringQuery query = StringQuery.newQuery()
                .query("select a.MSG_ID,a.MSG_TITLE,TO_CHAR(a.READ_TIME,'"+datePattern+"'),count(1) from MS_MESSAGE_READ_RECORD a where 1 = 1 ")
                .predicateNotNull(startDate)
                .query(" and a.READ_TIME >= :startDate")
                .param("startDate", startDate)
                .predicateNotNull(endDate)
                .query(" and a.READ_TIME <= :endDate")
                .param("endDate", endDate)
                .predicate(true)
                .query(" group by a.MSG_ID,a.MSG_TITLE,TO_CHAR(a.READ_TIME,'"+datePattern+"') order by TO_CHAR(a.READ_TIME,'"+datePattern+"') DESC").build();
        return findBySql(query);
    }

    @PersistenceContext
    private EntityManager em;


    private Page findByMySql(String sql, String countSql, NamedParams params, Pageable pageable) {
        Assert.hasText(sql, "Query must has text!");
        Assert.hasText(countSql, "Count sql must has text!");
        Assert.notNull(params, "QueryParams must not be null!");
        Assert.notNull(pageable, "PageRequest must not be null!");

        Query query = this.em.createNativeQuery(sql);
        this.setQueryParams(query, params);
        query.setMaxResults(pageable.getPageSize());
        query.setFirstResult(pageable.getOffset());
        List resultList = query.getResultList();
        Query countQuery = this.em.createNativeQuery(countSql);
        this.setQueryParams(countQuery, params);
        BigDecimal total = (BigDecimal)countQuery.getSingleResult();
        Page page = new Page(resultList, pageable, total.longValue());
        return page;
    }

    private void setQueryParams(Query query, NamedParams params) {
        if(!params.isEmpty()) {
            Map parameters = params.getParameters();
            Iterator i$ = parameters.keySet().iterator();

            while(i$.hasNext()) {
                String paramName = (String)i$.next();
                query.setParameter(paramName, parameters.get(paramName));
            }

        }
    }
}
