package com.ava.dealer.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.ISurveyResultService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.Org;
import com.ava.domain.vo.SurveyResultFindVO;
import com.ava.domain.vo.SurveyResultStaticVO;
import com.ava.domain.vo.SurveyResultVO;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgWithFilterManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExportExcelUtil;
@Controller
@RequestMapping("/dealer/survey")
public class SurveyController extends Base4MvcController{
	
	@Autowired
	IUserMenuService userMenuService;
	
	@Resource(name="dealer.surveyResultService")
	ISurveyResultService surveyResultService;
	
	/**
	 * 查询问卷调查列表
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param surveyResultVO
	 * @return
	 */
	@RequestMapping(value = "/listSurvey.vti", method = RequestMethod.GET)
	public String listSurvey(TransMsg transMsg, @ModelAttribute("survey") SurveyResultVO surveyResultVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = surveyResultService.listSurveyResult(transMsg, surveyResultVO, companyId, userId, false);

		getRequest().setAttribute("surveyList", rd.getFirstItem());
		getRequest().setAttribute("surveyResult", surveyResultVO);
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);

		List<Org> orgList = CacheOrgWithFilterManager.getOrgTree4Select(userId, companyId);
		getRequest().setAttribute("orgList", orgList);
		
		return "/dealer/survey/listSurvey";
	}
	
	/**
	 * 显示车型配置问卷调查页面
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @return
	 */
	@RequestMapping(value="/displayAdd.vti", method=RequestMethod.GET)
	public String displayAddSurvey(@ModelAttribute("surveyAdd") SurveyResultVO surveyResultAdd) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Company company = CacheCompanyManager.getCompany(companyId);
		
		ResponseData rd = surveyResultService.displayAddSurveyResult(surveyResultAdd, company);
		getRequest().setAttribute("surveyResultAdd", rd.get("surveyResultAdd"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		siteMenuType(rd);
		
		return "/dealer/survey/addSurvey";
	}
	
	/**
	 * 显示车型配置问卷调查页面
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @return
	 */
	@RequestMapping(value="/displayAddSurveyLevel.vti", method=RequestMethod.GET)
	public String displayAddSurveyLevel() {
		ResponseData rd = new ResponseData(1);
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		siteMenuType(rd);
		
		return "/dealer/survey/addSurveyLevel";
	}
	
	/**
	 * 添加问卷调查
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @return
	 */
	@RequestMapping(value="/add.vti", method=RequestMethod.POST)
	public String addSurvey(@ModelAttribute("surveyAdd") SurveyResultVO surveyResultAdd) {
		if (!checkAddSurvey(surveyResultAdd)) {
			return displayAddSurvey(surveyResultAdd);
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = surveyResultService.add(surveyResultAdd, companyId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddSurvey(surveyResultAdd);
	}
	
	/**
	 * 添加问卷调查
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @return
	 */
	@RequestMapping(value="/addSurveyLevel.vti", method=RequestMethod.POST)
	@ResponseBody
	public ResponseData addSurveyLevel() {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = surveyResultService.addSurveyLevel(companyId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return rd;
	}
	
	/**
	 * 查询经销商是否已经参与过调研
	 * @author liuq 
	 * @version 0.1 
	 * @return
	 */
	@RequestMapping(value="/querySurveyLevel.vti", method=RequestMethod.GET)
	@ResponseBody
	public ResponseData querySurveyLevel() {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = surveyResultService.querySurveyLevel(companyId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return rd;
	}
	
	/**
	 * 显示车型配置问卷调查页面(静态车型)
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultStaticVO
	 * @return
	 */
	@RequestMapping(value="/displayAddSurveyStatic.vti", method=RequestMethod.GET)
	public String displayAddSurveyStatic(@ModelAttribute("surveyAdd") SurveyResultStaticVO surveyResultStaticVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Company company = CacheCompanyManager.getCompany(companyId);
		
		ResponseData rd = surveyResultService.displayAddSurveyResultStatic(surveyResultStaticVO, company);
		getRequest().setAttribute("surveyResultAdd", rd.get("surveyResultStaticVO"));
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		siteMenuType(rd);
		
		return "/dealer/survey/addSurveyStatic";
	}
	
	/**
	 * 添加问卷调查(静态车型)
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResultAdd
	 * @return
	 */
	@RequestMapping(value="/addSurveyStatic", method=RequestMethod.POST)
	public String addSurveyStatic(@ModelAttribute("surveyResultAdd") SurveyResultStaticVO surveyResultStaticVO) {
		if (!checkAddSurveyStatic(surveyResultStaticVO)) {
			return displayAddSurveyStatic(surveyResultStaticVO);
		}
		
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		ResponseData rd = surveyResultService.addSurveyResultStatic(surveyResultStaticVO, companyId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayAddSurveyStatic(surveyResultStaticVO);
	}
	
	
	
	protected boolean checkAddSurvey(SurveyResultVO surveyResultAdd) {
		boolean flag = true;
		Integer firstSelectType = surveyResultAdd.getFirstSelectType();
		List<Integer> selectFirstCarStyles = surveyResultAdd.getSelectFirstCarStyles();
		flag = this.checkSelectType(firstSelectType, selectFirstCarStyles, "一");
		if (!flag) {
			return flag;
		}
		
		Integer secondSelectType = surveyResultAdd.getSecondSelectType();
		List<Integer> selectSecondCarStyles = surveyResultAdd.getSelectSecondCarStyles();
		flag = this.checkSelectType(secondSelectType, selectSecondCarStyles, "二");
		if (!flag) {
			return flag;
		}
		
		Integer threeSelectType = surveyResultAdd.getThreeSelectType();
		List<Integer> selectThreeCarStyles = surveyResultAdd.getSelectThreeCarStyles();
		flag = this.checkSelectType(threeSelectType, selectThreeCarStyles, "三");
		if (!flag) {
			return flag;
		}
		
		return true;
	}
	
	protected boolean checkAddSurveyStatic(SurveyResultStaticVO surveyResultStaticVO) {
		boolean flag = false;
		List<Integer> selectFirstCarStyles = surveyResultStaticVO.getSelectFirstCarStyles();
		List<Integer> selectSecondCarStyles = surveyResultStaticVO.getSelectSecondCarStyles();
		List<Integer> selectThreeCarStyles = surveyResultStaticVO.getSelectThreeCarStyles();
		List<Integer> selectFourCarStyles = surveyResultStaticVO.getSelectFourCarStyles();
		List<Integer> selectFiveCarStyles = surveyResultStaticVO.getSelectFiveCarStyles();
		List<Integer> selectSixCarStyles = surveyResultStaticVO.getSelectSixCarStyles();
		List<Integer> selectSevenCarStyles = surveyResultStaticVO.getSelectSevenCarStyles();
		List<Integer> selectEightCarStyles = surveyResultStaticVO.getSelectEightCarStyles();
		List<Integer> selectNineCarStyles = surveyResultStaticVO.getSelectNineCarStyles();
		List<Integer> selectTenCarStyles = surveyResultStaticVO.getSelectTenCarStyles();
		
		int size = 0;
		if (selectFirstCarStyles != null && selectFirstCarStyles.size() > 0) size ++;
		if (selectSecondCarStyles != null && selectSecondCarStyles.size() > 0) size ++;
		if (selectThreeCarStyles != null && selectThreeCarStyles.size() > 0) size ++;
		if (selectFourCarStyles != null && selectFourCarStyles.size() > 0) size ++;
		if (selectFiveCarStyles != null && selectFiveCarStyles.size() > 0) size ++;
		if (selectSixCarStyles != null && selectSixCarStyles.size() > 0) size ++;
		if (selectSevenCarStyles != null && selectSevenCarStyles.size() > 0) size ++;
		if (selectEightCarStyles != null && selectEightCarStyles.size() > 0) size ++;
		if (selectNineCarStyles != null && selectNineCarStyles.size() > 0) size ++;
		if (selectTenCarStyles != null && selectTenCarStyles.size() > 0) size ++;
		surveyResultStaticVO.setSize(size);
		
		Integer type = surveyResultStaticVO.getType();
		if (type == 1) {
			//总共四项，每项必须选择一个车型
			flag = this.checkSelectCarStyles(selectFirstCarStyles, "一");
			if (!flag) {
				return flag;
			}
			
			flag = this.checkSelectCarStyles(selectSecondCarStyles, "二");
			if (!flag) {
				return flag;
			}
			
			flag = this.checkSelectCarStyles(selectThreeCarStyles, "三");
			if (!flag) {
				return flag;
			}
			flag = this.checkSelectCarStyles(selectFourCarStyles, "四");
			if (!flag) {
				return flag;
			}
		} else if (type == 2) {
			//总共六项，必须选择二项
			if (size == 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "请选择两辆试驾车型！");
				return false;
			} else if (size < 2) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "您选择试驾车型少于两辆！");
				return false;
			} else if (size > 2) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "您选择试驾车型已经大于两辆！");
				return false;
			}
		} else if (type == 3) {
			if (selectTenCarStyles != null && selectTenCarStyles.size() > 0) {
				return true;
			} 
			if (size == 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "请选择最多不超过三辆的试驾车型！");
				return false;
			} else if (size > 3) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "您选择试驾车型已经超过三辆！");
				return false;
			}
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
					GlobalConstant.MSG_RESULT_CONTENT, "您不在需要配置车型的名单中，不需要配置！");
			return false;
		}
		
		
		return true;
	}
	
	boolean checkSelectCarStyles(List<Integer> selectCarStyles, String item){
		if (selectCarStyles == null || selectCarStyles.size() == 0) {
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "第" + item + "类请选择一个车型！");
				return false;
		}
		return true;
	}
	
	boolean checkSelectType(Integer selectType, List<Integer> selectCarStyles, String item){
		if (selectType != null) {
			if (selectCarStyles == null || selectCarStyles.size() == 0) {
				/* 留着以后问卷调查多选框的时候使用 if (selectType == GlobalConstant.TRUE) {
					message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
							GlobalConstant.MSG_RESULT_CONTENT, "第" + item + "项请至少选择一个车型！");
					return false;
				}
				if (selectType == GlobalConstant.FALSE) {
					message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
							GlobalConstant.MSG_RESULT_CONTENT, "第"+ item + "项请【至少选择一个车型】或者【只选择不配置Passat车型】！");
					return false;
				}*/
				//单选时候使用
				message(GlobalConstant.MSG_RESULT_LEVEL_WARNING,
						GlobalConstant.MSG_RESULT_CONTENT, "请选择一个试驾车型！");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 车型问卷列表导出EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param transMsg
	 * @param surveyResultVO
	 */
	@RequestMapping(value = "/exportSurvey.vti", method = RequestMethod.GET)
	public void exportSurvey(TransMsg transMsg, @ModelAttribute("survey") SurveyResultVO surveyResultVO) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		
		ResponseData rd = surveyResultService.listSurveyResult(transMsg, surveyResultVO, companyId, userId, true);
		surveyExcel((List<SurveyResultFindVO>)rd.getFirstItem());
	}
	
	/**
	 * 删除调研结果
	 * @author liuq 
	 * @version 0.1 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value="/deleteSurveyResult.vti", method=RequestMethod.GET)
	@ResponseBody
	public ResponseData deleteSurveyResult(@RequestParam("companyId") Integer companyId) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		return surveyResultService.deleteSurveyResult(companyId, userId);
	}
	
	/**
	 * 导出问卷列表数据到EXCEL
	 * @author liuq 
	 * @version 0.1 
	 * @param surveyResults
	 */
	public void surveyExcel(List<SurveyResultFindVO> surveyResults) {
		
		String[] titles = {"分销中心","省份",
				"城市","网络代码","经销商名称","网络形态","星级","车型","数量","提交时间"};
		
		String fileName ="";
	
		fileName="问卷结果列表信息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("问卷明细");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (surveyResults != null && surveyResults.size() > 0) {
			for (int i = 0; i < surveyResults.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				SurveyResultFindVO surveyResultFindVO = surveyResults.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(surveyResultFindVO.getSaleCenterName());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getProvinceName());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getCityName());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getDealerCode());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getCompanyName());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getSurveyDealerTypeNick());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getLevel());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getCarStyleName());
				cell = row.createCell(++ y);
				cell.setCellValue(surveyResultFindVO.getAmount());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(surveyResultFindVO.getCreationTime()));
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
}
