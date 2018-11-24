package com.ford.wechat.controller.license.vo;

import lombok.Data;

/**
 * Created by zhaoliang on 2017/5/26.
 */
@Data
public class ImageUrlEntity {

    private Long id;

    /** 车牌号 */
    private String plateNo;

    /** 行驶证图片地址 */
    private String imageUrl;

}
