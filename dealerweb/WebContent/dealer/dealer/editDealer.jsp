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
	
	Company company = (Company)request.getAttribute("company");
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
	$().ready(function(){
		dealerTypeChange();
		cascadeSelector3Level.init('regionCountryId','<%=regionCountryId%>','regionProvinceId','<%=regionProvinceId%>','regionCityId','<%=regionCityId%>');
	});
	
	function save(){
		var startTime = $('#startWorkTime').val();
		var endTime = $('#endWorkTime').val();
		if (startTime != " " && endTime != " ") {
			if(parseInt(startTime) >= parseInt(endTime)){
				alert("下班时间必须大于上班时间");
				return;
			}
		}
		var pageFormObj = document.getElementById("myPageForm");
		pageFormObj.action="<%=request.getContextPath()%>/base/dealer/edit.vti?startIndex=${startIndex}";
		pageFormObj.submit();
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
<form id="myPageForm" method="post" action = "<%=request.getContextPath()%>/base/dealer/edit.vti">
<form:hidden path="company.id"/>
<form:hidden id="dealerType" path="company.dealerType" />
<table border="0" cellpadding="0" cellspacing="0" class="tableBox">
	<tr>
    <td class="tdTitle" style="width:130px;"><em>*</em> 分销中心：</td>
    <td class="tdSpace">
	<form:select path='company.parentId'  >
		<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select>
    </td>
  </tr>
   <tr>
    <td class="tdTitle"><em>*</em> 省份：</td>
    <td class="tdSpace">
   <form:select id="regionCountryId" path="company.regionCountryId" onchange="cascadeSelector3Level.changeDataSort1('regionCountryId','regionProvinceId','regionCityId')" style="display:none;width:180px;"></form:select>
	<form:select  id="regionProvinceId" path="company.regionProvinceId" onchange="cascadeSelector3Level.changeDataSort2('regionCountryId','regionProvinceId','regionCityId')" style="width:110px"></form:select>
	&nbsp;&nbsp;<em>*</em>城市
	<form:select id="regionCityId" path="company.regionCityId" style="width:110px"></form:select>
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 网络代码：</td>
    <td class="tdSpace">
    <form:input path="company.dealerCode" cssClass="size" maxlength="8" cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em> 经销商名称：</td>
    <td class="tdSpace">
    <form:input path="company.cnName" cssClass="size" maxlength="30" cssStyle="width:200px"/>
    </td>
  </tr>
   <tr>
	  <td class="tdTitle"><em>*</em> 经销商状态：</td>
	  <td class="tdSpace">
		<form:select path='company.isKeyCity' cssClass="select_condition" >
			    <form:option value=" ">--请选择--</form:option>
				<form:options items="${isKeyCityList}"  itemLabel="optionText" itemValue="optionValue"  />
	    </form:select>
	    &nbsp;&nbsp; 网络形态：${company.dealerType_Nick}
	 </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em>状态：</td>
    <td class="tdSpace">
    <form:select path='company.deletionFlag' cssClass="select_condition"  >
		<form:options items="${dealerStatusList}" itemLabel="optionText" itemValue="optionValue" htmlEscape="false" />
	</form:select>
    </td>
  </tr>
  <tr id="parentCompanyRow">
    <td class="tdTitle">上级网点：</td>
    <td class="tdSpace">
    ${company.parentCompanyName}
    </td>
  </tr>
  <tr>
    <td class="tdTitle"><em>*</em>邮箱：</td>
    <td class="tdSpace">
    <form:input path="company.email" cssClass="size" maxlength="30" cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle">电话：</td>
    <td class="tdSpace">
     <form:input path="company.contactTel" cssClass="size" maxlength="15" cssStyle="width:200px" />
    </td>
  </tr>
  <tr>
    <td class="tdTitle">传真：</td>
    <td class="tdSpace">
    <form:input path="company.fax" cssClass="size" maxlength="15" cssStyle="width:200px" />
    </td>
  </tr>

  <tr>
    <td class="tdTitle">地址：</td>
    <td class="tdSpace">
    <form:input path="company.contactAddress" cssClass="size" cssStyle="width:400px" />
    </td>
  </tr>
  <tr>
   <td class="tdTitle">备注：</td>
    <td class="tdSpace">
    <form:input path="company.remark" cssClass="size" cssStyle="width:400px" />
    </td>
   <tr>
	<td colspan="4" class="tdSpace" align="center">
	<input name="" type="button" class="btn_submit"  onclick="save()" value="提 交" />
	<input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
	</td>
	</tr>

</table>
</form>
</body>
</html>
