package com.ford.wechat.respository.dealer;


import com.ford.wechat.entity.dealer.WeChannel;
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
 * All rights reserved. 2017-03-31 20:31
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeChannelRepository extends JpaRepository<WeChannel, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeChannel> pagingBy(GridPage page);

    /**
     * 根据渠道代码查询
     *
     * @param code
     * @return
     */
    public WeChannel getByCode(String code);

    /**
     * 根据经销商代码查询
     *
     * @param dealerCode
     * @return
     */
    public WeChannel getByDealerCode(String dealerCode);

    /**
     * 根据经销商服务代码查询
     *
     * @param dealerServiceCode
     * @return
     */
    public WeChannel getByDealerServiceCode(String dealerServiceCode);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeChannel> pagingBy(String dealerCode, String code, String name, Integer type, GridPage page);

}
