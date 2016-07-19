<%@ include file="/pub/jstl.jsp" %>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
<%@ include file="/dealer/include/meta.jsp" %>
<script>

$(document).ready(function(){
	changeOrg();
	var isDisable = $("#isDisable").val();
	if (isDisable == 1) {
		$("[name = isDisable]:checkbox").attr("checked", true);
	} else {
		$("[name = isDisable]:checkbox").attr("checked", false);
	}

});

function changeOrg() {
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
}


function toIsDisable(chechboxObj) {
	if (getCheckBoxStatus(chechboxObj) == true){
		chechboxObj.value = 1;
	}else{
		chechboxObj.value = 0;
	}
}

</script>

<body>

<form id="myPageForm" action = "<%=request.getContextPath()%>/base/user/edit.vti?startIndex=${startIndex}" method="post" >
<form:hidden path="user.id"/>
	<table border="0" cellpadding="0" cellspacing="0" 
		class="tableBox">
	<tr>
    <td class="tdTitle" style="width:130px;"><em>*</em> 经销商/分销中心：</td>
    <td class="tdSpace">
	<form:select id="orgId" cssClass="orgId"  path='user.orgId'  onchange="changeOrg()" >
		<form:options items="${orgList}"  itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select>
    </td>
  </tr>
   <tr id="isKeyCityRow" style="display:none" >
	  <td class="tdTitle">经销商状态：</td>
	  <td class="tdSpace">
		<form:select id="isKeyCity" disabled="true" path='user.isKeyCity'  >
			<form:options items="${isKeyCityList}"  itemLabel="optionText" itemValue="optionValue"  />
		</form:select>
	 </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 名称：</td>
    <td class="tdSpace">
    <form:input path="user.nickName" cssClass="size" maxlength="20"  cssStyle="width:200px" />系统显示名称
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 账号：</td>
    <td class="tdSpace">
    <c:out value="${user.loginName}"></c:out>
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em>电子邮件：</td>
    <td class="tdSpace">
     <form:input path="user.email" cssClass="size" maxlength="30"  cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle">手机：</td>
    <td class="tdSpace">
    <form:input path="user.mobile" cssClass="size" maxlength="15"  cssStyle="width:200px" />
    </td>
  </tr>
   <tr>
    <td class="tdTitle">禁用：</td>
    <td class="tdSpace">
   <form:checkbox id="isDisable" path="user.isDisable" onclick="toIsDisable(this)" value="${user.isDisable}" label="勾选禁止用户登录系统" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em>选择角色：</td>
    <td class="tdSpace">
	 <form:checkboxes items="${user.allRoles}" path="user.selectRoles" /> 
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