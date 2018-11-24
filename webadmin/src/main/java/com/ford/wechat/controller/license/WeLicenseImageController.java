package com.ford.wechat.controller.license;

import com.ford.wechat.controller.license.vo.ImageUrlEntity;
import com.ford.wechat.controller.license.vo.WeLicenseImageHandleReq;
import com.ford.wechat.entity.license.WeLicenseImage;
import com.ford.wechat.service.license.LicenseImageService;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoliang on 2017/5/26.
 */
@Controller
public class WeLicenseImageController {
    @Autowired
    private LicenseImageService service;

    //获取行驶证图片
    @ApiService(transCode = "imageUrlEntityGet")
    public List<ImageUrlEntity> imageUrlEntityGet(WeLicenseImageHandleReq req) {

        List<ImageUrlEntity> body=new ArrayList<>();
        List<WeLicenseImage> data = service.findByVinAndOpenId(req.getUserVin(),req.getOpenId());
        for(WeLicenseImage item: data){
            ImageUrlEntity imageUrlEntity=new ImageUrlEntity();
            BeanUtils.copyProperties(item, imageUrlEntity);
            body.add(imageUrlEntity);
        }
        return body;
    }
}
