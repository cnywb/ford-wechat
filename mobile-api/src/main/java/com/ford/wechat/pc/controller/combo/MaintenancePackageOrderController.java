/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * MaintenancePackageOrderController.java
 */
package com.ford.wechat.pc.controller.combo;

import com.ford.wechat.entity.pc.combo.MaintenancePackage;
import com.ford.wechat.entity.pc.combo.MaintenancePackageOrder;
import com.ford.wechat.pc.common.response.Response;
import com.ford.wechat.pc.controller.combo.vo.*;
import com.ford.wechat.service.pc.combo.MaintenancePackageOrderService;
import com.ford.wechat.service.pc.combo.MaintenancePackageService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 描述:MaintenancePackageOrderController CRUD 逻辑控制层
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@RequestMapping("/api/private/combo")
public class MaintenancePackageOrderController {

    @Autowired
    private MaintenancePackageOrderService service;

    @Autowired
    private MaintenancePackageService maintenancePackageService;

    /**
     * 查询套餐列表
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Response<List<MaintenancePackageListResp>> comboList(@RequestBody ComboListReq req) {
        List<MaintenancePackageListResp> respList = Lists.newLinkedList();
        List<MaintenancePackage> modelList = maintenancePackageService.findByModelAndPackageType(req.getModel(), req.getPackageType());
        for (MaintenancePackage maintenancePackage : modelList) {
            MaintenancePackageListResp resp = new MaintenancePackageListResp();
            BeanUtils.copyProperties(maintenancePackage, resp);
            respList.add(resp);
        }
        return new Response<>(respList);
    }

    /**
     * 查询原厂车型全集
     *
     * @return
     */
    @RequestMapping("/upkeep/model")
    @ResponseBody
    public Response<List<String>> upkeepModel() {
        String packageType = "SSP";
        return findModel(packageType);
    }

    /**
     * 根据VIN获取已经购买的原厂
     *
     * @param req 请求入参对象
     */
    @RequestMapping("/upkeep")
    @ResponseBody
    public Response<List<MaintenancePackageOrderListResp>> upkeep(@RequestBody MaintenancePackageOrderListReq req) {
        List<MaintenancePackageOrderListResp> respList = Lists.newLinkedList();
        List<MaintenancePackageOrder> orderList = service.findByVinAndProductType(req.getVin(), "Schedule Service Plan");
        for (MaintenancePackageOrder order : orderList) {
            MaintenancePackageOrderListResp resp = new MaintenancePackageOrderListResp();
            BeanUtils.copyProperties(order, resp);
            respList.add(resp);
        }
        return new Response<>(respList);
    }

    /**
     * 查询延保车型全集
     *
     * @return
     */
    @RequestMapping("/quality/model")
    @ResponseBody
    public Response<List<String>> qualityModel() {
        String packageType = "EW";
        return findModel(packageType);
    }

    /**
     * 根据VIN获取已经购买的原厂
     *
     * @param req 请求入参对象
     */
    @RequestMapping("/quality")
    @ResponseBody
    public Response<List<MaintenancePackageOrderListResp>> quality(@RequestBody MaintenancePackageOrderListReq req) {
        List<MaintenancePackageOrderListResp> respList = Lists.newLinkedList();
        List<MaintenancePackageOrder> orderList = service.findByVinAndProductType(req.getVin(), "Extended Warranty");
        for (MaintenancePackageOrder order : orderList) {
            MaintenancePackageOrderListResp resp = new MaintenancePackageOrderListResp();
            BeanUtils.copyProperties(order, resp);
            respList.add(resp);
        }
        return new Response<>(respList);
    }

    private Response<List<String>> findModel(String packageType) {
        List<String> modelList = maintenancePackageService.findByPackageType(packageType);
        return new Response<>(modelList);
    }
}