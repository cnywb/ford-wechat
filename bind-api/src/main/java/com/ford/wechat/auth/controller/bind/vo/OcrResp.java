package com.ford.wechat.auth.controller.bind.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;

/**
 * Created by Neel on 2017/5/18.
 */
@Data
public class OcrResp {

    private Long id;

    /** vin码 */
    private String vin;

    /** 行驶证识别车主姓名 */
    private String name;

    /** 车牌号 */
    private String plateNo;

    /** 扫码结果(json) */
//    private String result;

    /** 行驶证图片地址 */
    private String imageUrl;

    /** 上传类型(1.认证   2.解绑) */
    private Long type;

    /** 该是否已被认证过 */
    private boolean isAuthed = false;

}
