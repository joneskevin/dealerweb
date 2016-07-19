package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IOperationLogDao;
import com.ava.dao.ITestNoDriveWeekDao;
import com.ava.dao.IVehicleDao;
import com.ava.dao.IViolationDao;
import com.ava.dealer.service.IViolationService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.OperationLog;
import com.ava.domain.entity.TestNoDriveWeek;
import com.ava.domain.entity.User;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.entity.Violation;
import com.ava.domain.vo.TestNoDriveWeekFindVO;
import com.ava.domain.vo.TestNoDriveWeekVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.domain.vo.ViolationFindVO;
import com.ava.domain.vo.ViolationLineTimeDetailVO;
import com.ava.domain.vo.ViolationLineTimeVO;
import com.ava.domain.vo.ViolationVO;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;

@Service
public class ViolationService<T> extends BaseService implements IViolationService {

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IVehicleDao vehicleDao;

	@Autowired
	private ICompanyDao companyDao;

	@Autowired
	private IViolationDao violationDao;
	
	@Autowired
	private ITestNoDriveWeekDao testNoDriveWeekDao;
	
	@Autowired
	private IOperationLogDao operationLogDao;
		
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Integer saveViolation(Violation violation){
		return violationDao.saveViolation(violation);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateViolation(Violation violation){
		violationDao.updateViolation(violation);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void handleNoEndViolation(){
		hibernateDao.executeSQLUpdate(" update t_violation tv, t_test_drive ttd set tv.current_status=1, tv.end_time=ttd.end_time " +
				"where tv.vehicle_id=ttd.vehicle_id and tv.start_time=ttd.start_time and tv.current_status=0 and ttd.current_status=1");
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void handleNoEndViolation(Integer vehicleId,String startTime,String endTime, boolean hasFiling){
		StringBuffer sqlBuffer = new StringBuffer( "update t_violation tv, t_test_drive ttd set tv.current_status=1, tv.end_time=" + endTime);
		if(hasFiling){//存在报备，更新为删除状态
			sqlBuffer.append(", tv.DELETION_FLAG = 1 ");
		}
		sqlBuffer.append(" where tv.vehicle_id=ttd.vehicle_id and tv.start_time=ttd.start_time and tv.current_status=2 and ttd.current_status=1 ");
		sqlBuffer.append("and tv.vehicle_id=");
		sqlBuffer.append(vehicleId);
		sqlBuffer.append(" and tv.start_time=");
		sqlBuffer.append(startTime);
		hibernateDao.executeSQLUpdate(sqlBuffer.toString());
	}
	/**
	 * 获取试驾中的违规  并缓存起来
	 */
	public Violation getViolationByVehicle(Integer vehicleId,Integer type){
		HashMap map=new HashMap();
		map.put("vehicleId", vehicleId);
		map.put("currentStatus", 0);//试驾中
		if(null!=type && type.intValue()>0){
			map.put("typeId", type);//试驾类型
		}
		StringBuffer additionalCondition=new StringBuffer("");
		additionalCondition.append(" order by creationTime desc");
		List<Violation> violationList =violationDao.find(map,additionalCondition.toString());
		if(null!=violationList && !violationList.isEmpty()){
			return violationList.get(0);
		}
		return null;
	}
	
	@Override
	public ResponseData listViolationOld(TransMsg transMsg, Integer companyId,
			Violation violation,boolean isExport) {
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		ResponseData rd = new ResponseData(0);
		//查询违规表
		ArrayList violationList = new ArrayList();
		
		String additionalCondition = "";
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(violation.getDealerCode()==null||violation.getDealerCode().equals(""))
		{
			
		}else{
			 companyIdList =companyDao.getByDealerCodeList(violation.getDealerCode());
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		String companyIdStr="";
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(compangIdSend!=""){
			companyIdStr += " and  companyId in(" +compangIdSend + ")";
			
		}else{
			if (companyId != null || "".equals(companyId)) {
				companyIdStr+= " and  companyId in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
		HashMap parameters0 = new HashMap();
		if (violation.getPlateNumber() != null
				&& violation.getPlateNumber().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters0, "plateNumber",
					violation.getPlateNumber());
		}

		if (violation.getVin() != null && violation.getVin().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(parameters0, "vin",
					violation.getVin());
		}
		additionalCondition = companyIdStr;
		List<Vehicle> vehicleList = vehicleDao.find(parameters0,
				additionalCondition);
		List<String> vehicleIdListNeed= new ArrayList<String>();
		
		if (vehicleList != null && vehicleList.size() > 0){
			for(Vehicle v:vehicleList){
				vehicleIdListNeed.add(v.getId().toString());
			}
	

		if (vehicleList != null && vehicleList.size()>0) {
			additionalCondition = " and vehicleId in(" +TypeConverter.join(vehicleIdListNeed, ",")   + ")";
			additionalCondition = additionalCondition + " order by companyId asc";
			//查询起始时间
			String startTime="";
			String endTime ="";
		
			if(violation.getStartTime()!=null&&violation.getEndTime()!=null)
			{
				// 开始时间 结束时间
				 startTime = DateTime.toNormalDateTime(violation.getStartTime());
				 endTime = DateTime.toNormalDateTime(violation.getEndTime());
				 ParameterUtil.appendTimeConfineQueryCondition(
							transMsg.getParameters(), "creationTime", startTime,
							endTime); 
			}
			
			List<Violation> vioList = violationDao.findByPage(transMsg,
					additionalCondition);

			Vehicle vehicle;
			if(vioList!=null)
			{
				Company com ;
			for (Violation vio : vioList) {
				vehicle = vehicleDao.get(vio.getVehicleId());
				vio.setCarStyleId(vehicle.getCarStyleId());
				vio.setPlateNumber(vehicle.getPlateNumber());
				vio.setVin(vehicle.getVin());
				//获得一些值		
				 com = CacheCompanyManager.getCompany(vio.getCompanyId());
				 if(com!=null)
				 {
//					String fenxiao_name=CacheCompanyManager.getCompany(vio.getCompanyId()).getParentName();
					vio.setFenxiao_center(CacheCompanyManager.getCompany(vio.getCompanyId()).getParentName());
					vio.setIsKeyCity(com.getIsKeyCity());
					vio.setDealerType(com.getDealerType());
					vio.setDealerCode(com.getDealerCode());
					vio.setRegionProvinceId(com.getRegionProvinceId());
					vio.setRegionCityId(com.getRegionCityId());
					// new add
					vio.setCompanyName(com.getName());
					vio.nick();
					violationList.add(vio);
				 }
				}
			}
		}	
		}
		rd.setFirstItem(violationList);
		rd.setCode(1);
		return rd;
	}
	
	@Override
	public ResponseData listViolation(TransMsg transMsg, Integer companyId, Violation violation,boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" SELECT o.PARENT_ID as parentId, c.REGION_PROVINCE_ID as regionProvinceId, c.REGION_CITY_ID as regionCityId, ");
		exeSql.append(" c.CN_NAME as cnName, c.DEALER_CODE as dealerCode, c.IS_KEY_CITY as isKeyCity, c.DEALER_TYPE as dealerType, ");
		exeSql.append(" v.PLATE_NUMBER as plateNumber, v.CAR_STYLE_ID as carStyleId, v.VIN as vin, ");
		exeSql.append(" tv.ID as violationId, tv.TYPE as typeId, tv.CREATION_TIME as creationTime, tv.COUNT as countId ");
		exeSql.append(" FROM t_violation tv ");
		exeSql.append(" INNER JOIN t_org o on o.ID = tv.COMPANY_ID ");
		exeSql.append(" INNER JOIN t_company c on c.ORG_ID = tv.COMPANY_ID ");
		exeSql.append(" LEFT JOIN t_vehicle v on v.ID = tv.VEHICLE_ID ");
		exeSql.append(" WHERE 1 = 1 ");
		
		if (companyId != null) {
			exeSql.append(" AND tv.COMPANY_ID IN (" + CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ") ");
		}
		
		if (violation.getVin() != null && violation.getVin().trim().length() > 0) {
			exeSql.append(" AND v.VIN LIKE '%" + violation.getVin() + "%' ");
		}

		if (violation.getPlateNumber() != null && violation.getPlateNumber().trim().length() > 0) {
			exeSql.append(" AND v.PLATE_NUMBER LIKE '%" + violation.getPlateNumber() + "%' ");
		}
		
		if (violation.getDealerCode() != null && violation.getDealerCode().trim().length() > 0) {
			exeSql.append(" AND c.DEALER_CODE LIKE '%" + violation.getDealerCode() + "%' ");
		}
		
		if(violation.getStartTime() != null && violation.getEndTime() != null){
			// 开始时间 结束时间
			String startTime = DateTime.toNormalDateTime(violation.getStartTime());
			String endTime = DateTime.toNormalDateTime(violation.getEndTime());
			exeSql.append(" AND tv.CREATION_TIME BETWEEN ' " + startTime + " ' " + " AND ' " +  endTime + " ' ");
		}
		
		exeSql.append(" ORDER BY o.PARENT_ID, c.DEALER_CODE, tv.ID DESC ");
		
		List<ViolationFindVO> violationFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), ViolationFindVO.class);
		//总条数
		Integer total = 0;
		if (violationFindVOList != null && violationFindVOList.size() > 0) {
			total = violationFindVOList.size();
		}
		transMsg.setTotalRecords(TypeConverter.toLong(total));
		
		//分页
		if (transMsg.getStartIndex() == null) {
			transMsg.setStartIndex(0);
		}
		exeSql.append(" limit "+ transMsg.getStartIndex()
			          + "," + (transMsg.getPageSize()) + " ");
		
		violationFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), ViolationFindVO.class);
		ArrayList<Violation> violationList = new ArrayList<Violation>();
		if (violationFindVOList != null && violationFindVOList.size() > 0) {
			for (ViolationFindVO violationFindVO : violationFindVOList) {
				 Violation currentViolation = new Violation(violationFindVO);
				 currentViolation.setCompanyName(violationFindVO.getCnName());
				 if (violationFindVO.getParentId() != null) {
					 currentViolation.setFenxiao_center(CacheOrgManager.getOrgName(violationFindVO.getParentId()));
				 }
				 violationList.add(currentViolation);
			}
		}
		
		rd.setFirstItem(violationList);
		rd.setCode(1);
		return rd;
	}
	
	
	@Override
	public ResponseData listViolationForWeek(TransMsg transMsg ,Integer companyId,
			Violation violation,boolean isExport) {
		ResponseData rd = new ResponseData(0);
		//取出全部的list
		List listAllData;
		StringBuffer exeSql=new StringBuffer("");
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}		
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(violation.getDealerCode()==null||violation.getDealerCode().equals("")) {
//			return rd;
		} else {
		 companyIdList =companyDao.getByDealerCodeList(violation.getDealerCode());
		 //拿到该companyId下面所有的经销商ID
		 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
		 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
		 if(companyIdList==null) {
				rd.setMessage("没有该经销商!");
				return rd;
			} else {
				compangIdSend=  TypeConverter.join(companyIdList, ",") ;
			}
		}
		
		String companyIdStr = "";
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if (compangIdSend != "") {
			companyIdStr += " and  m.COMPANY_ID in(" +compangIdSend + ")";
		} else {
			if (companyId != null || "".equals(companyId)) {
				companyIdStr += " and  m.COMPANY_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
		exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode,  " +
					  "c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId," +
					  "c. CN_NAME, n.PLATE_NUMBER plateNumber ,n.CAR_STYLE_ID carStyleId ,n.vin vin," +
					  "m.TYPE typeId ,m.CREATION_TIME creationTime , sum(m.count) sumNum" +
					  " ,m.VEHICLE_ID vehicleId , m.DRIVE_LINE_ID driveLineId , m.COMPANY_ID,m.START_TIME,m.END_TIME " +
					  " from t_violation m , t_vehicle n ,t_company c, t_org o "+
					  "where m.VEHICLE_ID =n.id  and m.COMPANY_ID=c.ORG_ID and m.COMPANY_ID = o.ID ");
		//companyId
		exeSql.append(companyIdStr);
		
		//时间段查询
		String startTime= "";
		String endTime= "";

		String[] days = DateTime.getRangeOfWeek(TypeConverter.toInteger(violation
				.getWeek())); 
		Date end  = DateTime.addDays(DateTime.toDate(days[1]),1);
		
		startTime = DateTime.toNormalDate(DateTime.toDate(days[0]));
		endTime = DateTime.toNormalDate(end);

		if(violation.getWeek()!=null )
		{
			exeSql.append(" and m.CREATION_TIME>\""+ startTime +"\" and  m.CREATION_TIME<\""+  endTime+"\"");
		}
	
		if(violation.getPlateNumber()!=null && violation.getPlateNumber()!="")
		{
			exeSql.append(" and n.PLATE_NUMBER  like \"%"+  TypeConverter.toString(violation.getPlateNumber()).trim() +"%"+"\"");
		}
		if(violation.getVin()!=null && violation.getVin()!="")
		{
			exeSql.append(" and  n.vin like \"%" +TypeConverter.toString(violation.getVin()).trim()+"%"+"\"");
		}		
		exeSql.append(" group by m.VEHICLE_ID ,m.DRIVE_LINE_ID ");
		exeSql.append(" order by o.PARENT_ID, c.DEALER_CODE, m.ID desc ");
		
		//计算总个数
		listAllData = violationDao.queryByWeek(exeSql.toString());
		List<ViolationVO> listAll = parseViolationVOList(listAllData);
		rd.put("listAll",listAll);
		transMsg.setTotalRecords(TypeConverter.toLong(listAll.size()));
		exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
			
		System.out.println(exeSql);
		
		//	取出供某一页显示的list
		List list = violationDao.queryByWeek(exeSql.toString());
		List<ViolationVO> result = parseViolationVOList(list);
		
		rd.put("result",result);
		return rd ;
	}
	
	@Override
	public ResponseData listViolationForSeason(TransMsg transMsg,
			Integer companyId, Violation violation,boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		StringBuffer exeSql=new StringBuffer("");
		String companyIdStr="";
		//取出全部的list
		List listAllData;
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(violation.getDealerCode()==null||violation.getDealerCode().equals(""))
		{
//			return rd;
		}else{
			 companyIdList =companyDao.getByDealerCodeList(violation.getDealerCode());
			 //拿到该companyId下面所有的经销商ID
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(compangIdSend!=""){
			companyIdStr += " and  m.COMPANY_ID in(" +compangIdSend + ")";
		}else{
			if (companyId != null) {
				companyIdStr += " and  m.COMPANY_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
			exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode,  " +
					"c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId, " +
					"c. CN_NAME, n.PLATE_NUMBER plateNumber ,n.CAR_STYLE_ID carStyleId ,n.vin vin," +
					"m.TYPE typeId ,m.CREATION_TIME creationTime , sum(m.count) sumNum" +
					" ,m.VEHICLE_ID vehicleId , m.DRIVE_LINE_ID driveLineId , m.COMPANY_ID, m.START_TIME,m.END_TIME " +
					" from t_violation m , t_vehicle n ,t_company c, t_org o "+
					"where m.VEHICLE_ID =n.id  and m.COMPANY_ID=c.ORG_ID and m.COMPANY_ID = o.ID ");
			//companyId
			exeSql.append(companyIdStr);
			
			//时间段查询
			String startTime= "";
			String endTime= "";
			
			Integer nextYear =violation.getYear()+1;
			if (violation.getSeason() == 1) {
				startTime = violation.getYear() + "-01-01";
				endTime = violation.getYear() + "-04-01";
			} else if (violation.getSeason() == 2) {
				startTime = violation.getYear() + "-04-01";
				endTime = violation.getYear() + "-07-01";
			} else if (violation.getSeason() == 3) {
				startTime = violation.getYear() + "-07-01";
				endTime = violation.getYear() + "-10-01";
			} else if (violation.getSeason() == 4) {
				startTime = violation.getYear() + "-10-01";
				endTime = nextYear + "-01-01";
			}
			
			if(violation.getSeason()!=null )
			{
				exeSql.append(" and m.CREATION_TIME>\""+ startTime +"\" and  m.CREATION_TIME<\""+  endTime+"\"");
			}
			
			if(violation.getPlateNumber()!=null && violation.getPlateNumber()!="")
			{
				exeSql.append(" and n.PLATE_NUMBER  like \"%"+  TypeConverter.toString(violation.getPlateNumber()).trim() +"%"+"\"");
			}
			if(violation.getVin()!=null && violation.getVin()!="")
			{
				exeSql.append(" and  n.vin like \"%" +TypeConverter.toString(violation.getVin()).trim()+"%"+"\"");
			}	
			exeSql.append(" group by m.VEHICLE_ID  ,m.DRIVE_LINE_ID ");
			exeSql.append(" order by o.PARENT_ID, c.DEALER_CODE, m.ID desc ");
			//计算总个数
			listAllData = violationDao.queryByWeek(exeSql.toString());
			List<ViolationVO> listAll = parseViolationVOList(listAllData);
			rd.put("listAll",listAll);
			transMsg.setTotalRecords(TypeConverter.toLong(listAll.size()));
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
//		}
		//取出供某一页显示的list
		List list = violationDao.queryByWeek(exeSql.toString());
		List<ViolationVO> result = parseViolationVOList(list);
		
		rd.put("result",result);
		return rd;
	} 
	
	@Override
	public ResponseData listViolationForMonth( TransMsg transMsg ,Integer companyId,
			Violation violation,boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		StringBuffer exeSql=new StringBuffer("");
		String companyIdStr="";
		//取出全部的list
		List listAllData;
		
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(violation.getDealerCode()==null||violation.getDealerCode().equals(""))
		{
//					return rd;
		}else{
			 companyIdList =companyDao.getByDealerCodeList(violation.getDealerCode());
			 //拿到该companyId下面所有的经销商ID
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
				
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(compangIdSend!=""){
			companyIdStr += " and  m.COMPANY_ID in(" +compangIdSend + ")";
		}else{
			if (companyId != null) {
				companyIdStr += " and  m.COMPANY_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
	
			exeSql.append("select c.IS_KEY_CITY isKeyCity, c.DEALER_TYPE dealerType, c.DEALER_CODE dealerCode,  " +
					"c.REGION_PROVINCE_ID regionProvinceId,c.REGION_CITY_ID regionCityId," +
					"c. CN_NAME,n.PLATE_NUMBER plateNumber ,n.CAR_STYLE_ID carStyleId ,n.vin vin," +
					"m.TYPE typeId ,m.CREATION_TIME creationTime , sum(m.count) sumNum" +
					" ,m.VEHICLE_ID vehicleId , m.DRIVE_LINE_ID driveLineId , m.COMPANY_ID, m.START_TIME,m.END_TIME" +
					" from t_violation m , t_vehicle n ,t_company c, t_org o "+
					"where m.VEHICLE_ID =n.id  and m.COMPANY_ID=c.ORG_ID and m.COMPANY_ID = o.ID ");
			//companyId
			exeSql.append(companyIdStr);
			
			//时间段查询
			String startTime= "";
			String endTime= "";
			//处理单月统计
			
			//得到选中月份的第一天
			String date = violation.getYear().toString()+violation.getMonth()+"01";  //toDate
			Date dateFirst = DateTime.toDate(date);
			
			String[] days= DateTime.getRangeOfMonth(dateFirst);
			Date end  = DateTime.addDays(DateTime.toDate(days[1]),1);
			
			startTime = DateTime.toNormalDate(DateTime.toDate(days[0]));
			endTime = DateTime.toNormalDate(end);

			if(violation.getPlateNumber()!=null && violation.getPlateNumber()!="")
			{
				exeSql.append(" and n.PLATE_NUMBER  like \"%"+  TypeConverter.toString(violation.getPlateNumber()).trim() +"%"+"\"");
			}
			if(violation.getVin()!=null && violation.getVin()!="")
			{
				exeSql.append(" and  n.vin like \"%" +TypeConverter.toString(violation.getVin()).trim()+"%"+"\"");
			}	
		
			if(violation.getMonth()!=null )
			{
				exeSql.append(" and m.CREATION_TIME>\""+ startTime +"\" and  m.CREATION_TIME<\""+  endTime+"\"");
			}
			
			exeSql.append(" group by m.VEHICLE_ID  ,m.DRIVE_LINE_ID ");
			exeSql.append(" order by o.PARENT_ID, c.DEALER_CODE, m.ID desc ");
			//计算总个数
			listAllData = violationDao.queryByWeek(exeSql.toString());
			List<ViolationVO> listAll = parseViolationVOList(listAllData);
			rd.put("listAll",listAll);
			transMsg.setTotalRecords(TypeConverter.toLong(listAll.size()));
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
//		}
		//取出供某一页显示的list
		List list = violationDao.queryByWeek(exeSql.toString());
		List<ViolationVO> result = parseViolationVOList(list);
		
		rd.put("result",result);
		return rd;
	}
	@Override
	public ResponseData listViolationForIntevel(TransMsg transMsg ,Integer companyId,
			Violation violation,boolean isExport){
		ResponseData rd = new ResponseData(0);
		//取出全部的list
		List listAllData;
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		StringBuffer exeSql=new StringBuffer("");
		String companyIdStr="";
		//如果填网络代码 会可能查询得到一个经销商list:companyIdList
		Collection companyIdList =new ArrayList() ;
		String compangIdSend ="";
		if(violation.getDealerCode()==null||violation.getDealerCode().equals(""))
		{
//				return rd;
		}else{
			 companyIdList =companyDao.getByDealerCodeList(violation.getDealerCode());
			 //拿到该companyId下面所有的经销商ID
			 List allList = CacheOrgManager.getChildrenOrgIdsList(companyId);
			 companyIdList = TypeConverter.pksAndpks(companyIdList, allList) ;
//			 companyIdList =getRightCompanyIdList(companyIdList, allList) ;
			
			 if(companyIdList==null)
				{
					rd.setMessage("没有该经销商!");
					return rd;
				} else	{
					compangIdSend=  TypeConverter.join(companyIdList, ",") ;
				}
		}
		//如果传来网络形态（网络代码）查询就按这个经销商的companyId 否则就按当前登陆者的下属经销商集合
		if(compangIdSend!=""){
			companyIdStr += " and  m.COMPANY_ID in(" +compangIdSend + ")";
		}else{
			if (companyId != null) {
				companyIdStr += " and  m.COMPANY_ID in(" +  CacheOrgManager.getChildrenOrgIdsStr(companyId, GlobalConstant.TRUE) + ")";
			}
		}
		
		exeSql.append("select c.IS_KEY_CITY , c.DEALER_TYPE , c.DEALER_CODE ,  " +
				"c.REGION_PROVINCE_ID ,c.REGION_CITY_ID ," +
				"c. CN_NAME,n.PLATE_NUMBER  ,n.CAR_STYLE_ID  ,n.vin ," +
				"m.TYPE  ,m.CREATION_TIME  , sum(m.count) " +
				" ,m.VEHICLE_ID  , m.DRIVE_LINE_ID  ,m.COMPANY_ID, m.START_TIME,m.END_TIME " +
				" from t_violation m , t_vehicle n ,t_company c, t_org o "+
				"where m.VEHICLE_ID =n.id  and m.COMPANY_ID=c.ORG_ID and m.COMPANY_ID = o.ID ");
		
			exeSql.append(companyIdStr);
			
			//时间段查询
			String startTime= "";
			String endTime= "";
			if(violation.getStartTime()!=null&&violation.getEndTime()!=null)
			{
				startTime =DateTime.toNormalDateTime(violation.getStartTime()) ;
				endTime =DateTime.toNormalDateTime(violation.getEndTime()) ;
				exeSql.append(" and m.CREATION_TIME between date('"+ startTime
						+"') and  date('"+  endTime
						+"')"
						);
			}
			if(violation.getPlateNumber()!=null && violation.getPlateNumber()!="")
			{
				exeSql.append(" and n.PLATE_NUMBER  like \"%"+  TypeConverter.toString(violation.getPlateNumber()).trim() +"%"+"\"");
			}
			if(violation.getVin()!=null && violation.getVin()!="")
			{
				exeSql.append(" and  n.vin like \"%" +TypeConverter.toString(violation.getVin()).trim()+"%"+"\"");
			}
			
			exeSql.append(" group by m.VEHICLE_ID  ,m.DRIVE_LINE_ID");
			exeSql.append(" order by o.PARENT_ID, c.DEALER_CODE, m.ID desc ");
			//计算总个数
			listAllData = violationDao.queryByWeek(exeSql.toString());
			List<ViolationVO> listAll = parseViolationVOList(listAllData);
			rd.put("listAll",listAll);
			
			transMsg.setTotalRecords(TypeConverter.toLong(listAll.size()));
			exeSql.append(" limit "+(transMsg.getStartIndex()==null?0:transMsg.getStartIndex())+","+ transMsg.getPageSize()+"");
//		}
		System.out.print(exeSql);
		
		

		//	取出供某一页显示的list
		List list = violationDao.queryByWeek(exeSql.toString());
		List<ViolationVO> result = parseViolationVOList(list);
		
		rd.put("result",result);

		return rd;
	}
	public List parseViolationVOList(List list ) {
		List<ViolationVO> vioList= new ArrayList<ViolationVO> ();
		ViolationVO tmp =new ViolationVO();
		Company com ;
		if(list!=null)
		{
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				Object[] obj=(Object[]) iterator.next();
				tmp.setIsKeyCity( TypeConverter.toInteger(obj[0]));
				tmp.setDealerType(TypeConverter.toInteger(obj[1]));
				tmp.setDealerCode(TypeConverter.toInteger(obj[2]));
				tmp.setRegionProvinceId(TypeConverter.toInteger(obj[3]));
				tmp.setRegionCityId(TypeConverter.toInteger(obj[4]));
				tmp.setCompanyName(TypeConverter.toString(obj[5]));
				tmp.setPlateNumber(TypeConverter.toString(obj[6]));
				
				tmp.setCarStyleId(TypeConverter.toInteger(obj[7]));
				tmp.setVin(TypeConverter.toString(obj[8]));
				tmp.setTypeId(TypeConverter.toInteger(obj[9]));
//				tmp.setCreationTime(TypeConverter.toInteger(obj[9])); TODO
				tmp.setSumNum(TypeConverter.toInteger(obj[11]));
				tmp.setVehicleId(TypeConverter.toInteger(obj[12]));
				tmp.setDriveLineId(TypeConverter.toInteger(obj[13]));
				tmp.setCompanyId(TypeConverter.toInteger(obj[14]));
				tmp.setStart_Time(obj[15]==null? null :DateTime.toDate(obj[15].toString()));
				tmp.setEnd_Time(obj[15]==null? null :DateTime.toDate(obj[16].toString()));
				
				com = CacheCompanyManager.getCompany(tmp.getCompanyId());
				if(com!=null)
				{
					tmp.setFenxiao_center(com.getParentName());
				}
				tmp.nick();
				vioList.add(tmp);
				tmp = new ViolationVO();
			}
			return vioList;
			
		}
		return null;
	}
	@Override
	public ResponseData listDetail(TransMsg transMsg, Integer vehicleId, Integer driveLineId,
			String beginTime, String endTime,boolean isExport){ 
		ResponseData rd =new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		ParameterUtil.appendTimeConfineQueryCondition(
				transMsg.getParameters(), "creationTime", beginTime.trim(),
				endTime.trim());
		transMsg.getParameters().put("driveLineId",driveLineId);
		transMsg.getParameters().put("vehicleId",vehicleId);
	
		//单页显示用的list
		List<Violation> vioList = violationDao.findByPage(transMsg,
				null);
		rd.put("vioList", vioList);
		
		HashMap parameters1 = new HashMap();
		ParameterUtil.appendTimeConfineQueryCondition(
				parameters1, "creationTime", beginTime,
				endTime);	  
		parameters1.put("driveLineId",driveLineId);
		parameters1.put("vehicleId",vehicleId);
		//全部list
		List allList=violationDao.find(parameters1, null);
		rd.put("allList", allList);
		
		return rd ;
	}

	@Override
	public List<ViolationLineTimeVO> listViolation(TransMsg transMsg, Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,Integer violationType,
			String vin,String plateNumber, String dealerCode, String violationYear,
			String violationWeek, String violationMonth, String violationQuarter,boolean isExport) {
		String times[]=checkTime(violationYear,violationWeek, violationMonth, violationQuarter);
		StringBuffer queryList=new StringBuffer("");
		StringBuffer queryCount=new StringBuffer("");
		queryList.append("select vdi.saleCenterId, vdi.saleCenterName, vdi.provinceName, vdi.cityName, vdi.dealerTypeNick, vdi.isKeyCityNick,");
		queryList.append("vdi.dealerCode, vdi.companyName, vdi.parentDealerCode,vdi.parentCompanyName, vvs.vehicleId, vvs.plateNumber,vvs.vehicleStyle,vvs.vin,vio.sumNum ");
		queryList.append("from view_dealer_info vdi,view_vehicle_style vvs,");
		queryList.append("(select tv.vehicle_id as vehicleId, count(tv.vehicle_id) as sumNum from t_violation tv where tv.type=").append(violationType).append(" ");
		queryList.append(" and tv.deletion_flag = 0 and tv.current_status = 1 ");
		queryCount.append("select count(1) from view_dealer_info vdi,view_vehicle_style vvs, ");
		queryCount.append("(select tv.vehicle_id as vehicleId from t_violation tv  where tv.type=").append(violationType).append(" ");
		queryCount.append(" and tv.deletion_flag = 0 and tv.current_status = 1 ");
		
		if(null!=times && times.length==2){
			queryList.append("and tv.creation_time between date_format('").append(times[0]).append(" 00:00:00").append("','%Y-%m-%d %T') ");
			queryList.append("and date_format('").append(times[1]).append(" 23:59:59").append("','%Y-%m-%d %T')");
			queryCount.append("and tv.creation_time between date_format('").append(times[0]).append(" 00:00:00").append("','%Y-%m-%d %T') ");
			queryCount.append("and date_format('").append(times[1]).append(" 23:59:59").append("','%Y-%m-%d %T')");
		}
		queryList.append(" group by tv.vehicle_id ) vio where vdi.companyId=vvs.companyId and vvs.vehicleId=vio.vehicleId  ");
		queryCount.append(" group by tv.vehicle_id ) vio where vdi.companyId=vvs.companyId and vvs.vehicleId=vio.vehicleId  ");
		if(null!=dealerCode && dealerCode.trim().length()>=1){
			queryList.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
			queryCount.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
		}
		
		if (!ValidatorUtil.isBlankOrNull(vin)) {
			queryList.append(" and vvs.vin like '%").append(vin.trim()).append("%' ");
			queryCount.append(" and vvs.vin like '%").append(vin.trim()).append("%' ");
		}
		
		if (!ValidatorUtil.isBlankOrNull(plateNumber)) {
			queryList.append(" and vvs.plateNumber like '%").append(plateNumber.trim()).append("%' ");
			queryCount.append(" and vvs.plateNumber like '%").append(plateNumber.trim()).append("%' ");
		}
		if(null!=selectSaleCenterId && selectSaleCenterId.intValue()!=Integer.parseInt(GlobalConstant.NETWORK_DEVELOPMENT_ID) ){
			queryList.append(" and vdi.saleCenterId="+selectSaleCenterId);
			queryCount.append(" and vdi.saleCenterId="+selectSaleCenterId);
		}
		queryList.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryCount.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryList.append(" order by vdi.saleCenterId, vdi.dealerCode");
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		//设置分页
		queryList.append(getPaginationSql(transMsg, queryCount, isExport));
		
		return violationDao.exeSqlQueryList(queryList.toString(),ViolationLineTimeVO.class);
	}
	
	@Override
	public List<TestNoDriveWeekVO> listTestNoDriveWeek(TransMsg transMsg, Integer currentCompanyId, Integer currentUserId,Integer selectSaleCenterId,String vin,String plateNumber, String dealerCode, String violationYear,
			String violationWeek, String violationMonth, String violationQuarter,boolean isExport) {
		String times[]=checkTime(violationYear,violationWeek, violationMonth, violationQuarter);
		StringBuffer queryList=new StringBuffer("");
		StringBuffer queryCount=new StringBuffer("");
		queryList.append("select vdi.saleCenterId, vdi.saleCenterName, vdi.provinceName, vdi.cityName, vdi.dealerTypeNick, vdi.isKeyCityNick,");
		queryList.append("vdi.dealerCode, vdi.companyName, vdi.parentDealerCode,vdi.parentCompanyName, vvs.vehicleId, vvs.plateNumber,vvs.vehicleStyle,vvs.vin,vio.sumNum ");
		queryList.append("from view_dealer_info vdi,view_vehicle_style vvs,");
		queryList.append("(select tv.vehicle_id as vehicleId, count(tv.vehicle_id) as sumNum from t_test_no_drive_week tv  where 1=1 ");
		queryCount.append("select count(1) from view_dealer_info vdi,view_vehicle_style vvs, ");
		queryCount.append("(select tv.vehicle_id as vehicleId, count(tv.vehicle_id) as sumNum from t_test_no_drive_week tv  where 1=1 ");
		if( StringUtils.isNotEmpty(violationYear)){
			queryList.append("and tv.year = ").append(violationYear).append(" ");
			queryList.append("and tv.year = ").append(violationYear).append(" ");
		}
		if( StringUtils.isNotEmpty(violationWeek)){
			queryList.append("and tv.week = ").append(violationWeek).append(" ");
			queryList.append("and tv.week = ").append(violationWeek).append(" ");
		}
		if( StringUtils.isEmpty(violationYear) && StringUtils.isEmpty(violationWeek)){
			if(null!=times && times.length==2){
				queryList.append("and tv.create_time between date_format('").append(times[0]).append(" 00:00:00").append("','%Y-%m-%d %T') ");
				queryList.append("and date_format('").append(times[1]).append(" 23:59:59").append("','%Y-%m-%d %T')");
				queryCount.append("and tv.create_time between date_format('").append(times[0]).append(" 00:00:00").append("','%Y-%m-%d %T') ");
				queryCount.append("and date_format('").append(times[1]).append(" 23:59:59").append("','%Y-%m-%d %T')");
			}
		}
		queryList.append(" group by tv.vehicle_id ) vio where vdi.companyId=vvs.companyId and vvs.vehicleId=vio.vehicleId  ");
		queryCount.append(" group by tv.vehicle_id ) vio where vdi.companyId=vvs.companyId and vvs.vehicleId=vio.vehicleId  ");
		if(null!=dealerCode && dealerCode.trim().length()>=1){
			queryList.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
			queryCount.append(" and vdi.dealerCode like '%").append(dealerCode).append("%' ");
		}
		
		if (!ValidatorUtil.isBlankOrNull(vin)) {
			queryList.append(" and vvs.vin like '%").append(vin.trim()).append("%' ");
			queryCount.append(" and vvs.vin like '%").append(vin.trim()).append("%' ");
		}
		
		if (!ValidatorUtil.isBlankOrNull(plateNumber)) {
			queryList.append(" and vvs.plateNumber like '%").append(plateNumber.trim()).append("%' ");
			queryCount.append(" and vvs.plateNumber like '%").append(plateNumber.trim()).append("%' ");
		}
		if(null!=selectSaleCenterId && selectSaleCenterId.intValue()!=Integer.parseInt(GlobalConstant.NETWORK_DEVELOPMENT_ID) ){
			queryList.append(" and vdi.saleCenterId="+selectSaleCenterId);
			queryCount.append(" and vdi.saleCenterId="+selectSaleCenterId);
		}
		queryList.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryCount.append(getAccessSql(currentCompanyId,currentUserId).toString());
		queryList.append(" order by vdi.saleCenterId, vdi.dealerCode");
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		//设置分页
		queryList.append(getPaginationSql(transMsg, queryCount, isExport));
		
		return violationDao.exeSqlQueryList(queryList.toString(),TestNoDriveWeekVO.class);
	}
	
	@Override
	public List<ViolationLineTimeDetailVO> viewViolationDetail(TransMsg transMsg, Integer vehicleId,Integer violationType,String violationYear,String violationWeek, String violationMonth, String violationQuarter,boolean isExport) {
		String times[]=checkTime(violationYear,violationWeek, violationMonth, violationQuarter);
		StringBuffer queryList=new StringBuffer("");
		StringBuffer queryCount=new StringBuffer("");
		queryList.append("select vdi.saleCenterId, vdi.saleCenterName, vdi.provinceName, vdi.cityName, vdi.dealerTypeNick, vdi.isKeyCityNick,vdi.dealerCode, vdi.companyName, vdi.parentDealerCode,");
		queryList.append("vdi.parentCompanyName, vvs.vehicleId, vvs.plateNumber,vvs.vehicleStyle,vvs.vin,tv.drive_line_id as driveLineId,tv.creation_time as creationTime, start_time as startTime,tv.end_time as endTime ");
		queryList.append("from view_dealer_info vdi,view_vehicle_style vvs,t_violation tv ");
		queryList.append("where tv.deletion_flag = 0 and tv.current_status = 1 and tv.type=").append(violationType).append(" and tv.vehicle_id=").append(vehicleId).append(" ");
		queryCount.append("select count(1) from view_dealer_info vdi,view_vehicle_style vvs, t_violation tv ");
		queryCount.append("where tv.deletion_flag = 0 and tv.current_status = 1 and tv.type=").append(violationType).append(" and tv.vehicle_id=").append(vehicleId).append(" ");
		
		if(null!=times && times.length==2){
			queryList.append("and tv.creation_time between date_format('").append(times[0]).append(" 00:00:00").append("','%Y-%m-%d %T')");
			queryList.append("and date_format('").append(times[1]).append(" 23:59:59").append("','%Y-%m-%d %T')");
			queryCount.append("and tv.creation_time between date_format('").append(times[0]).append(" 00:00:00").append("','%Y-%m-%d %T')");
			queryCount.append("and date_format('").append(times[1]).append(" 23:59:59").append("','%Y-%m-%d %T')");
		}
		queryList.append("  and vdi.companyId=vvs.companyId and vvs.vehicleId=tv.vehicle_id  ");
		queryCount.append(" and vdi.companyId=vvs.companyId and vvs.vehicleId=tv.vehicle_id  ");

		queryList.append(" order by vdi.saleCenterId, vdi.dealerCode");
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		//设置分页
		queryList.append(getPaginationSql(transMsg, queryCount, isExport));
		
		return violationDao.exeSqlQueryList(queryList.toString(),ViolationLineTimeDetailVO.class);
	}
	public String[] checkTime(String violationYear,String violationWeek, String violationMonth, String violationQuarter){
		
		if(ValidatorUtil.isBlankOrNull(violationYear)){
			violationYear=DateTime.getYear();
		}
		if(violationYear.length()!=4 && !ValidatorUtil.validateNumber(violationYear)){
			throw new ProtocolParseBusinessException("msgResultContent","请输入有效年份（例如1970）");
		}
		
		int timeType=0;
		if(!ValidatorUtil.isBlankOrNull(violationWeek)){
			timeType=1;
		}else if(!ValidatorUtil.isBlankOrNull(violationMonth)){
			timeType=2;
		}else if(!ValidatorUtil.isBlankOrNull(violationQuarter)){
			timeType=3;
		}
		
		if((timeType==1 || timeType==2 || timeType==3) && ValidatorUtil.isBlankOrNull(violationYear) ){
			throw new ProtocolParseBusinessException("msgResultContent","请输入有效年份（例如1970）");
		}else{
			if(timeType==0){
				if(!ValidatorUtil.isBlankOrNull(violationYear)){
					String[] dateRange = new String[2];
					dateRange[0]=Integer.parseInt(violationYear)+"-01-01";
					dateRange[1]=Integer.parseInt(violationYear)+"-12-31";
					return dateRange;
				}
			}else if(timeType==1){
				if(!ValidatorUtil.validateNumber(violationWeek) || ((Integer.parseInt(violationWeek)>=55 || Integer.parseInt(violationWeek)<=0))){
					throw new ProtocolParseBusinessException("msgResultContent","请输入有效周（1-54）");
				}
				return DateTime.getRangeOfWeek(violationYear,violationWeek);
			}else if(timeType==2){
				if(!ValidatorUtil.validateNumber(violationMonth) || ((Integer.parseInt(violationMonth)>=13 || Integer.parseInt(violationMonth)<=0))){
					throw new ProtocolParseBusinessException("msgResultContent","请输入有效月（1-12）");
				}
				return DateTime.getRangeOfMonth(Integer.parseInt(violationYear),Integer.parseInt(violationMonth));
			}else if(timeType==3){
				if(!ValidatorUtil.validateNumber(violationQuarter) || ((Integer.parseInt(violationQuarter)>=5 || Integer.parseInt(violationQuarter)<=0))){
					throw new ProtocolParseBusinessException("msgResultContent","请输入有效季（1-4）");
				}
				return DateTime.getRangeOfQuarter(Integer.parseInt(violationYear),Integer.parseInt(violationQuarter));
			}
		}
		return null;
	}
	
	/**
	 * 获取试驾中的违规  并缓存起来
	 */
	public Violation getViolationByVehicle(Integer vehicleId,Integer type,Date startTime){
		HashMap map=new HashMap();
		map.put("vehicleId", vehicleId);
		if(null!=type && type.intValue()>0){
			map.put("typeId", type);//试驾类型
		}
		map.put("start_Time", startTime);
		StringBuffer additionalCondition=new StringBuffer("");
		//additionalCondition.append(" and start_Time=date('").append(startTime).append("') ");
		additionalCondition.append(" order by creationTime desc");
		List<Violation> violationList =violationDao.find(map,additionalCondition.toString());
		if(null!=violationList && !violationList.isEmpty()){
			return violationList.get(0);
		}
		return null;
	}

	@Override
	public ResponseData listNoTestDetail(TransMsg transMsg, Integer currentCompanyId, Integer currentUserId, ViolationVO violationVO, boolean isExport) {
		ResponseData rd = new ResponseData(1);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" dndw.id, dndw.YEAR AS year, dndw.MONTH AS month, dndw.WEEK AS week, ");
		exeSql.append(" DATE_SUB(dndw.CREATE_TIME, INTERVAL 6 DAY) AS startTime, dndw.CREATE_TIME AS endTime ");
		exeSql.append(" FROM t_test_no_drive_week dndw ");
		exeCountSql.append(" FROM t_test_no_drive_week dndw ");
		
		exeSql.append(" INNER JOIN t_vehicle v ON v.ID = dndw.VEHICLE_ID ");
		exeCountSql.append(" INNER JOIN t_vehicle v ON v.ID = dndw.VEHICLE_ID ");
		//关联车型
		exeSql.append(getVehicleJoinCarStyleSql());
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = dndw.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = dndw.COMPANY_ID ");
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		if (violationVO.getWeek() != null && violationVO.getWeek() > 0 ) {
			exeSql.append(" AND dndw.YEAR = " + DateTime.getYear()).append(" AND dndw.WEEK = ").append(violationVO.getWeek());
			exeCountSql.append(" AND dndw.YEAR = " + DateTime.getYear()).append(" AND dndw.WEEK = ").append(violationVO.getWeek());
		}
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, currentUserId));
		exeCountSql.append(getAccessSql(currentCompanyId, currentUserId));
		VehicleVO vehicleVO = violationVO.getVehicleVO();
		//添加车辆查询条件
		exeSql.append(getVehicleConditionSql(vehicleVO));
		exeCountSql.append(getVehicleConditionSql(vehicleVO));
		if (vehicleVO != null) {
			//添加经销商查询条件
			exeSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
			exeCountSql.append(getDealerConditionSql(vehicleVO.getDealer(), currentUserId));
		}
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , dndw.YEAR, dndw.WEEK DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<TestNoDriveWeekFindVO> testNoDriveWeekFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), TestNoDriveWeekFindVO.class);
		rd.setFirstItem(testNoDriveWeekFindVOList);
		return rd; 
	}

	@Override
	public ResponseData deleteTestNoDriveWeek(Integer testNoDriveWeekId, Integer userId) {
		ResponseData rd = new ResponseData(0);
		TestNoDriveWeek testNoDriveWeek = testNoDriveWeekDao.get(testNoDriveWeekId);
		if (testNoDriveWeek == null) {
			return rd;
		}
		testNoDriveWeekDao.delete(testNoDriveWeekId);
		OperationLog operationLog = new OperationLog();
		operationLog.setType(GlobalConstant.OPERATION_TYPE_DEL_NO_TEST);
		operationLog.setOperatorId(userId);
		operationLog.setOperatorTime(new Date());
		Integer vehicleId = testNoDriveWeek.getVehicleId();
		operationLog.setVehicleId(vehicleId);
		operationLog.setCompanyId(testNoDriveWeek.getCompanyId());
		User user = CacheUserManager.getUser(userId);
		Vehicle vehicle = CacheVehicleManager.getVehicleById(vehicleId);
		operationLog.setRemark("由【"+ user.getNickName() +"】删除VIN【" + vehicle.getVin() 
				+ "】【" + testNoDriveWeek.getYear() + "】年第【" + testNoDriveWeek.getWeek() + "】周的非活跃试驾记录");
		operationLogDao.save(operationLog);
		rd.setCode(1);
		return rd;
	}

}