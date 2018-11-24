package com.ford.wechat.web.calculator.vo;

import com.ford.wechat.entity.calculator.Calculator;

import java.util.List;

/**
 * Created by wanglijun on 16/11/18.
 */
public class CalsVo {
    private List<Calculator> changes = null;
    private List<Calculator> checkes = null;

    public List<Calculator> getChanges() {
        return changes;
    }

    public void setChanges(List<Calculator> changes) {
        this.changes = changes;
    }

    public List<Calculator> getCheckes() {
        return checkes;
    }

    public void setCheckes(List<Calculator> checkes) {
        this.checkes = checkes;
    }
}
