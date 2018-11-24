/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageRecordServiceImpl.java
 */
package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import com.ford.wechat.repository.pc.message.MessageRecordEntityRepository;
import com.ford.wechat.service.pc.message.MessageRecordEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述： MessageRecordServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MessageRecordEntityServiceImpl extends AbstractService implements MessageRecordEntityService {

    @Autowired
    private MessageRecordEntityRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<MessageRecordEntity> pagingBy(GridPage page, String openId, String vin, String msgClass, String msgType, String pushChannel) {
        return repository.pagingBy(page, openId, vin, msgClass, msgType, pushChannel);
    }

    public Page<MessageRecordEntity> pagingAdminBy(GridPage page, String vin, String msgClass, String msgType, String pushChannel)  {
        return repository.pagingAdminBy(page, vin, msgClass, msgType, pushChannel);
    }

    public MessageRecordEntity get(Long id) {
        return repository.get(id);
    }

    @Override
    public List<MessageRecordEntity> findByValidRecord() {
        return repository.findByValidRecord();
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(MessageRecordEntity object) {
        repository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(MessageRecordEntity object) {
        repository.update(object);
    }
}
