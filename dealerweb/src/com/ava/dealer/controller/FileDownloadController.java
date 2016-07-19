package com.ava.dealer.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.ava.base.controller.Base4MvcController;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConstant;
import com.ava.util.upload.UploadHelper;

@Controller
@RequestMapping("/dealer/fileList")
public class FileDownloadController extends Base4MvcController{
	
	Logger logger=Logger.getLogger(FileDownloadController.class);
		
	/**
	 * 
	* Description: 附件管理下载
	* @author henggh 
	* @version 0.1 
	* @param filingProposalId
	* @param filingProposalAttachmentId
	 */
	@RequestMapping(value = "/download.vti", method = RequestMethod.GET)
	public void declareAttachFileDown(@RequestParam("fileName")  String filingName) {
		try {
			logger.info("start download file:"+filingName);
			UploadHelper.downloadHelpFile(filingName,getResponse());
			logger.info("end download file:"+filingName);
		}catch(ProtocolParseBusinessException ex){
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,ex.getResultCode(), ex.getDesc());
		}catch(Exception e){
			logger.info(e.getMessage());
			message(GlobalConstant.MSG_RESULT_LEVEL_ERROR,"msgResultContent", "获取文件失败");
		}
	}
}