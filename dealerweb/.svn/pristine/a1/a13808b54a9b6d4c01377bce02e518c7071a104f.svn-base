<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<body class="body_pop">
<form id="myPageForm">
<div class="pop-tableBox">
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
			<input id="filingProposal_startTime" name="filingProposal.startTime" class="pop-text-short_noborder"	value="<fmt:formatDate value="${filingProposalCV.filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="true" type="text" />
			</td>
        <td width="15%" align="right"><span>*</span>结束日期：</td>
        <td>        
        	<input id="filingProposal_endTime" name="filingProposal.endTime" class="pop-text-short_noborder"	value="<fmt:formatDate value="${filingProposalCV.filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />" readonly="true" type="text" />
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right"><span>*</span>目的地：</td>
        <td colspan="3">
        	<input id="filingProposal_description" name="filingProposal.description" class="pop-text-long_noborder" readonly="true" value="${filingProposalCV.filingProposal.description}" type="text"/>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right" valign="top">报备原因：</td>
        <td colspan="3" >
        	<textarea id="filingProposal_reason" name="filingProposal.reason" cols ="100" rows = "3" readonly="true" class="textarea_noborder">${filingProposalCV.filingProposal.reason}</textarea>
        </td>
    </tr>
</table>
</div>
</form>
</body>
</html>