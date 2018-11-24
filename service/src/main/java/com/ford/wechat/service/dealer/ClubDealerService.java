package com.ford.wechat.service.dealer;

import com.ford.wechat.entity.dealer.ClubDealer;
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
 * All rights reserved. 2017-04-01 14:32
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface ClubDealerService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<ClubDealer> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(ClubDealer object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<ClubDealer> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(ClubDealer object);

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
    void update(ClubDealer object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    ClubDealer get(Long id);

    /**
     * 根据销售经销商查询
     *
     * @param dlsaleCode 销售经销商代码
     * @return
     */
    ClubDealer findByDSaleCode(String dlsaleCode);

    /**
     * 根据销售经销商查询
     *
     * @param serviceCode 经销商代码
     * @return
     */
    ClubDealer findByServiceCode(String serviceCode);
}
