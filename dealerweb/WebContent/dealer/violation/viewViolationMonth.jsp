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
    
    pageFormObj.action="<%=path%>/dealer/violation/outputDetailMonth.vti?startIndex=${transMsg.startIndex}&vehicleId=${vehicleId}&driveLineId=${driveLineId}&startTime=${startTime}&endTime=${endTime}&plateNumber=${plateNumber}&year=${year}&month=${month}&typeId_nick=${typeId_nick}&companyName=${companyName}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/detailMonth.vti";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/violation/detailMonth.vti" method="get" >
<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicleId}" />
<input type="hidden" id="driveLineId" name="driveLineId" value="${driveLineId}" />
<input type="hidden" id="startTime" name="startTime" value="${startTime}" />
<input type="hidden" id="endTime" name="endTime" value="${endTime}" />
<input type="hidden" id="week" name="week" value="${week}" />
<input type="hidden" id="year" name="year" value="${year}" />
<input type="hidden" id="month" name="month" value="${month}" />
<input type="hidden" id="typeId_nick" name="typeId_nick" value="${typeId_nick}" />
<input type="hidden" id="carStyleId_Nick" name="carStyleId_Nick" value="${carStyleId_Nick}" />
<input type="hidden" id="companyName" name="companyName" value="${companyName}" />
<input type="hidden" id="vin" name="vin" value="${vin}" />
<input type="hidden" id="plateNumber" name="plateNumber" value="${plateNumber}" />
<input type="hidden" id="vinValue" name="vinValue" value="${vinValue}" />
<input type="hidden" id="plateNumberValue" name="plateNumberValue" value="${plateNumberValue}" />
<input type="hidden" id="dealerCode" name="dealerCode" value="${dealerCode}" />
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<span>当前位置：违规明细 > 月度统计 > 违规明细    经销商:${companyName} 车牌:${plateNumber}</span>
	 </div>
	<div class="audit_nav_c">
		 <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
			 <a href="<%=path%>/dealer/violation/listMonth.vti?plateNumber=${plateNumberValue}&year=${year}&month=${month}&dealerCode=${dealerCode}&vin=${vinValue}";">返回上级</a>
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix">
            		<li class="li_100">违规日期</li>
            		<li class="li_140">开始时间</li>
            		<li class="li_140">结束时间</li>
            		<li class="li_80">违规类型</li>
            		<li style="border:0;padding-left:15px;">操作</li>
				</ul>
				<c:if test="${violationList != null}" >
				<c:forEach var="violation" items="${violationList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
            	<li class="li_100"><span title="<fmt:formatDate value="${violation.creationTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${violation.creationTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
            	<li class="li_140"><span title="<fmt:formatDate value="${violation.start_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${violation.start_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_140"><span title="<fmt:formatDate value="${violation.end_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${violation.end_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
				<li class="li_80"><span title="${violation.typeId_nick}">${violation.typeId_nick}</span></li>
            		<li style="border:0;padding-left:15px;">
			         <a href="<%=path%>/dealer/track/home.vti?plateNumber=${plateNumber}&startTime=${violation.start_Time}&endTime=${violation.end_Time}">查看轨迹</a>
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