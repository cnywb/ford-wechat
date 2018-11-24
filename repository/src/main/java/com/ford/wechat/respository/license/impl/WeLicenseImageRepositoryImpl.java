package com.ford.wechat.respository.license.impl;

import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.respository.license.WeLicenseImageRepository;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Neel on 2017/5/24.
 */
@Repository
public class WeLicenseImageRepositoryImpl extends DefaultJpaRepository<WeLicenseImage, Long> implements WeLicenseImageRepository {

    @Override
    public List<WeLicenseImage> findByVinAndOpenId(String vin, String openId) {

        StringQuery query = StringQuery.newQuery()
                .query("from WeLicenseImage where ")
                .predicateNotNull(vin)
                .query(" vin = :vin")
                .param("vin", vin)
                .query(" and openId = :openId")
                .param("openId", openId)
                .build();
        return find(query);
    }

    @Override
    public List<WeLicenseImage> findByVin(String vin) {
        StringQuery query = StringQuery.newQuery()
                .query("from WeLicenseImage where ")
                .query(" vin = :vin")
                .param("vin", vin)
                .build();
        return find(query);
    }
}
