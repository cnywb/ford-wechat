/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * MessageContentEntityService.java
 */

package com.ford.wechat.service.pc.message;

import com.ford.wechat.entity.pc.message.MessageContentEntity;
import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：MessageContentEntityService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MessageContentEntityService extends Service {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<MessageContentEntity> pagingBy(GridPage page, String vin, String mobile, String msClass, String msgType, String pushChannel);

    MessageContentEntity get(Long id);

    void save(MessageContentEntity object, List<MessageRecordEntity> recordEntityList);

    void save(MessageContentEntity contentEntity, MessageRecordEntity recordEntity);

    void delete(Long id);

    void update(MessageContentEntity object);
}
