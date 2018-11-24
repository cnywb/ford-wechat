/*
 * Copyright (c)  2016, newtouch.com
 * All rights reserved.
 * CarInfoServiceImpl.java 2016-11-02 下午2:31
 */
package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.CarInfo;
import com.ford.wechat.respository.user.CarInfoRepository;
import com.ford.wechat.service.user.CarInfoService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 描述：TODO
 *
 * @author wanglijun create on 2016-11-02
 * @since 1.0
 */
@Service
public class CarInfoServiceImpl extends AbstractService implements CarInfoService {
    @Autowired
    CarInfoRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<CarInfo> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void save(CarInfo object) {
        repository.save(object);
    }

    public void delete(List<CarInfo> objectList) {
        repository.delete(objectList);
    }

    public void delete(CarInfo object) {
        repository.delete(object);
    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void update(CarInfo object) {
        repository.update(object);
    }

    public CarInfo get(String id) {
        return repository.get(id);
    }

    /**
     * 根据GridPage对象按分页查找服务
     * uyE
     *
     * @param openId
     * @param buyDealerName
     * @param repairDealerName
     * @param model
     * @param style
     * @param color
     * @param buyStartDate
     * @param buyEndDate
     * @param page             分页对象，里有关键字keyWord,供模糊匹配  @return 分页结果数据对象集合
     */
    @Override
    public Page<CarInfo> pagingBy(String openId, String buyDealerName, String repairDealerName, String model, String style, String color, Date buyStartDate, Date buyEndDate, GridPage page) {
        return repository.pagingBy(openId, buyDealerName, repairDealerName, model, style, color, buyStartDate, buyEndDate, page);
    }

    @Override
    public CarInfo findByOpenId(String openId) {
        List<CarInfo> carInfoList = repository.findByOpenId(openId);
        if (carInfoList != null && carInfoList.size() > 0) {
            return carInfoList.get(0);
        }
        return null;
    }
}
