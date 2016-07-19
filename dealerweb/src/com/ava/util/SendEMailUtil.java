package com.ava.util;

import com.ava.resource.GlobalConfig;

public class SendEMailUtil {
	
	public static int sendMessage(String nickName, String emailAddress, String title, String content) {
		
		if (content == null || null==emailAddress || !TypeConverter.sizeLagerZero(emailAddress) || !ValidatorUtil.isEmail(emailAddress)) {
			return -1;
		}else{
			try{
				MailUtil mail = new MailUtil();
				StringBuffer contentBuf = new StringBuffer(200);
				contentBuf.append("<font size=2 color='#003366'>" + nickName);
				contentBuf.append("，您好:<br>");
				contentBuf.append(content + "<br><br>");
				contentBuf.append("系统登录地址" 
						+ "<br><a href='" + GlobalConfig.getDomain() + "'>" 
						+ GlobalConfig.getDomain() + "</a><br><br>");
				contentBuf.append("<font size=2 color='#663300'>本邮件为系统自动发出，请勿直接回复 ！</font><br>");
				mail.send(emailAddress, title , contentBuf.toString());
			}catch(Exception e){
				return -1;
			}
		}
		return 1;
	}

}
