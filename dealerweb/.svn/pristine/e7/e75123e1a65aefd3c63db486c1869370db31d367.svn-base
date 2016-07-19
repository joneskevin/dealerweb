<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
function confirmDelete() {
	if (confirm("确定要删除此记录吗？")) {
		return true;
	} else {
		return false;
	}
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/shortMessage/deleteInboxAll.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<span >当前位置：发件箱 > 邮件正文 </span>
	</div>
	<div class="audit_nav_c">
	 		<a href = "<%=path%>/base/shortMessage/outboxList.vti" target="iframe_right">【返回列表】</a>
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="mail_table_hd clearfix">
            		<li class="li_281">主题:&nbsp;&nbsp;&nbsp;&nbsp; ${shortMessageForOutbox.title}</li>
            	</ul>
            	<ul class="mail_table_hd clearfix">
            		<li class="li_281">发件人：<a href='<%=path%>/base/shortMessage/displaySend.vti?toLoginName=${shortMessageForOutbox.fromLoginName}' target="iframe_right"><c:out value="${shortMessageForOutbox.fromLoginName}" /></a></li>
            	</ul>
				<ul class="mail_table_hd clearfix">
            			<li class="li_281">时间:&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${shortMessageForOutbox.creationTime}" /></li>
            	</ul>
				 <ul class="mail_table_hd clearfix">
            			<li class="li_281"><a class="del" href='<%=path%>/base/shortMessage/deleteOutbox.vti?shortMessageId=<c:out value="${shortMessageForOutbox.id}" />'  onclick="return comfirmDelete();">删除</a></li>
            	</ul>
				<ul class="send-textarea clearfix">
            			 <%-- &nbsp;&nbsp;<c:out value="${shortMessageForOutbox.content}" escapeXml="false"/> --%>
            			 <form:textarea path="shortMessageForOutbox.content" cols="95" rows="12"/>
            	</ul>
		</div>
	</aside>
</div>
</form>
</body>
</html>