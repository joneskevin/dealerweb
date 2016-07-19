package com.ava.dealer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IBoxDao;
import com.ava.dao.IBoxOperationDao;
import com.ava.dao.IProposalDao;
import com.ava.dao.IVehicleDao;
import com.ava.dealer.service.IBoxService;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.Proposal;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.BoxFindVO;
import com.ava.domain.vo.BoxInfoVO;
import com.ava.domain.vo.BoxOperationFindVO;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheUserManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;

@Service
public class BoxService extends BaseService implements IBoxService {
	
	@Autowired
	private IHibernateDao hibernateDao;
	
	@Autowired
	private IBoxDao boxDao;
	
	@Autowired
	private IVehicleDao vehicleDao;
	
	@Autowired
	private IProposalDao proposalDao;
	
	@Autowired
	private IBoxOperationDao boxOperationDao;
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData addBox(Box box, Integer companyId) {
		ResponseData rd = new ResponseData(0);
        
		if (uniqueIdIsExistence(box.getUniqueId())) {
			rd.setCode(-1);
			rd.setMessage("设备号已存在");
			return rd;
		}
		
		Box dbBbox = boxDao.getBySimId(box.getSimId());
		if (dbBbox != null) {
			rd.setCode(-2);
			rd.setMessage("SIM卡已存在");
			return rd;
		}
		
		if (simMobileIsExistence(box.getSimMobile())) {
			rd.setCode(-3);
			rd.setMessage("通讯号已存在");
			return rd;
		}
		
		Integer vehicleId = GlobalConstant.FALSE;
		if (box.getVehicleId() != null) {
			vehicleId = box.getVehicleId();
		}
		box.setVehicleId(vehicleId);
		box.setStatus(GlobalConstant.BOX_STATUS_INACTIVE);
		box.setDeletionFlag(GlobalConstant.FALSE);
		Integer newId = (Integer) boxDao.save(box);
		
		if (null != newId && newId > 0) {
			rd.setCode(1);
			rd.setMessage("设备新增成功");
			rd.setFirstItem(box);
		} else {
			rd.setCode(-4);
			rd.setMessage("设备新增失败");
		}

		return rd;
	}

	public ResponseData deleteBox(Integer boxId, Integer companyId) {
		ResponseData rd = new ResponseData(0);

		Box dbBox = boxDao.get(boxId);
		if (dbBox == null) {
			rd.setCode(-1);
			rd.setMessage("设备对象为空！");
			return rd;
		}
		
		Integer vehicleId = dbBox.getVehicleId();
		if (vehicleId != null && vehicleId.intValue() > 0) {
			rd.setCode(-1);
			rd.setMessage("设备已经安装到车上，不允许删除！");
			return rd;
		}

		dbBox.setDeletionFlag(GlobalConstant.TRUE);// 删除状态
		boxDao.update(dbBox);

		rd.setFirstItem(dbBox);
		rd.setCode(1);
		rd.setMessage("设备删除成功！");
		return rd;
	}

	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData editBox(Box frmBox, Integer companyId, Integer flag) {
		ResponseData rd = new ResponseData(0);

		Integer boxId = frmBox.getId();
		Box dbBox = boxDao.get(boxId);
		
		if (flag != null && flag == GlobalConstant.TRUE) {
			Integer vehicleId = dbBox.getVehicleId();
			if (vehicleId != null && vehicleId.intValue() > 0) {
				rd.setCode(-1);
				rd.setMessage("设备已经安装到车上，不允许修改！");
				return rd;
			}
		}
		
		if (frmBox.getUniqueId() != null
				&& !frmBox.getUniqueId().equalsIgnoreCase(dbBox.getUniqueId())) {
			if (this.uniqueIdIsExistence(frmBox.getUniqueId())) {
				rd.setCode(-1);
				rd.setMessage("设备信息保存失败：新的设备号已存在！");
				return rd;
			}
		}
		
		if (frmBox.getSimId() != null
				&& !frmBox.getSimId().equalsIgnoreCase(dbBox.getSimId())) {
			Box box = boxDao.getBySimId(frmBox.getSimId());
			if (box != null) {
				rd.setCode(-2);
				rd.setMessage("设备信息保存失败：新的SIM卡已存在！");
				return rd;
			}
		}
		
		if (frmBox.getSimMobile() != null
				&& !frmBox.getSimMobile().equalsIgnoreCase(dbBox.getSimMobile())) {
			if (this.simMobileIsExistence(frmBox.getSimMobile())) {
				rd.setCode(-3);
				rd.setMessage("设备信息保存失败：新的通讯号已存在！");
				return rd;
			}
		}
		
		MyBeanUtils.copyPropertiesContainsDate(dbBox, frmBox);
		boxDao.update(dbBox);

		rd.setFirstItem(dbBox);
		rd.setCode(1);
		rd.setMessage("编辑设备成功");
		return rd;
	}
	
	public ResponseData listBox(TransMsg transMsg, BoxVO boxVO, Integer currentCompanyId, Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT count(*) from (SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" b.id, b.UNIQUE_ID as uniqueId, b.SIM_ID as simId, b.SIM_MOBILE as simMobile, b.CURRENT_VERSION as currentVersion, ");
		exeSql.append(" IF(b.STATUS = 1 , '已激活', '未激活') as statusNick ");
		exeSql.append(" FROM t_box b ");
		exeCountSql.append(" FROM t_box b ");
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = b.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = b.COMPANY_ID ");
		
		String vehicleSql = " LEFT JOIN t_vehicle v on v.Id = b.VEHICLE_ID ";
		exeSql.append(vehicleSql);
		exeCountSql.append(vehicleSql);
		//关联车型
		String carStyleSql = " LEFT JOIN t_car_style cs on cs.ID = v.CAR_STYLE_ID ";
		exeSql.append(carStyleSql);
		exeCountSql.append(carStyleSql);
		
		exeSql.append(" WHERE b.DELETION_FLAG = 0 ");
		exeCountSql.append(" WHERE b.DELETION_FLAG = 0 ");
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, currentUserId));
		exeCountSql.append(getAccessSql(currentCompanyId, currentUserId));
		
		if (boxVO.getUniqueId() != null && boxVO.getUniqueId().trim().length() > 0) {
			exeSql.append(" AND b.UNIQUE_ID LIKE \"%").append(boxVO.getUniqueId()).append("%\" ");
			exeCountSql.append(" AND b.UNIQUE_ID LIKE \"%").append(boxVO.getUniqueId()).append("%\" ");
		}
		
		if (boxVO.getSimId() != null && boxVO.getSimId().trim().length() > 0) {
			exeSql.append(" AND b.SIM_ID LIKE \"%").append(boxVO.getSimId()).append("%\" ");
			exeCountSql.append(" AND b.SIM_ID LIKE \"%").append(boxVO.getSimId()).append("%\" ");
		}
		
		if (boxVO.getDealerCode() != null && boxVO.getDealerCode().trim().length() > 0) {
			exeSql.append(" AND vdi.dealerCode LIKE \"%").append(boxVO.getDealerCode()).append("%\" ");
			exeCountSql.append(" AND vdi.dealerCode LIKE \"%").append(boxVO.getDealerCode()).append("%\" ");
		}
		
		//根据设备号分组
		exeSql.append(" GROUP BY b.UNIQUE_ID ");
		exeCountSql.append(" GROUP BY b.UNIQUE_ID ").append(" ) t");
		
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , b.ID DESC ");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<BoxFindVO> boxFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), BoxFindVO.class);
		rd.setFirstItem(boxFindVOList);
		return rd; 
	}

	public List<Box> find(HashMap<Object, Object> parameters, String additionalCondition){
		return boxDao.find(parameters, additionalCondition);
	}
	public Box getBox(Integer boxId) {
		Box box = boxDao.get(boxId);
		return box;
	}

	public Box getBox(String simId) {
		Box box = boxDao.getBySimId(simId);
		return box;
		
	}
	
	public boolean vehicleIdIsExistence(Integer vehicleId) {
		if (vehicleId == null || vehicleId.intValue() == 1) {
			return false;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("vehicleId", vehicleId);
		List objs = boxDao.find(parameters, null);
		if (objs != null && objs.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean simMobileIsExistence(String simMobile) {
		if (simMobile == null || simMobile.length() < 1) {
			return false;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("simMobile", simMobile);
		List objs = boxDao.find(parameters, null);
		if (objs != null && objs.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean uniqueIdIsExistence(String uniqueId) {
		if (uniqueId == null || uniqueId.length() < 1) {
			return false;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("uniqueId", uniqueId);
		List objs = boxDao.find(parameters, null);
		if (objs != null && objs.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseData displayAddBox(Box boxAdd) {
		ResponseData rd = new ResponseData(0);
		if (boxAdd == null) {
			boxAdd = new Box();
		}
		
		rd.put("boxAdd", boxAdd);
		return rd;

	}

	@Override
	public Box getBoxByUniqueId(String uniqueId) {
		Box box = boxDao.getByUniqueId(uniqueId);
		return box;
	}

	@Override
	public Box getBoxByVehicleId(Integer vehicleId) {
		Box box = boxDao.getByVehicleId(vehicleId);
		return  box;
	}
	
	@Override
	public ResponseData displayUnbind(Integer id) {
		ResponseData rd = new ResponseData(0);
		if (id != null) {
			Box dbBox = boxDao.get(id);
			if (dbBox != null && dbBox.getVehicleId() != null 
					&& dbBox.getVehicleId().intValue() > GlobalConstant.FALSE) {
				BoxInfoVO boxInfoVO = new BoxInfoVO(dbBox);
				Vehicle dbVehicle = vehicleDao.get(dbBox.getVehicleId());
				boxInfoVO.setVehicle(dbVehicle);
				
				BoxOperation boxOperation = new BoxOperation();
				boxInfoVO.setBoxOperation(boxOperation);
				
				rd.setCode(1);
				rd.setFirstItem(boxInfoVO);
				
			}
		}
		
		return rd;
	}
	
	@Override
	public ResponseData unbind(BoxInfoVO frmBox, Integer companyId, Integer currentUserId) {
		ResponseData rd = new ResponseData(0);
		rd.setCode(-1);
		rd.setMessage("解除绑定失败");

		Integer boxId = frmBox.getId();
		Box dbBox = boxDao.get(boxId);
		if (dbBox != null && dbBox.getVehicleId() != null) {
			Integer vehicleId = dbBox.getVehicleId();
			Vehicle dbVehicle = vehicleDao.get(vehicleId);
			if (dbVehicle != null) {
				dbBox.setVehicleId(GlobalConstant.FALSE);
				dbBox.setStatus(GlobalConstant.FALSE);
				boxDao.update(dbBox);
				
				//修改车辆安装状态
				dbVehicle.setConfigureStatus(GlobalConstant.CONFIGURE_STATUS_WATTING);
				vehicleDao.update(dbVehicle);
				//新增一条待安装的车辆申请信息 
				Proposal proposal = new Proposal();
				proposal.setType(GlobalConstant.PROPOSAL_TYPE_INSTALLATION);
				proposal.setStatus(GlobalConstant.PROPOSAL_STATUS_PASSED);
				proposal.setCompanyId(dbBox.getCompanyId());	
				proposal.setDepartmentId(dbBox.getCompanyId());
				proposal.setVehicleId(vehicleId);
				proposal.setContactName(CacheUserManager.getNickName(currentUserId));
				proposal.setContactPhone("");
				proposal.setProposerId(currentUserId);
				proposal.setProposalTime(DateTime.toDate(DateTime.getNormalDateTime()));
				proposalDao.save(proposal);
				
				//新增一条操作日志
				BoxOperation boxOperation = frmBox.getBoxOperation();
				boxOperation.setCompanyId(companyId);
				boxOperation.setBoxId(boxId);
				boxOperation.setVehicleId(vehicleId);
				boxOperation.setType(GlobalConstant.PROPOSAL_TYPE_DEMOLITION);
				boxOperation.setStatus(GlobalConstant.TRUE);
				boxOperation.setOperationName(currentUserId.toString());
				boxOperation.setOperationTime(new Date());
				
				boxOperationDao.save(boxOperation);
				rd.setFirstItem(dbBox);
				rd.setCode(1);
				rd.setMessage("解除绑定成功");
			}
		}

		return rd;
	}

	@Override
	public ResponseData listBoxOperation(TransMsg transMsg,
			BoxOperationVO boxOperationVO, Integer currentCompanyId,
			Integer currentUserId, boolean isExport) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		StringBuffer exeSql = new StringBuffer(" SELECT ");
		StringBuffer exeCountSql = new StringBuffer(" SELECT count(*) from (SELECT COUNT(1) ");
		//查询经销商信息
		exeSql.append(getDealerSql());
		//查询车辆信息
		exeSql.append(getVehicleSql());
		exeSql.append(" bo.id, bo.OPERATION_TIME as operationTime, ");
		exeSql.append(" (CASE bo.TYPE ");
		exeSql.append(" WHEN ").append( GlobalConstant.PROPOSAL_TYPE_INSTALLATION).append(" THEN '绑定' ");
		exeSql.append(" WHEN ").append(GlobalConstant.PROPOSAL_TYPE_DEMOLITION).append(" THEN '解绑' ");
		exeSql.append(" END) as typeNick, ");
		exeSql.append(" b.UNIQUE_ID as uniqueId ");
		exeSql.append(" FROM t_box_operation bo ");
		exeCountSql.append(" FROM t_box_operation bo ");
		
		String boxSql = " INNER JOIN t_box b on b.Id = bo.BOX_ID ";
		exeSql.append(boxSql);
		exeCountSql.append(boxSql);
		
		exeSql.append(" INNER JOIN view_dealer_info vdi on vdi.companyId = bo.COMPANY_ID ");
		exeCountSql.append(" INNER JOIN view_dealer_for_count vdi on vdi.companyId = bo.COMPANY_ID ");
		
		String vehicleSql = " LEFT JOIN t_vehicle v on v.Id = bo.VEHICLE_ID ";
		exeSql.append(vehicleSql);
		exeCountSql.append(vehicleSql);
		//关联车型
		String carStyleSql = " LEFT JOIN t_car_style cs on cs.ID = v.CAR_STYLE_ID ";
		exeSql.append(carStyleSql);
		exeCountSql.append(carStyleSql);
		
		exeSql.append(" WHERE 1 = 1 ");
		exeCountSql.append(" WHERE 1 = 1 ");
		
		//添加权限过滤条件
		exeSql.append(getAccessSql(currentCompanyId, currentUserId));
		exeCountSql.append(getAccessSql(currentCompanyId, currentUserId));
		
		if (boxOperationVO.getBox().getUniqueId() != null && boxOperationVO.getBox().getUniqueId().trim().length() > 0) {
			exeSql.append(" AND b.UNIQUE_ID LIKE \"%").append(boxOperationVO.getBox().getUniqueId()).append("%\" ");
			exeCountSql.append(" AND b.UNIQUE_ID LIKE \"%").append(boxOperationVO.getBox().getUniqueId()).append("%\" ");
		}
		
		if (boxOperationVO.getBox().getDealerCode() != null && boxOperationVO.getBox().getDealerCode().trim().length() > 0) {
			exeSql.append(" AND vdi.dealerCode LIKE \"%").append(boxOperationVO.getBox().getDealerCode()).append("%\" ");
			exeCountSql.append(" AND vdi.dealerCode LIKE \"%").append(boxOperationVO.getBox().getDealerCode()).append("%\" ");
		}
		
		if (boxOperationVO.getVin() != null && boxOperationVO.getVin().trim().length() > 0) {
			exeSql.append(" AND v.VIN LIKE \"%").append(boxOperationVO.getVin()).append("%\" ");
			exeCountSql.append(" AND v.VIN LIKE \"%").append(boxOperationVO.getVin()).append("%\" ");
		}
		
		
		//根据设备号分组
		exeSql.append(" GROUP BY b.UNIQUE_ID, bo.OPERATION_TIME ");
		exeCountSql.append(" GROUP BY b.UNIQUE_ID, bo.OPERATION_TIME ").append(" ) t");
		
		//根据分销中心、网络代码排序
		exeSql.append(getOrderSql());
		exeSql.append(" , bo.BOX_ID, bo.OPERATION_TIME DESC");
		//设置分页
		exeSql.append(getPaginationSql(transMsg, exeCountSql, isExport));
		List<BoxOperationFindVO> boxOperationFindVOList = hibernateDao.executeSQLQueryVoList(exeSql.toString(), BoxOperationFindVO.class);
		rd.setFirstItem(boxOperationFindVOList);
		return rd; 
	}

}
