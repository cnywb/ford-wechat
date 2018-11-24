package com.ford.wechat.service.survey.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class SurveyResultPageResponse {

    /** 答卷数量 */
    private int total_count;

    /** 当前页 */
    private int current_page;

    /** 每页数量 */
    private int page_size;

    /** 答题详情列表 */
    private List<Map> rspd_list = new ArrayList<>();

}
