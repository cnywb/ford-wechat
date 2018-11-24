package com.ford.wechat.entity.user;/**
 * Created by jovi on 09/06/2017.
 */

import java.io.Serializable;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-09 10:46
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class Subscribe implements Serializable {
    private static final long serialVersionUID = 3827502555254459280L;

    private String flag;

    public String getFlag() {
        return  flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Subscribe{" +  "flag=" + flag + '}';
    }
}
