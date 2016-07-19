package com.ava.gateway.dao;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.ava.gateway.domain.entity.BoxUpdateMessage;
import com.ava.gateway.domain.entity.CurrentGpsMessage;
import com.ava.gateway.domain.entity.GetCommonMessage;
import com.ava.gateway.domain.entity.ObdDismantleAlertMessage;
import com.ava.gateway.domain.entity.SetConfigMessage;
import com.ava.gateway.domain.entity.TBoxStatusMessage;

public interface IProtocolMessageDao {

	public void saveGetCommonMessage(GetCommonMessage getCommonMessage);
	
	public void saveTBoxStatusMessage(TBoxStatusMessage tBoxStatusMessage);
	
	public void saveSetConfigMessage(SetConfigMessage setConfigMessage);
	
	public void saveCurrentGpsMessage(CurrentGpsMessage currentGpsMessage);
	
	public void saveBoxUpdateMessage(BoxUpdateMessage boxUpdateMessage);
	
	public void saveObdDismantleAlertMessage(ObdDismantleAlertMessage obdDismantleAlertMessage);
	
	public Queue<String> findMessage(Map<String,Object> argMap,String taskId);
	

	/**
	 * 获取点火熄火列表，并按消息时间排序
	 *
	 * @author wangchao
	 * @version 
	 * @param vin
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Object> getIgnitionMessageList (String vin, String startTime, String endTime);
	
	/**
	 * 获取指定gps坐标信息
	 *
	 * @author wangchao
	 * @version 
	 * @param vin
	 * @param startTime
	 * @param endTime
	 * @param orderBy
	 * @return
	 */
	public CurrentGpsMessage getNearlyGpsPointMessage (String vin, String startTime, String endTime, String orderBy);
	
	public String findLastGpsDate(Map<String,Object> argMap);
}
