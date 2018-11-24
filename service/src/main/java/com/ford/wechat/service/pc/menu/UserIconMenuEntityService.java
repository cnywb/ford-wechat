/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * UserIconMenuEntity.java 2017-05-07 22:16:07
 */
package com.ford.wechat.service.pc.menu;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import com.ford.wechat.entity.pc.menu.UserIconMenuEntity;
import java.util.List;
/**
 * Created by yangkui on 2017-05-07 22:16:07 .
 * @since 1.0
 */
public interface UserIconMenuEntityService  {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page     分页对象，里面有关键字进行模糊匹配
     * @return
     */
    Page<UserIconMenuEntity> findByGridPage(GridPage page);

    void save(UserIconMenuEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(List<UserIconMenuEntity> objectList);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(UserIconMenuEntity object);

    /**
    * 逻辑删除 deleted true
    */
    void deleteByLogic(Long id);

    /**
    * 物理删除
    */
    void delete(UserIconMenuEntity object);

    /**
    * 物理删除
    */
    void delete(Long id);

    void update(UserIconMenuEntity object);

    UserIconMenuEntity get(Long id);

    /**
     * 根据OPENID和vin码查询用户自定义的菜单排序
     * @param openId
     * @param vin
     * @return
     */
    List<UserIconMenuEntity> findByOpenIdAndVin(String openId,String vin);
}