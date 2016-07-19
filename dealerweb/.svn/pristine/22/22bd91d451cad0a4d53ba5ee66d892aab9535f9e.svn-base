package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.ava.dao.ICarSeriesDao;
import com.ava.dao.ICarStyleDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IDesignateOrderDao;
import com.ava.dao.IVehicleDao;
import com.ava.dao.IVehicleInstallationPlanDao;
import com.ava.dealer.service.IVehicleInstallationPlanService;
import com.ava.domain.entity.CarSeries;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.DesignateOrder;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.entity.VehicleInstallationPlan;
import com.ava.domain.vo.DesignateOrderExcelVO;
import com.ava.domain.vo.DesignateOrderFindVO;
import com.ava.domain.vo.VehicleInstallationPlanFindVO;
import com.ava.domain.vo.VehicleInstallationPlanVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.util.MyBeanUtils;

@Service
public class VehicleInstallationPlanService extends BaseService implements IVehicleInstallationPlanService {

	@Autowired
	private IHibernateDao hibernateDao;
	@Autowired
	private IVehicleInstallationPlanDao vehicleInstallationPlanDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private ICarSeriesDao carSeriesDao;
	
	@Autowired
	private ICarStyleDao carStyleDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private IDesignateOrderDao designateOrderDao;
	
	@Override
	public ResponseData listVehicleInstallationPlan(TransMsg transMsg, VehicleInstallationPlanVO vehicleInstallationPlanVO, boolean isExport) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");

		exeSql.append(" vip.ID AS id, vip.TEST_DRIVE_CAR_STYLE_ID AS testDriveCarStyleId, vip.DESS_CAR_STYLE_ID AS dessCarStyleId, vip.TYPE as type, vip.FLAG as flag, ");
		exeSql.append(" concat(cs.NAME, ' ', cs.YEAR_TYPE) as testDriveCarStyleName, concat(cs1.NAME, ' ', cs1.YEAR_TYPE) as dessCarStyleName, ");
		exeSql.append(" (CASE vip.TYPE ");
		exeSql.append(" WHEN ").append(1).append(" THEN '换装' ");
		exeSql.append(" WHEN ").append(2).append(" THEN '拆除' ");
		exeSql.append(" END) as typeNick, ");
		exeSql.append(" (CASE vip.FLAG ");
		exeSql.append(" WHEN ").append(0).append(" THEN '启用' ");
		exeSql.append(" WHEN ").append(1).append(" THEN '停用' ");
		exeSql.append(" END) as flagNick ");
		exeSql.append(" FROM t_vehicle_installation_plan vip ");
		exeCountSql.append(" FROM t_vehicle_installation_plan vip ");
		//关联车型
		exeSql.append(" LEFT JOIN t_car_style cs on cs.ID = vip.TEST_DRIVE_CAR_STYLE_ID ");
		exeSql.append(" LEFT JOIN t_car_style cs1 on cs1.ID = vip.DESS_CAR_STYLE_ID ");
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		if (vehicleInstallationPlanVO.getTestDriveCarStyleId() != null && vehicleInstallationPlanVO.getTestDriveCarStyleId().intValue() > 0) {
			exeSql.append(" AND vip.TEST_DRIVE_CAR_STYLE_ID = ").append(vehicleInstallationPlanVO.getTestDriveCarStyleId());
			exeCountSql.append(" AND vip.TEST_DRIVE_CAR_STYLE_ID = ").append(vehicleInstallationPlanVO.getTestDriveCarStyleId());
		} 
		if (vehicleInstallationPlanVO.getDessCarStyleId() != null && vehicleInstallationPlanVO.getDessCarStyleId().intValue() > 0) {
			exeSql.append(" AND vip.DESS_CAR_STYLE_ID = ").append(vehicleInstallationPlanVO.getDessCarStyleId());
			exeCountSql.append(" AND vip.DESS_CAR_STYLE_ID = ").append(vehicleInstallationPlanVO.getDessCarStyleId());
		} 
		
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<VehicleInstallationPlanFindVO> InstallationPlanFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), VehicleInstallationPlanFindVO.class);
		
		rd.setFirstItem(InstallationPlanFindVOList);
		return rd;
	}
	
	@Override
	public List<CarStyle> getDemolitionCarStyleList() {
		List<CarStyle> carStyleList = new ArrayList<CarStyle>();
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("type", 2);
		parameters.put("flag", 0);//拆除计划为已启用
		List<VehicleInstallationPlan> vehicleInstallationPlanList = vehicleInstallationPlanDao.find(parameters, "");
		if (vehicleInstallationPlanList != null && vehicleInstallationPlanList.size() > 0) {
			for (VehicleInstallationPlan vehicleInstallationPlan : vehicleInstallationPlanList) {
				if (vehicleInstallationPlan != null && vehicleInstallationPlan.getTestDriveCarStyleId() != null) {
					CarStyle carStyle = new CarStyle();
					Integer carStyleId = vehicleInstallationPlan.getTestDriveCarStyleId();
					carStyle.setId(carStyleId);
					carStyle.setName(CacheCarManager.getCarStyleNameById(carStyleId));
					carStyleList.add(carStyle);
				}
			}
		}
		return carStyleList;
	}

	@Override
	public ResponseData listCarSeries(TransMsg transMsg, CarSeries carSeries, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		if (carSeries != null) {
			if (StringUtils.isNotBlank(carSeries.getName())) {
				ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "name", carSeries.getName());
			}
		}
		List<CarSeries> carSeriesList = carSeriesDao.findByPage(transMsg, "");
		rd.put("carSeriesList", carSeriesList);
		return rd;
	}

	@Override
	public ResponseData listCarStyle(TransMsg transMsg, CarStyle carStyle, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		if (carStyle != null) {
			if (StringUtils.isNotBlank(carStyle.getName())) {
				ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "name", carStyle.getName());
			}
			if (carStyle.getSeriesId() != null) {
				transMsg.put("seriesId", carStyle.getSeriesId());
			}
		}
		List<CarStyle> carStyleList = carStyleDao.findByPage(transMsg, "");
		rd.put("carStyleList", carStyleList);
		return rd;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addCarSeries(CarSeries carSeries) {
		ResponseData rd = new ResponseData(1);
		
		String name = carSeries.getName();
		CarSeries dbCarSeries = carSeriesDao.getCarSeriesName(name);
		if (dbCarSeries != null) {
			rd.setCode(-1);
			rd.setMessage("车系名已存在");
			return rd;
		}
		
		carSeriesDao.save(carSeries);
		CacheCarManager.removeCarStyleIdsCache();
		
		rd.setMessage("新增车系成功");
		rd.setFirstItem(carSeries);
		return rd;
	}

	@Override
	public ResponseData displayEditCarSeries(Integer carSeriesId) {
		ResponseData rd = new ResponseData(1);
		CarSeries dbCarSeries = carSeriesDao.get(carSeriesId);
		rd.setFirstItem(dbCarSeries);
		return rd;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData editCarSeries(CarSeries carSeries) {
		ResponseData rd = new ResponseData(1);
		
		Integer carSeriesId = carSeries.getId();
		CarSeries dbCarSeries = carSeriesDao.get(carSeriesId);
		String name = carSeries.getName();
		if (dbCarSeries.getName() !=null && !name.equalsIgnoreCase(dbCarSeries.getName())) {
			if (this.isCarSeriesExistence("name", name, null)) {
				rd.setCode(-1);
				rd.setMessage("保存失败：新的车型名已经存在！");
				return rd;
			}
		}
		MyBeanUtils.copyPropertiesContainsDate(dbCarSeries, carSeries);
		
		carSeriesDao.update(dbCarSeries);
		String hql = "update CarStyle set seriesName = ? where seriesId = ?";
		hibernateDao.bulkUpdate(hql, name, carSeriesId);
		CacheCarManager.removeCarStyleIdsCache();
		
		rd.setFirstItem(dbCarSeries);
		rd.setMessage("编辑车系成功");
		return rd;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addCarStyle(CarStyle carStyle) {
		ResponseData rd = new ResponseData(1);
		
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("name", carStyle.getName());
		parameters.put("yearType", carStyle.getYearType());
		List<CarStyle> carStyleList = carStyleDao.find(parameters, "");
		if (carStyleList != null &&  carStyleList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("车型名已存在");
			return rd;
		}
		
		carStyle.init();
		carStyleDao.save(carStyle);
		
		rd.setMessage("新增车型成功");
		rd.setFirstItem(carStyle);
		// 需更新缓存
		CacheCarManager.removeCarStyleIdsCache();
		return rd;
	}

	@Override
	public ResponseData displayEditCarStyle(Integer carStyleId) {
		ResponseData rd = new ResponseData(1);
		CarStyle dbCarStyle = carStyleDao.get(carStyleId);
		rd.setFirstItem(dbCarStyle);
		return rd;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData editCarStyle(CarStyle carStyle) {
		ResponseData rd = new ResponseData(1);
		
		Integer carStyleId = carStyle.getId();
		CarStyle dbCarStyle = carStyleDao.get(carStyleId);
		if ((dbCarStyle.getName() !=null && !carStyle.getName().equalsIgnoreCase(dbCarStyle.getName())) ||
				(dbCarStyle.getYearType() !=null && !carStyle.getYearType().equalsIgnoreCase(dbCarStyle.getYearType()))	) {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("name", carStyle.getName());
			parameters.put("yearType", carStyle.getYearType());
			List<CarStyle> carStyleList = carStyleDao.find(parameters, "");
			if (carStyleList != null &&  carStyleList.size() > 0) {
				rd.setCode(-1);
				rd.setMessage("保存失败：新的车型名已经存在！");
				return rd;
			}
		}
		MyBeanUtils.copyPropertiesContainsDate(dbCarStyle, carStyle);
		
		carStyleDao.update(dbCarStyle);
		
		rd.setFirstItem(dbCarStyle);
		rd.setMessage("编辑车型成功");
		// 需更新缓存
		CacheCarManager.putCarStyleCache(carStyleId, dbCarStyle);
		return rd;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData deleteCarSeries(Integer carSeriesId) {
		ResponseData rd = new ResponseData(1);
        
		if (carSeriesId == null) {
			rd.setCode(-1);
			rd.setMessage("carSeriesId为空！");
			return rd;
		}
		
		//判断是否有车型引用到车系
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("seriesId", carSeriesId);
		List<CarStyle> carStyleList = carStyleDao.find(parameters, "");
		if (carStyleList != null && carStyleList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("删除失败：有车型引用到该车系，不允许删除！");
			return rd;
		}
		carSeriesDao.delete(carSeriesId);
		CacheCarManager.removeCarStyleIdsCache();
		
		rd.setMessage("车系删除成功！");
		return rd;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData deleteCarStyle(Integer carStyleId) {
		ResponseData rd = new ResponseData(1);
        
		if (carStyleId == null) {
			rd.setCode(-1);
			rd.setMessage("carStyleId为空！");
			return rd;
		}
		
		//判断是否有车辆引用到车型
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("carStyleId", carStyleId);
		List<Vehicle> vehicleList = vehicleDao.find(parameters, "");
		if (vehicleList != null && vehicleList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("删除失败：有车辆引用到该车型，不允许删除！");
			return rd;
		}
		carStyleDao.delete(carStyleId);
		
		rd.setMessage("车型删除成功！");
		// 需更新缓存
		CacheCarManager.removeCarStyleIdsCache();
		return rd;
	}
	
	public boolean isCarSeriesExistence(String field, String value, String additionalCondition) {
		if (value == null || value.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put(field, value);
		List result = carSeriesDao.find(parameters, additionalCondition);
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
	
	public boolean isCarStyleExistence(String field, String value, String additionalCondition) {
		if (value == null || value.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put(field, value);
		List result = carStyleDao.find(parameters, additionalCondition);
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<CarSeries> getCarSeriesList() {
		List<CarSeries> carSeriesList = carSeriesDao.find(null, null);
		return carSeriesList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addInstallationPlan(VehicleInstallationPlan vehicleInstallationPlan) {
		ResponseData rd = new ResponseData(1);
		Integer type = vehicleInstallationPlan.getType();
		Integer testDriveCarStyleId = vehicleInstallationPlan.getTestDriveCarStyleId();
		if (type == 1) {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("testDriveCarStyleId", testDriveCarStyleId);
			parameters.put("dessCarStyleId", vehicleInstallationPlan.getDessCarStyleId());
			parameters.put("type", 1);
			List<VehicleInstallationPlan> vehicleInstallationPlanList = vehicleInstallationPlanDao.find(parameters, "");
			if (vehicleInstallationPlanList != null &&  vehicleInstallationPlanList.size() > 0) {
				rd.setCode(-1);
				rd.setMessage("换装计划已存在");
				return rd;
			}
			
		} else {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("testDriveCarStyleId", testDriveCarStyleId);
			parameters.put("type", 2);
			List<VehicleInstallationPlan> vehicleInstallationPlanList = vehicleInstallationPlanDao.find(parameters, "");
			if (vehicleInstallationPlanList != null &&  vehicleInstallationPlanList.size() > 0) {
				rd.setCode(-1);
				rd.setMessage("拆除计划已存在");
				return rd;
			}
		}
		
		vehicleInstallationPlan.setFlag(1);
		vehicleInstallationPlanDao.save(vehicleInstallationPlan);
		
		rd.setMessage("新增计划成功");
		rd.setFirstItem(vehicleInstallationPlan);
		return rd;
	}

	@Override
	public ResponseData listDesignateOrder(TransMsg transMsg, DesignateOrder designateOrder, boolean isExport) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");

		exeSql.append(" do.ID AS id, do.TEST_DRIVE_CAR_STYLE_ID AS testDriveCarStyleId, v.VIN AS vin, do.DESS_CAR_STYLE_ID AS dessCarStyleId, do.CREATION_TIME AS creationTime, ");
		exeSql.append(" do.DEALER_CODE AS dealerCode, vdi.companyName AS companyName, ");
		exeSql.append(" concat(cs.NAME, ' ', cs.YEAR_TYPE) as testDriveCarStyleName, concat(cs1.NAME, ' ', cs1.YEAR_TYPE) as dessCarStyleName ");
		
		exeSql.append(" FROM t_designate_order do ");
		exeCountSql.append(" FROM t_designate_order do ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.dealerCode = do.DEALER_CODE ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.dealerCode = do.DEALER_CODE ");
		
//		exeSql.append(" LEFT JOIN t_company c on c.DEALER_CODE = do.DEALER_CODE ");
		exeSql.append(" LEFT JOIN t_vehicle v on v.CAR_STYLE_ID = do.TEST_DRIVE_CAR_STYLE_ID AND v.COMPANY_ID = vdi.companyId ");
		
		//关联车型
		exeSql.append(" LEFT JOIN t_car_style cs on cs.ID = do.TEST_DRIVE_CAR_STYLE_ID ");
		exeSql.append(" LEFT JOIN t_car_style cs1 on cs1.ID = do.DESS_CAR_STYLE_ID ");
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		if (StringUtils.isNotBlank(designateOrder.getDealerCode())) {
			exeSql.append(" AND do.DEALER_CODE LIKE \"%").append(designateOrder.getDealerCode()).append("%\" ");
		}
		
		if (designateOrder.getDessCarStyleId() != null && designateOrder.getDessCarStyleId().intValue() > 0) {
			exeSql.append(" AND do.TEST_DRIVE_CAR_STYLE_ID = ").append(designateOrder.getDessCarStyleId());
			exeCountSql.append(" AND do.TEST_DRIVE_CAR_STYLE_ID = ").append(designateOrder.getDessCarStyleId());
		} 
		if (designateOrder.getTestDriveCarStyleId() != null && designateOrder.getTestDriveCarStyleId().intValue() > 0) {
			exeSql.append(" AND do.DESS_CAR_STYLE_ID = ").append(designateOrder.getTestDriveCarStyleId());
			exeCountSql.append(" AND do.DESS_CAR_STYLE_ID = ").append(designateOrder.getTestDriveCarStyleId());
		} 
		
		//exeSql.append(" ORDER BY do.DEALER_CODE ");
		
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<DesignateOrderFindVO> DesignateOrderFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), DesignateOrderFindVO.class);
		
		rd.setFirstItem(DesignateOrderFindVOList);
		return rd;
	}

	@Override
	public ResponseData enableInstallationPlan(Integer installationPlanId) {
		ResponseData rd = new ResponseData(1);
		if (installationPlanId == null) {
			rd.setCode(-1);
			rd.setMessage("installationPlanId为空！");
			return rd;
		}
		
		VehicleInstallationPlan vehicleInstallationPlan = vehicleInstallationPlanDao.get(installationPlanId);
		Integer testDriveCarStyleId = vehicleInstallationPlan.getTestDriveCarStyleId();
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("testDriveCarStyleId", testDriveCarStyleId);
		parameters.put("dessCarStyleId", vehicleInstallationPlan.getDessCarStyleId());
		List<DesignateOrder> designateOrderList = designateOrderDao.find(parameters, "");
		if (designateOrderList == null || designateOrderList.size() == 0) {
			rd.setCode(-1);
			rd.setMessage("启用前，请先导入有效的指派订单数据！");
			return rd;
		}
		
		StringBuffer exeSql = new StringBuffer("");
		if (vehicleInstallationPlan.getType() == 1) {
			exeSql.append(" update t_vehicle v, t_company c, t_designate_order do, t_vehicle_installation_plan vip ");
			exeSql.append(" set v.`REPLACE_PLAN_ID`= vip.id, v.CONFIGURE_STATUS = 30 ");
			exeSql.append(" WHERE v.company_id = c.org_id and c.dealer_code = do.dealer_code ");
			exeSql.append(" and do.TEST_DRIVE_CAR_STYLE_ID = vip.TEST_DRIVE_CAR_STYLE_ID and do.DESS_CAR_STYLE_ID = vip.DESS_CAR_STYLE_ID ");
			exeSql.append(" and v.configure_status in(20, 30) and v.car_style_id = do.TEST_DRIVE_CAR_STYLE_ID and v.car_style_id = " + vehicleInstallationPlan.getTestDriveCarStyleId());
			//将换装计划ID更新到要换装的车辆信息中
			hibernateDao.executeSQLUpdate(exeSql.toString());
		}
		
		vehicleInstallationPlan.setFlag(0);
		vehicleInstallationPlanDao.edit(vehicleInstallationPlan);
		
		rd.setMessage("计划启用成功！");
		return rd;
	}

	@Override
	public ResponseData disableInstallationPlan(Integer installationPlanId) {
		ResponseData rd = new ResponseData(1);
		if (installationPlanId == null) {
			rd.setCode(-1);
			rd.setMessage("installationPlanId为空！");
			return rd;
		}
		
		VehicleInstallationPlan vehicleInstallationPlan = vehicleInstallationPlanDao.get(installationPlanId);
		StringBuffer exeSql = new StringBuffer("");
		if (vehicleInstallationPlan.getType() == 1) {
			exeSql.append(" update t_vehicle v, t_company c, t_designate_order do, t_vehicle_installation_plan vip ");
			exeSql.append(" set v.`REPLACE_PLAN_ID`= 0 ");
			exeSql.append(" WHERE v.company_id = c.org_id and c.dealer_code = do.dealer_code ");
			exeSql.append(" and do.TEST_DRIVE_CAR_STYLE_ID = vip.TEST_DRIVE_CAR_STYLE_ID and do.DESS_CAR_STYLE_ID = vip.DESS_CAR_STYLE_ID ");
			exeSql.append(" and v.configure_status in(20, 30) and v.car_style_id = do.TEST_DRIVE_CAR_STYLE_ID and v.car_style_id = " + vehicleInstallationPlan.getTestDriveCarStyleId());
			//将换装计划ID更新为0
			hibernateDao.executeSQLUpdate(exeSql.toString());
		}
		
		vehicleInstallationPlan.setFlag(1);
		vehicleInstallationPlanDao.edit(vehicleInstallationPlan);
		
		rd.setMessage("计划停用成功！");
		return rd;
	}
	
	@Override
	public ResponseData upLoadDesignateOrder(List<DesignateOrderExcelVO> designateOrderExcelList) {
		ResponseData rd = new ResponseData(1);
		
		if (designateOrderExcelList != null && designateOrderExcelList.size() > 0) {
			for (DesignateOrderExcelVO designateOrderExcelVO : designateOrderExcelList) {
				String dealerCode = designateOrderExcelVO.getDealerCode();
				Integer testDriveCarStyleId = designateOrderExcelVO.getTestDriveCarStyleId();
				Integer dessCarStyleId = designateOrderExcelVO.getDessCarStyleId();
				String message = "请检查导入数据的有效性";
				if (dealerCode == null) {
					rd.setCode(-1);
					rd.setMessage("网络代码不能为空，" + message);
					return rd;
				}
				if (testDriveCarStyleId == null) {
					rd.setCode(-1);
					rd.setMessage("换装前车型ID不能为空，" + message);
					return rd;
				}
				if (dessCarStyleId == null) {
					rd.setCode(-1);
					rd.setMessage("换装后车型ID不能为空，" + message);
					return rd;
				}
				Company company = CacheCompanyManager.getByDealerCode(dealerCode);
				if (company == null) {
					rd.setCode(-1);
					rd.setMessage("网络代码：<" + dealerCode + ">不存在，" + message);
					return rd;
				}
				CarStyle carStyle  = CacheCarManager.getCarStyleById(testDriveCarStyleId);
				if (carStyle == null) {
					rd.setCode(-1);
					rd.setMessage("换装前车型ID：<" + testDriveCarStyleId + ">不存在，" + message);
					return rd;
				}
				carStyle = CacheCarManager.getCarStyleById(dessCarStyleId);
				if (carStyle == null) {
					rd.setCode(-1);
					rd.setMessage("换装后车型ID：<" + dessCarStyleId + ">不存在，" + message);
					return rd;
				}
			}
			for (DesignateOrderExcelVO designateOrderExcelVO : designateOrderExcelList) {
				HashMap<Object, Object> parameters = new HashMap<Object, Object>();
				parameters.put("dealerCode", designateOrderExcelVO.getDealerCode());
				parameters.put("testDriveCarStyleId", designateOrderExcelVO.getTestDriveCarStyleId());
				parameters.put("dessCarStyleId", designateOrderExcelVO.getDessCarStyleId());
				List<DesignateOrder> designateOrderList =  designateOrderDao.find(parameters, "");
				if (designateOrderList == null || designateOrderList.size() == 0) {
					DesignateOrder designateOrder = new DesignateOrder();
					MyBeanUtils.copyPropertiesContainsDate(designateOrder, designateOrderExcelVO);
					Date creationTime = new Date();
					designateOrder.setCreationTime(creationTime);
					designateOrderDao.save(designateOrder);
				}
				
			}
		}
		
		rd.setMessage("指派订单导入成功");
		return rd;
	}

	@Override
	public ResponseData deleteDesignateOrder(Integer designateOrderId) {
		ResponseData rd = new ResponseData(1);
        
		if (designateOrderId == null) {
			rd.setCode(-1);
			rd.setMessage("designateOrderId为空！");
			return rd;
		}
		designateOrderDao.delete(designateOrderId);
		
		rd.setMessage("指派订单删除成功！");
		return rd;
	}

	@Override
	public ResponseData deleteInstallationPlan(Integer installationPlanId) {
		ResponseData rd = new ResponseData(1);
        
		if (installationPlanId == null) {
			rd.setCode(-1);
			rd.setMessage("installationPlanId为空！");
			return rd;
		}
		vehicleInstallationPlanDao.delete(installationPlanId);
		
		rd.setMessage("换装拆除计划删除成功！");
		return rd;
	}
	
}
