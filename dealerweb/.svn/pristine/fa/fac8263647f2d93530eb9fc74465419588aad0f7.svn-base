package com.ava.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ICompanyDao;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.facade.IReportBaseFacade;
import com.ava.resource.DbCacheResource;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Service
public class ReportBaseFacade1 implements IReportBaseFacade {
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private ICompanyDao companyDao ;
	
	/**
	 * 抽取数据【入口】
	 * @param extractionFlag 0、按天数抽取、1、按时间段抽取
	 * @param dayNum 抽取天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 */
	@SuppressWarnings("unchecked")
	public void extraction(Integer extractionFlag, Integer dayNum, Date startDate, Date endDate) {
		try {
			if (extractionFlag != null) {
				//按天数抽取、执行删除操作
				if (extractionFlag == GlobalConstant.FALSE && dayNum != null) {
					if (dayNum == 1) {
						Date date = DateTime.minusDays(new Date(), 1);
						this.deleteReportBase(date, date);
						
					} else {
						startDate = DateTime.minusDays(new Date(), dayNum);
						endDate = DateTime.minusDays(new Date(), 1);
						this.deleteReportBase(startDate, endDate);
					}
				} 
				//按时间段抽取、执行删除操作
				if (extractionFlag == GlobalConstant.TRUE && startDate != null && endDate != null) {
					this.deleteReportBase(startDate, endDate);
				} 
				
				HashMap<Object, Object> parameters = new HashMap<Object, Object>();
				List<Company> companyList = companyDao.find(parameters, "");
				
				if (companyList != null && companyList.size() > 0) {
					for (Company company : companyList) { 
						if (company != null) {
							//按天数抽取
							if (extractionFlag == GlobalConstant.FALSE && dayNum != null) {
								this.extractionByFlag(company, extractionFlag, dayNum, startDate, endDate);
							} 
							//按时间段抽取
							if (extractionFlag == GlobalConstant.TRUE && startDate != null && endDate != null) {
								this.extractionByFlag(company, extractionFlag, dayNum, startDate, endDate);
							} 
							
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	/**
	 * 根据不同力度抽取
	 * @param company
	 * @param extractionFlag 0、按天数抽取、1、按时间段抽取
	 * @param dayNum 抽取天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 */
	void extractionByFlag(Company company, Integer extractionFlag, Integer dayNum, Date startDate, Date endDate) {
		//按天数抽取
		if (extractionFlag == GlobalConstant.FALSE) {
			if (dayNum == 1) {
				//抽取一天的数据，为前一天的数据
				Date date = DateTime.minusDays(new Date(), 1);
				String dateStr = DateTime.toNormalDate(date);
				addReportBase(company, dateStr);
				
			} else {
				startDate = DateTime.minusDays(new Date(), dayNum);
				endDate = DateTime.minusDays(new Date(), 1);
				List<String> dateList = DateTime.initDayList(startDate, endDate);
				if (dateList != null && dateList.size() > 0) {
					for (String dateStr : dateList) {
						addReportBase(company, dateStr);
					}
				}
			}
		} 
		//按时间段抽取
		if (extractionFlag == GlobalConstant.TRUE) {
			List<String> dateList = DateTime.initDayList(startDate, endDate);
			if (dateList != null && dateList.size() > 0) {
				for (String dateStr : dateList) {
					addReportBase(company, dateStr);
				}
			}
		} 
	}
	
	/**
	 * 抽取之前先删除该时间段的数据，避免重复添加
	 * @author liuq 
	 * @version 0.1 
	 * @param startDate
	 * @param endDate
	 */
	void deleteReportBase(Date startDate, Date endDate) {
		if (startDate != null && endDate != null) {
			String startDateStr = DateTime.toNormalDate(startDate);
			String endDateStr = DateTime.toNormalDate(endDate);
			StringBuffer exeSql = new StringBuffer("");
			exeSql.append(" DELETE FROM t_report_base");
			exeSql.append(" WHERE EXTRACTION_DATE BETWEEN ' ").append(startDateStr).append(" ' ").append(" AND ' ").append(endDateStr).append(" ' ");
			hibernateDao.executeSQLUpdate(exeSql.toString());
		}
	}
	
	/**
	 * 添加到抽取表中
	 * @param company
	 * @param dateStr
	 */
	protected void addReportBase(Company company, String dateStr) {
		try {
			//获取所有带有年款的车型
			List<CarStyle> carStyleList = CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
			if (carStyleList != null && carStyleList.size() > 0) {
				for (CarStyle carStyle : carStyleList) {
					//抽取所有数据
					String exeSql = getExecuteSql(company, carStyle, dateStr);
					hibernateDao.executeSQLUpdate(exeSql);
					 
				}
			}
		}  catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	  /**
	   * 抽取配置统计信息
	 * @param company
	 * @param carStyle
	 * @param dateStr
	 * @return TODO
	   */
	  String getExecuteSql(Company company, CarStyle carStyle, String dateStr) {
		 Integer companyId = company.getId();
		 Integer carStyleId = carStyle.getId();
		 //要求配置
		 String denandConfigCountSql = this.getConfigCountSql(companyId, carStyleId, GlobalConstant.CONFIGURE_TYPE_MUST, dateStr);
		 //可选配置
		 String optionalConfigCountSql = this.getConfigCountSql(companyId, carStyleId, GlobalConstant.CONFIGURE_TYPE_MAY, dateStr);
		 //实际配置
		 String realityConfigCount = this.getConfigCountSql(companyId, carStyleId, GlobalConstant.CONFIGURE_STATUS_INSTALLED, GlobalConstant.PROPOSAL_TYPE_INSTALLATION, dateStr);
		 //已拆除数量
		 String demolitionCount = this.getConfigCountSql(companyId, carStyleId, GlobalConstant.CONFIGURE_STATUS_UNINSTALLED, GlobalConstant.PROPOSAL_TYPE_DEMOLITION, dateStr);
		 
		 //TODO 
		 StringBuffer exeSql = new StringBuffer("");
		 exeSql.append(" INSERT INTO t_report_base ( ");
		 //抽取日期信息
		 exeSql.append(" EXTRACTION_DATE, YEARS, CREATE_TIME, "); 
		 //经销商信息
		 exeSql.append(" SALE_CENTER_ID, SALE_CENTER_NAME, PROVINCE_ID, PROVINCE_NAME, CITY_ID, CITY_NAME, ");
		 exeSql.append(" COMPANY_ID, COMPANY_NAME, DEALER_CODE, DEALER_TYPE, DEALER_TYPE_NICK, ");
		 exeSql.append(" PARENT_COMPANY_ID, PARENT_COMPANY_NAME, PARENT_DEALER_CODE, ");
		 //车型信息
		 exeSql.append(" CAR_STYLE_ID, CAR_STYLE_NAME, ");
		 exeSql.append(" DENAND_CONFIG_COUNT, ");//要求配置
		 exeSql.append(" OPTIONAL_CONFIG_COUNT, ");//可选配置
		 exeSql.append(" REALITY_CONFIG_COUNT, ");//实际配置
		 exeSql.append(" WAIT_INSTALLATION_COUNT, ");//待安装
		 exeSql.append(" DEMOLITION_COUNT, ");//已拆除数量
		 exeSql.append(" REPLACE_COUNT, ");//已换装数量
		 exeSql.append(" CUSTOMERS_COUNT, ");//有望客户数
		 exeSql.append(" TEST_DRIVE_COUNT, ");//试驾次数
		 exeSql.append(" VIOLATION_COUNT, ");//违规次数
		 exeSql.append(" TEST_DRIVE_RATE ");//试驾率
		 exeSql.append(" ) ");
		 exeSql.append(" VALUES (");
		 
		 dateStr = dateStr + " 00:00:00";
		 String yearStr = DateTime.toShortStr(DateTime.toDate(dateStr));
		 Integer years = Integer.valueOf(yearStr);
		 //抽取日期信息value
		 exeSql.append(" '").append(dateStr).append("', ").append(years).append(", ").append("sysdate(), ");
		
		 Integer saleCenterId = company.getParentId();
		 String saleCenterName = CacheOrgManager.getOrgName(saleCenterId);
		 
		 Integer provinceId = company.getRegionProvinceId();
		 String provinceName = DbCacheResource.getDataDictionaryNameById(provinceId);
		 
		 Integer cityId = company.getRegionCityId();
		 String cityName = DbCacheResource.getDataDictionaryNameById(cityId);
		 
		 String CompanyName = company.getCnName();
		 String dealerCode = company.getDealerCode();
	
		 Integer dealerType = company.getDealerType();
		 String dealerTypeNick = company.getDealerType_Nick();
		 //上级经销商信息
		 int parentCompanyId = 0;
		 String parentCompanyName = "";
		 String parentDealerCode = "";
		 if (dealerType == GlobalConstant.DEALER_TYPE_NON_STRAIGHT_DIRECT_SHOP) {
			 Company parentCompany =  CacheCompanyManager.getCompany(company.getCompanyId());
			if (parentCompany != null) {
				parentCompanyId = parentCompany.getId();
				parentCompanyName = parentCompany.getCnName();
				parentDealerCode = parentCompany.getDealerCode();
			}
		 }
		 
		 //经销商信息value
		 exeSql.append(" ").append(saleCenterId).append(", '").append(saleCenterName).append("', ");
		 exeSql.append(" ").append(provinceId).append(", '").append(provinceName).append("', ").append(cityId).append(", '").append(cityName).append("', ");
		 exeSql.append(" ").append(companyId).append(", '").append(CompanyName).append("', '").append(dealerCode).append("', ");
		 exeSql.append(dealerType).append(", '").append(dealerTypeNick).append("', ");
		 exeSql.append(" ").append(parentCompanyId).append(", '").append(parentCompanyName).append("', '").append(parentDealerCode).append("', ");
		 
		 //车型信息value
		 String carStyleName = carStyle.getName() + " " + carStyle.getYearType();
		 exeSql.append(" ").append(carStyleId).append(", '").append(carStyleName).append("', ");
		 exeSql.append("(").append(denandConfigCountSql).append("), ");//要求配置
		 
		 exeSql.append("(").append(optionalConfigCountSql).append("), ");//可选配置
		 exeSql.append("(").append(realityConfigCount).append("), ");//实际配置
		 exeSql.append("0, ");//待安装【不启用】
		 
		 exeSql.append("(").append(demolitionCount).append("), ");//已拆除数量
		 exeSql.append("0, ");//已换装数量【不启用】
		 
		 exeSql.append("0, ");//有望客户数
		 exeSql.append("0, ");//试驾次数
		 exeSql.append("0, ");//违规次数
		 exeSql.append(" '0.0' ");//试驾率【不启用】
		 exeSql.append(" ) ");
		 
		 return exeSql.toString();
	} 
	 
	/**
	 * 查询需求配置数量SQL
	 * @param companyId
	 * @param carStyleId
	 * @param configureType
	 * @param dateStr
	 * @return
	 */
    String getConfigCountSql(Integer companyId, Integer carStyleId, Integer configureType, String dateStr) {
		String startTime = dateStr + " 00:00:00";
		String endTime = dateStr + " 23:59:59";
		StringBuffer exeSql = new StringBuffer("");
		if (companyId != null && carStyleId != null && dateStr != null) {
			exeSql.append(" SELECT COUNT(t1.ID) FROM t_vehicle t1, t_proposal t2 ");
			exeSql.append(" WHERE t1.Id = t2.VEHICLE_ID ");
			exeSql.append(" AND t1.COMPANY_ID = t2.COMPANY_ID ");
			exeSql.append(" AND t1.CAR_STYLE_ID = ").append(carStyleId).append(" ");
			exeSql.append(" AND t1.COMPANY_ID = ").append(companyId).append(" ");
			exeSql.append(" AND t1.CONFIGURE_TYPE = ").append(configureType).append(" ");
			exeSql.append(" AND t1.CONFIGURE_STATUS IN ( ").append(GlobalConstant.CONFIGURE_STATUS_WATTING).append(",").append(GlobalConstant.CONFIGURE_STATUS_INSTALLED).append(" ) ");
			exeSql.append(" AND t2.TYPE IN ( ").append(GlobalConstant.PROPOSAL_TYPE_INSTALLATION).append(",").append(GlobalConstant.PROPOSAL_TYPE_REPLACE ).append(" ) ");
			exeSql.append(" AND t2.PROPOSAL_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append( endTime).append(" ' ");
			
		}
		return exeSql.toString();
	 }
	 
	 /**
	  * 查询已安装或已拆除数量SQL
	  * @author liuq 
	  * @version 0.1 
	  * @param companyId
	  * @param carStyleId
	  * @param configureStatus
	  * @param type 新装、拆除
	  * @param dateStr
	  * @return
	  */
	 String getConfigCountSql(Integer companyId, Integer carStyleId, Integer configureStatus, Integer type, String dateStr) {
		 String startTime = dateStr + " 00:00:00";
		 String endTime = dateStr + " 23:59:59";
		 StringBuffer exeSql = new StringBuffer("");
		 if (companyId != null && carStyleId != null && dateStr != null) {
			 exeSql.append(" SELECT COUNT(t1.ID) FROM t_vehicle t1, t_box_operation t2 ");
			 exeSql.append(" WHERE t1.Id = t2.VEHICLE_ID ");
			 exeSql.append(" AND t1.COMPANY_ID = t2.COMPANY_ID ");
			 exeSql.append(" AND t1.CAR_STYLE_ID = ").append(carStyleId).append(" ");
			 exeSql.append(" AND t1.COMPANY_ID = ").append(companyId).append(" ");
			 exeSql.append(" AND t1.CONFIGURE_STATUS = ").append(configureStatus).append(" ");
			 exeSql.append(" AND t2.TYPE = ").append(type).append(" ");
			 exeSql.append(" AND t2.OPERATION_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append( endTime).append(" ' ");
			 
		 }
		 return exeSql.toString();
	 }

}
