package com.ava.admin.job;



import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ava.admin.service.IReHandleTestDriveService;
import com.ava.admin.service.impl.ReHandleTestDriveServiceImpl;
import com.ava.resource.GlobalConfig;

public class TestDriveJob  implements Job {

	private Logger loger=Logger.getLogger(ReHandleTestDriveServiceImpl.class);

	private IReHandleTestDriveService reHandleTestDriveService = (IReHandleTestDriveService) GlobalConfig.getBean("reHandleTestDriveService");
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
//		AnalyseReHandleTask delayTask = new AnalyseReHandleTask(0, reHandleTestDriveService);
//		TestDriveThreadPool.execute(delayTask);
		loger.info("启动任务重跑机制...");
		//延迟上报的串形执行，也免跑重
		reHandleTestDriveService.analyseByDelay();
		//重跑失败出错的任务
		reHandleTestDriveService.failTaskRehandle();
		//4S店内和无效试驾可以并发跑；
		AnalyseReHandleTask invalidTask = new AnalyseReHandleTask(1, reHandleTestDriveService);
		TestDriveThreadPool.execute(invalidTask);
		AnalyseReHandleTask inCommpanyTask = new AnalyseReHandleTask(2, reHandleTestDriveService);
		TestDriveThreadPool.execute(inCommpanyTask);
	}

}
