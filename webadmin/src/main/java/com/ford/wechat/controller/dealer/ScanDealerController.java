package com.ford.wechat.controller.dealer;/**
 * Created by jovi on 17/02/2017.
 */

import com.ford.wechat.controller.config.vo.RedirectConfigHandleReq;
import com.ford.wechat.controller.dealer.vo.ScanDealerGetReq;
import com.ford.wechat.controller.dealer.vo.ScanDealerHandleReq;
import com.ford.wechat.controller.dealer.vo.ScanDealerPageResp;
import com.ford.wechat.controller.dealer.vo.ScanDealerRemoveReq;
import com.ford.wechat.entity.dealer.ScanDealer;
import com.ford.wechat.service.dealer.ScanDealerService;
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
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-02-17 11:15
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Controller
public class ScanDealerController {
    @Autowired
    private ScanDealerService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "scanDealerPage")
    public Page<ScanDealerPageResp> page(ScanDealerGetReq req) {
        Page<ScanDealer> pages = service.pagingBy (req.getPage ());
        Page<ScanDealerPageResp> respS = pages.map (new Converter<ScanDealer, ScanDealerPageResp>() {
            public ScanDealerPageResp convert(ScanDealer source) {
                ScanDealerPageResp resp = new ScanDealerPageResp ();
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
    @ApiService(transCode = "scanDealerHandle")
    public String handle(ScanDealerHandleReq req) {
        ScanDealer entity = new ScanDealer ();
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
    @ApiService(transCode = "scanDealerRemove")
    public void remove(ScanDealerRemoveReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }

}
