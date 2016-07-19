<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant" %>
<%
	User currentUser = SessionManager.getCurrentUser(request);
	Integer regionCountryId = 34;//GlobalConstant.CHINA_DEFAULT_ID;//中国，数据字典表中的id
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
<%@ include file="/dealer/include/meta.jsp" %>
<%@ include file="/dealer/include/typeAndSubtypeCascader.jsp"%>
<%   
	Integer regionProvinceId = null;
	Integer regionCityId = null;
	
	Company company = (Company)request.getAttribute("companyAdd");
	if (company != null){
		regionProvinceId = company.getRegionProvinceId();
		regionCityId = company.getRegionCityId();
	}else{
		regionProvinceId = -1;
		regionCityId = -1;
	}
	if(regionProvinceId==null){
		regionProvinceId = -1;
	}
	if(regionCityId==null){
		regionCityId = -1;
	}	
%>

<jsp:include page="/pub/cascadeSelector3Level.jsp" flush="true">
	<jsp:param name="sortId" value="<%=GlobalConstant.DICTIONARY_AREA%>"/>
</jsp:include>

<script>
	
	var cascadeSelector3Level = new CascadeSelector3Level();
	$(document).ready(function() {
		dealerTypeChange();
		cascadeSelector3Level.init('regionCountryId','<%=regionCountryId%>','regionProvinceId','<%=regionProvinceId%>','regionCityId','<%=regionCityId%>');
	});
	
	function save(){
		var startTime = $('#startWorkTime').val();
		var endTime = $('#endWorkTime').val();
		if(parseInt(startTime) >= parseInt(endTime)){
			if(startTime >= endTime){
				alert("下班时间必须大于上班时间");
				return;
			}
		}
		$("#myPageForm").submit();
	}
	
	function dealerTypeChange() {
	  var dealerType = $("#dealerType").attr("value");
	  if (dealerType == <%=GlobalConstant.DEALER_TYPE_NON_STRAIGHT_DIRECT_SHOP%>) {
		  $("#parentCompanyRow").show();
	  } else {
		  $("#parentCompanyRow").hide();
	  }
	} 
</script>

<body style="overflow-x:hidden ;">
<div class="tip" align="center"><span  class="red">新增经销商成功后，会生成一个以网络代码为帐号的用户，密码在用户管理中[查看]</span></div>
<form id="myPageForm" method="post" action = "<%=request.getContextPath()%>/base/dealer/add.vti">
<table border="0" cellpadding="0" cellspacing="0"  class="tableBox">
	<tr>
    <td class="tdTitle" style="width:150px;"><em>*</em> 分销中心：</td>
    <td class="tdSpace">
	<form:select path='companyAdd.parentId'  >
		<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select>
    </td>
  </tr>
   <tr>
    <td class="tdTitle"><em>*</em> 省份：</td>
    <td class="tdSpace">
   <form:select id="regionCountryId" path="companyAdd.regionCountryId" onchange="cascadeSelector3Level.changeDataSort1('regionCountryId','regionProvinceId','regionCityId')" style="display:none;width:180px;"></form:select>
	<form:select  id="regionProvinceId" path="companyAdd.regionProvinceId" onchange="cascadeSelector3Level.changeDataSort2('regionCountryId','regionProvinceId','regionCityId')" style="width:110px"></form:select>
	&nbsp;&nbsp;<em>*</em>城市 
	<form:select id="regionCityId" path="companyAdd.regionCityId" style="width:110px"></form:select>
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 网络代码：</td>
    <td class="tdSpace">
    <form:input path="companyAdd.dealerCode" cssClass="size" maxlength="8" cssStyle="width:200px" /><em>请输入网络代码</em>
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 经销商名称：</td>
    <td class="tdSpace">
    <form:input path="companyAdd.cnName" cssClass="size" maxlength="30" cssStyle="width:200px"/><em>请输入经销商名称</em>
    </td>
  </tr>
   <tr>
	  <td class="tdTitle"><em>*</em> 经销商状态：</td>
	  <td class="tdSpace">
		<form:select path='companyAdd.isKeyCity'  >
			    <form:option value=" ">--请选择--</form:option>
				<form:options items="${isKeyCityList}"  itemLabel="optionText" itemValue="optionValue"  />
	    </form:select>
	    &nbsp;&nbsp;<em>*</em> 网络形态：
	    <form:select path='companyAdd.dealerType' onchange="dealerTypeChange()" >
			    <form:option value=" ">--请选择--</form:option>
				<form:options items="${dealerTypeList}"  itemLabel="optionText" itemValue="optionValue"  />
	    </form:select>
	 </td>
  </tr>
  <tr id="parentCompanyRow">
    <td class="tdTitle">上级网点：</td>
    <td class="tdSpace">
    <form:select id="parentCompanyId" cssClass="orgId" path='companyAdd.companyId'  >
			 <form:options items="${parentOrgList}"  itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select>
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em>邮箱：</td>
    <td class="tdSpace">
    <form:input path="companyAdd.email" maxlength="30" cssClass="size" cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle">电话：</td>
    <td class="tdSpace">
     <form:input path="companyAdd.contactTel" maxlength="15" cssClass="size" cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle">传真：</td>
    <td class="tdSpace">
    <form:input path="companyAdd.fax" maxlength="15" cssClass="size" cssStyle="width:200px" />
    </td>
  </tr>

  <tr>
    <td class="tdTitle">地址：</td>
    <td class="tdSpace">
    <form:input path="companyAdd.contactAddress" cssClass="size" cssStyle="width:400px" />
    </td>
  </tr>
  <tr>
   <td class="tdTitle">备注：</td>
    <td class="tdSpace">
    <form:input path="companyAdd.remark" cssClass="size" cssStyle="width:400px" />
    </td>
   <tr>
    <td colspan="4" class="tdSpace" align="center">
 	<input name="" type="button" onclick="save()" class="btn_submit" value="提 交" />
	<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
    </td>
    </tr>

</table>
</form>
</body>
</html>
