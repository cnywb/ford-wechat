/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * AppLinkInfo.java 2016-10-22 下午4:05
 */

package com.ford.wechat.service.applink;


import com.ford.wechat.entity.applink.AppLinkInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
public interface AppLinkInfoService {

    Page<AppLinkInfo> pagingBy(GridPage page);

    void save(AppLinkInfo object);

    void delete(List<AppLinkInfo> objectList);

    void delete(AppLinkInfo object);

    void delete(Long id);

    void update(AppLinkInfo object);

    AppLinkInfo get(Long id);

    List<AppLinkInfo> doFindList(String plantForm);

    Page<AppLinkInfo> pagingBy(String plantForm, String appLinkName, String downloadName, GridPage page);
}
