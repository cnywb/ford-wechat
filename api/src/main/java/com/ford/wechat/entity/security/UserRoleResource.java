package com.ford.wechat.entity.security;

/**
 * Created by wanglijun on 16/12/3.
 */
public class UserRoleResource {
    /**用户名*/
    private String userName;
    /**登录名*/
    private String  userLoginName;
    /**角色代码*/
    private String roleCode;
    /**角色名称*/
    private String roleName;
    /**角色备注*/
    private String roleRemark;
    /**资源代码*/
    private String resourceCode;
    /**资源名称*/
    private String resourceName;
    /**资源类型*/
    private String resourceType;
    /**是否是菜单*/
    private String resourceIsMenu;
    /**资源权限**/
    private String resourcePermission;


    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceIsMenu() {
        return resourceIsMenu;
    }

    public void setResourceIsMenu(String resourceIsMenu) {
        this.resourceIsMenu = resourceIsMenu;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourcePermission() {
        return resourcePermission;
    }

    public void setResourcePermission(String resourcePermission) {
        this.resourcePermission = resourcePermission;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
