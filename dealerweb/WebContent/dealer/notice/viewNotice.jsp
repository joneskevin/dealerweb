<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body>

<div class="pop-tableBox">
<form id="myPageForm" action="<%=request.getContextPath()%>/base/notice/add.vti" method="post">
<table style="width:95%; margin:0px 0;">
    <tr>
    	<td width="60px" align="right">公告名称：</td>
        <td width="390px" colspan="3">
        	${notice.title}
        </td>
    </tr>
   
    <tr>
    	<td width="60px" align="right">开始日期：</td>
        <td width="60px">
       		 <fmt:formatDate value="${notice.startTime}" type="both" pattern="yyyy-MM-dd" />
		</td>
        <td width="60px" align="right">结束日期：</td>
        <td width="60px">			 
        	<fmt:formatDate value="${notice.invalidTime}" type="both" pattern="yyyy-MM-dd" />
        </td>
    </tr>
    
    <tr>
    	<td width="60px" align="right" valign="top">公告简介：</td>
        <td width="390px" colspan="3">
        	<textarea id="summary" name="summary" rows = "10" readonly="true" style="width:100%; height:200px; display:block;border:none;padding-top:5px;margin-bottom:5px;">${notice.summary}</textarea>
        </td>
    </tr>
  
</table>

</form>
</div>
</body>
</html>
