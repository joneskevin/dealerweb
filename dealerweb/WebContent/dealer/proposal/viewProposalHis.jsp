<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<div class="mainAll">
<div class="boxAll">
<div class="boxTitle boxAllBg">
<div class="iconBox2"><img src="<%=path%>/images/titleIcon_orgManage.gif" /></div>
<h1>车辆更新</h1>
<div class="iconR"><img src="<%=path%>/images/titleIconBox02_assignment2.gif" /></div>
	<ul class="tags">
		<li class="selectTag"><span class="left"></span>
		<a href="" target="main" >车辆更新信息</a><span class="right"></span>
		</li>
	</ul>
<div class="enterBoxAll"><img src="<%=path%>/images/titleIconBox02_R.gif" /></div>
</div>

<div class="allContent">
<div class="pageRoute">当前位置：车辆更新  > 查看申请历史</div>
<table id="tableList" class="tableList" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<th width="4%">申请人</th>
		<th width="7%">申请时间</th>
		<th width="3%">联系人</th>
		<th width="5%">联系电话</th>
		<th width="10%">审批意见</th>
		<th width="5%">申请状态</th>
		<th width="5%">操作</th>
	</tr>
	<c:if test="${proposalList != null}" >
		<c:forEach var="proposal" items="${proposalList}">
		<tr>
		<td><c:out value="${proposal.proposerName}" /></td>
		<td><fmt:formatDate value="${proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		<td><c:out value="${proposal.contactName}" /></td>
		<td><c:out value="${proposal.contactPhone}" /></td>
		<td><c:out value="${proposal.adviceDescription}" /></td>
		<td><c:out value="${proposal.status_nick}" /></td>
		<td><a href="javascript:openWindow('<%=path%>/dealer/proposal/viewProposal.vti?proposalId=<c:out value="${proposal.id}" />', 660, 500, '申请信息')">查看</a></td>
		</tr>
		</c:forEach>
	</c:if>
</table>
</div>
</div>
</div>
</body>
</html>