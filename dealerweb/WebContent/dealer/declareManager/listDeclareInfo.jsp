<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.base.dao.hibernate.TransMsg" %>
<%	
	TransMsg transMsg = (TransMsg)request.getAttribute("transMsg");
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
$(document).ready(function() {
	//$("#startTime").datepicker();
	//$("#endTime").datepicker();
});
function gotoLoadExcel(){
	var pageFormObj = document.getElementById("myPageForm");
	pageFormObj.action="<%=path%>/dealer/declareManager/exportViewDeclareHis.vti";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/declareManager/queryViewDeclareHis.vti";
}

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
	pageFormObj.action="<%=path%>/dealer/declareManager/queryViewDeclareHis.vti?startIndex=${transMsg.startIndex}";
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

</script>
<body>

<form id="myPageForm" action="" commandName="vehicle" method="get" >
<input type="hidden" id="declareType" name="declareType" value="${declareType}" />
<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicleId}" />

<div class="bd_rt" id="audit">	
<!-- 	<div class="bd_rt_nav clearfix"> -->
<!-- 	    <div >当前位置：报备管理  > 报备审批</div> -->
<!-- 	</div> -->
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<div ><span>当前位置：报备管理  > 报备审批</span></div>
		</c:if>
	</div>
	<div class="audit_nav_c">
		   <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
		    报备时间：
		 <input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		 <!--<input id="startTime" name="startTime" class="ipt_txt"  value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd" />" readonly="readonly" type="text" />-->
		 - 
		 <!--  <input id="endTime" name="endTime" class="ipt_txt"  value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd" />" readonly="readonly" type="text" />-->
		 <input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
<!-- 		<button  onclick='$("#startIndex").attr("value", "0");'/>查询</button> -->
		<input type="button"  class="btn_submit" value="查 询" onclick="queryDeclare()" />
	</div>	
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="width:1200px">
            		<li class="li_150">申请人</li>
            		<li class="li_150">申请时间</li>
            		<li class="li_150">开始日期</li>
            		<li class="li_150">结束日期</li>
            		<li class="li_150">报备类型</li>
            		<li class="li_150">报备状态</li>
            		<li class="li_280">操作</li>
				</ul>
			<c:if test="${filingProposalList != null}" >
			<c:forEach var="filingProposal" items="${filingProposalList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="width:1200px">
            	<li class="li_150"><span>${filingProposal.proposerName}</span></li>
            	<li class="li_150"><span title="<fmt:formatDate value="${filingProposal.commitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposal.commitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_150"><span title="<fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_150"><span title="<fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_150"><span>${filingProposal.type_nick}</span></li>
            	<li class="li_150"><span >${filingProposal.status_nick}</span></li>
            	<li class="li_280">
            		<c:if test="${2==filingProposal.status}">
            			<a href="javascript:openWindow('<%=path%>/dealer/declareManager/declareApprovalView.vti?filingProposalId=<c:out value="${filingProposal.id}" />&vehicleId=<c:out value="${filingProposal.vehicleId}" />&declareType=4', 800, 450, '审核')">审核</a>
					</c:if>
					<c:if test="${3==filingProposal.status || 4==filingProposal.status}">
            			<a href="javascript:openWindow('<%=path%>/dealer/declareManager/viewDeclare.vti?filingProposalId=<c:out value="${filingProposal.id}" />&vehicleId=<c:out value="${filingProposal.vehicleId}" />&declareType=${declareType}', 750, 400, '查看报备详情')">查看报备详情</a>
					</c:if>
					<a href="javascript:openWindow('<%=path%>/dealer/declareManager/declareAttachFileDownList.vti?filingProposalId=<c:out value="${filingProposal.id}" />', 700, 400, '查看附件')">查看附件</a>
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