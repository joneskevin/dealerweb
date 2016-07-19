package com.ava.dealer.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ICompanyCarStyleRelationDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IDeclareManagerDao;
import com.ava.dao.ITestDriveDao;
import com.ava.dao.IVehicleDao;
import com.ava.dao.IViolationDao;
import com.ava.dealer.service.IDeclareManagerService;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.CompanyCarStyleRelation;
import com.ava.domain.entity.FilingApproval;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.BoxVO;
import com.ava.domain.vo.CompanyVO;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.FilingApprovalVO;
import com.ava.domain.vo.FilingProposalAttachmentVO;
import com.ava.domain.vo.FilingProposalCompanyVehcileInfo;
import com.ava.domain.vo.FilingProposalVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.SendEMailUtil;
import com.ava.util.TypeConverter;
import com.ava.util.upload.UploadHelper;

@Service("dealer.declareManagerService")
public class DeclareManagerService implements IDeclareManagerService {
	@Resource(name="dealer.declareManagerDao")
	private IDeclareManagerDao declareManagerDao;
		
	@Autowired
	private IVehicleDao  vehicleDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private ITestDriveDao testDriveDao;
	
	@Autowired
	private IViolationDao violationDao;
	
	@Autowired
	ICompanyCarStyleRelationDao companyCarStyleRelationDao;
	
	@Override
	public FilingProposalVO getFilingProposal(Integer id) {
		FilingProposalVO filingProposalVO=new FilingProposalVO();
		FilingProposal filingProposal=declareManagerDao.getFilingProposal(id);
		if(null!=filingProposal)
			BeanUtils.copyProperties(filingProposal, filingProposalVO);
		return filingProposalVO;
	}
	
	@Override
	public FilingProposalVO getFilingProposalByVehicle(Integer vehicleId,Integer declareType) {
		FilingProposalVO filingProposalVO=new FilingProposalVO();
		
		Map map=new HashMap();
		map.put("vehicleId", vehicleId);
		map.put("status", 2);
		map.put("type", declareType);
		List<FilingProposal> filingProposalList=declareManagerDao.find(map,"");
		if(null!=filingProposalList && !filingProposalList.isEmpty()){
			BeanUtils.copyProperties(filingProposalList.get(0), filingProposalVO);
		}else{
			return null;
		}
		return filingProposalVO;
	}
	
	@Override
	public FilingProposalAttachmentVO getFilingProposalAttachment(Integer id) {
		FilingProposalAttachmentVO filingProposalAttachmentVO=new FilingProposalAttachmentVO();
		FilingProposalAttachment filingProposalAttachment=declareManagerDao.getFilingProposalAttachment(id);
		if(null!=filingProposalAttachment)
			BeanUtils.copyProperties(filingProposalAttachment, filingProposalAttachmentVO);
		return filingProposalAttachmentVO;
	}
	
	@Override
	public List<FilingProposalVO> getFilingProposalList(TransMsg transMsg,Integer vehicleId,Integer currentCompanyId,Integer declareType,String startTime,String endTime) {
		StringBuffer additionalCondition=new StringBuffer("");
		if(null!=declareType){
			if(1==declareType || 2==declareType){
				additionalCondition.append(" and type=").append(declareType);
				additionalCondition.append(" and vehicleId=").append(vehicleId);
				additionalCondition.append(" and status in (3,4) and companyId in(");
				additionalCondition.append(CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)).append(")");
				if(null!=startTime && !"".equals(startTime)){
					additionalCondition.append(" and startTime>=str_to_date('").append(startTime).append("','%Y-%m-%d %H:%i:%s')");
				}
				if(null!=endTime && !"".equals(endTime)){
					additionalCondition.append(" and endTime<=str_to_date('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
				}
				if(null==transMsg){
					transMsg=new TransMsg();
					transMsg.setPageSize(20);
				}
			}else if(3==declareType){
				additionalCondition.append(" and status in (2,3,4) and companyId in(");
				additionalCondition.append(CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)).append(")");
				if(null!=startTime && !"".equals(startTime)){
					additionalCondition.append(" and startTime>=str_to_date('").append(startTime).append("','%Y-%m-%d %H:%i:%s')");
				}
				if(null!=endTime && !"".equals(endTime)){
					additionalCondition.append(" and endTime<=str_to_date('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
				}
				additionalCondition.append(" order by id desc");
				if(null==transMsg){
					transMsg=new TransMsg();
					transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
				}
				/**additionalCondition = " and status=2 and companyId in("
						+ CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId) + ") order by id desc";
						*/
			}else if(4==declareType){
				if(null==transMsg){
					transMsg=new TransMsg();
					transMsg.setPageSize(30);
				}
				additionalCondition.append(" and vehicleId=").append(vehicleId);
				additionalCondition.append(" and status in (3,4) and companyId in(");
				additionalCondition.append(CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)).append(")");
				if(null!=startTime && !"".equals(startTime)){
					additionalCondition.append(" and startTime>=str_to_date('").append(startTime).append("','%Y-%m-%d %H:%i:%s')");
				}
				additionalCondition.append(" order by id desc");
			}
		}		
		return copyListProperties(declareManagerDao.findByPage(transMsg, additionalCondition.toString()),FilingProposalVO.class);
	}
	
	@Override
	public List<FilingProposalVO> saveFilingApproval(FilingApprovalVO filingApprovalReq,TransMsg transMsg,Integer userId,Integer companyId) {
		
		Date date=new Date();
		if(null==filingApprovalReq)
			throw new ProtocolParseBusinessException("msgResultContent","请求参数有误");
		
		FilingProposalVO filingProposalVO=getFilingProposal(filingApprovalReq.getFilingProposalId());
		if(null==filingProposalVO)
			throw new ProtocolParseBusinessException("msgResultContent","报备不存在");
		
		if(null==filingApprovalReq.getAdvice()){
			filingProposalVO.setStatus(GlobalConstant.FILING_PROPOSAL_STATUS_YES);
		}else{
			filingProposalVO.setStatus(Integer.parseInt(filingApprovalReq.getAdvice()));
		}
//		filingProposalVO.setApprovalId(userId);
//		filingProposalVO.setApprovalMessage(filingApprovalReq.getAdviceDescription());
//		filingProposalVO.setApprovalTime(date);
//		FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
//		filingProposalVO.setFilingProposalAttachmentVOList(null);
//		filingProposalCompanyVehcileInfo.setFilingProposal(filingProposalVO);
//		edit(filingProposalCompanyVehcileInfo);
		
		//曹村审批意见
		filingApprovalReq.setUserId(userId);
		FilingApproval filingApproval=new FilingApproval();
		BeanUtils.copyProperties(filingApprovalReq, filingApproval);
		filingApproval.setApprovalTime(date);
		declareManagerDao.save(filingApproval);
		
		//增加审核后修改时间段内的试驾信息为无效
		if(GlobalConstant.FILING_PROPOSAL_STATUS_YES == filingProposalVO.getStatus()){
			handleTestDrive(filingApprovalReq.getFilingProposalId(), filingProposalVO.getStartTime(), filingProposalVO.getEndTime());
		}

		List<FilingProposalVO> filingProposalList=getFilingProposalList(transMsg,-100,companyId,3,null,null);
		return filingProposalList;
	}
	
	/**
	 * 修改报备期间的试驾记录为无效，修改违规记录为无效
	 *
	 * @author wangchao
	 * @version 
	 * @param filingProposalId
	 * @param startTime
	 * @param endTime
	 */
	private void handleTestDrive(Integer filingProposalId, Date startTime,
			Date endTime) {
		//1.更新试驾表记录为无效状态
		testDriveDao.bulkUpdateTestDrive4DeclareManage(filingProposalId, startTime, endTime);
		//2.更新违规表记录为无效状态
		violationDao.bulkUpdateViolation4DeclareManage(filingProposalId, startTime, endTime);
		
	}

	@Override
	public ResponseData delFilingProposalAttachment(Integer filingProposalId,Integer filingProposalAttachmentId) {
		
		FilingProposalAttachmentVO filingProposalAttachmentVO=getFilingProposalAttachment(filingProposalAttachmentId);
		if(null!=filingProposalAttachmentVO){
			UploadHelper.delFile(filingProposalAttachmentVO.getFullPath());
			FilingProposalAttachment filingProposalAttachment=declareManagerDao.getFilingProposalAttachment(filingProposalAttachmentId);
			if(null!=filingProposalAttachment){
				declareManagerDao.delete(filingProposalAttachment);
			}
		}else{
			throw new ProtocolParseBusinessException("msgResultContent","删除文件失败");
		}
		ResponseData rd=getFilingProposalAttachList(filingProposalId);
		return rd;
	}
	
	@Override
	public void saveFilingProposalFile(FilingProposalAttachmentVO filingProposalAttachmentVO) {
		if(null!=filingProposalAttachmentVO){
			FilingProposalAttachment filingProposalAttachment=new FilingProposalAttachment();
			BeanUtils.copyProperties(filingProposalAttachmentVO, filingProposalAttachment);
			declareManagerDao.saveFilingProposalAttachment(filingProposalAttachment);
		}
	}
	
	@Override
	public ResponseData getFilingProposalAttachList(Integer filingProposalId) {
		ResponseData rd = new ResponseData(0);
		FilingProposal filingProposal=declareManagerDao.getFilingProposal(filingProposalId);
		if(null!=filingProposal){
			List<FilingProposalAttachment> filingProposalAttachmentList=declareManagerDao.getFilingProposalAttachment(filingProposal);
			List<FilingProposalAttachmentVO> fpaVOList=copyListProperties(filingProposalAttachmentList,FilingProposalAttachmentVO.class);
			rd.put("filingProposalAttachmentList", fpaVOList);
//			if(null!=filingProposal.getVehicleId()){
//				Vehicle vehicle=vehicleDao.get(filingProposal.getVehicleId());
//				VehicleVO vehicleVO=new VehicleVO();
//				BeanUtils.copyProperties(vehicle,vehicleVO);
//				rd.put("vehicle", vehicleVO);
//			}
		}
		return rd;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void save(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo) {
		if(!(null==filingProposalCompanyVehcileInfo || null==filingProposalCompanyVehcileInfo.getFilingProposal())){
			FilingProposalVO filingProposalVO=filingProposalCompanyVehcileInfo.getFilingProposal();
			VehicleVO vehicle=filingProposalCompanyVehcileInfo.getVehicle();
			Integer companyId=CacheVehicleManager.getVehicleById(vehicle.getId()).getCompanyId();
			
			if(null!=vehicle && vehicle.getId()>=1 ){
				//filingProposalVO.setCompanyId(companyId);
				//filingProposalVO.setVehicleId(vehicle.getId());
				
				FilingProposal filingProposal=new FilingProposal();
				
				List<FilingProposalAttachmentVO> filingProposalAttachmentVOList=null;//filingProposalVO.getFilingProposalAttachmentVOList();
				List<FilingProposalAttachment> filingProposalAttachmentList=new ArrayList<FilingProposalAttachment>();
				
				if(null!=filingProposalAttachmentVOList && !filingProposalAttachmentVOList.isEmpty()){
					for(FilingProposalAttachmentVO filingProposalAttachmentVO:filingProposalAttachmentVOList){
						FilingProposalAttachment filingProposalAttachment=new FilingProposalAttachment();
						BeanUtils.copyProperties(filingProposalAttachmentVO, filingProposalAttachment);
						filingProposalAttachmentList.add(filingProposalAttachment);
					}
				}
				BeanUtils.copyProperties(filingProposalVO, filingProposal);
				declareManagerDao.save(filingProposal, filingProposalAttachmentList);
			}
		}
	}

	@Override

	public void delete(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo) {
		if(!(null==filingProposalCompanyVehcileInfo || null==filingProposalCompanyVehcileInfo.getFilingProposal())){
			FilingProposalVO filingProposalVO=filingProposalCompanyVehcileInfo.getFilingProposal();
			FilingProposal filingProposal=new FilingProposal();
			//filingProposal.setId(filingProposalVO.getId());
			declareManagerDao.delete(filingProposal);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void edit(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo) {
		if(!(null==filingProposalCompanyVehcileInfo || null==filingProposalCompanyVehcileInfo.getFilingProposal())){
			
			FilingProposalVO filingProposalVO=filingProposalCompanyVehcileInfo.getFilingProposal();

			FilingProposal filingProposal=new FilingProposal();
			
			List<FilingProposalAttachmentVO> filingProposalAttachmentVOList=null;//filingProposalVO.getFilingProposalAttachmentVOList();
			List<FilingProposalAttachment> filingProposalAttachmentList=new ArrayList<FilingProposalAttachment>();
			
			if(null!=filingProposalAttachmentVOList && !filingProposalAttachmentVOList.isEmpty()){
				for(FilingProposalAttachmentVO filingProposalAttachmentVO:filingProposalAttachmentVOList){
					FilingProposalAttachment filingProposalAttachment=new FilingProposalAttachment();
					BeanUtils.copyProperties(filingProposalAttachmentVO, filingProposalAttachment);
					filingProposalAttachmentList.add(filingProposalAttachment);
				}
			}
			BeanUtils.copyProperties(filingProposalVO, filingProposal);
			declareManagerDao.merge(filingProposal, filingProposalAttachmentList);
		}
	}

	@Override
	public List<FilingProposalAttachmentVO> getFilingProposalAttachment(FilingProposalVO filingProposalVO) {
		
		List<FilingProposalAttachmentVO> filingProposalAttachmentVOList=new ArrayList<FilingProposalAttachmentVO>();
//		if(null!=filingProposalVO && filingProposalVO.getId()>=1){
//			FilingProposal filingProposal=new FilingProposal();
//			filingProposal.setId(filingProposalVO.getId());
//			
//			List<FilingProposalAttachment> filingProposalAttachmentList=declareManagerDao.getFilingProposalAttachment(filingProposal);
//			
//			if(null!=filingProposalAttachmentList && !filingProposalAttachmentList.isEmpty()){
//				
//				for(FilingProposalAttachment filingProposalAttachment:filingProposalAttachmentList){
//					FilingProposalAttachmentVO filingProposalAttachmentVO=new FilingProposalAttachmentVO();
//					BeanUtils.copyProperties(filingProposalAttachment, filingProposalAttachmentVO);
//					filingProposalAttachmentVOList.add(filingProposalAttachmentVO);
//				}
//			}
//		}
		return filingProposalAttachmentVOList;
	}
	
	@Override
	public FilingProposalCompanyVehcileInfo getFilingProposalCompanyVehcileInfo(Integer filingProposalId) {
		
		FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
		
		FilingProposal filingProposal=declareManagerDao.getFilingProposal(filingProposalId);
		if(null!=filingProposal){
			List<FilingProposalAttachment> filingProposalAttachment=declareManagerDao.getFilingProposalAttachment(filingProposal);
			Vehicle vehicle=null;//vehicleDao.get(filingProposal.getVehicleId());		
			setFilingProposalCompanyVehcileInfo(filingProposalCompanyVehcileInfo,vehicle,filingProposal,filingProposalAttachment);
		}
		
		return filingProposalCompanyVehcileInfo;
	}
	
	@Override
	public FilingProposalCompanyVehcileInfo getFilingProposalCompanyVehcileInfo(Integer vehicleId, Integer companyId) throws IllegalAccessException, InvocationTargetException {
		FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
		if(null!=vehicleId && null!=companyId && vehicleId>=1 &&companyId>=1){
			Vehicle vehicle=vehicleDao.get(vehicleId);
			setFilingProposalCompanyVehcileInfo(filingProposalCompanyVehcileInfo,vehicle,null,null);
		}
		return filingProposalCompanyVehcileInfo;
	}

	@Override
	public List<FilingProposalCompanyVehcileInfo> find(Map parameters,
			String additionalCondition) {
		List<FilingProposal> filingProposalList=declareManagerDao.find(parameters, additionalCondition);
		List<FilingProposalCompanyVehcileInfo> FilingProposalCompanyVehcileInfoList=new ArrayList<FilingProposalCompanyVehcileInfo>();
		if(null!=filingProposalList && !filingProposalList.isEmpty()){
			for(FilingProposal filingProposal:filingProposalList){
				FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
				setFilingProposalCompanyVehcileInfo(filingProposalCompanyVehcileInfo,null,filingProposal,null);
				FilingProposalCompanyVehcileInfoList.add(filingProposalCompanyVehcileInfo);
			}
		}
		return FilingProposalCompanyVehcileInfoList;
	}
	
	/**
	 * 
	* Description: 外出维修报备列表页面
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param vehicle
	* @param transMsg
	* @param declareType
	* @param dealerCode
	* @param startTime
	* @param endTime
	* @param declareStatus
	* @param isExport
	* @return
	 */

	public List<FilingProposalCompanyVehcileInfo> getFilingProposalCVInfoList(Integer companyId,VehicleVO vehicle,TransMsg transMsg,Integer declareType,String dealerCode,String startTime,String endTime,String declareStatus,boolean isExport){
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		if (isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		if(null!=startTime && "".equals(startTime.trim())){
			startTime=null;
		}
		if(null!=endTime && "".equals(endTime.trim())){
			endTime=null;
		}
		if(null!=declareStatus && "-1".equals(declareStatus)){
			declareStatus=null;
		}
		List<FilingProposalCompanyVehcileInfo> filingProposalCVInfoList =findByPage(transMsg, vehicle, companyId, declareType,dealerCode,startTime,endTime,declareStatus);
		return filingProposalCVInfoList;
	}
	
	/**
	 * 
	* Description: 查询报表详情
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param vehicleId
	* @param declareType
	* @param companyId
	* @return
	 */

	public List queryDeclareInfo(Integer filingProposalId,Integer vehicleId,Integer declareType,Integer companyId) {
		List list=new ArrayList();
		FilingProposalCompanyVehcileInfo filingProposalCV=null;
		List<FilingProposalVO> filingProposalList=null;
		filingProposalCV = getFilingProposalCompanyVehcileInfo(filingProposalId);
		if(null!=filingProposalCV){
			TransMsg transMsg=new TransMsg();
			String hisMonth=GlobalConfig.getString("DECLARE_HIS_MONTH");
			Integer declareHisMonth=(null==hisMonth || "".equals(hisMonth) ? 6 : Integer.parseInt(hisMonth));
			String startTime=DateTime.getDate(DateTime.getOffsetMonthDate(filingProposalCV.getFilingProposal().getProposalTime(),declareHisMonth),"yyyy-MM-dd HH:mm:ss");
			transMsg.setPageSize(12);
			transMsg.setStartIndex(1);
			filingProposalList=getFilingProposalList(transMsg,vehicleId,companyId,declareType,startTime,null);
			list.add(filingProposalList);
			list.add(filingProposalCV);
		}else{
			throw new ProtocolParseBusinessException("msgResultContent","没有查到相关记录");
		}
		return list;
	}
	
	/**
	 * 
	* Description: 根据车牌查找车辆信息
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param vehicleId
	* @param declareType
	* @param companyId
	* @return
	 */

	public ResponseData findVehiclePlateNumber(String plateNumber,Integer declareType){
		ResponseData rd = new ResponseData(0);

		Vehicle dbVehicle = vehicleDao.getByPalteNumber(plateNumber);
		if (null == dbVehicle) {
			return rd;
		}
		VehicleVO vehicleVO = new VehicleVO(dbVehicle);
		FilingProposalVO fpv = getFilingProposalByVehicle(vehicleVO.getId(),declareType);
		if (null != fpv) {
			if (declareType.intValue() == 1) {
				throw new ProtocolParseBusinessException("msgResultContent","还有处于待审批的外出报备");
			} else {
				throw new ProtocolParseBusinessException("msgResultContent","还有处于待审批的维修报备");
			}
		}
		String licensingTime = DateTime.toNormalDate(vehicleVO.getLicensingTime());
		vehicleVO.setLicensingTimeStr(licensingTime);

		Box box = CacheVehicleManager.getBoxByVehicleId(vehicleVO.getId());
		rd.put("vehicle", vehicleVO);
		rd.put("simMobile", (null == box ? "" : box.getSimMobile()));
		rd.setCode(1);
		return rd;
	}
	
	@Override
	public List<FilingProposalCompanyVehcileInfo> findByPage(TransMsg transMsg,VehicleVO vehicle, Integer currentCompanyId,int declareType,String dealerCode,String startTime,String endTime,String declareStatus) {
		
		List<FilingProposalCompanyVehcileInfo> filingProposalCVList=new ArrayList<FilingProposalCompanyVehcileInfo>();
		
		StringBuffer additionalCondition = new StringBuffer(" and status in (2,3,4) and type="+declareType);
		if(null!=vehicle){
			List<Vehicle> vehicleList=new ArrayList<Vehicle>();
			HashMap parameters=new HashMap();
			if(null!=vehicle.getVin() && !"".equals(vehicle.getVin())){
				ParameterUtil.appendLikeQueryCondition(parameters, "vin", vehicle.getVin());
			}
			if(null!=vehicle.getPlateNumber() && !"".equals(vehicle.getPlateNumber())){
				ParameterUtil.appendLikeQueryCondition(parameters, "plateNumber", vehicle.getPlateNumber());
			}
			if(null!=parameters && !parameters.isEmpty()){
				vehicleList=vehicleDao.find(parameters, "");
				if(null!=vehicleList){
					additionalCondition.append(" and vehicleId in (");
					for(Vehicle tvehicle:vehicleList){
						additionalCondition.append(tvehicle.getId()).append(",");
					}
					additionalCondition.deleteCharAt(additionalCondition.lastIndexOf(","));
					additionalCondition.append(")");
				}else{//如果为空
					 return filingProposalCVList;
				}
			}
		}
		
		if(null!=startTime){
			additionalCondition.append(" and startTime>=str_to_date('").append(startTime).append("','%Y-%m-%d %H:%i:%s')");
		}
		if(null!=endTime){
			additionalCondition.append(" and endTime<=str_to_date('").append(endTime).append("','%Y-%m-%d %H:%i:%s')");
		}
		if(null!=declareStatus){
			additionalCondition.append(" and status=").append(Integer.parseInt(declareStatus));
		}
		
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		String companyIdStr ="";
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(dealerCode==null||dealerCode.equals(""))
		{
			
		}else{
			 companyIdList =companyDao.getByDealerCodeList(dealerCode);
			 //拿到该companyId下面所有的经销商ID
			 List allList = CacheOrgManager.getChildrenOrgIdsList(currentCompanyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
				 //	"没有该经销商!"
					return null;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(compangIdSend!=""){
			companyIdStr += " and  companyId in(" +compangIdSend + ")";
		}else{
			if (currentCompanyId != null) {
				companyIdStr += " and  companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE) + ")";
			}
		}
		additionalCondition.append(companyIdStr);
		/*if(null!=dealerCode && !"".equals(dealerCode)){
			String condition="";
			List<Company> companyList=null;
			HashMap parameters=new HashMap();
			parameters.put("dealerCode", dealerCode);
			ParameterUtil.appendLikeQueryCondition(parameters, "dealerCode", dealerCode);
			condition+=" and companyId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId) + ")";
			companyList=companyDao.find(parameters, condition);
			if(null!=companyList && !companyList.isEmpty()){
				additionalCondition.append(" and companyId in(");
				for(Company company:companyList){
					additionalCondition.append(company.getId()+",");
				}
				additionalCondition.deleteCharAt(additionalCondition.lastIndexOf(","));
				additionalCondition.append(")");
			}
		}else{
			additionalCondition.append(" and companyId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId) + ")");
		}*/
		additionalCondition.append(" order by id desc,proposalTime desc");
		List<FilingProposal> filingProposalList=declareManagerDao.findByPage(transMsg, additionalCondition.toString());
		if(null!=filingProposalList){
			FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=null;
			FilingProposalVO filingProposalVO=null;
			VehicleVO vehicleVO=new VehicleVO();
			for(FilingProposal filingProposal:filingProposalList){
				filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
				filingProposalVO=new FilingProposalVO();
				BeanUtils.copyProperties(filingProposal, filingProposalVO);
				filingProposalCompanyVehcileInfo.setFilingProposal(filingProposalVO);
				vehicleVO= null;//getVehicleInfo(CacheVehicleManager.getVehicleById(filingProposal.getVehicleId()));
				if(null==vehicleVO){
					filingProposalCompanyVehcileInfo.setVehicle(new VehicleVO());
				}else{
					filingProposalCompanyVehcileInfo.setVehicle(vehicleVO);
				}
				filingProposalCVList.add(filingProposalCompanyVehcileInfo);
			}
		}
		return filingProposalCVList;
	}
		
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposalCompanyVehcileInfo> saveFilingProposal(HttpServletRequest request,MultipartFile multipartFile,FilingProposalCompanyVehcileInfo filingProposalCVInfo,Integer vehicleId){
		User user=SessionManager.getCurrentUser(request);
		Integer departmentId = SessionManager.getCurrentCompanyId(request);
		Date currentDate=new Date();
		FilingProposalVO filingProposal=filingProposalCVInfo.getFilingProposal();
		if(null==filingProposal)
			throw new ProtocolParseBusinessException("msgResultContent","报备信息填写不全");
		if(null==filingProposal.getStartTime() || null==filingProposal.getEndTime()){
			throw new ProtocolParseBusinessException("msgResultContent","开始日期和结束日期不能为空");
		}
		FilingProposalVO fpv=getFilingProposalByVehicle(vehicleId,filingProposal.getType());
		if(null!=fpv){
			if(filingProposal.getType().intValue()==1){
				throw new ProtocolParseBusinessException("msgResultContent","还有处于待审批的市场活动");
			}else{
				throw new ProtocolParseBusinessException("msgResultContent","还有处于待审批的事故维修");
			}
		}
		
		Integer companyId=CacheVehicleManager.getVehicleById(vehicleId).getCompanyId();
		if(null==filingProposal.getType())
			filingProposal.setType(1);
		//filingProposal.setStatus(GlobalConstant.FILING_PROPOSAL_STATUS_SUBMIT);
//		filingProposal.setCompanyId(null==companyId ? -1 : companyId);
//		filingProposal.setDepartmentId(departmentId);
//		filingProposal.setProposerId(user.getId());
//		filingProposal.setVehicleId(vehicleId);
//		filingProposal.setCommitTime(currentDate);

		FilingProposal fp=new FilingProposal();
		
		UploadHelper.validatorFile(multipartFile);
		BeanUtils.copyProperties(filingProposal, fp);
		fp.setProposalTime(currentDate);
		Integer filingProposalId=declareManagerDao.save(fp);
		uploadFile(request,multipartFile,filingProposalId,currentDate);
		sendEmail(fp);
		
		TransMsg transMsg=new TransMsg();
		List<FilingProposalCompanyVehcileInfo> list =getFilingProposalCVInfoList(departmentId,null,transMsg,filingProposal.getType(),null,null,null,null,false);
		return list;
	}
	
	
	/**
	 * 
	* Description: 编辑报备
	* @author henggh 
	* @version 0.1 
	* @param filingProposalCVInfo
	* @return
	 */

	public List<FilingProposalCompanyVehcileInfo> editDeclare(FilingProposalCompanyVehcileInfo filingProposalCVInfo,Integer userId,Integer companyId){
		FilingProposalVO filingProposalView=filingProposalCVInfo.getFilingProposal();
		Date currentDate=new Date();
//		if(null!=filingProposalView && null!=filingProposalView.getId()){
//			if(null==filingProposalCVInfo.getFilingProposal().getStartTime() || null==filingProposalCVInfo.getFilingProposal().getEndTime()){
//				throw new ProtocolParseBusinessException("msgResultContent","开始日期和结束日期不能为空");
//			}
//					
//			FilingProposalVO filingProposalVO=getFilingProposal(filingProposalView.getId());
//			filingProposalVO.setDescription(filingProposalView.getDescription());
//			filingProposalVO.setReason(filingProposalView.getReason());
//			filingProposalVO.setStartTime(filingProposalView.getStartTime());
//			filingProposalVO.setEndTime(filingProposalView.getEndTime());
//			filingProposalVO.setProposalTime(new Date());
//			filingProposalVO.setProposerId(userId);
//			filingProposalVO.setFilingProposalAttachmentVOList(null);
//			FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
//			filingProposalCompanyVehcileInfo.setFilingProposal(filingProposalVO);
//			edit(filingProposalCompanyVehcileInfo);
//		}
		TransMsg transMsg=new TransMsg();
		List list=getFilingProposalCVInfoList(companyId,null,transMsg,filingProposalView.getType(),null,null,null,null,false);
		return list;
	}
	
	/**
	 * 处理上一级有多个管理员情况
	 * @param users
	 * @param fp
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
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
	 * 单个附件上传
	 * @param request
	 * @param multipartFile
	 * @param filingProposalId
	 * @return
	 */

	public void uploadFile(HttpServletRequest request,MultipartFile multipartFile, Integer filingProposalId,Date date) {
		UploadHelper.getFileSet(request, 0, 0);
		User user = SessionManager.getCurrentUser(request);
		FilingProposalAttachmentVO fpaVO = new FilingProposalAttachmentVO();
		fpaVO.setCreateId(user.getId());
		fpaVO.setCreateTime(date);
		fpaVO.setFilingProposalId(filingProposalId);
		String rtnFile[] = UploadHelper.uploadFile(multipartFile,filingProposalId);
		if (null != rtnFile) {
			fpaVO.setOriginalName(rtnFile[0]);
			fpaVO.setDisplayName(rtnFile[1]);
			fpaVO.setFullPath(rtnFile[2]);
			fpaVO.setType(rtnFile[3]);
			fpaVO.setSize(String.valueOf(multipartFile.getSize()));
			fpaVO.setStatus(1);
			fpaVO.setCreateTime(date);
			saveFilingProposalFile(fpaVO);
		}
	}

	public ResponseData uploadFile(HttpServletRequest request,Integer filingProposalId,String delFiles){
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if(!(null==multipartRequest 
				|| null==multipartRequest.getFiles("attach") 
				|| multipartRequest.getFiles("attach").isEmpty()
				|| null==multipartRequest.getFiles("attach").get(0)
				|| multipartRequest.getFiles("attach").get(0).isEmpty())){
			ResponseData  rd=getFilingProposalAttachList(filingProposalId);
			Map<String,String> mapMessage=new HashMap<String,String>();
			if(null==rd)
				throw new ProtocolParseBusinessException("msgResultContent","获取已有文件列表出错");
			long existsFileSize=0L;
			int existsFileNum=0;
			if(null!=rd.get("filingProposalAttachmentList")){
				List<FilingProposalAttachmentVO> fpaVOList=(List<FilingProposalAttachmentVO>) rd.get("filingProposalAttachmentList");
				if(null!=fpaVOList){
					String delFileArray[]=null;
					if(null!=delFiles && delFiles.trim().length()>=1){
						delFileArray=delFiles.split(",");
					}
					existsFileNum=fpaVOList.size();
					for(FilingProposalAttachmentVO filingProposalAttachmentVO:fpaVOList){
						if(null!=delFileArray && delFileArray.length>0){
							for(int i=0;i<delFileArray.length;i++){
								if(Integer.parseInt(delFileArray[i])==filingProposalAttachmentVO.getId().intValue()){
									--existsFileNum;
									continue;
								}else{
									existsFileSize+=Long.parseLong(filingProposalAttachmentVO.getSize());
								}
							}
						}else{
							existsFileSize+=Long.parseLong(filingProposalAttachmentVO.getSize());
						}
					}
				}
			}
			List<MultipartFile> multipartFiles =null;
			Map map=UploadHelper.getFileSet(request,existsFileNum,existsFileSize);
			multipartFiles= (List<MultipartFile>) map.get("multipartFileList");
			if(null==multipartFiles || multipartFiles.size()<=0){
				throw new ProtocolParseBusinessException("msgResultContent","上传文件为空");
			}else{
				User user=SessionManager.getCurrentUser(request);
				FilingProposalAttachmentVO fpaVO=new FilingProposalAttachmentVO();
				fpaVO.setCreateId(user.getId());
				fpaVO.setCreateTime(new Date());
				fpaVO.setFilingProposalId(filingProposalId);
				for (MultipartFile multipartFile : multipartFiles) {
					String rtnFile[] = UploadHelper.uploadFile(multipartFile,filingProposalId);
					if(null!=rtnFile){
						fpaVO.setOriginalName(rtnFile[0]);
						fpaVO.setDisplayName(rtnFile[1]);
						fpaVO.setFullPath(rtnFile[2]);
						fpaVO.setType(rtnFile[3]);
						fpaVO.setSize(String.valueOf(multipartFile.getSize()));
						//fpaVO.setSize((new BigDecimal(multipartFile.getSize()).divide(new BigDecimal(1024*1024),2,BigDecimal.ROUND_HALF_UP)).toString());
						fpaVO.setStatus(1);
						saveFilingProposalFile(fpaVO);
					}
				}
			}
		}
		if(null!=delFiles && delFiles.trim().length()>0){
			delDeclareAttachmentFileNew(filingProposalId,delFiles);
		}
		return getDeclareAttachFileList(filingProposalId);
	}
	

	/**根据车型ID找车牌及车辆信息*/
	public Map findVehicleByCarStyleId(Integer companyId,Integer carStyleId){
		Map map=new HashMap();
		HashMap parameters=new HashMap();
		parameters.put("carStyleId", carStyleId);
		parameters.put("companyId", companyId);
		parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
		List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
		if(null==vehicleList || vehicleList.isEmpty()){
			map.put("plateNumbers", null);
			map.put("vehilce", null);
		}else{
			List<String> plateNumbers=getPlateNumbers(vehicleList);
			map.put("plateNumbers", plateNumbers);
			map.put("vehilce", vehicleList.get(0));
		}
		return map;
	}
	
	private void delDeclareAttachmentFileNew(Integer filingProposalId,String filingProposalAttachmentId) {
		String filingProposalAttachmentIds[]=filingProposalAttachmentId.split(",");
		FilingProposalAttachmentVO filingProposalAttachmentVO=null;
		for(int i=0;i<filingProposalAttachmentIds.length;i++){
			filingProposalAttachmentVO=getFilingProposalAttachment(Integer.parseInt(filingProposalAttachmentIds[i]));
			if(null!=filingProposalAttachmentVO){
				UploadHelper.delFile(filingProposalAttachmentVO.getFullPath());
				delFilingProposalAttachment(filingProposalId,Integer.parseInt(filingProposalAttachmentIds[i]));
			}else{
				throw new ProtocolParseBusinessException("msgResultContent","删除文件失败");
			}
		}
	}
	

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public List<FilingProposalCompanyVehcileInfo> submitCancelDeclare(Integer userId,Integer companyId,Integer filingProposalId,Integer declareType,Integer declareStatus){
		Date currentDate=new Date();
		FilingProposalVO filingProposalVO=getFilingProposal(filingProposalId);
		filingProposalVO.setStatus(declareStatus);
		//filingProposalVO.setProposerId(userId);
		filingProposalVO.setProposalTime(currentDate);
		if(null!=declareStatus && declareStatus.intValue()==2){
			//filingProposalVO.setCommitTime(currentDate);
		}else{
			//filingProposalVO.setCommitTime(null);
		}
		FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo=new FilingProposalCompanyVehcileInfo();
		//filingProposalVO.setFilingProposalAttachmentVOList(null);
		filingProposalCompanyVehcileInfo.setFilingProposal(filingProposalVO);

		edit(filingProposalCompanyVehcileInfo);
		
		TransMsg transMsg=new TransMsg();
		List<FilingProposalCompanyVehcileInfo> list=getFilingProposalCVInfoList(companyId,null,transMsg,declareType,null,null,null,null,false);
		return list;
	}
	

	public ResponseData getDeclareAttachFileList(Integer filingProposalId) {
		ResponseData rd=getFilingProposalAttachList(filingProposalId);
		return rd;
	}
	
	private VehicleVO getVehicleInfo(Vehicle vehicle){
		VehicleVO vehicleVO=new VehicleVO();		
		if(null!=vehicle){
			vehicleVO = new VehicleVO(vehicle);
			
			Company company = CacheCompanyManager.getCompany(vehicle.getCompanyId());
			
			DealerVO dealerVO=new DealerVO(company);
			if(null==dealerVO){
				dealerVO=new DealerVO();
			}
			if(null==dealerVO.getDistributionSaleCenterName()){
				dealerVO.setDistributionSaleCenterName("");
			}
			vehicleVO.setDealer(dealerVO);
			Box box = CacheVehicleManager.getBoxByVehicleId(vehicle.getId());
			BoxVO boxVO=new BoxVO(box);
			if(null==boxVO){
				vehicleVO.setBox(new BoxVO());
			}else{
				vehicleVO.setBox(boxVO);
			}
		}else{
			vehicleVO.setDealer(new DealerVO());
			vehicleVO.setBox(new BoxVO());
		}
		return vehicleVO;
	}
	
	private void setFilingProposalCompanyVehcileInfo(FilingProposalCompanyVehcileInfo filingProposalCompanyVehcileInfo,Vehicle vehicle,FilingProposal filingProposal,List<FilingProposalAttachment> filingProposalAttachment){
		VehicleVO vehicleVO=new VehicleVO();
		CompanyVO companyVO=new CompanyVO();
		FilingProposalVO filingProposalVO=new FilingProposalVO();
		
		if(null!=vehicle){
			
			VehicleVO aVehicleVO = new VehicleVO(vehicle);
			
			Company company = CacheCompanyManager.getCompany(vehicle.getCompanyId());
			
			DealerVO dealerVO=new DealerVO(company);
			if(null==dealerVO){
				dealerVO=new DealerVO();
			}
			if(null==dealerVO.getDistributionSaleCenterName()){
				dealerVO.setDistributionSaleCenterName("");
			}
			aVehicleVO.setDealer(dealerVO);
			Box box = CacheVehicleManager.getBoxByVehicleId(vehicle.getId());
			BoxVO boxVO=new BoxVO(box);
			if(null==boxVO){
				aVehicleVO.setBox(new BoxVO());
			}else{
				aVehicleVO.setBox(boxVO);
			}
			//BeanUtils.copyProperties(vehicle, vehicleVO);
			
			filingProposalCompanyVehcileInfo.setVehicle(aVehicleVO);
		}
		/**
		if(null!=company){
			BeanUtils.copyProperties(company, companyVO);
			filingProposalCompanyVehcileInfo.setCompany(companyVO);
		}*/
		if(null!=filingProposal){
			BeanUtils.copyProperties(filingProposal, filingProposalVO);
			if(null!=filingProposalAttachment && !filingProposalAttachment.isEmpty()){
				List<FilingProposalAttachmentVO> filingProposalAttachmentVOList=copyListProperties(filingProposalAttachment,FilingProposalAttachmentVO.class);
				//filingProposalVO.setFilingProposalAttachmentVOList(filingProposalAttachmentVOList);
			}
			filingProposalCompanyVehcileInfo.setFilingProposal(filingProposalVO);
		}
	}
	
	private List<String> getPlateNumbers(List<Vehicle> vehicleList){
		List<String> plateNumbers=new ArrayList<String>();
		if(null==vehicleList || vehicleList.isEmpty())
			return null;
		for(Vehicle vehicle : vehicleList){
			plateNumbers.add(vehicle.getPlateNumber());
		}
		return plateNumbers;
	}
	
	public Map findDealerCarStyle(Integer companyId){
		Map map=new HashMap();
		List<CarStyle> carStyleList=findByCompanyId(companyId);
		if(null==carStyleList || carStyleList.isEmpty()){
			throw new ProtocolParseBusinessException("msgResultContent","暂时未为您配置可用车型");
		}
		HashMap parameters=new HashMap();
		parameters.put("companyId", companyId);
		parameters.put("carStyleId", carStyleList.get(0).getId());
		parameters.put("configureStatus", GlobalConstant.CONFIGURE_STATUS_INSTALLED);
		List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
		if(null==vehicleList || vehicleList.isEmpty()){
			map.put("plateNumbers", null);
			map.put("vehilce", null);
		}else{
			List<String> plateNumbers=getPlateNumbers(vehicleList);
			map.put("plateNumbers", plateNumbers);
			map.put("vehilce", vehicleList.get(0));
		}
		map.put("carStyles", carStyleList);
		return map;
	}
	
	private List<CarStyle> findByCompanyId(Integer companyId) {
		
		List<CarStyle> carStyleList = new ArrayList<CarStyle>();
		Map parameters = new HashMap();
		parameters.put("companyId", companyId);
		List<CompanyCarStyleRelation> companyCarStyleRelationList = companyCarStyleRelationDao.find(parameters, "order by carStyleId asc");
		if (companyCarStyleRelationList != null && companyCarStyleRelationList.size() > 0) {
			for (CompanyCarStyleRelation companyCarStyleRelation : companyCarStyleRelationList) {
				if (companyCarStyleRelation != null && companyCarStyleRelation.getCarStyleId() != null &&
						companyCarStyleRelation.getConfigType() != null ) {
					Integer carStyleId = companyCarStyleRelation.getCarStyleId();
					Integer configType = companyCarStyleRelation.getConfigType();
					if (configType == GlobalConstant.CONFIGURE_TYPE_NO) {
						continue;
					}
					CarStyle carStyle = CacheCarManager.getCarStyleById(carStyleId);
					carStyleList.add(carStyle);
				}
				
			}
		}
		return carStyleList;
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
}