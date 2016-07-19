<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script language=JavaScript>
function checkUploadForm(){ 
	var theFile = document.getElementById("theFile");
	var strFileName = theFile.value;
	if (strFileName == "" ){
		alert("请选择要上传的文件");
		return false;
	}
	
	return true;  
}
</script>


<body>
<div class="mainAll">
<div class="boxAll">
<div class="boxTitle boxAllBg">
<div class="iconBox2"><img src="<%=path%>/images/titleIcon_orgManage.gif" /></div>
<h1>报表管理</h1>
<div class="iconR"><img src="<%=path%>/images/titleIconBox02_assignment2.gif" /></div>
	<ul class="tags">
		<li class="selectTag"><span class="left"></span>
		<a href="<%=path%>/dealer/report/excelExport.vti" target="main" >excel导入</a><span class="right"></span>
		</li>
	</ul>
</div>

<div class="allContent">
<div class="pageRoute">当前位置：报表管理  > excel导入测试</div> <!--  enctype="multipart/form-data"  -->
<div class='msgResult<form:errors path="MSG_RESULT_LEVEL"/>' id='msg_result' style="display:none;"><form:errors path="MSG_RESULT"/></div>

<form action="<%=path%>/dealer/report/excelExportInput.vti"  method="post" onsubmit="return checkUploadForm();" enctype="multipart/form-data">
		<h3>选择excle：</h3>
		<p>可选择新的文件</p>
		<p>
		 <input type="file" id="theFile" name="userFile"  style="border:1px double rgb(88,88,88);font:9pt; width:190px; height:25px;" size="16" />
		</p>
  		<input type="submit" class="btnOne"  value="上传" style="border:1px double rgb(88,88,88);font:10pt;width:60px;height:22px;"/>
		<p>&nbsp;</p>
		<p>提示：如果您使用的浏览器上传不了头像，请用IE7及以上浏览器</p>

</form>

</div>
</div>
</div>
</body>
</html>