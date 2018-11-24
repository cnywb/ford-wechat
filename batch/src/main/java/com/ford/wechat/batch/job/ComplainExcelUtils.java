/*
 * Copyright (c) dabing.io
 * All rights reserved.
 * LybOrderReportUtils.java
 */

package com.ford.wechat.batch.job;

import com.ford.wechat.entity.pc.complain.ComplainEntity;
import com.google.common.collect.Lists;
import io.dabing.common.date.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 描述: 退单统一报表工具类
 *
 * @author ziv
 * @since 1.0
 */
public class ComplainExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ComplainExcelUtils.class);

    public static Workbook replenishData(String fileTemplatePath, List<ComplainEntity> sourceData) {

        List<List<ExcelCellVal>> rowDataList = Lists.newLinkedList();
        for (ComplainEntity complainEntity : sourceData) {
            String complainDate = DateUtil.formatDate(complainEntity.getCreatedDate(), DateUtil.FORMAT_DATETIME_YYYY_MM_DD_HH_MM_SS_SSS);
            List<ExcelCellVal> cellValList = Lists.newLinkedList();
            ExcelCellVal cellVal1 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "投诉工单");
            cellValList.add(cellVal1);//类别(售后咨询/投诉工单）
            ExcelCellVal cellVal2 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCustomerMobile());
            cellValList.add(cellVal2);//联系电话
            ExcelCellVal cellVal3 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainDate);
            cellValList.add(cellVal3);//投诉时间
            ExcelCellVal cellVal4 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "微客服反馈时间：" + complainDate + " 反馈内容：" + complainEntity.getIncidentDesc());
            cellValList.add(cellVal4);//投诉问题
            ExcelCellVal cellVal5 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getComplainReason());
            cellValList.add(cellVal5);//投诉类别
            ExcelCellVal cellVal6 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "");
            cellValList.add(cellVal6);//性别
            ExcelCellVal cellVal7 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCustomerName());
            cellValList.add(cellVal7);//车主姓名
            ExcelCellVal cellVal8 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCustomerName());
            cellValList.add(cellVal8);//客户姓名
            ExcelCellVal cellVal9 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCarModel());
            cellValList.add(cellVal9);//车型
            ExcelCellVal cellVal10 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "");
            cellValList.add(cellVal10);//车系
            Long exerciseMileage = complainEntity.getExerciseMileage();
            ExcelCellVal cellVal11 = new ExcelCellVal(ExcelCellVal.TYPE_DOUBLE, exerciseMileage != null ? exerciseMileage.doubleValue() : 0);
            cellValList.add(cellVal11);//行驶里程
            ExcelCellVal cellVal12 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "");
            cellValList.add(cellVal12);//购买日期
            ExcelCellVal cellVal13 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getLicense());
            cellValList.add(cellVal13);//车牌
            ExcelCellVal cellVal14 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCustomerVin());
            cellValList.add(cellVal14);//VIN
            ExcelCellVal cellVal15 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "");
            cellValList.add(cellVal15);//卖车经销商
            ExcelCellVal cellVal16 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getInvolveDealer());
            cellValList.add(cellVal16);//投诉经销商
            ExcelCellVal cellVal17 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCustomerMobile());
            cellValList.add(cellVal17);//车主联系座机
            ExcelCellVal cellVal18 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, complainEntity.getCustomerMobile());
            cellValList.add(cellVal18);//手机
            ExcelCellVal cellVal19 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "");
            cellValList.add(cellVal19);//邮编
            ExcelCellVal cellVal20 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "");
            cellValList.add(cellVal20);//联系地址
            ExcelCellVal cellVal21 = new ExcelCellVal(ExcelCellVal.TYPE_STRING, "Y");
            cellValList.add(cellVal21);//是否新增
            rowDataList.add(cellValList);
        }
        try {
//            FileInputStream fs = new FileInputStream("/Users/ziv/IdeaProjects/private/ford/ford-wechat/batch/src/main/resources/微客服客诉反馈表单.xlsx");  //获取d://test.xlsx
            FileInputStream fs = new FileInputStream(fileTemplatePath);  //获取d://test.xlsx
            XSSFWorkbook wb = new XSSFWorkbook(fs);
            XSSFSheet sheet = wb.getSheetAt(0);  //获取到工作表，因为一个excel可能有多个工作表
            int endRowNum = 4;
            for (int i = 0; i < rowDataList.size(); i++) {
                List<ExcelCellVal> excelCellValList = rowDataList.get(i);
                Row row = sheet.createRow((short) (endRowNum + i)); //在现有行号后追加数据
                for (int cellNum = 0; cellNum < excelCellValList.size(); cellNum++) {
                    ExcelCellVal cellVal = excelCellValList.get(cellNum);
                    Cell cell = row.createCell(cellNum);
                    if (cellVal == null) {
                        continue;
                    }
                    if ("double".equalsIgnoreCase(cellVal.getDataType())) {
                        if (cellVal.getData() != null) {
                            Double vale = Double.parseDouble(cellVal.getData().toString());
                            cell.setCellValue(vale);
                        } else {
                            cell.setCellValue("");
                        }
                    } else if ("Date".equalsIgnoreCase(cellVal.getDataType())) {
                        cell.setCellValue(StringUtils.isEmpty(cellVal.getData()) ? null : DateUtil.parser(cellVal.getData().toString(), DateUtil.FORMAT_DATE_DEFAULT));
                    } else {
                        cell.setCellValue(StringUtils.isEmpty(cellVal.getData()) ? null : cellVal.getData().toString());
                    }
                }
            }
            return wb;
        } catch (Exception e) {
            logger.error("文件流生成异常！", e.getMessage());
        }
        return null;
    }
}