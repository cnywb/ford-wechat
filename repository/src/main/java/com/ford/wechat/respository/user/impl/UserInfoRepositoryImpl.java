/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * UserInfoRepositoryImpl.java 2016-11-02 下午2:23
 */
package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.UserInfo;
import com.ford.wechat.respository.user.UserInfoRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Repository
public class UserInfoRepositoryImpl extends DefaultJpaRepository<UserInfo, String> implements UserInfoRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<UserInfo> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from UserInfo where 1 = 1 ")
                .predicateHasText (page.getKeyWord ())
                .query (" and name like :name")
                .likeParam ("name", page.getKeyWord ())
                .build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
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
        StringQuery query = StringQuery.newQuery ()
                .query ("from UserInfo where 1 = 1 ")

                .predicateHasText (openId)
                .query (" and openId like :openId")
                .likeParam ("openId", openId)

                .predicateHasText (userName)
                .query (" and userName like :userName")
                .likeParam ("userName", userName)

                .predicateHasText (license)
                .query (" and license like :license")
                .likeParam ("license", license)

                .predicateHasText (mobile)
                .query (" and mobile like :mobile")
                .likeParam ("mobile", mobile)

                .predicateHasText (email)
                .query (" and email like :email")
                .likeParam ("email", email)

                .build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    @Override
    public List<UserInfo> findByOpenId(String openId) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from UserInfo where openId = :openId")
                .param ("openId", openId).build();
        return find(query);
    }

}
