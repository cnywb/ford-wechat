package com.ford.wechat.controller.security;

import com.ford.wechat.controller.security.vo.UserRoleResourcePageReq;
import com.ford.wechat.controller.security.vo.UserRoleResourcePageResp;
import com.ford.wechat.entity.excel.ExcelList;
import com.ford.wechat.service.excel.ExcelService;
import com.ford.wechat.service.security.UserService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by wanglijun on 2016-12-03.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class UserRoleResourceController {

   @Autowired
   private UserService service;


    @Autowired
    private ExcelService excelService;

    /**
     * 按关键字分页查询对象 调用userRoleResourcePage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "userRoleResourcePage")
    public Page<UserRoleResourcePageResp> userRoleResourcePage(UserRoleResourcePageReq req) {
        Page<Object[]> pages = service.pagingBy (req.getPage (),req.getUserName (),req.getRoleName (),req.getResourceName ());
        Page<UserRoleResourcePageResp> respS = pages.map (new Converter<Object[], UserRoleResourcePageResp> () {
            public UserRoleResourcePageResp convert(Object[] source) {
                UserRoleResourcePageResp resp = new UserRoleResourcePageResp ();
                if (source != null) {
                    resp.setUserLoginName(String.valueOf (source[0]));
                    resp.setUserName(String.valueOf (source[1]));
                    resp.setRoleCode(String.valueOf (source[2]));
                    resp.setRoleName(String.valueOf (source[3]));
                    resp.setRoleRemark(String.valueOf (source[4]));
                    resp.setResourceCode(String.valueOf (source[5]));
                    resp.setResourceName(String.valueOf (source[6]));
                    resp.setResourceType(String.valueOf (source[7]));
                    resp.setResourceIsMenu(String.valueOf (source[8]));
                    resp.setResourcePermission(String.valueOf (source[9]));
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 用户权限信息导出
     */
    @RequestMapping(value = "/userRoleResourceExport.ctl")
    public void userRoleResourceExport(HttpServletRequest request, HttpServletResponse response) {
        excelService.exportData(ExcelList.B_CODE_USER_ROLE_RESOURCE, request, response);
    }
}