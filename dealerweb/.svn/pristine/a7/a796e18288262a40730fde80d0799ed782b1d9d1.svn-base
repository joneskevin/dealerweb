package com.ava.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IDeclareManagerDao;
import com.ava.domain.entity.FilingApproval;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;

@Repository("dealer.declareManagerDao")
public class DeclareManagerDao implements IDeclareManagerDao {
	@Autowired
	private IHibernateDao hibernateDao;
	
	/**
	 * 根据ID获取报备
	 */
	public FilingProposal getFilingProposal(Integer id){
		return hibernateDao.get(FilingProposal.class, id);
	}
	
	public FilingProposalAttachment getFilingProposalAttachment(Integer id){
		return hibernateDao.get(FilingProposalAttachment.class, id);
	}
	
	/**
	 * 保存报备
	 */
	@Override
	public Integer save(FilingProposal filingProposal) {
		if(null!=filingProposal){
			return (Integer) hibernateDao.save(filingProposal);
		}else{
			return -1;
		}
	}
	
	
	/**
	 * 保存报备及报备附件
	 */
	@Override
	public void save(FilingProposal filingProposal,
			List<FilingProposalAttachment> filingProposalAttachmentList) {
		if(null!=filingProposal){
			Integer  filingProposalId=(Integer) hibernateDao.save(filingProposal);
			saveFilingProposalAttachment(filingProposalAttachmentList,filingProposalId);
		}
	}

	public void saveFilingProposalAttachment(FilingProposalAttachment filingProposalAttachment){
//		if(null!=filingProposalAttachment && null!=filingProposalAttachment.getFilingProposalId()){
//			hibernateDao.save(filingProposalAttachment);
//		}
	}
	/**
	 * 删除报备及报备附件
	 */
	@Override
	public void delete(FilingProposal filingProposal) {
		if(null!=filingProposal){
			deleteFilingProposalAttachment(getFilingProposalAttachment(filingProposal));
			hibernateDao.delete(filingProposal);
		}
	}
	
	/**
	 * 删除报备及报备附件
	 */
	@Override
	public void delete(FilingProposalAttachment filingProposalAttachment) {
		if(null!=filingProposalAttachment){
			hibernateDao.delete(filingProposalAttachment);
		}
	}
	
	/**
	 * 报备审批
	 */
	@Override
	public Integer save(FilingApproval filingApproval) {
		if(null!=filingApproval){
			return (Integer) hibernateDao.save(filingApproval);
		}else{
			return -1;
		}
	}

	/**
	 * 编辑报备和报备附件
	 */
	@Override
	public void edit(FilingProposal filingProposal,
			List<FilingProposalAttachment> filingProposalAttachmentList) {
		if(null!=filingProposal){
			hibernateDao.update(filingProposal);
			if(null!=filingProposalAttachmentList && !filingProposalAttachmentList.isEmpty()){
				deleteFilingProposalAttachment(getFilingProposalAttachment(filingProposal));
				saveFilingProposalAttachment(filingProposalAttachmentList,filingProposal.getId());
			}
		}
	}
	
	/**
	 * 编辑报备和报备附件
	 */
	@Override
	public void merge(FilingProposal filingProposal,
			List<FilingProposalAttachment> filingProposalAttachmentList) {
		if(null!=filingProposal){
			hibernateDao.merge(filingProposal);
			if(null!=filingProposalAttachmentList && !filingProposalAttachmentList.isEmpty()){
				deleteFilingProposalAttachment(getFilingProposalAttachment(filingProposal));
				saveFilingProposalAttachment(filingProposalAttachmentList,filingProposal.getId());
			}
		}
	}

	/**
	 * 根据报备信息获取附件列表
	 */
	@Override
	public List<FilingProposalAttachment> getFilingProposalAttachment(FilingProposal filingProposal) {
		List<FilingProposalAttachment> filingProposalAttachmentList=null;
		if(null!=filingProposal){
			HashMap<String,Integer> filingProposalAttachmentMap=new HashMap<String,Integer>();
			filingProposalAttachmentMap.put("filingProposalId", filingProposal.getId());
			filingProposalAttachmentList=hibernateDao.find("FilingProposalAttachment", filingProposalAttachmentMap);
		}
		return filingProposalAttachmentList;
	}
	
	/**
	 * 删除附件(全删除)
	 * @param filingProposal
	 */
	public void deleteFilingProposalAttachment(List<FilingProposalAttachment> filingProposalAttachmentList){
		if(null!=filingProposalAttachmentList && !filingProposalAttachmentList.isEmpty()){
			hibernateDao.delete(FilingProposalAttachment.class, filingProposalAttachmentList);
		}
	}
	
	public void saveFilingProposalAttachment(List<FilingProposalAttachment> filingProposalAttachmentList,Integer filingProposalId){
		if(null!=filingProposalAttachmentList && !filingProposalAttachmentList.isEmpty() && null!=filingProposalId){
			for(FilingProposalAttachment filingProposalAttachment:filingProposalAttachmentList){
//				if(null!=filingProposalId)
//					filingProposalAttachment.setFilingProposalId(filingProposalId);
				hibernateDao.save(filingProposalAttachment);
			}
		}
	}

	public List<FilingProposal> findByVehicleId(String hql){
		List<FilingProposal> filingProposalList=new ArrayList<FilingProposal>();
		if(null!=hql && !"".equals(hql)){
			filingProposalList=hibernateDao.excuteQuerystring(hql);
		}
		return filingProposalList;
	}
	
	public List<FilingProposal> find(Map parameters, String additionalCondition) {
		List<FilingProposal> filingProposalList = hibernateDao.find("FilingProposal", parameters, additionalCondition);
		return filingProposalList;
	}

	@Override
	public List<FilingProposal> findByPage(TransMsg msg,
			String additionalCondition) {
		List<FilingProposal> filingProposalList =hibernateDao.findByPage("FilingProposal", msg, additionalCondition);
		return filingProposalList;
	}
}
