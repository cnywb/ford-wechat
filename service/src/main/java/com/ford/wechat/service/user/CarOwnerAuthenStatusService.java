package com.ford.wechat.service.user;

import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * Created by wanglijun on 16/11/25.
 */
public interface CarOwnerAuthenStatusService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<CarOwnerAuthenStatus> pagingBy(GridPage page);

    void save(CarOwnerAuthenStatus entity);

    void update(CarOwnerAuthenStatus entity);

    CarOwnerAuthenStatus get(String vin);

    CarOwnerAuthenStatus getById(Long id);

    List<CarOwnerAuthenStatus> findProcessingBy(String openId);

    void save(String channelCode, Integer channelType, String openId, String mobile, String vin);
   /* void save(String openId, String mobile, String vin);*/

    List<CarOwnerAuthenStatus> findByUserVinAndOpenId(String userVin,String openId);

    void delete(List<CarOwnerAuthenStatus> objectList);

    void delete(CarOwnerAuthenStatus object);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CarOwnerAuthenStatus> pagingBy(String userVin, String openId, String userMobile, Long authState, GridPage page);
}
