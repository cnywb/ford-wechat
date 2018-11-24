package com.ford.wechat.respository.user.impl;

import com.ford.wechat.date.DateUtils;
import com.ford.wechat.entity.user.FordClubMember;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.respository.user.FordClubMemberRepository;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
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
@Repository
public class FordClubMemberRepositoryImpl extends DefaultJpaRepository<FordClubMember, Long> implements FordClubMemberRepository {

    /**
     * 根据GridPage对象按分页查找服务
     *
     * @param page 分页对象，里有关键字keyWord,供模糊匹配
     * @return 分页结果数据对象集合
     */
    public Page<FordClubMember> pagingBy(GridPage page) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordClubMember where 1 = 1 ")
                .predicateNotNull(page.getKeyWord())
                .query(" and name like :name")
                .likeParam("name", page.getKeyWord())
                .predicate(Boolean.TRUE)
                .query(" order by firstInsert desc").build();
        return find(query, PageRequest.newPage(page.getPageNumber(), page.getPageSize()));
    }

    /**
     * 根据vin码查询
     *
     * @param vvin vin码
     * @return 分页结果数据对象集合
     */
    @Override
    public FordClubMember findByVin(String vvin) {

        StringQuery query = StringQuery.newQuery()
                .query("from FordClubMember where 1 = 1 ")
                .predicateNotNull(vvin)
                .query(" and vvin = :vvin")
                .param("vvin", vvin)
                .build();
        List<FordClubMember> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }


    @Override
    public FordClubMember findBy(String openId, String vvin) {

        StringQuery query = StringQuery.newQuery()
                .query("from FordClubMember where 1 = 1 ")
                .predicateNotNull(vvin)
                .query(" and vvin = :vvin")
                .param("vvin", vvin)
                .predicateNotNull(openId)
                .query(" and openId = :openId")
                .param("openId", openId)
                .build();
        List<FordClubMember> list = find(query);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    /**
     * 根据vin码查询
     *
     * @param vvin vin码
     * @return 分页结果数据对象集合
     */
    @Override
    public List<FordClubMember> findListByVin(String vvin) {

        StringQuery query = StringQuery.newQuery()
                .query("from FordClubMember where 1 = 1 ")
                .predicateNotNull(vvin)
                .query(" and vvin = :vvin")
                .param("vvin", vvin)
                .build();
        return find(query);
    }

    /**
     * 根据用户编号查询
     *
     * @param userId 用户编号
     * @return 分页结果数据对象集合
     */
    @Override
    public List<FordClubMember> findByUserId(Long userId) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordClubMember where 1 = 1 ")
                .predicateNotNull(userId)
                .query(" and userId = :userId")
                .param("userId", userId)
                .build();
        return find(query);
    }



    @Override
    public List<FordClubMember> findListByOpenId(String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("select j from FordClubMember f, JoUser j where j.id = f.userId ")
                .query(" and j.wechatUserName = :wechatUserName")
                .param("wechatUserName", openId)
                .query(" order by j.id desc").build();
        return find(query);
    }

    @Override
    public String getSeq() {
        StringQuery query = StringQuery.newQuery()
                .query("select to_char(SEQ_FORD_CLUB_MEMBER.nextval,'000000000000000') from dual")
                .build();
        List<Object> results = findObjectBySql(query);
        if (!results.isEmpty()) {
            return results.get(0).toString();
        }
        return null;
    }

    @Override
    public Page<FordClubMember> pagingBy(Long userId, String mobile, String vvin, Integer channelType, GridPage page) {
        StringQuery query = StringQuery.newQuery ()
                .query("from FordClubMember where 1 = 1 ")

                .predicateNotNull (userId)
                .query (" and userId = :userId")
                .param ("userId", userId)

                .predicateHasText (mobile)
                .query (" and mobile like :mobile")
                .likeParam ("mobile", mobile)

                .predicateHasText (vvin)
                .query (" and vvin like :vvin")
                .likeParam ("vvin", vvin)

                .predicateNotNull (channelType)
                .query (" and channelType = :channelType")
                .param ("channelType", channelType)

                .predicate (Boolean.TRUE).build();
              /*  .query (" order by createdDate desc").build ();*/
        return find (query, PageRequest.newPage (page.getPageNumber (), page.getPageSize ()));
    }

    @Override
    public FordClubMember getFordClubMemberbyParam(String name, String mobile, String vvin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordClubMember where name  = :name and  mobile = :mobile and vvin  = :vvin ")
                .param("name", name)
                .param("mobile", mobile)
                .param("vvin", vvin)
                .build();
        List<FordClubMember> results = find(query);
        if (!results.isEmpty()) {
            return (FordClubMember) results.get(0);
        }
        return null ;
    }





    @Override
    public int countBy(String dateNo) {
        StringQuery query = StringQuery.newQuery()
                .query("select count(DISTINCT a.userId) as count from FordClubMember a where 1 = 1 ")
                .predicateNotNull(dateNo)
                .query(" and to_char(a.dcrtDate,'yyyymmdd') = :dateNo")
                .param("dateNo", dateNo)
                .build();
        List<Object> list = findObject(query);
        if (list.isEmpty()) return 0;

        return Integer.parseInt(list.get(0).toString());
    }

}
