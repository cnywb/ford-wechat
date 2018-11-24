/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * SurveyConfig.java 2016-11-02 下午2:25
 */

package com.ford.wechat.service.hub;


import com.ford.wechat.entity.hub.Redirection;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface RedirectionService {

    Page<Redirection> pagingBy(GridPage page);

    void save(Redirection object);

    void delete(List<Redirection> objectList);

    void delete(Redirection object);

    void delete(Long id);

    void update(Redirection object);

    Redirection get(Long id);

    Redirection getItem(String state);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Redirection> pagingBy(String state, String url, GridPage page);

}
