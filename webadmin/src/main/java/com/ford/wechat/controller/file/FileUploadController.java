/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * FileUploadControlle.java
 */

package com.ford.wechat.controller.file;

import com.ford.wechat.service.azure.AzureBlobService;
import com.ford.wechat.service.oss.OSSService;
import io.dabing.common.date.DateUtil;
import io.dabing.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * 描述: 文件统一上传 控制层代码
 *
 * @author ziv
 * @since 1.0
 */
@Controller
@RequestMapping("/resourceStore")
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private AzureBlobService azureBlobService;

    /**
     * 创建/修改对象处理
     */
    @RequestMapping("/resourceStoreHandle")
    public void resourceStoreHandle(HttpServletRequest request, HttpServletResponse response, MultipartFile file) {
        if (file != null) {
            String myFileName = file.getOriginalFilename();
            if (!myFileName.trim().equals("")) {
                /*response.setContentType("application/json");
                try {
                    if (file.getSize() / 1024 / 1024 > 1) {
                        response.getWriter().write("{\"status\":\"" + 500 + "\",\"errorMsg\":\"文件过大不能超过1M!\"}");
                        return;
                    }
                    String url = azureBlobService.blobUpload(file.getBytes(), myFileName);
                    response.getWriter().write("{\"url\":\"" + url + "\",\"fileName\":\"" + myFileName + "\"}");
                } catch (IOException e) {
                    logger.error("文件上传异常", e);
                }*/
                String fileSizeMsg = this.checkSize(file);
                try {
                    if (fileSizeMsg != null) {
                        response.getWriter().write("{\"status\":\"" + 500 + "\",\"errorMsg\":\"" + fileSizeMsg + "!\"}");
                    }
                    String url = this.uploadFile(file, "menu", myFileName);
                    response.getWriter().write("{\"url\":\"" + url + "\",\"fileName\":\"" + myFileName + "\"}");
                } catch (Exception e) {
                    logger.error("文件上传异常", e);
                }
            }
        }
    }

    /**
     * 创建/修改对象处理
     */
    @RequestMapping("/resourceStoreEditor")
    public void resourceStoreEditor(HttpServletRequest request, HttpServletResponse response) {
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件：
        Map<String, MultipartFile> multipartFileMap = multipartRequest.getFileMap();
        // 获得输入流：
        if (multipartFileMap.size() > 0) {
            MultipartFile multipartFile = multipartFileMap.get("wangEditorH5File");
            // 获得文件名：
            String filename = multipartFile.getOriginalFilename();
            if (!filename.trim().equals("")) {
                String fileSizeMsg = this.checkSize(multipartFile);
                try {
                    if (fileSizeMsg != null) {
                        response.getWriter().write("error|" + fileSizeMsg);
                    }
                    String url = this.uploadFile(multipartFile, "slt", filename);
                    response.getWriter().write(url);
                } catch (Exception e) {
                    logger.error("文件上传异常", e);
                }
            }
        }
    }

    private String checkSize(MultipartFile file) {
        if (file.getSize() / 1024 / 1024 > 1) {
            return "文件过大不能超过1M!";
        }
        return null;
    }

    private String uploadFile(MultipartFile file, String path, String fileName) throws BusinessException, IOException {
        if (file != null) {
            String url = azureBlobService.blobUpload(file.getBytes(), "pc/" + path, fileName);
            return url;
        }
        return "";
    }

}