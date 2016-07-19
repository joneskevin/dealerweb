package com.ava.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	
	/**
	 * 得到匹配标签的innerHtml
	 * @param matchStr
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static String getMatchInner(String matchStr,String startPattern,String endPattern){
		String result = null;
		if (matchStr != null) {
			result = getBehindMatch(matchStr, startPattern, 0);
			result = getPreviousMatch(result, endPattern, 0);
		}
		return result;
	}
	
	/**
	 * @param matchStr
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static String getMatch(String matchStr,String patternStr,int groupIndex){
		Pattern p = Pattern.compile(patternStr); 
		Matcher m = p.matcher(matchStr); 
	
		String item = null;
		if(m.find())
		{		
			item = m.group(groupIndex);
		}
		return item;
	}
	
	/**
	 * ���ƥ��ǰ���ǰ���ַ�
	 * @param matchStr
	 * @param patternStr
	 * @return
	 */
	public static String getPreviousMatch(String matchStr,String patternStr,int groupIndex)
	{
		String result = null;
		int startIndex = -1;
		if (matchStr != null && patternStr != null && groupIndex >= 0) {
			Pattern p = Pattern.compile(patternStr);
			Matcher m = p.matcher(matchStr);
			if(m.find()) {
				startIndex = m.start(groupIndex);
				if (startIndex > 0) {
					result = matchStr.substring(0, startIndex);
				}
			}
		}
		return result;
	}
	/**
	 * ���ƥ��ǰ��ĺ����ַ�
	 * @param matchStr
	 * @param patternStr
	 * @return
	 */	
	public static String getBehindMatch(String matchStr,String patternStr,int groupIndex)
	{
		String result = null;
		int endIndex = -1;
		if (matchStr != null && patternStr != null && groupIndex >= 0) {
			Pattern p = Pattern.compile(patternStr);
			Matcher m = p.matcher(matchStr);
			if(m.find()) {
				endIndex = m.end(groupIndex);
				if (endIndex > 0) {
					result = matchStr.substring(endIndex);
				}
			}
		}
		return result;
	}	
	/**
	 * @param matchStr
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static List getMatchSet(String matchStr,String patternStr,int groupIndex)
	{
		List itemList = new ArrayList();
		Pattern p = Pattern.compile(patternStr); 
		Matcher m = p.matcher(matchStr); 
		while(m.find())
		{
			String item = m.group(groupIndex);
			itemList.add(item);
		}
		return itemList;
	}	
	/**
	 * @param matchStr
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static int[] getStartEndOfFirstMatch(String matchStr,String patternStr,int groupIndex)
	{
		int[] startend = new int[2];
		startend[0] = -1;
		startend[1] = -1;
		if (matchStr != null && patternStr != null && groupIndex >= 0) {
			Pattern p = Pattern.compile(patternStr);
			Matcher m = p.matcher(matchStr);
			if (m.find()) {
				startend[0] = m.start(groupIndex);
				startend[1] = m.end(groupIndex);
			}
		}
		return startend;
	}
	
	/**
	 * @param matchStr
	 * @param patternStr
	 * @param groupIndex
	 * @return
	 */
	public static int[] getStartEndOfLastMatch(String matchStr,String patternStr,int groupIndex)
	{
		int[] startend = new int[2];
		startend[0] = -1;
		startend[1] = -1;
		if (matchStr != null && patternStr != null && groupIndex >= 0) {
			Pattern p = Pattern.compile(patternStr);
			Matcher m = p.matcher(matchStr);			
			while (m.find()) {
				startend[0] = m.start(groupIndex);
				startend[1] = m.end(groupIndex);
			}
		}
		return startend;
	}
	//////////////////////////////////////////////
	
	public static String postHtml(String sUrl,String querystr)
	{
		String sHtml = null;
//		SnatchHtmlDAO shdao = new SnatchHtmlDAO();		
//		sHtml = shdao.httpPost(sUrl,querystr);
		return sHtml;
	}
	
	public static String getHtml(String sUrl)
	{
		if(sUrl == null){
			return null;
		}
		String sHtml = null;
/*		SnatchHtmlDAO shdao = new SnatchHtmlDAO();		
		for (int i = 0; i < 3; i++) {
			if (sHtml != null && sHtml.length() > 0) {
				break;
			}else{
				sHtml = shdao.httpGet(sUrl);
			}
		}*/
		return sHtml;
	}
	
	public static String getNextPageUrl(String sHtml)
	{
		String url = new String("");
		return url;
	}
	
	/**
	 * 过滤‘[’到‘ ]’之间的内容
	 * @param content
	 * @return
	 */
	public static String filterSymbols(String content)
	{
		String	exp  = "\\[<(a|A)[^>]*>.*</(a|A)>\\]";
		content = content.replaceAll(exp,"");
		return content;
	}
	
	//////////////////////////////////////////////
	
	/**
	 * 进行基本的数据清洗
	 * @param sHtml
	 * @return
	 */
	public static String doBasePurify(String sHtml)
	{
		if(sHtml == null){
			return null;
		}
		String exp = null;
//	Ϊ��Ч�ʣ����˸ı�����˳��
//	ɾ��ɶ��ͱ�ǩ�Լ���ǩ�������
		exp  = "<(head|HEAD)[^>]*>(.*?)</(head|HEAD)>|<(script|SCRIPT)[^>]*>(.*?)</(script|SCRIPT)>|<(style|STYLE)[^>]*>(.*?)</(style|STYLE)>";
		sHtml = sHtml.replaceAll(exp,"");
//	ɾ�����͸����ǩ
		exp  = "&nbsp;|&nbsp|<(br|BR)>|<(img|IMG)[^>]*>";
		sHtml = sHtml.replaceAll(exp,"");	
//	ɾ��ɶ������α�ǩ,����ɾ���ǩ�������
		exp = "<(font|FONT)[^>]*>|</(font|FONT)>|<(strong|STRONG)[^>]*>|</(strong|STRONG)>";
		sHtml = sHtml.replaceAll(exp,"");
////	ɾ��յ�TD_Tag�ṹ��ǩ,ע��\sΪӢ�Ŀո�� ��Ϊ���Ŀո�
//		exp  = "<(td|TD)[^>]*>[\\s| ]*</(td|TD)>";
//		sHtml = sHtml.replaceAll(exp,"");
////	ɾ��յ�TR_Tag�ṹ��ǩ
//		exp  = "<(tr|TR)[^>]*>[\\s| ]*</(tr|TR)>";
//		sHtml = sHtml.replaceAll(exp,"");
////	ɾ��յ�DIV_Tag�ṹ��ǩ
//		exp  = "<(?:DIV|div)[^>]>[\\s| ]*</(?:DIV|div)>";
//		sHtml = sHtml.replaceAll(exp,"");
//	ɾ��ɼ��ǩ
		exp  = "\n|\r|\t";
		sHtml = sHtml.replaceAll(exp,"");

		return sHtml;
	}
	/**
	 * ��Ҫ���ı����й淶��
	 * @param sHtml
	 * @return
	 */
	public static String doFormat(String sHtml)
	{
//		String fmt;
//
//		exp = "<(TD|td)[^>]*>(.*?)<(TD|td)";
//		fmt = "</TD>";
//		sHtml = RegexModify(exp,"");
//
//		exp = "<(TD|td)[^>]*>(.*?)</(tr|TR)>";
//		fmt = "</TD>";
//		sHtml = RegexModify(exp,"");

		return sHtml;
	}
	/**
	 * @param linkUrl
	 * @param urlLength
	 * @return
	 */
	public static boolean isValidUrl(String linkUrl,int urlLength){
		if(urlLength < 10){
			urlLength = 10;
		}
		String patternStr = "http://.*?[(.html)|(.htm)|(.jsp)]";
		if (linkUrl != null && linkUrl.length() > urlLength) {
			Pattern p = Pattern.compile(patternStr);
			Matcher m = p.matcher(linkUrl);
			if(m.find()) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		String sHtml = "在 2014-02-12 10:43:58 发送的上一封邮件中，liujing@xinsuma 写到：刘京提交了一个采购申请 [<a href=\"javascript:openWindow('/base/baseOa/appro/proposalAction.do?proposalId=1&action=displayHandleProposal', 660, 500, '审批申请')\">点击此处进行处理</a>]";
//		sHtml = TextUtil.unicodeToString(sHtml);
		String	exp  = "\\[<(a|A)[^>]*>.*</(a|A)>\\]";
		sHtml = sHtml.replaceAll(exp,"");
		System.out.println(sHtml);
	}
}
