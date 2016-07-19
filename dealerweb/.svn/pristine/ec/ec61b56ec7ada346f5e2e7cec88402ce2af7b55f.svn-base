 package com.ava.dealer.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dealer.service.ICompanyCarStyleRelationService;
import com.ava.dealer.service.IProposalService;
import com.ava.dealer.service.IVehicleInstallationPlanService;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.HelpFile;
import com.ava.domain.entity.Org;
import com.ava.domain.entity.ProposalAttachment;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;
import com.ava.domain.vo.ProposalCompanyVehcileInfo;
import com.ava.domain.vo.ProposalFindVO;
import com.ava.domain.vo.ReplaceFindVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.facade.IPoposalFacade;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.MyStringUtils;
import com.ava.util.TypeConverter;
import com.ava.util.ValidatorUtil;
import com.ava.util.excel.ExportExcelUtil;
import com.ava.util.upload.UploadHelper;

@Controller
@RequestMapping(value="/dealer/proposal")
public class ProposalController extends Base4MvcController {
	
	Logger logger = Logger.getLogger(ProposalController.class);
	
	@Autowired
	IProposalService proposalService;
	
	@Autowired
	IPoposalFacade poposalFacade;
	
	@Autowired
	IVehicleService vehicleService;
	
	@Autowired
	ICompanyCarStyleRelationService companyCarStyleRelationService;
	
	@Autowired
	IVehicleInstallationPlanService vehicleInstallationPlanService;
	
	/**
	 * 显示新装申请列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/search.vti", method=RequestMethod.GET)
	public String listProposal(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo, @RequestParam("seriesId") Integer seriesId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = proposalService.listProposal(transMsg, vo, seriesId, companyId, currentUserId, false);

		initSelectList(companyId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", vo);
		getRequest().setAttribute("proposalCompanyVehcileInfoList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		String seriesName = "";
		if (seriesId == 9) {
			seriesName = "凌渡";
		} else if (seriesId == 14) {
			seriesName = "夏朗";
		} else if (seriesId == 14) {
			seriesName = "Cross Polo";
		}
		getRequest().setAttribute("seriesId", seriesId);
		getRequest().setAttribute("seriesName", seriesName);
		
		siteMenuType(rd);
		
		return "/dealer/proposal/listProposal";
	}
	
	/**
	 * 显示新增申请单的页面
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param seriesId
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddProposal(@ModelAttribute("proposalAdd") ProposalCompanyVehcileInfo vo, @RequestParam("seriesId") Integer seriesId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = proposalService.displayAddProposal(vo, companyId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", rd.get("proposalCompanyVehcileInfo"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		getRequest().setAttribute("seriesId", seriesId);
		
		initSelectListForNewConfig(companyId, seriesId);
		
		return "/dealer/proposal/addProposal";
	}

	/**
	 * 添加申请单
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param seriesId
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addProposal(HttpServletRequest request, @ModelAttribute("proposalAdd") ProposalCompanyVehcileInfo vo, @RequestParam("seriesId") Integer seriesId) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (!check4addProposal(vo, multipartRequest)) {
			return displayAddProposal(vo, seriesId);
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(request);
		Integer currentUserId = SessionManager.getCurrentUserId(request);
		
		List<MultipartFile> multipartFiles = new LinkedList<MultipartFile>();
		multipartFiles = multipartRequest.getFiles("attach");
		
		vo.getProposal().setType(GlobalConstant.PROPOSAL_TYPE_INSTALLATION);
		ResponseData rd = poposalFacade.addProposal(vo, multipartFiles, currentUserId, companyId);
		if (rd.getCode() == 1) {
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddProposal(vo, seriesId);
	}
	
	/**
	 * [新装]显示编辑申请单页面
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param companyId
	 * @param seriesId
	 * @return
	 */
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditProposal(@RequestParam("proposalId") Integer proposalId, @RequestParam("companyId") Integer companyId, @RequestParam("seriesId") Integer seriesId) {
		ResponseData rd = proposalService.displayEditProposal(proposalId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", rd.get("proposalCompanyVehcileInfo"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		getRequest().setAttribute("seriesId", seriesId);
		
		initSelectListForNewConfig(companyId, seriesId);
		return "/dealer/proposal/editProposal";
	}
	
	/**
	 * [新装]编辑申请单
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param seriesId
	 * @return
	 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editProposal(@ModelAttribute("proposalEdit") ProposalCompanyVehcileInfo vo, @RequestParam("seriesId") Integer seriesId) {
		Integer companyId = vo.getCompany().getId();
		if (!check4editProposal(vo)) {
			return displayEditProposal(vo.getProposal().getId(), companyId, seriesId);
		}
		
		ResponseData rd  = poposalFacade.editProposal(vo);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
			
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditProposal(vo.getProposal().getId(), companyId, seriesId);
	}
	
	/**
	 * [换装]显示编辑申请单页面
	 * 
	 * @param proposal
	 * @param proposalId
	 * @return
	 */
	@RequestMapping(value="/displayEditReplace.vti", method=RequestMethod.GET)
	public String displayEditReplace(@RequestParam("proposalId") Integer proposalId, @RequestParam("companyId") Integer companyId) {
		
		ResponseData rd = proposalService.displayEditProposal(proposalId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", rd.get("proposalCompanyVehcileInfo"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		return "/dealer/proposal/editProposal";
	}
	
	/**
	 * [换装]编辑申请单
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/editReplace.vti", method=RequestMethod.POST)
	public String editReplace(@ModelAttribute("proposalEdit") ProposalCompanyVehcileInfo vo) {
		Integer companyId = vo.getCompany().getId();
		if (!check4editProposal(vo)) {
			return displayEditReplace(vo.getProposal().getId(), companyId);
		}
		
		ResponseData rd  = poposalFacade.editProposal(vo);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
			
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayEditReplace(vo.getProposal().getId(), companyId);
	}
	
	/**
	 * [拆除]显示拆除车辆页面
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value="/displayDemolitionVehicle.vti", method=RequestMethod.GET)
	public String displayDemolitionVehicle(@ModelAttribute("vehicleVO") VehicleVO vehicle) {
		ResponseData rd = proposalService.displayVehicleDemolition(vehicle);
		getRequest().setAttribute("vehicleVO", rd.get("vehicleVO"));
		
		siteMenuType(rd);
		List<CarStyle> carStyleList = vehicleInstallationPlanService.getDemolitionCarStyleList();
		getRequest().setAttribute("carStyleList", carStyleList);
		
		return "/dealer/proposal/demolitionVehicle";
	}

	/**
	 * [拆除]根据车型或车辆拆除车辆
	 * @author liuq 
	 * @version 0.1 
	 * @param vehicle
	 * @return
	 */
	@RequestMapping(value="/demolitionVehicle.vti", method=RequestMethod.POST)
	public String demolitionVehicle(@ModelAttribute("vehicleVO") VehicleVO vehicle) {
		String userName = SessionManager.getCurrentUserNickName(getRequest());
		ResponseData rd = poposalFacade.demolitionVehicle(vehicle, userName);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else if (rd.getCode() == -1){
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayDemolitionVehicle(vehicle);
	}
	
	/**
	 * 取消申请
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param proposalType
	 */
	@RequestMapping(value="/cancelProposal.vti", method=RequestMethod.GET)
	public void cancelProposal(@RequestParam("proposalId") Integer proposalId, @RequestParam("proposalType") Integer proposalType) {
		ResponseData rd  = poposalFacade.cancelProposal(proposalId, proposalType);
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
	 * 显示审批页面
	 * 
	 * @param proposal
	 * @param proposalId
	 * @return
	 */
	@RequestMapping(value="/displayProposalApproval.vti", method=RequestMethod.GET)
	public String displayProposalApproval(@RequestParam("proposalId") Integer proposalId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
	
		ResponseData rd = proposalService.displayProposalApproval(proposalId, companyId, getRequest());
		getRequest().setAttribute("proposalCompanyVehcileInfo", rd.get("proposalCompanyVehcileInfo"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		return "/dealer/proposal/proposalApproval";
	}
	
	/**
	 * 申请单审批
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/proposalApproval.vti", method=RequestMethod.POST)
	public String proposalApproval(@ModelAttribute("proposalEdit") ProposalCompanyVehcileInfo vo) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd  = poposalFacade.proposalApproval(vo, companyId,getRequest());
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return "/pub/jsPages/closeDialogAndReloadParent";
	}

	/**
	 * 显示安装完成页面
	 * 
	 * @param proposal
	 * @param proposalId
	 * @return
	 */
	@RequestMapping(value="/displayInstallationComplete.vti", method=RequestMethod.GET)
	public String displayInstallationComplete(@ModelAttribute("proposalAdd") ProposalCompanyVehcileInfo vo,@RequestParam("proposalId") Integer proposalId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = proposalService.displayInstallationComplete(vo, proposalId, companyId, getRequest());
		getRequest().setAttribute("vo", rd.get("proposalCompanyVehcileInfo"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectList(companyId);
		return "/dealer/proposal/installationComplete";
	}
	
	/**
	 * 安装完成
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value="/installationComplete.vti", method=RequestMethod.POST)
	public String installationComplete(@ModelAttribute("proposalEdit") ProposalCompanyVehcileInfo vo) {
		Integer proposalId = vo.getProposal().getId();
		if (!checkInstallationComplete(vo)) {
			return displayInstallationComplete(vo, proposalId);
		}
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd  = poposalFacade.installationComplete(vo, companyId,getRequest());
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayInstallationComplete(vo, proposalId);
	}
	
	/**
	 * 查看新装申请单详细信息
	 * 
	 * @param proposalId
	 * @return 
	 */
	@RequestMapping(value="/view.vti", method=RequestMethod.GET)
	public String viewProposal(@RequestParam("proposalId") Integer proposalId) {
		
		ResponseData rd = proposalService.displayEditProposal(proposalId);
		getRequest().setAttribute("proposalAttachmentList", rd.get("proposalAttachmentList"));
		getRequest().setAttribute("vo", rd.get("proposalCompanyVehcileInfo"));
		
		return "/dealer/proposal/viewProposal";
	}

	/**
	 * 新装申请导出EXCEL
	 * @param transMsg
	 * @param proposal
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/outputProposalExcel.vti", method = RequestMethod.GET)
	public void outputProposalExcel(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,  @RequestParam("seriesId") Integer seriesId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = proposalService.listProposal(transMsg, vo, seriesId, companyId, currentUserId, true);
		writeExcelProposal((List<ProposalFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出数据到excel 新装申请
	 * @param violations
	 */
	public  void writeExcelProposal(List<ProposalFindVO> proposalCompanyVehcileInfoList) {
		
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称",
				"经销商状态","网络形态","车型","VIN","车牌","上牌日期","上牌人","联系人","联系电话","提交时间","状态","一级网点代码","一级网点名称"};
		String fileName ="";
		
		fileName="新装申请信息"+DateTime.toShortDateTime(new Date());
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("新装申请");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (proposalCompanyVehcileInfoList != null && proposalCompanyVehcileInfoList.size() > 0) {
			for (int i = 0; i < proposalCompanyVehcileInfoList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ProposalFindVO vo = proposalCompanyVehcileInfoList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDate(vo.getLicensingTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getLicensingExecutorName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getContactName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getContactPhone());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getProposalTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 显示新装申请审批列表
	 * 
	 * @param transMsg
	 * @param proposal
	 * @return
	 */
	@RequestMapping(value="/listApproval.vti", method=RequestMethod.GET)
	public String listApproval(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		
		if (proposalType != null) {
			vo.getProposal().setType(proposalType);
		}
		ResponseData rd = proposalService.listProposal(transMsg, vo, null, companyId, currentUserId, false);
		
		initSelectList(companyId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", vo);
		getRequest().setAttribute("proposalCompanyVehcileInfoList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/proposal/listInstallationApproval";
	}
	
	/**
	 * 新装申请审批列表导出EXCEL
	 * @param transMsg
	 * @param proposal
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportInstallationApproval.vti", method = RequestMethod.GET)
	public void exportInstallationApproval(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		transMsg.setPageSize(Integer.MAX_VALUE);
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = proposalService.listProposal(transMsg, vo, null, companyId, currentUserId, true);
		List<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = (List<ProposalCompanyVehcileInfo>)rd.getFirstItem();
		excelInstallationApproval(proposalCompanyVehcileInfoList);
	}
	
	/**
	 * 导出数据到excel 新装申请审批列表
	 * @param violations
	 */
	public void excelInstallationApproval(List<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList) {
		
		String[] titles = {"分销中心", "经销商名称",
				"网络代码","网络形态","车牌","车型","VIN","上牌日期","上牌人", "提交时间","状态"};
		String fileName ="";
		
		fileName="新装申请信息"+DateTime.toShortDateTime(new Date());
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("新装申请");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (proposalCompanyVehcileInfoList != null && proposalCompanyVehcileInfoList.size() > 0) {
			for (int i = 0; i < proposalCompanyVehcileInfoList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ProposalCompanyVehcileInfo vo = proposalCompanyVehcileInfoList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 1;
				cell.setCellValue(vo.getCompany().getParentName());
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getCompany().getCnName());
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getCompany().getDealerCode());
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getCompany().getDealerType_Nick());
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getVehicle().getPlateNumber());
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getVehicle().getCarStyleId_Nick());
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getVehicle().getVin());
				cell = row.createCell(y ++);
				cell.setCellValue(DateTime.toNormalDate(vo.getVehicle().getLicensingTime()));
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getVehicle().getLicensingExecutorName());
				cell = row.createCell(y ++);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getProposal().getProposalTime()));
				cell = row.createCell(y ++);
				cell.setCellValue(vo.getProposal().getStatus_nick());
				cell = row.createCell(y ++);
				
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 显示拆除申请列表
	 * 
	 * @param transMsg
	 * @param proposal
	 * @return
	 */
	@RequestMapping(value="/listDemolition.vti", method=RequestMethod.GET)
	public String listDemolition(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if (proposalType != null) {
			vo.getProposal().setType(proposalType);
		}
		ResponseData rd = proposalService.listDemolition(transMsg, vo, companyId, false);
		
		initSelectList(companyId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", vo);
		getRequest().setAttribute("proposalCompanyVehcileInfoList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/proposal/listDemolitionProposal";
		
	}
	
	
	/**
	 * 显示拆除申请审批列表
	 * 
	 * @param transMsg
	 * @param proposal
	 * @return
	 */
	@RequestMapping(value="/listDemolitionApproval.vti", method=RequestMethod.GET)
	public String listDemolitionApproval(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if (proposalType != null) {
			vo.getProposal().setType(proposalType);
		}
		ResponseData rd = proposalService.listDemolition(transMsg, vo, companyId, false);
		
		initSelectList(companyId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", vo);
		getRequest().setAttribute("proposalCompanyVehcileInfoList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/proposal/listDemolitionApproval";
		
	}
	
	/**
	 * 拆除申请审批列表导出EXCEL
	 * 
	 * @param transMsg
	 * @param proposal
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exportDemolitionApproval.vti", method=RequestMethod.GET)
	public void exportDemolitionApproval(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		if (proposalType != null) {
			vo.getProposal().setType(proposalType);
		}
		ResponseData rd = proposalService.listDemolition(transMsg, vo, companyId, true);
		List<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = (List<ProposalCompanyVehcileInfo>)rd.getFirstItem();
		excelDemolitionApproval(proposalCompanyVehcileInfoList);
		
	}
	
	/**
	 * 导出数据到excel 拆除申请审批列表
	 * @param violations
	 */
	public void excelDemolitionApproval(List<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList) {
		String[] titles = {"分销中心","经销商名称",
				"网络代码","网络形态","车牌","车型","VIN","通讯号","退出时间","状态"};
		String fileName ="";
		
		fileName="拆除申请信息"+DateTime.toShortDateTime(new Date());
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("拆除申请");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (proposalCompanyVehcileInfoList != null && proposalCompanyVehcileInfoList.size() > 0) {
			for (int i = 0; i < proposalCompanyVehcileInfoList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ProposalCompanyVehcileInfo vo = proposalCompanyVehcileInfoList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vo.getCompany().getParentName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompany().getCnName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompany().getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompany().getDealerType_Nick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVehicle().getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVehicle().getCarStyleId_Nick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVehicle().getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getBoxOperation().getBox().getSimMobile());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDate(vo.getBoxOperation().getExitTime()));
				cell = row.createCell(++ y);
				String configureStatus_Nick = "";
				Integer proposalStatusUnpassed = GlobalConstant.PROPOSAL_STATUS_UNPASSED;
				if (vo.getProposal().getStatus() == proposalStatusUnpassed) {
					configureStatus_Nick = "拒绝";
				} else {
					configureStatus_Nick = vo.getVehicle().getConfigureStatus_Nick();
				}
				cell.setCellValue(configureStatus_Nick);
				cell = row.createCell(++ y);
				
			}
		}
		
	
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 拆除申请
	 * 
	 * @param proposalCompanyVehcileInfo
	 * @return
	 */
	@RequestMapping(value="/demolitionProposal.vti", method=RequestMethod.GET)
	public void demolitionProposal(@RequestParam("vehicleId") Integer vehicleId, 
			@RequestParam("proposalType") Integer proposalType) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd  = poposalFacade.addDemolitionProposal(vehicleId, currentUserId, companyId, proposalType);
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
	 * 拆除完成
	 * 
	 * @param proposalCompanyVehcileInfo
	 * @return
	 */
	@RequestMapping(value="/demolitionComplete.vti", method=RequestMethod.GET)
	public void demolitionComplete(@RequestParam("vehicleId") Integer vehicleId, 
			@RequestParam("proposalType") Integer proposalType) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd  = poposalFacade.demolitionComplete(vehicleId, companyId, currentUserId, proposalType);
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
	 * 拆除申请导出EXCEL
	 * 
	 * @param transMsg
	 * @param proposal
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/outputDemolitionExcel.vti", method=RequestMethod.GET)
	public void outputDemolitionExcel(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if (proposalType != null) {
			vo.getProposal().setType(proposalType);
		}
		ResponseData rd = proposalService.listDemolition(transMsg, vo, companyId, true);
		List<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList = (List<ProposalCompanyVehcileInfo>)rd.getFirstItem();
		writeExcelDemolitionProposal(proposalCompanyVehcileInfoList);
		
	}
	
	/**
	 * 导出数据到excel 拆除申请
	 * @param violations
	 */
	public void writeExcelDemolitionProposal(List<ProposalCompanyVehcileInfo> proposalCompanyVehcileInfoList) {
		
		String[] titles = {"分销中心","省份","城市","经销商名称",
				"网络代码","经销商状态","网络形态","车牌","车型","VIN","通讯号","退出时间","状态"};
		String fileName ="";
		
		fileName="拆除申请信息"+DateTime.toShortDateTime(new Date());
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("拆除申请");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (proposalCompanyVehcileInfoList != null && proposalCompanyVehcileInfoList.size() > 0) {
			for (int i = 0; i < proposalCompanyVehcileInfoList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ProposalCompanyVehcileInfo vo = proposalCompanyVehcileInfoList.get(i);
				HSSFCell cell = row.createCell(0);
				
				cell.setCellValue(vo.getCompany().getParentName());
				cell = row.createCell(1);
				cell.setCellValue(vo.getCompany().getRegionProvinceId_Nick());
				cell = row.createCell(2);
				cell.setCellValue(vo.getCompany().getRegionCityId_Nick());
				cell = row.createCell(3);
				cell.setCellValue(vo.getCompany().getCnName());
				cell = row.createCell(4);
				cell.setCellValue(vo.getCompany().getDealerCode());
				cell = row.createCell(5);
				cell.setCellValue(vo.getCompany().getIsKeyCity_Nick());
				cell = row.createCell(6);
				cell.setCellValue(vo.getCompany().getDealerType_Nick());
				cell = row.createCell(7);
				cell.setCellValue(vo.getVehicle().getPlateNumber());
				cell = row.createCell(8);
				cell.setCellValue(vo.getVehicle().getCarStyleId_Nick());
				cell = row.createCell(9);
				cell.setCellValue(vo.getVehicle().getVin());
				cell = row.createCell(10);
				cell.setCellValue(vo.getBoxOperation().getBox().getSimMobile());
				cell = row.createCell(11);
				cell.setCellValue(DateTime.toNormalDate(vo.getBoxOperation().getExitTime()));
				cell = row.createCell(12);
				String configureStatus_Nick = "";
				Integer proposalStatusUnpassed = GlobalConstant.PROPOSAL_STATUS_UNPASSED;
				if (vo.getProposal().getStatus() == proposalStatusUnpassed) {
					configureStatus_Nick = "拒绝";
				} else {
					configureStatus_Nick = vo.getVehicle().getConfigureStatus_Nick();
				}
				cell.setCellValue(configureStatus_Nick);
				cell = row.createCell(13);
				
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	/**
	 * 显示换装列表
	 * 
	 * @param transMsg
	 * @param proposal
	 * @return
	 */
	@RequestMapping(value="/listReplace.vti", method=RequestMethod.GET)
	public String listReplace(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = proposalService.listReplace(transMsg, vo, companyId, currentUserId, false);
		
		initSelectList(companyId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", vo);
		getRequest().setAttribute("proposalCompanyVehcileInfoList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/proposal/listReplaceProposal";
		
	}
	
	/**
	 * 显示添加换装页面
	 * @param vo
	 * @param proposalType
	 * @return
	 */
	@RequestMapping(value="/displayAddReplace.vti", method=RequestMethod.GET)
	public String displayAddReplace(@ModelAttribute("proposalAdd") ProposalCompanyVehcileInfo vo,
			@RequestParam("companyId") Integer companyId,
			@RequestParam("replaceVehicleId") Integer replaceVehicleId,
			@RequestParam("dessCarStyleId") Integer dessCarStyleId) {
		ResponseData rd = proposalService.displayAddReplace(vo, companyId, replaceVehicleId, dessCarStyleId);
		getRequest().setAttribute("proposalCompanyVehcileInfo", rd.get("proposalCompanyVehcileInfo"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		initSelectListForReplace(companyId, replaceVehicleId);
		
		return "/dealer/proposal/addReplace";
	}

	/**
	 * 添加换装
	 * @param vo
	 * @param proposalType
	 * @return
	 */
	@RequestMapping(value="/addReplace.vti", method=RequestMethod.POST)
	public String addReplace(HttpServletRequest request, @ModelAttribute("proposalAdd") ProposalCompanyVehcileInfo vo) {
		Integer companyId = vo.getCompany().getId();
		Integer dessCarStyleId = vo.getVehicleInstallationPlan().getDessCarStyleId();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		if (!check4AddReplace(multipartRequest)) {
			return displayAddReplace(vo, companyId, vo.getProposal().getReplaceVehicleId(), dessCarStyleId);
		}
		Integer currentUserId = SessionManager.getCurrentUserId(request);
		vo.getVehicle().setCarStyleId(dessCarStyleId);
		
		List<MultipartFile> files = new LinkedList<MultipartFile>();
		files = multipartRequest.getFiles("attach");
		
		vo.getProposal().setType(GlobalConstant.PROPOSAL_TYPE_REPLACE);
		ResponseData rd = poposalFacade.addReplace(vo, files, currentUserId, companyId);
		if (rd.getCode() == 1) {
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		return displayAddReplace(vo, companyId, vo.getProposal().getReplaceVehicleId(), dessCarStyleId);
	}
	
	/**
	 * 换装导出EXCEL
	 * @param transMsg
	 * @param proposalCompanyVehcileInfo
	 * @param proposalType
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/outputReplaceExcel.vti", method=RequestMethod.GET)
	public void outputReplaceExcel(TransMsg transMsg, @ModelAttribute("proposalCompanyVehcileInfo") ProposalCompanyVehcileInfo vo,
			@RequestParam("proposalType") Integer proposalType) {
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = proposalService.listReplace(transMsg, vo, companyId, currentUserId, true);
		List<ReplaceFindVO> replaceFindVOList = (List<ReplaceFindVO>)rd.getFirstItem();
		writeExcelReplaceProposal(replaceFindVOList);
		
	}
	
	/**
	 * 导出数据到excel 换装
	 * @param violations
	 */
	public void writeExcelReplaceProposal(List<ReplaceFindVO> replaceFindVOList) {
		
		String[] titles = {"分销中心","省份","城市","网络代码","经销商名称",
				"经销商状态","网络形态","车型","待换装车型","VIN","车牌","退出时间","状态","一级网点代码","一级网点名称"};
		String fileName ="";
		
		fileName="换装信息"+DateTime.toShortDateTime(new Date());
		
		
		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("换装信息");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
		
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		if (replaceFindVOList != null && replaceFindVOList.size() > 0) {
			for (int i = 0; i < replaceFindVOList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ReplaceFindVO vo = replaceFindVOList.get(i);
				Integer y = 0;
				HSSFCell cell = row.createCell(0);
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getIsKeyCityNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDessCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDate(vo.getExitTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getConfigureStatusNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentCompanyName());
				cell = row.createCell(++ y);
				
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
		
	}
	
	public void initSelectListBase (Integer companyId) {
		List<Org> companyList = CacheOrgManager.getChildrenOrgTree4Select(null, companyId);
		getRequest().setAttribute("companyList", companyList);
		 
		List installationList = SelectOptionResource.installationStatusOptions();
		getRequest().setAttribute("installationList", installationList);
		
		List demolitionList = SelectOptionResource.demolitionStatusOptions();
		getRequest().setAttribute("demolitionList", demolitionList);
		
		List configureStatusList = SelectOptionResource.configureStatusOptions();
		getRequest().setAttribute("configureStatusList", configureStatusList);
		
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		getRequest().setAttribute("orgList", orgList);
			
	}
	 
	public void initSelectList(Integer companyId) {
		initSelectListBase(companyId);
		List<CarStyle> carStyleList = companyCarStyleRelationService.findByCompanyId(companyId, null);
		getRequest().setAttribute("carStyleList", carStyleList);
	}
	
	/**
	 * 加载换装下拉列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param seriesId TODO
	 * @param vehicleId
	 */
	public void initSelectListForNewConfig(Integer companyId, Integer seriesId) {
		List<CarStyle> carStyleList = companyCarStyleRelationService.findByCompanyId(companyId, seriesId);
		getRequest().setAttribute("carStyleList", carStyleList);
	}
	 
	/**
	 * 加载换装下拉列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @param vehicleId
	 */
	public void initSelectListForReplace(Integer companyId, Integer vehicleId) {
		 initSelectListBase(companyId);
		 List<CarStyle> carStyleList = companyCarStyleRelationService.findReplaceCarStyle(companyId, vehicleId);
		 getRequest().setAttribute("carStyleList", carStyleList);
	}

	/**
	 * 显示车上box是否安装成功页面(供安装人员在手机网页上查看)
	 * 
	 * @param proposal
	 * @return
	 */
	@RequestMapping(value="/displayConfirmInstallationResult.vti", method=RequestMethod.GET)
	public String displayConfirmInstallationResult() {
		getRequest().setAttribute("vin", getStringParameter("vin"));
		getRequest().setAttribute("plateNumber", getStringParameter("plateNumber"));
		getRequest().setAttribute("uniqueId", getStringParameter("uniqueId"));
		return "/dealer/proposal/confirmInstallationResult";
	}
	
	/**
	 * 查询车上box是否安装成功页面(供安装人员在手机网页上查看)
	 * @param vin
	 * @param plateNumber
	 * @param uniqueId
	 */
	@RequestMapping(value="/confirmInstallationResult.vti", method=RequestMethod.GET)
	public void confirmInstallationResult(@RequestParam("vin") String vin, @RequestParam("plateNumber") String plateNumber, @RequestParam("uniqueId") String uniqueId) {
		ResponseData rd = new ResponseData();
		rd.setCode(-1);
		if (MyStringUtils.isBlank(uniqueId) && MyStringUtils.isBlank(vin) && MyStringUtils.isBlank(plateNumber)) {
			rd.setMessage("请至少输入vin码、车牌、设备号其中一项");
			writeRd(rd);
			
		} else if (!MyStringUtils.isBlank(vin) && !ValidatorUtil.isVin(vin)) {
			rd.setMessage("vin码格式无效");
			writeRd(rd);
		} else if (!MyStringUtils.isBlank(plateNumber) && !ValidatorUtil.isCivilPlateNum(plateNumber)) {
			rd.setMessage("车牌格式无效");
			writeRd(rd);
		} else {
			ProposalCompanyVehcileInfo proposalCompanyVehcileInfo = new ProposalCompanyVehcileInfo();
			proposalCompanyVehcileInfo.getVehicle().setVin(vin);
			proposalCompanyVehcileInfo.getVehicle().setPlateNumber(plateNumber);
			proposalCompanyVehcileInfo.getTbox().setUniqueId(uniqueId);
			
			rd = poposalFacade.confirmInstallationResult(proposalCompanyVehcileInfo);
			writeRd(rd);
		}
		
	}
	
	/**
	 * 查看拆除申请单详细信息
	 * 
	 * @param proposalId
	 * @return 
	 */
	@RequestMapping(value="/viewDemolition.vti", method=RequestMethod.GET)
	public String viewDemolitionProposal(@RequestParam("proposalId") Integer proposalId, @RequestParam("vehicleId") Integer vehicleId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = proposalService.viewDemolitionProposal(proposalId, companyId, vehicleId);
		getRequest().setAttribute("vo", rd.get("proposalCompanyVehcileInfo"));
		
		return "/dealer/proposal/viewDemolition";
	}
	
	/**
	 * 查看换装信息
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param vehicleId
	 * @return
	 */
	@RequestMapping(value="/viewReplace.vti", method=RequestMethod.GET)
	public String viewReplace(@RequestParam("proposalId") Integer proposalId, @RequestParam("vehicleId") Integer vehicleId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		
		ResponseData rd = proposalService.viewDemolitionProposal(proposalId, companyId, vehicleId);
		getRequest().setAttribute("proposalAttachmentList", rd.get("proposalAttachmentList"));
		getRequest().setAttribute("vo", rd.get("proposalCompanyVehcileInfo"));
		
		return "/dealer/proposal/viewReplace";
	}
	
	/**
	 * 查看附件
	 * @author liuq 
	 * @version 0.1 
	 * @param proposalId
	 * @param fileId
	 */
	@RequestMapping(value = "/getAttachFileDown.vti", method = RequestMethod.GET)
	public void proposalAttachFileDown(@RequestParam("proposalId") Integer proposalId, @RequestParam("fileId") Integer fileId) {
		try {
			ProposalAttachment proposalAttachment = proposalService.getProposalAttachment(proposalId, fileId);
			UploadHelper.downloadFile(proposalAttachment.getFullPath(),getResponse());
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
	}
	
	/**
	 * 管理员编辑申请单表单验证
	 * 
	 * @param vo
	 * @return
	 */
	protected boolean checkInstallationComplete(ProposalCompanyVehcileInfo vo) {
		BoxVO  box = vo.getTbox();
		BoxOperationVO  boxOperationVO = vo.getBoxOperation();
		String uniqueId = box.getUniqueId();
		if (MyStringUtils.isBlank(uniqueId)) {
			message("设备号不能为空");
			return false;
		} 

		String simMobile = box.getSimMobile();
		if (MyStringUtils.isBlank(simMobile)) {
			message("通讯号不能为空");
			return false;
		} else if (!ValidatorUtil.isMobile(simMobile)) {
			message("通讯号格式不正确");
			return false;
		}
		
		Date operationTime = boxOperationVO.getOperationTime();
		if (operationTime == null) {
			message("安装时间不能不能为空");
			return false;
		} 
		
		String operationName = boxOperationVO.getOperationName(); 
		if (MyStringUtils.isBlank(operationName)) {
			message("安装人不能为空");
			return false;
		} 
		return true;
	}
	
	/**
	 * 添加申请单表单验证
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param multipartRequest
	 * @return
	 */
	protected boolean check4addProposal(ProposalCompanyVehcileInfo vo, MultipartHttpServletRequest multipartRequest) {
		
		Integer carStyleId = vo.getVehicle().getCarStyleId();
		if (!TypeConverter.sizeLagerZero(carStyleId)) {
			message("车型不能为空");
			return false;
		}
		
		String vin = vo.getVehicle().getVin();
		if (MyStringUtils.isBlank(vin)) {
			message("VIN码不能为空");
			return false;
		} else if (!ValidatorUtil.isVin(vin)) {
			message("VIN码格式无效");
			return false;
		}
		
		String plateNumber = vo.getVehicle().getPlateNumber();
		Integer isAddPlatenumberCity = vo.getProposal().getIsAddPlatenumberCity();
		if (TypeConverter.sizeLagerZero(isAddPlatenumberCity) && isAddPlatenumberCity == GlobalConstant.FALSE) {
			if (MyStringUtils.isBlank(plateNumber)) {
				message("车牌不能为空");
				return false;
			} else if (!ValidatorUtil.isCivilPlateNum(plateNumber)) {
				message("车牌格式无效");
				return false;
			}
		} else {
			if (!MyStringUtils.isBlank(plateNumber)) {
				if (!ValidatorUtil.isCivilPlateNum(plateNumber)) {
					message("车牌格式无效");
					return false;
				}
			}
		}
		
		String contactName = vo.getProposal().getContactName();
		if (MyStringUtils.isBlank(contactName)) {
			message("联系人名称不能为空");
			return false;
		}
		
		String contactPhone = vo.getProposal().getContactPhone();
		if (MyStringUtils.isBlank(contactPhone)) {
			message("联系电话不能为空");
			return false;
		}
		
		if(null == multipartRequest 
				|| null == multipartRequest.getFiles("attach") 
				|| multipartRequest.getFiles("attach").isEmpty()
				|| null == multipartRequest.getFiles("attach").get(0)
				|| multipartRequest.getFiles("attach").get(0).isEmpty()){
			message("上传文件为空");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 添加换装申请附件验证
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @param multipartRequest
	 * @return
	 */
	protected boolean check4AddReplace(MultipartHttpServletRequest multipartRequest) {
		if(null == multipartRequest 
				|| null == multipartRequest.getFiles("attach") 
				|| multipartRequest.getFiles("attach").isEmpty()
				|| null == multipartRequest.getFiles("attach").get(0)
				|| multipartRequest.getFiles("attach").get(0).isEmpty()){
			message("上传文件为空");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 编辑申请单表单验证
	 * @author liuq 
	 * @version 0.1 
	 * @param vo
	 * @return
	 */
	protected boolean check4editProposal(ProposalCompanyVehcileInfo vo) {

		String plateNumber = vo.getVehicle().getPlateNumber();
		if (MyStringUtils.isBlank(plateNumber)) {
			message("车牌不能为空");
			return false;
		} else if (!ValidatorUtil.isCivilPlateNum(plateNumber)) {
			message("车牌格式无效");
			return false;
		}

		String contactName = vo.getProposal().getContactName();
		if (MyStringUtils.isBlank(contactName)) {
			message("联系人名称不能为空");
			return false;
		}
		
		String contactPhone = vo.getProposal().getContactPhone();
		if (MyStringUtils.isBlank(contactPhone)) {
			message("联系电话不能为空");
			return false;
		}
		
		return true;
	}
		
}
