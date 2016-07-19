<%@ page contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<meta name="MobileOptimized" content="320">
<meta name="format-detection" content="telephone=no">
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/mobile.css" />

<script type="text/javascript" src = "<%=path%>/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src = "<%=path%>/js/jquery/jquery-ui-1.10.2.min.js"></script>
</head>

<script>
var pageFormObj = document.getElementById("myPageForm");
function comfirmCancel(){
	     var vin = $("#vin").val();
	     var plateNumber = $("#plateNumber").val();
	     var uniqueId = $("#uniqueId").val();
		 targetUrl = "<%=path%>/dealer/proposal/confirmInstallationResult.vti";
		 targetUrl +=  "?vin=" + vin;
		 targetUrl +=  "&plateNumber=" + plateNumber;
		 targetUrl +=  "&uniqueId=" + uniqueId;
	  	 targetUrl += "&random=" + Math.random();
		 $.ajax({
		 url: targetUrl,
   		  type: 'GET',
		      dataType: 'json',
		      //timeout: 10000,
		      error: function(){
		    	  $("#message").text("系统异常");
		      },
		      success: function(responseData){
		      if (responseData.code == 1) {
		    	  $("#message").text(responseData.message);
		    	 if (responseData.data != null && responseData.data.proposalCompanyVehcileInfo != null) {
		    		 	var proposalCompanyVehcileInfo = responseData.data.proposalCompanyVehcileInfo;
		    		 	if (proposalCompanyVehcileInfo.vehicle != null && proposalCompanyVehcileInfo.tbox != null) {
			        		var vehicle = proposalCompanyVehcileInfo.vehicle;
		    		 		var box = proposalCompanyVehcileInfo.tbox;
		    		 		siteDisplay(true);
		    		 		$("#result_companyName").text("经销商：" + vehicle.companyName);
		    		 		$("#result_vin").text("vin码：" + vehicle.vin);
		    		 		$("#result_plateNumber").text("车牌号：" + vehicle.plateNumber);
		    		 		$("#result_uniqueId").text("设备号：" + box.uniqueId);
		    		 	}
		    	  	} else {
		    	  		comfirmCancel(false);
		    	  		$("#message").text(responseData.message);
		    	  	}
		         
		     } else {
		    	 siteDisplay(false);
		    	 $("#message").text(responseData.message);
		    	 
		     }
		    }
	});
}

function siteDisplay(flag) {
	if (flag != null) {
		if(flag){
			$("#result_companyName").show();
			$("#result_vin").show();
			$("#result_plateNumber").show();
			$("#result_uniqueId").show();
		} else {
			$("#result_companyName").hide();
			$("#result_vin").hide();
			$("#result_plateNumber").hide();
			$("#result_uniqueId").hide();
		}
	}
} 
</script>

<body>
<form id="myPageForm" action="<%=path%>/dealer/proposal/confirmInstallationResult.vti" method="get" >
<p>查询车机绑定设备情况</p>
    <div class="txt clearfix"><input type="text" id="vin" name="vin" placeholder="请输入vin码"  maxlength="30" class="fr"/><span>vin码</span></div>
    <div class="txt clearfix"><input type="text" id="plateNumber" name="plateNumber" placeholder="请输入车牌号" maxlength="20"  class="fr"/><span>车牌号</span></div>
    <div class="txt clearfix"><input type="text" id="uniqueId" name="uniqueId" placeholder="请输入设备号" maxlength="30" class="fr"/><span>设备号</span></div>
    <div class="txt clearfix"><input type="button" value="查  询" onclick="comfirmCancel()" class="btn" /></div>
    <div class="message clearfix" style="display:block;" id="message" ></div>
    <div class="message clearfix" id="result_companyName" ></div>
    <div class="message clearfix" id="result_vin" ></div>
    <div class="message clearfix" id="result_plateNumber" ></div>
    <div class="message clearfix" id="result_uniqueId" ></div>
</form>
</body>
</html>