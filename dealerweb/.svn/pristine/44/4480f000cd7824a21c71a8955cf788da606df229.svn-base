package com.ava.util.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ava.base.domain.ResponseData;
import com.ava.exception.ProtocolParseBusinessException;
import com.ava.resource.GlobalConfig;
import com.ava.resource.GlobalConstant;

/**
 * @author guohong.heng
 * 
 * @create 
 * 
 * 
 * @description 上传帮助类
 * 
 */
public class UploadHelper {
	
	private static Logger logger=Logger.getLogger(UploadHelper.class);
	
	public static void validatorListFiles(List<MultipartFile> multipartFiles){
		if(null==multipartFiles || multipartFiles.isEmpty())
			throw new ProtocolParseBusinessException("msgResultContent","不能上传空文件");
		long fileTotalLength=0l;
		if(multipartFiles.size()>10)
			throw new ProtocolParseBusinessException("msgResultContent","总文件数量不能超过10个");
		for(MultipartFile multipartFile : multipartFiles){
			validatorFile(multipartFile);
			fileTotalLength+=multipartFile.getSize();
		}
		long fileValidLength=getMaxFileLength();
		if(fileTotalLength>fileValidLength)
			throw new ProtocolParseBusinessException("msgResultContent","总文件大小不能超过30M");
	}
	public static void validatorFile(MultipartFile multipartFile) {
		if(null==multipartFile || multipartFile.isEmpty()){
			throw new ProtocolParseBusinessException("msgResultContent","不能上传空文件");
		}
		if (!validateLengthFile(multipartFile, getMaxFileLength())) {
			throw new ProtocolParseBusinessException("msgResultContent","单个文件大小不能超过30M");
		}
		if (!validateAllowFile(multipartFile, getAllowExtName())) {
			throw new ProtocolParseBusinessException("msgResultContent","不符合文件上传格式要求");
		}
	}
	
	/**
	 * @descrption 根据HttpServletRequest对象获取MultipartFile集合
	 * @author guohong.heng
	 * @create 
	 * @param request
	 * @param maxLength
	 *            <strong>文件</strong>最大限制
	 * @param allowExtName
	 *            不允许上传的<strong>文件</strong>扩展名
	 * @return MultipartFile集合
	 */
	public static Map getFileSet(HttpServletRequest request,int existsFileNum,long existsFileSize) {
		Map map=new HashMap();
		MultipartHttpServletRequest multipartRequest = null;
		long totalSize=existsFileSize;
		multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = new LinkedList<MultipartFile>();
		files = multipartRequest.getFiles("attach");
		if(null==files || files.isEmpty())
			throw new ProtocolParseBusinessException("msgResultContent","上传文件为空");
		
		if((existsFileNum+files.size())>getMaxFileNum()){
			throw new ProtocolParseBusinessException("msgResultContent","每个报备最多只能上传10个");
		}
		// 移除不符合条件的
		for (int i = 0; i < files.size(); i++) {
			if(null==files.get(i) || files.get(i).isEmpty()){
				throw new ProtocolParseBusinessException("msgResultContent","不能上传空文件");
			}
			if (!validateLengthFile(files.get(i), getMaxFileLength())) {
				throw new ProtocolParseBusinessException("msgResultContent","单个文件大小超过30M");
			}
			if (!validateAllowFile(files.get(i), getAllowExtName())) {
				throw new ProtocolParseBusinessException("msgResultContent","不符合文件上传格式要求");
			}
			totalSize+=(files.get(i).getSize());
		}
		if(totalSize>getMaxFileLength()){
			throw new ProtocolParseBusinessException("msgResultContent","每个报备所上传文件总大小不能超过30M");
		}
		map.put("multipartFileList", files);
		return map;
	}
	
	/**
	 * 新装申请上传
	 * @author liuq 
	 * @version 0.1 
	 * @param files
	 * @param existsFileNum
	 * @param existsFileSize
	 * @return
	 */
	public static ResponseData getFileSet(List<MultipartFile> files,int existsFileNum,long existsFileSize) {
		ResponseData rd = new ResponseData(GlobalConstant.TRUE);
		long totalSize=existsFileSize;
		if(null==files || files.isEmpty()) {
			rd.setCode(-1);
			rd.setMessage("上传文件为空");
			return rd;
		
		}
		
		if((existsFileNum+files.size())>getMaxFileNum()){
			rd.setCode(-1);
			rd.setMessage("每个申请最多只能上传10个");
			return rd;
		}
		// 移除不符合条件的
		for (int i = 0; i < files.size(); i++) {
			if(null==files.get(i) || files.get(i).isEmpty()){
				rd.setCode(-1);
				rd.setMessage("不能上传空文件");
				return rd;
			}
			if (!validateLengthFile(files.get(i), getMaxFileLength())) {
				rd.setCode(-1);
				rd.setMessage("单个文件大小超过30M");
				return rd;
			}
			if (!validateAllowFile(files.get(i), getAllowExtName())) {
				rd.setCode(-1);
				rd.setMessage("不符合文件上传格式要求");
				return rd;
			}
			totalSize+=(files.get(i).getSize());
		}
		if(totalSize>getMaxFileLength()){
			rd.setCode(-1);
			rd.setMessage("每个申请所上传文件总大小不能超过30M");
			return rd;
		}
		rd.put("multipartFileList", files);
		return rd;
	}

	/**
	 * @descrption 保存<strong>文件</strong>
	 * @author guohong.heng
	 * @create 
	 * @param file
	 *            MultipartFile对象
	 * @param path
	 *            保存路径，如“D:\\File\\”
	 * @return 保存的全路径 如“D:\\File\\2345678.txt”
	 * @throws Exception
	 *             <strong>文件</strong>保存失败
	 */
	public static String[] uploadFile(MultipartFile file,int filingProposalId){
		try {
			String rtnName[]=new String[4];
			String originalFileName = file.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
			String saveFileName =genFileName() + extName;
			// 图片存储的全路径
			String fileFullPath = genFilePath(filingProposalId) + File.separator+saveFileName;
			FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
			rtnName[0]=originalFileName;
			rtnName[1]=saveFileName;
			rtnName[2]=fileFullPath;
			rtnName[3]=extName;
			return rtnName;
		} catch (Exception e) {
			throw new ProtocolParseBusinessException("msgResultContent","上传文件失败");
		}
	}
	
	/**
	 * 
	 * @param file
	 * @param proposalId
	 * @return
	 */
	public static String[] uploadHelpFile(MultipartFile file){
		try {
			String rtnName[]=new String[4];
			String originalFileName = file.getOriginalFilename();
			String extName = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
			String fileFullPath = getFileBasePath() + "/" + originalFileName;
			FileCopyUtils.copy(file.getBytes(), new File(fileFullPath));
			rtnName[0] = originalFileName;
			rtnName[1] = originalFileName.substring(0,originalFileName.indexOf("."));
			rtnName[2] = fileFullPath;
			rtnName[3]=extName;
			return rtnName;
		} catch (Exception e) {
			throw new ProtocolParseBusinessException("msgResultContent","上传文件失败");
		}
	}

	public static void downloadFile(String fileName,HttpServletResponse response) {
		File file =null;
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			file = getDownloadFile(fileName);
			
			String simpleFileName = URLEncoder.encode(fileName.substring(fileName.lastIndexOf(File.separator), fileName.length()), "UTF-8");
			
			//response.setContentType("application/zip");
			//response.addHeader("Content-Disposition","attachment;filename=" + fileName);
			
			//response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition","attachment;filename=" + simpleFileName);
			//response.setHeader("Content-Disposition", "filename="+ new String(filename.getBytes("gb2312"), "iso8859-1"));
			String fileType = simpleFileName.substring(simpleFileName.lastIndexOf("."), simpleFileName.length());
			setResponseHeader(response,fileType);
			
			outp = response.getOutputStream();
			in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e1) {
			throw new ProtocolParseBusinessException("msgResultContent","查看文件失败");
		}finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=file)
				file=null;
		}
	}
	
	public static void downloadHelpFile(String fileName,HttpServletResponse response) {
		File file =null;
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			String filePath=getHelpFileBasePath();
			if (!filePath.endsWith(File.separator)) {
				filePath += File.separator+fileName;
			}else{
				filePath+=fileName;
			}
			file =getDownloadFile(fileName);
			
			String simpleFileName = URLEncoder.encode(fileName.substring(fileName.lastIndexOf(File.separator), fileName.length()), "UTF-8");
			fileName = URLEncoder.encode(fileName, "UTF-8");
			
//			response.setContentType("application/zip");
			response.addHeader("Content-Disposition","attachment;filename=" + simpleFileName);
			
			//response.setHeader("Content-Disposition", "filename="+ new String(filename.getBytes("gb2312"), "iso8859-1"));
			String fileType = simpleFileName.substring(simpleFileName.lastIndexOf("."), simpleFileName.length());
			setResponseHeader(response,fileType);
			
			outp = response.getOutputStream();
			in = new FileInputStream(file);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e1) {
			throw new ProtocolParseBusinessException("msgResultContent","获取文件失败");
		}finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					logger.info("下载文件时,关闭数据流时IO异常。"+e.getMessage());
					in = null;
				}
			}
			if (outp != null) {
				try {
					outp.close();
					outp = null;
				} catch (IOException e) {
					logger.info("下载文件时,关闭数据流时IO异常。"+e.getMessage());
					outp = null;
				}
			}
			if(null!=file)
				file=null;
		}
	}
	
	/**
	 * 创建文件夹并生成目录
	 * @return
	 */
	private static String genFilePath(int filingProposalId){
		String filePath=getFileBasePath();
		if (!filePath.endsWith(File.separator)) {
			filePath += File.separator+new SimpleDateFormat("yyyyMM").format(new Date())+File.separator+filingProposalId;
		}else{
			filePath+=new SimpleDateFormat("yyyyMM").format(new Date())+File.separator+filingProposalId;
		}
		File baseDirectory = new File(filePath);
		if (!baseDirectory.isDirectory()) {
			baseDirectory.mkdirs();
		}
		return filePath;
	}
	
	private static String genFileName(){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+new Random().nextInt(1000);
	}

	public static boolean delFile(String fileName){
		File file=new File(fileName);
		return file.delete();
	}
	/**
	 * @descrption 验证<strong>文件</strong>格式，这里主要验证后缀名
	 * @author guohong.heng
	 * @create 
	 * @param file
	 *            MultipartFile对象
	 * @param maxLength
	 *            <strong>文件</strong>最大限制
	 * @param allowExtName
	 *            不允许上传的<strong>文件</strong>扩展名
	 * @return <strong>文件</strong>格式是否合法
	 */
	private static boolean validateAllowFile(MultipartFile file, String[] allowExtName) {
		String filename = file.getOriginalFilename();
		// 处理不选择<strong>文件</strong>点击上传时，也会有MultipartFile对象，在此进行过滤
		if (filename == "") {
			return false;
		}
		String extName = filename.substring(filename.lastIndexOf(".")+1)
				.toLowerCase();
		if (allowExtName == null || allowExtName.length == 0) {
			return false;
		} else if(Arrays.binarySearch(allowExtName, extName) != -1){
			return true;
		} else {
			return true;
		}
	}
	
	/**
	 * @descrption 验证<strong>文件</strong>格式，这里主要验证后缀名
	 * @author guohong.heng
	 * @create 
	 * @param file
	 *            MultipartFile对象
	 * @param maxLength
	 *            <strong>文件</strong>最大限制
	 * @param allowExtName
	 *            不允许上传的<strong>文件</strong>扩展名
	 * @return <strong>文件</strong>格式是否合法
	 */
	private static boolean validateLengthFile(MultipartFile file, long maxLength) {
		if (file.getSize() < 0 || file.getSize() > maxLength)
			return false;
		return true;
	}
	
	private static String getFileBasePath(){
		return GlobalConfig.getString("FILE_SAVE_BASE_PATH");
	}

	private static String getHelpFileBasePath(){
		return GlobalConfig.getString("FILE_HELP_BASE_PATH");
	}
	
	private static String getFileBaseBackupPath(){
		return GlobalConfig.getString("FILE_SAVE_BASE_BACKUP_PATH");
	}

	private static String getHelpFileBaseBackupPath(){
		return GlobalConfig.getString("FILE_HELP_BASE_BACKUP_PATH");
	}
	
	private static long getMaxFileLength(){
		String fileLength = GlobalConfig.getString("MAX_FILE_LENGTH");
		return null==fileLength || "".equals(fileLength) ? 0L : Long.parseLong(fileLength);
	}
	private static long getMaxFileNum(){
		String fileNum = GlobalConfig.getString("MAX_FILE_NUM");
		return null==fileNum || "".equals(fileNum) ? 0L : Integer.parseInt(fileNum);
	}

	private static String[] getAllowExtName(){
		String fileLength = GlobalConfig.getString("ALLOW_FILE_EXT_NAME");
		if(null!=fileLength && !"".equals(fileLength.trim())) {
			return fileLength.split("\\|");
		}
		return null;
	}
	
	public void previewFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OutputStream out=null;
		FileInputStream in=null;
		try {
			
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String fileName = request.getParameter("fileName");
			fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8").trim();//文件名<br><br>		
			String path = request.getRealPath("/")+"webs/";
			String target = path+"file"+File.separator+"workflow"+File.separator+fileName;//文件存储的位置<br>		
			String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			response.reset(); //清空response	
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			out = response.getOutputStream();
			in = new FileInputStream(target);
			
			fileName = URLEncoder.encode(fileName, "utf-8");
			fileType.toUpperCase();
			setResponseHeader(response,fileType);
			int n = 0;
			byte b[] = new byte[1024];
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			out.flush();
		} catch (Exception e) {
			throw new ProtocolParseBusinessException("msgResultContent","查看文件失败");
		} finally {
			if (in != null) {
				in.close();
				in=null;
			}
			if (out != null) {
				out.close();
				out=null;
			}
		}
	}

	/**
	 * 查看文件
	 * 
	 * @param config
	 * @param response
	 * @param downLoadFileName
	 */
	public static void previewFile(String filePath,HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,filePath.length());
			//fileName = URLEncoder.encode(fileName, "UTF-8");
			//response.reset();
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition","attachment;filename=" + fileName);
			//response.setHeader("Content-Disposition", "filename="+ new String(filename.getBytes("gb2312"), "iso8859-1"));
			String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
			setResponseHeader(response,fileType);
			bis = new BufferedInputStream(new FileInputStream(filePath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesread;
			while (-1 != (bytesread = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesread);
			}
			bos.flush();
			response.flushBuffer();
		} catch (Exception e) {
			throw new ProtocolParseBusinessException("msgResultContent","查看文件失败");
		} finally {
			try{
				if (bis != null){
					bis.close();
					bis=null;
				}
				if (bos != null){
					bos.close();
					bos=null;
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	private static void setResponseHeader(HttpServletResponse response, String fileType) {
		fileType=fileType.toUpperCase();
		if (".DOC".equals(fileType)) {
			response.setHeader("Content-Type", "application/msword");
		} else if (".DOCX".equals(fileType)) {
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		} else if (".PDF".equals(fileType)) {
			response.setHeader("Content-Type", "application/pdf");
		} else if (".TXT".equals(fileType)) {
			response.setHeader("Content-Type", "text/html");
		} else if (".XLS".equals(fileType)) {
			response.setHeader("Content-Type", "application/vnd.ms-excel");
		} else if (".XLSX".equals(fileType)) {
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		} else if (".PPT".equals(fileType)) {
			response.setHeader("Content-Type", "application/vnd.ms-powerpoint");
		} else if (".PPTX".equals(fileType)) {
			response.setHeader("Content-Type",
					"application/vnd.openxmlformats-officedocument.presentationml.presentation");
		} else if (".BMP".equals(fileType)) {
			response.setHeader("Content-Type", "image/bmp");
		} else if (".GIF".equals(fileType)) {
			response.setHeader("Content-Type", "image/gif");
		} else if (".IEF".equals(fileType)) {
			response.setHeader("Content-Type", "image/ief");
		} else if (".JPEG".equals(fileType)) {
			response.setHeader("Content-Type", "image/jpeg");
		} else if (".JPG".equals(fileType)) {
			response.setHeader("Content-Type", "image/jpeg");
		} else if (".PNG".equals(fileType)) {
			response.setHeader("Content-Type", "image/png");
		} else if (".TIFF".equals(fileType)) {
			response.setHeader("Content-Type", "image/tiff");
		} else if (".TIF".equals(fileType)) {
			response.setHeader("Content-Type", "image/tif");
		}
	}
	
	/**
	 * 根据下载文件的路径判断是否已存在文件，如上传目录不存在此文件，修改文件路径为为备份路径
	 *
	 * @author wangchao
	 * @version 
	 * @param fullName 下载文件的绝对路径
	 * @return
	 */
	private static File getDownloadFile( String fullName){
		//非空判断
		if(StringUtils.isEmpty(fullName)) return null;
		
		try {
			File tempFile = new File(fullName);
			if(!tempFile.exists()){//根据文件路径找不到此文件，说明已经移到备份目录了
				if(fullName.startsWith(getFileBasePath())){//说明是车辆相关文件
					fullName = fullName.replaceAll(getFileBasePath(), getFileBaseBackupPath());
					tempFile = new File(fullName); 
				}
				
				if(fullName.startsWith(getHelpFileBasePath())){//说明是车辆相关文件
					fullName = fullName.replaceAll(getHelpFileBasePath(), getHelpFileBaseBackupPath());
					tempFile = new File(fullName); 
				}
				return tempFile;
			} else {
				return tempFile;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}
	
	public static void main(String[] args){
		String fileName = "/var/lib/tomcat7/logs/uploadFile/dealervw/201507/1.jpg";
		String basePath = "/var/lib/tomcat7/logs/uploadFile/dealervw";
		String backupPath = "/uploadfiles/uploadFile/dealervw";
		if(fileName.startsWith(basePath)){//说明是车辆相关文件
			fileName = fileName.replaceAll(basePath, backupPath);
			System.out.println(fileName);
		}
		
	}
}
