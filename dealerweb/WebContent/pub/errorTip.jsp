<%@ page contentType="text/html; charset=utf-8"%>
<%@ page session="false" %>
<%@ include file="/pub/jstl.jsp" %>
<%!
    private static final org.apache.commons.logging.Log LOGGER = org.apache.commons.logging.LogFactory.getLog("Page_Errors");
%>
<%

	String errorLog = "";
	int statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
	String message = (String) request.getAttribute("javax.servlet.error.message");
	String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
	String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
	Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
	String throwableMessage = t == null ? "" : t.getMessage();
	Class e = (Class) request.getAttribute("javax.servlet.error.exception_type");
	String exceptionName = e == null? "" : e.getName();
	if(statusCode == 500) {
	    LOGGER.error(statusCode + "|" + message + "|" + servletName + "|" + uri + "|" + exceptionName + throwableMessage);  
	} else if(statusCode == 404) {
	    LOGGER.error(statusCode + "|" + message + "|" + servletName + "|" + uri); 
	}
	
	String queryString = request.getQueryString();
	String url = uri + (queryString == null || queryString.length() == 0 ? "" : "?" + queryString);
	url = url.replaceAll("&amp;", "&").replaceAll("&", "&amp;");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面<%=statusCode%>错误</title>
<link href="<%=request.getContextPath()%>/css/error.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/CommonUtil.js"></script>
</head>

<body>
<div class="main">
	<div class="top"></div>
    <div class="content">
    	<div class="logo"><img src="<%=request.getContextPath()%>/images/error/logo.png" /></div>
        <div class="title">系统提示</div>
        <div class="theme">您访问的页面或者记录不存在。</div>
        <div class="tips">
	        <p>
	        <%
				if(statusCode == 404) {
			%>
				对不起,暂时没有找到您所访问的页面地址,请联系管理员解决此问题.
				<br/>
				<br/>
				<a href="<%=url%>">刷新,看看是否能访问了</a>
				<br/>
			<%
				} else {
			%>
				对不起,您访问的页面出了一点内部小问题,请重新访问,或者先去别的页面转转,过会再来吧~
				<br/>
				<br/>
			<%
				}
			%>
				<br/>
				<br/>
			</p>
		</div>
        <div class="btn"><input onclick="javascript:window.history.back();" type="button" value="返回" />
        </div>
    </div>
    <div class="footer"></div>
</div>
</body>
</html>
