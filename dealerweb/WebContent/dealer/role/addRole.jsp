<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
<%@ include file="/dealer/include/meta.jsp" %>
<%@ include file="/dealer/include/typeAndSubtypeCascader.jsp"%>
<script>

</script>

<body style="overflow-x:hidden ;">
<form method="post" action = "<%=request.getContextPath()%>/base/role/add.vti">
<table border="0" cellpadding="0" cellspacing="0"  class="tableBox">
	<tr>
		<td class="tdTitle"><span class="red">*</span>角色名称：</td>
		<td class="tdSpace">
		<form:input cssClass="size" maxlength="16" path="roleAdd.name"/><br/>
		</td>
	</tr>
	<tr>
		<td class="tdTitle">角色描述：</td>
		<td class="tdSpace">
		<form:textarea path="roleAdd.description" cssClass="size" cols="50" rows="6" />
		<br/>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="tdSpace" align="center">
			<input name="" type="submit" class="btn_submit" value="提 交" />
			<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
		</td>
	</tr>

</table>
</form>
</body>
</html>
