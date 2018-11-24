package com.ford.wechat.batch.item.processor.dms.factory;/**
 * Created by jovi on 09/06/2017.
 */

import com.ford.wechat.batch.model.dms.factory.FactoryVin;
import com.ford.wechat.entity.factory.WeFactoryForm;
import com.ford.wechat.service.factory.WeFactoryFormService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-06-09 19:01
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
public class WeFactoryFromProcessor implements ItemProcessor<FactoryVin,WeFactoryForm> {
    /**
     * 批次号
     */
    private String batchNo;

    @Autowired
    private WeFactoryFormService factoryFormService;

    @Override
    public WeFactoryForm process(FactoryVin factoryVin) throws Exception {
        //查找数据库中是否有vin码
        WeFactoryForm factoryForm = factoryFormService.findByVin(factoryVin.getVin());
        if(factoryForm!=null){
            return null;
        }

        factoryForm = new WeFactoryForm();
        factoryForm.setVin(factoryVin.getVin());
        factoryForm.setFileName(factoryVin.getFileName());
        factoryForm.setBatchNo(batchNo);

        return factoryForm;
    }
}
