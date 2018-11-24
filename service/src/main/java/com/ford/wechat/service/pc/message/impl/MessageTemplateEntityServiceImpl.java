/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import com.ford.wechat.repository.pc.message.MessageTemplateEntityRepository;
import com.ford.wechat.repository.pc.message.MessageTemplateParamsEntityRepository;
import com.ford.wechat.service.pc.message.MessageTemplateEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述： MessageTemplateEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MessageTemplateEntityServiceImpl extends AbstractService implements MessageTemplateEntityService {

    @Autowired
    private MessageTemplateEntityRepository repository;

    @Autowired
    private MessageTemplateParamsEntityRepository paramsEntityRepository;

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
        return repository.pagingBy(page, code, name, templateType, useChannel);
    }

    public MessageTemplateEntity get(Long id) {
        return repository.get(id);
    }

    @Override
    public MessageTemplateEntity findByTemplateCode(String templateCode) {
        return repository.findByTemplateCode(templateCode);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(MessageTemplateEntity object, List<MessageTemplateParamsEntity> templateParamsEntityList) {
        repository.save(object);
        for (MessageTemplateParamsEntity param : templateParamsEntityList) {
            param.setTemplateId(object.getId());
        }
        paramsEntityRepository.save(templateParamsEntityList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(MessageTemplateEntity object, List<MessageTemplateParamsEntity> templateParamsEntityList) {
        repository.update(object);
        paramsEntityRepository.deleteByTemplateId(object.getId());
        for (MessageTemplateParamsEntity param : templateParamsEntityList) {
            param.setTemplateId(object.getId());
        }
        paramsEntityRepository.save(templateParamsEntityList);
    }

    @Override
    public MessageTemplateEntity findByCodeAndIdNot(String code, Long id) {
        return repository.findByCodeAndIdNot(code, id);
    }

    @Override
    public List<MessageTemplateParamsEntity> findByTemplateId(Long templateId) {
        return paramsEntityRepository.findByTemplateId(templateId);
    }
}
