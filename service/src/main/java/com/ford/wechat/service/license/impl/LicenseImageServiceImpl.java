package com.ford.wechat.service.license.impl;

import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.entity.ocr.OcrValue;
import com.ford.wechat.respository.license.WeLicenseImageRepository;
import com.ford.wechat.service.azure.AzureBlobService;
import com.ford.wechat.service.license.LicenseImageService;
import com.ford.wechat.service.ocr.OcrVehicleService;
import com.google.gson.Gson;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.ImageBase64Utils;
import io.dabing.core.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Neel on 2017/5/24.
 */
@Slf4j
@Service
public class LicenseImageServiceImpl extends AbstractService implements LicenseImageService {


    @Autowired
    AzureBlobService azureBlobService;

    @Autowired
    WeLicenseImageRepository weLicenseImageRepository;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public WeLicenseImage save(String openId, String vin, String owner, String plateNo, String result, String fileName, String base64Image) {

        if (StringUtils.isNotEmpty(vin)) vin = vin.toUpperCase();

        String blobPath = "chezhurenzheng/";
        String base64 = ImageBase64Utils.base64Content(base64Image);
        byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(base64);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {// 调整异常数据
                bytes[i] += 256;
            }
        }
        //行驶证上传微软Blob
        String url = azureBlobService.blobUpload(bytes, blobPath, fileName);

        WeLicenseImage t = new WeLicenseImage();
        t.setOpenId(openId);
        t.setVin(vin);
        t.setImageUrl(url);
        t.setName(owner);
        t.setPlateNo(plateNo);
        t.setResult(result);
        t.setType(1l);
        t.setCardId("");
        t.setMemberNo("");

        this.weLicenseImageRepository.save(t);

        return t;
    }



    @Override
    public List<WeLicenseImage> findByVinAndOpenId(String vin, String openId) {
        return weLicenseImageRepository.findByVinAndOpenId(vin,openId);
    }

    @Override
    public List<WeLicenseImage> findAll() {
        return weLicenseImageRepository.findAll();
    }

    @Override
    public WeLicenseImage get(Long id) {
        return weLicenseImageRepository.get(id);
    }

    @Override
    public List<WeLicenseImage> findByVin(String vin) {
        return weLicenseImageRepository.findByVin(vin);
    }


}
