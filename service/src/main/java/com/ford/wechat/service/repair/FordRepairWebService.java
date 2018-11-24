package com.ford.wechat.service.repair;

import com.ford.wechat.entity.repair.FordRepairWeb;
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
 * All rights reserved. 2017-04-01 14:27
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface FordRepairWebService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<FordRepairWeb> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(FordRepairWeb object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<FordRepairWeb> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(FordRepairWeb object);

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
    void update(FordRepairWeb object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    FordRepairWeb get(Long id);


    /**
     * 根据vin码查询
     *
     * @param vvin vin码
     * @return
     */
    List<FordRepairWeb> findByVin(String vvin);
}
