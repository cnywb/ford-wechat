package com.ford.wechat.hub.controller.survey;

import com.ford.wechat.hub.common.response.Response;
import com.ford.wechat.service.survey.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Neel on 16/10/22.
 */
@Slf4j
@RestController
@RequestMapping("/hub")
public class WenJuanController {

    @Autowired
    private SurveyService surveyService;


    /**
     * 根据项目短ID 和 openId 查询答题结果
     * @param shortId
     * @param openId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wenjuan/details/{shortId}/{openId}", method = RequestMethod.GET)
    public Response<Map> detail(@PathVariable("shortId") String shortId, @PathVariable(name="openId") String openId) throws Exception {
        log.info("/hub/wenjuan/details/%s/%s parameters: { openId: %s, shortId: %s}", shortId, openId, openId, shortId);

        Map result = this.surveyService.getDetailByShortIdAndSource(shortId, openId);
        if (result.get("err_code") != null) {
            return new Response<>(result.get("err_code").toString(), null);
        }

        return new Response<>(result);
    }

}
