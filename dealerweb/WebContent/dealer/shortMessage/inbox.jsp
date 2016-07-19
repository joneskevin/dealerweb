<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg"%>
<%
	TransMsg transMsg = (TransMsg)request.getAttribute("transMsg");
	//总记录数
	Long totalRow;
	if (transMsg.getTotalRecords() == null) {
		totalRow = new Long(0);
	} else {
		totalRow = transMsg.getTotalRecords();
	}
%>
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
var pageFormObj = document.getElementById("myPageForm");
function gotoDeleteMail(){
	pageFormObj.action="<%=path%>/base/shortMessage/deleteInboxAll.vti";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/base/shortMessage/inboxList.vti";
}
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/base/shortMessage/exportVehicle.vti";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/base/shortMessage/inboxList.vti";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/shortMessage/inboxList.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<span >当前位置 > 站内消息 > 收件箱</span>
	</div>
	<div class="audit_nav_c">
		<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
 		<a href = "<%=path%>/base/shortMessage/inboxList.vti" target="iframe_right" >【收件箱】</a>
					&nbsp;&nbsp; 
		<a href = "<%=path%>/base/shortMessage/outboxList.vti" target="iframe_right">【发件箱】</a>
					&nbsp;&nbsp; 
         <a href = "<%=path%>/base/shortMessage/displaySend.vti" target="iframe_right" >【写新邮件】</a>
			 
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" >
						提示：我们为您保存最近6个月来的收、发的站内消息，重要信息请另外保存。
						收件箱 (共 <%=totalRow %> 封，其中未读站内消息 <em><c:out value="${inboxCount}" /></em> 封)
				</ul>
				<ul class="table_hd clearfix">
            		<li class="li_71"><input type="checkbox" name="" value="选择" onClick="selectAll('messageIds')"/></li>
            		<li class="li_71"><img src="<%=path%>/images/message.gif" alt="站内消息" /></li>
            		<li class="li_180">发件人</li>
            		<li class="li_280">主题</li>
            		<li class="li_150">时间</li>
				</ul>
			<c:if test="${inboxList != null}" >
				<c:forEach var="shortMessageForInbox" items="${inboxList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
 				<li class="li_71"><input type="checkbox" name="messageIds" value="${shortMessageForInbox.id}"/></li>
 		
           		<li class="li_71" > 
            		<c:if test="${shortMessageForInbox.isreaded == 1}">
				    	 <img src="<%=path%>/images/message_read.gif" alt="已读短邮" />
			    	</c:if>
			      	<c:if test="${shortMessageForInbox.isreaded == 0}">
			    	  <img src="<%=path%>/images/message_noRead.gif" alt="未读短邮" />
			    	</c:if>
           		</li>
           		<li class="li_180"><span title="${shortMessageForInbox.fromLoginName}">${shortMessageForInbox.fromLoginName}</span></li>
           		<li class="li_280"><span title="${shortMessageForInbox.title}">
           			<%--  <a href='<%=path%>/base/shortMessage/inboxDetail.vti?shortMessageId=<c:out value="${shortMessageForInbox.id}" />' target="iframe_right">
			    		你好										 
			    	 </a> --%>
           			<c:if test="${shortMessageForInbox.isreaded == 1}">
			    	 <a href='<%=path%>/base/shortMessage/inboxDetail.vti?shortMessageId=<c:out value="${shortMessageForInbox.id}" />' title='<c:out value="${shortMessageForInbox.title}" />' target="iframe_right">
			    	 <c:out value="${shortMessageForInbox.title}" />
			    	 </a>
			    	</c:if>
			      	<c:if test="${shortMessageForInbox.isreaded == 0}">
			    	   <a href='<%=path%>/base/shortMessage/inboxDetail.vti?shortMessageId=<c:out value="${shortMessageForInbox.id}" />' title='<c:out value="${shortMessageForInbox.title}" />' target="iframe_right"><b><c:out value="${shortMessageForInbox.title}" /></b></a>
			    	</c:if></span>
			    	</li>
			    	
			    <li class="li_150"><span title="<fmt:formatDate value="${shortMessageForInbox.creationTime}" type="both" pattern="yyyy-MM-dd HH:mm"/>">
			    	<fmt:formatDate value="${shortMessageForInbox.creationTime}" type="both" pattern="yyyy-MM-dd HH:mm"/></span></li>
            		
				</ul>
				</c:forEach>
				</c:if>
		</div>
	</aside>
	<div class="bd_rt_ft">
			<strong><a href="#" onClick="javascript:gotoDeleteMail();">删除所选</a></strong>
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div>
</form>
</body>
</html>