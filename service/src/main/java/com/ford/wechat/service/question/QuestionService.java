/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * Question.java 2016-11-02 下午2:27
 */

package com.ford.wechat.service.question;


import com.ford.wechat.entity.question.Question;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface QuestionService {

    Page<Question> pagingBy(GridPage page);

    Page<Question> pagingWithOmitTitle(GridPage page);

    void save(Question object);

    void delete(List<Question> objectList);

    void delete(Question object);

    void delete(Long id);

    void update(Question object);

    Question get(Long id);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Deprecated
    Page<Question> pagingBy(String openId,String type,String title,Integer status,GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Question> pagingBy(String openId, String type, String title, Integer status, Date answerStartDate, Date answerEndDate,Date createStartDate,Date createEndDate, GridPage page);
}
