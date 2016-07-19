package com.ava.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {
	
	/**
	 * request.getRequestURI()和request.getRequestURL()区别
	 * request.getRequestURI()=/helpcenter/questions.jsp?action=viewNews&newsId=432
	 * request.getRequestURL()=http://kwa.kingsh.net/helpcenter/questions.jsp?action=viewNews&newsId=432
	 */

	/**	主方法	*/
	public static void main(String[] args) {
		String tmp = getPageName("http://kwa.kingsh.net/helpcenter/questions.jsp#adPositon");
		System.out.println("tmp=" + tmp);
	}

	/**	当前访问页的完整URI，如：/helpcenter/questions.jsp?action=viewNews&newsId=432	*/
	public static final String getFullURI(HttpServletRequest request) {
		return request.getRequestURI() + getParams(request);
	}

	/**	当前访问页的完整URL，如：http://kwa.kingsh.net/helpcenter/questions.jsp?action=viewNews&newsId=432	*/
	public static final String getFullURL(HttpServletRequest request) {
		return request.getRequestURL() + getParams(request);
	}
	
	/**
	* @return:	"?action=viewNews&newsId=432"
	*/
    public static String getParams(HttpServletRequest request) {
        StringBuffer stringBuffer = new StringBuffer("");
        Enumeration enumeration = request.getParameterNames();
        boolean firstParam = true;	//是否第一个参数
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = request.getParameter(key);
            if (firstParam){
            	stringBuffer.append("?");
            }else{
            	stringBuffer.append("&");
            }
            try{
            	value = TextUtil.substringByWidth(value, 200);	//由于get方式最多支持256个汉字，所以控制参数值在200汉字内
            	value = URLEncoder.encode(value, "UTF-8");
            }catch(UnsupportedEncodingException e){
            }
            stringBuffer.append(key).append("=").append(value);
            firstParam = false;
        }
        return stringBuffer.toString();
    }
	/**
	* @param: request 客户端请求
	* @param: exceptionParamNames 排除在外的参数，用","分隔，如"action,pageNo"
	* @return:	"?action=viewNews&newsId=432"
	*/
    public static String getParams(HttpServletRequest request, String exceptionParamNames) {
        StringBuffer stringBuffer = new StringBuffer("");
        Enumeration enumeration = request.getParameterNames();
        boolean firstParam = true;	//是否第一个参数
        outer: while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            StringTokenizer st = new StringTokenizer(exceptionParamNames, ",");
            while (st.hasMoreTokens()) {
                String exceptionName = st.nextToken();
                if (key.equals(exceptionName)) {
                    continue outer;
                }
            }
            String value = request.getParameter(key);
            if (firstParam){
            	stringBuffer.append("?");
            }else{
            	stringBuffer.append("&");
            }
            stringBuffer.append(key).append("=").append(value);
            firstParam = false;
        }
        return stringBuffer.toString();
        /*
         * String tmpParam = ""; try{ tmpParam = new
         * String(params.getBytes("GB2312"),"GB2312");
         * }catch(UnsupportedEncodingException encodee){
         * encodee.printStackTrace(); return "&process=failed"; } return
         * tmpParam; //return params;
         */
    }

	/** 得到当面访问页面的名称，例如：index.jsp */
	public static final String getPageName(String url) {
		String pageName;
		/**	url字符串可能出现的形式
		 * http://kwa.kingsh.net/helpcenter/questions.jsp
		 * http://kwa.kingsh.net/helpcenter/questions.jsp#
		 * http://kwa.kingsh.net/helpcenter/questions.jsp#adPositon
		 * http://kwa.kingsh.net/helpcenter/questions.jsp?key=value&id=4
		 */		
		//开始分析url字符串，找出最后一个“/”的位置
		int startIndex = url.lastIndexOf("/");
		if (startIndex > -1) {
			int endIndex = url.indexOf("?", startIndex);
			if (endIndex > -1) {
				pageName = url.substring(startIndex + 1, endIndex);
			} else {
				pageName = url.substring(startIndex + 1);
			}
			//这儿pageName已经是questions.jsp或者questions.jsp#ad的格式
			endIndex = pageName.indexOf("#");
			if (endIndex > -1) {
				pageName = pageName.substring(0, endIndex);
			}
		} else {
			pageName = "";
		}
		return pageName;
	}

	/** 得到当面访问页面的名称，例如：index.jsp */
	public static final String getPageName(HttpServletRequest request) {
		return getPageName(request.getRequestURL().toString());
	}

	/** 得到当面访问页面的路径，例如：http://192.168.4.110:8080/demosite/admin/counter/	*/
	public static final String getPath(HttpServletRequest request) {
		String path = request.getRequestURL().toString();
		int endIndex = path.lastIndexOf("/");
		if (endIndex>-1){
			path = path.substring(0, endIndex + 1);
		}
		return path;
	}

	/** 得到当面访问页面的完整域名(包括协议和端口)，例如：http://www.kingsh.com:8080 
	 * @注意：最后没有斜杠分隔符	*/
	public static final String getFullDomain(HttpServletRequest request) {
		return getFullDomain(request.getRequestURL().toString());
		
	}
	public static final String getFullDomain(String url) {
		String result = url;
		int endIndex = result.indexOf("/", 8);
		if (endIndex > -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/** 得到当面访问页面的域名，例如：www.kingsh.com
	 * @注意：该域名已经忽略端口号	*/
	public static final String getDomainName(HttpServletRequest request) {
		return getDomainName(request.getRequestURL().toString());
	}
	public static final String getDomainName(String url) {
		/**	url字符串可能出现的形式
		 * http://kwa.kingsh.net:8080/helpcenter/questions.jsp
		 * http://kwa.kingsh.net/helpcenter/questions.jsp?key=value&id=4
		 */
		String result = url;
		int startIndex = result.indexOf("://");
		if (startIndex > -1) {
			startIndex = startIndex + 3;
		} else {
			startIndex = 0;
		}
		int endIndex = result.indexOf("/", 8);
		if (endIndex > -1) {
			result = result.substring(startIndex, endIndex);
		} else {
			result = result.substring(startIndex);
		}
		//去除端口号
		endIndex = result.indexOf(":");
		if (endIndex > -1) {
			result = result.substring(0, endIndex);
		} else {
			result = result.substring(0);
		}
		return result;
	}
    
}