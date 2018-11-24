package com.ford.wechat.controller.applink;

import com.ford.wechat.controller.applink.vo.*;
import com.ford.wechat.entity.applink.AppLinkInfo;
import com.ford.wechat.service.applink.AppLinkInfoService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;


/**
 * Created by wanglijun on 2016-11-02.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class AppLinkInfoController {

    @Autowired
    AppLinkInfoService service;

    /**
     * 按关键字分页查询对象 调用appLinkInfoPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "appLinkInfoPage")
    public Page<AppLinkInfoPageResp> appLinkInfoPage(AppLinkInfoPageReq req) {
        Page<AppLinkInfo> pages = service.pagingBy (req.getPlantForm(),req.getAppLinkName(),req.getDownloadName(),req.getPage ());
        Page<AppLinkInfoPageResp> respS = pages.map (new Converter<AppLinkInfo, AppLinkInfoPageResp> () {
            public AppLinkInfoPageResp convert(AppLinkInfo source) {
                AppLinkInfoPageResp resp = new AppLinkInfoPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用appLinkInfoHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "appLinkInfoHandle")
    public String appLinkInfoHandle(AppLinkInfoHandleReq req) {
        AppLinkInfo entity = new AppLinkInfo ();
        if (req.getId () != null) {
            entity = service.get (req.getId ());
        }
        BeanUtils.copyProperties (req, entity);
        if (req.getId () != null) {
            service.update (entity);
        } else {
            service.save (entity);
        }
        return "";
    }

    /**
     * 删除对象处理,批量,单一删除均支持 调用appLinkInfoRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "appLinkInfoRemove")
    public void appLinkInfoRemove(AppLinkInfoRemoveReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }
}