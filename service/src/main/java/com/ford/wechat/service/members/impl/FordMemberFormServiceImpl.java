package com.ford.wechat.service.members.impl;

import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.respository.member.FordMemberFormRepository;
import com.ford.wechat.service.members.FordMemberFormService;
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
 * All rights reserved. 2017-05-26 12:36
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Service
public class FordMemberFormServiceImpl extends AbstractService implements FordMemberFormService {
    @Autowired
    private FordMemberFormRepository repository;


    /**
     * 保存
     *
     * @param object
     */
    public void save(FordMemberForm object) {
        repository.save(object);
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<FordMemberForm> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(FordMemberForm object) {
        repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        repository.delete(id);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(FordMemberForm object) {
        repository.update(object);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public FordMemberForm get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据 姓名 电话 vin码查询
     *
     * @param mobile
     * @param vin
     * @return
     */
    @Override
    public FordMemberForm findBy( String mobile, String vin) {
        return repository.findBy( mobile, vin);
    }

    /**
     * 根据  vin码查询
     *
     * @param vin
     * @return
     */
    @Override
    public FordMemberForm findByVin(String vin) {
        return repository.findByVin(vin);
    }

    /**
     * 根据 vin码查询
     *
     * @param vin
     * @return
     */
    @Override
    public List<FordMemberForm> findListByVin(String vin) {
        return repository.findListByVin(vin);
    }
}
