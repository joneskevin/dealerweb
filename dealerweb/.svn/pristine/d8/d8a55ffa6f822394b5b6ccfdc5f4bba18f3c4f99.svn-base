<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%
String act = request.getParameter("act");
if ("view".equals(act)) {//如果是图片上传后的显示处理页面
%>
<html>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<style type="text/css">
<!--
	BODY{font-size:9pt;}
	.tx1 { height: 20px;font-size: 9pt; border: 1px solid; border-color: #000000; color: #0000FF}
-->
</style>
<body leftmargin="0" topmargin="0" onLoad="setAttachmentPath()">
<%
	String viewpicpath = "";
	if (request.getParameter("picpath") != null && !"".equals(request.getParameter("picpath"))) {
		viewpicpath = request.getParameter("picpath");
	}
	if (viewpicpath == null || "".equals(viewpicpath)) {
%>
	<table bgcolor="#4c5155">
		<tr>
			<td valign="middle" height=140><br>
			<br>
			没有图片 <br>
			<br>
			<a href="upload.jsp?act=new">【添加图片】</a></td>
		</tr>
	</table>
<%
	}else{
		if (viewpicpath.toLowerCase().indexOf("wmv") > -1|| viewpicpath.toLowerCase().indexOf("avi") > -1) {
%>
		<embed width="150" border="0" showdisplay="0" showcontrols="1"
		autostart="1" autorewind="0" playcount="0" moviewindowwidth="150"
		filename="<%=viewpicpath%>" src="<%=viewpicpath%>"></embed>
<%
		}else if (viewpicpath.toLowerCase().indexOf("swf") > -1) {
%>
		<embed src="<%=viewpicpath%>" quality="high"
		pluginspage="http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash"
		type="application/x-shockwave-flash"></embed>
<%
		}else{
%>
		<img src="<%=viewpicpath%>" width="200">
<%
		}
%>
		<BR><BR><a href="upload.jsp?act=new">【修改图片】</a><BR><BR><BR>
<%
	}
%>
	<form method="post" name="form1"><input name="picpath" type="hidden" value="<%=viewpicpath%>"></form>
</body>
</html>
<%
} else if("new".equals(act)) {//如果是新增图片的处理页面
%>
<html>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<style type="text/css">
	<!--
	BODY{
	font-size:9pt
	}
	.tx1 { height: 20px;font-size: 9pt; border: 1px solid; border-color: #000000; color: #0000FF}
	-->
</style>
<script language=javascript>
	function check_file(){
	var strFileName=form1.FileName.value;
	if(strFileName==""){
		alert("请选择要上传的文件");
		return false;
	}	
	if (isPermissibleExtNames(strFileName)){
	    return true;
	}else{
	    alert("这种类型文件不允许上传！\r\n\r\n只允许上传这几种文件：" + _permissibleExtNames_ + "!");
		theFile.focus();
		return false;
	}
}
</script>
<body leftmargin="0" topmargin="0">
<form action="uploadprocess.jsp" method="post" name="form1" onSubmit="return check_file()" enctype="multipart/form-data">
<input name="picpath" type="hidden" value="">
<table bgcolor="#4c5155">
	<tr>
		<td valign="middle" height=180>
			<input name="FileName" type="FILE" class="tx1" size="20"> 
			<input type="submit" name="Submit" value="上传" style="border:1px double rgb(88,88,88);font:9pt">
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%
} else {
	System.out.println("upload.jsp........ unnormal accessed....");
	response.sendRedirect("upload.jsp?act=new");
}
%>
