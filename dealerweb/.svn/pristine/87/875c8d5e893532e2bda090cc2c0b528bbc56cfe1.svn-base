package com.ava.gateway.business;

import com.ava.gateway.domain.vo.BaseDataReq;
import com.ava.gateway.domain.vo.GetCommonDataReq;
import com.ava.gateway.domain.vo.GpsMessageDataReq;
import com.ava.gateway.domain.vo.HeaderMessage;
import com.ava.gateway.domain.vo.ProtocolParseRes;
import com.ava.gateway.domain.vo.SetConfigDataReq;
import com.ava.gateway.domain.vo.TBoxStatusDataReq;

public interface IProtocolParseBusinessService {
	public ProtocolParseRes  handleGetCommonDataRes(HeaderMessage headerMessage,GetCommonDataReq getCommonDataReq);
	public ProtocolParseRes handleTBoxStatusDataReq(HeaderMessage headerMessage,TBoxStatusDataReq tBoxStatusDataReq);
	public ProtocolParseRes handleSetConfigDataReq(HeaderMessage headerMessage,SetConfigDataReq setConfigDataReq);
	public ProtocolParseRes handleGpsMessageDataReq(HeaderMessage headerMessage,GpsMessageDataReq gpsMessageDataReq);
	public ProtocolParseRes handleBoxUpdate(HeaderMessage headerMessage,BaseDataReq baseDataReq);
	public ProtocolParseRes handleObdDismantleAlertMessageReq(HeaderMessage headerMessage,GpsMessageDataReq gpsMessageDataReq);
	
	/**
	 * 修复指定区间的点火报文信息，由于网关过滤或其他原因导致的点火报文缺失。
	 *
	 * @author wangchao
	 * @version 
	 * @param vin
	 * @param startTime
	 * @param endTime
	 */
	public void repairIgnitionAndFlameoutMessage(String vin, String startTime, String endTime);
	
	/**
	 * 记录异常日志
	 * @author tangqingsong
	 * @version 
	 * @param headerMessage
	 */
	public void saveTaskLog(HeaderMessage headerMessage,String logCode);
}
