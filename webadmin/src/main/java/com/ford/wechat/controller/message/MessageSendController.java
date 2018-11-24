package com.ford.wechat.controller.message;


import com.ford.wechat.controller.message.vo.MessageSendGetReq;
import com.ford.wechat.controller.message.vo.MessageSendPageResp;
import com.ford.wechat.entity.message.MessageSend;
import com.ford.wechat.service.message.MessageSendService;
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
public class MessageSendController {
    
    @Autowired
    private MessageSendService service;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "messageSendPage")
    public Page<MessageSendPageResp> page(MessageSendGetReq req) {
       //  Page<MessageSend> pages = service.pagingBy (req.getPage ());
        Page<MessageSend> pages = service.pagingBy(req.getSendResult(),req.getMobile(),req.getProjectCode(),req.getOpenId(),req.getVin(),req.getCreateStartDate(),req.getCreateEndDate(),req.getPage());
        Page<MessageSendPageResp> respS = pages.map (new Converter<MessageSend, MessageSendPageResp>() {
            public MessageSendPageResp convert(MessageSend source) {
                MessageSendPageResp resp = new MessageSendPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }
}


