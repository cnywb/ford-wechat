/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ActivityEntityRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.activity.impl;

import com.ford.wechat.entity.pc.activity.ActivityEntity;
import com.ford.wechat.repository.pc.activity.ActivityEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：ActivityEntityRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class ActivityEntityRepositoryImpl extends DefaultJpaRepository<ActivityEntity, Long> implements ActivityEntityRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<ActivityEntity> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + ActivityEntity.class.getName() + " a where 1 = 1 ")
                .predicateHasText(page.getKeyWord())
                .query(" and a.name like :name")
                .likeParam("name", page.getKeyWord()).predicate(true).query(" order by sortNo").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List<ActivityEntity> findAll() {
        StringQuery query = StringQuery.newQuery()
                .query("from " + ActivityEntity.class.getName() + " order by sortNo");
        return find(query);
    }
}
