package com.ford.wechat.service.survey.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SurveyPageResponse {

    /** 答卷数量 */
    private int total_count;

    /** 当前页 */
    private int current_page;

    /** 每页数量 */
    private int page_size;

    /** 项目列表 */
    private List<Survey> project_list = new ArrayList<>();

}
