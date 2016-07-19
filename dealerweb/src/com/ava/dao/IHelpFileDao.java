package com.ava.dao;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.HelpFile;


public interface IHelpFileDao {
	
	public List<HelpFile> findByPage(TransMsg msg, String additionalCondition);
	
	public HelpFile getOriginalName(String originalName);
	
	public Integer save(HelpFile helpFile);
	
	public void delete(Integer id);
	
	public HelpFile get(Integer id);
}
