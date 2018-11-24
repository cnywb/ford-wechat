/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * MessageTemplateEntityService.java
 */

package com.ford.wechat.service.pc.message;

import com.ford.wechat.entity.pc.message.tempate.MessageTemplateEntity;
import com.ford.wechat.entity.pc.message.tempate.MessageTemplateParamsEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：MessageTemplateEntityService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MessageTemplateEntityService extends Service {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page         分页对象，里有关键字keyWord,供模糊匹配
     * @param code
     * @param name
     * @param templateType
     * @param useChannel   @return 分页结果数据对象集合
     */
    Page<MessageTemplateEntity> pagingBy(GridPage page, String code, String name, String templateType, String useChannel);

    MessageTemplateEntity get(Long id);

    MessageTemplateEntity findByTemplateCode(String templateCode);

    void save(MessageTemplateEntity object, List<MessageTemplateParamsEntity> templateParamsEntityList);

    void delete(Long id);

    void update(MessageTemplateEntity object, List<MessageTemplateParamsEntity> templateParamsEntityList);

    MessageTemplateEntity findByCodeAndIdNot(String code, Long id);

    List<MessageTemplateParamsEntity> findByTemplateId(Long templateId);
}
