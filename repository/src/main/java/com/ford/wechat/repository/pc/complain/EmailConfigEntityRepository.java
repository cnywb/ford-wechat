/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * EmailConfigEntityRepository.java
 */
package com.ford.wechat.repository.pc.complain;

import com.ford.wechat.entity.pc.complain.EmailConfigEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：EmailConfigEntityRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface EmailConfigEntityRepository extends JpaRepository<EmailConfigEntity, Long> {

    List<EmailConfigEntity> getByCode(String code);
}
