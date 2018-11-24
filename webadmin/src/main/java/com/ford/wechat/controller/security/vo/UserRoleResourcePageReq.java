package com.ford.wechat.controller.security.vo;

import com.ford.wechat.controller.vo.PageReq;

/**
 * Created by wanglijun on 16/12/3.
 */
public class UserRoleResourcePageReq extends PageReq {
    /**用户名称*/
    private String userName;
    /**角色名称*/
    private String roleName;
    /**资源名称*/
    private String resourceName;


    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
