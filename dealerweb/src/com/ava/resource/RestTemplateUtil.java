package com.ava.resource;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ava.base.domain.ResponseData;

/**REST模板工具类*/
public class RestTemplateUtil {
	protected final static Logger logger = Logger.getLogger(RestTemplateUtil.class);
	
	private final static String REST_API_ADDRESS = GlobalConfig.getFingerprintRestDomain();
	
	/***
	 * 通过RestTemplate获取rest接口的数据
	 * @param url
	 * @return
	 */
	public static final ResponseData doGet(String url, Class clazz, Map parameters) {
		RestTemplate restTemplate = new RestTemplate();
		
		if(parameters != null && parameters.size() > 0){
			Iterator itor = parameters.keySet().iterator();
			while(itor.hasNext()){
				String key = (String) itor.next();
				String value = String.valueOf(parameters.get(key));
				
				try {
					 //为了防止属性值中包含“/”造成jvm崩溃错误，故进行转义处理
					 value = java.net.URLEncoder.encode(value, "utf8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(!url.contains("?")){
					url += "?" + key + "=" + value;
				}else{
					url += "&" + key + "=" + value;
				}
			}
		}
		
        ResponseEntity response = restTemplate.getForEntity(url, String.class);
        String jsonString = (String)response.getBody().toString(); 
        //logger.info("doGet success:" + jsonString);
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        
        Map clazzMap = new HashMap();
        clazzMap.put("data", HashMap.class);
        clazzMap.put("firstItem", clazz);
        //clazzMap.put("ftList", clazz);
        
        ResponseData rd = (ResponseData) JSONObject.toBean(jsonObject, ResponseData.class, clazzMap); 
        return rd;
	}

	public static final ResponseData doPost(String url, Class clazz, Map parameters) {
		RestTemplate restTemplate = new RestTemplate();
		
		if(parameters != null && parameters.size() > 0){
			Iterator itor = parameters.keySet().iterator();
			while(itor.hasNext()){
				String key = (String) itor.next();
				String value = String.valueOf(parameters.get(key));
				try {
					 //为了防止属性值中包含“/”造成jvm崩溃错误，故进行转义处理
					 value = java.net.URLEncoder.encode(value, "utf8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if(!url.contains("?")){
					url += "?" + key + "=" + value;
				}else{
					url += "&" + key + "=" + value;
				}
			}
		}
		
		ResponseEntity response = restTemplate.postForEntity(url, null, String.class);
		String jsonString = response.getBody().toString();
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        
        Map clazzMap = new HashMap();
        clazzMap.put("data", clazz);
        clazzMap.put("firstItem", clazz);
        
        ResponseData rd = (ResponseData) JSONObject.toBean(jsonObject, ResponseData.class, clazzMap); 
        return rd;
	}

	/*private static ResponseData createFingerprint(Integer userId, String userLoginName, String fingerprintTemplate) {
		String url = REST_API_ADDRESS + "/api/fingerprint";
        Map<String, Object> parameters = new HashMap<String, Object>();  
        parameters.put("userId", userId);
        parameters.put("userLoginName", userLoginName);
        parameters.put("fingerprintTemplate", fingerprintTemplate);
        ResponseData rd = doPost(url, FingerprintTemplate.class, parameters);
        
        return rd;
	}

	private static ResponseData getFingerprint(Integer userId) {
		String url = REST_API_ADDRESS + "/api/fingerprint/" + String.valueOf(userId);
        Map<String, Object> parameters = new HashMap<String, Object>();  
        ResponseData rd = doGet(url, FingerprintTemplate.class, parameters);
        
        return rd;
	}*/

	public static ResponseData checkFingerprint(Integer userId, String fingerprintTemplate) {
		String url = REST_API_ADDRESS + "/api/fingerprint/check/" + String.valueOf(userId);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fingerprintTemplate", fingerprintTemplate); 
        ResponseData rd = null;//doGet(url, FingerprintTemplate.class, parameters);
        
        return rd;
	}

	public static void main(String[] args) throws Exception {
		ResponseData rd = null;
		//rd = createFingerprint(1, "myLoginName", "fingerprintTemplate");
		//rd = getFingerprint(1);
		rd = checkFingerprint(1, "fingerprintTemplate");
		//logger.debug(rd.getCode());
	}
}