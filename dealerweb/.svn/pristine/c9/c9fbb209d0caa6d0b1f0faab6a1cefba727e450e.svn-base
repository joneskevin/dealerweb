/**
 * Created on 2015-1-4
 * filename: NoticeService.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.baseSystem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.hibernate.QueryParameter;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.INoticeService;
import com.ava.dao.ICompanyDao;
import com.ava.dao.INoticeDao;
import com.ava.dao.INoticeDealerRelationDao;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Notice;
import com.ava.domain.entity.NoticeDealerRelation;
import com.ava.domain.vo.NoticeVO;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

/**
 * Title: Class XXXX
 * Description:
 *
 *
 * @author jiangfei
 * @version xxx
 */
@Service
public class NoticeService implements INoticeService {
	
	@Autowired
	private INoticeDao noticeDao;
	@Autowired
	private ICompanyDao companyDao ;
	@Autowired
	private INoticeDealerRelationDao noticeDealerRelationDao;
	/*
	 * isAllData 为true 时 查询全部数据，否则false 只查询个人公告
	 * @see com.ava.baseSystem.service.INoticeService#listNotice(com.ava.base.dao.hibernate.TransMsg, com.ava.domain.entity.Notice, boolean)
	 */
	@Override 
	public ResponseData listNotice(TransMsg transMsg, Notice currentNotice) {
		
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		
		if (currentNotice.getTitle() != null
				&& currentNotice.getTitle().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "title",
					currentNotice.getTitle());
		}
		transMsg.put("status", 0);
		String additionalCondition = "";
		//查询全局、指定经销商公告
		additionalCondition += " and type in (1, 3) ";
		additionalCondition += " order by id desc";
		List noticeList = noticeDao.findByPage(transMsg, additionalCondition);
		
		rd.setFirstItem(noticeList);
        rd.setCode(1);
		return rd;
	}

	@Override 
	public ResponseData listUserNotice(TransMsg transMsg, boolean isAllData,Integer companyId) {
		
		ResponseData rd = new ResponseData(0);

		StringBuilder sb=new StringBuilder();
		sb.append("select t.* from (");
		sb.append(" select t1.id,t1.start_time as startTime,t1.invalid_time as invalidTime ,t1.title,t1.summary, '广播公告' as noticeType,'1' as status from t_notice t1 ");
		sb.append("where t1.status=0 and t1.type=1 ");
		sb.append("union ");
		sb.append("select t2.id,t2.start_time as startTime,t2.invalid_time as invalidTime ,t2.title,t2.summary, '私有公告' as noticeType ,'1' as status ");
		sb.append("from t_notice t2,t_notice_dealer_relation tr ");
		sb.append("where tr.notice_id=t2.id and t2.status=0 and t2.type in (2,3)");
		sb.append(" and tr.company_id="+companyId);
		sb.append(" ) t order by t.id desc ");
		if(null!=transMsg){
			if(isAllData){
				transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
			}
			sb.append("  limit ").append(transMsg.getStartIndex()).append(",").append(transMsg.getPageSize());
		}
		List<NoticeVO> noticeList = noticeDao.getNoticeVoList(sb.toString());
		rd.setFirstItem(noticeList);
        rd.setCode(1);
		return rd;
	}
	
	@Override
	public ResponseData listNoticeForHeader(Integer companyId) {
		ResponseData rd = new ResponseData(0);
		if(companyId!=null){
			HashMap parameters = new HashMap();		
			parameters.put("companyId", companyId);
			List<NoticeDealerRelation> relationList = noticeDealerRelationDao.find(parameters, null);
			List<Integer>  noticeIdList = new ArrayList();
			//公告串集合
			String noticeIdStr ="";
			String nowTime = DateTime.toNormalDate(new Date());
			String additionalCondition = "";
			String conditionStartTime = " and startTime "+ QueryParameter.OPERATOR_LE  + " ' " + nowTime + " ' " ;
			String conditionInvalidTime = " and invalidTime "+ QueryParameter.OPERATOR_GE + " ' " + nowTime + " ' " + "" ;
			additionalCondition += conditionStartTime;
			additionalCondition += conditionInvalidTime;
			
			//获得系统公告消息ID type== 1
			parameters = new HashMap();	
			parameters.put("status", 0);
			parameters.put("type", 1);
			//查询全局
//			additionalCondition += " and type =1   ";
			List<Notice> systemNoticeList = noticeDao.find(parameters, additionalCondition);
			//系统公告 1
			if(systemNoticeList!=null)
			{
				for(Notice n :systemNoticeList){
					noticeIdList.add(n.getId());
				}
			}
			//个人创建的公告 3  和系统公告 2
			if(relationList!=null)
			{
				for(NoticeDealerRelation n :relationList){
					noticeIdList.add(n.getNoticeId());
				}
			}
			
			String addConditionSecond = "";
			addConditionSecond += conditionStartTime;
			addConditionSecond += conditionInvalidTime;
			//组装公告ID串集合
			if(noticeIdList != null &&noticeIdList.size()>0) {
				noticeIdStr=  TypeConverter.join(noticeIdList, ",") ;
				addConditionSecond += " and id in(" +  noticeIdStr + ") ";
			}else{
				rd.setCode(1);
				return rd; //没有查到关联表信息 返回空值即可
			}
			
			parameters.clear();
			parameters.put("status", 0);
			addConditionSecond += " order by id desc "; 
			List noticeList = noticeDao.find(parameters, addConditionSecond);
			
			rd.setFirstItem(noticeList);
//			rd.put("", item);
	        rd.setCode(1);
		}
	
		return rd;
	}
	
	@Override
	public ResponseData displayAddNotice(Notice noticeAdd) {
		ResponseData rd = new ResponseData(0);
		if (noticeAdd == null) {
			noticeAdd = new Notice();
		}
		noticeAdd.setType(3);//添加默认局部类型 3
		rd.put("noticeAdd", noticeAdd);
		return rd;
	}

	@Override
	public ResponseData addNotice(Notice noticeAdd) {
		ResponseData rd = new ResponseData(0);
//		noticeAdd.setType(3); //添加的type都是3
		noticeAdd.setStatus(0);
		Integer newNoticeId = (Integer) noticeDao.save(noticeAdd);
		
		if (newNoticeId == null) {
			rd.setCode(-2);
			rd.setMessage("公告新增失败！");
			return rd;
		}

		rd.setCode(1);
		rd.setMessage("公告新增成功！");
		rd.setFirstItem(noticeAdd);
		return rd;
	}

	@Override
	public ResponseData displayEditNotice(Integer noticeId) {
		ResponseData rd = new ResponseData(0);

		Notice noticeEdit = noticeDao.get(noticeId);
		rd.put("notice", noticeEdit);
		return rd;
	}

	@Override
	public ResponseData editNotice(Notice noticeEdit) {
		ResponseData rd = new ResponseData(0);

		Integer noticeId = noticeEdit.getId();
		if ((noticeId == null) || (noticeId != null && noticeId.intValue() <= 0)) {
			noticeId = noticeEdit.getId();
		}
	
		Notice dbNotice = noticeDao.get(noticeId);

	/*	if (roleEdit.getName() != null
				&& !roleEdit.getName().equalsIgnoreCase(dbRole.getName())) {
			// 如果用户要修改角色名，则判断新的角色名是否已经存在
			if (roleDao.getByName(roleEdit.getName()) != null) {
				rd.setCode(-1);
				rd.setMessage("角色信息保存失败：角色名已重复！");
				return rd;
			}
		}*/
		
		MyBeanUtils.copyPropertiesContainsDate(dbNotice, noticeEdit);
		noticeDao.edit(dbNotice);
		//如果是从type 3切换到 type1 删除关联表里面的数据
		if(dbNotice.getType()==1){
			noticeDealerRelationDao.deleteByNoticeId(dbNotice.getId());
		}
		rd.setFirstItem(dbNotice);
		rd.setCode(1);
		rd.setMessage("公告色信息修改成功！");
		return rd;
	}
	
	
	@Override
	public ResponseData displayEditNoticeOfDealer(Integer noticeId) {
		return null;
	}

	@Override
	public ResponseData editNoticeOfDealer(Notice notice) {
		return null;
	}

	@Override
	public ResponseData deleteNotice(Integer noticeId) {
		return null;
	}

	
	@Override
	public List<Company> displayCompanyList() {
		ResponseData rd = new ResponseData(0);
//		Map data = new HashMap();
//		rd.setData(data);
		HashMap parameters = new HashMap();
		String additionalCondition = " order by regionProvinceId desc";
		
		List mateUserList = companyDao.find(parameters,  additionalCondition);
//		rd.put("mateUserList", mateUserList);
		return mateUserList;
	}
	
	/**
	 * 根据id查找一个公告
	 * 
	 * @param noticeId
	 * @return ResponseData
	 */
	@Override
	public Notice findNotice(Integer noticeId){
		if(noticeId == null){
			return null ;
		}
		Notice dbNotice = noticeDao.get(noticeId);
		return dbNotice ;
	}
	
}
