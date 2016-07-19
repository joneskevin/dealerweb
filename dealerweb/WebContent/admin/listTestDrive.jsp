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
	pageFormObj.action="<%=path%>/dealer/testDrive/exportValidTestDrive.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/testDrive/listValidTestDrive.vti";
}

window.onload=function(){
	var select = document.getElementById("testDriveStauts");
	var options = select.options;
	var selectValue = '${testDrive.status}';
	if(selectValue==false && !selectValue!="0"){
		options[0].selected = true;//设置默认选择项
	}
	//alert(selectValue);
}
</script>

<body>
<form id="myPageForm" action="<%=path%>/admin/testDrive/listTestDrive.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > 试驾查询</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	分销中心
	<form:select path='testDrive.dealer.parentId' cssClass="select_condition" >
		<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
	</form:select>
	网络代码
	<form:input path="testDrive.dealer.dealerCode" maxlength="7" cssClass="ipt_txt" />
	VIN
	<form:input path="testDrive.vehicle.vin" maxlength="17" cssClass="ipt_txt" />
	车牌
	<form:input path="testDrive.vehicle.plateNumber" maxlength="7" cssClass="ipt_txt" />
	起始时间
	<form:input path="testDrive.startTime_Nick"  cssClass="ipt_txt" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	结束时间
	<form:input path="testDrive.endTime_Nick"  cssClass="ipt_txt" name="endTime"  id="txtEndTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	状态
	<form:select path="testDrive.status" id="testDriveStauts">
		<form:option value="">全部</form:option>
		<form:option value="1">有效</form:option>
		<form:option value="0">无效驾驶</form:option>
		<form:option value="2">违规 </form:option>
		<form:option value="3">加油线路</form:option>
		<form:option value="4">4S店内试跑</form:option>
		
	</form:select>
	<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix" style="width:2210px;">
           		<li class="regionProvinceId_Nick_50">省份</li>
           		<li class="regionCityId_Nick_50">城市</li>
           		<li class="dealerCode_70">网络代码</li>
           		<li class="cnName_240">经销商名称</li>
           		<li class="isKeyCity_Nick_80">经销商状态</li>
           		<li class="dealerType_Nick_70">网络形态</li>
           		<li class="carStyleId_Nick_200">车型</li>
           		<li class="vin_140">VIN</li>
           		<li class="plateNumber_75">车牌</li>
				<li class="lineId_Nick_70">线路</li>
				<li class="loopCount_50">次数</li>
				<li class="mileage_70">里程[km]</li>
				<li class="date_85">试驾日期</li>
				<li class="time_150">开始时间</li>
				<li class="time_150">结束时间</li>
				<li class="status_nick_70">状态</li>
				<li class="time_150">试驾是否完成</li>
				<li class="short_operator_150">操作</li>
			</ul>
			<c:if test="${testDriveList != null}" >
				<c:forEach var="vo" items="${testDriveList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2210px;">
	            		<li class="regionProvinceId_Nick_50"><span title="${vo.provinceName}">${vo.provinceName}</span></li>
	            		<li class="regionCityId_Nick_50"><span title="${vo.cityName}">${vo.cityName}</span></li>
	            		<li class="dealerCode_70"><span title="${vo.dealerCode}">${vo.dealerCode}</span></li>
	            		<li class="cnName_240"><span title="${vo.companyName}">${vo.companyName}</span></li>
	            		<li class="isKeyCity_Nick_80"><span title="${vo.isKeyCityNick}">${vo.isKeyCityNick}</span></li>
	            		<li class="dealerType_Nick_70"><span title="${vo.dealerTypeNick}">${vo.dealerTypeNick}</span></li>
	            		<li class="carStyleId_Nick_200"><span title="${vo.carStyleName}">${vo.carStyleName}</span></li>
	            		<li class="vin_140"><span title="${vo.vin}">${vo.vin}</span></li>
	            		<li class="plateNumber_75"><span title="${vo.plateNumber}">${vo.plateNumber}</span></li>
	            		<li class="lineId_Nick_70"><span title="${vo.lineNick}">${vo.lineNick}</span></li>
	            		<li class="loopCount_50"><span title="${vo.loopCount}">${vo.loopCount}</span></li>
	            		<li class="mileage_70"><span title="<fmt:formatNumber type="number" maxFractionDigits="1" value="${vo.mileage/1000}" />"><fmt:formatNumber type="number" maxFractionDigits="1" value="${vo.mileage/1000}" /></span></li>
	            		<li class="date_85"><span title="${vo.testDriveDate}">${vo.testDriveDate}</span></li>
	            		<li class="time_150"><span title="<fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            		<li class="time_150"><span title="<fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            		<li class="status_nick_70"><span title="${vo.statusNick}">${vo.statusNick}</span></li>
	            		<li class="time_150"><span title="${vo.currentStatus}">${vo.currentStatus}</span></li>
	            		<li class="short_operator_150">
							<a href='<%=path%>/dealer/track/home.vti?plateNumber=${vo.plateNumber}&startTime=<fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>&endTime=<fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>'>查看轨迹</a>
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