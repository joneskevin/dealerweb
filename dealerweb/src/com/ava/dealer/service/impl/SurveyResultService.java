package com.ava.dealer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.ICompanyTypeDao;
import com.ava.dao.IOperationLogDao;
import com.ava.dao.ISurveyCarStyleDao;
import com.ava.dao.ISurveyResultDao;
import com.ava.dealer.service.ISurveyResultService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.CompanyType;
import com.ava.domain.entity.OperationLog;
import com.ava.domain.entity.SurveyCarStyle;
import com.ava.domain.entity.SurveyResult;
import com.ava.domain.entity.User;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.SurveyResultFindVO;
import com.ava.domain.vo.SurveyResultStaticVO;
import com.ava.domain.vo.SurveyResultVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgVehicleManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.cache.CacheVehicleManager;

@Service("dealer.surveyResultService")
public class SurveyResultService extends BaseService implements ISurveyResultService {

	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private ISurveyCarStyleDao surveyCarStyleDao;
	
	@Autowired
	private ISurveyResultDao surveyResultDao;
	
	@Autowired
	private ICompanyTypeDao companyTypeDao;
	
	@Autowired
	private IOperationLogDao operationLogDao;
	
	@Override
	public ResponseData displayAddSurveyResult(SurveyResultVO surveyResultAdd, Company company) {
		ResponseData rd = new ResponseData(0);
		if (surveyResultAdd == null) {
			surveyResultAdd = new SurveyResultVO();
		}
		
		Integer dealerType = 0;
		if (company != null && company.getDealerType() != null) {
			dealerType = company.getDealerType();
		}
		
		if (dealerType == GlobalConstant.DEALER_TYPE_4S_SHOP) {
			getCarStyleList(surveyResultAdd, 1, 1);
			surveyResultAdd.setFirstSelectType(GlobalConstant.TRUE);
			
		} else if (dealerType == GlobalConstant.DEALER_TYPE_NON_STRAIGHT_DIRECT_SHOP) {
			getCarStyleList(surveyResultAdd, 3, 1);
			surveyResultAdd.setFirstSelectType(GlobalConstant.FALSE);
			
		} else if (dealerType == GlobalConstant.DEALER_TYPE_STRAIGHT_DIRECT_SHOP) {
			getCarStyleList(surveyResultAdd, 4, 1);
			surveyResultAdd.setFirstSelectType(GlobalConstant.FALSE);
			
		} else if (dealerType == GlobalConstant.DEALER_TYPE_CITY_SHOWROOM) {
			getCarStyleList(surveyResultAdd, 5, 1);
			surveyResultAdd.setFirstSelectType(GlobalConstant.FALSE);
		}
		
	    rd.put("surveyResultAdd", surveyResultAdd);
		return rd;
	}
	
	@Override
	public ResponseData displayAddSurveyResultStatic(SurveyResultStaticVO surveyResultStaticVO, Company company) {
		ResponseData rd = new ResponseData(0);
		if (surveyResultStaticVO == null) {
			surveyResultStaticVO = new SurveyResultStaticVO();
		}
		
		Integer type = 0;//不参与问卷
		if (company != null && StringUtils.isNotBlank(company.getDealerCode())) {
			HashMap<Object, Object> parameters = new HashMap<Object, Object>();
			parameters.put("dealerCode", company.getDealerCode());
			List<CompanyType> companyTypeList = companyTypeDao.find(parameters, null);
			if (companyTypeList != null && companyTypeList.size() > 0) {
				CompanyType companyType = companyTypeList.get(0);
				type = companyType.getType();
			}
		}
		
		
		surveyResultStaticVO.setType(type);
	    rd.put("surveyResultStaticVO", surveyResultStaticVO);
		return rd;
	}
	
	/**
	 * 设置问卷车型列表
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @param dealerType 经销商类型 (4S店销量>20004S(1)、4S店销量<2000(2)、直营店(3)、直管直营店(4)、城市展厅(5))
	 * @param groupType 组别
	 * @return
	 */
	private SurveyResultVO getCarStyleList (SurveyResultVO surveyResultAdd, Integer dealerType, Integer groupType) {
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("dealerType", dealerType);
		parameters.put("groupType", groupType);
		ArrayList<SurveyCarStyle> carStyleList = (ArrayList<SurveyCarStyle>) surveyCarStyleDao.find(parameters, " order by id asc");
		if (carStyleList != null && carStyleList.size() > 0) {
			for (SurveyCarStyle surveyCarStyle : carStyleList) {
				if (groupType == 1) {
					surveyResultAdd.getFirstAllCarStyles().put(surveyCarStyle.getId(), surveyCarStyle.getCarStyleName());
				} else if (groupType == 2) {
					surveyResultAdd.getSecondAllCarStyles().put(surveyCarStyle.getId(), surveyCarStyle.getCarStyleName());
				} else if (groupType == 3) {
					surveyResultAdd.getThreeAllCarStyles().put(surveyCarStyle.getId(), surveyCarStyle.getCarStyleName());
				}
				
			}	
		}
		return surveyResultAdd;
	}

	@Override
	public ResponseData add(SurveyResultVO surveyResultVO, Integer companyId) {
		ResponseData rd = new ResponseData(1);
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List<SurveyResult> surveyResultList = surveyResultDao.find(parameters, "");
		if (surveyResultList != null && surveyResultList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("您已经参与过问卷调查，请勿重复提交！");
			return rd;
		}
		List<Integer> selectFirstCarStyles = surveyResultVO.getSelectFirstCarStyles();
		
		Boolean valid = this.checkSelectValid(selectFirstCarStyles);
		if (!valid) {
			rd.setCode(-1);
			rd.setMessage("请【至少选择一个车型】或者【只选择不配置Passat车型】！");
			return rd;
		}
		
		List<Integer> selectSecondCarStyles = surveyResultVO.getSelectSecondCarStyles();
		valid = this.checkSelectValid(selectSecondCarStyles);
		if (!valid) {
			rd.setCode(-1);
			rd.setMessage("请【至少选择一个车型】或者【只选择不配置Passat车型】！");
			return rd;
		}
		
		List<Integer> selectThreeCarStyles = surveyResultVO.getSelectThreeCarStyles();
		valid = this.checkSelectValid(selectThreeCarStyles);
		if (!valid) {
			rd.setCode(-1);
			rd.setMessage("请【至少选择一个车型】或者【只选择不配置Passat车型】！");
			return rd;
		}
		
		if (selectFirstCarStyles != null) {
			for (Integer carStyleId : selectFirstCarStyles) {
				SurveyResult surveyResult = new SurveyResult(companyId, carStyleId, new Date());
				surveyResultDao.save(surveyResult);
			}
		}
		
		if (selectSecondCarStyles != null) {
			for (Integer carStyleId : selectSecondCarStyles) {
				SurveyResult surveyResult = new SurveyResult(companyId, carStyleId, new Date());
				surveyResultDao.save(surveyResult);
			}
		}

		if (selectThreeCarStyles != null) {
			for (Integer carStyleId : selectThreeCarStyles) {
				SurveyResult surveyResult = new SurveyResult(companyId, carStyleId, new Date());
				surveyResultDao.save(surveyResult);
			}
		}
		rd.setMessage("问卷提交成功，感谢您的参与！");
		return rd;
	}
	
	/**
	 * 判断选择是否合法
	 * 是否需要判断只能选择不配置可选车型一项或者请至少选择一个车型
	 * @author liuq 
	 * @version 0.1 
	 * @param selectCarStyles
	 * @return
	 */
	private Boolean checkSelectValid(List<Integer> selectCarStyles) {
		Boolean valid = true;
		Boolean valid1 = true;
		Boolean valid2 = true;
		if (selectCarStyles != null) {
			for (Integer carStyleId : selectCarStyles) {
				SurveyCarStyle surveyCarStyle = surveyCarStyleDao.get(carStyleId);
				if (surveyCarStyle.getFlag() == GlobalConstant.TRUE) {
					valid1 = false;
				}
				if (surveyCarStyle.getFlag() == GlobalConstant.FALSE) {
					valid2 = false;
				}
			}
		}
		if (valid1 == false && valid2 == false) {
			valid = false;
		}
		return valid;
	}

	@Override
	public ResponseData delete(Integer surveyResultId, Integer companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseData edit(SurveyResult surveyResult, Integer companyId,
			Integer flag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseData listSurveyResult(TransMsg transMsg, SurveyResultVO surveyResultVO, Integer companyId, Integer userId, boolean isExport) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		exeSql.append(" vdi.dealerTypeNick as surveyDealerTypeNick, ");
		exeSql.append(" (CASE ct.TYPE ");
		exeSql.append(" WHEN ").append(4).append(" THEN '四星级' ");
		exeSql.append(" WHEN ").append(5).append(" THEN '五星级' ");
		exeSql.append(" WHEN ").append(6).append(" THEN '六星级' ");
		exeSql.append(" WHEN ").append(7).append(" THEN '七星级' ");
		exeSql.append(" ELSE '非星级' ");
		exeSql.append(" END) as level, ");
		exeSql.append(" '2.0T DL Comfort adv.' as carStyleName, 1 as amount, sr.CREATION_TIME as creationTime ");
		exeSql.append(" FROM t_survey_result sr ");
		exeCountSql.append(" FROM t_survey_result sr ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = sr.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = sr.COMPANY_ID ");
		
		String surveyCarStyleSql = " LEFT JOIN t_company_type ct on ct.DEALER_CODE = vdi.dealerCode ";
		exeSql.append(surveyCarStyleSql);
		exeCountSql.append(surveyCarStyleSql);
		
		//查看待安装、已安装的车辆
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(companyId, userId));
		exeCountSql.append(getAccessSql(companyId, userId));
		//添加经销商查询条件
		exeSql.append(getDealerConditionSql(surveyResultVO.getDealer(), userId));
		exeCountSql.append(getDealerConditionSql(surveyResultVO.getDealer(), userId));
		exeSql.append(" ORDER BY ct.TYPE desc, sr.id asc");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<SurveyResultFindVO> surveyResultFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), SurveyResultFindVO.class);
		rd.setFirstItem(surveyResultFindVOList);
		return rd; 
	}

	@Override
	public List<SurveyResult> find(HashMap<Object, Object> parameters,
			String additionalCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SurveyResult getSurveyResult(Integer surveyResultId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseData addSurveyResultStatic(
			SurveyResultStaticVO surveyResultStaticVO, Integer companyId) {
		ResponseData rd = new ResponseData(1);
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List<SurveyResult> surveyResultList = surveyResultDao.find(parameters, "");
		if (surveyResultList != null && surveyResultList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("您已经参与过车型配置调研，请勿重复提交！");
			return rd;
		}
		this.addSelectCarStyles(surveyResultStaticVO.getSelectFirstCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectSecondCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectThreeCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectFourCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectFiveCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectSixCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectSevenCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectEightCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectNineCarStyles(), companyId);
		this.addSelectCarStyles(surveyResultStaticVO.getSelectTenCarStyles(), companyId);
		rd.setMessage("配置调研已提交成功，感谢您的参与！！");
		return rd;
	}
	
	void addSelectCarStyles(List<Integer> selectCarStyles, Integer companyId) {
		if (selectCarStyles != null) {
			for (Integer carStyleId : selectCarStyles) {
				SurveyResult surveyResult = new SurveyResult(companyId, carStyleId, new Date());
				surveyResultDao.save(surveyResult);
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseData deleteSurveyResult(Integer companyId, Integer userId) {
		ResponseData rd = new ResponseData(0);

		StringBuffer exeHql = new StringBuffer("");
		exeHql.append(" DELETE FROM SurveyResult WHERE companyId = " + companyId);
		hibernateDao.executeUpdate(exeHql.toString());
		
		OperationLog operationLog = new OperationLog();
		operationLog.setType(GlobalConstant.OPERATION_TYPE_DEL_CAR_STYLE_SURVEY);
		operationLog.setOperatorId(userId);
		operationLog.setOperatorTime(new Date());
		operationLog.setCompanyId(companyId);
		User user = CacheUserManager.getUser(userId);
		Company company = CacheCompanyManager.getCompany(companyId);
		operationLog.setRemark("由【"+ user.getNickName() +"】删除网络代码【" + company.getDealerCode() 
				+ "】的车型配置调研");
		operationLogDao.save(operationLog);
		
		rd.setCode(1);
		rd.setMessage("经销商信息删除成功");
		return rd;
	}

	@Override
	public ResponseData addSurveyLevel(Integer companyId) {
		ResponseData rd = new ResponseData(1);
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List<SurveyResult> surveyResultList = surveyResultDao.find(parameters, "");
		if (surveyResultList != null && surveyResultList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("您的配车申请已经提交，不需要重复申请！");
			return rd;
		}
		SurveyResult surveyResult = new SurveyResult(companyId, 1, new Date());
		surveyResultDao.save(surveyResult);
		rd.setMessage("您的配车申请已经提交，感谢您的参与！");
		return rd;
	}

	@Override
	public ResponseData querySurveyLevel(Integer companyId) {
		ResponseData rd = new ResponseData(1);
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("companyId", companyId);
		List<SurveyResult> surveyResultList = surveyResultDao.find(parameters, "");
		if (surveyResultList != null && surveyResultList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("您的配车申请已经提交，不需要重复申请！");
			return rd;
		}
		return rd;
	}
	
	

}
