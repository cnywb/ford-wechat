package com.ford.wechat.respository.user;


import com.ford.wechat.entity.user.FordClubMember;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.JpaRepository;
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
 * All rights reserved. 2017-04-01 12:11
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public interface FordClubMemberRepository extends JpaRepository<FordClubMember, Long> {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<FordClubMember> pagingBy(GridPage page);

    /**
     * 根据vin码查询
     *
     * @param vvin vin码
     * @return 分页结果数据对象集合
     */
    FordClubMember findByVin(String vvin);

    FordClubMember findBy(String openId, String vvin);

    List<FordClubMember> findListByVin(String vvin);

    /**
     * 根据用户编号查询
     *
     * @param userId 用户编号
     * @return 分页结果数据对象集合
     */
    List<FordClubMember> findByUserId(Long userId);

    List<FordClubMember> findListByOpenId(String openId);

    String getSeq();

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    Page<FordClubMember> pagingBy(Long userId, String mobile, String vvin, Integer channelType, GridPage page);

    public FordClubMember getFordClubMemberbyParam(String name, String mobile, String vvin);

    int countBy(String dateNo);
}
