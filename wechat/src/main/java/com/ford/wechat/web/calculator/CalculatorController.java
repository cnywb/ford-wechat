package com.ford.wechat.web.calculator;

import com.ford.wechat.entity.calculator.Calculator;
import com.ford.wechat.service.calculator.CalculatorService;
import com.ford.wechat.web.calculator.vo.CalsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglijun on 16/11/18.
 */
@Controller
@RequestMapping("/fordwechat")
public class CalculatorController {
    /***
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

    @Autowired
    private CalculatorService service;

    @ResponseBody
    @RequestMapping("/getlictype.do")
    public String getlictype(String cartype) {
        return service.getAllLicType(cartype);
    }

    @ResponseBody
    @RequestMapping("/getallcartype.do")
    public String getallcartype() {
        return service.getAllCarType();
    }

    @ResponseBody
    @RequestMapping("/getallcalcultors.do")
    public CalsVo getallcalcultors(String cartype, String lictype, String year, String month) {
        logger.info("cartype{}", cartype);
        logger.info("lictype{}", lictype);
        CalsVo vo = new CalsVo();
        List<Calculator> list = service.getAllCalculators(cartype, lictype, year, month);
        List<Calculator> changes = new ArrayList<>();
        List<Calculator> checkes = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (Calculator c : list) {
                if (c.getType().indexOf("更换") >= 0) {
                    changes.add(c);
                }
                if (c.getType().indexOf("检查") >= 0) {
                    checkes.add(c);
                }
            }
        }
        vo.setChanges(changes);
        vo.setCheckes(checkes);
        return vo;
    }

    @RequestMapping("/caculatorindex.do")
    public ModelAndView caculatorindex() {
        return new ModelAndView("templates/baoy_calculator");
    }

}
