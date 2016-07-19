package com.ava.back.service;


public interface ITimerTaskAtHourService {

	/**	过期站内消息清除	*/
	public boolean processShortMessage();
	
	/**	组织实际使用人数计算	*/
	public boolean processOrgUserNum();
	
	/**	组织实际硬盘使用空间统计	*/
	public boolean processOrgUsage();
	
	/**	审批模块未关联数据清理	*/
	public boolean processApproval();
}
