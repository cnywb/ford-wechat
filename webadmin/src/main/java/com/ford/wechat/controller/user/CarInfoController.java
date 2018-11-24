package com.ford.wechat.controller.user;

import com.ford.wechat.controller.user.vo.*;
import com.ford.wechat.entity.user.CarInfo;
import com.ford.wechat.service.user.CarInfoService;
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
public class CarInfoController {

    @Autowired
    CarInfoService service;

    /**
     * 按关键字分页查询对象 调用carInfoPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "carInfoPage")
    public Page<CarInfoPageResp> carInfoPage(CarInfoPageReq req) {
        Page<CarInfo> pages = service.pagingBy (req.getOpenId(),req.getBuyDealerName(),req.getRepairDealerName(),req.getModel(),req.getStyle(),req.getColor(),req.getBuyStartDate(),req.getBuyEndDate(),req.getPage ());
        Page<CarInfoPageResp> respS = pages.map (new Converter<CarInfo, CarInfoPageResp> () {
            public CarInfoPageResp convert(CarInfo source) {
                CarInfoPageResp resp = new CarInfoPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用carInfoHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "carInfoHandle")
    public String carInfoHandle(CarInfoHandleReq req) {
        CarInfo entity = new CarInfo ();
        if (req.getId () != null) {
            entity = service.get (req.getOpenId ());
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
     * 删除对象处理,批量,单一删除均支持 调用carInfoRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "carInfoRemove")
    public void carInfoRemove(CarInfoRemoveReq req) {
//        for (CarInfoRemoveVo vo : req.getDataVo ()) {
//            service.delete (vo.getId ());
//        }
    }
}