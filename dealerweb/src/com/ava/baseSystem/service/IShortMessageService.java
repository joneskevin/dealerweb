package com.ava.baseSystem.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.ShortMessage;

public interface IShortMessageService {
	
	/**
	 * 显示发送站内消息页面
	 * @param companyId
	 * @param userId
	 * @param toLoginName
	 * @param replyShortMessageId
	 * @return
	 */
	public ResponseData displaySend(Integer companyId, Integer userId, String toLoginName, Integer replyShortMessageId);
	
	/**
	 * 发送站内消息
	 * @param shortMessage
	 * @param userId
	 * @param currentOrg
	 * @param fromLoginName
	 * @param isSmsShortMessageSend
	 * @param nickName
	 * @param request
	 * @return
	 */
	public ResponseData sendWrite(ShortMessage shortMessage, Integer userId, Company currentOrg, 
			String fromLoginName, String isSmsShortMessageSend, String nickName, HttpServletRequest request);
	
	/**
	 * 发件箱
	 * @param transMsg
	 * @param userId
	 * @return
	 */
	public ResponseData displayOutboxList(TransMsg transMsg, Integer userId);

	/**
	 * 删除发件箱中一条站内消息
	 * @param shortMessageId
	 * @return
	 */
	public ResponseData deleteOutbox(Integer shortMessageId);
	
	/**
	 * 删除发件箱中多条条站内消息
	 * @param shortMessageId
	 * @return
	 */
	public ResponseData deleteOutboxAll(Integer[] ids);
	
	/**
	 * 在发件箱中查看站内消息的详细信息
	 * @param shortMessageId
	 * @return
	 */
	public ResponseData displayOutboxDetail(Integer shortMessageId);
	
	/**
	 * 发件箱
	 * @param transMsg
	 * @param currentUserId
	 * @return
	 */
	public ResponseData displayInboxList(TransMsg transMsg, Integer currentUserId);
	
	/**
	 * 在收件箱中查看站内消息的详细信息
	 * @param shortMessageId
	 * @param userId
	 * @return
	 */
	public ResponseData displayInboxDetail(Integer shortMessageId, Integer userId);

	/**
	 * 删除发件箱中一条站内消息
	 * @param shortMessageId
	 * @return
	 */
	public ResponseData deleteInbox(Integer shortMessageId);
	
	/**
	 * 删除发件箱中多条站内消息
	 * @param ids
	 * @return
	 */
	public ResponseData deleteInboxAll(Integer[] ids);

	/**
	 * 获取新消息数量
	 * @param userId
	 * @return
	 */
	public ResponseData checkNewShortMessage(Integer userId);
	
	/**
	 * 站内信 发送，TODO 需优化从缓存里读取
	 * @param vin
	 * @param uniqueId
	 * @param title
	 */
	public void sendMessage(String vin, String uniqueId, String message);
	
	/**
	 * 获取导出消息列表 
	 * @author tangqingsong
	 * @version 
	 * @param transMsg
	 * @param currentUserId
	 * @return
	 */
	public List<ShortMessage> getExportShortMessageList(TransMsg transMsg, Integer currentUserId );
}
