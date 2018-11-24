package com.ford.wechat.hub.controller.survey;

import com.ford.wechat.entity.survey.SurveyCallback;
import com.ford.wechat.hub.common.response.Response;
import com.ford.wechat.service.survey.SurveyCallbackService;
import io.dabing.common.exception.BusinessException;
import io.dabing.common.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Neel on 16/10/22.
 */
@Slf4j
@Controller
@RequestMapping("/hub/callback")
public class WenJuanCallbackController {

    @Autowired
    private SurveyCallbackService surveyCallbackService;



    @RequestMapping(value = "/wenjuan", method = RequestMethod.GET)
    public Response<Void> callback(@RequestParam(name="wj_respondent") String openId, @RequestParam(name="wj_short_id") String shortId, @RequestParam(name="wj_respondent_status") String status, @RequestParam(name="wj_timestamp") String timestamp, @RequestParam(name="wj_signature") String signature) throws Exception {
        log.info("/hub/callback/wenjuan/ parameters: [ openId: {}, shortId: {}, status: {} timestamp: {}, signature: {} ]", openId, shortId, status, timestamp, signature);

        //http://localhost:8084/fordwechat/wenjuan/state/callback?wj_respondent=xxxx&wj_short_id=AxswHs&wj_respondent_status=1&wj_timestamp=130000000&wj_signature=KJADBJKJKHDJSHHDHD

        String validSignature = MD5Utils.digest(openId + status + shortId + timestamp);
        log.info("valid signature is: {}", validSignature);
        /*if (!validSignature.equals(signature)) {
            throw new BusinessException("签名错误");
        }*/

        SurveyCallback entity = new SurveyCallback();
        entity.setOpenId(openId);
        entity.setShortId(shortId);
        entity.setSignature(signature);
        entity.setStatus("1".equals(status) ? "有效答卷" : "无效答卷");
        entity.setTimestamp(timestamp);

        this.surveyCallbackService.save(entity);

        return new Response<>();

    }

}
