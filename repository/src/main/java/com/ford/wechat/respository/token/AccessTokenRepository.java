/*
 * Copyright (c)  2016
 * All rights reserved.
 * AccessTokenRepository.java 2016-10-22 下午4:45
 */
package com.ford.wechat.respository.token;

import com.ford.wechat.entity.token.AccessToken;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<AccessToken> pagingBy(GridPage page);

}
