<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.ava.resource.GlobalConfig"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=GlobalConfig.getDomainName()%> - 后台管理系统</title>
</head>
<frameset rows="*" cols="162,*" framespacing="0" frameborder="1" border="false" id="frame" scrolling="no">
  <frame name="left" scrolling="auto" marginwidth="0" marginheight="0" src="<%=request.getContextPath()%>/back/indexLeft.jsp">
  <frameset framespacing="0" border="false" rows="35,*" frameborder="0" scrolling="yes">
    <frame name="top" scrolling="no" src="<%=request.getContextPath()%>/back/indexTop.jsp">
    <frame name="main" scrolling="yes" src="<%=request.getContextPath()%>/back/indexMain.jsp">
  </frameset>
</frameset>
<noframes>
  <body leftmargin="2" topmargin="0" marginwidth="0" marginheight="0">
  <p>你的浏览器版本过低！！！本系统要求IE6及以上版本才能使用本系统。</p>
  </body>
</noframes>
</html>
