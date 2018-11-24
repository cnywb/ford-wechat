/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * EmailConfigEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.complain.impl;

import com.ford.wechat.entity.pc.complain.EmailConfigEntity;
import com.ford.wechat.repository.pc.complain.EmailConfigEntityRepository;
import com.ford.wechat.service.pc.complain.EmailConfigEntityService;
import io.dabing.core.service.AbstractService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述： EmailConfigEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class EmailConfigEntityServiceImpl extends AbstractService implements EmailConfigEntityService {

    @Autowired
    private EmailConfigEntityRepository repository;

    @Override
    public EmailConfigEntity getByCode(String code) {
        List<EmailConfigEntity> emailConfigEntityList = repository.getByCode(code);
        if(!CollectionUtils.isEmpty(emailConfigEntityList))
            return emailConfigEntityList.get(0);
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(EmailConfigEntity object) {
        repository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(EmailConfigEntity object) {
        repository.update(object);
    }
}
