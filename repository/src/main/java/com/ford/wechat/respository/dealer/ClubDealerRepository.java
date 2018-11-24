package com.ford.wechat.respository.dealer;


import com.ford.wechat.entity.dealer.ClubDealer;
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
 * All rights reserved. 2017-04-01 14:29
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface ClubDealerRepository extends JpaRepository<ClubDealer, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<ClubDealer> pagingBy(GridPage page);

    /**
     * 根据销售经销商查询
     *
     * @param dlsaleCode 销售经销商代码
     * @return
     */
    ClubDealer findByDSaleCode(String dlsaleCode);

    /**
     * 根据经销商服务代码查询
     *
     * @param serviceCode 经销商服务代码
     * @return
     */
    ClubDealer findByServiceCode(String serviceCode);
}
