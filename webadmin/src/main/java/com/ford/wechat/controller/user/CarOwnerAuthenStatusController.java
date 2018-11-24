package com.ford.wechat.controller.user;

import com.ford.wechat.controller.user.vo.CarOwnerAuthenStatusGetReq;
import com.ford.wechat.controller.user.vo.CarOwnerAuthenStatusHandleReq;
import com.ford.wechat.controller.user.vo.CarOwnerAuthenStatusPageResp;
import com.ford.wechat.controller.user.vo.CarOwnerAuthenStatusRemoveReq;
import com.ford.wechat.entity.user.CarOwnerAuthenStatus;
import com.ford.wechat.service.bind.BindService;
import com.ford.wechat.service.user.CarOwnerAuthenStatusService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/24.
 */
@Controller
public class CarOwnerAuthenStatusController {
    @Autowired
    private CarOwnerAuthenStatusService service;

    @Autowired
    private BindService bindService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "carOwnerAuthenStatusPage")
    public Page<CarOwnerAuthenStatusPageResp> page(CarOwnerAuthenStatusGetReq req) {
        Page<CarOwnerAuthenStatus> pages =service.pagingBy(req.getUserVin(),req.getOpenId(),req.getUserMobile(),req.getAuthState(),req.getPage());
        Page<CarOwnerAuthenStatusPageResp> respS = pages.map (new Converter<CarOwnerAuthenStatus, CarOwnerAuthenStatusPageResp>() {
            public CarOwnerAuthenStatusPageResp convert(CarOwnerAuthenStatus source) {
                CarOwnerAuthenStatusPageResp resp = new CarOwnerAuthenStatusPageResp();
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
    @ApiService(transCode = "carOwnerAuthenStatusHandle")
    public String handle(CarOwnerAuthenStatusHandleReq req) {
        String resultValue = "success";
        CarOwnerAuthenStatus entity = new CarOwnerAuthenStatus ();
        entity = service.getById(req.getId());
               // service.get (req.getUserVin());
        if (req.getNotPass()==0) {
            entity.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
            entity.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_FAIL);
            entity.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_MANUAL);
            entity.setTimes(entity.getTimes()+1);
            entity.setAuthDate(new Date());
        }
        else {
            try{
                int result=bindService.doOwnerAuthenForConsole(entity.getChannelCode(), entity.getChannelType(), req.getOpenId(),req.getUserMobile(),req.getUserVin(),req.getImgUrlId());
                if(result==1){
                    entity.setUserVin(req.getUserVin());
                    entity.setAuthState(CarOwnerAuthenStatus.AUTH_STATE_AUDITED);
                    entity.setAuthResult(CarOwnerAuthenStatus.AUTH_RESULT_PAAS);
                    entity.setAuthWay(CarOwnerAuthenStatus.AUTH_WAY_MANUAL);
                    entity.setTimes(entity.getTimes()+1);
                    entity.setAuthDate(new Date());
                }
                else {
                    resultValue = "审核失败!";
                }
            }
            catch (Exception e){
                e.printStackTrace();
                resultValue = "审核失败!";
            }
        }
        if (req.getUserVin() != null) {
            service.update (entity);
        } else {
            service.save (entity);
        }
        return resultValue;
    }


    /**
     * 删除对象处理,批量,单一删除均支持 调用userInfoRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "carOwnerAuthenStatusRemove")
    public void userInfoRemove(CarOwnerAuthenStatusRemoveReq req) {
           CarOwnerAuthenStatus carOwnerAuthenStatus = service.getById(req.getId());
            service.delete(carOwnerAuthenStatus);
    }


}
