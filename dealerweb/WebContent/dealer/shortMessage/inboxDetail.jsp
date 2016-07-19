<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body> 
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
<form id="myPageForm" action="<%=path%>/base/shortMessage/deleteInboxAll.vti" method="post" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<span >当前位置：收件箱 > 邮件正文</span>
	   </div>
		<div class="audit_nav_c">
			<a href = "<%=path%>/base/shortMessage/inboxList.vti" target="iframe_right">【返回列表】</a>
		</div>
		<aside class="bd_rt_main clearfix">
			<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="mail_table_hd clearfix">
					<li class="li_281">
	            	主题: &nbsp;&nbsp;&nbsp;&nbsp;${shortMessageForInbox.title}
	            	<%-- <input type="text" style="width:455px;float:left;overflow:auto;" 
	            	value="${shortMessageForInbox.title}"/> --%>
	            	<%-- ${fn:substring(shortMessageForInbox.title, 0, 26)} --%>
	            	</li>
            	</ul>
            	<ul class="mail_table_hd clearfix">
            		<li class="li_281">
            		发件人：<a href='<%=path%>/base/shortMessage/displaySend.vti?toLoginName=${shortMessageForInbox.fromLoginName}' target="iframe_right">
            		<c:out value="${shortMessageForInbox.fromLoginName}" /></a>
            		</li>
            	</ul>
				<ul class="mail_table_hd clearfix">
					<li class="li_281">
            		 时间 : &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${shortMessageForInbox.creationTime}" />
            		</li>
            	</ul>
				 <ul class="mail_table_hd clearfix">
				 	<li class="li_281">
				  		<a class="replay" href='<%=path%>/base/shortMessage/displaySend.vti?toLoginName=<c:out value="${shortMessageForInbox.fromLoginName}" />&replyShortMessageId=<c:out value="${shortMessageForInbox.id}" />'>回复</a>
            			<a class="del" href='<%=path%>/base/shortMessage/deleteInbox.vti?shortMessageId=<c:out value="${shortMessageForInbox.id}" />' onclick="return comfirmDelete();">删除</a>
            		</li>
            	</ul>
				<ul class="send-textarea clearfix" >
            		  <%--  &nbsp;&nbsp;<c:out value="${shortMessageForInbox.content}" /> --%>
            		   <form:textarea path="shortMessageForInbox.content" cols="95" rows="12"/>
            	</ul>
			</div>	
		</aside>
	</div><!--/.bd_rt-->
</form>

</body>
</html>