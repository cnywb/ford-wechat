/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageTemplateEntityRepository.java
 */
package com.ford.wechat.repository.pc.message;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;


/**
 * 描述：MessageTemplateEntityRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MessageTemplateEntityRepository extends JpaRepository<MessageTemplateEntity, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @param code
     *@param name
     * @param templateType
     * @param useChannel @return 分页结果数据对象集合
     */
    Page<MessageTemplateEntity> pagingBy(GridPage page, String code, String name, String templateType, String useChannel);

    MessageTemplateEntity findByCodeAndIdNot(String code, Long id);

    MessageTemplateEntity findByTemplateCode(String templateCode);
}
