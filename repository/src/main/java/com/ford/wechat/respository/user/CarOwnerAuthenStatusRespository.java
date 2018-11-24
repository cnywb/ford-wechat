package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * Created by wanglijun on 16/11/25.
 */
public interface CarOwnerAuthenStatusRespository extends JpaRepository<CarOwnerAuthenStatus, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CarOwnerAuthenStatus> pagingBy(GridPage page);


    List<CarOwnerAuthenStatus> findProcessingBy(String openId);

    List<CarOwnerAuthenStatus> findByUserVinAndOpenId(String userVin,String openId);

    CarOwnerAuthenStatus findByUserVin(String userVin);
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<CarOwnerAuthenStatus> pagingBy(String userVin, String openId, String userMobile, Long authState, GridPage page);

}
