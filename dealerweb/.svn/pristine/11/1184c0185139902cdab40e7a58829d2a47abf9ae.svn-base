<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp" %>
<%@ page import="com.ava.resource.GlobalConstant" %>
<%@ page import="java.util.Date" %>

<%
	Integer currentYear = Integer.valueOf(DateTime.getYear());
	Integer nextYear = currentYear + 1;
	Integer yearAfter = currentYear + 2;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<%@ include file="/dealer/include/typeAndSubtypeCascader.jsp"%>
<script>
$(document).ready(function(){
	$(".years").change(function(){
	  var years = $("#years").attr("value");
	  var companyId = $("#companyId").attr("value");
	  
	  var targetUrl = "<%=request.getContextPath()%>/base/dealer/findCarStyle.vti?years=" + years;
	  	  targetUrl += "&companyId=" + companyId;
	  	  targetUrl += "&random=" + Math.random();
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
		         if (responseData.data.carStyleVOList) {
		    	    	var carStyleSize = responseData.data.carStyleVOList.length;
		    	    	for (var i = 0; i < carStyleSize; i++) {
		    	    		var carStyle = responseData.data.carStyleVOList[i];
		    	    		if (carStyle) {
		    					var congfigType = carStyle.congfigType;//车型配置方式
		    					var id = carStyle.id;
		    					var configTypeName = "configType" + id;
		    					if (congfigType == 2) {
		    						$("input[name="+ configTypeName +"]").get(0).checked=true;  
		    					}
		    					if (congfigType == 1) {
		    						$("input[name="+ configTypeName +"]").get(1).checked=true;  
		    					}
		    					if (congfigType == 0) {
		    						$("input[name="+ configTypeName +"]").get(2).checked=true;  
		    					}
		    	    	 	}	
		    	    	 }
		    	  	}
		         
		     } else {
		      jAlert(responseData.message);
		     }
		    }
		 });
	});
});
</script>

<body style="overflow-x:hidden ;">
<form method="post" action = "<%=request.getContextPath()%>/base/dealer/configCarStyle.vti">
<form:hidden id="companyId" path="company.id"/>
<table border="0" cellpadding="0" cellspacing="0"  class="tableBox">
  <tr>
    <td class="tdTitle" >经销称名称：</td>
    <td class="tdSpace">
    <c:out value="${company.cnName}"></c:out>
    </td>
  </tr>
  <c:if test="${carStyleVOList != null}" >
  <c:forEach var="carStyle" items="${carStyleVOList}">
	  <tr>
	    <td class="tdTitle" style="width: 230px;"><c:out value="${carStyle.name} ${carStyle.yearType}" /></td>
	    
	    <td class="tdSpace">
	     <input type="radio"  name="configType${carStyle.id}" ${carStyle.congfigType == 2 ? "checked='checked'" : ""} value="2">必配</input>
	     <input type="radio"  name="configType${carStyle.id}" ${carStyle.congfigType == 1 ? "checked='checked'" : ""} value="1">选配</input>
	     <input type="radio"  name="configType${carStyle.id}" ${carStyle.congfigType == 0 ? "checked='checked'" : ""}  value="0">无需配置</input>
	    </td>
	  </tr>
  </c:forEach>
  </c:if>
  
   <tr>
	<td colspan="4" class="tdSpace" align="center">
	<input name="" type="submit" class="btn_submit" value="提 交" />
	</td>
   </tr>

</table>
</form>
</body>
</html>
