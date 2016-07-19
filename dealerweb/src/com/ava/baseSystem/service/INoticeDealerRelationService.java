/**
 * Created on 2015-1-4

 * filename: INoticeDealerRelationService.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.baseSystem.service;

import java.util.HashMap;
import java.util.List;

import com.ava.domain.entity.NoticeDealerRelation;
/**
 * Title: Class XXXX
 * Description:
 *
 *
 * @author jiangfei
 * @version xxx
 */
public interface INoticeDealerRelationService {
	
	public Integer add(NoticeDealerRelation noticeDealerRelation);
	
	public void edit(NoticeDealerRelation noticeDealerRelation);
	
	public void deleteByNoticeId(Integer noticeId) ;
	
	public List find(HashMap parameters,String additionalCondition);

}
