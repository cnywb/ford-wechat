/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * AccessToken.java 2016-10-22 下午4:47
 */

package com.ford.wechat.service.token;


import com.ford.wechat.entity.token.AccessToken;
import com.ford.wechat.entity.token.CardInfo;
import com.ford.wechat.entity.token.JsApiInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-10-22
 * @since 1.0
 */
public interface AccessTokenService {

    Page<AccessToken> pagingBy(GridPage page);

    void save(AccessToken object);

    void delete(List<AccessToken> objectList);

    void delete(AccessToken object);

    void delete(Long id);

    void update(AccessToken object);

    AccessToken get(Long id);

    String getAccessToken();

    JsApiInfo getJsticket(String url);

    CardInfo getCardTicket(String card_id);

    String getJsApiTicket();
    String getAccessTokenWeixinByAppId();
    

}
