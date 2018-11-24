/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * ActivityEntityService.java
 */

package com.ford.wechat.service.pc.activity;

import com.ford.wechat.entity.pc.activity.ActivityEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：ActivityEntityService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface ActivityEntityService extends Service {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<ActivityEntity> pagingBy(GridPage page);

    ActivityEntity get(Long id);

    void save(ActivityEntity object);

    void delete(Long id);

    void update(ActivityEntity object);

    List<ActivityEntity> findAll();
}
