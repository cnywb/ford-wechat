package com.ford.wechat.service.members;

import com.ford.wechat.entity.member.WeAssessLog;
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
 * All rights reserved. 2017-06-02 14:56
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface WeAssessLogService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<WeAssessLog> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(WeAssessLog object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<WeAssessLog> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(WeAssessLog object);

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
    void update(WeAssessLog object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    WeAssessLog get(Long id);
}
