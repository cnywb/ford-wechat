package com.ford.wechat.batch.tasklet.verify.day.mail;/**
 * Created by jovi on 27/03/2017.
 */

import com.ford.wechat.batch.constants.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016
 * All rights reserved. 2017-03-27 17:05
 * </p>
 *
 * @author MaoJiaWei
 * @version 1.0
 */
@Getter
@Setter
public class DayVerifyMailSendTasklet implements Tasklet {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;
    /**
     * 发送者
     */
    @Value("#{settings['day.auth.mail.send.from']}")
    private String fromMail;
    /**
     * 接受者
     */
    @Value("#{settings['day.auth.mail.send.to']}")
    private String toMail;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        try{
            String outPutFilePath = (String) chunkContext.getStepContext().getJobParameters().get(Parameter.OUTPUT_FILE_PATH);
            String dateNo = (String) chunkContext.getStepContext().getJobParameters().get(Parameter.DATE_NO);

            //创建多元化邮件
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //创建 mimeMessage 帮助类，用于封装信息至 mimeMessage
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            //基本信息
            //发送者地址，必须填写正确的邮件格式，否者会发送失败
            helper.setFrom(fromMail);
            //邮件主题
            helper.setSubject(dateNo+"认证数据");
            //邮件内容，简单的邮件信息只能添加文本信息
            helper.setText(dateNo+"认证数据",true);
            //发送地址
            String[] receivers = toMail.split(";");
            InternetAddress[] tos = new InternetAddress[receivers.length];
            for(int i = 0;i<receivers.length;i++){
                tos[i] = new InternetAddress(receivers[i]);
            }
            // 将所有接收者地址都添加到邮件接收者属性中
            helper.setTo(tos);
            //附件
            String path = outPutFilePath+"/"+dateNo;
            File directory = new File(path);
            if (directory.exists()) {
                if(directory.isDirectory()){
                    File[] files = directory.listFiles();
                    for (File file:files){
                        helper.addAttachment(file.getName(),file);
                    }
                }
            }
            javaMailSender.send(mimeMessage);

        }catch (Exception e){
            logger.error("邮件发送失败:{}",e.getMessage(),e);
        }
        return null;
    }
}
