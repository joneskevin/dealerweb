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
		<div align="left" class="style4"><strong>当前位置：</strong>系统管理&gt;
		操作员管理&gt;编辑操作员</div>
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
<form id="myPageForm" action="/back/operator/edit.vti" method="post">
<input id="id" name="id" type="hidden" value="<c:out value="${frmOperator.id}" />" />
<table width="100%" height="132" border="0" align="center"
	cellpadding="2" cellspacing="1" class="tablebg">
    	<tr>
        	<th colspan="2" height="30" class="bgcolor" style=" font:normal 13px;" align="center">新增BASE帮助</th>
        </tr>
		<tr>
			<td width="32%" bgcolor="#e7f5f0">
			<div align="right"><font color=red>*</font>登录名称：</div>
			</td>
			<td width="68%" bgcolor="#FFFFFF">
				<form:input path="frmOperator.loginName" size="78"/>
			</td>
		</tr>
		<tr>
			<td width="32%" bgcolor="#e7f5f0">
			<div align="right"><font color=red>*</font>登录密码：</div>
			</td>
			<td width="68%" bgcolor="#FFFFFF">
				<form:password path="frmOperator.loginPassword" size="16"/>
			</td>
		</tr>
		<tr>
			<td width="32%" bgcolor="#e7f5f0">
			<div align="right"><font color=red>*</font>真实姓名：</div>
			</td>
			<td width="68%" bgcolor="#FFFFFF">
				<form:input path="frmOperator.name" size="78"/>
			</td>
		</tr>
		<tr bgcolor="#FFFFFF">
			<td class="40td">
			<div align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
			</td>
			<td class="40td">
				<input name="" type="submit" class="bgcolor" value="保 存">
				<input name="" type="reset" class="bgcolor" value="重 置">
			</td>
		</tr>
</table>
</form>
</body>
</html>
