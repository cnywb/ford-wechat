/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * RedirectConfigServiceImpl.java 2016-11-02 下午2:26
 */
package com.ford.wechat.service.config.impl;

import com.ford.wechat.service.config.RedirectConfigService;
import com.ford.wechat.entity.config.RedirectConfig;
import com.ford.wechat.respository.config.RedirectConfigRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Service
public class RedirectConfigServiceImpl extends AbstractService implements RedirectConfigService {
    @Autowired
    RedirectConfigRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<RedirectConfig> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    public void save(RedirectConfig object) {
        repository.save (object);
    }

    public void delete(List<RedirectConfig> objectList) {
        repository.delete (objectList);
    }

    public void delete(RedirectConfig object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(RedirectConfig object) {
        repository.update (object);
    }

    public RedirectConfig get(Long id) {
        return repository.get (id);
    }

    @Override
    public RedirectConfig getItem(String state) {
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
    public Page<RedirectConfig> pagingBy(String state, String url, GridPage page) {
        return repository.pagingBy(state, url, page);
    }
}
