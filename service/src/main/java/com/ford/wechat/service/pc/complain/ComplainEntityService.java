/*
 * Copyright (c) dabing.io
 * All rights reserved. 
 * ComplainEntityService.java
 */

package com.ford.wechat.service.pc.complain;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.Service;

import java.util.Date;
import java.util.List;

/**
 * 描述：ComplainEntityService 服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface ComplainEntityService extends Service {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @param startDate
     *@param endDate @return 分页结果数据对象集合
     */
    Page<ComplainEntity> pagingBy(GridPage page, Date startDate, Date endDate);

    ComplainEntity get(Long id);

    void save(ComplainEntity object);

    void delete(Long id);

    void update(ComplainEntity object);

    List<ComplainEntity> findBySendMail(String sendMailSending);

    void updateSendMailBySendMail(String sendMailTarget, String sendMailWhere);
}
