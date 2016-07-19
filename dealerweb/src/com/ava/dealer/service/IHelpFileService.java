package com.ava.dealer.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.domain.entity.HelpFile;

public interface IHelpFileService {
	
	public ResponseData listHelpFile(TransMsg transMsg, HelpFile helpFile);
	
	public ResponseData upload(List<MultipartFile> multipartFiles, Integer userId);
	
	public ResponseData delete(Integer helpFileId);
	
	public HelpFile get(Integer helpFileId);
	
}
