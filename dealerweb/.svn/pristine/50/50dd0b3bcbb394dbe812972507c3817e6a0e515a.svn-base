package com.ava.dealer.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ava.base.controller.Base4MvcController;
import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.baseSystem.service.IUserMenuService;
import com.ava.dealer.service.IDeclareManagerService;
import com.ava.dealer.service.IVehicleService;
import com.ava.domain.entity.Box;
import com.ava.domain.entity.CarStyle;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.User;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.vo.DealerVO;
import com.ava.domain.vo.FilingApprovalVO;
import com.ava.domain.vo.FilingProposalAttachmentVO;
import com.ava.domain.vo.FilingProposalCompanyVehcileInfo;
import com.ava.domain.vo.FilingProposalVO;
import com.ava.domain.vo.VehicleVO;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheOrgManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExportExcelUtil;
import com.ava.util.upload.UploadHelper;

@Controller
@RequestMapping("/dealer/declareManager")
public class DeclareManagerController extends Base4MvcController{
	
	Logger logger=Logger.getLogger(DeclareManagerController.class);

	@Resource(name="dealer.declareManagerService")
	IDeclareManagerService declareManagerService;

	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired
	IUserMenuService userMenuService;
	
	/**
	 * 
	* Description: 外出、维修报备查看
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param vehicle
	* @param declareType
	* @param dealerCode
	* @param startTime
	* @param endTime
	* @param declareStatus
	* @return
	 */
	@RequestMapping(value = "/queryDeclareList.vti", method = RequestMethod.GET)
	public String queryOutDeclareList(TransMsg transMsg,@ModelAttribute("vehicle") VehicleVO vehicle,@RequestParam("declareType")  Integer declareType, @RequestParam("dealerCode") String dealerCode,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,@RequestParam("declareStatus") String declareStatus) {
		
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());

			getRequest().setAttribute("filingProposalCVInfoList", declareManagerService.getFilingProposalCVInfoList(companyId,vehicle,transMsg,declareType,dealerCode,startTime,endTime,declareStatus, false) );
			setFilingProposalCVInfoList(companyId,vehicle,transMsg,declareType,dealerCode,startTime,endTime,declareStatus, false);

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
		return "/dealer/declareManager/listDeclare";
	}
	
	/**
	 * 
	* Description: 外出、维修报备查看页面
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param declareType
	* @return
	 */
	@RequestMapping(value = "/listDeclare.vti", method = RequestMethod.GET)
	public String listDeclare(TransMsg transMsg,@RequestParam("declareType")  Integer declareType) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());

			List<FilingProposalCompanyVehcileInfo> list=declareManagerService.getFilingProposalCVInfoList(companyId,null,transMsg,declareType,null,null,null,null,false);
			getRequest().setAttribute("filingProposalCVInfoList", list);
			setFilingProposalCVInfoList(companyId,null,transMsg,declareType,null,null,null,null,false);

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
		return "/dealer/declareManager/listDeclare";
	}
	
	/**
	 * 
	* Description: 外出、维修报备审批页面
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param vehicleId
	* @param declareType
	* @return
	 */
	@RequestMapping(value = "/viewDeclareHis.vti", method = RequestMethod.GET)
	public String viewDeclareHis(TransMsg transMsg,
			@RequestParam("vehicleId")  Integer vehicleId,@RequestParam("declareType")  Integer declareType) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			List<FilingProposalVO> filingProposalList=declareManagerService.getFilingProposalList(transMsg,vehicleId,companyId,declareType,null,null);
			getRequest().setAttribute("startTime", "");
			getRequest().setAttribute("endTime", "");
			getRequest().setAttribute("filingProposalList", filingProposalList);
			getRequest().setAttribute("vehicleId", vehicleId);
			getRequest().setAttribute("declareType", declareType);
			
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
		return "/dealer/declareManager/listDeclareInfo";
	}
	
	/**
	 * 
	* Description: 外出、维修报备审批查询
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param vehicleId
	* @param declareType
	* @param startTime
	* @param endTime
	* @return
	 */
	@RequestMapping(value = "/queryViewDeclareHis.vti", method = RequestMethod.GET)
	public String queryViewDeclareHis(TransMsg transMsg,
			@RequestParam("vehicleId")  Integer vehicleId,@RequestParam("declareType")  Integer declareType,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) {
		try{
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			List<FilingProposalVO> filingProposalList=declareManagerService.getFilingProposalList(transMsg,vehicleId,companyId,declareType,startTime,endTime);
			getRequest().setAttribute("filingProposalList", filingProposalList);
			if(null!=startTime){
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}
			if(null!=endTime){
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}
			
			getRequest().setAttribute("vehicleId", vehicleId);
			getRequest().setAttribute("declareType", declareType);
					
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
		return "/dealer/declareManager/listDeclareInfo";
	}
		
	/**
	 * 
	* Description: 报备详情查看
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param vehicleId
	* @param declareType
	* @return
	 */
	@RequestMapping(value = "/viewDeclare.vti", method = RequestMethod.GET)
	public String viewDeclare(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("vehicleId")  Integer vehicleId,@RequestParam("declareType")  Integer declareType) {
		try {
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			List list=declareManagerService.queryDeclareInfo(filingProposalId,vehicleId,declareType,companyId);
			if(null!=list && list.size()>=1){
				getRequest().setAttribute("filingProposalList", list.get(0));
				getRequest().setAttribute("filingProposalCV",list.get(1));
			}else{
				getRequest().setAttribute("filingProposalList", null);
				getRequest().setAttribute("filingProposalCV",null);
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
						GlobalConstant.MSG_RESULT_CONTENT, "没有查到相关记录");
			}
		}catch(ProtocolParseBusinessException ex){
			getRequest().setAttribute("filingProposalList", null);
			getRequest().setAttribute("filingProposalCV",null);
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
		} catch (Exception e) {
			logger.info(e.getMessage());
			getRequest().setAttribute("filingProposalList", null);
			getRequest().setAttribute("filingProposalCV",null);
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, "系统异常");
		}
		return "/dealer/declareManager/viewDeclareNew";
	}
		
	/**
	 * 
	* Description: 添加报备
	* @author henggh 
	* @version 0.1 
	* @param declareType
	* @return
	 */
	@RequestMapping(value = "/addDeclareNewView.vti", method = RequestMethod.GET)
	public String addDeclareNewView(
			@RequestParam("declareType") Integer declareType) {
		try{
			setDeclareInfo(null,null,declareType,null);
			return "/dealer/declareManager/addNewDeclare";
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
			return "/dealer/declareManager/addNewDeclare";
		}catch(Exception e){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
			return "/dealer/declareManager/addNewDeclare";
		}
	}
	
	public String addDeclareFail(Integer vehicleId,Integer carStyleId,FilingProposalVO filingProposal){
		
		setDeclareInfo(vehicleId,carStyleId,filingProposal.getType(),filingProposal);
		return "/dealer/declareManager/addNewDeclare";
	}
	
	public void setDeclareInfo(Integer vehicleId,Integer carStyleId,Integer declareType,FilingProposalVO filingProposalParam) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Company company = CacheCompanyManager.getCompany(companyId);
		DealerVO dealerVO = new DealerVO(company);
		
		Vehicle vehicle=null;
		List<String> plateNumbers=null;
		VehicleVO vehicleVO=new VehicleVO();
		Map dealerCarStyleMap=declareManagerService.findDealerCarStyle(companyId);
		List<CarStyle> carStyles=(List<CarStyle>) dealerCarStyleMap.get("carStyles");

		if(null==vehicleId){
			FilingProposalVO filingProposal=new FilingProposalVO();
			filingProposal.setType(declareType);
			getRequest().setAttribute("filingProposal", filingProposal);
			plateNumbers=(List<String>) dealerCarStyleMap.get("plateNumbers");
			vehicle=(Vehicle) dealerCarStyleMap.get("vehilce");
		}else{
			Map carStyleMap=declareManagerService.findVehicleByCarStyleId(companyId, carStyleId);
			plateNumbers=(List<String>) carStyleMap.get("plateNumbers");
			vehicle=CacheVehicleManager.getVehicleById(vehicleId);
			getRequest().setAttribute("filingProposal", filingProposalParam);
		}

		getRequest().setAttribute("dealer", dealerVO);
		getRequest().setAttribute("declareType", declareType);
		getRequest().setAttribute("carStyles", carStyles);
		
		if (null != vehicle) {
			BeanUtils.copyProperties(vehicle, vehicleVO);
			String licensingTime = DateTime.toNormalDate(vehicleVO.getLicensingTime());
			vehicleVO.setLicensingTimeStr(licensingTime);
			Box box = CacheVehicleManager.getBoxByVehicleId(vehicleId);
			getRequest().setAttribute("vehicle", vehicleVO);
			getRequest().setAttribute("simMobile", (null == box ? "" : box.getSimMobile()));
			getRequest().setAttribute("selectedPlateNumber", vehicle.getPlateNumber());
			getRequest().setAttribute("plateNumberList", plateNumbers);
			getRequest().setAttribute("selectedCarStyleId",vehicle.getCarStyleId());
		}else{
			getRequest().setAttribute("vehicle", vehicleVO);
			getRequest().setAttribute("simMobile", "");
			getRequest().setAttribute("selectedPlateNumber", "");
			getRequest().setAttribute("selectedCarStyleId", carStyles.get(0).getId());
		}
	}
	
	@RequestMapping(value = "/findVehicleByCarStyleId.vti", method = RequestMethod.POST)
	public void findVehicleByCarStyleId(@RequestParam("carStyleId") Integer carStyleId){
		ResponseData rd = new ResponseData(0);
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		Company company = CacheCompanyManager.getCompany(companyId);
		DealerVO dealerVO = new DealerVO(company);
		Map map=declareManagerService.findVehicleByCarStyleId(companyId, carStyleId);
		List<String> plateNumbers=(List<String>) map.get("plateNumbers");
		Vehicle vehicle=(Vehicle) map.get("vehilce");
		
		if(null==vehicle){
			rd.setCode(-1);
			rd.put("vehicle", new VehicleVO());
			rd.put("simMobile", "");
			rd.put("selectedPlateNumber", "");
			rd.put("plateNumberList", null);
			rd.setMessage("该车型没有相关配置车辆");
		}else{
			rd.setCode(1);
			VehicleVO vehicleVO=new VehicleVO();
			BeanUtils.copyProperties(vehicle, vehicleVO);
			String licensingTime = DateTime.toNormalDate(vehicleVO.getLicensingTime());
			vehicleVO.setLicensingTimeStr(licensingTime);
			Box box = CacheVehicleManager.getBoxByVehicleId(vehicleVO.getId());			
			rd.put("vehicle", vehicleVO);
			rd.put("simMobile", (null == box ? "" : box.getSimMobile()));
			rd.put("selectedPlateNumber", vehicleVO.getPlateNumber());
			rd.put("plateNumberList", plateNumbers);
		}
		writeRd(rd);
	}
	
	/**
	 * 
	* Description: 根据车牌查信息
	* @author henggh 
	* @version 0.1 
	* @param plateNumber
	* @param declareType
	 */
	@RequestMapping(value = "/findVehiclePlateNumber.vti", method = RequestMethod.POST)
	public void findVehiclePlateNumber(@RequestParam("plateNumber")  String plateNumber,@RequestParam("declareType")  Integer declareType){
		ResponseData rd = new ResponseData(0);
		VehicleVO vehicleVO = vehicleService.getByPalteNumber(plateNumber);

		if(null!=vehicleVO){
			getRequest().setAttribute("vehicle", vehicleVO);
			FilingProposalVO fpv=declareManagerService.getFilingProposalByVehicle(vehicleVO.getId(),declareType);
			if(null==fpv){
				 String licensingTime = DateTime.toNormalDate(vehicleVO.getLicensingTime());
				 vehicleVO.setLicensingTimeStr(licensingTime);
				 
				 Box box = CacheVehicleManager.getBoxByVehicleId(vehicleVO.getId());
			     rd.put("vehicle", vehicleVO);
			     rd.put("simMobile", (null==box ? "": box.getSimMobile()));
			     rd.setCode(1);
			}else{
				rd.setCode(-1);
				if(declareType.intValue()==1){
					rd.setMessage("还有处于待审批的市场活动");
				}else{
					rd.setMessage("还有处于待审批的事故维修");
				}
			}
			writeRd(rd);
		}
	}
		
	/**
	 * 
	* Description: 新增保存
	* @author henggh 
	* @version 0.1 
	* @param filingProposalCVInfo
	* @param vehicleId
	* @return
	 */	
	@RequestMapping(value = "/addDeclare.vti", method = RequestMethod.POST)
	public String addDeclare(@ModelAttribute("filingProposalCVInfo") FilingProposalCompanyVehcileInfo filingProposalCVInfo,@RequestParam("declareFile") MultipartFile multipartFile,@RequestParam("vehicleId") Integer vehicleId,@RequestParam("selectedCarStyleId") Integer selectedCarStyleId){
		FilingProposalVO filingProposal=null;
		try{
			Integer departmentId = SessionManager.getCurrentCompanyId(getRequest());
			
			filingProposal=filingProposalCVInfo.getFilingProposal();
			List<FilingProposalCompanyVehcileInfo> list=declareManagerService.saveFilingProposal(getRequest(),multipartFile,filingProposalCVInfo,vehicleId);
			getRequest().setAttribute("filingProposalCVInfoList", list);
			setFilingProposalCVInfoList(departmentId,null,null,filingProposal.getType(),null,null,null,null,false);
			return "/pub/jsPages/closeDialogAndReloadParent";
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
			return addDeclareFail(vehicleId,selectedCarStyleId,filingProposal);
		}catch(Exception e){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
			return addDeclareFail(vehicleId,selectedCarStyleId,filingProposal);
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
	@RequestMapping(value = "/submitCancelDeclare.vti", method = RequestMethod.POST)
	public void submitCancelDeclare(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("declareType")  Integer declareType,@RequestParam("declareStatus")  Integer declareStatus) {
		Map map=new HashMap();
		try{
			User user=SessionManager.getCurrentUser(getRequest());
			TransMsg transMsg=new TransMsg();
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			List list=declareManagerService.submitCancelDeclare(user.getId(), companyId, filingProposalId, declareType, declareStatus);
			getRequest().setAttribute("filingProposalCVInfoList", list);
			setFilingProposalCVInfoList(companyId,null,transMsg,declareType,null,null,null,null,false);
			map.put("message", "success");
			writeMap(map);
			//return "/dealer/declareManager/listDeclare";
		}catch(ProtocolParseBusinessException ex){
			map.put("message", "error");
			writeMap(map);
		}catch(Exception e){
			logger.info(e.getMessage());
			map.put("message", "系统异常");
			writeMap(map);
		}
	}
	
	/**
	 * 
	* Description: 编辑报备
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param declareType
	* @return
	 */
	@RequestMapping(value = "/editDeclareView.vti", method = RequestMethod.GET)
	public String editDeclareView(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("declareType")  Integer declareType){
		
		try{
			FilingProposalCompanyVehcileInfo filingProposalCV;
			
			filingProposalCV = declareManagerService.getFilingProposalCompanyVehcileInfo(filingProposalId);
			getRequest().setAttribute("declareType", declareType);
			getRequest().setAttribute("filingProposalCV",filingProposalCV);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/editDeclare";		
	}
	
	public String editDeclareFail(FilingProposalVO filingProposal){
		
		FilingProposalCompanyVehcileInfo filingProposalCV;
		try {
			filingProposalCV = declareManagerService.getFilingProposalCompanyVehcileInfo(filingProposal.getFilingProposalId());
			filingProposalCV.setFilingProposal(filingProposal);
			getRequest().setAttribute("declareType", filingProposal.getType());
			getRequest().setAttribute("filingProposalCV",filingProposalCV);
			User user=SessionManager.getCurrentUser(getRequest());
			getRequest().setAttribute("currentUserName", user.getNickName());
		} catch (IllegalAccessException | InvocationTargetException e) {
			logger.info(e.getMessage());
		}
		return "/dealer/declareManager/editDeclare";
	}
	
	/**
	 * 
	* Description: 编辑报备提交
	* @author henggh 
	* @version 0.1 
	* @param filingProposalCVInfo
	* @return
	 */
	@RequestMapping(value = "/editDeclare.vti", method = RequestMethod.POST)
	public String editDeclare(@ModelAttribute("filingProposalCVInfo") FilingProposalCompanyVehcileInfo filingProposalCVInfo){
		try{
			FilingProposalVO filingProposalView=filingProposalCVInfo.getFilingProposal();
			User user=SessionManager.getCurrentUser(getRequest());
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			List<FilingProposalCompanyVehcileInfo> list= declareManagerService.editDeclare(filingProposalCVInfo,user.getId(),companyId);
			getRequest().setAttribute("filingProposalCVInfoList", list);
			TransMsg transMsg=new TransMsg();
			setFilingProposalCVInfoList(companyId,null,transMsg,filingProposalView.getType(),null,null,null,null,false);
			return "/pub/jsPages/closeDialogAndReloadParent";
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					ex.getResultCode(), ex.getDesc());
			return editDeclareFail(filingProposalCVInfo.getFilingProposal());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
			return editDeclareFail(filingProposalCVInfo.getFilingProposal());
		}
	}
	
	/**
	 * 
	* Description: 报备审批打开页面
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param vehicleId
	* @param declareType
	* @return
	 */
	@RequestMapping(value = "/declareApprovalView.vti", method = RequestMethod.GET)
	public String declareApprovalView(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("vehicleId")  Integer vehicleId,@RequestParam("declareType")  Integer declareType) {
		try {
			FilingProposalCompanyVehcileInfo filingProposalCV;
			filingProposalCV = declareManagerService.getFilingProposalCompanyVehcileInfo(filingProposalId);
			if(null!=filingProposalCV){
				Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
				String startTime=DateTime.getDate(DateTime.getOffsetMonthDate(filingProposalCV.getFilingProposal().getProposalTime(),3),"yyyy-MM-dd HH:mm:ss");
				List<FilingProposalVO> filingProposalList=declareManagerService.getFilingProposalList(null,vehicleId,companyId,declareType,startTime,null);
				getRequest().setAttribute("filingProposalList", filingProposalList);				
				getRequest().setAttribute("filingProposalCV",filingProposalCV);
				FilingApprovalVO filingProposal =new FilingApprovalVO();
				filingProposal.setFilingProposalId(filingProposalId);
				getRequest().setAttribute("filingApproval", filingProposal);
			}else{
				message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,GlobalConstant.MSG_RESULT_CONTENT, "没有查到相关记录");
			}
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/declareApprovalNew";
	}
	
	/**
	 * 
	* Description: 报备审批提交
	* @author henggh 
	* @version 0.1 
	* @param transMsg
	* @param filingApproval
	* @return
	 */
	@RequestMapping(value = "/declareApproval.vti", method = RequestMethod.POST)
	public String declareApproval(TransMsg transMsg,@ModelAttribute("filingApproval") FilingApprovalVO filingApproval) {
		try {
			Integer userId=SessionManager.getCurrentUserId(getRequest());
			Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
			List<FilingProposalVO> filingProposalList=declareManagerService.saveFilingApproval(filingApproval,transMsg,userId,companyId);
			getRequest().setAttribute("filingProposalList", filingProposalList);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		return "/pub/jsPages/closeDialogAndReloadParent";
	}
		
	/**
	 * 
	* Description: 附件管理列表
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	@RequestMapping(value = "/declareAttachFileList.vti", method = RequestMethod.GET)
	public String declareAttachFileList(@RequestParam("filingProposalId")  Integer filingProposalId) {
		try {
			ResponseData rd=declareManagerService.getFilingProposalAttachList(filingProposalId);
			getDeclareAttachFileList(filingProposalId,rd);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		
		return "/dealer/declareManager/declareAttachFileList";
	}
	
	/**
	 * 
	* Description: 查看附件列表
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @return
	 */
	@RequestMapping(value = "/declareAttachFileDownList.vti", method = RequestMethod.GET)
	public String declareAttachFileDownList(@RequestParam("filingProposalId")  Integer filingProposalId) {
		try {
			ResponseData rd=declareManagerService.getFilingProposalAttachList(filingProposalId);
			getDeclareAttachFileList(filingProposalId,rd);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/declareAttachFileDownList";
	}
	
	/**
	 * 
	* Description: 附件管理下载
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param filingProposalAttachmentId
	 */
	@RequestMapping(value = "/declareAttachFileDown.vti", method = RequestMethod.GET)
	public void declareAttachFileDown(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("filingProposalAttachmentId")Integer filingProposalAttachmentId) {
		
		try {
			FilingProposalAttachmentVO filingProposalAttachmentVO=declareManagerService.getFilingProposalAttachment(filingProposalAttachmentId);
			UploadHelper.downloadFile(filingProposalAttachmentVO.getFullPath(),getResponse());
			//getDeclareAttachFileList(filingProposalId);
			//return "/dealer/declareManager/declareAttachFileDownList";
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
	}
	
	/**
	 * 
	* Description: 附件管理文件上传  要求必须有附件
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param delFiles
	* @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/uploadDelFile.vti", method = RequestMethod.POST)
	public String uploadDelFile(HttpServletRequest request, @RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("delFiles")String delFiles) {
		
		try {
			ResponseData rd=declareManagerService.uploadFile(request,filingProposalId,delFiles);
			getDeclareAttachFileList(filingProposalId,rd);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/declareAttachFileList";
	}
	
	@RequestMapping(value="/uploadFile.vti", method = RequestMethod.POST)
	public String uploadFile(HttpServletRequest request, @RequestParam("filingProposalId")  Integer filingProposalId) {
		try{
			ResponseData rd=declareManagerService.uploadFile(request,filingProposalId,null);
			getDeclareAttachFileList(filingProposalId,rd);
		}catch(ProtocolParseBusinessException ex){
			ResponseData rd=declareManagerService.getFilingProposalAttachList(filingProposalId);
			getDeclareAttachFileList(filingProposalId,rd);
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			ResponseData rd=declareManagerService.getFilingProposalAttachList(filingProposalId);
			getDeclareAttachFileList(filingProposalId,rd);
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/declareAttachFileList";
	}
		
	@RequestMapping(value="/delDeclareAttachmentFile.vti", method = RequestMethod.GET)
	public String delDeclareAttachmentFile(@RequestParam("filingProposalId")  Integer filingProposalId,@RequestParam("filingProposalAttachmentId")Integer filingProposalAttachmentId) {
		try{
			ResponseData rd=declareManagerService.delFilingProposalAttachment(filingProposalId,filingProposalAttachmentId);
			getDeclareAttachFileList(filingProposalId,rd);
		}catch(ProtocolParseBusinessException ex){
			ResponseData rd=declareManagerService.getFilingProposalAttachList(filingProposalId);
			getDeclareAttachFileList(filingProposalId,rd);
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			ResponseData rd=declareManagerService.getFilingProposalAttachList(filingProposalId);
			getDeclareAttachFileList(filingProposalId,rd);
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					"msgResultContent", "系统异常");
		}
		return "/dealer/declareManager/declareAttachFileList";
	}
	
	private void getDeclareAttachFileList(Integer filingProposalId,ResponseData rd) {
		List<FilingProposalAttachmentVO> filingProposalAttachmentList=new ArrayList<FilingProposalAttachmentVO>();
		if(null!=rd ){
			List<FilingProposalAttachmentVO> temp=(List<FilingProposalAttachmentVO>) rd.get("filingProposalAttachmentList");
			if(null!=temp && !temp.isEmpty()){
				for(FilingProposalAttachmentVO filingProposalAttachment:temp){
					filingProposalAttachment.setSize((new BigDecimal(filingProposalAttachment.getSize()).divide(new BigDecimal(1024*1024),2,BigDecimal.ROUND_HALF_UP)).toString());
					filingProposalAttachmentList.add(filingProposalAttachment);
				}
			}
		}
		getRequest().setAttribute("filingProposalAttachmentList", filingProposalAttachmentList);
		getRequest().setAttribute("filingProposalId", filingProposalId);
	}
	
	
	/**
	 * 
	* Description: 
	* @author henggh 
	* @version 0.1 
	* @param companyId
	* @param vehicle
	* @param transMsg
	* @param declareType
	* @param dealerCode
	* @param startTime
	* @param endTime
	* @param declareStatus
	* @param isExport
	* @return
	 */
	private void setFilingProposalCVInfoList(Integer companyId,VehicleVO vehicle,TransMsg transMsg,Integer declareType,String dealerCode,String startTime,String endTime,String declareStatus,boolean isExport){
		if(null==transMsg){
			transMsg=new TransMsg();
		}
		
		if (isExport) {
			transMsg.setPageSize(Integer.MAX_VALUE);
		} else {
			transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		}
		getRequest().setAttribute("transMsg", transMsg);

		List departmentList = CacheOrgManager.getChildrenOrgTree4Select(null,companyId);
		getRequest().setAttribute("departmentList", departmentList);
		getRequest().setAttribute("vehicle",new VehicleVO());
		getRequest().setAttribute("declareType", declareType);
		
		User user=SessionManager.getCurrentUser(getRequest());
		getRequest().setAttribute("currentUserName", user.getNickName());
		if(null==dealerCode){
			getRequest().setAttribute("dealerCode", "");
		}else{
			getRequest().setAttribute("dealerCode", dealerCode);
		}
		
		if(null==startTime){
			getRequest().setAttribute("startTime", "");
		}else{
			getRequest().setAttribute("startTime", startTime);
		}
		
		if(null==endTime){
			getRequest().setAttribute("endTime", "");
		}else{
			getRequest().setAttribute("endTime", endTime);
		}
		
		if(null==declareStatus){
			getRequest().setAttribute("declareStatus", "-1");
		}else{
			getRequest().setAttribute("declareStatus", declareStatus);
		}
	}
	
	@RequestMapping(value = "/exportViewDeclareHis.vti", method = RequestMethod.GET)
	public void exportViewDeclareHis(TransMsg transMsg,
			@RequestParam("vehicleId")  Integer vehicleId,@RequestParam("declareType")  Integer declareType,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		transMsg.setPageSize(Integer.MAX_VALUE);
		List<FilingProposalVO> filingProposalList=declareManagerService.getFilingProposalList(transMsg,vehicleId,companyId,declareType,startTime,endTime);
		getRequest().setAttribute("filingProposalList", filingProposalList);
		//导出
			write2excelHis(filingProposalList,startTime,endTime);
		try {
			if(null!=startTime){
				getRequest().setAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			}
			if(null!=endTime){
				getRequest().setAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			}
		} catch (ParseException e) {
		}
		getRequest().setAttribute("vehicleId", vehicleId);
		getRequest().setAttribute("declareType", declareType);
		
	}
	
	@RequestMapping(value = "/exportListDeclare.vti", method = RequestMethod.GET)
	public void exportListDeclare(TransMsg transMsg,@ModelAttribute("vehicle") VehicleVO vehicle,@RequestParam("declareType")  Integer declareType, @RequestParam("dealerCode") String dealerCode,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime,@RequestParam("declareStatus") String declareStatus) {
		Integer companyId = SessionManager.getCurrentCompanyId(getRequest());
		transMsg.setPageSize(Integer.MAX_VALUE);
		List<FilingProposalCompanyVehcileInfo>  infoList = declareManagerService.getFilingProposalCVInfoList(companyId,vehicle,transMsg,declareType,dealerCode,startTime,endTime,declareStatus,true);
		
		getRequest().setAttribute("filingProposalCVInfoList", infoList);
		//导出
		write2excel(infoList,declareType,startTime,endTime,vehicle, dealerCode);
		try {
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
			
		} catch (ParseException e) {
			
		}
	}
	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excelHis(List<FilingProposalVO> filingProposalList,  
			String startTime,String endTime) {
		
		String[] titles = {"申请人","申请时间","开始日期","结束日期","报备类型",
        		"报备状态"};
		
		String fileName ="报备审批";
		
	    if(startTime!=null&&startTime!="")
	    {
	    	fileName+= "["+startTime.substring(0,10)+"-"+endTime.substring(0,10)+"]";
	    }
		fileName+=DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("报备审批");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		for (int i = 0; i < filingProposalList.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			FilingProposalVO filingProposal = filingProposalList.get(i);
			HSSFCell cell = row.createCell(0);
        	
			//cell.setCellValue(filingProposal.getProposerName());
			cell = row.createCell(1);
			cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getProposalTime()));
			cell = row.createCell(2);
			cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getStartTime()));
			cell = row.createCell(3);
			cell.setCellValue(DateTime.toNormalDateTime(filingProposal.getEndTime()));
			cell = row.createCell(4);
			//cell.setCellValue(filingProposal.getType_nick());
			cell = row.createCell(5);
			//cell.setCellValue(filingProposal.getStatus_nick());
					
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}	
	/**
	 * 导出数据到excel
	 * @param violations
	 */
	public  void write2excel(List<FilingProposalCompanyVehcileInfo> filingProposalCompanyVehcileInfo, Integer declareType, 
			String startTime,String endTime, VehicleVO vehicle,String dealerCode) {
		
		String[] titles = {"分销中心","网络代码","经销商名称","经销商状态","网络形态","省份",
				"城市","车牌","车型","VIN","提交时间","开始日期","结束日期","目的地",
        		"状态","审核人"};
		
		String fileName ="";
		fileName += (declareType == 1) ? "市场活动" : "事故维修";
	    if(vehicle.getPlateNumber()!=null&&vehicle.getPlateNumber()!="")
	    {
	    	fileName+= "车牌"+vehicle.getPlateNumber();
	    }
	    if(vehicle.getVin()!=null&&vehicle.getVin()!="")
	    {
	    	fileName+= "vin码"+vehicle.getVin();
	    }
	    if(dealerCode!=null&&dealerCode!="")
	    {
	    	fileName+= "网络代码"+dealerCode;
	    }
	    if(startTime!=null&&startTime!="")
	    {
	    	fileName+= "["+startTime.substring(0,10)+"-"+endTime.substring(0,10)+"]";
	    }
		fileName+=DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet((declareType == 1) ? "市场活动" : "事故维修");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (filingProposalCompanyVehcileInfo != null && filingProposalCompanyVehcileInfo.size() > 0) {
			for (int i = 0; i < filingProposalCompanyVehcileInfo.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				FilingProposalCompanyVehcileInfo filingProposalCVInfo = filingProposalCompanyVehcileInfo.get(i);
				HSSFCell cell = row.createCell(0);
				
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getDistributionSaleCenterName());
				cell = row.createCell(1);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getDealerCode());
				cell = row.createCell(2);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getCnName());
				cell = row.createCell(3);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getIsKeyCity_Nick());
				cell = row.createCell(4);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getDealerType_Nick());
				cell = row.createCell(5);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getRegionProvinceId_Nick());
				cell = row.createCell(6);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getDealer().getRegionCityId_Nick());
				cell = row.createCell(7);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getPlateNumber());
				cell = row.createCell(8);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getCarStyleId_Nick());
				cell = row.createCell(9);
				cell.setCellValue(filingProposalCVInfo.getVehicle().getVin());
				cell = row.createCell(10);
				//cell.setCellValue(DateTime.toNormalDateTime(filingProposalCVInfo.getFilingProposal().getCommitTime()));
				cell = row.createCell(11);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposalCVInfo.getFilingProposal().getStartTime()));
				cell = row.createCell(12);
				cell.setCellValue(DateTime.toNormalDateTime(filingProposalCVInfo.getFilingProposal().getEndTime()));
				cell = row.createCell(13);
				//cell.setCellValue(filingProposalCVInfo.getFilingProposal().getDescription());
				cell = row.createCell(14);
				//cell.setCellValue(filingProposalCVInfo.getFilingProposal().getStatus_nick());
				cell = row.createCell(15);
				//cell.setCellValue(filingProposalCVInfo.getFilingProposal().getApprovalName());
				
			}
		}
		//传送excel到客户
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));
	}
}