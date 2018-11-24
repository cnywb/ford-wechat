/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved.
 * UserRole.java 2015-11-02 14:37:12
 */
package com.ford.wechat.respository.security;

import com.ford.wechat.entity.security.BasUserRole;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * Created by ziv.hung on 2015-11-02 14:37:12.
 * @since 1.0
 */
public interface UserRoleRepository extends JpaRepository<BasUserRole, Long> {

    List<BasUserRole> findBy(Long userId, String roleCode);

    Page<BasUserRole> findBy(Long userId, String roleCode, GridPage page);

    void deleteByUserId(Long userId);
}