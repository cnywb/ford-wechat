package com.ford.wechat.controller.question.vo;

import com.ford.wechat.controller.vo.PageReq;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by wanglijun on 16/11/2.
 */
public class QuestionPageReq  extends PageReq{
    /**
     * 微信openId
     */
    private String openId;

    /**
     * 问题类别
     */
    private String type;

    /**
     * 问题主题
     */
    private String title;

    /**状态值：1为提问，2为已回答，9为删除*/
    private Integer status;
    /**
     * 提问时间
     * @return
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createStartDate;

    /**
     * 提问时间
     * @return
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createEndDate;
    /**
     * 答案时间
     * @return
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date answerStartDate;

    /**
     * 答案时间
     * @return
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date answerEndDate;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAnswerStartDate() {
        return answerStartDate;
    }

    public void setAnswerStartDate(Date answerStartDate) {
        this.answerStartDate = answerStartDate;
    }

    public Date getAnswerEndDate() {
        return answerEndDate;
    }

    public void setAnswerEndDate(Date answerEndDate) {
        this.answerEndDate = answerEndDate;
    }

    public Date getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(Date createStartDate) {
        this.createStartDate = createStartDate;
    }

    public Date getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(Date createEndDate) {
        this.createEndDate = createEndDate;
    }
}
