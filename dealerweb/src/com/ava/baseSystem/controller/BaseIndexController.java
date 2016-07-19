package com.ava.baseSystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.INoticeService;
import com.ava.domain.entity.User;
import com.ava.domain.vo.MenuVO;
import com.ava.domain.vo.UserMenuRelationVO;
import com.ava.facade.IHomepage4BaseFacade;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheUserMenuManager;

@Controller
@RequestMapping(value="/base/index")
public class BaseIndexController extends Base4MvcController {

	@Autowired
	private IHomepage4BaseFacade facade;
	
	@Autowired
	private INoticeService noticeService;
	
	@RequestMapping(value = "/displayMain.vti", method=RequestMethod.GET)
	public String displayMain(TransMsg transMsg) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = facade.displayMain(transMsg, companyId, userId, getRequest());
		Map map = (Map) rd.getData();
		
		getRequest().setAttribute("company", map.get("company"));
		getRequest().setAttribute("userMenuRelationVOList", CacheUserMenuManager.getUserMenuRelationVOList(userId));
		
		String link = "/dealer/include/main";
		List<UserMenuRelationVO> userMenuRelationVOList = CacheUserMenuManager.getUserMenuRelationVOList(userId);
		if (userMenuRelationVOList !=null && userMenuRelationVOList.size() > 0) {
			UserMenuRelationVO userMenuRelationVO = userMenuRelationVOList.get(0);
			
			if (userMenuRelationVO != null && userMenuRelationVO.getMenu() != null) {
				MenuVO menuVO = userMenuRelationVO.getMenu();
				if (menuVO.getLinks().indexOf("?") >= 1 ) {
					link = menuVO.getLinks() + "&menuId= " + menuVO.getId() + "&menuType=1";
				} else {
					link = menuVO.getLinks() + "?menuId= " + menuVO.getId() + "&menuType=1";
				}
			}
		} 
		return link;
	}
	
	@RequestMapping(value = "/displayHome.vti", method=RequestMethod.GET)
	public String displayHome() {
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = noticeService.listNoticeForHeader(companyId);
		getRequest().setAttribute("noticeList", rd.getFirstItem());
		
		if(currentUserId != null && currentUserId.intValue() > 0){
			return "/dealer/include/home";
		}else{
			getRequest().setAttribute("user", new User());
			return "/dealer/registerLogin/login";
		}
	}
}
