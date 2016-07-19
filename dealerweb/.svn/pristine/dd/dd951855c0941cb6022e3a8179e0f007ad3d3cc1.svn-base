package com.ava.dealer.service.impl;

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

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IApprovalDao;
import com.ava.dao.IBoxOperationDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IProposalDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.IProposalService;
import com.ava.domain.entity.AddPlateNumberCity;
import com.ava.domain.entity.Approval;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.ProposalAttachment;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.ApprovalVO;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;
import com.ava.domain.vo.CompanyVO;
import com.ava.domain.vo.DemolitionFindVO;
import com.ava.domain.vo.ProposalCompanyVehcileInfo;
import com.ava.domain.vo.ProposalFindVO;
import com.ava.domain.vo.ProposalVO;
import com.ava.domain.vo.ReplaceFindVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheBoxManager;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;
import com.ava.util.upload.UploadHelper;

@Service
public class ProposalService extends BaseService implements IProposalService {	
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IProposalDao proposalDao;
	
	@Autowired
	private IApprovalDao approvalDao;
	
	@Autowired
	private IVehicleDao  vehicleDao;
	
	@Autowired
	private IBoxOperationDao boxOperationDao;

	@Override
	public ResponseData displayAddProposal(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer companyId) {
		ResponseData rd = new ResponseData(0);
		if (proposalCompanyVehcileInfo == null) {
			proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
		}
		
		if(null != companyId){
			Company dbCompany = CacheCompanyManager.getCompany(companyId);
			if (dbCompany != null) {
				CompanyVO companyVO = new CompanyVO(dbCompany);
				
				HashMap<Object, Object> parameters = new HashMap<Object, Object>();
				parameters.put("cityId", dbCompany.getRegionCityId());
				AddPlateNumberCity addPlateNumberCity = hibernateDao.get(" AddPlateNumberCity ", parameters);
				if (addPlateNumberCity != null) {
					proposalCompanyVehcileInfo.getProposal().setIsAddPlatenumberCity(GlobalConstant.TRUE);
				} else {
					proposalCompanyVehcileInfo.getProposal().setIsAddPlatenumberCity(GlobalConstant.FALSE);
				}
				
				proposalCompanyVehcileInfo.setCompany(companyVO);
			}
			
		}
		
		rd.put("proposalCompanyVehcileInfo", proposalCompanyVehcileInfo);
		return rd;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addProposal(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, List<MultipartFile> multipartFiles, Vehicle dbVehicle, Integer currentUserId, Integer companyId){
		ResponseData rd = new ResponseData(0);
		ProposalVO proposalVO = proposalCompanyVehcileInfo.getProposal();
		proposalVO.setVehicleId(dbVehicle.getId());
		proposalVO.setCompanyId(companyId);
		
		Proposal proposal = new Proposal();
		BeanUtils.copyProperties(proposalVO, proposal);
		
		Integer proposalStatus = GlobalConstant.PROPOSAL_STATUS_PASSED;//已通过
		
		if (dbVehicle != null) {
			if (dbVehicle.getConfigureStatus() == GlobalConstant.CONFIGURE_STATUS_IMPORTANT_NO) {
				proposalStatus = GlobalConstant.PROPOSAL_STATUS_IMPORTANT_NO;
			}
			if (dbVehicle.getConfigureStatus() == GlobalConstant.CONFIGURE_STATUS_NO_IMPORTANT_NO) {
				proposalStatus = GlobalConstant.PROPOSAL_STATUS_NO_IMPORTANT_NO;
			}
		}
		
		proposal.setStatus(proposalStatus);
		proposal.setDepartmentId(companyId);
		proposal.setProposerId(currentUserId);
		proposal.setProposalTime(DateTime.toDate(DateTime.getNormalDateTime()));
		
		Integer proposalId = proposalDao.save(proposal);
		if (proposalId != null) {
			this.addProposalAttachment(proposalId, companyId, currentUserId, multipartFiles);
			rd.setCode(1);
			rd.setMessage("申请单新增成功");
		} else {
			rd.setCode(-1);
			rd.setMessage("申请单新增失败");
			return rd;
		}
		rd.setFirstItem(proposalCompanyVehcileInfo);
		return rd;
	}
	
	/**
	 * 添加附件
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param companyId
	 * @param currentUserId
	 * @param multipartFiles
	 */
	private void addProposalAttachment(Integer proposalId, Integer companyId, Integer currentUserId, List<MultipartFile> multipartFiles){
		if (multipartFiles != null && multipartFiles.size() > 0) {
			for(MultipartFile multipartFile : multipartFiles){
				uploadFile(currentUserId, multipartFile, proposalId);
			}
		}
	}

	/**
	 *保存附件
	 * @author liuq 
	 * @version 0.1 
	 * @param userId
	 * @param multipartFile
	 * @param proposalId
	 */
	private void uploadFile(Integer userId,MultipartFile multipartFile, Integer proposalId) {
		ProposalAttachment proposalAttachment = new ProposalAttachment();
		String rtnFile[] = UploadHelper.uploadFile(multipartFile, proposalId);
		if (rtnFile != null) {
			proposalAttachment.setCreateId(userId);
			proposalAttachment.setCreateTime(new Date());
			proposalAttachment.setProposalId(proposalId);
			proposalAttachment.setOriginalName(rtnFile[0]);
			proposalAttachment.setDisplayName(rtnFile[1]);
			proposalAttachment.setFullPath(rtnFile[2]);
			proposalAttachment.setType(rtnFile[3]);
			proposalAttachment.setSize(String.valueOf(multipartFile.getSize()));
			proposalAttachment.setStatus(1);
			proposalDao.saveProposalAttachment(proposalAttachment);
		}
	}
	
	@Override
	public ResponseData displayAddReplace(ProposalCompanyVehcileInfo vo, Integer companyId, Integer replaceVehicleId, Integer dessCarStyleId) {
		ResponseData rd = new ResponseData(0);
		if (vo == null) {
			vo = new ProposalCompanyVehcileInfo();
		}
		
		vo.getProposal().setReplaceVehicleId(replaceVehicleId);
		vo.getVehicleInstallationPlan().setDessCarStyleId(dessCarStyleId);
		vo.getVehicleInstallationPlan().setDessCarStyleNick(CacheCarManager.getCarStyleNameById(dessCarStyleId));
		
		if(null != companyId){
			Company dbCompany = CacheCompanyManager.getCompany(companyId);
			if (dbCompany != null) {
				CompanyVO companyVO = new CompanyVO(dbCompany);
				
				HashMap<Object, Object> parameters = new HashMap<Object, Object>();
				parameters.put("cityId", dbCompany.getRegionCityId());
				AddPlateNumberCity addPlateNumberCity = hibernateDao.get(" AddPlateNumberCity ", parameters);
				if (addPlateNumberCity != null) {
					vo.getProposal().setIsAddPlatenumberCity(GlobalConstant.TRUE);
				} else {
					vo.getProposal().setIsAddPlatenumberCity(GlobalConstant.FALSE);
				}
				
				vo.setCompany(companyVO);
			}
		}
		
		rd.put("proposalCompanyVehcileInfo", vo);
		return rd;
	}
	
	@Override
	public ResponseData displayVehicleDemolition(VehicleVO vehicle) {
		ResponseData rd = new ResponseData(1);
		if (vehicle == null) {
			vehicle = new VehicleVO();
		}
		
		rd.put("vehicleVO", vehicle);
		return rd;
	}
	
	@Override
	public ResponseData displayEditProposal(Integer proposalId) {
		ResponseData rd = new ResponseData(0);
		ProposalCompanyVehcileInfo proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
		Proposal dbProposal = proposalDao.get(proposalId);
		ProposalVO proposalVO = new ProposalVO(dbProposal);
		
		proposalCompanyVehcileInfo.setProposal(proposalVO);
		
		Company dbCompany = CacheCompanyManager.getCompany(proposalVO.getCompanyId());
		 if (dbCompany != null) {
			 CompanyVO  companyVO = new CompanyVO (dbCompany);
			 proposalCompanyVehcileInfo.setCompany(companyVO);
		 }
		 
		 Vehicle dbVehicle = vehicleDao.get(proposalVO.getVehicleId());
		 if (dbVehicle != null) {
			 VehicleVO vehicleVO = new VehicleVO(dbVehicle);
			 proposalCompanyVehcileInfo.setVehicle(vehicleVO);
		 }
		 
		 HashMap parameters = new HashMap();
		 parameters.put("proposalId", proposalId);
		 List approvalList = approvalDao.find(parameters, "order by id desc");
		 if (approvalList != null && approvalList.size() > 0) {
			 Approval  dbApproval = (Approval) approvalList.get(0);
			 ApprovalVO approvalVO = new ApprovalVO(dbApproval);
			 proposalCompanyVehcileInfo.setApproval(approvalVO);
		 }
		 
		List<ProposalAttachment> proposalAttachmentList = this.getProposalAttachOriginalList(proposalId);
		rd.put("proposalAttachmentList", proposalAttachmentList);
		rd.put("proposalCompanyVehcileInfo", proposalCompanyVehcileInfo);
		return rd;
	}
	
	/**
	 * 获取申请附件列表（申请附件以Byte为单位）
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @return
	 */
	private List<ProposalAttachment> getProposalAttachOriginalList(Integer proposalId) {
		List<ProposalAttachment> proposalAttachmentList = proposalDao.getProposalAttachmentList(proposalId);
		return proposalAttachmentList;
	}

	@Override
	public ResponseData listProposalOld(TransMsg transMsg,
			ProposalCompanyVehcileInfo proposalCompanyVehcileInfo,
			Integer companyId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		//查询申请列表
		ArrayList<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = new ArrayList<ProposalCompanyVehcileInfo>();
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
        
		//车辆表条件、公司表条件、申请单条件
		String addVehicleCondition = "", addCompnayCondition = "", addProposalCondition = "";
		
		if (companyId != null) {
			addVehicleCondition += " and companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
		}
		
		VehicleVO vehicleVO = proposalCompanyVehcileInfo.getVehicle();
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		if (vehicleVO.getPlateNumber() != null && vehicleVO.getPlateNumber().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "plateNumber", vehicleVO.getPlateNumber());
		}

		if (vehicleVO.getVin() != null && vehicleVO.getVin().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "vin", vehicleVO.getVin());
		}
		
		String vehicles = "";
		//查询车辆列表
		List<Vehicle> vehicleList = vehicleDao.find(parameters, addVehicleCondition);
		if (vehicleList !=null && vehicleList.size() > 0) {
			
			StringBuffer vehicleBuff = new StringBuffer();
			for (Vehicle vehicle : vehicleList) {
				 vehicleBuff.append(vehicle.getId()).append(",");
			}
			if (vehicleBuff.length() > 0) {
				vehicles = vehicleBuff.substring(0, vehicleBuff.length() - 1);
				addProposalCondition += " and vehicleId in(" + vehicles + ")";
			}
		}
		 
		if (vehicles == "" && vehicles.trim().length() == 0) {
			rd.setFirstItem(proposalCompanyVehcileInfoList);
			rd.setCode(1);
			return rd;
		}
		
		//查询经销商列表 
		parameters.clear();
		CompanyVO companyVO = proposalCompanyVehcileInfo.getCompany();
		if (companyVO.getDealerCode() != null && companyVO.getDealerCode().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "dealerCode", companyVO.getDealerCode());
		}
		
		if (companyId != null) {
			addCompnayCondition += " and id in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
		}
		
		String companys = "";
		List<Company> companyList = companyDao.find(parameters, addCompnayCondition);
		if (companyList != null && companyList.size() > 0) {
			StringBuffer companyBuff = new StringBuffer();
			for (Company company : companyList) {
				 companyBuff.append(company.getId()).append(",");
			}
			
			if (companyBuff.length() > 0) {
				companys = companyBuff.substring(0, companyBuff.length() - 1);
				addProposalCondition += " and companyId in(" + companys + ")";
			}
		}
		
		if (companys == "" && companys.trim().length() == 0) {
			rd.setFirstItem(proposalCompanyVehcileInfoList);
			rd.setCode(1);
			return rd;
		}
		
		ProposalVO proposalVO = proposalCompanyVehcileInfo.getProposal();
		proposalCompanyVehcileInfoList = this.getProposalList(transMsg, proposalVO, addProposalCondition);

		rd.setFirstItem(proposalCompanyVehcileInfoList);
		rd.setCode(1);
		return rd;
	}
	
	@Override
	public ResponseData listProposal(TransMsg transMsg, ProposalCompanyVehcileInfo proposalCompanyVehcileInfo,
		Integer seriesId, Integer companyId, Integer userId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" p.ID as proposalId, p.CONTACT_NAME as contactName, p.CONTACT_PHONE as contactPhone, p.PROPOSAL_TIME as proposalTime, ");
		exeSql.append(" p.STATUS as status, (case p.STATUS ");
		exeSql.append(" WHEN ").append( GlobalConstant.PROPOSAL_STATUS_PASSED).append(" THEN '待安装' ");
		exeSql.append(" WHEN ").append(GlobalConstant.PROPOSAL_STATUS_INSTALLED).append(" THEN '已安装' ");
		exeSql.append(" END) as statusNick, adc.CITY_ID as restrictionCityId ");
		exeSql.append(" FROM t_proposal p ");
		exeCountSql.append(" FROM t_proposal p ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi ON vdi.companyId = p.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi ON vdi.companyId = p.COMPANY_ID ");
		
		String vehicleSql = " INNER JOIN t_vehicle v ON v.ID = p.VEHICLE_ID ";
		exeSql.append(vehicleSql);
		exeCountSql.append(vehicleSql);
		
		exeSql.append(getVehicleJoinCarStyleSql());//关联车型
		exeCountSql.append(getVehicleJoinCarStyleSql());
		
		exeSql.append(getRestrictionCitySql());//关联限购城市
		String typeSql = " WHERE p.TYPE = " + GlobalConstant.PROPOSAL_TYPE_INSTALLATION;
		exeSql.append(typeSql);
		exeCountSql.append(typeSql);
		
		String seriesSql = " AND cs.SERIES_ID = " + seriesId;//区分不同车系
		exeSql.append(seriesSql);
		exeCountSql.append(seriesSql);
		
		String statusSql = " AND p.STATUS = " + GlobalConstant.PROPOSAL_STATUS_PASSED;
		exeSql.append(statusSql);
		exeCountSql.append(statusSql);
		//车辆状态为待安装
		String configureStatusSql = " AND v.CONFIGURE_STATUS = " + GlobalConstant.CONFIGURE_STATUS_WATTING;
		exeSql.append(configureStatusSql);
		exeCountSql.append(configureStatusSql);
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(companyId, userId));
		exeCountSql.append(getAccessSql(companyId, userId));
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(proposalCompanyVehcileInfo.getVehicle()));
		exeCountSql.append(getVehicleConditionSql(proposalCompanyVehcileInfo.getVehicle()));
		//添加经销商查询条件
		exeSql.append(getCompanyConditionSql(proposalCompanyVehcileInfo.getCompany(), userId));
		exeCountSql.append(getCompanyConditionSql(proposalCompanyVehcileInfo.getCompany(), userId));
		
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" ,p.ID DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<ProposalFindVO> proposalFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), ProposalFindVO.class);
		rd.setFirstItem(proposalFindVOList);
		rd.setCode(1);
		return rd;
	}
	
	/**
	 * 获得申请列表信息
	 * @param transMsg
	 * @param proposalVO
	 * @param addProposalCondition
	 * @return
	 */
	private ArrayList<ProposalCompanyVehcileInfo> getProposalList(TransMsg transMsg, ProposalVO proposalVO, String addProposalCondition) {
		ArrayList<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = new ArrayList<ProposalCompanyVehcileInfo>();
		if (proposalVO.getType() != null) {
			transMsg.put("type", proposalVO.getType() );
		}
		
		if (proposalVO.getStatus() != null) {
			transMsg.put("status", proposalVO.getStatus());
		} else {
			addProposalCondition += " and status in (" + GlobalConstant.PROPOSAL_STATUS_PROCESSING + "," + 
			GlobalConstant.PROPOSAL_STATUS_PASSED + "," + GlobalConstant.PROPOSAL_STATUS_UNPASSED + "," +
			GlobalConstant.PROPOSAL_STATUS_IMPORTANT_NO + "," + GlobalConstant.PROPOSAL_STATUS_NO_IMPORTANT_NO + "," +
			GlobalConstant.PROPOSAL_STATUS_INSTALLED + ")";
		}
		
		if (proposalVO.getCompanyId() != null && proposalVO.getCompanyId().intValue() > 0) {
			Integer proposalCompanyId = proposalVO.getCompanyId();
			addProposalCondition += " and companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(proposalCompanyId, GlobalConstant.TRUE) + ")";
		}
		
		addProposalCondition += " order by id desc ";
		List<Proposal> proposalList = proposalDao.findByPage(transMsg, addProposalCondition);
		if (proposalList != null) {
			for (Proposal dbProposal : proposalList) {
				ProposalCompanyVehcileInfo proposalCompanyVehcile = new ProposalCompanyVehcileInfo();
				ProposalVO currentproposalVO = new ProposalVO(dbProposal);
				proposalCompanyVehcile.setProposal(currentproposalVO);
				 
				 Company dbCompany = CacheCompanyManager.getCompany(currentproposalVO.getCompanyId());
				 if (dbCompany != null) {
					 CompanyVO  companyVO = new CompanyVO (dbCompany);
					 proposalCompanyVehcile.setCompany(companyVO);
				 }
				 
				 Vehicle dbVehicle = vehicleDao.get(currentproposalVO.getVehicleId());
				 if (dbVehicle != null) {
					 VehicleVO vehicleVO = new VehicleVO(dbVehicle);
					 proposalCompanyVehcile.setVehicle(vehicleVO);
				 }
				 proposalCompanyVehcileInfoList.add(proposalCompanyVehcile);
			}
		}
		return proposalCompanyVehcileInfoList;
	}
	
	@Override
	public ResponseData displayProposalApproval(Integer proposalId,
			Integer companyId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		ProposalCompanyVehcileInfo proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
		Proposal dbProposal = proposalDao.get(proposalId);
		ProposalVO proposalVO = new ProposalVO(dbProposal);
		
		proposalCompanyVehcileInfo.setProposal(proposalVO);
		
		Company dbCompany = CacheCompanyManager.getCompany(proposalVO.getCompanyId());
		if (dbCompany != null) {
			CompanyVO  companyVO = new CompanyVO (dbCompany);
			proposalCompanyVehcileInfo.setCompany(companyVO);
		}
		
		Vehicle dbVehicle = vehicleDao.get(proposalVO.getVehicleId());
		if (dbVehicle != null) {
			VehicleVO vehicleVO = new VehicleVO(dbVehicle);
			proposalCompanyVehcileInfo.setVehicle(vehicleVO);
		}
		
		 ApprovalVO approval =new ApprovalVO();
		 approval.setUserNick(SessionManager.getCurrentUserNickName(request));
		 proposalCompanyVehcileInfo.setApproval(approval);
		
		rd.put("proposalCompanyVehcileInfo", proposalCompanyVehcileInfo);
		return rd;
	}

	@Override
	public ResponseData listDemolitionOld(TransMsg transMsg,
			ProposalCompanyVehcileInfo proposalCompanyVehcileInfo,
			Integer companyId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		ArrayList<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = new ArrayList<ProposalCompanyVehcileInfo>();
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
        
		//车辆表条件、公司表条件、申请单条件
		String addVehicleCondition = "", addCompnayCondition = "", addProposalCondition = "";
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		
		//查询经销商列表 
		parameters.clear();
		CompanyVO companyVO = proposalCompanyVehcileInfo.getCompany();
		if (companyVO.getDealerCode() != null && companyVO.getDealerCode().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters, "dealerCode", companyVO.getDealerCode());
		}
		
		if (companyId != null) {
			addCompnayCondition += " and id in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
		}
		
		String companys = "";
		List<Company> companyList = companyDao.find(parameters, addCompnayCondition);
		if (companyList != null && companyList.size() > 0) {
			StringBuffer companyBuff = new StringBuffer();
			for (Company company : companyList) {
				companyBuff.append(company.getId()).append(",");
			}
			
			if (companyBuff.length() > 0) {
				companys = companyBuff.substring(0, companyBuff.length() - 1);
				addVehicleCondition += " and companyId in(" + companys + ")";
			}
		}
		if (companys == "" && companys.trim().length() == 0) {
			rd.setFirstItem(proposalCompanyVehcileInfoList);
			rd.setCode(1);
			return rd;
		}
		
		VehicleVO vehicleVO = proposalCompanyVehcileInfo.getVehicle();
		if (vehicleVO.getConfigureStatus() != null) {
			transMsg.put("configureStatus", vehicleVO.getConfigureStatus());
		}
		
		Integer proposalType = proposalCompanyVehcileInfo.getProposal().getType();
		//拆除申请列表的车辆配置状态为已安装、拆装待审核
		if (proposalType == GlobalConstant.PROPOSAL_TYPE_DEMOLITION) {
			addVehicleCondition += " and configureStatus in(" + GlobalConstant.CONFIGURE_STATUS_INSTALLED + "," 
			+ GlobalConstant.CONFIGURE_STATUS_DISASSEMBLY_PENDING + "," + GlobalConstant.CONFIGURE_STATUS_DEMOLISHING + ")";
		}
		
		if (vehicleVO.getCompanyId() != null && vehicleVO.getCompanyId().intValue() > 0) {
			Integer vehicleCompanyId = vehicleVO.getCompanyId();
			addVehicleCondition += " and companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(vehicleCompanyId, GlobalConstant.TRUE) + ")";
		}
		
		if (vehicleVO.getPlateNumber() != null && vehicleVO.getPlateNumber().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "plateNumber", vehicleVO.getPlateNumber());
		}

		if (vehicleVO.getVin() != null && vehicleVO.getVin().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "vin", vehicleVO.getVin());
		}
		
		//查询车辆列表
		List<Vehicle> vehicleList = vehicleDao.findByPage(transMsg, addVehicleCondition);
		proposalCompanyVehcileInfoList = this.getDemolitionList(vehicleList, addProposalCondition, proposalType);
		
		rd.setFirstItem(proposalCompanyVehcileInfoList);
		rd.setCode(1);
		return rd;
	}
	
	@Override
	public ResponseData listDemolition(TransMsg transMsg,
			ProposalCompanyVehcileInfo vo,
			Integer companyId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
	
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" SELECT o.PARENT_ID as parentId, c.REGION_PROVINCE_ID as regionProvinceId, c.REGION_CITY_ID as regionCityId, ");
		exeSql.append(" c.CN_NAME as cnName, c.DEALER_CODE as dealerCode, c.IS_KEY_CITY as isKeyCity, c.DEALER_TYPE as dealerType, ");
		exeSql.append(" v.ID as vehicleId, v.PLATE_NUMBER as plateNumber, v.CAR_STYLE_ID as carStyleId, v.VIN as vin, v.CONFIGURE_STATUS as configureStatus, ");
		exeSql.append(" b1.SIM_MOBILE as simMobile, b1.UNIQUE_ID as uniqueId, ");
		exeSql.append(" p.ID as proposalId, p.STATUS as status ");
		exeSql.append(" FROM t_vehicle v ");
		exeSql.append(" INNER JOIN t_org o on o.ID = v.COMPANY_ID ");
		exeSql.append(" INNER JOIN t_company c on  c.ORG_ID = v.COMPANY_ID ");
		exeSql.append(" LEFT JOIN t_box b1 on b1.VEHICLE_ID = v.ID ");
		//exeSql.append(" LEFT JOIN t_box_operation b2 on b2.VEHICLE_ID = v.ID AND b2.BOX_ID = b1.ID AND b2.TYPE = " + GlobalConstant.PROPOSAL_TYPE_INSTALLATION);
		exeSql.append(" LEFT JOIN t_proposal p on p.VEHICLE_ID = v.ID AND p.TYPE = " + GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
		
		VehicleVO vehicleVO = vo.getVehicle();
		
		if (companyId != null) {
			if (vehicleVO.getCompanyId() != null) {
				companyId = vehicleVO.getCompanyId();
			}
			exeSql.append(" WHERE v.COMPANY_ID IN (" + CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ") ");
		}
		
		if (vehicleVO.getConfigureStatus() != null && vehicleVO.getConfigureStatus().intValue() >= 0) {
			exeSql.append(" AND v.CONFIGURE_STATUS = " + vehicleVO.getConfigureStatus());
		} else {
			//拆除申请列表的车辆配置状态为已安装、拆装待审核
			exeSql.append(" AND v.CONFIGURE_STATUS in (" + GlobalConstant.CONFIGURE_STATUS_INSTALLED + "," + 
			GlobalConstant.CONFIGURE_STATUS_DISASSEMBLY_PENDING + "," + GlobalConstant.CONFIGURE_STATUS_DEMOLISHING + ")" );
		}
		
		if (vehicleVO.getVin() != null && vehicleVO.getVin().trim().length() > 0) {
			exeSql.append(" AND v.VIN LIKE '%" + vehicleVO.getVin() + "%' ");
		}

		if (vehicleVO.getPlateNumber() != null && vehicleVO.getPlateNumber().trim().length() > 0) {
			exeSql.append(" AND v.PLATE_NUMBER LIKE '%" + vehicleVO.getPlateNumber() + "%' ");
		}
		
		CompanyVO companyVO = vo.getCompany();
		if (companyVO.getDealerCode() != null && companyVO.getDealerCode().trim().length() > 0) {
			exeSql.append(" AND c.DEALER_CODE LIKE '%" + companyVO.getDealerCode() + "%' ");
		}
		
		exeSql.append(" ORDER BY o.PARENT_ID, c.DEALER_CODE, b2.ID DESC, v.ID DESC, p.PROPOSAL_TIME DESC ");
		
		List<DemolitionFindVO> demolitionFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), DemolitionFindVO.class);
		//总条数
		Integer total = 0;
		if (demolitionFindVOList != null && demolitionFindVOList.size() > 0) {
			total = demolitionFindVOList.size();
		}
		transMsg.setTotalRecords(TypeConverter.toLong(total));
		
		//分页
		if (transMsg.getStartIndex() == null) {
			transMsg.setStartIndex(0);
		}
		exeSql.append(" limit "+ transMsg.getStartIndex()
			          + "," + (transMsg.getPageSize()) + " ");
		
		demolitionFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), DemolitionFindVO.class);
		ArrayList<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = new ArrayList<ProposalCompanyVehcileInfo>();
		if (demolitionFindVOList != null) {
			for (DemolitionFindVO demolitionFindVO : demolitionFindVOList) {
				 ProposalCompanyVehcileInfo proposalCompanyVehcile = new ProposalCompanyVehcileInfo();
				 ProposalVO proposal = new ProposalVO(demolitionFindVO);
				 //申请ID
				 proposal.setId(demolitionFindVO.getProposalId());
				 proposalCompanyVehcile.setProposal(proposal);
				 
				 CompanyVO company = new CompanyVO(demolitionFindVO);
				 proposalCompanyVehcile.setCompany(company);
				 
				 VehicleVO vehicle = new VehicleVO(demolitionFindVO);
				 vehicle.setId(demolitionFindVO.getVehicleId());
				 proposalCompanyVehcile.setVehicle(vehicle);
				 
				 BoxOperationVO boxOperation = new BoxOperationVO(demolitionFindVO);
				 BoxVO box = new BoxVO(demolitionFindVO);
				 boxOperation.setBox(box);
				 
				 if (boxOperation.getOperationTime() != null) {
					 //试驾车服役期为一年，在“安装时间”后自动顺延一年就是“退出时间”
					 Date exitTime = DateTime.getOffsetMonthDate(boxOperation.getOperationTime(), -12);
					 boxOperation.setExitTime(exitTime);
				 }
				 proposalCompanyVehcile.setBoxOperation(boxOperation);
				 
				 
				 proposalCompanyVehcileInfoList.add(proposalCompanyVehcile);
			}
		}
		
		rd.setFirstItem(proposalCompanyVehcileInfoList);
		rd.setCode(1);
		return rd; 
	}

	/**
	 * 获得拆除列表信息
	 * @param vehicleList
	 * @param addProposalCondition
	 * @param proposalType
	 * @return
	 */
	private ArrayList<ProposalCompanyVehcileInfo> getDemolitionList(List<Vehicle> vehicleList,String addProposalCondition, Integer proposalType) {
		ArrayList<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = new ArrayList<ProposalCompanyVehcileInfo>();
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		if (vehicleList != null && vehicleList.size() > 0) {
			for (Vehicle dbVehicle : vehicleList) {
				 ProposalCompanyVehcileInfo proposalCompanyVehcile = new ProposalCompanyVehcileInfo();
				 VehicleVO vehicleVO = new VehicleVO(dbVehicle);
				 proposalCompanyVehcile.setVehicle(vehicleVO);
				 Integer vehicleId = dbVehicle.getId();
				 
				 Company dbCompany = CacheCompanyManager.getCompany(vehicleVO.getCompanyId());
				 if (dbCompany != null) {
					 CompanyVO  companyVO = new CompanyVO (dbCompany);
					 proposalCompanyVehcile.setCompany(companyVO);
				 }
				 
				 parameters.clear();
				 parameters.put("vehicleId", vehicleId);
				 parameters.put("type", proposalType);
				 
				//申请表条件
				 addProposalCondition = " order by proposalTime desc ";
				 List proposalList = proposalDao.find(parameters, addProposalCondition);
				 if (proposalList != null && proposalList.size() > 0) {
					 Proposal proposal = (Proposal) proposalList.get(0);
					 ProposalVO proposalVO = new ProposalVO(proposal);
					 proposalCompanyVehcile.setProposal(proposalVO);
				 }
				 
			 	 parameters.clear();
				 parameters.put("vehicleId", vehicleId);
				 parameters.put("type", GlobalConstant.PROPOSAL_TYPE_INSTALLATION);
				 List boxOperationList = boxOperationDao.find(parameters, " order by id desc");
				 if (boxOperationList != null && boxOperationList.size() > 0) {
						BoxOperation boxOperation = (BoxOperation) boxOperationList.get(0);
						BoxOperationVO boxOperationVO = new BoxOperationVO(boxOperation);
						Box box = CacheBoxManager.getBoxById(boxOperationVO.getBoxId());
						if (box != null) {
							BoxVO boxVO = new BoxVO(box);
							boxOperationVO.setBox(boxVO);
						}
						//试驾车服役期为一年，在“安装时间”后自动顺延一年就是“退出时间”
						Date exitTime = DateTime.getOffsetMonthDate(boxOperation.getOperationTime(), -12);
						boxOperationVO.setExitTime(exitTime);
						proposalCompanyVehcile.setBoxOperation(boxOperationVO);
		         }
				 
				 proposalCompanyVehcileInfoList.add(proposalCompanyVehcile);
			}
		}
		return proposalCompanyVehcileInfoList;
	}
	
	@Override
	public ResponseData listReplace(TransMsg transMsg,
			ProposalCompanyVehcileInfo vo, Integer companyId, Integer userId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql = new StringBuffer("SELECT t.* FROM ( ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT count(1) from ( ");
		
		//新装以及换装已安装可以换装的车
		Map<String, StringBuffer> newDressSqlMap = getReplaceSql(vo, companyId, userId, GlobalConstant.FALSE);
		exeSql.append(newDressSqlMap.get("exeSql"));
		exeCountSql.append(newDressSqlMap.get("exeCountSql"));
		exeSql.append(" UNION ALL");
		exeCountSql.append(" UNION ALL");
		//首批待换装的车
		Map<String, StringBuffer> waitDressSqlMap = getReplaceSql(vo, companyId, userId, GlobalConstant.TRUE);
		exeSql.append(waitDressSqlMap.get("exeSql"));
		exeCountSql.append(waitDressSqlMap.get("exeCountSql"));
		
		exeSql.append(" ) t ");
		exeCountSql.append(" ) t");
		
		//排序
		exeSql.append(" ORDER BY t.saleCenterId, t.dealerCode, t.vehicleId desc ");
		
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<ReplaceFindVO> replaceFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), ReplaceFindVO.class);
		rd.setFirstItem(replaceFindVOList);
		rd.setCode(1);
		return rd; 
	}

	
	/**
	 * 拼接换装SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param companyId
	 * @param userId
	 * @param flag 0:新装以及换装已安装可以换装的车; 1:首批待换装的车SQ
	 * @return
	 */
	private Map<String, StringBuffer> getReplaceSql(ProposalCompanyVehcileInfo vo, Integer companyId, Integer userId, Integer flag){
		Map<String, StringBuffer> sqlMap =new HashMap<String, StringBuffer>();
		StringBuffer exeSql = new StringBuffer(" ( SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" ( SELECT 1 ");
		exeSql.append(getDealerSql());
		exeSql.append(getVehicleSql());
		
		exeSql.append(" p.ID as proposalId, p.STATUS as status, ");
		exeSql.append(" adc.CITY_ID as restrictionCityId, ");
		exeSql.append(" vi.DESS_CAR_STYLE_ID as dessCarStyleId, concat(cs1.NAME, ' ', cs1.YEAR_TYPE) as dessCarStyleName ");//待换装车型
		exeSql.append(" FROM t_vehicle v ");
		exeCountSql.append(" FROM t_vehicle v ");
		exeSql.append(getVehicleJoinCarStyleSql());//关联车型
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = v.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = v.COMPANY_ID ");
		
		String proposalSql = "";
		if (flag == GlobalConstant.FALSE) {
			proposalSql = " INNER JOIN t_proposal p on p.VEHICLE_ID = v.ID AND p.TYPE in (" + GlobalConstant.PROPOSAL_TYPE_INSTALLATION + "," + GlobalConstant.PROPOSAL_TYPE_REPLACE + ")";
		} else {
			proposalSql = " INNER JOIN t_proposal p on p.VEHICLE_ID = v.ID AND p.TYPE = " + GlobalConstant.PROPOSAL_TYPE_REPLACE;
		}
		exeSql.append(proposalSql);
		exeCountSql.append(proposalSql);

		String configureStatusSql = "";
		if (flag == GlobalConstant.FALSE) {
			configureStatusSql = " AND v.CONFIGURE_STATUS = " + GlobalConstant.CONFIGURE_STATUS_INSTALLED;
		} else {
			configureStatusSql = " AND v.CONFIGURE_STATUS = " + GlobalConstant.CONFIGURE_STATUS_WATTING;
		}
		exeSql.append(configureStatusSql);
		exeCountSql.append(configureStatusSql);
		
		//关联安装计划表
		//exeSql.append(" LEFT JOIN t_vehicle_installation_plan vi on vi.TEST_DRIVE_CAR_STYLE_ID = v.CAR_STYLE_ID AND vi.TYPE = " + GlobalConstant.TRUE);
		exeSql.append(" LEFT JOIN t_vehicle_installation_plan vi on vi.id = v.REPLACE_PLAN_ID AND vi.TEST_DRIVE_CAR_STYLE_ID = v.CAR_STYLE_ID AND vi.FLAG = 0 AND vi.TYPE = " + GlobalConstant.TRUE);
		exeSql.append(" LEFT JOIN t_car_style cs1 on cs1.ID = vi.DESS_CAR_STYLE_ID ");//关联换装车型
		exeSql.append(getRestrictionCitySql());//关联限购城市
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		//添加条件
		exeSql.append(getAccessSql(companyId, userId));
		exeCountSql.append(getAccessSql(companyId, userId));
		exeSql.append(getVehicleConditionSql(vo.getVehicle()));
		exeCountSql.append(getVehicleConditionSql(vo.getVehicle()));
		exeSql.append(getCompanyConditionSql(vo.getCompany(), userId));
		exeCountSql.append(getCompanyConditionSql(vo.getCompany(), userId));
		
		exeSql.append(" ) ");
		exeCountSql.append(" ) ");
		
		sqlMap.put("exeSql", exeSql);
		sqlMap.put("exeCountSql", exeCountSql);
		return sqlMap;
	}
	
	@Override
	public ResponseData displayInstallationComplete(ProposalCompanyVehcileInfo proposalCompanyVehcileInfo, Integer proposalId,
			Integer companyId, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		if (proposalCompanyVehcileInfo == null) {
			proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
		}
		Proposal dbProposal = proposalDao.get(proposalId);
		ProposalVO proposalVO = new ProposalVO(dbProposal);
		
		proposalCompanyVehcileInfo.setProposal(proposalVO);
		
		Company dbCompany = CacheCompanyManager.getCompany(proposalVO.getCompanyId());
		if (dbCompany != null) {
			CompanyVO  companyVO = new CompanyVO (dbCompany);
			proposalCompanyVehcileInfo.setCompany(companyVO);
		}
		
		Vehicle dbVehicle = vehicleDao.get(proposalVO.getVehicleId());
		if (dbVehicle != null) {
			VehicleVO vehicleVO = new VehicleVO(dbVehicle);
			proposalCompanyVehcileInfo.setVehicle(vehicleVO);
		}
		
		 ApprovalVO approval =new ApprovalVO();
		 approval.setUserNick(SessionManager.getCurrentUserNickName(request));
		 proposalCompanyVehcileInfo.setApproval(approval);
		
		rd.put("proposalCompanyVehcileInfo", proposalCompanyVehcileInfo);
		return rd;
	}

	@Override
	public ResponseData viewDemolitionProposal(Integer proposalId,
			Integer companyId, Integer vehicleId) {
		ResponseData rd = new ResponseData(0);
		ProposalCompanyVehcileInfo proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
		
		 Vehicle dbVehicle = vehicleDao.get(vehicleId);
		 if (dbVehicle != null) {
			 VehicleVO vehicleVO = new VehicleVO(dbVehicle);
			 proposalCompanyVehcileInfo.setVehicle(vehicleVO);
		 }
		 
		 Company dbCompany = CacheCompanyManager.getCompany(dbVehicle.getCompanyId());
		 if (dbCompany != null) {
			 CompanyVO  companyVO = new CompanyVO (dbCompany);
			 proposalCompanyVehcileInfo.setCompany(companyVO);
		 }
		 
		 if (proposalId != null) {
			 Proposal dbProposal = proposalDao.get(proposalId);
			 ProposalVO proposalVO = new ProposalVO(dbProposal);
			
			 proposalCompanyVehcileInfo.setProposal(proposalVO);
			 HashMap parameters = new HashMap();
			 parameters.put("proposalId", proposalId);
			 List approvalList = approvalDao.find(parameters, "order by id desc");
			 if (approvalList != null && approvalList.size() > 0) {
				 Approval  dbApproval = (Approval) approvalList.get(0);
				 ApprovalVO approvalVO = new ApprovalVO(dbApproval);
				 proposalCompanyVehcileInfo.setApproval(approvalVO);
			 }
		} 
		
		List<ProposalAttachment> proposalAttachmentList = this.getProposalAttachOriginalList(proposalId);
		rd.put("proposalAttachmentList", proposalAttachmentList);
		rd.put("proposalCompanyVehcileInfo", proposalCompanyVehcileInfo);
		return rd;
	}

	@Override
	public ProposalAttachment getProposalAttachment(Integer proposalId, Integer fileId) {
		HashMap<Object, Object> parameters=new HashMap<Object, Object>();
		parameters.put("id", fileId);
		parameters.put("proposalId", proposalId);
		ProposalAttachment proposalAttachment = proposalDao.getProposalAttachment(parameters);
		return proposalAttachment;
	}

}
