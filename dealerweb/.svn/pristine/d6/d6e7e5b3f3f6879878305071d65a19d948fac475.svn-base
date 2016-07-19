<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<script>
$(document).ready(function() {
	//$('#filingProposal_startTime').datetimepicker({showSecond: false,changeMonth: true,changeYear: true,dateFormat: 'yy-mm-dd',timeFormat: 'HH:mm:ss'});
	//$('#filingProposal_endTime').datetimepicker({showSecond: false,changeMonth: true,changeYear: true,dateFormat: 'yy-mm-dd',timeFormat: 'HH:mm:ss'});
});

function save(){
	var startTime=$('#filingProposal_startTime').val();
	var endTime=$('#filingProposal_endTime').val();
	var description=$('#filingProposal_description').val();
	var reason=$('#filingProposal_reason').val();
	
	if(null==startTime || ""==startTime || null==endTime || ""==endTime){
		alert("开始日期和结束日期都不能为空");
		return;
	}

	if(null==description || description.length<=0 || description.length>100){
		alert("目的地不能为空且长度不能超过100");
		return;
	}
	if(""!=reason && reason.length>300){
		alert("原因长度不能超过300");
		return;
	}
	if(!compareCalendar(startTime, endTime)){
		alert("结束日期必须大于开始日期");
		return;
	}
			
	$("#myPageForm").submit();
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

<body class="body_pop">
<div class="pop-tableBox">
<form id="myPageForm" action="<%=path%>/dealer/declareManager/editDeclare.vti" method="post">
	<input type="hidden" id="filingProposal.id" name="filingProposal.id" value="${filingProposalCV.filingProposal.id}" />
	<input type="hidden" id="filingProposal.type" name="filingProposal.type" value="${declareType}" />
	<input type="hidden" id="filingProposal.companyId" name="filingProposal.companyId" value="${filingProposalCV.filingProposal.companyId}" />
<table>
	<tr>
    	<td width="15%" align="right">经销商状态：</td>
        <td width="35%">${filingProposalCV.vehicle.dealer.isKeyCity_Nick}</td>
        <td width="15%" align="right">网络形态：</td>
        <td>${filingProposalCV.vehicle.dealer.dealerType_Nick}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">网络代码：</td>
        <td width="35%">${filingProposalCV.vehicle.dealer.dealerCode}</td>
        <td width="15%" align="right">分销中心：</td>
        <td>${filingProposalCV.vehicle.dealer.distributionSaleCenterName}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">省份：</td>
        <td width="35%">${filingProposalCV.vehicle.dealer.regionProvinceId_Nick}</td>
        <td width="15%" align="right">城市：</td>
        <td>${filingProposalCV.vehicle.dealer.regionCityId_Nick}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">经销商名称：</td>
        <td colspan="3">${filingProposalCV.vehicle.dealer.cnName}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">车牌：</td>
        <td width="35%">${filingProposalCV.vehicle.plateNumber}</td>
        <td width="15%" align="right">车型：</td>
        <td>${filingProposalCV.vehicle.carStyleId_Nick}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">VIN码：</td>
        <td width="35%">${filingProposalCV.vehicle.vin}</td>
        <td width="15%" align="right">通讯号：</td>
        <td>${filingProposalCV.vehicle.box.simMobile}</td>
    </tr>
    <tr>
    	<td width="15%" align="right"><span>*</span>开始日期：</td>
        <td width="35%">
        	<input type="text" id="filingProposal_startTime" name="filingProposal.startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${filingProposalCV.filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
			
			</td>
        <td width="15%" align="right"><span>*</span>结束日期：</td>
        <td>
        <input type="text" id="filingProposal_endTime" name="filingProposal.endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${filingProposalCV.filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right"><span>*</span>目的地：</td>
        <td colspan="3">
        	<input id="filingProposal_description" name="filingProposal.description" class="pop-text-long" value="${filingProposalCV.filingProposal.description}" type="text"/>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right">报备原因：</td>
        <td colspan="3">
        	<textarea id="filingProposal_reason" name="filingProposal.reason" cols ="100" rows = "3">${filingProposalCV.filingProposal.reason}</textarea>
        </td>
    </tr>
</table>
<!-- <button onclick="save()">提交</button> -->
<input type="button" onclick="save()" value="提交"  class="input_button"/>
</form>
</div>
</body>
</html>