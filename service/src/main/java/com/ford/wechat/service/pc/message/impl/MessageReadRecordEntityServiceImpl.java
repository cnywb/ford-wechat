/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageReadRecordEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.entity.pc.message.MessageReadRecordEntity;
import com.ford.wechat.repository.pc.message.MessageReadRecordEntityRepository;
import com.ford.wechat.service.pc.message.MessageReadRecordEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 描述： MessageReadRecordEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MessageReadRecordEntityServiceImpl extends AbstractService implements MessageReadRecordEntityService {

    @Autowired
    private MessageReadRecordEntityRepository repository;

    @Override
    public Page<Object[]> pagingBy(GridPage page, String pattern, Date startDate, Date endDate) {
        return repository.pagingBy(page, pattern, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<MessageReadRecordEntity> object) {
        repository.save(object);
    }
}
