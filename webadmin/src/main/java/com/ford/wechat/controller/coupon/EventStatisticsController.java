package com.ford.wechat.controller.coupon;

import com.ford.wechat.controller.coupon.vo.EventStatisticsGetReq;
import com.ford.wechat.controller.coupon.vo.EventStatisticsPageResp;
import com.ford.wechat.entity.coupon.EventStatistics;
import com.ford.wechat.service.coupon.EventStatisticsService;
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
public class EventStatisticsController {

    @Autowired
    private EventStatisticsService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "eventStatisticsPage")
    public Page<EventStatisticsPageResp> page(EventStatisticsGetReq req) {
        String createStartDate = null;
        String createEndDate = null;
        if(req.getCreateEndDate() != null && req.getCreateStartDate() != null){
             createStartDate = req.getCreateStartDate().replaceAll("-","");
             createEndDate = req.getCreateEndDate().replaceAll("-","");
        }

       // Page<EventStatistics> pages = service.pagingBy (req.getPage ());
        Page<EventStatistics> pages = service.pagingBy(req.getProjectCode(),createStartDate,createEndDate,req.getPage());

        Page<EventStatisticsPageResp> respS = pages.map (new Converter<EventStatistics, EventStatisticsPageResp>() {
            public EventStatisticsPageResp convert(EventStatistics source) {
                EventStatisticsPageResp resp = new EventStatisticsPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }
}

