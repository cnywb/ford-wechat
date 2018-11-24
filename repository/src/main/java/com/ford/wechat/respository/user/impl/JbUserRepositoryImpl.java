package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JbUser;
import com.ford.wechat.respository.user.JbUserRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by Neel on 2017/5/19.
 */
@Repository
public class JbUserRepositoryImpl extends DefaultJpaRepository<JbUser, Long> implements JbUserRepository {

    @Override
    public void deleteByUserId(Long userId) {
        StringQuery query = StringQuery.newQuery()
                .query("delete from JbUser a where a.id =:userId")
                .param("userId",userId);
        executeUpdate(query);
    }
}
