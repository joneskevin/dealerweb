package com.ava.gateway.protocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ava.gateway.business.IProtocolParseBusinessService;
import com.ava.gateway.dao.IProtocolMessageDao;
import com.ava.gateway.facade.IProtocolParseBusinessFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SpringContext;

@Controller
@RequestMapping(value="/api/web2gateway")
public class ProtocolParseTestUtil{
	
	@Resource(name="testDriver.web2gatewayFacade")
	IProtocolParseBusinessFacade protocolParseBusinessFacade;
	
	@Resource(name="testDriver.web2gatewayService")
	IProtocolParseBusinessService protocolParseBusinessService;

	private Logger loger=Logger.getLogger(ProtocolParseTestUtil.class);
    	
	@RequestMapping(value="/handleMessage/test", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public void handleMessage(){
		try{
			IProtocolMessageDao messageDao = SpringContext.getBean("testDriver.web2gatewayDao");
			Map<String,Object> argMap = new HashMap<String, Object>();
			argMap.put("vin","LSVG066R2F2057396");
			//argMap.put("serialNumber","41031901322");
			argMap.put("startDate","2015-07-21 14:41:27");
			argMap.put("endDate","2015-07-21 15:02:16");
//			List<String> jsonList = messageDao.findMessage(argMap,GlobalConstant.TEST_DRIVE_REHANDLE_CODE);
//			for(String json:jsonList){
//				//开始处理
//	    		protocolParseBusinessFacade.handleMessage(json);
//			}
			System.out.println("处理完成.....");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
	}
	
	/**
	 * 用于补指定车辆的点火报文
	 *
	 * @author wangchao
	 * @version 
	 * @param vin
	 * @param startDate
	 * @param endDate
	 */
	@RequestMapping(value="/repairMessage/fireOn", method = RequestMethod.GET)
	@ResponseBody
	public String repairMessageFireOn( String vin, String startTime, String endTime){
		loger.info(" repairMessageFireOn - start vin=["+vin+"] startTime=["+startTime+"] endTime=["+endTime+"]");
		if( StringUtils.isEmpty(vin)){
			return "{'result':0,'msg':'vin is empty'}";
		}
		if( StringUtils.isEmpty(startTime)){
			return "{'result':0,'msg':'startTime is empty'}";
		}
		if( StringUtils.isEmpty(endTime)){
			return "{'result':0,'msg':'endTime is empty'}";
		}
		
		try{
			protocolParseBusinessService.repairIgnitionAndFlameoutMessage(vin, startTime, endTime);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
		loger.info(" repairMessageFireOn - end");
		return "{'result':1}";
	}
	
	

}
