package com.ford.wechat.respository.user;

import com.ford.wechat.entity.user.OwnerQrCodeUrl;

import io.dabing.core.repository.JpaRepository;

/**
 * Created by huangwen  on 17/07/20.
 */
public interface OwnerQrCodeUrlRepository extends JpaRepository<OwnerQrCodeUrl, String> {
   
    /**
     * 根据referrerVin查询已生成的二维码url
     *
     * @param openId
     * @return
     */
    OwnerQrCodeUrl getOwnerQrCodeUrlByReferrerVin(String referrerVin);

}
