/*
 * Copyright (c)  2016
 * All rights reserved.
 * QuestionRepository.java 2016-11-02 下午2:17
 */
package com.ford.wechat.respository.question;

import com.ford.wechat.entity.question.Question;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Deprecated
    Page<Question> pagingBy(GridPage page);


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
    Page<Question> pagingBy(String openId, String type, String title, Integer status, Date answerStartDate, Date answerEndDate,Date createStartDate,Date createEndDate, GridPage page);

}
