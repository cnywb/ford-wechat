/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * SurveyConfigServiceImpl.java 2016-11-02 下午2:26
 */
package com.ford.wechat.service.survey.impl;

import com.ford.wechat.entity.survey.SurveyCallback;
import com.ford.wechat.respository.survey.SurveyCallbackRepository;
import com.ford.wechat.service.survey.SurveyCallbackService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author Neel create on 2016-11-02
 * @since 1.0
 */
@Service
public class SurveyCallbackServiceImpl extends AbstractService implements SurveyCallbackService {

    @Autowired
    SurveyCallbackRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<SurveyCallback> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, timeout = 30)
    public void save(SurveyCallback object) {
        repository.save (object);
    }

    public void delete(List<SurveyCallback> objectList) {
        repository.delete (objectList);
    }

    public void delete(SurveyCallback object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(SurveyCallback object) {
        repository.update (object);
    }

    public SurveyCallback get(Long id) {
        return repository.get (id);
    }

    @Override
    public SurveyCallback getItem(String shortId) {
        return repository.getItem(shortId);
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
        return repository.pagingBy(shortId, openId, page);
    }
}
