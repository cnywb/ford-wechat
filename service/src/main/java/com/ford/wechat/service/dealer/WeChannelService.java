package com.ford.wechat.service.dealer;

import com.ford.wechat.entity.dealer.WeChannel;
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
 * All rights reserved. 2017-03-31 20:33
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeChannelService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<WeChannel> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(WeChannel object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<WeChannel> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(WeChannel object);

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
    void update(WeChannel object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    WeChannel get(Long id);

    /**
     * 根据渠道代码查询
     *
     * @param code
     * @return
     */
    WeChannel getByCode(String code);

    /**
     * 根据经销商查询
     *
     * @param dealerCode
     * @return
     */
    WeChannel getByDealerCode(String dealerCode);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<WeChannel> pagingBy(String dealerCode, String code, String name, Integer type, GridPage page);

}
