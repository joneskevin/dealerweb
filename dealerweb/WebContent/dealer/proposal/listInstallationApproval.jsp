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
	pageFormObj.action="<%=path%>/dealer/proposal/exportInstallationApproval.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/proposal/listApproval.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/proposal/listApproval.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆更新  > 新装审批</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	   <strong><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	   </strong>
		所属单位
		<form:select path='proposalCompanyVehcileInfo.proposal.companyId' cssClass="select_condition_company"  >
			<form:options items="${companyList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
		</form:select>
		VIN
		<form:input path="proposalCompanyVehcileInfo.vehicle.vin"  cssClass="ipt_txt" />
		车牌
		<form:input path="proposalCompanyVehcileInfo.vehicle.plateNumber" cssClass="ipt_txt" />
		网络代码
		<form:input path="proposalCompanyVehcileInfo.company.dealerCode" maxlength="12" cssClass="ipt_txt"/>
<!-- 		申请状态： -->
<%-- 		<form:select path='proposalCompanyVehcileInfo.proposal.status' cssClass="select_condition"> --%>
<%-- 				<form:option value="">--请选择--</form:option> --%>
<%-- 				<form:options items="${installationList}" itemLabel="optionText" itemValue="optionValue"  htmlEscape="false" /> --%>
<%-- 		</form:select> --%>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1520px;">
					<li class="distributionSaleCenterName_80">分销中心</li>
					<li class="cnName_240">经销商名称</li>
					<li class="dealerCode_70">网络代码</li>
					<li class="dealerType_Nick_70">网络形态</li>
					<li class="plateNumber_75">车牌</li>
					<li class="carStyleId_Nick_200">车型</li>
					<li class="vin_140">VIN</li>
					<li class="date_85">上牌日期</li>
					<li class="people_70">上牌人</li>
					<li class="time_150">提交时间</li>
					<li class="long_status_nick_100">状态</li>
					<li class="middle_operator_220">操作</li>
				</ul>
			<c:if test="${proposalCompanyVehcileInfoList != null}" >
			<c:forEach var="vo" items="${proposalCompanyVehcileInfoList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1520px">
            		<li class="distributionSaleCenterName_80"><span title="${vo.company.parentName}">${vo.company.parentName}</span></li>
            		<li class="cnName_240"><span title="${vo.company.cnName}">${vo.company.cnName}</span></li>
            		<li class="dealerCode_70"><span title="${vo.company.dealerCode}">${vo.company.dealerCode}</span></li>
            		<li class="dealerType_Nick_70"><span title="${vo.company.dealerType_Nick}">${vo.company.dealerType_Nick}</span></li>
            		<li class="plateNumber_75"><span title="${vo.vehicle.plateNumber}">${vo.vehicle.plateNumber}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.vehicle.carStyleId_Nick}">${vo.vehicle.carStyleId_Nick}</span></li>
            		<li class="vin_140"><span title="${vo.vehicle.vin}">${vo.vehicle.vin}</span></li>
            		<li class="date_85"><span title="<fmt:formatDate value="${vo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${vo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
            		<li class="people_70"><span title="${vo.vehicle.licensingExecutorName}">${vo.vehicle.licensingExecutorName}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${vo.proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.proposal.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="long_status_nick_100"><span title="${vo.proposal.status_nick}">${vo.proposal.status_nick}</span></li>
            		<li class="middle_operator_220">
						<% if (leftMenuCurrentUser.getUserRight().isRightVehicleUpdateManagement()) {  %>
						<!--【新装申请】待审核 -->
						<c:set var="proposalStatusProcessing" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_PROCESSING)%>"/> 
					 	<c:if test="${proposalStatusProcessing == vo.proposal.status}">
					 	
					 	<% if (leftMenuCurrentUser.getUserRight().isRightInstallationApproval()) {  %>
						<a href="javascript:openWindow('<%=path%>/dealer/proposal/displayProposalApproval.vti?proposalId=<c:out value="${vo.proposal.id}" />&startIndex=${transMsg.startIndex}', 660, 450, '新装申请信息')">审核</a>
						<% } %>
						</c:if>
						
						<c:if test="${proposalStatusProcessing != vo.proposal.status}">
					 	<% if (leftMenuCurrentUser.getUserRight().isRightInstallationApprovalView()) {  %>
						<a href="javascript:openWindow('<%=path%>/dealer/proposal/view.vti?proposalId=<c:out value="${vo.proposal.id}" />&startIndex=${transMsg.startIndex}', 660, 450, '新装申请信息')">查看</a>
						<% } %>
						</c:if>
						
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