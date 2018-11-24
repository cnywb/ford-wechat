/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateParamsEntityRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.message.impl;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import com.ford.wechat.repository.pc.message.MessageTemplateParamsEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：MessageTemplateParamsEntityRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MessageTemplateParamsEntityRepositoryImpl extends DefaultJpaRepository<MessageTemplateParamsEntity, Long> implements MessageTemplateParamsEntityRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<MessageTemplateParamsEntity> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageTemplateParamsEntity a where a.deleted = :deleted ")
                .param("deleted", false)
                .predicateHasText(page.getKeyWord())
                .query(" and a.name like :name")
                .likeParam("name", page.getKeyWord()).build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public void deleteByTemplateId(Long id) {
        StringQuery query = StringQuery.newQuery()
                .query("delete from MessageTemplateParamsEntity a where a.templateId = :templateId ")
                .param("templateId", id).build();
        executeUpdate(query);
    }

    @Override
    public List<MessageTemplateParamsEntity> findByTemplateId(Long id) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageTemplateParamsEntity a where a.templateId = :templateId ")
                .param("templateId", id).build();
        return find(query);
    }
}
