package com.ava.dealer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.IFilingProposalService;
import com.ava.domain.entity.FilingProposal;
import com.ava.domain.entity.FilingProposalAttachment;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.User;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.FilingProposalAttachmentVO;
import com.ava.domain.vo.FilingProposalHis;
import com.ava.domain.vo.FilingProposalVO;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExportExcelUtil;
import com.ava.util.upload.UploadHelper;

@Controller
@RequestMapping("/dealer/filingProposalManager")
public class FilingProposalController extends Base4MvcController{
	
	Logger logger=Logger.getLogger(FilingProposalController.class);

	@Resource(name="dealer.filingProposalService")
	IFilingProposalService filingProposalService;
	
	@Autowired
	IUserMenuService userMenuService;
	
	/**
	 * 市场活动菜单页面及查询页面
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param startTime
	 * @param endTime
	 * @param dealerCode
	 * @return
	 */
	@RequestMapping(value = "/filingProposalMarketList.vti", method = RequestMethod.GET)
	public String filingProposalMarketList(TransMsg transMsg,String startTime,String endTime, String dealerCode) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer userId = SessionManager.getCurrentUserId(getRequest());
			
			if(StringUtils.isEmpty(startTime)){
				startTime = DateTime.getMonthFirstDay(new Date()) + " 00:00:00";
			}
			
			if(StringUtils.isEmpty(endTime)){
				endTime = DateTime.getMonthLastDay(new Date()) + " 23:59:59";
			}

			getRequest().setAttribute("filingProposalList", 
					filingProposalService.getFilingProposalVoList(transMsg, companyId, userId, GlobalConstant.FILING_PROPOSAL_OUT_TYPE, startTime, endTime, false, dealerCode));
			
			getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			
			getRequest().setAttribute("dealerCode", dealerCode);
			getRequest().setAttribute("transMsg", transMsg);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			getRequest().setAttribute("menuId", rd.get("menuId"));
			getRequest().setAttribute("menuType", rd.get("menuType"));
			getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/listMarketFilingProposal";
	}
	
	/**
	 * 维修报备查询及菜单打开页面
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param startTime
	 * @param endTime
	 * @param dealerCode
	 * @return
	 */
	@RequestMapping(value = "/filingProposalRepairList.vti", method = RequestMethod.GET)
	public String filingProposalRepairList(TransMsg transMsg,String startTime,String endTime, String dealerCode) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer userId = SessionManager.getCurrentUserId(getRequest());
			
			if(StringUtils.isEmpty(startTime)){
				startTime = DateTime.getMonthFirstDay(new Date()) + " 00:00:00";
			}
			
			if(StringUtils.isEmpty(endTime)){
				endTime = DateTime.getMonthLastDay(new Date()) + " 23:59:59";
			}

//			getRequest().setAttribute("filingProposalList", filingProposalService.getFilingProposalList(transMsg, companyId, GlobalConstant.FILING_PROPOSAL_REPAIR_TYPE, startTime, endTime,false));

			getRequest().setAttribute("filingProposalList", 
					filingProposalService.getFilingProposalVoList(transMsg, companyId, userId, GlobalConstant.FILING_PROPOSAL_REPAIR_TYPE, startTime, endTime, false, dealerCode));
			
			getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			
			getRequest().setAttribute("dealerCode", dealerCode);
			getRequest().setAttribute("transMsg", transMsg);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			getRequest().setAttribute("menuId", rd.get("menuId"));
			getRequest().setAttribute("menuType", rd.get("menuType"));
			getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/listRepairFilingProposal";
	}

	
	/**
	 * 
	* Description: 添加报备(市场活动及事故维修)
	* @author henggh 
	* @version 0.1 
	* @param filingProposal
	* @param selectedVehicles
	* @param filingProposalType
	* @return
	 */
	@RequestMapping(value = "/addFilingProposalNew.vti", method = RequestMethod.POST)
	public String addFilingProposalNew(Model model, @ModelAttribute("filingProposal") FilingProposal filingProposal,String selectedVehicles,Integer filingProposalType,@RequestParam("attach") MultipartFile[] files) {
		try{
			Integer userId  = getCurrentUserId();
			Integer companyId  = getCurrentCompanyId();
			List<FilingProposal> filingProposals=filingProposalService.addFilingProposalNew(files,filingProposalType,filingProposal,selectedVehicles, userId, companyId);
			model.addAttribute("filingProposalList", filingProposals);
			
			return "/pub/jsPages/closeDialogAndReloadParent";
		}catch(ProtocolParseBusinessException ex){
			message(model,GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
			setFilingProposalFailData(model,filingProposalType,filingProposal,selectedVehicles);
			if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
				return "/dealer/declareManager/addMarketFilingProposalNew";
			}else{
				return "/dealer/declareManager/addRepairFilingProposalNew";
			}
		}catch(Exception e){
			logger.info(e.getMessage());
			message(model, GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			setFilingProposalFailData(model,filingProposalType,filingProposal,selectedVehicles);
			if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
				return "/dealer/declareManager/addMarketFilingProposalNew";
			}else{
				return "/dealer/declareManager/addRepairFilingProposalNew";
			}
		}		
	}
	
	/**
	 * 
	* Description: 编辑报备(市场活动及事故维修)
	* @author henggh 
	* @version 0.1 
	* @param filingProposal
	* @param selectedVehicles
	* @param filingProposalType
	* @return
	 */
	@RequestMapping(value = "/editFilingProposal.vti", method = RequestMethod.POST)
	public String editFilingProposal(Model model, @ModelAttribute("filingProposal") FilingProposal filingProposal,Integer filingProposalId,Integer filingProposalType) {
		String selectedVehicles=null;
		Integer companyId=null;
		try{
			companyId = SessionManager.getCurrentCompanyId(getRequest());
			filingProposal.setId(filingProposalId);
			List<FilingProposal> filingProposals=filingProposalService.editFilingProposal(getRequest(),filingProposalType,filingProposal,selectedVehicles);
			getRequest().setAttribute("filingProposalList", filingProposals);
			return "/pub/jsPages/closeDialogAndReloadParent";
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			if(null==filingProposalId){
				getRequest().setAttribute("dealer", new DealerVO());
				getRequest().setAttribute("vehicleStyles", null);
			}else{
				Map map=filingProposalService.getFilingProposalInfo(companyId, filingProposalId);
				if(null==map || map.isEmpty()){
					getRequest().setAttribute("dealer", new DealerVO());
					getRequest().setAttribute("vehicleStyles", null);
				}else{
					getRequest().setAttribute("dealer", map.get("dealer"));
					getRequest().setAttribute("vehicleStyles", map.get("vehicleStyles"));
				}
			}
			getRequest().setAttribute("filingProposalList", filingProposal);
			if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
				return "/dealer/declareManager/editMarketFilingProposal";
			}else{
				return "/dealer/declareManager/editRepairFilingProposal";
			}
		}
	}
	
	/**
	 * 
	* Description: 添加报备出错页面处理
	* @author henggh 
	* @version 0.1 
	* @param filingProposalType
	* @param filingProposal
	* @param selectedVehicles
	 */
	public void setFilingProposalFailData(Model model,Integer filingProposalType,FilingProposal filingProposal,String selectedVehicles) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Map map=filingProposalService.getAddFilingProposalInfo(companyId,selectedVehicles);
			model.addAttribute("dealer", map.get("dealer"));
			model.addAttribute("vehicleStyles", map.get("vehicleStyles"));
			model.addAttribute("filingProposal", filingProposal);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
	}

	/**
	 * 
	* Description: 报备取消
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param declareType
	* @param declareStatus
	 */
	@RequestMapping(value = "/cancelFilingProposal.vti", method = RequestMethod.POST)
	public void cancelFilingProposal(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("filingProposalType")  Integer filingProposalType) {
		Map<String,String> map=null;
		try{
			map=new HashMap<String,String>();
			User user=SessionManager.getCurrentUser(getRequest());
			TransMsg transMsg=new TransMsg();
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			filingProposalService.cancelFilingProposal(filingProposalId, companyId, filingProposalType);
			map.put("message", "success");
			writeMap(map);
		}catch(ProtocolParseBusinessException ex){
			map.put("message", ex.getDesc());
			writeMap(map);
		}catch(Exception e){
			logger.info(e.getMessage());
			map.put("message", "系统异常");
			writeMap(map);
		}finally{
			map=null;
		}
	}
	
	/**
	 * 
	* Description: 添加报备页面(市场活动及事故维修)
	* @author henggh 
	* @version 0.1 
	* @param filingProposalType
	* @return
	 */
	@RequestMapping(value = "/addFilingProposalView.vti", method = RequestMethod.GET)
	public String addFilingProposalView(Integer filingProposalType) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Map map=filingProposalService.getAddFilingProposalInfo(companyId,null);
			getRequest().setAttribute("dealer", map.get("dealer"));
			getRequest().setAttribute("vehicleStyles", map.get("vehicleStyles"));
			getRequest().setAttribute("filingProposal", new FilingProposal());
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
			return "/dealer/declareManager/addMarketFilingProposalNew";
		}else{
			return "/dealer/declareManager/addRepairFilingProposalNew";
		}
	}
	
	/**
	 * 
	* Description: 编辑报备页面(市场活动及事故维修)
	* @author henggh 
	* @version 0.1 
	* @param filingProposalType
	* @return
	 */
	@RequestMapping(value = "/editFilingProposalView.vti", method = RequestMethod.GET)
	public String editFilingProposalView(Integer filingProposalId, Integer filingProposalType) {
		try{
			Map map=filingProposalService.editFilingProposalView(filingProposalId,filingProposalType);
			getRequest().setAttribute("dealer", map.get("dealer"));
			getRequest().setAttribute("vehicleStyles", map.get("vehicleStyles"));
			getRequest().setAttribute("filingProposal", map.get("filingProposal"));
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
			return "/dealer/declareManager/editMarketFilingProposal";
		}else{
			return "/dealer/declareManager/editRepairFilingProposal";
		}
	}
	
	/**
	 * 
	* Description: 报备详情页面(市场活动及事故维修)
	* @author henggh 
	* @version 0.1 
	* @param filingProposalType
	* @return
	 */
	@RequestMapping(value = "/filingProposalDetailView.vti", method = RequestMethod.GET)
	public String getFilingProposalDetailView(Integer filingProposalId, Integer filingProposalType) {
		try{
			Map map=filingProposalService.getFilingProposalDetail(filingProposalId, filingProposalType);
			getRequest().setAttribute("dealer", map.get("dealer"));
			getRequest().setAttribute("vehicleStyles", map.get("vehicleStyles"));
			getRequest().setAttribute("filingProposal", map.get("filingProposal"));
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
			return "/dealer/declareManager/viewMarketFilingProposal";
		}else{
			return "/dealer/declareManager/viewRepairFilingProposal";
		}
	}
	
	/**
	 * 
	* Description: 附件管理
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	@RequestMapping(value = "/filingProposalFileManage.vti", method = RequestMethod.GET)
	public String filingProposalFileManage(@RequestParam("filingProposalId")  Integer filingProposalId) {
		try {
			List<FilingProposalAttachmentVO> fpaVOList=filingProposalService.getFilingProposalAttachList(filingProposalId);
			getRequest().setAttribute("filingProposalAttachmentList", fpaVOList);
			getRequest().setAttribute("filingProposalId", filingProposalId);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		
		return "/dealer/declareManager/filingProposalFileManage";
	}
	
	/**
	 * 
	* Description: 删除附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param delFiles
	* @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/delFilingProposalFile.vti", method = RequestMethod.POST)
	public void delFilingProposalFile(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("delFiles")String delFiles) {
		try {
			filingProposalService.delFilingProposalFile(filingProposalId, delFiles);
			Map map=new HashMap();
			map.put("message", "success");
			writeMap(map);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
	}
	
	/**
	 * 
	* Description: 附件管理上传附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/uploadFilingProposalFile.vti", method = RequestMethod.POST)
	public String uploadFilingProposalFile(HttpServletRequest request, @RequestParam("filingProposalId")  Integer filingProposalId) {
		List<FilingProposalAttachmentVO> fpaVOList=null;
		try {
			fpaVOList=filingProposalService.uploadFile(request, filingProposalId);
			getRequest().setAttribute("filingProposalAttachmentList", fpaVOList);
			getRequest().setAttribute("filingProposalId", filingProposalId);
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			fpaVOList=filingProposalService.getFilingProposalAttachList(filingProposalId);
			request.setAttribute("filingProposalAttachmentList", fpaVOList);
			request.setAttribute("filingProposalId", filingProposalId);
		}finally{
			fpaVOList=null;
		}
		return "/dealer/declareManager/filingProposalFileManage";
	}
	
	/**
	 * 
	* Description: 报备审批列表页面
	* @author henggh 
	* @version 0.1 
	* @param saleCenterId
	* @param beginTime
	* @param endTime
	* @return
	 */
	@RequestMapping(value="/approveFilingProposalList.vti", method = RequestMethod.GET)
	public String approveFilingProposalList(TransMsg transMsg,Integer selectSaleCenterId,String dealerCode,String startTime,String endTime){
		List<FilingProposalVO> filingProposalVOList=null;
		try {
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			
			if(StringUtils.isEmpty(startTime)){
				startTime = DateTime.getMonthFirstDay(new Date()) + " 00:00:00";
			}
			
			if(StringUtils.isEmpty(endTime)){
				endTime = DateTime.getMonthLastDay(new Date()) + " 23:59:59";
			}
			
			filingProposalVOList=filingProposalService.approveFilingProposalList(transMsg,currentCompanyId,currentUserId,selectSaleCenterId,dealerCode,startTime,endTime,false);
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			getRequest().setAttribute("filingProposalVOList", filingProposalVOList);

			getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			
			getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			if(null!=dealerCode && !"".equals(dealerCode)){
				getRequest().setAttribute("dealerCode", dealerCode);
			}else{
				getRequest().setAttribute("dealerCode", "");
			}
			getRequest().setAttribute("transMsg", transMsg);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			getRequest().setAttribute("menuId", rd.get("menuId"));
			getRequest().setAttribute("menuType", rd.get("menuType"));
			getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("filingProposalVOList", filingProposalVOList);
		}finally{
			filingProposalVOList=null;
		}
		return "/dealer/declareManager/listApprovalFilingProposal";
	}
	
	/**
	 * 
	* Description: 历史报备列表页面
	* @author henggh 
	* @version 0.1 
	* @param saleCenterId
	* @param beginTime
	* @param endTime
	* @return
	 */
	@RequestMapping(value="/listFilingProposalHis.vti", method = RequestMethod.GET)
	public String listFilingProposalHis(TransMsg transMsg,Integer selectSaleCenterId,String dealerCode,String startTime,String endTime){
		List<FilingProposalHis> filingProposalHisList=null;
		try {
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			
			if(StringUtils.isEmpty(startTime)){
				startTime = DateTime.getMonthFirstDay(new Date()) + " 00:00:00";
			}
			
			if(StringUtils.isEmpty(endTime)){
				endTime = DateTime.getMonthLastDay(new Date()) + " 23:59:59";
			}
			
			filingProposalHisList=filingProposalService.getFilingProposalHisList(transMsg, currentCompanyId,currentUserId,selectSaleCenterId,dealerCode,startTime,endTime,false);
			getRequest().setAttribute("filingProposalHisList", filingProposalHisList);
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			if(null!=dealerCode && !"".equals(dealerCode)){
				getRequest().setAttribute("dealerCode", dealerCode);
			}else{
				getRequest().setAttribute("dealerCode", "");
			}
			getRequest().setAttribute("transMsg", transMsg);
			ResponseData rd = userMenuService.siteMenuType(getRequest());
			getRequest().setAttribute("menuId", rd.get("menuId"));
			getRequest().setAttribute("menuType", rd.get("menuType"));
			getRequest().setAttribute("userMenuRelationVOList", rd.get("userMenuRelationVOList"));
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("filingProposalHisList", filingProposalHisList);
		}finally{
			filingProposalHisList=null;
		}
		return "/dealer/declareManager/listFilingProposalHis";
	}
	
	/**
	 * 
	* Description: 报备审批页面
	* @author henggh 
	* @version 0.1 
	* @param saleCenterId
	* @param beginTime
	* @param endTime
	* @return
	 */
	@RequestMapping(value="/approveFilingProposalView.vti", method = RequestMethod.GET)
	public String approveFilingProposalView(Integer filingProposalId){
		try {
			Map map=filingProposalService.approveFilingProposalView(filingProposalId);
			getRequest().setAttribute("dealer", map.get("dealer"));
			getRequest().setAttribute("vehicleStyles", map.get("vehicleStyles"));
			getRequest().setAttribute("filingProposal", map.get("filingProposal"));
			getRequest().setAttribute("fpaVOList", map.get("fpaVOList"));
			getRequest().setAttribute("filingProposalId", filingProposalId);
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("dealer",new DealerVO());
			getRequest().setAttribute("vehicleStyles", null);
			getRequest().setAttribute("filingProposal", new FilingProposal());
			getRequest().setAttribute("fpaVOList", null);
			getRequest().setAttribute("filingProposalId", filingProposalId);
		}
		return "/dealer/declareManager/approveFilingProposal";
	}
	
	/**
	 * 
	* Description: 报备审批
	* @author henggh 
	* @version 0.1 
	* @param saleCenterId
	* @param beginTime
	* @param endTime
	* @return
	 */
	@RequestMapping(value="/approveFilingProposal.vti", method = RequestMethod.POST)
	public String approveFilingProposal(FilingProposal filingProposal,Integer filingProposalId,Integer advice,Integer selectSaleCenterId,String startTime,String endTime){
		List<FilingProposalVO> filingProposalVOList=null;
		try {
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			filingProposal.setStatus(advice);
			filingProposalVOList=filingProposalService.approveFilingProposal(filingProposal,filingProposalId,currentCompanyId,currentUserId);
			getRequest().setAttribute("filingProposalVOList", filingProposalVOList);
			return "/pub/jsPages/closeDialogAndReloadParent";
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("filingProposalVOList", filingProposalVOList);
		}finally{
			filingProposalVOList=null;
		}
		return "/dealer/declareManager/approveFilingProposal";
	}
	
	/**
	 * 
	* Description: 查看附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param filingProposalAttachmentId
	 */
	@RequestMapping(value = "/getAttachFileDown.vti", method = RequestMethod.GET)
	public void declareAttachFileDown(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("fileId")Integer fileId) {
		try {
			FilingProposalAttachment filingProposalAttachment=filingProposalService.getFilingProposalAttachment(filingProposalId, fileId);
			UploadHelper.downloadFile(filingProposalAttachment.getFullPath(),getResponse());
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
	}
	
	/**
	 * 导出报备数据
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param filingProposalType
	 * @param startTime
	 * @param endTime
	 * @param dealerCode
	 */
	@RequestMapping(value = "/exportFilingProposal.vti", method = RequestMethod.GET)
	public void exportFilingProposal(TransMsg transMsg,Integer filingProposalType,String startTime,String endTime, String dealerCode) {
		try {
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer userId = SessionManager.getCurrentUserId(getRequest());
			//List<FilingProposal> filingProposalList=filingProposalService.getFilingProposalList(transMsg, companyId, filingProposalType, startTime, endTime,true);
			List<FilingProposalVO> filingProposalList =
					filingProposalService.getFilingProposalVoList(transMsg, companyId, userId, filingProposalType, startTime, endTime, true, dealerCode);
			//导出
			exportFilingProposal2Excel(filingProposalList,filingProposalType);
			if(null!=startTime && !"".equals(startTime)){
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}else{
				getRequest().setAttribute("startTime", "");
			}
			if(null!=endTime && !"".equals(endTime)){
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}else{
				getRequest().setAttribute("endTime", "");
			}
			getRequest().setAttribute("transMsg", transMsg);
		} catch (ParseException e) {
			
		}
	}
	
	/**
	 * 
	* Description: 导出报备审批数据
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param vehicleId
	* @param declareType
	* @param startTime
	* @param endTime
	 */
	@RequestMapping(value = "/exportApprovalFilingProposal.vti", method = RequestMethod.GET)
	public void exportApprovalFilingProposal(TransMsg transMsg,Integer selectSaleCenterId,String dealerCode,String startTime,String endTime) {
		List<FilingProposalVO> filingProposalVOList=null;
		try {
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			filingProposalVOList=filingProposalService.approveFilingProposalList(transMsg,currentCompanyId,currentUserId,selectSaleCenterId,dealerCode,startTime,endTime,true);
			exportApprovalFilingProposal2Excel(filingProposalVOList);
			getRequest().setAttribute("filingProposalVOList", filingProposalVOList);
			if(null!=startTime && !"".equals(startTime)){
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}else{
				getRequest().setAttribute("startTime", "");
			}
			if(null!=endTime && !"".equals(endTime)){
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}else{
				getRequest().setAttribute("endTime", "");
			}
			getRequest().setAttribute("transMsg", transMsg);
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("filingProposalVOList", filingProposalVOList);
		}finally{
			filingProposalVOList=null;
		}
	}
	
	/**
	 * 
	* Description: 导出历史报备数据
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param vehicleId
	* @param declareType
	* @param startTime
	* @param endTime
	 */
	@RequestMapping(value = "/exportFilingProposalHis.vti", method = RequestMethod.GET)
	public void exportFilingProposalHis(TransMsg transMsg,Integer selectSaleCenterId,String dealerCode,String startTime,String endTime) {
		List<FilingProposalHis> filingProposalHisList=null;
		try {
			Integer currentCompanyId = SessionManager.getCurrentCompanyId(getRequest());
			Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
			filingProposalHisList=filingProposalService.getFilingProposalHisList(transMsg, currentCompanyId,currentUserId,selectSaleCenterId,dealerCode,startTime,endTime,true);
			exportFilingProposalHis(filingProposalHisList);
			getRequest().setAttribute("filingProposalHisList", filingProposalHisList);
			List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(currentUserId, currentCompanyId);
			getRequest().setAttribute("orgList", orgList);
			if(null!=startTime && !"".equals(startTime)){
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}else{
				getRequest().setAttribute("startTime", "");
			}
			if(null!=endTime && !"".equals(endTime)){
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}else{
				getRequest().setAttribute("endTime", "");
			}
			if(null!=selectSaleCenterId && selectSaleCenterId.intValue()>=1){
				getRequest().setAttribute("selectedSaleCenterId", selectSaleCenterId);
			}else{
				getRequest().setAttribute("selectedSaleCenterId", orgList.get(0).getId());
			}
			if(null!=dealerCode && !"".equals(dealerCode)){
				getRequest().setAttribute("dealerCode", dealerCode);
			}else{
				getRequest().setAttribute("endTime", "");
			}
			getRequest().setAttribute("transMsg", transMsg);
		}catch(Exception e){
			logger.info(e.getMessage());
			if(e instanceof ProtocolParseBusinessException){
				ProtocolParseBusinessException ex=(ProtocolParseBusinessException) e;
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
			}
			getRequest().setAttribute("filingProposalHisList", filingProposalHisList);
		}finally{
			filingProposalHisList=null;
		}
	}
		
	/**
	 * 导出报备数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param filingProposalList
	 * @param filingProposalType
	 */
	public  void exportFilingProposal2Excel(List<FilingProposalVO> filingProposalList, Integer filingProposalType) {
		String[] titles = {"经销商名称","网络代码","开始日期","结束日期","状态","申请时间","目的地","审核人","审核时间"};
		String fileName=null;
		if(filingProposalType==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
			fileName="市场活动_"+DateTime.toShortDateTime(new Date());
		}else{
			fileName="事故维修_"+DateTime.toShortDateTime(new Date());
		}
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if(null!=filingProposalList && !filingProposalList.isEmpty()){
			for (int i = 0; i < filingProposalList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				FilingProposalVO filingProposal = filingProposalList.get(i);
				Integer y = 0;
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(filingProposal.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(filingProposal.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getStartTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getEndTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(filingProposal.getFilingStatus());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getProposalTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(filingProposal.getDescription());
				cell = row.createCell(++ y);
				cell.setCellValue("");
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getApprovalTime()));		
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}	
	
	/**
	 * 导出报备审批数据到excel
	 * @param violations
	 */
	public  void exportApprovalFilingProposal2Excel(List<FilingProposalVO> filingProposalList) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态","申请时间","开始时间","结束时间","报备类型","报备状态"};
		String fileName="报备审批_"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if(null!=filingProposalList && !filingProposalList.isEmpty()){
			for (int i = 0; i < filingProposalList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				FilingProposalVO filingProposal = filingProposalList.get(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(filingProposal.getSaleCenterName());
				cell = row.createCell(1);
				cell.setCellValue(filingProposal.getProvinceName());
				cell = row.createCell(2);
				cell.setCellValue(filingProposal.getCityName());
				cell = row.createCell(3);
				cell.setCellValue(filingProposal.getDealerCode());
				cell = row.createCell(4);
				cell.setCellValue(filingProposal.getCompanyName());
				cell = row.createCell(5);
				cell.setCellValue(filingProposal.getIsKeyCityNick());
				cell = row.createCell(6);
				cell.setCellValue(filingProposal.getDealerTypeNick());
				cell = row.createCell(7);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getProposalTime()));
				if(filingProposal.getType().intValue()==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
					cell = row.createCell(8);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getStartTime()));
					cell = row.createCell(9);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getEndTime()));
				}else{
					cell = row.createCell(8);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getStartTime()));
					cell = row.createCell(9);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getEndTime()));
				}
				cell = row.createCell(10);
				cell.setCellValue(filingProposal.getFilingType());
				cell = row.createCell(11);
				cell.setCellValue(filingProposal.getFilingStatus());
			}
		}
		
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
	/**
	 * 导出历史报备数据到excel
	 * @param violations
	 */
	public  void exportFilingProposalHis(List<FilingProposalHis> filingProposalList) {
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称","经销商状态","网络形态","车型","车牌","申请时间","开始时间","结束时间","报备类型","报备状态","审核人","审核时间"};
		String fileName="历史报备_"+DateTime.toShortDateTime(new Date());
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet(fileName);
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if(null!=filingProposalList && !filingProposalList.isEmpty()){
			for (int i = 0; i < filingProposalList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				FilingProposalHis filingProposal = filingProposalList.get(i);
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(filingProposal.getSaleCenterName());
				cell = row.createCell(1);
				cell.setCellValue(filingProposal.getProvinceName());
				cell = row.createCell(2);
				cell.setCellValue(filingProposal.getCityName());
				cell = row.createCell(3);
				cell.setCellValue(filingProposal.getDealerCode());
				cell = row.createCell(4);
				cell.setCellValue(filingProposal.getCompanyName());
				cell = row.createCell(5);
				cell.setCellValue(filingProposal.getIsKeyCityNick());
				cell = row.createCell(6);
				cell.setCellValue(filingProposal.getDealerTypeNick());
				cell = row.createCell(7);
				cell.setCellValue(filingProposal.getVehicleStyle());
				cell = row.createCell(8);
				cell.setCellValue(filingProposal.getPlateNumber());
				cell = row.createCell(9);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getProposalTime()));
				if(filingProposal.getType().intValue()==GlobalConstant.FILING_PROPOSAL_OUT_TYPE){
					cell = row.createCell(10);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getStartTime()));
					cell = row.createCell(11);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getEndTime()));
				}else{
					cell = row.createCell(10);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getStartTime()));
					cell = row.createCell(11);
					cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getEndTime()));
				}
				cell = row.createCell(12);
				cell.setCellValue(filingProposal.getFilingType());
				cell = row.createCell(13);
				cell.setCellValue(filingProposal.getFilingStatus());
				cell = row.createCell(14);
				cell.setCellValue(filingProposal.getApprovalName());	
				cell = row.createCell(15);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getApprovalTime()));	
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	} 
}