/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * SurveyConfigRepositoryImpl.java 2016-11-02 下午2:08
 */
package com.ford.wechat.respository.survey.impl;

import com.ford.wechat.entity.survey.SurveyCallback;
import com.ford.wechat.respository.survey.SurveyCallbackRepository;
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
public class SurveyCallbackRepositoryImpl extends DefaultJpaRepository<SurveyCallback, Long> implements SurveyCallbackRepository {

    @Override
    public SurveyCallback getItem(String shortId) {
        StringQuery query = StringQuery.newQuery()
                .query("from SurveyCallback where 1 = 1")
                .query(" and shortId = :shortId ")
                .param("shortId", shortId)
                .build();
        List<SurveyCallback> reds = find(query);
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
    public Page<SurveyCallback> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from SurveyCallback where 1 = 1 ")
                .predicateHasText (page.getKeyWord ())
                .query (" and shortId like :shortId")
                .likeParam ("shortId", page.getKeyWord ())
                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param shortId
     * @param openId
     * @param page  分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<SurveyCallback> pagingBy(String shortId, String openId, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from SurveyCallback where 1 = 1 ")

                .predicateHasText (shortId)
                .query (" and shortId like :shortId")
                .likeParam ("shortId", shortId)

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicate (Boolean.TRUE)
                .query (" order by id desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
