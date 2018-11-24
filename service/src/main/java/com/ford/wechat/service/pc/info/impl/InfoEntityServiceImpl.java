/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * InfoEntityServiceImpl.java 2017-05-16 19:31:48
 */
package com.ford.wechat.service.pc.info.impl;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ford.wechat.repository.pc.info.InfoEntityRepository;
import com.ford.wechat.entity.pc.info.InfoEntity;
import com.ford.wechat.service.pc.info.InfoEntityService;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by ziv.hung on 2017-05-16 19:31:48 .
 * @since 1.0
 */
@Service
public class InfoEntityServiceImpl  implements InfoEntityService {
    @Autowired
    private InfoEntityRepository repository;
    /**
    * 根据GridPage对象按分页查找服务
    *
    * @param page     分页对象，里面有关键字进行模糊匹配
    * @return
    */
    @Override
    public Page<InfoEntity> findByGridPage(GridPage page) {
        return repository.findByGridPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void save(InfoEntity object) {
        repository.save(object);
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(List<InfoEntity> objectList) {
        for (InfoEntity object : objectList) {
             deleteByLogic(object.getId());
        }
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(InfoEntity object) {
        deleteByLogic(object.getId());
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(Long id) {
        InfoEntity objectDb = this.get(id);
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
    public void delete(InfoEntity object) {
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
    public void update(InfoEntity object) {
        repository.update(object);
    }

    public InfoEntity get(Long id) {
        return repository.get(id);
    }
}