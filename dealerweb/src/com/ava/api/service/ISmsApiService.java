package com.ava.api.service;


public interface ISmsApiService {
	/**	发送一条站内消息*/
	public boolean send(Integer orgId, Integer userId, String toMobile, String content, String description);

}
