/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * UserInfo.java 2016-11-02 下午2:29
 */

package com.ford.wechat.service.user;


import com.ford.wechat.entity.user.UserInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface UserInfoService {

    Page<UserInfo> pagingBy(GridPage page);

    void save(UserInfo object);

    void delete(List<UserInfo> objectList);

    void delete(UserInfo object);

    void delete(String id);

    void update(UserInfo object);

    UserInfo get(String id);


    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<UserInfo> pagingBy(String openId,String userName,String license,String mobile,String email,GridPage page);

    UserInfo findByOpenId(String openId);
}
