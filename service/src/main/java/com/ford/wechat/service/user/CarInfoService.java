/*
 * Copyright (c)  2016  newtouch.com
 * All rights reserved. 
 * CarInfo.java 2016-11-02 下午2:29
 */

package com.ford.wechat.service.user;


import com.ford.wechat.entity.user.CarInfo;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
public interface CarInfoService {

    Page<CarInfo> pagingBy(GridPage page);

    void save(CarInfo object);

    void delete(List<CarInfo> objectList);

    void delete(CarInfo object);


    void update(CarInfo object);

    CarInfo get(String id);

    /**
     * 根据GridPage对象按分页查找服务
     * uyE
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CarInfo> pagingBy(String openId, String buyDealerName, String repairDealerName, String model, String style, String color, Date buyStartDate, Date buyEndDate, GridPage page);

    CarInfo findByOpenId(String openId);
}
