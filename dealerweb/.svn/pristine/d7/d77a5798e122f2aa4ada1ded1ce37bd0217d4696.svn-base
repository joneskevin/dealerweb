package com.ava.baseCommon.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.ava.base.domain.ResponseData;
import com.ava.baseCommon.service.IDownloadApiService;
import com.ava.resource.GlobalConfig;
import com.ava.util.FileUtil;
import com.ava.util.TypeConverter;
import com.ava.util.download.DownloadHelper;

@Service
public class DownloadApiService implements IDownloadApiService {
	/**生成下载文件流*/
	public ResponseData writeHSSFWorkbook(HttpServletResponse response,
			HSSFWorkbook workbook, String displayName, String userAgent) {
		ResponseData rd = new ResponseData(0);

		int fileLength = workbook.getBytes().length;
		String extendName = "xls";
		String fileName = DownloadHelper.buildFileNameByAgent(displayName, extendName, userAgent);
		String contentType = buildContentTypeByExtendName(extendName);

		response.setHeader("Content-Length", String.valueOf(fileLength));
		//设置被下载文件的名称
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		response.setContentType(contentType);
		
		byte[] byteArray = workbook.getBytes();  
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray, 0, fileLength);  
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		rd.setCode(1);
		return rd;
	}
	
	/**生成下载文件流*/
	public ResponseData writeOutStream(HttpServletResponse response,
			String filePath, String displayName, String userAgent) {
		ResponseData rd = new ResponseData(0);

		String fileName = "";
		if (TypeConverter.sizeLagerZero(displayName)) {
		} else {
			displayName = FileUtil.getFileName(filePath);
		}
		String extendName = FileUtil.getExtName(filePath);
		String contentType = buildContentTypeByExtendName(extendName);
		
		fileName = DownloadHelper.buildFileNameByAgent(displayName, extendName, userAgent);

		//获取文件对象
		File file = new File(GlobalConfig.getDefaultAppPath() + filePath, "");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		//设置被下载文件的名称
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		response.setContentType(contentType);

		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));

			bos = new BufferedOutputStream(response.getOutputStream());

			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bis != null) {
					bis.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		rd.setCode(1);
		return rd;
	}
	
	public static String buildContentTypeByExtendName(String extendName){
		String contentType = "";
		//判断文件类型
		if ("zip".equalsIgnoreCase(extendName)) {
			contentType = "zip";
		} else if ("rar".equalsIgnoreCase(extendName)) {
			contentType = "rar";
		} else if ("xls".equalsIgnoreCase(extendName)) {
			contentType = "application/vnd.ms-excel";
		} else {
			contentType = "";
		}
		
		return contentType;
	}
}
