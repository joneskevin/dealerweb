package com.ava.back.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.ITimerTaskAtMinuteService;
import com.ava.dao.IShortMessageDao;
import com.ava.dao.IUserDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.ShortMessage;
import com.ava.domain.entity.User;
import com.ava.resource.SmsManager;

@Service
public class TimerTaskAtMinuteService implements ITimerTaskAtMinuteService{

	@Autowired
	private IShortMessageDao shortMessageDao;

	@Autowired
	private IUserDao userDao;

	/**	待办事宜短信提醒	*/
	public boolean processPendingTask(){
		//查找出已经到提醒时间的待办事宜
		//PendingTask pendingTask;
		User user;
		HashMap parameters = new HashMap();
		parameters.put("needRemind", 1);
		List pendingTaskList = null;//pendingTaskDao.find(parameters, " AND remindTime<'" + DateTime.getNormalDateTime() +"'", 999);
		if (pendingTaskList != null && pendingTaskList.size() > 0){
			Iterator itor = pendingTaskList.iterator();
			while (itor.hasNext()) {
				//pendingTask = (PendingTask) itor.next();
				//pendingTask.setNeedRemind(0);
				//pendingTaskDao.edit(pendingTask);
				user = null;//CacheUserManager.getUser(pendingTask.getUserId());
				Company org = null;//org = CacheCompanyManager.getCompany(pendingTask.getOrgId());
				if (user == null || org == null){
					continue;
				}
				//发送一封站内消息给用户
				ShortMessage newShortMessage = new ShortMessage();
				newShortMessage.initSystemShortMessage();
				newShortMessage.setDeleteByfromUser(1);
				newShortMessage.setToUserId(user.getId());
				newShortMessage.setToLoginName(user.getLoginName());
				//newShortMessage.setTitle("一项需要处理的待办事宜：" + pendingTask.getTitle());
				//newShortMessage.setContent(pendingTask.getDescription());
				shortMessageDao.edit(newShortMessage);	//持久化站内消息对象，该方法同时自动给OBC_Server发送消息同步用户新邮件数量
				
				//站内消息发送完毕后再短信通知
				if (org.isSmsPendingTask() && user.getMobile() != null){
					String smsContent = null;//"你有一项需要处理的待办事宜：" + pendingTask.getTitle();
					SmsManager.send(org.getId(), user.getId(), user.getMobile(), smsContent, "待办事宜的短信通知");
				}
				//System.out.println("----------------------- 一项需要处理的待办事宜：" + pendingTask.getTitle());
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("共处理已经到提醒时间的待办事宜 " + pendingTaskList.size() + " 条");
		}
		return true;
	}	
}
