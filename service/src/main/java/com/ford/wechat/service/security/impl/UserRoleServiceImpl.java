/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved.
 * UserRoleServiceImpl.java 2015-11-02 14:37:12
 */
package com.ford.wechat.service.security.impl;


import com.ford.wechat.entity.security.BasUserRole;
import com.ford.wechat.respository.security.UserRoleRepository;
import com.ford.wechat.service.security.UserRoleService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ziv.hung on 2015-11-02 14:37:12 .
 *
 * @since 1.0
 */
@Service
public class UserRoleServiceImpl extends AbstractService implements UserRoleService {
    @Autowired
    private UserRoleRepository repository;

    @Cacheable(value = "userRoleCache", key = "#userId+'findBy'")
    public List<BasUserRole> findBy(Long userId, String roleCode) {
        return repository.findBy(userId, roleCode);
    }

    public Page<BasUserRole> findBy(Long userId, String roleCode, GridPage page) {
        Page<BasUserRole> rolePage=repository.findBy(userId, roleCode, page);
        for(BasUserRole userRole:rolePage.getContent ()){
            userRole.getUser ().getId ();
        }
        return rolePage;
    }

    public Boolean existUserRole(Long userId, String roleCode) {
        List<BasUserRole> userRoleList = repository.findBy(userId, roleCode);
        if (userRoleList != null && userRoleList.size() > 0)
            return Boolean.TRUE;
        return Boolean.FALSE;
    }

    @CacheEvict(value = "userRoleCache", key = "#userRole.user.id + 'findBy'")
    public void save(BasUserRole userRole) {
        repository.save(userRole);
    }

    @CacheEvict(value = "userRoleCache")
    public void delete(List<BasUserRole> userRoleList) {
        repository.delete(userRoleList);
    }

    /**
     * 根据用户ID,删除角色
     *
     * @param userId
     */
    @Override
    public void deleteByUserId(Long userId) {
        this.repository.deleteByUserId(userId);
    }

    @CacheEvict(value = "userRoleCache")
    public void delete(String roleCode) {
        List<BasUserRole> userRoleList = repository.findBy(null, roleCode);
        repository.delete(userRoleList);
    }

    public void update(BasUserRole object) {
        repository.update(object);
    }

    public BasUserRole get(Long id) {
        return repository.get(id);
    }
}