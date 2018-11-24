package com.ford.wechat.respository.member;


import com.ford.wechat.entity.member.WeAssessLog;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-02 14:57
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeAssessLogRepository extends JpaRepository<WeAssessLog, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeAssessLog> pagingBy(GridPage page);

}
