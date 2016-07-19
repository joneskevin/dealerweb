package com.ava.resource;

import com.ava.domain.vo.OrgLoginRecord;
import com.ava.util.DateTime;
import com.ava.util.Queue;

/**静态对象管理类*/
public class StaticObjManagerOrgLoginRecord {
	
	private static Queue<OrgLoginRecord> orgLoginRecordQueue = null;

	private static Queue<OrgLoginRecord> getVisitRecordQueue() {
		if (orgLoginRecordQueue == null) {
			orgLoginRecordQueue = new Queue<OrgLoginRecord>();
			orgLoginRecordQueue.setSizeIsLimited(false);
		}
		return orgLoginRecordQueue;
	}
	
	/**	队列元素消亡时间检查操作，把超时的队列元素删除	*/
	private static void itemDeleteCheck(){
		for ( int i=0 ; i<getVisitRecordQueue().size() ; i++ ) {
			OrgLoginRecord itemInQueue = getVisitRecordQueue().elementAt(i);
			if ( itemInQueue.getLoginDate()==null || !itemInQueue.getLoginDate().equals(DateTime.getNormalDate()) ){
				//如果队列元素的登录时间为空或不等于当前时间，则说明已失效，应该从队列删除
				getVisitRecordQueue().removeElement(itemInQueue);
			}else if(itemInQueue.getOrgId()==null){
				//如果队列元素的orgId为空，说明该对象非法，则删除
				getVisitRecordQueue().removeElement(itemInQueue);				
			}else{
				//如果没有，则由于队列的特性，后面的元素也不可能到达消亡时间，所以可以跳过继续检查
				break;
			}
		}
	}
	
	/**
	 * 	判断是否多次的重复登录，原理是检查访问记录队列中是否存在匹配记录，只匹配orgId和登录日期
	 *	同时需要每次进行队列元素消亡时间检查操作，把超时的队列元素删除
	*/
	public static boolean isFrequentLogin(Integer orgId){
		if ( orgId==null ){
			return true;
		}
		
		//首先进行队列元素消亡时间检查操作，把超时的队列元素删除
		itemDeleteCheck();
		
		//然后进行重复访问记录搜索，处于效率考虑，从队列的最后开始搜索，获得结果的速度最快
		for ( int i=getVisitRecordQueue().size()-1 ; i>=0 ; i-- ) {
			OrgLoginRecord itemInQueue = getVisitRecordQueue().elementAt(i);
			if ( orgId.intValue()==itemInQueue.getOrgId().intValue() 
					&& itemInQueue.getLoginDate().equals(DateTime.getNormalDate()) ) {
				//如果元素的orgId和登录日期相同，说明队列有重复记录，是频繁访问
				return true;
			}
		} 
		//队列中没有重复访问记录，则把该记录加入队列中
		OrgLoginRecord newItem = new OrgLoginRecord(orgId);
		getVisitRecordQueue().insert(newItem);
		
		return false;
	}

}
