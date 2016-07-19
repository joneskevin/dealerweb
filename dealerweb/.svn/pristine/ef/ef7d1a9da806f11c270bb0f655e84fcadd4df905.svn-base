package com.ava.baseSystem.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.ava.baseSystem.service.IShortMessageService;
import com.ava.domain.entity.Company;
import com.ava.domain.entity.ShortMessage;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.resource.cache.CacheCompanyManager;
import com.ava.util.DateTime;
import com.ava.util.excel.ExportExcelUtil;

@Controller
@RequestMapping(value="/base/shortMessage")
public class ShortMessageController extends Base4MvcController{
	
	
	@Autowired
	private IShortMessageService service;

	/**
	 * 发件箱列表
	 * @param transMsg
	 * @return
	 */
	@RequestMapping(value="/outboxList.vti",  method=RequestMethod.GET)
	public String displayOutboxList(TransMsg transMsg) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = service.displayOutboxList(transMsg, userId);
		
		getRequest().setAttribute("outboxList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/shortMessage/outbox";
	}
	
	/**
	 * 发站内消息页面
	 * @param transMsg
	 * @return
	 */
	@RequestMapping(value="/displaySend.vti",  method=RequestMethod.GET)
	public String displaySend() {
		Integer companyId ;
//		if(SessionManager.getCurrentCompanyId(getRequest())==null)
//		{
//			companyId=SessionManager.getCurrentCompanyId(getRequest());
//		}else{
//			companyId=null;
//		}
		
//		Integer userId = SessionManager.getCurrentUserId(getRequest());
		String toLoginName = getStringParameter("toLoginName");
		Integer replyShortMessageId = getIntegerParameter("replyShortMessageId");
		
		ResponseData rd = service.displaySend(null, null, toLoginName, replyShortMessageId);
//		ResponseData rd = service.displaySend(companyId, userId, toLoginName, replyShortMessageId);
		
		Map data = rd.getData();
		getRequest().setAttribute("mateUserList", data.get("mateUserList"));
		getRequest().setAttribute("user", data.get("user"));
		getRequest().setAttribute("shortMessage", data.get("shortMessage"));
		
		return "/dealer/shortMessage/send";
	}
		
	@RequestMapping(value="/sendWrite.vti",  method=RequestMethod.POST)
	public String sendWrite(@ModelAttribute("shortMessage") ShortMessage shortMessage) {	
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		Company currentOrg  ;
		if(userId!=null)
		{
			currentOrg = CacheCompanyManager.getCompany(userId);
		}else{
			currentOrg =null;
		}
		 
		String fromLoginName = SessionManager.getCurrentUserLoginName(getRequest());
		String isSmsShortMessageSend = getStringParameter("isSmsShortMessageSend");
		String  userNickName = SessionManager.getCurrentUserNickName(getRequest());
		
		ResponseData rd =  service.sendWrite(shortMessage, userId, currentOrg, fromLoginName, 
				isSmsShortMessageSend, userNickName, getRequest());
		
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displayOutboxList(new TransMsg());
			
		}else if (rd.getCode() == 2) {
			String toLoginName = (String) rd.getFirstItem();
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, toLoginName);

		}else {
			message(GlobalConstant.MSG_RESULT_LEVEL_WARNING, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			return displaySend();
			//return mapping.findForward("alertAndHistoryBack");
			//return displaySend(mapping, form1, request, response);
		}
		return displayOutboxList(new TransMsg());
		
	}
	
	/**
	 * 收件箱列表
	 * @param transMsg
	 * @return
	 */
	@RequestMapping(value="/inboxList.vti",  method=RequestMethod.GET)
	public String displayInboxList(TransMsg transMsg) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = service.displayInboxList(transMsg, userId);
		
		Map data = rd.getData();
		getRequest().setAttribute("inboxList", data.get("inboxList"));
		getRequest().setAttribute("inboxCount", data.get("inboxCount"));
		
		getRequest().setAttribute("transMsg", transMsg);
		
		return "/dealer/shortMessage/inbox";
	}
	
	/**
	 * 收件信息导出EXCEL
	 * @author tangqingsong 
	 * @version 0.1 
	 * @param transMsg
	 */
	@RequestMapping(value = "/exportVehicle.vti", method = RequestMethod.GET)
	public void exportShowMessage(TransMsg transMsg) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		List<ShortMessage> shortMessageList = service.getExportShortMessageList(transMsg, userId);
		//导出文件
		showMessageExcel(shortMessageList);
	}

	
	/**
	 * 导出车辆列表数据到excel
	 * @author tangqingsong 
	 * @version 0.1 
	 * @param shortMessageList
	 */
	public void showMessageExcel(List<ShortMessage> shortMessageList) {
		
		String[] titles = {"类型","vin码","设备号","发生时间"};
		
		String fileName ="";
	
		fileName="站内消息"+DateTime.toShortDateTime(new Date());

		HSSFWorkbook excel = new HSSFWorkbook();
		HSSFSheet sheet = excel.createSheet("站内消息");
		HSSFRow firstRow = sheet.createRow(0);
		HSSFCell cells[] = new HSSFCell[titles.length];
	
		for (int i = 0; i < titles.length; i++) {
			cells[0] = firstRow.createCell(i);
			cells[0].setCellValue(titles[i]);
		}
		
		if (shortMessageList != null && shortMessageList.size() > 0) {
			for (int i = 0; i < shortMessageList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				ShortMessage shortMessage = shortMessageList.get(i);
				HSSFCell cell = row.createCell(0);
				int y = 0;
				cell.setCellValue(shortMessage.getMessageType());
				cell = row.createCell(++ y);
				cell.setCellValue(shortMessage.getVin());
				cell = row.createCell(++ y);
				cell.setCellValue(shortMessage.getSerialNumber());
				cell = row.createCell(++ y);
				cell.setCellValue(DateTime.toNormalDateTime(shortMessage.getCreationTime()));
				cell = row.createCell(++ y);
			}
		}
		
		ExportExcelUtil.sendFileToUser(fileName, excel, getResponse(), getRequest().getHeader("User-Agent"));

	}
	
	@RequestMapping(value="/deleteOutbox.vti",  method=RequestMethod.GET)
	public String deleteOutbox(@RequestParam("shortMessageId") Integer shortMessageId) {		
		ResponseData rd = service.deleteOutbox(shortMessageId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		return displayOutboxList(new TransMsg());
	}
	
	@RequestMapping(value="/deleteOutboxAll.vti",  method=RequestMethod.GET)
	public String deleteOutboxAll(@RequestParam( value= "messageIds", required = false)Integer[] ids) {	
	if(ids==null)
	{
		message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
				GlobalConstant.MSG_RESULT_CONTENT, "请选择消息再删除!");
	}else{
		ResponseData rd = service.deleteOutboxAll(ids);
			if (rd.getCode() == 1) {
				message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			}
	}
		return displayOutboxList(new TransMsg());
	}
		
	@RequestMapping(value="/outboxDetail.vti",  method=RequestMethod.GET)
	public String displayOutboxDetail(@RequestParam("shortMessageId") Integer shortMessageId) {
		ResponseData rd = service.displayOutboxDetail(shortMessageId);
		
		Map data = rd.getData();
		getRequest().setAttribute("shortMessageForOutbox", data.get("shortMessageForOutbox"));
		getRequest().setAttribute("user", data.get("user"));
		
		return "/dealer/shortMessage/outboxDetail";
	}
	
	@RequestMapping(value="/inboxDetail.vti",  method=RequestMethod.GET)
	public String displayInboxDetail(@RequestParam("shortMessageId") Integer shortMessageId) {
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = service.displayInboxDetail(shortMessageId, userId);
		
		getRequest().setAttribute("shortMessageForInbox", rd.get("shortMessageForInbox"));
		getRequest().setAttribute("user", rd.get("user"));
		
		return "/dealer/shortMessage/inboxDetail";
	}
	
	@RequestMapping(value="/deleteInbox.vti",  method=RequestMethod.GET)
	public String deleteInbox(@RequestParam("shortMessageId") Integer shortMessageId) {		
		ResponseData rd = service.deleteInbox(shortMessageId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		return displayInboxList(new TransMsg());
	}
		
	@RequestMapping(value="/deleteInboxAll.vti",  method=RequestMethod.GET)
	public String deleteInboxAll(@RequestParam( value= "messageIds", required = false)Integer[] messageIds) {	
		
		if(messageIds==null)
		{
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, "请选择消息再删除!");
		}else{
			ResponseData rd = service.deleteInboxAll(messageIds);
			if (rd.getCode() == 1) {
			    message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS, GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
			}
		}
		return displayInboxList(new TransMsg());
	}

	/**	检查新站内消息的方法	*/
	@RequestMapping(value="/checkNew.vti",  method=RequestMethod.GET)
	public void checkNewShortMessage(){
		ResponseData rd = new ResponseData(0);
		Integer userId = SessionManager.getCurrentUserId(getRequest());
		rd = service.checkNewShortMessage(userId);
		writeRd(rd);
	}
}
