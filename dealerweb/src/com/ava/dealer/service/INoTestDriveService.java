package com.ava.dealer.service;

import java.util.List;

import com.ava.domain.vo.TestDriveVO;

public interface INoTestDriveService {
	
	/** 查询连续无试驾列表 
	 * @param season 季度*/
	public List<TestDriveVO> listNoTestDrive(Integer season);
	
	
}
