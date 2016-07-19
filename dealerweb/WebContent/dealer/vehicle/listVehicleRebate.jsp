<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

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
	pageFormObj.action="<%=path%>/dealer/vehicle/exportVehicleRebate.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/vehicle/listVehicleRebate.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/vehicle/listVehicleRebate.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆信息  > 车辆返利</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
		 分销中心
		<form:select path='vehicle.dealer.parentId' cssClass="select_condition" >
			<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
		</form:select>
		网络代码
		<form:input path="vehicle.dealer.dealerCode" cssClass="ipt_txt"/>
		VIN
		<form:input path="vehicle.vin" cssClass="ipt_txt"/>
		车牌
		<form:input path="vehicle.plateNumber" cssClass="ipt_txt"/>
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" >
				<ul class="table_hd clearfix" style="width:1545px;">
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
            		<li class="season_50">季度</li>
            		<li class="isRebateNick_80">返利情况</li>
            		<li class="parentCode_100">一级网点代码</li>
            		<li class="parentCodeName_240">一级网点名称</li>
				</ul>
			<c:if test="${vehicleRebateList != null}" >
				<c:forEach var="vehicleRebate" items="${vehicleRebateList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1545px;">
            		<li class="distributionSaleCenterName_80"><span title="${vehicleRebate.saleCenterName}">${vehicleRebate.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${vehicleRebate.provinceName}">${vehicleRebate.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${vehicleRebate.cityName}">${vehicleRebate.cityName}</span></li>
            		<li class="dealerCode_70"><span title="${vehicleRebate.dealerCode}">${vehicleRebate.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${vehicleRebate.companyName}">${vehicleRebate.companyName}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${vehicleRebate.isKeyCityNick}">${vehicleRebate.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${vehicleRebate.dealerTypeNick}">${vehicleRebate.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${vehicleRebate.carStyleName}">${vehicleRebate.carStyleName}</span></li>
            		<li class="vin_140"><span title="${vehicleRebate.vin}">${vehicleRebate.vin}</span></li>
            		<li class="plateNumber_75"><span title="${vehicleRebate.plateNumber}">${vehicleRebate.plateNumber}</span></li>
            		<li class="season_50"><span title="Q${vehicleRebate.season}">Q${vehicleRebate.season}</span></li>
            		<li class="isRebateNick_80"><span title="${vehicleRebate.isRebateNick}">${vehicleRebate.isRebateNick}</span></li>
            		<li class="parentCode_100"><span title="${vehicleRebate.parentDealerCode}">${vehicleRebate.parentDealerCode}</span></li>
            		<li class="parentCodeName_240"><span title="${vehicleRebate.parentCompanyName}">${vehicleRebate.parentCompanyName}</span></li>
				</ul>
				</c:forEach>
				</c:if>
	</div>
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div><!--/.bd_rt-->
</form>
</body>
</html>