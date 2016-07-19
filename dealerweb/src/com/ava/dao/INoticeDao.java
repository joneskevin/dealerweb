/**
 * Created on 2015-1-4
 * filename: INoticeDao.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.dao;

import java.util.HashMap;
import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.domain.entity.Notice;
import com.ava.domain.vo.NoticeVO;
/**
 * Title: Class XXXX
 * Description:
 *
 * @author jiangfei
 * @version xxx
 */
public interface INoticeDao {
	public List find(HashMap parameters,String additionalCondition);
	
	public List findByPage(TransMsg msg, String additionalCondition);
	
	public List<NoticeVO> getNoticeVoList(String sql);
	
	public int save(Notice notice);
	
	public void edit(Notice notice);		
	
	public Notice get(Integer noticeId);
	
	public void delete(Integer id);
}
