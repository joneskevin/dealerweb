/**
 * Created on 2015-1-4
 * filename: NoticeDao.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ava.base.dao.IHibernateDao;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.dao.INoticeDao;
import com.ava.domain.entity.Notice;
import com.ava.domain.entity.Role;
import com.ava.domain.vo.NoticeVO;

/**
 * Title: Class XXXX
 * Description:
 *
 *
 * @author jiangfei
 * @version xxx
 */
@Repository
public class NoticeDao implements INoticeDao {

	@Autowired
	private IHibernateDao hibernateDao;
	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<Object> objList = (List<Object>) hibernateDao.find("Notice", parameters, additionalCondition);
		return objList;
	}

	@Override
	public List findByPage(TransMsg msg, String additionalCondition) {
		List objList = hibernateDao.findByPage("Notice", msg, additionalCondition);
		return objList;
	}

	@Override
	public List<NoticeVO> getNoticeVoList(String sql) {
		List<NoticeVO> noticeList = hibernateDao.executeSQLQueryVoList(sql, NoticeVO.class);
		return noticeList;
	}
	
	@Override
	public int save(Notice notice) {
		if(notice == null ){
			return 0;
		} 
		return (Integer) hibernateDao.save(notice);
	}

	@Override
	public void edit(Notice notice) {
		if (notice != null) {
			hibernateDao.edit(notice);
		}
	}

	@Override
	public Notice get(Integer noticeId) {
		if (noticeId == null) {
			return null;
		}
		Notice notice = (Notice) hibernateDao.get(Notice.class, noticeId);
		return notice;
	}

	@Override
	public void delete(Integer id) {
		hibernateDao.delete(Notice.class, id);
	}

}
