/*
 * Copyright (c)  2015, dabing.io
 * All rights reserved. 
 * User.java 15/10/19 下午3:53
 */
package com.ford.wechat.controller.security;


import com.ford.wechat.controller.TransCode;
import com.ford.wechat.controller.security.vo.*;
import com.ford.wechat.entity.security.BasUser;
import com.ford.wechat.entity.security.OperationType;
import com.ford.wechat.lisenter.LogUtil;
import com.ford.wechat.service.security.UserRoleService;
import com.ford.wechat.service.security.UserService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ziv.hung on 15/10/19.
 * 管理人员 业务逻辑
 *
 * @since 1.0
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;


    //按关键字分页查询对象
    @ApiService(transCode = TransCode.T_9000)
    public Page<T9000Resp> findPage(T9000Req req) {
        return searchUsers(req.getPage());
    }

    /**
     * 角色授权用户界面使用 可供授权用户分页数据
     */
    @ApiService(transCode = "preAuthorizedUsers")
    public Page<T9000Resp> preAuthorizedUsers(T9000Req req) {
        return searchUsers(req.getPage());
    }

    private Page<T9000Resp> searchUsers(GridPage page) {
        Page<BasUser> pages = userService.findByGridPage(page);
        Page<T9000Resp> respS = pages.map(new Converter<BasUser, T9000Resp>() {
            public T9000Resp convert(BasUser source) {
                T9000Resp resp = new T9000Resp();
                BeanUtils.copyProperties(source, resp);
                resp.setFirstInsert(DateUtil.formatDate(source.getCreatedDate(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS));
                return resp;
            }
        });
        return respS;
    }

    //单一创建对象
    @ApiService(transCode = TransCode.T_9001)
    public String create(T9001Req req) {
        BasUser user = new BasUser ();
        Boolean userCheck = userService.findUserBy(req.getLoginName(), null);
        if (userCheck) {
            return "登录用户已存在!";
        }
        BeanUtils.copyProperties(req, user);
        //密码采用加密后的密码
        if (req.getPassword() != null) {
            user.setPassword(userService.getEncodePassword(req.getPassword()));
        }
        LogUtil.writer (OperationType.USER_CREATE,req.getLoginName ());
        this.userService.save(user);
        return "";
    }

    //单一创建对象
    @ApiService(transCode = "userModify")
    public String userModify(T9001Req req) {
        BasUser user = userService.get(req.getId());
        Boolean userCheck = userService.findUserBy(req.getLoginName(), user.getId());
        if (userCheck) {
            return "登录用户已存在!";
        }
        BeanUtils.copyProperties(req, user, new String[]{"password",});
        this.userService.update(user);
        LogUtil.writer (OperationType.USER_MODIFY,user.getLoginName (),req.getLoginName ());
        return "";
    }


    //登录用户自改密码
    @ApiService(transCode = "modifyPwd")
    public void modifyPwd(T9001Req req) {
        BasUser user = userService.getCurrentUser();
        //密码采用加密后的密码
        if (req.getPassword() != null) {
            user.setPassword(userService.getEncodePassword(req.getPassword()));
        }
        user.setCredentialExpiredDate (DateUtil.getNextDateByMonth(new Date (), 3));
        this.userService.update(user);
        LogUtil.writer (OperationType.USER_PASSWORD_MODIFY,user.getLoginName (),req.getPassword ().replace (req.getPassword (),LogUtil.SENSITIVE_WORDS));
    }

    //单一创建对象
    @ApiService(transCode = "modifyPassword")
    public void modifyPassword(T9001Req req) {
        BasUser user = userService.get(req.getId());
        //密码采用加密后的密码
        if (req.getPassword() != null) {
            user.setPassword(userService.getEncodePassword(req.getPassword()));
        }
        //密码有效期为3个月
        user.setCredentialExpiredDate (DateUtil.getNextDateByMonth (new Date (),3));
        LogUtil.writer (OperationType.USER_PASSWORD_RESET,user.getLoginName ());
        this.userService.update(user);
    }

    //批量删除对象
    @ApiService(transCode = TransCode.T_9003)
    public void delete(T9003Req req) {
        List<String> params=new ArrayList<> ();
        for (T9003ReqVo vo : req.getReqs()) {
            BasUser user = userService.get(vo.getId());
            this.userRoleService.deleteByUserId(user.getId());
            this.userService.delete(vo.getId());
            params.add (user.getLoginName ());
        }

        LogUtil.writer (OperationType.USER_REMOVE,params.toString ());
    }
}