package com.ford.wechat.respository.user.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ford.wechat.entity.user.OwnerQrCodeUrl;
import com.ford.wechat.respository.user.OwnerQrCodeUrlRepository;

import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;

/**
 * Created by huangwen  on 17/07/20.
 */
@Repository
public class OwnerQrCodeUrlRepositoryImpl extends DefaultJpaRepository<OwnerQrCodeUrl, String> implements OwnerQrCodeUrlRepository {


	@Override
	public OwnerQrCodeUrl getOwnerQrCodeUrlByReferrerVin(String referrerVin) {
		 StringQuery query = StringQuery.newQuery()
	                .query("from OwnerQrCodeUrl where 1=1 ")
	                .query(" and referrerVin = :referrerVin")
	                .param("referrerVin", referrerVin).build();
	        List<OwnerQrCodeUrl> list = find(query);
	        return list.isEmpty() ? null : list.get(0);
	}

    
}
