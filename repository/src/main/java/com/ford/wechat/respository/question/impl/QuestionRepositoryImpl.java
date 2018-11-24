/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * QuestionRepositoryImpl.java 2016-11-02 下午11:36
 */
package com.ford.wechat.respository.question.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.question.Question;
import com.ford.wechat.respository.question.QuestionRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Repository
public class QuestionRepositoryImpl extends DefaultJpaRepository<Question, Long> implements QuestionRepository {

    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryImpl.class);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Question> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery().query("from Question where 1=1 and status=2 ")
                .predicateHasText(page.getKeyWord())
                .query(" and content like :content ")
                .likeParam("content", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by id desc").build();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param openId
     * @param type
     * @param title
     * @param status
     * @param page    分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<Question> pagingBy(String openId, String type, String title, Integer status, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from Question where 1 = 1 ")

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (type)
                .query (" and type like :type")
                .likeParam ("type", type)

                .predicateHasText (title)
                .query (" and title like :title")
                .likeParam ("title", title)

                .predicateNotNull (status)
                .query (" and status = :status")
                .param ("status", status)

                .predicate (Boolean.TRUE)
                .query (" order by id desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param openId
     * @param type
     * @param title
     * @param status
     * @param answerStartDate
     * @param answerEndDate
     * @param page            分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<Question> pagingBy(String openId, String type, String title, Integer status, Date answerStartDate, Date answerEndDate, Date createStartDate, Date createEndDate, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from Question where 1 = 1 ")

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (type)
                .query (" and type like :type")
                .likeParam ("type", type)

                .predicateHasText (title)
                .query (" and title like :title")
                .likeParam ("title", title)

                .predicateNotNull (status)
                .query (" and status = :status")
                .param ("status", status)

                .predicateNotNull (answerStartDate)
                .query(" and  answerDate  >=  :answerStartDate")
                .param ("answerStartDate", answerStartDate)

                .predicateNotNull (answerEndDate)
                .query(" and  answerDate  <=  :answerEndDate")
                .param ("answerEndDate", DateUtils.formatEndDate(answerEndDate))

                .predicateNotNull (createStartDate)
                .query(" and  createdDate  >=  :createStartDate")
                .param ("createStartDate", createStartDate)

                .predicateNotNull (createEndDate)
                .query(" and  createdDate  <=  :createEndDate")
                .param ("createEndDate", DateUtils.formatEndDate(createEndDate))

                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
