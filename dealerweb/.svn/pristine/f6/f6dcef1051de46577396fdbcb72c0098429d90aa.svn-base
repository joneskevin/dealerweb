<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.SessionManager,com.ava.resource.GlobalConstant"%>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
<script language='javascript'>
function checkOnDelete(){
	if( confirm("确定要删除您所选择的记录吗？") ){
		return true;
	}else{
		return false;
    }
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
		<div align="left" class="style4"><strong>当前位置：</strong>系统管理 &gt; 操作员管理
		&gt; 操作员列表</div>
	</tr>
	<tr>
		<td width="83%" height=23 nowrap class="bgcolor"><strong>管理菜单：</strong>
		<%
		if (SessionManager.getCurrentOperator(request).hasRightOfAddOperator()){		
		%>
		<a href='/back/operator/displayAdd.vti' target="_self">新增操作员</a>
		<%} %>
		</td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
		<div align="right"><a href="javascript:history.back();"><img
			src="/back/images/backto.gif"
			border="0"></a><a href="javascript:window.location.reload();"><img
			src="/back/images/refresh.gif"
			border="0"></a></div>
		</td>
	</tr>
</table>
<table width="100%" border="0" align="center" cellpadding="2"
	cellspacing="1" class="tablebg">
	<form id="myPageForm" action="/back/operator/search.vti" method="get">
		<tr class="bgcolor">
			<td width="5%" height="25" class="bgcolor">操作员ID</td>
			<td width="15%" height="25">登录名</td>
			<td width="10%" height="25">用户姓名</td>
			<td width="25%" height="25">上次登录时间</td>
			<td width="20%" height="25" colspan=2>操作</td>
		</tr>
		<c:if test="${operators != null}">
			<c:forEach var="operator" items="${operators}">
				<tr bgcolor="#FFFFFF" onmouseover="this.bgColor='#DFEDEA'"
					onmouseout="this.bgColor='#FFFFFF'">
					<td align="center"><c:out value="${operator.id}" /></td>
					<td title="点击该条记录进行修改"><a
						href="/back/operator/displayEdit.vti?operatorId=<c:out value="${operator.id}" />">
						<c:out value="${operator.loginName}" /></a></td>
					<td><c:out value="${operator.name}" /></td>
					<td><fmt:formatDate value="${operator.lastLoginTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td colspan=2>
					<%
					if (SessionManager.getCurrentOperator(request).hasRightOfGrantRight()){		
					%>
					<a href="/back/operator/displayGrant.vti?operatorId=<c:out value="${operator.id}" />&pageNo=<%=request.getAttribute(GlobalConstant.PAGE_NO)%>">分配权限</a>
					<%
					}
					%>
					<%
					if (SessionManager.getCurrentOperator(request).hasRightOfDeleteOperator()){		
					%>
					<a href="/back/operator/delete.vti?operatorId=<c:out value="${operator.id}" />&pageNo=<%=request.getAttribute(GlobalConstant.PAGE_NO)%>" onclick="return checkOnDelete()">删除</a>
					<%
					}
					%>
					</td>
					
				</tr>
			</c:forEach>
			<tr bgcolor="#FFFFFF">
				<td colspan=40 align=right>
					<div align="right">
					<jsp:include page="/pub/navigate/navigation.jsp" flush="true"/>
					</div>
				</td>
			</tr>
		</c:if>
		<c:if test="operators == null">
			<tr bgcolor="#FFFFFF">
				<td colspan=8 align=right>Empty result</td>
			</tr>
		</c:if>
	</form>
</table>
</body>
</html>
