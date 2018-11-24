/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.message.impl;

import com.ford.wechat.entity.pc.message.MessageContentEntity;
import com.ford.wechat.repository.pc.message.MessageContentEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * 描述：MessageContentRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MessageContentEntityRepositoryImpl extends DefaultJpaRepository<MessageContentEntity, Long> implements MessageContentEntityRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<MessageContentEntity> pagingBy(GridPage page, String vin, String mobile, String msClass, String msgType, String pushChannel) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageContentEntity a where 1 = 1 ")
                .predicateHasText(vin)
                .query(" and a.vin = :vin")
                .param("vin", vin)
                .predicateHasText(msgType)
                .query(" and a.msgType = :msgType")
                .param("msgType", msgType)
                .predicateHasText(pushChannel)
                .query(" and a.pushChannel like :pushChannel")
                .likeParam("pushChannel", pushChannel)
                .predicate(true)
                .query(" order by a.id desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }
}
