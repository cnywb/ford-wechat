/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * AppLinkInfoRepositoryImpl.java 2016-10-22 下午4:04
 */
package com.ford.wechat.respository.applink.impl;

import com.ford.wechat.entity.applink.AppLinkInfo;
import com.ford.wechat.respository.applink.AppLinkInfoRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述：AppLink
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
@Repository
public class AppLinkInfoRepositoryImpl extends DefaultJpaRepository<AppLinkInfo, Long> implements AppLinkInfoRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<AppLinkInfo> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from AppLinkInfo where 1 = 1 ")
                .predicateHasText (page.getKeyWord ())
                .query (" and name like :name")
                .likeParam ("name", page.getKeyWord ())
                .predicate (Boolean.TRUE)
                .query (" order by createdDate desc").build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param plantForm
     * @param appLinkName
     * @param downloadName
     * @param page         分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<AppLinkInfo> pagingBy(String plantForm, String appLinkName, String downloadName, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query ("from AppLinkInfo where 1 = 1 ")

                .predicateHasText (plantForm)
                .query (" and plantForm = :plantForm")
                .param ("plantForm", plantForm)

                .predicateHasText (appLinkName)
                .query (" and appLinkName like :appLinkName")
                .likeParam ("appLinkName", appLinkName)

                .predicateHasText (downloadName)
                .query (" and downloadName like :downloadName")
                .likeParam ("downloadName", downloadName)

                .predicate (Boolean.TRUE)
                .query (" order by id desc")
                .build ();
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    /**
     *
     * @return
     */
    @Override
    public List<AppLinkInfo> doFindList(String plantForm) {
        StringQuery query = StringQuery.newQuery().query("from AppLinkInfo where 1=1 ")
                .query(" and plantForm = :plantForm")
                .param("plantForm", plantForm)
                .build();
        return find(query);
    }
}
