package com.ford.wechat.service.dealer;

import com.ford.wechat.entity.dealer.ScanDealer;
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
 * All rights reserved. 2017-02-17 11:12
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface ScanDealerService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<ScanDealer> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(ScanDealer object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<ScanDealer> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(ScanDealer object);

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
    void update(ScanDealer object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    ScanDealer get(Long id);
}
