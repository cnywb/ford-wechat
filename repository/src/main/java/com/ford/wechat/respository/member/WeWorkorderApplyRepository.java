package com.ford.wechat.respository.member;


import com.ford.wechat.entity.member.WeWorkorderApply;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-23 18:38
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeWorkorderApplyRepository extends JpaRepository<WeWorkorderApply, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeWorkorderApply> pagingBy(GridPage page);

    List<WeWorkorderApply> findList(String openId, String vin);

    List<WeWorkorderApply> findUnbindList(String openId, String vin);

    List<WeWorkorderApply> findUnAuthList(String openId);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeWorkorderApply> pagingBy(String vin, String openId, String mobile, Integer assessStatus, GridPage page);

}
