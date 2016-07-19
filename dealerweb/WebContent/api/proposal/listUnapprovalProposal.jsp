<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/api/apiMeta.jsp"%>
</head>

<body>

<div class="boxAll">

<div class="allContent">
	<form id="myPageForm" action="/api/proposal/html/listUnapproval">	
	<input type="hidden" id="accessToken" name="accessToken" value="${apiUserLoginToken.token}" />
	<table id="tableList" class="tableList" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>发布时间</th>
			<th>申请人</th>
			<th>类型</th>
			<th>下一处理者</th>
			<th>操作</th>
		</tr>
		<c:if test="${proposalList != null}">
		<c:forEach var="row" items="${proposalList}">
		<tr>
			<td><fmt:formatDate value="${row.proposalTime}" type="both" pattern="MM-dd HH:mm"/></td>
			<td>${row.proposerUserNick}</td>
			<td>${row.subType_Nick}&nbsp;${row.type_Nick}</td>
			<td>
				<c:if test="${row.approvers != null}">
				<c:forEach var="approver" items="${row.approvers}">
					${approver.nickName}&nbsp;
				</c:forEach>
				</c:if>
			</td>
			<td>
				<a href="javascript:openWindow('<%=path%>/base/proposal/view.vti?proposalId=${row.id}', 400, 550, '申请信息')">查看</a>
	
				<c:set var="globalTrue" value="<%=String.valueOf(GlobalConstant.TRUE)%>"/>
				<c:if test="${row.canApprove == globalTrue}">
	            	<a href="javascript:openWindow('<%=path%>/base/proposal/displayHandle.vti?proposalId=${row.id}', 800, 600, '审批')">审批</a>
	            </c:if>
				<c:if test="${row.canEditOwnerAttachment == globalTrue}">
	            	<a href="javascript:openWindow('<%=path%>/base/proposal/displayEdit4Executor.vti?proposalId=${row.id}', 660, 450, '编辑申请')">编辑</a>
	            </c:if>
			</td>
		</tr>
		</c:forEach>
		</c:if>
	</table>
	<div class="pages">
		<span class="page">
			<jsp:include page="/pub/navigate/navigation4Base.jsp" flush="true"/>
		</span>
	</div>
	</form>
</div>

</div>

</body>
</html>
