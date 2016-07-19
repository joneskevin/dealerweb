package com.ava.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ava.base.domain.ResponseData;
import com.ava.base.domain.UploadReturnData;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;

public class UploadUtil {
	public static String getAllowedFileNames() {
		List allowedNameList = new ArrayList();
		allowedNameList.addAll(permissiblePictureExtNames);
		allowedNameList.addAll(permissibleVedioExtNames);
		allowedNameList.addAll(permissibleDocumentExtNames);
		allowedNameList.addAll(permissibleCompressionExtNames);
		
		String names = TypeConverter.join(allowedNameList, ",", null, null);
		if (names != null) {
			return names;
		}
		return "";
	}

	public static String getDeniedFileNames() {
		String str = TypeConverter.join(unPermissibleExtNames, ",", null,
				null);
		if (str != null) {
			return str;
		}
		return "";
	}

	static List permissiblePictureExtNames = new ArrayList();
	static {
		permissiblePictureExtNames.add("bmp");
		permissiblePictureExtNames.add("jpg");
		permissiblePictureExtNames.add("jpeg");
		permissiblePictureExtNames.add("png");
		permissiblePictureExtNames.add("gif");
	}

	static List permissibleVedioExtNames = new ArrayList();
	static {
		permissibleVedioExtNames.add("swf");
		permissibleVedioExtNames.add("flv");
	}

	static List permissibleDocumentExtNames = new ArrayList();
	static {
		permissibleDocumentExtNames.add("doc");
		permissibleDocumentExtNames.add("docx");
		permissibleDocumentExtNames.add("xls");
		permissibleDocumentExtNames.add("xlsx");
		permissibleDocumentExtNames.add("ppt");
		permissibleDocumentExtNames.add("pptx");
		permissibleDocumentExtNames.add("vsd");
		permissibleDocumentExtNames.add("vsdx");
		permissibleDocumentExtNames.add("xml");
		permissibleDocumentExtNames.add("pdf");
		permissibleDocumentExtNames.add("txt");
	}

	static List permissibleCompressionExtNames = new ArrayList();
	static {
		permissibleCompressionExtNames.add("rar");
		permissibleCompressionExtNames.add("zip");
	}

	static List unPermissibleExtNames = new ArrayList();
	static {
		unPermissibleExtNames.add("");
		unPermissibleExtNames.add("exe");
		unPermissibleExtNames.add("bat");
		unPermissibleExtNames.add("sh");
		unPermissibleExtNames.add("js");
		unPermissibleExtNames.add("jsp");
		unPermissibleExtNames.add("jspx");
		unPermissibleExtNames.add("java");
		unPermissibleExtNames.add("html");
		unPermissibleExtNames.add("htm");
		unPermissibleExtNames.add("ftl");

	}

	public static boolean isPermissiblePictureExtNames(String fileExtendName) {
		if (TypeConverter.sizeLagerZero(fileExtendName)) {
			if (permissiblePictureExtNames.contains(fileExtendName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPermissibleVedioExtNames(String fileExtendName) {
		if (TypeConverter.sizeLagerZero(fileExtendName)) {
			if (permissibleVedioExtNames.contains(fileExtendName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPermissibleDocumentExtNames(String fileExtendName) {
		if (TypeConverter.sizeLagerZero(fileExtendName)) {
			if (permissibleDocumentExtNames.contains(fileExtendName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPermissibleCompressionExtNames(String fileExtendName) {
		if (TypeConverter.sizeLagerZero(fileExtendName)) {
			if (permissibleCompressionExtNames.contains(fileExtendName)) {
				return true;
			}
		}
		return false;
	}

	/** 检查文件后缀名是否允许上传类型 */
	public static boolean isPermissibleExtNames(String fileExtendName) {
		if (TypeConverter.sizeLagerZero(fileExtendName)) {
			// 上传文件有后缀名
			if (isPermissiblePictureExtNames(fileExtendName)
					|| isPermissibleVedioExtNames(fileExtendName)
					|| isPermissibleDocumentExtNames(fileExtendName)
					|| isPermissibleCompressionExtNames(fileExtendName)) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}

	public static int getGroupByExtName(String extendName) {
		int groupId = -1;
		if (isPermissiblePictureExtNames(extendName)) {
			groupId = GlobalConstant.UPLOAD_TYPE_GROUP_PICTURE;
		} else if (UploadUtil.isPermissibleVedioExtNames(extendName)) {
			groupId = GlobalConstant.UPLOAD_TYPE_GROUP_VEDIO;
		} else if (UploadUtil.isPermissibleDocumentExtNames(extendName)) {
			groupId = GlobalConstant.UPLOAD_TYPE_GROUP_DOCUMENT;
		} else if (UploadUtil.isPermissibleCompressionExtNames(extendName)) {
			groupId = GlobalConstant.UPLOAD_TYPE_GROUP_COMPRESSION;
		} else {
			groupId = GlobalConstant.UPLOAD_TYPE_GROUP_OTHER;
		}
		return groupId;
	}

	/**
	 * 参数： uploadPath：保存路径父目录（相对地址），系统会自动在其下建按日期分类的文件夹
	 */
	public static ResponseData upload(String originalFilename, InputStream is, String uploadPath) {
		int maxSize = GlobalConstant.UPLOAD_FILE_MAX_SIZE;
		return upload(originalFilename, is, uploadPath, maxSize, true, true, true, true);
	}

	/**
	 * 参数： uploadPath：保存路径父目录（相对地址），系统会自动在其下建按日期分类的文件夹 maxSize：允许上传文件的最大大小 单位：字节
	 * isPicture：允许上传文件的类型是图片类
	 * isVedio：允许上传文件的类型是视频类
	 * isDocument：允许上传文件的类型是文档类
	 * isCompression：允许上传文件的类型是压缩包类
	 */
	public static ResponseData upload(String originalFilename, InputStream is, String uploadPath,
			int maxSize, boolean isPicture, boolean isVedio,
			boolean isDocument, boolean isCompression) {
		ResponseData rd = new ResponseData(0);
		UploadReturnData urd = new UploadReturnData();
		rd.setFirstItem(urd);

		if (is == null || "".equals(originalFilename)) {
			rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 1);
			rd.setMessage("未选择上传文件");
			return rd;
		}
		String originalFileName = null;
		String fileExtendName = null;
		originalFileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
		urd.setOriginalFileName(originalFileName);// 最初文件名

		fileExtendName = FileUtil.getExtName(originalFilename);
		fileExtendName = fileExtendName.toLowerCase();
			
		if (isPicture) {
			if (!isPermissiblePictureExtNames(fileExtendName)) {
				rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 2);
				rd.setMessage("文件扩展名不合法，上传的文件不是图片");
				return rd;
			}
		} else if (isVedio) {
			if (!isPermissibleVedioExtNames(fileExtendName)) {
				rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 2);
				rd.setMessage("文件扩展名不合法，上传的文件不是视频");
				return rd;
			}
		} else if (isDocument) {
			if (!isPermissibleDocumentExtNames(fileExtendName)) {
				rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 2);
				rd.setMessage("文件扩展名不合法，上传的文件不是文档");
				return rd;
			}
		}else if(isCompression) {
			if (!isPermissibleCompressionExtNames(fileExtendName)) {
				rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 2);
				rd.setMessage("文件扩展名不合法，上传的文件不是压缩包");
				return rd;
			}
		}

		String systemFileName = "";
		String relevantPath = null;
		if (TypeConverter.sizeLagerZero(uploadPath)) {
			relevantPath = uploadPath + GlobalConstant.UploadPathName_Date;
		} else {
			relevantPath = GlobalConstant.UploadPathName
					+ GlobalConstant.UploadPathName_Date;
		}
		try {
			String fullSavePath = GlobalConfig.getDefaultAppPath()
					+ relevantPath;
			FileUtil.mkDir(fullSavePath + GlobalConstant.FILE_SEPARATOR);

			systemFileName = String.valueOf(new Date().getTime()) + "_"
					+ (int) (Math.random() * 100000) + "." + fileExtendName;
			urd.setSystemFileName(systemFileName);// 系统生成的文件名
			urd.setFullPath(relevantPath + GlobalConstant.FILE_SEPARATOR
					+ systemFileName);// 带文件名的完全路径

			float totalBytes = uploadData(fullSavePath, systemFileName, is, maxSize);
			if (TypeConverter.isEqualIntValue(totalBytes, -1)) {
				urd.setFileSize(0);// 文件大小
				FileUtil.delFile(fullSavePath + GlobalConstant.FILE_SEPARATOR
						+ systemFileName);
				rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 3);
				rd.setMessage("上传文件不能超过" + FileUtil.toMbSize(maxSize) + "M");
				return rd;
			} else if (totalBytes > 0) {
				urd.setFileSize(totalBytes);// 文件大小
				rd.setCode(1);
				rd.setMessage("上传成功");
				return rd;
			} else {
				FileUtil.delFile(fullSavePath + GlobalConstant.FILE_SEPARATOR
						+ systemFileName);
				rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 4);
				rd.setMessage("上传失败，文件大小为0");
				return rd;
			}
		} catch (Exception e) {
			rd.setCode(GlobalConstant.MESSAGE_MODULE_ID_UPLOAD - 5);
			rd.setMessage("上传失败");
			return rd;
		}
	}

	/***
	 * 限制每个上传文件的最大长度:maxSize(单位为字节) 返回上传文件的大小，单位为字节;
	 ***/
	private static float uploadData(String fileSavePath, String fileName,
			InputStream stream, int maxSize) {
		float totalBytes = 0;
		try {
			OutputStream bos = new FileOutputStream(fileSavePath
					+ GlobalConstant.FILE_SEPARATOR + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((bytesRead = stream.read(buffer, 0, 1024 * 8)) != -1) {
				bos.write(buffer, 0, bytesRead);
				totalBytes = totalBytes + bytesRead;
				if (totalBytes > maxSize) {
					totalBytes = -1;// 文件大小超出限制
					bos.close();
					stream.close();
					return totalBytes;
				}
			}
			bos.close();
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// log.info("The IOException has been happened when uploading the "+fileName+" file!");
			e.printStackTrace();
			FileUtil.delFile(fileSavePath + GlobalConstant.FILE_SEPARATOR
					+ fileName);
		}
		return totalBytes;
	}
}