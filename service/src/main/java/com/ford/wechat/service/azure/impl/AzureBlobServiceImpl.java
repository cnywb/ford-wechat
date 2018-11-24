package com.ford.wechat.service.azure.impl;

import com.ford.wechat.service.azure.AzureBlobService;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.text.MessageFormat;

/**
 * Created by Neel on 16/12/14.
 */
@Service
public class AzureBlobServiceImpl implements InitializingBean, AzureBlobService {


    /***日志*/
    private static final Logger logger = LoggerFactory.getLogger(AzureBlobServiceImpl.class);


    public static final String STORAGE_CONNECTION_STRING = "DefaultEndpointsProtocol=http;AccountName={0};AccountKey={1};EndpointSuffix=core.chinacloudapi.cn";

    /***
     * Blob Client
     */
    private static CloudBlobClient serviceClient;

    private static String azureBlobUrl;

    private static String azureContainerName;

    /***
     * 账号
     */
    @Value("${azure.blob.account.name}")
    private String accountName;

    /**
     * 账号的KEY
     */
    @Value("${azure.blob.account.key}")
    private String accountKey;


    /**
     * Blob Url
     */
    @Value("${azure.blob.url}")
    private String blobUrl;


    /**
     * Blob Url
     */
    @Value("${azure.blob.container.name}")
    private String containerName;

    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     * <p>This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     *
     * @throws Exception in the event of misconfiguration (such
     *                   as failure to set an essential property) or if initialization fails.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        String connection = MessageFormat.format(STORAGE_CONNECTION_STRING, accountName, accountKey);
        CloudStorageAccount account = CloudStorageAccount.parse(connection);
        serviceClient = account.createCloudBlobClient();
        azureBlobUrl = this.blobUrl;
        logger.info("####################azureBlobUrl:{}##########",azureBlobUrl);
        azureContainerName = this.containerName;
    }

    /***
     * 下载文件并保存;
     * @param containerName
     * @param localFilePath
     * @param blobPath
     */
    @Override
    public void blobDown(String containerName, String localFilePath, String blobPath, String fileName) {
        try {
            FileUtils.forceMkdir(new File(localFilePath + blobPath));
            CloudBlobContainer container = serviceClient.getContainerReference(containerName);
            CloudBlockBlob downBlob = container.getBlockBlobReference(blobPath + fileName);
            downBlob.download(new FileOutputStream(localFilePath + downBlob.getName()));
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public String blobUpload(InputStream inputStream, String fileName, Long size) {
        try {
            CloudBlobContainer container = serviceClient.getContainerReference(azureContainerName);
            CloudBlockBlob blob = container.getBlockBlobReference(fileName);
            blob.upload(inputStream, size);
            return azureBlobUrl + fileName;
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;

    }

    /***
     *
     * @param sourceFile 文件名
     * @param blobPath Azure Blob存储路径
     */
    @Override
    public void blobUpload(String containerName, File sourceFile, String blobPath) {
        try {
            CloudBlobContainer container = serviceClient.getContainerReference(containerName);
            container.createIfNotExists();
            String blobReference = blobPath + sourceFile.getName();
            CloudBlockBlob blob = container.getBlockBlobReference(blobReference);
            blob.upload(new FileInputStream(sourceFile), sourceFile.length());
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @param containerName
     * @param localFilePath 本地存储路径
     * @param blobPath      Blob存储路径
     * @param fileName      文件名
     */
    @Override
    public void blobUpload(String containerName, String localFilePath, String blobPath, String fileName) {
        try {
            CloudBlobContainer container = serviceClient.getContainerReference(containerName);
            container.createIfNotExists();
            CloudBlockBlob blob = container.getBlockBlobReference(blobPath + fileName);
            File sourceFile = new File(localFilePath + fileName);
            blob.upload(new FileInputStream(sourceFile), sourceFile.length());
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public String blobUpload(byte[] bytes, String blobPath, String fileName) {
        try {
            if (!blobPath.endsWith("/")) {
                blobPath = blobPath + "/";
            }

            CloudBlobContainer container = serviceClient.getContainerReference(azureContainerName);
            container.createIfNotExists();
            CloudBlockBlob blob = container.getBlockBlobReference(blobPath + fileName);
            blob.uploadFromByteArray(bytes, 0, bytes.length);
            logger.info(blob.getUri().toString());
            return azureBlobUrl + blobPath + fileName;
        } catch (StorageException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage(), e);
        }
        return StringUtils.EMPTY;

    }

    public static void main(String[] args) {
        try {
            String connection = MessageFormat.format(STORAGE_CONNECTION_STRING, "cafcreporting", "yr1P/ZAqLMD+TB5lrBHefoFlB+7FW5liBun5b6Jt9iTxHexs+VTdKpw7Sk97sscwopQW5STGq8mhi5F4Mxr0Lw==");
            CloudStorageAccount account = CloudStorageAccount.parse(connection);
            serviceClient = account.createCloudBlobClient();
            CloudBlobContainer container = serviceClient.getContainerReference("callcenter");
            container.createIfNotExists();
            CloudBlockBlob blob = container.getBlockBlobReference("/chezhurenzheng/" + "20170625151138_9ee05e3b9750efa66080849bab7a2325.jpg");
            blob.downloadBlockList();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
