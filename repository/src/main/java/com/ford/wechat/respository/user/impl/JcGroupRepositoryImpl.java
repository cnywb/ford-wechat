package com.ford.wechat.respository.user.impl;

import com.ford.wechat.entity.user.JcGroup;
import com.ford.wechat.respository.user.JcGroupRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Neel on 2017/5/19.
 */
@Repository
public class JcGroupRepositoryImpl extends DefaultJpaRepository<JcGroup, Long> implements JcGroupRepository {

    @Override
    public JcGroup findByRegDef() {
        StringQuery query = StringQuery.newQuery()
                .query("from JcGroup where 1 = 1 ")
                .query(" and regDef = :regDef")
                .param("regDef", true)
                .build();
        List<JcGroup> list = find(query);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }
}
