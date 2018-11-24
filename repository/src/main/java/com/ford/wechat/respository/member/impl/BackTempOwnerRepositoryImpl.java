//package com.ford.wechat.respository.member.impl;
//
//import com.ford.wechat.entity.member.BackTempOwner;
//import com.ford.wechat.entity.member.FordClubApply;
//import com.ford.wechat.respository.member.BackTempOwnerRepository;
//import io.dabing.core.repository.DefaultJpaRepository;
//import io.dabing.core.repository.domain.StringQuery;
//import org.springframework.stereotype.Repository;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
///**
// * Created by Neel on 2017/5/24.
// */
//@Repository
//public class BackTempOwnerRepositoryImpl extends DefaultJpaRepository<BackTempOwner, Long> implements BackTempOwnerRepository {
//
//    @Override
//    public BackTempOwner findByVin(String vin) {
//        StringQuery query = StringQuery.newQuery()
//                .query("select a.vin as vin, a.owner_name as ownerName, replace(a.mobile,' ','') as mobile from crm.back_temp_owner a where 1 = 1")
//                .query(" and a.mobile != null")
//                .query(" and a.vin = :vin")
//                .param("vin", vin.trim().toUpperCase())
//                .query(" group by a.vin,a.owner_name,replace(a.mobile,' ','')")
//                .build();
//        List<BackTempOwner> list = findBySql(query, BackTempOwner.class);
//        if (!CollectionUtils.isEmpty(list)) {
//            return list.get(0);
//        }
//        return null;
//    }
//}


package com.ford.wechat.respository.member.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ford.wechat.entity.member.BackTempOwner;
import com.ford.wechat.respository.member.BackTempOwnerRepository;

import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;

/**
 * Created by Huangwen on 2017/9/28.
 */
@Repository
public class BackTempOwnerRepositoryImpl extends DefaultJpaRepository<BackTempOwner, Long> implements BackTempOwnerRepository {

    @Override
    public BackTempOwner findByVin(String vin) {

        if (vin!=null){
            vin = vin.trim().toUpperCase();
            System.out.println("--vin.trim().toUpperCase =" + vin);
        }

        StringQuery query = StringQuery.newQuery()
//				.query("select OWNER_ID,VIN,OWNER_NAME,MOBILE from BackTempOwner a where ")
                .query("from BackTempOwner where vin = :vin")
                .param("vin", vin).build();

//                .query("select a.vin as vin, a.owner_name as ownerName, replace(a.mobile,' ','') as mobile from crm.back_temp_owner a where 1 = 1 ")
//                .query(" and a.mobile Is Not Null ")
//                .query(" and a.vin = :vin")
//                .inParam("vin", vin)
//                .query(" group by a.vin,a.owner_name,replace(a.mobile,' ','')")
//                .build();
        System.out.println("--findByVin StringQuery.getQuery =" + query.getQuery());

        List<BackTempOwner> list = find(query);
        System.out.println("-List<BackTempOwner> list.size =" + list.size());
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public BackTempOwner findByVinTopOne(String vin) {
        try{
            StringQuery query = StringQuery.newQuery()
                    .query("from BackTempOwner where 1 = 1 ")
                    .query(" and vin = :vin")
                    .inParam("vin",vin)
//	            .query(" and mobile is not null")
                    .build();
            List<BackTempOwner> list = find(query);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
