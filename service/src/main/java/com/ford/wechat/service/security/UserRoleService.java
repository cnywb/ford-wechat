/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved.
 * UserRole.java 2015-11-02 14:37:12
 */
package com.ford.wechat.service.security;


import com.ford.wechat.entity.security.BasUserRole;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * Created by ziv.hung on 2015-11-02 14:37:12 .
 *
 * @since 1.0
 */
public interface UserRoleService extends Service {

    List<BasUserRole> findBy(Long userId, String roleCode);

    Page<BasUserRole> findBy(Long userId, String roleCode, GridPage page);

    Boolean existUserRole(Long userId, String roleCode);

    void save(BasUserRole object);

    void delete(List<BasUserRole> userRoleList);

    /**
     * 根据用户ID,删除角色
     * @param userId
     */
    void deleteByUserId(Long userId);

    void delete(String roleCode);

    void update(BasUserRole object);

    BasUserRole get(Long id);
}