package com.ford.wechat.controller.batch;

import com.ford.wechat.controller.batch.vo.BatchTaskGetReq;
import com.ford.wechat.controller.batch.vo.BatchTaskHandleReq;
import com.ford.wechat.controller.batch.vo.BatchTaskPageResp;
import com.ford.wechat.entity.batch.BatchTask;
import com.ford.wechat.service.batch.BatchTaskService;
import com.ford.wechat.service.support.WeTableSequenceService;
import io.dabing.common.util.DateUtils;
import io.dabing.common.util.NumberUtil;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * Created by zhaoliang on 2017/5/27.
 */
@Controller
public class BatchTaskController {

    @Autowired
    private BatchTaskService service;

    @Autowired
    private WeTableSequenceService tableSequenceService;

    /**
     * 按关键字分页查询对象 调用redirectConfigPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "batchTaskPage")
    public Page<BatchTaskPageResp> page(BatchTaskGetReq req) {
        Page<BatchTask> pages = service.pagingBy(req.getBatchNo(),req.getRunTimes(),req.getStatus(),req.getCreateStartDate(),req.getCreateEndDate(),req.getPage());
                //service.pagingBy (req.getPage ());
        Page<BatchTaskPageResp> respS = pages.map (new Converter<BatchTask, BatchTaskPageResp>() {
            public BatchTaskPageResp convert(BatchTask source) {
                BatchTaskPageResp resp = new BatchTaskPageResp();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                    resp.setDataTypeName(source.getBatchType().getName());
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用redirectConfigHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "batchTaskHandle")
    public String handle(BatchTaskHandleReq req) {

        BatchTask entity = new BatchTask();
        Date date = new Date();
        if (req.getId() != null) {
            entity = service.get(req.getId());
            entity.setStatus(BatchTask.STATUS_INIT);
            entity.setStartDate(null);
            entity.setEndDate(null);
            entity.setExecuteStatus(null);
            entity.setBatchResult(null);
            entity.setResetParameter(DateUtils.format(date, DateUtils.FORMAT_DATE_TIME_DEFAULT));
            service.update(entity);
        } else {
            BeanUtils.copyProperties(req, entity);
            Long num = this.tableSequenceService.doIncrementSeqValue(new Date(),BatchTask.class.getName());
            String batchNo = NumberUtil.getBatchNo(num);
            entity.setBatchNo(batchNo);
            entity.setStatus(BatchTask.STATUS_INIT);
            service.save(entity);
        }
        return "";
    }

}
