package com.company.project.infrastructure.tools;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;

/**
 * @author lj
 * @Description 处理 properties 文件
 */
public class ExcelUtil {

    /**
     * @param titileName 标题集合
     * @param value      内容
     * @param fileName   文件名
     * @param response
     */
    public static void exportExcel(String[] titileName, LinkedList<LinkedList<String>> value, String fileName, HttpServletResponse response) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(30);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(45);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont headerFont1 = wb.createFont();
        headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
        headerFont1.setFontName("Times New Roman");
        headerFont1.setFontHeightInPoints((short) 14);
        style.setFont(headerFont1);

        HSSFCell cell = null;
        for (int i = 0; i < titileName.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titileName[i]);
            cell.setCellStyle(style);
        }

        HSSFCellStyle rowStyle = wb.createCellStyle();
        rowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        rowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont fontStyle = wb.createFont();
        fontStyle.setFontName("Times New Roman");
        fontStyle.setFontHeightInPoints((short) 12);
        rowStyle.setFont(fontStyle);

        for (int i = 0; i < value.size(); i++) {
            LinkedList<String> list = value.get(i);
            row = sheet.createRow(i + 1);
            row.setHeightInPoints(30);
            for (int j = 0; j < list.size(); j++) {
                HSSFCell ce0 = row.createCell(j);
                ce0.setCellValue(list.get(j));
                ce0.setCellStyle(rowStyle);
            }
        }
        export(fileName, wb, response);
    }

    /**
     * @param titileName        标题集合
     * @param value             内容
     * @param fileName          文件名
     * @param response
     * @param columnWidth       每列宽度
     * @param rowHeightInPoints 全局每行的高度
     * @param heightInPoints
     * @param style             cell样式
     * @param wb
     * @param rowStyle          每行的样式
     */
    public static void exportExcelWithStyle(String[] titileName, LinkedList<LinkedList<String>> value,
                                            String fileName, HttpServletResponse response,
                                            Integer columnWidth, Integer rowHeightInPoints,
                                            Integer heightInPoints, HSSFCellStyle style, HSSFWorkbook wb, HSSFCellStyle rowStyle) {
        // 第一步，创建一个webbook，对应一个Excel文件
        if (wb == null) {
            wb = new HSSFWorkbook();
        }
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.setDefaultColumnWidth(columnWidth);
        sheet.setDefaultRowHeightInPoints(rowHeightInPoints);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        row.setHeightInPoints(heightInPoints);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        if (style == null) {
            style = wb.createCellStyle();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont headerFont1 = wb.createFont();
            headerFont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 字体加粗
            headerFont1.setFontName("Times New Roman");
            headerFont1.setFontHeightInPoints((short) 14);
            style.setFont(headerFont1);
        }
        HSSFCell cell = null;
        for (int i = 0; i < titileName.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titileName[i]);
            cell.setCellStyle(style);
        }

        if (rowStyle != null) {
            rowStyle = wb.createCellStyle();
            rowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            rowStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont fontStyle = wb.createFont();
            fontStyle.setFontName("Times New Roman");
            fontStyle.setFontHeightInPoints((short) 12);
            rowStyle.setFont(fontStyle);
        }

        for (int i = 0; i < value.size(); i++) {
            LinkedList<String> list = value.get(i);
            row = sheet.createRow(i + 1);
            row.setHeightInPoints(30);
            for (int j = 0; j < list.size(); j++) {
                HSSFCell ce0 = row.createCell(j);
                ce0.setCellValue(list.get(j));
                ce0.setCellStyle(rowStyle);
            }
        }
        export(fileName, wb, response);
    }

    public static void export(String fileName, HSSFWorkbook wb, HttpServletResponse response) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            out.flush();
            out.close();
            byte[] bytes = out.toByteArray();
            response.setContentType("application/x-msdownload");
            String name = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
