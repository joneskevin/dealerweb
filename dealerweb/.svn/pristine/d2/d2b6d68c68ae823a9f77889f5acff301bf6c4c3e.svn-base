package com.ava.util;

import org.apache.commons.lang.StringUtils;

public class MyStringUtils extends StringUtils {
	
	/** 把字符串里的数字都提取出来	*/
	public static String collectNumber(String str) {
		if (str == null) {
			return str;
		}
        char[] chars = str.toCharArray();
        int length = chars.length;
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < length; i++){
			 if (chars[i] >= '0' && chars[i] <= '9'){
				 sb.append(chars[i]);
			 }
		}
		return sb.toString();
	}

	/**按指定长度进行字符串截断*/
	public static String cut(String str, int len) {
		if (str == null) {
			return "";
		}
		if (str.length() > len){
			return str.substring(0, len) + "...";
		}
		return str;
	}

}
