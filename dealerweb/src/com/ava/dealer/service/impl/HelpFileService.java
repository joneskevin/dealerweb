package com.ava.dealer.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ava.base.dao.hibernate.TransMsg;
import com.ava.base.domain.ResponseData;
import com.ava.dao.IHelpFileDao;
import com.ava.dealer.service.IHelpFileService;
import com.ava.domain.entity.HelpFile;
import com.ava.resource.GlobalConstant;
import com.ava.util.upload.UploadHelper;

@Service
public class HelpFileService implements IHelpFileService {
	
	@Autowired
	private IHelpFileDao helpFileDao;
	

	public ResponseData listHelpFile(TransMsg transMsg, HelpFile helpFile) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		transMsg.setPageSize(GlobalConstant.DEFAULT_TABLE_ROWS);
		String additionalCondition = "order by id desc";
		List<HelpFile> helpFileList = helpFileDao.findByPage(transMsg, additionalCondition);
		
		rd.setFirstItem(helpFileList);
		return rd;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResponseData upload(List<MultipartFile> multipartFiles, Integer userId) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		ResponseData fileRd = new ResponseData(0);
		//校验上传的文件是否符合规则
		fileRd = UploadHelper.getFileSet(multipartFiles, 0, 0);
		if (fileRd.getCode() != 1) {
			rd.setCode(fileRd.getCode());
			rd.setMessage(fileRd.getMessage());
			return rd;
		}
		multipartFiles= (List<MultipartFile>) fileRd.get("multipartFileList");
		if (multipartFiles != null && multipartFiles.size() > 0) {
			for(MultipartFile multipartFile : multipartFiles){
				HelpFile helpFile = helpFileDao.getOriginalName(multipartFile.getOriginalFilename());
				if (helpFile != null) {
					rd.setCode(-1);
					rd.setMessage("文件【" + multipartFile.getOriginalFilename() + "】重复！");
					return rd;
				}
			}
			for(MultipartFile multipartFile : multipartFiles){
				HelpFile helpFile = new HelpFile();
				String rtnFile[] = UploadHelper.uploadHelpFile(multipartFile);
				if (rtnFile != null) {
					helpFile.setCreateId(userId);
					helpFile.setCreateTime(new Date());
					helpFile.setOriginalName(rtnFile[0]);
					helpFile.setDisplayName(rtnFile[1]);
					helpFile.setFullPath(rtnFile[2]);
					helpFile.setType(rtnFile[3]);
					helpFile.setSize(String.valueOf(multipartFile.getSize()));
					helpFile.setStatus(1);
					
					helpFileDao.save(helpFile);
				}
			}
		}
		return rd;
	}


	@Override
	public ResponseData delete(Integer helpFileId) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		helpFileDao.delete(helpFileId);
		return rd;
	}

	@Override
	public HelpFile get(Integer helpFileId) {
		HelpFile helpFile = helpFileDao.get(helpFileId);
		return helpFile;
	}
	

}
