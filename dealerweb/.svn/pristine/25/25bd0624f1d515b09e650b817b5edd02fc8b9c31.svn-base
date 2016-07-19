package com.ava.dealer.controller;

import java.text.DecimalFormat;
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
import com.ava.baseSystem.service.ICompanyService;
import com.ava.dealer.service.IDriveLineService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.DriveLine;
import com.ava.domain.entity.Org;
import com.ava.domain.vo.DriveLineFindVO;
import com.ava.domain.vo.DriveLineVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SelectOptionResource;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.MyBeanUtils;
import com.ava.util.MyStringUtils;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping("/dealer/driveLine")
public class DriveLineController extends Base4MvcController {
	@Autowired
	private IDriveLineService driveLineService;
	
	@Autowired
	private ICompanyService companyService;

	@RequestMapping(value = "/set.vti", method = RequestMethod.GET)
	public String setDriveLine() {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());

		ResponseData rd = driveLineService.displayHome(userId, companyId);
		getRequest().setAttribute("startIndex",
				getIntegerParameter("startIndex"));

		getRequest().setAttribute("dhtmlXtreeXML", rd.get("dhtmlXtreeXML"));

		return "/dealer/driveLine/setDriveLine";
	}

	@RequestMapping(value = "/list.vti", method = RequestMethod.GET)
	public String listDriveLine(TransMsg transMsg, @ModelAttribute("driveLine") DriveLineVO driveLine) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = driveLineService.listDriveLine(transMsg, driveLine, companyId, userId, false);

		getRequest().setAttribute("driveLineList", rd.get("driveLineList"));
		getRequest().setAttribute("driveLine", driveLine);
		getRequest().setAttribute("transMsg", transMsg);

		initSelectList(companyId);
		siteMenuType(rd);

		return "/dealer/driveLine/listDriveLine";
	}
	
	/**
	 * 线路列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param driveLine
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/exportDriveLine.vti", method = RequestMethod.GET)
	public void exportDriveLine(TransMsg transMsg, @ModelAttribute("driveLine") DriveLineVO driveLine) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = driveLineService.listDriveLine(transMsg, driveLine, companyId, userId, true);
		driveLineExcel((List<DriveLineFindVO>)rd.get("driveLineList"));
	}
	
	/**
	 * 导出线路列表数据到excel
	 * @author liuq 
	 * @version 0.1 
	 * @param driveLines
	 */
	public void driveLineExcel(List<DriveLineFindVO> driveLines) {
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","经销商状态","网络形态","名称","类型","围栏类型","里程[km]","创建时间","更新时间","一级网点代码","一级网点名称"};
		
		String fileName ="";
	
		fileName="线路列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("线路明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (driveLines != null && driveLines.size() > 0) {
			for (int i = 0; i < driveLines.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				DriveLineFindVO vo = driveLines.get(i);
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
				cell.setCellValue(vo.getName());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getFenceTypeNick());
				cell = row.createCell(++ y);
				String mileage = "0";
				if (vo.getMileage() != null && vo.getMileage() > 0) {
					mileage = new DecimalFormat("#.#").format((float)vo.getMileage() / (float)1000);
				}
				cell.setCellValue(mileage);
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getCreationTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(vo.getModifyTime()));
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(vo.getParentCompanyName());
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}

	@RequestMapping(value = "/displayAdd.vti", method = RequestMethod.GET)
	public String displayAddDriveLine(@ModelAttribute("driveLineAdd") DriveLine frmDriveLine) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		if (frmDriveLine.getCompanyId() == null) {
			frmDriveLine.setCompanyId(companyId);
		}
		frmDriveLine.nick();

		getRequest().setAttribute("driveLineAdd", frmDriveLine);
		initSelectList(companyId);
		return "/dealer/driveLine/addDriveLine";
	}

	@RequestMapping(value = "/add.vti", method = RequestMethod.POST)
	public String addDriveLine(@ModelAttribute("driveLineAdd") DriveLine frmDriveLine) {
		if (!checkAddOrEditDriveLine(frmDriveLine)) {
			return displayAddDriveLine(frmDriveLine);
		}

		ResponseData rd = driveLineService.addDriveLine(frmDriveLine);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		return displayAddDriveLine(frmDriveLine);
	}

	@RequestMapping(value = "/delete.vti", method = RequestMethod.GET)
	public void deleteDriveLine(@RequestParam("driveLineId") Integer driveLineId) {
		ResponseData rd = driveLineService.deleteDriveLine(driveLineId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		writeRd(rd);
	}

	@RequestMapping(value = "/displayEdit.vti", method = RequestMethod.GET)
	public String displayEditDriveLine(@ModelAttribute("driveLineEdit") DriveLine frmDriveLine,@RequestParam("startIndex") Integer startIndex) {
		getRequest().setAttribute("startIndex", startIndex);

		DriveLineVO dbDriveLine = driveLineService.getDriveLine(frmDriveLine.getId());
		// 为了保持当前修改的状态
		MyBeanUtils.copyPropertiesContainsDate(dbDriveLine, frmDriveLine);

		getRequest().setAttribute("driveLine", dbDriveLine);

		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		initSelectList(companyId);

		return "/dealer/driveLine/editDriveLine";
	}

	/** 编辑线路 */
	@RequestMapping(value = "/edit.vti", method = RequestMethod.POST)
	public String editDriveLine(
			@ModelAttribute("driveLineEdit") DriveLine frmDriveLine,
			@RequestParam("startIndex") Integer startIndex) {
		if (!checkAddOrEditDriveLine(frmDriveLine)) {
			return displayEditDriveLine(frmDriveLine, startIndex);
		}

		ResponseData rd = driveLineService.editDriveLine(frmDriveLine);
		if (rd.getCode() == 1) {
			return "/pub/jsPages/closeDialogAndReloadParent";
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		return displayEditDriveLine(frmDriveLine, startIndex);
	}

	/** 为配置围栏显示线路 */
	/**
	 * 为配置围栏显示线路
	 * @param frmDriveLine
	 * @param startIndex
	 * @return
	 */
	@RequestMapping(value = "/displaySetFence.vti", method = RequestMethod.GET)
	public String displaySetFence(@ModelAttribute("driveLineEdit") DriveLineVO frmDriveLine, @RequestParam("startIndex") Integer startIndex) {
		DriveLineVO dbDriveLine = driveLineService.getDriveLine(frmDriveLine.getId());
		getRequest().setAttribute("driveLine", dbDriveLine);
		getRequest().setAttribute("startIndex", startIndex);

		if (dbDriveLine.getFenceType() != null
				&& dbDriveLine.getFenceType().intValue() == GlobalConstant.DRIVE_LINE_FENCE_TYPE_DOUBLE) {
			return "/dealer/driveLine/setDoubleFence";
		} else {
			return "/dealer/driveLine/setSingleFence";
		}
	}
	
	/**
	 * 配置线路参考范围
	 * @param frmDriveLine
	 * @param startIndex
	 * @return
	 */
	@RequestMapping(value = "/displaySetReferenceLine.vti", method = RequestMethod.GET)
	public String displaySetReferenceLine(@ModelAttribute("driveLineEdit") DriveLineVO frmDriveLine, @RequestParam("startIndex") Integer startIndex) {
		DriveLineVO driveLineVO = driveLineService.getReferenceLine(frmDriveLine.getId());
		getRequest().setAttribute("driveLine", driveLineVO);
		getRequest().setAttribute("startIndex", startIndex);
		
		return "/dealer/driveLine/setReferenceLine";
	}

	
	/**
	 * 保存参考线路
	 * @param frmDriveLine
	 * @param startIndex
	 * @return
	 */
	@RequestMapping(value = "/setReferenceLine.vti", method = RequestMethod.POST)
	public String setReferenceLine(@ModelAttribute("driveLineEdit") DriveLineVO frmDriveLine, @RequestParam("startIndex") Integer startIndex, String lng,String lat) {
		String jsonPolygon1 = frmDriveLine.getJsonPolygon1();
		if (MyStringUtils.isBlank(jsonPolygon1)) {
			message("参考线路没有设置");
			return displaySetReferenceLine(frmDriveLine, startIndex);
		}
		
		Company company=companyService.getOrgById(frmDriveLine.getCompanyId());
		if(null!=company){
			if(null!=lng && null!=lat && !"".equals(lng.trim()) && !"".equals(lat)){
				company.setBaiduLng(Double.parseDouble(lng));
				company.setBaiduLat(Double.parseDouble(lat));
				companyService.editCompany(company);
			}
		}
		
		ResponseData rd = driveLineService.setReferenceLine(frmDriveLine);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displaySetReferenceLine(frmDriveLine, startIndex);
		}
		
		return "redirect:/dealer/driveLine/list.vti?startIndex=0";
	}
	
	/**
	 * 保存和线路关联的围栏
	 * @param frmDriveLine
	 * @param startIndex
	 * @return
	 */
	@RequestMapping(value = "/setFence.vti", method = RequestMethod.POST)
	public String setFence(@ModelAttribute("driveLineEdit") DriveLineVO frmDriveLine, @RequestParam("startIndex") Integer startIndex) {
		if (frmDriveLine.getFenceType().intValue() == GlobalConstant.DRIVE_LINE_FENCE_TYPE_SINGLE) {
			String jsonPolygon1 = frmDriveLine.getJsonPolygon1();
			if (MyStringUtils.isBlank(jsonPolygon1)) {
				message("围栏没有设置");
				return displaySetFence(frmDriveLine, startIndex);
			}
		} else {
			String jsonPolygon1 = frmDriveLine.getJsonPolygon1();
			if (MyStringUtils.isBlank(jsonPolygon1)) {
				message("请绘制外圈围栏");
				return displaySetFence(frmDriveLine, startIndex);
			}

			String jsonPolygon2 = frmDriveLine.getJsonPolygon2();
			if (MyStringUtils.isBlank(jsonPolygon2)) {
				message("请绘制内圈围栏");
				return displaySetFence(frmDriveLine, startIndex);
			}
		}

		ResponseData rd = driveLineService.setFence(frmDriveLine);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displaySetFence(frmDriveLine, startIndex);
		}

		return "redirect:/dealer/driveLine/list.vti?startIndex=0";
	}

	@RequestMapping(value = "/view.vti", method = RequestMethod.GET)
	public String viewDriveLine(@RequestParam("driveLineId") Integer driveLineId) {
		DriveLineVO driveLine = driveLineService.getDriveLine(driveLineId);
		if(null==driveLine){
			driveLine=new DriveLineVO();
			driveLine.setSemicyclePoint("");
		}
		getRequest().setAttribute("driveLine", driveLine);

		return "/dealer/driveLine/viewDriveLine";
	}
	
	/**
	 * 显示设置半圈点界面
	 * @param driveLineId
	 * @return
	 */
	@RequestMapping(value = "/setSemcyclePointView.vti", method = RequestMethod.GET)
	public String setSemcyclePoint(@RequestParam("driveLineId") Integer driveLineId) {
		DriveLineVO driveLine = driveLineService.getDriveLine(driveLineId);
		if(null==driveLine){
			driveLine=new DriveLineVO();
			driveLine.setSemicyclePoint("");
		}
		getRequest().setAttribute("driveLine", driveLine);
		return "/dealer/driveLine/setDriveLineSemcyclePoint";
	}
	
	/**
	 * 设置半圈点
	 * @param driveLineId
	 * @return
	 */
	@RequestMapping(value = "/saveSemcycle.vti", method = RequestMethod.POST)
	public String saveSemcycle(@RequestParam("driveLineId") Integer driveLineId,@RequestParam("lngLat") String lngLat) {
		DriveLineVO driveLine = driveLineService.getDriveLine(driveLineId);
		if(null!=driveLine && null!=lngLat && !"".equals(lngLat.trim())){
			driveLine.setSemicyclePoint(lngLat);
			driveLineService.updateSemcycle(driveLine);
		}
		return "/pub/jsPages/closeDialogAndReloadParent";
	}

	private boolean checkAddOrEditDriveLine(DriveLine driveLine) {
		String name = driveLine.getName();
		if (MyStringUtils.isBlank(name)) {
			message("名称不能为空");
			return false;
		}

		Integer fencType = driveLine.getFenceType();
		if (fencType == null || fencType.intValue() < 1) {
			message("围栏类型必须选择");
			return false;
		}
		return true;
	}
	
	/**
	 * 加载下拉列表
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 */
	private void initSelectList(Integer companyId) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		List<Org> selectOrgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		getRequest().setAttribute("selectOrgList", selectOrgList);
		getRequest().setAttribute("orgList", CacheOrgManager.getChildrenOrgTree4Select(null, companyId));
		getRequest().setAttribute("driveLineTypeOptions", SelectOptionResource.driveLineTypeOptions());
		getRequest().setAttribute("driveLineFenceTypeOptions", SelectOptionResource.driveLineFenceTypeOptions());
	}
}