package com.ford.wechat.respository.member.impl;

import com.ford.wechat.entity.member.FordClubApply;
import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.respository.member.FordClubApplyRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Neel on 2017/5/19.
 */
@Repository
public class FordClubApplyRepositoryImpl extends DefaultJpaRepository<FordClubApply, Long> implements FordClubApplyRepository {

    @Override
    public FordClubApply findByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordClubApply where 1 = 1 ")
                .query(" and vvin = :vvin")
                .param("vvin", vin.trim().toUpperCase())
                .build();
        List<FordClubApply> list = find(query);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public String getSeq() {
        StringQuery query = StringQuery.newQuery()
                .query("select to_char(SEQ_FORD_CLUB_APPLY.nextval,'000000000000000') from dual")
                .build();
        List<Object> results = findObjectBySql(query);
        if (!results.isEmpty()) {
            return results.get(0).toString();
        }
        return null;
    }
}
