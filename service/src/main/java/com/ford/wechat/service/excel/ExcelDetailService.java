/*
 * Copyright (c)  2016, dabing.io
 * All rights reserved.
 * ExcelDetail.java 2016-05-12 15:03:01
 */
package com.ford.wechat.service.excel;


import com.ford.wechat.entity.excel.ExcelDetail;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.List;

/**
 * Created by ziv.hung on 2016-05-12 15:03:01 .
 *
 * @since 1.0
 */
public interface ExcelDetailService {
    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里面有关键字进行模糊匹配
     * @return
     */
    Page<ExcelDetail> findByGridPage(GridPage page);

    /**
     * 根据模块Id获取 excel列数据
     *
     * @param excelListId 模块ID
     * @return
     */
    List<ExcelDetail> findBy(Long excelListId);

    void save(ExcelDetail object);

    /**
     * 物理删除
     */
    void delete(ExcelDetail object);

    /**
     * 物理删除
     */
    void delete(Long id);

    void update(ExcelDetail object);

    ExcelDetail get(Long id);
}