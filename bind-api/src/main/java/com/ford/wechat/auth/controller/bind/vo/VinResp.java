package com.ford.wechat.auth.controller.bind.vo;/**
 * Created by jovi on 05/05/2017.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-05 13:16
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VinResp {

//    public static int SUCCESS_AUTH_CODE = 200;
//    public static String SUCCESS_AUTH_MESSAGE = "已认证";
//
//    public static int LACK_OPENID_CODE = 201;
//    public static String LACK_OPENID_MESSAGE = "OPENID不存在";
//
//    public static int LACK_VIN_CODE = 202;
//    public static String LACK_VIN_MESSAGE = "对应VIN码不存在";

    private String vin;

    private String brand;

    private String type;

    private String model;
}
