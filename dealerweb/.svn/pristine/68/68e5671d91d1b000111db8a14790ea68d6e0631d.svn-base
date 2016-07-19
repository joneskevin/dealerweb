package com.ava.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.entity.FilingProposalDetail;
import com.ava.domain.vo.FilingProposalVO;
import com.ava.domain.vo.VehicleStyle;

public interface IFilingProposalDao {

	/**
	 * 
	* Description: 根据报备ID获取报备信息
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	public FilingProposal getFilingProposalById(Integer filingProposalId);
	
	public FilingProposal getFilingProposal(HashMap parameters);
	
	public FilingProposal getFilingProposal(HashMap parameters,String additionalCondition);
	
	public List<FilingProposal> getFilingProposalList(TransMsg transMsg,String additionalCondition);
	
	/**
	 * 
	* Description: 获取附件信息
	* @author henggh 
	* @version 0.1 
	* @param parameters
	* @return
	 */
	public FilingProposalAttachment getFilingProposalAttachment(HashMap parameters);
	/**
	 * 
	* Description: 分页查找报备信息
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param parameters
	* @param additionalCondition
	* @return
	 */
	public List<FilingProposal> findFilingProposalByPage(TransMsg transMsg, Map parameters ,String additionalCondition);
	
	/**
	 * 
	* Description: 分页查找报备信息
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param additionalCondition
	* @return
	 */
	public List<FilingProposal> findFilingProposalByPage(TransMsg transMsg, String additionalCondition);
	
	/**
	 * 
	* Description: 添加报备
	* @author henggh 
	* @version 0.1 
	* @param FilingProposal
	* @param selectedVehicles
	* @return
	 */
	public Integer addFilingProposal(FilingProposal filingProposal,String selectedVehicles);
	
	/**
	 * 
	* Description: 附件列表
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	public List<FilingProposalAttachment> getFilingProposalAttachment(Integer filingProposalId);
	/**
	 * 
	* Description: 报备报备附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalAttachment
	* @return
	 */
	public Integer saveFilingProposalAttachment(FilingProposalAttachment filingProposalAttachment);
	
	/**
	 * 
	* Description: 删除附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param delFiles
	 */
	public void deleteFilingProposalFile(Integer filingProposalId,String delFiles);
	/**
	 * 
	* Description: 编辑报备
	* @author henggh 
	* @version 0.1 
	* @param filingProposal
	* @param selectedVehicles
	 */
	public void editFilingProposal(FilingProposal filingProposal,String selectedVehicles);
	
	/**
	 * 
	* Description: 先更新报备明细为删除状态
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param filingProposalId
	 */
	public void deleteFilingProposalDetail(Integer companyId,Integer filingProposalId);
	
	/**
	 * 
	* Description: 取消报备
	* @author henggh 
	* @version 0.1 
	* @param filingProposal
	 */
	public void cancelFilingProposal(FilingProposal filingProposal);
		
	/**
	 * 
	* Description: 查询经销商及其下属直营店的经销商ID或直营店ID
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @return
	 */
	public String getDealerIds(Integer companyId);
	
	/**
	 * 
	* Description: 查询当前报备所选择的车辆用于查看报备详情及审批界面
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param filingProposalId
	* @return
	 */
	public List<VehicleStyle> getVehicleByFilingProposal(Integer companyId,Integer filingProposalId);
	
	/**
	 * 
	* Description: 查询当前经销商所有已安装车辆用于经销商筛选车辆
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @return
	 */
	public List<VehicleStyle> getVehicleByCompany(Integer companyId);
	
	/**
	 * 
	* Description: 根据明细表查询是否有处于待审核状态的车辆
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param filingProposalType
	* @param selectedVehicles
	* @return
	 */
	public boolean isExistsFilingProposal(Integer companyId,Integer filingProposalType,String selectedVehicles);
	/**
	 * 
	* Description: 检查当前是否有处于待报备的车辆
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param filingProposalType
	* @return
	 */
	public List<String> getExistsFilingProposal(Integer companyId,Integer filingProposalType);
	
	/**
	 * 
	* Description: 根据报备ID查询有效明细数据
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param companyId
	* @return
	 */
	public List<FilingProposalDetail> getFilingProposalDetail(Integer filingProposalId, Integer companyId);
	
	/**
	 * 
	* Description: 
	* @author henggh 
	* @version 0.1 
	* @param exeSql
	* @return
	 */
	public <T> List<T> exeSqlQueryList(String exeSql,Class classz);
	
	public void updateTestDrive(String sql);
	
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
	public boolean hasFilingProposal(Integer vehicleId, String startTime, String endTime, String status);
}
