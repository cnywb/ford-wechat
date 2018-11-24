/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * GroupServiceImpl.java 2016-11-02 下午2:28
 */
package com.ford.wechat.service.group.impl;

import com.ford.wechat.entity.group.Group;
import com.ford.wechat.respository.group.GroupRepository;
import com.ford.wechat.service.group.GroupService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Service
public class GroupServiceImpl extends AbstractService implements GroupService {
    @Autowired
    GroupRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<Group> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    public void save(Group object) {
        repository.save (object);
    }

    public void delete(List<Group> objectList) {
        repository.delete (objectList);
    }

    public void delete(Group object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(Group object) {
        repository.update (object);
    }

    public Group get(Long id) {
        return repository.get (id);
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param openId
     * @param name
     * @param mobile
     * @param favourCarName
     * @param dealer
     * @param appLinkName
     * @param buyStartDate
     * @param buyEndDate
     * @param page          分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<Group> pagingBy(String openId, String name, String mobile, String favourCarName, String dealer, String appLinkName, Date buyStartDate, Date buyEndDate, GridPage page) {
        return repository.pagingBy(openId, name, mobile, favourCarName, dealer, appLinkName, buyStartDate, buyEndDate, page);
    }
}
