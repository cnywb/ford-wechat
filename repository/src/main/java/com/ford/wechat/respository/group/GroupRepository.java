/*
 * Copyright (c)  2016
 * All rights reserved.
 * GroupRepository.java 2016-11-02 下午2:10
 */
package com.ford.wechat.respository.group;

import com.ford.wechat.entity.group.Group;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Group> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Group> pagingBy(String openId, String name, String mobile, String favourCarName, String dealer, String appLinkName, Date buyStartDate, Date buyEndDate, GridPage page);

}
