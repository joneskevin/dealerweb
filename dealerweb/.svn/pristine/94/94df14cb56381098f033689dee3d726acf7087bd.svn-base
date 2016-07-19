package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Box;
import com.ava.domain.vo.BoxInfoVO;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;

public interface IBoxService {
	
	public ResponseData displayAddBox(Box boxAdd);

	/** 新增设备 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData addBox(Box box, Integer companyId);

	/** 删除设备 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData deleteBox(Integer boxId, Integer companyId);

	/** 编辑设备 ，其中companyId是当前登录者所属的组织ID
	 * @param flag 操作标志*/
	public ResponseData editBox(Box box, Integer companyId, Integer flag);

	public ResponseData listBox(TransMsg transMsg, BoxVO boxVO, Integer currentCompanyId, Integer currentUserId, boolean isExport);
	
	public ResponseData listBoxOperation(TransMsg transMsg, BoxOperationVO boxOperationVO, Integer currentCompanyId, Integer currentUserId, boolean isExport);
	
	/** 显示解绑页面 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData displayUnbind(Integer id);
	
	/** 把设备从车上解除绑定 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData unbind(BoxInfoVO boxInfoVO, Integer companyId, Integer currentUserId);
	
	public List<Box> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public Box getBox(Integer boxId);
	
	public Box getBox(String simId);
	
	public Box getBoxByUniqueId(String uniqueId);
	
	public Box getBoxByVehicleId(Integer vehicleId);
	
	public boolean simMobileIsExistence(String simMobile);
	
	public boolean uniqueIdIsExistence(String uniqueId);
	
	public boolean vehicleIdIsExistence(Integer vehicleId);
	
}
