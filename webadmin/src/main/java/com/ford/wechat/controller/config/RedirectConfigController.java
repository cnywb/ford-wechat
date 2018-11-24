package com.ford.wechat.controller.config;

import com.ford.wechat.controller.config.vo.*;
import com.ford.wechat.entity.config.RedirectConfig;
import com.ford.wechat.service.config.RedirectConfigService;
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
public class RedirectConfigController {

    @Autowired
    RedirectConfigService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "redirectConfigPage")
    public Page<RedirectConfigPageResp> redirectConfigPage(RedirectConfigPageReq req) {
        Page<RedirectConfig> pages = service.pagingBy (req.getState(),req.getUrl(),req.getPage ());
        Page<RedirectConfigPageResp> respS = pages.map (new Converter<RedirectConfig, RedirectConfigPageResp> () {
            public RedirectConfigPageResp convert(RedirectConfig source) {
                RedirectConfigPageResp resp = new RedirectConfigPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用redirectConfigHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "redirectConfigHandle")
    public String redirectConfigHandle(RedirectConfigHandleReq req) {
        RedirectConfig entity = new RedirectConfig ();
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
     * 删除对象处理,批量,单一删除均支持 调用redirectConfigRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "redirectConfigRemove")
    public void redirectConfigRemove(RedirectConfigRemoveReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }
}