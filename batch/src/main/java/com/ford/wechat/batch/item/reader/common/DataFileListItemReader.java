package com.ford.wechat.batch.item.reader.common;

import com.ford.wechat.batch.model.DataFileVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @param <T>
 */
public class DataFileListItemReader<T> extends AbstractItemStreamItemReader<T> implements InitializingBean {

    /**日志*/
    private static final Logger logger= LoggerFactory.getLogger(DataFileListItemReader.class);



    private AtomicInteger counter = new AtomicInteger(0);

    private List<File> files=null;

    /**文件路径*/
    private Resource resource;
    /**批次号*/
    private String batchNo;



    public DataFileListItemReader() {

    }


    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }



    public String getBatchNo() {
        return batchNo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.resource, "The Resource must not be null.");
        Assert.notNull(this.batchNo,  "The BatchNo  must not be null.");
    }



    @Override
    public T read() throws Exception {
        int index = this.counter.incrementAndGet() - 1;
        File  file=index >= this.files.size()?null:this.files.get(index);
        DataFileVo dataFile=null;

        if (file == null) {
            return null;
        }


        Path path= Paths.get(file.getAbsolutePath());

        BasicFileAttributes attributes= Files.readAttributes(path,BasicFileAttributes.class);
        dataFile=new DataFileVo();
        dataFile.setBatchNo(batchNo);

        dataFile.setFileName(file.getName());
        dataFile.setAbsolutePath(file.getAbsolutePath());
        dataFile.setCreationTime(new Date(attributes.creationTime().toMillis()));
        dataFile.setLastAccessTime(new Date(attributes.lastAccessTime().toMillis()));
        dataFile.setLastModifiedTime(new Date(attributes.lastModifiedTime().toMillis()));
        dataFile.setSize(attributes.size());

        Integer total=0;
        dataFile.setTotal(Long.valueOf (total));//设置每个文件数据总条数,此属性应该由报文中提供,但是目前只能解析数据得到,此方案有待改进
        return (T)dataFile;

    }




    public void open(ExecutionContext executionContext) throws ItemStreamException {


        super.open(executionContext);
       if(executionContext.containsKey("COUNT")) {
           this.counter.set(executionContext.getInt(this.getExecutionContextKey("COUNT"), 0));
       }else{
           this.counter.set(0);
           executionContext.put("COUNT",0);
       }
        this.doOpen();
    }

    protected   void doOpen() {
        try {
            logger.info("读取文件：{}",this.resource.getFile().getName());
            Collection<File> files1 = FileUtils.listFiles(this.resource.getFile(), new SuffixFileFilter(".xml"), null);
            this.files=new ArrayList<>(files1);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        super.update(executionContext);
        executionContext.putInt("COUNT", this.counter.get());
    }



}
