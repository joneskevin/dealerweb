package com.ava.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {
	
	public static final String doGet(String urlhttp) {
		return doGet(urlhttp, "");
	}
	
	public static final String doGet(String urlhttp, String parameterPairStr) {
		return doGet(urlhttp, parameterPairStr, "UTF-8");
	}

	/**
	 * 进行Get提交方式的网页内容查询，其中参数3是查询网页的编码 
	 * 参数1例子：http://www.kingsh.com/index.do
	 * 参数2例子：action=displayUserList&userId=61 
	 * 参数3例子：GB2312
	 */
	public static final String doGet(String urlhttp, String parameterPairStr, String coder) {
		String urlString = urlhttp + "?" + parameterPairStr;
		//System.out.println("urlString=" + urlString);
		try {
			URL url = new URL(urlString);
			BufferedReader in = new BufferedReader(new InputStreamReader(url
					.openStream(), coder));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			return sb.toString().trim();
		} catch (Exception e) {
			System.out.println("urlString=" + urlString);
			System.out.println("==========> GetHttp.doGet()调用出现错误：" + e);
			return "exception";
		}
	}

	public static final String doPost(String urlhttp, Map parameters) {
		return doPost(urlhttp, parameters, "UTF-8");
	}

	/**
	 * 进行Post提交方式的网页内容查询，其中参数3是查询网页的编码
	 * 参数1例子：http://www.kingsh.com/index.do
	 * 参数2例子：action=displayUserList&userId=61 
	 * 参数3例子：GB2312
	 */
	public static final String doPost(String urlhttp, Map parameters, String chartsetName) {
		try {			
			URL url = new URL(urlhttp);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			if(parameters != null && parameters.size() > 0){
				PrintWriter pwriter = new PrintWriter(connection.getOutputStream());

				String parameterPairStr = "";
				Iterator itor = parameters.keySet().iterator();
				if(itor.hasNext()){
					String key = (String) itor.next();
					parameterPairStr = key + "=" + parameters.get(key);
				}
				
				while(itor.hasNext()){
					String key = (String) itor.next();
					parameterPairStr = "&" + key + "=" + parameters.get(key);
				}
				pwriter.println(parameterPairStr);
				pwriter.close();
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), chartsetName));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			in.close();
			//System.out.println("sb.toString().trim()=" + sb.toString().trim());
			return sb.toString().trim();
		} catch (Exception e) {
			System.out.println("==========> GetHttp.doPost()调用出现错误：" + e);
			return "exception";
		}
	}
}