<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%
	User leftMenuCurrentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

function selectAll(checkeAllObj, checkboxName) {
	if ($(checkeAllObj).is('.active')) {
		$(checkeAllObj).removeClass("active");
	} else {
		$(checkeAllObj).addClass("active");
	}
	$(".checkbox").each(function(index,domEle){
		if ($(this).is('.active')) {
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	});
}

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/proposal/exportInstallationPlanExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/installationPlan/listInstallationPlan.vti?startIndex=${transMsg.startIndex}";
}

function comfirmEnable(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定启用吗？")) {
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
		    	 pageFormObj.action="<%=path%>/dealer/installationPlan/listInstallationPlan.vti";
		    	 var startIndex = "${transMsg.startIndex}";
		    	 if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	 }
		    	 pageFormObj.submit();
		         
		     } else {
		        jAlert(responseData.message);
		     }
		    }
		 });
	} 
}

function comfirmDisable(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定停用吗？")) {
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
		    	 pageFormObj.action="<%=path%>/dealer/installationPlan/listInstallationPlan.vti";
		    	 var startIndex = "${transMsg.startIndex}";
		    	 if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	 }
		    	 pageFormObj.submit();
		         
		     } else {
		        jAlert(responseData.message);
		     }
		    }
		 });
	} 
}

function comfirmDelete(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定要删除此记录吗？")) {
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
		    	 pageFormObj.action="<%=path%>/dealer/installationPlan/listInstallationPlan.vti";
		    	 var startIndex = "${transMsg.startIndex}";
		    	 if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	 }
		    	 pageFormObj.submit();
		         
		     } else {
		        jAlert(responseData.message);
		     }
		    }
		 });
	} 
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/installationPlan/listInstallationPlan.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：系统设置  > 换装拆除计划</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	   <strong><span>
	   <a href="javascript:openWindow('<%=path%>/dealer/installationPlan/displayAddInstallationPlan.vti?startIndex=${transMsg.startIndex}&type=1', 660, 200, '添加换装计划')" >添加换装计划</a>
	   <a href="javascript:openWindow('<%=path%>/dealer/installationPlan/displayAddInstallationPlan.vti?startIndex=${transMsg.startIndex}&type=2', 660, 200, '添加拆除计划')" >添加拆除计划</a>
	   </span></strong>
	 换装前/待拆除车型
	<form:select path='vehicleInstallationPlan.testDriveCarStyleId' cssClass="select_condition" >
			<form:option value="">--请选择--</form:option>
			<form:options items="${carStyleList}" itemLabel="name" itemValue="id" htmlEscape="false" />
	</form:select>
		<input type="submit" class="btn_submit" value="查询" /> 
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:2240px;">
					<li class="regionCityId_Nick_50">计划ID</li>
					<li class="regionCityId_Nick_50">类型</li>
					<li class="carStyleId_Nick_200">换装前/待拆除车型ID</li>
					<li class="carStyleId_Nick_200">换装前/待拆除车型</li>
					<li class="carStyleId_Nick_200">换装后车型ID</li>
					<li class="carStyleId_Nick_200">换装后车型</li>
					<li class="carStyleId_Nick_200">状态</li>
					<li class="middle_operator_220">操作</li>
				</ul>
			<c:if test="${vehicleInstallationPlanList != null}" >
			<c:forEach var="vo" items="${vehicleInstallationPlanList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2240px">
            		<li class="regionCityId_Nick_50"><span title="${vo.id}">${vo.id}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${vo.typeNick}">${vo.typeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.testDriveCarStyleId}">${vo.testDriveCarStyleId}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.testDriveCarStyleName}">${vo.testDriveCarStyleName}</span></li>
            		<li class=carStyleId_Nick_200><span title="${vo.dessCarStyleId}">${vo.dessCarStyleId}</span></li>
            		<li class=carStyleId_Nick_200><span title="${vo.dessCarStyleName}">${vo.dessCarStyleName}</span></li>
            		<li class=carStyleId_Nick_200><span title="${vo.flagNick}">${vo.flagNick}</span></li>
            		<li class="middle_operator_220">
	            		<a href="#" onClick="javascript:comfirmEnable('<%=path%>/dealer/installationPlan/enableInstallationPlan.vti?installationPlanId=${vo.id}&startIndex=${transMsg.startIndex}');">启用</a>
	            		<c:if test="${vo.flag == 0}">
	            		<a href="#" onClick="javascript:comfirmDisable('<%=path%>/dealer/installationPlan/disableInstallationPlan.vti?installationPlanId=${vo.id}&startIndex=${transMsg.startIndex}');">停用</a>
	            		</c:if>
	            		<c:if test="${vo.flag == 1}">
	            		<a href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/installationPlan/deleteInstallationPlan.vti?installationPlanId=${vo.id}&startIndex=${transMsg.startIndex}');">删除</a>
	            		</c:if>
            		</li>
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div>
</form>
</body>
</html>