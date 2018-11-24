/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateParamsEntityRepository.java
 */
package com.ford.wechat.repository.pc.message;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：MessageTemplateParamsEntityRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MessageTemplateParamsEntityRepository extends JpaRepository<MessageTemplateParamsEntity, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<MessageTemplateParamsEntity> pagingBy(GridPage page);

    void deleteByTemplateId(Long id);

    List<MessageTemplateParamsEntity> findByTemplateId(Long id);
}
