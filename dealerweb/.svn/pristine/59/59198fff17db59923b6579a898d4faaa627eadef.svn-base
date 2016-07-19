package com.ava.dao;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.ShortMessage;

public interface IShortMessageDao {
	
	public List findByPage(TransMsg msg, String additionalCondition);
	/**	持久化站内消息对象，同时给OBC_Server发送消息同步用户新邮件数量	*/
	public void edit(ShortMessage shortMessage);

	public void delete(Integer id);
	
	public ShortMessage get(Integer id);
		
	public void deleteByFromUser(Integer shortMessageId);
	
	public void deleteByToUser(Integer shortMessageId);
	
	public int executeUpdate(String hql);
	
	public int executeQueryInt(String hql);
	
	public ShortMessage getByTitle(String title);
	
	
}
