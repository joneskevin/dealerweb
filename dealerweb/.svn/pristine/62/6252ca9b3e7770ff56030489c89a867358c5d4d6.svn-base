package com.ava.util.upload;

public class ReportItemFactory {
	
	public static ReportItemImpl Create(String fileName, long beginTime, long uploadSize, long uploadTime,
			ReportItemImpl reportItem) {
		if (reportItem == null) {
			return new DefaultReportItem(fileName, beginTime, uploadSize, uploadTime);
		} else {
			DefaultReportItem defautItem = (DefaultReportItem) reportItem;
			//defautItem.reload();
			defautItem.setFileName(fileName);
			defautItem.setBeginTime(beginTime);
			defautItem.setUploadSize(uploadSize);
			defautItem.setUploadTime(uploadTime);
			return defautItem;
		}
	}
}
