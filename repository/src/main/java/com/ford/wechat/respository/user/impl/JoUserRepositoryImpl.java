package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.respository.user.JoUserRepository;
import io.dabing.common.util.DateUtils;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by wanglijun on 16/11/20.
 */
@Repository
public class JoUserRepositoryImpl extends DefaultJpaRepository<JoUser, Long> implements JoUserRepository {


    @Override
    public int getCountByWechatId(String openId) {
        List<JoUser> list = getList(openId);
        if (!CollectionUtils.isEmpty (list))
            return list.size();
        return 0;
    }

    @Override
    public JoUser getJoUserByWechatId(String openId) {
        List<JoUser> list = getList(openId);
        if (!CollectionUtils.isEmpty (list))
            return list.get(0);
        return null;
    }
    @Override
    public JoUser getByMobile(String mobile) {
        List<JoUser> list = findByMobile(mobile);
        if (!CollectionUtils.isEmpty (list))
            return list.get(0);
        return null;
    }

    @Override
    public List<JoUser> getList(String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("select j from JoUser j where 1 = 1 ")
                .query(" and j.wechatUserName = :wechatUserName")
                .param("wechatUserName", openId)
                .query(" order by j.id desc").build();
        return find(query);
    }


    List<JoUser> findByMobile(String mobile) {
        StringQuery query = StringQuery.newQuery()
                .query("select j from JoUser j,FordClubMember f where j.id=f.userId ")
                .query(" and j.mobilePhone = :mobilePhone")
                .param("mobilePhone", mobile)
                .query(" order by j.id desc").build();
        return find(query);
    }

    @Override
    public List<JoUser> getList(String openId, String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("select j from JoUser j,FordClubMember f where j.id=f.userId ")
                .query(" and j.wechatUserName = :wechatUserName")
                .param("wechatUserName", openId)
                .query(" and f.vvin = :vvin")
                .param("vvin", vin)
                .query(" order by j.id desc").build();
        return find(query);
    }


    @Override
    public void updateResetByUserId(Long userId, String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("update JoUser set mobilePhone = null, vin = null, current_point = null, wechat_username = null where wechat_username != :openId")
                .param("openId",openId)
                .query(" and id = :userId")
                .param("userId", userId);
        executeUpdate(query);
    }

    @Override
    public JoUser getByUserIdNotOpenId(Long userId, String openId) {
        StringQuery query = StringQuery.newQuery()
                .query("from JoUser where 1=1 ")
                .query(" and wechatUserName != :wechatUserName")
                .param("wechatUserName", openId)
                .query(" and id = :id")
                .param("id", userId)
                .query(" order by id desc").build();
        List<JoUser> list = find(query);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String getMemberNoSeq() {
        StringQuery query = StringQuery.newQuery()
                .query("select to_char(SEQ_WE_MEMBER_NO.nextval, '000000') from dual")
                .build();
        List<Object> results = findObjectBySql(query);
        if (!results.isEmpty()) {
            return results.get(0).toString().trim();
        }
        return null;
    }

}
