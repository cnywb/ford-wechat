/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityEntityRepository.java
 */
package com.ford.wechat.repository.pc.activity;

import com.ford.wechat.entity.pc.activity.ActivityEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：ActivityEntityRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface ActivityEntityRepository extends JpaRepository<ActivityEntity, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<ActivityEntity> pagingBy(GridPage page);

    List<ActivityEntity> findAll();
}
