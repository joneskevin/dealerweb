package com.ava.api.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.api.service.ISmsApiService;
import com.ava.dao.ISmsDao;
import com.ava.domain.entity.Sms;
import com.ava.util.HttpUtil;
import com.ava.util.ValidatorUtil;

@Service
public class SmsApiService implements ISmsApiService {

	@Autowired
	private ISmsDao smsDao;

	/** 发送一条站内消息 */
	public boolean send(Integer orgId, Integer userId, String toMobile, String content, String description) {
		if (content == null || "".equals(content.trim())) {
			return false;
		}
		if (ValidatorUtil.isMobile(toMobile)) {
			// 是正确的手机号码
			// 开始发送站内消息给网关
			String encodeContent = "";
			try {
				encodeContent = URLEncoder.encode(content, "GBK");
				//content = changeCharset(content, "UTF-8", "GBK");
			}catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}
			String urlhttp = "http://59.50.95.61/API/ZXDX.CFM";
			Map parameters = new HashMap();
			parameters.put("DLZH", "100149");
			parameters.put("DLMM", "shwztc39892");
			parameters.put("SJHM", toMobile);
			parameters.put("XXNR", encodeContent);
			parameters.put("FSHM", "");
			HttpUtil.doPost(urlhttp, parameters, "GBK");

			// 发送成功，保存发送记录
			Sms sms = new Sms();
			sms.init();
			sms.setOrgId(orgId);
			sms.setUserId(userId);
			sms.setMobile(toMobile);
			sms.setContent(content);
			sms.setDescription(description);
			smsDao.edit(sms);
			// 返回发送成功结果
			return true;
		} else {
			// 不是正确的手机号码
			return false;
		}
	}
	
	public static void main(String[] args) {
		String encodedContent = "你有一项新的审批，申请者：郑晓宏，申请类别：报销";
		System.out.println("content1=" + encodedContent);
		try {
			encodedContent = URLEncoder.encode(encodedContent, "GBK");
			//content = changeCharset(content, "UTF-8", "GBK");
		}catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
		String urlhttp = "http://59.50.95.61/API/ZXDX.CFM";
		Map parameters = new HashMap();
		parameters.put("DLZH", "100149");
		parameters.put("DLMM", "shwztc39892");
		parameters.put("SJHM", "13391157152;13764571012;");
		parameters.put("XXNR", encodedContent);
		parameters.put("FSHM", "");
		HttpUtil.doPost(urlhttp, parameters, "GBK");
	}

}
