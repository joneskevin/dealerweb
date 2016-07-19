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
	dealerCodeBlur();

	$("#dealerCode").blur(function() {
		dealerCodeBlur();
	});

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

function dealerCodeBlur() {
	var dealerCode = $("#dealerCode").attr("value");
	var targetUrl = "<%=request.getContextPath()%>/dealer/vehicle/findDealerInfo.vti?dealerCode=" + dealerCode;
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
				if (responseData.data != null && responseData.data.company != null) {
					var company = responseData.data.company;
					if (company != null) {
						var companyId = company.id;
						if (companyId > 0) {
							$("#companyId").val(companyId);
						}
						var companyName = company.cnName;
						if (companyName.length > 0) {
							$("#companyName").text(companyName);
						}
						var saleCenterName = company.parentName;
						if (saleCenterName.length > 0) {
							$("#saleCenterName").text(saleCenterName);
						}
						var provinceName = company.regionProvinceId_Nick;
						if (provinceName.length > 0) {
							$("#provinceName").text(provinceName);
						}
						var cityName = company.regionCityId_Nick;
						if (cityName.length > 0) {
							$("#cityName").text(cityName);
						}
						var dealerTypeNick = company.dealerType_Nick;
						if (dealerTypeNick.length > 0) {
							$("#dealerTypeNick").text(dealerTypeNick);
						}

						//是否限购城市
						var isRestrictionCity = company.isRestrictionCity;
						$("#isRestrictionCity").val(isRestrictionCity);
						if (isRestrictionCity != null && isRestrictionCity == 0) {
							$("#plateNumberEM").css("display", "block");
							$("#plateNumberEM1").css("display", "none");
						} else {
							$("#plateNumberEM").css("display", "none");
							$("#plateNumberEM1").css("display", "block");
						}

					}
				} else {
					$("#companyId").val("");
					$("#companyName").text("");
					$("#saleCenterName").text("");
					$("#provinceName").text("");
					$("#cityName").text("");
					$("#dealerTypeNick").text("");
					$("#isRestrictionCity").val("");
				}

			} else {
				jAlert(responseData.message);
			}
		}
	});
}

function save() {
	var companyId = $("#companyId").val();
	if (companyId == "") {
		jAlert("经销商不存在，请重新输入网络代码！");
		return;
	}
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

	var isRestrictionCity = $("#isRestrictionCity").val();
	var plateNumber = $("#plateNumber").val();
	var re = /^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
	if (isRestrictionCity != "" && isRestrictionCity == 0) {
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

	$("#myPageForm").submit();
}
</script>

<body>
    <form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/vehicle/add.vti">
        <form:hidden id="companyId" path="vehicleVO.companyId" />
        <form:hidden id="isRestrictionCity" path="vehicleVO.dealer.isRestrictionCity" />
        <table border="0" cellpadding="0" cellspacing="0" class="tableBox">
            <tr>
                <td class="tdTitle">网络代码：</td>
                <td class="tdSpace" colspan="3">
                    <form:input id="dealerCode" path="vehicleVO.dealer.dealerCode" cssClass="size" maxlength="7" cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">经销商名称：</td>
                <td class="tdSpace" colspan="3">${vehicleVO.dealer.cnName}<span id="companyName" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">分销中心：</td>
                <td class="tdSpace" colspan="3">${vehicleVO.dealer.parentName}<span id="saleCenterName" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">省份：</td>
                <td class="tdSpace">${vehicleVO.dealer.regionProvinceId_Nick}<span id="provinceName" />
                </td>
                <td class="tdTitle">城市：</td>
                <td class="tdSpace">${vehicleVO.dealer.regionCityId_Nick}<span id="cityName" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">网络形态：</td>
                <td class="tdSpace" colspan="3">${vehicleVO.dealer.dealerType_Nick}<span id="dealerTypeNick" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><em>*</em> 车型：</td>
                <td class="tdSpace" colspan="3">
                    <form:select id="carStyleId" path='vehicleVO.carStyleId'>
                        <form:options items="${carStyleList}" itemLabel="name" itemValue="id" htmlEscape="false" />
                    </form:select>
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><em>*</em> VIN码：</td>
                <td class="tdSpace" colspan="3">
                    <form:input id="vin" path="vehicleVO.vin" cssClass="size" maxlength="17"  cssStyle="width:200px" /><em> 只能输入半角的大写字母和数字！</em>
                </td>
            </tr>
            <tr>
                <td class="tdTitle"><span id="plateNumberEM" style="display:block;"><em> *</em>车牌：</span>
                    <span id="plateNumberEM1" style="display:none;">车牌：</span>
                </td>
                <td class="tdSpace" colspan="3">
                    <form:input id="plateNumber" path="vehicleVO.plateNumber" cssClass="size" maxlength="7" cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">上牌日期：</td>
                <td class="tdSpace">
                    <input type="text" id="licensingTime" name="licensingTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" value="<fmt:formatDate value="${vehicleVO.licensingTime}" type="both" pattern="yyyy-MM-dd" />" class="size date" readonly="true" />
                </td>
                <td class="tdTitle">上牌人：</td>
                <td class="tdSpace">
                    <form:input id="licensingExecutorName" path="vehicleVO.licensingExecutorName" cssClass="size" maxlength="30" cssStyle="width:200px" />
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
