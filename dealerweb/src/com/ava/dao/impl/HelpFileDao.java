package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.IHelpFileDao;
import com.ava.domain.entity.HelpFile;
import com.ava.resource.GlobalConstant;

@Repository
public class HelpFileDao implements IHelpFileDao {
	
	@Autowired
	private IHibernateDao hibernateDao;

	public List<HelpFile> findByPage(TransMsg msg, String additionalCondition) {
		List<HelpFile> helpFileList =  hibernateDao.findByPage("HelpFile", msg, additionalCondition);
		return helpFileList;
	}
	
	@Override
	public Integer save(HelpFile helpFile) {
		Integer objId = null;
		if (helpFile != null) {
			try {
				objId = (Integer) hibernateDao.save(helpFile);
			} catch (Exception e) {
			}
		}

		return objId;
	}

	@Override
	public HelpFile getOriginalName(String originalName) {
		if (originalName == null) {
			return null;
		}
		HashMap<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put("originalName", originalName);
		List<HelpFile> objs = hibernateDao.find("HelpFile", parameters, "");
		if(objs != null && objs.size() > 0){
			return  objs.get(0);
		}
		return null;
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(HelpFile.class, id);
		
	}

	@Override
	public HelpFile get(Integer id) {
		return hibernateDao.get(HelpFile.class, id);
	}

}
