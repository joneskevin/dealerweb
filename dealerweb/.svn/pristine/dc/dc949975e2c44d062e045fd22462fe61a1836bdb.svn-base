<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
var pageFormObj = document.getElementById("myPageForm");
function query() {
	var startTime=$("#startTime").val(); 
	var endTime=$("#endTime").val(); 
	if (startTime == "" || endTime == "") {
		alert("开始时间结束时间都不能为空!");                        
		return false;      
	}
	if(startTime > endTime) {                       
		alert("开始时间不能大于结束时间!");                        
		return false;                    
	}
	var DifDay = dateDiff(startTime, endTime);
	if (DifDay > 92) {
		alert("试驾时间范围不能大于一个季度!");                        
		return false;   
	}
	pageFormObj.submit();
}

//计算两个日期天数差的函数，通用
function dateDiff(date1, date2) {
    var type1 = typeof date1,
        type2 = typeof date2;
    if (type1 == 'string')
        date1 = stringToTime(date1);
    else if (date1.getTime)
        date1 = date1.getTime();
    if (type2 == 'string')
        date2 = stringToTime(date2);
    else if (date2.getTime)
        date2 = date2.getTime();
    return (date2 - date1) / (1000 * 60 * 60 * 24); //结果是秒
}

function stringToTime(string) {
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null, (parseInt(d[1], 10) || 1) - 1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null
    )).getTime();
}

function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/testDrive/exportValidTestDrive.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/testDrive/listValidTestDrive.vti";
}
</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/testDrive/listValidTestDrive.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：试驾明细  > 有效试驾</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
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
	试驾时间
	<input id="startTime" name="startTime"  value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" 
	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" class="Wdate left" type="text" />
	-
	<input id="endTime" name="endTime"  value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>" 
	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" class="Wdate left" type="text" />
	<input type="button" onclick="query()" class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix" style="width:2210px;">
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
				<li class="lineId_Nick_70">线路</li>
				<li class="loopCount_50">次数</li>
				<li class="mileage_70">里程[km]</li>
				<li class="date_85">试驾日期</li>
				<li class="time_150">开始时间</li>
				<li class="time_150">结束时间</li>
				<li class="status_nick_70">状态</li>
				<li class="short_operator_150">操作</li>
				<li class="parentCode_100">一级网点代码</li>
            	<li class="parentCodeName_240">一级网点名称</li>
			</ul>
			<c:if test="${testDriveList != null}" >
				<c:forEach var="vo" items="${testDriveList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2210px;">
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
	            		<li class="lineId_Nick_70"><span title="${vo.lineNick}">${vo.lineNick}</span></li>
	            		<li class="loopCount_50"><span title="${vo.loopCount}">${vo.loopCount}</span></li>
	            		<li class="mileage_70"><span title="<fmt:formatNumber type="number" maxFractionDigits="1" value="${vo.mileage/1000}" />"><fmt:formatNumber type="number" maxFractionDigits="1" value="${vo.mileage/1000}" /></span></li>
	            		<li class="date_85"><span title="${vo.testDriveDate}">${vo.testDriveDate}</span></li>
	            		<li class="time_150"><span title="<fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            		<li class="time_150"><span title="<fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
	            		<li class="status_nick_70"><span title="${vo.statusNick}">${vo.statusNick}</span></li>
	            		<li class="short_operator_150">
							<a href='<%=path%>/dealer/track/home.vti?plateNumber=${vo.plateNumber}&startTime=<fmt:formatDate value="${vo.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>&endTime=<fmt:formatDate value="${vo.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>'>查看轨迹</a>
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