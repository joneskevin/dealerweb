<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/pub/jstl.jsp"%>
<%@ page import="com.ava.resource.GlobalConstant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/main.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/dealer/css/style.css" />
<%@ include file="/dealer/include/meta.jsp"%>
</head>
<script>
$(document).ready(function() {
	//加载多<strong>文件</strong>上传的JS
	$('#multiFileId').MultiFile({
		list : '#addMultiFileIdList',
		accept:'png|jpg|bmp|jpeg|doc|docx|xls|xlsx|ppt|pptx|pdf',
		max:10, 
		STRING: {
	           remove:'删除',
	           selected:'文件重复: $file',  
	           denied:'不支持上传该文件类型 $ext!',
	           duplicate:'文件重复:\n$file'
		}
	});
	
	var isAddPlatenumberCity = $("#isAddPlatenumberCity").attr("value");
	if (isAddPlatenumberCity != null && isAddPlatenumberCity == 0) {
		$("#plateNumberEM").css("display", "block");
		$("#plateNumberEM1").css("display", "none");
	} else {
		$("#plateNumberEM").css("display", "none");
		$("#plateNumberEM1").css("display", "block");
	}

	$("#vin").blur(function() {
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
							var plateNumber = vehicle.plateNumber;
							if (plateNumber.length > 0) {
								$("#plateNumber").val(plateNumber);
							}
							var carStyleId = vehicle.carStyleId;
							if (carStyleId > 0) {
								$("#carStyleId").val(carStyleId);
							}
							var licensingTimeStr = vehicle.licensingTimeStr;
							if (licensingTimeStr.length > 0) {
								$("#licensingTime").val(licensingTimeStr);
							}
							var licensingExecutorName = vehicle.licensingExecutorName;
							if (licensingExecutorName.length > 0) {
								$("#licensingExecutorName").val(licensingExecutorName);
							}
						}
					}

				} else {
					jAlert(responseData.message);
				}
			}
		});
	});

});

function save() {
	var carStyleId = $("#carStyleId").val();
	if (carStyleId == "") {
		jAlert("请选择车型！");
		return;
	}
	var vin = $("#vin").val();
	if (vin == "") {
		jAlert("VIN码不能为空！");
		return;
	}
	if (vin.length != 17) {
		jAlert("VIN码长度需要17位！");
		return;
	}
	
	var vinStrs = vin.split("");
	if (vinStrs != null && vinStrs.length > 0) {
		for (var i = 0; i < vinStrs.length; i++) { 
			var reg = /^[0-9A-Z]*$/g;
			if (!(reg.test($.trim(vinStrs[i])))) {
				 jAlert("只能输入半角的大写字母和数字，字符【"+ vinStrs[i] +"】不满足规范！");
			     return;
			} 
		} 
	}

	var isAddPlatenumberCity = $("#isAddPlatenumberCity").val();
	var plateNumber = $("#plateNumber").val();
	var re = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	if (isAddPlatenumberCity != "" && isAddPlatenumberCity == 0) {
		if (plateNumber == "") {
			jAlert("车牌不能为空！");
			return;
		} else if (plateNumber.search(re) == -1) {
			jAlert("车牌格式无效！");
			return;
		}
	} else {
		if (plateNumber != "" && plateNumber.search(re) == -1) {
			jAlert("车牌格式无效！");
			return;
		}
	}

	var contactName = $("#contactName").val();
	if (contactName == "") {
		jAlert("联系人名称不能为空！");
		return;
	}

	var contactPhone = $("#contactPhone").val();
	if (contactPhone == "") {
		jAlert("联系电话不能为空！");
		return;
	}
	
	var multiFileTitle=$('.MultiFile-title');
	if((undefined==multiFileTitle || null==multiFileTitle || multiFileTitle.length<=0) ){
		alert("请选择附件");
		return ;
	}
	if (confirm("确定要换装吗？")) {
		$("#myPageForm").submit();
	}
}
</script>

<body>
    <form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/proposal/addReplace.vti" enctype="multipart/form-data">
    	<form:hidden path="proposalCompanyVehcileInfo.company.id" />
        <form:hidden path="proposalCompanyVehcileInfo.proposal.replaceVehicleId" />
        <form:hidden id="isAddPlatenumberCity" path="proposalCompanyVehcileInfo.proposal.isAddPlatenumberCity" />
        <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
            <tr>
                <td class="tdTitle">分销中心：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.company.parentName}</td>
            </tr>
            <tr>
                <td class="tdTitle">省份：</td>
                <td class="tdSpace">${proposalCompanyVehcileInfo.company.regionProvinceId_Nick}</td>
                <td class="tdTitle">城市：</td>
                <td class="tdSpace">${proposalCompanyVehcileInfo.company.regionCityId_Nick}</td>
            </tr>
            <tr>
                <td class="tdTitle" style="width:130px;">经销商状态：</td>
                <td class="tdSpace">${proposalCompanyVehcileInfo.company.isKeyCity_Nick}</td>
                <td class="tdTitle">网络形态：</td>
                <td class="tdSpace">${proposalCompanyVehcileInfo.company.dealerType_Nick}</td>
            </tr>
            <tr>
                <td class="tdTitle">网络代码：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.company.dealerCode}</td>
            </tr>
            <tr>
                <td class="tdTitle">经销商名称：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.company.cnName}</td>
            </tr>
            <tr>
                <td class="tdTitle"> 车型：</td>
                <td class="tdSpace" colspan="3">
                    <form:hidden path="proposalCompanyVehcileInfo.vehicleInstallationPlan.dessCarStyleId" /> ${proposalCompanyVehcileInfo.vehicleInstallationPlan.dessCarStyleNick}
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><em>*</em> VIN码：</td>
                <td class="tdSpace" colspan="3">
                    <form:input id="vin" path="proposalCompanyVehcileInfo.vehicle.vin" cssClass="size" maxlength="17" cssStyle="width:200px" /><em> 只能输入半角的大写字母和数字！</em>
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><span id="plateNumberEM" style="display:block;"><em> *</em>车牌：</span>
                    <span id="plateNumberEM1" style="display:none;">车牌：</span>
                </td>
                <td class="tdSpace" colspan="3">
                    <form:input id="plateNumber" path="proposalCompanyVehcileInfo.vehicle.plateNumber" cssClass="size" maxlength="7" cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">上牌日期：</td>
                <td class="tdSpace">
                    <input type="text" id="licensingTime" name="vehicle.licensingTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" value="<fmt:formatDate value="${proposalCompanyVehcileInfo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd" />" class="size date" readonly="true" />
                </td>
                <td class="tdTitle">上牌人：</td>
                <td class="tdSpace">
                    <form:input id="licensingExecutorName" path="proposalCompanyVehcileInfo.vehicle.licensingExecutorName" cssClass="size" maxlength="30" cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle"> <em>*</em> 联系人：</td>
                <td class="tdSpace">
                    <form:input id="contactName" path="proposalCompanyVehcileInfo.proposal.contactName" cssClass="size" maxlength="5" cssStyle="width:200px" />
                </td>
                <td class="tdTitle"> <em>*</em> 联系电话：</td>
                <td class="tdSpace">
                    <form:input id="contactPhone" path="proposalCompanyVehcileInfo.proposal.contactPhone" cssClass="size" maxlength="15" cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
		    	<td class="tdTitle"><em>*</em>上传附件：</td>
		        <td colspan="3">
					<fieldset style="width:99%;">
						<legend><em>请上传购车发票、行驶证、强险保单附件</em></legend>
						<!-- 注意这里的attach名字为固定，如果要控制上传的格式，则追加accept="doc|txt|jsp"  最大上传量maxlength="3" -->
						<input type="file" name="attach" id="multiFileId" class="text-input"/>
						<div id="addMultiFileIdList"></div>
					</fieldset>
		        </td>
   			</tr>
            <tr>
                <td colspan="4" class="tdSpace" align="center">
                    <input name="" type="button" onclick="save()" class="btn_submit" value="提 交" />
                    <input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
                </td>
            </tr>
        </table>
    </form>
</body>
</html>
