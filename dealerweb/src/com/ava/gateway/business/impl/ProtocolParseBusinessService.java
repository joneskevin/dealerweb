package com.ava.gateway.business.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.admin.dao.IReHandleTestDriveDao;
import com.ava.admin.domain.vo.TaskMessageLogVo;
import com.ava.dao.IShortMessageDao;
import com.ava.domain.entity.ShortMessage;
import com.ava.enums.ServiceResponseEnum;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.gateway.business.IProtocolParseBusinessService;
import com.ava.gateway.dao.IProtocolMessageDao;
import com.ava.gateway.domain.entity.BaseMessage;
import com.ava.gateway.domain.entity.BoxUpdateMessage;
import com.ava.gateway.domain.entity.CurrentGpsMessage;
import com.ava.gateway.domain.entity.GetCommonMessage;
import com.ava.gateway.domain.entity.ObdDismantleAlertMessage;
import com.ava.gateway.domain.entity.SetConfigMessage;
import com.ava.gateway.domain.entity.TBoxStatusMessage;
import com.ava.gateway.domain.vo.BaseDataReq;
import com.ava.gateway.domain.vo.GetCommonDataReq;
import com.ava.gateway.domain.vo.GetCommonDataRes;
import com.ava.gateway.domain.vo.GpsMessageDataReq;
import com.ava.gateway.domain.vo.GpsMessageDataRes;
import com.ava.gateway.domain.vo.HeaderMessage;
import com.ava.gateway.domain.vo.ParamItem;
import com.ava.gateway.domain.vo.ProtocolParseRes;
import com.ava.gateway.domain.vo.SetConfigDataReq;
import com.ava.gateway.domain.vo.SetConfigDataRes;
import com.ava.gateway.domain.vo.TBoxStatusDataReq;
import com.ava.gateway.domain.vo.TBoxStatusDataRes;
import com.ava.resource.GlobalConstant;
import com.ava.util.DateTime;

@Service("testDriver.web2gatewayService")
public class ProtocolParseBusinessService implements IProtocolParseBusinessService{
	
	@Resource(name="testDriver.web2gatewayDao")
	IProtocolMessageDao protocolMessageDao;
	protected final static Logger logger = Logger.getLogger(ProtocolParseBusinessService.class);
	
	@Autowired
	private IShortMessageDao shortMessageDao;
	
	@Resource(name="reHandleTestDriveDao")
	private IReHandleTestDriveDao reHandleTestDriveDao;
	
	/**
	 * 处理获取指令代码
	 * @param getCommonDataReq
	 * @return
	 */
	public ProtocolParseRes  handleGetCommonDataRes(HeaderMessage headerMessage,GetCommonDataReq getCommonDataReq){
		ProtocolParseRes protocolResponse=new ProtocolParseRes();
		
		GetCommonMessage getCommonMessage=new GetCommonMessage();
		GetCommonDataRes getCommonDataRes=new GetCommonDataRes();
		
		if(null==getCommonDataReq)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		
		setBusinessBeanHeaderMessage(headerMessage,getCommonMessage);
		getCommonMessage.setFireState(getCommonDataReq.getFireState());
		getCommonMessage.setTboxToken(getCommonDataReq.getTboxToken());
		protocolMessageDao.saveGetCommonMessage(getCommonMessage);
		
		getCommonDataRes.setCommand("10");
		getCommonDataRes.setConfig(null);
		getCommonDataRes.setExistCmd(1);
		protocolResponse.setResponseData(getCommonDataRes);
		
		return protocolResponse;
	}
	
	/**
	 * 状态变更指令
	 * @param headerMessage
	 * @param tBoxStatusDataReq
	 * @return
	 */
	public ProtocolParseRes handleTBoxStatusDataReq(HeaderMessage headerMessage,TBoxStatusDataReq tBoxStatusDataReq){
		ProtocolParseRes protocolResponse=new ProtocolParseRes();
		
		TBoxStatusDataRes tBoxStatusDataRes=new TBoxStatusDataRes();
		TBoxStatusMessage tBoxStatusMessage=new TBoxStatusMessage();
		
		if(null==tBoxStatusDataReq)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		
		setBusinessBeanHeaderMessage(headerMessage,tBoxStatusMessage);
		tBoxStatusMessage.setMid(tBoxStatusDataReq.getMid());
		tBoxStatusMessage.setActiveAck(tBoxStatusDataReq.getActiveAck());
		protocolMessageDao.saveTBoxStatusMessage(tBoxStatusMessage);
		
		tBoxStatusDataRes.setMid(tBoxStatusDataReq.getMid());
		tBoxStatusDataRes.setSecurityContext("111111");
		tBoxStatusDataRes.settBoxStatus("1111111");
		tBoxStatusDataRes.settBoxToken("1111111");
		
		protocolResponse.setResponseData(tBoxStatusDataRes);
		
		return protocolResponse;
	}
	
	/**
	 * 参数配置指令
	 * @param headerMessage
	 * @param setConfigDataReq
	 * @return
	 */
	public ProtocolParseRes handleSetConfigDataReq(HeaderMessage headerMessage,SetConfigDataReq setConfigDataReq){
		ProtocolParseRes protocolResponse=new ProtocolParseRes();
		SetConfigDataRes setConfigDataRes=new SetConfigDataRes();
		SetConfigMessage setConfigMessage=new SetConfigMessage();
		
		if(null==setConfigDataReq)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		
		setBusinessBeanHeaderMessage(headerMessage,setConfigMessage);
		setConfigMessage.setIsDefault(setConfigDataReq.getIsDefault());
		protocolMessageDao.saveSetConfigMessage(setConfigMessage);
		
		setConfigDataRes=getSetConfigDataRes();
		protocolResponse.setResponseData(setConfigDataRes);
		
		return protocolResponse;
	}
	
	/**
	 * 有关gps消息指令
	 * @param headerMessage
	 * @param gpsMessageDataReq
	 * @return
	 */
	public ProtocolParseRes handleGpsMessageDataReq(HeaderMessage headerMessage,GpsMessageDataReq gpsMessageDataReq){
				
		ProtocolParseRes protocolResponse=new ProtocolParseRes();
		GpsMessageDataRes gpsMessageDataRes=new GpsMessageDataRes();
		CurrentGpsMessage currentGpsMessage=new CurrentGpsMessage();

		if(null==gpsMessageDataReq)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		
		setBusinessBeanHeaderMessage(headerMessage,currentGpsMessage);
		setCurrentGpsMessage(headerMessage.getMessageCode(),currentGpsMessage,gpsMessageDataReq);			
		if(headerMessage.getTypeCode()!=null && !headerMessage.getTypeCode().isEmpty()){
			//重跑，不需要保存GPS点
		}else{
			protocolMessageDao.saveCurrentGpsMessage(currentGpsMessage);
		}
		gpsMessageDataRes.setExistCmd(0);
		gpsMessageDataRes.setParamVer(1);
		protocolResponse.setResponseData(gpsMessageDataRes);
		
		return protocolResponse;
	}
	
	/**
	 * 有关gps消息指令
	 * @param headerMessage
	 * @param gpsMessageDataReq
	 * @return
	 */
	public ProtocolParseRes handleObdDismantleAlertMessageReq(HeaderMessage headerMessage,GpsMessageDataReq gpsMessageDataReq){
				
		ProtocolParseRes protocolResponse=new ProtocolParseRes();
		ObdDismantleAlertMessage obdDismantleAlertMessage=new ObdDismantleAlertMessage();

		if(null==gpsMessageDataReq)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		
		setBusinessBeanHeaderMessage(headerMessage,obdDismantleAlertMessage);
		setObdDismantleAlertMessage(headerMessage.getMessageCode(),obdDismantleAlertMessage,gpsMessageDataReq);			
		protocolMessageDao.saveObdDismantleAlertMessage(obdDismantleAlertMessage);
		protocolResponse.setResponseData(null);
		saveObdDismantleAlert(obdDismantleAlertMessage.getVin(),obdDismantleAlertMessage.getSerialNumber(),headerMessage.getTimestamp());//增加发站内短消息
		
		return protocolResponse;
	}
	
	/**
	 * OTA升级
	 * @param headerMessage
	 * @param gpsMessageDataReq
	 * @return
	 */
	public ProtocolParseRes handleBoxUpdate(HeaderMessage headerMessage,BaseDataReq baseDataReq){
				
		ProtocolParseRes protocolResponse=new ProtocolParseRes();

		BoxUpdateMessage boxUpdateMessage=new BoxUpdateMessage();
		
		if(null==baseDataReq)
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10001);
		
		setBusinessBeanHeaderMessage(headerMessage,boxUpdateMessage);
		protocolMessageDao.saveBoxUpdateMessage(boxUpdateMessage);
		
		return protocolResponse;
	}
	
	public void setBusinessBeanHeaderMessage(HeaderMessage headerMessage,BaseMessage baseMessage){
		
		baseMessage.setImsi(headerMessage.getImsi());
		baseMessage.setSerialNumber(headerMessage.getSerialNumber());
		baseMessage.setVin(headerMessage.getVin());
		baseMessage.setMessageCode(headerMessage.getMessageCode());
		Date commonDate;
		try {
			commonDate = null==headerMessage.getTimestamp() || "".equals(headerMessage.getTimestamp().trim()) ? new Date() :new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(headerMessage.getTimestamp());
			baseMessage.setMessageDate(commonDate);
		} catch (ParseException e) {
			logger.error(e.getMessage());
			throw new ProtocolParseBusinessException(ServiceResponseEnum.PROTOCOL_GATEWAY_ERROR_CODE_10002);
		}
	}
	
	/**
	 * 处理gps信息
	 * @param messageCode
	 * @param currentGpsMessage
	 * @param gpsMessageDataReq
	 */
	public void setCurrentGpsMessage(int messageCode,CurrentGpsMessage currentGpsMessage,GpsMessageDataReq gpsMessageDataReq){
		if(null!=gpsMessageDataReq){
			if(1281==messageCode){
				currentGpsMessage.setFireState(gpsMessageDataReq.getFireState());
			}
			if(1537==messageCode){
				currentGpsMessage.setAlarmType(gpsMessageDataReq.getAlarmType());
			}
			if(1538==messageCode){
				currentGpsMessage.setRemindTypeCcwg(transObjType(gpsMessageDataReq.isRemindTypeCcwg()));
				currentGpsMessage.setRemindTypeDdwg(transObjType(gpsMessageDataReq.isRemindTypeDdwg()));
				currentGpsMessage.setRemindTypeDkcc(transObjType(gpsMessageDataReq.isRemindTypeDkcc()));
				currentGpsMessage.setRemindTypeHbxo(transObjType(gpsMessageDataReq.isRemindTypeHbxo()));
				currentGpsMessage.setRemindTypeHbxws(transObjType(gpsMessageDataReq.isRemindTypeHbxws()));
				currentGpsMessage.setRemindTypeHchwg(transObjType(gpsMessageDataReq.isRemindTypeHchwg()));
				currentGpsMessage.setRemindTypeOverspeed(transObjType(gpsMessageDataReq.isRemindTypeOverspeed()));
				currentGpsMessage.setRemindTypeSs(transObjType(gpsMessageDataReq.isRemindTypeSs()));
				currentGpsMessage.setRemindTypeXhtx(transObjType(gpsMessageDataReq.isRemindTypeXhtx()));
				currentGpsMessage.setRemindTypeYlgd(transObjType(gpsMessageDataReq.isRemindTypeYlgd()));
			}
			currentGpsMessage.setLocation(transObjType(gpsMessageDataReq.isLocation()));
			currentGpsMessage.setLng(gpsMessageDataReq.getLng());
			currentGpsMessage.setLat(gpsMessageDataReq.getLat());
			currentGpsMessage.setBaiduLng(gpsMessageDataReq.getBaiduLng());
			currentGpsMessage.setBaiduLat(gpsMessageDataReq.getBaiduLat());
			currentGpsMessage.setHeading(gpsMessageDataReq.getHeading());
			currentGpsMessage.setSpeed(gpsMessageDataReq.getSpeed());
			currentGpsMessage.setOilValueOnce(gpsMessageDataReq.getOilValueOnce());
			currentGpsMessage.setMileageOnce(gpsMessageDataReq.getMileageOnce());
			currentGpsMessage.setkO1Tankinhalt(gpsMessageDataReq.getkO1Tankinhalt());
			currentGpsMessage.setMfnSensorwarn(transObjType(gpsMessageDataReq.getMfnSensorwarn()));
			currentGpsMessage.setFt1TuerGeoeffnet(transObjType(gpsMessageDataReq.getFt1TuerGeoeffnet()));
			currentGpsMessage.setFt1Verriegelt(transObjType(gpsMessageDataReq.getFt1Verriegelt()));
			currentGpsMessage.setBt1TuerGeoeffnet(transObjType(gpsMessageDataReq.getBt1TuerGeoeffnet()));
			currentGpsMessage.setBt1Verriegelt(transObjType(gpsMessageDataReq.getBt1Verriegelt()));
			currentGpsMessage.setHl1TuerGeoeffnet(transObjType(gpsMessageDataReq.getHl1TuerGeoeffnet()));
			currentGpsMessage.setHl1Verriegelt(transObjType(gpsMessageDataReq.getHl1Verriegelt()));
			currentGpsMessage.setHr1TuerGeoeffnet(transObjType(gpsMessageDataReq.getHr1TuerGeoeffnet()));
			currentGpsMessage.setHr1Verriegelt(transObjType(gpsMessageDataReq.getHr1Verriegelt()));
			currentGpsMessage.setBt1FhOeffnung(gpsMessageDataReq.getBt1FhOeffnung());
			currentGpsMessage.setFt1FhOeffnung(gpsMessageDataReq.getFt1FhOeffnung());
			currentGpsMessage.setHl1FhOeffnung(gpsMessageDataReq.getHl1FhOeffnung());
			currentGpsMessage.setHr1FhOeffnung(gpsMessageDataReq.getHr1FhOeffnung());
			currentGpsMessage.setFt1FhHlFreigabe(transObjType(gpsMessageDataReq.getFt1FhHlFreigabe()));
			currentGpsMessage.setFt1FhHrFreigabe(transObjType(gpsMessageDataReq.getFt1FhHrFreigabe()));
			currentGpsMessage.setKo1Handbremse(transObjType(gpsMessageDataReq.getKo1Handbremse()));
			currentGpsMessage.setZk4Heckeinzelentrieg(transObjType(gpsMessageDataReq.getZk4Heckeinzelentrieg()));
			currentGpsMessage.setBskHdHauptraste(transObjType(gpsMessageDataReq.getBskHdHauptraste()));
			currentGpsMessage.setKo1Kuehlmittel(transObjType(gpsMessageDataReq.getKo1Kuehlmittel()));
			currentGpsMessage.setMo5Heissl(transObjType(gpsMessageDataReq.getMo5Heissl()));
			currentGpsMessage.setKo1WaschWasser(transObjType(gpsMessageDataReq.getKo1WaschWasser()));
			currentGpsMessage.setGw5PositionPsd(gpsMessageDataReq.getGw5PositionPsd());
			currentGpsMessage.setBskDefLampe(transObjType(gpsMessageDataReq.getBskDefLampe()));
			currentGpsMessage.setBskAbblendlicht(transObjType(gpsMessageDataReq.getBskAbblendlicht()));
			currentGpsMessage.setBskNebellicht(transObjType(gpsMessageDataReq.getBskNebellicht()));
			currentGpsMessage.setBskNebelschlusslicht(transObjType(gpsMessageDataReq.getBskNebelschlusslicht()));
			currentGpsMessage.setBskFernlicht(transObjType(gpsMessageDataReq.getBskFernlicht()));
			currentGpsMessage.setKo1StaOeldr(transObjType(gpsMessageDataReq.getKo1StaOeldr()));
			currentGpsMessage.setStorigeBatt(gpsMessageDataReq.getStorigeBatt());
			currentGpsMessage.setKo1AngezKmh(gpsMessageDataReq.getKo1AngezKmh());
			currentGpsMessage.setKo3Kilometer(gpsMessageDataReq.getKo3Kilometer());
			currentGpsMessage.setMo1Drehzahl(gpsMessageDataReq.getMo1Drehzahl());
		}
	}
	
	/**
	 * 处理gps信息
	 * @param messageCode
	 * @param currentGpsMessage
	 * @param gpsMessageDataReq
	 */
	public void setObdDismantleAlertMessage(int messageCode,ObdDismantleAlertMessage obdDismantleAlertMessage,GpsMessageDataReq gpsMessageDataReq){
		if(null!=gpsMessageDataReq){
			obdDismantleAlertMessage.setFireState(gpsMessageDataReq.getFireState());
			obdDismantleAlertMessage.setAlarmType(gpsMessageDataReq.getAlarmType());
			obdDismantleAlertMessage.setLng(gpsMessageDataReq.getLng());
			obdDismantleAlertMessage.setLat(gpsMessageDataReq.getLat());
			obdDismantleAlertMessage.setBaiduLng(gpsMessageDataReq.getBaiduLng());
			obdDismantleAlertMessage.setBaiduLat(gpsMessageDataReq.getBaiduLat());
			obdDismantleAlertMessage.setHeading(gpsMessageDataReq.getHeading());
			obdDismantleAlertMessage.setSpeed(gpsMessageDataReq.getSpeed());
		}
	}
	
	private Integer transObjType(Boolean original){
		return null==original ? null : (original ? 1 :0);
	}
	/**
	 * 获取配置信息
	 * @return
	 */
	public SetConfigDataRes getSetConfigDataRes(){
		
		SetConfigDataRes setConfigDataRes=new SetConfigDataRes();
		List<ParamItem> paramItemList=new ArrayList<ParamItem>();
		ParamItem paramItem=null;
		for(int i=0;i<10;i++){
			paramItem=new ParamItem();
			String uuid=UUID.randomUUID().toString();
			paramItem.setParamKey(String.valueOf(i));
			paramItem.setParamLength(uuid.getBytes().length);
			paramItem.setParamValue(uuid);
			paramItemList.add(paramItem);
		}
		setConfigDataRes.setParamVer(1);
		int paramSize=null==paramItemList ? 0 :paramItemList.size();
		setConfigDataRes.setParamSize(paramSize);
		setConfigDataRes.setParamItem(paramItemList);
		return setConfigDataRes;
	}
	
	public void saveObdDismantleAlert(String vin, String uniqueId, String alertDate){
		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setContent("有车辆发生拆除报警，信息如下：vin:"+vin+";设备号:"+uniqueId+";拆除发生时间:"+alertDate);
		shortMessage.setTitle(vin+"拆除报警");
		shortMessage.setFromLoginName("admin@group");
		shortMessage.setFromUserId(GlobalConstant.DEFAULT_GROUP_ORG_ID);
		shortMessage.setToUserId(Integer.parseInt(GlobalConstant.OPERATIONS_CENTER_ID));
		shortMessage.setToLoginName("admin@vti");
		shortMessage.setCreationTime(new Date());
		shortMessage.setDeleteByfromUser(0);
		shortMessage.setDeleteBytoUser(0);
		shortMessage.setIsreaded(0);
		shortMessageDao.edit(shortMessage);	//持久化站内消息对象，该方法同时自动给OBC_Server发送消息同步用户新邮件数量
	}
	
	@Override
	public void repairIgnitionAndFlameoutMessage(String vin, String startTime, String endTime) {
		//1.获取点火熄火列表，并按消息时间排序
		List<Object> currentGpsMessageList = protocolMessageDao.getIgnitionMessageList(vin, startTime, endTime);
		
		if(null == currentGpsMessageList || currentGpsMessageList.size()==0){
			return;
		}
		logger.info("currentGpsMessageList size ="+ currentGpsMessageList.size());
		
		//2.检查目标列表中疑似缺失的点火报文时间段		
		for( int i=0; i<currentGpsMessageList.size(); i++){
			CurrentGpsMessage temp = (CurrentGpsMessage)currentGpsMessageList.get(i);
			if(i>0){//从第二个报文开始判定
				CurrentGpsMessage prevMessage = (CurrentGpsMessage)currentGpsMessageList.get(i-1);
				if(temp.getFireState().equals(prevMessage.getFireState())){//如果两个相邻的报文的点火熄火fireState标示相同，说明需要补报文
					if(temp.getFireState().intValue() == 0){//说明两个点火报文中缺熄火报文
						logger.info("repairIgnitionAndFlameoutMessage 补熄火报文");
						//根据当前的点火报文补熄火报文
						repairFlameoutMessage(prevMessage, temp);
					}
					
					if(temp.getFireState().intValue() == 1){//说明两个熄火报文中缺点火报文
						logger.info("repairIgnitionAndFlameoutMessage 补点火报文");
						//根据上一个熄火报文补点火报文
						repairIgnitionMessage(prevMessage, temp);
					}
				}
				//两个相邻1281报文点火熄火类型不同，不用补报文
			}
		}
	}
	
	/**
	 * 
	 * 根据上一个熄火报文补点火报文
	 *
	 * @author wangchao
	 * @version 
	 * @param startFlameoutMessage
	 * @param endFlameoutMessage
	 */
	private void repairIgnitionMessage(CurrentGpsMessage startFlameoutMessage, CurrentGpsMessage endFlameoutMessage){
		CurrentGpsMessage newIgnitionMessage = new CurrentGpsMessage();
		try {
			//根据距离上一个熄火点最近的gps来推导此点火点的messageDate
			CurrentGpsMessage nearlyGpsMessage = protocolMessageDao.
					getNearlyGpsPointMessage(startFlameoutMessage.getVin(), DateTime.toNormalDateTime(startFlameoutMessage.getMessageDate()), DateTime.toNormalDateTime(endFlameoutMessage.getMessageDate()), "");
			if(null!=nearlyGpsMessage){//两个熄火报文之间有gps报文
				newIgnitionMessage = (CurrentGpsMessage) BeanUtils.cloneBean(startFlameoutMessage);
				newIgnitionMessage.setId(null);//去掉id
				newIgnitionMessage.setFireState(new Integer(0));//设置为点火报文
				newIgnitionMessage.setCreateTime(new Date());
				newIgnitionMessage.setMessageDate(DateTime.addSeconds(nearlyGpsMessage.getMessageDate(),-10));//往前推10秒
			}else{//两个熄火报文之间无gps报文
				newIgnitionMessage = (CurrentGpsMessage) BeanUtils.cloneBean(endFlameoutMessage);
				newIgnitionMessage.setId(null);//去掉id
				newIgnitionMessage.setFireState(new Integer(0));//设置为点火报文
				newIgnitionMessage.setCreateTime(new Date());
				newIgnitionMessage.setMessageDate(DateTime.addSeconds(endFlameoutMessage.getMessageDate(),-1));//往前推1秒
			}
			
			if(null!=newIgnitionMessage){
				protocolMessageDao.saveCurrentGpsMessage(newIgnitionMessage);
			}
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 
	 * 根据当前的点火报文补熄火报文
	 *
	 * @author wangchao
	 * @version 
	 * @param startIgnitionMessage
	 * @param endIgnitionMessage
	 */
	private void repairFlameoutMessage(CurrentGpsMessage startIgnitionMessage, CurrentGpsMessage endIgnitionMessage){
	
		CurrentGpsMessage newFlameoutMessage = new CurrentGpsMessage();
		try {
			//根据距离上一个熄火点最近的gps来推导此点火点的messageDate
			CurrentGpsMessage nearlyGpsMessage = protocolMessageDao.
					getNearlyGpsPointMessage(startIgnitionMessage.getVin(), DateTime.toNormalDateTime(startIgnitionMessage.getMessageDate()), DateTime.toNormalDateTime(endIgnitionMessage.getMessageDate()), "desc");
			if(null!=nearlyGpsMessage){//两个熄火报文之间有gps报文
				newFlameoutMessage = (CurrentGpsMessage) BeanUtils.cloneBean(endIgnitionMessage);
				newFlameoutMessage.setId(null);//去掉id
				newFlameoutMessage.setFireState(new Integer(1));//设置为点火报文
				newFlameoutMessage.setCreateTime(new Date());
				newFlameoutMessage.setMessageDate(DateTime.addSeconds(nearlyGpsMessage.getMessageDate(),10));//往后推10秒
			}else{//两个熄火报文之间无gps报文
				newFlameoutMessage = (CurrentGpsMessage) BeanUtils.cloneBean(startIgnitionMessage);
				newFlameoutMessage.setId(null);//去掉id
				newFlameoutMessage.setFireState(new Integer(1));//设置为熄火报文
				newFlameoutMessage.setCreateTime(new Date());
				newFlameoutMessage.setMessageDate(DateTime.addSeconds(startIgnitionMessage.getMessageDate(),1));//往后推1秒
			}
			if(null!=newFlameoutMessage){
				protocolMessageDao.saveCurrentGpsMessage(newFlameoutMessage);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * 记录异常日志
	 * @author tangqingsong
	 * @version 
	 * @param headerMessage
	 */
	public void saveTaskLog(HeaderMessage headerMessage,String logCode){
		String year = DateTime.getDateStr(headerMessage.getTimestamp(), DateTime.PATTERN_YEAR);
		if(year.equals("2008") || (headerMessage.getTypeCode()!=null && !headerMessage.getTypeCode().isEmpty())){
			//初始日期的或重跑不需要记录
			return;
		}
		TaskMessageLogVo logVo = new TaskMessageLogVo();
		logVo.setVin(headerMessage.getVin());
		logVo.setCurtime(new Date());
		logVo.setCounter(1);
		Date msgDate = DateTime.getDate(headerMessage.getTimestamp(), DateTime.PATTERN_DATETIME);
		if(headerMessage.getStartTime()!=null){
			logVo.setStartDate(headerMessage.getStartTime());
		}else{
			logVo.setStartDate(msgDate);
		}
		logVo.setEndDate(msgDate);
		logVo.setLogCode(logCode);
		logVo.setStatus(GlobalConstant.LOG_HANDLE_NORMAL);
		logVo.setSerialNumber(headerMessage.getSerialNumber());
		if(logCode.equals(GlobalConstant.LOG_REQUEST_TIME_OUT)){
			//延迟上报的日志处理
			Map<String,Object> argMap = new HashMap<String,Object>();
			argMap.put("vin", logVo.getVin());
			argMap.put("status",GlobalConstant.LOG_HANDLE_NORMAL);
			argMap.put("logCode",GlobalConstant.LOG_REQUEST_TIME_OUT);
			List<TaskMessageLogVo> logList = reHandleTestDriveDao.findLogMessage(argMap);
			if(logList!=null && !logList.isEmpty()){
				TaskMessageLogVo oldLog = logList.get(0);
				logVo.setId(oldLog.getId());
				//比较时间
				if(oldLog.getStartDate()!=null){
					if(msgDate.compareTo(oldLog.getStartDate())>0){
						//如果报文时间大于开始时间，那么开始时间还是用旧的开始时间
						logVo.setStartDate(oldLog.getStartDate());
					}
				}
				if(oldLog.getEndDate()!=null){
					if(msgDate.compareTo(oldLog.getEndDate())<0){
						//如果报文时间小于结束时间，那么结束时间还是用旧的结束时间
						logVo.setEndDate(oldLog.getEndDate());
					}
				}
				reHandleTestDriveDao.saveOrUpdateTaskMessageLog(logVo);
			}else{
				reHandleTestDriveDao.saveTaskMessageLog(logVo);
			}
		}else{
			reHandleTestDriveDao.saveOrUpdateTaskMessageLog(logVo);
		}
	}

}
