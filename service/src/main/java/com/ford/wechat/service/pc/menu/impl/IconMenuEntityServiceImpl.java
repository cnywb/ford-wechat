/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * IconMenuEntityServiceImpl.java 2017-05-05 15:37:27
 */
package com.ford.wechat.service.pc.menu.impl;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ford.wechat.repository.pc.menu.IconMenuEntityRepository;
import com.ford.wechat.entity.pc.menu.IconMenuEntity;
import com.ford.wechat.service.pc.menu.IconMenuEntityService;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by yangkui on 2017-05-05 15:37:27 .
 * @since 1.0
 */
@Service
@Slf4j
public class IconMenuEntityServiceImpl  implements IconMenuEntityService {
    @Autowired
    private IconMenuEntityRepository repository;
    /**
    * 根据GridPage对象按分页查找服务
    *
    * @param page     分页对象，里面有关键字进行模糊匹配
    * @return
    */
    @Override
    public Page<IconMenuEntity> findByGridPage(GridPage page) {
        return repository.findByGridPage(page);
    }


    @Override
    public List<IconMenuEntity> findAll() {
       log.info("从数据库查询所有菜单");
        return repository.findAll();
    }

    /**
     * 查询所有首页图标菜单
     * @return
     */
    @Override
    public List<IconMenuEntity> findRecommendMenus(){
        log.info("从数据库查询所有推荐的菜单");
        return repository.findRecommendMenus();
    }



    /**
     * 查询无缓存的数据
     *
     * @return
     */
    @Override
    public List<IconMenuEntity> findAllNoCache() {
        return repository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void save(IconMenuEntity object) {
        repository.save(object);
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(List<IconMenuEntity> objectList) {
        for (IconMenuEntity object : objectList) {
             deleteByLogic(object.getId());
        }
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(IconMenuEntity object) {
        deleteByLogic(object.getId());
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(Long id) {
        IconMenuEntity objectDb = this.get(id);
        if (objectDb == null) {
            return;
        }
        //objectDb.setDeleted(true);
        this.repository.update(objectDb);
    }

    /**
    * 物理删除
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void delete(IconMenuEntity object) {
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
    public void update(IconMenuEntity object) {
        repository.update(object);
    }

    public IconMenuEntity get(Long id) {
        return repository.get(id);
    }
}