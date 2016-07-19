<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body class="body_pop">

<div class="pop-tableBox">
<form id="myPageForm" action="<%=request.getContextPath()%>/base/notice/add.vti" method="post">

<table>
    <tr>
    	<td width="10%" align="right"><span>*</span>公告名称：</td>
        <td width="65%">
        	<input id="title" name="title" class="pop-text-long" value="${noticeAdd.title}" type="text" maxlength="80"></input>
        </td>
    </tr>
  
    <tr>
    	<td width="10%" align="right"><span>*</span>开始日期：</td>
        <td width="65%">
        	<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" value="<fmt:formatDate value="${noticeAdd.startTime}" type="both" pattern="yyyy-MM-dd" />" class="Wdate right" readonly="true"/>
		</td>
    </tr>
   
    <tr>
        <td width="10%" align="right"><span>*</span>结束日期：</td>
        <td width="65%">        
        	<input type="text" id="invalidTime" name="invalidTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" value="<fmt:formatDate value="${noticeAdd.invalidTime}" type="both" pattern="yyyy-MM-dd" />" class="Wdate right" readonly="true"/>
        </td>
    </tr>
     <tr>
		<td width="10%" align="right">公告类型：</td>
		<td width="55%">
			<form:radiobutton path="noticeAdd.type" value="1" />全局
			<form:radiobutton path="noticeAdd.type" value="3" />非全局
		</td>
	</tr>
    <tr>
    	<td width="10%" align="right"><span>*</span>公告简介：</td>
        <td width="55%"><textarea id="summary" name="summary" rows = "5" >${noticeAdd.summary}</textarea>
        </td>
    </tr>
    <tr>
		<td colspan="2" class="tdSpace" align="center">
			<input name="" type="submit" class="btn_submit" value="提 交" />
		<!-- 	<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" /> -->
		</td>
	</tr>
</table>

</form>
</div>
</body>
</html>
