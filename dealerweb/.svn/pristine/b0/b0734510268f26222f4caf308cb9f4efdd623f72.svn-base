/**
 * Created on 2015-1-4
 * filename: NoticeService.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.baseSystem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.baseSystem.service.INoticeDealerRelationService;
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
@Service
public class NoticeDealerRelationService implements INoticeDealerRelationService {

	@Autowired
	private INoticeDealerRelationDao noticeDealerRelationDao;
	 
	@Override
	public Integer add(NoticeDealerRelation noticeDealerRelation) {
		return noticeDealerRelationDao.save(noticeDealerRelation);
	}


	@Override
	public void edit(NoticeDealerRelation noticeDealerRelation) {
		noticeDealerRelationDao.edit(noticeDealerRelation);
	}


	@Override
	public void deleteByNoticeId(Integer noticeId) {
		noticeDealerRelationDao.deleteByNoticeId(noticeId);
	}


	@Override
	public List find(HashMap parameters, String additionalCondition) {
		List<NoticeDealerRelation> noticeDealerRelationlist = 
				(ArrayList<NoticeDealerRelation>) noticeDealerRelationDao.find(parameters, null);
		return noticeDealerRelationlist ;
	}
	
}
