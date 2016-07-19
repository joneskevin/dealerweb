package com.ava.dealer.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.vo.CompanyVO;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.TypeConverter;

@Service
public class BaseService {
	
	@Autowired
	private IHibernateDao hibernateDao;

	/**
	 * 设置分页SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg 分页参数
	 * @param exeCountSql 统计总条数SQL
	 * @param isExport 是否导出
	 * @return
	 */
	public StringBuffer getPaginationSql(TransMsg transMsg, StringBuffer exeCountSql, boolean isExport) {
		StringBuffer exeSql = new StringBuffer("");
		if(isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		
		//总条数
		Integer total = hibernateDao.getSqlCount(exeCountSql.toString());
		transMsg.setTotalRecords(TypeConverter.toLong(total));
		
		if (transMsg.getStartIndex() == null) {
			transMsg.setStartIndex(0);
		}
		exeSql.append(" limit ").append(transMsg.getStartIndex()).append("," )
			  .append(transMsg.getPageSize()).append( " ");
		return exeSql;
	}
	
	/**
	 * 获得访问权限SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getAccessSql(Integer currentCompanyId, Integer currentUserId) {
		StringBuffer exeSql = new StringBuffer("");
		if (currentCompanyId != null && currentUserId != null) {
			if (SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.DISTRIBUTION_CENTER_ADMIN_ROLE_ID) {
				exeSql.append(" AND vdi.saleCenterId = ").append(currentCompanyId);
			}
			if (SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.DEFAULT_DEALER_ROLE_ID) {
				exeSql.append(" AND vdi.companyId IN ( ").append(CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)).append( ")");
			}
		}
		
		return exeSql;
	}
	
	/**
	 * [reportBase]中获得访问权限SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getReportAccessSql(Integer currentCompanyId, Integer currentUserId) {
		StringBuffer exeSql = new StringBuffer("");
		if (currentCompanyId != null && currentUserId != null) {
			if (SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.DISTRIBUTION_CENTER_ADMIN_ROLE_ID) {
				exeSql.append(" AND r.SALE_CENTER_ID = ").append(currentCompanyId);
			}
			if (SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.DEFAULT_DEALER_ROLE_ID) {
				exeSql.append(" AND r.COMPANY_ID IN ( ").append(CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)).append( ")");
			}
		}
		
		return exeSql;
	}
	
	
	/**
	 * 获得车辆查询字段SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	public StringBuffer getVehicleSql() {
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" v.ID as vehicleId, v.PLATE_NUMBER as plateNumber, v.VIN as vin, v.CONFIGURE_STATUS as configureStatus, ");
		exeSql.append(" v.CAR_STYLE_ID as carStyleId, concat(cs.NAME, ' ', cs.YEAR_TYPE) as carStyleName, ");
		exeSql.append(" v.LICENSING_TIME as licensingTime, v.LICENSING_EXECUTOR_NAME as licensingExecutorName, ");
		exeSql.append(" v.OBD_NO as obdNo, v.OBD_FLAG as obdFlag, IF(v.OBD_FLAG = 1 , '有', '无') as obdFlagNick, ");
		exeSql.append(" v.INSTALLATION_TIME as installationTime, v.DEMOLITION_TIME as demolitionTime,  v.DELETION_TIME as deletionTime, ");
		//试驾车服役期为一年，在“安装时间”后自动顺延一年就是“退出时间”
		exeSql.append(" DATE_ADD(v.INSTALLATION_TIME, INTERVAL 1 YEAR) as exitTime, ");
		exeSql.append(" (CASE v.CONFIGURE_STATUS ");
		exeSql.append(" WHEN ").append( GlobalConstant.CONFIGURE_STATUS_WATTING).append(" THEN '待安装' ");
		exeSql.append(" WHEN ").append(GlobalConstant.CONFIGURE_STATUS_INSTALLED).append(" THEN '已安装' ");
		exeSql.append(" WHEN ").append(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED).append(" THEN '锁定' ");
		exeSql.append(" END) as configureStatusNick, ");
		
		return exeSql;
	}
	
	/**
	 * 经销商关联限购城市SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	public StringBuffer getRestrictionCitySql() {
		return new StringBuffer(" LEFT JOIN t_add_plateNumber_city adc on adc.CITY_ID = vdi.cityId ");
	}
	
	/**
	 * 车辆表关联车型SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	public StringBuffer getVehicleJoinCarStyleSql() {
		return new StringBuffer(" INNER JOIN t_car_style cs on cs.ID = v.CAR_STYLE_ID ");
	}
	
	/**
	 * 判断查询条件中是否有车辆参数
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleVO
	 * @return
	 */
	public boolean isHaveVehicleParameter(VehicleVO vehicleVO) {
		boolean flag = false;
		if (vehicleVO != null) {
			if ((StringUtils.isNotBlank(vehicleVO.getVin()))|| (StringUtils.isNotBlank(vehicleVO.getPlateNumber()))
					|| vehicleVO.getConfigureStatus() != null) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获得车辆查询条件SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicleVO
	 * @return
	 */
	public StringBuffer getVehicleConditionSql(VehicleVO vehicleVO) {
		StringBuffer exeSql = new StringBuffer("");
		if (vehicleVO != null) {
			if (StringUtils.isNotBlank(vehicleVO.getVin())) {
				exeSql.append(" AND v.VIN LIKE \"%").append(vehicleVO.getVin().trim()).append("%\" ");
			}
			
			if (StringUtils.isNotBlank(vehicleVO.getPlateNumber())) {
				exeSql.append(" AND v.PLATE_NUMBER LIKE \"%").append(vehicleVO.getPlateNumber().trim()).append("%\" ");
			}
			
			if(vehicleVO.getConfigureStatus() != null){
				exeSql.append(" AND v.CONFIGURE_STATUS = ").append(vehicleVO.getConfigureStatus());
			} 
			
			//查看两年以内的历史车辆、功能暂时屏蔽 TODO 
			/*if(vehicleVO.getIsHistroy() != null && vehicleVO.getIsHistroy().intValue() == GlobalConstant.TRUE){
				String startDate = DateTime.toNormalDate(DateTime.minusDays(new Date(), 730));
				String endDate = DateTime.getNormalDate();
				String startTime = startDate + " 00:00:00";
				String endTime = endDate + " 23:59:59";
				exeSql.append(" AND b2.OPERATION_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append(endTime).append(" ' ");
			}*/
		}
		
		return exeSql;
	}
	
	/**
	 * 获得经销商查询字段SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	public StringBuffer getDealerSql() {
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" vdi.saleCenterId, vdi.saleCenterName, vdi.provinceId, vdi.provinceName, vdi.cityId, vdi.cityName, ");
		exeSql.append(" vdi.parentCompanyId, vdi.parentCompanyName, vdi.parentDealerCode, vdi.companyId, vdi.companyName, vdi.dealerCode, ");
		exeSql.append(" vdi.isKeyCity, vdi.isKeyCityNick, vdi.dealerType, vdi.dealerTypeNick, vdi.netWorkTime, ");
		return exeSql;
	}
	
	/**
	 * [reportBase]中获得经销商查询字段SQL
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	public StringBuffer getReportDealerSql() {
		StringBuffer exeSql = new StringBuffer("");
		exeSql.append(" r.SALE_CENTER_ID as saleCenterId, r.SALE_CENTER_NAME as saleCenterName, ");
		exeSql.append(" r.PROVINCE_ID as provinceId, r.PROVINCE_NAME as provinceName, r.CITY_ID as cityId, r.CITY_NAME as cityName, ");
		exeSql.append(" r.PARENT_COMPANY_ID as parentCompanyId, r.PARENT_COMPANY_NAME as parentCompanyName, r.PARENT_DEALER_CODE as parentDealerCode, ");
		exeSql.append(" r.COMPANY_ID as companyId, r.COMPANY_NAME as companyName, r.DEALER_CODE as dealerCode, ");
		exeSql.append(" r.IS_KEY_CITY_NICK as isKeyCityNick, r.DEALER_TYPE_NICK as dealerTypeNick, ");
		return exeSql;
	}
	
	/**
	 * [reportBase]获得经销商查询条件SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getReportDealerConditionSql(DealerVO dealerVO, Integer currentUserId) {
		StringBuffer exeSql = new StringBuffer("");
		if (dealerVO != null) {
			//总部管理员、运营管理员添加该条件
			if (currentUserId != null && SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.HEADQUARTERS_ROLE_ID
					|| SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.OPERATIONS_ADMIN_ROLE_ID) {
				if (dealerVO.getParentId() != null && dealerVO.getParentId().intValue() > 0) {
					String saleCenterId = dealerVO.getParentId().toString();
					if (!saleCenterId.equals(GlobalConstant.NETWORK_DEVELOPMENT_ID)) {
						exeSql.append(" AND r.SALE_CENTER_ID = ").append(dealerVO.getParentId());
					}
				}
			} 
			if (StringUtils.isNotBlank(dealerVO.getDealerCode())) {
				exeSql.append(" AND r.DEALER_CODE LIKE \"%").append(dealerVO.getDealerCode().trim()).append("%\" ");
			}
			if (dealerVO.getDealerType() != null) {
				exeSql.append(" AND r.DEALER_TYPE =").append(dealerVO.getDealerType()).append(" ");
			}
		}
		return exeSql;
	}
	
	/**
	 * 获得经销商查询条件SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getDealerConditionSql(DealerVO dealerVO, Integer currentUserId) {
		StringBuffer exeSql = new StringBuffer("");
		if (dealerVO != null) {
			//总部管理员、运营管理员添加该条件
			if (currentUserId != null && SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.HEADQUARTERS_ROLE_ID
					|| SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.OPERATIONS_ADMIN_ROLE_ID) {
				if (dealerVO.getParentId() != null && dealerVO.getParentId().intValue() > 0) {
					String saleCenterId = dealerVO.getParentId().toString();
					if (!saleCenterId.equals(GlobalConstant.NETWORK_DEVELOPMENT_ID)) {
						exeSql.append(" AND vdi.saleCenterId = ").append(dealerVO.getParentId());
					}
				}
			} 
			if (StringUtils.isNotBlank(dealerVO.getDealerCode())) {
				exeSql.append(" AND vdi.dealerCode LIKE \"%").append(dealerVO.getDealerCode().trim()).append("%\" ");
			}
			if (dealerVO.getDealerType() != null) {
				exeSql.append(" AND vdi.dealerType =").append(dealerVO.getDealerType()).append(" ");
			}
		}
		return exeSql;
	}
	
	/**
	 * 获得经销商查询条件SQL
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getCompanyConditionSql(CompanyVO companyVO, Integer currentUserId) {
		StringBuffer exeSql = new StringBuffer("");
		if (companyVO != null) {
			//总部管理员、运营管理员添加该条件
			if (currentUserId != null && SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.HEADQUARTERS_ROLE_ID
					|| SessionManager.getMaxRoleId(currentUserId) == GlobalConstant.OPERATIONS_ADMIN_ROLE_ID) {
				if (companyVO.getParentId() != null && companyVO.getParentId().intValue() > 0) {
					String saleCenterId = companyVO.getParentId().toString();
					if (!saleCenterId.equals(GlobalConstant.NETWORK_DEVELOPMENT_ID)) {
						exeSql.append(" AND vdi.saleCenterId = ").append(companyVO.getParentId());
					}
				}
			} 
			if (StringUtils.isNotBlank(companyVO.getDealerCode())) {
				exeSql.append(" AND vdi.dealerCode LIKE \"%").append(companyVO.getDealerCode().trim()).append("%\" ");
			}
		}
		return exeSql;
	}
	
	/**
	 * 根据分销中心、网络代码排序
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getOrderSql() {
		StringBuffer exeSql = new StringBuffer(" ORDER BY vdi.saleCenterId, vdi.dealerCode ");
		return exeSql;
	}
	
	/**
	 * [reportBase]根据分销中心、网络代码排序
	 * @author liuq 
	 * @version 0.1 
	 * @param currentCompanyId
	 * @param currentUserId
	 * @return
	 */
	public StringBuffer getReportOrderSql() {
		StringBuffer exeSql = new StringBuffer(" ORDER BY r.SALE_CENTER_ID, r.DEALER_CODE ");
		return exeSql;
	}
}
