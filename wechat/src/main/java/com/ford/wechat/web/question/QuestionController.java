package com.ford.wechat.web.question;

import com.ford.wechat.entity.question.Question;
import com.ford.wechat.service.question.QuestionService;
import com.ford.wechat.web.members.MembersController;
import io.dabing.common.grid.GridPage;
import io.dabing.core.repository.domain.Page;
import io.dabing.core.repository.domain.PageRequest;
import io.dabing.core.repository.domain.Pageable;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wanglijun on 16/11/16.
 */
@Controller
@RequestMapping("/fordwechat")
public class QuestionController {
    /***
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService service;

    @ResponseBody
    @RequestMapping("/getQuestionTitles.do")
    public Page<Question> getList(GridPage page) {
        return service.pagingWithOmitTitle(page);
    }

    @ResponseBody
    @RequestMapping("/getQuestionById.do")
    public Question getQuestionById(Long qid) {
        logger.info("qid{}", qid);
        return service.get(qid);
    }

    @ResponseBody
    @RequestMapping("/saveQuestion.do")
    public String save(String qTitle, String qContent, String questionType, String openid) {
//        logger.info("qTitle{}",qTitle);
//        logger.info("qContent{}",qContent);
//        logger.info("questionType{}",questionType);
        logger.info("openid{}", qTitle);
        Question q = new Question();
        q.setOpenId(openid);
        q.setTitle(qTitle);
        q.setCreatedDate(new Date());
        q.setContent(qContent);
        q.setType(questionType);
        q.setStatus(1);
        service.save(q);
        return "1";
    }


//    @ResponseBody
//    @RequestMapping("/testQuestionTitles.do")
//    public Page<Question> testList(GridPage page){
//        logger.info ("PageNumber:{}",page.getPageNumber());
//        logger.info ("PageSize:{}",page.getPageSize());
//        logger.info ("KeyWord:{}",page.getKeyWord());
//        List<Question> list = new ArrayList<Question>();
//        Question q = new Question();
//        q.setTitle("test");
//        q.setContent("10");
//        q.setAnswerContent("20");
//        q.setCreatedDate(new Date());
//        q.setType("normal1");
//        q.setId(1L);
//        Question q2 = new Question();
//        q2.setTitle("test2");
//        q2.setContent("12");
//        q2.setAnswerContent("22");
//        q2.setCreatedDate(new Date());
//        q2.setType("normal2");
//        q2.setId(2L);
//        Question q3 = new Question();
//        q3.setTitle("test3");
//        q3.setContent("13");
//        q3.setAnswerContent("23");
//        q3.setCreatedDate(new Date());
//        q3.setType("normal3");
//        q3.setId(3L);
//        if(page.getPageNumber()==1) {
//            list.add(q);
//            list.add(q2);
//        }
//        if(page.getPageNumber()==2);
//            list.add(q3);
//        Pageable pb = new PageRequest(page.getPageNumber(),page.getPageSize());
//        Page<Question> pg = new Page<Question>(list,pb,0);
//        return pg;
//    }

    @RequestMapping("/questionindex.do")
    public ModelAndView index(HttpServletResponse response) {
        //setCookie("openid","oldb_11111",response);
        return new ModelAndView("templates/question");
    }
//
//
//    private void setCookie(String name, String value, HttpServletResponse response) {
//        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
//            Cookie co = new Cookie(name, value);
//            co.setPath("/");
//            co.setMaxAge(-1);
//            response.addCookie(co);
//        }
//    }
}
