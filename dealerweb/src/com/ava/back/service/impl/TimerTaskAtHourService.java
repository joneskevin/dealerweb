package com.ava.back.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.back.service.ITimerTaskAtHourService;
import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IShortMessageDao;
import com.ava.domain.entity.Company;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;
import com.ava.util.FileUtil;

@Service
public class TimerTaskAtHourService implements ITimerTaskAtHourService{

	@Autowired
	private IHibernateDao hibernateDao;

	@Autowired
	private ICompanyDao companyDao;

	@Autowired
	private IShortMessageDao shortMessageDao;

	private Company org;

	/**	过期站内消息清除	*/
	public boolean processShortMessage(){
		//删除过期站内消息，删除日期是发送日期在6个月之前的
		Date delDate = DateTime.minusDays(new java.util.Date(), 31 * 6);
		String hsql = "delete from ShortMessage where creationTime<'" + DateTime.toNormalDateTime(delDate) + "'";
		int temp = shortMessageDao.executeUpdate(hsql);
		System.out.println("共删除过期站内消息 " + temp + " 条");
		return true;
	}
	

	/**	组织实际使用人数计算	*/
	public boolean processOrgUserNum(){
		HashMap parameters = new HashMap();
		List orgList = companyDao.findNatively(parameters);
		Iterator itor = orgList.iterator();
		String hql;
		int count;
		int userNum;
		while (itor.hasNext()) {
			org = (Company) itor.next();
			if (org!=null){
				hql = "select count(id) from User where companyId=" + org.getId();
				count = hibernateDao.executeQueryInt(hql);
				userNum = count-1;	//把admin用户的占用扣除
				if (userNum <= 0){
					userNum = 1;
				}
				org.setUserNum(userNum);
				companyDao.edit(org);
			}
		}
		return true;
	}
	
	/**	组织实际硬盘使用空间统计	*/
	public boolean processOrgUsage(){
		HashMap parameters = new HashMap();
		List orgList = companyDao.findNatively(parameters);
		Iterator itor = orgList.iterator();
		long orgUsage = 0L;
		String orgUploadPath ;
		while (itor.hasNext()) {
			org = (Company) itor.next();
			if (org!=null){
				orgUploadPath = GlobalConfig.getDefaultAppPath() + GlobalConstant.UploadPathName_Org + GlobalConstant.FILE_SEPARATOR + org.getId();
				orgUsage = FileUtil.getDirSize(orgUploadPath);
				org.setOrgUsage(orgUsage);	
				companyDao.edit(org);
			}
		}
		return true;
	}

	/**	审批模块未关联数据清理	*/
	public boolean processApproval(){
		String hsql;
		int temp;
		/**================================	通用审批	===========================*/
		//首先删除未关联组织的申请单
		hsql = "delete from Proposal where orgId not in (select id from Org)";
		temp = hibernateDao.executeUpdate(hsql);
		System.out.println("共删除未关联组织的申请单 " + temp + " 条");
		//再次删除未关联申请单的审批
		hsql = "delete from Approval where proposalId not in (select id from Proposal)";
		temp = hibernateDao.executeUpdate(hsql);
		System.out.println("共删除未关联申请单的审批 " + temp + " 条");
		
		return true;
	}

}
