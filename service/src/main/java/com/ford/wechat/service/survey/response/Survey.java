package com.ford.wechat.service.survey.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Survey {

    /** 用户编号 */
    private String user;

    /** 项目状态 */
    private int status;

    /** 项目短id */
    private String short_id;

    /** 项目id */
    private String project_id;

    /** 项目标题 */
    private String title;

    /** 项目创建时间 */
    private String create_time;

    /** 项目更新时间 */
    private String update_time;

    /** 答题数量 */
    private String respondent_count;

    /** 项目类型 */
    private String ptype;

}
