package com.ava.admin.job;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ava.admin.domain.vo.TaskMessageVo;
import com.ava.admin.service.IReHandleTestDriveService;
import com.ava.resource.GlobalConstant;

public class TestDriveTask implements Runnable {
	private Logger loger=Logger.getLogger(TestDriveTask.class);
	private IReHandleTestDriveService reHandleTestDriveService;
	private Map<String,Object> taskMessageMap;
	
	public TestDriveTask(IReHandleTestDriveService reHandleTestDriveService,Map<String,Object> taskMessageMap){
		this.reHandleTestDriveService = reHandleTestDriveService;
		this.taskMessageMap = taskMessageMap;
	}
	
	@Override
	public void run() {
		try{
			reHandleTestDriveService.reHandle(taskMessageMap);
		}catch (Exception e) {
			e.printStackTrace();
			TaskMessageVo taskMessageVo = (TaskMessageVo) taskMessageMap.get("taskMessageVo");
			loger.error("重跑任务运行错误："+e.getMessage()+"taskID:"+taskMessageVo.getId());
			taskMessageVo.setStatus(GlobalConstant.TASK_STAUTS_ERROR);
			reHandleTestDriveService.updateTaskMessage(taskMessageVo);
		}
	}

}
