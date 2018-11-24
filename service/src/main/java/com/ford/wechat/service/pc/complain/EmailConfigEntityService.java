/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * EmailConfigEntityService.java
 */

package com.ford.wechat.service.pc.complain;

import com.ford.wechat.entity.pc.complain.EmailConfigEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：EmailConfigEntityService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface EmailConfigEntityService extends Service {

    EmailConfigEntity getByCode(String code);

    void save(EmailConfigEntity object);

    void update(EmailConfigEntity object);
}
