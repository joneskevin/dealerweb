package com.ava.facade;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Org;

public interface IOrgFacade {

	/**新增组织*/
	public ResponseData addOrg(Org org, Integer companyId, String obName,
			String adminEmail);
}
