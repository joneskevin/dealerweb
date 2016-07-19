package com.ava.util.upload;

public class DefaultReportItem implements ReportItemImpl {

	private String fileName = null;

	private long beginTime = 0L;

	private long uploadTime = 0L;

	private long uploadSize = 0L;

	public DefaultReportItem(String fileName, long beginTime, long uploadSize, long uploadTime) {
		this.fileName = fileName;
		this.beginTime = beginTime;
		this.uploadSize = uploadSize;
		this.uploadTime = uploadTime;
	}

	public void reload() {
		fileName = null;
		beginTime = 0L;
		uploadSize = 0L;
		uploadTime = 0L;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public String getFileName() {
		return fileName.replaceAll("[^\\\\+\\\\+]+\\\\", "");
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getUploadSize() {
		return uploadSize;
	}

	public void setUploadSize(long uploadSize) {
		this.uploadSize = uploadSize;
	}

	public long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(long uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getTotalTime() {
		long totalS = (uploadTime - beginTime)/1000;
		return totalS + "S";
	}

	public float getUploadSizeMB() {
		float kbByteTotalSize = (float) uploadSize / 1000000F;
		return (float) Math.round(kbByteTotalSize * 100F) / 100F;
	}

	public float getUploadSizeKB() {
		float kbByteTotalSize = (float) uploadSize / 1000F;
		return (float) Math.round(kbByteTotalSize * 100F) / 100F;
	}

	public float getUploadSpeedKB() {
		float kbPerS = (float) uploadSize / (float) (uploadTime - beginTime);
		return (float) Math.round(kbPerS * 100F) / 100F;
	}

}
