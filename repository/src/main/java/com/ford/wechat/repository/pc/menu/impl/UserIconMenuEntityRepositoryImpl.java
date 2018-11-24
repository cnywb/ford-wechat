/*
 * Copyright (c)  2017, dabing.io
 * All rights reserved.
 * UserIconMenuEntity.java 2017-05-07 22:16:07
 */
package com.ford.wechat.repository.pc.menu.impl;

import org.springframework.stereotype.Repository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import com.ford.wechat.entity.pc.menu.UserIconMenuEntity;
import com.ford.wechat.repository.pc.menu.UserIconMenuEntityRepository;

import java.util.List;

/**
 * Created by yangkui on 2017-05-07 22:16:07 .
 *
 * @since 1.0
 */
@Repository
public class UserIconMenuEntityRepositoryImpl extends DefaultJpaRepository<UserIconMenuEntity, Long> implements UserIconMenuEntityRepository {
    /**
     * 根据GridPage对象按分页查找服务
     * @param page 分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<UserIconMenuEntity> findByGridPage(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from "+UserIconMenuEntity.class.getName()+" a where (1=1) ")
                .predicateHasText(page.getKeyWord())
                    .query(" and a.name like :name")
                    .likeParam("name", page.getKeyWord())
                .query(" order by a.id desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据OPENID和vin码查询用户自定义的菜单排序
     *
     * @param openId
     * @param vin
     * @return
     */
    @Override
    public List<UserIconMenuEntity> findByOpenIdAndVin(String openId, String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from "+UserIconMenuEntity.class.getName()+" a where (1=1) ")
                .predicateHasText(openId)
                .query(" and a.openId =:openId")
                .param("openId",openId)
                .predicateHasText(vin)
                .query(" and a.vin =:vin")
                .param("vin",vin).build();
        return find(query);
    }
}