/* 
* Created on 2014-10-27
* filename:  DriveForClientDriverEnum.java
* Description: 
* Copyright: Copyright(c)2014
* Company: BDC 
*/
package com.ava.enums;

/**
 * 
 * Title: Class DriveForClientDriverEnum.java
 * Description: 
 * Copyright: Copyright(c)2014
 * Company: BDC
 *
 * @author henggh
 * @version 0.1
 */
public enum ServiceResponseEnum {
	PROTOCOL_GATEWAY_SUCCESS("0","成功"),
	PROTOCOL_GATEWAY_ERROR_CODE_9999("9999","非法地址"),
	PROTOCOL_GATEWAY_ERROR_CODE_10000("10000","根据请求参数查无记录"),
	PROTOCOL_GATEWAY_ERROR_CODE_10001("10001","请求协议体不符合协议规范"),
	PROTOCOL_GATEWAY_ERROR_CODE_10002("10002","请求参数值的格式错误"),
	PROTOCOL_GATEWAY_ERROR_CODE_10003("10003","用户身份验证错误"),
	PROTOCOL_GATEWAY_ERROR_CODE_10004("10004","用户服务已停用，无法使用服务"),
	PROTOCOL_GATEWAY_ERROR_CODE_10005("10005","用户未激活"),
	PROTOCOL_GATEWAY_ERROR_CODE_10006("10006","服务器内部错误 "),
	PROTOCOL_GATEWAY_ERROR_CODE_10007("10007","其它(消息编码不存在)"),
	PROTOCOL_GATEWAY_ERROR_CODE_10008("10008","VIN错误"),
	PROTOCOL_GATEWAY_ERROR_CODE_10009("10009","IMSI错误"),
	PROTOCOL_GATEWAY_ERROR_CODE_10010("10010","设备ID错误"),
	PROTOCOL_GATEWAY_ERROR_CODE_10011("10011","SIM卡电话号码不存在"),
	PROTOCOL_GATEWAY_ERROR_CODE_10012("10012","SIM与销售数据不一致"),
	PROTOCOL_GATEWAY_ERROR_CODE_10013("10013","token错误或已过期"),
	PROTOCOL_GATEWAY_INFO_MESSAGE_10014("10014",""),
	PROTOCOL_GATEWAY_INFO_MESSAGE_10015("10015","box已绑定且与传入的vin不匹配"),
	PROTOCOL_GATEWAY_INFO_MESSAGE_10016("10016","VIN已绑定且与传输的设备编号不匹配"),
	PROTOCOL_GATEWAY_INFO_MESSAGE_10017("10017","设备与车辆绑定成功"),
	;
	private String code;
	private String desc;
	ServiceResponseEnum(String code,String desc){
		this.code=code;
		this.desc = desc;
	}
	public String getValue() {
		return this.code+";"+this.desc;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getDesc(){
		return this.desc;
	}
}
