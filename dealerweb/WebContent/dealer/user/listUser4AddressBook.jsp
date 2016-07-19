<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.resource.SessionManager"%>
<%
	User addressCurrentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<div class="mainAll">

<div class="boxAll">
<div class="boxTitle boxAllBg">
<div class="iconBox2"><img src="<%=path%>/images/titleIcon_address.gif" /></div>
<h1>通讯录</h1>
<div class="iconR"><img
	src="<%=path%>/images/titleIconBox02_assignment2.gif" /></div>
<div class="enterBoxAll"><span></span><img
	src="<%=path%>/images/titleIconBox02_R.gif" /></div>
</div>

<div class="allContent">
<div class="pageRoute">当前位置：通讯录</div>
<div class="pageRoute">友情提示：红色名称的用户为在线用户</div>
<form id="myPageForm" action = "<%=path%>/base/user/listUser4AddressBook.vti" method="get" >
<div class="communication">
    <c:if test="${userList != null}">
      <c:forEach var="user" items="${userList}">
       <dl>
			<dt>
				<a href="javascript:openWindow('<%=path%>/base/user/view.vti?userId=<c:out value="${user.id}" />', 660, 445, '<c:out value="${user.nickName}" />的详细资料')">
					<c:if test="${user.onlineStatus == 1}">
					  <font color="red"><c:out value="${user.nickName}" /></font>
					</c:if>
					<c:if test="${user.onlineStatus == 0}">
					  <c:out value="${user.nickName}" />
					</c:if>
				</a> 
				<a href = "<%=path%>/base/shortMessage/displaySend.vti?toLoginName=<c:out value="${user.loginName}" />"><img
					src="<%=path%>/images/message_read.gif"
					alt="给<c:out value="${user.nickName}" />发站内消息" /></a>
			</dt>

			<dd class="pic"><i></i> 
			<%
			 	if (addressCurrentUser.getUserRight().isRightUserMangementAll()) {
			%>
				<a
					href = "<%=path%>/base/user/displayEdit.vti?id=<c:out value="${user.id}"/>"
					target="main"> <img
					src="<c:out value="${user.avatar}"/>" alt="头像" /> </a> 
			<%
			 	} else {
			 %>
				<img src="<c:out value="${user.avatar}"/>" alt="头像" /> 
			<%
			 	}
			 %>
			</dd>

			<dd class="txt">
			<ul>
				<li><c:out value="${user.loginName}"/></li>
				<li>手机：<c:out value="${user.mobile}"/></li>
				<li>QQ：<c:out value="${user.qq}"/></li>
			</ul>
			</dd>
		</dl>
      </c:forEach>
    </c:if>
    
</div>
<div class="pages">
	<span class="page"> 
		<jsp:include page="/pub/navigate/navigation4Base.jsp" flush="true">
			<jsp:param name="pageFormAct" value="listUser4AddressBook" />
		</jsp:include> 
	</span>
</div>
</form>
</div>

</div>

</div>




</body>
</html>
