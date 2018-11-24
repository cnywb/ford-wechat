package com.ford.wechat.respository.member;

import com.ford.wechat.entity.member.FordClubApply;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/19.
 */
public interface FordClubApplyRepository extends JpaRepository<FordClubApply, Long> {

    FordClubApply findByVin(String vin);

    String getSeq();
}
