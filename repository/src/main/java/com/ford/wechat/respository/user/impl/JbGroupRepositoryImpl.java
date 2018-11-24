package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JbUserGroup;
import com.ford.wechat.respository.user.JbGroupRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Neel on 2017/5/19.
 */
@Repository
public class JbGroupRepositoryImpl extends DefaultJpaRepository<JbUserGroup, Long> implements JbGroupRepository {

    @Override
    public JbUserGroup findByRegDef() {
        StringQuery query = StringQuery.newQuery()
                .query("from JbUserGroup where 1 = 1 ")
                .query(" and regDef = :regDef")
                .param("regDef", true)
                .build();
        List<JbUserGroup> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
