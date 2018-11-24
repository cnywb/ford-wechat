package com.ford.wechat.service.ocr;

import com.ford.wechat.entity.ocr.OcrValue;

/**
 * Created by Neel on 2017/5/17.
 */
public interface OcrVehicleService {

    /**
     * 阿里行驶证识别
     * @param base64Image
     * @return
     */
    OcrValue obtain(String base64Image);
}
