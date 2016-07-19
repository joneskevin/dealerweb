/**
 * Created on 2015-1-4
 * filename: NoticeController.java
 * Description: 
 * Copyright: Copyright(c)2013
 * Company: Mars
 */
package com.ava.baseSystem.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.INoticeDealerRelationService;
import com.ava.baseSystem.service.INoticeService;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Notice;
import com.ava.domain.entity.NoticeDealerRelation;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.TypeConverter;

/**
 * Title: Class XXXX Description:
 * 
 * 
 * @author jiangfei
 * @version xxx
 */
@Controller
@RequestMapping(value = "/base/notice")
public class NoticeController extends Base4MvcController {
	@Autowired
	private INoticeService noticeService;
	
	@Autowired
	private INoticeDealerRelationService noticeDealerRelationService;
	
	@Autowired
	private IUserMenuService userMenuService;
	
	/**
	 * 显示公告列表
	 * 
	 * @param transMsg
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/listUserNotice.vti", method=RequestMethod.GET)
	public String listUserNotice(TransMsg transMsg) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = noticeService.listUserNotice(transMsg,false,companyId);
		
		getRequest().setAttribute("noticeList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		
		return "/dealer/notice/listUserNotice";
	}
	
	/**
	 * 显示公告列表
	 * 
	 * @param transMsg
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String listNotice(TransMsg transMsg, @ModelAttribute("notice") Notice notice) {
		ResponseData rd = noticeService.listNotice(transMsg, notice);
		
		getRequest().setAttribute("noticeList", rd.getFirstItem());
		getRequest().setAttribute("notice", notice);
		getRequest().setAttribute("transMsg", transMsg);
		
		rd = userMenuService.siteMenuType(getRequest());
		getRequest().setAttribute("menuId", rd.get("menuId"));
		getRequest().setAttribute("menuType", rd.get("menuType"));
		getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		
		return "/dealer/notice/listNotice";
	}
	
	/**
	 * 显示公告列表  为header.jsp时候使用
	 * 
	 * @param transMsg
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/searchForHeader.vti", method=RequestMethod.GET)
	public void listNoticeForHeader() {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = noticeService.listNoticeForHeader(companyId);
//		ResponseData rd = noticeService.listUserNotice(null, true, companyId);
		getRequest().setAttribute("noticeList", rd.getFirstItem());
		
		writeRd(rd);
	}
	
	/**
	 * 显示新增公告的页面
	 * 
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/view.vti", method=RequestMethod.GET)
	public String viewNotice(@ModelAttribute("frmNotice") Notice frmNotice) {
		Integer noticeId = frmNotice.getId();
		if (frmNotice.getId() == null) {
			noticeId = getIntegerParameter("noticeId");
		}
		ResponseData rd =noticeService.displayEditNotice(noticeId);
		Notice notice = (Notice) rd.get("notice");
		
		getRequest().setAttribute("notice", notice);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/notice/viewNotice";
	}
	/**
	 * 显示新增公告的页面
	 * 
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddNotice(@ModelAttribute("noticeAdd") Notice notice) {
		ResponseData rd = noticeService.displayAddNotice(notice);
		getRequest().setAttribute("noticeAdd", rd.get("noticeAdd"));
		
		return "/dealer/notice/addNotice";
	}

	/**
	 * 添加公告
	 * 
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addNotice(@ModelAttribute("noticeAdd") Notice notice) {
		if (!checkaddNotice(notice)) {
			return displayAddNotice(notice);
		}
		
		ResponseData rd = noticeService.addNotice(notice);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddNotice(notice);
	}
	
	
	/**
	 * 管理员编辑公告页面
	 * 
	 * @param notice
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditNotice(@ModelAttribute("frmNotice") Notice frmNotice) {
		Integer noticeId = frmNotice.getId();
		if (frmNotice.getId() == null) {
			noticeId = getIntegerParameter("noticeId");
		}
		ResponseData rd =noticeService.displayEditNotice(noticeId);
		Notice notice = (Notice) rd.get("notice");
		// 为了保持当前修改的状态
		MyBeanUtils.copyPropertiesContainsDate(notice, frmNotice);
		getRequest().setAttribute("notice", notice);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/notice/editNotice";
	}

	/**
	 * 管理员编辑公告
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editNotice(@ModelAttribute("noticeEdit") Notice notice) {
		//停用时候不需要校验
		if(notice.getStatus() == null && !checkaddNotice(notice)) {
			return displayEditNotice(notice);
		}
		
		ResponseData rd  = noticeService.editNotice(notice);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditNotice(notice);
	}
	
	/**
	 * 显示公告权限编辑页面
	 * 
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value="/displayEditNoticeOfDealer.vti", method=RequestMethod.GET)
	public String displayEditNoticeOfDealer(@RequestParam("noticeId") Integer noticeId) {
		if (noticeId== null) {
			noticeId = getIntegerParameter("noticeId");
		}
		ResponseData rd =noticeService.displayEditNotice(noticeId);
		Notice notice = (Notice) rd.get("notice");
		HashMap parameters = new HashMap();
		parameters.put("noticeId",noticeId);
		//获得一个公司的集合str 在text文本显示
		String companyNameStr = "" ;
		List<NoticeDealerRelation> noticeDealerRelationList =
				noticeDealerRelationService.find(parameters, null);
		if(noticeDealerRelationList!=null){
			for(NoticeDealerRelation n : noticeDealerRelationList){
				String name = CacheOrgManager.getOrgName(n.getCompanyId());
				if(name != null){
					companyNameStr += name+"； ";
				}
			}
		}
		
		getRequest().setAttribute("companyNameStr", companyNameStr);
		
		getRequest().setAttribute("notice", notice);
		List<Company> companyList = noticeService.displayCompanyList();
		
		getRequest().setAttribute("mateUserList", companyList);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		return "/dealer/notice/distributeNotice";
	}

	/**
	 * 编辑公告权限
	 * 
	 * @param notice
	 * @return
	 */
	@RequestMapping(value="/editRoleRightsCode.vti", method=RequestMethod.POST)
	public String editRoleRightsCode(@ModelAttribute("notice") Notice notice) {
		if(notice != null){
			//删除该公告下属的经销商信息
			noticeDealerRelationService.deleteByNoticeId(notice.getId());
			
			String rightsMessage = notice.getContents();
			Notice noticeOld  = noticeService.findNotice(notice.getId());
			//复制原来的  到新的notice
			MyBeanUtils.copyPropertiesContainsDate(notice, noticeOld);
			//解析字符串集合 名字[id]; 
			Pattern p = Pattern.compile("(?<=\\[)(.+?)(?=\\])");
			Matcher m = p.matcher(rightsMessage);
			//创建中间对象
			NoticeDealerRelation noticeDealerRelation ;
			while (m.find()) {
				String companyId = m.group();
				noticeDealerRelation = new NoticeDealerRelation();
				noticeDealerRelation.setCompanyId(TypeConverter.toInteger(companyId));
				noticeDealerRelation.setNoticeId(notice.getId());
				noticeDealerRelation.setMonth(TypeConverter.toInteger(DateTime.toShortStr(notice.getStartTime())));
				noticeDealerRelationService.add(noticeDealerRelation);
			}
		}
		TransMsg transMsg = new TransMsg();
		transMsg.setStartIndex(getIntegerParameter("startIndex"));
		notice.setTitle("");//防止页面显示出title查询条件
		return listNotice(transMsg, notice);
	}

	/**
	 * 删除公告
	 * 
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value="/delete.vti", method=RequestMethod.GET)
	public void deleteRole(@RequestParam("noticeId") Integer noticeId) {
		ResponseData rd = noticeService.deleteNotice(noticeId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		writeRd(rd);
	}
	/**
	 * 添加角色表单验证
	 * 
	 * @param notice
	 * @return
	 */
	protected boolean checkaddNotice(Notice notice) {
		String name = notice.getTitle();
		if (!TypeConverter.sizeLagerZero(name)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "公告名称必须填写！");
			return false;
		}
		String summary = notice.getSummary();
		if (!TypeConverter.sizeLagerZero(summary)) {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "公告简介必须填写！");
			return false;
		}
		
		if(notice.getStartTime()==null||notice.getInvalidTime()==null){
			 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					 GlobalConstant.MSG_RESULT_CONTENT, 
					 "请补齐日期!");
			 return false;
		}
		else if(notice.getStartTime()!=null&&notice.getInvalidTime()!=null)//开始时间大于结束时间
		{
			if(DateTime.getSecondDifference(notice.getInvalidTime(),notice.getStartTime())>0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始日期大于结束日期,请修改开始日期!");
				 return false;
			}
			//获得当前日期
			Date nowDate = DateTime.toDate(DateTime.getNormalDate());
			if(DateTime.getSecondDifference(nowDate,notice.getStartTime())<0)
			{
				 message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						 GlobalConstant.MSG_RESULT_CONTENT, 
						 "开始日期小于当前日期,请修改开始日期!");
				 return false;
			}
		}
		return true;
	}
}
