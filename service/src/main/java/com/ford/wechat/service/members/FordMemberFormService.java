package com.ford.wechat.service.members;

import com.ford.wechat.entity.member.FordMemberForm;

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
public interface FordMemberFormService {


    /**
     * 保存
     *
     * @param object
     */
    void save(FordMemberForm object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<FordMemberForm> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(FordMemberForm object);

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
    void update(FordMemberForm object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    FordMemberForm get(Long id);

    /**
     * 根据 电话 vin码查询
     * @param mobile
     * @param vin
     * @return
     */
    FordMemberForm findBy(String mobile, String vin);

    /**
     * 根据  vin码查询
     *
     * @param vin
     * @return
     */
    public FordMemberForm findByVin(String vin);

    /**
     * 根据 vin码查询
     * @param vin
     * @return
     */
    List<FordMemberForm> findListByVin(String vin);
}
