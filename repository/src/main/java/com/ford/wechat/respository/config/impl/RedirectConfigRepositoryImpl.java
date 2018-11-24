/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * RedirectConfigRepositoryImpl.java 2016-11-02 下午2:08
 */
package com.ford.wechat.respository.config.impl;

import com.ford.wechat.entity.config.RedirectConfig;
import com.ford.wechat.respository.config.RedirectConfigRepository;
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
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Repository
public class RedirectConfigRepositoryImpl extends DefaultJpaRepository<RedirectConfig, Long> implements RedirectConfigRepository {

    @Override
    public RedirectConfig getItem(String state) {
        StringQuery query = StringQuery.newQuery()
                .query("from RedirectConfig where 1 = 1")
                .query(" and state = :state ")
                .param("state", state)
                .build();
        List<RedirectConfig> reds = find(query);
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
    public Page<RedirectConfig> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from RedirectConfig where 1 = 1 ")
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
     * @param state
     * @param url
     * @param page  分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<RedirectConfig> pagingBy(String state, String url, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from RedirectConfig where 1 = 1 ")

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
