package com.ava.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ava.resource.GlobalConstant;

public class CookieProcess {

	/**	添加一个产品id到cookie中，产品队列长度默认4个	*/
	public static boolean addProductHistory(Integer productId, HttpServletRequest request, HttpServletResponse response) {
		String productHistory = getCookieValue(GlobalConstant.COOKIE_PRODUCT_HISTORY, request);
		String[] productHistoryArray ;
		//System.out.println("===================================>");
		if (productId==null){
			return false;
		}
		if ( productHistory==null || "".equals(productHistory) ){
			productHistory = productId.toString();
		}else{
			boolean isExist = false;
			productHistoryArray = productHistory.split("!");
			for (int i=0; i<productHistoryArray.length; i++){
				if (productHistoryArray[i].toString().equals(productId.toString())){
					isExist = true;
				}
			}			
			if (!isExist) {
				if (productHistoryArray.length < GlobalConstant.PRODUCT_HISTORY_LENGTH) {
					//如果不到历史浏览记录上限，则直接在字符串后加新记录
					//System.out.println("productHistory=" + productHistory);
					productHistory = productHistory + "!"
							+ productId.toString();
				} else if (productHistoryArray.length == GlobalConstant.PRODUCT_HISTORY_LENGTH) {
					//如果刚好达到上限，则循环把记录都往前移一位
					for (int i = 0; i < productHistoryArray.length - 1; i++) {
						productHistoryArray[i] = productHistoryArray[i + 1];
					}
					productHistoryArray[productHistoryArray.length - 1] = productId
							.toString();
					productHistory = changeArrayToString(productHistoryArray);
				} else {
					//剩下的情况是记录数已经超过上限（可能其他误操作或系统bug引起）的异常情况，则新增一个标准长度的数组，然后循环赋值
					String[] rightProductHistoryArray = new String[GlobalConstant.PRODUCT_HISTORY_LENGTH];
					for (int i = 0; i < GlobalConstant.PRODUCT_HISTORY_LENGTH - 1; i++) {
						rightProductHistoryArray[i] = productHistoryArray[i + 1];
					}
					rightProductHistoryArray[GlobalConstant.PRODUCT_HISTORY_LENGTH - 1] = productId
							.toString();
					productHistory = changeArrayToString(rightProductHistoryArray);
				}
			}			
		}
		//System.out.println("productHistory=" + productHistory);
		setCookieValue(GlobalConstant.COOKIE_PRODUCT_HISTORY, productHistory, response);
		return true;
	}
		
	public static String changeArrayToString(String[] stringArray){
		return changeArrayToString(stringArray, "!");
	}
	
	public static String changeArrayToString(String[] stringArray, String splitCode){
		String result ;
		if (stringArray==null || stringArray.length==0){
			return null;
		}else{
			result = "";
			for (int i=0; i<stringArray.length; i++){
				if (i==0){
					result = stringArray[i];
				}else{
					result = result + splitCode + stringArray[i];
				}
			}
			return result;
		}
	}
	
	/**	根据Cookie 名称得到请求中的 Cookie 值, 如果 Cookie 值是 null, 则返回 null*/
	public static String getCookieValue(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		int length = 0;

		if (cookies == null || cookies.length == 0)
			return null;
		length = cookies.length;
		for (int i = 0; i < length; i++) {
			Cookie cookie = cookies[i];
			//System.out.println( "cookie.getPath() :" + cookie.getPath() );
			if (cookie.getName().equals(name)) {
				// 需要对 Cookie 中的汉字进行 URL 反编码
				return cookie.getValue();
			}
		}
		return null;
	}

	public static void setCookieValue(String cookieName, String cookieValue, HttpServletResponse response) {
		//cookie有效期默认1年
		setCookieValue(cookieName, cookieValue, 31536000, response);
	}

	public static void setCookieValue(String cookieName, String cookieValue,
			int maxAge,HttpServletResponse response) {
		if ( cookieName==null ){
			return;
		}
		Cookie cookie=new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);		
	}

	public static void removeCookie(String cookieName,HttpServletResponse response) {
		if ( cookieName==null ){
			return;
		}
		Cookie cookie = new Cookie(cookieName,"");
		cookie.setMaxAge(1);
		cookie.setPath("/");
		response.addCookie(cookie);	
	}
}
