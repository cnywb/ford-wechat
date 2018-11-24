package com.ford.wechat.service.calculator.impl;

import com.alibaba.druid.util.StringUtils;
import com.ford.wechat.entity.calculator.Calculator;
import com.ford.wechat.respository.calculator.CalculatorResponsitory;
import com.ford.wechat.service.calculator.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wanglijun on 16/11/18.
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {
    @Autowired
    private CalculatorResponsitory responsitory;

    //private static final Logger logger = LoggerFactory.getLogger(CalculatorServiceImpl.class);

//    private static List<Calculator> cals = null;
//    static {
//        Calculator c = new Calculator();
//        c.setCarType("金牛座");
//        c.setLicType("5000");
//        c.setProject("更换机油滤清器");
//        c.setResult("建议每10000公里/6个月更换一次<br />*在恶劣环境或空气污染下，建议缩短更换周期。");
//
//        Calculator c1 = new Calculator();
//        c1.setCarType("金牛座");
//        c1.setLicType("5000");
//        c1.setProject("检查挡风玻璃雨刮片");
//        c1.setResult("建议每10000公里/6个月检查一次");
//
//        Calculator c2 = new Calculator();
//        c2.setCarType("金牛座");
//        c2.setLicType("15000");
//        c2.setProject("更换燃油滤清器");
//        c2.setResult("建议每10000公里/6个月更换一次<br />*在恶劣环境或空气污染下，建议缩短更换周期。");
//
//        Calculator c3 = new Calculator();
//        c3.setCarType("金牛座");
//        c3.setLicType("15000");
//        c3.setProject("更换发动机机油");
//        c3.setResult("建议每10000公里/6个月更换一次");
//
//        Calculator c4 = new Calculator();
//        c4.setCarType("金牛座");
//        c4.setLicType("15000");
//        c4.setProject("检查防冻液");
//        c4.setResult("建议每10000公里/6个月更换一次");
//
//        Calculator c5 = new Calculator();
//        c5.setCarType("金牛座");
//        c5.setLicType("15000");
//        c5.setProject("检查轮胎");
//        c5.setResult("建议每10000公里/6个月更换一次");
//
//        cals.add(c);
//        cals.add(c1);
//        cals.add(c2);
//        cals.add(c3);
//        cals.add(c4);
//        cals.add(c5);
//
//    }

//    private List<Calculator> getCals() {
//        if (null == cals)
//            cals = responsitory.findAll();
//        return cals;
//    }

    @Override
    public String getAllCarType() {
        StringBuffer sb = new StringBuffer();
        for (Object c : responsitory.getAllCarType())
            sb.append("," + (String) c);
        //logger.info("getAllCarType====" + sb.toString());
        return sb.toString().substring(1);
        /**List<Calculator> list = getCals();
         logger.info("listsize=======" + list.size());
         StringBuffer sb = new StringBuffer();
         List<String> filters = new ArrayList<>();
         for (Calculator c : list) {
         boolean flag = true;
         for(String f:filters){
         if(f.equals(c.getCarType())){
         flag = false;
         break;
         }
         }
         if(flag){
         filters.add(c.getCarType());
         sb.append("," + c.getCarType());
         }
         }
         logger.info("sb.toString()=======" + sb.toString());
         if (sb.toString().length() > 0)
         return sb.toString().substring(1);
         logger.info("sb.toString()1111=======" + sb.toString());
         return sb.toString();**/
    }

    @Override
    public String getAllLicType(String carType) {
        StringBuffer sb = new StringBuffer();
        for (Object c : responsitory.getAllLicType(carType))
            sb.append("," + (String) c);
        //logger.info("getAllLicType====" + sb.toString());
        return sb.toString().substring(1);
        /**List<Calculator> list = getCals();
         StringBuffer sb = new StringBuffer();
        List<String> filters = new ArrayList<>();
        for (Calculator c : list) {
            if (c.getCarType().equals(carType.trim())) {
                boolean flag = true;
                for (String f : filters) {
                    if (f.equals(c.getLicType())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    filters.add(c.getLicType());
                    sb.append("," + c.getLicType());
                }
            }
        }
         logger.info("sb.toString()=======" + sb.toString());
        if (sb.toString().length() > 0)
            return sb.toString().substring(1);
         logger.info("sb.toString()1111=======" + sb.toString());
         return "";**/
    }

    @Override
    public List<Calculator> getAllCalculators(String carType, String licType, String year, String month) {
        long optionmonth = getOptionMonth(year, month, carType, licType);
        //logger.info("optionmonth====" + optionmonth);
        return responsitory.getAllList(carType, licType, optionmonth);
        /**List<Calculator> list = getCals();
        List<Calculator> cartypes = new ArrayList<>();
        List<Calculator> result = new ArrayList<>();
        for (Calculator c : list) {
            if (c.getCarType().equals(carType.trim()) && c.getLicType().equals(licType.trim()))
                cartypes.add(c);
        }
        if (null != cartypes && cartypes.size() > 0) {
            Long mon = getOptionMonth(year, month, cartypes);
            for (Calculator c : cartypes) {
                if (mon != null && mon.longValue() == c.getMonth().longValue())
                    result.add(c);
            }
        }
         return result;**/
    }

    private Long getDifMonth(String year, String month) {
        Long result = null;
        if (!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month)) {
            Calendar cal = Calendar.getInstance();//使用日历类
            long dyear = cal.get(Calendar.YEAR);//得到年
            long dmonth = cal.get(Calendar.MONTH) + 1;//得到月，因为从0开始的，所以要加1
            result = (dyear - Long.parseLong(year)) * 12 + (dmonth - Long.parseLong(month));
        }
        return result;
    }

    private List<Long> getAllMonth(String cartype, String licType) {
        List<Long> months = new ArrayList<>();
        List<Object> cartypes = responsitory.getAllMonth(cartype, licType);
        for (Object c : cartypes) {
            months.add((long) c);
        }
        Collections.sort(months);
        return months;
    }

    private Long getOptionMonth(String year, String month, String cartype, String licType) {
        Long result = null;
        Long difMonth = getDifMonth(year, month);
        List<Long> allMonth = getAllMonth(cartype, licType);
        if (null != difMonth) {
            for (long m : allMonth) {
                if (m <= difMonth.longValue())
                    result = m;
            }
        }
        return result;
    }

}
