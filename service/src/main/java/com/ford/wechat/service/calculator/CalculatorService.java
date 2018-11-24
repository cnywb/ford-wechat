package com.ford.wechat.service.calculator;

import com.ford.wechat.entity.calculator.Calculator;

import java.util.List;

/**
 * Created by wanglijun on 16/11/18.
 */
public interface CalculatorService {

    String getAllLicType(String carType);

    String getAllCarType();

    List<Calculator> getAllCalculators(String carType, String licType, String year, String month);
}
