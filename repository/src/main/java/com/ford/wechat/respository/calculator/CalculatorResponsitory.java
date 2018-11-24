package com.ford.wechat.respository.calculator;

import com.ford.wechat.entity.applink.AppLinkInfo;
import com.ford.wechat.entity.calculator.Calculator;
import io.dabing.core.repository.JpaRepository;

import java.util.List;

/**
 * Created by wanglijun on 16/11/18.
 */
public interface CalculatorResponsitory extends JpaRepository<Calculator, Long> {

    @Override
    List<Calculator> findAll();

    List<Object> getAllCarType();

    List<Object> getAllLicType(String carType);

    List<Object> getAllMonth(String carType, String licType);

    List<Calculator> getAllList(String carType, String licType, long month);
}
