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
		    	 pageFormObj.action="<%=path%>/dealer/proposal/search.vti";
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

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/proposal/outputProposalExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/proposal/search.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/proposal/search.vti" method="get" >
<input type="hidden" name="seriesId" value="${seriesId}" />
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆更新  > ${seriesName}新装</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	   <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	   <% if (leftMenuCurrentUser.getUserRight().isRightInstallationProposalAdd()) {  %>
	   <a href="javascript:openWindow('<%=path%>/dealer/proposal/displayAdd.vti?startIndex=${transMsg.startIndex}&seriesId=${seriesId}', 660, 470, '新增车辆申请')" >新增车辆申请</a>
	   <% } %>
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
		<%-- 申请状态
		<form:select path='proposalCompanyVehcileInfo.proposal.status' cssClass="select_condition" >
				<form:option value="">--请选择--</form:option>
				<form:options items="${installationList}" itemLabel="optionText" itemValue="optionValue"  htmlEscape="false" />
		</form:select> --%>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:2240px;">
					<li class="distributionSaleCenterName_80">分销中心</li>
					<li class="regionProvinceId_Nick_50">省份</li>
					<li class="regionCityId_Nick_50">城市</li>
					<li class="dealerCode_70">网络代码</li>
					<li class="cnName_240">经销商名称</li>
					<li class="isKeyCity_Nick_80">经销商状态</li>
					<li class="dealerType_Nick_70">网络形态</li>
					<li class="carStyleId_Nick_200">车型</li>
					<li class="vin_140">VIN</li>
					<li class="plateNumber_75">车牌</li>
					<li class="date_85">上牌日期</li>
					<li class="people_70">上牌人</li>
					<li class="people_70">联系人</li>
					<li class="contact_120">联系电话</li>
					<li class="time_150">提交时间</li>
					<li class="long_status_nick_100">状态</li>
					<li class="middle_operator_220">操作</li>
					<li class="parentCode_100">一级网点代码</li>
					<li class="parentCodeName_240">一级网点名称</li>
				</ul>
			<c:if test="${proposalCompanyVehcileInfoList != null}" >
			<c:forEach var="vo" items="${proposalCompanyVehcileInfoList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2240px">
            		<li class="distributionSaleCenterName_80"><span title="${vo.saleCenterName}">${vo.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${vo.provinceName}">${vo.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${vo.cityName}">${vo.cityName}</span></li>
            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${vo.isKeyCityNick}">${vo.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${vo.dealerTypeNick}">${vo.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vo.carStyleName}">${vo.carStyleName}</span></li>
            		<li class="vin_140"><span title="${vo.vin}">${vo.vin}</span></li>
            		<li class="plateNumber_75"><span title="${vo.plateNumber}">${vo.plateNumber}</span></li>
            		<li class="date_85"><span title="<fmt:formatDate value="${vo.licensingTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${vo.licensingTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
            		<li class="people_70"><span title="${vo.licensingExecutorName}">${vo.licensingExecutorName}</span></li>
            		<li class="people_70"><span title="${vo.contactName}">${vo.contactName}</span></li>
            		<li class="contact_120"><span title="${vo.contactPhone}">${vo.contactPhone}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${vo.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="long_status_nick_100"><span title="${vo.statusNick}">${vo.statusNick}</span></li>
            		<li class="middle_operator_220">
						<!--【新装申请】待审核 -->
						<a href="javascript:openWindow('<%=path%>/dealer/proposal/view.vti?proposalId=${vo.proposalId}&startIndex=${transMsg.startIndex}', 760, 450, '新装申请信息')">查看</a>
						
						<c:set var="proposalStatusPassed" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_PASSED)%>"/>
					 	<c:if test="${proposalStatusPassed == vo.status && vo.restrictionCityId > 0 && vo.plateNumber == '' }">
					 	
					 	<% if (leftMenuCurrentUser.getUserRight().isRightInstallationProposalEdit()) {  %>
						<a href="javascript:openWindow('<%=path%>/dealer/proposal/displayEdit.vti?companyId=${vo.companyId}&proposalId=${vo.proposalId}&seriesId=${seriesId}&startIndex=${transMsg.startIndex}', 660, 420, '编辑申请')">编辑</a>
						<% } %>
						
<%-- 						<% if (leftMenuCurrentUser.getUserRight().isRightInstallationProposalCancle()) {  %> --%>
<%-- 				 		 <a  href="#" onClick="javascript:comfirmCancel('<%=path%>/dealer/proposal/cancelProposal.vti?proposalId=<c:out value="${vo.proposal.id}"/>&proposalType=1&startIndex=${transMsg.startIndex}');">取消申请</a> --%>
				 		  
<%-- 				 		 <% } %> --%>
				 		 
						</c:if>
						<c:set var="proposalStatusPassed" value="<%=String.valueOf(GlobalConstant.PROPOSAL_STATUS_PASSED)%>"/> 
					 	<c:if test="${proposalStatusPassed == vo.status}">
					 	
					 	<% if (leftMenuCurrentUser.getUserRight().isRightInstallationProposalSuccess()) {  %>
						<%--<a href="javascript:openWindow('<%=path%>/dealer/proposal/displayInstallationComplete.vti?proposalId=<c:out value="${vo.proposalId}" />&startIndex=${transMsg.startIndex}', 660, 450, '安装完成信息')">安装完成</a> --%>
				 		  <% } %>
				 		  
						</c:if>
            		</li>
            		<li class="parentCode_100"><span title="${vo.parentDealerCode}">${vo.parentDealerCode}</span></li>
            		<li class="parentCodeName_240"><span title="${vo.parentCompanyName}">${vo.parentCompanyName}</span></li>
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div><!--/.bd_rt-->
</form>
</body>
</html>