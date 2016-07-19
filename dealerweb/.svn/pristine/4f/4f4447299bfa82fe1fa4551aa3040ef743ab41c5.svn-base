<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.entity.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
	function save(){
		var startTime=$('#filingProposal_startTime').val();
		var endTime=$('#filingProposal_endTime').val();
		var description=$('#filingProposal_description').val();
		var reason=$('#filingProposal_reason').val();
		var declareFile=$('#declareFile').val();
		
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
		if(null==declareFile || ""==declareFile){
			alert("请上传附件");
			return ;
		}
		var selectedVehicles=getVehicleId();
		if(null==selectedVehicles || ""==selectedVehicles){
			alert("没有选择车辆不能添加事故维修");
			return;
		}else{
			$("#selectedVehicles").val(selectedVehicles);
			$("#myPageForm").submit();
		}
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
	function getVehicleId(){
		var vehicleIdsChenked=$("input[type='checkbox']:checked").val([]);
		var vehicleIds = "";
		for(var i=0;i<vehicleIdsChenked.length;i++){
			vehicleIds += vehicleIdsChenked[i].value +",";
		}
		if(vehicleIds.length>=2){
			vehicleIds=vehicleIds.substring(0, vehicleIds.lastIndexOf(","));
		}
		return vehicleIds;
	}
</script>
<body class="body_pop">

<div class="pop-tableBox">
<form id="myPageForm" action="<%=path%>/dealer/filingProposalManager/addFilingProposal.vti" method="post" enctype="multipart/form-data">
	<input type="hidden" id="selectedVehicles" name="selectedVehicles" value="" />
	<input type="hidden" id="filingProposalType" name="filingProposalType" value="2" />
<table>
	<tr>
    	<td width="100px" align="right">经销商状态：</td>
        <td width="100px">${dealer.isKeyCity_Nick}</td>
        <td width="100px" align="right">网络形态：</td>
        <td width="100px">${dealer.dealerType_Nick}</td>
        <td width="100px" align="right">网络代码：</td>
        <td width="100px">${dealer.dealerCode}</td>
    </tr>
    <tr>
        <td width="100px" align="right">分销中心：</td>
        <td width="100px">${dealer.distributionSaleCenterName}</td>
        <td width="100px" align="right">省份：</td>
        <td width="100px">${dealer.regionProvinceId_Nick}</td>
        <td width="100px" align="right">城市：</td>
        <td width="100px">${dealer.regionCityId_Nick}</td>
    </tr>
    <tr>
    	<td width="100px" align="right">经销商名称：</td>
        <td width="500px" colspan="5">${dealer.cnName}</td>
    </tr>
    <tr>
    	<td width="100px" align="right" valign="top">报备车辆：</td>
        <td width="500px" colspan="5">

	        <c:if test="${vehicleStyles != null}" >
	           <table style="width:500px; margin:0px 0;font-family:"微软雅黑";">
				<c:forEach var="vehicleStyle" items="${vehicleStyles}" varStatus="statusObj">
						<c:choose>
						    <c:when test="${(statusObj.index +1) %2==0}" >
						    	<td>
								<c:if test="${vehicleStyle.checked=='1'}" >
						    		<input type="checkbox" id="${vehicleStyle.vehicleId}" name="vehicleIds" value="${vehicleStyle.vehicleId}" checked="checked"  > ${vehicleStyle.vehicleInfo}</input>
						    	</c:if>
								<c:if test="${vehicleStyle.checked=='0'}" >
						    		<input type="checkbox" id="${vehicleStyle.vehicleId}" name="vehicleIds" value="${vehicleStyle.vehicleId}"> ${vehicleStyle.vehicleInfo}</input>
						    	</c:if>
								</td>
								</tr>
							</c:when>
							<c:when test="${statusObj.last && (statusObj.index +1) %2!=0}" >
						    	<td>
								<c:if test="${vehicleStyle.checked=='1'}" >
						    		<input type="checkbox" id="${vehicleStyle.vehicleId}" name="vehicleIds" value="${vehicleStyle.vehicleId}" checked="checked"  > ${vehicleStyle.vehicleInfo}</input>
						    	</c:if>
								<c:if test="${vehicleStyle.checked=='0'}" >
						    		<input type="checkbox" id="${vehicleStyle.vehicleId}" name="vehicleIds" value="${vehicleStyle.vehicleId}"> ${vehicleStyle.vehicleInfo}</input>
						    	</c:if>
								</td>
								<td></td>
								</tr>
							</c:when>
							<c:otherwise>
							    <tr><td>
	      						<c:if test="${vehicleStyle.checked=='1'}" >
						    		<input type="checkbox" id="${vehicleStyle.vehicleId}" name="vehicleIds" value="${vehicleStyle.vehicleId}" checked="checked"  > ${vehicleStyle.vehicleInfo}</input>
						    	</c:if>
								<c:if test="${vehicleStyle.checked=='0'}" >
						    		<input type="checkbox" id="${vehicleStyle.vehicleId}" name="vehicleIds" value="${vehicleStyle.vehicleId}"> ${vehicleStyle.vehicleInfo}</input>
						    	</c:if>
	      						</td>
	   						</c:otherwise>
  						</c:choose>
				</c:forEach>
					</table>
			</c:if>
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right" valign="top"><span>*</span>上传附件：</td>
        <td width="500px" colspan="5">
        	  <input type="file" id="declareFile" name="declareFile"  style="border:1px double rgb(88,88,88);font:12px; width:260px; height:20px;"  multiple="multiple" />
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right"><span>*</span>开始日期：</td>
        <td width="200px" colspan="2">
        	<input type="text" id="filingProposal_startTime" name="startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		</td>
        <td width="100px" align="right"><span>*</span>结束日期：</td>
        <td width="200px" colspan="2">
        	<input type="text" id="filingProposal_endTime" name="endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right"><span>*</span>目的地：</td>
        <td width="500px" colspan="5">
        	<input id="filingProposal_description" name="description" class="pop-text-long" value="${filingProposal.description}" type="text"/>
        </td>
    </tr>
    <tr>
    	<td width="100px" align="right">报备原因：</td>
        <td width="500px" colspan="5"><textarea id="filingProposal_reason" name="reason" rows = "3" >${filingProposal.reason}</textarea></td>
    </tr>
</table>
	<input type="button" onclick="save()" value="提交"  class="input_button"/>
</form>
</div>
</body>
</html>