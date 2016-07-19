package com.ava.api.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ava.api.service.IUserApiService;
import com.ava.api.vo.UserApiVo;
import com.ava.base.controller.Base4MvcController;
import com.ava.base.domain.ResponseData;

@Controller
@RequestMapping("/api/user")
public class UserApiController extends Base4MvcController {
	@Autowired
	private IUserApiService userApiService;
	
	/**
	 * 手机端用户登录,进行有效性验证并处理成UserApiVo对象返回给客户端
	 */
    @RequestMapping(value="/login", method = RequestMethod.GET) 
	public void login(String loginName, String encryptedPassword) {
		ResponseData rd = new ResponseData(0);	
		try {
			loginName = java.net.URLDecoder.decode(loginName, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		UserApiVo user = new UserApiVo(loginName, encryptedPassword);
		//登陆验证
		rd = userApiService.login(getRequest(), getResponse(), user);
		if(rd.getCode() == 1){//登录成功
			UserApiVo userVo = (UserApiVo) rd.getFirstItem();
			rd.setFirstItem(userVo);
			
			rd.setCode(1);
			rd.setMessage("登陆成功");
		}

		writeRd(rd); 
	}
	
    @RequestMapping(method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)  
	public void createUser(@PathVariable Integer userId) {
		ResponseData responseData = new ResponseData(0);
		
		UserApiVo user = userApiService.getUser(userId);
		if (user != null){
			responseData.setFirstItem(user);

			responseData.setCode(1);

			getResponse().setHeader("Location", "/" + user.getId());
		}else{
			responseData.setCode(-1);
			responseData.setMessage("编号不能为空");
		}

		writeRd(responseData); 
	}
    
    @RequestMapping(value="/{userId}", method = RequestMethod.GET) 
	public void getUser(@PathVariable Integer userId) {
		ResponseData responseData = new ResponseData(0);
		
		UserApiVo user = userApiService.getUser(userId);
		if (user != null){
			responseData.setFirstItem(user);

			responseData.setCode(1);
		}else{
			responseData.setCode(-1);
			responseData.setMessage("编号不能为空");
		}
		writeRd(responseData); 
	}
    
    @RequestMapping(value="/map/{userId}", method = RequestMethod.GET) 
	public void getUserMap(@PathVariable Integer userId) {
		ResponseData responseData = new ResponseData(0);
		
		UserApiVo user = userApiService.getUser(userId);
		if (user != null){
			HashMap userMap = new HashMap();
			userMap.put("user", user);
			
			responseData.setData(userMap);
			responseData.setCode(1);
		}else{
			responseData.setCode(-1);
			responseData.setMessage("编号不能为空");
		}
		writeRd(responseData); 
	}
    
    @RequestMapping(value="/{userId}", method=RequestMethod.PUT)  
	public void updateUser(@PathVariable Integer userId) {
		ResponseData responseData = new ResponseData(0);
		
		UserApiVo user = userApiService.getUser(userId);
		if (user != null){
			responseData.setFirstItem(user);

			responseData.setCode(1);
		}else{
			responseData.setCode(-1);
			responseData.setMessage("编号不能为空");
		}
		writeRd(responseData); 
	}
    
    @RequestMapping(value="/{userId}", method=RequestMethod.DELETE)  
	public void deleteUser(@PathVariable Integer userId) {
		ResponseData responseData = new ResponseData(0);
		
		UserApiVo user = userApiService.getUser(userId);
		if (user != null){
			responseData.setFirstItem(user);

			responseData.setCode(1);
		}else{
			responseData.setCode(-1);
			responseData.setMessage("编号不能为空");
		}
		writeRd(responseData); 
	}
}
