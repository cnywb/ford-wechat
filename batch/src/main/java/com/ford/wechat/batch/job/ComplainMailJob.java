/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * ComplainMailJob.java
 */

package com.ford.wechat.batch.job;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.ford.wechat.service.pc.complain.ComplainEntityService;
import io.dabing.common.util.DateUtils;
import io.dabing.redis.client.BinaryRedisClient;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

/**
 * 描述:TODO
 *
 * @author ziv
 * @since 1.0
 */
@Configuration
public class ComplainMailJob {


    @Resource(name = "javaMailCrcSender")
    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(ComplainMailJob.class);
    /**
     * 发件人地址
     */
    @Value(value = "${mail.crc.from}")
    private String fromMail;
    /**
     * 投诉建议模板
     */
    @Value(value = "${complain.file.template.path}")
    private String fileTemplatePath;

    @Autowired
    private JobRepository jobRepository;

    @Resource(name = "jpaTransactionManager")
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ComplainEntityService complainEntityService;

    @Autowired
    private BinaryRedisClient binaryRedisClient;

    /**
     * 这里的方法名就是外部引用改bean的名称
     *
     * @return
     */
    @Bean
    public Job complainMailBatchJob() {
        return new JobBuilder("complainMailBatchJob").repository(jobRepository).start(step1(new Tasklet() {
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                List<ComplainEntity> sourceData = complainEntityService.findBySendMail(ComplainEntity.SEND_MAIL_SENDING);
                if (CollectionUtils.isEmpty(sourceData) || sourceData.size() < 1) {
                    logger.info("发送[投诉建议]执行结束，本次不发送，投诉建议条数为0条");
                    return RepeatStatus.FINISHED;
                }
                String toMail = binaryRedisClient.get("FC:PC:COMPLAIN:TOMAIL", String.class);
                String ccMail = binaryRedisClient.get("FC:PC:COMPLAIN:CCMAIL", String.class);
                logger.info("发送[投诉建议]执行开始:fromMail:{},toMail:{}", new Object[]{fromMail, toMail});
                String mailSubject = "微客服投诉清单+投诉+" + DateUtils.format(new Date(), DateUtils.FORMAT_DATE_TIME_YYYYMMDDHHMM) + "+" + sourceData.size();
                String mailContent = subContent(sourceData);
                Workbook workbook = ComplainExcelUtils.replenishData(fileTemplatePath, sourceData);
                if (workbook == null) {
                    return RepeatStatus.CONTINUABLE;
                }
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                InputStreamSource inputStreamSource = new ByteArrayResource(outputStream.toByteArray());
                //创建多元化邮件
                MimeMessage mimeMessage = ((JavaMailSenderImpl) javaMailSender).createMimeMessage();
                //创建 mimeMessage 帮助类，用于封装信息至 mimeMessage
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "GBK");
                //基本信息
                //发送者地址，必须填写正确的邮件格式，否者会发送失败
                helper.setFrom(fromMail);
                //邮件主题
                helper.setSubject(mailSubject);
                //邮件内容，简单的邮件信息只能添加文本信息
                helper.setText(mailContent, true);
                //邮件接收者的邮箱地址
                String toMainMail[] = toMail.split(";");
                helper.setTo(toMainMail);
                if (ccMail != null || ccMail.indexOf(";") > -1) {
                    String ccMainMail[] = ccMail.split(";");
                    helper.setCc(ccMainMail);
                }
                //添加附件
                helper.addAttachment(MimeUtility.encodeWord(mailSubject + ".xlsx"), inputStreamSource);
                javaMailSender.send(mimeMessage);
                logger.info("发送[投诉建议]执行结束:fromMail:{},toMail:{}", new Object[]{fromMail, toMail});
                return RepeatStatus.FINISHED;
            }
        })).next(step2(new Tasklet() {
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                complainEntityService.updateSendMailBySendMail(ComplainEntity.SEND_MAIL_SENDED, ComplainEntity.SEND_MAIL_SENDING);
                logger.info("发送[投诉建议]更新发送状态以及时间结束");
                return RepeatStatus.FINISHED;
            }
        })).build();
    }

    private Step step1(Tasklet tasklet) {
        return new StepBuilder("step1").repository(jobRepository).transactionManager(
                transactionManager)
                .tasklet(tasklet)
                .build();
    }

    private Step step2(Tasklet tasklet) {
        return new StepBuilder("step2").repository(jobRepository).transactionManager(
                transactionManager)
                .tasklet(tasklet)
                .build();
    }

    private String subContent(List<ComplainEntity> sourceData) {
        StringBuffer stringBuffer = new StringBuffer();
        //投诉人：杨涛  投诉日期：2017-09-11  VIN: LVSHJHACX2F123456 投诉经销商：重庆安福 投诉问题：天窗漏水
        for (ComplainEntity entity : sourceData) {
            stringBuffer.append("<br>投诉人：").append(entity.getCustomerName())
                    .append(" 投诉日期：").append(DateUtils.format(entity.getCreatedDate(), DateUtils.FORMAT_DATE_YYYY_MM_DD))
                    .append(" VIN：").append(entity.getCustomerVin())
                    .append(" 投诉经销商：").append(entity.getInvolveDealer())
                    .append(" 投诉问题：").append(entity.getIncidentDesc());
        }
        return stringBuffer.toString();
    }
}