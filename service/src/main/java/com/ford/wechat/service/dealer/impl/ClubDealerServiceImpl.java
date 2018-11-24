package com.ford.wechat.service.dealer.impl;

import com.ford.wechat.entity.dealer.ClubDealer;
import com.ford.wechat.respository.dealer.ClubDealerRepository;
import com.ford.wechat.service.dealer.ClubDealerService;

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
 * All rights reserved. 2017-04-01 14:33
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class ClubDealerServiceImpl extends AbstractService implements ClubDealerService {
    @Autowired
    private ClubDealerRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<ClubDealer> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(ClubDealer object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<ClubDealer> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(ClubDealer object) {
       repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        ClubDealer entity = this.repository.get(id);
        repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(ClubDealer object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public ClubDealer get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据销售经销商查询
     *
     * @param dlsaleCode 销售经销商代码
     * @return
     */
    @Override
    public ClubDealer findByDSaleCode(String dlsaleCode) {
        return repository.findByDSaleCode(dlsaleCode);
    }

    /**
     * 根据销售经销商查询
     *
     * @param serviceCode 经销商代码
     * @return
     */
    @Override
    public ClubDealer findByServiceCode(String serviceCode) {
        return repository.findByServiceCode(serviceCode);
    }
}
