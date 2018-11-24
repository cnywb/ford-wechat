package com.ford.wechat.service.license;

import com.ford.wechat.entity.license.WeLicenseImage;

import java.util.List;

/**
 * Created by Neel on 2017/5/24.
 */
public interface LicenseImageService {

    WeLicenseImage save(String openId, String vin, String owner, String plateNo, String result, String requestId, String base64Image);

    List<WeLicenseImage> findByVinAndOpenId(String vin,String openId);
    List<WeLicenseImage> findAll();

    WeLicenseImage get(Long id);

    List<WeLicenseImage> findByVin(String vin);

}
