<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body style="overflow-x:hidden ;width:800px;">

<table border="0" cellpadding="0" cellspacing="1" bgcolor="#92b4d0" class="tableBox">
  <tr>
    <td class="tdTitle">申请人：</td>
    <td class="tdSpace">
    	${proposal.proposerUserNick}[${proposal.proposerUserCompanyName}]
    </td>
  </tr>
  <tr>
    <td class="tdTitle">发布时间：</td>
    <td class="tdSpace"><fmt:formatDate value="${proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
  </tr>
  <tr>
    <td class="tdTitle">类型：</td>
    <td class="tdSpace">${proposal.type_Nick}</td>
  </tr>
  <tr>
    <td class="tdTitle">子类型：</td>
    <td class="tdSpace">${proposal.subType_Nick}</td>
  </tr>
  <c:if test="${proposal.sumInterval_Nick != null}">
	<tr>
		<td class="tdTitle">金额区间：</td>
		<td class="tdSpace">${proposal.sumInterval_Nick}</td>
	</tr>
  </c:if>
  <tr>
    <td class="tdTitle top">描述：</td>
    <td class="tdSpace">${proposal.description}</td>
  </tr>
  <tr>
    <td class="tdTitle top">审批状态：</td>
    <td class="tdSpace">${proposal.approvalStatus_Nick}</td>
  </tr>
  <c:if test="${fn:length(proposal.approvers) > 0}">
  <tr>
    <td class="tdTitle top">下一处理者：</td>
    <td class="tdSpace">
    	<c:forEach var="approver" items="${proposal.approvers}">  
			${approver.nickName}&nbsp;
		</c:forEach>
    </td>
  </tr>
  </c:if>
  <c:if test="${fn:length(approvalList) > 0}">
  <tr>
    <td class="tdTitle top">审批详情：</td>
    <td class="tdSpace">
    <ol>
    	<c:forEach var="row" items="${approvalList}">   
	    <li>${row.userNick} 于
	    	<fmt:formatDate value="${row.approvalTime}" type="both" pattern="yyyy-MM-dd HH:mm"/> 
	    	${row.approvalStatus_Nick}了此申请  描述：${row.remark}
	    </li>
		</c:forEach>
    </ol>
    </td>
  </tr>
  </c:if>
  <c:if test="${fn:length(attachments) > 0}">
  <tr>
    <td class="tdTitle top">附件列表：</td>
    <td class="tdSpace">
   		<ol>
    		<c:forEach var="row" items="${attachments}">   
				<li style="font-size:12px;">${row.creatorId_Nick}&nbsp;
				<a href = "<%=path%>/base/common/download.vti?filePath=${row.fullPath}&displayName=${row.displayName}">${row.displayName}</a>
				</li>
			</c:forEach>
    	</ol>
    </td>
  </tr>
  </c:if>
</table>
</body>
</html>