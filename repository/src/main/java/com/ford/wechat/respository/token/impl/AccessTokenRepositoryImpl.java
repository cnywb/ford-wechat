/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * AccessTokenRepositoryImpl.java 2016-10-22 下午4:47
 */
package com.ford.wechat.respository.token.impl;

import com.ford.wechat.entity.token.AccessToken;
import com.ford.wechat.respository.token.AccessTokenRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
@Repository
public class AccessTokenRepositoryImpl extends DefaultJpaRepository<AccessToken, Long> implements AccessTokenRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AccessToken> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from AccessToken where 1 = 1 ")
                .predicateHasText (page.getKeyWord ())
                .query (" and name like :name")
                .likeParam ("name", page.getKeyWord ())
                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }
}
