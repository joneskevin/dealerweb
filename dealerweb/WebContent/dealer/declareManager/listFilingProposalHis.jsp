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
	pageFormObj.action="<%=path%>/dealer/filingProposalManager/exportFilingProposalHis.vti";
    pageFormObj.submit();
    pageFormObj.action="<%=path%>/dealer/filingProposalManager/listFilingProposalHis.vti";
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
	pageFormObj.action="<%=path%>/dealer/filingProposalManager/listFilingProposalHis.vti?startIndex=${transMsg.startIndex}";
    pageFormObj.submit();	
	//$("#myPageForm").submit();
}

function compareDate(checkStartDate, checkEndDate) {//比较日前大小
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
<form id="myPageForm" action="" method="get" >
<div class="bd_rt" id="audit">
	<div class="bd_rt_nav clearfix">
		<c:if test="${menuType == 1}" >
			<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
		</c:if>
		<c:if test="${menuType == 0}" >
			<div ><span>当前位置 ： 报备管理  > 历史报备</span></div>
		</c:if>
	</div>
	<div class="audit_nav_c">
	    <strong><span><a href="#" onClick="javascript:gotoLoadExcel();">表格导出</a></span></strong>
	    		 分销中心
		<select id="selectSaleCenterId" name="selectSaleCenterId" style="border:1px solid #666;">
			<c:forEach items="${orgList}" var="org" varStatus="num">
					<c:choose>
						<c:when test="${org.id==selectedSaleCenterId}" >
						    <option value="${org.id}" selected>${org.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${org.id}">${org.name}</option>
						</c:otherwise>
  					</c:choose>
			</c:forEach>
		</select>
		网络代码
		<input id="dealerCode" name="dealerCode" value="${dealerCode}" class="ipt_txt" />
		  报备开始时间
		<input type="text" id="startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		-
		<input type="text" id="endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		<input type="button"  class="btn_submit" value="查 询" onclick="queryDeclare()" />
	</div>
	<aside class="bd_rt_main clearfix">
		<div class="table_list table_all" style="min-width:1200px;overflow-y:hidden;overflow:auto">
				<ul class="table_hd clearfix" style="min-width:1780px;">
            		<li class="distributionSaleCenterName_80">分销中心</li>
            		<li class="regionProvinceId_Nick_50">省份</li>
            		<li class="regionCityId_Nick_50">城市</li>
            		<li class="cnName_240">经销商名称</li>
            		<li class="dealerCode_70">网络代码</li>
            		<li class="isKeyCity_Nick_80">经销商状态</li>
            		<li class="dealerType_Nick_70">网络形态</li>
            		<li class="carStyleId_Nick_200">车型</li>
            		<li class="plateNumber_75">车牌</li>
            		<li class="time_150">申请时间</li>
            		<li class="time_150">开始时间</li>
            		<li class="time_150">结束时间</li>
            		<li class="isKeyCity_Nick_80">报备类型</li>
            		<li class="isKeyCity_Nick_80">报备状态</li>
            		<li class="people_70">审核人</li>
            		<li class="time_150">审核时间</li>
				</ul>
			<c:if test="${filingProposalHisList != null}" >
			<c:forEach var="filingProposalHis" items="${filingProposalHisList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' style="min-width:1780px">
            		<li class="distributionSaleCenterName_80"><span title="${filingProposalHis.saleCenterName}">${filingProposalHis.saleCenterName}</span></li>
            		<li class="regionProvinceId_Nick_50"><span title="${filingProposalHis.provinceName}">${filingProposalHis.provinceName}</span></li>
            		<li class="regionCityId_Nick_50"><span title="${filingProposalHis.cityName}">${filingProposalHis.cityName}</span></li>
            		<li class="cnName_240"><span title="${filingProposalHis.companyName}">${filingProposalHis.companyName}</span></li>
            		<li class="dealerCode_70"><span title="${filingProposalHis.dealerCode}">${filingProposalHis.dealerCode}</span></li>
            		<li class="isKeyCity_Nick_80"><span title="${filingProposalHis.isKeyCityNick}">${filingProposalHis.isKeyCityNick}</span></li>
            		<li class="dealerType_Nick_70"><span title="${filingProposalHis.dealerTypeNick}">${filingProposalHis.dealerTypeNick}</span></li>
            		<li class="carStyleId_Nick_200"><span title="${filingProposalHis.vehicleStyle}">${filingProposalHis.vehicleStyle}</span></li>
            		<li class="plateNumber_75"><span title="${filingProposalHis.plateNumber}">${filingProposalHis.plateNumber}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${filingProposalHis.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalHis.proposalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            		<c:if test="${filingProposalHis.type==1}" >
						<li class="time_150"><span title="<fmt:formatDate value="${filingProposalHis.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalHis.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
						<li class="time_150"><span title="<fmt:formatDate value="${filingProposalHis.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalHis.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
					</c:if>
					<c:if test="${filingProposalHis.type==2}" >
						<li class="time_150"><span title="<fmt:formatDate value="${filingProposalHis.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalHis.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
						<li class="time_150"><span title="<fmt:formatDate value="${filingProposalHis.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalHis.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>					
					</c:if>
            		<li class="isKeyCity_Nick_80"><span>${filingProposalHis.filingType}</span></li>
            		<li class="isKeyCity_Nick_80"><span>${filingProposalHis.filingStatus}</span></li>
            		<li class="people_70"><span>${filingProposalHis.approvalName}</span></li>
            		<li class="time_150"><span title="<fmt:formatDate value="${filingProposalHis.approvalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>"><fmt:formatDate value="${filingProposalHis.approvalTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
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