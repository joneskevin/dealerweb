<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.resource.GlobalConfig"%>
<html>
<head>
<jsp:include page="/back/backMeta.jsp" flush="true">
	<jsp:param name="titleInner" value="" />
</jsp:include>
</head>
<body leftmargin="0" topmargin="0">
<table width="100%" border="0" cellpadding="2" cellspacing="1">
<tr align="center">
    <td height=25 colspan=2 bgcolor="#39867B" ><strong><%=GlobalConfig.getDomainName()%>后台信息管理系统</strong>
</tr>
<tr align="center"> 
    <td height=23 colspan=2 nowrap class="tdbg" ><strong>欢迎您来到<%=GlobalConfig.getDomainName()%>后台信息管理系统,请点击左侧菜单进行操作!</strong></td>
</tr>
</table>

<div align="center"><br>
    版权所有 <%=GlobalConfig.getDomainName()%> <BR>
Copyright(C) 1998-2008 All Rights Reserved. 
  <BR>
</div>
</body>
</html>

