/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * GroupRepositoryImpl.java 2016-11-02 下午2:10
 */
package com.ford.wechat.respository.group.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.group.Group;
import com.ford.wechat.respository.group.GroupRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Repository
public class GroupRepositoryImpl extends DefaultJpaRepository<Group, Long> implements GroupRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Group> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from Group where 1 = 1 ")
                .predicateHasText (page.getKeyWord ())
                .query (" and name like :name")
                .likeParam ("name", page.getKeyWord ())
                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param openId
     * @param name
     * @param mobile
     * @param favourCarName
     * @param dealer
     * @param appLinkName
     * @param buyStartDate
     * @param buyEndDate
     * @param page          分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<Group> pagingBy(String openId, String name, String mobile, String favourCarName, String dealer, String appLinkName, Date buyStartDate, Date buyEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from Group where 1 = 1 ")

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (name)
                .query (" and name like :name")
                .likeParam ("name", name)

                .predicateHasText (mobile)
                .query (" and mobile like :mobile")
                .likeParam ("mobile", mobile)

                .predicateHasText (favourCarName)
                .query (" and favourCarName like :favourCarName")
                .likeParam ("favourCarName", favourCarName)

                .predicateHasText (dealer)
                .query (" and dealer like :dealer")
                .likeParam ("dealer", dealer)

                .predicateHasText (appLinkName)
                .query (" and appLinkName like :appLinkName")
                .likeParam ("appLinkName", appLinkName)

                .predicateNotNull (buyStartDate)
                .query(" and  buyDate  >  :buyStartDate")
                .param ("buyStartDate", buyStartDate)

                .predicateNotNull (buyEndDate)
                .query(" and  buyDate  <  :buyEndDate")
                .param ("buyEndDate", DateUtils.formatEndDate(buyEndDate))


                .predicate (Boolean.TRUE)
                .query (" order by id desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
