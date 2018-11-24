/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * UserInfoServiceImpl.java 2016-11-02 下午2:31
 */
package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.UserInfo;
import com.ford.wechat.respository.user.UserInfoRepository;
import com.ford.wechat.service.user.UserInfoService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Service
public class UserInfoServiceImpl extends AbstractService implements UserInfoService {
    @Autowired
    UserInfoRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<UserInfo> pagingBy(GridPage page) {
        return repository.pagingBy (page);
    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void save(UserInfo object) {
        repository.save (object);
    }

    public void delete(List<UserInfo> objectList) {
        repository.delete (objectList);
    }

    public void delete(UserInfo object) {
        repository.delete (object);
    }

    public void delete(String id) {
        repository.delete (id);
    }


    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void update(UserInfo object) {
        repository.update (object);
    }

    public UserInfo get(String id) {
        return repository.get (id);
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param openId
     * @param userName
     * @param license
     * @param mobile
     * @param email
     * @param page     分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<UserInfo> pagingBy(String openId, String userName, String license, String mobile, String email, GridPage page) {
        return repository.pagingBy(openId, userName, license, mobile, email, page);
    }

    @Override
    public UserInfo findByOpenId(String openId) {
        List<UserInfo> userInfoList = repository.findByOpenId(openId);
        if(userInfoList!=null && userInfoList.size()>0){
            return userInfoList.get(0);
        }
        return null;
    }
}
