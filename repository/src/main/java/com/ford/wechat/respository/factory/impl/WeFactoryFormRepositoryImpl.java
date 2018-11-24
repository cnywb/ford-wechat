package com.ford.wechat.respository.factory.impl;

import com.ford.wechat.entity.factory.WeFactoryForm;
import com.ford.wechat.entity.user.JoUser;
import com.ford.wechat.respository.factory.WeFactoryFormRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neel on 2017/5/24.
 */
@Repository
public class WeFactoryFormRepositoryImpl extends DefaultJpaRepository<WeFactoryForm, Long> implements WeFactoryFormRepository {
    /**
     * 根据vin码查询
     * @param vin
     * @return
     */
    @Override
    public WeFactoryForm findByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeFactoryForm where 1=1 ")
                .query(" and vin = :vin")
                .param("vin", vin)
                .build();
        List<WeFactoryForm> list = find(query);
        if (!list.isEmpty()) return list.get(0);
        return null;
    }
}
