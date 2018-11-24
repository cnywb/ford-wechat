package com.ford.wechat.entity.survey;

import com.ford.wechat.entity.AuditEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Neel on 16/11/1.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="WE_SURVEY_CONFIG")
public class SurveyConfig extends AuditEntity{

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_SURVEY_CONFIG")
    @SequenceGenerator(name = "SEQ_WE_SURVEY_CONFIG",allocationSize=1,sequenceName = "SEQ_WE_SURVEY_CONFIG")
    private Long id;


    /** 短项目ID*/
    @Column(name="SHORT_ID")
    private String shortId;

    /** 是否需要回调 1. 需要  0. 不需要*/
    @Column(name="NEED_CALLBACK")
    private Boolean needCallback = Boolean.FALSE;

    /** 答题完跳转链接 */
    @Column(name="REDIRECT_URL")
    private String redirectUrl;

    /** 回调地址 */
    @Column(name="CALLBACK_URL")
    private String callbackUrl;

    /** 备注 */
    @Column(name="REMARK")
    private String remark;


}
