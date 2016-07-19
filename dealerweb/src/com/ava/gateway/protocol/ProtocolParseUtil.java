package com.ava.gateway.protocol;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ava.dealer.service.IUserTokenService;
import com.ava.enums.ServiceResponseEnum;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.gateway.domain.vo.ProtocolParseRes;
import com.ava.gateway.domain.vo.TokenRes;
import com.ava.gateway.facade.IProtocolParseBusinessFacade;
import com.ava.resource.GlobalConfig;

@Controller
@RequestMapping(value="/api/web2gateway")
public class ProtocolParseUtil{
	
	@Resource(name="testDriver.web2gatewayFacade")
	IProtocolParseBusinessFacade protocolParseBusinessFacade;
	
	@Autowired
	private IUserTokenService userTokenService;
	
	private Logger loger=Logger.getLogger(ProtocolParseUtil.class);
    	
	@RequestMapping(value="/handleMessage", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String,Object> handleMessage(@RequestParam("msg") String msg){
		try{
    		ProtocolParseRes responseData=protocolParseBusinessFacade.handleMessage(msg);
    		return writeMapMessage1(ServiceResponseEnum.PROTOCOL_GATEWAY_SUCCESS.getCode(),ServiceResponseEnum.PROTOCOL_GATEWAY_SUCCESS.getDesc(),responseData);
    	}catch( ProtocolParseBusinessException pe){
			loger.error("handleMessage--------原始消息信息:"+msg+";异常代码:"+pe.getResultCode()+";消息内容:"+pe.getDesc());
			if(pe.getResultEnum()==ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10017){
    			return writeMapMessage1(ServiceResponseEnum.PROTOCOL_GATEWAY_SUCCESS.getCode(),"绑定成功",null);
    		}else{
    			return writeMapMessage1(pe.getResultCode(),pe.getDesc(),null);
    		}
		}catch(Exception e){
    		if(null!=e.getCause()){
    			loger.error("handleMessage--------原始消息信息:"+msg+";异常信息:"+e.getCause().getClass() + "," + e.getCause().getMessage());
    		}else{
    			e.printStackTrace();
    			loger.error("handleMessage--------原始消息信息:"+msg+";异常信息:"+e.getMessage());
    		}
    		return writeMapMessage1(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006.getCode(),ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006.getDesc(),null);
    	}
	}
    
	@RequestMapping(value="/refreshToken", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> refreshToken(@RequestParam("userName") String userName, @RequestParam("accessToken") String accessToken){
		String newToken=userTokenService.refreshUserToken(userName, accessToken);
		Map<String,Object> map=new HashMap<String,Object>();
		if(null==newToken || "".equals(newToken.trim())){
			map.put("resultCode", ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10013.getCode());
			map.put("desc", ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10013.getDesc());
			map.put("data", null);
		}else{
			TokenRes tokenRes=new TokenRes();
			map.put("resultCode", "0");
			map.put("desc", "成功获取新token");
			tokenRes.setToken(newToken);
			map.put("data", tokenRes);
		}
		return map;
	}
	
	@RequestMapping(value="/reloadGlobalConfig", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String reloadGlobalConfig(){
		loger.info("-- GlobalConfig reload start");
		GlobalConfig.reloadProperties();
		loger.info("-- GlobalConfig reload end");
		return "{'result':1}";
	}
		
/*	private @ResponseBody Map<String,Object> writeMapMessage(String errorCode ,String desc, ProtocolParseRes responseData){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resultCode", errorCode);
		map.put("desc", desc);
		map.put("data", responseData);
		return map;
		//writeMap(map);
	}*/
	
	private @ResponseBody Map<String,Object> writeMapMessage1(String errorCode ,String desc, ProtocolParseRes responseData){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("resultCode", errorCode);
		map.put("desc", desc);
		map.put("data", responseData);
		return map;
		//writeMap(map);
	}
}
