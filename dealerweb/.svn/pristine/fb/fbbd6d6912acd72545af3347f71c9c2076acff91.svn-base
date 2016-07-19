package com.ava.dealer.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.ava.dealer.service.IHelpFileService;
import com.ava.domain.entity.HelpFile;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;
import com.ava.resource.SessionManager;
import com.ava.util.upload.UploadHelper;

@Controller
@RequestMapping("/dealer/helpFile")
public class HelpFileController extends Base4MvcController {
	
	Logger logger = Logger.getLogger(HelpFileController.class);
	
	@Autowired
	private IHelpFileService helpFileService;
	
	@RequestMapping(value = "/list.vti", method = RequestMethod.GET)
	public String list(TransMsg transMsg, @ModelAttribute("helpFile") HelpFile helpFile) {
		ResponseData rd = helpFileService.listHelpFile(transMsg, helpFile);

		getRequest().setAttribute("helpFileList", rd.getFirstItem());
		getRequest().setAttribute("transMsg", transMsg);

		siteMenuType(rd);
		return "/dealer/helpFile/listHelpFile";
	}
	
	/**
	 * 显示帮助文档上传界面
	 * @return
	 */
	@RequestMapping(value="/displayUpload.vti", method=RequestMethod.GET)
	public String displayUpload() {
		getRequest().setAttribute("startIndex", getIntegerParameter("startIndex"));
		
		return "/dealer/helpFile/uploadHelpFile";
	}

	/**
	 * 文件上传
	 * @return
	 */
	@RequestMapping(value="/upload.vti", method=RequestMethod.POST)
	public String upload(HttpServletRequest request) {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> multipartFiles = new LinkedList<MultipartFile>();
		multipartFiles = multipartRequest.getFiles("attach");
		Integer currentUserId = SessionManager.getCurrentUserId(getRequest());
		ResponseData rd = helpFileService.upload(multipartFiles, currentUserId);
		if (rd.getCode() == 1) {
			
			TransMsg transMsg = new TransMsg();
			transMsg.setStartIndex(getIntegerParameter("startIndex"));
			return "/pub/jsPages/closeDialogAndReloadParent";
			
		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}
		
		return displayUpload();
	}
	
	@RequestMapping(value = "/getAttachFileDown.vti", method = RequestMethod.GET)
	public void getAttachFileDown(@RequestParam("helpFileId") Integer helpFileId) {
		try {
			HelpFile helpFile = helpFileService.get(helpFileId);
			UploadHelper.downloadFile(helpFile.getFullPath(),getResponse());
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "系统异常");
		}
	}
	
	@RequestMapping(value = "/delete.vti", method = RequestMethod.GET)
	public void delete(@RequestParam("helpFileId") Integer helpFileId) {
		ResponseData rd = helpFileService.delete(helpFileId);
		if (rd.getCode() == 1) {
			message(GlobalConstant.MSG_RESULT_LEVEL_SUCCESS,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());

		} else {
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,
					GlobalConstant.MSG_RESULT_CONTENT, rd.getMessage());
		}

		writeRd(rd);
	}
}
