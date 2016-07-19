package com.ava.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IFilingProposalDao;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.entity.FilingProposalDetail;
import com.ava.domain.vo.VehicleStyle;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;

@Repository
public class FilingProposalDao implements IFilingProposalDao{
	
	protected final static Logger logger = Logger.getLogger(FilingProposalDao.class);

	@Autowired
	private IHibernateDao hibernateDao;
	
	public FilingProposal getFilingProposalById(Integer filingProposalId){
		return hibernateDao.get(FilingProposal.class, filingProposalId);
	}
	
	public FilingProposal getFilingProposal(HashMap parameters){
		return hibernateDao.get("FilingProposal", parameters);
	}
	
	public FilingProposal getFilingProposal(HashMap parameters,String additionalCondition){
		return hibernateDao.get("FilingProposal", parameters, additionalCondition);
	}
	
	public List<FilingProposal> getFilingProposalList(TransMsg transMsg,String additionalCondition){
		if(null==transMsg){
			transMsg=new TransMsg();
			transMsg.setStartIndex(0);
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		additionalCondition+=" order by id desc";
		return hibernateDao.findByPage("FilingProposal", transMsg, additionalCondition);
	}
	
	public List<FilingProposal> findFilingProposalByPage(TransMsg transMsg, Map parameters ,String additionalCondition){
		if(null==transMsg){
			transMsg=new TransMsg();
			transMsg.setStartIndex(0);
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		additionalCondition+=" order by id desc";
		return hibernateDao.find("FilingProposal", parameters, additionalCondition, transMsg.getStartIndex(), transMsg.getPageSize());
	}
	
	public List<FilingProposal> findFilingProposalByPage(TransMsg transMsg, String additionalCondition){
		if(null==transMsg){
			transMsg=new TransMsg();
			transMsg.setStartIndex(0);
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		additionalCondition+=" order by id desc";
		return hibernateDao.findByPage("FilingProposal", transMsg, additionalCondition);
	}
	
	public FilingProposalAttachment getFilingProposalAttachment(HashMap parameters){
		return hibernateDao.get("FilingProposalAttachment", parameters);
	}
	@Override
	public Integer addFilingProposal(FilingProposal filingProposal,String selectedVehicles) {
		
		Integer filingProposalId=(Integer) hibernateDao.save(filingProposal);
		saveFilingProposalDetail(filingProposal.getCompanyId(),filingProposalId,selectedVehicles);
		return filingProposalId;
	}
	
	/**
	 * 编辑报备
	 */
	public void editFilingProposal(FilingProposal filingProposal,String selectedVehicles){
		hibernateDao.merge(filingProposal);
		//saveFilingProposalDetail(filingProposal.getCompanyId(),filingProposal.getId(),selectedVehicles);
	}
	
	/**
	 * 编辑报备保存前先更新之前报备明细处于删除状态
	 */
	public void deleteFilingProposalDetail(Integer companyId,Integer filingProposalId){
		StringBuffer stringBuffer=new StringBuffer("");
		stringBuffer.append(" update t_filing_proposal_detail tfpd  set tfpd.status=0 where tfpd.status=1 and tfpd.proposal_id=").append(filingProposalId);
		stringBuffer.append(" and company_id=").append(companyId);
		hibernateDao.executeSQLUpdate(stringBuffer.toString());
	}
	
	/**
	 * 取消报备
	 */
	public void cancelFilingProposal(FilingProposal filingProposal){
		hibernateDao.merge(filingProposal);
	}
	/**
	 * 
	* Description: 关联表去掉  改用分隔符形式存储
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param filingProposalId
	* @param selectedVehicles
	 */
	public void saveFilingProposalDetail(Integer companyId,Integer filingProposalId,String selectedVehicles){
		String[] vehicleIds=selectedVehicles.split(",");
		Date date=new Date();
		for(String vehicleId : vehicleIds){
			FilingProposalDetail filingProposalDetail=new FilingProposalDetail();
			filingProposalDetail.setCompanyId(companyId);
			filingProposalDetail.setProposalId(filingProposalId);
			filingProposalDetail.setVehicleId(Integer.parseInt(vehicleId));
			filingProposalDetail.setStatus(1);
			filingProposalDetail.setMonths(Integer.parseInt(DateTime.toShortStr(date)));
			hibernateDao.save(filingProposalDetail);
		}
	}
	
	@Override
	public Integer saveFilingProposalAttachment(
			FilingProposalAttachment filingProposalAttachment) {
		return (Integer) hibernateDao.save(filingProposalAttachment);
	}
	
	/**
	 * 根据报备信息获取附件列表
	 */
	@Override
	public List<FilingProposalAttachment> getFilingProposalAttachment(Integer filingProposalId) {
		List<FilingProposalAttachment> filingProposalAttachmentList=null;
		if(null!=filingProposalId){
			HashMap<String,Integer> filingProposalAttachmentMap=new HashMap<String,Integer>();
			filingProposalAttachmentMap.put("proposalId", filingProposalId);
			filingProposalAttachmentList=hibernateDao.find("FilingProposalAttachment", filingProposalAttachmentMap);
		}
		return filingProposalAttachmentList;
	}
	
	/**
	 * 删除附件
	 */
	public void deleteFilingProposalFile(Integer filingProposalId,String delFiles){
		StringBuffer stringBuffer=new StringBuffer("");
		stringBuffer.append(" delete from t_filing_proposal_attachment  where proposal_id=").append(filingProposalId);
		stringBuffer.append(" and id in(").append(delFiles).append(")");
		hibernateDao.executeSQLUpdate(stringBuffer.toString());
	}
	
	public String getDealerIds(Integer companyId){
		StringBuffer dealerQuery =new StringBuffer("");
		dealerQuery.append("select tr.id from t_org tr where tr.id=").append(companyId).append(" or tr.company_id=").append(companyId);
		StringBuffer dealerIds=new StringBuffer("");
		List<Integer> dealerList=hibernateDao.executeSQLQueryList(dealerQuery.toString());
		if(! (null==dealerList || dealerList.isEmpty())){
			for(Integer dealerId : dealerList){
				dealerIds.append(dealerId).append(",");
			}
			return dealerIds.substring(0, dealerIds.lastIndexOf(","));
		}
		return null;
	}
	
	
	public List<FilingProposalDetail> getFilingProposalDetail(Integer filingProposalId, Integer companyId){
		Map parameters=new HashMap();
		parameters.put("proposalId", filingProposalId);
		parameters.put("companyId", companyId);
		parameters.put("status", 1);
		List<FilingProposalDetail> filingProposalDetails=hibernateDao.find("FilingProposalDetail", parameters);
		return filingProposalDetails;
	}
	
	/**
	 * 
	* Description: 查询当前报备所选择的车辆用于查看报备详情及审批界面
	* @author henggh 
	* @version 0.1 
	* @return
	 */
	public List<VehicleStyle> getVehicleByFilingProposal(Integer companyId,Integer filingProposalId){
		StringBuffer sb=new StringBuffer("");
		sb.append("select tv.id as vehicleId,tv.plate_number as plateNumber,tcs.series_name as seriesName,tcs.discharge,tcs.year_type as yearType,");
		sb.append(" concat(tv.plate_number, '(', tcs.name, ' ', tcs.year_type, ')') as vehicleInfo , '0' as checked ");
		sb.append(" from t_vehicle tv, t_car_style tcs ");
		sb.append(" where tv.car_style_id=tcs.id ");
		choseVehicle(sb);
		sb.append(" and tv.company_id=").append(companyId);
		sb.append(" and exists(select tfpd.id from t_filing_proposal_detail tfpd");
		sb.append(" where tfpd.status=1 and tfpd.vehicle_id=tv.id ");
		sb.append(" and tfpd.company_id=").append(companyId);
		sb.append(" and tfpd.proposal_id=").append(filingProposalId).append(")");
		
		return hibernateDao.executeSQLQueryVoList(sb.toString(), VehicleStyle.class);
	}
	
	/**
	 * 
	* Description: 查询当前经销商所有已安装车辆用于经销商筛选车辆
	* @author henggh 
	* @version 0.1 
	* @return
	 */
	public List<VehicleStyle> getVehicleByCompany(Integer companyId){
		StringBuffer sb=new StringBuffer("");
		sb.append("select tv.id as vehicleId,tv.plate_number as plateNumber,tcs.series_name as seriesName,tcs.discharge,tcs.year_type as yearType,");
		sb.append(" concat(tv.plate_number, '(', tcs.name, ' ', tcs.year_type, ')') as vehicleInfo , '0' as checked ");
		sb.append(" from t_vehicle tv, t_car_style tcs ");
		sb.append(" where tv.car_style_id=tcs.id ");
		choseVehicle(sb);
		sb.append(" and tv.company_id=").append(companyId);
		return hibernateDao.executeSQLQueryVoList(sb.toString(), VehicleStyle.class);
	}

	public boolean isExistsFilingProposal(Integer companyId,Integer filingProposalType,String selectedVehicles){
		StringBuffer sb=new StringBuffer("");
		sb.append("select tfp.id from t_filing_proposal tfp ,t_filing_proposal_detail tfpd ");
		sb.append(" where tfp.id=tfpd.proposal_id and tfp.company_id=tfpd.company_id " );
		sb.append(" and tfp.type=").append(filingProposalType);
		sb.append(" and tfp.status= ").append(GlobalConstant.FILING_PROPOSAL_STATUS_INIT);
		sb.append(" and tfp.company_id=").append(companyId).append(" ");
		sb.append(" and tfpd.status=1 ");
		sb.append(" and tfpd.vehicle_id in(").append(selectedVehicles).append(")");
		List<Integer> list=hibernateDao.executeSQLQueryList(sb.toString());
		if(null==list || list.isEmpty())
			return false;
		return true;
	}
	
	public List<String> getExistsFilingProposal(Integer companyId,Integer filingProposalType){
		StringBuffer sb=new StringBuffer("");
		sb.append(" select tfp.vehicle_ids from t_filing_proposal tfp ");
		sb.append(" where 1=1 " );
		sb.append(" and tfp.type=").append(filingProposalType);
		sb.append(" and tfp.status= ").append(GlobalConstant.FILING_PROPOSAL_STATUS_INIT);
		sb.append(" and tfp.company_id=").append(companyId);
		List<String> list=hibernateDao.executeSQLQueryList(sb.toString());
		return list;
	}
	
	/**
	 * 利用sql语句进行更新操作
	 */
	public void updateTestDrive(String sql){
		//hibernateDao.executeSQLUpdate(sql);
		updateSqlById(sql);
	}

	/**
	 * 把更新语句的条件转换为用id来更新
	 * @author tangqingsong
	 * @version 
	 * @param sql
	 */
	private void updateSqlById(String sql){
		try{
			String tableName = sql.substring(sql.indexOf("update")+"update".length(), sql.indexOf("set"));
			String condition = sql.substring(sql.indexOf("where"), sql.length());
			String qsql = "select id from "+tableName+" "+condition;
			String usql = sql.substring(sql.indexOf("update"), sql.indexOf("where"))+" where id=:id";
			if(!qsql.isEmpty()){
				List<Integer> list = hibernateDao.executeSQLQueryList(qsql);
				if(list!=null  && !list.isEmpty()){
					HashMap<String,Object> argMap = new HashMap<>();
					for(Integer id:list){
						argMap.put("id", id);
						hibernateDao.executeSQLUpdate(usql, argMap);
					}
				}
			}
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public <T> List<T>  exeSqlQueryList(String exeSql,Class classz){
		List<T> list=hibernateDao.executeSQLQueryVoList(exeSql,classz);
		return list;
	}
	
	/**
	 * 判定车辆在指定工作时间内是否存在指定状态的报备记录
	 *
	 * @author wangchao
	 * @version 
	 * @param vehicleId 车辆id 不能为空
	 * @param startTime 区间开始时间yyyy-mm-dd hh:MM:ss 不能为空
	 * @param endTime 区间结束时间yyyy-mm-dd hh:MM:ss 可为空
	 * @param status 状态 1 已审核 0 未审核 可为空
	 * @return
	 */
	public boolean hasFilingProposal(Integer vehicleId, String startTime, String endTime, String status){
		if( null == vehicleId || null == startTime){//输入参数为空，判定为无报备记录
			logger.error(" hasApprovedFilingProposal input vehicleId =["+vehicleId+"] startTime=["+startTime+"] has null Object");
			return false;
		}
		StringBuffer sql=new StringBuffer("");
		sql.append("select tfp.id as id from t_filing_proposal tfp,t_filing_proposal_detail tfpd ");
		sql.append(" where tfp.id=tfpd.proposal_id " );
		if(StringUtils.isNotEmpty(status)){//增加状态输入判定，可为空
			sql.append(" and tfp.status in (" ).append(status).append(")");
		}
		sql.append(" and tfpd.vehicle_id=").append(vehicleId);

		if(StringUtils.isEmpty(endTime)){//没有结束日期，就只根据试驾开始时间判断是否存在报备申请
			sql.append(" and tfp.start_time<='").append(startTime).append("'").append(" and tfp.end_time>='").append(startTime).append("'");
		}else{//存在结束日期，那么报备结束时间需要大于试驾结束时间
			sql.append(" and tfp.start_time<='").append(startTime).append("'").append(" and tfp.end_time>='").append(endTime).append("'");
		}
		List<String> list=hibernateDao.executeSQLQueryList(sql.toString());
		if(null!=list && list.size()>0){
			return true;
		}
		return false;
	}
	
	private void choseVehicle(StringBuffer sb){
		sb.append(" and tv.configure_status in (").append(GlobalConstant.CONFIGURE_STATUS_WATTING).append(",").append(GlobalConstant.CONFIGURE_STATUS_INSTALLED).append(") ");
	}
}
