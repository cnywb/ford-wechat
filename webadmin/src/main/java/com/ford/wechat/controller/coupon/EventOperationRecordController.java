package com.ford.wechat.controller.coupon;

import com.ford.wechat.controller.coupon.vo.EventOperationRecordGetReq;
import com.ford.wechat.controller.coupon.vo.EventOperationRecordPageResp;
import com.ford.wechat.entity.coupon.EventOperationRecord;
import com.ford.wechat.service.coupon.EventOperationRecordService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Controller
public class EventOperationRecordController {

    @Autowired
    private EventOperationRecordService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "eventOperationRecordPage")
    public Page<EventOperationRecordPageResp> page(EventOperationRecordGetReq req) {

        String createStartDate = null;
        String createEndDate = null;
        if(req.getCreateEndDate() != null && req.getCreateStartDate() != null){
            createStartDate = req.getCreateStartDate().replaceAll("-","");
            createEndDate = req.getCreateEndDate().replaceAll("-","");
        }

        Page<EventOperationRecord> pages = service.pagingBy(req.getProjectCode(),req.getOpenId(),req.getOperationType(),createStartDate,createEndDate,req.getPage());

        Page<EventOperationRecordPageResp> respS = pages.map (new Converter<EventOperationRecord, EventOperationRecordPageResp>() {
            public EventOperationRecordPageResp convert(EventOperationRecord source) {
                EventOperationRecordPageResp resp = new EventOperationRecordPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }
}


