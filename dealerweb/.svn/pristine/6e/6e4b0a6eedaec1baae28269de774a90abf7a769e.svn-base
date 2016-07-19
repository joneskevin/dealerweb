package com.ava.facade.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.IHibernateDao;
import com.ava.domain.entity.CarStyle;
import com.ava.facade.IReportBaseExtractionFacade;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCarManager;
import com.ava.util.DateTime;

/**
 * 报表基础数据抽取类
 * Title: Class ReportBaseExtractionFacade.java
 * Copyright: Copyright(c)2016
 * Company: BDC
 * @author liuq
 * @version 0.1
 */
@Service
public class ReportBaseExtractionFacade implements IReportBaseExtractionFacade {
	
	protected final static Logger logger = Logger.getLogger(ReportBaseExtractionFacade.class);

	@Autowired
	private IHibernateDao hibernateDao;
	
    /**
	 * 抽取数据【入口】
	 * @param extractionFlag 0、按天数抽取、1、按时间段抽取
	 * @param dayNum 抽取天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 */
	@Override
	public void extraction(Integer extractionFlag, Integer dayNum, Date startDate, Date endDate) {
		try {
			if (extractionFlag != null) {
				if (extractionFlag == GlobalConstant.FALSE && dayNum != null) {
					startDate = DateTime.minusDays(new Date(), dayNum);
					endDate = DateTime.minusDays(new Date(), 1);
				} 
				//先执行删除操作
				this.deleteReportBase(startDate, endDate);
				this.extractionByFlag(extractionFlag, dayNum, startDate, endDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
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
	 * 根据不同力度抽取
	 * @param extractionFlag 0、按天数抽取、1、按时间段抽取
	 * @param dayNum 抽取天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 */
	void extractionByFlag(Integer extractionFlag, Integer dayNum, Date startDate, Date endDate) {
		boolean extractionByDate = false;//是否按时间段抽取
		//按天数抽取
		if (extractionFlag == GlobalConstant.FALSE) {
			if (dayNum == 1) {
				//抽取一天的数据，为前一天的数据
				Date date = DateTime.minusDays(new Date(), 1);
				String dateStr = DateTime.toNormalDate(date);
				addReportBase(dateStr);
				
			} else {
				if (startDate != null && endDate != null) {
					extractionByDate = true;
				}
			}
		} 
		//按时间段抽取
		if (extractionFlag == GlobalConstant.TRUE && startDate != null && endDate != null) {
			extractionByDate = true;
		}
		if (extractionByDate) {
			List<String> dateList = DateTime.initDayList(startDate, endDate);
			if (dateList != null && dateList.size() > 0) {
				for (String dateStr : dateList) {
					addReportBase(dateStr);
				}
			}
		}
	}
	
	/**
	 * 添加到抽取表中
	 * @param dateStr
	 */
	protected void addReportBase(String dateStr) {
		//获取所有带有年款的车型
		List<CarStyle> carStyleList = CacheCarManager.getAllCarStyleByBrandId(GlobalConstant.CAR_BRAND_VM);
		try {
			if (carStyleList != null && carStyleList.size() > 0) {
				for (CarStyle carStyle : carStyleList) {
					//抽取所有数据
					String exeSql = getExecuteSql(carStyle, dateStr);
					hibernateDao.executeSQLUpdate(exeSql);
					 
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		
	}
	
	 /**
	  * 抽取基础数据信息
	  * @param carStyle
	  * @param dateStr
	  * @return
	  */
	  String getExecuteSql(CarStyle carStyle, String dateStr) {
		 Integer carStyleId = carStyle.getId();
		 StringBuffer exeSql = new StringBuffer("");
		 exeSql.append(" INSERT INTO t_report_base ( ");
		 //抽取日期信息
		 exeSql.append(" EXTRACTION_DATE, YEARS, CREATE_TIME, "); 
		 //经销商信息
		 exeSql.append(" SALE_CENTER_ID, SALE_CENTER_NAME, PROVINCE_ID, PROVINCE_NAME, CITY_ID, CITY_NAME, ");
		 exeSql.append(" COMPANY_ID, COMPANY_NAME, DEALER_CODE, DEALER_TYPE, DEALER_TYPE_NICK, IS_KEY_CITY_NICK, ");
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
		 exeSql.append(" SELECT ");
		 
		 dateStr = dateStr + " 00:00:00";
		 String yearStr = DateTime.toShortStr(DateTime.toDate(dateStr));
		 Integer years = Integer.valueOf(yearStr);
		 //抽取日期信息value
		 exeSql.append(" '").append(dateStr).append("', ").append(years).append(", ").append("sysdate(), ");
		 
		 //经销商信息value
		 exeSql.append(" vdi.saleCenterId, vdi.saleCenterName, vdi.provinceId, vdi.provinceName, vdi.cityId, vdi.cityName, ");
		 exeSql.append(" vdi.companyId, vdi.companyName, vdi.dealerCode,vdi.dealerType, vdi.dealerTypeNick, vdi.isKeyCityNick, ");
		 exeSql.append(" vdi.parentCompanyId, vdi.parentCompanyName, vdi.parentDealerCode, ");
		 
		 //车型信息value
		 String carStyleName = carStyle.getName() + " " + carStyle.getYearType();
		 exeSql.append(" ").append(carStyleId).append(", '").append(carStyleName).append("', ");
		 exeSql.append("0, ");//要求配置
		 
		 exeSql.append("0, ");//可选配置
		 exeSql.append("0, ");//实际配置
		 exeSql.append("0, ");//待安装【不启用】
		 
		 exeSql.append("0, ");//已拆除数量
		 exeSql.append("0, ");//已换装数量【不启用】
		 
		 exeSql.append("0, ");//有望客户数
		 exeSql.append("0, ");//试驾次数
		 exeSql.append("0, ");//违规次数
		 exeSql.append(" '0.0' ");//试驾率【不启用】
		 exeSql.append(" FROM view_dealer_info vdi ");//经销商视图
		 
		 return exeSql.toString();
	} 

    /**
	 * 更新业务数据【入口】
	 * @param updateFlag 0、按天数更新、1、按时间段更新
	 * @param dayNum 更新天数
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 */
	@Override
	public void updateReportBase(Integer updateFlag, Integer dayNum, Date startDate, Date endDate) {
		try {
			if (updateFlag != null) {
				if ((updateFlag == GlobalConstant.FALSE && dayNum != null) 
						|| (updateFlag == GlobalConstant.TRUE && startDate != null && endDate != null)) {
					boolean updateByDate = false;//是否按时间段更新
					//按天数更新
					if (updateFlag == GlobalConstant.FALSE) {
						if (dayNum == 1) {
							//更新一天的数据，为前一天的数据
							Date date = DateTime.minusDays(new Date(), 1);
							String dateStr = DateTime.toNormalDate(date);
							updateReportBaseBySql(dateStr);
							
						} else {
							startDate = DateTime.minusDays(new Date(), dayNum);
							endDate = DateTime.minusDays(new Date(), 1);
							updateByDate = true;
						}
					} 
					//按时间段更新
					if (updateFlag == GlobalConstant.TRUE) {
						updateByDate = true;
					} 
					if (updateByDate) {
						List<String> dateList = DateTime.initDayList(startDate, endDate);
						if (dateList != null && dateList.size() > 0) {
							for (String dateStr : dateList) {
								updateReportBaseBySql(dateStr);
							}
						}
					}
				} 
							
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
		
	/**
	 * 根据sql更新ReportBase
	 * @param dateStr
	 */
	protected void updateReportBaseBySql(String dateStr) {
		try {
			String exeSql = getExecuteBusinessDataSql(null, dateStr);
			hibernateDao.executeSQLUpdate(exeSql);
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
		
	 /**
	   * 更新业务数据SQL
	   * @param carSeries
	   * @param dateStr
	   * @return
	   */
	  String getExecuteBusinessDataSql(CarStyle carStyle, String dateStr) {
		  //要求配置
		  String denandConfigCountSql = this.getConfigCountSql(GlobalConstant.CONFIGURE_TYPE_MUST, dateStr);
		  //可选配置
		  String optionalConfigCountSql = this.getConfigCountSql(GlobalConstant.CONFIGURE_TYPE_MAY, dateStr);
		  //实际配置
		  String realityConfigCount = this.getConfigCountSql(GlobalConstant.CONFIGURE_STATUS_INSTALLED, GlobalConstant.PROPOSAL_TYPE_INSTALLATION, dateStr);
		  //已拆除数量
		  String demolitionCount = this.getConfigCountSql(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED, GlobalConstant.PROPOSAL_TYPE_DEMOLITION, dateStr);
		  
		  StringBuffer exeSql = new StringBuffer("");
		  exeSql.append(" UPDATE t_report_base rb SET");
		  exeSql.append(" DENAND_CONFIG_COUNT =  ").append("(").append(denandConfigCountSql).append("), ");//要求配置
		  exeSql.append(" OPTIONAL_CONFIG_COUNT = ").append("(").append(optionalConfigCountSql).append("), ");//可选配置
		  exeSql.append(" REALITY_CONFIG_COUNT = ").append("(").append(realityConfigCount).append("), ");//实际配置
		  exeSql.append(" DEMOLITION_COUNT = ").append("(").append(demolitionCount).append(") ");//已拆除数量
		  exeSql.append(" WHERE rb.EXTRACTION_DATE =date('").append(dateStr).append("') ");
		  
		  return exeSql.toString();
		  
	  } 
	  
    /**
	 * 查询需求配置数量SQL
	 * @param configureType
	 * @param dateStr
	 * @return
	 */
    String getConfigCountSql(Integer configureType, String dateStr) {
		String startTime = dateStr + " 00:00:00";
		String endTime = dateStr + " 23:59:59";
		StringBuffer exeSql = new StringBuffer("");
		if (dateStr != null) {
			exeSql.append(" SELECT COUNT(t1.ID) FROM t_vehicle t1, t_proposal t2 ");
			exeSql.append(" WHERE t1.Id = t2.VEHICLE_ID ");
			exeSql.append(" AND t1.COMPANY_ID = t2.COMPANY_ID ");
			exeSql.append(" AND t1.CAR_STYLE_ID = rb.CAR_STYLE_ID ");
			exeSql.append(" AND t1.COMPANY_ID = rb.COMPANY_ID");
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
	  * @param configureStatus
	  * @param type 新装、拆除
	  * @param dateStr
	  * @return
	  */
	 String getConfigCountSql(Integer configureStatus, Integer type, String dateStr) {
		 String startTime = dateStr + " 00:00:00";
		 String endTime = dateStr + " 23:59:59";
		 StringBuffer exeSql = new StringBuffer("");
		 if (dateStr != null) {
			 exeSql.append(" SELECT COUNT(t1.ID) FROM t_vehicle t1 ");
			 exeSql.append(" WHERE t1.CAR_STYLE_ID = rb.CAR_STYLE_ID ");
			 exeSql.append(" AND t1.COMPANY_ID = rb.COMPANY_ID");
			 exeSql.append(" AND t1.CONFIGURE_STATUS = ").append(configureStatus).append(" ");
			 if (type == GlobalConstant.PROPOSAL_TYPE_INSTALLATION) {
	    		exeSql.append(" AND t1.INSTALLATION_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append( endTime).append(" ' ");
			 }
    		 if (type == GlobalConstant.PROPOSAL_TYPE_DEMOLITION) {
    			//统计拆除时，查看车辆表的安装时间是否为空
     			exeSql.append(" AND t1.INSTALLATION_TIME IS NOT NULL ");
    			exeSql.append(" AND t1.DEMOLITION_TIME BETWEEN ' ").append(startTime).append(" ' ").append(" AND ' ").append( endTime).append(" ' ");
    		 }
		 }
		 return exeSql.toString();
	 }
	
	
}
	
