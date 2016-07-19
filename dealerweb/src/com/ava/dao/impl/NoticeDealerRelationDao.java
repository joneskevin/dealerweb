/**
 * Created on 2015-1-5
 * filename: NoticeDealerRelationDao.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.dao.INoticeDealerRelationDao;
import com.ava.domain.entity.NoticeDealerRelation;

/**
 * Title: Class XXXX
 * Description:
 *
 *
 * @author jiangfei
 * @version xxx
 */
@Repository
public class NoticeDealerRelationDao implements INoticeDealerRelationDao {

	@Autowired
	private IHibernateDao hibernateDao;
	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("NoticeDealerRelation", parameters, additionalCondition);
		return objList;
	}

	
	@Override
	public int save(NoticeDealerRelation noticeDealerRelation) {
		if(noticeDealerRelation == null ){
			return 0;
		} 
		return (Integer) hibernateDao.save(noticeDealerRelation);
	}

	@Override
	public void edit(NoticeDealerRelation noticeDealerRelation) {
		if (noticeDealerRelation != null) {
			hibernateDao.edit(noticeDealerRelation);
		}
	}

	@Override
	public NoticeDealerRelation get(Integer noticeDealerRelationId) {
		if (noticeDealerRelationId == null) {
			return null;
		}
		NoticeDealerRelation noticeDealerRelation = (NoticeDealerRelation) hibernateDao.get(NoticeDealerRelation.class, noticeDealerRelationId);
		return noticeDealerRelation;
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(NoticeDealerRelation.class, id);
	}
	
	@Override
	public void deleteByNoticeId(Integer noticeId) {
		hibernateDao.executeUpdate("delete NoticeDealerRelation where noticeId = " + noticeId);
	}

}
