/*
 * Copyright (c)  2015, dabing
 * All rights reserved. 
 * ImCodeList.java 15/10/13 下午4:25
 */
package com.ford.wechat.service.im.impl;


import com.ford.wechat.entity.im.ImCodeList;
import com.ford.wechat.respository.im.ImCodeListRepository;
import com.ford.wechat.service.im.ImCodeListService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：字典类服务实现
 *
 * @author ziv.hung
 * @since 1.0
 */
@Service
public class ImCodeListServiceImpl extends AbstractService implements ImCodeListService {
    @Autowired
    private ImCodeListRepository repository;

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param typeCode 字典大类代码 允许为空
     * @param page     分页对象，里面有关键字进行模糊匹配
     * @return
     */
    public Page<ImCodeList> findByGridPage(String typeCode, GridPage page) {
        return repository.findByGridPage(typeCode, page);
    }

    /**
     * 根据租户ID和字典大类代码 查询所属字典项
     *
     * @param typeCode 字典大类代码 允许为空
     * @return
     */
    @Cacheable(value = "springCacheManager", key = "#typeCode + 'findByTypeCode'")
    public List<ImCodeList> findByTypeCode(String typeCode) {
        return repository.findByTypeCode(typeCode);
    }

    @CacheEvict(value = "springCacheManager",  key = "#imCodeList.getTypeCode() + 'findByTypeCode'")
    public void save(ImCodeList imCodeList) {
        this.repository.save(imCodeList);
    }

    @CacheEvict(value = "springCacheManager",  key = "#imCodeList.getTypeCode() + 'findByTypeCode'")
    public void update(ImCodeList imCodeList) {
        this.repository.update(imCodeList);
    }

    public ImCodeList get(Long id) {
        return this.repository.get(id);
    }

    public void delete(Long id) {
        this.repository.delete(id);
    }


    /***
     * 根据字典类型,小类字典代码查询小类字典名称
     *
     * @param typeCode 类型
     * @param code     代码
     * @return 返回名称,如果为空则返回空字符串
     */
    @Cacheable(value = "springCacheManager", key = "#typeCode + #code+'getNameByTypeAndCode'")
    public String getNameByTypeAndCode(String typeCode, String code) {
        ImCodeList codeList=repository.getByTypeCodeAndCode(typeCode, code);
        if(codeList!=null){
           return codeList.getNameCn();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 根据大类代码和字典值查找字典对象
     *
     * @param typeCode
     * @param code
     * @return
     */
    @Override
    @Cacheable(value = "springCacheManager", key = "#typeCode + #code+'getByTypeCodeAndCode'")
    public ImCodeList getByTypeCodeAndCode(String typeCode, String code) {
        return repository.getByTypeCodeAndCode(typeCode, code);
    }
}