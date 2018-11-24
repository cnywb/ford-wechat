/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntity.java 2017-05-16 19:31:47
 */
package com.ford.wechat.repository.pc.info.impl;

import org.springframework.stereotype.Repository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import com.ford.wechat.entity.pc.info.InfoEntity;
import com.ford.wechat.repository.pc.info.InfoEntityRepository;

/**
 * Created by ziv.hung on 2017-05-16 19:31:47 .
 *
 * @since 1.0
 */
@Repository
public class InfoEntityRepositoryImpl extends DefaultJpaRepository<InfoEntity, Long> implements InfoEntityRepository {
    /**
     * 根据GridPage对象按分页查找服务
     * @param page 分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<InfoEntity> findByGridPage(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from InfoEntity a where (1=1) ")
                .predicateHasText(page.getKeyWord())
                    .query(" and a.name like :name")
                    .likeParam("name", page.getKeyWord()).build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }
}