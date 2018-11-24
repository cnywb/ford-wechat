/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * QuestionServiceImpl.java 2016-11-02 下午2:27
 */
package com.ford.wechat.service.question.impl;

import com.ford.wechat.WechatQuestionFilterWords;
import com.ford.wechat.entity.question.Question;
import com.ford.wechat.respository.question.QuestionRepository;
import com.ford.wechat.service.question.QuestionService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Service
public class QuestionServiceImpl extends AbstractService implements QuestionService {
    @Autowired
    QuestionRepository repository;


    /**
     * 微信前端显示内容
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Question> pagingBy(GridPage page) {
        Page<Question> p = repository.pagingBy(page);
        if (null != p && p.getContent() != null && p.getContent().size() > 0) {
            setFilterContent(p.getContent());
        }
        return p;
    }

    /**
     * 微信前端显示问题列表只显示前20个字段
     *
     * @param page
     * @return
     */
    @Override
    public Page<Question> pagingWithOmitTitle(GridPage page) {
        Page<Question> p = pagingBy(page);
        if (null != p && p.getContent() != null && p.getContent().size() > 0) {
            setTitle(p.getContent());
        }
        return p;
    }

    public void save(Question object) {
        repository.save (object);
    }

    public void delete(List<Question> objectList) {
        repository.delete (objectList);
    }

    public void delete(Question object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(Question object) {
        repository.update (object);
    }

    public Question get(Long id) {
        return repository.get (id);
    }

    private void setFilterContent(List<Question> list) {
        String[] filter = WechatQuestionFilterWords.getFilterWordsArray();
        for (Question q : list) {
            for (String word : filter) {
                q.setTitle(q.getTitle().replace(word, "*"));
                q.setContent(q.getContent().replace(word, "*"));
                q.setAnswerContent(q.getAnswerContent().replace(word, "*"));
            }
        }
    }

    private void setTitle(List<Question> list) {
        for (Question q : list) {
            if (!StringUtils.isEmpty(q.getTitle()) && q.getTitle().length() > 5)
                q.setTitle(q.getTitle().substring(0, 5) + "...");
        }
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param openId
     * @param type
     * @param title
     * @param status
     * @param page   分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<Question> pagingBy(String openId, String type, String title, Integer status, GridPage page) {
        return repository.pagingBy(openId, type, title, status, page);
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
        return repository.pagingBy(openId, type, title, status, answerStartDate, answerEndDate,createStartDate,createEndDate, page);
    }
}
