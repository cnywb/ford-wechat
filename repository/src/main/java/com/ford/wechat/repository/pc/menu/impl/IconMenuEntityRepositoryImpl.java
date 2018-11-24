/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntity.java 2017-05-05 15:37:27
 */
package com.ford.wechat.repository.pc.menu.impl;

import org.springframework.stereotype.Repository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import com.ford.wechat.entity.pc.menu.IconMenuEntity;
import com.ford.wechat.repository.pc.menu.IconMenuEntityRepository;

import java.util.List;

/**
 * Created by yangkui on 2017-05-05 15:37:27 .
 *
 * @since 1.0
 */
@Repository
public class IconMenuEntityRepositoryImpl extends DefaultJpaRepository<IconMenuEntity, Long> implements IconMenuEntityRepository {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<IconMenuEntity> findByGridPage(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + IconMenuEntity.class.getName() + " a where (1=1) ")
                .predicateHasText(page.getKeyWord())
                .query(" and a.name like :name")
                .likeParam("name", page.getKeyWord())
                .query(" order by a.order desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List<IconMenuEntity> findAll() {
        StringQuery query = StringQuery.newQuery()
                .query("from " + IconMenuEntity.class.getName() + " a")
                .query(" order by a.order desc")
                .build();
        return find(query);
    }

    /**
     * 查询所有推荐的菜单
     *
     * @return
     */
    @Override
    public List<IconMenuEntity> findRecommendMenus() {
        StringQuery query = StringQuery.newQuery()
                .query("from " + IconMenuEntity.class.getName() + " a where ")
                .query(" a.recommend =:recommend")
                .param("recommend", true)
                .query(" order by a.order desc")
                .build();
        return find(query);

    }
}