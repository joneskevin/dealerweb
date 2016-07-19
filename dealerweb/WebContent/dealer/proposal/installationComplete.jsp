<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.domain.entity.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
</head>
</head>
<script>
$(document).ready(function() {
	$("#operationTime").datepicker();
	
	 $("#operationTime").change(function(){
	       var thetime=$(this).val();  
		   var d = new Date(Date.parse(thetime.replace(/-/g,"/")));  
	  	   var curDate=new Date();  
		   if(d > curDate){
			alert("请选择小于等于今天的时间！");  
		}});   
	 
	 $("#uniqueId").blur(function(){
		  var uniqueId = $("#uniqueId").attr("value");
		  var targetUrl = "<%=request.getContextPath()%>/dealer/box/findBoxInfo.vti?uniqueId=" + uniqueId;
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
			         if (responseData.data != null && responseData.data.box != null) {
		        		 var box = responseData.data.box;
		        		 $("#simMobile").val(box.simMobile);
			    	  	} else {
			    	  	 $("#simMobile").val("");
			    	  	} 
			         
			     } else {
			      jAlert(responseData.message);
			     }
			    }
			 });
		});
});
	function installationComplete() {
		var uniqueId = $("#uniqueId").val();
		if (uniqueId == "") {
			jAlert("设备号不能为空");
			$("#uniqueId").focus(); 
			return;
		}
		
		var simMobile = $("#simMobile").val();
		if (simMobile == "") {
			jAlert("通讯号不能为空");
			$("#simMobile").focus(); 
			return;
		}
		if (!(simMobile.match(/^1[3|4|5|8][0-9]\d{4,8}$/)) || simMobile.length != 11) {
			jAlert("通讯号格式不正确");
			$("#simMobile").focus(); 
		    return;
		}
		
		var operationTime = $("#operationTime").val();
		if (operationTime == "") {
			jAlert("安装时间不能不能为空");
			return;
		} 
		
		var operationName = $("#operationName").val();
		if (operationName == "") {
			$("#operationName").focus(); 
			jAlert("安装人不能为空");
			return;
		} 
		   
		if (confirm("确定安装完成？")) {
			$("#myPageForm").submit();
		}
	}
	
</script>
<body>
	<form id="myPageForm"
		action="<%=request.getContextPath()%>/dealer/proposal/installationComplete.vti" method="post">
		<form:hidden path="vo.proposal.id" />
		<table border="0" cellpadding="0" cellspacing="0" 
			class="tableBox">
			<tr>
				<td class="tdTitle">分销中心：</td>
				<td class="tdSpace">${vo.company.parentName}</td>
			</tr>
			<tr>
				<td class="tdTitle">省份：</td>
				<td class="tdSpace">${vo.company.regionProvinceId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">城市：</td>
				<td class="tdSpace">${vo.company.regionCityId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">经销商名称：</td>
				<td class="tdSpace">${vo.company.cnName}</td>
			</tr>
			<tr>
				<td class="tdTitle">网络代码：</td>
				<td class="tdSpace">${vo.company.dealerCode}</td>
			</tr>
			<tr>
				<td class="tdTitle">经销商状态：</td>
				<td class="tdSpace">${vo.company.isKeyCity_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">网络形态：</td>
				<td class="tdSpace">${vo.company.dealerType_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">车牌：</td>
				<td class="tdSpace">${vo.vehicle.plateNumber}</td>
			</tr>
			<tr>
				<td class="tdTitle">车型：</td>
				<td class="tdSpace">${vo.vehicle.carStyleId_Nick}</td>
			</tr>
			<tr>
				<td class="tdTitle">VIN码：</td>
				<td class="tdSpace">${vo.vehicle.vin}</td>
			</tr>
			<tr>
				<td class="tdTitle">上牌日期：</td>
				<td class="tdSpace"><fmt:formatDate value="${vo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td class="tdTitle">上牌人：</td>
				<td class="tdSpace">${vo.vehicle.licensingExecutorName}</td>
			</tr>
			<tr>
				<td class="tdTitle">联系人：</td>
				<td class="tdSpace">${vo.proposal.contactName}</td>
			</tr>
			<tr>
				<td class="tdTitle">联系电话：</td>
				<td class="tdSpace">${vo.proposal.contactPhone}</td>
			</tr>
			<tr>
				<td class="tdTitle">提交时间：</td>
				<td class="tdSpace"><fmt:formatDate value="${vo.proposal.proposalTime}" type="both" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td class="tdTitle"> 状态：</td>
				<td class="tdSpace"><em>${vo.proposal.status_nick}</em></td>
			</tr>
			<tr>
				<td class="tdTitle"><em>*</em>设备号：</td>
				<td class="tdSpace"><form:input id="uniqueId" path="vo.tbox.uniqueId" maxlength="30" cssClass="size" cssStyle="width:200px" /></td>
			</tr>
			<tr>
				<td class="tdTitle"><em>*</em>通讯号：</td>
				<td class="tdSpace"><form:input id="simMobile" path="vo.tbox.simMobile" maxlength="20" cssClass="size" cssStyle="width:200px" /></td>
			</tr>
			<tr>
				<td class="tdTitle"><em>*</em>安装时间：</td>
				<td class="tdSpace">
				<input id="operationTime"
					name="boxOperation.operationTime" class="size date"
					value="<fmt:formatDate value="${vo.boxOperation.operationTime}"
				type="both" pattern="yyyy-MM-dd" />"
					readonly="readonly" type="text" />
					
				</td>
			</tr>
			<tr>
				<td class="tdTitle"><em>*</em>安装人：</td>
				<td class="tdSpace"><form:input id="operationName" path="vo.boxOperation.operationName" maxlength="15" cssClass="size" cssStyle="width:200px" /></td>
			</tr>
			<tr>
				<td colspan="4" class="tdSpace" align="center"><input name=""
					type="button" class="btn_submit" onclick="installationComplete()" value="提  交" /></td>
			</tr>
		</table>
	</form>

</body>
</html>