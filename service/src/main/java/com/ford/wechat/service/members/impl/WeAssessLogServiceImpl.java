package com.ford.wechat.service.members.impl;

import com.ford.wechat.entity.member.WeAssessLog;
import com.ford.wechat.respository.member.WeAssessLogRepository;
import com.ford.wechat.service.members.WeAssessLogService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class WeAssessLogServiceImpl extends AbstractService implements WeAssessLogService {
    @Autowired
    private WeAssessLogRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<WeAssessLog> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(WeAssessLog object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<WeAssessLog> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(WeAssessLog object) {
        object.setDeleted(Boolean.TRUE);
        this.update(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        WeAssessLog entity = this.repository.get(id);
        entity.setDeleted(Boolean.TRUE);
        this.update(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(WeAssessLog object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public WeAssessLog get(Long id) {
        return repository.get(id);
    }
}
