package com.ava.util;

import java.io.FileInputStream;
import java.io.IOException;

import com.ava.resource.GlobalConfig;


public class ReadTemplate {

	private static String tlpContent = null;

	private static String userRightsCodeContent = null;

	private static String operatorRightsCodeContent = null;

	public ReadTemplate() {
	}

	/**	根据指定路径读取模版文件内容，注意参数中路径为绝对地址	*/
	public static String getTemplateContent(String templatePath) {
		if (templatePath==null || "".equals(templatePath))
			templatePath = GlobalConfig.getDefaultAppPath() + "/template/mail/inviteMail.template";
		if (tlpContent == null){
			try {
				tlpContent = readTemplateContent(templatePath);
				if (tlpContent == null)
					System.err.println("Error reading Template file");
			} catch (Exception e) {
				System.err.println("Error reading Template file");
			}
		}
		return tlpContent;
	}

	/**	读取用户权限文件内容	*/
	public static String getUserRightsCodeContent(String templatePath) {
		if (templatePath==null || "".equals(templatePath))
			templatePath = GlobalConfig.getDefaultAppPath() + "/js/dxTree/treeData4base.xml";
		if ( userRightsCodeContent==null ){
			try {
				userRightsCodeContent = readTemplateContent(templatePath);
				if (userRightsCodeContent == null)
					System.err.println("Error reading treeUserRightsCode.xml");
			} catch (Exception e) {
				System.err.println("Error reading treeUserRightsCode.xml file");
			}
		}
		return userRightsCodeContent;
	}

	/**	读取操作员权限文件内容	*/
	public static String getOperatorRightsCodeContent(String templatePath) {
		if (templatePath==null || "".equals(templatePath))
			templatePath = GlobalConfig.getDefaultAppPath() + "/js/dxTree/treeData4back.xml";
		if ( operatorRightsCodeContent==null ){
			try {
				operatorRightsCodeContent = readTemplateContent(templatePath);
				if (operatorRightsCodeContent == null)
					System.err.println("Error reading treeOperatorRightsCode xml");
			} catch (Exception e) {
				System.err.println("Error reading treeOperatorRightsCode xml file");
			}
		}
		return operatorRightsCodeContent;
	}
	
	private static String readTemplateContent(String templatePath) {
		String s1 = null;
		try {
			FileInputStream fileinputstream = new FileInputStream(templatePath);
			int i = fileinputstream.available();
			byte abyte0[] = new byte[i];
			fileinputstream.read(abyte0);
			fileinputstream.close();
			//s1 = new String(abyte0);
			s1 = new String(abyte0, "UTF-8");
		} catch (IOException ioexception) {
			System.err.println("Error reading Template file");
		}
		return s1;
	}

}