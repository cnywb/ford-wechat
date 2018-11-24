/*
 * Copyright (c)  2016
 * All rights reserved.
 * AppLinkInfoRepository.java 2016-10-22 下午4:03
 */
package com.ford.wechat.respository.applink;

import com.ford.wechat.entity.applink.AppLinkInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：AppLink
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
public interface AppLinkInfoRepository extends JpaRepository<AppLinkInfo, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<AppLinkInfo> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<AppLinkInfo> pagingBy(String plantForm,String appLinkName,String downloadName,GridPage page);

    /**
     * 返回所有列表信息
     * @return
     */
    List<AppLinkInfo> doFindList(String plantForm);

}
