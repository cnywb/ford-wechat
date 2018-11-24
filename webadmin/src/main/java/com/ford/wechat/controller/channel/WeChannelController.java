package com.ford.wechat.controller.channel;

import com.ford.wechat.controller.channel.vo.WeChannelGetReq;
import com.ford.wechat.controller.channel.vo.WeChannelHandleReq;
import com.ford.wechat.controller.channel.vo.WeChannelPageResp;
import com.ford.wechat.controller.channel.vo.WeChannelRemoveReq;
import com.ford.wechat.entity.dealer.WeChannel;
import com.ford.wechat.service.dealer.WeChannelService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2017
 * All rights reserved. 2017-05-04 13:55
 * </p>
 *
 * @author zhaoliang
 * @version 1.0
 */
@Controller
public class WeChannelController {
    @Autowired
    private WeChannelService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "weChannelPage")
    public Page<WeChannelPageResp> page(WeChannelGetReq req) {
        Page<WeChannel> pages = service.pagingBy(req.getDealerCode(),req.getCode(),req.getName(),req.getType(),req.getPage());
                //service.pagingBy (req.getPage ());
        Page<WeChannelPageResp> respS = pages.map (new Converter<WeChannel, WeChannelPageResp>() {
            public WeChannelPageResp convert(WeChannel source) {
                WeChannelPageResp resp = new WeChannelPageResp();
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
    @ApiService(transCode = "weChannelHandle")
    public String handle(WeChannelHandleReq req) {
        WeChannel entity = new WeChannel ();
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
    @ApiService(transCode = "weChannelRemove")
    public void remove(WeChannelRemoveReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }

}
