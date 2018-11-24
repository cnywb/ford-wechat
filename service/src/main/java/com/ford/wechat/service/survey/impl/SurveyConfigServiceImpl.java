/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * SurveyConfigServiceImpl.java 2016-11-02 下午2:26
 */
package com.ford.wechat.service.survey.impl;

import com.ford.wechat.entity.survey.SurveyConfig;
import com.ford.wechat.respository.survey.SurveyConfigRepository;
import com.ford.wechat.service.survey.SurveyConfigService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author Neel create on 2016-11-02
 * @since 1.0
 */
@Service
public class SurveyConfigServiceImpl extends AbstractService implements SurveyConfigService {

    @Autowired
    SurveyConfigRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<SurveyConfig> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    public void save(SurveyConfig object) {
        repository.save (object);
    }

    public void delete(List<SurveyConfig> objectList) {
        repository.delete (objectList);
    }

    public void delete(SurveyConfig object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(SurveyConfig object) {
        repository.update (object);
    }

    public SurveyConfig get(Long id) {
        return repository.get (id);
    }

    @Override
    public SurveyConfig getItem(String shortId) {
        return repository.getItem(shortId);
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
        return repository.pagingBy(shortId, redirectUrl, page);
    }
}
