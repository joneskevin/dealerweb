package com.ava.admin.job;

import com.ava.admin.service.IReHandleTestDriveService;

public class AnalyseReHandleTask  implements Runnable {
    private int taskType;//0 为延迟试驾分析，1 为无效试驾分析  2 为4S店内试驾分析
	private IReHandleTestDriveService reHandleTestDriveService;
	
    public AnalyseReHandleTask(int taskType,IReHandleTestDriveService reHandleTestDriveService){
    	this.taskType = taskType;
    	this.reHandleTestDriveService = reHandleTestDriveService;
    }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(taskType==0){
			reHandleTestDriveService.analyseByDelay();
		}else if(taskType==1){
			reHandleTestDriveService.analyseByInvalid();
		}else if(taskType==2){
			reHandleTestDriveService.analyseByInCommpany();
		}
	}

}
