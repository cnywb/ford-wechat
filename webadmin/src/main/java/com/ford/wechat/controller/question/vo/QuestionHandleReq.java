package com.ford.wechat.controller.question.vo;

import com.ford.wechat.controller.vo.HandleReq;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class QuestionHandleReq extends HandleReq{
    /**
     * 主键
     */
    private Long id;

    /**微信openId*/
    private String openId;

    /**问题类别*/
    private String type;

    /**问题主题*/
    private String title;

    /**内容*/
    private String content;

    /**答案内容*/
    private String answerContent;

    /**答案时间*/
    private Date answerDate;

    /**状态值：1为提问，2为已回答，9为删除*/
    private Integer status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
