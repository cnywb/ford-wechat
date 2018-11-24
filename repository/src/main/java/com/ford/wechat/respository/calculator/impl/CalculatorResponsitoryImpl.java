package com.ford.wechat.respository.calculator.impl;

import com.ford.wechat.entity.calculator.Calculator;
import com.ford.wechat.respository.calculator.CalculatorResponsitory;
import io.dabing.core.repository.DefaultJpaRepository;
import io.dabing.core.repository.domain.StringQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wanglijun on 16/11/18.
 */
@Repository
public class CalculatorResponsitoryImpl extends DefaultJpaRepository<Calculator, Long> implements CalculatorResponsitory {


    @Override
    public List<Object> getAllCarType() {
        StringQuery query = StringQuery.newQuery().query("select c.carType as carType from Calculator c where 1=1 group by c.carType ")
                .query(" order by c.carType ")
                .build();
        return findObject(query);
    }

    @Override
    public List<Object> getAllLicType(String carType) {
        StringQuery query = StringQuery.newQuery().query("select c.licType as licType from Calculator c where 1=1 ")
                .query(" and c.carType = :carType ")
                .param("carType", carType)
                .query(" group by c.licType ")
                .query(" order by c.licType ")
                .build();
        return findObject(query);
    }

    @Override
    public List<Object> getAllMonth(String carType, String licType) {
        StringQuery query = StringQuery.newQuery().query("select c.month as month from Calculator c where 1=1 ")
                .query(" and c.carType = :carType ")
                .param("carType", carType)
                .query(" and c.licType = :licType ")
                .param("licType", licType)
                .query(" group by c.month ")
                .query(" order by c.month ")
                .build();
        return findObject(query);
    }

    @Override
    public List<Calculator> getAllList(String carType, String licType, long month) {
        StringQuery query = StringQuery.newQuery().query("select c from Calculator c where 1=1 ")
                .query(" and c.carType = :carType ")
                .param("carType", carType)
                .query(" and c.licType = :licType ")
                .param("licType", licType)
                .query(" and c.month = :month ")
                .param("month", month)
                .query(" order by c.id ")
                .build();
        return find(query);
    }
}
