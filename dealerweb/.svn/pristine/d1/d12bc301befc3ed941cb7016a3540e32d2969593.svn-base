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
	function save() {
		$("#myPageForm").submit();
	}
</script>
<body style="overflow-x: hidden;">
    <form id="myPageForm" method="post" action="<%=request.getContextPath()%>/dealer/proposal/edit.vti">
        <input type="hidden" name="proposalType" value="${proposalCompanyVehcileInfo.proposal.type}" />
        <form:hidden path="proposalCompanyVehcileInfo.proposal.id" />
        <form:hidden path="proposalCompanyVehcileInfo.vehicle.vin" />
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
                <td class="tdTitle">网络代码：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.company.dealerCode}</td>
            </tr>
            <tr>
                <td class="tdTitle">经销商名称：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.company.cnName}</td>
            </tr>
            <tr>
                <td class="tdTitle" style="width:130px;">经销商状态：</td>
                <td class="tdSpace">${proposalCompanyVehcileInfo.company.isKeyCity_Nick}</td>
                <td class="tdTitle">网络形态：</td>
                <td class="tdSpace">${proposalCompanyVehcileInfo.company.dealerType_Nick}</td>
            </tr>
            <tr>
                <td class="tdTitle">车型：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.vehicle.carStyleId_Nick}</td>
            </tr>
            <tr>
                <td class="tdTitle"> VIN码：</td>
                <td class="tdSpace" colspan="3">${proposalCompanyVehcileInfo.vehicle.vin}</td>
            </tr>
            <tr>
                <td class="tdTitle"><em>*</em> 车牌：</td>
                <td class="tdSpace" colspan="3">
                    <form:input path="proposalCompanyVehcileInfo.vehicle.plateNumber" cssClass="size" maxlength="7" cssStyle="width:200px" />
                </td>
            </tr>
            <tr>
                <td class="tdTitle">上牌日期：</td>
                <td class="tdSpace">
                    <input type="text" id="licensingTime" name="vehicle.licensingTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',autoPickDate:true})" value="<fmt:formatDate value="${proposalCompanyVehcileInfo.vehicle.licensingTime}" type="both" pattern="yyyy-MM-dd" />" class="size date" readonly="true" />
                </td>
                <td class="tdTitle">上牌人：</td>
                <td class="tdSpace">
                    <form:input path="proposalCompanyVehcileInfo.vehicle.licensingExecutorName" cssClass="size" maxlength="5" cssStyle="width:200px" />
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
                <td colspan="4" class="tdSpace" align="center">
                    <input name="" type="button" onclick="save()" class="btn_submit" value="提 交" />
                    <input type="button" class="btn_submit" onclick="javascript:new parent.dialog().reset();" value="取 消" />
                </td>
            </tr>
        </table>
    </form>
</body>

</html>

