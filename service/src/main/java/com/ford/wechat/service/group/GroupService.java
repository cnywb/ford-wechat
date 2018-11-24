/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * Group.java 2016-11-02 下午2:28
 */

package com.ford.wechat.service.group;


import com.ford.wechat.entity.group.Group;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface GroupService {

    Page<Group> pagingBy(GridPage page);

    void save(Group object);

    void delete(List<Group> objectList);

    void delete(Group object);

    void delete(Long id);

    void update(Group object);

    Group get(Long id);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<Group> pagingBy(String openId, String name, String mobile, String favourCarName, String dealer, String appLinkName, Date buyStartDate, Date buyEndDate, GridPage page);
}
