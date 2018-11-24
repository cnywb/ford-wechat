package com.ford.wechat.entity.ocr;

import lombok.Data;

/**
 * Created by Neel on 2017/5/18.
 */
@Data
public class OcrValue {

    private String config_str;
    private String owner;
    private String plate_num;
    private String vehicle_type;
    private String vin;
    private String engine_num;
    private String register_date;
    private String request_id;
    private boolean success;
    /** 非阿里云接口字段，用于Azure Blob存储图片路径 */
//    private String imageUrl;
}
