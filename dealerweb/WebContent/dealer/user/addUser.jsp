<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.domain.entity.User"%>

<%
	User currentUser = SessionManager.getCurrentUser(request);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
<%@ include file="/dealer/include/meta.jsp" %>
<script>

$(document).ready(function(){
	$(".orgId").change(function(){
	  var orgId = $("#orgId").attr("value");
	  
	  var targetUrl = "<%=request.getContextPath()%>/base/dealer/findDealerInfo.vti?orgId=" + orgId;
		 $.ajax({
		 url: targetUrl,
    		  type: 'GET',
		      dataType: 'json',
		      timeout: 10000,
		      error: function(){
		         alert('系统异常');
		      },
		      success: function(responseData){
		      if (responseData.code == 1) {
		         if (responseData.data != null && responseData.data.org != null) {
		        		 var org = responseData.data.org;
			        	 if(org.orgType == '<%=GlobalConstant.ABSTRACT_ORG_TYPE_COMPANY%>' && org.dealerCode != 0){
			     			$("#isKeyCityRow").show();
			     			$("#isKeyCity").val(org.isKeyCity);
			     		}else{
			     			$("#isKeyCityRow").hide();
			     		}	
		    	  	} else {
		    	  		$("#isKeyCityRow").hide();
		    	  	}
		         
		     } else {
		      jAlert(responseData.message);
		     }
		    }
		 });
	});
});

function toIsDisable(chechboxObj) {
	if (getCheckBoxStatus(chechboxObj) == true){
		chechboxObj.value = 1;
	}else{
		chechboxObj.value = 0;
	}
}
</script>
<body >

<form id="myPageForm" action = "<%=request.getContextPath()%>/base/user/add.vti" method="post" >
<input type="hidden" name="startIndex" value="${startIndex}" />
<table border="0" cellpadding="0" cellspacing="0" 
		class="tableBox">
  <tr>
    <td class="tdTitle" style="width:130px;"><em>*</em> 经销商/分销中心：</td>
    <td class="tdSpace">
	<form:select id="orgId" cssClass="orgId" path='userAdd.orgId'  >
		<form:options items="${orgList}"  itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select>
    </td>
  </tr>
   <tr id="isKeyCityRow" style="display:none" >
	  <td class="tdTitle">经销商状态：</td>
	  <td class="tdSpace">
	  <form:select id="isKeyCity" disabled="true" path='userAdd.isKeyCity'  >
				<form:options items="${isKeyCityList}"  itemLabel="optionText" itemValue="optionValue"  />
	   </form:select>
	 </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 名称：</td>
    <td class="tdSpace">
    <form:input path="userAdd.nickName" cssClass="size" maxlength="20" cssStyle="width:200px" /><em>系统显示名称</em>
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 账号：</td>
    <td class="tdSpace">
    <form:input path="userAdd.loginName" cssClass="size" maxlength="20" cssStyle="width:200px"/><em>系统登录帐号</em>
    </td>
  </tr>
 	 <tr>
    <td class="tdTitle"><em>*</em> 密码：</td>
    <td class="tdSpace">
    <form:password path="userAdd.pseudoPassword" cssClass="size" maxlength="16" cssStyle="width:200px"/><em>6-16位，区分大小写，只能使用字母，数字</em>
    </td>
   </tr>
  <tr>
    <td class="tdTitle"><em>*</em>电子邮件：</td>
    <td class="tdSpace">
     <form:input path="userAdd.email" cssClass="size" maxlength="30" cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle">手机：</td>
    <td class="tdSpace">
    <form:input path="userAdd.mobile" cssClass="size" maxlength="15" cssStyle="width:200px" />
    </td>
  </tr>
   <tr>
    <td class="tdTitle">禁用：</td>
    <td class="tdSpace">
   <form:checkbox path="userAdd.isDisable" onclick="toIsDisable(this)" value="1" label="勾选禁止用户登录系统" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em>选择角色：</td>
    <td class="tdSpace">
		 <form:checkboxes items="${userAdd.allRoles}" path="userAdd.selectRoles" /> 
    </td>
  </tr>
  
   <tr>
    <td colspan="4" class="tdSpace" align="center">
    <input name="" type="submit" class="btn_submit" value="提 交" />
    <input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
    </td>
    </tr>
</table>
</form>

</body>
</html>