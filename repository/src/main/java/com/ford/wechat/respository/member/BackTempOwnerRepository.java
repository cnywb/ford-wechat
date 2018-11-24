package com.ford.wechat.respository.member;

import com.ford.wechat.entity.member.BackTempOwner;
import io.dabing.core.repository.JpaRepository;

/**
 * Created by Neel on 2017/5/24.
 */
public interface BackTempOwnerRepository extends JpaRepository<BackTempOwner, Long> {

    BackTempOwner findByVin(String vin);

	BackTempOwner findByVinTopOne(String vin);
}
