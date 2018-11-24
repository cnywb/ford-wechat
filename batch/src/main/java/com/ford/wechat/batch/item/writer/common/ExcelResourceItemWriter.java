/**
 *
 */
package com.ford.wechat.batch.item.writer.common;

import com.ford.wechat.service.excel.ExcelService;
import io.dabing.common.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * 增加了自动创建文件目录的能力
 *
 * @param <T>
 */
public class ExcelResourceItemWriter<T> extends AbstractItemStreamItemWriter<T> {
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ExcelResourceItemWriter.class);

    final static private String RESOURCE_INDEX_KEY = "resource.index";

    final static private String CURRENT_RESOURCE_ITEM_COUNT = "resource.item.count";

    private Resource resource;

    private ResourceAwareItemWriterItemStream<? super T> delegate;

    private int itemCountLimitPerResource = Integer.MAX_VALUE;

    private int currentResourceItemCount = 0;

    private int resourceIndex = 1;

    private boolean saveState = true;


    private boolean opened = false;

    private String bussinessCode;

    private String path;

    @Autowired
    private ExcelService excelService;

    public ExcelResourceItemWriter() {
        this.setExecutionContextName(ClassUtils.getShortName(MultiResourceItemWriter.class));
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        logger.info("文件写数据开始......");
        excelService.lionExportData(items,bussinessCode,path);
        logger.info("文件写数据结束......");
    }

    /**
     * After this limit is exceeded the next chunk will be written into newly
     * created resource.
     */
    public void setItemCountLimitPerResource(int itemCountLimitPerResource) {
        this.itemCountLimitPerResource = itemCountLimitPerResource;
    }

    /**
     * Delegate used for actual writing of the output.
     */
    public void setDelegate(ResourceAwareItemWriterItemStream<? super T> delegate) {
        this.delegate = delegate;
    }

    /**
     * Prototype for output resources. Actual output files will be created in
     * the same directory and use the same name as this prototype with appended
     * suffix (according to
     */
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setSaveState(boolean saveState) {
        this.saveState = saveState;
    }


    @Override
    public void close() throws ItemStreamException {
        super.close();
        resourceIndex = 1;
        currentResourceItemCount = 0;
        if (opened) {
            delegate.close();
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        super.open(executionContext);
        resourceIndex = executionContext.getInt(getExecutionContextKey(RESOURCE_INDEX_KEY), 1);
        currentResourceItemCount = executionContext.getInt(getExecutionContextKey(CURRENT_RESOURCE_ITEM_COUNT), 0);

        if (executionContext.containsKey(getExecutionContextKey(CURRENT_RESOURCE_ITEM_COUNT))) {
            // It's a restart
            delegate.open(executionContext);
        } else {
            opened = false;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        super.update(executionContext);
        if (saveState) {
            if (opened) {
                delegate.update(executionContext);
            }
            executionContext.putInt(getExecutionContextKey(CURRENT_RESOURCE_ITEM_COUNT), currentResourceItemCount);
            executionContext.putInt(getExecutionContextKey(RESOURCE_INDEX_KEY), resourceIndex);
        }
    }



    public String getBussinessCode() {
        return bussinessCode;
    }

    public void setBussinessCode(String bussinessCode) {
        this.bussinessCode = bussinessCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
