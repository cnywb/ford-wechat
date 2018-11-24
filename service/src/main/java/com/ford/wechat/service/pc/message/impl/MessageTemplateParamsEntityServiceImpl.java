/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateParamsEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import com.ford.wechat.repository.pc.message.MessageTemplateParamsEntityRepository;
import com.ford.wechat.service.pc.message.MessageTemplateParamsEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述： MessageTemplateParamsEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MessageTemplateParamsEntityServiceImpl extends AbstractService implements MessageTemplateParamsEntityService {

    @Autowired
    private MessageTemplateParamsEntityRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<MessageTemplateParamsEntity> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    public MessageTemplateParamsEntity get(Long id) {
        return repository.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(MessageTemplateParamsEntity object) {
        repository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(MessageTemplateParamsEntity object) {
        repository.update(object);
    }
}
