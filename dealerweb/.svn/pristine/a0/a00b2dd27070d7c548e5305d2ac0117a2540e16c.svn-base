package com.ava.baseSystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.dao.util.ParameterUtil;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IRoleService;
import com.ava.dao.IMenuDao;
import com.ava.dao.IRoleDao;
import com.ava.dao.IUserDao;
import com.ava.dao.IUserMenuRelationDao;
import com.ava.dao.IUserRoleRelationDao;
import com.ava.domain.entity.Menu;
import com.ava.domain.entity.Role;
import com.ava.domain.entity.User;
import com.ava.domain.entity.UserMenuRelation;
import com.ava.domain.entity.UserRoleRelation;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheUserManager;
import com.ava.resource.cache.CacheUserMenuManager;
import com.ava.util.MyBeanUtils;
import com.ava.util.ReadTemplate;
import com.ava.util.TextUtil;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleDao roleDao;
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserRoleRelationDao userRoleRelationDao;
	
	@Autowired
	private IMenuDao menuDao;
	
	@Autowired
	private IUserMenuRelationDao userMenuRelationDao;
	
	@Override
	public List<Role> listUserRole() {
		
		return roleDao.find(null, null);
	}

	@Override
	public Role findUserRole(int roleId) {
		HashMap parameters = new HashMap();
		parameters.put("id", roleId);
		ArrayList roleList = (ArrayList)roleDao.find(parameters, null);
		// roleList.get(0);
		return (Role)roleList.get(0);
		
	}


	@Override
	public ResponseData listRole(TransMsg transMsg, Role currentRole,
			HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		
		if (currentRole.getName() != null
				&& currentRole.getName().length() > 0) {
			ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "name",
					currentRole.getName());
		}
				
		String additionalCondition = " order by id desc";
		List roleList = roleDao.findByPage(transMsg, additionalCondition);
		
		StringBuffer rightsCode = new StringBuffer();
		if (roleList != null && roleList.size() > 0) {
			for (int i = 0; i < roleList.size(); i++) {
				Role role = (Role) roleList.get(i);
				rightsCode.append(role.getRightsCode());
			}
		}
	
		rd.setFirstItem(roleList);
        rd.setCode(1);
		return rd;
	}
	
	

	@Override
	public ResponseData displayAddRole(Role roleAdd) {
		ResponseData rd = new ResponseData(0);
		if (roleAdd == null) {
			roleAdd = new Role();
		}
		
		rd.put("roleAdd", roleAdd);
		return rd;
	}

	@Override
	public ResponseData displayEditRole(Integer roleId) {
		ResponseData rd = new ResponseData(0);

		Role roleEdit = roleDao.get(roleId);
		rd.put("role", roleEdit);
		return rd;
	}

	@Override
	public ResponseData editRole(Role roleEdit) {
		ResponseData rd = new ResponseData(0);

		Integer roleId = roleEdit.getId();
		if ((roleId == null) || (roleId != null && roleId.intValue() <= 0)) {
			roleId = roleEdit.getId();
		}

		Role dbRole = roleDao.get(roleId);

		if (roleEdit.getName() != null
				&& !roleEdit.getName().equalsIgnoreCase(dbRole.getName())) {
			// 如果用户要修改角色名，则判断新的角色名是否已经存在
			if (roleDao.getByName(roleEdit.getName()) != null) {
				rd.setCode(-1);
				rd.setMessage("角色信息保存失败：角色名已重复！");
				return rd;
			}
		}

		MyBeanUtils.copyPropertiesContainsDate(dbRole, roleEdit);
		roleDao.edit(dbRole);

		rd.setFirstItem(dbRole);
		rd.setCode(1);
		rd.setMessage("角色信息修改成功！");
		return rd;
	}

	@Override
	public ResponseData displayEditRoleRightsCode(Integer roleId) {
		ResponseData rd = new ResponseData(0);
		
		Role dbRole = roleDao.get(roleId);
		String oldXML = TextUtil.disableBreakLineTag(ReadTemplate
				.getUserRightsCodeContent(""));
		String newXML;
		List vipExtendList = null;//CacheVipExtendManager.getVipExtendList(companyId);
		if (vipExtendList == null || vipExtendList.size() <= 0) {
			newXML = oldXML;
		} else {
			StringBuffer strBuf = new StringBuffer("");
			// 去除原始xml文件最后的“</tree>”内容块，用来存放新内容
			strBuf.append(oldXML.substring(0, oldXML.length() - 7));
			strBuf
					.append("<item text='扩展模块' id='Menu1Lve' open='1' im0='books_close.gif' im1='tombs.gif' im2='tombs.gif' call='1' select='1'>");
			Iterator iterator = vipExtendList.iterator();
			strBuf.append("</item>");
			strBuf.append("</tree>");
			newXML = strBuf.toString();
		}
		
		rd.put("role", dbRole);
		rd.put("dhtmlXtreeXML", newXML);

		return rd;
	}

	@Override
	public ResponseData editRoleRightsCode(Role role, HttpServletRequest request) {
		ResponseData rd = new ResponseData(0);
		Integer roleId = role.getId();
		Role dbRole = roleDao.get(roleId);
        
		String rightsCodes = role.getRightsCode();
		dbRole.setRightsCode(rightsCodes);
		roleDao.edit(dbRole);
		request.setAttribute("roleId", roleId);
		
		HashMap parameters = new HashMap();
		parameters.put("roleId", roleId);
		
		//给用户重先加载权限
		List userRoleRelationList = userRoleRelationDao.find(parameters, "");
		if(userRoleRelationList != null && userRoleRelationList.size() >0 ) {
			for (int i =0; i < userRoleRelationList.size(); i++ ) {
				UserRoleRelation userRoleRelation = (UserRoleRelation) userRoleRelationList.get(i);
				User user = userDao.get(userRoleRelation.getUserId());
				if (user != null) {
					this.processRightCode(user);//权限转换处理，根据user得到对应的权限 
					this.siteUserMenuRelation(user, rightsCodes);//设置用户设置菜单表
					CacheUserManager.removeUser(user.getId());
				}
			}
		}

		rd.setFirstItem(dbRole);
		rd.setCode(1);
		return rd;
	}
	
	/**
	 * 设置用户设置菜单表
	 * @return
	 */
	private void siteUserMenuRelation(User user, String rightsCodes) {
		
		HashMap parameters = new HashMap();
		parameters.put("userId", user.getId());
		String adduserMenuRelationCondition = " order by id asc";
		List<UserMenuRelation> userMenuRelationList = userMenuRelationDao.find(parameters, adduserMenuRelationCondition);

		if (userMenuRelationList != null && userMenuRelationList.size() > 0) {
			for (UserMenuRelation userMenuRelation : userMenuRelationList) {
				if (userMenuRelation != null) {
					Menu menu = menuDao.get(userMenuRelation.getMenuId());
					if (menu != null) {
					  String rightsCode = menu.getRightsCode();
					   //如果用户设置的权限不包含在所对应的的角色的权限中，则删除
					  if (rightsCodes.indexOf(rightsCode) == -1){
						  userMenuRelationDao.delete(userMenuRelation.getId());
					  }
					}
				}
			}
		 }
		
		//清除用户设置快捷菜单缓存
		CacheUserMenuManager.clear();
	}
	
	@Override
	public ResponseData deleteRole(Integer roleId) {
		ResponseData rd = new ResponseData(0);
         
		if (roleId == null) {
			rd.setCode(-1);
			rd.setMessage("roleId为空！");
			return rd;
		}
           
		HashMap parameters = new HashMap();
		parameters.put("roleId", roleId);
		
		ArrayList<UserRoleRelation> roleList = (ArrayList<UserRoleRelation>) userRoleRelationDao.find(parameters, null);
		if (roleList != null && roleList.size() > 0) {
			rd.setCode(-2);
			rd.setMessage("角色已被用户引用，不允许删除！");
			return rd;
		}
        roleDao.delete(roleId);
		
		rd.setCode(1);
		rd.setMessage("角色信息删除成功！");
		return rd;
	}

	@Override
	public ResponseData addRole(Role roleAdd) {
		ResponseData rd = new ResponseData(0);
		if (roleDao.getByName(roleAdd.getName()) != null) {
			rd.setCode(-1);
			rd.setMessage("角色已经存在！");
			return rd;
		}

		Integer newRoleId = (Integer) roleDao.save(roleAdd);

		if (newRoleId == null) {
			rd.setCode(-2);
			rd.setMessage("角色新增失败！");
			return rd;

		}

		rd.setCode(1);
		rd.setMessage("角色新增成功！");
		rd.setFirstItem(roleAdd);
		return rd;
	}
	
  /** 根据userId从用户和角色关系表中获取角色中rightsCode(权限码)*/
	public  StringBuffer getRightsCode(Integer userId) {
    	if (userId == null) {
    		return null;
    	}
    	
    	StringBuffer rightsCode = new StringBuffer();
    		rightsCode = new StringBuffer();
    		
    		HashMap parameters = new HashMap();
    		parameters.put("userId", userId);
    		
    		List userRoleRelationList = userRoleRelationDao.find(parameters, "");
    		if(userRoleRelationList != null && userRoleRelationList.size() >0 ) {
    			for (int i = 0; i < userRoleRelationList.size(); i++) {
    				UserRoleRelation userRoleRelation = (UserRoleRelation) userRoleRelationList.get(i);
    				Role role = roleDao.get(userRoleRelation.getRoleId());
    				rightsCode.append(role.getRightsCode());
    		}
    		
    	}
    	return rightsCode;
    }
	
	/** 权限转换处理，根据user得到对应的权限 */
	public void processRightCode(User user) {
		String rightsCode = getRightsCode(user.getId()).toString();
		String loginName = user.getLoginName();
		
		if (rightsCode.length() >= 0
				|| "admin@group".equalsIgnoreCase(loginName)) {
			
			/************************************** 车辆管理-开始 **************************************/
			
			/** 车辆管理 */
			if (rightsCode.indexOf("Menu1L1") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightVehicleManagement(true);
			}
			
			/** 车辆管理-车辆列表 */
			if (rightsCode.indexOf("1100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightListVehicleAll(true);
			}
			
			/** 车辆管理-实时监控 */
			if (rightsCode.indexOf("1200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightRealtimeMonitorAll(true);
			}

			/** 车辆管理-轨迹回放 */
			if (rightsCode.indexOf("1300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTrackPlayBackAll(true);
			}
			
			/** 车辆管理-历史车辆 */
			if (rightsCode.indexOf("1400") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightHistoryVehicleAll(true);
			}
			
			/** 车辆管理-车辆返利 */
			if (rightsCode.indexOf("1500") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightVehicleRebateAll(true);
			}
			
			/** 车辆管理-车辆维护 */
			if (rightsCode.indexOf("1600") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightVehicleMaintainAll(true);
			}
			
			/** 车辆管理-车辆OBD监控列表 */
			if (rightsCode.indexOf("1700") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightMonitorVehicleObdAll(true);
			}

			/************************************** 车辆管理-结束 **************************************/
			
			/************************************** 设备管理-开始 **************************************/
			
			/** 设备管理 */
			if (rightsCode.indexOf("Menu1L8") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightBoxManagement(true);
			}
			
			/** 设备管理-设备维护 */
			if (rightsCode.indexOf("8100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightBoxAll(true);
			}
			
			/************************************** 设备管理-结束 **************************************/
			
			/************************************** 违规管理-开始 **************************************/
			
			/** 违规管理 */
			if (rightsCode.indexOf("Menu1L2") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightViolationManagement(true);
			}
			
			/** 违规管理-线路违规 */
			if (rightsCode.indexOf("2100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightLineViolation(true);
			}
			
			/** 违规管理-时间违规 */
			if (rightsCode.indexOf("2200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTimeViolation(true);
			}
			
			
			/** 违规管理-非活跃试驾*/
			if (rightsCode.indexOf("2300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightNoValidTestDriveAll(true);
			}
			
			/************************************** 违规管理-结束 **************************************/
			
			/************************************** 图表管理-开始 **************************************/
			
			/** 图表管理*/
			if (rightsCode.indexOf("Menu1L3") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightChartManagement(true);
			}
			
			/** 图表管理-车辆配置统计（分车型）*/
			if (rightsCode.indexOf("3100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightVehicleConfigStatisticsForCarStyleAll(true);
			}
			
			/** 图表管理-车辆配置统计（分区） */
			if (rightsCode.indexOf("3200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightVehicleConfigStatisticsForRegionAll(true);
			}
			
			/** 图表管理-试驾车使用 */
			if (rightsCode.indexOf("3300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveUseAll(true);
			}
			
			/** 图表管理-试驾率环比 */
			if (rightsCode.indexOf("3400") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveRatioAll(true);
			}
			
			/**图表管理-单周试驾率环比*/
			if (rightsCode.indexOf("3500") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveWeekRatioAll(true);
			}
			
			/************************************** 图表管理-结束 **************************************/

			
			/************************************** 报备管理-开始 **************************************/
			
			/** 报备管理 */
			if (rightsCode.indexOf("Menu1L4") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightFilingManagement(true);
			}
			
			/** 报备管理-市场活动 */
			if (rightsCode.indexOf("4100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightFilingOutAll(true);
			}
			
			/** 报备管理-事故维修 */
			if (rightsCode.indexOf("4200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightFilingMaintainAll(true);
			}
			
			/** 报备管理-报备审批 */
			if (rightsCode.indexOf("4300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightFilingApprovalAll(true);
			}
			
			/** 报备管理-历史报备 */
			if (rightsCode.indexOf("4400") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightFilingHistoryAll(true);
			}
			
			/************************************** 报备管理-结束 **************************************/
			
			/************************************** 车辆更新-开始 **************************************/
			
			/** 车辆更新 */
			if (rightsCode.indexOf("Menu1L5") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightVehicleUpdateManagement(true);
			}
			
			/** 车辆更新-新装申请-查看 */
			if (rightsCode.indexOf("5100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationProposalView(true);
			}
			
			/** 车辆更新-新装申请-新增 */
			if (rightsCode.indexOf("5101") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationProposalAdd(true);
			}
			
			/** 车辆更新-新装申请-编辑 */
			if (rightsCode.indexOf("5102") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationProposalEdit(true);
			}
			
			/** 车辆更新-新装申请-取消申请 */
			if (rightsCode.indexOf("5103") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationProposalCancle(true);
			}
			
			/** 车辆更新-新装申请-安装完成 */
			if (rightsCode.indexOf("5104") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationProposalSuccess(true);
			}
			
			/** 车辆更新-新装审批-查看 */
			if (rightsCode.indexOf("5200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationApprovalView(true);
			}
			
			/** 车辆更新-新装审批-审批 */
			if (rightsCode.indexOf("5201") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationApproval(true);
			}
			
			/** 车辆更新-拆除申请-查看 */
			if (rightsCode.indexOf("5300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionProposalView(true);
			}
			
			/** 车辆更新-拆除申请-新增 */
			if (rightsCode.indexOf("5301") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionProposal(true);
			}
			
			/** 车辆更新-拆除申请-取消申请 */
			if (rightsCode.indexOf("5302") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionProposalCancel(true);
			}
			
			/** 车辆更新-拆除申请-拆除完成*/
			if (rightsCode.indexOf("5303") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionProposalSuccess(true);
			}
			
			/** 车辆更新-拆除审批-查看 */
			if (rightsCode.indexOf("5400") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionApprovalView(true);
			}
			
			/** 车辆更新-拆除审批-审批 */
			if (rightsCode.indexOf("5401") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionApproval(true);
			}
			
			/** 车辆更新-换装-查看 */
			if (rightsCode.indexOf("5500") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightReplaceProposalView(true);
			}
			
			/** 车辆更新-换装-新装 */
			if (rightsCode.indexOf("5501") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightReplaceProposalAdd(true);
			}
			
			/** 车辆更新-拆除 */
			if (rightsCode.indexOf("5600") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDemolitionVehicle(true);
			}
			
			/** 车辆更新-换装拆除计划 */
			if (rightsCode.indexOf("5700") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInstallationPlan(true);
			}
			
			/************************************** 车辆更新-结束 **************************************/
			
			/************************************** 试驾管理-开始 **************************************/
			
			/** 试驾管理 */
			if (rightsCode.indexOf("Menu1L6") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveManagement(true);
			}
			
			/** 试驾管理-有效试驾 */
			if (rightsCode.indexOf("6100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightValidTestDriveAll(true);
			}
			
			
			/** 试驾管理-单周统计 */
			if (rightsCode.indexOf("6200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveSingleWeeksStatisticsAll(true);
			}
			
			/** 试驾管理-月度统计 */
			if (rightsCode.indexOf("6300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveSingleMonthStatisticsAll(true);
			}
			
			/** 试驾管理-季度统计 */
			if (rightsCode.indexOf("6400") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveQuarterStatisticsAll(true);
			}
			
			/** 试驾管理-阶段统计 */
			if (rightsCode.indexOf("6500") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveStageStatisticsAll(true);
			}
			
			/** 试驾管理-无效试驾 */
			if (rightsCode.indexOf("6600") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightInValidTestDriveAll(true);
			}
			
			/************************************** 试驾管理-结束 **************************************/
			
			/************************************** 报表管理-开始 **************************************/
			
			/** 报表管理 */
			if (rightsCode.indexOf("Menu1L9") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightReportManagement(true);
			}
			
			/** 报表管理-配置报表 */
			if (rightsCode.indexOf("9100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightConfigureReportAll(true);
			}
			
			/** 报表管理-违规报表 */
			if (rightsCode.indexOf("9200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightViolationReportAll(true);
			}
			
			/** 报表管理-试驾率报表*/
			if (rightsCode.indexOf("9300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightTestDriveRateReportAll(true);
			}
			
			/************************************** 报表管理-结束 **************************************/
			
			/************************************** 系统设置-开始 **************************************/
			
			/** 系统设置 */
			if (rightsCode.indexOf("Menu1L7") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightSystemManagement(true);
			}
			
			/** 系统设置-组织管理 */
			if (rightsCode.indexOf("7100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightOrgMangementAll(true);
			}
			
			
			/** 系统设置-线路管理-查看 */
			if (rightsCode.indexOf("7500") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightLineView(true);
			}
			
			/** 系统设置-线路管理-添加 */
			if (rightsCode.indexOf("7501") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightLineAdd(true);
			}
			
			/** 系统设置-线路管理-编辑 */
			if (rightsCode.indexOf("7502") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightLineEdit(true);
			}
			
			/** 系统设置-线路管理-删除 */
			if (rightsCode.indexOf("7503") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightLineDelete(true);
			}
			
			/** 系统设置-线路管理-设置参考线路 */
			if (rightsCode.indexOf("7504") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightSetReferenceLine(true);
			}
			
			/** 系统设置-线路管理-设置围栏 */
			if (rightsCode.indexOf("7505") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightSetFence(true);
			}
			
			/** 系统设置-线路管理-设置检测点' */
			if (rightsCode.indexOf("7506") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightSetSemcyclePoint(true);
			}
			
			/** 系统设置-公告列表 */
			if (rightsCode.indexOf("7700") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightNoticeListAll(true);
			}
			
			/** 系统设置-车型配置调研 */
			if (rightsCode.indexOf("7900") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightCarStyleConfig(true);
			}
			
			/** 系统设置-问卷结果列表 */
			if (rightsCode.indexOf("79100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightQuestionnaireResult(true);
			}
			
			/** 系统设置-用户密码修改 */
			if (rightsCode.indexOf("7800") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightUserEditPass(true);
			}
			
			/************************************** 系统设置-结束 **************************************/
			
			/************************************** 运营管理-开始 **************************************/
			/** 系统设置 */
			if (rightsCode.indexOf("Menu1L10") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightAdminManage(true);
			}
			
			/** 运营管理-用户管理 */
			if (rightsCode.indexOf("10100") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightUserMangementAll(true);
			}
			
			/** 运营管理-角色管理 */
			if (rightsCode.indexOf("10200") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightRoleMangementAll(true);
			}
			
			/** 运营管理-经销商管理 */
			if (rightsCode.indexOf("10300") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightDealerMangementAll(true);
			}
			
			/** 运营管理-公告管理 */
			if (rightsCode.indexOf("10400") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightNoticeMangementAll(true);
			}
			
			/** 运营管理-试驾查询 */
			if (rightsCode.indexOf("10500") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightAdminListTestDrive(true);
			}
			
			/** 运营管理-GPS报文查询 */
			if (rightsCode.indexOf("10600") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightAdminListGpsPoint(true);
			}
			/** 运营管理-重跑任务查询 */
			if (rightsCode.indexOf("10700") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightAdminTaskList(true);
			}
			/** 运营管理-异常日志查询 */
			if (rightsCode.indexOf("10800") > -1
					|| "admin@group".equalsIgnoreCase(loginName)) {
				user.getUserRight().setRightAdminLogList(true);
			}
			/************************************** 运营管理-结束 **************************************/

		}
	}


}
