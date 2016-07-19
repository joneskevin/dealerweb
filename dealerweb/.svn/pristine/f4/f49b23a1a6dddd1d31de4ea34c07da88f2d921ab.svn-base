package com.ava.dealer.service;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.BoxOperation;

public interface IBoxOperationService {
	
	public ResponseData displayAddBoxOperation(BoxOperation boxOperationAdd);

	/** 新增设备操作信息 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData addBoxOperation(BoxOperation boxOperation, Integer companyId);

	/** 删除设备操作信息 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData deleteBoxOperation(Integer boxOperationId, Integer companyId);

	/** 编辑设备操作信息 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData editBoxOperation(BoxOperation boxOperation, Integer companyId);

	/** 查询设备操作信息列表 ，其中companyId是当前登录者所属的组织ID*/
	public ResponseData listBoxOperation(TransMsg transMsg, BoxOperation boxOperation, Integer companyId);
	
	public List<BoxOperation> find(HashMap<Object, Object> parameters, String additionalCondition);
	
	public BoxOperation getBoxOperation(Integer boxOperationId);
	
	public BoxOperation getBoxOperation(String simId);
	
	public boolean simMobileIsExistence(String simMobile);
	
	public boolean uniqueIdIsExistence(String uniqueId);
	
}
