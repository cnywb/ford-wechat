package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JbUserExt;
import com.ford.wechat.respository.user.JbUserExtRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by Neel on 2017/5/22.
 */
@Repository
public class JbUserExtRepositoryImpl extends DefaultJpaRepository<JbUserExt, Long> implements JbUserExtRepository {

    @Override
    public void deleteByUserId(Long userId) {
        StringQuery query = StringQuery.newQuery()
                .query("delete from JbUserExt a where a.userId =:userId")
                .param("userId",userId);
        executeUpdate(query);
    }

}
