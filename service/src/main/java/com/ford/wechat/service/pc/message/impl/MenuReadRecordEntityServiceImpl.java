/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MenuReadRecordEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.message.impl;

import com.ford.wechat.entity.pc.menu.MenuReadRecordEntity;
import com.ford.wechat.repository.pc.message.MenuReadRecordEntityRepository;
import com.ford.wechat.service.pc.message.MenuReadRecordEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 描述： MenuReadRecordEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class MenuReadRecordEntityServiceImpl extends AbstractService implements MenuReadRecordEntityService {

    @Autowired
    private MenuReadRecordEntityRepository repository;

    public Page<Object[]> pagingBy(GridPage page, String datePattern, Date startDate, Date endDate) {
        return repository.pagingBy(page, datePattern, startDate, endDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(List<MenuReadRecordEntity> object) {
        repository.save(object);
    }
}
