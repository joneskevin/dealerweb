<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ page import="java.util.*,com.jspsmart.upload.*,com.ava.util.*,com.ava.resource.*"%>

<%
//如果图片上传的处理页面

// 新建一个SmartUpload对象
SmartUpload su=new SmartUpload();
// 上传初始化
su.initialize(pageContext);
// 设定上传限制
// 1.限制每个上传文件的最大长度。默认：10M
su.setMaxFileSize(GlobalConstant.UPLOAD_FILE_MAX_SIZE);
// 2.限制总上传数据的长度。
// su.setTotalMaxFileSize(5000000);
// 3.设定允许上传的文件（通过扩展名限制）
su.setAllowedFilesList(UploadUtil.getAllowedFileNames());
// 4.设定禁止上传的文件（通过扩展名限制）,禁止上传带有exe,bat,sh等扩展名的文件和没有扩展名的文件。
su.setDeniedFilesList(UploadUtil.getDeniedFileNames());

//上传文件
try {
	su.upload();
}catch (SecurityException ex) {
	//out.println(ex);
	out.println("<script language='javascript'>");
	out.println("<!--");
	out.println("alert('文件上传失败，可能是该文件类型不允许上传或文件太大！\\n只允许上传这几种文件：" + UploadUtil.getAllowedFileNames() 
			+ "，请注意文件类型的大小写是否正确！\\n请注意文件大小请限制在" + GlobalConstant.UPLOAD_FILE_MAX_SIZE_NICK + "之内！')");
	out.println("history.back()");
	out.println("//-->");
	out.println("</script>");
	return;
}

//upload files to special directory
//int count=su.save("/upload");

com.jspsmart.upload.File file=su.getFiles().getFile(0);
String filepath=null;
String filename=null;
String fileExt=null;
String picpath=null;
String virtualPath="";
String physicalPath="";
boolean success=true;

if(file.isMissing()){
	success=false;
	out.println("failure");
}else{
	success=true;
	fileExt=file.getFileExt().toLowerCase();
	Date now=new Date();
	filename=now.getTime()+"."+fileExt;		
	filepath = "/upload/" + DateTime.getYear() + "/" + DateTime.getMonth() + "/" + DateTime.getDay() + "/";
	
	FileUtil.mkDir( GlobalConfig.getDefaultAppPath() + filepath );
	virtualPath = filepath+filename;						//得到原始图片带文件名的虚拟路径
	file.saveAs(virtualPath, su.SAVE_VIRTUAL);
	picpath = virtualPath;
}

if(success){
	response.sendRedirect("upload.jsp?act=view&picpath=" + picpath);
}else{
	out.println("<script language='javascript'>");
	out.println("<!--");
	out.println("history.back()");
	out.println("alert('上传失败，重新上传!')");
	out.println("//-->");
	out.println("</script>");
}

%>