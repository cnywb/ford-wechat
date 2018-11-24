package com.ford.wechat.controller.coupon;

import com.ford.wechat.controller.coupon.vo.CouponDmsGetReq;
import com.ford.wechat.controller.coupon.vo.CouponDmsHandleReq;
import com.ford.wechat.controller.coupon.vo.CouponDmsPageResp;
import com.ford.wechat.entity.coupon.CouponDms;
import com.ford.wechat.service.coupon.CouponDmsService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

/**
 * Created by zhaoliang on 2017/8/31.
 */
@Controller
public class CouponDmsController {

    @Autowired
    private CouponDmsService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "couponDmsPage")
    public Page<CouponDmsPageResp> page(CouponDmsGetReq req) {
        Page<CouponDms> pages = service.pagingBy (req.getSendDmsStatus(),req.getTargetDealer(),req.getBatchNo(),req.getVin(),req.getCustomerMobile(),req.getSendSms(),req.getCreateStartDate(), req.getCreateEndDate(), req.getPage());
        Page<CouponDmsPageResp> respS = pages.map (new Converter<CouponDms, CouponDmsPageResp>() {
            public CouponDmsPageResp convert(CouponDms source) {
                CouponDmsPageResp resp = new CouponDmsPageResp ();
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
    @ApiService(transCode = "couponDmsHandle")
    public String handle(CouponDmsHandleReq req) {

        CouponDms entity = new CouponDms();
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
}
