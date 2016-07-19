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
/* 	if (document.getElementById('smToLoginName').value.length>0)
    {
        document.getElementById('smToLoginName').value="";
    } */
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
			oldLoginNames = oldLoginNames + "；" + loginName ;
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
		oldLoginNames = oldLoginNames.replace(/；；/g,"；");
		if(oldLoginNames.indexOf("；") == 0){
			//如果第一个字符是分号，则去除
			oldLoginNames = oldLoginNames.substring(1);
		}
		if(oldLoginNames.indexOf("；") == oldLoginNames.length-1){
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
<form id="myPageForm" action="<%=path%>/base/notice/editRoleRightsCode.vti" method="post" >
<form:hidden id="id" path="notice.id"/>
<input type="hidden" name="startIndex" value ="${startIndex}"/>
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<span >当前位置：系统设置  > 公告管理  > 分配经销商</span>
	</div>
<!-- 	<div class="audit_nav_c">
 		<a href = "javascript:history.go(-1)" target="iframe_right" >【返回】</a>
	</div> -->
	<div id="menuPanel" style=" display:none; ">
				<div class="title">
			    	<p>选择经销商</p>
			       	<span><a id="closeId" href="javascript:void(0);">关闭</a></span>
			    </div>
				<div class="contactList" id="menuPanel_mate">
			    	<ul>
			    	<c:if test="${mateUserList != null}">
			    	  <c:forEach var="company" items="${mateUserList}">
						<li><input id='menuPanel_child_${company.id}' 
			          	onClick="clickMenuPanelChild(this)" type="checkbox" value='<c:out value="${company.cnName}[${company.id}]" />'/>&nbsp;<c:out value="${company.cnName}" />&nbsp;[<c:out value="${company.id}" />]</li>
					</c:forEach>
					</c:if>
			        </ul>
				</div>
		</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto"> 
			<ul class="table_hd clearfix" >
					友情提示：您可以点击“选择经销商”按钮选择您的公告想发送的经销商。
			</ul>
			<span>公告标题: ${notice.title}</span>
				<ul class="mail_ipt_txt clearfix">
					 <input id="menuPanelLink_001" type="button" value="选择经销商:" onClick="showMenuDiv();" style="background: #999;float:left;"/>
				 </ul>
				 <ul class="send-textarea clearfix"> <p>【本次选择经销商】 </p>
				  <form:textarea  id="smToLoginName" path="notice.contents" style="width:495px;" rows="10" />
				 <!--  <div id="smToLoginName"  path="notice.contents">
				   </div> -->
				 </ul>
				  <ul class="send-textarea clearfix"><p>【上次已选经销商】</p>
				 	 <textarea  id="lastTimeValue" name="lastTimeValue" style="width:495px;" rows="9" > ${companyNameStr}</textarea>
				  </ul>
				<!-- <ul class="mail_button clearfix"> -->
					<input type="submit" value="保存" class="btn_submit" />	
					<input type="submit" value="取消"  class="btn_submit"  onclick="javascript:history.back(-1);"/>
				<!-- </ul> 	 -->	
		</div>
	</aside>
	<div class="bd_rt_ft" style="width:2000px;">
	</div>
</div>
</form>
</body>
</html>