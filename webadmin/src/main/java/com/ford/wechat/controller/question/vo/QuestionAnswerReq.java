package com.ford.wechat.controller.question.vo;/**
 * Created by jovi on 03/12/2016.
 */

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2016-12-03 16:54
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
public class QuestionAnswerReq {
    /**
     * 问题编号
     */
    private Long id;
    /**
     * 问题回答
     */
    private String answerContent;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
