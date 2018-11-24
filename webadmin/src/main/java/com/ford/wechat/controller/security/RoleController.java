/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved. 
 * Role.java 15/10/31 上午9:49
 */
package com.ford.wechat.controller.security;


import com.ford.wechat.controller.TransCode;
import com.ford.wechat.controller.security.vo.*;
import com.ford.wechat.entity.security.BasPermission;
import com.ford.wechat.entity.security.BasRole;
import com.ford.wechat.entity.security.OperationType;
import com.ford.wechat.lisenter.LogUtil;
import com.ford.wechat.service.security.PermissionService;
import com.ford.wechat.service.security.ResourceService;
import com.ford.wechat.service.security.RoleService;
import com.ford.wechat.service.security.UserRoleService;
import io.dabing.common.date.DateUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yang.kui on 15/10/31.
 * 角色管理
 *
 * @since 1.0
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ResourceService resourceService;

    //按关键字分页查询对象
    @ApiService(transCode = TransCode.T_9100)
    public Page<T9100Resp> findPage(T9100Req req) {
        Page<BasRole> pages = roleService.findByGridPage(req.getPage());
        Page<T9100Resp> respS = pages.map(new Converter<BasRole, T9100Resp>() {
            public T9100Resp convert(BasRole source) {
                T9100Resp resp = new T9100Resp();
                BeanUtils.copyProperties(source, resp);
                resp.setFirstInsert(DateUtil.formatDate(source.getCreatedDate(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS));
                return resp;
            }
        });
        return respS;
    }

    //单一创建对象
    @ApiService(transCode = TransCode.T_9101)
    public String create(T9101Req req) {
        boolean isExist = roleService.existCode(req.getCode(), null);
        if (isExist) {
            return "角色代码已存在!";
        }
        BasRole object = new BasRole ();
        BeanUtils.copyProperties(req, object);
        roleService.save(object);
        LogUtil.writer (OperationType.AUTH_ROLE_USER_ADD,object.getCode (),object.getName ());
        return "";
    }

    //单一创建对象
    @ApiService(transCode = "modifyRole")
    public String modifyRole(T9101Req req) {
        boolean isExist = roleService.existCode(req.getCode(), req.getId());
        if (isExist) {
            return "角色代码已存在!";
        }
       BasRole object = roleService.get(req.getId());
        BeanUtils.copyProperties(req, object);
        roleService.update(object);
        LogUtil.writer (OperationType.ROLE_MODIFY,object.getCode (),req.getCode (),object.getName (),object.getName ());
        return "";
    }

    //资源权限预览
    @ApiService(transCode = "viewResource")
    public List<ViewPermissionResp> viewResource(ViewPermissionReq req) {
        List<ViewPermissionResp> respList = resourceService.findTreeByRoleCode(req.getRoleCode(), null, ViewPermissionResp.class);
        for (ViewPermissionResp r :respList){
            r.setPermissionBoolean(r.getRESOURCECODE()!=null);
            r.setResourceId(r.getRESOURCEID());
            r.setResourceCode(r.getRESOURCECODE());
            r.setResourceName(r.getRESOURCENAME());
            r.setIsMenu(r.getISMENU());
            r.setParentCode(r.getPARENTCODE());
            r.setPermissionBoolean(r.getPRESOURCECODE()!=null);
        }
        return respList;
    }

    //授权资源权限
    @ApiService(transCode = "authorizeResource")
    public void authorizeResource(AuthorizeResourceReq req) {
        String roleCode = req.getRoleCode();
        List<BasPermission> permissionList = permissionService.findBy(roleCode);

        List<String> removes=new ArrayList<> ();
        List<String> inserts=new ArrayList<> ();

        //被去除的资源权限
        List<BasPermission> permissionRemoveList = new ArrayList<BasPermission>();
        for (BasPermission permission : permissionList) {
            boolean flag = true;
            for (PermissionVo pvo : req.getPermissionVoList()) {
                if (permission.getResourceCode().equals(pvo.getResourceCode())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                removes.add (permission.getResourceCode ());
                permissionRemoveList.add(permission);
            }
        }
        //新增资源权限
        List<BasPermission> permissionInsertList = new ArrayList<BasPermission>();
        for (PermissionVo pvo : req.getPermissionVoList()) {
            boolean flag = true;
            for (BasPermission permission : permissionList) {
                if (pvo.getResourceCode().equals(permission.getResourceCode())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                BasPermission permissionInsert = new BasPermission();
                permissionInsert.setResourceCode(pvo.getResourceCode());
                permissionInsert.setRoleCode(roleCode);
                permissionInsertList.add(permissionInsert);
                inserts.add (permissionInsert.getResourceCode ());
            }
        }
        permissionService.deleteAndInsert(permissionRemoveList, permissionInsertList);
        //添加日志
        LogUtil.writer (OperationType.AUTH_ROLE_RESOURCE,roleCode,inserts.toString (),removes.toString ());
    }

    //批量删除对象
    @ApiService(transCode = TransCode.T_9103)
    public void delete(T9103Req req) {
        for (String roleCode : req.getReqVos()) {
            roleService.delete(roleCode);
        }
    }

    private static Map<String, String> createColumnNames() {
        Map<String, String> columnNames = new HashMap<String, String>();
        columnNames.put("name", "角色名称");
        columnNames.put("remark", "备注");
        columnNames.put("firstInsert", "创建时间");
        return columnNames;
    }

    /***
     * 创建 excel 数据行数据集合
     *
     * @param roleList 数据对象集合
     * @return
     */
    private static List<Map<String, Object>> createExcelRecord(String fileName, List<BasRole> roleList) {
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", fileName);
        listMap.add(map);
        for (int j = 0; j < roleList.size(); j++) {
            BasRole object = roleList.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("name", object.getName());
            mapValue.put("remark", object.getRemark());
            mapValue.put("firstInsert", DateUtil.formatDate(object.getCreatedDate(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS));
            listMap.add(mapValue);
        }
        return listMap;
    }
}