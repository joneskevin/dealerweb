package com.ava.util.download;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;

public class DownloadHelper {
	
	public static String buildFileNameByAgent(String displayName, String extendName, String userAgent){
		if(displayName != null && !displayName.contains("." + extendName)){
			displayName += "." + extendName;
		}

		String fileName = "";
		try {
			// 根据客户端浏览器来决定不同的编码
			if (userAgent != null && userAgent.indexOf("MSIE") > -1) {
				// 是ie浏览器
				fileName = URLEncoder.encode(displayName, "UTF-8");
				fileName = fileName.replace("+", "%20");
			} else if (userAgent != null && userAgent.indexOf("Mozilla") > -1) {
				// 是firefox等浏览器
				fileName = MimeUtility.encodeText(displayName, "UTF8", "B");
			} else {
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fileName;
	}
}
