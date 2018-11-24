package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JcUserExt;
import com.ford.wechat.respository.user.JcUserExtRepository;
import com.ford.wechat.respository.user.JoUserRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

/**
 * Created by Neel on 2017/5/22.
 */
@Repository
public class JcUserExtRepositoryImpl extends DefaultJpaRepository<JcUserExt, Long> implements JcUserExtRepository {

    @Override
    public void deleteByUserId(Long userId) {
        StringQuery query = StringQuery.newQuery()
                .query("delete from JcUserExt a where a.userId =:userId")
                .param("userId",userId);
        executeUpdate(query);
    }


}
