/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * MessageRecordService.java
 */

package com.ford.wechat.service.pc.message;

import com.ford.wechat.entity.pc.message.MessageRecordEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.List;

/**
 * 描述：MessageRecordService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MessageRecordEntityService extends Service {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<MessageRecordEntity> pagingBy(GridPage page, String openId, String vin, String msgClass, String msgType, String pushChannel);

    Page<MessageRecordEntity> pagingAdminBy(GridPage page, String vin, String msgClass, String msgType, String pushChannel);

    MessageRecordEntity get(Long id);

    List<MessageRecordEntity> findByValidRecord();

    void save(MessageRecordEntity object);

    void delete(Long id);

    void update(MessageRecordEntity object);
}
