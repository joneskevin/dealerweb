<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>

$(document).ready(function() {
	demolitionTypeClick();
	vinBlurEvent();
	
	 $("#vin").blur(function() {
		 vinBlurEvent();
     });
	 
});

function vinBlurEvent(){
    var vin = $("#vin").attr("value");
    var targetUrl = "<%=request.getContextPath()%>/dealer/vehicle/findVehicleInfo.vti?vin=" + vin;
    targetUrl += "&random=" + Math.random();
    $.ajax({
        url: targetUrl,
        type: 'GET',
        dataType: 'json',
        timeout: 10000,
        error: function() {
            alert('系统异常');
        },
        success: function(responseData) {
            if (responseData.code == 1) {
                if (responseData.data != null && responseData.data.vehicle != null) {
                    var vehicle = responseData.data.vehicle;
                    if (vehicle != null) {
                   	 var vehicleId = vehicle.id;
                        if (vehicleId > 0) {
                            $("#vehicleId").val(vehicleId);
                        }
                        var plateNumber = vehicle.plateNumber;
                        if (plateNumber.length > 0) {
                            $("#plateNumber").text(plateNumber);
                        }
                        
                        var carStyleId_Nick = vehicle.carStyleId_Nick;
                        if (carStyleId_Nick.length > 0) {
                       	 $("#carStyleId_Nick").text(carStyleId_Nick);
                        }
                    }
                    var dealer = responseData.data.vehicle.dealer;
                    if (dealer != null) {
                   	 var dealerCode = dealer.dealerCode;
                        if (dealerCode.length > 0) {
                            $("#dealerCode").text(dealerCode);
                        }
                   	 var cnName = dealer.cnName;
                        if (cnName.length > 0) {
                            $("#companyName").text(cnName);
                        }
                    }
                }

            } else {
                jAlert(responseData.message);
            }
        }
    });
}

function demolitionTypeClick() { 
  var demolitionType= document.getElementsByName("demolitionType"); 
  for (i = 0; i < demolitionType.length; i ++) { 
    if (demolitionType[i].checked) {
    	if (demolitionType[i].value == 0) {
    		$("#demolitionByCarStyle").css("display", "block");
            $("#demolitionByVehicle").css("display", "none");
    	} else {
    	    $("#demolitionByCarStyle").css("display", "none");
            $("#demolitionByVehicle").css("display", "block");
    	}
    	
      } 
   } 
} 

function save() {
	 var demolitionType= document.getElementsByName("demolitionType"); 
	  for (i = 0; i < demolitionType.length; i ++) { 
	    if (demolitionType[i].checked) {
	    	if (demolitionType[i].value == 0) {
	    		var carStyleId = $("#carStyleId").val();
	    		if (carStyleId == "") {
	    			jAlert("请选择车型！");
	    			return;
	    		}
	    		if (confirm("确定要拆除该车型下所有的车吗？")) {
	    			$("#myPageForm").submit();
	    		}
	    	} else {
	    		var vehicleId = $("#vehicleId").val();
	    		if (vehicleId == "") {
	    			jAlert("车辆不存在，请重新输入VIN！");
	    			return;
	    		}
	    		if (confirm("确定要拆除该车吗？")) {
	    			$("#myPageForm").submit();
	    		}
	    	}
	    	
	      } 
	  } 
}

</script>
<body>

<form id="myPageForm" action="<%=path%>/dealer/proposal/demolitionVehicle.vti"" method="post" >
	<div class="bd_rt" id="audit">
		<div class="bd_rt_nav clearfix">
	    	<c:if test="${menuType == 1}" >
				<jsp:include page="/pub/navigate/navigationUserMenu.jsp" flush="true"/>
			</c:if>
			<c:if test="${menuType == 0}" >
	    		<span >当前位置：车辆更新  > 车辆拆除</span>
			</c:if>
		</div>
		
		<aside class="bd_rt_main clearfix" >
		<div class='msgResult<html:errors property="MSG_RESULT_LEVEL"/>' id='msg_result' style="DISPLAY:none;"><html:errors property="MSG_RESULT"/></div>
		<div style="height:200px;">
				<div style="padding-left: 55px;padding-top: 20px;padding-bottom:10px" >
				拆除类型：
				<form:radiobutton path="vehicleVO.demolitionType" value="0" onclick="demolitionTypeClick()"  checked="true" />车型
                <form:radiobutton path="vehicleVO.demolitionType" value="1" onclick="demolitionTypeClick()" />车辆
                </div>
				<table id="demolitionByCarStyle" border="0" cellpadding="0" cellspacing="0" class="tableBox" >
					<tr >
						<td class="tdTitle">拆除车型：</td>
						<td class="tdSpace" >
						<form:select path='vehicleVO.carStyleId' >
		                        <form:options items="${carStyleList}" itemLabel="name" itemValue="id" htmlEscape="false" />
		                </form:select>
						</td>
					</tr>
				</table>
				<table id="demolitionByVehicle" border="0" cellpadding="0" cellspacing="0" class="tableBox" style="display: none" >
				<form:hidden id="vehicleId" path="vehicleVO.id"/>
				<tr>
	                <td class="tdTitle"> VIN码：</td>
	                <td class="tdSpace" >
	                    <form:input id="vin" path="vehicleVO.vin" cssClass="size" maxlength="17" cssStyle="width:200px" />
	                </td>
	            </tr>
	            <tr>
	                <td class="tdTitle">车牌：</td>
	                <td class="tdSpace" >${vehicleVO.plateNumber}<span id="plateNumber" />
	                </td>
	            </tr>
	            <tr>
	                <td class="tdTitle"> 车型：</td>
	                <td class="tdSpace" > ${vehicleVO.carStyleId_Nick}<span id="carStyleId_Nick" />
	                </td>
	            </tr>
				<tr>
                <td class="tdTitle">网络代码：</td>
                <td class="tdSpace" >${vehicleVO.dealer.dealerCode}<span id="dealerCode" />
                </td>
	            </tr>
	            <tr>
	                <td class="tdTitle">经销商名称：</td>
	                <td class="tdSpace" >${vehicleVO.dealer.cnName}<span id="companyName" />
	                </td>
	            </tr>
	            </table>
			<div style="padding-left: 150px;" >
			<input name="" type="button" onclick="save()" class="btn_submit" value="提 交" />
			</div>
		</div>	
		</aside>
		<div class="bd_rt_ft" />
	</div>
</form>

</body>
</html>