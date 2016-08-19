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
	pageFormObj.action="<%=path%>/dealer/vehicle/exportMaintainVehicle.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/vehicle/listMaintainVehicle.vti?startIndex=${transMsg.startIndex}";
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/vehicle/listMaintainVehicle.vti" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：车辆信息  > 车辆维护</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	<strong><span>
		<a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
		<a href="javascript:openWindow('<%=path%>/dealer/vehicle/displayAdd.vti', 750, 420, '添加车辆')">添加车辆</a>
	 </span></strong>
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
	状态
	<form:select path='vehicle.configureStatus' cssClass="select_condition" >
			<form:option value="">--请选择--</form:option>
			<form:options items="${installationList}" itemLabel="optionText" itemValue="optionValue"  htmlEscape="false" />
	</form:select>
	<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" >
			<ul class="table_hd clearfix" style="width:1470px;">
				<li style="width:60px;">分销中心</li>
           		<li class="regionProvinceId_Nick_50">省份</li>
           		<li class="regionCityId_Nick_50">城市</li>
           		<li class="dealerCode_70">网络代码</li>
           		<li class="cnName_240">经销商名称</li>
           		<li class="dealerType_Nick_70">网络形态</li>
           		<li class="carStyleId_Nick_200">车型</li>
           		<li class="vin_140">VIN</li>
           		<li class="plateNumber_75">车牌</li>
           		<li class="uniqueId_120">设备号</li>
           		<li class="time_150">安装时间</li>
           		<li class="configureStatus_Nick_80">状态</li>
           		<li class="isNetWorkDealerNick_100">新入网经销商</li>
           		<li style="width:50px;">操作</li>
			</ul>
			<c:if test="${vehicleList != null}" >
				<c:forEach var="vehicle" items="${vehicleList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1470px;">
	            		<li style="width:60px;"><span title="${vehicle.saleCenterName}">${vehicle.saleCenterName}</span></li>
	            		<li class="regionProvinceId_Nick_50"><span title="${vehicle.provinceName}">${vehicle.provinceName}</span></li>
	            		<li class="regionCityId_Nick_50"><span title="${vehicle.cityName}">${vehicle.cityName}</span></li>
	            		<li class="dealerCode_70"><span title="${vehicle.dealerCode}">${vehicle.dealerCode}</span></li>
	            		<li class="cnName_240"><span title="${vehicle.companyName}">${vehicle.companyName}</span></li>
	            		<li class="dealerType_Nick_70"><span title="${vehicle.dealerTypeNick}">${vehicle.dealerTypeNick}</span></li>
	            		<li class="carStyleId_Nick_200"><span title="${vehicle.carStyleName}">${vehicle.carStyleName}</span></li>
	            		<li class="vin_140"><span title="${vehicle.vin}">${vehicle.vin}</span></li>
	            		<li class="plateNumber_75"><span title="${vehicle.plateNumber}">${vehicle.plateNumber}</span></li>
	            		<li class="uniqueId_120"><span title="${vehicle.obdNo}">${vehicle.obdNo}</span></li>
	            		<li class="time_150"><span title="<fmt:formatDate value="${vehicle.installationTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${vehicle.installationTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
	            		<li class="configureStatus_Nick_80"><span title="${vehicle.configureStatusNick}">${vehicle.configureStatusNick}</span></li>
	            		<li class="isNetWorkDealerNick_100"><span title="${vehicle.isNetWorkDealerNick}">${vehicle.isNetWorkDealerNick}</span></li>
	            		<li style="width:50px;">
	            			<a href="javascript:openWindow('<%=path%>/dealer/vehicle/displayEdit.vti?vehicleId=${vehicle.vehicleId}', 750, 420, '编辑车辆')">编辑</a>
	            		</li>
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