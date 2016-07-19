<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%
	User leftMenuCurrentUser = SessionManager.getCurrentUser(request);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

var pageFormObj = document.getElementById("myPageForm");
function comfirmCancel(targetUrl){
	if (confirm("确定要取消申请吗？")) {
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
		    	 pageFormObj.action="<%=path%>/dealer/proposal/listDemolition.vti";
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

function confirmDemolition(targetUrl){
	if(confirm("确定车辆拆除？")){
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
		    	 pageFormObj.action="<%=path%>/dealer/proposal/listDemolition.vti";
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

function confirmDemolitionComplete(targetUrl){
	if(confirm("确定车辆拆除完成？")){
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
		    	 pageFormObj.action="<%=path%>/dealer/proposal/listDemolition.vti";
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

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/proposal/outputDemolitionExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/proposal/listDemolition.vti?startIndex=${transMsg.startIndex}";
}

</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/proposal/listDemolition.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆更新  > 拆除申请</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
		<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	   </span></strong>
		VIN
		<form:input path="proposalCompanyVehcileInfo.vehicle.vin"  cssClass="ipt_txt" />
		车牌
		<form:input path="proposalCompanyVehcileInfo.vehicle.plateNumber" cssClass="ipt_txt" />
		网络代码
		<form:input path="proposalCompanyVehcileInfo.company.dealerCode" maxlength="12" cssClass="ipt_txt"/>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1580px;">
					<li class="distributionSaleCenterName_80">分销中心</li>
					<li class="regionProvinceId_Nick_50">省份</li>
					<li class="regionCityId_Nick_50">城市</li>
					<li class="cnName_240">经销商名称</li>
					<li class="dealerCode_70">网络代码</li>
					<li class="isKeyCity_Nick_80">经销商状态</li>
					<li class="dealerType_Nick_70">网络形态</li>
					<li class="plateNumber_75">车牌</li>
					<li class="carStyleId_Nick_200">车型</li>
					<li class="vin_140">VIN</li>
					<li class="simMobile_95">通讯号</li>
					<li class="date_85">退出时间</li>
					<li class="long_status_nick_100">状态</li>
					<li class="middle_operator_220">操作</li>
				</ul>
			<c:if test="${proposalCompanyVehcileInfoList != null}" >
			<c:forEach var="vo" items="${proposalCompanyVehcileInfoList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1580px">
            		<li class="distributionSaleCenterName_80"><span title="${vo.company.parentName}">${vo.company.parentName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${vo.company.regionProvinceId_Nick}">${vo.company.regionProvinceId_Nick}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${vo.company.regionCityId_Nick}">${vo.company.regionCityId_Nick}</span></li>
            		<li class="cnName_240"><span title="${vo.company.cnName}">${vo.company.cnName}</span></li>
            		<li class="dealerCode_70"><span title="${vo.company.dealerCode}">${vo.company.dealerCode}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${vo.company.isKeyCity_Nick}">${vo.company.isKeyCity_Nick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${vo.company.dealerType_Nick}">${vo.company.dealerType_Nick}</span></li>
            		<li class="plateNumber_75"><span title="${vo.vehicle.plateNumber}">${vo.vehicle.plateNumber}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.vehicle.carStyleId_Nick}">${vo.vehicle.carStyleId_Nick}</span></li>
            		<li class="vin_140"><span title="${vo.vehicle.vin}">${vo.vehicle.vin}</span></li>
            		<li class="simMobile_95"><span title="${vo.boxOperation.box.simMobile}">${vo.boxOperation.box.simMobile}</span></li>
            		<li class="date_85"><span title="<fmt:formatDate value="${vo.boxOperation.exitTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${vo.boxOperation.exitTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
            		<c:set var="proposalStatusUnpassed" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_UNPASSED)%>"/>
            			<c:choose>
          			    <c:when test="${proposalStatusUnpassed == vo.proposal.status}">
          			    <li class="long_status_nick_100"><span title="拒绝">拒绝</span></li>
         				</c:when>
            			<c:otherwise>
            			<li class="long_status_nick_100"><span title="${vo.vehicle.configureStatus_Nick}">${vo.vehicle.configureStatus_Nick}</span></li>
			           </c:otherwise>
					    </c:choose>		
            		
            		<li class="middle_operator_220">
						<% if (leftMenuCurrentUser.getUserRight().isRightVehicleUpdateManagement()) {  %>
					 	
					 	<% if (leftMenuCurrentUser.getUserRight().isRightDemolitionProposalView()) {  %>
					 	<a href="javascript:openWindow('<%=path%>/dealer/proposal/viewDemolition.vti?proposalId=<c:out value="${vo.proposal.id}" />&vehicleId=<c:out value="${vo.vehicle.id}" />&startIndex=${transMsg.startIndex}', 660, 450, '拆除申请信息')">查看</a>
					 	<% } %> 
					 	
						<% if (leftMenuCurrentUser.getUserRight().isRightDemolitionProposalSuccess()) {  %>
				 		  <a  href="#" onClick="javascript:confirmDemolitionComplete('<%=path%>/dealer/proposal/demolitionComplete.vti?vehicleId=<c:out value="${vo.vehicle.id}" /> &proposalType=2&startIndex=${transMsg.startIndex}');">拆除完成</a>
				 		 <% } %>
						
					 	<% } %>
            		</li>
			</ul>
		</c:forEach>
		</c:if>
	</div>
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div>
</form>
</body>
</html>