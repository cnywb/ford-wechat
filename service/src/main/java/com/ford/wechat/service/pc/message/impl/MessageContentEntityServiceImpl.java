/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.entity.pc.message.MessageContentEntity;
import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.repository.pc.message.MessageContentEntityRepository;
import com.ford.wechat.repository.pc.message.MessageRecordEntityRepository;
import com.ford.wechat.service.pc.message.MessageContentEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述： MessageContentEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MessageContentEntityServiceImpl extends AbstractService implements MessageContentEntityService {

    @Autowired
    private MessageContentEntityRepository repository;
    @Autowired
    private MessageRecordEntityRepository recordEntityRepository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<MessageContentEntity> pagingBy(GridPage page, String vin, String mobile, String msClass, String msgType, String pushChannel) {
        return repository.pagingBy(page, vin, mobile, msClass, msgType, pushChannel);
    }

    public MessageContentEntity get(Long id) {
        return repository.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(MessageContentEntity object, List<MessageRecordEntity> recordEntityList) {
        repository.save(object);
        recordEntityRepository.save(recordEntityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(MessageContentEntity contentEntity, MessageRecordEntity recordEntity) {
        repository.save(contentEntity);
        recordEntity.setContent(contentEntity);
        recordEntityRepository.save(recordEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        List<MessageRecordEntity> recordEntityList = recordEntityRepository.findByContentId(id);
        recordEntityRepository.delete(recordEntityList);
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(MessageContentEntity object) {
        repository.update(object);
    }
}
