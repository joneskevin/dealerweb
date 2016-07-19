<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/testDrive/outputListNoTestDriveExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/testDrive/listNoTestDrive.vti";
}

</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/testDrive/listNoTestDrive.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：违规明细  > 非活跃试驾明细</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
			<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
			VIN：
			<form:input path="noTestDrive.vehicle.vin"  cssClass="ipt_txt" />
			车牌：
			<form:input path="noTestDrive.vehicle.plateNumber" cssClass="ipt_txt" />
			网络代码：
			<form:input path="noTestDrive.dealer.dealerCode" cssClass="ipt_txt" />
			试驾季度：
			<form:select path='noTestDrive.season' cssClass="select_condition" >
				<form:options items="${testDriveSeasonList}" itemLabel="optionText" itemValue="optionValue"  />
			</form:select>
			连续无试驾天数：
			<form:input path="noTestDrive.durationDayCount" maxlength="3" 
				onkeyup="this.value=this.value.replace(/\D/g,'')" 
				onafterpaste="this.value=this.value.replace(/\D/g,'')"  cssClass="ipt_txt" cssStyle="width:50px"/>
			<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1460px;">
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
					<li class="countId_70">连续天数</li>
					<li class="time_150">开始时间</li>
					<li class="time_150">结束时间</li>
				</ul>
				<c:if test="${noTestDriveList != null}" >
				<c:forEach var="noTestDrive" items="${noTestDriveList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1460px;">
            		<li class="distributionSaleCenterName_80"><span title="${noTestDrive.dealer.distributionSaleCenterName}">${noTestDrive.dealer.distributionSaleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${noTestDrive.dealer.regionProvinceId_Nick}">${noTestDrive.dealer.regionProvinceId_Nick}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${noTestDrive.dealer.regionCityId_Nick}">${noTestDrive.dealer.regionCityId_Nick}</span></li>
            		<li class="cnName_240"><span title="${noTestDrive.dealer.cnName}">${noTestDrive.dealer.cnName}</span></li>
            		<li class="dealerCode_70"><span title="${noTestDrive.dealer.dealerCode}">${noTestDrive.dealer.dealerCode}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${noTestDrive.dealer.isKeyCity_Nick}">${noTestDrive.dealer.isKeyCity_Nick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${noTestDrive.dealer.dealerType_Nick}">${noTestDrive.dealer.dealerType_Nick}</span></li>
            		<li class="plateNumber_75"><span title="${noTestDrive.vehicle.plateNumber}">${noTestDrive.vehicle.plateNumber}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${noTestDrive.vehicle.carStyleId_Nick}">${noTestDrive.vehicle.carStyleId_Nick}</span></li>
            		<li class="vin_140"><span title="${noTestDrive.vehicle.vin}">${noTestDrive.vehicle.vin}</span></li>
            		<li class="countId_70"><span title="${noTestDrive.durationDayCount}">${noTestDrive.durationDayCount}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${noTestDrive.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${noTestDrive.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${noTestDrive.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${noTestDrive.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
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