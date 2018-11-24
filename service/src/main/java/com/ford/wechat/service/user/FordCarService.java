package com.ford.wechat.service.user;

import com.ford.wechat.entity.user.FordCar;
import io.dabing.common.grid.GridPage;
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
 * All rights reserved. 2017-04-01 14:20
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface FordCarService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<FordCar> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(FordCar object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<FordCar> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(FordCar object);

    /**
     * 根据主键删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 更新
     *
     * @param object
     */
    void update(FordCar object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    FordCar get(Long id);

    /**
     * 查找vin码对应的维修或销售经销商
     * @param vin
     * @param i
     * @return
     */
    public FordCar findByVin(String vin, int i);
}
