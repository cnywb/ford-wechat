package com.ford.wechat.respository.member.impl;

import com.ford.wechat.entity.member.FordMemberForm;
import com.ford.wechat.respository.member.FordMemberFormRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Neel on 2017/5/19.
 */
@Repository
public class FordMemberFormRepositoryImpl extends DefaultJpaRepository<FordMemberForm, Long> implements FordMemberFormRepository {

    @Override
    public FordMemberForm findBy(String name, String mobile, String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordMemberForm where 1 = 1 ")
                .query(" and vformTel = :vformTel")
                .param("vformTel", mobile)
                .query(" and vvin = :vvin")
                .param("vvin", vin.toUpperCase())
                .build();
        List<FordMemberForm> list = find(query);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public FordMemberForm findBy(String mobile, String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordMemberForm where 1 = 1 ")
                .query(" and vformTel = :vformTel")
                .param("vformTel", mobile)
                .query(" and vvin = :vvin")
                .param("vvin", vin.toUpperCase())
                .build();
        List<FordMemberForm> list = find(query);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<FordMemberForm> findListByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordMemberForm where 1 = 1 ")
                .query(" and vvin = :vvin")
                .param("vvin", vin.toUpperCase())
                .build();
        return find(query);
    }

    @Override
    public FordMemberForm findByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from FordMemberForm where 1 = 1 ")
                .query(" and vvin = :vvin")
                .param("vvin", vin.toUpperCase())
                .build();
        List<FordMemberForm> list = find(query);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String getSeq() {
        StringQuery query = StringQuery.newQuery()
                .query("select to_char(SEQ_FORD_MEMBER_FORM.nextval,'FM0000000000') from dual")
                .build();
        List<Object> results = findObjectBySql(query);
        if (!results.isEmpty()) {
            return results.get(0).toString();
        }
        return null;
    }

}
