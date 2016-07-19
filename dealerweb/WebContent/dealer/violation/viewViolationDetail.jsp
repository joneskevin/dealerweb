<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%
	 User currentUser = SessionManager.getCurrentUser(request);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
    
    pageFormObj.action=	"<%=path%>/dealer/violation/exportViolationDetail.vti?startIndex=${transMsg.startIndex}";
        
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/viewViolationDetail.vti";
}

function queryViolationList(){
	var detailUrl="<%=path%>/dealer/violation/returnLineTimeViolation.vti?";
	pageFormObj.action=detailUrl;
    pageFormObj.submit();
}
</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/violation/viewViolationDetail.vti" method="get" >
<input type="hidden" id="violationType" name="violationType" value="${violationType}" />
<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicleId}" />
<input type="hidden" id="selectSaleCenterId" name="selectSaleCenterId" value="${selectSaleCenterId}" />
<input type="hidden" id="vin" name="vin" value="${vin}" />
<input type="hidden" id="plateNumber" name="plateNumber" value="${plateNumber}" />
<input type="hidden" id="dealerCode" name="dealerCode" value="${dealerCode}" />
<input type="hidden" id="violationYear" name="violationYear" value="${violationYear}" />
<input type="hidden" id="violationWeek" name="violationWeek" value="${violationWeek}" />
<input type="hidden" id="violationMonth" name="violationMonth" value="${violationMonth}" />
<input type="hidden" id="violationQuarter" name="violationQuarter" value="${violationQuarter}" />
<input type="hidden" id="myPageIndex" name="myPageIndex" value="${myPageIndex}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
		<span>当前位置：${locationMessage}</span>
	 </div>
	<div class="audit_nav_c">
		 <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
		 <a  href="#" onClick="queryViolationList();">返回上一级</a>
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix">
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="vin_140">VIN</li>
            		<li class="plateNumber_75">车牌</li>
            		<li class="li_140">试驾开始时间</li>
            		<li class="li_140">试驾结束时间</li>
            		<li class="li_140">违规时间</li>
            		<li style="border:0;padding-left:15px;">操作</li>
				</ul>
				<c:if test="${violationList != null}" >
				<c:forEach var="violation" items="${violationList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
	            	<li class="carStyleId_Nick_200"><span title="${violation.vehicleStyle}">${violation.vehicleStyle}</span></li>
	            	<li class="vin_140"><span title="${violation.vin}">${violation.vin}</span></li>
	            	<li class="plateNumber_75"><span title="${violation.plateNumber}">${violation.plateNumber}</span></li>
	            	<li class="li_140"><span title="<fmt:formatDate value="${violation.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${violation.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            	<li class="li_140"><span title="<fmt:formatDate value="${violation.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${violation.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            	<li class="li_140"><span title="<fmt:formatDate value="${violation.creationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${violation.creationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            	<li style="border:0;padding-left:15px;">
				         <a href="<%=path%>/dealer/track/home.vti?plateNumber=${violation.plateNumber}&startTime=${violation.startTime}&endTime=${violation.endTime}">查看轨迹</a>
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