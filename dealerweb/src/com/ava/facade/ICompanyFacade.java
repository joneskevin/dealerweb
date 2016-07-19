package com.ava.facade;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Company;

public interface ICompanyFacade {

	/**
	 * 添加经销商
	 * @param company
	 * @return
	 */
	public ResponseData addDealer(Company company) throws IOException;
	
	/**
	 * 配置车型
	 * @param company
	 * @return
	 */
	public ResponseData configCarStyle(Company company, HttpServletRequest request);
}
