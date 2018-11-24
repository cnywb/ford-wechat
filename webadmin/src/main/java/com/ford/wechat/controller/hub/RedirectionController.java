package com.ford.wechat.controller.hub;

import com.ford.wechat.controller.hub.vo.RedirectionHandleReq;
import com.ford.wechat.controller.hub.vo.RedirectionPageReq;
import com.ford.wechat.controller.hub.vo.RedirectionPageResp;
import com.ford.wechat.controller.hub.vo.RedirectionDeleteReq;
import com.ford.wechat.entity.hub.Redirection;
import com.ford.wechat.service.hub.RedirectionService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;


/**
 * Created by Neel on 2017-11-02.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class RedirectionController {

    @Autowired
    RedirectionService service;

    /**
     * 按关键字分页查询对象 调用redirectionPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "redirectionPage")
    public Page<RedirectionPageResp> redirectionPage(RedirectionPageReq req) {
        Page<Redirection> pages = service.pagingBy (req.getState(),req.getUrl(),req.getPage ());
        Page<RedirectionPageResp> respS = pages.map (new Converter<Redirection, RedirectionPageResp> () {
            public RedirectionPageResp convert(Redirection source) {
                RedirectionPageResp resp = new RedirectionPageResp();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用redirectionHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "redirectionHandle")
    public String redirectionHandle(RedirectionHandleReq req) {
        Redirection entity = new Redirection();
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
     * 删除对象处理,批量,单一删除均支持 调用redirectionRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "redirectionDelete")
    public void redirectionDetele(RedirectionDeleteReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }
}