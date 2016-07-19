<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<%
	String roleString = (String)request.getAttribute("roleString");
%>
<body>

<div class="allContent">
	<table border="0" cellpadding="0" cellspacing="0" 
		class="tableBox">
		<tr>
			<td class="tdTitle">所属单位：</td>
			<td class="tdSpace">${user.companyName}</td>
		</tr>
		<tr>
			<td class="tdTitle">账号：</td>
			<td class="tdSpace">${user.loginName}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">登录密码：</td>
			<td class="tdSpace">${user.pseudoPassword}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">名称：</td>
			<td class="tdSpace">${user.nickName}
			</td>
		</tr>
		<tr>
			<td class="tdTitle">手机：</td>
			<td class="tdSpace">${user.mobile}</td>
		</tr>
		<tr>
			<td class="tdTitle">电子邮件：</td>
			<td class="tdSpace">${user.email}</td>
		</tr>
 		<tr>
		    <td class="tdTitle">禁用：</td>
		    <td class="tdSpace">
			<c:if test="${user.isDisable == 1}">
			<font color="red">是 </font>
			</c:if>
			<c:if test="${user.isDisable == 0}">
			<font color="red">否 </font>
			</c:if>
			</td>
		  </tr>
		  <tr>
		    <td class="tdTitle">角色：</td>
		    <td class="tdSpace">${user.roles}</td>
		  </tr>
	</table>
</div>

</body>
</html>