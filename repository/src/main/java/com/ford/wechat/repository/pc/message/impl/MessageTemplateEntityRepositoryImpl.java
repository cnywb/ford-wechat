/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.message.impl;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.repository.pc.message.MessageTemplateEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：MessageTemplateEntityRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MessageTemplateEntityRepositoryImpl extends DefaultJpaRepository<MessageTemplateEntity, Long> implements MessageTemplateEntityRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page         分页对象，里有关键字keyWord,供模糊匹配
     * @param code
     * @param name
     * @param templateType
     * @param useChannel   @return 分页结果数据对象集合
     */
    public Page<MessageTemplateEntity> pagingBy(GridPage page, String code, String name, String templateType, String useChannel) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + MessageTemplateEntity.class.getName() + " a where 1 = 1 ")
                .predicateHasText(code)
                .query(" and a.name = :code")
                .param("code", code)
                .predicateHasText(name)
                .query(" and a.name = :name")
                .param("name", name)
                .predicateHasText(templateType)
                .query(" and a.templateType = :templateType")
                .param("templateType", templateType)
                .predicateHasText(useChannel)
                .query(" and a.useChannel like :useChannel")
                .likeParam("useChannel", useChannel).build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public MessageTemplateEntity findByCodeAndIdNot(String code, Long id) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + MessageTemplateEntity.class.getName() + " a where a.code = :code")
                .param("code", code)
                .predicateNotNull(id)
                .query(" and a.id <> :id")
                .param("id", id).build();
        List<MessageTemplateEntity> templateEntityList = find(query);
        if (templateEntityList == null || templateEntityList.size() == 0) {
            return null;
        }
        return templateEntityList.get(0);
    }

    @Override
    public MessageTemplateEntity findByTemplateCode(String templateCode) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + MessageTemplateEntity.class.getName() + " a where a.code = :code")
                .param("code", templateCode)
                .query(" order by a.id desc").build();
        List<MessageTemplateEntity> templateEntityList = find(query);
        if (templateEntityList == null || templateEntityList.size() == 0) {
            return null;
        }
        return templateEntityList.get(0);
    }
}
