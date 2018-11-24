package com.ford.wechat.respository.auth.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.auth.AuthToDms;
import com.ford.wechat.respository.auth.AuthToDmsRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-19 16:00
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Repository
public class AuthToDmsRepositoryImpl extends DefaultJpaRepository<AuthToDms, Long> implements AuthToDmsRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AuthToDms> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from AuthToDms where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE).build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public Page<AuthToDms> pagingBy(String batchNo, String vin, String openId, Integer channelType, Integer sendDmsStatus, Integer verify, Date createStartDate, Date createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from AuthToDms where 1 = 1 ")

                .predicateHasText (batchNo)
                .query (" and batchNo like :batchNo")
                .likeParam ("batchNo", batchNo)

                .predicateHasText (vin)
                .query (" and vin like :vin")
                .likeParam ("vin", vin)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateNotNull (channelType)
                .query (" and channelType = :channelType")
                .param ("channelType", channelType)

                .predicateNotNull (sendDmsStatus)
                .query (" and sendDmsStatus = :sendDmsStatus")
                .param ("sendDmsStatus", sendDmsStatus)

                .predicateNotNull (verify)
                .query (" and verify = :verify")
                .param ("verify", verify)

                .predicateNotNull (createStartDate)
                .query(" and  sendDmsDate  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateNotNull (createEndDate)
                .query(" and  sendDmsDate  <=  :createEndDate")
                .param ("createEndDate", DateUtils.formatEndDate(createEndDate))

                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
