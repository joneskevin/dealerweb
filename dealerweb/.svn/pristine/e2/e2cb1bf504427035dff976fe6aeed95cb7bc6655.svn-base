<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ include file="/pub/jstl.jsp" %>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
</head>
<script language=JavaScript>
function checkOnLogin(){
	var loginName = document.getElementById("loginName").value;
	if(loginName == null || loginName.length == 0){
		alert("用户登录名必须填写！");
		return false;
	}
	var loginPassword = document.getElementById("loginPassword").value;
	if(loginPassword == null || loginPassword.length == 0){
		alert("登录密码必须填写！");
		return false;
	}
	return true;
}

$().ready(function(){
	if(window.parent != window.top){
		window.top.location.href='/back/operator/logout.vti';
	}
});
</script>

<body bgcolor="#ffffff" text="#000000" link="#0000ff" vlink="#800080"
	alink="#ff0000">
<table>
	<tr>
		<td height="120"></td>
	</tr>
</table>
<form name="loginForm" action="/back/operator/login.vti" method="post">
	<table width="395" height="230" border="0" align="center"
		cellpadding="0" cellspacing="0"
		background="<%=request.getContextPath()%>/back/images/login_line.gif">
		<tr>
			<td height="92" valign="top"><img
				src="<%=request.getContextPath()%>/back/images/admin_login_top.gif"
				width="395" height="87" /></td>
		</tr>
		<tr>
			<td>
			<table width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="11%" height="30"><img
						src="<%=request.getContextPath()%>/back/images/login_tp1.gif"
						width="24" height="22" hspace="5" /></td>
					<td width="22%">登录名称</td>
					<td width="67%">
						<input type="text" name="loginName" value="" id="loginName" class="input150" maxlength="20">
					</td>
				</tr>
				<tr>
					<td height="30"><img
						src="<%=request.getContextPath()%>/back/images/login_tp2.gif"
						width="24" height="22" hspace="5" /></td>
					<td>登录密码</td>
					<td>
						<input type="password" name="loginPassword" value="" id="loginPassword" class="input150" style="width:134px;" maxlength="18">
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>
					<div align="left">
						<input name="operatorLoginAction"  type="submit" class="bgcolor" value="登 录" onclick="return checkOnLogin();" />
					</div>
					</td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td><img
				src="<%=request.getContextPath()%>/back/images/login_bottom.gif"
				width="395" height="8" /></td>
		</tr>
	</table>
</form>
</body>
</html>
