/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * AppLinkInfoServiceImpl.java 2016-10-22 下午4:06
 */
package com.ford.wechat.service.applink.impl;

import com.ford.wechat.service.applink.AppLinkInfoService;
import com.ford.wechat.entity.applink.AppLinkInfo;
import com.ford.wechat.respository.applink.AppLinkInfoRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
@Service
public class AppLinkInfoServiceImpl extends AbstractService implements AppLinkInfoService {
    @Autowired
    AppLinkInfoRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AppLinkInfo> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    public void save(AppLinkInfo object) {
        repository.save (object);
    }

    public void delete(List<AppLinkInfo> objectList) {
        repository.delete (objectList);
    }

    public void delete(AppLinkInfo object) {
        repository.delete (object);
    }

    public void delete(Long id) {
        repository.delete (id);
    }

    public void update(AppLinkInfo object) {
        repository.update (object);
    }

    public AppLinkInfo get(Long id) {
        return repository.get (id);
    }

    @Override
    public List<AppLinkInfo> doFindList(String plantForm) {
        return repository.doFindList(plantForm);
    }

    @Override
    public Page<AppLinkInfo> pagingBy(String plantForm, String appLinkName, String downloadName, GridPage page) {
        return repository.pagingBy(plantForm, appLinkName, downloadName, page);
    }
}
