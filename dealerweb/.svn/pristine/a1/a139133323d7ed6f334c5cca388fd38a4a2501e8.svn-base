<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<% User leftMenuCurrentUser = SessionManager.getCurrentUser(request); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<body>
<script>

var pageFormObj = document.getElementById("myPageForm");
function gotoLoadExcel(){
	pageFormObj.action="<%=path%>/dealer/violation/exportNoTestDetail.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/violation/listNoTestDetail.vti?startIndex=${transMsg.startIndex}";
}

function comfirmDelete(targetUrl){
	var pageFormObj = document.getElementById("myPageForm");
	if (confirm("确定要删除此记录吗？")) {
	  	 targetUrl += "&random=" + Math.random();
		 $.ajax({
		 url: targetUrl,
   		  type: 'GET',
		      dataType: 'json',
		      timeout: 10000,
		      error: function(){
		         alert('系统异常');
		      },
		      success: function(responseData){
		      if (responseData.code == 1) {
		    	 pageFormObj.action="<%=path%>/dealer/violation/listNoTestDetail.vti";
		    	 var startIndex = "${transMsg.startIndex}";
		    	 if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	 }
		    	 pageFormObj.submit();
		         
		     } else {
		        jAlert(responseData.message);
		     }
		    }
		 });
	} 
}

</script>
<form id="myPageForm" action="<%=path%>/dealer/violation/listNoTestDetail.vti" method="get" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
			<c:if test="${menuType == 1}" >
				<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
			</c:if>
			<c:if test="${menuType == 0}" >
				<span>当前位置：违规明细  > 非活跃试驾明细 </span>
			</c:if>
	 	</div>
			<div class="audit_nav_c">
			 	 <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
			 	  分销中心
				<form:select path='violationVO.vehicleVO.dealer.parentId' cssClass="select_condition" >
					<form:options items="${orgList}" itemLabel="name" itemValue="id"  htmlEscape="false" />
				</form:select>
				网络代码
				<form:input path="violationVO.vehicleVO.dealer.dealerCode" cssClass="ipt_txt"/>
				VIN
				<form:input path="violationVO.vehicleVO.vin" cssClass="ipt_txt"/>
				 车牌
				<form:input path="violationVO.vehicleVO.plateNumber" cssClass="ipt_txt"/>
				试驾周期(本年)
				第<form:input path="violationVO.week" maxlength="2"
						onkeyup="this.value=this.value.replace(/\D/g,'')" 
						onafterpaste="this.value=this.value.replace(/\D/g,'')" cssClass="ipt_txt"/>周
				<button>查询</button>
			</div>
		
		<aside class="bd_rt_main clearfix">
				<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1690px;">
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
            		<li class="testDriveNum_70">年</li>
	           		<li class="testDriveNum_70">月</li>
	           		<li class="testDriveNum_70">周</li>
	           		<li class="time_150">开始时间</li>
	           		<li class="time_150">结束时间</li>
	           		<% if (leftMenuCurrentUser.getCompanyId() == 1) { %>
            		<li class="tiny_operator_100">操作</li>
            		<% } %>
				</ul>
				<c:if test="${testNoDriveWeekList != null}" >
				<c:forEach var="testNoDriveWeek" items="${testNoDriveWeekList}" varStatus="statusObj">
				<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1690px;">
            		<li class="distributionSaleCenterName_80"><span title="${testNoDriveWeek.saleCenterName}">${testNoDriveWeek.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${testNoDriveWeek.provinceName}">${testNoDriveWeek.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${testNoDriveWeek.cityName}">${testNoDriveWeek.cityName}</span></li>
            		<li class="dealerCode_70"><span title="${testNoDriveWeek.dealerCode}">${testNoDriveWeek.dealerCode}</span></li>
            		<li class="cnName_240"><span title="${testNoDriveWeek.companyName}">${testNoDriveWeek.companyName}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${testNoDriveWeek.isKeyCityNick}">${testNoDriveWeek.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${testNoDriveWeek.dealerTypeNick}">${testNoDriveWeek.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${testNoDriveWeek.carStyleName}">${testNoDriveWeek.carStyleName}</span></li>
            		<li class="vin_140"><span title="${testNoDriveWeek.vin}">${testNoDriveWeek.vin}</span></li>
            		<li class="plateNumber_75"><span title="${testNoDriveWeek.plateNumber}">${testNoDriveWeek.plateNumber}</span></li>
            		<li class="testDriveNum_70"><span title="${testNoDriveWeek.year}">${testNoDriveWeek.year}</span></li>
	           		<li class="testDriveNum_70"><span title="${testNoDriveWeek.month}">${testNoDriveWeek.month}</span></li>
	           		<li class="testDriveNum_70"><span title="${testNoDriveWeek.week}">${testNoDriveWeek.week}</span></li>
	           		<li class="time_150"><span title="<fmt:formatDate value="${testNoDriveWeek.startTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${testNoDriveWeek.startTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
	           		<li class="time_150"><span title="<fmt:formatDate value="${testNoDriveWeek.endTime}" type="both" pattern="yyyy-MM-dd"/>"><fmt:formatDate value="${testNoDriveWeek.endTime}" type="both" pattern="yyyy-MM-dd"/></span></li>
	           		<% if (leftMenuCurrentUser.getCompanyId() == 1) { %>
            		<li class="tiny_operator_100">
			         	<a href="#" onClick="javascript:comfirmDelete('<%=path%>/dealer/violation/deleteTestNoDriveWeek.vti?testNoDriveWeekId=${testNoDriveWeek.id}&startIndex=${transMsg.startIndex}');">删除</a>
            		</li>
            		<% } %>
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