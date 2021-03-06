/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * RedirectConfig.java 2016-11-02 下午2:25
 */

package com.ford.wechat.service.config;


import com.ford.wechat.entity.config.RedirectConfig;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface RedirectConfigService {

    Page<RedirectConfig> pagingBy(GridPage page);

    void save(RedirectConfig object);

    void delete(List<RedirectConfig> objectList);

    void delete(RedirectConfig object);

    void delete(Long id);

    void update(RedirectConfig object);

    RedirectConfig get(Long id);

    RedirectConfig getItem(String state);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<RedirectConfig> pagingBy(String state, String url, GridPage page);

}
