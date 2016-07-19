package com.ava.resource;

import javax.servlet.http.HttpServletRequest;

import com.ava.api.service.ISmsApiService;
import com.ava.dao.ICompanyDao;
import com.ava.domain.entity.Company;

/**短信管理类*/
public class SmsManager {
	
    private static ISmsApiService smsApiService = (ISmsApiService) GlobalConfig.getBean("smsApiService");
    private static ICompanyDao companyDao = (ICompanyDao) GlobalConfig.getBean("companyDao");

    private SmsManager() {
    }

    /**	批量发送站内消息*/
    public static int batchSend(HttpServletRequest request, String toMobiles, String content, String description) {
    	Integer orgId = SessionManager.getCurrentCompanyId(request);	
		Integer userId = SessionManager.getCurrentUserId(request);
		Company org = companyDao.get(orgId);
		String[] toMobileArray = toMobiles.split(";");
		if (toMobileArray != null && toMobileArray.length>0){
			//开始循环发送站内消息
			for (int i=0; i<toMobileArray.length; i++){
		    	if ( smsApiService.send(orgId, userId, toMobileArray[i], content, description) ){
		    	}else{
		    	}
			}
		}
		
		return 1;
    }

    /** 发送一条站内消息 */
    public static boolean send(HttpServletRequest request, String toMobile, String content, String description) {
    	Integer orgId = SessionManager.getCurrentCompanyId(request);	
		Integer userId = SessionManager.getCurrentUserId(request);
		return send(orgId, userId, toMobile, content, description);
    }
    
    /** 发送一条站内消息 */
    public static boolean send(Integer orgId, Integer userId, String toMobile, String content, String description) {
		return smsApiService.send(orgId, userId, toMobile, content, description);
    }
}
