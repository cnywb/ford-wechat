/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainEntityRepository.java
 */
package com.ford.wechat.repository.pc.complain;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;


/**
 * 描述：ComplainEntityRepository 仓储服务接口层代码类
 *
 * @author ziv
 * @since 1.0
 */
public interface ComplainEntityRepository extends JpaRepository<ComplainEntity, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @param startDate
     *@param endDate @return 分页结果数据对象集合
     */
    Page<ComplainEntity> pagingBy(GridPage page, Date startDate, Date endDate);

    List<ComplainEntity> findByOpenId(String openId);

    List<Object[]> findForExcel(Date startDate, Date endDate);

    List<ComplainEntity> findBySendMail(String sendMail);

    void updateSendMailBySendMail(String sendMailTarget, String sendMailWhere);
}
