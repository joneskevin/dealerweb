<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>

<%	
	TransMsg transMsg = (TransMsg)request.getAttribute("transMsg");
    User currentUser = SessionManager.getCurrentUser(request);
	//总记录数
	Integer startIndex;
	if (transMsg.getStartIndex() == null) {
		startIndex = new Integer(0);
	} else {
		startIndex = transMsg.getStartIndex();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

function gotoLoadExcel(){
	var pageFormObj = document.getElementById("myPageForm");
	pageFormObj.action="<%=path%>/dealer/declareManager/exportListDeclare.vti";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/declareManager/queryDeclareList.vti";
}
$(document).ready(function() {
	//$("#startTime").datepicker();
	//$("#endTime").datepicker();
	//$('#filingProposal_startTime').datetimepicker({showSecond: false,changeMonth: true,changeYear: true,dateFormat: 'yy-mm-dd',timeFormat: 'HH:mm:ss'});
	//$('#filingProposal_endTime').datetimepicker({showSecond: false,changeMonth: true,changeYear: true,dateFormat: 'yy-mm-dd',timeFormat: 'HH:mm:ss'});
});

function queryDeclare(){
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	if(null!=startTime && ""!=startTime && null!=endTime && ""!=endTime){
		if(!compareCalendar(startTime, endTime)){
			alert("结束日期必须大于开始日期");
			return;
		}
	}
	var pageFormObj = document.getElementById("myPageForm");
	pageFormObj.action="<%=path%>/dealer/declareManager/queryDeclareList.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();	
	//$("#myPageForm").submit();
}

function compareDate(checkStartDate, checkEndDate) {   //比较日前大小
	var arys1= new Array();   
	var arys2= new Array();   
	if(checkStartDate != null && checkEndDate != null) {   
		arys1=checkStartDate.split('-');   
		var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);   
		arys2=checkEndDate.split('-');   
		var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);   
		if(sdate > edate) {
			return false;      
		}  else {
			return true;   
		}
	}   
}

function compareTime(startDate, endDate) {//判断日期，时间大小
	if (startDate.length > 0 && endDate.length > 0) {
		var startDateTemp = startDate.split(" ");
		var endDateTemp = endDate.split(" ");
					
		var arrStartDate = startDateTemp[0].split("-");
		var arrEndDate = endDateTemp[0].split("-");

		var arrStartTime = startDateTemp[1].split(":");
		var arrEndTime = endDateTemp[1].split(":");

		var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]);
		var allEndDate = new Date(arrEndDate[0], arrEndDate[1], arrEndDate[2], arrEndTime[0], arrEndTime[1], arrEndTime[2]);
				
		if (allStartDate.getTime() >= allEndDate.getTime()) {
			return false;
		} else {
			return true;
       }
	} else {
		return false;
	}
}

function compareCalendar(startDate, endDate) {//比较日期，时间大小
	if (startDate.indexOf(" ") != -1 && endDate.indexOf(" ") != -1 ) {
		return compareTime(startDate, endDate);	//包含时间，日期		
	} else {
		return compareDate(startDate, endDate);//不包含时间，只包含日期
	}
}

function cancelDeclare(filingProposalId,declareStatus,declareType){
	var pageFormObj = document.getElementById("myPageForm");
	var actionUrl="<%=path%>/dealer/declareManager/queryDeclareList.vti?startIndex=${transMsg.startIndex}";
	$.ajax({
		url:"<%=path%>/dealer/declareManager/submitCancelDeclare.vti",
		type:"POST",
		dataType:"json",
		data:{filingProposalId:filingProposalId,declareStatus:declareStatus,declareType:declareType},
		success:function(data) {
			if(null==data || null==data.message){
				alert("取消失败");
			}else if("success"==data.message){
				var startIndex = "${transMsg.startIndex}";
		    	if (startIndex != null && startIndex !="") {
		    	 	$("#startIndex").val(startIndex);
		    	}
				pageFormObj.action=actionUrl;
				pageFormObj.submit();
			}else{
				alert("取消出现异常");
			}
		}
	});
}
</script>

<body>
<form id="myPageForm" action="" commandName="vehicle" method="get" >
<input type="hidden" id="declareType" name="declareType" value="${declareType}" />
<input type="hidden" id="declareStatus" name="declareStatus" value="-1" style="width:200px;height:20px"/>
<div class="bd_rt" id="audit">

<!-- 	<div class="bd_rt_nav clearfix"> -->
<%--     	<span>当前位置 > 报备管理  > <c:if test="${1 == declareType}" >外出报备</c:if><c:if test="${2 == declareType}" >维修报备</c:if></span> --%>
<!-- 	</div> -->
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<div ><span>当前位置 ： 报备管理  > <c:if test="${1 == declareType}" >市场活动</c:if><c:if test="${2 == declareType}" >事故维修</c:if></span></div>
		</c:if>
	</div>
	<div class="audit_nav_c">
	    <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a>
	    <a href="javascript:openWindow('<%=path%>/dealer/declareManager/addDeclareNewView.vti?declareType=${declareType}', 750, 450, '添加报备')">添加报备</a>
	    </span></strong>
		 VIN：<form:input path="vehicle.vin" class="ipt_txt" /> 
		 车牌：<form:input path="vehicle.plateNumber"  class="ipt_txt" /> 
		  网络代码：<input id="dealerCode" name="dealerCode" value="${dealerCode}" class="ipt_txt" />
		  报备时间：
		<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		-
		<!--  <input id="endTime" name="endTime" class="ipt_txt"  value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd" />" readonly="readonly" type="text" />-->
		<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
<!-- 		<button  onclick='$("#startIndex").attr("value", "0");'>查询</button> -->
		<input type="button"  class="btn_submit" value="查 询" onclick="queryDeclare()" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="min-width:2025px;">
            		<li class="distributionSaleCenterName_80">分销中心</li>
            		<li class="dealerCode_70">网络代码</li>
            		<li class="cnName_240">经销商名称</li>
            		<li class="isKeyCity_Nick_80">经销商状态</li>
            		<li class="dealerType_Nick_70">网络形态</li>
            		<li class="regionProvinceId_Nick_50">省份</li>
            		<li class="regionCityId_Nick_50">城市</li>
            		<li class="plateNumber_75">车牌</li>
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="vin_140">VIN</li>
            		<li class="time_150">提交时间</li>
            		<li class="time_150">开始日期</li>
            		<li class="time_150">结束日期</li>
            		<li class="destination_70">目的地</li>
            		<li class="status_nick_70">状态</li>
            		<li class="people_70">审核人</li>
            		<li class="long_operator_280">操作</li>
				</ul>
			<c:if test="${filingProposalCVInfoList != null}" >
			<c:forEach var="filingProposalCVInfo" items="${filingProposalCVInfoList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="min-width:2025px">
            	<li class="distributionSaleCenterName_80"><span title="${filingProposalCVInfo.vehicle.dealer.distributionSaleCenterName}">${fn:substring(filingProposalCVInfo.vehicle.dealer.distributionSaleCenterName,0,2)}</span></li>
            	<li class="dealerCode_70"><span >${filingProposalCVInfo.vehicle.dealer.dealerCode}</span></li>
            	<li class="cnName_240"><span title="${filingProposalCVInfo.vehicle.dealer.cnName}">${fn:substring(filingProposalCVInfo.vehicle.dealer.cnName,0,18)}</span></li>
            	<li class="isKeyCity_Nick_80"><span >${filingProposalCVInfo.vehicle.dealer.isKeyCity_Nick}</span></li>
            	<li class="dealerType_Nick_70"><span >${filingProposalCVInfo.vehicle.dealer.dealerType_Nick}</span></li>
            	<li class="regionProvinceId_Nick_50"><span title="${filingProposalCVInfo.vehicle.dealer.regionProvinceId_Nick}">${fn:substring(filingProposalCVInfo.vehicle.dealer.regionProvinceId_Nick,0,2)}</span></li>
            	<li class="regionCityId_Nick_50"><span title="${filingProposalCVInfo.vehicle.dealer.regionCityId_Nick}">${fn:substring(filingProposalCVInfo.vehicle.dealer.regionCityId_Nick,0,2)}</span></li>
            	<li class="plateNumber_75"><span >${filingProposalCVInfo.vehicle.plateNumber}</span></li>
            	<li class="carStyleId_Nick_200"><span title="${filingProposalCVInfo.vehicle.carStyleId_Nick}">${fn:substring(filingProposalCVInfo.vehicle.carStyleId_Nick,0,30)}</span></li>
            	<li class="vin_140"><span >${filingProposalCVInfo.vehicle.vin}</span></li>
            	<li class="time_150"><span title="<fmt:formatDate value="${filingProposalCVInfo.filingProposal.commitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalCVInfo.filingProposal.commitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="time_150"><span title="<fmt:formatDate value="${filingProposalCVInfo.filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalCVInfo.filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="time_150"><span title="<fmt:formatDate value="${filingProposalCVInfo.filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalCVInfo.filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="destination_70"><span title="${filingProposalCVInfo.filingProposal.description}">${fn:substring(filingProposalCVInfo.filingProposal.description,0,10)}</span></li>
            	<li class="status_nick_70"><span >${filingProposalCVInfo.filingProposal.status_nick}</span></li>
            	<li class="people_70"><span title="${filingProposalCVInfo.filingProposal.approvalName}">${filingProposalCVInfo.filingProposal.approvalName}</span></li>
            	<li class="long_operator_280">
		 			<c:if test="${2==filingProposalCVInfo.filingProposal.status}">
		 			<a  onClick="cancelDeclare('<c:out value="${filingProposalCVInfo.filingProposal.id}" />','7','${declareType}');">取消报备</a>
<%-- 		 				<a href="<%=path%>/dealer/declareManager/submitCancelDeclare.vti?filingProposalId=<c:out value="${filingProposalCVInfo.filingProposal.id}" />&declareStatus=7&declareType=${declareType}">取消报备</a> --%>
						<a href="javascript:openWindow('<%=path%>/dealer/declareManager/editDeclareView.vti?filingProposalId=<c:out value="${filingProposalCVInfo.filingProposal.id}" />&declareType=${declareType}', 750, 400, '编辑报备')">编辑报备</a>
						<a href="javascript:openWindow('<%=path%>/dealer/declareManager/declareAttachFileList.vti?filingProposalId=<c:out value="${filingProposalCVInfo.filingProposal.id}" />', 800, 450, '附件管理')">附件管理</a>
					</c:if>
					<a href="javascript:openWindow('<%=path%>/dealer/declareManager/viewDeclare.vti?filingProposalId=<c:out value="${filingProposalCVInfo.filingProposal.id}" />&vehicleId=<c:out value="${filingProposalCVInfo.vehicle.id}" />&declareType=${declareType}', 750, 400, '查看报备详情')">查看报备详情</a>
<%-- 				<a href="javascript:openWindow('<%=path%>/dealer/declareManager/viewDeclareHis.vti?vehicleId=<c:out value="${filingProposalCVInfo.vehicle.id}" />&declareType=${declareType}', 800, 450, '查看报备历史')">查看报备历史</a> --%>
            	</li>
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	</aside>
	<div class="bd_rt_ft">
			<jsp:include page="/pub/navigate/navigation4dealer.jsp" flush="true"/>
	</div>
</div><!--/.bd_rt-->
</form>
</body>
</html>