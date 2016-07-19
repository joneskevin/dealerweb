<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<script>

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/violation/output.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/list.vti?startIndex=${transMsg.startIndex}";
}
</script>
<form id="myPageForm" action="<%=path%>/dealer/violation/list.vti" method="get" >
	<div class="bd_rt" id="audit">
		<!-- <div class="bd_rt_nav clearfix">
			<span>主界面 >违规明细</span>
			</div> -->
			
		<div class="bd_rt_nav clearfix">
			<c:if test="${menuType == 1}" >
				<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
			</c:if>
			<c:if test="${menuType == 0}" >
				<span>当前位置：违规明细  > 违规明细 </span>
			</c:if>
	 	</div>
			<div class="audit_nav_c">
			 	 <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
				VIN：
				<form:input path="violation.vin" cssClass="ipt_txt"/>
				 车牌：
				<form:input path="violation.plateNumber" cssClass="ipt_txt"/>
				网络代码：
				<form:input path="violation.dealerCode" cssClass="ipt_txt"/>
				违规时间：
				<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${violation.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
				-
				<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" 	value="<fmt:formatDate value="${violation.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
				
				 <button>查询</button>
			</div>
		
		<aside class="bd_rt_main clearfix">
				<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1480px;">
            		<li class="isKeyCity_Nick_80">经销商状态</li>
            		<li class="dealerType_Nick_70">网络形态</li>
            		<li class="dealerCode_70">网络代码</li>
            		<li class="distributionSaleCenterName_80">分销中心</li>
            		<li class="regionProvinceId_Nick_50">省份</li>
            		<li class="regionCityId_Nick_50">城市</li>
            		<li class="cnName_240">经销商名称</li>
             		<li class="plateNumber_75">车牌</li>
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="vin_140">VIN</li> 
            		<li class="typeId_nick_70">违规类型</li>
            		<li class="time_150">违规时间</li>
            		<li class="testDriveNum_70">违规次数</li>
            		<li class="tiny_operator_100">操作</li>
            		<!-- <li style="border:0;padding-left:15px;">操作</li> -->
				</ul>
				<c:if test="${violationList != null}" >
				<c:forEach var="violation" items="${violationList}" varStatus="statusObj">
					<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1480px">
            		<li class="isKeyCity_Nick_80"><span title="${violation.isKeyCity_Nick}">${violation.isKeyCity_Nick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${violation.dealerType_Nick}">${violation.dealerType_Nick}</span></li>
            		<li class="dealerCode_70"><span title="${violation.dealerCode}">${violation.dealerCode}</span></li>
            		<li class="distributionSaleCenterName_80"><span title="${violation.fenxiao_center}">${violation.fenxiao_center}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${violation.regionProvinceId_Nick}">${violation.regionProvinceId_Nick}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${violation.regionCityId_Nick}">${violation.regionCityId_Nick}</span></li>
            		<li class="cnName_240"><span title="${violation.companyName}">${violation.companyName}</span></li>
            		<li class="plateNumber_75"><span title="${violation.plateNumber}">${violation.plateNumber}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${violation.carStyleId_Nick}">${violation.carStyleId_Nick}</span></li>
            		<li class="vin_140"><span title="${violation.vin}">${violation.vin}</span></li>
            		<li class="typeId_nick_70"><span title="${violation.typeId_nick}">${violation.typeId_nick}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${violation.creationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${violation.creationTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<li class="testDriveNum_70"><span title="${violation.countId}">${violation.countId}</span></li>
            		<%-- <li style="border:0;padding-left:15px;">
			         	<a href="<%=path%>/dealer/track/home.vti?plateNumber=${violation.plateNumber}&startTime=${violation.start_Time}&endTime=${violation.end_Time}">查看轨迹</a>
            		</li> --%>
            		<li class="tiny_operator_100">
			         	<a href="<%=path%>/dealer/track/home.vti?plateNumber=${violation.plateNumber}&startTime=${violation.start_Time}&endTime=${violation.end_Time}">查看轨迹</a>
            		</li>
				</ul>
				</c:forEach>
				</c:if>
			</div><!--/table_list-->
		</aside>
		<div class="bd_rt_ft">
				<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
		</div>
		</div>
</form>

</body>
</html>