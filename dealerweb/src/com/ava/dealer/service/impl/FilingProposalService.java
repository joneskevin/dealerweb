package com.ava.dealer.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IFilingProposalDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.dealer.service.IFilingProposalService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.entity.FilingProposalDetail;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.FilingProposalAttachmentVO;
import com.ava.domain.vo.FilingProposalHis;
import com.ava.domain.vo.FilingProposalVO;
import com.ava.domain.vo.VehicleStyle;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.DateTime;
import com.ava.util.SendEMailUtil;
import com.ava.util.upload.UploadHelper;

@Service("dealer.filingProposalService")
public class FilingProposalService extends BaseService implements IFilingProposalService {
	@Autowired
	private IFilingProposalDao filingProposalDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	
	@Override
	public List<FilingProposal> getFilingProposalList(TransMsg transMsg,Integer companyId,Integer type,String startTime,String endTime,boolean isExport) {
		if(null==transMsg){
			transMsg=new TransMsg();
			transMsg.setStartIndex(0);
		}
		StringBuffer additionalCondition=new StringBuffer("");
		if (companyId == GlobalConstant.DEFAULT_GROUP_ORG_ID) {
			additionalCondition.append(" and status=0 and type=").append(type);
		} else {
			String dealerIds=filingProposalDao.getDealerIds(companyId);
			if(null==dealerIds || "".equals(dealerIds))
				return null;
			additionalCondition.append(" and status=0 and companyId in (").append(dealerIds).append(") and type=").append(type);
		}
		if(!(null==startTime || "".equals(startTime.trim()))){
			additionalCondition.append(" and startTime>=date_format('").append(startTime).append("','%Y-%m-%d %T')");
		}
		if(!(null==endTime || "".equals(endTime.trim()))){
			additionalCondition.append(" and endTime<=date_format('").append(endTime).append("','%Y-%m-%d %T')");
		}
		List<FilingProposal> filingProposalList=filingProposalDao.getFilingProposalList(transMsg, additionalCondition.toString());
		return filingProposalList;
	}
	
	@Override
	public List<FilingProposalVO> getFilingProposalVoList(TransMsg transMsg,Integer companyId,Integer userId,Integer type,String startTime,String endTime, boolean isExport, String dealerCode) {
		StringBuffer queryList=new StringBuffer("");
		StringBuffer queryCount=new StringBuffer("");
		queryList.append("select vdi.saleCenterId,vdi.saleCenterName,vdi.isKeyCityNick,vdi.dealerTypeNick,vdi.dealerCode,");
		queryList.append(" vdi.provinceName,vdi.cityName,vdi.companyName, " );
		queryList.append(" tfp.id as filingProposalId,tfp.proposal_time as proposalTime,tfp.start_time as startTime,tfp.end_time as endTime,tfp.type,tfp.status,");
		queryList.append(" case tfp.type when 1 then '市场活动' when 2 then '事故维修' else '未知' end as filingType," );
		queryList.append(" case tfp.status when 0 then '待审核' when 1 then '同意' when 2 then '拒绝' when 3 then '取消' else '未知' end as filingStatus, ");
		queryList.append(" tfp.DESTINATION as description, tfp.APPROVAL_ID as approvalId, tfp.APPROVAL_TIME as approvalTime ");
		queryList.append(" from t_filing_proposal tfp, view_dealer_info vdi where tfp.company_id=vdi.companyId and tfp.status=0 and tfp.type=").append(type);	
		queryCount.append(" select count(1) from t_filing_proposal tfp, view_dealer_info vdi where tfp.company_id=vdi.companyId and tfp.status=0 and tfp.type=").append(type);
		if(null!=startTime && !"".equals(startTime.trim())){
			queryList.append(" and tfp.start_time>=date_format('").append(startTime).append("','%Y-%m-%d %T')");
			queryCount.append(" and tfp.start_time>=date_format('").append(startTime).append("','%Y-%m-%d %T')");
		}
		if(null!=endTime && !"".equals(endTime.trim())){
			queryList.append(" and tfp.start_time<=date_format('").append(endTime).append("','%Y-%m-%d %T')");//修改时间区间查询只针对报备开始时间
			queryCount.append(" and tfp.start_time<=date_format('").append(endTime).append("','%Y-%m-%d %T')");
		}	
		if(null!=dealerCode && dealerCode.trim().length()>=1){
			queryList.append(" and vdi.dealerCode = '").append(dealerCode).append("' ");
			queryCount.append(" and vdi.dealerCode = '").append(dealerCode).append("' ");
		}
		queryList.append(getAccessSql(companyId,userId).toString());
		queryCount.append(getAccessSql(companyId,userId).toString());
		queryList.append(" order by vdi.saleCenterId, vdi.dealerCode");
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		//设置分页
		queryList.append(getPaginationSql(transMsg, queryCount, isExport));
		List<FilingProposalVO> filingProposalVoList = filingProposalDao.exeSqlQueryList(queryList.toString(),FilingProposalVO.class);
		
		return filingProposalVoList;
	}
	
	/**
	 * 添加报备
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposal> addFilingProposal(HttpServletRequest request,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles) {
		if(null==selectedVehicles || "".equals(selectedVehicles.trim()))
			throw new ProtocolParseBusinessException("msgResultContent","没有选择车辆");
		if(selectedVehicles.lastIndexOf(",")==selectedVehicles.length()-1){
			selectedVehicles=selectedVehicles.substring(0,selectedVehicles.lastIndexOf(","));
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles =null;
		Integer userId=SessionManager.getCurrentUserId(request);
		Integer companyId=SessionManager.getCurrentCompanyId(request);
		if(!(null==multipartRequest
				|| null==multipartRequest.getMultiFileMap() || null==multipartRequest.getMultiFileMap().get("declareFile"))){
			Date currentDate=new Date();
			if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
				Date currentDay=DateTime.toDate(DateTime.getNormalDate());
				if(DateTime.getSecondDifference(DateTime.addDays(currentDay, 3),filingProposal.getStartTime()) <0 ){
					throw new ProtocolParseBusinessException("msgResultContent","开始日期必须为三天后的日期");
				}
				if(DateTime.getSecondDifferenceNew(filingProposal.getStartTime(),filingProposal.getEndTime()) > 5*24*3600){
					throw new ProtocolParseBusinessException("msgResultContent","报备开始日期和结束日期时间差不能大于5天");
				}
			}
			
			/**
			 List<String> existsProposalVehicles=filingProposalDao.getExistsFilingProposal(companyId, filingProposalType);
			 if(isExistsFilingProposal(selectedVehicles,existsProposalVehicles))
				throw new ProtocolParseBusinessException("msgResultContent","部分车辆还处于待审核中");
				*/
			
			multipartFiles=multipartRequest.getMultiFileMap().get("declareFile");
			UploadHelper.validatorListFiles(multipartFiles);
			
			filingProposal.setProposalTime(currentDate);
			filingProposal.setProposerId(userId);
			filingProposal.setCompanyId(companyId);
			filingProposal.setStatus(GlobalConstant.FILING_PROPOSAL_STATUS_INIT);
			filingProposal.setType(filingProposalType);
			filingProposal.setCreateTime(currentDate);
			filingProposal.setUpdateTime(currentDate);
			Integer filingProposalId=filingProposalDao.addFilingProposal(filingProposal,selectedVehicles);
			
			for(MultipartFile multipartFile:multipartFiles){
				uploadFile(companyId,multipartFile,filingProposalId,currentDate);
			}
		}

		List<FilingProposal> filingProposalList=getFilingProposalList(null,companyId,filingProposalType,null,null,false);
		return filingProposalList;
	}
	
	/**
	 * 添加报备
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposal> addFilingProposalNew( MultipartFile[] files,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles, Integer userId, Integer companyId) {
		if(null==selectedVehicles || "".equals(selectedVehicles.trim()))
			throw new ProtocolParseBusinessException("msgResultContent","没有选择车辆");
		if(selectedVehicles.lastIndexOf(",")==selectedVehicles.length()-1){
			selectedVehicles=selectedVehicles.substring(0,selectedVehicles.lastIndexOf(","));
		}
		
		Date currentDate=new Date();
		if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
//			Date currentDay=DateTime.toDate(DateTime.getNormalDate());
//			if(DateTime.getSecondDifference(DateTime.getNextDays(new Date(), new Integer(1)),filingProposal.getStartTime()) <0 ){
			if(DateTime.getSecondDifference(currentDate,filingProposal.getStartTime()) <=0 || DateTime.getSecondDifference(currentDate,filingProposal.getEndTime()) <=0){
				throw new ProtocolParseBusinessException("msgResultContent",this.getFilingProposalStartTimeCheckAlertMsg());
			}
			if(DateTime.getSecondDifferenceNew(filingProposal.getStartTime(),filingProposal.getEndTime()) > getFilingProposalDateRang()*24*3600){
				throw new ProtocolParseBusinessException("msgResultContent",getFilingProposalDateRangAlertMsg());
			}
			//增加检查报备时间区间最小值，初始最小为60分钟，也就是一小时
			if(DateTime.getSecondDifferenceNew(filingProposal.getStartTime(),filingProposal.getEndTime()) < getFilingProposalDateMinRang()*60){
				throw new ProtocolParseBusinessException("msgResultContent",getFilingProposalDateMinRangAlertMsg());
			}
		}
		
		filingProposal.setProposalTime(currentDate);
		filingProposal.setProposerId(userId);
		filingProposal.setCompanyId(companyId);
		filingProposal.setStatus(GlobalConstant.FILING_PROPOSAL_STATUS_INIT);
		filingProposal.setType(filingProposalType);
		filingProposal.setCreateTime(currentDate);
		filingProposal.setUpdateTime(currentDate);
		Integer filingProposalId=filingProposalDao.addFilingProposal(filingProposal,selectedVehicles);
		
		for(MultipartFile multipartFile:files){
			uploadFile(companyId,multipartFile,filingProposalId,currentDate);
		}
		List<FilingProposal> filingProposalList=getFilingProposalList(null,companyId,filingProposalType,null,null,false);
		return filingProposalList;
	}
	
	/**
	 * 编辑报备(不允许重新选择车辆)
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposal> editFilingProposal(HttpServletRequest request,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles) {
		if(null==filingProposal || null==filingProposal.getId() )
			throw new ProtocolParseBusinessException("msgResultContent","报备信息缺失");
		//selectedVehicles=getSelectedVehicle(selectedVehicles);
		Integer companyId=SessionManager.getCurrentCompanyId(request);
		Date currentDate=new Date();
		FilingProposal editFilingProposal=filingProposalDao.getFilingProposalById(filingProposal.getId());
		if(null==editFilingProposal || editFilingProposal.getStatus()!=GlobalConstant.FILING_PROPOSAL_STATUS_INIT)
			throw new ProtocolParseBusinessException("msgResultContent","报备已处理不能编辑");

		//handleSelectedVehicleForEdit(companyId,filingProposalType,editFilingProposal.getId(),selectedVehicles);车辆不允许编辑  故去掉
		
		if (filingProposal.getStartTime() != null) {
			editFilingProposal.setStartTime(filingProposal.getStartTime());
		}
		if (filingProposal.getEndTime() != null) {
			editFilingProposal.setEndTime(filingProposal.getEndTime());
		}
		editFilingProposal.setDescription(filingProposal.getDescription());
		editFilingProposal.setDescription(filingProposal.getDescription());
		editFilingProposal.setReason(filingProposal.getReason());
		editFilingProposal.setUpdateTime(currentDate);
		editFilingProposal.setStatus(GlobalConstant.FILING_PROPOSAL_STATUS_INIT);

		filingProposalDao.editFilingProposal(editFilingProposal,selectedVehicles);
		
		List<FilingProposal> filingProposalList=getFilingProposalList(null,companyId,filingProposalType,null,null,false);
		return filingProposalList;
	}
	
	private String getSelectedVehicle(String selectedVehicles){
		String resultSelectedVehicles=selectedVehicles;
		if(null==resultSelectedVehicles || "".equals(resultSelectedVehicles.trim()))
			throw new ProtocolParseBusinessException("msgResultContent","没有选择车辆");
		if(resultSelectedVehicles.lastIndexOf(",")==resultSelectedVehicles.length()-1){
			resultSelectedVehicles=resultSelectedVehicles.substring(0,resultSelectedVehicles.lastIndexOf(","));
		}
		return resultSelectedVehicles;
	}
	/**
	 * 
	* Description: 执行先删除本次报备已入库的车辆 然后再判定是否有选择的车辆处于待审核状态
	* @author henggh 
	* @version 0.1 
	* @param filingProposalType
	* @param filingProposalId
	* @param selectedVehicles
	 */
	private void handleSelectedVehicleForEdit(Integer companyId,Integer filingProposalType, Integer filingProposalId,String selectedVehicles){
		filingProposalDao.deleteFilingProposalDetail(companyId, filingProposalId);
		if(filingProposalDao.isExistsFilingProposal(companyId,filingProposalType,selectedVehicles))
			throw new ProtocolParseBusinessException("msgResultContent","有车辆还处于待审核中");
	}
	
	@Override
	public Map editFilingProposalView(Integer filingProposalId, Integer filingProposalType) {
		FilingProposal filingProposal=filingProposalDao.getFilingProposalById(filingProposalId);
		if(null==filingProposal || null==filingProposal.getStatus() || filingProposal.getStatus()!=GlobalConstant.FILING_PROPOSAL_STATUS_INIT){
			throw new ProtocolParseBusinessException("msgResultContent","报备已被处理过编辑无法保存");
		}
		Map map=new HashMap();
		Integer companyId = filingProposal.getCompanyId();
		Company company=CacheCompanyManager.getCompany(companyId);
		DealerVO dealerVO=new DealerVO(company);
		map.put("dealer", dealerVO);
		List<VehicleStyle> vehicleStyles=filingProposalDao.getVehicleByFilingProposal(companyId,filingProposalId);
		List<FilingProposalDetail> filingProposalDetails=filingProposalDao.getFilingProposalDetail(filingProposalId,companyId);
		//map.put("vehicleStyles", getFinalVehicleStyle(vehicleStyles,filingProposalDetails));
		map.put("vehicleStyles", vehicleStyles);
		map.put("filingProposal", filingProposal);
		return map;
	}
	
	@Override
	public Map getFilingProposalDetail(Integer filingProposalId,Integer filingProposalType) {
		if(null==filingProposalId)
			throw new ProtocolParseBusinessException("msgResultContent","报备信息缺失");
		HashMap parameters=new HashMap();
		parameters.put("id", filingProposalId);
		parameters.put("type", filingProposalType);
		FilingProposal filingProposal=filingProposalDao.getFilingProposal(parameters);
		if(null==filingProposal)
			throw new ProtocolParseBusinessException("msgResultContent","报备不存在");
		Map map=new HashMap();
		Company company=CacheCompanyManager.getCompany(filingProposal.getCompanyId());
		DealerVO dealerVO=new DealerVO(company);
		map.put("dealer", dealerVO);
		List<VehicleStyle> vehicleStyles=filingProposalDao.getVehicleByFilingProposal(filingProposal.getCompanyId(),filingProposalId);
		map.put("vehicleStyles", vehicleStyles);
		map.put("filingProposal", filingProposal);
		return map;
	}
	
	@Override
	public Map getFilingProposalInfo(Integer companyId, Integer filingProposalId) {
		Map map=new HashMap();
		Company company=CacheCompanyManager.getCompany(companyId);
		DealerVO dealerVO=new DealerVO(company);
		map.put("dealer", dealerVO);
		List<VehicleStyle> vehicleStyles=filingProposalDao.getVehicleByFilingProposal(companyId,filingProposalId);
		map.put("vehicleStyles", vehicleStyles);
		return map;
	}
	
	/**
	 * 取消报备
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void cancelFilingProposal(Integer filingProposalId, Integer companyId, Integer type) {
		FilingProposal filingProposal=filingProposalDao.getFilingProposalById(filingProposalId);
		if(null==filingProposal.getStatus() || filingProposal.getStatus()!=GlobalConstant.FILING_PROPOSAL_STATUS_INIT){
			throw new ProtocolParseBusinessException("msgResultContent","待取消的报备已被处理过");
		}
		filingProposal.setStatus(GlobalConstant.FILING_PROPOSAL_STATUS_CANCEL);
		filingProposalDao.cancelFilingProposal(filingProposal);
		//增加还原报备时间区间内相关试驾隐藏状态
		updateTestDrive4CancelFilingProposal(filingProposal.getId(), filingProposal.getStartTime(), filingProposal.getEndTime(), filingProposal.getStatus());
	}
	
	/**
	 * 获取报备附件列表（报备附件以K为单位）
	 */
	@Override
	public List<FilingProposalAttachmentVO> getFilingProposalAttachList(Integer filingProposalId) {
		List<FilingProposalAttachment> filingProposalAttachmentList=filingProposalDao.getFilingProposalAttachment(filingProposalId);
		List<FilingProposalAttachmentVO> fpaVOList=copyListProperties(filingProposalAttachmentList,FilingProposalAttachmentVO.class);
		setDeclareAttachFileSize(fpaVOList);
		return fpaVOList;
	}
	
	/**
	 * 获取报备附件列表（报备附件以Byte为单位）
	 */
	private List<FilingProposalAttachment> getFilingProposalAttachOriginalList(Integer filingProposalId) {
		List<FilingProposalAttachment> filingProposalAttachmentList=filingProposalDao.getFilingProposalAttachment(filingProposalId);
		return filingProposalAttachmentList;
	}
	
	/**
	 * 删除附件
	 */
	@Override
	public void delFilingProposalFile(Integer filingProposalId,String delFiles) {
		filingProposalDao.deleteFilingProposalFile(filingProposalId, delFiles);
	}
	
	/**
	 * 附件管理
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposalAttachmentVO> uploadFile(HttpServletRequest request,Integer filingProposalId){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if(!(null==multipartRequest 
				|| null==multipartRequest.getFiles("attach") 
				|| multipartRequest.getFiles("attach").isEmpty()
				|| null==multipartRequest.getFiles("attach").get(0)
				|| multipartRequest.getFiles("attach").get(0).isEmpty())){
			List<FilingProposalAttachment> filingProposalAttachments=getFilingProposalAttachOriginalList(filingProposalId);
			long existsFileSize=0L;
			int existsFileNum=0;
			if(!(null==filingProposalAttachments || filingProposalAttachments.isEmpty())){
				existsFileNum=filingProposalAttachments.size();
				for(FilingProposalAttachment filingProposalAttachment:filingProposalAttachments){
					existsFileSize+=Long.parseLong(filingProposalAttachment.getSize());
				}
			}
			List<MultipartFile> multipartFiles =null;
			Map map=UploadHelper.getFileSet(request,existsFileNum,existsFileSize);
			multipartFiles= (List<MultipartFile>) map.get("multipartFileList");
			if(null==multipartFiles || multipartFiles.size()<=0){
				throw new ProtocolParseBusinessException("msgResultContent","上传文件为空");
			}else{
				Date currentDate=new Date();
				User user=SessionManager.getCurrentUser(request);
				for (MultipartFile multipartFile : multipartFiles) {
					String rtnFile[] = UploadHelper.uploadFile(multipartFile,filingProposalId);
					if(null!=rtnFile){
						FilingProposalAttachment fpa=new FilingProposalAttachment();
						fpa.setCreateId(user.getId());
						fpa.setCreateTime(currentDate);
						fpa.setProposalId(filingProposalId);
						fpa.setOriginalName(rtnFile[0]);
						fpa.setDisplayName(rtnFile[1]);
						fpa.setFullPath(rtnFile[2]);
						fpa.setType(rtnFile[3]);
						fpa.setSize(String.valueOf(multipartFile.getSize()));
						fpa.setStatus(1);
						filingProposalDao.saveFilingProposalAttachment(fpa);
					}
				}
			}
		}

		return getFilingProposalAttachList(filingProposalId);
	}

	/**
	 * 添加报备时保存附件
	 * @param request
	 * @param multipartFile
	 * @param filingProposalId
	 * @return
	 */

	private void uploadFile(Integer userId,MultipartFile multipartFile, Integer filingProposalId,Date date) {
		FilingProposalAttachment fpa = new FilingProposalAttachment();
		fpa.setCreateId(userId);
		fpa.setCreateTime(date);
		fpa.setProposalId(filingProposalId);
		String rtnFile[] = UploadHelper.uploadFile(multipartFile,filingProposalId);
		if (null != rtnFile) {
			fpa.setOriginalName(rtnFile[0]);
			fpa.setDisplayName(rtnFile[1]);
			fpa.setFullPath(rtnFile[2]);
			fpa.setType(rtnFile[3]);
			fpa.setSize(String.valueOf(multipartFile.getSize()));
			fpa.setStatus(1);
			fpa.setCreateTime(date);
			filingProposalDao.saveFilingProposalAttachment(fpa);
		}
	}
	
	public Map getAddFilingProposalInfo(Integer companyId,String selectedVehicles){
		Map map=new HashMap();
		Company company=CacheCompanyManager.getCompany(companyId);
		DealerVO dealerVO=new DealerVO(company);
		map.put("dealer", dealerVO);
		List<VehicleStyle> vehicleStyles=filingProposalDao.getVehicleByCompany(companyId);
		map.put("vehicleStyles", genVehicleStyle(vehicleStyles,selectedVehicles));
		return map;
	}
	
	@Override
	public List<FilingProposalVO> approveFilingProposalList(TransMsg transMsg,Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,String dealerCode,String beginTime, String endTime,boolean isExport) {
		StringBuffer queryList=new StringBuffer("");
		StringBuffer queryCount=new StringBuffer("");
		queryList.append("select vdi.saleCenterId,vdi.saleCenterName,vdi.isKeyCityNick,vdi.dealerTypeNick,vdi.dealerCode,");
		queryList.append(" vdi.provinceName,vdi.cityName,vdi.companyName, " );
		queryList.append(" tfp.id as filingProposalId,tfp.proposal_time as proposalTime,tfp.start_time as startTime,tfp.end_time as endTime,tfp.type,tfp.status," );
		queryList.append(" case tfp.type when 1 then '市场活动' when 2 then '事故维修' else '未知' end as filingType," );
		queryList.append(" case tfp.status when 0 then '待审核' when 1 then '同意' when 2 then '拒绝' when 3 then '取消' else '未知' end as filingStatus " );
		queryList.append(" from t_filing_proposal tfp, view_dealer_info vdi where tfp.company_id=vdi.companyId and tfp.status=0 ");	
		queryCount.append(" select count(1) from t_filing_proposal tfp, view_dealer_info vdi where tfp.company_id=vdi.companyId and tfp.status=0 ");
		if(null!=beginTime && !"".equals(beginTime.trim())){
			queryList.append(" and tfp.start_time>=date_format('").append(beginTime).append("','%Y-%m-%d %T')");
			queryCount.append(" and tfp.start_time>=date_format('").append(beginTime).append("','%Y-%m-%d %T')");
		}
		if(null!=endTime && !"".equals(endTime.trim())){
			queryList.append(" and tfp.start_time<=date_format('").append(endTime).append("','%Y-%m-%d %T')");
			queryCount.append(" and tfp.start_time<=date_format('").append(endTime).append("','%Y-%m-%d %T')");
		}	
		if(null!=selectSaleCenterId && selectSaleCenterId.intValue()!=Integer.parseInt(GlobalConstant.NETWORK_DEVELOPMENT_ID) ){
			queryList.append(" and vdi.saleCenterId="+selectSaleCenterId);
			queryCount.append(" and vdi.saleCenterId="+selectSaleCenterId);
		}
		if(null!=dealerCode && dealerCode.trim().length()>=1){
			queryList.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
			queryCount.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
		}
		queryList.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryCount.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryList.append(" order by vdi.saleCenterId, vdi.dealerCode");
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		//设置分页
		queryList.append(getPaginationSql(transMsg, queryCount, isExport));
		List<FilingProposalVO> filingProposalVoList = filingProposalDao.exeSqlQueryList(queryList.toString(),FilingProposalVO.class);
		
		return filingProposalVoList;
	}
	
	@Override
	public List<FilingProposalHis> getFilingProposalHisList(TransMsg transMsg,Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,String dealerCode,String beginTime, String endTime,boolean isExport) {
		StringBuffer queryList=new StringBuffer("");
		StringBuffer queryCount=new StringBuffer("");
		queryList.append("select vdi.saleCenterId, vdi.saleCenterName, vdi.provinceName, vdi.cityName, vdi.dealerTypeNick, vdi.isKeyCityNick, ");
		queryList.append(" vdi.dealerCode, vdi.companyName, vdi.parentDealerCode,vdi.parentCompanyName, u.nick_name as approvalName," );
		queryList.append(" vvs.plateNumber,vvs.vehicleStyle, " );
		queryList.append(" tfp.id as filingProposalId,tfp.type,tfp.status,tfp.proposal_time as proposalTime, tfp.approval_time as approvalTime, " );
		queryList.append(" tfp.start_time as startTime,tfp.end_time as endTime, " );
		queryList.append(" case tfp.type when 1 then '市场活动' when 2 then '事故维修' else '未知' end as filingType," );
		queryList.append(" case tfp.status when 0 then '待审核' when 1 then '同意' when 2 then '拒绝' when 3 then '取消' else '未知' end as filingStatus " );
		
		queryList.append(" from t_filing_proposal tfp ,t_filing_proposal_detail tfpd,view_dealer_info vdi, t_user u, view_vehicle_style vvs ");
		queryList.append(" where tfpd.vehicle_id=vvs.vehicleId and vvs.companyId=tfpd.company_id ");
		queryList.append(" and tfpd.company_id=vdi.companyId ");
		queryList.append(" and tfp.approval_id=u.id ");
		queryList.append(" and tfp.id=tfpd.proposal_id and tfp.status in (1,2) ");
		
		queryCount.append(" select count(1) from t_filing_proposal tfp ,t_filing_proposal_detail tfpd,view_dealer_info vdi, view_vehicle_style vvs ");
		queryCount.append(" where tfpd.vehicle_id=vvs.vehicleId and vvs.companyId=tfpd.company_id ");
		queryCount.append(" and tfpd.company_id=vdi.companyId ");
		queryCount.append(" and tfp.id=tfpd.proposal_id and tfp.status in (1,2) ");
		
		if(null!=beginTime && !"".equals(beginTime.trim())){
			queryList.append(" and tfp.start_time>=date_format('").append(beginTime).append("','%Y-%m-%d %T')");
			queryCount.append(" and tfp.start_time>=date_format('").append(beginTime).append("','%Y-%m-%d %T')");
		}
		if(null!=endTime && !"".equals(endTime.trim())){
			queryList.append(" and tfp.start_time<=date_format('").append(endTime).append("','%Y-%m-%d %T')");
			queryCount.append(" and tfp.start_time<=date_format('").append(endTime).append("','%Y-%m-%d %T')");
		}
		if(null!=selectSaleCenterId && selectSaleCenterId.intValue()!=Integer.parseInt(GlobalConstant.NETWORK_DEVELOPMENT_ID) ){
			queryList.append(" and vdi.saleCenterId="+selectSaleCenterId);
			queryCount.append(" and vdi.saleCenterId="+selectSaleCenterId);
		}
		if(null!=dealerCode && dealerCode.trim().length()>=1){
			queryList.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
			queryCount.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
		}
		queryList.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryCount.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryList.append(" order by vdi.saleCenterId, vdi.dealerCode");
		//设置分页
		queryList.append(getPaginationSql(transMsg, queryCount, isExport));
		List<FilingProposalHis> filingProposalHisList = filingProposalDao.exeSqlQueryList(queryList.toString(),FilingProposalHis.class);
		
		return filingProposalHisList;
	}
	
	public Map approveFilingProposalView(Integer filingProposalId) {
		if(null==filingProposalId)
			throw new ProtocolParseBusinessException("msgResultContent","报备信息缺失");
		FilingProposal filingProposal=filingProposalDao.getFilingProposalById(filingProposalId);
		if(null==filingProposal)
			throw new ProtocolParseBusinessException("msgResultContent","报备不存在");
		Map map=new HashMap();
		Company company=CacheCompanyManager.getCompany(filingProposal.getCompanyId());
		DealerVO dealerVO=new DealerVO(company);
		map.put("dealer", dealerVO);
		List<VehicleStyle> vehicleStyles=filingProposalDao.getVehicleByFilingProposal(filingProposal.getCompanyId(),filingProposalId);
		map.put("vehicleStyles", vehicleStyles);
		map.put("filingProposal", filingProposal);
		
		List<FilingProposalAttachment> fpaVOList = getFilingProposalAttachOriginalList(filingProposalId);
		map.put("fpaVOList", fpaVOList);
		
		return map;
	}
	
	/**
	 * 审核报备
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposalVO> approveFilingProposal(FilingProposal filingProposal,Integer filingProposalId,Integer currentCompanyId, Integer currentUserId) {
		if(null==filingProposalId)
			throw new ProtocolParseBusinessException("msgResultContent","报备信息缺失");

		Date currentDate=new Date();
		FilingProposal editFilingProposal=filingProposalDao.getFilingProposalById(filingProposalId);
		if(null==editFilingProposal || editFilingProposal.getStatus()!=GlobalConstant.FILING_PROPOSAL_STATUS_INIT)
			throw new ProtocolParseBusinessException("msgResultContent","报备已处理不能再次审核");

		editFilingProposal.setApprovalId(currentUserId);
		editFilingProposal.setApprovalMessage(filingProposal.getApprovalMessage());
		editFilingProposal.setApprovalTime(currentDate);
		editFilingProposal.setStatus(filingProposal.getStatus());

		filingProposalDao.editFilingProposal(editFilingProposal,null);
		
		handleTestDrive(editFilingProposal.getId(), editFilingProposal.getStartTime(), editFilingProposal.getEndTime(), filingProposal.getStatus());
		
		List<FilingProposalVO> filingProposalVoList = approveFilingProposalList(null,currentCompanyId,currentUserId,null,null,null,null,false);
		return filingProposalVoList;
	}
	@Override
	public FilingProposalAttachment getFilingProposalAttachment(Integer filingProposalId,Integer fileId) {
		HashMap parameters=new HashMap();
		parameters.put("id", fileId);
		parameters.put("proposalId", filingProposalId);
		FilingProposalAttachment filingProposalAttachment=filingProposalDao.getFilingProposalAttachment(parameters);
		return filingProposalAttachment;
	}
	
	/**
	 * 
	* Description: 主要用于保存、编辑失败时将已选中的报备选中
	* @author henggh 
	* @version 0.1 
	* @param vehicleStyles
	* @param selectedVehicles
	* @return
	 */
	private List<VehicleStyle> genVehicleStyle(List<VehicleStyle> vehicleStyles,String selectedVehicles){
		if(null==vehicleStyles || vehicleStyles.isEmpty())
			return vehicleStyles;
		if(null==selectedVehicles || selectedVehicles.trim().length()<=0)
			return vehicleStyles;
		if(selectedVehicles.lastIndexOf(",")==selectedVehicles.length()-1){
			selectedVehicles=selectedVehicles.substring(0,selectedVehicles.lastIndexOf(","));
		}
		String[] vehicleIds=selectedVehicles.split(",");
		for(VehicleStyle vehicleStyle : vehicleStyles){
			if(isSelectedVehicle(vehicleStyle.getVehicleId(),vehicleIds))
				vehicleStyle.setChecked("1");
		}
		return vehicleStyles;
	}
	private boolean isExistsFilingProposal(String selectedVehicles, List<String> existsFilingProposalVehicles){
		if(null==existsFilingProposalVehicles || existsFilingProposalVehicles.isEmpty())
			return false;
		String[] checkedVehicle=selectedVehicles.split(",");
		for(String vehicles : existsFilingProposalVehicles){
			String[] dbVehicles =vehicles.split(",");
			for(String dbVehicle : dbVehicles){
				if(isSelectedVehicle(Integer.parseInt(dbVehicle),checkedVehicle))
					return true;
			}
		}
		return false;
	}
	private boolean isSelectedVehicle(Integer vehicleId,String[] vehicleIds){
		for(String selectedVehicleId : vehicleIds){
			if(Integer.parseInt(selectedVehicleId) == vehicleId)
				return true;
		}
		return false;
	}
	
	/**
	 * 
	* Description: 选择当前经销商所拥有的所有车辆
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @return
	 */
	private List<VehicleStyle> getVehicle(Integer companyId){
		return filingProposalDao.getVehicleByCompany(companyId);
	}
	
	/**
	 * 
	* Description: 主要用于处理编辑报备时将本报备对应的车辆选中
	* @author henggh 
	* @version 0.1 
	* @param vehicleStyles
	* @param filingProposalDetails
	* @return
	 */
	public List<VehicleStyle> getFinalVehicleStyle(List<VehicleStyle> vehicleStyles, List<FilingProposalDetail> filingProposalDetails){
		List<VehicleStyle> vehicleStyleFinal= vehicleStyles;
		if(null==vehicleStyleFinal || vehicleStyleFinal.isEmpty() || null==filingProposalDetails || filingProposalDetails.isEmpty())
			return vehicleStyleFinal;
		for(VehicleStyle vehicleStyle : vehicleStyleFinal){
			for(FilingProposalDetail filingProposalDetail : filingProposalDetails){
				if(vehicleStyle.getVehicleId().intValue()==filingProposalDetail.getVehicleId().intValue()){
					vehicleStyle.setChecked("1");
					break;
				}
			}
		}
		return vehicleStyleFinal;
	}

	
	
	public StringBuffer getAccessSql(Integer currentCompanyId, Integer currentUserId) {
		StringBuffer exeSql = new StringBuffer("");
		if (currentCompanyId != null && currentUserId != null) {
			if (userRoleRelationDao.getMaxRoleId(currentUserId) == GlobalConstant.DISTRIBUTION_CENTER_ADMIN_ROLE_ID) {
				exeSql.append(" and vdi.saleCenterId = ").append(currentCompanyId);
			}
			if (userRoleRelationDao.getMaxRoleId(currentUserId) == GlobalConstant.DEFAULT_DEALER_ROLE_ID) {
				exeSql.append(" and vdi.companyId in ( ").append(CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)).append( ")");
			}
		}
		
		return exeSql;
	}
	/**
	 * 处理上一级有多个管理员情况
	 * @param users
	 * @param fp
	 */
	private void sendEmail(FilingProposal fp){
		List<User> users=null;
		Org org=null;
		if(null!=fp && null!=fp.getCompanyId()){
			org=CacheOrgManager.get(fp.getCompanyId());
		}
		if(null!=org && null!=org.getParentId()){
			users= CacheUserManager.findUserByOrgId(org.getParentId(),GlobalConstant.DISTRIBUTION_CENTER_ADMIN_ROLE_ID);
		}
		if(null!=users && !users.isEmpty()){
			for(User user:users){
				if(null!=user && null!=user.getEmail() && !"".equals(user.getEmail())){
					sendEmail(user,fp);
				}
			}
		}
	}
	
	/**
	 * 发送邮件
	 * @param parentAdmin
	 * @param filingProposal
	 * @return
	 */
	private Integer sendEmail(User parentAdmin,FilingProposal filingProposal){
		StringBuffer sb=new StringBuffer("");
		sb.append("现有一个报备需要您审批，具体信息如下：<br>");
		//sb.append("提交时间："+DateTime.getDate(filingProposal.getCommitTime(),"yyyy-MM-dd HH:mm:ss"));
		sb.append("</br>报备类型："+(filingProposal.getType().intValue()==1 ? "外出报备":"维修报备"));
		sb.append("<br>开始日期："+DateTime.getDate(filingProposal.getStartTime(),"yyyy-MM-dd HH:mm:ss")).append("</br>结束日期："+DateTime.getDate(filingProposal.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
		sb.append("</br>需要您审批");
		Integer resultCode=SendEMailUtil.sendMessage(parentAdmin.getLoginName(), parentAdmin.getEmail(), "需要审核的报备信息", sb.toString());
		return resultCode;
	}
	
	/**
	 * 处理试驾信息和违规信息
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param startTime
	 * @param endTime
	 * @param status
	 */
	private void handleTestDrive(Integer proposalId,Date startTime,Date endTime, Integer status){
		StringBuffer testDriveSql=new StringBuffer("");
		if(GlobalConstant.FILING_PROPOSAL_STATUS_YES==status){
			testDriveSql.append("update t_test_drive set status=5 where start_time>='").append(DateTime.toNormalDateTime(startTime)).append("' ");
			testDriveSql.append("and end_time<='").append(DateTime.toNormalDateTime(endTime)).append("' ");
			testDriveSql.append("and vehicle_id in ( select vehicle_id from t_filing_proposal_detail where proposal_id=").append(proposalId).append(")");
			filingProposalDao.updateTestDrive(testDriveSql.toString());
			
			testDriveSql.setLength(0);
			testDriveSql.append("update t_violation set deletion_flag=1 where deletion_flag=0 and start_time>='").append(DateTime.toNormalDateTime(startTime)).append("' ");
			testDriveSql.append("and end_time<='").append(DateTime.toNormalDateTime(endTime)).append("' ");
			testDriveSql.append("and vehicle_id in ( select vehicle_id from t_filing_proposal_detail where proposal_id=").append(proposalId).append(")");	
			filingProposalDao.updateTestDrive(testDriveSql.toString());
		}
		//报备拒绝将有效试驾更新为违规,只把currentStatus改为1
		if(GlobalConstant.FILING_PROPOSAL_STATUS_NO==status){
			updateTestDrive4CancelFilingProposal(proposalId, startTime, endTime, status);
		}
		
	}
	
	/**
	 * 经销商主动发起取消报备，相关已完成的试驾，需要修改隐藏状态
	 *
	 * @author wangchao
	 * @version 
	 * @param proposalId
	 * @param startTime
	 * @param endTime
	 * @param status
	 */
	private void updateTestDrive4CancelFilingProposal(Integer proposalId,Date startTime,Date endTime, Integer status){
		StringBuffer testDriveSql=new StringBuffer("");
		testDriveSql.append("update t_test_drive set current_status=1 where current_status = 3 and start_time>='").append(DateTime.toNormalDateTime(startTime)).append("' ");
		testDriveSql.append("and end_time<='").append(DateTime.toNormalDateTime(endTime)).append("' ");
		testDriveSql.append("and vehicle_id in ( select vehicle_id from t_filing_proposal_detail where proposal_id=").append(proposalId).append(")");
		filingProposalDao.updateTestDrive(testDriveSql.toString());
		
		testDriveSql.setLength(0);
		testDriveSql.append("update t_violation set deletion_flag=0 where deletion_flag=1 and start_time>='").append(DateTime.toNormalDateTime(startTime)).append("' ");
		testDriveSql.append("and end_time<='").append(DateTime.toNormalDateTime(endTime)).append("' ");
		testDriveSql.append("and vehicle_id in ( select vehicle_id from t_filing_proposal_detail where proposal_id=").append(proposalId).append(")");	
		filingProposalDao.updateTestDrive(testDriveSql.toString());
	}
	
	@Override
	public List<FilingProposal> getFilingProposalByVehicle(Integer vehicleId,String workTime) {
		StringBuffer queryList=new StringBuffer("");
		queryList.append("select tfp.* from t_filing_proposal tfp,t_filing_proposal_detail tfpd ");
		queryList.append(" where tfp.id=tfpd.proposal_id and tfp.status=1 " );
		queryList.append(" and start_time<=str_to_date('").append(workTime).append("','%Y-%m-%d %T')");
		queryList.append(" and end_time>=str_to_date('").append(workTime).append("','%Y-%m-%d %T')");
		queryList.append(" and tfpd.vehicle_id=").append(vehicleId);
		
		List<FilingProposal> filingProposalHisList = filingProposalDao.exeSqlQueryList(queryList.toString(),FilingProposal.class);
		
		return filingProposalHisList;
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
	public boolean hasApprovedFilingProposal(Integer vehicleId, String startTime, String endTime, String status){
		return filingProposalDao.hasFilingProposal(vehicleId,startTime,endTime,status);
	}
	
	private void setDeclareAttachFileSize(List<FilingProposalAttachmentVO> filingProposalAttachments) {
		//List<FilingProposalAttachmentVO> filingProposalAttachmentList=new ArrayList<FilingProposalAttachmentVO>();
		if(null!=filingProposalAttachments && !filingProposalAttachments.isEmpty()){
			for(FilingProposalAttachmentVO filingProposalAttachment:filingProposalAttachments){
				filingProposalAttachment.setSize((new BigDecimal(filingProposalAttachment.getSize()).divide(new BigDecimal(1024*1024),2,BigDecimal.ROUND_HALF_UP)).toString());
				//filingProposalAttachmentList.add(filingProposalAttachment);
			}
		}
	}
	
	private <M,N> List<N> copyListProperties(List<M> sourceList,Class<N> classz){
		List<N> resultList=new ArrayList<N>();
		if(null!=sourceList && !sourceList.isEmpty()){
			
			try {
				for (M m : sourceList) {
					N target;
					target = (N) classz.newInstance();
					BeanUtils.copyProperties(m, target);
					resultList.add(target);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return resultList;
	}
	
	/**
	 * 获取报备提前小时数设定
	 *
	 * @author wangchao
	 * @version 
	 * @return
	 */
//	private int getFilingProposalStartTimeSetting(){
//		return GlobalConfig.getInt("filing_proposal_start_date_setting");
//	}
	
	/**
	 * 获取报备提前天数设定告警信息
	 *
	 * @author wangchao
	 * @version 
	 * @return
	 */
	private String getFilingProposalStartTimeCheckAlertMsg(){
		return GlobalConfig.getString("filing_proposal_start_date_alert_msg");
	}
	
	/**
	 * 允许提前报备的开始结束时间最大时间间隔，必须是整数，单位（天）
	 *
	 * @author wangchao
	 * @version 
	 * @return
	 */
	private int getFilingProposalDateRang(){
		return GlobalConfig.getInt("filing_proposal_date_rang");
	}
	
	/**
	 * 允许提前报备的开始结束时间最大时间间隔，必须是整数，单位（分钟）
	 *
	 * @author wangchao
	 * @version 
	 * @return
	 */
	private int getFilingProposalDateMinRang(){
		return GlobalConfig.getInt("filing_proposal_date_min_rang");
	}
	
	/**
	 * 获取报备提前天数设定告警信息
	 *
	 * @author wangchao
	 * @version 
	 * @return
	 */
	private String getFilingProposalDateRangAlertMsg(){
		return GlobalConfig.getString("filing_proposal_date_rang_alert_msg");
	}
	
	/**
	 * 获取报备最小时间设定告警信息
	 *
	 * @author wangchao
	 * @version 
	 * @return
	 */
	private String getFilingProposalDateMinRangAlertMsg(){
		return GlobalConfig.getString("filing_proposal_date_min_rang_alert_msg");
	}

	
}