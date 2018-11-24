package com.ford.wechat.entity.question;

import com.ford.wechat.entity.VersionEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wanglijun on 16/11/1.
 */
@Entity
@Table(name= "WE_QUESTION")
public class Question extends VersionEntity{


    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.SEQUENCE,generator = "SEQ_WE_QUESTION_ID")
    @SequenceGenerator(name = "SEQ_WE_QUESTION_ID",allocationSize=1,sequenceName = "SEQ_WE_QUESTION_ID")
    private Long id;

    /**微信openId*/
    @Column(name="OPEN_ID")
    private String openId;

    /**问题类别*/
    @Column(name="QUESTION_TYPE")
    private String type;

    /**问题主题*/
    @Column(name="Q_TITLE")
    private String title;

    /**内容*/
    @Column(name="Q_CONTENT")
    private String content;

    /**答案内容*/
    @Column(name="A_CONTENT")
    private String answerContent;

    /**答案时间*/
    @Column(name="ANSWER_DATE")
    private Date answerDate;

    /**状态值：1为提问，2为已回答，9为删除*/
    @Column(name="FLAG")
    private Integer status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }
}
