package com.ava.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;

public class ValidatorUtil extends GenericValidator {

	
	/**	是否字母	*/
	public static boolean isAlphabet(String s){
		for(int i=0;i<s.length();i++){
		if(!(s.charAt(i)>='A'&&s.charAt(i)<='Z')&&!(s.charAt(i)>='a'&&s.charAt(i)<='z')){
		return false;
		}
		}return true;
		}
	
	/**	是否字母或数字组合	*/
	public static boolean isAlphaNumeric(String s) {
		if (s==null){
			return false;
		}
		byte abyte0[] = s.getBytes();
		for (int i = 0; i < abyte0.length; i++)
			if ((abyte0[i] < 48 || abyte0[i] > 57)
					&& (abyte0[i] < 65 || abyte0[i] > 90)
					&& (abyte0[i] < 97 || abyte0[i] > 122))
				return false;

		return true;
	}

	public static boolean isSize(String s) {
		if (s != null){
			if (s.length() < 6)
				return false;
		}
		return true;
	}
	
    /** 判断字符的长度6-15之间 */
	public static boolean isValidSize4Password(String s) {
		if (s==null){
			return false;
		}
		if (s.length()>=6 && s.length()<=16){
			return true;
		}
		return false;
	}

	/**	是否纯数字	*/
	public static boolean isNumeric(String s) {
		if (s==null){
			return false;
		}
		byte abyte0[] = s.getBytes();
		if (abyte0.length == 0) {
			return false;
		}
		for (int i = 0; i < abyte0.length; i++) {
			if (abyte0[i] < 48 || abyte0[i] > 57) {
				return false;
			}
		}
		return true;
	}

	/**	是否固话号码	*/
	public static boolean isPhone(String s) {
		if (s != null) {
			String strTemp = "0123456789-()－（）*";
			for (int i = 0; i < s.length(); i++) {
				if (strTemp.indexOf(s.charAt(i)) == -1) {
					return false;
				}
			}
			if (s.length() >= 7 && s.length() < 30) {
				return true;
			}
		}
		return false;
	}

	/**	是否移动电话号码	*/
	public static boolean isMobile(String s) {
		if (s != null) {
			s = s.trim();
			if (isNumeric(s) && s.length() == 11 && "1".equals(s.substring(0, 1))) {
				//如果是纯数字，11位长度，开头数字是1的字符串，则是正确的手机号码
				return true;
			}
		}
		return false;
	}

	/**	是否邮编	*/
	public static boolean isZip(String s) {
		if (s != null) {
			if (isNumeric(s) && s.length() == 6) {
				return true;
			}
		}
		return false;
	}

	/**	是否QQ号码	*/
	public static boolean isQQ(String s) {
		if (s != null) {
			if (isNumeric(s) && s.length() > 4 && s.length() < 16) {
				return true;
			}
		}
		return false;
	}

	/**	是否正确格式日期，有8位的格式或者14位包括时间的格式
	 * 格式1：20080924
	 * 格式2：20080924153326
	 * 	*/
	public static boolean isValidISODate(String s) {
		if (s == null || !isNumeric(s))
			return false;
		if (s.length() != 8 && s.length() != 14)
			return false;
		if (s.length() == 14) {
			if (Integer.parseInt(s.substring(8, 10)) > 24)
				return false;
			if (Integer.parseInt(s.substring(10, 12)) > 60)
				return false;
			if (Integer.parseInt(s.substring(12, 14)) > 60)
				return false;
		}
		int i = Integer.parseInt(s.substring(0, 4));
		int j = Integer.parseInt(s.substring(4, 6));
		int k = Integer.parseInt(s.substring(6, 8));
		byte byte0 = 31;
		if (j < 1 || j > 12)
			return false;
		if (j == 4 || j == 6 || j == 9 || j == 11)
			byte0 = 30;
		else if (j == 2)
			if (i % 400 == 0)
				byte0 = 29;
			else if (i % 100 == 0)
				byte0 = 28;
			else if (i % 4 == 0)
				byte0 = 29;
			else
				byte0 = 28;
		return k >= 1 && k <= byte0;
	}

	/**	是否身份证号码	*/
	public static boolean isIdentification(String s) {
		if (s != null) {
			if (isNumeric(s)) {
				if (s.length() == 15 || s.length() == 18) {
					return true;
				}
			}
		}
		return false;
	}

	/**	是否纯中文	*/
	public static boolean isChinese(String text) {
		if (MyStringUtils.isBlank(text)){
			return false;
		}
		for (int i = 0; i < text.length(); i++) { 
            String aChar = text.substring(i, i+1); 
            boolean isChinese = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", aChar); 
            if(!isChinese){
            	 return false; 
            }
        } 		
		return true;
	}
	
	/**	判断民用车牌格式：<br>
	 * 1、第一位是汉字<br>
	 * 2、长度在6－30之间<br>
	 * 3、不允许纯数字（防止跟可能需要的手机号码登录冲突）<br>
	 * 4、不允许@符号（防止跟可能需要的email登录冲突）<br>
	 * 	*/
	public static boolean isCivilPlateNum(String plateNum) {
		if (plateNum == null){
			return false;
		}
		if (plateNum.length() != 7){
			//长度不等于7
			return false;
		}
		String firstChar = String.valueOf(plateNum.charAt(0));
		if (!isChinese(firstChar)){
			//第一个字符不是中文
			return false;
		}
		String secondChar = String.valueOf(plateNum.charAt(1));
		if (!isAlphabet(secondChar)){
			//第二个字符不是字母
			return false;
		}		
		String tailChar = plateNum.substring(2);
		if (!isAlphaNumeric(tailChar)){
			return false;
		}
		
		return true;
	}

	
	/**	判断vin码格式：<br>
	 * 1、第一位是汉字<br>
	 * 2、长度等于17位<br>
	 * 	*/
	public static boolean isVin(String vin) {
		if (vin == null){
			return false;
		}
		if (vin.length() != 17){
			//长度不等于17位
			return false;
		}
		
		return true;
	}
	
	/**
	 * 验证是否为数字
	 * @param number
	 * @return
	 */
	public static boolean validateNumber(String number)  {
		if(isBlankOrNull(number)){
			return false;
		}
		if(number.startsWith("0")){
			return false;
		}
		if(!isNumeric(number)){
			return false;
		}

		return true;
/*		String reg = "/^[1-9]+[0-9]*]*$/";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();*/
	}
}
