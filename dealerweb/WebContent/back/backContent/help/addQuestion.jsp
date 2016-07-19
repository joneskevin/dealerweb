<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
</head>
<body>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
		<div align="left" class="style4"><strong>当前位置：</strong>帮助中心&gt;
		新增帮助</div>
	</tr>
	<tr>
		<td width="81%" height=23 nowrap class="bgcolor"><strong>管理菜单：</strong></td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
		<div align="right"><a href="javascript:history.back();"><img
			src="/back/images/backto.gif" border="0"></a><a
			href="javascript:window.location.reload();"><img
			src="/back/images/refresh.gif" border="0"></a></div>
		</td>
	</tr>
</table>
<form id="myPageForm" action="/back/question/add.vti" method="post">
<table width="100%" height="132" border="0" align="center"
	cellpadding="2" cellspacing="1" class="tablebg">
    	<tr>
        	<th colspan="2" height="30" class="bgcolor" style=" font:normal 13px;" align="center">新增BASE帮助</th>
        </tr>
		<tr>
			<td width="32%" bgcolor="#e7f5f0">
			<div align="right">标题：</div>
			</td>
			<td width="68%" bgcolor="#FFFFFF">
				<form:input path="question.title" size="78"/>
			</td>
		</tr>
        <tr>
			<td width="32%" bgcolor="#e7f5f0">
			<div align="right">选择问题标识：</div>
			</td>
			<td width="68%" bgcolor="#FFFFFF">
		        <form:select path="question.sortLevelId" items="${questionSortsLevel3IdList}" itemLabel="optionText" itemValue="optionValue">
		        </form:select>
			</td>
		</tr>
		<tr>
			<td width="32%" bgcolor="#e7f5f0">
				<div align="right">内容：</div>
			</td>
			<td bgcolor="#FFFFFF">	
				<form:hidden id="FCKeditor1" path="question.content" />
	       		<iframe id="FCKeditor1_Frame" src="/pub/fck/editor/fckeditor.html?InstanceName=FCKeditor1&Toolbar=Default" 
		   			frameborder=0 width="730" height="400" scrolling=no></iframe>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td class="40td">
			<div align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
			</td>
			<td class="40td">
				<input name="" type="submit" class="bgcolor" value="新  增">
				<input name="" type="reset" class="bgcolor" value="重 置">
			</td>
		</tr>
</table>
</form>
</body>
</html>
