package com.ava.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ava.api.service.IUserApiService;
import com.ava.base.domain.ResponseData;
import com.ava.base.controller.Base4MvcController;
import com.ava.resource.SessionManager;

@Controller
@RequestMapping("/api/tempPasswordNotice")
public class TempPasswordNoticeApiController extends Base4MvcController {
	@Autowired
	private IUserApiService userApiService;

	/**作为RESTful风格的create的例子，没有验证测试*/
    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
	public void createNotice(@RequestParam("userId") Integer userId) {
		ResponseData responseData = new ResponseData(0);
		
		String toEmail = getStringParameter("toEmail");
		String toMobile = getStringParameter("toMobile");
		
		int result = userApiService.sendTempPassowrd(userId, null, toMobile, toEmail);
		if (result == 1){
			responseData.setCode(1);
		}else{
			responseData.setCode(-1);
			responseData.setMessage("发送失败");
		}
		
		getResponse().setHeader("Location", "/" + 1);
		writeRd(responseData); 
	}
    
    @RequestMapping(value="/send.vti", method = RequestMethod.GET) 
	public void send(@RequestParam("toEmail") String toEmail) {
		ResponseData responseData = new ResponseData(0);
		
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		int result = userApiService.sendTempPassowrd(userId, null, null, toEmail);
		if (result == 1){
			responseData.setCode(1);
		}else{
			responseData.setCode(-1);
			responseData.setMessage("发送短信失败");
		}
		
		writeRd(responseData); 
	}
}
