package com.ava.gateway.facade.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.base.dao.util.StringUtils;
import com.ava.baseSystem.service.ICompanyService;
import com.ava.baseSystem.service.IShortMessageService;
import com.ava.dao.ILocationDao;
import com.ava.dealer.service.IAlertService;
import com.ava.dealer.service.IDriveLineService;
import com.ava.dealer.service.IFilingProposalService;
import com.ava.dealer.service.IMonitorService;
import com.ava.dealer.service.ITestDriveService;
import com.ava.dealer.service.IVehicleService;
import com.ava.dealer.service.IViolationService;
import com.ava.domain.entity.Alert;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.DriveLine;
import com.ava.domain.entity.Location;
import com.ava.domain.entity.TestDrive;
import com.ava.domain.entity.Vehicle;
import com.ava.domain.entity.Violation;
import com.ava.domain.vo.DriveLineVO;
import com.ava.domain.vo.DriveLineWithPolygonVO;
import com.ava.enums.ServiceResponseEnum;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.gateway.business.IProtocolParseBusinessService;
import com.ava.gateway.business.impl.ProtocolParseBusinessService;
import com.ava.gateway.dao.IProtocolMessageDao;
//import com.ava.gateway.domain.entity.CurrentGpsMessage;
import com.ava.gateway.domain.vo.BaseDataReq;
import com.ava.gateway.domain.vo.GetCommonDataReq;
import com.ava.gateway.domain.vo.GpsMessageDataReq;
import com.ava.gateway.domain.vo.HeaderMessage;
import com.ava.gateway.domain.vo.ProtocolParseRes;
import com.ava.gateway.domain.vo.SetConfigDataReq;
import com.ava.gateway.domain.vo.TBoxStatusDataReq;
import com.ava.gateway.facade.IProtocolParseBusinessFacade;
import com.ava.gateway.gpsUtil.GPSPoint;
import com.ava.gateway.gpsUtil.GpsUtil;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.resource.cache.CacheDriveLineManager;
import com.ava.resource.cache.CacheTestDriveManager;
import com.ava.resource.cache.CacheVehicleManager;
import com.ava.util.DateTime;
import com.ava.util.gis.GisUtil;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;

@Service("testDriver.web2gatewayFacade")
public class ProtocolParseBusinessFacade implements IProtocolParseBusinessFacade{

	@Autowired
	private IShortMessageService shortMessageService;
	
	@Autowired
	private IVehicleService vehicleService;
		
	@Autowired
	ILocationDao locationDao; //位置
	
	@Autowired
	ITestDriveService testDriveService;//试驾
	
	@Autowired
	IViolationService violationService; //违规
	
	@Autowired
	IAlertService alertService; //报警
	
//	@Autowired
//	private IBoxService boxService;
	
	@Resource(name="testDriver.web2gatewayService")
	IProtocolParseBusinessService protocolParseBusinessService;
	
	@Autowired
	IDriveLineService driveLineService;
	
	@Autowired
	ICompanyService companyService;
	
	@Resource(name="dealer.filingProposalService")
	IFilingProposalService filingProposalService;
	
	@Autowired
	private IMonitorService monitorService;
		
	@Resource(name="testDriver.web2gatewayDao")
	IProtocolMessageDao protocolMessageDao;

	
	protected final static Logger logger = Logger.getLogger(ProtocolParseBusinessService.class);
	
	/**
	 * 解析消息入口处
	 */
	public ProtocolParseRes handleMessage(String msg){
		HeaderMessage headerMessage=new HeaderMessage();
		if(null==msg || "".equals(msg.trim())){
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);	
		}
		JSONObject jsonObj = JSONObject.fromObject(msg);
		if(null==jsonObj){
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		}
		JSONObject headerJson = jsonObj.getJSONObject("header");
		headerMessage=handleHeaderMessage(headerJson);
		
		if(null==headerMessage)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		Vehicle vehicle=verifyVehicleBox(headerMessage);
		JSONObject bodyJson = jsonObj.getJSONObject("body");
		ProtocolParseRes protocolParseRes = handleBodyMessage(vehicle,headerMessage,bodyJson);
		return protocolParseRes;
	}
	/**
	 * 解析请求消息头部
	 */
	private HeaderMessage handleHeaderMessage(JSONObject headerJson){
		HeaderMessage headerMessage;
		try{
			headerMessage=getHeader(headerJson);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006);
		}
		return headerMessage;
	}
	/**
	 * json转换为头部对象
	 * @param header
	 * @return
	 */
	private HeaderMessage getHeader(JSONObject header){
		HeaderMessage headerMessage=null;
		try{
			if(null!=header)
				headerMessage=(HeaderMessage) JSONObject.toBean(header, HeaderMessage.class);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10006);
		}
		return headerMessage;
	}
	/**
	 * 权限验证
	 */
	public Vehicle verifyVehicleBox(HeaderMessage header){
		if(null==header
				|| StringUtils.isEmpty(header.getImsi())
				|| StringUtils.isEmpty(header.getSerialNumber())
				|| StringUtils.isEmpty(header.getVin())) {
			shortMessageService.sendMessage(header.getVin(), header.getSerialNumber(), ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001.getDesc());
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		}
		//Integer vehicleId=-100;
		//Integer dealerId=-100;
		handleErrorVin(header);//处理错误vin码车辆 
		/**查询车辆是否存在*/
		Vehicle vehicle=CacheVehicleManager.getVehicleByVin(header.getVin());
		if(null==vehicle) {
			shortMessageService.sendMessage(header.getVin(), header.getSerialNumber(), ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10003.getDesc());
			protocolParseBusinessService.saveTaskLog(header, GlobalConstant.LOG_USER_NOT_EXIST);
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10003);
		}
		//vehicleId=vehicle.getId();
		//dealerId=vehicle.getCompanyId();
		if(null==vehicle.getCompanyId() || null==vehicle.getId()) {
			shortMessageService.sendMessage(header.getVin(), header.getSerialNumber(), ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10003.getDesc());
			protocolParseBusinessService.saveTaskLog(header, GlobalConstant.LOG_USER_NOT_EXIST);
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10003);
			
		}
		//Map<String,Integer> map =new HashMap<String,Integer>();
		//map.put("vehicleId", vehicleId);
		//map.put("dealerId", dealerId);
		
		//handleBoxMessage(header,vehicle);//采取松散关系处理  故暂时不需要一对一关系处理
		//return map;
		return vehicle;
	}
	
	/**
	 * 处理错误vin码
	 * @author tangqingsong
	 * @version 
	 * @param header
	 */
	private void handleErrorVin(HeaderMessage header){
		if(GlobalConstant.ERROR_VIN_LIST.containsKey(header.getVin())){
			//属于错误vin码车辆，根据obd_id查询出vin码
			Vehicle vehicle = CacheVehicleManager.getVehicleBySerialNumber(header.getSerialNumber());
			if(vehicle!=null && !header.getVin().isEmpty()){
				header.setVin(vehicle.getVin());
			}
		}
	}
	/**
	 * 处理消息体
	 * @param headerMessage
	 * @param bodyJson
	 * @return
	 */
	public ProtocolParseRes handleBodyMessage(Vehicle vehicle,HeaderMessage headerMessage,JSONObject bodyJson){
		ProtocolParseRes protocolResponse=new ProtocolParseRes();
		
		JSONObject bodyDataJson = bodyJson.getJSONObject("data");
		if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_GET_COMMON==headerMessage.getMessageCode()){
			GetCommonDataReq baseDataRequest=(GetCommonDataReq)JSONObject.toBean(bodyDataJson, GetCommonDataReq.class);
			protocolResponse=protocolParseBusinessService.handleGetCommonDataRes(headerMessage,baseDataRequest);
		}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UPDATE_STATUS==headerMessage.getMessageCode()){
			TBoxStatusDataReq baseDataRequest=(TBoxStatusDataReq) JSONObject.toBean(bodyDataJson, TBoxStatusDataReq.class);
			protocolResponse=protocolParseBusinessService.handleTBoxStatusDataReq(headerMessage,baseDataRequest);
		}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_SET_CONFIG==headerMessage.getMessageCode()){
			SetConfigDataReq baseDataRequest=(SetConfigDataReq)JSONObject.toBean(bodyDataJson, SetConfigDataReq.class);
			protocolResponse=protocolParseBusinessService.handleSetConfigDataReq(headerMessage,baseDataRequest);
		}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT==headerMessage.getMessageCode() 
				|| GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_QUERY_VEHICLE_STATUS==headerMessage.getMessageCode()
				|| GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_TEST_MESSAGE_UP==headerMessage.getMessageCode()
				|| GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP==headerMessage.getMessageCode()
				|| GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_VEHICLE_ALARM==headerMessage.getMessageCode()
				|| GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_DRIVING_ERROR_REMIND==headerMessage.getMessageCode()){
			if(headerMessage.getTypeCode()==null || headerMessage.getTypeCode().isEmpty()){
				vehicleService.handleVehicleBox(1, headerMessage.getVin(), headerMessage.getSerialNumber(), headerMessage.getImsi());
			}
			GpsMessageDataReq baseDataRequest=(GpsMessageDataReq)JSONObject.toBean(bodyDataJson, GpsMessageDataReq.class);
			
			Date saveDate=new Date();
			protocolResponse=protocolParseBusinessService.handleGpsMessageDataReq(headerMessage,baseDataRequest);//存放原始消息，以便后续查证
			handleTestDriveInfoNew(vehicle,headerMessage,baseDataRequest,saveDate);//处理违规、越界、报警信息
		}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_OTA_UPDATE==headerMessage.getMessageCode()){
			BaseDataReq baseDataRequest=(BaseDataReq)JSONObject.toBean(bodyDataJson, BaseDataReq.class);
			protocolResponse=protocolParseBusinessService.handleBoxUpdate(headerMessage,baseDataRequest);
		}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_OBD_DISMANTLE_ALERT==headerMessage.getMessageCode()){
			vehicleService.handleVehicleBox(0, headerMessage.getVin(), headerMessage.getSerialNumber(), headerMessage.getImsi());
			GpsMessageDataReq baseDataRequest=(GpsMessageDataReq)JSONObject.toBean(bodyDataJson, GpsMessageDataReq.class);
			protocolResponse=protocolParseBusinessService.handleObdDismantleAlertMessageReq(headerMessage,baseDataRequest);//存放原始消息，以便后续查证
		}else{
			shortMessageService.sendMessage(headerMessage.getVin(), headerMessage.getSerialNumber(), ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10007.getDesc());
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10007);
		}

		return protocolResponse;
	}
	
	private void handleTestDriveInfoNew(Vehicle vehicle, HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate){
//		boolean isExistDeclare=filingProposalService.hasApprovedFilingProposal(vehicle.getId(),headerMessage.getTimestamp());
		handleOriginalPoint(vehicle.getId(),baseDataRequest,headerMessage.getTypeCode());//优先解决经纬度为空情况
		saveLocation(vehicle.getId(),headerMessage,baseDataRequest,saveDate);//优先保存经纬度信息
		String msgDate = DateTime.getDate(DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATE),DateTime.PATTERN_DATE);
		Date messageTime=DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss");//报文上送时间，作为判定是否合法的时间点
		if(!msgDate.equals(DateTime.getDate(saveDate, DateTime.PATTERN_DATE))){
			//报文时间和当前时间不是同一天，那么做记录..
			protocolParseBusinessService.saveTaskLog(headerMessage, GlobalConstant.LOG_REQUEST_TIME_OUT);
		}
//		if(!isExistDeclare){
			if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_IGNITE_FLAMEOUT==headerMessage.getMessageCode()){
				//如果点火报文时间在当前时间之后，一般是设备时间异常，会引起试驾记录缓存中的开始时间大于后续时间正常的试驾记录变为历史记录，导致无法判定。
				long diffSeconds = DateTime.getSecondDifferenceNew(new Date(),messageTime);//与当前处理的试驾时间不匹配，说明是历史数据缓存在obd中，
				if( diffSeconds > 24* 60 * 60){//大于当前时间一天 暂时按这个来配置
					logger.info("vin:"+headerMessage.getVin()+"收到异常点火报文，点火熄火时间[" + headerMessage.getTimestamp() + "]，先不处理");
					throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"vin:"+headerMessage.getVin()+"收到异常点火报文，点火熄火时间[" + headerMessage.getTimestamp() + "]，先不处理");
				}
				
				boolean isCompanyPoint=isNearCompany(vehicle,baseDataRequest,headerMessage.getTypeCode());
				int fireState=baseDataRequest.getFireState();
				if(0==fireState && isCompanyPoint ){//点火 开始试驾
					logger.info("vin:"+headerMessage.getVin()+"试驾开始;时间:"+headerMessage.getTimestamp());
					handleFireOn(vehicle,headerMessage,baseDataRequest,saveDate);
					//saveTestDrive(vehicle,headerMessage,baseDataRequest,saveDate,-5);
				}else if(1==fireState){//熄火 本次试驾结束
					logger.info("vin:"+headerMessage.getVin()+"收到熄火报文;时间:"+headerMessage.getTimestamp());
					handleFireOut(vehicle,headerMessage,baseDataRequest,saveDate,isCompanyPoint);
				} else {
					logger.info("vin:"+headerMessage.getVin()+"判定为无效试驾;isCompanyPoint[" + isCompanyPoint + "]fireState" + fireState +"]时间:"+headerMessage.getTimestamp());
				}
			}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_UP_GSP==headerMessage.getMessageCode()){
				TestDrive testDrive=CacheTestDriveManager.getTestDriveByVehicleId(vehicle.getId(),headerMessage.getTypeCode(),headerMessage.getTimestamp());
				if(null==testDrive){
					logger.info("vin:"+headerMessage.getVin()+"无试驾开始");
					throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"vin:"+headerMessage.getVin()+"无试驾开始");
				}
				//增加对历史gps坐标信息的判定，如果上送时间戳小于试驾时间说明上送的是历史记录，暂时不做判定处理
				long diffSeconds=DateTime.getSecondDifferenceNew(testDrive.getStartTime(),messageTime);//与当前处理的试驾时间不匹配，说明是历史数据缓存在obd中，
				if(diffSeconds<0){//上报时间小于试驾开始时间
					logger.info("vin:"+headerMessage.getVin()+"收到历史数据，先不处理");
					throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"vin:"+headerMessage.getVin()+"，先不处理");
				}
				
				boolean isCompanyPoint=isNearCompany(vehicle,baseDataRequest,headerMessage.getTypeCode());
				//begin tangqingsong 2015/7/9 把判断线路逻辑后移到熄火时处理
				//begin tangqingsong 2015/9/15 违规判断只在工作时间内判断
				if(getWorkTime(headerMessage.getTimestamp())){
					int lineId = isCrossLineNew(vehicle,vehicle.getCompanyId(),baseDataRequest.getBaiduLng(),baseDataRequest.getBaiduLat(),headerMessage.getTypeCode(),testDrive);
					if(-2==lineId){//越界 
						//logger.info("没有确定线路情况下  vin:"+headerMessage.getVin()+"发生越界行为");
						saveTestDrive(vehicle,headerMessage,baseDataRequest,saveDate,-1,testDrive);
						return;
					}
				}
				
//				if(!isCompanyPoint){
					if(testDrive.getStatus()==null || testDrive.getStatus().equals(GlobalConstant.TEST_DRIVE_COMPANY_INNER)){
						testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_INVALID);
						//更新试驾对象
						CacheTestDriveManager.putTestDrive(testDrive.getVehicleId(), testDrive, headerMessage.getTypeCode());
					}
					List<GPSPoint> gpsPoints=CacheTestDriveManager.getTestDriveGpsPoint(vehicle.getId(),headerMessage.getTypeCode());
					int gpsBuffMaxSize = Integer.parseInt(GlobalConfig.getString("gps_point_buffer_max_size"));
					if(gpsPoints==null || gpsPoints.size()<gpsBuffMaxSize){
						//cache中的GPS点小于临界值，才保存
						CacheTestDriveManager.putTestDriveGpsPoint(vehicle.getId(), baseDataRequest.getBaiduLng(),baseDataRequest.getBaiduLat(),headerMessage.getTypeCode());
					}else{
						//cache中的GPS点是否已到临界值，触发 线路判断处理...
						refenceLineHandle(vehicle,headerMessage,baseDataRequest,saveDate,isCompanyPoint,testDrive);
						//更新试驾对象
						CacheTestDriveManager.putTestDrive(testDrive.getVehicleId(), testDrive, headerMessage.getTypeCode());
					}
//				}

				//end
			}else if(GlobalConstant.PROTOCOL_GATEWAY_MESSAGE_CODE_VEHICLE_ALARM==headerMessage.getMessageCode()){
				//处理报警信息
				saveAlert(vehicle,headerMessage,baseDataRequest,saveDate,null,null);
			}
//		}
	}
	
	/**
	 * 处理半圈点问题
	 * @param vehicle
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param lineId
	 * @param isCompanyPoint
	 * @param saveDate
	 */
	private void handleSemicyclePoint(Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Integer lineId,boolean isCompanyPoint,Date startTime,Date saveDate,TestDrive testDrive){
		if(DateTime.getSecondDifferenceNew(startTime, DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME))>=5 &&
				DateTime.getSecondDifferenceNew(startTime,DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME))<=2*3600  ){
			boolean isSemicyclePoint=isSemicyclePointNew(vehicle,baseDataRequest.getBaiduLng(),baseDataRequest.getBaiduLat(),lineId);
			if(isSemicyclePoint && (!isCompanyPoint)){//既在起点又在半圈点时作为起点处理  不能加1圈
				//logger.info("进入半圈点范围内，开始处理圈数");
				saveTestDrive(vehicle,headerMessage,baseDataRequest,saveDate,-3,testDrive);
			}
		}
	}
	
	/**
	 * 保存实时经纬度信息
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 * @return
	 */
	private void saveLocation(Integer vehicleId,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate){
		String speed=String.valueOf(baseDataRequest.getSpeed());
		if(null!=speed && speed.indexOf(".")>=1){
			speed=speed.substring(0, speed.indexOf("."));
		}
		Location location=new Location();
		location.setVehicleId(vehicleId);
		location.setSpeed(Integer.parseInt(speed));
		location.setLatitude(baseDataRequest.getBaiduLat());
		location.setLongitude(baseDataRequest.getBaiduLng());
		location.setOrignLatitude(null==baseDataRequest.getLat()  ? "0" : baseDataRequest.getLat());
		location.setOrignLongitude(null==baseDataRequest.getLng() ? "0" : baseDataRequest.getLng());
		
		location.setCreationTime(Long.parseLong(DateTime.stringDateFormat(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss","yyyyMMddHHmmss")));
		location.setSaveDate(saveDate);
		CacheTestDriveManager.putLocation(vehicleId, location,headerMessage.getTypeCode());
		locationDao.save(location);
		if(headerMessage.getTypeCode()!=null && !headerMessage.getTypeCode().isEmpty()){
			//重跑，不需要更新车辆位置
		}else{
			//4.保存到数据库表
			monitorService.saveOrUpdateMonitorLocation(location);
		}
	}
	
	/**
	 * 保存试驾信息
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 */
	private void saveTestDrive(Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate,Integer type,TestDrive testDrive){
		if(-5==type){//0 点火  且从坐标是4S店起点坐标  试驾开始
			if(null==testDrive){
				testDrive=new TestDrive();
				testDrive.setCompanyId(vehicle.getCompanyId());
				testDrive.setVehicleId(vehicle.getId());
				testDrive.setTestDriveDate(DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
				testDrive.setDepartmentId(vehicle.getCompanyId());
				testDrive.setStartTime(DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
				testDrive.setSaveDate(saveDate);
				testDrive.setLoopCount(0);
				String currentMil=String.valueOf(null==baseDataRequest.getKo3Kilometer() ? 0 :baseDataRequest.getKo3Kilometer() );
				Integer currentMilInt=Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(currentMil)*1000));
				testDrive.setMileage(currentMilInt);
				testDrive.setStatus(4);
				testDrive.setCurrentStatus(0);
				testDrive.setPointFlag(0);
				Integer testDriveId=testDriveService.saveTestDrive(testDrive);
				testDrive.setId(testDriveId);
				CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
			}
		}else if(-3==type){// 3  更新试驾圈数
			logger.info("vin:"+vehicle.getVin()+"到了半圈点范围内,GPS时间:"+headerMessage.getTimestamp()+";数据:("+baseDataRequest.getBaiduLng() +","+baseDataRequest.getBaiduLat()+"),前一个关键位置状态:"+testDrive.getPointFlag());
			if(null!=testDrive && null!=testDrive.getPointFlag() && 0==testDrive.getPointFlag()){
				logger.info("vin:"+vehicle.getVin()+"符合上一个关键位置状态0(起点处)，开始更新圈数并更新当前到达关键位置状态1(半圈点)");
				if(null!=testDrive.getLoopCount() && testDrive.getLoopCount()>=1){
					testDrive.setLoopCount(testDrive.getLoopCount()+1);
					testDrive.setPointFlag(1);
				}else{
					testDrive.setLoopCount(1);
					testDrive.setPointFlag(1);
				}
				testDriveService.updateTestDrive(testDrive);
				CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
			}
		}else if(-1==type){//-1 试驾进行中越界违规
			logger.info("vin:"+headerMessage.getVin()+"发生越界行为");
			if(null!=testDrive){
				//增加判定试驾开始时间、结束时间是否已提交过报备申请
				boolean hasFiling = filingProposalService.hasApprovedFilingProposal(vehicle.getId(),headerMessage.getTimestamp(), DateTime.toNormalDateTime(testDrive.getEndTime()),"0,1");
				testDrive.setStatus(2);
				if(hasFiling){
					testDrive.setCurrentStatus(3);
				}
				testDriveService.updateTestDrive(testDrive);
				CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
				saveViolation(vehicle,testDrive.getStartTime(),null,testDrive.getId(),GlobalConstant.LINE_VIOLATION_TYPE,1,1,hasFiling,headerMessage.getTypeCode());
				if(!hasFiling){
					saveAlert(vehicle,headerMessage,baseDataRequest,saveDate,null,"vin:"+headerMessage.getVin()+"发生越界行为");
					shortMessageService.sendMessage(headerMessage.getVin(), headerMessage.getSerialNumber(), "发生越界行为");
				}
			}
		}else if(-200==type){//更新试驾为无效试驾  前提是找到了试驾线路上的点
			logger.info("vin:"+vehicle.getVin()+"进入正常试驾中，线路暂时未知");
			if(null!=testDrive && 4==testDrive.getStatus()){
				testDrive.setStatus(0);
				testDriveService.updateTestDrive(testDrive);
				CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
			}
		}else if(0==type){//0 正常上传经纬度在经销商位置点附近  且上一个点在半圈点时进行更新
			logger.info("vin"+vehicle.getVin()+"回到了起点范围内,GPS时间:"+headerMessage.getTimestamp()+";数据:("+baseDataRequest.getBaiduLng() +","+baseDataRequest.getBaiduLat()+"),前一个关键位置状态:"+testDrive.getPointFlag());
			if(null!=testDrive && null!=testDrive.getPointFlag() && 1==testDrive.getPointFlag()){
				logger.info("vin:"+vehicle.getVin()+"符合上一个关键位置状态1(半圈处)，开始更新当前到达关键位置状态0(地点)");
				testDrive.setPointFlag(0);
				testDriveService.updateTestDrive(testDrive);
				CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
			}
		}else{//更新试驾线路ID
			//logger.info("正式进入更新试驾线路ID代码段中");
			if(null!=testDrive ){
				//logger.info("vin:"+vehicle.getVin()+"已确定出试驾线路ID:"+type+"并开始更新试驾线路id");
				DriveLine driveLine=CacheDriveLineManager.getDriveLineById(type);
				if(GlobalConstant.DRIVE_LINE_TYPE_OIL == driveLine.getType() && !testDrive.getStatus().equals(GlobalConstant.TEST_DRIVE_STATUS_UNLAW)){
					testDrive.setStatus(3);
				}else{
					if(null!=testDrive && 4==testDrive.getStatus()){
						testDrive.setStatus(0);
					}
				}
				testDrive.setLineId(type);
				testDriveService.updateTestDrive(testDrive);
				CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
				CacheTestDriveManager.removeTestDriveGpsPoint(vehicle.getId(),headerMessage.getTypeCode());//确定出试驾线路ID时  从缓存中清除掉缓存的GPS数据
			}
		}
	}
	
	/**
	 * 处理点火报文
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 */
	private void handleFireOn(Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate){
		TestDrive testDrive=CacheTestDriveManager.getTestDriveByVehicleId(vehicle.getId(),headerMessage.getTypeCode(),headerMessage.getTimestamp());
		if(null==testDrive){
			testDrive=new TestDrive();
			testDrive.setCompanyId(vehicle.getCompanyId());
			testDrive.setVehicleId(vehicle.getId());
			testDrive.setTestDriveDate(DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
			testDrive.setDepartmentId(vehicle.getCompanyId());
			testDrive.setStartTime(DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
			testDrive.setSaveDate(saveDate);
			testDrive.setLoopCount(0);
			String currentMil=String.valueOf(null==baseDataRequest.getKo3Kilometer() ? 0 :baseDataRequest.getKo3Kilometer() );
			Integer currentMilInt=Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(currentMil)*1000));
			testDrive.setMileage(currentMilInt);
			testDrive.setStatus(4);
			testDrive.setCurrentStatus(0);
			testDrive.setPointFlag(0);
			Integer testDriveId=testDriveService.saveTestDrive(testDrive);
			testDrive.setId(testDriveId);
			CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
		}else if(null!=testDrive && DateTime.getSecondDifferenceNew(testDrive.getStartTime(), DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME))>=5){
			//判断新的点火是否也在4S店内，并且距离上一个GPS或点火的时间相差3分钟内都不处理当前点火
			HashMap<String,Object> argMap = new HashMap<>();
			argMap.put("vin", headerMessage.getVin());
			argMap.put("startDate", DateTime.getDate(testDrive.getStartTime(), DateTime.PATTERN_DATETIME));
			argMap.put("endDate", headerMessage.getTimestamp());
			String lastDate = protocolMessageDao.findLastGpsDate(argMap);
			int beginInterval = Integer.parseInt(GlobalConfig.getString("drive_begin_interval"));
			if((lastDate!=null && !lastDate.isEmpty()) &&
					DateTime.getSecondDifferenceNew(DateTime.getDate(lastDate, DateTime.PATTERN_DATETIME)
					, DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME))<= beginInterval){
				return;
			}
			//logger.info("连续收到两个点火报文,中间无熄火报文;vin:"+headerMessage.getVin()+";第一个点火报文时间:"+DateTime.toNormalDateTime(testDrive.getStartTime())+";第二个点火报文时间:"+headerMessage.getTimestamp());
			testDrive.setCurrentStatus(2);
			//testDrive.setEndTime(DateTime.getDate(location.getCreationTime().toString(), DateTime.PATTERN_SHORTDATETIME));
			//处理结束点是否时间违规，有效状态下圈数是否为0
			testDriveService.updateTestDrive(testDrive);
			CacheTestDriveManager.removeTestDriveById(testDrive.getVehicleId(),headerMessage.getTypeCode());
			CacheTestDriveManager.removeTestDriveGpsPoint(testDrive.getVehicleId(),headerMessage.getTypeCode());
			CacheTestDriveManager.removeLineViolation(vehicle.getId(),headerMessage.getTypeCode());
			
			testDrive=new TestDrive();
			testDrive.setCompanyId(vehicle.getCompanyId());
			testDrive.setVehicleId(vehicle.getId());
			testDrive.setTestDriveDate(DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd"));
			testDrive.setDepartmentId(vehicle.getCompanyId());
			testDrive.setStartTime(DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss"));
			testDrive.setSaveDate(saveDate);
			testDrive.setLoopCount(0);
			String currentMil=String.valueOf(null==baseDataRequest.getKo3Kilometer() ? 0 :baseDataRequest.getKo3Kilometer() );
			Integer currentMilInt=Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(currentMil)*1000));
			testDrive.setMileage(currentMilInt);
			testDrive.setStatus(4);
			testDrive.setCurrentStatus(0);
			testDrive.setPointFlag(0);
			Integer testDriveId=testDriveService.saveTestDrive(testDrive);
			testDrive.setId(testDriveId);
			CacheTestDriveManager.putTestDrive(vehicle.getId(), testDrive,headerMessage.getTypeCode());
//			Location location=testDriveService.getLastLocation(null, " and creationTime>="+ DateTime.toShortDateTime(testDrive.getStartTime())+" and creationTime <"+DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME));
//			if(null!=location){
//
//			}
		}
	}
	
	/**
	 * 处理熄火报文
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 */
	private void handleFireOut(Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate,boolean isCompanyPoint){
		TestDrive testDrive=CacheTestDriveManager.getTestDriveByVehicleId(vehicle.getId(),headerMessage.getTypeCode(),headerMessage.getTimestamp());
		if(null!=testDrive){
			long differ = DateTime.getSecondDifferenceNew(testDrive.getStartTime(), DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME));
			if(differ>=1){//代表着是最近一对试乘试驾的熄火报文
				Date endTime=DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String currentMil=String.valueOf(null==baseDataRequest.getKo3Kilometer() ? 0 :baseDataRequest.getKo3Kilometer() );
				Integer currentMilInt=Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(currentMil)*1000));
				int mileage=currentMilInt.intValue()-testDrive.getMileage().intValue();
				List<GPSPoint> gpsPoints=CacheTestDriveManager.getTestDriveGpsPoint(vehicle.getId(),headerMessage.getTypeCode());
				//判断里程...
				int vaild_test_drive_mileage = Integer.parseInt(GlobalConfig.getString("valid_test_drive_mileage"));//设定的有效试驾最少里程数
				if((mileage<vaild_test_drive_mileage && headerMessage.getTypeCode()!=null)){
					mileage = getMileage(gpsPoints);
				}
				//为避免里程为负数，（生产上有碰到过熄火前 设备重置缓存，导致里程缓存清0，导致熄火报文里程小于点火报文里程，这种情况需要重跑）
				//add by wangchao 20160412 start
				if( mileage < 0 ){
					mileage = 0;
					//这种情况，需要记录重跑任务
					protocolParseBusinessService.saveTaskLog(headerMessage, GlobalConstant.LOG_REQUEST_TIME_OUT);
				}
				//add by wangchao 20160412 end
				long intevalTime=DateTime.getSecondDifferenceNew(testDrive.getStartTime(),endTime);
				if(isCompanyPoint || isNearCompanyExtend(vehicle,baseDataRequest,intevalTime,mileage,headerMessage.getTypeCode())){
					
					//4.0 增加判定试驾开始时间、结束时间是否已提交过报备申请
					boolean hasFiling = filingProposalService.hasApprovedFilingProposal(vehicle.getId(),DateTime.toNormalDateTime(testDrive.getStartTime()), DateTime.toNormalDateTime(testDrive.getEndTime()),"0,1");
					
					//begin tangqingsong 2015/7/8 增加熄火前判定试驾有效性的方法，以及线路判断处理
					boolean testDriveIsVaild = isTestDriveVaild(testDrive,gpsPoints,intevalTime,mileage,headerMessage);//校验试驾有效性
					//线路判断处理
					refenceLineHandle(vehicle,headerMessage,baseDataRequest,saveDate,isCompanyPoint,testDrive);
					//end
					//logger.info("vin:"+headerMessage.getVin()+"试驾结束; 300米范围内熄火:"+isCompanyPoint);
					setFinishTestDriveInfo(testDrive,vehicle,headerMessage,baseDataRequest,saveDate,mileage,intevalTime,endTime,1, hasFiling,testDriveIsVaild);
					testDriveService.updateTestDrive(testDrive);
					CacheTestDriveManager.removeTestDriveById(vehicle.getId(),headerMessage.getTypeCode());
					CacheTestDriveManager.removeTestDriveGpsPoint(vehicle.getId(),headerMessage.getTypeCode());//试驾结束时  从缓存中清除掉缓存的GPS数据(预防在试驾过程中没有确定出试驾线路)
				}
			}else{//代表着是可能是前一次一对试乘试驾的熄火报文(更新历史数据  不用清除)
				//logger.info("后续补传信息 vin:"+headerMessage.getVin()+" 试驾结束; 熄火报文时间:"+headerMessage.getTimestamp());
				Date endTime=DateTime.getDate(headerMessage.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				//先取出上一次试乘试驾信息出来  然后进行处理
				Map<String,Integer> map=new HashMap<String,Integer>();
				map.put("vehicleId", vehicle.getId());
				List<TestDrive> testDrives=testDriveService.getTestDriveList(map," and currentStatus=2 and startTime<'"+headerMessage.getTimestamp() + "' order by startTime desc limit 1");
				if(!(null==testDrives || testDrives.isEmpty())){
					TestDrive td=testDrives.get(0);
					if(null==td.getEndTime()){
						String currentMil=String.valueOf(null==baseDataRequest.getKo3Kilometer() ? 0 :baseDataRequest.getKo3Kilometer() );
						Integer currentMilInt=Integer.parseInt(new java.text.DecimalFormat("0").format(Double.parseDouble(currentMil)*1000));
						int mileage=currentMilInt.intValue()-td.getMileage().intValue();
						long intevalTime=DateTime.getSecondDifferenceNew(td.getStartTime(),endTime);
						//为避免里程为负数，（生产上有碰到过熄火前 设备重置缓存，导致里程缓存清0，导致熄火报文里程小于点火报文里程，这种情况需要重跑）
						//add by wangchao 20160412 start
						if( mileage < 0 ){
							mileage = 0;
							//这种情况，需要记录重跑任务
							protocolParseBusinessService.saveTaskLog(headerMessage, GlobalConstant.LOG_REQUEST_TIME_OUT);
						}
						//add by wangchao 20160412 end
						if(isCompanyPoint || isNearCompanyExtend(vehicle,baseDataRequest,intevalTime,mileage,headerMessage.getTypeCode())){
							boolean hasFiling = filingProposalService.hasApprovedFilingProposal(vehicle.getId(),DateTime.toNormalDateTime(td.getStartTime()),headerMessage.getTimestamp(),"0,1");
							//begin tangqingsong 2015/7/8 增加熄火前判定试驾有效性的方法，以及线路判断处理
//							List<GPSPoint> gpsPoints=CacheTestDriveManager.getTestDriveGpsPoint(vehicle.getId());
//							boolean testDriveIsVaild = isTestDriveVaild(testDrive,gpsPoints,intevalTime,mileage);//校验试驾有效性
							logger.info("后续补传信息 vin:"+headerMessage.getVin()+"试驾结束; 300米范围内熄火:"+isCompanyPoint);	
							setFinishTestDriveInfo(td,vehicle,headerMessage,baseDataRequest,saveDate,mileage,intevalTime,endTime,0, hasFiling,true);
							testDriveService.updateTestDrive(testDrive);
							handViolation(vehicle,testDrive,headerMessage,baseDataRequest, hasFiling);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据GPS点之间的距离计算距离
	 * @author tangqingsong
	 * @version 
	 * @param gpsPoints
	 * @return
	 */
	private int getMileage(List<GPSPoint> gpsPoints){
		if(gpsPoints==null || gpsPoints.size()<2){
			return 0;
		}
		int mileage = 0;
		GPSPoint prevGps = gpsPoints.get(0);
		for(int i=1;i<gpsPoints.size();i++){
			GPSPoint gPSPoint = gpsPoints.get(i);
			//计算当前点和上一个点的距离
			double distance =GisUtil.getDistance(gPSPoint.getLat(),gPSPoint.getLng(),prevGps.getLat(),prevGps.getLng());
			mileage+=distance;
			prevGps = gPSPoint;
		}
		return mileage;
	}
	/**
	 * 
	 * 2015/7/9 判断线路处理
	 *
	 * @author tangqingsong
	 * @version 
	 * @param vehicle
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 * @param isCompanyPoint
	 */
	private void refenceLineHandle(Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate,boolean isCompanyPoint,TestDrive testDrive){
		List<GPSPoint> gpsPoints=CacheTestDriveManager.getTestDriveGpsPoint(vehicle.getId(),headerMessage.getTypeCode());
		if(gpsPoints!=null && testDrive.getLineId()==null){
			int refenceLineId = getRefenceLineBySemiPoint(testDrive,gpsPoints, vehicle.getCompanyId(),baseDataRequest);
			logger.info("vin:"+headerMessage.getVin()+",vehicleId:"+vehicle.getId()+",companyId:"+vehicle.getCompanyId()+",使用半圈点来判断线路为："+refenceLineId);
			if(refenceLineId==0 && (null==testDrive.getStatus() || 4==testDrive.getStatus())){//一旦确定不是在4S店范围内移动时  不再更新非4S店内移动标识
				//logger.info("判定试驾线路获得ID 但是属于公共交叉的");
				saveTestDrive(vehicle,headerMessage,baseDataRequest,saveDate,-200,testDrive);//更新试驾息的线路ID
			}else if(refenceLineId>=1){//根据试驾线路确定出所用试驾线路(隐患就是把加油线路也包含在内)
				//logger.info("判定试驾线路获得唯一试驾线路ID并准备更新试驾线路ID:"+refenceLineId);
				saveTestDrive(vehicle,headerMessage,baseDataRequest,saveDate,refenceLineId,testDrive);//更新试驾息的线路ID					
				handleSemicyclePoint(vehicle,headerMessage,baseDataRequest,refenceLineId,isCompanyPoint,testDrive.getStartTime(),saveDate,testDrive);
			}else{
				int lineId=isCrossLineNew(vehicle,vehicle.getCompanyId(),baseDataRequest.getBaiduLng(),baseDataRequest.getBaiduLat(),headerMessage.getTypeCode(),testDrive);
				if(lineId>=1){//获得线路  并且更新试驾、违规线路ID
					//logger.info("根据围栏获得试驾线路ID");
					saveTestDrive(vehicle,headerMessage,baseDataRequest,saveDate,lineId,testDrive);//更新试驾息的线路ID
					handleSemicyclePoint(vehicle,headerMessage,baseDataRequest,lineId,isCompanyPoint,testDrive.getStartTime(),saveDate,testDrive);
				}
			}
		}
	}
	
	/**
	 * 
	 * 2015/7/8 增加熄火前判定试驾有效性的方法
	 * @author tangqingsong
	 * @version 
	 * @param gpsPoints
	 * @param intevalTime
	 * @param mileage
	 * @return
	 */
	private boolean isTestDriveVaild(TestDrive testDrive,List<GPSPoint> gpsPoints,long intevalTime,int mileage,HeaderMessage headerMessage){
		int valid_test_drvie_time = Integer.parseInt(GlobalConfig.getString("valid_test_drvie_time"));
		int valid_test_drive_max_mileage = Integer.parseInt(GlobalConfig.getString("valid_test_drive_max_mileage"));
		//取出点火和熄火之间所有的GPS坐标点
		if(intevalTime>valid_test_drvie_time){
			//2)、试驾耗时超过最大设定的上限（初值定为3600秒），算作无效
			return false;
		}else if(mileage>valid_test_drive_max_mileage){
			//3)、试驾里程超过设定的最大上限（初值定为100公里），算无效
			return false;
		}else if((gpsPoints==null || gpsPoints.isEmpty()) && testDrive.getLineId()==null ){
			//1)、点火熄火时间区域中间如果无gps坐标点上送但里程大于2.5公里算有效，但告警
			int vaild_test_drive_mileage = Integer.parseInt(GlobalConfig.getString("valid_test_drive_mileage"));//设定的有效试驾最少里程数
			if(mileage>=vaild_test_drive_mileage){
				if(headerMessage.getTypeCode()==null){
					headerMessage.setStartTime(testDrive.getStartTime());
					protocolParseBusinessService.saveTaskLog(headerMessage, GlobalConstant.LOG_DEFECT_GPS);
				}
				return true;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 合法熄火报文接收后，触发对试驾做最后判定。
	 * @param testDrive 缓存的试驾信息
	 * @param vehicle 车辆信息
	 * @param headerMessage 上送报文头
	 * @param baseDataRequest
	 * @param saveDate 接收到报文的开始时间（系统时间）
	 * @param mileage 里程数
	 * @param intevalTime 试驾持续时间
	 * @param endTime
	 * @param flag
	 * @param hasFiling 是否存在报备 申请，true 存在；false 不存在、
	 */
	private void setFinishTestDriveInfo(TestDrive testDrive,Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate,int mileage,long intevalTime,Date endTime,Integer flag, boolean hasFiling,boolean testDriveIsVaild){
		testDrive.setIntevalTime(intevalTime);
		testDrive.setEndTime(endTime);
		testDrive.setMileage(mileage);
		Integer currentLoopCount= null==testDrive.getLoopCount() ? 0 : testDrive.getLoopCount();
		logger.info("testDrive  status:"+testDrive.getStatus()+";vin:"+vehicle.getVin()+";lineId:"+testDrive.getLineId()+";里程:"+testDrive.getMileage()+";圈数:"+testDrive.getLoopCount());
		if(null==testDrive.getStatus() || 4==testDrive.getStatus()){
			DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(vehicle.getCompanyId());
			DriveLineVO driveLine=null;
			if(!(null==driveLineWithPolygon || null==driveLineWithPolygon.getLines() || driveLineWithPolygon.getLines().isEmpty())){
				driveLine=driveLineWithPolygon.getLines().get(0);
				logger.info("testDrive  status:4;vin:"+vehicle.getVin()+";lineId:"+driveLine.getId());
				testDrive.setLineId(driveLine.getId());
			}
			//里程大于等于试驾里程 就算一次有效试驾
			if(isAnEffectiveTestDrive(testDrive.getMileage().intValue()) && null!=driveLine){
				currentLoopCount = caclulationLoopNum(testDrive.getMileage().intValue(), driveLine);
				if( currentLoopCount > 3){//封顶算3次试驾
					currentLoopCount = 3;
				}
			}
		}else if(0==testDrive.getStatus() || 1==testDrive.getStatus() || 2==testDrive.getStatus()){
			DriveLineVO driveLine=null;
			if(null==testDrive.getLineId()){
				DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(vehicle.getCompanyId());
//				DriveLineVO driveLine=null;
				if(!(null==driveLineWithPolygon || null==driveLineWithPolygon.getLines() || driveLineWithPolygon.getLines().isEmpty())){
					driveLine=driveLineWithPolygon.getLines().get(0);
					logger.info("testDrive  status:"+testDrive.getStatus()+";vin:"+vehicle.getVin()+";lineId:"+driveLine.getId());
					testDrive.setLineId(driveLine.getId());
				}
			}else{
				DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(vehicle.getCompanyId());
//				DriveLineVO driveLine=null;
				if(!(null==driveLineWithPolygon || null==driveLineWithPolygon.getLines() || driveLineWithPolygon.getLines().isEmpty())){
					for(DriveLineVO dLine: driveLineWithPolygon.getLines()){
						if(dLine.getId().intValue()==testDrive.getLineId().intValue()){
							driveLine=dLine;
							break;
						}
					}
					if(null==driveLine ){
						driveLine=driveLineWithPolygon.getLines().get(0);
						logger.info("有线路情况下 但线路不正确情况 testDrive  status:"+testDrive.getStatus()+";vin:"+vehicle.getVin()+";lineId:"+driveLine.getId());
						testDrive.setLineId(driveLine.getId());
					}
				}
			}
			//判断是否经过半圈点，或里程数大于4500米；两个条件满足一条就算一次试驾
			if(testDrive.getLoopCount() > 0 || isAnEffectiveTestDrive(testDrive.getMileage().intValue()) && null!=driveLine){
				currentLoopCount = 1;
				if(isAnEffectiveTestDrive(testDrive.getMileage().intValue())){//里程超过4500米计算次数
					currentLoopCount = caclulationLoopNum(testDrive.getMileage().intValue(), driveLine);
				}
				if( currentLoopCount > 3){//封顶算3次试驾
					currentLoopCount = 3;
				}
			}
		}
		if(null==testDrive.getStatus()){//无状态
			if(testDrive.getMileage()>=500 && testDrive.getMileage() < 4000){
				currentLoopCount=0;
				testDrive.setStatus(0);
			}
		}else if(2==testDrive.getStatus()){
			saveViolation(vehicle,testDrive.getStartTime(),endTime,testDrive.getId(),GlobalConstant.LINE_VIOLATION_TYPE,0,flag,hasFiling,headerMessage.getTypeCode());
		}else if(3==testDrive.getStatus()){
			testDrive.setStatus(0);
		}else {
			if(!(4==testDrive.getStatus() && testDrive.getMileage()<=499)){//4S店范围内如果移动500m后记为无效试驾数据
				if(( 0==testDrive.getStatus() || 4==testDrive.getStatus() ) && currentLoopCount.intValue()>=1){
					testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_VALID);
					if(!testDriveIsVaild){
						testDrive.setLoopCount(0);
						testDrive.setStatus(GlobalConstant.TEST_DRIVE_STATUS_INVALID);//做无效试驾
					}
				}
			}
		}
		//logger.info(";圈数:"+currentLoopCount+";status:"+testDrive.getStatus());
		testDrive.setLoopCount(currentLoopCount);
		testDrive.setCurrentStatus(1);
		testDrive.setPointFlag(0);
		if(hasFiling){//存在报备申请，就修改试驾记录为报备记录状态
			testDrive.setCurrentStatus(3);
		}
		
		//begin tangqingsong 2015/7/27 更改，如果里程小于500M，无效试驾更改为4S店内试驾
		if((testDrive.getStatus()==null || testDrive.getStatus().equals(GlobalConstant.TEST_DRIVE_STATUS_INVALID)) && testDrive.getMileage()<=500){
			testDrive.setStatus(GlobalConstant.TEST_DRIVE_COMPANY_INNER);//判断为店内试驾
		}
		//end
		
		//begin tangqingsong 2015/9/15 更改，晚上22:00-次日早上7:00的隐藏试驾
		if(getNotWorkTime(headerMessage.getTimestamp())){
			testDrive.setCurrentStatus(GlobalConstant.TEST_DRIVE_CURRENT_STATUS_NOT_WORK_TIME);
		}
		//end
		//modfiy wangchao 2016/03/30 24小时内，有效试驾都算有效，不隐藏有效试驾数据。
		if(1 == testDrive.getStatus()){
			testDrive.setCurrentStatus(1);
		}
		
		/**如果车辆是退役状态，隐藏试驾违规记录信息*/
		if(GlobalConstant.CONFIGURE_STATUS_UNINSTALLED == vehicle.getConfigureStatus() && 2 == testDrive.getStatus()){
			testDrive.setCurrentStatus(4);
		}

	}
	
    /**
     * 计算试驾次数
     *
     * @author wangchao
     * @version 
     * @param totalMileage
     * @return 默认返回1，计算实际试驾里程数与线路总里程数的倍数关系。
     */
    private int caclulationLoopNum(int totalMileage, DriveLineVO driveLine){
    	if( null==driveLine || null==driveLine.getMileage() || 0 == driveLine.getMileage().intValue()){
    		return 0;
    	}
    	int vaild_test_drive_mileage = Integer.parseInt(GlobalConfig.getString("valid_test_drive_mileage"));//设定的有效试驾最少里程数
    	int driveLineLen=null==driveLine.getMileage() ? vaild_test_drive_mileage :driveLine.getMileage().intValue();//线路设定里程数
    	if( totalMileage >= driveLineLen){
    		return totalMileage/driveLineLen;
    	}
    	return 1;
    }
    
    /**
     * 判断是否里程超过了有效试驾距离
     * @param totalMileage 试驾里程数
     * @return
     */
    private boolean isAnEffectiveTestDrive( int totalMileage){
    	int vaild_test_drive_mileage = Integer.parseInt(GlobalConfig.getString("valid_test_drive_mileage"));
    	if( totalMileage >= vaild_test_drive_mileage){
    		return true;
    	}
    	return false;
    }
    
	/**
	 * 处理连续两个点火报文事宜判断违规情况
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 * @param type
	 *  @param endPointFlag
	 *  0终点 如果存在线路违规则更新试驾结束时间并从缓存清除掉  没有不处理
	 *  1中间点  如果没有则新建,存在更新违规次数 
	 * 1越界违规  2非工作时间违规  其它线路id
	 */
	private void handViolation(Vehicle vehicle,TestDrive testDrive,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest, boolean hasFiling){
		if(null!=testDrive.getStatus() && 2==testDrive.getStatus()){
			String startTime=DateTime.toNormalDate(testDrive.getStartTime());	
			violationService.handleNoEndViolation(vehicle.getId(),startTime,headerMessage.getTimestamp(), hasFiling);
		}else if(null!=testDrive.getStatus() && (0==testDrive.getStatus() || 1==testDrive.getStatus()) ){
//			boolean workTime=true;
//			workTime=getWorkTime(vehicle.getCompanyId(),headerMessage.getTimestamp());//根据试驾开始时间判定是否非工作违规
//			if(!workTime){//非工作时间违规
//				logger.info("补传熄火报文 vin:"+headerMessage.getVin()+"非工作时间违规");
//				Violation violation=new Violation();
//				violation=new Violation();
//				violation.setTypeId(GlobalConstant.TIME_VIOLATION_TYPE);
//				violation.setCountId(1);
//				violation.setCurrentStatus(1);
//				violation.setVehicleId(vehicle.getId());
//				violation.setStart_Time(testDrive.getStartTime());
//				violation.setEnd_Time(DateTime.toTimestamp(headerMessage.getTimestamp()));
//				violation.setCompanyId(vehicle.getCompanyId());
//				violation.setDepartmentId(vehicle.getCompanyId());
//				violation.setDriveLineId(testDrive.getId());
//				if(hasFiling){//存在报备申请就更新为删除状态
//					violation.setDeletionFlag(1);
//				}else{
//					violation.setDeletionFlag(0);
//				}
//				violation.setCreationTime(testDrive.getStartTime());
//				violationService.saveViolation(violation);
//				if(!hasFiling){
//					saveAlert(vehicle,headerMessage,baseDataRequest,testDrive.getStartTime(),null,"vin:"+headerMessage.getVin()+"非工作时间违规");
//					shortMessageService.sendMessage(headerMessage.getVin(), headerMessage.getSerialNumber(), "非工作时间违规");
//				}
//			}
		}		
	 }
	
	/**
	 * 保存违规信息
	 * @param headerMessage
	 * @param baseDataRequest
	 * @param saveDate
	 * @param type
	 *  @param endPointFlag  
	 *  0终点 如果存在线路违规则更新试驾结束时间并从缓存清除掉  没有不处理    
	 *  1中间点  如果没有则新建,存在更新违规次数 
	 * 1越界违规  2非工作时间违规  其它线路id
	 */
	private void saveViolation(Vehicle vehicle,Date startDate,Date endDate,Integer testDriveId,Integer type,Integer endPointFlag,Integer flag, boolean hasFiling,
								String typeCode){
		if(1==flag){
			if(GlobalConstant.LINE_VIOLATION_TYPE==type.intValue()){//结束试驾时若有线路违规就不记录时间违规  线路违规在终点时更新结束时间及状态
				//Violation violationCache=CacheTestDriveManager.getLineViolationByVehicleId(vehicle.getId());
				Violation violationCache=CacheTestDriveManager.getLineViolationByVehicleId(vehicle.getId(),startDate,typeCode);
				//0终点 如果存在线路违规则更新试驾结束时间并从缓存清除掉  没有不处理
				//1中间点  如果没有则新建,存在更新违规次数 
				//logger.info("vin:"+vehicle.getVin()+"进入越界违规处理方法");
				if(null!=violationCache && 0==endPointFlag){//结束试驾时判断存在违规则更新结束时间
					//logger.info("vin:"+vehicle.getVin()+"终点时处理越界违规--更新完结状态");
					violationCache.setEnd_Time(endDate);
					violationCache.setCurrentStatus(1);
					if(hasFiling || GlobalConstant.CONFIGURE_STATUS_UNINSTALLED == vehicle.getConfigureStatus()){//存在报备，修改为删除状态 或者车辆已退役
						violationCache.setDeletionFlag(1);
					}
					violationService.updateViolation(violationCache);
					CacheTestDriveManager.removeLineViolation(vehicle.getId(),typeCode);
				}else if(null==violationCache && 1==endPointFlag){//试驾过程中若没有违规则新建违规，否则更新违规次数
					//logger.info("vin:"+vehicle.getVin()+"进入越界违规处理方法--保存");
					Violation violation=new Violation();
					violation.setTypeId(GlobalConstant.LINE_VIOLATION_TYPE);
					violation.setCountId(1);
					violation.setCurrentStatus(0);
					violation.setVehicleId(vehicle.getId());
					violation.setStart_Time(startDate);
					violation.setCreationTime(startDate);
					violation.setCompanyId(vehicle.getCompanyId());
					violation.setDepartmentId(vehicle.getCompanyId());
					violation.setDriveLineId(testDriveId);
					if(hasFiling || GlobalConstant.CONFIGURE_STATUS_UNINSTALLED == vehicle.getConfigureStatus()){//存在报备，初始为删除状态 或者车辆已退役
						violation.setDeletionFlag(1);
					}else{
						violation.setDeletionFlag(0);
					}
					Integer violationId=violationService.saveViolation(violation);
					violation.setId(violationId);
					CacheTestDriveManager.putViolation(vehicle.getId(), violation, GlobalConstant.LINE_VIOLATION_TYPE,typeCode);
				}else if(null!=violationCache && 1==endPointFlag){
					//logger.info("vin:"+vehicle.getVin()+"更新越界违规次数");
					violationCache.setCountId(violationCache.getCountId()+1);
					if(hasFiling || GlobalConstant.CONFIGURE_STATUS_UNINSTALLED == vehicle.getConfigureStatus()){//存在报备，修改为删除状态 或者车辆已退役
						violationCache.setDeletionFlag(1);
					}
					violationService.updateViolation(violationCache);
					CacheTestDriveManager.putViolation(vehicle.getId(), violationCache, GlobalConstant.LINE_VIOLATION_TYPE,typeCode);
				}
			}else if(GlobalConstant.TIME_VIOLATION_TYPE==type.intValue()){//时间违规只在结束试驾时才判断  此时可以直接取出试驾ID  开始、结束时间
				Violation violation=new Violation();
				violation=new Violation();
				violation.setTypeId(GlobalConstant.TIME_VIOLATION_TYPE);
				violation.setCountId(1);
				violation.setCurrentStatus(1);
				violation.setVehicleId(vehicle.getId());
				violation.setStart_Time(startDate);
				violation.setEnd_Time(endDate);
				violation.setCompanyId(vehicle.getCompanyId());
				violation.setDepartmentId(vehicle.getCompanyId());
				violation.setDriveLineId(testDriveId);
				if(hasFiling || GlobalConstant.CONFIGURE_STATUS_UNINSTALLED == vehicle.getConfigureStatus()){//存在报备，初始为删除状态 或者车辆已退役
					violation.setDeletionFlag(1);
				}else{
					violation.setDeletionFlag(0);
				}
				violation.setCreationTime(endDate);
				violationService.saveViolation(violation);
			}
		}
	 }
	private void saveAlert(Vehicle vehicle,HeaderMessage headerMessage,GpsMessageDataReq baseDataRequest,Date saveDate,Integer alertType,String content){
		Alert alert=new Alert();
		alert.setCreationTime(DateTime.toShortDateTimeL(saveDate));
		alert.setEventId(-1);
		alert.setReaded(0);
		alert.setToUserId(-1);
		alert.setVehicleId(vehicle.getId());
		/**
		if(null!=alertType && alertType.intValue()==1){
			//非工作时间报警
			alert.setType(1);
			alert.setContent("非工作报警");
			alert.setRank(GlobalConstant.ALERT_TIME);
			alertService.saveAlert(alert);
		}else if(null!=alertType && alertType.intValue()==2){
			//越界报警
			alert.setType(1);
			alert.setContent("越界报警");
			alert.setRank(GlobalConstant.ALERT_LINE);
			alertService.saveAlert(alert);
		}
		*/
		if(null!=baseDataRequest.getStorigeBatt()){
			alert.setType(1);
			alert.setContent("断电报警");
			alert.setRank(GlobalConstant.ALERT_STORIGE_BATT);
			alertService.saveAlert(alert);
		}
		if(null!=baseDataRequest.getBskDefLampe()){
			alert.setType(1);
			alert.setContent("灯缺失报警");
			alert.setRank(GlobalConstant.ALERT_BSK_LAMPE);
			alertService.saveAlert(alert);
		}
		if(null!=baseDataRequest.getKo1Kuehlmittel()){
			alert.setType(1);
			alert.setContent("冷却液液位报警");
			alert.setRank(GlobalConstant.ALERT_KUEH_MITTEL);
			alertService.saveAlert(alert);
		}
		if(null!=baseDataRequest.getMo5Heissl()){
			alert.setType(1);
			alert.setContent("冷却液温度报警");
			alert.setRank(GlobalConstant.ALERT_HEISSL);
			alertService.saveAlert(alert);
		}
		if(null!=baseDataRequest.getKo1WaschWasser()){
			alert.setType(1);
			alert.setContent("风窗清洗液缺少报警");
			alert.setRank(GlobalConstant.ALERT_WASCH_WASSER);
			alertService.saveAlert(alert);
		}
		if(null!=baseDataRequest.getStorigeBatt()){
			alert.setType(1);
			alert.setContent("机油报警");
			alert.setRank(GlobalConstant.ALERT_STA_OELDR);
			alertService.saveAlert(alert);
		}
	}
	
	private DriveLineWithPolygonVO getTestDrivingLineNew(Integer dealerId){
		DriveLineWithPolygonVO driveLineWithPolygon=CacheTestDriveManager.getDriveLineWithPolygonByDealerIdNew(dealerId);
		return driveLineWithPolygon;
	}
		
	private boolean isNearCompany(Vehicle vehicle,GpsMessageDataReq baseDataRequest,String typeCode){
		String currentLng="",currentLat="";//上传的位置信息
		String companyLng="",companyLat="";//经销商位置信息
		
		if(null==baseDataRequest.getBaiduLng()
				|| "".equals(baseDataRequest.getBaiduLng())
				|| null==baseDataRequest.getBaiduLat() 
				|| "".equals(baseDataRequest.getBaiduLat())
				|| "0".equals(baseDataRequest.getBaiduLng())
				|| "0".equals(baseDataRequest.getBaiduLat())){//预防点火熄火时经纬度采集不到的情况
			Location currentLocation=CacheTestDriveManager.getLocationByVehicleId(vehicle.getId(),typeCode);
			currentLng=currentLocation.getLongitude();
			currentLat=currentLocation.getLatitude();
		}else{
			currentLng=baseDataRequest.getBaiduLng();
			currentLat=baseDataRequest.getBaiduLat();
		}
		Company company=CacheCompanyManager.getCompany(vehicle.getCompanyId());
		
		if(null!=company){
			companyLng=null==company.getBaiduLng() ? null :String.valueOf(company.getBaiduLng());
			companyLat=null==company.getBaiduLat() ? null :String.valueOf(company.getBaiduLat());
		}
		if(null==currentLng || null==currentLat || "".equals(currentLng.trim()) || "".equals(currentLat.trim())){
			logger.info("点火/熄火时没有GPS位置信息");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"点火/熄火时没有GPS位置信息");
		}
		if(null==companyLng || null==companyLat || "".equals(companyLng.trim()) || "".equals(companyLat.trim())){
			logger.info("没有设置经销商位置");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"没有设置经销商位置");
		}
		Boolean isCompanyPoint = isCompanyPoint(currentLng,currentLat,companyLng,companyLat);
		if (!isCompanyPoint) {
			logger.info("非靠近经销商范围，当前坐标currentLng[" + currentLng + "];currentLat[" + currentLat + "];经销商坐标companyLng[" + companyLng + "];companyLat[" + companyLat + "]");
		}
		return isCompanyPoint;
	}
	
	/*private boolean isReturnNearCompany(Vehicle vehicle,GpsMessageDataReq baseDataRequest){
		String currentLng="",currentLat="";//上传的位置信息
		String companyLng="",companyLat="";//经销商位置信息
		Location currentLocation=CacheTestDriveManager.getLocationByVehicleId(vehicle.getId());
		if(null==baseDataRequest.getBaiduLng()
				|| "".equals(baseDataRequest.getBaiduLng())
				|| null==baseDataRequest.getBaiduLat() 
				|| "".equals(baseDataRequest.getBaiduLat())
				|| "0".equals(baseDataRequest.getBaiduLng())
				|| "0".equals(baseDataRequest.getBaiduLat())){//预防点火熄火时经纬度采集不到的情况
			currentLng=currentLocation.getLongitude();
			currentLat=currentLocation.getLatitude();
		}else{
			currentLng=baseDataRequest.getBaiduLng();
			currentLat=baseDataRequest.getBaiduLat();
		}
		Company company=CacheCompanyManager.getCompany(vehicle.getCompanyId());
		
		if(null!=company){
			companyLng=null==company.getBaiduLng() ? null :String.valueOf(company.getBaiduLng());
			companyLat=null==company.getBaiduLat() ? null :String.valueOf(company.getBaiduLat());
		}
		if(null==currentLng || null==currentLat || "".equals(currentLng.trim()) || "".equals(currentLat.trim())){
			logger.info("点火/熄火时没有GPS位置信息");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"点火/熄火时没有GPS位置信息");
		}
		if(null==companyLng || null==companyLat || "".equals(companyLng.trim()) || "".equals(companyLat.trim())){
			logger.info("没有设置经销商位置");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"没有设置经销商位置");
		}
		return isReturnCompanyPoint(currentLng,currentLat,companyLng,companyLat);
	}*/
	
	private boolean isNearCompanyExtend(Vehicle vehicle,GpsMessageDataReq baseDataRequest,long intevalTime,int mileage,String typeCode){
		String currentLng="",currentLat="";//上传的位置信息
		String companyLng="",companyLat="";//经销商位置信息
		
		if(null==baseDataRequest.getBaiduLng()
				|| "".equals(baseDataRequest.getBaiduLng())
				|| null==baseDataRequest.getBaiduLat() 
				|| "".equals(baseDataRequest.getBaiduLat())
				|| "0".equals(baseDataRequest.getBaiduLng())
				|| "0".equals(baseDataRequest.getBaiduLat())){//预防点火熄火时经纬度采集不到的情况
			Location currentLocation=CacheTestDriveManager.getLocationByVehicleId(vehicle.getId(),typeCode);
			currentLng=currentLocation.getLongitude();
			currentLat=currentLocation.getLatitude();
		}else{
			currentLng=baseDataRequest.getBaiduLng();
			currentLat=baseDataRequest.getBaiduLat();
		}
		Company company=CacheCompanyManager.getCompany(vehicle.getCompanyId());
		
		if(null!=company){
			companyLng=null==company.getBaiduLng() ? null :String.valueOf(company.getBaiduLng());
			companyLat=null==company.getBaiduLat() ? null :String.valueOf(company.getBaiduLat());
		}
		if(null==currentLng || null==currentLat || "".equals(currentLng.trim()) || "".equals(currentLat.trim())){
			logger.info("点火/熄火时没有GPS位置信息");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"点火/熄火时没有GPS位置信息");
		}
		if(null==companyLng || null==companyLat || "".equals(companyLng.trim()) || "".equals(companyLat.trim())){
			logger.info("没有设置经销商位置");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"没有设置经销商位置");
		}
		logger.info("vin:"+vehicle.getVin()+"320米无法满足试驾结束条件，进入400M判断试驾结束条件");
		return isCompanyPointExtend(currentLng,currentLat,companyLng,companyLat,intevalTime,mileage);
	}
		
	/**
	 * 判断是否在工作时间范围内
	 * 在 true
	 * 否 false
	 * @param messageDate
	 * @return
	 */
	private boolean getWorkTime(String messageDate){
		Date workDate=DateTime.getDate(messageDate, "yyyy-MM-dd HH:mm:ss");
		int hour = DateTime.getHour(workDate);
		if((hour>=9)&&(hour<18)){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否在非工作时间范围内
	 * 在 true
	 * 否 false
	 * @param messageDate
	 * @return
	 */
	private boolean getNotWorkTime(String messageDate){
		Date workDate=DateTime.getDate(messageDate, "yyyy-MM-dd HH:mm:ss");
		int hour = DateTime.getHour(workDate);
		//非工作时间区间 ： 22:00-次日早上7:00
		if((hour>=22 && hour<=23) || (hour>=0 && hour<7)){
			return true;
		}
		return false;
	}
	/**
	 * 头部消息解析正常并通过校验构造返回消息
	 * @param header
	 * @param bodyResp
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map genResponseMessage(HeaderMessage header,ProtocolParseRes bodyResp){
		Map map=new HashMap();
		map.put("header", header);
		map.put("body", bodyResp);
		return map;
	}
	
	/**
	 * 根据半圈点判断线路
	 * @author tangqingsong
	 * @version 
	 * @param gpsPoints
	 * @param dealerId
	 * @return
	 */
	private int getRefenceLineBySemiPoint(TestDrive testDrive,List<GPSPoint> gpsPoints,Integer dealerId,GpsMessageDataReq baseDataRequest){
		Map<Integer,Integer> results = new HashMap<Integer,Integer>();//统计结果
		int gpsPointSize = gpsPoints.size();
		if( gpsPointSize < 2 ){//gps坐标点小于3个，无法生成多边形，GisUtil.createPolygon会抛出异常。无法判断是否经过了半圈点。一般这种情况里程都不够
			return -1;//点不够，不作判断
		}
		//获取参考线宽度
		String refenceLineDistince = GlobalConfig.getString("valid_refence_line_distince");
		//读取经销商位置
		Company company=CacheCompanyManager.getCompany(dealerId);
		if(null==company.getBaiduLat() || null==company.getBaiduLng()){
			logger.info("没有设置经销商位置");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"没有设置经销商位置");
		}		
		Map<Integer,Integer> lineTypeMap = new HashMap<Integer,Integer>();//保存线路类型
		try{
			//1、把试驾GPS移动坐标转为多边形
			List<GPSPoint> tempPoints = new ArrayList<GPSPoint>();
			GPSPoint startPoint = new GPSPoint(Double.parseDouble(baseDataRequest.getBaiduLng()),Double.parseDouble(baseDataRequest.getBaiduLat()));
			tempPoints.add(startPoint);//多边形的起始和结束位置必须一致
			
			for(int i=0;i<gpsPoints.size();i++){
				tempPoints.add(gpsPoints.get(i));
			}
			GPSPoint endPoint = new GPSPoint(Double.parseDouble(baseDataRequest.getBaiduLng()),Double.parseDouble(baseDataRequest.getBaiduLat()));
			tempPoints.add(endPoint);//多边形的起始和结束位置必须一致
			Polygon gpsPolygon=GisUtil.createPolygon(tempPoints);//开始绘制
			//2、遍历所有线路，判断GPS坐标是否有经过半圈点，如果经过并计算落在此线路上的有效GPS数量； 遍历所有线路半圈点，把半圈点转换为多边形并判断GPS坐标多边形是否和半圈点相交，如果相交权值加1
			DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(dealerId);
			if(null==driveLineWithPolygon || null==driveLineWithPolygon.getLines() || driveLineWithPolygon.getLines().isEmpty())
				throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"test driving line is null");
			Map<Integer,Integer> notIntersectSemiMap = new HashMap<Integer,Integer>();//不经过半圈点的线路 
			List<DriveLineVO> lines=driveLineWithPolygon.getLines();
			for(int n=0;n<lines.size();n++){
				DriveLineVO driveLine = lines.get(n);
				if(driveLine.getSemicyclePoint()!=null && !driveLine.getSemicyclePoint().isEmpty()){
					//把半圈点向东南西北扩大50米变成多边形
					int semiMaxLength = Integer.parseInt(GlobalConfig.getString("semi_point_extend_max_length"));
					Polygon semiPolygon = GisUtil.createPolygon(driveLine.getSemicyclePoint(), semiMaxLength);
					//计算GPS点落在线路的数量
					int validPointCount = 0;
					for(int i=0;i<gpsPoints.size();i++){
						GPSPoint gpsPoint = gpsPoints.get(i);
						boolean ok=GpsUtil.bufferJudgeNoCompress(gpsPoint,driveLine.getReferenceLPoints(),refenceLineDistince,String.valueOf(company.getBaiduLat()));
						if(ok){
							validPointCount++;
						}
					}
					//判断试驾GPS多边形是否和半圈点相交
					if(gpsPolygon.intersects(semiPolygon)){
						//如果线路判断GPS点占到总GPS点的50%，那么即认为是该条线路符合条件；
						if(validPointCount>=gpsPointSize*0.5){
							results.put(driveLine.getId(), validPointCount);
						}
					}else{
						//如果线路判断GPS点占到总GPS点的50%，那么即认为是该条线路符合条件；
						if(validPointCount>=gpsPointSize*0.5){
							notIntersectSemiMap.put(driveLine.getId(), validPointCount);
						}
					}
					lineTypeMap.put(driveLine.getId(), driveLine.getType());
				}
			}
			if(results.isEmpty()){
				//如果没有经过半圈点的线路，那么就用GPS点落在线路上超过50%的线路来判断线路；
				results = notIntersectSemiMap;
			}else{
				//有经过线路半圈点，那么设置圈数为1
				testDrive.setLoopCount(1);//设置线路圈数为1；
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("使用半圈点判断线路时发生异常："+e.getMessage());
		}
		int firstLineId = -1;
		if(results.size()==1){
			//只有一条线路符合，直接返回
			for(int lineId : results.keySet()){
				firstLineId = lineId;
			}
			return firstLineId;
		}else if(results.size()>1){
			//有多条线路符合，如果有多条那么排序，返回GPS有效数最大的线路
			results=sortMapDesc(results);
			for(int lineId : results.keySet()){
				if(lineTypeMap.get(lineId).equals(GlobalConstant.DRIVE_LINE_TYPE_OIL)){
					//默认去除加油线路
					continue;
				}
				firstLineId = lineId; //只取第一条有效数最大的线路
				break;
			}
			return firstLineId;
		}else{
			//如果判断不出线路，默认取第一第线路 改；
			logger.info("ProtocolParseBusinessFacade isRefenceLineBySemiPoint:根据半圈点不能判定线路! dealerId:"+dealerId);
			return -1;
		}

	}
	
	/**
	 * 先判断落在哪条参考线上  如果没有落在参考线上再判断是否在围栏内  在围栏内则判定不在4S店范围内移动
	 * @param dealerId
	 * @param lng
	 * @param lat
	 * @return
	 */
	/*private int isRefenceLine(List<GPSPoint> gpsPoints,Integer dealerId,String lng,String lat,Integer testDriveStatus){
		//logger.info("进入判定试驾线路ID start");
		DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(dealerId);
		if(null==driveLineWithPolygon || null==driveLineWithPolygon.getLines() || driveLineWithPolygon.getLines().isEmpty())
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"test driving line is null");

		if(null==lng || "".equals(lng.trim()) || "0.0".equals(lng.trim()) || "0".equals(lng.trim())){
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
		}

		if(null==lat || "".equals(lat.trim()) || "0.0".equals(lat.trim()) || "0".equals(lat.trim())){
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
		}

		Company company=CacheCompanyManager.getCompany(dealerId);

		if(null==company.getBaiduLat() || null==company.getBaiduLng()){
			logger.info("没有设置经销商位置");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"没有设置经销商位置");
		}
		String refenceLineDistince = GlobalConfig.getString("valid_refence_line_distince");
		List<DriveLineVO> lines=driveLineWithPolygon.getLines();
		Map<Integer,Integer> linesMap=new HashMap<Integer,Integer>();
		int gpsPointSize=gpsPoints.size();
		Integer finalDriveLineId=0;
		int validPointCount = Integer.parseInt(GlobalConfig.getString("valid_point_count"));
		if(null!=gpsPoints && gpsPointSize>=validPointCount){
			for(DriveLineVO driveLine : lines){
				if(!(null==driveLine.getReferenceLPoints() || driveLine.getReferenceLPoints().isEmpty())){
					int lineId=0;
					int lineCount=0;
					for(GPSPoint gpsPoint: gpsPoints){
						boolean ok=GpsUtil.bufferJudgeNoCompress(gpsPoint,driveLine.getReferenceLPoints(),refenceLineDistince,String.valueOf(company.getBaiduLat()));
						if(ok){
							lineId=driveLine.getId();
							lineCount++;
						}
					}
					//logger.info("经销商试驾线路信息:"+driveLine.getId()+";试驾参考点数量:"+gpsPointSize+";有效试驾点数:"+lineCount);
					if( (gpsPointSize<=validPointCount && lineCount>=validPointCount)
							|| (gpsPointSize>=16 && gpsPointSize<=40 && lineCount>=gpsPointSize*0.7)
							|| (gpsPointSize>=41 && gpsPointSize<=70 && lineCount>=gpsPointSize*0.6)
							|| (gpsPointSize>=71 && gpsPointSize<=200 && lineCount>=gpsPointSize*0.5)){
						linesMap.put(lineId,lineCount);
						finalDriveLineId=lineId;
					}
				}
			}
		}
		if(null==linesMap || linesMap.isEmpty())
			return -1;
		//处理  大于16个点以上时  一个线路占比百分百  一个线路低于百分百  直接确定出线路
		linesMap=sortMapDesc(linesMap);
		if(null!=testDriveStatus && 4!=testDriveStatus){
			if(null!=linesMap && linesMap.size()==1){
				//logger.info("进入正常试驾中,判定参考线时取到的交叉参考线数量:"+linesMap.size() + "线路ID:"+finalDriveLineId);
				return finalDriveLineId;
			}else {
				int lineId=handleDriveLineId(linesMap);
				if(lineId>=1)
					return lineId;
				return 0;
			}
		}else{
			if(null!=linesMap && linesMap.size()==1){//确定线路编号
				//logger.info("判定参考线时取到的交叉参考线数量:"+linesMap.size() + "线路ID:"+finalDriveLineId);
				return finalDriveLineId;
			}else{
				int lineId=handleDriveLineId(linesMap);
				if(lineId>=1)
					return lineId;
				return companyOutTransfer(gpsPoints,lng,lat);
			}
		}
		//logger.info("判定处于哪条参考线上方法内结束");
	}*/
		
	/*private int companyOutTransfer(List<GPSPoint> gpsPoints,String lng,String lat){
		int outTransfer=0;
		for(GPSPoint gpsPoint: gpsPoints){
			boolean ok=isCompanyOut(String.valueOf(gpsPoint.getLng()),String.valueOf(gpsPoint.getLat()),lng,lat);
			if(ok){
				outTransfer++;
			}
		}
		if(outTransfer>=5)
			return 0;
		return -1;
	}*/
	/**
	 * 判定是否越界
	 * @param dealerId
	 * @param lng
	 * @param lat
	 * @return
	 */
	private int isCrossLineNew(Vehicle vehicle,Integer dealerId,String lng,String lat,String typeCode,TestDrive testDrive){
		DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(dealerId);
		if(null==driveLineWithPolygon)
		throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"test driving line is null");

		if(null==lng || "".equals(lng.trim()) || "0.0".equals(lng.trim()) || "0".equals(lng.trim())){
		throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
		}

		if(null==lat || "".equals(lat.trim()) || "0.0".equals(lat.trim()) || "0".equals(lat.trim())){
		throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
		}

		String point=GisUtil.getPointByLngLat(lng,lat);
		
		List<DriveLineVO> lines=driveLineWithPolygon.getLines();

		int lineId=0;
		int lineCount=0;
		for(DriveLineVO driveLine : lines){
			boolean ok= isOutLine(point,driveLine);
			if(ok){
				lineId=driveLine.getId();
				lineCount++;
			}
		}
		//logger.info("判定是否越界时获取到的交叉参考线数量:"+lineCount + "线路ID:"+lineId);
		if(lineCount==0){//一条线路都不靠近
			//begin tangqingsong 2015/7/16 增加判断越界规则，如果越界次数超过10次，那么才算越界
			Integer maxOutCount = Integer.parseInt(GlobalConfig.getString("valid_out_line_size"));
			if(testDrive.getOutLineCount()!=null && testDrive.getOutLineCount()>=maxOutCount){
				//越界
				return -2;
			}else{
				//增加越界次数
				incrementOutCount(testDrive);
				//更新试驾对象
				CacheTestDriveManager.putTestDrive(testDrive.getVehicleId(), testDrive,typeCode);
				return 0;
			}
			//end 

		}
		//不越界
		if(testDrive.getOutLineCount()!=null && testDrive.getOutLineCount()>0){
			testDrive.setOutLineCount(0);//越界次数清零
			//更新试驾对象
			CacheTestDriveManager.putTestDrive(testDrive.getVehicleId(), testDrive,typeCode);
		}
		if(lineCount==1){
			return lineId;
		}else{  //多条线路交叉
			return 0;
		}
	}
	
	
	/**
	 * 
	 * 对越界计数器加1
	 * @author tangqingsong
	 * @version 
	 * @param testDrive
	 */
	private synchronized void incrementOutCount(TestDrive testDrive){
		if(testDrive.getOutLineCount()!=null){
			testDrive.setOutLineCount(testDrive.getOutLineCount()+1);
		}else{
			testDrive.setOutLineCount(1);
		}
	}
	/**
	 * 判定是否越界
	 * @param dealerId
	 * @param lng
	 * @param lat
	 * @return
	 */
//	private boolean isCrossLineByLine(Integer dealerId,Integer lineId,String lng,String lat){
//		DriveLineWithPolygonVO driveLineWithPolygon=getTestDrivingLineNew(dealerId);
//		if(null==driveLineWithPolygon)
//		throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"test driving line is null");
//
//		if(null==lng || "".equals(lng.trim()) || "0.0".equals(lng.trim()) || "0".equals(lng.trim())){
//		throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
//		}
//
//		if(null==lat || "".equals(lat.trim()) || "0.0".equals(lat.trim()) || "0".equals(lat.trim())){
//		throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
//		}
//
//		String point=GisUtil.getPointByLngLat(lng,lat);
//		
//		List<DriveLineVO> lines=driveLineWithPolygon.getLines();
//
//		for(DriveLineVO driveLine : lines){
//			if(driveLine.getId().intValue()==lineId.intValue()){
//				boolean ok= isOutLine(point,driveLine);
//				return ok;
//			}
//		}
//		return false;
//	}
	
	private boolean isOutLine(String point, DriveLineVO driveLine){
		boolean isOut = false;
		if (null != driveLine) {
			try {
				if (null==driveLine.getFenceType() || 1 == driveLine.getFenceType().intValue()) {
					if(null!=driveLine.getOutPolygon()){
						
						return GisUtil.isInner(point, driveLine.getOutPolygon());
					}else{
						return false;
					}
				} else if (2 == driveLine.getFenceType()) {
					boolean out = false;
					boolean inner = true;
					
					if(null!=driveLine.getOutPolygon()){
						out = GisUtil.isInner(point,driveLine.getOutPolygon());
					}else{
						out=false;
					}
					
					if(null!=driveLine.getInnerPolygon()){
						inner = GisUtil.isInner(point,driveLine.getInnerPolygon());
					}else{
						inner=true;
					}
					if (out && !inner) isOut = true;
				}
			} catch (ParseException e) {
				throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"判定是否线路越界时出错");
			}
		}
		return isOut;
	}	
	/**
	 * 是否在4S店坐标
	 * @param lng
	 * @param lat
	 * @param lineId
	 * @return
	 */
	private boolean isCompanyPoint(String lng,String lat,String companyLng,String companyLat){		
		double distince =GisUtil.getDistance(lng,lat,companyLng,companyLat);
		return distince<=getValidStartEndDistince();
	}
	
	/**
	 * 是否在4S店坐标
	 * @param lng
	 * @param lat
	 * @param lineId
	 * @return
	 */
	/*private boolean isReturnCompanyPoint(String lng,String lat,String companyLng,String companyLat){
		double distince =GisUtil.getDistance(lng,lat,companyLng,companyLat);
		return distince<=getReturnCompanyDistince();
	}*/
	
	/**
	 * 是否在4S店坐标
	 * @param lng
	 * @param lat
	 * @param lineId
	 * @return
	 */
	private boolean isCompanyPointExtend(String lng,String lat,String companyLng,String companyLat,long intevalTime,int mileage){		
		double distince =GisUtil.getDistance(lng,lat,companyLng,companyLat);
		logger.info("当前位置信息:("+lng + ","+lat +")  经销商点位置:("+companyLng +","+companyLat+") 两点间距离为:"+distince + "里程差值:"+mileage + ";时间间隔:"+intevalTime +"s");
		int validStartEndDistinceExtend = Integer.parseInt(GlobalConfig.getString("valid_start_end_distince_extend"));
		int validStartEndTimeExtend = Integer.parseInt(GlobalConfig.getString("valid_start_end_time_extend"));
		int validStartEndMileageExtend = Integer.parseInt(GlobalConfig.getString("valid_start_end_mileage_extend"));
		return (distince<=validStartEndDistinceExtend && distince>getValidStartEndDistince()) && ( intevalTime>=validStartEndTimeExtend || mileage>=validStartEndMileageExtend );
	}
	
	/**
	 * 是否在4S店外移动（判断车辆是在试驾中还是4S店内移动）
	 * @param lng
	 * @param lat
	 * @param lineId
	 * @return
	 */
	/*private boolean isCompanyOut(String lng,String lat,String companyLng,String companyLat){
		
		if(null==lng || "".equals(lng.trim()) || null==lat || "".equals(lat.trim()) ){
			logger.info("(lng,lat):("+lng +","+lat+")");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"isCompanyOut gps point exception");
		}
		if(null==companyLng || "".equals(companyLng.trim()) ||null==companyLat || "".equals(companyLat.trim())){
			logger.info("(companyLng,companyLat):("+companyLng +","+companyLat+")");
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"isCompanyOut company point exception");
		}
		
		double distince =GisUtil.getDistance(lng,lat,companyLng,companyLat);
		return distince>getNoCompanyTransferDistince();
	}*/
		
	/**
	 * 计算是否过了半圈点
	 * @param lng
	 * @param lat
	 * @param lineId
	 * @return
	 */
	private boolean isSemicyclePointNew(Vehicle vehicle,String lng,String lat,Integer lineId){
		
		DriveLine driveLine=CacheDriveLineManager.getDriveLineById(lineId);
		if(null==driveLine)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"test driving line is null");
		if(null!=driveLine.getType() && GlobalConstant.DRIVE_LINE_TYPE_OIL==driveLine.getType())
			return false;
		String lngLat=driveLine.getSemicyclePoint();
		if(null==lngLat || "".equals(lngLat.trim()))
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"线路没有设置半圈点");
		String latLngStr[]=lngLat.split(",");
		double distince =GisUtil.getDistance(lng,lat,latLngStr[0],latLngStr[1]);
		
		return distince<getValidSemcycleDistince();
	}
	
	/**
	 * 处理原始经纬度信息点
	 * @param baseDataRequest
	 */
	private void handleOriginalPoint(Integer vehicleId,GpsMessageDataReq baseDataRequest,String typeCode){
		if(null==baseDataRequest.getBaiduLat()
				|| "".equals(baseDataRequest.getBaiduLat())
				|| "0.0".equals(baseDataRequest.getBaiduLat()) 
				|| "0".equals(baseDataRequest.getBaiduLat())){//取不到缓存数据 上传上来的经纬度也是空，数据清零
			Location cacheLocation=CacheTestDriveManager.getLocationByVehicleId(vehicleId,typeCode);
			if(null==cacheLocation){
				throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_INFO_MESSAGE_10014.getCode(),"gps don't get position");
			}
			
			baseDataRequest.setLat(cacheLocation.getOrignLatitude());
			baseDataRequest.setLng(cacheLocation.getOrignLongitude());
			baseDataRequest.setBaiduLat(cacheLocation.getLatitude());
			baseDataRequest.setBaiduLng(cacheLocation.getLongitude());
		}
	}
	
	private Double getValidSemcycleDistince(){
		String validDistince = GlobalConfig.getString("valid_semcycle_distince");
		if(null!=validDistince && validDistince.trim().length()>0)
			return Double.parseDouble(validDistince);
		return 1500D;
	}
	/**计算当前经纬度和4S店经纬度距离*/
	private Double getValidStartEndDistince(){
		String validDistince = GlobalConfig.getString("valid_start_end_distince");
		if(null!=validDistince && validDistince.trim().length()>0)
			return Double.parseDouble(validDistince);
		return 500D;
	}
	
	/**获取4S店外设定有效距离*/
	/*private Double getNoCompanyTransferDistince(){
		String validDistince = GlobalConfig.getString("valid_no_company_transfer_distince");
		if(null!=validDistince && validDistince.trim().length()>0)
			return Double.parseDouble(validDistince);
		return 500D;
	}
	
	*//**获取返回到4S店有效距离*//*
	private Double getReturnCompanyDistince(){
		String validDistince = GlobalConfig.getString("valid_near_point_distince");
		if(null!=validDistince && validDistince.trim().length()>0)
			return Double.parseDouble(validDistince);
		return 100D;
	}

	private int handleDriveLineId(Map<Integer, Integer> oldMap){
		Map<Integer, Integer> newMap=sortMapDesc(oldMap);
		//第二种方式 entrySet()
		Set<Map.Entry<Integer, Integer>> set2=newMap.entrySet();
		int count=0;
		int firstValue = 0,secondValue = 0;
		int firstLineId = 0;
		for (Iterator <Map.Entry<Integer, Integer>> iterator = set2.iterator(); iterator.hasNext();) {
			Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) iterator.next();
			count++;
			if(count==1){
				firstLineId=entry.getKey();
				firstValue=entry.getValue();
			}
			if(count==2){
				secondValue=entry.getValue();
				break;
			}
		}
		if((firstValue-secondValue)>=5)
			return firstLineId;
		return -1;
	}*/
//    private Map sortMapAsc(Map oldMap) {
//        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());
//        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
//            @Override
//            public int compare(Entry<java.lang.String, Integer> arg0,
//                    Entry<java.lang.String, Integer> arg1) {
//                return arg0.getValue() - arg1.getValue();
//            }
//        });
//        Map newMap = new LinkedHashMap();
//        for (int i = 0; i < list.size(); i++) {
//            newMap.put(list.get(i).getKey(), list.get(i).getValue());
//        }
//        return newMap;
//    }
    
    private Map<Integer, Integer> sortMapDesc(Map<Integer, Integer> oldMap) {
        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(oldMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Entry<Integer, Integer> arg0,
                    Entry<Integer, Integer> arg1) {
                return arg1.getValue() - arg0.getValue();
            }
        });
        Map<Integer, Integer> newMap = new LinkedHashMap<Integer, Integer>();
        for (int i = 0; i < list.size(); i++) {
            newMap.put(list.get(i).getKey(), list.get(i).getValue());
        }
        return newMap;
    }
}
