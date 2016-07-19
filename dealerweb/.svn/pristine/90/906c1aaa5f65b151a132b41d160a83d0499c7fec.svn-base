<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg"%>
<%@page import="com.ava.resource.cache.CacheCompanyManager"%>
<%
	Company currentOrg = CacheCompanyManager.getCompany(SessionManager.getCurrentCompanyId(request));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
function clickMenuPanelChild(para) {
	var value = getCheckBoxValue(para);
	var smToLoginName = document.getElementById('smToLoginName');
	if (getCheckBoxStatus(para) == true){
		//选中某个联系人的复选框
		smToLoginName.value = addToUserLoginName(smToLoginName.value, value);
	}else{
		//删除某个联系人的复选框
		smToLoginName.value = delToUserLoginName(smToLoginName.value, value);
	}
}
function addToUserLoginName(oldLoginNames, loginName) {
	if ( oldLoginNames.indexOf(loginName) > -1 ){
		//已存在该用户，则不必再添加
	}else{
		if (oldLoginNames.length > 0){
			oldLoginNames = oldLoginNames + ";" + loginName;
		}else{
			oldLoginNames = loginName;
		}
	}
	return oldLoginNames;
}
function delToUserLoginName(oldLoginNames, loginName) {
	if ( oldLoginNames.indexOf(loginName) > -1 ){
		//存在该用户，则删除
		oldLoginNames = oldLoginNames.replace(loginName,"");
		//删除后可能存在多余的分号，去除
		oldLoginNames = oldLoginNames.replace(/;;/g,";");
		if(oldLoginNames.indexOf(";") == 0){
			//如果第一个字符是分号，则去除
			oldLoginNames = oldLoginNames.substring(1);
		}
		if(oldLoginNames.indexOf(";") == oldLoginNames.length-1){
			//如果最后一个字符是分号，则去除
			oldLoginNames = oldLoginNames.substring(0, oldLoginNames.length-1);
		}
	}else{
	}
	return oldLoginNames;
}

window.onload = function() {
	document.body.onclick=checkClick;
}
function confirmDelete() {
	if (confirm("确定要删除此记录吗？")) {
		return true;
	} else {
		return false;
	}
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/base/shortMessage/sendWrite.vti" method="post" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<span >当前位置： 站内消息  > 写新邮件</span>
	</div>
	<div class="audit_nav_c">
 		<a href = "<%=path%>/base/shortMessage/inboxList.vti" target="iframe_right" >【收件箱】</a>
				&nbsp;&nbsp; 
		<a href = "<%=path%>/base/shortMessage/outboxList.vti" target="iframe_right">【发件箱】</a>
					&nbsp;&nbsp; 
        <a href = "<%=path%>/base/shortMessage/displaySend.vti" target="iframe_right" >【写新邮件】</a>
			 
	</div>
	<div id="menuPanel" style=" display:none; ">
				<div class="title">
			    	<p>选择收件人</p>
			       	<span><a id="closeId" href="javascript:void(0);">关闭</a></span>
			    </div>
				<div class="contactList" id="menuPanel_mate">
			    	<ul>
			    	<c:if test="${mateUserList != null}">
			    	  <c:forEach var="user" items="${mateUserList}">
						<li><input id='menuPanel_child_${user.id}' 
			          	onClick="clickMenuPanelChild(this)" type="checkbox" value='<c:out value="${user.loginName}" />'/>&nbsp;<c:out value="${user.loginName}" />&nbsp;[<c:out value="${user.nickName}" />]</li>
					</c:forEach>
					</c:if>
			        </ul>
				</div>
		</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto"> 
			<ul class="table_hd clearfix" >
					友情提示：您可以点击“收件人”按钮选择您想发送站内消息的收件人地址。
			</ul>
			    <br>
			   
				<ul class="mail_ipt_txt clearfix">
					 <input id="menuPanelLink_001" type="button" value="收件人:" onClick="showMenuDiv();" style="background: #999;float:left;"/>
					 <form:input id="smToLoginName" path="shortMessage.toLoginName" style="width:450px; float:left;" /></li>
				 </ul>
				   <br>
			     <ul class="mail_ipt_txt clearfix">
						<p style="float:left;">主题：</p>
						<form:input path="shortMessage.title" style="width:455px;float:left;"/>
				 </ul> 
				 <br>
				 	
				 <ul class="send-textarea clearfix"><!--  <p>正文: </p> -->
				  <form:textarea path="shortMessage.content" style="width:495px;" rows="12"/>
				</ul>
				 <br>
				<ul  class="mail_button clearfix">
					<input type="submit" value="发 送" class="btn_submit" >	
				</ul> 		
		 
		</div>
	</aside>
</div>
</form>
</body>
</html>