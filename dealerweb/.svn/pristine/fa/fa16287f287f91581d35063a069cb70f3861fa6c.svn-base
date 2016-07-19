package com.ava.facade.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.baseSystem.service.IUserService;
import com.ava.dao.ICarStyleDao;
import com.ava.dao.ICompanyCarStyleRelationDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.CompanyCarStyleRelation;
import com.ava.domain.entity.User;
import com.ava.domain.entity.UserRoleRelation;
import com.ava.domain.entity.Vehicle;
import com.ava.facade.ICompanyFacade;
import com.ava.resource.GlobalConstant;

@Service
public class CompanyFacade implements ICompanyFacade {
	
	@Autowired
	private ICarStyleDao carStyleDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private ICompanyCarStyleRelationDao companyCarStyleRelationDao;
	
	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IVehicleService vehicleService;

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addDealer(Company company) throws IOException {
		
		ResponseData rd = companyService.addDealer(company);
		
		if (rd.getCode() == 1) {
			company = (Company) rd.getFirstItem();
			ResponseData userRd = userService.addAdmin(company, null);
			
			if (userRd.getCode() == 1) {
				User currentUser = (User) userRd.getFirstItem();
				
				//添加经销上角色
				UserRoleRelation userRoleRelation = new UserRoleRelation();
				userRoleRelation.setUserId(currentUser.getId());
				userRoleRelation.setRoleId(GlobalConstant.DEFAULT_DEALER_ROLE_ID);
				userRoleRelationDao.save(userRoleRelation);
				
				/**
				//发送登录帐号、密码到经销商的邮箱
				int sendPasswordResult = userApiService.sendPassowrd(currentUser.getId(), currentUser.getPseudoPassword(), currentUser.getEmail());
				if(sendPasswordResult == 1) {
					;
				} else {
					rd.setCode(-5);
					rd.setMessage("管理员帐号信息邮件发送失败！");
					return rd;
				}
				
				//发送登录帐号、密码到总部
				int sendPasswordToHeadquartersResult = userApiService.sendPasswordToHeadquarters(currentUser.getId(), currentUser.getPseudoPassword());
				if(sendPasswordToHeadquartersResult == 1) {
					;
				} else {
					rd.setCode(-6);
					rd.setMessage("总部帐号信息邮件发送失败！");
					return rd;
				}
				*/
			} else {
				return userRd;
			}
		} 
		
		return rd;
		
	}
	
	@Override
	public ResponseData configCarStyle(Company company, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		Integer companyId = company.getId();
		
		//先通过[companyId]删除经销商和车型关系表记
		HashMap pHashMap = new HashMap();
		pHashMap.put("companyId", companyId);
		
		ArrayList<CompanyCarStyleRelation>  companyCarStyleRelationList = (ArrayList<CompanyCarStyleRelation>) companyCarStyleRelationDao.find(pHashMap, null);
		if(companyCarStyleRelationList !=null && companyCarStyleRelationList.size()>0) {
			 for (CompanyCarStyleRelation companyCarStyleRelation : companyCarStyleRelationList) {
				 companyCarStyleRelationDao.delete(companyCarStyleRelation.getId());
			  }
		}
		
		//添加经销商和车型关系表记录
		pHashMap.clear();
		pHashMap.put("level", GlobalConstant.FALSE);
		ArrayList<CarStyle> carStyleList = (ArrayList<CarStyle>) carStyleDao.find(pHashMap, null);
		if(carStyleList != null && carStyleList.size() > 0) {
			for (CarStyle carStyle : carStyleList) {
				CompanyCarStyleRelation companyCarStyleRelation = new CompanyCarStyleRelation();
				companyCarStyleRelation.setCompanyId(company.getId());
				
				Integer carStyleId = carStyle.getId();
				companyCarStyleRelation.setCarStyleId(carStyleId);
				
				Integer configType = Integer.valueOf(request.getParameter("configType" + carStyleId));
				if (request.getParameter("configType" + carStyleId) == null) {
					continue;
				}
				companyCarStyleRelation.setConfigType(configType);
				companyCarStyleRelationDao.save(companyCarStyleRelation);
				
				//根据车型ID将车型的配置方式同步到车辆表中
				rd = this.updateVehicleConifg(companyId, carStyleId, configType);
			}
		}
		
		rd.setFirstItem(company);
		rd.setCode(1);
		rd.setMessage("经销商车型配置成功");
		return rd;
	}
	
	/**
	 * 根据车型ID将车型的配置方式同步到车辆表中
	 * @param companyId
	 * @param carStyleId
	 * @param configType
	 * @return
	 */
	ResponseData updateVehicleConifg(Integer companyId, Integer carStyleId, Integer configType) {
		ResponseData rd = new ResponseData(0);
		CarStyle baseCarStyle = carStyleDao.get(carStyleId);
		if (baseCarStyle != null) {
			HashMap<Object, Object> pHashMap = new HashMap<Object, Object>();
			pHashMap.put("companyId", companyId);
			pHashMap.put("carStyleId", baseCarStyle.getId());
			ArrayList<Vehicle>  vehicleList = (ArrayList) vehicleDao.find(pHashMap, null);
			if(vehicleList != null && vehicleList.size() > 0) {
				for (Vehicle vehicle : vehicleList) {
					vehicle.setConfigureType(configType);
					rd = vehicleService.editVehicle(vehicle);
				}
			}
		}
		return rd;
	}
	
}
