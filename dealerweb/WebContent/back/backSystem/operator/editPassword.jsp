<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
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
		<div align="left" class="style4"><strong>当前位置：</strong>系统管理&gt;
		操作员管理&gt;密码修改</div>
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
<table width="100%" height="132" border="0" align="center"
	cellpadding="2" cellspacing="1" class="tablebg">
	<form name="theForm" action="/back/operator/editPassword.vti" method="post">
		<tr>
			<td width="32%" class="bgcolor">
			<div align="right">原密码：</div>
			</td>
			<td width="68%" bgcolor="#FFFFFF">
				<input type="password" name="oldPassword" value="" maxlength="18">
			</td>
		</tr>
		<tr>
			<td width="32%" class="bgcolor">
			<div align="right">新密码：</div>
			</td>
			<td bgcolor="#FFFFFF">
				<input type="password" name="newPassword1" value="" maxlength="18">
			</td>
		</tr>
		<tr>
			<td width="32%" class="bgcolor">
			<div align="right">密码确认：</div>
			</td>
			<td bgcolor="#FFFFFF">
				<input type="password" name="newPassword2" value="" maxlength="18">
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td class="40td">
			<div align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
			</td>
			<td class="40td">
				<input type="submit" class="bgcolor" value="确  定" />
				<input type="reset" value="清　空 " class="bgcolor">
			</td>
		</tr>
	</form>
</table>
</body>
</html>
