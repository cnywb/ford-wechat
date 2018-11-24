package com.ford.wechat.service.auth;

import com.ford.wechat.entity.auth.AuthToDms;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-19 16:06
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface AuthToDmsService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<AuthToDms> pagingBy(GridPage page);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<AuthToDms> pagingBy(String batchNo, String vin, String openId, Integer channelType, Integer sendDmsStatus, Integer verify, Date createStartDate, Date createEndDate, GridPage page);



    /**
     * 保存
     *
     * @param object
     */
    void save(AuthToDms object);

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<AuthToDms> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(AuthToDms object);

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
    void update(AuthToDms object);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    AuthToDms get(Long id);

    String sendAuthToDms(List<Map<String, String>> contentList);

    /**
     * 获取经销商服务代码
     * @param authToDms
     * @return
     */
    public AuthToDms getAuthToDms(AuthToDms authToDms);

    /**
     * 获取经销商服务代码
     * @param vin
     * @return
     */
    public String getAuthToDms(String vin);

    /**
     *
     * @param openId
     * @return
     */
    public int subscribe(String openId);
}
