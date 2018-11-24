package com.ford.wechat.controller.question;

import com.ford.wechat.controller.question.vo.*;
import com.ford.wechat.entity.question.Question;
import com.ford.wechat.service.excel.ExcelService;
import com.ford.wechat.service.question.QuestionService;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.web.annotation.ApiService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


/**
 * Created by wanglijun on 2016-11-02.
 * 描述:TODO
 *
 * @since 1.0
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService service;

    @Autowired
    private ExcelService excelService;
    /**
     * 按关键字分页查询对象 调用questionPage
     *
     * @param req 请求入参对象(包含了keyWord关键字)
     * @return
     */
    @ApiService(transCode = "questionPage")
    public Page<QuestionPageResp> questionPage(QuestionPageReq req) {
        Page<Question> pages = service.pagingBy (req.getOpenId(),req.getType(),req.getTitle(),req.getStatus(),req.getAnswerStartDate(),req.getAnswerEndDate(),req.getCreateStartDate(),req.getCreateEndDate(),req.getPage ());
        Page<QuestionPageResp> respS = pages.map (new Converter<Question, QuestionPageResp> () {
            public QuestionPageResp convert(Question source) {
                QuestionPageResp resp = new QuestionPageResp ();
                if (source != null) {
                    BeanUtils.copyProperties (source, resp);
                }
                return resp;
            }
        });
        return respS;
    }

    /**
     * 创建/修改对象处理 调用questionHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "questionHandle")
    public String questionHandle(QuestionHandleReq req) {
        Question entity = new Question ();
        if (req.getId () != null) {
            entity = service.get (req.getId ());
        }
        BeanUtils.copyProperties (req, entity);
        if (req.getId () != null) {
            service.update (entity);
        } else {
            service.save (entity);
        }
        return "";
    }

    /**
     * 删除对象处理,批量,单一删除均支持 调用questionRemove
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "questionRemove")
    public void questionRemove(QuestionRemoveVo req) {
        service.delete (req.getId ());
    }

    /**
     * 创建/修改对象处理 调用questionHandle
     *
     * @param req 请求入参对象
     * @return
     */
    @ApiService(transCode = "questionAnswer")
    public String questionAnswer(QuestionAnswerReq req) {
        Question entity = this.service.get(req.getId());
        entity.setAnswerContent(req.getAnswerContent());
        entity.setAnswerDate(new Date());
        entity.setStatus(2);
        this.service.update(entity);
        return "";
    }


    /**
     *服务号问题列表导出
     */
    @RequestMapping(value = "issueListExport.ctl")
    public void resultExport(QuestionPageReq req, HttpServletRequest request, HttpServletResponse response) {
        GridPage page = new GridPage();
        page.setPageNumber(1);
        page.setPageSize(10000);
        Page<Question> pages = service.pagingBy (req.getOpenId(),req.getType(),req.getTitle(),req.getStatus(),req.getAnswerStartDate(),req.getAnswerEndDate(),req.getCreateStartDate(),req.getCreateEndDate(),page);
        Page<QuestionPageResp> respS = pages.map(new Converter<Question, QuestionPageResp>() {
            public QuestionPageResp convert(Question source) {
                QuestionPageResp resp = new QuestionPageResp();
                if (source != null) {
                    BeanUtils.copyProperties(source, resp);

                }
                return resp;
            }
        });
        excelService.lionExportData(respS.getContent(),"问题列表","issueListExport", request, response);
    }



}