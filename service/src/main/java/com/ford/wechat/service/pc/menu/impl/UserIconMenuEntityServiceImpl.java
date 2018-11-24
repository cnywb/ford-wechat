/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * UserIconMenuEntityServiceImpl.java 2017-05-07 22:16:07
 */
package com.ford.wechat.service.pc.menu.impl;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ford.wechat.repository.pc.menu.UserIconMenuEntityRepository;
import com.ford.wechat.entity.pc.menu.UserIconMenuEntity;
import com.ford.wechat.service.pc.menu.UserIconMenuEntityService;
import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
/**
 * Created by yangkui on 2017-05-07 22:16:07 .
 * @since 1.0
 */
@Service
public class UserIconMenuEntityServiceImpl  implements UserIconMenuEntityService {
    @Autowired
    private UserIconMenuEntityRepository repository;
    /**
    * 根据GridPage对象按分页查找服务
    *
    * @param page     分页对象，里面有关键字进行模糊匹配
    * @return
    */
    @Override
    public Page<UserIconMenuEntity> findByGridPage(GridPage page) {
        return repository.findByGridPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void save(UserIconMenuEntity object) {
        repository.save(object);
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(List<UserIconMenuEntity> objectList) {
        for (UserIconMenuEntity object : objectList) {
             deleteByLogic(object.getId());
        }
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(UserIconMenuEntity object) {
        deleteByLogic(object.getId());
    }

    /**
    * 逻辑删除 deleted true
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void deleteByLogic(Long id) {
        UserIconMenuEntity objectDb = this.get(id);
        if (objectDb == null) {
            return;
        }
        this.repository.update(objectDb);
    }

    /**
    * 物理删除
    */
    @Override
    @Transactional(rollbackFor = Exception.class, timeout = 30, propagation = Propagation.REQUIRED)
    public void delete(UserIconMenuEntity object) {
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
    public void update(UserIconMenuEntity object) {
        repository.update(object);
    }

    public UserIconMenuEntity get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据OPENID和vin码查询用户自定义的菜单排序
     *
     * @param openId
     * @param vin
     * @return
     */
    @Override
    @Cacheable(cacheNames = "menus",key = "#openId+'-'=#vin")
    public List<UserIconMenuEntity> findByOpenIdAndVin(String openId, String vin) {
        return repository.findByOpenIdAndVin(openId, vin);
    }

}