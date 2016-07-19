<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.domain.entity.User"%>
<%
	User currentUser = SessionManager.getCurrentUser(request);
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
<div class="iconBox2"><img src="<%=path%>/images/titleIcon_orgManage.gif" /></div>
<h1>用户管理</h1>
<div class="iconR"><img src="<%=path%>/images/titleIconBox02_assignment2.gif" /></div>
<div class="enterBoxAll"><img src="<%=path%>/images/titleIconBox02_R.gif" /></div>
<ul class="tags">
		<li class="selectTag"><span class="left"></span>
		<a href = "<%=path%>/base/baseSystem/personal/userAction.do?action=listUser" target="main" >用户信息</a><span class="right"></span>
		</li>
	</ul>
</div>

<div class="allContent">
<div class="pageRoute">当前位置：用户管理  > 用户密码设置<span><a href = "<%=path%>/base/baseSystem/personal/userAction.do?action=listUser" target="main">返回列表</a></span></div>
<div class='msgResult<html:errors property="MSG_RESULT_LEVEL"/>' id='msg_result' style="DISPLAY:none;"><html:errors property="MSG_RESULT"/></div>
<html:form action = "<%=path%>/base/baseSystem/personal/userAction" method="post">
<input type="hidden" name="action" value="editUserPassword" />   
<html:hidden name="USER_FORM" property="user.id"/>
<table border="0" cellpadding="0" cellspacing="1" bgcolor="#92b4d0" class="tableBox">
  <tr>
    <th colspan="2">用户密码设置</th>
  </tr>
  <tr>
    <td class="tdTitle">登录名：</td>
    <td class="tdSpace"><bean:write name="USER_FORM" property="user.loginName"/> </td>
  </tr>
  <tr>
    <td class="tdTitle">最近登录时间：</td>
    <td class="tdSpace"><bean:write name="USER_FORM" property="user.lastLoginTime" format="yyyy-MM-dd HH:mm:ss"/></td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 新密码：</td>
    <td class="tdSpace"><html:password name="USER_FORM" styleClass="input_text" property="user.pagePassword"/></td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 确认新密码：</td>
    <td class="tdSpace"><html:password name="USER_FORM" styleClass="input_text" property="user.pageConfirmPassword"/></td>
  </tr>
  
  <tr>
    <td colspan="2" class="tdSpace" style="text-align:right;">
    <input name="" type="submit" class="btnTwo" value="保存设置" />
    </td>
    </tr>
</table>
</html:form>
</div>
</div>

</div>


</body>
</html>