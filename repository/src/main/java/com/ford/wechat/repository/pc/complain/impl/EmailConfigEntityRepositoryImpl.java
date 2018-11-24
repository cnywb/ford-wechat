/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * EmailConfigEntityRepositoryImpl.java
 */
package com.ford.wechat.repository.pc.complain.impl;

import com.ford.wechat.entity.pc.complain.EmailConfigEntity;
import com.ford.wechat.repository.pc.complain.EmailConfigEntityRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：EmailConfigEntityRepositoryImpl 仓储服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Repository
public class EmailConfigEntityRepositoryImpl extends DefaultJpaRepository<EmailConfigEntity, Long> implements EmailConfigEntityRepository {

    @Override
    public List<EmailConfigEntity> getByCode(String code) {
        StringQuery query = StringQuery.newQuery()
                .query("from " + EmailConfigEntity.class.getName() + " where code = :code ")
                .param("code", code)
                .build();
        return find(query);
    }
}
