/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.activity.impl;

import com.ford.wechat.entity.pc.activity.ActivityEntity;
import com.ford.wechat.repository.pc.activity.ActivityEntityRepository;
import com.ford.wechat.service.pc.activity.ActivityEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述： ActivityEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class ActivityEntityServiceImpl extends AbstractService implements ActivityEntityService {

    @Autowired
    private ActivityEntityRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<ActivityEntity> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    public ActivityEntity get(Long id) {
        return repository.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ActivityEntity object) {
        repository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ActivityEntity object) {
        repository.update(object);
    }

    @Override
    public List<ActivityEntity> findAll() {
        return repository.findAll();
    }
}
