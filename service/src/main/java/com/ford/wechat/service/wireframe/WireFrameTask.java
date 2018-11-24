package com.ford.wechat.service.wireframe;

import com.ford.wechat.service.azure.AzureBlobService;
import io.dabing.common.date.DateUtil;
import io.dabing.redis.client.RedisClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by lc on 2017/9/20.
 * 已移植至批处理任务项目
 */
@Component
@Configurable
//@EnableScheduling
public class WireFrameTask {

    private static final Logger logger = LoggerFactory.getLogger(WireFrameTask.class);

    private static  final String FILE_ORDER ="WX-DMS-ORDER";

    private static  final String FILE_CANCLE ="DMS-CANCEL";

    private static  final String FILE_PERSON ="DMS-PERSON";

    private static  final String REDIS_KEY= "WX_DMS";


    @Autowired
    private RedisClient redisClient;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AzureBlobService azureBlobService;


    //@Scheduled(cron = "0 0 1 * * ?")
    public void executeSendOrder(){

        Date[] distance = getTimeDistance();
        Date startDate = distance[0];
        Date endDate = distance[1];
        //appointmentService.getYesterDayOrder(startDate,endDate);
    }


    //@Scheduled(cron = "0 0 1 * * ?")
    public void executeSendOrderCancle(){

    }

   // @Scheduled(cron = "0 0 1 * * ?")
    public void executeSendPerson(){

    }

    /*生成文件名称*/
    private String geranteFileName(String fileType, Date date, String fileSeq){
        StringBuffer fileName = new StringBuffer(fileType);
        String fileDate = DateUtil.formatDate(date,DateUtil.FORMAT_DATE_YYYYMMDD);
        fileName.append(fileDate);
        fileName.append(fileSeq);
        return  fileName.toString();
    }

    /*生成文件头*/
    private String geranteFileHeader(String sign,String source,Date date,String fileSeq,String fileReocrdSize){
        StringBuffer sb = new StringBuffer(sign);
        sb.append("|");
        sb.append(source);
        sb.append("|");
        sb.append(DateUtil.formatDate(date,DateUtil.FORMAT_DATE_YYYYMMDD));
        sb.append("|");
        sb.append(DateUtil.formatDate(date,DateUtil.FORMAT_TIME_HHMMSS));
        sb.append("|");
        sb.append(fileSeq);
        sb.append("|");
        sb.append(fileReocrdSize);
        return  sb.toString();
    }


    /*生成文件序列号*/
    private Long getSeq(){
        String redisValue = redisClient.get(REDIS_KEY);
        if (StringUtils.isEmpty(redisValue)) {
            synchronized (this) {
                if (StringUtils.isEmpty(redisValue)){
                    redisClient.set(REDIS_KEY, "0");
                }
            }
        }
        Long incr = redisClient.incr(REDIS_KEY);
        return  incr;
    }

    /*计算今天00:00到24:00的时间段*/
    private Date[] getTimeDistance() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        int hour = 0;

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 1);
        Date start = calendar.getTime();
        hour = 23;
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date end = calendar.getTime();
        Date[] date = {start,end};
        return date;
    }
}
