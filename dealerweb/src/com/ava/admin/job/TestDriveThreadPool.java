package com.ava.admin.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ava.resource.GlobalConfig;



public class TestDriveThreadPool {
	
//    private ScheduledThreadPoolExecutor scheduledPool = new ScheduledThreadPoolExecutor(1);
	
	//创建一个可重用固定线程数的线程池
    public static ExecutorService threadPool = null;
    static{
    	int poolSize = Integer.parseInt(GlobalConfig.getString("thread_pool_max_size"));
    	threadPool = Executors.newFixedThreadPool(poolSize);
    }

    
	private TestDriveThreadPool(){
		
	}
	
	
//	private void run(){
//		scheduledPool.scheduleAtFixedRate(new Runnable() {
//                      @Override
//                      public void run() {
//                           //throw new RuntimeException();
//                           System.out.println("开始检查是否有任务运行");
//                      }
//                  }, 1000*10, 6000, TimeUnit.MILLISECONDS);
//	}
	
	/**
	 * 执行任务
	 * @author tangqingsong
	 * @version 
	 * @param currentGpsMessageVo
	 */
	public static void execute(Runnable task){
//		ReHandleTestDriveTask task = new ReHandleTestDriveTask(reHandleTestDriveService, currentGpsMessageVo);
		threadPool.execute(task);
	}
	
	public static void stop(){
		if(threadPool!=null){
			threadPool.shutdown();
		}
		//scheduledPool.shutdown();
	}
	
	
}
