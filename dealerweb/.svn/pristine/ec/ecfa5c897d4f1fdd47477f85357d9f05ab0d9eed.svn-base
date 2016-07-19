package com.ava.dealer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IBoxOperationDao;
import com.ava.dealer.service.IBoxOperationService;
import com.ava.domain.entity.BoxOperation;
import com.ava.domain.entity.Org;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.MyBeanUtils;

@Service
public class BoxOperationService implements IBoxOperationService {
	
	@Autowired
	private IBoxOperationDao boxOperationDao;
	
	public ResponseData addBoxOperation(BoxOperation boxOperation, Integer companyId) {
		ResponseData rd = new ResponseData(0);
        
		boxOperation.setOperationTime(new Date());
		Integer newId = (Integer) boxOperationDao.save(boxOperation);
		
		if (null != newId && newId > 0) {
			rd.setCode(1);
			rd.setMessage("设备操作信息新增成功");
			rd.setFirstItem(boxOperation);
		} else {
			rd.setCode(-4);
			rd.setMessage("设备操作信息新增失败");
		}

		return rd;
	}

	public ResponseData deleteBoxOperation(Integer boxOperationId, Integer companyId) {
		ResponseData rd = new ResponseData(0);

		BoxOperation dbBoxOperation = boxOperationDao.get(boxOperationId);
		if (dbBoxOperation == null) {
			rd.setCode(-1);
			rd.setMessage("设备操作信息对象为空！");
			return rd;
		}

//		dbBoxOperation.setDeletionFlag(GlobalConstant.TRUE);// 删除状态
//		boxOperationDao.update(dbBoxOperation);

		rd.setFirstItem(dbBoxOperation);
		rd.setCode(1);
		rd.setMessage("设备操作信息删除成功！");
		return rd;
	}

	public ResponseData editBoxOperation(BoxOperation frmBoxOperation, Integer companyId) {
		ResponseData rd = new ResponseData(0);

		Integer boxOperationId = frmBoxOperation.getId();
		BoxOperation dbBoxOperation = boxOperationDao.get(boxOperationId);
		
//		if (frmBoxOperation.getUniqueId() != null
//				&& !frmBoxOperation.getUniqueId().equalsIgnoreCase(dbBoxOperation.getUniqueId())) {
//			if (this.uniqueIdIsExistence(frmBoxOperation.getUniqueId())) {
//				rd.setCode(-1);
//				rd.setMessage("设备信息保存失败：新的设备号已存在！");
//				return rd;
//			}
//		}
//		
//		if (frmBoxOperation.getSimId() != null
//				&& !frmBoxOperation.getSimId().equalsIgnoreCase(dbBoxOperation.getSimId())) {
//			BoxOperation boxOperation = boxOperationDao.getBySimId(frmBoxOperation.getSimId());
//			if (boxOperation != null) {
//				rd.setCode(-2);
//				rd.setMessage("设备信息保存失败：新的SIM卡已存在！");
//				return rd;
//			}
//		}
//		
//		if (frmBoxOperation.getSimMobile() != null
//				&& !frmBoxOperation.getSimMobile().equalsIgnoreCase(dbBoxOperation.getSimMobile())) {
//			if (this.simMobileIsExistence(frmBoxOperation.getSimMobile())) {
//				rd.setCode(-3);
//				rd.setMessage("设备信息保存失败：新的通讯号已存在！");
//				return rd;
//			}
//		}

		MyBeanUtils.copyPropertiesContainsDate(dbBoxOperation, frmBoxOperation);
//		boxOperationDao.update(dbBoxOperation);

		rd.setFirstItem(dbBoxOperation);
		rd.setCode(1);
		rd.setMessage("编辑设备成功");
		return rd;
	}

	public ResponseData listBoxOperation(TransMsg transMsg, BoxOperation boxOperation,
			Integer currentCompanyId) {
		ResponseData rd = new ResponseData(0);
		transMsg.setPageSize(5);

		String additionalCondition = "";

		Integer orgId = boxOperation.getCompanyId();
		Org org = CacheOrgManager.get(orgId);
		if (org != null) {
			additionalCondition += " and companyId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(orgId, GlobalConstant.TRUE) + ")";
		} else {
			additionalCondition += " and companyId in("
					+ CacheOrgManager.getChildrenOrgIdsStr(currentCompanyId, GlobalConstant.TRUE)
					+ ")";
		}

//		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "simId", boxOperation.getSimId());
//		ParameterUtil.appendLikeQueryCondition(transMsg.getParameters(), "simMobile", boxOperation.getSimMobile());


		additionalCondition = additionalCondition + " order by id desc";
		List<BoxOperation> boxOperationList = boxOperationDao.findByPage(transMsg, additionalCondition);

		rd.setFirstItem(boxOperationList);
		rd.setCode(1);
		return rd;
	}

	public List<BoxOperation> find(HashMap<Object, Object> parameters, String additionalCondition){
		return boxOperationDao.find(parameters, additionalCondition);
	}
	public BoxOperation getBoxOperation(Integer boxOperationId) {
		BoxOperation boxOperation = boxOperationDao.get(boxOperationId);
		return boxOperation;
	}

	public BoxOperation getBoxOperation(String simId) {
//		BoxOperation boxOperation = boxOperationDao.getBySimId(simId);
		//return boxOperation;
		return null;
	}

	@Override
	public boolean simMobileIsExistence(String simMobile) {
		if (simMobile == null || simMobile.length() < 1) {
			return false;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("simId", simMobile);
		List objs = boxOperationDao.find(parameters, null);
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
		List objs = boxOperationDao.find(parameters, null);
		if (objs != null && objs.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseData displayAddBoxOperation(BoxOperation boxOperationAdd) {
		ResponseData rd = new ResponseData(0);
		if (boxOperationAdd == null) {
			boxOperationAdd = new BoxOperation();
		}
		
		rd.put("boxOperationAdd", boxOperationAdd);
		return rd;

	}

}
