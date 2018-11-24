package com.ford.wechat.service.survey;

import com.ford.wechat.service.survey.response.Survey;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;

import java.util.Map;

public interface SurveyService {

    Page<Map> pagingDetailBy(String shortId, GridPage gridPage);

    Page<Survey> pagingBy(Integer status, GridPage gridPage);

    Map getDetailByShortIdAndSource(String shortId, String source);

//    String saveLink(String shortId, String title);

    String getHubCallbackUri();

    String getHubOAuthUri(String state);

    String getSurveyUri(String shortId, String source);
}
