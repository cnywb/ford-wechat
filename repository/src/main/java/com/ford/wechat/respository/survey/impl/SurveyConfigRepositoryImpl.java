/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * SurveyConfigRepositoryImpl.java 2016-11-02 下午2:08
 */
package com.ford.wechat.respository.survey.impl;

import com.ford.wechat.entity.survey.SurveyCallback;
import com.ford.wechat.entity.survey.SurveyConfig;
import com.ford.wechat.respository.survey.SurveyCallbackRepository;
import com.ford.wechat.respository.survey.SurveyConfigRepository;
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
public class SurveyConfigRepositoryImpl extends DefaultJpaRepository<SurveyConfig, Long> implements SurveyConfigRepository {

    @Override
    public SurveyConfig getItem(String shortId) {
        StringQuery query = StringQuery.newQuery()
                .query("from SurveyConfig where 1 = 1")
                .query(" and shortId = :shortId ")
                .param("shortId", shortId)
                .build();
        List<SurveyConfig> reds = find(query);
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
    public Page<SurveyConfig> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from SurveyConfig where 1 = 1 ")

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
     * @param redirectUrl
     * @param page  分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<SurveyConfig> pagingBy(String shortId, String redirectUrl, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from SurveyConfig where 1 = 1 ")

                .predicateHasText (shortId)
                .query (" and shortId like :shortId")
                .likeParam ("shortId", shortId)

                .predicateHasText (redirectUrl)
                .query (" and redirectUrl like :redirectUrl")
                .likeParam ("redirectUrl", redirectUrl)

                .predicate (Boolean.TRUE)
                .query (" order by id desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
