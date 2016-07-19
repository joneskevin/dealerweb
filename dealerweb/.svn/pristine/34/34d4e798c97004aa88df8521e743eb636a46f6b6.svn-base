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
	pageFormObj.action="<%=path%>/dealer/report/outputTestDriveInfoExcel.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/report/listTestDrive.vti?startIndex=${transMsg.startIndex}";
} 
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/report/listTestDrive.vti" method="get" >
<div class="bd_rt" id="audit">
	
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
		<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<span>当前位置：试驾明细 > 试驾明细 </span>
		</c:if>
	 </div>
	<div class="audit_nav_c">
	 	<strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
				VIN码 ：
				<form:input path="testDriveInfoVO.vin" cssClass="ipt_txt"/>
				车牌 ：
				<form:input path="testDriveInfoVO.plateNumber" cssClass="ipt_txt"/>
				 网络代码：
				<form:input path="testDriveInfoVO.id" cssClass="ipt_txt"/> 
				 试驾时间
				<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${testDriveInfoVO.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
				-
				<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" 	value="<fmt:formatDate value="${testDriveInfoVO.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		
		<input type="submit"  class="btn_submit" value="查询" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1690px;">
					<li class="li_100">经销商状态</li>
            		<li class="li_70">网络形态</li>
            		<li class="li_70">网络代码</li>
            		<li class="li_120">分销中心</li>
            		<li class="li_50">省份</li>
            		<li class="li_50">城市</li>
            		<li class="li_240">经销商名称</li>
             		
             		<li class="li_80">车牌</li>
            		<li class="li_120">车型</li>
             		<li class="li_130">VIN</li>
             		<li class="li_100">线路</li>
            		<li class="li_50">次数</li>
             		<li class="li_70">里程/km</li>
					<li class="li_140">开始时间</li>
             		<li class="li_140">结束时间</li>
             		<li class="li_80">用时(分)</li>
				</ul>
				<c:if test="${testDriveInfoVOList != null}" >
				<c:forEach var="companyCarStyleVO" items="${testDriveInfoVOList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'style="width:1690px;">
				
            		<li class="li_100"><span title="${companyCarStyleVO.isKeyCity_Nick}">${companyCarStyleVO.isKeyCity_Nick}</span></li>
            		<li class="li_70"><span title="${companyCarStyleVO.dealerType_Nick}">${companyCarStyleVO.dealerType_Nick}</span></li>
            		<li class="li_70"><span title="${companyCarStyleVO.dealerCode}">${companyCarStyleVO.dealerCode}</span></li>
            		<li class="li_120"><span title="${companyCarStyleVO.fenxiao_center_nick}">${companyCarStyleVO.fenxiao_center_nick}</span></li>
            		<li class="li_50"><span title="${companyCarStyleVO.regionProvinceId_Nick}">${companyCarStyleVO.regionProvinceId_Nick}</span></li>
            		<li class="li_50"><span title="${companyCarStyleVO.regionCityId_Nick}">${companyCarStyleVO.regionCityId_Nick}</span></li>
            		<li class="li_240"><span title="${companyCarStyleVO.name}">${companyCarStyleVO.name}</span></li>

            		<li class="li_80"><span title="${companyCarStyleVO.plateNumber}">${companyCarStyleVO.plateNumber}</span></li>
            		<li class="li_120"><span title="${companyCarStyleVO.carStyleId_Nick}">${companyCarStyleVO.carStyleId_Nick}</span></li>
            		<li class="li_130"><span title="${companyCarStyleVO.vin}">${companyCarStyleVO.vin}</span></li>
            		<li class="li_80"><span title="${companyCarStyleVO.lineId_Nick}">${companyCarStyleVO.lineId_Nick}</span></li>
            		<li class="li_50"><span title="${companyCarStyleVO.loopCount}">${companyCarStyleVO.loopCount}</span></li>
            		<li class="li_70"><span title="${companyCarStyleVO.MILEAGE}">${companyCarStyleVO.MILEAGE}</span></li>
            		<li class="li_140"><span title="<fmt:formatDate value="${companyCarStyleVO.start_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${companyCarStyleVO.start_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="li_140"><span title="<fmt:formatDate value="${companyCarStyleVO.end_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${companyCarStyleVO.end_Time}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="li_80"><span title="${companyCarStyleVO.costTime}">${companyCarStyleVO.costTime}</span></li>
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