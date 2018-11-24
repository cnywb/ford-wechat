/*
 * Copyright (c)  2016
 * All rights reserved.
 * CarInfoRepository.java 2016-11-02 下午2:19
 */
package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.CarInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;


/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface CarInfoRepository extends JpaRepository<CarInfo, String> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CarInfo> pagingBy(GridPage page);


    /**
     * 根据GridPage对象按分页查找服务
     * uyE
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CarInfo> pagingBy(String openId, String buyDealerName, String repairDealerName, String model, String style, String color, Date buyStartDate, Date buyEndDate, GridPage page);


    List<CarInfo> findByOpenId(String openId);
}
