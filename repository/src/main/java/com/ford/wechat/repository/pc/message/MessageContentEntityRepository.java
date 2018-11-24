/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MessageContentRepository.java
 */
package com.ford.wechat.repository.pc.message;

import com.ford.wechat.entity.pc.message.MessageContentEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;


/**
 * 描述：MessageContentRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface MessageContentEntityRepository extends JpaRepository<MessageContentEntity, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<MessageContentEntity> pagingBy(GridPage page, String vin, String mobile, String msClass, String msgType, String pushChannel);
}
