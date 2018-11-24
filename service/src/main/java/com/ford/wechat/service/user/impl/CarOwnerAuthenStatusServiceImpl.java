package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import com.ford.wechat.respository.user.CarOwnerAuthenStatusRespository;
import com.ford.wechat.service.user.CarOwnerAuthenStatusService;
import io.dabing.common.grid.GridPage;
import io.dabing.common.util.DateUtils;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by wanglijun on 16/11/25.
 */
@Service
public class CarOwnerAuthenStatusServiceImpl extends AbstractService implements CarOwnerAuthenStatusService {

    @Autowired
    private CarOwnerAuthenStatusRespository respository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    @Override
    public Page<CarOwnerAuthenStatus> pagingBy(GridPage page) {
        return respository.pagingBy(page);
    }

    @Override
    public void save(CarOwnerAuthenStatus entity) {
        respository.save(entity);
    }

    @Override
    public void update(CarOwnerAuthenStatus entity) {
        respository.update(entity);
    }

    @Override
    public CarOwnerAuthenStatus get(String vin) {
        return respository.findByUserVin(vin);
    }

    @Override
    public CarOwnerAuthenStatus getById(Long id) {
        return respository.get(id);
    }

    @Override
    public List<CarOwnerAuthenStatus> findProcessingBy(String openId) {
        return respository.findProcessingBy(openId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void save(String channelCode, Integer channelType, String openId, String mobile, String vin) {
        CarOwnerAuthenStatus entity = new CarOwnerAuthenStatus();
        entity.setChannelCode(channelCode);
        entity.setChannelType(channelType);
        entity.setTimes(0);
        entity.setAuthResult(0l);
        entity.setAuthState(0l);
        entity.setAuthWay("");//TODO
        entity.setCreateDate(Long.parseLong(DateUtils.format(new Date(), DateUtils.FORMAT_DATE_TIME_YYYYMMDDHHMMSS)));
        entity.setOpenId(openId);
        entity.setUserMobile(mobile);
        entity.setUserVin(vin == null ? vin : vin.toUpperCase());
        respository.save(entity);
    }

    @Override
    public List<CarOwnerAuthenStatus> findByUserVinAndOpenId(String userVin, String openId) {
        return respository.findByUserVinAndOpenId(userVin, openId);
    }

    @Override
    public void delete(List<CarOwnerAuthenStatus> objectList) {
        respository.delete(objectList);
    }

    @Override
    public void delete(CarOwnerAuthenStatus object) {
       respository.delete(object);
    }


    @Override
    public Page<CarOwnerAuthenStatus> pagingBy(String userVin, String openId, String userMobile, Long authState, GridPage page) {
        return respository.pagingBy(userVin, openId, userMobile, authState, page);
    }
}
