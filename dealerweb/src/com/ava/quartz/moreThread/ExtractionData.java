package com.ava.quartz.moreThread;

import java.util.Date;

import com.ava.facade.impl.ReportBaseFacade;

public class ExtractionData implements Runnable {
	
	  private String name;
	 
	    public ExtractionData(String name) {
	        this.name = name;
	    }
	    public ExtractionData(String name, ReportBaseFacade reportBaseFacade, Integer extractionFlag, 
	    		Integer dayNum, Date startDate, Date endDate, Integer extractionType) {
	    	reportBaseFacade.extraction(extractionFlag, dayNum, startDate, endDate);
	    	this.name = name;
	    }
	 
	    public void run() {
	        for (int i = 0; i < 4; i++) {
	            System.out.println(name + "运行     " + i);
	        }
	    }
	 
	    public static void main(String[] args) {
	    	ExtractionData h1=new ExtractionData("线程A");
	        Thread demo= new Thread(h1);
	        ExtractionData h2=new ExtractionData("线程Ｂ");
	        Thread demo1=new Thread(h2);
	        demo.start();
	        demo1.start();
	    }
	 
}
