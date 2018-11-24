package com.ford.wechat.controller.members;

import com.ford.wechat.controller.members.vo.WeUnauthApplyGetReq;
import com.ford.wechat.controller.members.vo.WeUnauthApplyHandleReq;
import com.ford.wechat.controller.members.vo.WeUnauthApplyPageResp;
import com.ford.wechat.controller.members.vo.WeUnauthApplyRemoveReq;
import com.ford.wechat.entity.member.WeWorkorderApply;
import com.ford.wechat.entity.member.WeUnauthLog;
import com.ford.wechat.service.members.WeWorkorderApplyService;
import com.ford.wechat.service.members.WeUnauthLogService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import static com.ford.wechat.entity.member.WeWorkorderApply.ASSESS_RESULT_CLOSE;

/**
 * Created by zhaoliang on 2017/5/25.
 */
@Controller
public class WeUnauthApplyController {
    @Autowired
    private WeWorkorderApplyService service;

    @Autowired
    private WeUnauthLogService logService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "weUnauthApplyPage")
    public Page<WeUnauthApplyPageResp> page(WeUnauthApplyGetReq req) {
        Page<WeWorkorderApply> pages = service.pagingBy(req.getVin(),req.getOpenId(),req.getMobile(),req.getAssessStatus(),req.getPage());
                //service.pagingBy (req.getPage ());
        Page<WeUnauthApplyPageResp> respS = pages.map (new Converter<WeWorkorderApply, WeUnauthApplyPageResp>() {
            public WeUnauthApplyPageResp convert(WeWorkorderApply source) {
                WeUnauthApplyPageResp resp = new WeUnauthApplyPageResp();
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
    @ApiService(transCode = "weUnauthApplyHandle")
    public String handle(WeUnauthApplyHandleReq req) {
        WeUnauthLog logEntity = new WeUnauthLog();
        int logValue;
        if(req.getNotPass()==0){
            //调用不通过解绑或修改手机号
             logValue=service.noPass(req.getId(),req.getApplyType(), req.getAssessMemo());
        } else
            {       String vin = req.getVin();
                if (StringUtils.isNotEmpty(req.getVin())){
                     vin = req.getVin().toUpperCase();
                }
            //调用修改手机或解绑方法
             logValue=service.doUnAuthenOrChangeMobile(req.getId(),req.getApplyType(),req.getOpenId(),vin ,req.getMobile());
        }
        logEntity.setOldOpenId(req.getOldOpenId());
        logEntity.setNewOpenId(req.getOpenId());
        logEntity.setOldMobile(req.getOldMobile());
        logEntity.setNewMobile(req.getMobile());
        logEntity.setVin(req.getVin());
        logEntity.setOldName(req.getOldName());
        logEntity.setNewName(req.getName());
        logEntity.setLicenseImgId(req.getImgUrlId());
        logEntity.setLicenseUrl(req.getLicenseUrl());
        if(logValue==0){
            //解绑失败
           logEntity.setStatus(0);
        }else if(logValue==1){
            //解绑成功
           logEntity.setStatus(1);
        }else if(logValue==2){
            //修改手机成功
            logEntity.setStatus(2);
        }else {
            //修改手机失败
           logEntity.setStatus(3);
        }

        logService.save(logEntity);

        return "";
    }



    /**
     * 关闭工单 调用redirectConfigRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "weUnauthApplyRemove")
    public void remove(WeUnauthApplyRemoveReq req) {
        WeWorkorderApply weWorkorderApply = service.get(req.getId());
        weWorkorderApply.setAssessResult(ASSESS_RESULT_CLOSE);
        service.update(weWorkorderApply);
    }

}
