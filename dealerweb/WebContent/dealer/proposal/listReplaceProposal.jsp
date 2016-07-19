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
	pageFormObj.action="<%=path%>/dealer/proposal/outputReplaceExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/proposal/listReplace.vti?startIndex=${transMsg.startIndex}";
}

</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/proposal/listReplace.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆更新  > 车辆换装</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
		<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	   </span></strong>
	       分销中心
		<form:select path='proposalCompanyVehcileInfo.company.parentId' cssClass="select_condition" >
			<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
		</form:select>
		网络代码
		<form:input path="proposalCompanyVehcileInfo.company.dealerCode" maxlength="7" cssClass="ipt_txt"/>
		VIN
		<form:input path="proposalCompanyVehcileInfo.vehicle.vin" maxlength="17" cssClass="ipt_txt" />
		车牌
		<form:input path="proposalCompanyVehcileInfo.vehicle.plateNumber" maxlength="7" cssClass="ipt_txt" />
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:2020px;">
					<li class="distributionSaleCenterName_80">分销中心</li>
					<li class="regionProvinceId_Nick_50">省份</li>
					<li class="regionCityId_Nick_50">城市</li>
					<li class="dealerCode_70">网络代码</li>
					<li class="cnName_240">经销商名称</li>
					<li class="isKeyCity_Nick_80">经销商状态</li>
					<li class="dealerType_Nick_70">网络形态</li>
					<li class="carStyleId_Nick_200">车型</li>
					<li class="carStyleId_Nick_200">待换装车型</li>
					<li class="vin_140">VIN</li>
					<li class="plateNumber_75">车牌</li>
					<li class="date_85">退出时间</li>
					<li class="long_status_nick_100">状态</li>
					<li class="middle_operator_220">操作</li>
					<li class="parentCode_100">一级网点代码</li>
					<li class="parentCodeName_240">一级网点名称</li>
				</ul>
			<c:if test="${proposalCompanyVehcileInfoList != null}" >
			<c:forEach var="vo" items="${proposalCompanyVehcileInfoList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2020px">
            		<li class="distributionSaleCenterName_80"><span title="${vo.saleCenterName}">${vo.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${vo.provinceName}">${vo.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${vo.cityName}">${vo.cityName}</span></li>
            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${vo.isKeyCityNick}">${vo.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${vo.dealerTypeNick}">${vo.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.carStyleName}">${vo.carStyleName}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.dessCarStyleName}">${vo.dessCarStyleName}</span></li>
            		<li class="vin_140"><span title="${vo.vin}">${vo.vin}</span></li>
            		<li class="plateNumber_75"><span title="${vo.plateNumber}">${vo.plateNumber}</span></li>
            		<li class="date_85"><span title="<fmt:formatDate value="${vo.exitTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${vo.exitTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
            		<c:set var="proposalStatusUnpassed" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_UNPASSED)%>"/>
            			<c:choose>
          			    <c:when test="${proposalStatusUnpassed == vo.status}">
          			    <li class="long_status_nick_100"><span title="拒绝">拒绝</span></li>
         				</c:when>
            			<c:otherwise>
            			<li class="long_status_nick_100"><span title="${vo.configureStatusNick}">${vo.configureStatusNick}</span></li>
			           </c:otherwise>
					    </c:choose>
            		<li class="middle_operator_220">
					 	<a href="javascript:openWindow('<%=path%>/dealer/proposal/viewReplace.vti?proposalId=<c:out value="${vo.proposalId}" />&vehicleId=<c:out value="${vo.vehicleId}" />&startIndex=${transMsg.startIndex}', 760, 450, '换装信息')">查看</a>
            			<c:set var="proposalStatusPassed" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_PASSED)%>"/>
					 	<c:if test="${proposalStatusPassed == vo.status && vo.restrictionCityId > 0 && vo.plateNumber == '' }">
						<a href="javascript:openWindow('<%=path%>/dealer/proposal/displayEditReplace.vti?companyId=${vo.companyId}&proposalId=<c:out value="${vo.proposalId}" />&startIndex=${transMsg.startIndex}', 660, 420, '编辑申请')">编辑</a>
						</c:if>
						<% if (leftMenuCurrentUser.getUserRight().isRightReplaceProposalAdd()) {  %>
						<c:set var="configureStatusInstalled" value="<%=String.valueOf(GlobalConstant.CONFIGURE_STATUS_INSTALLED)%>"/> 
					 	<c:if test="${configureStatusInstalled == vo.configureStatus && vo.dessCarStyleId > 0}">
				 		   <a href="javascript:openWindow('<%=path%>/dealer/proposal/displayAddReplace.vti?companyId=${vo.companyId}&replaceVehicleId=${vo.vehicleId}
				 		   &dessCarStyleId=${vo.dessCarStyleId}&startIndex=${transMsg.startIndex}', 660, 500, '换装')">换装</a>
						</c:if>
				 		 <% } %>
            		</li>
            		<li class="parentCode_100"><span title="${vo.parentDealerCode}">${vo.parentDealerCode}</span></li>
            		<li class="parentCodeName_240"><span title="${vo.parentCompanyName}">${vo.parentCompanyName}</span></li>
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