package com.ava.baseCommon.service;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ava.base.domain.ResponseData;

public interface IDownloadApiService{
	/**生成下载文件流*/
	public ResponseData writeHSSFWorkbook(HttpServletResponse response, HSSFWorkbook workbook, String displayName, String userAgent);

	/**生成下载文件流*/
	public ResponseData writeOutStream(HttpServletResponse response, String filePath, String displayName, String userAgent);
	
}
