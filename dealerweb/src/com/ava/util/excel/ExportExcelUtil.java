package com.ava.util.excel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ava.base.dao.util.ReflectUtils;
import com.ava.util.download.DownloadHelper;

public class ExportExcelUtil {
	private static Logger logger=Logger.getLogger(ExportExcelUtil.class);

	/**
	 * 发送excel信息到客户端
	 * @author liuq 
	 * @version 0.1 
	 * @param fileName
	 * @param excel
	 * @param response
	 * @param userAgent
	 */
	public static void sendFileToUser(String fileName,HSSFWorkbook excel,HttpServletResponse response, String userAgent){
		ServletOutputStream sos=null;
		try {
			String extendName = "xls";
			//加上后缀
			String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串
            String recommendedName = DownloadHelper.buildFileNameByAgent(fileName, extendName, userAgent);//设置文件名称的编码格式
            response.setContentType(contentType);//设置导出文件格式
            response.setHeader("Content-Disposition", "attachment; filename= " +  recommendedName);//
	           
            response.resetBuffer();
            
            sos = response.getOutputStream();
			excel.write(sos);
			sos.flush();
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally{
			if(null!=sos){
				try {
					sos.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
				sos=null;
			}
		}
	}
	
	public static HSSFRow toHSSFRow(Object obj, HSSFRow row, String[] propertyNames) {
		if(obj == null){
			return null;
		}
		if(propertyNames == null || propertyNames.length == 0){
			return null;
		}

		Map properties = new HashMap();
		for (int i=0; i<propertyNames.length; i++) {
			String fieldName = propertyNames[i];
			Object fieldValue = ReflectUtils.callGetMethod(fieldName, obj);
			String value = String.valueOf(fieldValue);
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(value);
		}
		return row;
	}


	public static HSSFRow toHSSFRow(Object[] objs, HSSFRow row, String[][] properties) {
		if(properties == null || properties.length == 0){
			return null;
		}
		String[] propertyNames = new String[properties.length];
		for (int i=0; i<properties.length; i++) {
			propertyNames[i] = properties[i][1];
		}
		
		return toHSSFRow(objs, row, propertyNames);
	}

	public static HSSFRow toHSSFRow(Object[] objs, HSSFRow row, String[] propertyNames) {
		if(objs == null || objs.length == 0){
			return null;
		}
		if(propertyNames == null || propertyNames.length == 0){
			return null;
		}

		Map properties = new HashMap();
		for (int i=0; i<propertyNames.length; i++) {
			String fieldName = propertyNames[i];
			Object fieldValue = null;
			for(int k=0; k<objs.length; k++){
				if(ReflectUtils.hasGetMethod(fieldName, objs[k])){
					fieldValue = ReflectUtils.callGetMethod(fieldName, objs[k]);
				}
			}
			String value = String.valueOf(fieldValue == null ? "" : fieldValue);
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(value);
		}
		return row;
	}
	
	/**
	 * 构造title行，返回HSSFSheet
	 * @param fileName
	 * @param objectNames
	 * @param properties
	 */
	public static HSSFSheet buildHSSFRowOfTitle(HSSFWorkbook workbook, String fileName, String[][] properties) {
		HSSFSheet sheet = workbook.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[properties.length];
		for (int i = 0; i < properties.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(properties[i][0]);
		}
		
		return sheet;
	}
}
