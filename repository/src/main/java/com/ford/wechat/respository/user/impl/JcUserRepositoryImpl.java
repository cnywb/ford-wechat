package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JcUser;
import com.ford.wechat.respository.user.JcUserRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by Neel on 2017/5/19.
 */
@Repository
public class JcUserRepositoryImpl extends DefaultJpaRepository<JcUser, Long> implements JcUserRepository {

    @Override
    public void deleteByUserId(Long userId) {
        StringQuery query = StringQuery.newQuery()
                .query("delete from JcUser a where a.userId =:userId")
                .param("userId",userId);
        executeUpdate(query);
    }


    @Override
    public void updateResetByUserId(Long userId) {
        StringQuery query = StringQuery.newQuery()
                .query("update JcUser set groupId = 1 where userId =:userId")
                .param("userId", userId);
        executeUpdate(query);
    }
}
