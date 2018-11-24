package com.ford.wechat.service.azure;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Neel on 2017/5/17.
 */
public interface AzureBlobService {

    void blobDown(String containerName, String localFilePath, String blobPath, String fileName);

    String blobUpload(InputStream inputStream, String fileName, Long size);

    void blobUpload(String containerName, File sourceFile, String blobPath);

    void blobUpload(String containerName, String localFilePath, String blobPath, String fileName);

    String blobUpload(byte[] bytes, String blobPath, String fileName);

}
