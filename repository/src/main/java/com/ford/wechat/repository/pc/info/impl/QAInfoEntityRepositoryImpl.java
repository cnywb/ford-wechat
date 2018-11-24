/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntity.java 2017-05-16 19:35:15
 */
package com.ford.wechat.repository.pc.info.impl;

import org.springframework.stereotype.Repository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import com.ford.wechat.entity.pc.info.QAInfoEntity;
import com.ford.wechat.repository.pc.info.QAInfoEntityRepository;

/**
 * Created by ziv.hung on 2017-05-16 19:35:15 .
 *
 * @since 1.0
 */
@Repository
public class QAInfoEntityRepositoryImpl extends DefaultJpaRepository<QAInfoEntity, Long> implements QAInfoEntityRepository {
    /**
     * 根据GridPage对象按分页查找服务
     * @param page 分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<QAInfoEntity> findByGridPage(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from "+QAInfoEntity.class.getName()+" a where 1=1 ")
                .predicateHasText(page.getKeyWord())
                    .query(" and (a.question like :keyword or a.tags like :keyword)")
                    .likeParam("keyword", page.getKeyWord())
                .query(" order by a.id desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }
}