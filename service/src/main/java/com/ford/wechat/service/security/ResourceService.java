/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved.
 * Resource.java 2015-10-31 04:39:39
 */
package com.ford.wechat.service.security;

import com.ford.wechat.entity.security.BasResource;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * Created by yangkui on 2015-10-31 04:39:39.
 *
 * @since 1.0
 */
public interface ResourceService extends Service {

    /**
     * 根据资源类型查找资源
     *
     * @param type 资源类型，@see io.dabing.chuangdao.entity.security.Resource
     * @return
     */

    List<BasResource> findBy(String type);

    /**
     * 根据角色代码获取角色授权的资源(角色授权中树形结构数据)
     *
     * @param roleCode 角色代码
     * @return
     */

    List findTreeByRoleCode(String roleCode, String isMenu, Class clazz);

    void save(BasResource object);

    /**
     * 逻辑删除 deleted true
     */
    void deleteByLogic(List<BasResource> objectList);

    /**
     * 逻辑删除 deleted true
     */
    void deleteByLogic(BasResource object);

    /**
     * 逻辑删除 deleted true
     */
    void deleteByLogic(Long id);

    /**
     * 物理删除
     */
    void delete(BasResource object);

    /**
     * 物理删除
     */
    void delete(Long id);


    void update(BasResource object);

    BasResource get(Long id);
}