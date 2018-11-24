package com.ford.wechat.service.user;

import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
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
 * All rights reserved. 2017-04-01 12:10
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface FordClubMemberService {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    Page<FordClubMember> pagingBy(GridPage page);

    /**
     * 保存
     *
     * @param object
     */
    void save(FordClubMember object);

    String getSeq();

    /**
     * 批量删除
     *
     * @param objectList
     */
    void delete(List<FordClubMember> objectList);

    /**
     * 删除
     *
     * @param object
     */
    void delete(FordClubMember object);

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
    void update(FordClubMember object);

    void updateForAuthSuccess(FordClubMember clubMember, String mobile, String channelCode, Integer channelType, String openId, String source, JoUser joUser);

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    FordClubMember get(Long id);

    /**
     * 根据vin码查询
     *
     * @param vvin
     * @return
     */
    FordClubMember findByVin(String vvin);

    FordClubMember findBy(String openId, String vin);

    /**
     * 根据vin码查询（缓存）
     *
     * @param vvin
     * @return
     */
    FordClubMember findByVinCache(String vvin);

    /**
     * 根据用户编号
     *
     * @param userId
     * @return
     */
    List<FordClubMember> findByUserId(Long userId);

    /**
     * 保存缓存
     *
     * @param member
     * @return
     */
    void saveMemberCache(FordClubMember member);


    /**
     * 根据openId查找第一条vin
     *
     * @param openId
     * @return vin
     * @author Richard
     */
    String findOneVinByOpenId(String openId);

    List<FordClubMember> findVinByOpenId(String openId);

    /**
     * 根据openId查找第一条会员信息
     *
     * @param openId
     * @return FordClubMember
     * @author Richard
     */
    FordClubMember findOneMemberByOpenId(String openId);

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<FordClubMember> pagingBy(Long userId, String mobile, String vvin, Integer channelType, GridPage page);


    int countBy(String dateNo);
}
