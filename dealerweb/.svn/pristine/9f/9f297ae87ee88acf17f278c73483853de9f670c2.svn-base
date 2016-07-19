package com.ava.util;

import java.io.FileInputStream;
import java.io.IOException;

import com.ava.resource.GlobalConfig;


public class ReadPositionTemplate {

	private static String tlpContent = null;

	private static String positionRightsCodeContent = null;

	public ReadPositionTemplate() {
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

	/**	读取岗位权限文件内容	*/
	public static String getPositionRightsCodeContent(String templatePath) {
		if (templatePath==null || "".equals(templatePath))
			templatePath = GlobalConfig.getDefaultAppPath() + "/js/dxTree/treeData4position.xml";
		if ( positionRightsCodeContent==null ){
			try {
				positionRightsCodeContent = readTemplateContent(templatePath);
				if (positionRightsCodeContent == null)
					System.err.println("Error reading treePositionRightsCode.xml");
			} catch (Exception e) {
				System.err.println("Error reading treePositionRightsCode.xml file");
			}
		}
		return positionRightsCodeContent;
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