<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<script>	
	function queryPlateNumberData(){ //柱状图初始化的值 
		var plateNumber = $("#plateNumber").attr("value");
		var declareType=$("#filingProposal_declareType").attr("value");
		$("#selectedCarStyleId").val($("#carStyleId").attr("value"));
		$.ajax({
				url:"<%=request.getContextPath()%>/dealer/declareManager/findVehiclePlateNumber.vti",
				type:"POST",
				dataType:"json",
				//timeout: 10000,
				data:{plateNumber:plateNumber,declareType:declareType},
				success:function(responseData) {
					  if(null==responseData){
						  alert('没有找到车辆信息');
					  }else{
					      if (responseData.code == 1) {
						         if (responseData.data != null && responseData.data.vehicle != null) {
							         if (responseData.data.vehicle != null) {
						        		 var vehicle = responseData.data.vehicle;
						        		 //$("#carStyleId_Nick").val(vehicle.carStyleId_Nick);
						        		 $("#vin").val(vehicle.vin);
						        		 $("#vehicleId").val(vehicle.id);
							    	  }
							         if(null!=responseData.data.simMobile){
							        	 $("#simMobile").val(responseData.data.simMobile);
							         }
						    	  }
						     }else {
									jAlert(responseData.message);
							}
					  }
				},
				error: function(){
			         alert('服务器发生异常');
			    },
		});
	}
	function queryCarStyleData(){
		var carStyleId = $("#carStyleId").attr("value");
		$("#selectedCarStyleId").val(carStyleId);
		$.ajax({
				url:"<%=request.getContextPath()%>/dealer/declareManager/findVehicleByCarStyleId.vti",
				type:"POST",
				dataType:"json",
				//timeout: 10000,
				data:{carStyleId:carStyleId},
				success:function(responseData) {
					setData(responseData);
				},
				error: function(){
			         alert('服务器发生异常');
			    },
		});
	}
	function save(){
		var vin=$('#vin').val();
		var startTime=$('#filingProposal_startTime').val();
		var endTime=$('#filingProposal_endTime').val();
		var description=$('#filingProposal_description').val();
		var reason=$('#filingProposal_reason').val();
		var declareFile=$('#declareFile').val();
		if(null==vin || ""==vin){
			alert("没有选择车辆不能报备");
			return;
		}
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
		}else{
			$("#myPageForm").submit();
		}
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
	function setData(data){
		if(null==data){
			  alert('没有找到车辆信息');
		  }else{
		      if (data.code == 1) {
			         if (data.data != null && data.data.vehicle != null) {
				         if (data.data.vehicle != null) {
			        		 var vehicle = data.data.vehicle;
			        		 $("#vin").val(vehicle.vin);
			        		 $("#vehicleId").val(vehicle.id);
				    	  }
				         if(null!=data.data.simMobile){
				        	 $("#simMobile").val(data.data.simMobile);
				         }
				         bindPlateNumber(data.data.plateNumberList,data.data.selectedPlateNumber);
			    	  }
			     }else {
	        		 $("#vin").val("");
	        		 $("#plateNumber").empty();
					jAlert(data.message);
				}
		  }
	}
	function compareCalendar(startDate, endDate) {//比较日期，时间大小
		if (startDate.indexOf(" ") != -1 && endDate.indexOf(" ") != -1 ) {
			return compareTime(startDate, endDate);	//包含时间，日期		
		} else {
			return compareDate(startDate, endDate);//不包含时间，只包含日期
		}
	}
	function bindPlateNumber(data,selectedPlateNumber){
		$("#plateNumber").empty();
		//var plateNumbers = eval('(' + data + ')');
		var plateNumbers = data;
		for (var index in plateNumbers) {
			var optionstring = "<option value=\"" + plateNumbers[index] + "\" >" + plateNumbers[index] + "</option>";
			$("#plateNumber").append(optionstring);   //为Select追加一个Option(下拉项)          
		}
	}
</script>
<body class="body_pop">

<div class="pop-tableBox">
<form id="myPageForm" action="<%=path%>/dealer/declareManager/addDeclare.vti" method="post" enctype="multipart/form-data">
	<input type="hidden" id="filingProposal_declareType" name="filingProposal.type" value="${filingProposal.type}" />
	<input type="hidden" id="vehicleId" name="vehicleId" value="${vehicle.id}" />
	<input type="hidden" id="selectedCarStyleId" name="selectedCarStyleId" value="${selectedCarStyleId}" />
<table>
	<tr>
    	<td width="15%" align="right">经销商状态：</td>
        <td width="35%">${dealer.isKeyCity_Nick}</td>
        <td width="15%" align="right">网络形态：</td>
        <td>${dealer.dealerType_Nick}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">网络代码：</td>
        <td width="35%">${dealer.dealerCode}</td>
        <td width="15%" align="right">分销中心：</td>
        <td>${dealer.distributionSaleCenterName}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">省份：</td>
        <td width="35%">${dealer.regionProvinceId_Nick}</td>
        <td width="15%" align="right">城市：</td>
        <td>${dealer.regionCityId_Nick}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">经销商名称：</td>
        <td colspan="3">${dealer.cnName}</td>
    </tr>
    <tr>
        <td width="15%" align="right">车型：</td>
        <td>
        <select id="carStyleId" name="carStyleId" style="border:1px solid #666;" onchange="queryCarStyleData()">
				<c:forEach items="${carStyles}" var="carStyle">
					<c:choose>
						<c:when test="${selectedCarStyleId==carStyle.id}">
							<option value="${carStyle.id}" selected>${carStyle.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${carStyle.id}">${carStyle.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
		</select>
    	<td width="15%" align="right">车牌：</td>
        <td width="35%">
	   	   <select id="plateNumber" name="plateNumber" style="border:1px solid #666;" onchange="queryPlateNumberData()">
				<c:forEach items="${plateNumberList}" var="plateNum">
					<c:choose>
						<c:when test="${selectedPlateNumber==plateNum}">
							<option value="${plateNum}" selected>${plateNum}</option>
						</c:when>
						<c:otherwise>
							<option value="${plateNum}">${plateNum}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
	   	</td>
    </tr>
    <tr>
    	<td width="15%" align="right">VIN码：</td>
        <td width="35%"><input type="text" id="vin" name="vin" value="${vehicle.vin}" readonly="true"/></td>
        <td width="15%" align="right">通讯号：</td>
        <td><input type="text" id="simMobile" name="simMobile" value="${simMobile}" readonly="true"/></td>
    </tr>
    <tr>
    	<td width="15%" align="right"><span>*</span>上传附件：</td>
        <td colspan="3">
        	  <input type="file" id="declareFile" name="declareFile"  style="border:1px double rgb(88,88,88);font:12px; width:260px; height:20px;"  />
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right"><span>*</span>开始日期：</td>
        <td width="35%">
        	<input type="text" id="filingProposal_startTime" name="filingProposal.startTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
		</td>
        <td width="15%" align="right"><span>*</span>结束日期：</td>
        <td>        
        	<input type="text" id="filingProposal_endTime" name="filingProposal.endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true})" value="<fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" class="Wdate right" readonly="true"/>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right"><span>*</span>目的地：</td>
        <td colspan="3">
        	<input id="filingProposal_description" name="filingProposal.description" class="pop-text-long" value="${filingProposal.description}" type="text"/>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right">报备原因：</td>
        <td colspan="3"><textarea id="filingProposal_reason" name="filingProposal.reason" rows = "3" >${filingProposal.reason}</textarea></td>
    </tr>
</table>
	<input type="button" onclick="save()" value="提交"  class="input_button"/>
	<!-- <button onclick="save()">提交</button> -->
</form>
</div>
</body>
</html>