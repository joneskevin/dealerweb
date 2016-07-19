/**
 * Created on 2015-1-4
 * filename: INoticeService.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.baseSystem.service;

import java.util.List;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Notice;
/**
 * Title: Class XXXX
 * Description:
 *
 *
 * @author jiangfei
 * @version xxx
 */
public interface INoticeService {
	/**
	 * 查询公告列表
	 * 
	 * @param transMsg
	 * @param currentNotice
	 * @return ResponseData
	 */
	public ResponseData listNotice(TransMsg transMsg, Notice currentNotice);
	
	public ResponseData listUserNotice(TransMsg transMsg, boolean isAllData,Integer companyId);
	
	/**
	 * 查询公告列表 header.jsp页面展示 
	 * 
	 * @return ResponseData
	 */
	public ResponseData listNoticeForHeader(Integer companyId);

	/**
	 * 显示公告添加页面
	 * 
	 * @param noticeAdd
	 * @return ResponseData
	 */
	public ResponseData displayAddNotice(Notice noticeAdd);
	
	/**
	 * 添加公告
	 * 
	 * @param noticeAdd
	 * @return ResponseData
	 */
	public ResponseData addNotice(Notice noticeAdd);
	
	/**
	 * 显示编辑公告页面
	 * 
	 * @param noticeId
	 * @return ResponseData
	 */
	public ResponseData displayEditNotice(Integer noticeId);

	/**
	 * 修改公告
	 * 
	 * @param noticeEdit
	 * @return ResponseData
	 */
	public ResponseData editNotice(Notice noticeEdit);
	

	/**
	 * 显示公告所属经销商编辑页面
	 * 
	 * @param noticeId
	 * @return
	 */
	public ResponseData displayEditNoticeOfDealer(Integer noticeId);

	/**
	 * 编辑公告所属经销商
	 * 
	 * @param notice
	 * @return
	 */
	public ResponseData editNoticeOfDealer(Notice notice);
	
	/**
	 * 删除一个公告
	 * 
	 * @param noticeId
	 * @return ResponseData
	 */
	public ResponseData deleteNotice(Integer noticeId);
	
	/**
	 * 展示 userList 供公告选择
	 * 
	 * @return List<Company>
	 */
	public List<Company> displayCompanyList();

	/**
	 * 根据id查找一个公告
	 * 
	 * @param noticeId
	 * @return ResponseData
	 */
	public Notice findNotice(Integer noticeId);
}
