/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * SurveyConfigServiceImpl.java 2016-11-02 下午2:26
 */
package com.ford.wechat.service.hub.impl;

import com.ford.wechat.entity.hub.Redirection;
import com.ford.wechat.respository.hub.RedirectionRepository;
import com.ford.wechat.service.hub.RedirectionService;
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
public class RedirectionServiceImpl extends AbstractService implements RedirectionService {

    @Autowired
    RedirectionRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Redirection> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    public void save(Redirection object) {
        repository.save (object);
    }

    public void delete(List<Redirection> objectList) {
        repository.delete (objectList);
    }

    public void delete(Redirection object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(Redirection object) {
        repository.update (object);
    }

    public Redirection get(Long id) {
        return repository.get (id);
    }

    @Override
    public Redirection getItem(String state) {
        return repository.getItem(state);
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
        return repository.pagingBy(state, url, page);
    }
}
