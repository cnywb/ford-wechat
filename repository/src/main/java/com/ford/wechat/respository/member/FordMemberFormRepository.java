package com.ford.wechat.respository.member;

import com.ford.wechat.entity.member.FordMemberForm;
import io.dabing.core.repository.JpaRepository;

import java.util.List;

/**
 * Created by Neel on 2017/5/19.
 */
public interface FordMemberFormRepository extends JpaRepository<FordMemberForm, Long> {

    FordMemberForm findBy(String name, String mobile, String vin);

    List<FordMemberForm> findListByVin(String vin);

    FordMemberForm findByVin(String vin);

    String getSeq();

    FordMemberForm findBy(String mobile, String vin);

}
