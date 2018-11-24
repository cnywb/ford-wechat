package com.ford.wechat.respository.user;


import com.ford.wechat.entity.user.FordCar;
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
 * All rights reserved. 2017-04-01 14:18
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface FordCarRepository extends JpaRepository<FordCar, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<FordCar> pagingBy(GridPage page);

    /**
     * 查找vin码对应的维修或销售经销商
     * @param vin
     * @param i
     * @return
     */
    public FordCar findByVin(String vin, int i);

    /**
     * 查找vin码对应的维修或销售经销商
     * @param vin
     * @return
     */
    public FordCar findFordCarByVin(String vin) ;
}
