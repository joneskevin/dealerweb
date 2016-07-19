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
	function save(advice){
		var adviceDescription=$('#adviceDescription').val();
		if(null!=adviceDescription && adviceDescription.length>100){
			alert("意见长度不能超过100");
			return;
		}
		 $("#advice").val(advice);
		$("#myPageForm").submit();
	}
</script>

<body class="body_pop">
<form id="myPageForm" action="<%=path%>/dealer/declareManager/declareApproval.vti" method="post">
<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingApproval.filingProposalId}" />
<input type="hidden" id="advice" name="advice" value="" />
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
    	<td width="15%" align="right">开始日期：</td>
        <td width="35%"><fmt:formatDate value="${filingProposalCV.filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        <td width="15%" align="right">结束日期：</td>
        <td><fmt:formatDate value="${filingProposalCV.filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
    </tr>
    <tr>
    	<td width="15%" align="right">提交时间：</td>
        <td width="35%"><fmt:formatDate value="${filingProposalCV.filingProposal.commitTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
        <td width="15%" align="right">报备状态：</td>
        <td>${filingProposalCV.filingProposal.status_nick}</td>
    </tr>
    <tr>
    	<td width="15%" align="right">目的地：</td>
        <td colspan="3">
        	<input id="filingProposal_description" name="filingProposal.description" class="pop-text-long_noborder" readonly="true" value="${filingProposalCV.filingProposal.description}" type="text"/>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right" valign="top">报备原因：</td>
        <td colspan="3" >
        	<textarea id="filingProposal_reason" name="filingProposal.reason" cols ="100" rows = "2" readonly="true" class="textarea_noborder">${filingProposalCV.filingProposal.reason}</textarea>
        </td>
    </tr>
    <tr>
    	<td width="15%" align="right" valign="top">意见描述：</td>
        <td colspan="3">
        	<textarea id="adviceDescription" name="adviceDescription" cols ="100"  class="textarea_noborder" style="border:1px solid #666;padding:1px 0;"></textarea>
        </td>
    </tr>
</table>
<div class="input_btn_div">
	<input type="button" onclick="save(3)" value="同意"/>
	<input type="button" onclick="save(4)" value="拒绝" />
</div>

</div>
<b class="pop_more_btn"><span>报备历史明细</span></b>
	<aside class="pop_more">
	    <c:if test="${filingProposalList != null}" >
		<c:forEach var="filingProposal" items="${filingProposalList}">
			<p style="word-break:break-all">
				<span>开始时间：<fmt:formatDate value="${filingProposal.startTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				<span>结束时间：<fmt:formatDate value="${filingProposal.endTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span> 
				<span>类型： <c:out value="${filingProposal.type_nick}" /></span><br />
				                    报备原因： <c:out value="${filingProposal.reason}" />
			</p>
		</c:forEach>
		</c:if>
	</aside>
</form>
</body>
</html>