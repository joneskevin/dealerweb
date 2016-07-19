<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>


<body>
<form id="myPageForm" action="<%=path%>/admin/testDrive/listGpsPoint.vti" method="get" >
<input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
    	<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
    	<span >当前位置：运营管理  > GPS报文查询</span>
		</c:if>
	</div>
	<div class="audit_nav_c">
	设备号
	<form:input path="currentGpsMessageVo.serialNumber" maxlength="17" cssClass="ipt_txt" />
	VIN
	<form:input path="currentGpsMessageVo.vin" maxlength="17" cssClass="ipt_txt" />
	起始时间
	<form:input path="currentGpsMessageVo.startDate"  cssClass="ipt_txt" name="startTime"  id="txtStartTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	结束时间
	<form:input path="currentGpsMessageVo.endDate"  cssClass="ipt_txt" name="endTime"  id="txtEndTime" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" />
	报文类型
	<form:select path="currentGpsMessageVo.messageCode" id="messageCode">
		<form:option value="">全部</form:option>
		<form:option value="1281">点火熄火</form:option>
		<form:option value="1284">GPS报文</form:option>
	</form:select>
	根据
	<form:select path="currentGpsMessageVo.orderType" id="orderType">
		<form:option value="createTime">创建时间</form:option>
		<form:option value="messageDate">报文时间</form:option>
	</form:select>
	<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
			<ul class="table_hd clearfix" style="width:2210px;">
				<li class="vin_140" >vin</li>
           		<li class="vin_140">设备号</li>
           		<li class="status_nick_70">报文编码</li>
           		<li class="status_nick_70">报文类型</li>
           		<li class="time_150">报文时间</li>
           		<li class="time_150">创建时间</li>
           		<li class="vin_140">IMSI</li>
           		<li class="vin_140">经度</li>
           		<li class="vin_140">纬度</li>
           		<li class="vin_140">百度经度</li>
           		<li class="vin_140">百度纬度</li>
           		<li class="status_nick_70">速度</li>
           		<li class="status_nick_70">里程计算值</li>
			</ul>
			<c:if test="${gpsPointList != null}" >
				<c:forEach var="vo" items="${gpsPointList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:2210px;">
						<li class="vin_140" ><span title="${vo.vin}">${vo.vin}</span></li>
	            		<li class="vin_140"><span title="${vo.serialNumber}">${vo.serialNumber}</span></li>
		            	<li class="status_nick_70"><span title="${vo.messageCode}">${vo.messageCode}</span></li>
						<li class="status_nick_70"><span title="${vo.fireState}">${vo.fireState}</span></li>
						<li class="time_150"><span title="${vo.messageDate}">${vo.messageDate}</span></li>
						<li class="time_150"><span title="${vo.createTime}">${vo.createTime}</span></li>
						<li class="vin_140"><span title="${vo.imsi}">${vo.imsi}</span></li>
						<li class="vin_140"><span title="${vo.lng}">${vo.lng}</span></li>
						<li class="vin_140"><span title="${vo.lat}">${vo.lat}</span></li>
						<li class="vin_140"><span title="${vo.baiduLng}">${vo.baiduLng}</span></li>
						<li class="vin_140"><span title="${vo.baiduLat}">${vo.baiduLat}</span></li>
						<li class="status_nick_70"><span title="${vo.speed}">${vo.speed}</span></li>
						<li class="status_nick_70"><span title="${vo.ko3Kilometer}">${vo.ko3Kilometer}</span></li>						
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