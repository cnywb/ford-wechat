package com.ford.wechat.respository.license;

import com.ford.wechat.entity.license.WeLicenseImage;
import io.dabing.core.repository.JpaRepository;

import java.util.List;

/**
 * Created by Neel on 2017/5/24.
 */
public interface WeLicenseImageRepository extends JpaRepository<WeLicenseImage, Long> {

    List<WeLicenseImage> findByVinAndOpenId(String vin, String openId);
    List<WeLicenseImage> findByVin(String vin);
}
