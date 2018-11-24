/*
 * Copyright (c)  2016
 * All rights reserved.
 * RedirectConfigRepository.java 2016-11-02 下午2:07
 */
package com.ford.wechat.respository.config;

import com.ford.wechat.entity.config.RedirectConfig;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface RedirectConfigRepository extends JpaRepository<RedirectConfig, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<RedirectConfig> pagingBy(GridPage page);

    RedirectConfig getItem(String state);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<RedirectConfig> pagingBy(String state, String url, GridPage page);


}
