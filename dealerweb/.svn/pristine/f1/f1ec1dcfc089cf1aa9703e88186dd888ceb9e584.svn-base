package com.ava.baseSystem.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.dao.ICarStyleDao;
import com.ava.dao.ICompanyCarStyleRelationDao;
import com.ava.dao.ICompanyDao;
import com.ava.dao.IOrgDao;
import com.ava.dao.IUserDao;
import com.ava.dao.IVehicleDao;
import com.ava.domain.entity.AddPlateNumberCity;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.CompanyCarStyleRelation;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.CarStyleVO;
import com.ava.domain.vo.DealerPointResultVO;
import com.ava.domain.vo.DealerPointVO;
import com.ava.domain.vo.LocationVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgVehicleManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

@Service
public class CompanyService implements ICompanyService {	
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IOrgDao orgDao;
	
	@Autowired
	private ICarStyleDao carStyleDao;
	
	@Autowired
	private ICompanyCarStyleRelationDao companyCarStyleRelationDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	/** 根据ID获得组织对象 */
	public Company getOrgById(Integer orgId){
		return companyDao.get(orgId);
	}
	
	public Company getCompanyByDealerCode(String dealerCode){
		Company company = new Company();
		if (dealerCode == null || dealerCode.length() == 0) {
			return null;
		}
		HashMap parameters = new HashMap();
		parameters.put("dealerCode", dealerCode);
		List result = companyDao.find(parameters, "");
		if (result != null && result.size() > 0) {
			company = (Company) result.get(0);
			parameters.clear();
			parameters.put("cityId", company.getRegionCityId());
			AddPlateNumberCity addPlateNumberCity = hibernateDao.get(" AddPlateNumberCity ", parameters);
			if (addPlateNumberCity != null) {
				company.setIsRestrictionCity(GlobalConstant.TRUE);
			} else {
				company.setIsRestrictionCity(GlobalConstant.FALSE);
			}
			return company;
		}
		return null;
	}
	
	/**编辑组织信息*/
	public void editOrg(Company org){
		if (org!=null){
			companyDao.edit(org);
		}
	}

	/**
	 * 判断公司全称是否重复
	 * 
	 * @param cnName
	 * @return
	 */
	private boolean cnNameIsExistence( String cnName ) {
		if (cnName == null || cnName.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("cnName", cnName);
		List result = companyDao.findExist(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * 判断中文简称是否重复
	 * 
	 * @param companyId
	 * @param name
	 * @return
	 */
	private boolean nameIsExistence(String name) {
		if (name == null || name.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("name", name);
		List result = companyDao.findExist(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
		
	}

	/**显示编辑组织信息*/
	public ResponseData displayEditQrgInfo(Integer companyId) {
		ResponseData rd = new ResponseData(0);
		Map data = new HashMap();
		rd.setData(data);
		
		Company company = companyDao.get(companyId);
		Boolean orgStructureNotBuild = false;
		Boolean orgInfoNotBuild = false;
		
		HashMap map = new HashMap();
		map.put("companyId", companyId);
		List userList = userDao.find(map, " ORDER BY id DESC");
		if(userList==null || userList.size()<=1){        	
        	//如果组织只有admin@一个用户，请添加联系人
			orgStructureNotBuild = true;
        }
				
		if( company.getCnName()==null || company.getCnName().length()<=0 ){
			//组织信息还未填写
			orgInfoNotBuild = true;
		}
		
		data.put("company", company);
		data.put("orgStructureNotBuild", orgStructureNotBuild);
		data.put("orgInfoNotBuild", orgInfoNotBuild);
		return rd;
	}

	/**编辑组织信息*/
	public ResponseData editOrgInfo(Company company) {
		ResponseData rd = new ResponseData(0);
		Company dbCompany = companyDao.get(company.getId());
		
		if (company.getCnName()!= null
				&& !company.getCnName().equalsIgnoreCase(dbCompany.getCnName())) {
			// 如果用户要修改公司全称，则判断新的公司全称是否已经存在
			if (cnNameIsExistence(company.getCnName())) {
				rd.setCode(-1);
				rd.setMessage("公司全称已重复！");
				
			}
		}
		
		if (company.getName()!= null
				&& !company.getName().equalsIgnoreCase(dbCompany.getName())) {
			// 如果用户要修改中文简称，则判断新的中文简称是否已经存在
			if (nameIsExistence(company.getName())) {
				rd.setCode(-2);
				rd.setMessage("中文简称已重复！");
				
			}
		}
		
		if ( company.getOfficialWebsiteFlag() == null){
			//选择不启用官网，则置零
			company.setOfficialWebsiteFlag(0);
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbCompany, company);
		companyDao.edit(dbCompany);
		rd.setFirstItem(dbCompany);
		
		//移除缓存中的org对象，让其自动加载
		CacheCompanyManager.removeCompany(dbCompany.getId());
		
		rd.setCode(1);
		rd.setMessage("资料修改成功！");
		return rd;
	}

	@Override
	public ResponseData listCompany(TransMsg transMsg, Company company,
			Integer currentCompanyId, HttpServletRequest request, boolean isExport) {
		ResponseData rd = new ResponseData(0);
		if(isExport == true) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}

		String additionalCondition = "";
		
		if (currentCompanyId == GlobalConstant.DEFAULT_GROUP_ORG_ID) {
			currentCompanyId = Integer.valueOf(GlobalConstant.NETWORK_DEVELOPMENT_ID);
		}
		
		Integer parentId = company.getParentId();
		Org org = CacheOrgManager.get(parentId);
		if (org != null) {
			additionalCondition += " and parentId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(parentId, GlobalConstant.TRUE) + ")";
		} else {
			additionalCondition += " and parentId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE) + ")";
		}
		
		if (company.getDealerCode() != null && company.getDealerCode().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "dealerCode",
					company.getDealerCode());
		}
		
		if (company.getCnName() != null && company.getCnName().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "cnName",
					company.getCnName());
		}
		
		if (company.getIsKeyCity() != null && company.getIsKeyCity().intValue() >= 0) {
			transMsg.put("isKeyCity", company.getIsKeyCity());
		}
		
		if (company.getDealerType() != null && company.getDealerType().intValue() >= 0) {
			transMsg.put("dealerType", company.getDealerType());
		}
		
		if (company.getDeletionFlag() != null) {
			transMsg.put("deletionFlag", company.getDeletionFlag());
		}

		additionalCondition = additionalCondition + " order by parentId asc , dealerType asc,  dealerCode asc";
		List companyList = companyDao.findByPage(transMsg, additionalCondition);
        
		rd.setFirstItem(companyList);
        rd.setCode(1);
		return rd;
	}

	@Override
	public ResponseData viewCompany(Integer companyId) {
		ResponseData rd = new ResponseData(0);
		Company company = companyDao.get(companyId);
		rd.put("company", company);
		return rd;
	}

	@Override
	public ResponseData displayAddCompany(Company companyAdd) {
		ResponseData rd = new ResponseData(0);
		if (companyAdd == null) {
			companyAdd = new Company();
		}
		
		rd.put("companyAdd", companyAdd);
		return rd;
	}

	@Override
	public ResponseData addDealer(Company company) throws IOException {
		ResponseData rd = new ResponseData(0);
		
		Org org = company;
		Integer parentId = org.getParentId();
		Org parentOrg = orgDao.get(parentId);
		if (parentOrg == null) {
			rd.setCode(-1);
			rd.setMessage("父节点不存在！");
			return rd;
		} else {
			if (parentOrg.getIsLeaf() == null
					|| GlobalConstant.TRUE == parentOrg.getIsLeaf().intValue()) {
				parentOrg.setIsLeaf(GlobalConstant.FALSE);
				orgDao.edit(parentOrg);
			}
		}

		HashMap parameters = new HashMap();
		parameters.put("dealerCode", company.getDealerCode());
		List companyList = companyDao.findExist(parameters, "");
		if (companyList != null && companyList.size() > 0) {
			rd.setCode(-2);
			rd.setMessage("网络代码已经存在！");
			return rd;
		}
		
		parameters.clear();
		parameters.put("cnName", company.getCnName());
		companyList = companyDao.findExist(parameters, "");
		if (companyList != null && companyList.size() > 0) {
			// 在登录者所在公司的组织结构中，该名称已存在
			rd.setCode(-3);
			rd.setMessage("经销商名称已经存在！");
			return rd;
		}
		
		Integer levelId = 0;
		// 如果父节点为0，则层级为1
		if (parentId == 0 || "0".equals(parentId)) {
			levelId = 1;
		} else {
			// 如果父节点不为0，则层级为上级的层级+1
			levelId = parentOrg.getLevelId() + 1;
		}

		org.init();
		Integer dealerType = company.getDealerType();
		//如果是网络形态选择的为直营店
		if (dealerType == GlobalConstant.DEALER_TYPE_NON_STRAIGHT_DIRECT_SHOP) {
			Integer parentCompanyId = company.getCompanyId();
			Company parentCompany = CacheCompanyManager.getCompany(parentCompanyId);
			if (parentCompany != null && parentCompany.getDealerType() == GlobalConstant.DEALER_TYPE_4S_SHOP ) {
				org.setCompanyId(parentCompanyId);
			} else {
				rd.setCode(-1);
				rd.setMessage("请选择4S店");
				return rd;
			}
		} else {
			org.setCompanyId(parentId);
		}
		org.setName(company.getCnName());
		org.setLevelId(levelId);

		if (company.getContactAddress() != null && company.getContactAddress().trim().length() > 0) {
			//通过经销商的位置调用百度API接口
			LocationVO dealerLocationVO = getDealerLocation(company.getContactAddress());
			 if (dealerLocationVO != null && dealerLocationVO.getLat() != null && dealerLocationVO.getLng() != null) {
				 company.setBaiduLat(dealerLocationVO.getLat());
				 company.setBaiduLng(dealerLocationVO.getLng());
			 }
		}

		Integer newId = null;// 抽象组织对象持久化后产生的ID
		
		MyBeanUtils.copyPropertiesContainsDate(company, org);
		company.init();
		company.setStartWorkTime(GlobalConstant.DEALER_START_WORK_TIME);
		company.setEndWorkTime(GlobalConstant.DEALER_END_WORK_TIME);
		company.setCreationTime(DateTime.getTimestamp());
		newId = (Integer) companyDao.save(company);

		if (newId != null) {
			rd.setFirstItem(company);
			CacheOrgManager.clear();
			CacheCompanyManager.clear();
			CacheOrgWithFilterManager.clear();
			CacheOrgVehicleManager.clear();

			rd.setCode(1);
			rd.setMessage("经销商新增成功");
			return rd;
		}

		rd.setCode(0);
		rd.setMessage("经销商新增失败");
		return rd;
	}

	@Override
	public ResponseData displayEditCompany(Integer companyId) {
		ResponseData rd = new ResponseData(0);

		Company companyEdit = companyDao.get(companyId);
		rd.put("company", companyEdit);
		return rd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseData editCompany(Company company) {
		ResponseData rd=new ResponseData(0);
		Integer companyId = company.getId();
		Company dbCompany = companyDao.get(companyId);
		String dbDealerCode = dbCompany.getDealerCode();
		String currentDealerCode = company.getDealerCode();
		Integer deletionFlag = company.getDeletionFlag();
		
		if (currentDealerCode !=null && !currentDealerCode.equalsIgnoreCase(dbDealerCode)) {
			if (this.dealerCodeIsExistence(currentDealerCode)) {
				rd.setCode(-1);
				rd.setMessage("经销商信息保存失败：新的网络代码已存在！");
				return rd;
			}
		}
		
		if (company.getCnName() !=null && !company.getCnName().equalsIgnoreCase(dbCompany.getCnName() )) {
			if (this.cNameIsExistence(company.getCnName())) {
				rd.setCode(-1);
				rd.setMessage("经销商信息保存失败：新的经销商已存在！");
				return rd;
			}
		}
		
		if (company.getEmail() != null && !company.getEmail().equalsIgnoreCase(dbCompany.getEmail())) {
			if (this.emailIsExistence(company.getEmail())) {
				rd.setCode(-1);
				rd.setMessage("经销商信息保存失败：新的E-Mail已存在！");
				return rd;
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbCompany, company);
		dbCompany.setName(company.getCnName());
		if (deletionFlag == GlobalConstant.TRUE) {
			dbCompany.setDeletionTime(new Timestamp(new Date().getTime()));
		} else {
			dbCompany.setDeletionTime(null);
		}
		companyDao.edit(dbCompany);
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("companyId", companyId);
		List<User> userList = userDao.findAll(parameters, "");
		if (userList != null && userList.size() > 0) {
			for(User user: userList){
				user.setLoginName(currentDealerCode);
				user.setNickName(currentDealerCode);
				if (deletionFlag == GlobalConstant.TRUE) {
					user.setDeletionFlag(GlobalConstant.TRUE);
					user.setDeletionTime(DateTime.toNormalDotDateTime(new Date()));
				} else {
					user.setDeletionFlag(GlobalConstant.FALSE);
					user.setDeletionTime(null);
				}
				userDao.edit(user);
				// 从用户缓存表移除该用户对象
				CacheUserManager.removeUser(user.getId());
			}
		}

		CacheOrgManager.clear();
		CacheCompanyManager.clear();
		CacheOrgWithFilterManager.clear();
		CacheOrgVehicleManager.clear();
		
		rd.setFirstItem(company);
		rd.setCode(1);
		rd.setMessage("经销商信息修改");
		return rd;
	}

	public boolean dealerCodeIsExistence(String dealerCode) {
		if (dealerCode == null || dealerCode.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("dealerCode", dealerCode);
		List result = companyDao.findExist(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean cNameIsExistence(String cnName) {
		if (cnName == null || cnName.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("cnName", cnName);// 经销商名称
		List result = companyDao.findExist(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 查找email是否存在
	 * 
	 * @param email
	 * @return Boolean
	 */
	public boolean emailIsExistence(String email) {
		if (email == null || email.length() <= 0) {
			return false;
		}
		HashMap parameters = new HashMap();
		parameters.put("email", email);
		List result = companyDao.findExist(parameters, "");
		if (result != null && result.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseData deleteCompany(Integer companyId) {
		ResponseData rd=new ResponseData(0);
		
		HashMap parameters = new HashMap();
		parameters.put("companyId", companyId);
		List userList = userDao.find(parameters, "");
		if (userList != null && userList.size() > 0) {
			rd.setCode(-1);
			rd.setMessage("该经销商下有用户，请先删除用户");
			return rd;
		}
		Company dbCompany = companyDao.get(companyId);
		
		dbCompany.setDeletionFlag(GlobalConstant.TRUE);
		companyDao.edit(dbCompany);

		CacheOrgManager.clear();
		CacheCompanyManager.clear();
		CacheOrgWithFilterManager.clear();
		CacheOrgVehicleManager.clear();
		
		rd.setFirstItem(dbCompany);
		rd.setCode(1);
		rd.setMessage("经销商信息删除成功");
		return rd;
	}


	@Override
	public ResponseData displayConfigCarStyle(Integer companyId) {
		ResponseData rd = new ResponseData(0);
        
		Company company = companyDao.get(companyId);
		CarStyleVO carStyleVO = new CarStyleVO(null);
		company.setCarStyleVO(carStyleVO);
		rd.put("company", company);
		
		HashMap pHashMap = new HashMap();
		pHashMap.put("level", GlobalConstant.FALSE); //层级、基础的车型层级为0
		List<CarStyle>  carStyleList = carStyleDao.find(pHashMap, "");
		List  carStyleVOList = new ArrayList(); 
		
		//设置配置方式
		if (carStyleList != null && carStyleList.size() > 0) {
			for (CarStyle carStyle : carStyleList) {
				carStyleVO = new CarStyleVO(carStyle);
				pHashMap.clear();
				pHashMap.put("carStyleId", carStyle.getId());
				pHashMap.put("companyId", companyId);
				List<CompanyCarStyleRelation> companyCarStyleRelationList = companyCarStyleRelationDao.find(pHashMap, "");
				if (companyCarStyleRelationList != null && companyCarStyleRelationList.size() > 0) {
					for (CompanyCarStyleRelation companyCarStyleRelation : companyCarStyleRelationList) {
						carStyleVO.setCongfigType(companyCarStyleRelation.getConfigType());
					}
				} else {
					carStyleVO.setCongfigType(GlobalConstant.CONFIGURE_TYPE_NO);
				}
				
				carStyleVOList.add(carStyleVO);
			}
		}
		
		rd.put("carStyleVOList",carStyleVOList);
		rd.setCode(1);
		return rd;
	}

	@Override
	public Company getCompanyByVehicleId(Integer vehicleId) {
		if (vehicleId == null) {
			return null;
		}
		
		Vehicle vehicle = vehicleDao.get(vehicleId);
		if (vehicle == null) {
			return null;
		}
		
		Company company = new Company();
		if (vehicle.getCompanyId() != null) {
			company = companyDao.get(vehicle.getCompanyId());
		}
		
		return company;
	}
	
	@Override
	public void initDealerPoints() throws IOException {
		HashMap<Object,Object> parameters = new HashMap<Object,Object>();
		List<Company> dealerlist = companyDao.find(parameters, "");
		if (dealerlist != null && dealerlist.size() > 0) {
			for (Company company : dealerlist) {
				if (company != null && company.getContactAddress() != null && company.getContactAddress().trim().length() > 0) {
					//通过经销商的位置调用百度API接口
					LocationVO dealerLocationVO = getDealerLocation(company.getContactAddress());
					 if (dealerLocationVO != null && dealerLocationVO.getLat() != null && dealerLocationVO.getLng() != null) {
						 company.setBaiduLat(dealerLocationVO.getLat());
						 company.setBaiduLng(dealerLocationVO.getLng());
						 this.editCompany(company);
					 }
				}
			}
		}
	}
	
	/**
	 * 通过经销商地址信息调用百度搜索API,返回查询到第一个坐标点
	 * @param dealerAddree 
	 * @return
	 * @throws IOException
	 */
	public static LocationVO getDealerLocation(String dealerAddree) throws IOException{
		LocationVO  dealerLocationVO = null;
		if (dealerAddree != null) {
			 DealerPointResultVO dealerPointResultVO =
				 readJsonFromUrl("http://api.map.baidu.com/place/v2/search?query="+ dealerAddree +"&region=全国&output=json&ak=" + GlobalConstant.BAIDU_MAP_AK);
			 if (dealerPointResultVO != null && dealerPointResultVO.getLocation() != null) {
				 dealerLocationVO = dealerPointResultVO.getLocation();
			 }
		 }
		 return dealerLocationVO;
	}

	public static DealerPointResultVO readJsonFromUrl(String url) throws IOException {
	    InputStream is = new URL(url).openStream();
	    try {
	      DealerPointResultVO dealerPointResultVO = null;
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject jsonObj = JSONObject.fromObject(jsonText);
	      DealerPointVO dealerPointVO = (DealerPointVO) JSONObject.toBean(jsonObj, DealerPointVO.class);
	      if (dealerPointVO != null && dealerPointVO.getStatus() == GlobalConstant.FALSE) {
	    	  List<DealerPointResultVO> resultList = dealerPointVO.getResults();
	    	  if (resultList != null && resultList.size() > 0) {
	    		  //取查询出来的第一个结果
	    		  jsonObj = JSONObject.fromObject(resultList.get(0));
	    		  if (jsonObj != null) {
	    			  dealerPointResultVO = (DealerPointResultVO) JSONObject.toBean(jsonObj, DealerPointResultVO.class);
	    		  }
	    	  }
	      }
	      return dealerPointResultVO;
	    } finally {
	      is.close();
	    }
	 }
	
	 private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}
	 
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseData retreatCompany(Integer companyId) {
		ResponseData rd = new ResponseData(0);

		// 1 先將关联用户删除（逻辑删除）
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("companyId", companyId);
		@SuppressWarnings("unchecked")
		List<User> userList = userDao.find(parameters, "");
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				user.setDeletionFlag(GlobalConstant.TRUE);
				user.setDeletionTime(DateTime.toNormalDotDateTime(new Date()));
				userDao.edit(user);
				// 从用户缓存表移除该用户对象
				CacheUserManager.removeUser(user.getId());
			}
		}
		// 2 修改数据库经销商删除标示为（已删除）。
		Company dbCompany = companyDao.get(companyId);
		dbCompany.setDeletionFlag(GlobalConstant.TRUE);
		dbCompany.setDeletionTime(new Timestamp(new Date().getTime()));
		companyDao.edit(dbCompany);

		// 3 清cache
		CacheOrgManager.clear();
		CacheCompanyManager.clear();
		CacheOrgWithFilterManager.clear();
		CacheOrgVehicleManager.clear();

		// 4 组装返回结果
		rd.setCode(1);
		rd.setMessage("经销商信息删除成功");
		return rd;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseData rollbackRetreatCompany(Integer companyId) {
		ResponseData rd = new ResponseData(0);

		// 1 先將关联用户删除（取消逻辑删除）
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("companyId", companyId);
		@SuppressWarnings("unchecked")
		List<User> userList = userDao.findAll(parameters, "");
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				user.setDeletionFlag(GlobalConstant.FALSE);
				user.setDeletionTime(null);
				userDao.edit(user);
			}
		}
		// 2 修改数据库经销商删除标示为（取消已删除）。
		Company dbCompany = companyDao.get(companyId);
		dbCompany.setDeletionFlag(GlobalConstant.FALSE);
		dbCompany.setDeletionTime(null);
		companyDao.edit(dbCompany);

		// 3 清cache
		CacheOrgManager.clear();
		CacheCompanyManager.clear();
		CacheOrgWithFilterManager.clear();
		CacheOrgVehicleManager.clear();

		// 4 组装返回结果
		rd.setCode(1);
		rd.setMessage("经销商信息删除成功");
		return rd;
	}
	
}
