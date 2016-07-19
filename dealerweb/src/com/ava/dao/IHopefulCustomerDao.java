package com.ava.dao;

import java.util.List;
import java.util.Map;

import com.ava.domain.entity.HopefulCustomer;

public interface IHopefulCustomerDao {
	public List<HopefulCustomer> find(Map parameters, String additionalCondition) ;
	
}
