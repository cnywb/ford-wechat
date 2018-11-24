/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainEntityServiceImpl.java
 */
package com.ford.wechat.service.pc.complain.impl;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.ford.wechat.repository.pc.complain.ComplainEntityRepository;
import com.ford.wechat.service.pc.complain.ComplainEntityService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 描述： ComplainEntityServiceImpl 服务接口实现层代码类
 *
 * @author ziv
 * @since 1.0
 */
@Service
public class ComplainEntityServiceImpl extends AbstractService implements ComplainEntityService {

    @Autowired
    private ComplainEntityRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page      分页对象，里有关键字keyWord,供模糊匹配
     * @param startDate
     * @param endDate   @return 分页结果数据对象集合
     */
    public Page<ComplainEntity> pagingBy(GridPage page, Date startDate, Date endDate) {
        return repository.pagingBy(page, startDate, endDate);
    }

    public ComplainEntity get(Long id) {
        return repository.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(ComplainEntity object) {
        repository.save(object);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ComplainEntity object) {
        repository.update(object);
    }

    @Override
    public List<ComplainEntity> findBySendMail(String sendMail) {
        return repository.findBySendMail(sendMail);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSendMailBySendMail(String sendMailTarget, String sendMailWhere) {
        repository.updateSendMailBySendMail(sendMailTarget, sendMailWhere);
    }
}
