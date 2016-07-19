package com.ava.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IReportBaseDao;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.ReportBase;
import com.ava.facade.IReportBaseFacade;
import com.ava.resource.DbCacheResource;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCarManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;

@Service
public class ReportBaseFacade implements IReportBaseFacade {
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IReportBaseDao reportBaseDao;
	
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
				//parameters.put("id", 600);
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
	 * 根据不同的时间抽取力度抽取
	 * @param company
	 * @param dateStr
	 */
	void addReportBase(Company company, String dateStr) {
		try {
			//获取所有带有年款的车型
			List<CarStyle> carStyleList = CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
			if (carStyleList != null && carStyleList.size() > 0) {
				for (CarStyle carStyle : carStyleList) {
					 ReportBase reportBase = new ReportBase();
					//抽取所有数据
					 reportBase = getConfiguStatisticsInfo(reportBase, company, carStyle, dateStr);
					 reportBaseDao.save(reportBase); 
					 
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
	   * @return
	   */
	  ReportBase getConfiguStatisticsInfo(ReportBase reportBase, Company company, CarStyle carStyle, String dateStr) {
		 Integer companyId = company.getId();
		 Integer carStyleId = carStyle.getId();
		 if (reportBase == null) {
			 reportBase = new ReportBase();
		 }
		 //要求配置
		 Integer denandConfigCount = this.getConfigCount(companyId, carStyleId, GlobalConstant.CONFIGURE_TYPE_MUST, dateStr);
		 //可选配置
		 Integer optionalConfigCount = this.getConfigCount(companyId, carStyleId, GlobalConstant.CONFIGURE_TYPE_MAY, dateStr);
		 //实际配置
		 Integer realityConfigCount = this.getConfigCount(companyId, carStyleId, GlobalConstant.CONFIGURE_STATUS_INSTALLED, GlobalConstant.PROPOSAL_TYPE_INSTALLATION, dateStr);
		 //已拆除数量
		 Integer demolitionCount = this.getConfigCount(companyId, carStyleId, GlobalConstant.CONFIGURE_STATUS_UNINSTALLED, GlobalConstant.PROPOSAL_TYPE_DEMOLITION, dateStr);
		
		 //车型
		 reportBase.setCarStyleId(carStyleId);
		 reportBase.setCarStyleName(carStyle.getName() + " " + carStyle.getYearType());
		 
		 reportBase.setDenandConfigCount(denandConfigCount);
		 reportBase.setOptionalConfigCount(optionalConfigCount);
		 reportBase.setRealityConfigCount(realityConfigCount);
		 reportBase.setDemolitionCount(demolitionCount);
		 reportBase.setWaitInstallationCount(0);//不启用
		 reportBase.setReplaceCount(0);//不启用
		 
		 //分销中心
		 Integer saleCenterId = company.getParentId();
		 reportBase.setSaleCenterId(saleCenterId);
		 reportBase.setSaleCenterName(CacheOrgManager.getOrgName(saleCenterId));
		 
		 //经销商
		 reportBase.setCompanyId(companyId);
		 reportBase.setCompanyName(company.getCnName());
		 reportBase.setDealerCode(company.getDealerCode());
		 reportBase.setIsKeyCityNick(company.getIsKeyCity_Nick());
		 reportBase.setDealerType(company.getDealerType());
		 reportBase.setDealerTypeNick(company.getDealerType_Nick());
		 
		 //省份
		 Integer provinceId = company.getRegionProvinceId();
		 reportBase.setProvinceId(provinceId);
		 reportBase.setProvinceName(DbCacheResource.getDataDictionaryNameById(provinceId));
		 
		 //城市
		 Integer cityId = company.getRegionCityId();
		 reportBase.setCityId(cityId);
		 reportBase.setCityName(DbCacheResource.getDataDictionaryNameById(cityId));
		 
		 dateStr = dateStr + " 00:00:00";
		 reportBase.setExtractionDate(DateTime.toDate(dateStr));
		 String years = DateTime.toShortStr(DateTime.toDate(dateStr));
		 reportBase.setYears(Integer.valueOf(years));
		 reportBase.setCreateTime(new Date());
		 return reportBase;
		 
	} 
	

	 /**
	  * 查询需求配置数量
	  * @author liuq 
	  * @version 0.1 
	  * @param companyId
	  * @param carStyleId
	  * @param configureType 要求配置、可选配置
	  * @param dateStr
	  * @return
	  */
	 Integer getConfigCount(Integer companyId, Integer carStyleId, Integer configureType, String dateStr) {
		String startTime = dateStr + " 00:00:00";
		String endTime = dateStr + " 23:59:59";
		Integer configCount = 0;
		StringBuffer exeSql = new StringBuffer("");
		if (companyId != null && carStyleId != null && dateStr != null) {
			exeSql.append(" SELECT COUNT(t1.ID) FROM t_vehicle t1, t_proposal t2 ");
			exeSql.append(" WHERE t1.Id = t2.VEHICLE_ID ");
			exeSql.append(" AND t1.COMPANY_ID = t2.COMPANY_ID ");
			exeSql.append(" AND t1.CAR_STYLE_ID = ").append(carStyleId).append(" ");
			exeSql.append(" AND t1.COMPANY_ID = ").append(companyId).append(" ");
			exeSql.append(" AND t1.CONFIGURE_TYPE = ").append(configureType ).append(" ");
			exeSql.append(" AND t1.CONFIGURE_STATUS IN ( ").append(GlobalConstant.CONFIGURE_STATUS_WATTING).append(",").append(GlobalConstant.CONFIGURE_STATUS_INSTALLED ).append(" ) ");
			exeSql.append(" AND t2.TYPE IN ( ").append(GlobalConstant.PROPOSAL_TYPE_INSTALLATION).append(",").append(GlobalConstant.PROPOSAL_TYPE_REPLACE ).append(" ) ");
			exeSql.append(" AND t2.PROPOSAL_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append(endTime).append(" ' ");
			
			if (exeSql.length() > 0) {
				Integer count = hibernateDao.getSqlCount(exeSql.toString());
				 if (count != null && count > 0) {
					 configCount = count;
				 }
			}
		}
		return configCount;
	 }
	 
	 /**
	  * 查询已安装或已拆除数量
	  * @author liuq 
	  * @version 0.1 
	  * @param companyId
	  * @param carStyleId
	  * @param configureStatus
	  * @param type 新装、拆除
	  * @param dateStr
	  * @return
	  */
	 Integer getConfigCount(Integer companyId, Integer carStyleId, Integer configureStatus, Integer type, String dateStr) {
		 String startTime = dateStr + " 00:00:00";
		 String endTime = dateStr + " 23:59:59";
		 Integer configCount = 0;
		 StringBuffer exeSql = new StringBuffer("");
		 if (companyId != null && carStyleId != null && dateStr != null) {
			 exeSql.append(" SELECT COUNT(t1.ID) FROM t_vehicle t1, t_box_operation t2 ");
			 exeSql.append(" WHERE t1.Id = t2.VEHICLE_ID ");
			 exeSql.append(" AND t1.COMPANY_ID = t2.COMPANY_ID ");
			 exeSql.append(" AND t1.CAR_STYLE_ID = ").append(carStyleId).append(" ");
			 exeSql.append(" AND t1.COMPANY_ID = ").append(companyId).append(" ");
			 exeSql.append(" AND t1.CONFIGURE_STATUS = ").append(configureStatus).append(" ");
			 exeSql.append(" AND t2.TYPE = ").append(type).append(" ");
			 exeSql.append(" AND t2.OPERATION_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append(endTime).append(" ' ");
			 
			 if (exeSql.length() > 0) {
				 Integer count = hibernateDao.getSqlCount(exeSql.toString());
				 if (count != null && count > 0) {
					 configCount = count;
				 }
			 }
		 }
		 return configCount;
	 }
	
}
