/*
 * Copyright (c)  2016
 * All rights reserved.
 * UserInfoRepository.java 2016-11-02 下午2:20
 */
package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.UserInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<UserInfo> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<UserInfo> pagingBy(String openId,String userName,String license,String mobile,String email,GridPage page);

    List<UserInfo> findByOpenId(String openId);
}
