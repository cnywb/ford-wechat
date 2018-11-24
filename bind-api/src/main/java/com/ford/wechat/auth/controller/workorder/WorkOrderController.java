package com.ford.wechat.auth.controller.workorder;

import com.ford.wechat.auth.common.response.Response;
import com.ford.wechat.auth.common.utils.SessionUtils;
import com.ford.wechat.auth.controller.bind.vo.VinResp;
import com.ford.wechat.service.members.WeWorkorderApplyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Neel on 2017/5/26.
 */

@RestController
@RequestMapping(value="/i/service")
public class WorkOrderController {

    @Autowired
    private WeWorkorderApplyService weWorkorderApplyService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Response<List<VinResp>> save(String vin, String mobile, String question, String option, String dealer, String desc, Long imageId, String imageUrl) {
        String openId = SessionUtils.getOpenId();

        if (StringUtils.isNotEmpty(vin)) {
            vin = vin.toUpperCase();
        }

        this.weWorkorderApplyService.saveWorkorderApply(openId, vin, mobile, question, option, dealer, desc, imageId, imageUrl);

        /*if (question.equals("修改手机")) {//修改手机
            this.weWorkorderApplyService.saveChangeMobileApply(openId, vin, mobile, question, option, dealer, desc, imageId, imageUrl);
        } else {//解绑
            this.weWorkorderApplyService.saveUnbindVinApply(openId, vin, question, option, dealer, desc, imageId, imageUrl);
        }*/

        return new Response<>();
    }
}
