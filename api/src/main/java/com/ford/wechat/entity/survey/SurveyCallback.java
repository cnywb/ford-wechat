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
@Table(name="WE_SURVEY_CALLBACK")
public class SurveyCallback extends AuditEntity{

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_SURVEY_CALLBACK")
    @SequenceGenerator(name = "SEQ_WE_SURVEY_CALLBACK",allocationSize=1,sequenceName = "SEQ_WE_SURVEY_CALLBACK")
    private Long id;


    /** 短项目ID*/
    @Column(name="SHORT_ID")
    private String shortId;

    /** 微信openId*/
    @Column(name="OPEN_ID")
    private String openId;

    /**答题状态：有效答卷； 无效答卷*/
    @Column(name="STATUS")
    private String status;

    /**回调时间戳*/
    @Column(name="TIMESTAMP")
    private String timestamp;

    /**签名*/
    @Column(name="SIGNATURE")
    private String signature;

}
