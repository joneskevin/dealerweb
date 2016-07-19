<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ include file="/pub/jstl.jsp" %>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
<script language='javascript'>
function setPermission(){
	$("#permissionCode").attr("value", tree2.getAllCheckedBranches());
}
var tree2;
$(function(){
	tree2 = createTree('tree_rightsCode', "<c:out value='${rightsTreeXml}' escapeXml='false'/>", '<c:out value="${operator.permissionCode}"/>', true);
});
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
			<div align="left" class="style4">
				<strong>当前位置：</strong>系统管理 &gt; 操作员管理 &gt; 权限分配
			</div>
	</tr>
	<tr>
		<td width="83%" height=23 nowrap class="bgcolor">
			<strong>管理菜单：</strong>
		</td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
			<div align="right">
				<a href="javascript:history.back();"><img
						src="/back/images/backto.gif"
						border="0">
				</a><a href="javascript:window.location.reload();"><img
						src="/back/images/refresh.gif"
						border="0">
				</a>
			</div>
		</td>
	</tr>
</table>
<form name="theForm" action="/back/operator/grant.vti" method="post">
<input type="hidden" name="pageNo"  value="<%=request.getAttribute(GlobalConstant.PAGE_NO)%>" />
<input type="hidden" name="operatorId"  value="<c:out value="${operator.id}"/>" />
<input type="hidden" id="permissionCode" name="permissionCode" value="<c:out value="${permissionCode}"/>" />
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="tablebg">
	<tr bgcolor="#FFFFFF">
		<td align=right colspan=8>
			<div align="left">
				<span><strong>被选择用户：</strong>
				<c:out value="${operator.name}"/>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span> 
				<input type="submit" class="bgcolor" value="分配权限" onclick="setPermission();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				(选中的项的操作权限将赋予被选中用户) 
				</span>
			</div>
		</td>
	</tr>
	<tr>
		<td bgcolor="#e7f5f0" height="25">
			<div id="tree_rightsCode"
				style="width:550; height:680;background-color:#f5f5f5;border :1px solid Silver;; overflow:auto;"></div>
		</td>
		<td bgcolor="#e7f5f0" align=center>
			<div>
				<a href="javascript:void(0);"
					onclick="alert(tree2.getAllChecked());"></a>
				<a href="javascript:void(0);"
					onclick="alert(tree2.getAllCheckedBranches());"></a>
			</div>
		</td>
	</tr>
</table>
</form>
</body>
</html>
