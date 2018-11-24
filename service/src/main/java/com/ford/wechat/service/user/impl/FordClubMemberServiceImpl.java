package com.ford.wechat.service.user.impl;

import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.entity.user.JobStatus;
import com.ford.wechat.respository.user.FordClubMemberRepository;
import com.ford.wechat.respository.user.JoUserRepository;
import com.ford.wechat.service.user.FordClubMemberService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.service.AbstractService;
import io.dabing.redis.client.BinaryRedisClient;
import io.dabing.redis.util.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
@Service
public class FordClubMemberServiceImpl extends AbstractService implements FordClubMemberService {
    @Autowired
    private FordClubMemberRepository repository;
    @Autowired
    private BinaryRedisClient redisClient;

    @Autowired
    private JoUserRepository jobUserRepository;

    private final String FORD_CLUB_MEMBER_VVIN = "FORD:CLUB:MEMBER:VVIN:{0}";

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FordClubMember> pagingBy(GridPage page) {
        return repository.pagingBy(page);
    }

    /**
     * 保存
     *
     * @param object
     */
    public void save(FordClubMember object) {
        object.setJobStatus(JobStatus.NOT_TO_JOB);
        repository.save(object);
    }

    @Override
    public String getSeq() {
        return repository.getSeq();
    }

    /**
     * 批量删除
     *
     * @param objectList
     */
    public void delete(List<FordClubMember> objectList) {
        repository.delete(objectList);
    }

    /**
     * 删除
     *
     * @param object
     */
    public void delete(FordClubMember object) {
        repository.delete(object);
    }

    /**
     * 根据主键删除
     *
     * @param id
     */
    public void delete(Long id) {
        FordClubMember entity = this.repository.get(id);
        repository.delete(entity);
    }

    /**
     * 更新
     *
     * @param object
     */
    public void update(FordClubMember object) {
        repository.update(object);
    }

    /**
     * 车主认证审核
     *
     * @param clubMember
     * @param mobile
     * @param channelCode
     * @param openId
     * @param joUser
     */
    @Override
    public void updateForAuthSuccess(FordClubMember clubMember, String mobile, String channelCode, Integer channelType, String openId, String source, JoUser joUser) {
        clubMember.setMobile(mobile);
        clubMember.setChannelCode(channelCode);
        clubMember.setChannelType(channelType);
        clubMember.setJobStatus(0);
        clubMember.setOpenId(openId);
        clubMember.setSource(source);
        clubMember.setMemberNo(joUser.getId().toString());

        this.update(clubMember);
    }

    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    public FordClubMember get(Long id) {
        return repository.get(id);
    }

    /**
     * 根据vin码查询
     *
     * @param vvin
     * @return
     */
    @Override
    public FordClubMember findByVin(String vvin) {
        return repository.findByVin(vvin);
    }

    @Override
    public FordClubMember findBy(String openId, String vin) {
        return repository.findBy(openId, vin);
    }

    /**
     * 根据vin码查询（缓存）
     *
     * @param vvin
     * @return
     */
    @Override
    public FordClubMember findByVinCache(String vvin) {
        Assert.hasText(vvin);
        String key= RedisKeyUtils.build (FORD_CLUB_MEMBER_VVIN,vvin);
        FordClubMember member = redisClient.get(key,FordClubMember.class);
        return member;
    }


    /**
     * 根据用户编号
     *
     * @param userId
     * @return
     */
    @Override
    public List<FordClubMember> findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }


    /**
     * 根据openId查找第一条vin
     *
     * @param openId
     * @return vin
     * @author Richard
     */
    @Override
    public String findOneVinByOpenId(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new BusinessException("OPENID不存在");
        }

        JoUser user = jobUserRepository.getJoUserByWechatId(openId);
        if (user == null) return null;

        //查询对应vin码
        List<FordClubMember> list = this.findByUserId(user.getId());
        if (list.isEmpty()) return null;

        return list.get(0).getVvin();
    }

    @Override
    public List<FordClubMember> findVinByOpenId(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new BusinessException("OPENID不存在");
        }

        JoUser user = jobUserRepository.getJoUserByWechatId(openId);
        if (user == null) return null;

        //查询对应vin码
        return this.findByUserId(user.getId());
    }


    /**
     * 根据openId查找第一条会员信息
     *
     * @param openId
     * @return FordClubMember
     * @author Richard
     */
    @Override
    public FordClubMember findOneMemberByOpenId(String openId) {

        if (StringUtils.isEmpty(openId)) {
            throw new BusinessException("OPENID不存在");
        }

        JoUser user = jobUserRepository.getJoUserByWechatId(openId);
        if (user == null) return null;

        //查询对应vin码
        List<FordClubMember> list = this.findByUserId(user.getId());

        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Page<FordClubMember> pagingBy(Long userId, String mobile, String vvin, Integer channelType, GridPage page) {
        return repository.pagingBy(userId, mobile, vvin, channelType, page);
    }

    /**
     * 保存缓存
     *
     * @param member
     * @return
     */
    @Override
    public void saveMemberCache(FordClubMember member) {
        if(member==null){
            return;
        }
        String key= RedisKeyUtils.build (FORD_CLUB_MEMBER_VVIN,member.getVvin());
        redisClient.set(key,member);
    }



    @Override
    public int countBy(String dateNo) {
        return this.repository.countBy(dateNo);
    }
}
