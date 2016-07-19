package com.ava.baseSystem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.ava.base.dao.hibernate.QueryParameter;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IShortMessageService;
import com.ava.dao.IShortMessageDao;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.ShortMessage;
import com.ava.domain.entity.User;
import com.ava.resource.GlobalConfig;
import com.ava.resource.SmsManager;
import com.ava.resource.cache.CacheShortMessageManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.DateTime;
import com.ava.util.RegexUtil;
import com.ava.util.TypeConverter;

@Service
public class ShortMessageService implements IShortMessageService{
	
	@Autowired
	private IShortMessageDao shortMessageDao;
	
	@Autowired
	private IUserDao userDao;
	
	//上次访问checkNew的时间 
	private static Date lastCheckNewTime;
	
	/**
	 * 消息类型
	 */
	public static List<String> shorMessageTypeList = new LinkedList<String>();
	static{
		shorMessageTypeList.add("非工作时间违规");
		shorMessageTypeList.add("越界行为");
		shorMessageTypeList.add("用户身份验证错误");
		shorMessageTypeList.add("拔出报警");
	}
	
	public ResponseData displaySend(Integer companyId, Integer userId, String toLoginName, Integer replyShortMessageId){
		ResponseData rd = new ResponseData(0);
		Map data = new HashMap();
		rd.setData(data);
		
		//找内部联系人
		Map parameters = new HashMap();
//		parameters.put("companyId", companyId);
//		QueryParameter para = new QueryParameter("id", QueryParameter.OPERATOR_NEQ, userId);
//		parameters.put("id", para);
		List mateUserList = userDao.find(parameters, " ORDER BY loginName ASC");
		data.put("mateUserList", mateUserList);
		
		ShortMessage shortMessage = new ShortMessage();
		
		if (toLoginName != null && toLoginName.length() > 0){
			shortMessage.setToLoginName(toLoginName);
			//显示用户头像
			User user = CacheUserManager.getUser(toLoginName);
			if (user == null) {
				rd.setCode(-1);
				rd.setMessage("未找到相应用户");
				return rd;
			} else {
				data.put("user", user);
			}
		}
		
		if (replyShortMessageId != null && replyShortMessageId.intValue() > 0){
			//是回复邮件，则初始化标题和内容，即在原标题和原内容前增加"回复："
			ShortMessage oldShortMessage = shortMessageDao.get(replyShortMessageId);
			shortMessage.setTitle("回复：" + oldShortMessage.getTitle());
			StringBuffer sb = new StringBuffer();
			char br = 10;	//换行符
			sb.append(br).append(br).append(br).append(br);
			sb.append("---------------------------------------------------");
			sb.append(br);
			sb.append("在 ");
			sb.append(DateTime.toNormalDateTime(oldShortMessage.getCreationTime()));
			sb.append(" 发送的上一封邮件中，");
			sb.append(oldShortMessage.getFromLoginName());
			sb.append(" 写到：");
			sb.append(br);
			sb.append(RegexUtil.filterSymbols(oldShortMessage.getContent()));
			shortMessage.setContent(sb.toString());
			
		}
		
		data.put("shortMessage", shortMessage);
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData sendWrite(ShortMessage shortMessage, Integer userId, Company currentOrg, 
			String fromLoginName, String isSmsShortMessageSend, String nickName, HttpServletRequest request){
		ResponseData rd = new ResponseData(0);
		
		if (shortMessage == null) {
			rd.setCode(0);
			rd.setMessage("shortMessage为空");
			return rd;
		}
		
		if (shortMessage.getTitle() == null || shortMessage.getTitle().trim().length()<1){
			rd.setCode(-1);
			rd.setMessage("主题必须填写！");
			return rd;
		}
		
		String toLoginName = shortMessage.getToLoginName();
		if (toLoginName == null || toLoginName.trim().length()<1) {
			rd.setCode(-2);
			rd.setMessage("收件人必须填写！");
			return rd;
		}
		
		boolean isAllSended = true;
		StringBuffer sb = new StringBuffer("站内消息未全部发送成功，以下用户名未找到：");
		String[] toLoginNameArray = toLoginName.split(";");
		if (toLoginNameArray != null && toLoginNameArray.length>0){
			StringBuffer sbToMobiles = new StringBuffer("");
			//开始循环发送站内消息
			for (int i=0; i<toLoginNameArray.length; i++){
				//Integer toUserId = CacheUserManager.getUserId(toLoginNameArray[i]);
				User toUser = CacheUserManager.getUser(toLoginNameArray[i]);
				if (toUser == null){
					//输入的loginName错误，找不到用户
					if (isAllSended){
						//代表第一个发现的未找到用户名
						sb.append(toLoginNameArray[i]);
					}else{
						sb.append(" ; " + toLoginNameArray[i]);
					}
					isAllSended = false;
					continue;
				}
				/*if ( currentOrg.getId().intValue() == toUser.getCompanyId().intValue()){
					//如果是内部联系人，则需要获取手机号来进行短信提醒
					if (toUser.getMobile() != null && toUser.getMobile().trim().length() > 0){
						sbToMobiles.append(toUser.getMobile()).append(";");
					}
				}*/
				
				ShortMessage newShortMessage = new ShortMessage();
				newShortMessage.setFromUserId(userId);
				newShortMessage.setFromLoginName(fromLoginName);
				newShortMessage.setToUserId(toUser.getId());
				newShortMessage.setToLoginName(toLoginNameArray[i]);
				newShortMessage.setCreationTime(new java.util.Date());
				newShortMessage.setTitle(shortMessage.getTitle());
				newShortMessage.setContent(shortMessage.getContent());
				newShortMessage.setDeleteByfromUser(0);
				newShortMessage.setDeleteBytoUser(0);
				newShortMessage.setIsreaded(0);
				shortMessageDao.edit(newShortMessage);	//持久化站内消息对象，该方法同时自动给OBC_Server发送消息同步用户新邮件数量
			}
			/**
			 * 判断是否需要手机短信提醒，一共要满足三个条件
			 * 1：组织允许站内消息发送的短信提醒
			 * 2：发件人选择了短信提醒的复选框按钮
			 * 3：至少有一个收件人填写了手机号码
			 */
			if ( currentOrg != null && currentOrg.isSmsShortMessageSend()){
				//组织允许站内消息发送的短信提醒
				if ( "true".equalsIgnoreCase(isSmsShortMessageSend) ){
					//发件人选择了短信提醒的复选框按钮
					if (sbToMobiles.toString().length() > 0){
						//至少有一个收件人填写了手机号码，则进行短信提醒
						String smsContent = "你有一封新的站内消息，发件人：" + nickName + 
							"。标题：" + shortMessage.getTitle();
						SmsManager.batchSend(request, sbToMobiles.toString(), smsContent, "站内消息内部联系人短信通知");
					}
				}
			}
		}
		if (isAllSended){
			rd.setCode(1);
			rd.setMessage("发送成功！");
			return rd;
		}else{
			toLoginName = sb.toString();
			rd.setFirstItem(toLoginName);
			rd.setCode(2);
			return rd;
		}
	}
	
	public ResponseData displayOutboxList(TransMsg transMsg, Integer userId){
		ResponseData rd = new ResponseData(0);
		
//		transMsg.setPageSize(10);
		transMsg.put("fromUserId", userId);	//当前用户即发件箱的发送用户
		transMsg.put("deleteByfromUser", 0);
		List outboxList = shortMessageDao.findByPage(transMsg, " ORDER BY id DESC");
        
        rd.setFirstItem(outboxList);
        rd.setCode(1);
		return rd;
	}
	
	public ResponseData displayOutboxDetail(Integer shortMessageId){
		ResponseData rd = new ResponseData(0);
		Map data = new HashMap();
		rd.setData(data);
		
		ShortMessage shortMessage = shortMessageDao.get(shortMessageId);
		if (shortMessage == null){
			rd.setCode(0);
			return rd;
		}
		data.put("shortMessageForOutbox", shortMessage);
		
		//显示用户头像
		Integer userId = shortMessage.getToUserId();
		User user = CacheUserManager.getUser(userId);
		data.put("user", user);
		
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData deleteOutbox(Integer shortMessageId){
		ResponseData rd = new ResponseData(0);
		if (shortMessageId  != null && shortMessageId.intValue() > 0) {
			shortMessageDao.deleteByFromUser(shortMessageId);			
		}
		
		rd.setCode(1);
		return rd;
	}

	
	public ResponseData deleteOutboxAll(Integer[] ids) {
		ResponseData rd = new ResponseData(0);
		if (ids == null) {
			rd.setCode(0);
			return rd;
		}
		
		for (int i = 0; i < ids.length; i++) {
			shortMessageDao.deleteByFromUser(ids[i]);
		}
		
		rd.setCode(1);
		return rd;
	}
	
	//收件箱
	public ResponseData displayInboxList(TransMsg transMsg, Integer currentUserId ) {
		ResponseData rd = new ResponseData(0);
		Map data = new HashMap();
		rd.setData(data);
		
//		transMsg.setPageSize(13); 
		transMsg.put("toUserId", currentUserId);	//当前用户即收件箱的接收用户
		transMsg.put("deleteBytoUser", 0);
		List inboxList = shortMessageDao.findByPage(transMsg, " ORDER BY id DESC");
        data.put("inboxList", inboxList);
        
        String hql = "select count(id) from ShortMessage where isreaded=0 AND toUserId=" + currentUserId;
        int inboxCount = shortMessageDao.executeQueryInt(hql);
        rd.setFirstItem(inboxCount);
        data.put("inboxCount", inboxCount);
        
        rd.setCode(1);
		return rd;
	}
	
	
	/**
	 * 获取导出消息列表 
	 * @author tangqingsong
	 * @version 
	 * @param transMsg
	 * @param currentUserId
	 * @return
	 */
	public List<ShortMessage> getExportShortMessageList(TransMsg transMsg, Integer currentUserId ) {
		transMsg.put("toUserId", currentUserId);	//当前用户即收件箱的接收用户
		transMsg.put("deleteBytoUser", 0);
		transMsg.setPageSize(Integer.MAX_VALUE);
		List<ShortMessage> shortMessageList = shortMessageDao.findByPage(transMsg, " ORDER BY id DESC");
		//解析数据
		parseMessageType(shortMessageList);
		
		return shortMessageList;
	}
	
	
	/**
	 * 解析出消息类型、vin、设备号等数据 
	 * @author tangqingsong
	 * @version 
	 * @param shortMessageList
	 */
	private void parseMessageType(List<ShortMessage> shortMessageList){
		if (shortMessageList != null && shortMessageList.size() > 0) {
			for (int i = 0; i < shortMessageList.size(); i++) {
				ShortMessage shortMessage = shortMessageList.get(i);
				String content = shortMessage.getContent();
				//1、解析出消息类型
				for(String type : shorMessageTypeList){
					if(shortMessage.getTitle().contains(type)){
						shortMessage.setMessageType(type);
						//解析 设备号和vin
						String[] contents  = content.split("】VIN:【");
						//解析设备号
						shortMessage.setSerialNumber(getArrayValueByTry(contents,0).replace("设备号:【", ""));
						//解析vin
						shortMessage.setVin(getArrayValueByTry(contents,1).replace("】", ""));
						break;
					}else if(shortMessage.getTitle().contains("拆除报警")){
						shortMessage.setMessageType("拔出报警");
						//解析 设备号和vin
						String[] contents  = content.split(";");
						//解析设备号
						shortMessage.setSerialNumber(getArrayValueByTry(contents,1).replace("设备号:", ""));
						//解析vin
						shortMessage.setVin(getArrayValueByTry(contents,0).replace("有车辆发生拆除报警，信息如下：vin:", ""));
						break;
					}else{
						shortMessage.setMessageType("其他");
					}
				}				
			}
		}
	}
	
	/**
	 * 从数组安全的取值
	 * @author tangqingsong
	 * @version 
	 * @param contents
	 * @param index
	 * @return
	 */
	private String getArrayValueByTry(String[] contents,int index){
		try{
			if(contents.length<=index){
				return "";
			}
			return contents[index];
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public ResponseData displayInboxDetail(Integer shortMessageId, Integer userId){
		ResponseData rd = new ResponseData(0);
		
		ShortMessage shortMessage = shortMessageDao.get(shortMessageId);
		if (shortMessage == null){
			rd.setCode(0);
			return rd;
		}
		
		if (shortMessage.getIsreaded()==null || shortMessage.getIsreaded().intValue()!=1){
			shortMessage.setIsreaded(1);
			shortMessageDao.edit(shortMessage);	//持久化站内消息对象，该方法同时自动给OBC_Server发送消息同步用户新邮件数量
			//清空用户新站内消息缓存，让他重新读取
			CacheShortMessageManager.remove(userId);
		}
		rd.setFirstItem(shortMessage);
		rd.put("shortMessageForInbox", shortMessage);
		
		//显示用户头像
		Integer formUserId = shortMessage.getFromUserId();
		User user = CacheUserManager.getUser(formUserId);
		rd.setFirstItem(user);
		rd.put("user", user);
		
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData deleteInbox(Integer shortMessageId) {
		ResponseData rd = new ResponseData(0);
		if (shortMessageId  != null && shortMessageId.intValue() > 0) {
			shortMessageDao.deleteByToUser(shortMessageId);
		}
		
		rd.setCode(1);
		return rd;
	}
	
	public ResponseData deleteInboxAll(Integer[] ids){
		ResponseData rd = new ResponseData(0);
		if (ids == null) {
			rd.setCode(0);
			return rd;
		}
		
		for (int i = 0; i < ids.length; i++) {
			shortMessageDao.deleteByToUser(ids[i]);
		}
		rd.setCode(1);
		return rd;
	}
	
	private Integer getNewShortMessageCount1(Integer userId){
		if (userId == null){
			return 0;
		}
		String hql = "select count(id) from ShortMessage where isReaded=0 AND toUserId=" + userId;
		return userDao.executeQueryInt(hql);
	}

	@Override
	public ResponseData checkNewShortMessage(Integer userId) {
		//如果lastCheckNewTime为空 ,从数据库去取,存到缓存里,
		//否则不为空,且间隔大于6s,则从数据库去取，并更新缓存.
		//否则不为空，且间隔小于等于6s，从缓存中获取.
		ResponseData rd = new ResponseData(0);
		Integer newShortMessageCount = 0;
		if(lastCheckNewTime==null){
			String hql = "select count(id) from ShortMessage where isReaded=0 AND toUserId=" + userId;
    		newShortMessageCount = shortMessageDao.executeQueryInt(hql);
    		CacheShortMessageManager.put(userId, newShortMessageCount);
		}else if(lastCheckNewTime!=null){
			long secondeIntevel= DateTime.getSecondDifference(new Date(), lastCheckNewTime);
			String intevel= GlobalConfig.getString("ShortMessageIntevel");
			if(secondeIntevel>TypeConverter.toInteger(intevel))
			{
				String hql = "select count(id) from ShortMessage where isReaded=0 AND toUserId=" + userId;
	    		newShortMessageCount = shortMessageDao.executeQueryInt(hql);
	    		CacheShortMessageManager.put(userId, newShortMessageCount);
			}else{
				newShortMessageCount= CacheShortMessageManager.getNewShortMessageCount(userId);
			}
			
		}
		rd.setCode(1);
		rd.setFirstItem(newShortMessageCount);
		return rd;
	}

	/**
	 * 站内信 TODO 需优化从缓存里读取
	 * @param vin
	 * @param uniqueId
	 * @param title
	 */
	public void sendMessage(String vin, String uniqueId, String message){
		String title = vin + message;
		ShortMessage newShortMessage = shortMessageDao.getByTitle(title);
		if (newShortMessage == null) {
			newShortMessage = new ShortMessage();
			newShortMessage.setFromUserId(1);
			newShortMessage.setFromLoginName("系统");
			newShortMessage.setToUserId(10);
			newShortMessage.setToLoginName("运营管理员");
			newShortMessage.setCreationTime(new java.util.Date());
			newShortMessage.setTitle(title);
			newShortMessage.setContent("设备号:【" + uniqueId + "】VIN:【" + vin + "】" );
			newShortMessage.setDeleteByfromUser(0);
			newShortMessage.setDeleteBytoUser(0);
			newShortMessage.setIsreaded(0);
			shortMessageDao.edit(newShortMessage);
		}
	}
}
