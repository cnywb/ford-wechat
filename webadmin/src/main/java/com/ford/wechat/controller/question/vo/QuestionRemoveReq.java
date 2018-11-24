package com.ford.wechat.controller.question.vo;

import java.util.List;

/**
 * Created by wanglijun on 16/11/2.
 */
public class QuestionRemoveReq {

    private List<QuestionRemoveVo> dataVo;

    public List<QuestionRemoveVo> getDataVo() {
        return dataVo;
    }

    public void setDataVo(List<QuestionRemoveVo> dataVo) {
        this.dataVo = dataVo;
    }
}
