package com.ford.wechat.batch.model.dms.factory;/**
 * Created by jovi on 09/06/2017.
 */

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Data;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-09 18:59
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Data
@XStreamAlias("bean")
public class FactoryVin {

    @XStreamAlias("vin")
    private String vin;

    private String fileName;
}
