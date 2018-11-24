package com.ford.wechat.controller.coupon;

import com.ford.wechat.controller.coupon.vo.*;
import com.ford.wechat.entity.coupon.Event;
import com.ford.wechat.entity.coupon.EventDetail;
import com.ford.wechat.service.coupon.EventDetailService;
import com.ford.wechat.service.coupon.EventService;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoliang on 2017/8/27.
 */
@Controller
public class EventDetailController {

    @Autowired
    private EventDetailService service;

    @Autowired
    private EventService eventService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "eventDetailPage")
    public Page<EventDetailPageResp> page(EventDetailGetReq req) {

        String createStartDate = null;
        String createEndDate = null;
        if(req.getCreateEndDate() != null && req.getCreateStartDate() != null){
            createStartDate = req.getCreateStartDate().replaceAll("-","");
            createEndDate = req.getCreateEndDate().replaceAll("-","");
        }

        Page<EventDetail> pages = service.pagingBy(req.getProjectCode(),createStartDate,createEndDate,req.getPage());
        // Page<EventDetail> pages = service.pagingBy (req.getPage ());
        Page<EventDetailPageResp> respS = pages.map (new Converter<EventDetail, EventDetailPageResp>() {
            public EventDetailPageResp convert(EventDetail source) {
                EventDetailPageResp resp = new EventDetailPageResp();
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
    @ApiService(transCode = "eventDetailHandle")
    public String handle(EventDetailHandleReq req) {

        eventService.compareDateNo(req.getProjectCode(),req.getDateNo());

        EventDetail entity = new EventDetail ();

        if (req.getId () != null) {
            entity = service.get (req.getId ());
        }

        BeanUtils.copyProperties (req, entity);

        if (req.getId () != null) {
            service.update (entity);
        } else {
            EventDetail eventDetail = service.getByDateNo(req.getDateNo());
            Assert.isNull(eventDetail, "该日期批次已经存在!");
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
    @ApiService(transCode = "eventDetailRemove")
    public void remove(EventDetailRemoveReq req) {
        for (Long id:req.getIds()) {
            service.delete (id);
        }
    }


    /**
     * 查询代金券活动配置
     */
    @ApiService(transCode = "eventList")
    public List<EventPageResp> listEvent(EventGetReq req) {
        List<EventPageResp> body = new ArrayList<>();
        List<Event> data = eventService.findAll();
        for (Event item : data) {
            EventPageResp event = new EventPageResp();
            BeanUtils.copyProperties(item, event);
            event.setEventId(item.getId());
            body.add(event);
        }
        return body;
    }

}


