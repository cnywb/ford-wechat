package com.ford.wechat.vin;/**
 * Created by jovi on 25/05/2017.
 */

import org.springframework.util.StringUtils;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-05-25 20:15
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class VinUtils {

    private static final int VIN_LENGTH = 17;

    public static Boolean isVin(String s){
        if(!StringUtils.hasText(s)){
            return Boolean.FALSE;
        }

        int len = s.length();
        if (len == VIN_LENGTH) {
            return  Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
