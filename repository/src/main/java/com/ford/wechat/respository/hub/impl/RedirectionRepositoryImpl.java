/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * SurveyConfigRepositoryImpl.java 2016-11-02 下午2:08
 */
package com.ford.wechat.respository.hub.impl;

import com.ford.wechat.entity.hub.Redirection;
import com.ford.wechat.respository.hub.RedirectionRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author Neel create on 2016-11-02
 * @since 1.0
 */
@Repository
public class RedirectionRepositoryImpl extends DefaultJpaRepository<Redirection, Long> implements RedirectionRepository {

    @Override
    public Redirection getItem(String state) {
        StringQuery query = StringQuery.newQuery()
                .query("from Redirection where 1 = 1")
                .query(" and state = :state ")
                .param("state", state)
                .build();
        List<Redirection> reds = find(query);
        if (null != reds && reds.size() > 0)
            return reds.get(0);
        return null;
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Redirection> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from Redirection where 1 = 1 ")
                .predicateHasText (page.getKeyWord ())
                .query (" and state like :state")
                .likeParam ("state", page.getKeyWord ())
                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param state
     * @param url
     * @param page  分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<Redirection> pagingBy(String state, String url, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from Redirection where 1 = 1 ")

                .predicateHasText (state)
                .query (" and state like :state")
                .likeParam ("state", state)

                .predicateHasText (url)
                .query (" and url like :url")
                .likeParam ("url", url)

                .predicate (Boolean.TRUE)
                .query (" order by id desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
