package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IShortMessageDao;
import com.ava.domain.entity.ShortMessage;

@Repository
public class ShortMessageDao  implements IShortMessageDao{
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	public List findByPage(TransMsg msg, String additionalCondition){
		List<Object> objList = (List<Object>) hibernateDao.findByPage("ShortMessage", msg,
				additionalCondition);
		return objList;
		
	}

	/**	持久化站内消息对象，同时给OBC_Server发送消息同步用户新邮件数量	*/
	public void edit(ShortMessage shortMessage){
		if (shortMessage == null){
			return;
		}
		hibernateDao.edit(shortMessage);
	}
	
	public void delete(Integer id){
		hibernateDao.delete(ShortMessage.class, id);
	}
	
	public void deleteByFromUser(Integer shortMessageId){
		ShortMessage shortMessage = this.get(shortMessageId);
		if (shortMessage != null){
			if(shortMessage.getDeleteBytoUser().intValue()==1){
				hibernateDao.delete(shortMessage);
			}else{
				shortMessage.setDeleteByfromUser(1);
				this.edit(shortMessage);
			}
		}
	}
	
	public void deleteByToUser(Integer shortMessageId){
		ShortMessage shortMessage = this.get(shortMessageId);
		if (shortMessage != null){
			if(shortMessage.getDeleteByfromUser().intValue()==1){
				hibernateDao.delete(shortMessage);
			}else{
				shortMessage.setDeleteBytoUser(1);	
				shortMessage.setIsreaded(1);
				this.edit(shortMessage);
			}
		}
		
	}
	
	public ShortMessage get(Integer id){
		ShortMessage shortMessage = (ShortMessage) hibernateDao.get(ShortMessage.class, id);
		return shortMessage;
		
	}

	@Override
	public int executeUpdate(String hql) {
		return hibernateDao.executeUpdate(hql);
	}

	@Override
	public int executeQueryInt(String hql) {
		return hibernateDao.executeQueryInt(hql);
	}
	
	@Override
	public ShortMessage getByTitle(String title) {
		if (title == null || title.length() < 1) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("title", title);
		List<ShortMessage> objs = hibernateDao.find("ShortMessage", parameters, "");
		if (objs != null && objs.size() > 0) {
			return (ShortMessage) objs.get(0);
		}
		return null;
	}
	
}
