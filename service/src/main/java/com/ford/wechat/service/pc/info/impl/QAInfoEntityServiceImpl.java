/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * QAInfoEntityServiceImpl.java 2017-05-16 19:35:16
 */
package com.ford.wechat.service.pc.info.impl;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ford.wechat.repository.pc.info.QAInfoEntityRepository;
import com.ford.wechat.entity.pc.info.QAInfoEntity;
import com.ford.wechat.service.pc.info.QAInfoEntityService;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by ziv.hung on 2017-05-16 19:35:16 .
 * @since 1.0
 */
@Service
public class QAInfoEntityServiceImpl  implements QAInfoEntityService {
    @Autowired
    private QAInfoEntityRepository repository;
    /**
    * 根据GridPage对象按分页查找服务
    *
    * @param page     分页对象，里面有关键字进行模糊匹配
    * @return
    */
    @Override
    public Page<QAInfoEntity> findByGridPage(GridPage page) {
        return repository.findByGridPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void save(QAInfoEntity object) {
        repository.save(object);
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(List<QAInfoEntity> objectList) {
        for (QAInfoEntity object : objectList) {
             deleteByLogic(object.getId());
        }
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(QAInfoEntity object) {
        deleteByLogic(object.getId());
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(Long id) {
        QAInfoEntity objectDb = this.get(id);
        if (objectDb == null) {
            return;
        }
//        objectDb.setDeleted(true);
        this.repository.update(objectDb);
    }

    /**
    * 物理删除
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void delete(QAInfoEntity object) {
        this.repository.delete(object);
    }

    /**
    * 物理删除
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        this.repository.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void update(QAInfoEntity object) {
        repository.update(object);
    }

    public QAInfoEntity get(Long id) {
        return repository.get(id);
    }
}