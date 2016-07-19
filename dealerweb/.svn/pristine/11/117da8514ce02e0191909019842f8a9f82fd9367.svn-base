package com.ava.baseCommon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ava.base.controller.Base4MvcController;
import com.ava.baseCommon.service.IDownloadApiService;

@Controller
@RequestMapping("/base/common")
public class BaseDownloadController extends Base4MvcController {

	@Autowired
	private IDownloadApiService service;

	/**整个系统使用的下载接口*/
	@RequestMapping(value = "/download.vti", method = RequestMethod.GET)
	public void writeOutStream() {
		String filePath = getStringParameter("filePath");
		String displayName = getStringParameter("displayName");
		String userAgent = getRequest().getHeader("User-Agent");
		service.writeOutStream(getResponse(), filePath, displayName,
				userAgent);
	}
}
