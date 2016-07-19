<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<%	
	TransMsg transMsg = (TransMsg)request.getAttribute("transMsg");
	//总记录数
	Integer startIndex;
	if (transMsg.getStartIndex() == null) {
		startIndex = new Integer(0);
	} else {
		startIndex = transMsg.getStartIndex();
	}
%>
<head>
	<%@ include file="/dealer/include/meta.jsp" %>
</head>
<script>
$(document).ready(function() {
	//$("#startTime").datepicker();
	//$("#endTime").datepicker();
});
</script>
<body class="body_pop">
<div style="width:740px; margin:5px auto;">
<form id="myPageForm" action="<%=path%>/dealer/declareManager/queryViewDeclareHis.vti" commandName="vehicle" method="get" >
<input type="hidden" id="declareType" name="declareType" value="${declareType}" />
<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicleId}" />

<div class="pop-search">
	<!--  开始时间：<input id="startTime" name="startTime" style="width:100px" class="size date" value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd" />" readonly="readonly" type="text" />
	结束时间：<input id="endTime" name="endTime" style="width:100px" class="size date" value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd" />" readonly="readonly" type="text" />-->
	 报备时间:
		 <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		 - 
		 <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
	<button onclick='$("#startIndex").attr("value", "0");'>查询</button>
</div>

<table width="740px" cellpadding="0" cellspacing="0">
	<tr class="pop-data-title">
    	<td><strong>申请人</strong></td>
        <td><strong>申请时间</strong></td>
        <td><strong>开始日期</strong></td>
        <td><strong>结束日期</strong></td>
        <td><strong>报备状态</strong></td>
        <td><strong>目的地</strong></td>
        <td><strong>原因</strong></td>
    </tr>
    <c:if test="${filingProposalList != null}" >
	<c:forEach var="filingProposal" items="${filingProposalList}" varStatus="statusObj">
	<tr class='${statusObj.index%2==0?"pop-data-even":"pop-data-odd"}'>
		<td><c:out value="${filingProposal.proposerName}" /></td>
		<td class="pop-date"><fmt:formatDate value="${filingProposal.commitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td class="pop-date"><fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td class="pop-date"><fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<!--<td><c:out value="${filingProposal.type}" /></td>-->
		<td><c:out value="${filingProposal.status_nick}" /></td>
		<td title="${filingProposal.description}"><c:out value="${fn:substring(filingProposal.description,0,10)}" /></td>		
		<td title="${filingProposal.reason}"><c:out value="${fn:substring(filingProposal.reason,0,10)}" /></td>
	</tr>
	</c:forEach>
	</c:if>
</table>
<div class="pop-bottom clearfix">
	<div class="pop-excel">表格导出</div>
	<div class="pop-pages"><jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/></div>
</div>
</form>
</div>
</body>