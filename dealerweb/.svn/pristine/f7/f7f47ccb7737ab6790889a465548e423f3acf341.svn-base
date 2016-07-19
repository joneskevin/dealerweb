package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.admin.dao.IReHandleTestDriveDao;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IBoxDao;
import com.ava.dao.IBoxOperationDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IDesignateOrderDao;
import com.ava.dao.IProposalDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.dao.IVehicleDao;
import com.ava.dao.IVehicleInstallationPlanDao;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.DesignateOrder;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.entity.VehicleInstallationPlan;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.RebateFindVO;
import com.ava.domain.vo.VehicleFindVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.enums.ServiceResponseEnum;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheBoxManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

@Service
public class VehicleService extends BaseService implements IVehicleService {
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	
	@Autowired
	private IBoxDao boxDao;
	
	@Autowired
	private IBoxOperationDao boxOperationDao;
	
	@Autowired
	private IProposalDao proposalDao;
	
	@Autowired
	private IVehicleInstallationPlanDao vehicleInstallationPlanDao;
	
	@Autowired
	private IDesignateOrderDao designateOrderDao;
	
	@Resource(name="reHandleTestDriveDao")
	private IReHandleTestDriveDao reHandleTestDriveDao;
	
	protected final static Logger logger = Logger.getLogger(VehicleService.class);

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addVehicle(Vehicle vehicle, Integer companyId) {
		ResponseData rd = new ResponseData(0);

		String vin = vehicle.getVin();
		Vehicle dbVehicle = vehicleDao.getByVin(vin);
		if (dbVehicle != null) {
			rd.setCode(-1);
			rd.setMessage("已存在有相同VIN码的车辆");
			return rd;
		}
		
		if (vehicle.getPlateNumber() != null && vehicle.getPlateNumber().length() > 0) {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("plateNumber", vehicle.getPlateNumber());
			List<Vehicle> vehicleList = hibernateDao.find("Vehicle", parameters, " and configureStatus != " + GlobalConstant.CONFIGURE_STATUS_UNINSTALLED);
			if (vehicleList != null && vehicleList.size() > 0) {
				rd.setCode(-1);
				rd.setMessage("已存在有相同车牌的车辆");
				return rd;
			}
		}else {
			vehicle.setPlateNumber("");
		}
		
		Integer newId = (Integer) vehicleDao.save(vehicle);
		
		CacheVehicleManager.removeVehicleById(newId);//删除缓存

		rd.setCode(1);
		rd.setMessage("新增车辆成功");
		rd.setFirstItem(vehicle);
		return rd;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData editVehicle(Vehicle frmVehicle) {
		ResponseData rd = new ResponseData(0);

		Integer vehicleId = frmVehicle.getId();
		Vehicle dbVehicle = vehicleDao.get(vehicleId);
		if (dbVehicle.getVin() !=null && !frmVehicle.getVin().equalsIgnoreCase(dbVehicle.getVin())) {
			if (this.isExistence(vehicleId, "vin", frmVehicle.getVin(), null)) {
				rd.setCode(-1);
				rd.setMessage("车辆信息保存失败：新的VIN已经存在！");
				return rd;
			}
		}
		
		if (dbVehicle.getPlateNumber() !=null && !frmVehicle.getPlateNumber().equalsIgnoreCase(dbVehicle.getPlateNumber())) {
			if (this.isExistence(vehicleId, "plateNumber", frmVehicle.getPlateNumber(), " and configureStatus != " + GlobalConstant.CONFIGURE_STATUS_UNINSTALLED)) {
				rd.setCode(-1);
				rd.setMessage("车辆信息保存失败：新的车牌已经存在！");
				return rd;
			}
		}
		
		
		MyBeanUtils.copyPropertiesContainsDate(dbVehicle, frmVehicle);
		vehicleDao.update(dbVehicle);
		
		CacheVehicleManager.removeVehicleById(dbVehicle.getId());//删除缓存

		rd.setFirstItem(dbVehicle);
		rd.setCode(1);
		rd.setMessage("编辑车辆成功");
		return rd;
	}
	
	public ResponseData displayAddMaintainVehicle(VehicleVO vehicle){
		ResponseData rd = new ResponseData(1);
		if (vehicle == null) {
			vehicle = new VehicleVO();
		}
		if (vehicle.getDealer() == null) {
			DealerVO dealer = new DealerVO();
			vehicle.setDealer(dealer);
		}
		rd.setFirstItem(vehicle);
		return rd;
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addMaintainVehicle(VehicleVO vehicleVO) {
		ResponseData rd = new ResponseData(1);
		
		Integer companyId = vehicleVO.getCompanyId();
		String vin = vehicleVO.getVin();
		Vehicle dbVehicle = vehicleDao.getByVin(vin);
		if (dbVehicle != null) {
			rd.setCode(-1);
			rd.setMessage("已存在有相同VIN码的车辆");
			return rd;
		}
		
		if (vehicleVO.getPlateNumber() != null && vehicleVO.getPlateNumber().length() > 0) {
			if (this.isExistence(null, "plateNumber", vehicleVO.getPlateNumber(), " and configureStatus != " + GlobalConstant.CONFIGURE_STATUS_UNINSTALLED)) {
				rd.setCode(-1);
				rd.setMessage("已存在有相同车牌的车辆");
				return rd;
			}
			
		} else {
			vehicleVO.setPlateNumber("");
		}
		
		Vehicle vehicle = new Vehicle();
		MyBeanUtils.copyPropertiesContainsDate(vehicle, vehicleVO);
		Integer configureStatus = GlobalConstant.CONFIGURE_STATUS_WATTING;
		Integer proposalStatus = GlobalConstant.PROPOSAL_STATUS_PASSED;
		
		//如果添加的车型为待换装的车型，状态设为已安装, 并设置计划Id
		if (vehicleVO.getCarStyleId() != null && vehicleVO.getCarStyleId().intValue() > 0) {
			Company company = CacheCompanyManager.getCompany(companyId);
			Integer carStyleId = vehicleVO.getCarStyleId();
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("dealerCode", company.getDealerCode());
			parameters.put("testDriveCarStyleId", carStyleId);
			List<DesignateOrder> doList = designateOrderDao.find(parameters, "");
			if (doList != null && doList.size() > 0) {
				parameters.clear();
				parameters.put("flag", 0);//启用
				parameters.put("type", 1);//换装
				parameters.put("testDriveCarStyleId", carStyleId);//换装车型
				List<VehicleInstallationPlan> vipList = vehicleInstallationPlanDao.find(parameters, "");
				if (vipList != null && vipList.size() > 0) {
					VehicleInstallationPlan vehicleInstallationPlan = vipList.get(0);
					vehicle.setReplacePlanId(vehicleInstallationPlan.getId());
					configureStatus = GlobalConstant.CONFIGURE_STATUS_INSTALLED;
					proposalStatus = GlobalConstant.PROPOSAL_STATUS_INSTALLED;
				}
			}
			
		}
		
		vehicle.setConfigureStatus(configureStatus);
		vehicle.setConfigureType(GlobalConstant.CONFIGURE_TYPE_MUST);		
		Integer vehicleId = vehicleDao.save(vehicle);
		
		Proposal proposal = new Proposal();
		proposal.setCompanyId(companyId);
		proposal.setDepartmentId(companyId);
		proposal.setVehicleId(vehicleId);
		
		proposal.setType(GlobalConstant.PROPOSAL_TYPE_REPLACE);//换装
		proposal.setStatus(proposalStatus);
		proposal.setProposerId(Integer.valueOf(GlobalConstant.OPERATIONS_CENTER_ID));
		proposal.setProposalTime(DateTime.toDate(DateTime.getNormalDateTime()));
		proposal.setContactName("");
		proposal.setContactPhone("");
		
		proposalDao.save(proposal);
		
		rd.setMessage("新增车辆成功");
		rd.setFirstItem(vehicleVO);
		return rd;
	}
	
	@Override
	public ResponseData displayEditMaintainVehicle(Integer vehicleId) {
		ResponseData rd = new ResponseData(0);
		Vehicle dbVehicle = vehicleDao.get(vehicleId);
		VehicleVO vehicleVO = new VehicleVO(dbVehicle);
		
		Company dbCompany = CacheCompanyManager.getCompany(dbVehicle.getCompanyId());
		if (dbCompany != null) {
			DealerVO  dealerVO = new DealerVO (dbCompany);
			vehicleVO.setDealer(dealerVO);
		}
		
		rd.setFirstItem(vehicleVO);
		return rd;
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData editMaintainVehicle(VehicleVO frmVehicle) {
		ResponseData rd = new ResponseData(1);
		
		Integer vehicleId = frmVehicle.getId();
		Vehicle dbVehicle = vehicleDao.get(vehicleId);
		if (dbVehicle.getVin() !=null && !frmVehicle.getVin().equalsIgnoreCase(dbVehicle.getVin())) {
			if (this.isExistence(vehicleId, "vin", frmVehicle.getVin(), null)) {
				rd.setCode(-1);
				rd.setMessage("车辆信息保存失败：新的VIN已经存在！");
				return rd;
			}
		}
		
		if (dbVehicle.getPlateNumber() !=null && !frmVehicle.getPlateNumber().equalsIgnoreCase(dbVehicle.getPlateNumber())) {
			if (this.isExistence(vehicleId, "plateNumber", frmVehicle.getPlateNumber(), " and configureStatus != " + GlobalConstant.CONFIGURE_STATUS_UNINSTALLED)) {
				rd.setCode(-1);
				rd.setMessage("车辆信息保存失败：新的车牌已经存在！");
				return rd;
			}
		}
		MyBeanUtils.copyPropertiesContainsDate(dbVehicle, frmVehicle);
		if (frmVehicle.getLicensingTime() == null) {
			dbVehicle.setLicensingTime(null);
		}
		//如果添加的车型为待换装的车型，状态设为已安装
		if (frmVehicle.getCarStyleId() != null && frmVehicle.getCarStyleId().intValue() > 0) {
			Integer carStyleId = frmVehicle.getCarStyleId();
			String exeCountSql = "select count(vi.id) from VehicleInstallationPlan vi where vi.flag = 0 and vi.testDriveCarStyleId =" + carStyleId + " and vi.type = " + GlobalConstant.TRUE;
			int count = hibernateDao.executeQueryInt(exeCountSql);
			if (count > 0) {
				dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_INSTALLED);
			}
			
		}
		
		vehicleDao.update(dbVehicle);
		
		CacheVehicleManager.removeVehicleById(dbVehicle.getId());//删除缓存
		
		rd.setFirstItem(dbVehicle);
		rd.setMessage("编辑车辆成功");
		return rd;
	}
	
	@Override
	public ResponseData regainVehcile(VehicleVO frmVehicle) {
		ResponseData rd = new ResponseData(1);
		
		Integer vehicleId = frmVehicle.getId();
		Vehicle dbVehicle = vehicleDao.get(vehicleId);
		MyBeanUtils.copyPropertiesContainsDate(dbVehicle, frmVehicle);
		if (frmVehicle.getLicensingTime() == null) {
			dbVehicle.setLicensingTime(null);
		}
		dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_WATTING);
		vehicleDao.update(dbVehicle);
		CacheVehicleManager.removeVehicleById(dbVehicle.getId());//删除缓存
		
		rd.setFirstItem(dbVehicle);
		rd.setMessage("恢复车辆成功");
		return rd;
	}

	public ResponseData listVehicleOld(TransMsg transMsg, Vehicle vehicle,
			Integer currentCompanyId, Integer currentUserId, String dealerCode, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		
		if(isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		
		String additionalCondition = "";
		String companyIdStr ="";
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend = "";
		if(dealerCode!=null && dealerCode.trim().length() > 0){
			 companyIdList =companyDao.getByDealerCodeList(dealerCode);
			 //拿到该companyId下面所有的经销商ID
			 List allList = CacheOrgManager.getChildrenOrgIdsList(currentCompanyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if (companyIdList == null) {
					rd.setMessage("没有该经销商!");
					return rd;
				} else {
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
			    }
		}
		
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(compangIdSend != ""){
			companyIdStr += " and  companyId in(" +compangIdSend + ")";
		}else{
			if (currentCompanyId != null) {
				companyIdStr += " and  companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE) + ")";
			}
		}
		
		additionalCondition = companyIdStr;
		
		if(null!=vehicle && null!=vehicle.getConfigureStatus()){
			additionalCondition+=" and configureStatus="+vehicle.getConfigureStatus().intValue();
		}
		
		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "vin",
				vehicle.getVin());
		transMsg.put("carStyleId", vehicle.getCarStyleId());
		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(),
				"plateNumber", vehicle.getPlateNumber());

		additionalCondition = additionalCondition + " order by id desc";
		List<Vehicle> vehicleList = vehicleDao.findByPage(transMsg, additionalCondition);
		
		List<VehicleVO> vehicleVOList = new ArrayList();
		if(vehicleList != null && vehicleList.size() > 0){
			for(Vehicle aVehicle : vehicleList){
				if (aVehicle.getConfigureStatus() == GlobalConstant.CONFIGURE_STATUS_INSTALLED) {
					aVehicle.setIsDemolition_Nick("是");
				} else {
					aVehicle.setIsDemolition_Nick("否");
				}
				
				//过滤角色为经销商的用户不能看已锁定的车辆
				if (aVehicle.getConfigureStatus() == GlobalConstant.CONFIGURE_STATUS_UNINSTALLED) {
					if (userRoleRelationDao.isUserCompriseRole(currentUserId, GlobalConstant.DEFAULT_DEALER_ROLE_ID))
						continue;
				}
				
				VehicleVO aVehicleVO = new VehicleVO(aVehicle);
				
				Company dealer = CacheCompanyManager.getCompany(aVehicle.getCompanyId());
				aVehicleVO.setDealer(new DealerVO(dealer));
				
				HashMap parameters = new HashMap();
				parameters.put("vehicleId", aVehicle.getId());
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
					
					aVehicleVO.setBoxOperationVO(boxOperationVO);
				}
				vehicleVOList.add(aVehicleVO);
			}
		}

		rd.setFirstItem(vehicleVOList);
		rd.setCode(1);
		return rd; 
	}
	
	public ResponseData listVehicle(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" IF(vip.ID > 0 , '是', '否') AS isDemolitionNick, '' AS isNetWorkDealerNick ");
		exeSql.append(" FROM t_vehicle v ");
		exeCountSql.append(" FROM t_vehicle v ");
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		//拆除计划表
		exeSql.append("LEFT JOIN t_vehicle_installation_plan vip ON vip.TEST_DRIVE_CAR_STYLE_ID = v.CAR_STYLE_ID AND vip.TYPE = 2 ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = v.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = v.COMPANY_ID ");
		
		//查看待安装、已安装的车辆
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		String configureStatusSql = " AND v.CONFIGURE_STATUS IN ( " + GlobalConstant.CONFIGURE_STATUS_WATTING + " , "  + GlobalConstant.CONFIGURE_STATUS_INSTALLED + ") ";
		exeSql.append(configureStatusSql);
		exeCountSql.append(configureStatusSql);
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, currentUserId));
		exeCountSql.append(getAccessSql(currentCompanyId, currentUserId));
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		exeCountSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , v.INSTALLATION_TIME DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<VehicleFindVO> vehicleFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), VehicleFindVO.class);
		//设置是否为新经销商入网属性
		this.siteNetWorkDealerNick(vehicleFindVOList);
		rd.setFirstItem(vehicleFindVOList);
		rd.setCode(1);
		return rd; 
	}
	
	/**
	 * 以开通账号为起算时间,2月、5月、8月、11月的15日之前（包含15日）与当前相差5个月是新入网经销商
	 * 否则相差7个月是新入网经销商
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleFindVOLis
	 */
	private void siteNetWorkDealerNick(List<VehicleFindVO> vehicleFindVOList) {
		if (vehicleFindVOList != null && vehicleFindVOList.size() > 0) {
			for (VehicleFindVO vehicleFindVO : vehicleFindVOList) {
				Integer differenceMonth = DateTime.getMonthInterval(vehicleFindVO.getNetWorkTime(), new Date());//入网日期和当前日期相差月份
				String netWorkTimeStr =  DateTime.toNormalDate(vehicleFindVO.getNetWorkTime());
				Integer netWorkSeason = DateTime.getSeason(vehicleFindVO.getNetWorkTime());
				Integer netWorkMonth = Integer.valueOf(DateTime.getMonth(netWorkTimeStr));
				Integer netWorkDay = Integer.valueOf(DateTime.getDay(netWorkTimeStr));
				String isNetWorkDealerNick = "否";
				if (netWorkSeason == 1) {
					if (((netWorkMonth == 1 && differenceMonth <= 5) 
							|| (netWorkMonth == 2 && netWorkDay <= 15 && differenceMonth <= 4))
							|| (((netWorkMonth == 2 && netWorkDay > 15) 
							|| (netWorkMonth == 3)) && differenceMonth <= 7 )) {
						isNetWorkDealerNick = "是";
					} 
					
				} else if (netWorkSeason == 2) {
					if (((netWorkMonth == 4 && differenceMonth <= 5) 
							|| (netWorkMonth == 5 && netWorkDay <= 15 && differenceMonth <= 4))
							|| (((netWorkMonth == 5 && netWorkDay > 15) 
							|| (netWorkMonth == 6)) && differenceMonth <= 7 ) ) {
						isNetWorkDealerNick = "是";
					} 
				} else if (netWorkSeason == 3) {
					if (((netWorkMonth == 7 && differenceMonth <= 5) 
							|| (netWorkMonth == 8 && netWorkDay <= 15 && differenceMonth <= 4))
							|| (((netWorkMonth == 8 && netWorkDay > 15) 
							|| (netWorkMonth == 9)) && differenceMonth <= 7 ) ) {
						isNetWorkDealerNick = "是";
					} 
					
				} else if (netWorkSeason == 4) {
					if (((netWorkMonth == 10 && differenceMonth <= 5) 
							|| (netWorkMonth == 11 && netWorkDay <= 15 && differenceMonth <= 4))
							|| (((netWorkMonth == 11 && netWorkDay > 15) 
							|| (netWorkMonth == 12)) && differenceMonth <= 7 ) ) {
						isNetWorkDealerNick = "是";
					} 
				}
				vehicleFindVO.setIsNetWorkDealerNick(isNetWorkDealerNick);
				
			}
		}
	}
	
	public ResponseData listHistoryVehicle(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" IF(vip.ID > 0 , '是', '否') AS isDemolitionNick, '' AS isNetWorkDealerNick ");
		exeSql.append(" FROM t_vehicle v ");
		exeCountSql.append(" FROM t_vehicle v ");
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		//拆除计划表
		exeSql.append("LEFT JOIN t_vehicle_installation_plan vip ON vip.TEST_DRIVE_CAR_STYLE_ID = v.CAR_STYLE_ID AND vip.TYPE = 2 ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = v.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = v.COMPANY_ID ");
		
		/* 大众业务调整：首批导入的数据待换装的车没安装盒子也需换装 TODO
		String boxOperationSql = " INNER JOIN t_box_operation b2 on b2.VEHICLE_ID = v.ID AND b2.TYPE = " +  GlobalConstant.PROPOSAL_TYPE_DEMOLITION;
		exeSql.append(boxOperationSql);
		exeCountSql.append(boxOperationSql);
		exeSql.append(" LEFT JOIN t_box b1 on b2.BOX_ID = b1.ID ");
		 */
		
		//查看待安装、已安装的车辆
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, currentUserId));
		exeCountSql.append(getAccessSql(currentCompanyId, currentUserId));
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		exeCountSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , v.ID DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<VehicleFindVO> vehicleFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), VehicleFindVO.class);
		rd.setFirstItem(vehicleFindVOList);
		rd.setCode(1);
		return rd; 
	}
	
	public ResponseData listMaintainVehicle(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" '' AS isDemolitionNick ");
		exeSql.append(" FROM t_vehicle v ");
		exeCountSql.append(" FROM t_vehicle v ");
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		//拆除计划表
		exeSql.append("LEFT JOIN t_vehicle_installation_plan vip ON vip.TEST_DRIVE_CAR_STYLE_ID = v.CAR_STYLE_ID AND vip.TYPE = 2 ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = v.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = v.COMPANY_ID ");
		
		//exeSql.append(" LEFT JOIN t_box b1 on b1.VEHICLE_ID = v.ID ");
		
		//查看待安装、已安装的车辆
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		if(vehicleVO.getIsHistroy() == null || vehicleVO.getIsHistroy().intValue() == GlobalConstant.FALSE){
			String configureStatusSql = " AND v.CONFIGURE_STATUS IN ( " + GlobalConstant.CONFIGURE_STATUS_WATTING + " , "  + GlobalConstant.CONFIGURE_STATUS_INSTALLED + ") ";
			exeSql.append(configureStatusSql);
			exeCountSql.append(configureStatusSql);
		}
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		exeCountSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , v.INSTALLATION_TIME DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<VehicleFindVO> vehicleFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), VehicleFindVO.class);
		//设置是否为新经销商入网属性
		this.siteNetWorkDealerNick(vehicleFindVOList);
		rd.setFirstItem(vehicleFindVOList);
		rd.setCode(1);
		return rd; 
	}
	
	@Override
	public ResponseData listMonitorVehicleObd(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" '' AS isDemolitionNick ");
		exeSql.append(" FROM t_vehicle v ");
		exeCountSql.append(" FROM t_vehicle v ");
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		//拆除计划表
		exeSql.append("LEFT JOIN t_vehicle_installation_plan vip ON vip.TEST_DRIVE_CAR_STYLE_ID = v.CAR_STYLE_ID AND vip.TYPE = 2 ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = v.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = v.COMPANY_ID ");
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		vehicleVO.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_INSTALLED);
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		exeCountSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , v.ID DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<VehicleFindVO> vehicleFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), VehicleFindVO.class);
		rd.setFirstItem(vehicleFindVOList);
		rd.setCode(1);
		return rd; 
	}
	
	public ResponseData listVehicleRebate(TransMsg transMsg, VehicleVO vehicleVO, Integer currentCompanyId, Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆返利信息
		exeSql.append(getVehicleSql());
		exeSql.append(" b.ID as rebateId, b.SEASON as season, b.REBATE_DATE as rebateDate, b.CREATION_TIME as creationTime, ");
		exeSql.append(" b.IS_REBATE as isRebate, IF (b.IS_REBATE = 1, '是', '否')  as isRebateNick");
		exeSql.append(" FROM t_rebate b ");
		exeCountSql.append(" FROM t_rebate b ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = b.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = b.COMPANY_ID ");
		
		String vehicleSql = " INNER JOIN t_vehicle v on v.ID = b.VEHICLE_ID ";
		exeSql.append(vehicleSql);
		exeCountSql.append(vehicleSql);
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, currentUserId));
		exeCountSql.append(getAccessSql(currentCompanyId, currentUserId));
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		exeCountSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , b.ID DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<RebateFindVO> rebateFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), RebateFindVO.class);
		rd.setFirstItem(rebateFindVOList);
		rd.setCode(1);
		return rd; 
	}
	
	public ResponseData getVehicleList(Integer currentCompanyId) {
		ResponseData rd = new ResponseData(0);
		
		String additionalCondition = "";
		String orgIds = "";
		orgIds = CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE);
		additionalCondition += " and companyId in("	+ orgIds + ")";
		
		additionalCondition = additionalCondition + " order by id desc";
		List<Vehicle> vehicleList = vehicleDao.find(null, additionalCondition);
		
		rd.setFirstItem(vehicleList);
		rd.setCode(1);
		return rd;
	}
	public ResponseData listVehicleForOutput(TransMsg transMsg, Vehicle vehicle,
			Integer currentCompanyId, Integer dealerCode) {
		ResponseData rd = new ResponseData(0);
//		transMsg.setPageSize(5);

		String additionalCondition = "";

		String orgIds = "";
		if(dealerCode != null && dealerCode.intValue() > 0){
			Org dealer = companyDao.getByDealerCode(dealerCode.toString());
			if(dealer != null){
				orgIds = dealer.getId().toString();
			}else{
				rd.setCode(1);
				return rd;
			}
		}else{
			Integer orgId = vehicle.getCompanyId();
			Org org = CacheOrgManager.get(orgId);
			if (org != null) {
				orgIds = CacheOrgManager.getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE);
			} else {
				orgIds = CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE);
			}
		}
		additionalCondition += " and companyId in("
				+ orgIds + ")";

		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "vin",
				vehicle.getVin());
		transMsg.put("carStyleId", vehicle.getCarStyleId());
		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(),
				"plateNumber", vehicle.getPlateNumber());

		additionalCondition = additionalCondition + " order by id desc";
		List<Vehicle> vehicleList = vehicleDao.findByPage(transMsg, additionalCondition);
		
		List<VehicleVO> vehicleVOList = new ArrayList();
		if(vehicleList != null && vehicleList.size() > 0){
			for(Vehicle aVehicle : vehicleList){
				VehicleVO aVehicleVO = new VehicleVO(aVehicle);
				
				Company dealer = CacheCompanyManager.getCompany(aVehicle.getCompanyId());
				aVehicleVO.setDealer(new DealerVO(dealer));
				
				Box box = CacheVehicleManager.getBoxByVehicleId(aVehicle.getId());
				aVehicleVO.setBox(new BoxVO(box));
				
				vehicleVOList.add(aVehicleVO);
			}
		}

		rd.setFirstItem(vehicleVOList);
		rd.setCode(1);
		return rd;
	}
	
	/**
	 * 
	* Description: 查找经销商信息
	* @author henggh 
	* @version 0.1 
	* @param dealerCode
	* @return
	 */
	public ResponseData findMaintenanceDealer(String dealerCode){
		ResponseData rd = new ResponseData(0);
		Company company = companyDao.getByDealerCode(dealerCode);
		rd.put("dealer", company);
		if(null!=company){
			rd.put("dealer", null);
		}
		return rd;
	}
	public Vehicle getVehicle(Integer vehicleId) {
		Vehicle aVehicle = vehicleDao.get(vehicleId);
		return aVehicle;
	}
	
	public Vehicle getByVin(String vin){
		Vehicle aVehicle = vehicleDao.getByVin(vin);
		return aVehicle;
	}

	@Override
	public VehicleVO getVehcileByVin(String vin) {
		Vehicle dbVehicle = vehicleDao.getByVin(vin);
		VehicleVO vehicleVO = new VehicleVO(dbVehicle);
		if (dbVehicle != null) {
			Integer companyId = dbVehicle.getCompanyId();
			if (companyId != null && companyId.intValue() > 0) {
				Company company = CacheCompanyManager.getCompany(dbVehicle.getCompanyId());
				DealerVO dealer = new DealerVO(company);
				vehicleVO.setDealer(dealer);
			}
		}
		return vehicleVO;
	}

	
	public boolean isExistence(Integer vehicleId, String field, String value, String additionalCondition) {
		if (value == null || value.length() <= 0) {
			return false;
		}
		HashMap<String,String> parameters = new HashMap<String,String>();
		parameters.put(field, value);
		List<Vehicle> vehicleList = vehicleDao.find(parameters, additionalCondition);
		if (vehicleList != null && vehicleList.size() > 0) {
			for (Vehicle vehicle : vehicleList) {
				if (vehicleId != null && vehicle.getId() == vehicleId) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public VehicleVO getByPalteNumber(String plateNumber){
		Vehicle dbVehicle = vehicleDao.getByPalteNumber(plateNumber);
		VehicleVO vehicleVO = new VehicleVO(dbVehicle);
		return vehicleVO;
	}

	/**
	 *【设备自动绑定解绑处理方法】入口
	 * @author liuq 
	 * @version 0.1 
	 * @param type
	 * @param vin
	 * @param uniqueId
	 * @param simId
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void handleVehicleBox(Integer type, String vin, String uniqueId, String simId) {
		if (type == null || vin == null || uniqueId == null || simId == null) {
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10002);
		}
		
		Vehicle vehicle = vehicleDao.getByVin(vin);
		if (vehicle == null) {
			logger.info("调用车辆与设备自动绑定解绑时，车辆对象为空");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(), "车辆对象为空");
		}
		
		Integer companyId = vehicle.getCompanyId();
		Integer configureStatus= vehicle.getConfigureStatus();
		Integer obdFlag = vehicle.getObdFlag();
		
		if (type == GlobalConstant.TRUE) {
			//车辆表中无设备信息时或者车辆表中的设备和上传的设备不相同时更新
			if ((0 == obdFlag) || (1 == obdFlag && !uniqueId.equals(vehicle.getObdNo()))) {
				this.updateVehicleAndBox(vehicle, companyId, uniqueId, simId, GlobalConstant.TRUE);
				logger.info("【VW设备绑定】VIN:"+ vin + ";设备号:" + uniqueId + ";车状态:" + configureStatus + ";OBD状态:" + obdFlag);
				
			}
			
		} else if (type == GlobalConstant.FALSE) {
			this.updateVehicleAndBox(vehicle, companyId, uniqueId, simId, GlobalConstant.FALSE);
			logger.info("【VW设备解绑】VIN:"+ vin + ";设备号:" + uniqueId + ";车状态:" + configureStatus + ";OBD状态:" + obdFlag);
			
		} else {
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10002);
		}
	}
	
	/**
	 * 【设备自动绑定解绑处理方法】:更新车辆信息和设备信息，并处理告警信息
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @param companyId
	 * @param uniqueId
	 * @param simId
	 * @param obdFlag 0：解绑 1：绑定
	 */
	void updateVehicleAndBox(Vehicle vehicle, Integer companyId, String uniqueId, String simId, Integer obdFlag){
		Integer vehicleId = vehicle.getId();
		//更新车辆信息
		this.updateVehicle(vehicle, uniqueId, obdFlag);
		//更新设备信息
		this.updateBox(companyId, vehicleId, uniqueId, simId, obdFlag);
		//设备绑定好处理告警日志
		this.handleDeviceLog(uniqueId);
	}
	
	/**
	 *【设备自动绑定解绑处理方法】:更新车辆信息
	 * @param vehicle
	 * @param obdNo
	 * @param obdFlag 0：解绑 1：绑定
	 */
	@SuppressWarnings("unchecked")
	void updateVehicle(Vehicle vehicle, String obdNo, Integer obdFlag){
		Integer vehicleId = vehicle.getId();
		Integer configureStatus = vehicle.getConfigureStatus();
		
		if (obdFlag == GlobalConstant.TRUE) {
			//车辆状态为待安装状态时，更新车辆状态为已安装
			if (configureStatus == GlobalConstant.CONFIGURE_STATUS_WATTING || configureStatus == GlobalConstant.CONFIGURE_STATUS_INSTALLED) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_INSTALLED;
				//更新申请表信息
				this.updateProposalByInstallation(vehicleId);
			}
			
			//查询OBD是否存在于其他车上，如存在则取消绑定。
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("obdNo", obdNo);
			List<Vehicle> vehicelList = vehicleDao.find(parameters, "");
			if (vehicelList != null && vehicelList.size() > 0) {
				Vehicle dbVehicle = (Vehicle) vehicelList.get(0);
				if (dbVehicle != null && dbVehicle.getId() != vehicleId) {
					dbVehicle.setObdFlag(GlobalConstant.FALSE);
					dbVehicle.setObdNo(null);
					vehicleDao.update(dbVehicle);
					CacheVehicleManager.putVehicle(dbVehicle.getId() , dbVehicle);
				}
			}
			
			//插入安装时间用于报表抽取
			if (configureStatus == GlobalConstant.CONFIGURE_STATUS_INSTALLED && vehicle.getInstallationTime() == null) {
				vehicle.setInstallationTime(new Date());
			}
			
		} else if (obdFlag == GlobalConstant.FALSE) {
			//处于待拆除或者已拆除
			if (configureStatus == GlobalConstant.CONFIGURE_STATUS_DEMOLISHING || configureStatus == GlobalConstant.CONFIGURE_STATUS_UNINSTALLED) {
				configureStatus = GlobalConstant.CONFIGURE_STATUS_UNINSTALLED;
				//更新申请表信息
				this.updateProposalByDemotion(vehicleId);
			}	
			 
			//插入拆除时间用于报表抽取
			if (configureStatus == GlobalConstant.CONFIGURE_STATUS_UNINSTALLED && vehicle.getDemolitionTime() == null) {
				vehicle.setDemolitionTime(new Date());
			}
			obdNo = null;//拔除时将OBD设为null
		}
		
		vehicle.setObdNo(obdNo);
		vehicle.setObdFlag(obdFlag);
		vehicle.setConfigureStatus(configureStatus);
		vehicleDao.update(vehicle);
		CacheVehicleManager.putVehicle(vehicleId, vehicle);
	}
	
	/**
	 *【设备自动绑定解绑处理方法】:新装换装时更新申请表信息
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleId
	 */
	void updateProposalByInstallation(Integer vehicleId) {
		//查询有没有【待安装】的新装、换装申请记录
		if (vehicleId != null) {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("vehicleId", vehicleId);
			parameters.put("status", GlobalConstant.PROPOSAL_STATUS_PASSED);
			StringBuffer configureStatusSql = new StringBuffer(" and type in ( " +  GlobalConstant.PROPOSAL_TYPE_INSTALLATION + " , "  + GlobalConstant.PROPOSAL_TYPE_REPLACE + ") ") ;
			configureStatusSql.append(" order by id desc ");
			List<Proposal> proposalList = proposalDao.find(parameters, configureStatusSql.toString());
			if (proposalList != null) {
				Proposal proposal = proposalList.get(0);
				proposal.setStatus(GlobalConstant.PROPOSAL_STATUS_INSTALLED);
				proposalDao.edit(proposal);
			}
		}
	}
	
	/**
	 *【设备自动绑定解绑处理方法】:拆除时更新申请表信息
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleId
	 */
	void updateProposalByDemotion(Integer vehicleId) {
		//查询有没有【待安装】的新装、换装申请记录
		if (vehicleId != null) {
			//查询有没有【待拆除】的拆除申请记录
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("type", GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
			parameters.put("vehicleId", vehicleId);
			parameters.put("status", GlobalConstant.PROPOSAL_STATUS_PASSED);
			List<Proposal> proposalList = proposalDao.find(parameters, "order by id desc");
			if (proposalList != null) {
				Proposal proposal = proposalList.get(0);
				proposal.setStatus(GlobalConstant.PROPOSAL_STATUS_UNINSTALLED);
				proposalDao.edit(proposal);
			}
		}
	}
	
	
	/**
	 *【设备自动绑定解绑处理方法】:插入或更新设备表信息
	 * @param companyId
	 * @param vehicleId
	 * @param uniqueId
	 * @param simId
	 * @param obdFlag 0:解绑 1:绑定
	 * @return
	 */
	Integer updateBox(Integer companyId, Integer vehicleId, String uniqueId, String simId, Integer obdFlag){
		Integer boxId = 0;
		Box box = null;
		if (obdFlag == 1) {
			//查询设备表中的车辆是否绑定其他设备，如果存在则取消绑定关系
			Box currentBox = CacheVehicleManager.getBoxByVehicleId(vehicleId);
			if (currentBox != null && !(currentBox.getUniqueId().equals(uniqueId))) {
				//更新BOX信息
				currentBox.setVehicleId(GlobalConstant.FALSE);
				currentBox.setStatus(GlobalConstant.BOX_STATUS_INACTIVE);
				boxDao.update(currentBox);
				CacheVehicleManager.removeBox(vehicleId);
			}
			box = boxDao.getByUniqueId(uniqueId);
			if (box == null) {
				box = new Box();
			}
			box.setCompanyId(companyId);
			box.setVehicleId(vehicleId);
			box.setUniqueId(uniqueId);
			box.setSimId(simId);
			box.setSimMobile("");
			box.setStatus(GlobalConstant.BOX_STATUS_ACTIVATION);
			box.setCurrentVersion("1.0");
			box.setDeletionFlag(GlobalConstant.FALSE);
			box.setInstallTime(new Date());
			boxId = boxDao.merge(box);
			box.setId(boxId);
			
			if (vehicleId != null) {
				CacheVehicleManager.putBox(vehicleId, box);
			}
			this.addBoxOperation(companyId, vehicleId, boxId, GlobalConstant.PROPOSAL_TYPE_INSTALLATION);//新增绑定日志
		} else if (obdFlag == 0) {
			box = CacheVehicleManager.getBoxByVehicleId(vehicleId);
			if (box != null) {
				boxId = box.getId();
				//更新BOX信息
				box.setVehicleId(GlobalConstant.FALSE);
				box.setStatus(GlobalConstant.BOX_STATUS_INACTIVE);
				boxDao.update(box);
			}
			CacheVehicleManager.removeBox(vehicleId);
			this.addBoxOperation(companyId, vehicleId, boxId, GlobalConstant.PROPOSAL_TYPE_DEMOLITION);//新增解绑日志
		}
		return boxId;
	}
	
	/**
	 *【设备自动绑定解绑处理方法】:插入BOX操作表信息
	 * @param companyId
	 * @param vehicleId
	 * @param boxId
	 * @param type
	 */
	void addBoxOperation(Integer companyId, Integer vehicleId, Integer boxId, Integer type){
		BoxOperation boxOperation = new BoxOperation();
		boxOperation.setCompanyId(companyId);
		boxOperation.setVehicleId(vehicleId);
		boxOperation.setBoxId(boxId);
		boxOperation.setType(type);
		boxOperation.setStatus(GlobalConstant.TRUE);
		boxOperation.setOperationName("admin");
		boxOperation.setOperationTime(new Date());
		boxOperationDao.save(boxOperation);
	}

	
	public void handleDeviceLog(String serialNumber){
		//根据设备号查询出日志，并处理
		Map<String,Object> argMap = new HashMap<String,Object>();
		argMap.put("status",GlobalConstant.LOG_HANDLE_NORMAL);
		argMap.put("logCode",GlobalConstant.LOG_USER_NOT_EXIST);
		argMap.put("serialNumber",serialNumber);
		List<TaskMessageLogVo> logList = reHandleTestDriveDao.findLogMessage(argMap);
		if(logList!=null && !logList.isEmpty()){
			for(TaskMessageLogVo log : logList){
				log.setStatus(GlobalConstant.LOG_HANDLE_END);
				log.setCurtime(new Date());
				reHandleTestDriveDao.updateTaskMessageLogStatus(log);
			}
		}
	}
}
