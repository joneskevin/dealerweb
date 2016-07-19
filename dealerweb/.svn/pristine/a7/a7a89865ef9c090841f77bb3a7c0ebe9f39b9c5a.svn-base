package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.QueryParameter;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IProposalAttachmentDao;
import com.ava.domain.entity.ProposalAttachment;
import com.ava.resource.GlobalConfig;
import com.ava.util.FileUtil;

@Repository
public class ProposalAttachmentDao implements IProposalAttachmentDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	public void edit(ProposalAttachment attachement){
		hibernateDao.edit(attachement);
	}
	
	public void delete( Integer id) {
		//首先删除硬盘上的文件
		ProposalAttachment item = this.get(id);
		FileUtil.delFile(GlobalConfig.getDefaultAppPath() + item.getFullPath());
		//从数据库中删除
		hibernateDao.delete(ProposalAttachment.class, id);
	}

	public ProposalAttachment get(Integer id) {
		ProposalAttachment news = (ProposalAttachment) hibernateDao.get(ProposalAttachment.class, id);		
		return news;
	}
	
	public List getByProposerIdAndCreatorId(Integer proposalId, Integer creatorId){
		HashMap parameters = new HashMap();
		parameters.put("proposalId", proposalId);
		parameters.put("creatorId", creatorId);
		List<Object> objList = (List<Object>) hibernateDao.find("ProposalAttachment", parameters,"order by id desc");
		return objList;
	}
	
	public List getByProposalId(Integer proposalId){
		HashMap parameters = new HashMap();
		parameters.put("proposalId", proposalId);
		List<Object> objList = (List<Object>) hibernateDao.find("ProposalAttachment", parameters,"order by id desc");
		return objList;
	}
	
	public List getByProposalIdAndType(Integer proposalId, Integer type){
		HashMap parameters = new HashMap();
		parameters.put("proposalId", proposalId);
		parameters.put("type", type);
		List<Object> objList = (List<Object>) hibernateDao.find("ProposalAttachment", parameters,"order by id asc");
		return objList;
	}
	
	public List getByProposalIdAndFilteredType(Integer proposalId, Integer filteredType){
		HashMap parameters = new HashMap();
		parameters.put("proposalId", proposalId);
		QueryParameter para = new QueryParameter("type", QueryParameter.OPERATOR_NEQ, filteredType);
		parameters.put("type", para);
		List<Object> objList = (List<Object>) hibernateDao.find("ProposalAttachment", parameters,"order by id asc");
		return objList;
	}
	
	public List findByPage(TransMsg msg) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("ProposalAttachment", msg);
		return objList;
	}
	
	public List findByPage(TransMsg msg, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.findByPage("ProposalAttachment", msg, additionalCondition);
		return objList;
	}
	
}