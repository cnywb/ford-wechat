/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageRecordRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.message.impl;

import com.ford.wechat.entity.pc.message.MessageEnum;
import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.repository.pc.message.MessageRecordEntityRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 描述：MessageRecordRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class MessageRecordEntityRepositoryImpl extends DefaultJpaRepository<MessageRecordEntity, Long> implements MessageRecordEntityRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<MessageRecordEntity> pagingBy(GridPage page, String openId, String vin, String msClass, String msgType, String pushChannel) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageRecordEntity a where 1 = 1 ")
                .query(" and ((a.openId = :openId and a.msgClass = :msgClassGR) or a.msgClass = :msgClassGG)")
                .param("msgClassGR", MessageEnum.MSG_CLASS_GR)
                .param("msgClassGG", MessageEnum.MSG_CLASS_GG)
                .param("openId", openId)
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
                .query(" and a.publishTime <= :nowDate and a.invalidTime >= :nowDate")
                .param("nowDate",new Date())
                .predicate(true)
                .query(" order by a.publishTime desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    public Page<MessageRecordEntity> pagingAdminBy(GridPage page, String vin, String msgClass, String msgType, String pushChannel) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageRecordEntity a where 1 = 1 ")
                .predicateHasText(msgClass)
                .query(" and a.msgClass = :msgClass")
                .param("msgClass", msgClass)
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
                .query(" and a.publishTime <= :nowDate and a.invalidTime >= :nowDate")
                .param("nowDate",new Date())
                .predicate(true)
                .query(" order by a.publishTime desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    @Override
    public List<MessageRecordEntity> findByContentId(Long contentId) {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageRecordEntity a where a.content.id = :contentId")
                .param("contentId", contentId)
                .predicate(true)
                .query(" order by a.id desc").build();
        return find(query);
    }

    @Override
    public List<MessageRecordEntity> findByValidRecord() {
        StringQuery query = StringQuery.newQuery()
                .query("from MessageRecordEntity a where a.publishTime <= :nowDate and a.invalidTime >= :nowDate")
                .param("nowDate", new Date())
                .predicate(true)
                .query(" order by a.publishTime desc").build();
        return find(query);
    }
}
