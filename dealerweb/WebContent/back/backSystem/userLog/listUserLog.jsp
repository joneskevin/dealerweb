<%@ page contentType="text/html; charset=utf-8" language="java"	errorPage=""%>
<%@ include file="/pub/jstl.jsp" %>
<%
	String conferenceStartDate="";
  //  MonitorUserLog monitorUserLog =new MonitorUserLog();
%>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
</head>

<script>
$(document).ready(function() {  
	$("#startTime").datepicker(); 
	$("#endTime").datepicker(); 
});
</script>

<body>
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr align="center">
		<td height=25 colspan=2 bgcolor="#39867B">
		<div align="left" class="style4"><strong>当前位置：</strong>系统管理&gt;
		日志管理&gt;用户日志</div>
	</tr>
	<tr>
		<td width="81%" height=23 nowrap class="bgcolor"><strong>用户日志：</strong></td>
		<td width="23%" height=23 valign="bottom" nowrap class="bgcolor">
		<div align="right"><a href="javascript:history.back();"><img
			src="/back/images/backto.gif" border="0"></a><a
			href="javascript:window.location.reload();"><img
			src="/back/images/refresh.gif" border="0"></a></div>
			<div id="deadlineHour"> 
			</div>
		</td>
	</tr>
</table>

<form id="myPageForm" action="/back/userLog/search.vti" method="get">
<table width="100%" border="0" cellpadding="2" cellspacing="1"
	class="border">
	<tr valign="bottom" bgcolor="#FFFFFF">
	   <td width="40">开始时间：
	     <span>
	      	<input id="startTime" name="startTime" class="size date"  value="<c:out value="${startTime}"/>" readonly="readonly" type="text" />
		</span>
	     --结束时间：
		 <span>		  
	      	<input id="endTime" name="endTime" class="size date"  value="<c:out value="${endTime}"/>" readonly="readonly" type="text" />
		  </span>
	    </td>
	   	<tr align="center" bgcolor="#FFFFFF">
			<td colspan="4" height="40" class="40td">
				<input type="submit" value="搜  索" class="bgcolor">
			</td>
		</tr>
	</tr>
</table>		

<table width="100%" border="0" align="center" cellpadding="2"
	cellspacing="1" class="tablebg">
	<tr class="bgcolor">
		<td width="60" height="25" class="bgcolor">用户ID</td>
		<td width="60" height="25">操作ID</td>
		<td width="60" height="25">操作名</td>
		<td width="110" height="25">操作者所属公司</td>
		<td width="108" height="25">操作时间</td>
		<td width="170" height="25">操作详情</td>
		<td width="50" height="25" >备注</td>
	</tr>
	<c:if test="${userLogs != null}">
		<c:forEach var="monitorUserLog" items="${userLogs}">
			<tr bgcolor="#FFFFFF" onmouseover="this.bgColor='#DFEDEA'"
				onmouseout="this.bgColor='#FFFFFF'">
				 
				 <!--<td align="center"><c:out value="${monitorUserLog}" /></td> -->

				<td><c:out value="${monitorUserLog.userName}" /></td>
				<td><c:out value="${monitorUserLog.actionId}" /></td>
				<td><c:out value="${monitorUserLog.actionName}" /></td>

				<td><c:out value="${monitorUserLog.companyName}" /></td>
				<td><fmt:formatDate value="${monitorUserLog.actionTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		
				 <td title='${monitorUserLog.urlAndParameters}'>
          			<c:out value="${fn:length(monitorUserLog.urlAndParameters) > 60 ? fn:substring(monitorUserLog.urlAndParameters, 0, 60) : monitorUserLog.urlAndParameters}" />
           			<c:out value="${fn:length(monitorUserLog.urlAndParameters) > 60 ? '...' : ''}" />
				</td>
				<td><c:out value="${monitorUserLog.remark}" /></td>
			</tr>
		</c:forEach>
		<tr bgcolor="#FFFFFF">
			<td colspan=40 align=right>
			<div align="right">
			<jsp:include page="/pub/navigate/navigation.jsp" flush="true"/></div>
			</td>
		</tr>
	</c:if>
	<c:if test="operators == null">
		<tr bgcolor="#FFFFFF">
			<td colspan=8 align=right>Empty result</td>
		</tr>
	</c:if>
</table>
</form>
</body>
</html>
