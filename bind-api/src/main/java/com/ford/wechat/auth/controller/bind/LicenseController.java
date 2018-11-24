package com.ford.wechat.auth.controller.bind;

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.DateUtils;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.bind.vo.OcrResp;
import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.entity.ocr.OcrValue;
import com.ford.wechat.service.bind.VehicleService;
import com.ford.wechat.service.license.LicenseImageService;
import com.ford.wechat.service.ocr.OcrVehicleService;
import com.ford.wechat.service.user.JoUserService;
import com.google.gson.Gson;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Neel on 2017/5/18.
 */
@Slf4j
@RestController
@RequestMapping(value="/i/bind/license")
public class LicenseController {

    @Autowired
    LicenseImageService licenseImageService;

    @Autowired
    OcrVehicleService ocrVehicleService;

    @Autowired
    private JoUserService joUserService;

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public Response<OcrResp> put(@RequestParam(required = true) String base64Image) {

        String openId = SessionUtils.getOpenId();

        OcrValue orcValue = ocrVehicleService.obtain(base64Image);
        OcrResp body = new OcrResp();
        if (orcValue != null && !StringUtils.isEmpty(orcValue.getVin())) {

        }

        WeLicenseImage licenseImage = licenseImageService.save(openId, orcValue.getVin(), orcValue.getOwner(), orcValue.getPlate_num(), new Gson().toJson(orcValue), orcValue.getRequest_id() + ".jpg", base64Image);

        BeanUtils.copyProperties(licenseImage, body);
        return new Response<>(body);

    }

}
