package com.ava.dealer.controller;

import java.util.Date;
import java.util.List;

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

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dealer.service.IBoxService;
import com.ava.domain.entity.Box;
import com.ava.domain.vo.BoxFindVO;
import com.ava.domain.vo.BoxInfoVO;
import com.ava.domain.vo.BoxOperationFindVO;
import com.ava.domain.vo.BoxOperationVO;
import com.ava.domain.vo.BoxVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.MyStringUtils;
import com.ava.util.ValidatorUtil;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping("/dealer/box")
public class BoxController extends Base4MvcController {
	
	@Autowired
	private IBoxService boxService;
	
	/**
	 * 通过设备号查询设备信息信息
	 */
	@RequestMapping(value = "/findBoxInfo.vti", method = RequestMethod.GET)
	public void findBoxInfo() {
		ResponseData rd = new ResponseData(0);
		String uniqueId = getStringParameter("uniqueId");
		if (uniqueId != null) {
			Box box = boxService.getBoxByUniqueId(uniqueId);
			if  (box != null) {
			     rd.put("box", box);
			}
			rd.setCode(1);
		} else {
			rd.setCode(-1);
			rd.setMessage("系统错误");
		}
		
		writeRd(rd);
	}
	
	@RequestMapping(value = "/displayAdd.vti", method = RequestMethod.GET)
	public String displayAddBox(
			@ModelAttribute("boxAdd") Box frmBox) {
		ResponseData rd = boxService.displayAddBox(frmBox);
		getRequest().setAttribute("boxAdd", rd.get("boxAdd"));
		
		init4AddOrEdit();//初始化表单需要的一些数据

		return "/dealer/box/addBox";
	}

	@RequestMapping(value = "/add.vti", method = RequestMethod.POST)
	public String addBox(@ModelAttribute("boxAdd") Box frmBox) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if (!checkAddBox(frmBox)) {
			return displayAddBox(frmBox);
		}
		
		ResponseData rd = boxService.addBox(frmBox, companyId);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		return displayAddBox(frmBox);
	}
	
	@RequestMapping(value="/displayEdit.vti", method=RequestMethod.GET)
	public String displayEditBox(@ModelAttribute("boxEdit") Box frmBox, @RequestParam("startIndex") Integer startIndex){
		getRequest().setAttribute("startIndex", startIndex);

		Box dbBox = boxService.getBox(frmBox.getId());
		// 为了保持当前修改的状态
		MyBeanUtils.copyPropertiesContainsDate(dbBox, frmBox);
		getRequest().setAttribute("box", dbBox);
		
		init4AddOrEdit();//初始化表单需要的一些数据
		
		return "/dealer/box/editBox";
	}
	
	/**
	 * 显示解绑页面
	 * @param frmBox
	 * @param startIndex
	 * @return
	 */
	@RequestMapping(value="/displayUnbind.vti", method=RequestMethod.GET)
	public String displayUnbind(@ModelAttribute("boxEdit") BoxInfoVO frmBox, @RequestParam("startIndex") Integer startIndex){
		getRequest().setAttribute("startIndex", startIndex);
		
		if (frmBox != null) {
			ResponseData rd = boxService.displayUnbind(frmBox.getId());
			getRequest().setAttribute("box", rd.getFirstItem());
			
			init4AddOrEdit();//初始化表单需要的一些数据
		}
		
		return "/dealer/box/unbind";
	}
	
	/**
	 * 把设备从车上解除绑定
	 * @param frmBox
	 * @return
	 */
	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	public String unbind(@ModelAttribute("boxEdit") BoxInfoVO frmBox, @RequestParam("startIndex") Integer startIndex) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = boxService.unbind(frmBox, companyId, currentUserId);
	
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayUnbind(frmBox, startIndex);
		}
	}

	@RequestMapping(value="/delete.vti", method=RequestMethod.GET)
	public void deleteBox(@RequestParam("boxId") Integer boxId) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = boxService.deleteBox(boxId, companyId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		//return "redirect:/dealer/box/list.vti?startIndex=" + getIntegerParameter("startIndex");
		writeRd(rd);
	}


	/**编辑设备 */
	@RequestMapping(value="/edit.vti", method=RequestMethod.POST)
	public String editBox(@ModelAttribute("boxEdit") Box frmBox, @RequestParam("startIndex") Integer startIndex) {
		if (!checkEditBox(frmBox)) {
			return displayEditBox(frmBox, startIndex);
		}

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd  = boxService.editBox(frmBox, companyId, GlobalConstant.TRUE);
	
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayEditBox(frmBox, startIndex);
		}
		
	}

	@RequestMapping(value = "/list.vti", method = RequestMethod.GET)
	public String listBox(TransMsg transMsg, @ModelAttribute("box") BoxVO box) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = boxService.listBox(transMsg, box, companyId, currentUserId, false);

		getRequest().setAttribute("box", box);
		getRequest().setAttribute("boxList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);

		return "/dealer/box/listBox";
	}
	
	@RequestMapping(value = "/listBoxOperation.vti", method = RequestMethod.GET)
	public String listBoxOperation(TransMsg transMsg, @ModelAttribute("boxOperationVO") BoxOperationVO boxOperationVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = boxService.listBoxOperation(transMsg, boxOperationVO, companyId, currentUserId, false);
		
		getRequest().setAttribute("boxOperationVO", boxOperationVO);
		getRequest().setAttribute("boxOperationList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		siteMenuType(rd);
		
		return "/dealer/box/listBoxOperation";
	}
	
	/**
	 * 设备列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param box
	 */
	@RequestMapping(value = "/exportBox.vti", method = RequestMethod.GET)
	public void exportBox(TransMsg transMsg, @ModelAttribute("box") BoxVO box) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = boxService.listBox(transMsg, box, companyId, currentUserId, true);

		boxExcel((List<BoxFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出设备列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param boxs
	 */
	public void boxExcel(List<BoxFindVO> boxs) {
		String[] titles = {"分销中心","网络代码","经销商名称","设备号","SIM卡号","车型","VIN","车牌","状态"};
		
		String fileName ="";
	
		fileName="设备列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("设备明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (boxs != null && boxs.size() > 0) {
			for (int i = 0; i < boxs.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				BoxFindVO vo = boxs.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getUniqueId());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getSimId());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getStatusNick());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	/**
	 * 设备操作日志列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param boxOperationVO
	 */
	@RequestMapping(value = "/exportBoxOperation.vti", method = RequestMethod.GET)
	public void exportBoxOperation(TransMsg transMsg, @ModelAttribute("boxOperationVO") BoxOperationVO boxOperationVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = boxService.listBoxOperation(transMsg, boxOperationVO, companyId, currentUserId, true);

		boxOperationExcel((List<BoxOperationFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 导出设备操作日志列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param boxOperations
	 */
	public void boxOperationExcel(List<BoxOperationFindVO> boxOperations) {
		String[] titles = {"分销中心","网络代码","经销商名称","设备号","车型","VIN","车牌","类型","操作时间"};
		
		String fileName ="";
	
		fileName="设备操作日志列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("操作日志明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (boxOperations != null && boxOperations.size() > 0) {
			for (int i = 0; i < boxOperations.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				BoxOperationFindVO vo = boxOperations.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(vo.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getUniqueId());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getPlateNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getOperationTime()));
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	@RequestMapping(value="/view.vti", method=RequestMethod.GET)
	public String viewBox(@RequestParam("boxId") Integer boxId) {
		Box box = boxService.getBox(boxId);
		getRequest().setAttribute("box", box);

		return "/dealer/box/viewBox";
	}

	private void init4AddOrEdit() {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		getRequest().setAttribute("orgList", CacheOrgManager.getChildrenOrgTree4Select(null, companyId));
	}

	private boolean checkAddBox(Box box) {
		String uniqueId = box.getUniqueId();
		if (MyStringUtils.isBlank(uniqueId)) {
			message("设备号不能为空");
			return false;
		} 
		
		String simId = box.getSimId();
		if (MyStringUtils.isBlank(simId)) {
			message("sim卡号不能为空");
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
		
		String currentVersion = box.getCurrentVersion();
		if (MyStringUtils.isBlank(currentVersion)) {
			message("当前版本不能为空");
			return false;
		} 
		
		return true;
	}

	private boolean checkEditBox(Box box) {
		String uniqueId = box.getUniqueId();
		if (MyStringUtils.isBlank(uniqueId)) {
			message("设备号不能为空");
			return false;
		} 
		
		String simId = box.getSimId();
		if (MyStringUtils.isBlank(simId)) {
			message("sim卡号不能为空");
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
		
		String currentVersion = box.getCurrentVersion();
		if (MyStringUtils.isBlank(currentVersion)) {
			message("当前版本不能为空");
			return false;
		} 
		
		return true;
	}
}
